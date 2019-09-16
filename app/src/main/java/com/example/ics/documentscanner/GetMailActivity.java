package com.example.ics.documentscanner;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ics.documentscanner.Sharedprefrence.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Pattern;

import static com.example.ics.documentscanner.RuntimePermission.hasPermissions;

public class GetMailActivity extends AppCompatActivity {
    private static final int PERMISSION_ALL = 1 ;
    ProgressDialog progressDialog;
    private String TAG = "AccountsActivityTAG";
    private String wantPermission = Manifest.permission.GET_ACCOUNTS;
    private Activity activity = GetMailActivity.this;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private String get_email;
    TextView email_address_view;
    private CardView ver_f_btn;
    private EditText num_edit;
    String put_num;
    private SessionManager sessionManager;
    private  TextView emp_no;
//    String URLString = "https://www.spellclasses.co.in/DM/Api/insert_customer";
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String store;
    String[] PERMISSIONS = {Manifest.permission.CAMERA,
            Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_mail);
        ver_f_btn = (CardView) findViewById(R.id.ver_f_btn);
        num_edit = (EditText) findViewById(R.id.num_edit);
        put_num = num_edit.getText().toString();
       checkAndRequestPermissions();

        sessionManager=new SessionManager(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if(sharedpreferences.getString("Name" ,store ) != null){
            Intent sp = new Intent(GetMailActivity.this , MainRadioActivity.class);
            startActivity(sp);
        }
        ver_f_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num_edit.getText().toString().isEmpty() == false  && num_edit.getText().length() == 10  ) {
//                    store = num_edit.getText().toString();
//                    editor = sharedpreferences.edit();
//                    editor.putString("Name" , store);
//                    editor.commit();
//
//                 //   Toast.makeText(GetMailActivity.this, "Filds are empty", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(GetMailActivity.this, "" + put_num, Toast.LENGTH_SHORT).show();
//                    getEmails(num_edit.getText().toString());
//                   checkPermission(wantPermission);
//                    Intent go_to_main = new Intent(GetMailActivity.this, MainRadioActivity.class);
//                    go_to_main.putExtra("mob_to_ret",put_num);
//                    startActivity(go_to_main);
                    //  Toast.makeText(GetMailActivity.this, ""+, Toast.LENGTH_SHORT).show();
                    //new Post_number().execute();
                   // startActivity(go_to_main);
                   // finish();
//                    if (!checkPermission(wantPermission)) {
//                      //  requestPermission(wantPermission);
//                        checkAndRequestPermissions();
//                    } else {
//                        checkPermission(wantPermission);
//                        //getEmails();
//                    }
                    store = num_edit.getText().toString();
                    editor = sharedpreferences.edit();
                    editor.putString("Name" , store);
                    editor.commit();
                    Toast.makeText(GetMailActivity.this, " Permissions Granted", Toast.LENGTH_LONG).show();
                    Intent go_from_oreqresult = new Intent(GetMailActivity.this , MainRadioActivity.class);
                    startActivity(go_from_oreqresult);
                    finish();
                } else {
//                    checkPermission(wantPermission);
//                    requestPermission(wantPermission);
                    emp_no = (TextView)findViewById(R.id.emp_no);
                    emp_no.setText("Please check the number of digits in the field where you have entered the mobile number");
                  //  Toast.makeText(GetMailActivity.this, "Please specify your mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        if (!checkPermission(wantPermission)) {
//            requestPermission(wantPermission);
//        } else {
//            //getEmails();
//        }

        //checkAndRequestPermissions();

    }
//
//    private void getEmails(String mobile_number) {
//        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
//
//        // Getting all registered Google Accounts;
//        // Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
//
//        // Getting all registered Accounts;
//        Account[] accounts = AccountManager.get(this).getAccounts();
//
//        for (Account account : accounts) {
//            if (emailPattern.matcher(account.name).matches()) {
//                Log.d(TAG, String.format("%s - %s", account.name, account.type));
//                email_address_view = (TextView) findViewById(R.id.email_address_view);
//                email_address_view.setText(account.name);
//                sessionManager.serverEmailLogin(account.name,mobile_number);
////                Intent go_to_main = new Intent(GetMailActivity.this, MainRadioActivity.class);
////                go_to_main.putExtra("mob_to_ret",put_num);
////                go_to_main.putExtra("email_to_ret",account.name);
////                //  Toast.makeText(GetMailActivity.this, ""+, Toast.LENGTH_SHORT).show();
////                //new Post_number().execute();
////                startActivity(go_to_main);
////                finish();
////                if (account.name != null && mobile_number != null) {
////                    progressDialog = new ProgressDialog(GetMailActivity.this);
////                    progressDialog.setMessage(""+ account.name); // Setting Message
////                    progressDialog.setTitle("Verifying"); // Setting Title
////                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
////                    progressDialog.show(); // Display Progress Dialog
////                    progressDialog.setCancelable(false);
////                    new Thread(new Runnable() {
////                        public void run() {
////                            try {
////                                Thread.sleep(5000);
////                            } catch (Exception e) {
////                                e.printStackTrace();
////                            }
////                            progressDialog.dismiss();
////                            if (num_edit.getText().toString().isEmpty() == false && num_edit.getText().length() == 10) {
////                                Intent go_to_main = new Intent(GetMailActivity.this, MainRadioActivity.class);
////                                go_to_main.putExtra("mob_to_ret",put_num);
////                                go_to_main.putExtra("email_to_ret",put_num);
////                              //  Toast.makeText(GetMailActivity.this, ""+, Toast.LENGTH_SHORT).show();
////                                //new Post_number().execute();
////                                startActivity(go_to_main);
////                                finish();
////
////
////
////                             //  startActivity(go_to_main);
////                            }
////
////                        }
////                    }).start();
////                } else {
////                    Toast.makeText(GetMailActivity.this, "Field is empty", Toast.LENGTH_SHORT).show();
////
////                }
//            }
//
//        }
//    }


    private boolean checkPermission(String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(activity, permission);
            if (result == PackageManager.PERMISSION_GRANTED && num_edit.getText().length() == 10) {
//                store = num_edit.getText().toString();
//                editor = sharedpreferences.edit();
//                editor.putString("Name" , store);
//                editor.commit();
            //    Intent go_to_main = new Intent(GetMailActivity.this, MainRadioActivity.class);
           //     go_to_main.putExtra("mob_to_ret",put_num);
//                //  Toast.makeText(GetMailActivity.this, ""+, Toast.LENGTH_SHORT).show();
//                //new Post_number().execute();
             //   startActivity(go_to_main);
//                finish();
               // getEmails(num_edit.getText().toString());
                return true;
            } else {
               // Toast.makeText(GetMailActivity.this, "Either you have putted the wrong number or you have denied the permission ", Toast.LENGTH_LONG).show();
                //requestPermission(permission);
                //checkAndRequestPermissions();
                return false;
            }
        } else {
            return true;
        }
    }
//
//    private void requestPermission(String permission) {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(GetMailActivity.this, permission)) {
//            Toast.makeText(GetMailActivity.this, "Get account permission allows us to get your email", Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(GetMailActivity.this, "You have denied the permission ", Toast.LENGTH_LONG).show();
//        }
//        ActivityCompat.requestPermissions(GetMailActivity.this, new String[]{permission}, PERMISSION_REQUEST_CODE);
//    }
//
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2]== PackageManager.PERMISSION_GRANTED && grantResults[3]== PackageManager.PERMISSION_GRANTED && grantResults[4]== PackageManager.PERMISSION_GRANTED) {
//                    if (num_edit.getText().toString().isEmpty() == false && num_edit.getText().length() ==10) {
////                        store = num_edit.getText().toString();
////                        editor = sharedpreferences.edit();
////                        editor.putString("Name" , store);
////                        editor.commit();
                    Pattern emailPattern = Patterns.EMAIL_ADDRESS;

                    Account[] accounts = AccountManager.get(GetMailActivity.this).getAccounts();


                    for (Account account : accounts) {
                        if (emailPattern.matcher(account.name).matches()) {
                            Log.d("Getting Account", String.format("%s - %s", account.name, account.type));

                            Toast.makeText(this, "Your gmail is "+ account.name, Toast.LENGTH_SHORT).show();
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
                     Toast.makeText(GetMailActivity.this, " Permissions Granted", Toast.LENGTH_LONG).show();
////                        Intent go_from_oreqresult = new Intent(GetMailActivity.this , MainRadioActivity.class);
////                        startActivity(go_from_oreqresult);
//
//                        //   Toast.makeText(GetMailActivity.this, "Filds are empty", Toast.LENGTH_SHORT).show();
//                       // Toast.makeText(GetMailActivity.this, "" + put_num, Toast.LENGTH_SHORT).show();
//                     //   Toast.makeText(GetMailActivity.this, "" + put_num, Toast.LENGTH_SHORT).show();
//                      //  Toast.makeText(GetMailActivity.this, "Feilds are empty", Toast.LENGTH_SHORT).show();
//                     //   getEmails(num_edit.getText().toString());
//                    }
                 //   Toast.makeText(GetMailActivity.this, "got it", Toast.LENGTH_LONG).show();
                } else {
                    checkAndRequestPermissions();
                    Toast.makeText(GetMailActivity.this, "  Permissions Denied.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

//
//    private class Post_number extends AsyncTask<Void, Void, Void> {
//        String Info;
//        ProgressDialog pDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //   pDialog = ProgressDialog.show(getBaseContext(),"","Please Wait..Connecting to the server",true);
//            //  pDialog.setCancelable(true);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            HashMap<String, Object> number_to_post = new HashMap<>();
//            String mobile = num_edit.getText().toString();
//            number_to_post.put("mobile", mobile);
//            Connection connection = new Connection();
//            String result = connection.sendpostRequst(URLString, number_to_post);
//            System.out.println(result);
//            try {
//                JSONObject object = new JSONObject(result);
//                Info = object.getString("info");
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
////            pDialog.dismiss();
//            //  Toast.makeText(GetMailActivity.this, Info, Toast.LENGTH_LONG).show();
//            super.onPostExecute(aVoid);
//        }
//    }


    //-------------------------------------------------

    private void checkAndRequestPermissions() {
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    @Override
    public void onBackPressed() {

        System.exit(0);
        super.onBackPressed();
    }
}


