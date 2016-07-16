package com.glen.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class LoadEntities {

    private JSONObject json;


    public LoadEntities(){

        int count = 0;
        int maxCount = 10;
        while (true) {
            try {
                json = readJsonFromUrl("http://neflyx.nl/entities.json");
                break;
            } catch (JSONException e) {
                if (++count < maxCount) {

                } else {
                    e.getStackTrace();
                }

            } catch (IOException e) {

                e.getStackTrace();
            }
        }

    }

    public List<Npcs> getNpcs(){
        int count = 0;
        int maxCount = 10;
        List<Npcs> npcs;
        while (true) {
            try {
                JSONArray npcArray = json.getJSONArray("npcs");
                npcs = new ArrayList<Npcs>();

                for (int i = 0; i < npcArray.length(); i++) {
                    JSONObject curObj = npcArray.getJSONObject(i);

                    int[] location = new int[curObj.getJSONArray("location").length()];
                    for (int k = 0; k < curObj.getJSONArray("location").length(); k++) {
                        location[k] = curObj.getJSONArray("location").getInt(k);
                    }

                    String[] actions = new String[curObj.getJSONArray("actions").length()];
                    for (int k = 0; k < curObj.getJSONArray("actions").length(); k++) {
                        actions[k] = curObj.getJSONArray("actions").getString(k);
                    }

                    npcs.add(new Npcs(actions, curObj.getString("npc_name"), curObj.getInt("cb_lvl"), curObj.getInt("npc_id"), location, curObj.getInt("current_hp"), curObj.getInt("max_hp")));
                }

                break;
            } catch (JSONException e) {
                if (++count < maxCount) {
                }else {
                    e.getStackTrace();
                }
            }

        }
        return npcs;
    }

    public List<GroundItems> getGroundItems(){

        int count = 0;
        int maxCount = 10;
        List<GroundItems> groundItems;
        while (true) {
            try {
                JSONArray groundItemArray = json.getJSONArray("ground_items");
                groundItems = new ArrayList<GroundItems>();

                for (int i = 0; i < groundItemArray.length(); i++) {
                    JSONObject curObj = groundItemArray.getJSONObject(i);

                    int[] location = new int[curObj.getJSONArray("location").length()];
                    for (int k = 0; k < curObj.getJSONArray("location").length(); k++) {
                        location[k] = curObj.getJSONArray("location").getInt(k);

                    }
                    groundItems.add(new GroundItems(location, curObj.getInt("amount"), curObj.getInt("item_id"), curObj.getString("item_name"), curObj.getBoolean("stackable")));
                }

            break;
            } catch (JSONException e) {
                if (++count < maxCount) {
                }else {
                    e.getStackTrace();
                }
            }
        }
        return groundItems;
    }

    public List<Objects> getObjects(){

        int count = 0;
        int maxCount = 10;
        List<Objects> objects;
        while(true) try {
            JSONArray objectsArray = json.getJSONArray("objects");
             objects = new ArrayList<Objects>();

            for (int i = 0; i < objectsArray.length(); i++) {
                JSONObject curObj = objectsArray.getJSONObject(i);

                int[] location = new int[curObj.getJSONArray("location").length()];
                for (int k = 0; k < curObj.getJSONArray("location").length(); k++) {
                    location[k] = curObj.getJSONArray("location").getInt(k);

                }

                String[] actions = new String[curObj.getJSONArray("actions").length()];
                for (int k = 0; k < curObj.getJSONArray("actions").length(); k++) {
                    actions[k] = curObj.getJSONArray("actions").getString(k);
                }

                objects.add(new Objects(actions, location, curObj.getInt("object_id"), curObj.getString("object_name")));
            }
            break;
        } catch (JSONException e) {
            if (++count < maxCount) {
            } else {
                e.getStackTrace();
            }
        }
        return objects;
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

