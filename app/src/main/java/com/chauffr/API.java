package com.chauffr;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Wenyue on 7/12/2015.
 */
public class API {
    private static String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJTZXJ2aWNlVHlwZSI6IkNoYXVmZmV1ciJ9.JszCOlg1Ow7XJ9OPMvV1wVRm5c4lEJM0BtkHH8GtfYQ";
    private static String host = "http://54.153.152.111";

    public static JSONObject authenticateClient(JSONObject client){
        JSONObject responseJSON = null;
        String path = "/chauffr_services_test/servicestack/AuthenticateClient";
        try{
            client.put("Token", TOKEN);
            responseJSON = new JSONObject(httpRequest(client, path, "POST"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseJSON;
    }

    public static JSONObject getChauffeurByCodeRequest(String code){
        JSONObject responseJSON=null;
        String path = "/chauffr_services_test/servicestack/GetChauffeurByCode";
        try {
            JSONObject params = new JSONObject();
            params.put("Token", TOKEN);
            params.put("ChauffeurCode", code);
            responseJSON = new JSONObject(httpRequest(params, path, "GET"));
            return responseJSON;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseJSON;
    }

    public static JSONArray getChauffeurCodeList(){
        String path = "/chauffr_services_test/servicestack/GetChauffeurCodes";
        try {
            JSONObject params = new JSONObject();
            params.put("Token", TOKEN);
            String response= httpRequest(params, path, "GET");
            JSONObject responseJSON = new JSONObject(response);
            return (JSONArray)responseJSON.get("ChauffeurCodes");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static JSONArray getCarTypeList(int chauffeurId){
        String path = "/chauffr_services_test/servicestack/GetFaresVehicleBased";
        try {
            JSONObject params = new JSONObject();
            params.put("ChauffeurId", chauffeurId);
            params.put("Token", TOKEN);
            String response= httpRequest(params, path, "GET");
            JSONObject responseJSON = new JSONObject(response);
            return (JSONArray)responseJSON.get("FaresVehicleBased");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static String httpRequest(JSONObject requestParams, String path, String method){
        try {
            URL url = new URL(host+path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod(method); //"POST"/ "GET"
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoInput(true); //allows receiving data.
            connection.setDoOutput(true); // allows sending data
            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(requestParams.toString());
            osw.flush();
            osw.close();
            os.close();
            try {
                Log.i("param",requestParams.toString() );
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

    public static JSONObject registerClientRequest(JSONObject clientJSON){
        JSONObject responseJSON = null;
        String path ="/chauffr_services_test/servicestack/RegisterClient";
        try {
            JSONObject params = new JSONObject();
            params.put("ClientDetails", clientJSON);
            params.put("Token", TOKEN);
            params.put("callback", "myCallback");
            responseJSON = new JSONObject(httpRequest(params, path, "POST"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseJSON;
    }

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
