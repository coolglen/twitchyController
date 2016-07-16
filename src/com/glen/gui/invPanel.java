package com.glen.gui;

import com.glen.gui.SidePanel;
import com.glen.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class invPanel extends SidePanel {

    public JComboBox combobox;
    public DefaultComboBoxModel model;
    public boolean updating = true;
    public final JButton back = new JButton("Back");

    public invPanel(final String[] items, String panelTitle, final JFrame f) {

        combobox = new JComboBox(items);
        final JButton equip = new JButton("          Equip          ");


        GridLayout gridTop = new GridLayout();
        gridTop.setHgap(4);
        gridTop.setVgap(10);
        gridTop.setRows(3);

        this.centerInnerPanel.setLayout(gridTop);
        this.centerInnerPanel.add(equip);
        this.centerInnerPanel.add(combobox);
        this.topPanel.add(new JLabel(panelTitle));
        this.bottomPanel.add(back);
        f.add(this, BorderLayout.EAST);
        f.validate();


        equip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                equipGear(combobox.getSelectedIndex());
                f.requestFocus();
            }


            private void equipGear(int index) {
                String itemName = items[index].replaceAll(" ","_").toLowerCase();
                Main.IRC.sendIRCMessage("inv " + itemName + " w");
            }
        });
    }

    public void setUpdating(boolean updating) {
        this.updating = updating;
    }
}
