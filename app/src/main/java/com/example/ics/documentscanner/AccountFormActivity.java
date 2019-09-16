package com.example.ics.documentscanner;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.text.InputFilter;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import static com.example.ics.documentscanner.GetMailActivity.MyPREFERENCES;
import static com.example.ics.documentscanner.RuntimePermission.hasPermissions;

public class AccountFormActivity extends AppCompatActivity {
    private static final int PERMISSION_ALL = 1;
    String cus_to;
    LinkedList<String> names_in_db = new LinkedList<String>();
    Toolbar toolbar_account;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public SQLiteDatabase dbsq;
    String name_str, email_str, pan_nostr, adhan_nostr, mobilestr, ret_emailstr, ret_mobilestr;
    ProgressDialog dialog;
    DatePickerDialog.OnDateSetListener date;
    String gm = null;
    EditText name, email, location, mobile, landmark, pan, gst, cus_e_m;
    String Name, Email, Location, Mobile, Landmark, Pan, Gst, Customeremail;
    Button submit, skip;
    private static final int PERMISSION_REQUEST_CODE = 1;
    AlertDialog.Builder builder;
    SessionManager sessionManager;
    TextInputLayout tobeinvis;
    private String wantPermission = Manifest.permission.GET_ACCOUNTS;
    private DBHelper dbHelper;
    ArrayList<Account_sqldb> acc_list;
    Accountlist_adapter acc_adapter = null;
    StringBuilder sp;
    int offline_sq;
    @SuppressLint("ServiceCast")
    TelecomManager tm;
    String Mobile_no;
    PhoneAccountHandle phoneAccountHandle;
    PhoneAccount phoneAccount;

//    String[] PERMISSIONS = {android.Manifest.permission.CAMERA,
//            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.GET_ACCOUNTS};

    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_form);
        sessionManager = new SessionManager(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


//        if(sharedpreferences.getString("cus_id" ,store ) != null){
//            Intent sp = new Intent(AccountFormActivity.this , MainActivity.class);
//            startActivity(sp);
//        }
        //
        // Email = email.getText().toString();
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;

        Account[] accounts = AccountManager.get(AccountFormActivity.this).getAccounts();

        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                Log.d("Getting Account", String.format("%s - %s", account.name, account.type));
                gm = account.name;
           //     Toast.makeText(this, "Your gmail is "+gm, Toast.LENGTH_SHORT).show();
//                            email = (EditText) findViewById(R.id.email);
//                            email.setText("" + gm, TextView.BufferType.EDITABLE);
//                            email.setClickable(false);

                //  email_address_view = (TextView) findViewById(R.id.email_address_view);
                //email_address_view.setText(account.name);
                //sessionManager.serverEmailLogin(account.name);
//                Intent go_to_main = new Intent(AccountFormActivity.this, MainRadioActivity.class);
//                go_to_main.putExtra("mob_to_ret",put_num);
//                go_to_main.putExtra("email_to_ret",account.name);
                //  Toast.makeText(GetMailActivity.this, ""+, Toast.LENGTH_SHORT).show();
                //new Post_number().execute();
//                startActivity(go_to_main);
                //  finish();

            }
        }

          //     getmail();

        toolbar_account = (Toolbar) findViewById(R.id.toolbar_account);

        toolbar_account.setNavigationIcon(R.drawable.arrow);

        toolbar_account.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tool = new Intent(AccountFormActivity.this , MainRadioActivity.class);
                startActivity(tool);
                finish();
                onBackPressed();
            }
        });


        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);

//        if(gm == null){
//          Email = email.getText().toString();
//        }else{
//            email.setText(gm);
//            Email = email.getText().toString();
//        }

     //   email.setFocusable(false);
      //  email.setClickable(false);
        location = (EditText)findViewById(R.id.location);
        mobile = (EditText)findViewById(R.id.mobile);
        landmark = (EditText)findViewById(R.id.landmark);
        pan = (EditText)findViewById(R.id.pan);
        gst = (EditText)findViewById(R.id.gst);
        cus_e_m = (EditText)findViewById(R.id.cus_e_m);
        submit = (Button)findViewById(R.id.submit);
        tobeinvis = (TextInputLayout)findViewById(R.id.tobeinvis);
        pan.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        dbHelper = new DBHelper(AccountFormActivity.this, "ihisaab_offline.sqlite", null, 1);
        builder = new AlertDialog.Builder(AccountFormActivity.this);
//        if(gm != null){
//            if(email.getText().equals(gm))
//                Email = email.getText().toString();
////            email.setClickable(false);
//            tobeinvis.setVisibility(View.GONE);
//
//        }else{
//          //  email.setText(gm);
//
//            Email = email.getText().toString();
//
//        }

        if(gm == null){
          Email = email.getText().toString();
        }else{
            email.setText(gm);
            Email = email.getText().toString();
            tobeinvis.setVisibility(View.GONE);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Name = name.getText().toString();
                Location = location.getText().toString();
                Mobile = mobile.getText().toString();
                Landmark = landmark.getText().toString();
                Pan = pan.getText().toString();
                Gst = gst.getText().toString();
                Customeremail = cus_e_m.getText().toString();
                Email = email.getText().toString();
                if (Connectivity.isNetworkAvailable(AccountFormActivity.this)) {
                    if(name.getText().toString().isEmpty() || pan.getText().toString().isEmpty() || name.getText().length() <=2 || Pan.length() !=10 || !Mobile.isEmpty() &&  Mobile.length() !=10){
                        if(Name.isEmpty()){
                             name.setError("Name should not be empty");

                          }else  if(!Mobile.isEmpty() &&  Mobile.length() !=10){
                            mobile.setError("Phone number should be of exactly of 10 digit");
                            // Toast.makeText(AccountFormActivity.this, "Phone number should be of exactly of 10 digit", Toast.LENGTH_SHORT).show();
                        }
                        if(Pan.isEmpty() || pan.length() !=10){
                              pan.setError("Pan number ");
                            if(!Mobile.isEmpty() &&  Mobile.length() !=10){
                                mobile.setError("Phone number should be of exactly of 10 digit");
                                // Toast.makeText(AccountFormActivity.this, "Phone number should be of exactly of 10 digit", Toast.LENGTH_SHORT).show();
                            }
                          }else    if(!Mobile.isEmpty() &&  Mobile.length() !=10){
                            mobile.setError("Phone number should be of exactly of 10 digit");
                            // Toast.makeText(AccountFormActivity.this, "Phone number should be of exactly of 10 digit", Toast.LENGTH_SHORT).show();
                        }
                          if( pan.length() !=10){
                              pan.setError("Pan number should be exactly of 10 digit");
                              if(!Mobile.isEmpty() &&  Mobile.length() !=10){
                                  mobile.setError("Phone number should be of exactly of 10 digit");
                                  // Toast.makeText(AccountFormActivity.this, "Phone number should be of exactly of 10 digit", Toast.LENGTH_SHORT).show();
                              }
                          }else  if(!Mobile.isEmpty() &&  Mobile.length() !=10){
                              mobile.setError("Phone number should be of exactly of 10 digit");
                              // Toast.makeText(AccountFormActivity.this, "Phone number should be of exactly of 10 digit", Toast.LENGTH_SHORT).show();
                          }

//                            name.setError("Name should not be empty");
//                            pan.setError("Pan number should be exactly of 10 digit");

//                            name.setError("Name should not be empty");

                           // pan.setError("Pan number should be exactly of 10 digit");

//                          if(Name.isEmpty()){
//                              name.setError("Name should not be empty");
//                          }
//                          if(Pan.isEmpty() || pan.length() !=10){
//                              pan.setError("Pan number should be exactly of 10 digit");
//                          }
//                            Toast.makeText(AccountFormActivity.this, "Fields should not be empty", Toast.LENGTH_SHORT).show();

                        }else{

                        new PostForm().execute();
                    }

                }else if(name.getText().toString().isEmpty() || pan.getText().toString().isEmpty() || name.getText().length() <=2 || Pan.length() !=10 || !Mobile.isEmpty() &&  Mobile.length() !=10)
                    {
                        if(Name.isEmpty()){
                            name.setError("Name should not be empty");

                        }else  if(!Mobile.isEmpty() &&  Mobile.length() !=10){
                            mobile.setError("Phone number should be of exactly of 10 digit");
                            // Toast.makeText(AccountFormActivity.this, "Phone number should be of exactly of 10 digit", Toast.LENGTH_SHORT).show();
                        }
                        if(Pan.isEmpty() || pan.length() !=10){
                            pan.setError("Pan number ");
                            if(!Mobile.isEmpty() &&  Mobile.length() !=10){
                                mobile.setError("Phone number should be of exactly of 10 digit");
                                // Toast.makeText(AccountFormActivity.this, "Phone number should be of exactly of 10 digit", Toast.LENGTH_SHORT).show();
                            }
                        }else    if(!Mobile.isEmpty() &&  Mobile.length() !=10){
                            mobile.setError("Phone number should be of exactly of 10 digit");
                            // Toast.makeText(AccountFormActivity.this, "Phone number should be of exactly of 10 digit", Toast.LENGTH_SHORT).show();
                        }
                        if( pan.length() !=10){
                            pan.setError("Pan number should be exactly of 10 digit");
                            if(!Mobile.isEmpty() &&  Mobile.length() !=10){
                                mobile.setError("Phone number should be of exactly of 10 digit");
                                // Toast.makeText(AccountFormActivity.this, "Phone number should be of exactly of 10 digit", Toast.LENGTH_SHORT).show();
                            }
                        }else  if(!Mobile.isEmpty() &&  Mobile.length() !=10){
                            mobile.setError("Phone number should be of exactly of 10 digit");
                            // Toast.makeText(AccountFormActivity.this, "Phone number should be of exactly of 10 digit", Toast.LENGTH_SHORT).show();
                        }
                    }else{
//                        dbHelper = new DBHelper(AccountFormActivity.this, "ihisaab_offline.sqlite", null, 1);
//int id, String Name, String Email , int Mobile,String Location , String Landmark ,  String PAN , String GST,String Customer_id , String Image_address;
//                        Boolean result_of_acc = true;
                                dbHelper.queryData("CREATE TABLE IF NOT EXISTS ACCOUNT(Id INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR NOT NULL, EMAIL VARCHAR NOT NULL, MOBILE INTEGER, LOCATION  VARCHAR,LANDMARK VARCHAR,PAN VARCHAR ,GST VARCHAR,CUSTOMER_ID VARCHAR,IMAGE_ADDRESS VARCHAR, CUSTOMER_CODE VARCHAR)");
                    dbHelper.queryData("CREATE TABLE IF NOT EXISTS ACCOUNT_USER_CODE(Id INTEGER PRIMARY KEY AUTOINCREMENT, CUSTOMER_CODE VARCHAR)");
                            Intent intent_offline = new Intent(AccountFormActivity.this,MainActivity.class);
                            Date cus_date = new Date();
                            String cut_name = Name.substring(0,4);
                         // int month = cus_date.getMonth();
                            String year = String.valueOf(cus_date.getYear());
                            int mn_cus = cus_date.getMonth() +1;
                            String cus_code = cut_name.concat("0"+mn_cus).concat(""+year.substring(1,3));
                            int getsize = names_in_db.size();

                        //    int mobile_int = Integer.parseInt(mobile.getText().toString());
                            String mobile_int = mobile.getText().toString();
//                            try{
//                               // dbsq = new SQLiteDatabase();
//                                String sql = "SELECT * FROM ACCOUNT WHERE Name = "+Name;
//                                Log.e("Sql is", ""+sql);
////                                Cursor for_check_name = dbHelper.getData("SELECT * FROM ACCOUNT WHERE Name ="+Name.trim().toString());
//                                Cursor for_check_name = dbHelper.getData(sql);
//                               // Cursor for_check_name = dbsq.rawQuery("SELECT * FROM ACCOUNT WHERE Name = ?",new String[]{String.valueOf(Name)});
//                                int get_count = for_check_name.getCount();
//                                if(get_count >=0){
//                                    Toast.makeText(AccountFormActivity.this, "Duplicate names found :"+get_count, Toast.LENGTH_SHORT).show();
//                                    Log.e("Duplicate names found :",""+get_count);
//                                }
//                            }catch (SQLException s){
//                                Log.e("SQL expe","No Accounts found");
//
//                            }

                            Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    Cursor cursor = dbHelper.getData("SELECT * FROM ACCOUNT");

                            // get all data from sqlite
                             int add_num;
//                             acc_list.clear();
                            while (cursor.moveToNext() ) {
                                int id = cursor.getInt(0);
                                String name_of = cursor.getString(1);
                                if(name_of.equals(Name)){
                                    names_in_db.addLast(""+Name);
                                    Log.e("Duplicate data found ","of"+Name);
                                    Log.e("Duplicate data count ","of"+names_in_db.size());
                                    sp = new StringBuilder();
                                    if(sp == null){
                                        sp = sp.append(1);
                                    }else{
                                        offline_sq = names_in_db.size()+1;

                                        sp.append(cus_code).append("-").append(offline_sq);
                                    }
                                    Log.e("Duplicate data count ","of"+offline_sq);
                                }
                                String email = cursor.getString(2);
                                String mobile = cursor.getString(3);
                                String location = cursor.getString(4);
                                String landmark = cursor.getString(5);
                                String Pan_to = cursor.getString(6);
                                String gst = cursor.getString(7);
                                String cust_em = cursor.getString(8);
                                String img_to = cursor.getString(9);
                                 cus_to = cursor.getString(10);

                                intent_offline.putExtra("id_of",id);
                                intent_offline.putExtra("Img_name",img_to);
                                Log.e("Image before",""+img_to);
                                intent_offline.putExtra("Nam_name",name_of);
                                intent_offline.putExtra("Em_name",email);
                                intent_offline.putExtra("Mob_name",mobile);
                                intent_offline.putExtra("Loc_name",location);
                                intent_offline.putExtra("Lan_name",landmark);
                                intent_offline.putExtra("Gst_name",gst);
                                intent_offline.putExtra("Cust_name",cust_em);
                                intent_offline.putExtra("Pan_name",Pan_to);
                                intent_offline.putExtra("Cus_id",cus_to);

                             //   byte[] image = cursor.getBlob(3);
                                Toast.makeText(AccountFormActivity.this, " customer id is "+cus_to+" pan is "+Pan_to+" name is "+name_of, Toast.LENGTH_LONG).show();
//                                acc_list.add(new Account_sqldb(Name,Email,mobile_int,Location,Landmark,Pan,Gst,Customeremail));
                            //    Log.e("Customer code is : ",""+cus_to);
                            }
              dbHelper.insertDatatoAccount_user( cus_code+"-"+1);
                    if(sp == null && names_in_db.size()<=0){
                       // intent_offline.putExtra("id_of",id);
//                        intent_offline.putExtra("Img_name",img_to);
//                        Log.e("Image before",""+img_to);
                        intent_offline.putExtra("Nam_name",name.getText().toString());
                        intent_offline.putExtra("Em_name",email.getText().toString());
                        intent_offline.putExtra("Mob_name" ,mobile.getText().toString());
                        intent_offline.putExtra("Loc_name",location.getText().toString());
                        intent_offline.putExtra("Lan_name",landmark.getText().toString());
                        intent_offline.putExtra("Gst_name",gst.getText().toString());
                        intent_offline.putExtra("Cust_name",cut_name);
                        intent_offline.putExtra("Pan_name",pan.getText().toString());
                        intent_offline.putExtra("Cus_id",cus_to);
                        dbHelper.insertDatatoAccount(name.getText().toString(), email.getText().toString(), mobile.getText().toString(), location.getText().toString(), landmark.getText().toString(), pan.getText().toString(), gst.getText().toString(), cut_name, "dummy", cus_code+"-"+1);
                        dbHelper.insertDatatoAccount_user(cus_code+"-"+1);
                       // dbHelper.insertDatatoAccount(Name, Email, mobile_int, Location, Landmark, Pan, Gst, Customeremail, "dummy", cus_code+"-"+1);
                        dbHelper.queryData("CREATE TABLE IF NOT EXISTS ACCOUNT_USER_CODE(Id INTEGER PRIMARY KEY AUTOINCREMENT, CUSTOMER_CODE VARCHAR)");
                        intent_offline.putExtra("cus", cus_code+"-"+1);
                        dbHelper.insertDatatoAccount_user( cus_code+"-"+1);
                        startActivity(intent_offline);
                        finish();

                    }else{
                        dbHelper.insertDatatoAccount(Name, Email, mobile_int, Location, Landmark, Pan, Gst, Customeremail, "dummy", cus_code+"-"+sp);
                        intent_offline.putExtra("cus", ""+sp);
                        startActivity(intent_offline);
                        finish();

                    }

                            Toast.makeText(AccountFormActivity.this, "no internet", Toast.LENGTH_SHORT).show();
                            Toast.makeText(AccountFormActivity.this, ""+dbHelper.getDatabaseName(), Toast.LENGTH_SHORT).show();
//                            Toast.makeText(AccountFormActivity.this, "month is"+cus_date.getMonth(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(AccountFormActivity.this, ""+cus_code, Toast.LENGTH_SHORT).show();




                    }
//                    dbHelper = new DBHelper(AccountFormActivity.this, "FoodDB.sqlite", null, 1);
////int id, String Name, String Email , int Mobile,String Location , String Landmark ,  String PAN , String GST,String Customer_id , String Image_address;
//                  Boolean result_of_acc =  dbHelper.queryData("CREATE TABLE IF NOT EXISTS ACCOUNT(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR NOT NULL, Email VARCHAR NOT NULL, Mobile INTEGER, Location VARCHAR,Landmark VARCHAR,PAN VARCHAR NOT NULL,GST VARCHAR,Customer_id VARCHAR , Image_address VARCHAR)");
//                   if(result_of_acc){
//                       Toast.makeText(AccountFormActivity.this, "no internet", Toast.LENGTH_SHORT).show();
//
//                   }


            }
        }
        );
    }

    private void getmail() {

    }
//end
    public class PostForm extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {
          //  dialog = new ProgressDialog(AccountFormActivity.this);
          //  dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.com/Api/accounting");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("name", Name);
                postDataParams.put("email", Email);
                postDataParams.put("mobile", Mobile);
                postDataParams.put("location", Location);
                postDataParams.put("landmark", Landmark);
                postDataParams.put("pan", Pan);
                postDataParams.put("gst", Gst);
                postDataParams.put("cust_email",Customeremail);
//
//        // Getting all registered Google Accounts;
//        // Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
//
//        // Getting all registered Accounts;
//        Account[] accounts = AccountManager.get(this).getAccounts();


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
              //  dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    String response = jsonObject.getString("response");
                    String message = jsonObject.getString("message");
                    JSONObject dt = jsonObject.getJSONObject("data");
                    String cus_code = dt.getString("cust_code");
                 //   sessionManager.serverEmailLogin(cus_code);
//                    store = cus_code.toString();
//                    editor = sharedpreferences.edit();
//                    editor.putString("cus_code" , store);
                    Toast.makeText(AccountFormActivity.this, ""+cus_code, Toast.LENGTH_SHORT).show();
                    Log.e("Your Customer code is", cus_code);
                    if (response.equalsIgnoreCase("True")) {
                        Toast.makeText(AccountFormActivity.this, "Thanks for registering", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AccountFormActivity.this,MainActivity.class);
                        intent.putExtra("cus", cus_code);
                        startActivity(intent);
                       finish();
                    }


                } catch (JSONException e) {
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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {

                    //   Toast.makeText(GetMailActivity.this, "got it", Toast.LENGTH_LONG).show();
                } else {
                    checkPermission(wantPermission);
                    Toast.makeText(AccountFormActivity.this, "  Permissions Denied.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void checkPermission(String wantPermission) {
    }

//    //--------------
//    private void checkAndRequestPermissions() {
//        if (!hasPermissions(this, PERMISSIONS)) {
//            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
//        }
//    }
    @Override
    public void onBackPressed() {
        Intent tomainradio  = new Intent(AccountFormActivity.this , MainRadioActivity.class);
        startActivity(tomainradio);
        finish();
        super.onBackPressed();
    }
}

