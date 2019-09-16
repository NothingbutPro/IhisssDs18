package com.example.ics.documentscanner;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class FormActivity extends AppCompatActivity {
    Toolbar toolbar_income;
    Calendar myCalendar;
    ProgressDialog dialog;
    DatePickerDialog.OnDateSetListener date;
    private String dateFlage;
    EditText nameAssessee,fatherName,address,email_,status,pan,residentailStatus,aadharNo,dateofBirth,sex,passportNo,tele,password,
            bank,bankLocation,accountNo,micrNo,ifsc,type;
    Button submit ,skip;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        //------------
        final CharSequence[] items = {"Bulk User", "Single User","Go back"};
        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(FormActivity.this);

                if (items[item].equals("Bulk User")) {
                    Intent sxb = new Intent(FormActivity.this,SingleUser.class);
                    startActivity(sxb);
                }
                else if (items[item].equals("Single User")) {
                    Intent sx = new Intent(FormActivity.this,SingleUser.class);
                    String from_radio_to_emai = getIntent().getStringExtra("ret_of_email");
                    String from_radio_to_mob = getIntent().getStringExtra("mob_to_ret");

                    if(from_radio_to_emai != null && from_radio_to_mob !=null){
                        sx.putExtra("to_the_last_email",from_radio_to_emai);
                        sx.putExtra("to_the_last_mob",from_radio_to_mob);
                        Toast.makeText(FormActivity.this, ""+from_radio_to_emai, Toast.LENGTH_SHORT).show();
                        Toast.makeText(FormActivity.this, ""+from_radio_to_mob, Toast.LENGTH_SHORT).show();
                        startActivity(sx);
                    }else{
                        startActivity(sx);
                    }
                    // android.os.Process.killProcess(android.os.Process.myPid());
                    // moveTaskToBack(true);
                    //   System.runFinalizersOnExit(true);

                //    moveTaskToBack(true);
                    //    finish();
                    // dialog.dismiss();
                }else if (items[item].equals("Go back")) {
                    Intent sx = new Intent(FormActivity.this,MainRadioActivity.class);
                    startActivity(sx);
                    // android.os.Process.killProcess(android.os.Process.myPid());
                    // moveTaskToBack(true);
                    //   System.runFinalizersOnExit(true);

                    //    moveTaskToBack(true);
                    //    finish();
                    // dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
      // builder = new AlertDialog.Builder(FormActivity.this);


    }



    //=========================

    }
