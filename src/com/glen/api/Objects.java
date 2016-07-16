package com.glen.api;


public class Objects {

    private String name;
    private int[] location;
    private int id;
    private String[] actions;

    public Objects(String[] actions, int[] location, int id, String name) {
        this.actions = actions;
        this.location = location;
        this.id = id;
        this.name = name;
    }

    public String[] getActions() {
        return actions;
    }

    public void setActions(String[] actions) {
        this.actions = actions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}