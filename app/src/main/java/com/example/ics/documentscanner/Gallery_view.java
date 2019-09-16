package com.example.ics.documentscanner;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Gallery_view extends AppCompatActivity {
         String get_valid_user;
         File file;
         String image_url = "http://ihisaab.com/uploads/report/";
         String image_url2 = "http://ihisaab.com/uploads/report/";
         URL image_download_url;
         DownloadManager.Request request;
         List<File> Reports;
         ListView reports_list;
         DownloadManager downloadManager;
    String document;
    TextView reports_txt;
    URL  url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view);
        get_valid_user = getIntent().getStringExtra("gallery_code");
        try_to_get_reports();
        reports_txt = (TextView)findViewById(R.id.reports_txt);
        new Post_for_gallery(get_valid_user).execute();

//url is = http://ihisaab.com/api/reportByCode?cut_code=SHRE1018-1
    }

    private void try_to_get_reports() {
        Intent intent = new Intent(Gallery_view.this,MainRadioActivity.class);
        String root =  Environment.DIRECTORY_DOWNLOADS.concat ("/I-Hisaab_Reports");
        File Report_file = new File(root);
        Log.e("File is on", ""+Report_file);
        if(Report_file.exists())
        {
            Toast.makeText(this, "Retrieving  Reports", Toast.LENGTH_LONG).show();
            retrive_all_of_them(Report_file);
        }
    }

    private Boolean retrive_all_of_them(File reports) {
        if(reports.isDirectory()){
            String[] children = reports.list();
            for(int i =0; i<children.length; i++){
                Boolean success = retrive_all_of_them(new File(reports , children[i]));
                Log.e("File retrieve","name was "+children[i].toString());
                if (!success){
                    return  false;
                }
            }

        }
        Reports.add(reports.getAbsoluteFile());
        return true;
    }

    private class Post_for_gallery extends AsyncTask<String, Void, String>{
        String gallery_user;
        public Post_for_gallery(String get_valid_user) {
            this.gallery_user = get_valid_user;
        }

        protected void onPreExecute() {
            //  dialog = new ProgressDialog(AccountFormActivity.this);
            //  dialog.show();

        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                  url = new URL("http://ihisaab.com/api/reportByCode");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("cut_code", gallery_user);

                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000  /*milliseconds*/);
                conn.setConnectTimeout(15000  /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                //  dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    Log.e("Array length", "is "+jsonArray.length());
                    String repo_dir =  Environment.DIRECTORY_DOWNLOADS.concat ("/I-Hisaab_Reports");
                    Log.e("Report"," main directory is "+repo_dir);
                    for(int i =0 ;i<jsonArray.length();i++){
                        downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                        JSONObject object = jsonArray.getJSONObject(i);
                        document = object.getString("document");
                        Log.e("Document name is",""+document);
                        image_url = image_url + document;
                        image_download_url = new URL(image_url);
                       Log.e("Document name is",""+image_url);
                        request = new DownloadManager.Request(Uri.parse(image_url2+""+document));
                        request.setDescription("For reports");
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS.concat("/I-Hisaab_Reports") , ""+document);

                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
                        request.setVisibleInDownloadsUi(true);
                        request.setAllowedOverRoaming(true);
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setMimeType("*/*");

//                        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        downloadManager.enqueue(request);
                        String result_of_reports_true = "Report Success full retrieved,Please go to the Download/I-Hisaab_Reports folder";
                        reports_txt.setText(result_of_reports_true);
                       document = null;
                       image_url = null;
                       image_url = image_url2;


                    }



                } catch (JSONException e) {
                    String result_of_reports = "Failed to retrieve reports ,please try again later";
                    reports_txt.setText(result_of_reports);
                    Toast.makeText(Gallery_view.this, "Failed to retrieve reports ,please try again later", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    @Override
    public void onBackPressed() {
        Intent g_to_mai = new Intent(Gallery_view.this, MainRadioActivity.class);
        startActivity(g_to_mai);
        finish();
        super.onBackPressed();
    }
}
