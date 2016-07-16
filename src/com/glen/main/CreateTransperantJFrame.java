package com.glen.main;

import java.awt.*;

import javax.swing.*;

class CreateTransperantJFrame extends JFrame {

    public CreateTransperantJFrame() {

        this.setPreferredSize(new Dimension(1420, 820));
        this.setUndecorated(true);
        this.setResizable(true);
        this.setBackground(new Color(0,0,0,1));
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.requestFocus();
        this.setVisible(true);
    }



}
