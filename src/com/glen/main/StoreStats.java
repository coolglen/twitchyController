package com.glen.main;

public class StoreStats {

    private String stat;
    private String currentLevelString;
    private String realLevelString;

    public StoreStats(String stat, String currentLevelString, String realLevelString) {
        this.stat = stat;
        this.currentLevelString = currentLevelString;
        this.realLevelString = realLevelString;
    }

    public String getStat() {
        return stat;
    }

    public String getCurrentLevel() {

      //  int currentLevel = Integer.parseInt(currentLevelString);
        return currentLevelString;
    }
    public String getRealLevel() {

        //int realLevel = Integer.parseInt(realLevelString);
        return realLevelString;
    }
}
