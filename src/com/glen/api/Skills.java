
package com.glen.api;

public class Skills{
   	private int current_level;
   	private int real_level;
   	private String skill_name;
   	private int xp_left;

    public Skills(int real_level, String skill_name, int current_level, int xp_left) {
        this.real_level = real_level;
        this.skill_name = skill_name;
        this.current_level = current_level;
        this.xp_left = xp_left;
    }

    public int getCurrent_level(){
		return this.current_level;
	}

 	public int getReal_level(){
		return this.real_level;
	}

 	public String getSkill_name(){
		return this.skill_name;
	}

 	public Number getXp_left(){
		return this.xp_left;
	}

}
