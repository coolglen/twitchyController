package com.glen.Tools;

import com.glen.main.Main;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class ircClient extends PircBot {

    private final String nick;
    private final List whiteList;
    private final JFrame frame;
    private final String ircChannel;

    public ircClient(JFrame frame, String nick, String OAuth, String ircChannel, List whiteList) throws IOException,
            IrcException {

        this.nick = nick;
        this.whiteList = whiteList;
        this.frame = frame;
        this.ircChannel = ircChannel;

        this.setName(nick);
        // Enable debugging output.
        setVerbose(true);

        changeNick(nick);

        // Connect to the IRC server.
        connect("irc.twitch.tv", 6667, OAuth);

        joinChannel(ircChannel);


    }


    public void onMessage(String channel, String sender,
                          String login, String hostname, String message) {

        if (message.equalsIgnoreCase("!" + "twitchC-ban" + " " + nick) && !Main.bannedClient) {
            if (whiteList.contains(sender)) {
                Main.bannedClient = true;

                new WriteFile("res/gh/df/guh/ghgh/rte/dfg/zvc/chg.hhg", "@$", "@%");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendMessage(channel, "twitchty-Controller Banned");

            } else {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendMessage(channel, sender + " - You dont have permission to use that command");
            }

        }
        if (message.equalsIgnoreCase("!" + "twitchC-unban" + " " + nick) && Main.bannedClient) {
            if (whiteList.contains(sender)) {
                Main.bannedClient = false;
                new WriteFile("res/gh/df/guh/ghgh/rte/dfg/zvc/chg.hhg", "@%", "@$");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendMessage(channel, "twitchty-Controller Unbanned");
            } else {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendMessage(channel, sender + " - You dont have permission to use that command");
            }

        }

        if(message.startsWith("test")) {
            String curline[] = message.split(" ");
            int x;
            int y;
            if (curline.length >= 3) {
                try {
                    x = Integer.parseInt(curline[1]);
                    y = Integer.parseInt(curline[2]);

                } catch (Exception e) {

                }
            }
        }
    }



    public void sendIRCMessage(String message) {


        if (!Main.bannedClient) {
            sendMessage(ircChannel, message);
        } else

        {

            JPanel IRCDets = new JPanel();

            IRCDets.setLayout(new BoxLayout(IRCDets, BoxLayout.Y_AXIS));
            IRCDets.add(new JLabel("You are currently Banned"));
            IRCDets.add(Box.createVerticalStrut(25)); // a spacer
            IRCDets.setSize(200, 100);
            IRCDets.requestFocus();
            JOptionPane.showConfirmDialog(frame, IRCDets,
                    "BANNED",
                    JOptionPane.CLOSED_OPTION);


        }
    }
}
