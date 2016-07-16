package com.glen.Tools;

import com.glen.main.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class OverlayButtonListener implements ActionListener {
    private int message;
    public OverlayButtonListener(int message){
        this.message = message;


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (message){
            case 0: Main.IRC.sendIRCMessage("tab combat");
                break;
            case 1: Main.IRC.sendIRCMessage("tab stats");
                break;
            case 2: Main.IRC.sendIRCMessage("tab quests");
                break;
            case 3: Main.IRC.sendIRCMessage("tab inventory");
                break;
            case 4: Main.IRC.sendIRCMessage("tab equipt");
                break;
            case 5: Main.IRC.sendIRCMessage("tab prayer");
                break;
            case 6: Main.IRC.sendIRCMessage("tab spell");
                break;

        }
    }
}
