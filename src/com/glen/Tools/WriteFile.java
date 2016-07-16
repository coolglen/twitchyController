package com.glen.Tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WriteFile {


    private String[] file;

    public WriteFile(String fileName, String line, String newValue) {

        LoadFile rf = new LoadFile(fileName);
        List<String> fileList = new ArrayList<String>();


        try {
            file = rf.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.addAll(fileList, file);
        try {


            File fileN = new File(fileName);
            FileWriter fw = new FileWriter(fileN.getAbsolutePath());

            String[] endfile;
            if (fileName.equals("config.cfg")) {
                for (int i = 0; i < file.length; i++) {
                    if (line.equals("@gear")) {
                        if (file[i].startsWith(line)) {
                            fileList.add(i + 1, newValue);
                            break;
                        }


                    } else if (line.equals("@item")) {
                        if (file[i].startsWith(line)) {
                            fileList.add(i + 1, newValue);
                            break;
                        }
                    } else if (line.equals("@$") || line.equals("@%")) {
                        if (file[i].startsWith(line)) {
                            fileList.set(i, newValue);
                            break;
                        }
                    } else if (file[i].startsWith(line)) {
                        String[] currentLine = line.split(": ");
                        fileList.set(i, currentLine[0] + newValue);
                        break;
                    }
                }
                int k = 0;
                endfile = new String[fileList.size()];
                for (String f : fileList) {
                    endfile[k] = f;
                    k++;
                }
                for (String anEndfile : endfile) {
                    fw.write(anEndfile + "\n");


                }
            } else if (fileName.equals("res/gh/df/guh/ghgh/rte/dfg/zvc/chg.hhg")) {

                for (int i = 0; i < file.length; i++) {
                    if (line.equals("@$") || line.equals("@%")) {
                        if (file[i].startsWith(line)) {
                            fileList.set(i, newValue);
                            break;
                        }
                    }
                }
                int k = 0;
                endfile = new String[fileList.size()];
                for (String f : fileList) {
                    endfile[k] = f;
                    k++;
                }
                for (String anEndfile : endfile) {
                    fw.write(anEndfile + "\n");

                }
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
