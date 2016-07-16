package com.glen.gui;

import com.glen.Tools.Matrix4f;
import com.glen.Tools.ircClient;
import com.glen.main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class OptionsBox extends JPanel implements MouseMotionListener, MouseListener{
    private List options;
    private JPanel inventorypanel;
    private int initX;
    private int initY;
    private int invWidth;
    int invHeight;
    private Matrix4f panelBox;
    private int[] optionBoxes;
    private Point curs;
    private BufferedImage optionBoxImg;
    private String itemName;
    private  Matrix4f[] optrect;
    private Color[] optionColor;
    private int clickedOption;
    private long startTime;
    private boolean canClose = false;

    public OptionsBox(int initX, int initY,String itemName, List options, JPanel inventoryPanel, int invWidth, int invHeight){

        this.invWidth = invWidth;
        this.invHeight = invHeight;
        this.itemName = itemName;
        this.initX = initX;
        this.initY = initY;
        this.inventorypanel = inventoryPanel;
        this.options = options;

        this.setPreferredSize(new Dimension(inventoryPanel.getWidth(), inventoryPanel.getHeight()));
        inventoryPanel.add(this);
        this.setOpaque(false);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.validate();

        startTime = System.currentTimeMillis() / 1000;
        optrect = new Matrix4f[options.size()];
        optionColor = new Color[options.size()];

        File file = new File("res/1014_0.png");
        try {
            optionBoxImg = ImageIO.read(file.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("unable to open " + file.getAbsoluteFile());
        }

    }

    protected void paintComponent(Graphics g) {
       long lastTime = System.currentTimeMillis() / 1000;
        if(lastTime - startTime > 0.2){
            canClose = true;
        }
        this.requestFocus();
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setFont(new Font("TimesRoman", Font.BOLD, (int) (invWidth * 0.06)));
        FontMetrics fm = g2d.getFontMetrics();
        if(options != null) {
            Rectangle2D defaultRect = fm.getStringBounds("choose a option" + itemName, g2d);
            int largestString = (int) defaultRect.getWidth();
            int largestStringInt = -1;
            for (int i = 0; i < options.size(); i++) {
                Rectangle2D testRect = fm.getStringBounds(options.get(i).toString() + itemName, g2d);
                if (largestString < testRect.getWidth()) {
                    largestString = (int) testRect.getWidth();
                    largestStringInt = i;

                }
            }

            Rectangle2D rect;
            if(largestStringInt < 0){
                rect = fm.getStringBounds("choose a option" + itemName, g2d);
            }else {
                rect = fm.getStringBounds(options.get(largestStringInt).toString() + itemName, g2d);
            }

            int width = (int) ((int) (rect.getWidth() + (invWidth*0.10))*0.80);
            int height = (int) ((rect.getHeight() * 2) * (options.size() ));
            int x = initX - width / 2;
            int y = (int) (initY - (rect.getHeight() / 3));
            int boxHeight = (int) (((int) rect.getHeight() *1.5)*(options.size()+1));


            if (x < 0) x = 0;
            if (y < 0) y = 0;
            if (x + width > invWidth) x = invWidth - width;
            if (y + height > invHeight) y = invHeight - height;

            g2d.drawImage(optionBoxImg, x, y, width, boxHeight, null);
            g2d.setColor(Color.BLACK);
            panelBox = new Matrix4f(x, y, width, boxHeight);

            optionBoxes = new int[options.size()];


            for (int i = 0; i < options.size(); i++) {

                if(optionColor[i] == null){
                    optionColor[i] = Color.WHITE;
                }

                int optionsY = (int)( (rect.getHeight()*2) +(y + rect.getHeight() * i));
                g2d.setColor(Color.WHITE);
                g2d.drawString("Choose option", x + 20, (int) (y + rect.getHeight()));
                g2d.setColor(optionColor[i]);
                g2d.drawString(options.get(i).toString(), (x + 20), optionsY );

                Rectangle2D optrectString = fm.getStringBounds(options.get(i).toString(), g2d);
                optrect[i] = new Matrix4f(x+20, optionsY -15, width, (int)(optrectString.getHeight()));
                g2d.setColor(Color.ORANGE);
                // System.out.print();
                g2d.drawString(itemName, (int) ((x + (width * 0.05)) + optrectString.getWidth()) + 15, optionsY);


                optionBoxes[i] = (int) (optrectString.getY());
            }

        }

        g2d.dispose();

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {


        curs = e.getPoint();
        for(int i = 0; i < optrect.length; i++){
            if((e.getX() > optrect[i].getX() && e.getX() < optrect[i].getX() + optrect[i].getWidth()) && (e.getY() > optrect[i].getY() && e.getY() < optrect[i].getY() + optrect[i].getHeight())){

                optionColor[i] = Color.YELLOW;
                clickedOption = i;
            }else{
                optionColor[i] = Color.WHITE;
            }
        }

        if((e.getX() < panelBox.getX() || e.getX() > panelBox.getX() + panelBox.getWidth()) || e.getY() < panelBox.getY() || e.getY() > panelBox.getY() + panelBox.getHeight()){
            VirtualInventory.clearOptionBox();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(options != null && e.getButton() == 1 && Main.IRC != null) {
            Main.IRC.sendIRCMessage("inv " +  itemName.replaceAll(" ", "_")+ " " +options.get(clickedOption).toString() );
            inventorypanel.remove(this);
            inventorypanel.requestFocus();
            inventorypanel.revalidate();
        }
        if(canClose && e.getButton() == 1) {
            inventorypanel.remove(this);
            inventorypanel.requestFocus();
            inventorypanel.revalidate();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        inventorypanel.remove(this);
        inventorypanel.revalidate();
    }
}
