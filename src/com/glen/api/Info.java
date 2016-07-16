
package com.glen.api;

import org.json.JSONArray;

import java.util.List;

public class Info {
    private List last_click;
    private List location;
    private int run_energy;
    private int special_energy;

    public Info(int special_energy, List location, List last_click, int run_energy){
        this.special_energy = special_energy;
        this.location = location;
        this.last_click = last_click;
        this.run_energy = run_energy;

    }

    public List getLast_click() {
        return this.last_click;
    }

    public List getLocation() {
        return this.location;
    }

    public int getRun_energy() {
        return this.run_energy;
    }

    public int getSpecial_energy() {
        return special_energy;
    }
}
