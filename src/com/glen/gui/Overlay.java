package com.glen.gui;

import com.glen.main.Main;

import javax.swing.*;
import java.awt.*;

public class Overlay extends JPanel{

    public JPanel mainPanel;
    public JPanel mapPanel;
    public JPanel hpPanel;
    public JPanel hpTextPanel;
    public JPanel prayerTextPanel;
    public JPanel invPanel;
    public JPanel bottomTabPanel;
    public JPanel invItemInfoPanel;

    public JLabel hp = new JLabel("??");
    public JLabel prayer = new JLabel("??");
    private final int mainPanelHeight = (int) ((Main.frameOriginalHeight - 75) * 0.67f);
    private final int mainPanelWidth =  (int) (Main.frameOriginalWidth * 0.32f);
    private final int mapPanelHeight =  (int) ((Main.frameOriginalHeight - 75) * 0.33f);
    public Overlay(Frame f){

        this.setOpaque(false);
        this.setPreferredSize(new Dimension(Main.frameOriginalWidth, Main.frameOriginalHeight));
        this.setLayout(new BorderLayout());

         JPanel uiPanel = new JPanel(new BorderLayout());

        uiPanel.setPreferredSize(new Dimension(mainPanelWidth, Main.frameOriginalHeight));

        uiPanel.setOpaque(false);
        this.add(uiPanel, BorderLayout.EAST);

        mapPanel = new JPanel();
        mapPanel.setLayout(new BorderLayout());
        mapPanel.setPreferredSize(new Dimension(uiPanel.getWidth(),mapPanelHeight));

        mapPanel.setOpaque(false);
        uiPanel.add(mapPanel, BorderLayout.NORTH);

        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(uiPanel.getWidth(), mainPanelHeight));


        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);

        uiPanel.add(mainPanel, BorderLayout.SOUTH);



        JPanel topTabPanel = new JPanel();
        topTabPanel.setPreferredSize(new Dimension(uiPanel.getWidth(), (int) (mainPanelHeight * 0.10)));
        topTabPanel.setOpaque(false);
        topTabPanel.setLayout(new BoxLayout(topTabPanel, BoxLayout.X_AXIS));
        mainPanel.add(topTabPanel, BorderLayout.NORTH);

        bottomTabPanel = new JPanel();
        bottomTabPanel.setPreferredSize(new Dimension(uiPanel.getWidth(), (int) (mainPanelHeight * 0.10)));
        bottomTabPanel.setOpaque(false);
        bottomTabPanel.setLayout(new BoxLayout(bottomTabPanel, BoxLayout.Y_AXIS));
        mainPanel.add(bottomTabPanel, BorderLayout.SOUTH);
        invItemInfoPanel = new JPanel();
        invItemInfoPanel.setPreferredSize(new Dimension(10,10));
        invItemInfoPanel.setOpaque(false);
        bottomTabPanel.add(invItemInfoPanel);


        JPanel mainInnerpanel = new JPanel();
        mainInnerpanel.setOpaque(false);
        mainPanel.add(mainInnerpanel, BorderLayout.CENTER);


        invPanel = new JPanel();
        invPanel.setPreferredSize(new Dimension((int) (mainPanelWidth * 0.78), (mainPanelHeight)));
        invPanel.setOpaque(false);
        invPanel.setLayout(new BorderLayout());
        mainInnerpanel.add(invPanel);


        JButton topPanelButtons[] = new JButton[7]; //topTabPanel buttons (7) size = mainPanelWidth /7
     /*   for(int i = 0; i < 7; i++){
            topPanelButtons[i] = new JButton();
            topPanelButtons[i].setMaximumSize(new Dimension(((mainPanelWidth)/7), (int) (mainPanelHeight * 0.10)));
            topPanelButtons[i].setMinimumSize(new Dimension(((mainPanelWidth)/7), (int) (mainPanelHeight * 0.10)));
            topPanelButtons[i].setPreferredSize(new Dimension(((mainPanelWidth)/7), (int) (mainPanelHeight * 0.10)));
            topPanelButtons[i].setOpaque(false);
            com.glen.Tools.OverlayButtonListener bl = new com.glen.Tools.OverlayButtonListener(i);
            topPanelButtons[i].addActionListener(bl);
            topTabPanel.add(topPanelButtons[i]);
            topTabPanel.add(Box.createRigidArea(new Dimension(1, 0)));
        }*/




        //hp
        hpPanel = new JPanel();
        JPanel mapInfoPanel = new JPanel();
        JPanel hpPanel1 = new JPanel();
        JPanel prayerPanel1 = new JPanel();
        JPanel prayerPanel = new JPanel();
        mapInfoPanel.setLayout(new BorderLayout());
        hpPanel1.setLayout(new BorderLayout());
        prayerPanel1.setLayout(new BorderLayout());
        hpPanel.setLayout(new BorderLayout());
        prayerPanel.setLayout(new BorderLayout());
        hpPanel.setOpaque(false);
        prayerPanel.setOpaque(false);
        mapInfoPanel.setOpaque(false);
        hpPanel1.setOpaque(false);
        prayerPanel1.setOpaque(false);

        hpPanel1.setPreferredSize(new Dimension((int) (mainPanelWidth * 0.20), (int) (mainPanelHeight * 0.19)));
        prayerPanel1.setPreferredSize(new Dimension((int) (mainPanelWidth * 0.20), (int) (mainPanelHeight * 0.22)));

        hpPanel1.add(hpPanel, BorderLayout.SOUTH);
        prayerPanel1.add(prayerPanel, BorderLayout.NORTH);

        hpTextPanel = new JPanel();
        prayerTextPanel = new JPanel();


        hp.setFont(hp.getFont().deriveFont(f.getWidth() * 0.015f));
        hpTextPanel.setLayout(new BorderLayout());
        hpTextPanel.add(hp, BorderLayout.CENTER);
        hpTextPanel.setBackground(Color.RED);

        prayer.setFont(prayer.getFont().deriveFont(f.getWidth() * 0.015f));
        prayerTextPanel.setLayout(new BorderLayout());
        prayerTextPanel.add(prayer, BorderLayout.CENTER);
        prayerTextPanel.setBackground(Color.CYAN);
        hpPanel.add(hpTextPanel, BorderLayout.EAST);
        prayerPanel.add(prayerTextPanel, BorderLayout.EAST);


        mapInfoPanel.add(hpPanel1, BorderLayout.NORTH);
        mapInfoPanel.add(prayerPanel1, BorderLayout.SOUTH);
        mapPanel.add(mapInfoPanel, BorderLayout.WEST);

        mapInfoPanel.validate();

    }
}
