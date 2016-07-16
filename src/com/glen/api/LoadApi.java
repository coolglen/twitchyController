package com.glen.api;

import com.glen.gui.Inventory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;


public class LoadApi {

    private JSONObject json;

    public void updateApi(){
        int count = 0;
        int maxCount = 10;
        while (true) {
            try {
                json = readJsonFromUrl("http://neflyx.nl/api.json");
                //json = readJsonFromUrl("https://gist.githubusercontent.com/coolglen/b0d988529e473a1e24be/raw/70f759072038078d1047cf7a7801a4d9b6876810/gistfile1.txt");
                break;
            } catch (JSONException e) {
                if (++count > maxCount) {
                    e.getStackTrace();
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
    public Info getInfo(){

        int count = 0;
        int maxCount = 10;
        Info info = null;
        while(true) {
            try {
                JSONArray infoArray = json.getJSONArray("info");


                JSONObject curObj = infoArray.getJSONObject(0);

                List<Integer> location = new ArrayList<Integer>();
                for (int k = 0; k < curObj.getJSONArray("location").length(); k++) {
                    location.add(curObj.getJSONArray("location").getInt(k));
                }

                List<Integer> clickLocation = new ArrayList<Integer>();
                for (int k = 1; k < curObj.getJSONArray("last_click").length(); k++) {
                    clickLocation.add(curObj.getJSONArray("last_click").getInt(k));
                }

                info = (new Info(curObj.getInt("special_energy"), location, clickLocation, curObj.getInt("run_energy")));
                break;
            } catch (JSONException e) {
                if (++count > maxCount) {
                    e.printStackTrace();
                    break;
                }else{
                    updateApi();
                }
            }

        }
        return info;
    }

    public List<Inventory> getInventory(){
        int h = 0;
        int count = 0;
        int maxCount = 10;
        List<Inventory> inventory = new ArrayList<Inventory>();
        String itemName;
        while(true) {
            try {
                JSONArray inventArray = json.getJSONArray("inventory");



                for (int i = 0; i < inventArray.length(); i++) {
                    JSONObject curObj = inventArray.getJSONObject(i);

                    List<String> actions = new ArrayList<String>();
                    for (int k = 0; k < curObj.getJSONArray("actions").length(); k++) {
                        actions.add(curObj.getJSONArray("actions").getString(k));
                    }
                    h = i;
                    if(curObj.has("item_name")){
                        itemName = curObj.getString("item_name");
                    }else {
                        System.out.println("no name");
                        itemName = "null";
                    }
                    inventory.add(new Inventory(curObj.getBoolean("stackable"), curObj.getInt("amount"), curObj.getInt("item_id"), curObj.getInt("inventory_slot"), itemName, actions));
                }

                break;
            } catch (JSONException e) {

                if (++count > maxCount) {
                    e.printStackTrace();
                    break;
                }else{
                   updateApi();
                }
            }
        }
        return inventory;
    }

    public List<Skills> getSkills() {

        int count = 0;
        int maxCount = 10;
        JSONArray skillsArray = null;
        List<Skills> skills = new ArrayList<Skills>();
        while (true) {
            try {
                skillsArray = json.getJSONArray("skills");



                for (int i = 0; i < skillsArray.length(); i++) {
                    JSONObject curObj = skillsArray.getJSONObject(i);
                    skills.add(new Skills(curObj.getInt("real_level"), curObj.getString("skill_name"), curObj.getInt("current_level"), curObj.getInt("xp_left")));
                }
                break;
            } catch (JSONException e) {
                if (++count > maxCount) {
                    e.getStackTrace();
                    break;
                }else{
                    updateApi();
                }

            }
        }
        return skills;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {

        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {

            is.close();

        }
    }

}
