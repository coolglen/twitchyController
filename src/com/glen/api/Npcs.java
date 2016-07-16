package com.glen.api;



public class Npcs {

    private String Name;
    private int combatLevel;
    private int id;
    private int[] location = new int[3];
    private int max_hp;
    private String[] actions;
    private int currentHp;


    public Npcs(String[] actions, String name, int combatLevel, int id, int[] location, int currentHp, int max_hp) {
        this.actions = actions;
        Name = name;
        this.combatLevel = combatLevel;
        this.id = id;
        this.location = location;
        this.max_hp = max_hp;
        this.currentHp = currentHp;
    }

    public String[] getActions() {
        return actions;
    }

    public void setActions(String[] actions) {
        this.actions = actions;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCombatLevel() {
        return combatLevel;
    }

    public void setCombatLevel(int combatLevel) {
        this.combatLevel = combatLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public int getMax_hp() {
        return max_hp;
    }

    public void setMax_hp(int max_hp) {
        this.max_hp = max_hp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }
}

