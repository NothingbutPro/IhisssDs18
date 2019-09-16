package com.example.ics.documentscanner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by HP on 13-04-2018.
 */

public class Connection {
    public String sendpostRequst(String registerUrl, HashMap<String, Object> dataPrams) {
        String response="";
        try {
            URL url = new URL(registerUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(dataPrams!=null) {
                conn.setReadTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getpostdataparams(dataPrams));

                writer.flush();
                writer.close();
                os.close();
            }
            int responseCode=conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder stringBuffer=new StringBuilder();
                String line;
                while ((line=br.readLine())!=null){
                    stringBuffer.append(line);
                }
                String responce = stringBuffer.toString();
                return responce;
            }
            else {
                response="Error In Connecting";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getpostdataparams(HashMap<String, Object> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, Object> entry :params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
        }

        return result.toString();
    }
}
