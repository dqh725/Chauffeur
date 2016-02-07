package com.chauffr;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Wenyue on 5/02/2016.
 */
public class GeoCoder {

    public static final int SHORT_NAME=0;
    public static final int LONG_NAME=1;
    private static String API = "AIzaSyCKXxMpUIrQkEaKlmnaBP276RBrbTgQGlk";
    private JSONObject addressDetail = null;
    private JSONArray addressComponent = null;
    private String placeId;


    public GeoCoder(String placeId){
        this.placeId = placeId;
        getAddressDetail();
    }

    public JSONObject getAddressDetail(){
        JSONObject responseJSON;
        String uri =  "https://maps.googleapis.com/maps/api/geocode/json?"
        +"place_id="+placeId+"&key="+API;
        try {
            responseJSON = new JSONObject(httpRequest(uri));
            addressDetail = (JSONObject)(responseJSON.getJSONArray("results").get(0));
            Log.i("addresss",addressDetail.toString());
            addressComponent = addressDetail.getJSONArray("address_components");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return addressDetail;
    }

    public String getCountry(int shortOrLong){
        return getComponent("country", shortOrLong);
    }

    public String getPostalCode(int shortOrLong){
        return getComponent("postal_code", shortOrLong);
    }

    public String getLocality(int shortOrLong){
        return getComponent("locality", shortOrLong);
    }

    public String getRoute(int shortOrLong){
        return getComponent("route", shortOrLong);
    }

    public String getStreetNumber(int shortOrLong){
        return getComponent("street_number", shortOrLong);
    }

    public String getState(int shortOrLong){
        return getComponent("administrative_area_level_1", shortOrLong);
    }

    public String getComponent(String componentName,int shortOrLong){
        if(addressComponent==null){
            return null;
        }
        else{
            JSONObject temp =null;
            for(int i= addressComponent.length()-1; i>=0; i-- ){
                try {
                    temp = (JSONObject)addressComponent.get(i);
                    Log.i("component", temp.toString());
                    JSONArray typesJSON = temp.getJSONArray("types");
                    for(int j=0;j<typesJSON.length();j++) {
                        if (typesJSON.getString(j).equals(componentName) && shortOrLong==SHORT_NAME){
                            return temp.getString("short_name");
                        }
                        else if (typesJSON.getString(j).equals(componentName) && shortOrLong==LONG_NAME){
                            return temp.getString("long_name");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }


    public static String httpRequest(String uri){
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET"); //"POST"/ "GET"
            try {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                String res = convertStreamToString(in);
                Log.i("myres", res);
                return res;
            } finally{
                connection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"ConnectionFailed\":\"true\"}";
        }

    }
    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
