package com.glen.main;

class Items implements Comparable<Items> {

    private String item;
    private String amountString;
    private String slotString;
    private String idString;
    private String options;

    public Items(String item, String idString, String amountString) {
        this.item = item;
        this.amountString = amountString;
        this.idString = idString;
    }

    public Items(String item, String idString, String amountString, String slotString, String options) {
        this.item = item;
        this.amountString = amountString;
        this.slotString = slotString;
        this.idString = idString;
        this.options = options;
    }
    public String getItems() {
        return item;
    }

    public int getAmount() {

        int amount = Integer.parseInt(amountString);
        return amount;
    }
    public int getSlot() {

        int slot = Integer.parseInt(slotString);
        return slot;
    }

    public String getOptions() {
        return options;
    }

    public int getId() {
        int id = Integer.parseInt(idString);
        return id;
    }

    @Override
    public int compareTo(Items o) {
        return 0;
    }
}