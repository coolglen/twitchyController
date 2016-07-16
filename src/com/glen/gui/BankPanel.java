package com.glen.gui;

import com.glen.gui.SidePanel;
import com.glen.main.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class BankPanel extends SidePanel {

    private int amount = 0;
    private String amountString;
    public final JButton back = new JButton("Back");

    public BankPanel(final String[] item, String panelTitle, final JFrame f) {

        final JComboBox combobox = new JComboBox(item);
        AutoCompletion.enable(combobox);



        JButton withdraw = new JButton("Withdraw");
        JButton deposit = new JButton("deposit");
        JButton plus = new JButton("+");
        JButton minus = new JButton("-");
        final JTextField amountField = new JTextField();
        JPanel textFieldPanel = new JPanel(new GridLayout());
        JPanel amountTextPanel = new JPanel();
        amountTextPanel.setLayout(new BoxLayout(amountTextPanel, BoxLayout.Y_AXIS));


        GridLayout gridTop = new GridLayout();

        gridTop.setVgap(10);
        gridTop.setRows(5);
        gridTop.setHgap(4);
        textFieldPanel.setBackground(Color.GRAY);
        amountTextPanel.setBackground(Color.GRAY);
        amountField.setText(Integer.toString(amount));
        this.centerInnerPanel.setLayout(gridTop);
        this.centerInnerPanel.add(withdraw);
        this.centerInnerPanel.add(deposit);
        this.centerInnerPanel.add(combobox);
        this.centerInnerPanel.add(combobox);
        this.topPanel.add(new JLabel(panelTitle));
        gridTop.setHgap(1);
        this.centerInnerPanel.add(textFieldPanel);
        this.centerInnerPanel.add(amountTextPanel);
        amountTextPanel.add(new JLabel("                Amount"));
        textFieldPanel.add(minus);
        textFieldPanel.add(amountField);
        textFieldPanel.add(plus);


        this.bottomPanel.add(back);
        f.add(this, BorderLayout.EAST);

        withdraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                withdraw(combobox.getSelectedIndex());
                amount = Integer.parseInt(amountField.getText());
                f.requestFocus();
            }


            private void withdraw(int index) {
                amount = Integer.parseInt(amountField.getText());
                if (amount == 0) {
                    amountString = "1";
                } else {
                    amountString = Integer.toString(amount);
                }

                String itemName = item[index].replaceAll(" ","_").toLowerCase();
                Main.IRC.sendIRCMessage("bank withdraw " + itemName + " " + amountString);
            }
        });
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                amount += 1;
                amountField.setText(Integer.toString(amount));
                f.requestFocus();

            }
        });
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (amount > 0) {
                    amount -= 1;
                }

                amountField.setText(Integer.toString(amount));
                f.requestFocus();

            }
        });
        deposit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deposit(combobox.getSelectedIndex());
                f.requestFocus();

            }


            private void deposit(int index) {
                amount = Integer.parseInt(amountField.getText());
                if (amount == 0) {
                    amountString = "";
                } else {
                    amountString = Integer.toString(amount);
                }

                String itemName = item[index].replaceAll(" ", "_").toLowerCase();
                Main.IRC.sendIRCMessage("bank deposit " + itemName + " " + amountString);
            }
        });

    }

}
