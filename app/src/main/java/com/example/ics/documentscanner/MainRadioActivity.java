package com.example.ics.documentscanner;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ics.documentscanner.Sharedprefrence.AppPrefences;
import com.example.ics.documentscanner.Sharedprefrence.SessionManager;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;

import static com.example.ics.documentscanner.MainActivity.GALLERY_DIRECTORY_NAME;

public class MainRadioActivity extends AppCompatActivity  {
    Dialog dialog1 ;
     Dialog dialog_acc ;
    Dialog dialog_final_acc ;
    Button account_rd, income_tx;
    CardView card1, card2;
    ImageView acc_btn, income_btn;
    PopupWindow popupWindow;
    EditText customer_edit_id, customerId;
    RelativeLayout popup_linear;
    RadioGroup clientType;
    RadioButton newClient, existingClient;
    TextView btn_ok, bt_ok1, bt_ok, btn_ok1;
    SharedPreferences sharedpreferences_cus;
    public static final String cus_preferences = "cus_code";
    public static final String Name = "cus_key";
    LinkedList<String> customer_images = new LinkedList<String>();
    ImageButton exit_from;
    String Image_address , Image_pan;
    String cs_get_in;
    EditText customerId_tax;
    String CustomerId, cs_get;
    RadioGroup clientType1;
    RadioButton newClient1;
    RadioButton existingClient1;
    String cust_code = "";
    private String pan_no;
    File image_image;
    File file_delete;
    String Name_from_db,Email,Location,Mobile,Landmark,Pan,Gst ,Customeremail;
    String Name_from_image,Customer_code,Image_n;
    String  GALLERY_DIRECTORY_NAME_OFFLINE = "Account";
    // String cust_code = "";
 Boolean internet_check;
 private DBHelper dbHelper,dbHelperimage;

    @Override
    public void onBackPressed() {
     //   System.exit(0);
//        finish();
//        moveTaskToBack(true);
        Toast.makeText(this, "Please exit from above exit button", Toast.LENGTH_SHORT).show();
        Intent to_be_back = new Intent(MainRadioActivity.this , MainRadioActivity.class);
        startActivity(to_be_back);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainradio_activity);
        internet_check = Connectivity.isNetworkAvailable(MainRadioActivity.this);
        dialog1  = new Dialog(MainRadioActivity.this);
        dialog_acc  = new Dialog(MainRadioActivity.this);
        //++++++++++++++++++++++++++++++++++++For shared reference+++++++++++++++++++++++++++++++++++++++++++++
        exit_from = (ImageButton) findViewById(R.id.exit_from);
        exit_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String root =  Environment.getExternalStorageDirectory().toString();
                File file_delete = new File(root + "/Pictures/"+"Account");
                Log.e("Storage at ",""+file_delete);
                if(Connectivity.isNetworkAvailable(MainRadioActivity.this)){
                    delete_thegodamn_files(file_delete);
                }else{
                    Log.e("Images will be","Saved");
                }
           //  MainRadioActivity.this.finish();
//             System.exit(0);
                moveTaskToBack(true);
            }
        });

        check_forsyn();
//        sharedpreferences_cus = getSharedPreferences(cus_preferences,
//                Context.MODE_PRIVATE);
//        editor = sharedpreferences_cus.edit();
//        Id = getIntent().getStringExtra("cus_code");
//        editor.putString(cus_preferences , Id);
//        editor.commit();
        //   final Context c = MainRadioActivity.this;
        customer_edit_id = (EditText) findViewById(R.id.customer_edit_id);
        account_rd = (Button) findViewById(R.id.account_rd);
        card1 = (CardView) findViewById(R.id.card1);

        card2 = (CardView) findViewById(R.id.card2);

        income_tx = (Button) findViewById(R.id.income_tx);
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainRadioActivity.this, "This Feature will be coming soon", Toast.LENGTH_SHORT).show();
//

                dialog1.setContentView(R.layout.custom_layout_income);
                dialog1.setTitle("Please choose your Type.");
                // set the custom dialog components - text, image and button
                clientType1 = (RadioGroup) dialog1.findViewById(R.id.clientType1);
                newClient1 = (RadioButton) dialog1.findViewById(R.id.newClient1);
                existingClient1 = (RadioButton) dialog1.findViewById(R.id.existingClient1);
                btn_ok1 = (TextView) dialog1.findViewById(R.id.btn_ok1);
                btn_ok1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final int k = clientType1.getCheckedRadioButtonId();
                        Log.e("ddddddddd", k + "");
                        if (R.id.newClient1 == k) {
                            dialog1.dismiss();
                            Intent intent = new Intent(MainRadioActivity.this, FormActivity.class);
                           // Toast.makeText(MainRadioActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                        } else if (R.id.existingClient1 == k) {
                            dialog1.dismiss();
                            //===============================dialog 2
                            final Dialog dialog = new Dialog(MainRadioActivity.this);
                            dialog1.setContentView(R.layout.existclientdialogincome);
                            dialog1.setTitle("Please choose your Type.");

                            // set the custom dialog components - text, image and button
                            customerId_tax = (EditText) dialog1.findViewById(R.id.customerId_tax);
                            bt_ok = (TextView) dialog1.findViewById(R.id.bt_ok);

                            bt_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                     cs_get_in = customerId_tax.getText().toString();

                                    if (!customerId_tax.getText().toString().isEmpty()) {
                                        if (Connectivity.isNetworkAvailable(MainRadioActivity.this)) {
                                            new PostTaxExistingCustomer().execute();
                                        }else{
                                            Toast.makeText(MainRadioActivity.this, "Please check the internet ", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(MainRadioActivity.this, "PAN Number is  null ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            dialog1.show();
                            //================================
                        }
                    }
                });
                dialog1.show();
            }
        });


        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for dialog account
                dialog_acc.setContentView(R.layout.custom_dialog);
                dialog_acc.setTitle("Please choose your Type.");
                // set the custom dialog components - text, image and button
                clientType = (RadioGroup) dialog_acc.findViewById(R.id.clientType);
                newClient = (RadioButton) dialog_acc.findViewById(R.id.newClient);
                existingClient = (RadioButton) dialog_acc.findViewById(R.id.existingClient);
                btn_ok = (TextView) dialog_acc.findViewById(R.id.btn_ok);

                //   Toast.makeText(MainRadioActivity.this, "Your ID is "+Id, Toast.LENGTH_SHORT).show();

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final int i = clientType.getCheckedRadioButtonId();
                        Log.e("ddddddddd", i + "");
                        if (R.id.newClient == i) {
                            dialog_acc.dismiss();
                            Intent intent = new Intent(MainRadioActivity.this, AccountFormActivity.class);
                            startActivity(intent);
                            finish();

                        } else if (R.id.existingClient == i) {
                            dialog_acc.dismiss();
                            //===============================final  dialog for account
                             dialog_final_acc = new Dialog(MainRadioActivity.this);
                            dialog_final_acc.setContentView(R.layout.existclientdialog);
                            dialog_final_acc.setTitle("Please choose your Type.");
                             onStop();
                            // set the custom dialog components - text, image and button
                            customerId = (EditText) dialog_final_acc.findViewById(R.id.customerId);
                            bt_ok1 = (TextView) dialog_final_acc.findViewById(R.id.bt_ok1);

                            bt_ok1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    cs_get = customerId.getText().toString();

                                    if (!customerId.getText().toString().isEmpty()) {
                                        if (Connectivity.isNetworkAvailable(MainRadioActivity.this)) {
                                            new PostExistingCustomer().execute();

                                        } else{
                                            try {
                                                dbHelper = new DBHelper(MainRadioActivity.this, "ihisaab_offline.sqlite", null, 1);
                                                dbHelper.queryData("CREATE TABLE IF NOT EXISTS ACCOUNT_USER_CODE(Id INTEGER PRIMARY KEY AUTOINCREMENT, CUSTOMER_CODE VARCHAR)");
                                                Cursor check_cursor = dbHelper.getData("SELECT * FROM ACCOUNT");
                                                Cursor check_cursor_image = dbHelper.getData("SELECT * FROM ACCOUNT_IMAGE");
                                                while (check_cursor.moveToNext()) {
                                                    // int id = check_cursor.getInt(0);
//                                                String name_of = cursor.getString(1);

                                                    String Pan_to = check_cursor.getString(7);
                                                    String cus_to = check_cursor.getString(10);
                                                    if (cus_to.equals(cs_get.toString())) {

                                                        Toast.makeText(MainRadioActivity.this, "Customer found ", Toast.LENGTH_SHORT).show();
                                                        Intent offline_user = new Intent(MainRadioActivity.this, MainActivity.class);
                                                        offline_user.putExtra("offline_us_cd", cus_to);
//                                                    dbHelper.insertDatatoAccount_user( cus_to);
                                                        startActivity(offline_user);
                                                        //    finish();
                                                    } else {
                                                        //    Toast.makeText(MainRadioActivity.this, " Invalid Customer", Toast.LENGTH_LONG).show();
                                                    }
                                                    //   byte[] image = cursor.getBlob(3);
                                                    //   Toast.makeText(MainRadioActivity.this, " id is "+id+" pan is "+Pan_to+" name is "+name_of, Toast.LENGTH_LONG).show();
//                                acc_list.add(new Account_sqldb(Name,Email,mobile_int,Location,Landmark,Pan,Gst,Customeremai
// l));
                                                    Log.e("Customer code is : ", "" + cus_to);
                                                }
                                                try {

                                                    Cursor check_cursor_user = dbHelper.getData("SELECT * FROM ACCOUNT_USER_CODE");
                                                    while (check_cursor_user.moveToNext() && check_cursor_user.getCount() >= 0) {
                                                        String cus_to_user = check_cursor_user.getString(1);
                                                        if (cus_to_user.equals(cs_get.toString())) {
                                                            Toast.makeText(MainRadioActivity.this, "Customer found ", Toast.LENGTH_SHORT).show();
                                                            Intent offline_user = new Intent(MainRadioActivity.this, MainActivity.class);
                                                            offline_user.putExtra("offline_us_cd", cus_to_user);
                                                            //       dbHelper.insertDatatoAccount_user( cus_to_user);
                                                            startActivity(offline_user);
                                                            //dialog.dismiss();
                                                           finish();
                                                        } else {
                                                            //     Toast.makeText(MainRadioActivity.this, " Invalid Customer code", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                } catch (SQLException e) {

                                                }
                                            }catch(SQLiteException e){

                                            }
                                        }
                                     //else finish
                                    } else {
                                        Toast.makeText(MainRadioActivity.this, "Please enter the customer code", Toast.LENGTH_SHORT).show();
                                    }

//                                    finish();

                                }
                            }
                            );
                            dialog_final_acc.show();

//                            finish();

                            //================================
                        }
                    }
                });

                    dialog_acc.show();
            }
        });

    }
    private Boolean delete_thegodamn_files(File file_delete) {
        if(file_delete.isDirectory()){
            String[] children = file_delete.list();
            for(int i =0; i<children.length; i++){
                Boolean success = delete_thegodamn_files(new File(file_delete , children[i]));
                Log.e("File deleted","name was "+children[i].toString());
                if (!success){
                    return  false;
                }
            }

        }
        return file_delete.delete();
    }

    private void check_forsyn() {
        if(Connectivity.isNetworkAvailable(MainRadioActivity.this)) {
            dbHelper = new DBHelper(MainRadioActivity.this, "ihisaab_offline.sqlite", null, 1);
//            dbHelperimage = new DBHelper(MainRadioActivity.this , "ihisaab_offline.sqlite" , null , 1);
//            Cursor image_cursor = dbHelperimage.getData("SELECT * FROM ACCOUNT_IMAGE");
//            while(image_cursor.moveToNext() && image_cursor.getCount()>=0){
//                Image_address = image_cursor.getString(3);
//                Image_pan = image_cursor.getString(2);
//                File  file = new File(Image_address);
//            }
            try {
                Cursor check_cursor_ag = dbHelper.getData("SELECT * FROM ACCOUNT");
                Cursor image_cursor_image = dbHelper.getData("SELECT * FROM ACCOUNT_IMAGE");
               // Cursor image_cursor_user = dbHelper.getData("SELECT * FROM ACCOUNT_USER_CODE");
                if (check_cursor_ag.moveToNext() == false) {
                    while (image_cursor_image.moveToNext() && image_cursor_image.getCount() >= 0) {
                        Name_from_image = image_cursor_image.getString(1);
                        Image_n = image_cursor_image.getString(2);
                        image_image = new File("/storage/emulated/0/Pictures/Account/" + Image_n);
                        Customer_code = image_cursor_image.getString(3);
//                    customer_images.addLast(""+customer_images);
                                new OnlyImageupload(image_image, Customer_code).execute();



                    }
                }

            } catch (SQLException e) {

            }

            try {
                Cursor check_cursor = dbHelper.getData("SELECT * FROM ACCOUNT");
            while (check_cursor.moveToNext() && check_cursor.getCount() >=0) {
                int id = check_cursor.getInt(0);
                Log.e("id  is ", "" + id);
                Name_from_db = check_cursor.getString(1);
                Email = check_cursor.getString(2);
                Mobile = check_cursor.getString(3);
                Location = check_cursor.getString(4);
                Landmark = check_cursor.getString(5);
                Pan = check_cursor.getString(6);
                Gst = check_cursor.getString(7);
                Customeremail = check_cursor.getString(8);
                Image_address = check_cursor.getString(9);
                String customer_c_c = check_cursor.getString(10);
                File image = new File("/storage/emulated/0/Pictures/Account/" + Image_address);
                Log.e("Image address is ", "" + Image_address);

                if (image.exists()) {
                    new Post_fromoff_to_on(id, Name_from_db, Email, Mobile, Location, Landmark, Pan, Gst, Customeremail, image).execute();
                }
                try{
                    Cursor image_cursor = dbHelper.getData("SELECT * FROM ACCOUNT_IMAGE");
                    while (image_cursor.moveToNext() && image_cursor.getCount()>=0){
                        Name_from_image = image_cursor.getString(1);
                        Image_n = image_cursor.getString(2);
                        image_image = new File("/storage/emulated/0/Pictures/Account/"+Image_n);
                        Customer_code = image_cursor.getString(3);
                        customer_images.addLast(""+customer_images);
                        if (Customer_code.equals(customer_c_c)){
                            new OnlyImageupload( image_image , Customer_code).execute();
                        }
                    }

                }catch( SQLException e){

                }
            }


//                                                String Pan_to = cursor.getString(7);
//                String cus_to = check_cursor.getString(9);
//                if(cus_to.equals(cs_get.toString())){
//                    Toast.makeText(MainRadioActivity.this, "Customer found ", Toast.LENGTH_SHORT).show();
//
//                }else{
//                    Toast.makeText(MainRadioActivity.this, " Invalid Customer", Toast.LENGTH_LONG).show();
//                }
                //   byte[] image = cursor.getBlob(3);
                //   Toast.makeText(MainRadioActivity.this, " id is "+id+" pan is "+Pan_to+" name is "+name_of, Toast.LENGTH_LONG).show();
//                                acc_list.add(new Account_sqldb(Name,Email,mobile_int,Location,Landmark,Pan,Gst,Customeremail));
//                Log.e("Customer code is : ", "" + cus_to);
            }catch (SQLException e){
                Toast.makeText(this, "No data in offline mode", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private Boolean getcheckoninternet() {
        return true;
    }

    //============================================================

    public class PostExistingCustomer extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(MainRadioActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.com/Api/checkaccountuser");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("cust_code", cs_get);

                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds*/);
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
                Log.e("PostPIinsuranceDetails", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    String response = jsonObject.getString("response");
                    if (response.equalsIgnoreCase("true")) {
                        JSONObject data_obj = jsonObject.getJSONObject("data");
                        String id = data_obj.getString("id");
                        String employee_id = data_obj.getString("employee_id");
                        cust_code = data_obj.getString("cust_code");
                        String name = data_obj.getString("name");
                        String email = data_obj.getString("email");
                        String mobile = data_obj.getString("mobile");
                        String location = data_obj.getString("location");
                        String landmark = data_obj.getString("landmark");
                        String pan = data_obj.getString("pan");
                        String gst = data_obj.getString("gst");
                        String status = data_obj.getString("status");
                        String created = data_obj.getString("created");
                        String modified = data_obj.getString("modified");

                        AppPrefences.setCustomerid(MainRadioActivity.this,cust_code);
                        Intent intent = new Intent(MainRadioActivity.this, MainActivity.class);
                        intent.putExtra("cust_code", cust_code);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainRadioActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
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

    //-----------------------------------------------------------------


    public class PostTaxExistingCustomer extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(MainRadioActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.com/Api/checktaxuser");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("pan_no", cs_get_in);

                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds*/);
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
                Log.e("PostPIinsuranceDetails", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    String response = jsonObject.getString("response");
                    if (response.equalsIgnoreCase("true")) {
                        JSONObject data_obj = jsonObject.getJSONObject("data");
                        String id = data_obj.getString("id");
                        String name = data_obj.getString("name");
                        String email = data_obj.getString("email");
                        pan_no = data_obj.getString("pan_no");
                        String adhan_no = data_obj.getString("adhan_no");
                        String mobile = data_obj.getString("mobile");
                        String self_referal_code = data_obj.getString("self_referal_code");
                        String reference_referal_code = data_obj.getString("reference_referal_code");
                        String ret_email = data_obj.getString("ret_email");
                        String ret_mobile = data_obj.getString("ret_mobile");
                        String status = data_obj.getString("status");
                        String created = data_obj.getString("created");

                        Intent intent = new Intent(MainRadioActivity.this, Single_user_act_2.class);
                        intent.putExtra("pan_i", pan_no);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainRadioActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
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


    private class Post_fromoff_to_on extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        String result="";
        int id;
        File image;
        String name_from_db1, email1, mobile1, location1, landmark1,  pan1,  gst1,  customeremail1;
        public Post_fromoff_to_on(int id, String name_from_db, String email, String mobile, String location, String landmark, String pan, String gst, String customeremail, File image) {
            this.id = id;
       this.name_from_db1 = name_from_db;
       this.email1 = email;
       this.mobile1 = mobile;
       this.location1 = location;
       this.landmark1 = landmark;
       this.pan1 = pan;
       this.gst1 = gst;
       this.customeremail1 = customeremail;
       this.image = image;

        }

        protected void onPreExecute() {
            //  dialog = new ProgressDialog(AccountFormActivity.this);
            //  dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

               // URL url = new URL("http://ihisaab.com/ihisaabv2/Api/accounting");
                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);

                JSONObject postDataParams = new JSONObject();
                entity.addPart("name", new StringBody("" + name_from_db1));
                entity.addPart("email", new StringBody("" + email1));
                entity.addPart("mobile", new StringBody("" + mobile1));
                entity.addPart("location", new StringBody("" + location1));
                entity.addPart("landmark", new StringBody("" + landmark1));
                entity.addPart("pan", new StringBody("" + pan1));
                entity.addPart("gst", new StringBody("" + gst1));
                entity.addPart("cust_email", new StringBody(""+customeremail1));
            //   entity.addPart("doc", new FileBody(image));
                result = Utilities.postEntityAndFindJson("http://ihisaab.com/Api/accounting", entity);
//                postDataParams.put("name", name_from_db1);
//                postDataParams.put("email", email1);
//                postDataParams.put("mobile", mobile1);
//                postDataParams.put("location", location1);
//                postDataParams.put("landmark", landmark1);
//                postDataParams.put("pan", pan1);
//                postDataParams.put("gst", gst1);
//                postDataParams.put("cust_email",customeremail1);
//                postDataParams.put("cust_email",customeremail1);
//
//        // Getting all registered Google Accounts;
//        // Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
//
//        // Getting all registered Accounts;
//        Account[] accounts = AccountManager.get(this).getAccounts();


//                Log.e("postDataParams", postDataParams.toString());
//
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(15000  /*milliseconds*/);
//                conn.setConnectTimeout(15000  /*milliseconds*/);
//                conn.setRequestMethod("POST");
//                conn.setDoInput(true);
//                conn.setDoOutput(true);
//
//                OutputStream os = conn.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(
//                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getPostDataString(postDataParams));
//
//                writer.flush();
//                writer.close();
//                os.close();
//
//                int responseCode = conn.getResponseCode();
//
//                if (responseCode == HttpsURLConnection.HTTP_OK) {
//
//                    BufferedReader in = new BufferedReader(new
//                            InputStreamReader(
//                            conn.getInputStream()));
//
//                    StringBuffer sb = new StringBuffer("");
//                    String line = "";
//
//                    while ((line = in.readLine()) != null) {
//
//                        StringBuffer Ss = sb.append(line);
//                        Log.e("Ss", Ss.toString());
//                        sb.append(line);
//                        break;
//                    }
//
//                    in.close();
//                    return sb.toString();
//
//                } else {
//                    return new String("false : " + responseCode);
//                }
            }
            catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

           return result;
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
                    if(result !=null){
                        new OnlyImageupload( image , cus_code).execute();
//                  try{
//
//                      try{
//                          Cursor image_cursor = dbHelper.getData("SELECT * FROM ACCOUNT_IMAGE");
//                          while (image_cursor.moveToNext() && image_cursor.getCount()>=0){
//                              Name_from_image = image_cursor.getString(1);
//                              Image_n = image_cursor.getString(2);
//                              image_image = new File("/storage/emulated/0/Pictures/Account/"+Image_n);
//                              Customer_code = image_cursor.getString(3);
//                              customer_images.addLast(""+customer_images);
//                              if (Customer_code.equals(Customer_code)){
//                                  new OnlyImageupload( image_image , Customer_code).execute();
//                              }
//                          }
//
//                      }catch( SQLException e){
//
//                      }
//                  }

                    }

                    //   sessionManager.serverEmailLogin(cus_code);
//                    store = cus_code.toString();
//                    editor = sharedpreferences.edit();
//                    editor.putString("cus_code" , store);
                    Toast.makeText(MainRadioActivity.this, ""+cus_code, Toast.LENGTH_SHORT).show();
                    Log.e("Your Customer code is", cus_code);

                    if (response.equalsIgnoreCase("True")) {
                      //  new OnlyImageupload(image , cus_code ).execute();
                        dbHelper = new DBHelper(MainRadioActivity.this, "ihisaab_offline.sqlite", null, 1);
//                         dbHelper.queryData("DELETE FROM ACCOUNT WHERE CUSTOMER_CODE="+cus_code);
                        try {
                            dbHelper.deleteData(id);
                            dbHelper.deleteData_for_image(id);
                        }catch(SQLException e){

                        }
                       // dbHelper.deleteData_for_image(id);
                        String root =  Environment.getExternalStorageDirectory().toString();
                        File file_delete = new File("/storage/emulated/0/Pictures/Account/"+image);
                        Log.e("Storage at ",""+file_delete);
                        if(Connectivity.isNetworkAvailable(MainRadioActivity.this)){
//                            showofflinedialog();
                           delete_thegodamn_files(file_delete);
                        }else{
//                            delete_thegodamn_files(file_delete);
                           // Toast.makeText(MainRadioActivity.this, "Please wait deleting scanned images", Toast.LENGTH_SHORT).show();
                         //   Log.e("Images will be","Saved");
                        }

                        // android.os.Process.killProcess(android.os.Process.myPid());
                        finish();
                      //  dbHelper.deleteData(id);
//                        Intent intent = new Intent(MainRadioActivity.this,MainActivity.class);
//                        intent.putExtra("cus", cus_code);
//                      //  startActivity(intent);
//                        finish();
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

        private class Send_again extends  AsyncTask<String, Void, String>{
            String code;
            File im;
            public Send_again(String cus_code, File image)  {
                this.code = cus_code;
                this.im = image;
            }

            @Override
            protected String doInBackground(String... strings) {
                return null;
            }
        }
    }

    @Override
    protected void onDestroy() {

//        try{
//            if(Connectivity.isNetworkAvailable(this)){
//                if(file_delete.exists()){
//                    String root =  Environment.getExternalStorageDirectory().toString();
//                    File file_delete = new File("/storage/emulated/0/Pictures/Account/");
//                    Log.e("Storage at ",""+file_delete);
//                    delete_thegodamn_files(file_delete);
//                }
//            }
//        }catch(NullPointerException e){
//
//        }


        super.onDestroy();
        if(dialog_acc !=null && dialog_acc.isShowing()){
            dialog_acc.dismiss();
        }
        if(dialog_final_acc !=null && dialog_final_acc.isShowing()){
            dialog_final_acc.dismiss();
        }


    }

    private void showofflinedialog() {
        final CharSequence[] items = {"Ok"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainRadioActivity.this);
        builder.setTitle("Submitting the Data is Complete!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(MainRadioActivity.this);

                if (items[item].equals("OK")) {
                    Intent intent = new Intent(MainRadioActivity.this,MainRadioActivity.class);
                    String root =  Environment.getExternalStorageDirectory().toString();
                    file_delete = new File(root + "/Pictures/"+GALLERY_DIRECTORY_NAME);
                    Log.e("Storage at ",""+file_delete);
                    if(Connectivity.isNetworkAvailable(MainRadioActivity.this)){
                        delete_thegodamn_files(file_delete);
                    }

                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

//    private Boolean delete_thegodamn_files(File file_delete) {
//        if(file_delete.isDirectory()){
//            String[] children = file_delete.list();
//            for(int i =0; i<children.length; i++){
//                Boolean success = delete_thegodamn_files(new File(file_delete , children[i]));
//                Log.e("File deleted","name was "+children[i].toString());
//                if (!success){
//                    return  false;
//                }
//            }
//
//        }
//        return  true;
//    }

    private class OnlyImageupload extends  AsyncTask<String, Void, String>{
        public  String customer;
        public File file;

        ProgressDialog dialog;
        String result="";
        public OnlyImageupload(File image, String cus_code) {
            this.customer = cus_code;
            this.file = image;
        }

        @Override
        protected String doInBackground(String... strings) {
            MultipartEntity entity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);
            try {
                entity.addPart("cust_code", new StringBody("" +customer));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            entity.addPart("doc", new FileBody(file));
            Log.e("File is",""+file.getName());

            result = Utilities.postEntityAndFindJson("http://ihisaab.com/Api/customerDetails", entity);
            return  result;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                dbHelper.deleteData_for_image();
            }catch (SQLException e){

            }

            super.onPostExecute(s);
        }
    }

    @Override
    protected void onStop() {

        super.onStop();

    }

}
