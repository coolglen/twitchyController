package com.glen.api;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class LoadBank {


    private List<Bank> bank;
    private JSONObject json;


            public LoadBank(){
        try {
            json = readJsonFromUrl("http://neflyx.nl/bank.json");
            JSONArray bank = json.getJSONArray("bank");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public List<Bank> getBank(){
        try {
            JSONArray bankArray = json.getJSONArray("bank");
            bank = new ArrayList<Bank>();

            for (int i = 0; i < bankArray.length(); i++) {
                JSONObject curObj = bankArray.getJSONObject(i);

                bank.add(new Bank(curObj.getBoolean("stackable"), curObj.getInt("amount"), curObj.getInt("item_id"),curObj.getString("item_name")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this.bank;
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
