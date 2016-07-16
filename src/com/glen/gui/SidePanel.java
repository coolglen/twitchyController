package com.glen.gui;

import com.glen.main.Main;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class SidePanel extends JPanel {

    public final JPanel bottomPanel;
    public final JPanel topPanel;
    public final JPanel centerPanel;
    public final JPanel centerInnerPanel;
    public final JPanel leftPanel;
    public final JPanel rightPanel;
    public final GridBagLayout gridBagLayout;

    public SidePanel() {

        gridBagLayout = new GridBagLayout();

        bottomPanel = new JPanel();
        centerPanel = new JPanel(gridBagLayout);
        centerInnerPanel = new JPanel();
        topPanel = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        Color color = new Color(0, 40, 0);
        this.setPreferredSize(new Dimension(200, Main.getFrameHeight() / 3));
        this.setBorder(BorderFactory.createLineBorder(color, 1));
        this.setBackground(Color.GRAY);
        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(rightPanel, BorderLayout.EAST);
        this.add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(centerInnerPanel);
        this.add(leftPanel, BorderLayout.WEST);
        bottomPanel.setBackground(Color.GRAY);
        topPanel.setBackground(Color.GRAY);
        leftPanel.setBackground(Color.GRAY);
        rightPanel.setBackground(Color.GRAY);
        centerPanel.setBackground(Color.GRAY);
        centerInnerPanel.setBackground(Color.GRAY);


    }


}
