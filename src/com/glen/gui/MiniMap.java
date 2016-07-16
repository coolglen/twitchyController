package com.glen.gui;

import com.glen.main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MiniMap extends JPanel{

    private BufferedImage fullMap;

    private int playerX;
    private int playerY;
    private int mapInstance;

    private int xOffset = 0;
    private int yOffset = 0;
    private int mapScaleX;
    private int mapScaleY;
    private double mapZoom = 0;

    public MiniMap(int mapPanelWidth, int mapPanelHeight, JPanel mapPanel){

        File mapFile = new File("res/map.png");
        try {
            fullMap =  ImageIO.read(mapFile.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }


        this.setVisible(true);
        this.setPreferredSize(new Dimension((int) (mapPanelWidth * 0.80), mapPanelHeight));
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());
        mapPanel.add(this, BorderLayout.EAST);
        JSlider mapScaleSlider = new JSlider(JSlider.VERTICAL, 0, 1000, 0);


        mapScaleSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                double tmpSliderValue =  source.getValue();
                mapZoom = tmpSliderValue / 300;
            }
        });
        JSlider yOffsetSlider = new JSlider(JSlider.VERTICAL, -200, 200, 0);


        yOffsetSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();

                yOffset = -source.getValue();
                System.out.println(-source.getValue());
            }
        });
        this.add(mapScaleSlider, BorderLayout.WEST);
        this.add(yOffsetSlider, BorderLayout.EAST);
        this.validate();
    }

    public void setPlayerX(int playerX){
        this.playerX = playerX;

    }
    public void setPlayerY(int playerY){
        this.playerY = playerY;
    }
    public void setMapInstance(int mapInstance){
        this.mapInstance = mapInstance;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        double minCoordX = 2048 - xOffset;
        double minCoordY = 2396 + yOffset;
        double maxCoordX = 3904 - xOffset;
        double maxCoordY = 4252 + yOffset;
        double percentX = fullMap.getWidth() / (maxCoordX - minCoordX);
        double percentY = (  fullMap.getHeight() / (  minCoordY-maxCoordY) );
        double x = (int) (((playerX - minCoordX) * percentX));
        double y = (int) (((playerY - maxCoordY) * percentY));
        int mapWidth = this.getWidth();
        int mapHeight = this.getHeight();
        int circleRad = (int) (((this.getWidth() * this.getHeight()) *0.000080) * mapZoom);

        if(circleRad < 4) circleRad = 4;
        if(((x > 0 && x < fullMap.getWidth()) && (y > 0 && y < fullMap.getHeight())) && mapInstance == 0) {

            BufferedImage map = fullMap.getSubimage((int) x - (mapWidth/2), (int) y - (mapHeight/2), mapWidth, mapHeight);
            mapScaleX = (int) (mapWidth * mapZoom);
            mapScaleY = (int) (mapHeight * mapZoom);
           g2d.drawImage(map, -mapScaleX/2, -mapScaleY/2, mapWidth + mapScaleX, mapHeight +mapScaleY, null);

        }else {
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, ((this.getWidth() + this.getHeight()) / 30)));
            FontMetrics fm = g2d.getFontMetrics();
            Rectangle2D rect = fm.getStringBounds("Cannot load map here", g2d);
            g2d.setColor(Color.WHITE);
            g2d.drawString("Cannot load map here", (int) (((this.getWidth() / 2) - (rect.getWidth() / 2))), (int) ((this.getHeight() / 2) - (rect.getHeight() / 2)));

        }

        g2d.setColor(Color.WHITE);


        g2d.fillOval((this.getWidth()/2)-circleRad/2, (this.getHeight()/2)-circleRad/2, circleRad, circleRad);




        g2d.dispose();

    }


}
