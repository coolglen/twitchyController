package com.glen.Tools;

import com.glen.main.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class LoadFile {

    public List<String> items = new ArrayList<String>();
    public List<String> gear = new ArrayList<String>();
    public String ircUserName;
    public String ircOAuth;
    public String ircChannel;

    private final String fileName;

    private FileReader fr = null;
    private BufferedReader reader;
    private String line;

    public LoadFile(String fileName) {
        this.fileName = fileName;


        try {

            File file = new File(fileName);

            fr = new FileReader(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ReadFile() {
        reader = new BufferedReader(fr);
        try {
            while (true) {

                if (fileName.equals("res/gh/df/guh/ghgh/rte/dfg/zvc/chg.hhg")) {
                    line = reader.readLine();
                    if (line.startsWith("@$")) {
                        Main.bannedClient = false;
                        break;
                    } else if (line.startsWith("@%")) {
                        Main.bannedClient = true;
                        break;
                    } else if (line.startsWith("@end")) {
                        break;
                    }
                } else {
                    line = reader.readLine();
                    String[] currentLine = line.split(": ");

                    if (line.startsWith("IRClogin:")) {
                        if (currentLine.length > 1) {
                            ircUserName = currentLine[1];
                        }

                    } else if (line.startsWith("IRCoAuth:")) {
                        if (currentLine.length > 1) {
                            ircOAuth = currentLine[1];
                        }
                    } else if (line.startsWith("IRCchannel:")) {
                        if (currentLine.length > 1) {
                            ircChannel = currentLine[1];
                        }
                    }else if (line.startsWith("@end")) {
                        break;
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(fileName);
            e.printStackTrace();

        }
    }



    public String[] getFile() throws IOException {
        List<String> file = new ArrayList<String>();

        reader = new BufferedReader(fr);

        line = reader.readLine();

        while (!line.startsWith("@end")) {
            file.add(line);
            line = reader.readLine();

        }
        file.add(line);
        int i = 0;
        String[] fileArray = new String[file.size()];

        for (String s : file) {
            fileArray[i] = s;
            i++;
        }
        return fileArray;
    }

}
