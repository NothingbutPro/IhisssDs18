package com.example.ics.documentscanner;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ics.documentscanner.Sharedprefrence.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class SingleUser extends AppCompatActivity {
    // TextView help;
    Button nxa;
    EditText name_of_i, e_mail, pan_i, aadhaNo, mob, ret_email, ret_mobile,ref_ref_code;
    String names, emails, pan_nos, adhan_nos, mobiles, ret_emails, ret_mobiles,ref_codes;
    int n;
    Button skip, next;
    Button si_bx;
    TextView help;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user);


        final Dialog dialog = new Dialog(SingleUser.this);
        dialog.setContentView(R.layout.helpdialog);
        dialog.setTitle("Please choose your Type.");

        dialog.show();
        si_bx = (Button) dialog.findViewById(R.id.si_bx);
        si_bx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        sessionManager = new SessionManager(this);
        help = (TextView) findViewById(R.id.help);
        next = (Button) findViewById(R.id.next);
        name_of_i = (EditText) findViewById(R.id.name_of_i);
        e_mail = (EditText) findViewById(R.id.e_mail);
        pan_i = (EditText) findViewById(R.id.pan_i);
        aadhaNo = (EditText) findViewById(R.id.aadhaNo);
        mob = (EditText) findViewById(R.id.mob);
        ref_ref_code = (EditText)findViewById(R.id.ref_ref_code);
        String strMobile=sessionManager.getMobile();
        String strName=sessionManager.getUsername();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Intent st = new Intent(SingleUser.this ,Single_user_act_2.class);
                // startActivity(st);

                if (Connectivity.isNetworkAvailable(SingleUser.this)) {

                } else {
                    Toast.makeText(SingleUser.this, "no internet", Toast.LENGTH_SHORT).show();

                }
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//
                final Dialog dialog = new Dialog(SingleUser.this);
                dialog.setContentView(R.layout.helpdialog);
                dialog.setTitle("Please choose your Type.");

                dialog.show();
                si_bx = (Button) dialog.findViewById(R.id.si_bx);
                si_bx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });

        nxa = (Button) findViewById(R.id.next);
        nxa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                names = name_of_i.getText().toString();
                emails = e_mail.getText().toString();
                pan_nos = pan_i.getText().toString();
                adhan_nos = aadhaNo.getText().toString();
                mobiles = mob.getText().toString();
                ret_emails = e_mail.getText().toString();
                ret_mobiles = mob.getText().toString();
                ref_codes = ref_ref_code.getText().toString();
//         Intent to_s1 = new Intent(SingleUser.this ,Single_user_act_2.class);
//         startActivity(to_s1);
                //   new Post_Income_Form().execute();
                Intent to_s1 = new Intent(SingleUser.this, Single_user_act_2.class);
                to_s1.putExtra("name", names);
                to_s1.putExtra("email", emails);
                to_s1.putExtra("pan_no", pan_nos);
                to_s1.putExtra("adhan_no", adhan_nos);
                to_s1.putExtra("mobile", mobiles);
                to_s1.putExtra("ret_email", emails);
                to_s1.putExtra("ret_mobile", mobiles);
                to_s1.putExtra("ref_code",ref_codes);
                Toast.makeText(SingleUser.this, ""+ref_codes, Toast.LENGTH_SHORT).show();
                //to_s1.putExtra("pan",pan_nos);
                if(names.isEmpty() || pan_nos.isEmpty() || mobiles.isEmpty()||ret_emails.isEmpty()||
                        ret_mobiles.isEmpty()){
                    if(names.isEmpty()){
                        name_of_i.setError("Name should not be empty");
                    }if(pan_nos.isEmpty() || pan_nos.length() != 10){
                        pan_i.setError("Pan number is either less than 10 digit or empty");
                    }
                    if(mobiles.isEmpty() || mobiles.length() !=10 ){
                        mob.setError("Mobile number is either less than 10 digit or empty");
                    }
                    Toast.makeText(SingleUser.this, "Fields can not be empty", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(to_s1);
                }


            }
        });
    }

    private class Post_Income_Form extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(SingleUser.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.com/Api/taxreturn");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("name", names);
                postDataParams.put("email", emails);
                postDataParams.put("pan_no", pan_nos);
                postDataParams.put("adhan_no", adhan_nos);
                postDataParams.put("mobile", mobiles);
                postDataParams.put("ret_email", emails);
                postDataParams.put("ret_mobile", mobiles);


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
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    String response = jsonObject.getString("response");
                    Log.e("Response is", response);
                    String message = jsonObject.getString("message");
                    JSONObject dt = jsonObject.getJSONObject("data");
                    // String cus_code = dt.getString("cust_code");
//                    store = cus_code.toString();
//                    editor = sharedpreferences.edit();
//                    editor.putString("cus_code" , store);
                    // Toast.makeText(SingleUser.this, ""+cus_code, Toast.LENGTH_SHORT).show();
                    //     Log.e("Your Customer code is", cus_code);
                    Intent to_s1 = new Intent(SingleUser.this, Single_user_act_2.class);
                    startActivity(to_s1);
                    if (response.equalsIgnoreCase("True")) {
                        Intent to_s2 = new Intent(SingleUser.this, Single_user_act_2.class);
                        to_s2.putExtra("pan", pan_nos);
                        startActivity(to_s2);
                        finish();
                    }


                } catch (JSONException e) {
                    Intent to_s1 = new Intent(SingleUser.this, Single_user_act_2.class);
                    to_s1.putExtra("name", names);
                    to_s1.putExtra("email", emails);
                    to_s1.putExtra("pan_no", pan_nos);
                    to_s1.putExtra("adhan_no", adhan_nos);
                    to_s1.putExtra("mobile", mobiles);
                    String final_email_ret = getIntent().getStringExtra("to_the_last_email");
                    String final_mob = getIntent().getStringExtra("to_the_last_mob");
                    if(final_email_ret != null && final_mob != null){
                    //    Toast.makeText(SingleUser.this, ""+final_email_ret, Toast.LENGTH_SHORT).show();
                      //  Toast.makeText(SingleUser.this, ""+final_mob, Toast.LENGTH_SHORT).show();

                        to_s1.putExtra("ret_email", final_email_ret);
                        to_s1.putExtra("ret_mobile", final_mob);
                    }else if(final_email_ret == null || final_mob == null){
                        to_s1.putExtra("ret_email", emails);
                        to_s1.putExtra("ret_mobile", mobiles);

                    }



                  //  String from_emai = getIntent().getStringExtra("ret_of_email");
                   // String from_mob = getIntent().getStringExtra("mob_to_ret");
                    //to_s1.putExtra("pan",pan_nos);
                    startActivity(to_s1);
                    e.printStackTrace();
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
    }

    @Override
    public void onBackPressed() {
        Intent ix = new Intent(SingleUser.this ,MainRadioActivity.class);
        startActivity(ix);
        super.onBackPressed();
    }
}
