package com.glen.gui;

import com.glen.Tools.Matrix4f;
import com.glen.main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class VirtualInventory extends JPanel implements MouseMotionListener, MouseListener{

    private List<Inventory> items;
    private BufferedImage image;
    private String[] id;
    private String[] itemName = new String[28];
    private int[]  amount = new int[28];
    private List[] actions = new List[28];
    private boolean[] stackable = new boolean[28];

    private Matrix4f[] boundingBoxes = new Matrix4f[28];
    private int curSlot;
    private int invpanelX;
    private int invpanelY;
    private Point curs;


    private Color invColor = new Color(73, 63, 51);
    private int gridX;
    private int gridY;
    private int gridWidth;
    private int gridHeight;
    private Matrix4f currentHoverItem = null;
    private int currentHoverItemSlot;

    private JLabel itemInfo;
    private JPanel infoPanel;
    public static JPanel optionBox;
    private static JPanel thisPanel;

    private String urlStr;
    private URL url;
    private HttpURLConnection connection;
    private boolean onFirstTry = true;


    private File imgFile;
    private BufferedImage backgroundImage;

    public VirtualInventory(int invpanelX, int invPanelY) {
        this.invpanelX = invpanelX;
        this.invpanelY = invPanelY;
        this.setBackground(invColor);
        this.setOpaque(false);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.setLayout(new FlowLayout());
        this.validate();
        thisPanel = this;
        File backgroundImgFile = new File("res/14_0.png");
        try {
            backgroundImage = ImageIO.read(backgroundImgFile.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setItems(List items) {
        this.items = items;
    }

    private void createCacheFile(){
                try {
                    if(onFirstTry) {
                        System.out.println("trying 1");
                        urlStr = "http://cdn.rsbuddy.com/items/"+id[curSlot]+".png";

                        url = new URL(urlStr);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestProperty(
                                "User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
                    }else {
                        System.out.println("trying 2");
                        urlStr = "http://www.runelocus.com/items/img/" + id[curSlot] + ".png";
                        url = new URL(urlStr);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestProperty(
                                "User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
                        onFirstTry = true;
                    }



                    InputStream is = url.openStream();
                    OutputStream os = new FileOutputStream(imgFile.getAbsolutePath());

                    byte[] b = new byte[2048];
                    int length;

                    while ((length = is.read(b)) != -1)

                    {
                        os.write(b, 0, length);
                    }

                    is.close();
                    os.close();
                    System.out.println(connection.getResponseCode());
                } catch (MalformedURLException e) {

                    if(onFirstTry){
                        onFirstTry = false;
                        createCacheFile();

                    }else{
                        e.printStackTrace();
                        System.exit(5);
                    }
                }  catch (IOException e) {
                    e.printStackTrace();
                    System.exit(2);
                }
    }
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        if(items != null) {
            if (optionBox != null) {
                optionBox.repaint();
            }
            g2d.drawImage(backgroundImage, 0, 0, this.getWidth(), getHeight(), null);


            id = new String[28];
            for (Inventory i : items) {
                id[i.getInventory_slot()] = String.valueOf(i.getItem_id());
                amount[i.getInventory_slot()] = i.getAmount();
                stackable[i.getInventory_slot()] = i.isStackable();
                itemName[i.getInventory_slot()] = i.getItem_name();
                actions[i.getInventory_slot()] = i.getActions();


            }

            if (id.length > 0) {
                for (int y = 0; y < 7; y++) {
                    for (int x = 0; x < 4; x++) {
                        gridX = invpanelX / 4 * x + 5;
                        gridY = invpanelY / 6 * y + 5;
                        gridWidth = (int) ((invpanelX / 4) * 0.90);
                        gridHeight = (int) ((invpanelY / 6) * 0.90);
                        curSlot = y * 4 + x;
                        imgFile = new File("cache/icons/" + id[curSlot] + ".png");
                        if (id[curSlot] != null) {

                            if (!imgFile.exists()) {
                                createCacheFile();
                            }


                            try {
                                image = ImageIO.read(imgFile.getAbsoluteFile());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            List<String> actionsList = actions[curSlot];


                            g2d.drawImage(image, gridX, gridY, gridWidth, gridHeight, null);
                            boundingBoxes[curSlot] = new Matrix4f(gridX, gridY, gridX + gridWidth, gridY + gridHeight);


                            if (stackable[curSlot]) {
                                String amountInStack;
                                if(amount[curSlot] < 100000){
                                    amountInStack = Integer.toString(amount[curSlot]);
                                }else{
                                    amountInStack = Integer.toString(amount[curSlot]/ 1000) + "k";
                                }
                                if(amount[curSlot] < 10000000){
                                    g2d.setColor(Color.YELLOW);

                                }else{
                                    g2d.setColor(Color.GREEN);
                                }
                                g2d.setFont(new Font("Consolas", Font.PLAIN, (int) ((invpanelX + invpanelY) * 0.020)));
                                g2d.drawString(amountInStack, gridX + (gridWidth / 7), gridY + (gridHeight / 3));
                            }

                        } else {


                            int fillerX = backgroundImage.getWidth() / 4 * x;
                            int fillerWidth = backgroundImage.getWidth() / 4;
                            int fillerY = backgroundImage.getHeight() / 7 * y;
                            int fillerHeight = backgroundImage.getHeight() / 7;
                            g2d.setColor(invColor);
                            BufferedImage fillerBg = backgroundImage.getSubimage(fillerX, fillerY, fillerWidth, fillerHeight);
                            g2d.drawImage(fillerBg, gridX, gridY, null);
                            boundingBoxes[curSlot] = null;

                        }


                    }
                }


            }
            g2d.dispose();
        }
    }



    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if(currentHoverItem != null){
            if(itemInfo == null) {
               infoPanel = new JPanel();
                infoPanel.setBackground(Color.BLACK);

                infoPanel.validate();
                itemInfo = new  JLabel(itemName[currentHoverItemSlot]);
                itemInfo.setForeground(Color.WHITE);
                itemInfo.setFont(new Font("Arial Black", Font.BOLD, 15));
                infoPanel.add(itemInfo);
                Main.overlay.invItemInfoPanel.add(infoPanel);
            }
            Main.overlay.invItemInfoPanel.revalidate();
            Main.overlay.invItemInfoPanel.repaint();
            if ((x < currentHoverItem.getX() || x > currentHoverItem.getWidth()) || (y < currentHoverItem.getY() || y > currentHoverItem.getHeight())) {
                infoPanel.remove(itemInfo);
                Main.overlay.invItemInfoPanel.remove(infoPanel);
                itemInfo = null;
                currentHoverItem = null;
                currentHoverItemSlot = -1;


            }
        }else {
            for (int i = 0; i < boundingBoxes.length; i++) {
                if (boundingBoxes[i] != null) {


                    if ((x > boundingBoxes[i].getX() && x < boundingBoxes[i].getWidth()) && (y > boundingBoxes[i].getY() && y < boundingBoxes[i].getHeight())) {

                        currentHoverItem = boundingBoxes[i];
                        currentHoverItemSlot = i;


                    }
                }
            }
        }
    }

public static void clearOptionBox(){
    if(optionBox != null){
        thisPanel.remove(optionBox);
        optionBox = null;
        thisPanel.revalidate();
    }
}

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        this.requestFocus();

        if(e.getButton() == 3 && currentHoverItemSlot >-1 && actions[currentHoverItemSlot].size() > 0){

           optionBox = new OptionsBox(e.getX(), e.getY(),itemName[currentHoverItemSlot], actions[currentHoverItemSlot], this,  invpanelX,  invpanelY);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

        if(itemInfo != null) {
            Main.overlay.invItemInfoPanel.remove(infoPanel);
            itemInfo = null;
            currentHoverItem = null;
            currentHoverItemSlot = -1;
        }


    }
}




