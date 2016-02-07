package com.chauffeur.appyfuture.chauffeur;


import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Wenyue on 7/12/2015.
 */
public class API {
    private String public_key = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJTZXJ2aWNlVHlwZSI6IkNoYXVmZmV1ciJ9.JszCOlg1Ow7XJ9OPMvV1wVRm5c4lEJM0BtkHH8GtfYQ";
    private String host = "54.153.152.111";
    public API(){
    }

    public String createClientRequest(){
        return null;
    }

    public String getChauffeurListByIdRequest(String id){
        return null;
    }

    public void httpRequest(HashMap<String, String> requestParams, String path, String method){
        try {
            URL url = new URL(host+path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod(method); //"POST"/ "GET"
            connection.setDoInput(true); //allows receiving data.
            connection.setDoOutput(true); // allows sending data
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(hash2string(requestParams));
            writer.flush();
            writer.close();
            os.close();

            try {
                InputStream in = new BufferedInputStream(connection.getInputStream());

            } finally{
                connection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // hash value to json string of url
    private String hash2string(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
