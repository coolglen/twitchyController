
package com.glen.api;


public class Bank{
   	private int amount;
   	private int item_id;
   	private String item_name;
   	private Boolean stackable;

    public Bank(boolean stackable, int amount, int item_id, String item_name){
    this.stackable = stackable;
        this.amount = amount;
        this.item_id = item_id;
        this.item_name = item_name;
    }

 	public int getAmount(){
		return this.amount;
	}

 	public int getItem_id(){
		return this.item_id;
	}

 	public String getItem_name(){
		return this.item_name;
	}

    public Boolean isStackable(){
		return this.stackable;
	}
}
