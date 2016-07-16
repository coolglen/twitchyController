package com.glen.api;


public class GroundItems {

    private boolean stackable;
    private int amount;
    private int id;
    private String name;
    private int[] location;

    public GroundItems(int[] location, int amount, int id, String name, boolean stackable) {
        this.location = location;
        this.amount = amount;
        this.id = id;
        this.name = name;
        this.stackable = stackable;
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

