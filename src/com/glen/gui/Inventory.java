
package com.glen.gui;

import java.util.List;

public class Inventory{
   	private List actions;
   	private int amount;
   	private int inventory_slot;
   	private int item_id;
   	private String item_name;
   	private boolean stackable;

    public Inventory(boolean stackable, int amount, int item_id, int inventory_slot, String item_name, List actions){
        this.stackable = stackable;
        this.amount = amount;
        this.item_id = item_id;
        this.inventory_slot = inventory_slot;
        this.item_name = item_name;
        this.actions = actions;

    }
 	public List getActions(){
		return this.actions;
	}
 	public int getAmount(){
		return this.amount;
	}
 	public int getInventory_slot(){
		return this.inventory_slot;
	}
 	public int getItem_id(){
		return this.item_id;
	}
 	public String getItem_name(){
		return this.item_name;
	}
 	public boolean isStackable(){
		return this.stackable;
	}
}
