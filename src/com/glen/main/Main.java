package com.glen.main;

import com.glen.Tools.LoadFile;
import com.glen.Tools.WriteFile;
import com.glen.Tools.ircClient;
import com.glen.api.*;
import com.glen.api.Objects;
import com.glen.gui.*;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.swing.*;


public class Main implements MouseListener, KeyListener {

    private final String version = "v1.5";

    private Point lastCurs;

    private static int cursX = 0;
    private static int cursY = 0;
    private static int frameWidth;
    private static int frameHeight;

    private int calibBoxX = 0;
    private int calibBoxY = 0;

    public static String ircChannel;
    private static String ircUserName;
    private static String ircOAuth;

    public static ircClient IRC;

    private static String[] bankItems;

    private static String[] invItems;

    private LoadApi api;

    private boolean calibrate = true;

    private boolean isSidePanel = false;
    private boolean restartUpdates = false;

    public static int frameOriginalWidth;
    public static int frameOriginalHeight;

    private static String myString;

    private String getInventState = "on";
    private String getHpState = "off";
    private String getPrayerState = "off";
    private String getMapState = "off";

    public static JFrame f;
    private JButton toggleSidePanel;
    private JLabel coords;
    private JLabel hp;
    private JLabel prayerInv;
    private SidePanel sidePanelOne;
    private invPanel invSidePanel;
    private BankPanel bnkSidePanel;
    private SidePanel teleportPanel;
    private SidePanel optionPanel;
    public static Overlay overlay;
    private MiniMap minimap;

    private JPanel infoPanel;
    public static boolean bannedClient;

    private final List<String> whiteList = new ArrayList<String>();

    private static final int MAIN_PANEL = 0;
    private static final int INV_PANEL = 1;
    private static final int BANK_PANEL = 2;
    private static final int TELE_PANEL = 10;
    private static final int OPTIONS_PANEL = 7;


    void initiateWhiteList() {
        whiteList.add("coolglen201");
        whiteList.add("sbot_");
        whiteList.add("stanley_mole");
        whiteList.add("ayevera");
        whiteList.add("quint666");
        whiteList.add("123kappa3");
        whiteList.add("stunts1337");

    }


    void controlPanel() {

        JPanel jp = new JPanel();

        JPanel rightPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel leftPanel = new JPanel();

        coords = new JLabel();
        JButton BMore = new JButton("Help");
        final JButton close = new JButton("Close");
        JButton inventory = new JButton("Open Inventory");
        toggleSidePanel = new JButton(">>");
        final JPanel betterPanel = new JPanel();
        final JPanel labelPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(betterPanel, BoxLayout.X_AXIS);


        betterPanel.setLayout(boxLayout);
        f.add(betterPanel);
        JLabel l1 = new JLabel("Controls:");
        JLabel l2 = new JLabel("Enter: send Command ");
        JLabel l3 = new JLabel("Left-Click: set Left-Click coordinate ");
        JLabel l4 = new JLabel("Right-Click: set Right-Click coordinate ");
        JLabel l5 = new JLabel("Middle-Click/Right-CTRL: set hover coordinate ");
        SpringLayout springLayout = new SpringLayout();
        springLayout.putConstraint(SpringLayout.NORTH, close,
                40,
                SpringLayout.NORTH, l5);
        springLayout.putConstraint(SpringLayout.NORTH, l5,
                20,
                SpringLayout.NORTH, l4);
        springLayout.putConstraint(SpringLayout.NORTH, l4,
                20,
                SpringLayout.NORTH, l3);
        springLayout.putConstraint(SpringLayout.NORTH, l3,
                20,
                SpringLayout.NORTH, l2);
        springLayout.putConstraint(SpringLayout.NORTH, l2,
                20,
                SpringLayout.NORTH, l1);


        labelPanel.add(close);
        labelPanel.setLayout(springLayout);
        labelPanel.add(l1);
        labelPanel.add(l2);
        labelPanel.add(l3);
        labelPanel.add(l4);
        labelPanel.add(l5);
        labelPanel.setMaximumSize(new Dimension(300, 300));
        labelPanel.setLocation(100, 100);
        labelPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        betterPanel.setOpaque(false);
        Color col = UIManager.getColor("activeCaptionBorder");
        betterPanel.getRootPane().setBorder(BorderFactory.createLineBorder(col, 5));

        jp.setPreferredSize(new Dimension(f.getWidth(), 40));

        Color color = new Color(0, 40, 0);
        jp.setBorder(BorderFactory.createLineBorder(color, 1));
        jp.setBackground(Color.GRAY);
        jp.setLayout(new BorderLayout());
        jp.add(centerPanel, BorderLayout.CENTER);
        jp.add(rightPanel, BorderLayout.EAST);
        jp.add(leftPanel, BorderLayout.WEST);

        leftPanel.setBackground(Color.GRAY);
        centerPanel.setBackground(Color.GRAY);
        rightPanel.setBackground(Color.GRAY);
        jp.add(BMore, BorderLayout.WEST);
        leftPanel.setPreferredSize(new Dimension(400, 52));
       centerPanel.setLayout(new BorderLayout());
        JPanel coordPanel = new JPanel();



        coordPanel.add(coords);
        coordPanel.setBackground(Color.GRAY);
        coordPanel.setPreferredSize(new Dimension(200, 50));
        coordPanel.add(coords);
        centerPanel.add(coordPanel, BorderLayout.WEST);

        JPanel hpPanel = new JPanel();
        JPanel hptextPanel = new JPanel();
        hpPanel.setPreferredSize(new Dimension(120, 30));
        hpPanel.setMinimumSize(new Dimension(120, 30));

        hpPanel.setBackground(Color.GRAY);
        hpPanel.setLayout(new SpringLayout());

        JPanel fill = new JPanel();
        fill.setBackground(Color.GRAY);
        fill.setLayout(new FlowLayout());
        hpPanel.add(hptextPanel);

       // fill.add(hpPanel);
        centerPanel.add(fill);
        rightPanel.add(toggleSidePanel);

        centerPanel.add(fill, BorderLayout.CENTER);
        f.add(jp, BorderLayout.SOUTH);
        f.revalidate();


        BMore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                betterPanel.add(labelPanel);
                betterPanel.setVisible(true);
                betterPanel.revalidate();
            }
        });
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                betterPanel.setVisible(false);
                betterPanel.revalidate();


            }
        });


        toggleSidePanel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (toggleSidePanel.getText().equals(">>")) {
                    if (sidePanelOne == null) {
                        sidePanel();
                    }
                    sidePanelOne.setVisible(true);
                    isSidePanel = true;
                    f.setSize((int) ((calibBoxX) * 1.095) + 200,
                            (int) ((calibBoxY) * 1.24) + 40);

                    toggleSidePanel.setText("<<");
                    if (bnkSidePanel != null) bnkSidePanel.setVisible(true);
                    if (invSidePanel != null) invSidePanel.setVisible(true);
                    if (teleportPanel != null) teleportPanel.setVisible(true);
                    if(optionPanel != null) optionPanel.setVisible(true);

                } else {
                    f.setSize((int) ((calibBoxX) * 1.095),
                            (int) ((calibBoxY) * 1.24) + 40);
                    toggleSidePanel.setText(">>");
                    isSidePanel = false;
                    sidePanelOne.setVisible(false);
                    if (bnkSidePanel != null) bnkSidePanel.setVisible(false);
                    if (invSidePanel != null) invSidePanel.setVisible(false);
                    if (teleportPanel != null) teleportPanel.setVisible(false);
                    if(optionPanel != null) optionPanel.setVisible(false);

                }

            }

        });

    }


    private int t = 5;
    void sidePanel() {

        GridLayout gridTop = new GridLayout();
        sidePanelOne = new SidePanel();
       // JButton bankB = new JButton("Bank");
        //JButton invB = new JButton("Inventory");
        JButton teleport = new JButton("Teleports");
        JButton options = new JButton("Options");

        gridTop.setHgap(4);
        gridTop.setVgap(10);
        gridTop.setRows(2);

        f.setSize((int) ((calibBoxX) * 1.095) + 200,
                (int) ((calibBoxY) * 1.24) + 40);


        sidePanelOne.centerInnerPanel.setLayout(gridTop);
//        sidePanelOne.centerInnerPanel.add(bankB);
//        sidePanelOne.centerInnerPanel.add(invB);
        sidePanelOne.centerInnerPanel.add(teleport);
        sidePanelOne.bottomPanel.add(new JLabel("INCOMPLETE"));

        sidePanelOne.topPanel.add(options, BorderLayout.NORTH);

        toggleSidePanel.setText("<<");

        f.add(sidePanelOne, BorderLayout.EAST);
        f.revalidate();
       /* bankB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.remove(sidePanelOne);
                bankSidePanel();
            }
        });
        invB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.remove(sidePanelOne);
                invSidePanel();
            }
        });*/
        teleport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.remove(sidePanelOne);
                t = 6;
                teleportSidePanel();
            }
        });

        options.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.remove(sidePanelOne);
                optionsPanel();
            }
        });



    }

    public void optionsPanel(){
            optionPanel = new SidePanel();

        JButton back = new JButton("Back");
        optionPanel.centerInnerPanel.setLayout(new BoxLayout(optionPanel.centerInnerPanel, BoxLayout.Y_AXIS));
        optionPanel.centerInnerPanel.setPreferredSize(new Dimension(frameOriginalHeight, 100));
        //overlay
        JPanel overlayOptionPanel = new JPanel();
        overlayOptionPanel.setLayout(new BoxLayout(overlayOptionPanel, BoxLayout.LINE_AXIS));
        final JButton overlayButton = new JButton(getInventState);
        JLabel overlayLabel = new JLabel("Inventory:    ");
        overlayOptionPanel.setBackground(Color.GRAY);
        overlayOptionPanel.add(overlayLabel);
        overlayOptionPanel.add(overlayButton);
        overlayOptionPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        optionPanel.centerInnerPanel.add(overlayOptionPanel);
        optionPanel.centerInnerPanel.add(Box.createRigidArea(new Dimension(0, 45)));

        //minimap
        JPanel miniOptionPanel = new JPanel();
        miniOptionPanel.setLayout(new BoxLayout(miniOptionPanel, BoxLayout.LINE_AXIS));
        final JButton minimapButton = new JButton(getMapState);
        JLabel minimapLabel = new JLabel("Minimap:    ");
        miniOptionPanel.setBackground(Color.GRAY);
        miniOptionPanel.add(minimapLabel);
        miniOptionPanel.add(minimapButton);
        miniOptionPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        optionPanel.centerInnerPanel.add(miniOptionPanel);
        optionPanel.centerInnerPanel.add(Box.createRigidArea(new Dimension(0, 45)));



        //hp
        JPanel hpOptionPanel = new JPanel();
        hpOptionPanel.setLayout(new BoxLayout(hpOptionPanel, BoxLayout.LINE_AXIS));
        final JButton hpButton = new JButton(getHpState);
        JLabel hpLabel = new JLabel("HealthPoints:    ");
        hpOptionPanel.setBackground(Color.GRAY);
        hpOptionPanel.add(hpLabel);
        hpOptionPanel.add(hpButton);
        hpOptionPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        optionPanel.centerInnerPanel.add(hpOptionPanel);
        optionPanel.centerInnerPanel.add(Box.createRigidArea(new Dimension(0, 45)));

        //prayer
        JPanel prayerOptionPanel = new JPanel();
        prayerOptionPanel.setLayout(new BoxLayout(prayerOptionPanel, BoxLayout.LINE_AXIS));
        final JButton prayerButton = new JButton(getPrayerState);
        JLabel prayerLabel = new JLabel("Prayer:    ");
        prayerOptionPanel.setBackground(Color.GRAY);
        prayerOptionPanel.add(prayerLabel);
        prayerOptionPanel.add(prayerButton);
        prayerOptionPanel.setAlignmentX( Component.RIGHT_ALIGNMENT);
        optionPanel.centerInnerPanel.add(prayerOptionPanel);



        optionPanel.bottomPanel.add(back);
        f.add(optionPanel, BorderLayout.EAST);
        f.revalidate();
        optionPanel.bottomPanel.add(back);
        f.add(optionPanel, BorderLayout.EAST);
        f.revalidate();

        overlayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(overlayButton.getText() == "on"){
                   overlayButton.setText("off");
                    getInventState = "off";

                        overlay.mainPanel.setVisible(true);

                }else{
                   overlayButton.setText("on");
                    getInventState = "on";
                    if(overlay != null ){
                        overlay.mainPanel.setVisible(false);
                    }
                }

            }
        });
        hpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(hpButton.getText() == "on"){
                    hpButton.setText("off");
                    getHpState = "off";
                    overlay.hpTextPanel.setVisible(true);
                }else{
                    hpButton.setText("on");
                    getHpState = "on";
                    overlay.hpTextPanel.setVisible(false);
                }
            }
        });
        prayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(prayerButton.getText() == "on"){
                    prayerButton.setText("off");
                    getPrayerState = "off";
                    overlay.prayerTextPanel.setVisible(true);
                }else{
                    prayerButton.setText("on");
                    getPrayerState = "on";
                    overlay.prayerTextPanel.setVisible(false);
                }
            }
        });
        minimapButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(minimapButton.getText() == "on"){
                   minimapButton.setText("off");
                    getMapState = "off";
                    minimap.setVisible(true);
                }else{
                    minimapButton.setText("on");
                    getMapState = "on";
                    minimap.setVisible(false);
                }
            }
        });
       back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeSidePanel(OPTIONS_PANEL);
            }
        });



    }
    void invSidePanel() {

        invSidePanel = new invPanel(invItems , "Inventory", f);
        f.revalidate();
        invSidePanel.back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                invSidePanel.setUpdating(false);
                closeSidePanel(INV_PANEL);
            }
        });

    }


    void bankSidePanel() {

       // bnkSidePanel = new BankPanel(bankItems, "Bank", f);
        f.revalidate();
        bnkSidePanel.back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeSidePanel(BANK_PANEL);
            }
        });

    }


    void teleportSidePanel(){

        teleportPanel = new SidePanel();
        JPanel teleButtonPanel = new JPanel();
        GridLayout gridLayout = new GridLayout();
        gridLayout.setHgap(30);
        gridLayout.setVgap(20);
        gridLayout.setColumns(2);
        gridLayout.setRows(5);
        teleButtonPanel.setLayout(gridLayout);
        teleButtonPanel.setBackground(Color.GRAY);
        JButton back = new JButton("Back");

        JButton mageTab = new JButton("mage tab");

        JButton home = new JButton("H");
        JButton varrock = new JButton("V");
        JButton lum = new JButton("L");
        JButton fally = new JButton("F");
        JButton house =  new JButton(new ImageIcon(new File("res/houseIcon.png").getAbsolutePath()));
        JButton cam = new JButton("C");
        JButton ardy = new JButton("A");
        JButton watchtower = new JButton("W");
        JButton trollhiem = new JButton("T");
        File bananaFile = new File("res/bananaIcon.png");
        Icon banana = new ImageIcon(bananaFile.getAbsolutePath());
        JButton ape = new JButton(banana);



        teleButtonPanel.add(home);
        teleButtonPanel.add(varrock);
        teleButtonPanel.add(fally);
        teleButtonPanel.add(lum);
        teleButtonPanel.add(fally);
        teleButtonPanel.add(house);
        teleButtonPanel.add(cam);
        teleButtonPanel.add(ardy);
        teleButtonPanel.add(watchtower);
        teleButtonPanel.add(trollhiem);
        teleButtonPanel.add(ape);

        GridLayout gl = new GridLayout();
        gl.setRows(2);
        gl.setVgap(20);
        teleportPanel.centerInnerPanel.setLayout(gl);
        JPanel magetabPanel = new JPanel(new BorderLayout());
        magetabPanel.add(mageTab, BorderLayout.SOUTH);
        teleportPanel.centerInnerPanel.add(magetabPanel);
        magetabPanel.setBackground(Color.GRAY);
        teleportPanel.centerInnerPanel.add(teleButtonPanel);
        teleportPanel.bottomPanel.add(back);
        f.add(teleportPanel, BorderLayout.EAST);
        f.revalidate();

        mageTab.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IRC.sendIRCMessage("tab magic"); //mage tab

            }
        });
        home.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IRC.sendIRCMessage("click 575 225"); //home tele

            }
        });
        varrock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IRC.sendIRCMessage("click 600 275"); // varrock tele

            }
        });
        lum.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IRC.sendIRCMessage("click 666 280");// lumbridge tele

            }
        });
        fally.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IRC.sendIRCMessage("click 570 305"); // fally tele

            }
        });
        house.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IRC.sendIRCMessage("click 615 305");// house tele

            }
        });
        cam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IRC.sendIRCMessage("click 695 300");// camelot tele

            }
        });
        ardy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IRC.sendIRCMessage("click 666 330");// ardy tele
            }
        });
        watchtower.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IRC.sendIRCMessage("click 615 350");// watchtower tele

            }
        });
        trollhiem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IRC.sendIRCMessage("click 615 375");// trollheim tele

            }
        });
        ape.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IRC.sendIRCMessage("click 690 375");//  apeAtoll tele

            }
        });
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeSidePanel(TELE_PANEL);

            }
        });
    }


    void closeSidePanel(int p) {

        if (p == MAIN_PANEL) {
            toggleSidePanel.setText(">>");
            f.remove(sidePanelOne);
            if (bnkSidePanel != null) f.remove(bnkSidePanel);
            if (invSidePanel != null) f.remove(invSidePanel);

            f.setSize((int) ((calibBoxX) * 1.095),
                    (int) ((calibBoxY) * 1.24) + 40);

        }
        if (p == BANK_PANEL) {
            f.remove(bnkSidePanel);
            sidePanel();
        }

        if (p == INV_PANEL) {
            f.remove(invSidePanel);
            sidePanel();
        }

        if (p == TELE_PANEL) {
            f.remove(teleportPanel);
            sidePanel();
        }
        if(p == OPTIONS_PANEL){
            f.remove(optionPanel);

            sidePanel();
        }

    }


    void drawString(int stringNumber) {

        String click = "click";
        if (stringNumber == 1) {
            click = "LClick";
        }
        if (stringNumber == 2) {
            click = "Hover";
        }
        if (stringNumber == 3) {
            click = "RClick";
        }

        if (cursX < 765 && cursY < 500 && cursY > 0) {
            String stringX = Integer.toString(cursX);
            String stringY = Integer.toString(cursY);

            String str = click;
            String str2 = stringX + ", " + stringY;
            if (coords != null) {
                coords.setText(str + ": " + str2);
            }
        }
    }


    private void init() {
        f = new CreateTransperantJFrame();
        Color color = UIManager.getColor("activeCaptionBorder");
        f.getRootPane().setBorder(BorderFactory.createLineBorder(color, 1));
        f.addKeyListener(this);
        f.addMouseListener(this);
        f.setTitle("Twitchy Controller " + version);
        f.pack();
        f.validate();



        frameWidth = f.getWidth();
        frameHeight = -66 + f.getHeight();

        initiateWhiteList();

        helpInfo();

    }


    public void helpInfo(){
        infoPanel = new JPanel();
        JPanel innerPanel = new JPanel();
        JLabel info1 = new JLabel("Aline the top left of the window with the topleft of the stream.");
        JLabel info2 = new JLabel("then right click on the 700, 400 coordiante of the stream.");
        info2.setFont(info2.getFont ().deriveFont (f.getWidth()*0.01f));
        info1.setFont(info1.getFont ().deriveFont (f.getWidth()*0.01f));
        GridLayout gl = new GridLayout();
        gl.setVgap(10);
        gl.setRows(2);
        gl.setColumns(1);
        innerPanel.setLayout(gl);
        infoPanel.setOpaque(false);
        innerPanel.setOpaque(false);
        innerPanel.add(info1, BorderLayout.CENTER);
        innerPanel.add(info2, BorderLayout.CENTER);
        infoPanel.add(innerPanel, BorderLayout.CENTER);
        f.add(infoPanel, BorderLayout.CENTER);
        f.revalidate();

    }
    void login() {

        initIRCdets();

        f.setAlwaysOnTop(false);

        JTextField userField = new JTextField(1);
        JTextField passField = new JTextField(1);
        JCheckBox remember = new JCheckBox("Remember Details");
        userField.setText(ircUserName);
        passField.setText(ircOAuth);
        JPanel IRCDets = new JPanel();

        IRCDets.setLayout(new BoxLayout(IRCDets, BoxLayout.Y_AXIS));
        IRCDets.add(new JLabel("Twitch UserName (lowercase)"));
        IRCDets.add(userField);
        IRCDets.add(Box.createVerticalStrut(25)); // a spacer
        IRCDets.setSize(200, 100);
        IRCDets.add(new JLabel(
                "Twitch OAuth (get it here: http://www.twitchapps.com/tmi/)"));
        IRCDets.add(passField);
        IRCDets.requestFocus();
        IRCDets.add(remember);

        int result = JOptionPane.showConfirmDialog(f, IRCDets,
                "Please Enter Twitch username and OAuth",
                JOptionPane.OK_OPTION);

        if (result == JOptionPane.OK_OPTION) {

            String nick = userField.getText();
            String OAuth = passField.getText();
            if (remember.isSelected()) {
                saveLoginDetails(nick, OAuth);
            }

            try {
                IRC = new ircClient(f, nick, OAuth, ircChannel, whiteList);
            } catch (NickAlreadyInUseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IrcException e) {
                // TODO Auto-generated catch block
                e.getStackTrace();

            }


        }
        controlPanel();

        overlay = new Overlay(f);
        f.setAlwaysOnTop(true);
        f.add(overlay);
        overlay.mainPanel.setVisible(false);
        f.revalidate();
        update();
    }

    public void update(){


        Thread one = new Thread() {

            public void run() {
                double hpPercent = 0;
                double prayPercent = 0;
                VirtualInventory v = new VirtualInventory(overlay.invPanel.getWidth(), (int) (overlay.invPanel.getHeight() * 0.80));
                minimap = new MiniMap(overlay.mapPanel.getWidth(), overlay.mapPanel.getHeight(), overlay.mapPanel);
                overlay.invPanel.add(v);

                api = new LoadApi();

                String hpIs = "0";
                String hpLevel = "0";
                String prayIs = "0";
                String prayLevel = "0";
               while(true){

                   long startTime = System.currentTimeMillis();

                       api.updateApi();

                   if(getHpState == "off") {
                       hpIs = String.valueOf(api.getSkills().get(3).getCurrent_level());
                       hpLevel = String.valueOf(api.getSkills().get(3).getReal_level());
                   }
                   if(getPrayerState == "off") {
                       prayIs = String.valueOf(api.getSkills().get(5).getCurrent_level());
                       prayLevel = String.valueOf(api.getSkills().get(5).getReal_level());
                   }


                   if(overlay.hp != null){
                       overlay.hp.setText(hpIs);
                   }
                   if(overlay.prayer != null) {
                       overlay.prayer.setText(prayIs);
                   }
                   if(Integer.parseInt(hpIs) != 0) {
                       hpPercent = Double.parseDouble(hpIs) / Double.parseDouble(hpLevel);
                   }

                   int hpRedCol = (int)(255- (255*hpPercent));
                   int hpGreenCol = (int) (255*hpPercent);
                   if(hpRedCol < 0){
                       hpRedCol = 0;
                   }else if(hpRedCol > 255){
                       hpRedCol = 255;
                   }
                   if(hpGreenCol < 0){
                       hpGreenCol = 0;
                   }else if(hpGreenCol > 255){
                       hpGreenCol = 255;
                   }
                   int prayRedCol = (int) (255- (255*prayPercent));
                   if(prayRedCol < 0) {
                       prayRedCol = 0;
                   }else if (prayRedCol > 255){
                       prayRedCol = 255;
                   }
                    Color hpColor = new Color(hpRedCol, hpGreenCol, 0);

                   overlay.hpTextPanel.setBackground(hpColor);


                   if(Integer.parseInt(prayIs) != 0){

                       prayPercent  = (Double.parseDouble(prayIs)) / Double.parseDouble(prayLevel);
                   }


                   Color prayColor = new Color(prayRedCol, 255, 255);
                   overlay.prayerTextPanel.setBackground(prayColor);

                   if(getMapState == "off") {
                       minimap.setPlayerX((Integer) api.getInfo().getLocation().get(0));
                       minimap.setPlayerY((Integer) api.getInfo().getLocation().get(1));
                       minimap.setMapInstance((Integer) api.getInfo().getLocation().get(2));
                       minimap.repaint();
                   }

                   if(getInventState == "off") {
                       v.setItems(api.getInventory());
                       v.repaint();
                   }


                   long finishTime = (System.currentTimeMillis() - startTime);
                      System.out.println(finishTime);
               }
            }
        };

        one.start();

    }

    private static void initIRCdets() {

        LoadFile configFile = new LoadFile("config.cfg");
        configFile.ReadFile();

        ircUserName = configFile.ircUserName;
        ircOAuth = configFile.ircOAuth;
        ircChannel = configFile.ircChannel;

    }


    private void saveLoginDetails(String user, String OAuth) {
        new WriteFile("config.cfg", "IRClogin:", ": " + user);
        new WriteFile("config.cfg", "IRCoAuth:", ": " + OAuth);

    }


    public static int getFrameHeight() {
        return frameHeight;
    }


    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);

        new Main().init();


    }

    public static int getCursX(){
        return cursX;
    }

    public static int getCursY(){
        return cursY;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }
    int i = 0;
    @Override
    public void mousePressed(MouseEvent arg0) {
        f.requestFocus();


        frameWidth = f.getWidth();
        frameHeight = -66 + f.getHeight();
        if (isSidePanel) {
            frameWidth -= 200;

            i++;
        }
        Point curs = f.getMousePosition();
        if (curs != null && lastCurs != curs) {
            float calcX = curs.x * 765 / frameWidth;
            float calcY = (curs.y - 25) * 500 / (frameHeight);

            cursX = (int) calcX;
            cursY = (int) calcY;
            lastCurs = curs;
        }

        if (calibrate) {

            if (arg0.getButton() == 3) {
                assert curs != null;
                if (calibBoxX == 0 && curs.y > 30) {
                    calibBoxX = curs.x;
                    calibBoxY = curs.y;
                }

            }
            if (calibBoxX > 0) {

                f.setSize((int) ((calibBoxX) * 1.095),
                        (int) ((calibBoxY) * 1.24) + 40);
                frameOriginalWidth = f.getWidth();
                frameOriginalHeight = f.getHeight();

                calibrate = false;
                f.remove(infoPanel);
                f.setResizable(false);
                f.revalidate();
                f.repaint();
                login();
            }
        }
        if (arg0.getButton() == 1) {
            drawString(1);
            myString = "click " + cursX + " " + cursY;

        }
        if (arg0.getButton() == 2) {
            drawString(2);
            myString = "hover " + cursX + " " + cursY;

        }
        if (arg0.getButton() == 3) {
            drawString(3);
            myString = "rclick " + cursX + " " + cursY;
        }

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        f.requestFocus();
        frameWidth = f.getWidth();
        frameHeight = -66 + f.getHeight();
        if (isSidePanel) {
            frameWidth -= 200;
        }
        Point curs = f.getMousePosition();
        if (curs != null && lastCurs != curs) {
            float calcX = curs.x * 765 / frameWidth;
            float calcY = (curs.y - 25) * 500 / (frameHeight);

            cursX = (int) calcX;
            cursY = (int) calcY;
            lastCurs = curs;
        }
        if (arg0.getKeyCode() == 17) {
            drawString(2);
            myString = "hover " + cursX + " " + cursY;

        }
        if (arg0.getKeyCode() == 10) {
            if (myString != null) {
                IRC.sendIRCMessage(myString);
            }

        }

    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

}
