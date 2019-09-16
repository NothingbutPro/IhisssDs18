package com.example.ics.documentscanner;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.example.ics.documentscanner.RuntimePermission.hasPermissions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    PdfDocument pdfDocument;
    private int REQUEST_CAMERA = 1, SELECT_FILE = 0;
    int call_back = 0;
    SharedPreferences sharedpreferences_cus;
    SharedPreferences.Editor editor_cus;
    String store_cus;
    String cus_id;
    private int PICK_IMAGE_REQUEST = 1;
    //Uri to store the image uri
    private Uri filePath;
    private Bitmap bitmap = null;
    private Bitmap scanned_bitmap;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private String mess;
    private String response_code;
    private String TAG = "ProfileActivity";
    private final int SELECT_PHOTO = 1;
    private String updatedprofilepic;
    private Uri file;
    private ImageView select_registrationplate;
    private Button next_reg_plate,skip_regplate;
    private String flag=null;
    SharedPreferences getSharedpreferences_cus;
    public static final String cus_preferences= "cus_code";
    public static final String Name = "cus_key";
    SharedPreferences.Editor editor;
    int Gallery_view=2;
    File[] off_files;
    private static final int RC_OCR_CAPTURE = 9003;
    //    private static final String TAG = "MainActivity";
    private static final int MY_CAMERA_REQUEST_CODE = 100;


    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    // key to store image path in savedInstance state
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";

    public static final int MEDIA_TYPE_IMAGE = 1;

    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 8;

    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Account";
    public static  String GALLERY_DIRECTORY_NAME_USER;

    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String IMAGE_EXTENSION_pdf = "pdf";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static String imageStoragePath;
    private Bundle savedInstanceState;
    String cus_id2;
    String to_show_cus_id;
    String name_offline;
    String Pan_no;
    File offline_file;
    private static final int PERMISSION_ALL = 1 ;
    String[] PERMISSIONS = {android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    DBHelper dbHelper_of_main;
    String id,n,em,mb ;
    String lc ;
    String lk ;
    String pn ;
    String gst ;
    String cust ;
    double uploads;
    String get_offline_user,get_offline_cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cus_id = getIntent().getStringExtra("cust_code");
        cus_id2 = getIntent().getStringExtra("cus");
        name_offline = getIntent().getStringExtra("name_of");
        Pan_no = getIntent().getStringExtra("Pan_name");
        id = getIntent().getStringExtra("id_of");
           n = getIntent().getStringExtra("Nam_name");
           em = getIntent().getStringExtra("Em_name");
           mb = getIntent().getStringExtra("Mob_name");
            lc = getIntent().getStringExtra("Loc_name");
             lk = getIntent().getStringExtra("Lan_name");
            pn = getIntent().getStringExtra("Pan_name");
           gst = getIntent().getStringExtra("Gst_name");
             cust = getIntent().getStringExtra("Cust_name");
             get_offline_user = getIntent().getStringExtra("offline_us");
             get_offline_cd = getIntent().getStringExtra("offline_us_cd");

            // img_n = imgFile.getName();
     //  offline_concept();


       initialComp();


    }

    private void offline_concept() {
        if (Connectivity.isNetworkAvailable(MainActivity.this)) {
            if(cus_id !=null){
                GALLERY_DIRECTORY_NAME_USER = "Account_"+cus_id;
            }
            if(cus_id2 !=null){
                GALLERY_DIRECTORY_NAME_USER = "Account_"+cus_id2;
            }
        }else if(GALLERY_DIRECTORY_NAME_USER == null) {
            if(cus_id !=null){
                GALLERY_DIRECTORY_NAME_USER = "Account_"+cus_id+"nt";
                offline_file  = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE).getParentFile();


//                  for(int ixp = 0 ;ixp<= offline_file.listFiles().length; ixp++){
//
//                  }
                     //   Toast.makeText(this, "true its exist main cus id", Toast.LENGTH_SHORT).show();
                        String name_of_dir = offline_file.getAbsolutePath();
                        if(name_of_dir.equals(GALLERY_DIRECTORY_NAME_USER)){
                             off_files = offline_file.listFiles(new FilenameFilter() {
                                @Override
                                public boolean accept(File dir, String name) {
//                                    Toast.makeText(MainActivity.this, ""+dir.getName(), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(MainActivity.this, ""+name.endsWith(".jpg"), Toast.LENGTH_SHORT).show();
                                    return (name.endsWith(".jpg"));
                                }
                            });
                        }



            }
            if(cus_id2 !=null){
                GALLERY_DIRECTORY_NAME_USER = "Account_"+cus_id2+"nt";
                offline_file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);


                      //  Toast.makeText(this, "true its exist", Toast.LENGTH_SHORT).show();
                        String name_of_dir = offline_file.getAbsolutePath();
                        Toast.makeText(this, ""+name_of_dir, Toast.LENGTH_LONG).show();


                    // String name_of_dir = offline_file.getParentFile().getAbsolutePath();
                    //  Toast.makeText(this, ""+name_of_dir, Toast.LENGTH_LONG).show();



               //  Toast.makeText(this, ""+offline_file, Toast.LENGTH_LONG).show();

            }
            Toast.makeText(MainActivity.this, "no internet", Toast.LENGTH_SHORT).show();

        }

    }

    private void initialComp() {
//        to_show_cus_id  = getIntent().getStringExtra("cus");
        to_show_cus_id  = getIntent().getStringExtra("cus");
        final Context context = MainActivity.this;
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.single_alert);
        // set the custom dialog components - text, image and button
        TextView cus_id_to_s = (TextView)dialog.findViewById(R.id.cus_id_v);
        cus_id_to_s.setText(""+to_show_cus_id);

        Button con = (Button) dialog.findViewById(R.id.si_bc);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

              selectImage();
            }
        });
        if(to_show_cus_id == null){

            selectImage();
           // to_show_cus_id= null;
            dialog.dismiss();
        }else{
            dialog.show();
           //
            dialog.setCancelable(false);
            to_show_cus_id= null;
//
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:


                if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.CAMERA )
                        != PackageManager.PERMISSION_GRANTED) {

                    if (getFromPref(this, ALLOW_KEY)) {

                        //showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.GET_ACCOUNTS)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                android.Manifest.permission.CAMERA)) {
                            //showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(this,
                                    new String[]{android.Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    selectImage();
                }
                break;
        }
    }


    //********************************************************************************************

    public static void saveToPreferences(Context context, String key,
                                         Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }


    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DON'T ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You have to allow it", Toast.LENGTH_SHORT).show();
                //        dialog.dismiss();
                  //      finish();
                       // showAlert();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{android.Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                       // dialog.dismiss();
                        selectImage();

                    }
                });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DON'T ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Show setting alert ", Toast.LENGTH_SHORT).show();
                      //  dialog.dismiss();
                        //finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //  onCaptureImageResult(VehicleActivity.this);

                    }
                });
        alertDialog.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
//                    if (num_edit.getText().toString().isEmpty() == false && num_edit.getText().length() ==10) {
////                        store = num_edit.getText().toString();
////                        editor = sharedpreferences.edit();
////                        editor.putString("Name" , store);
////                        editor.commit();
                    Toast.makeText(MainActivity.this, " Permissions Granted", Toast.LENGTH_LONG).show();
                    selectImage();
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
                    Toast.makeText(MainActivity.this, "  Permissions Denied.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_CAMERA: {
//                for (int i = 0, len = permissions.length; i < len; i++) {
//                    String permission = permissions[i];
//                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
//                        boolean showRationale =
//                                ActivityCompat.shouldShowRequestPermissionRationale
//                                        (this, permission);
//                        if (showRationale) {
//                            showAlert();
//                        } else if (!showRationale) {
//                            // user denied flagging NEVER ASK AGAIN
//                            // you can either enable some fall back,
//                            // disable features of your app
//                            // or open another dialog explaining
//                            // again the permission and directing to
//                            // the app setting
//                            saveToPreferences(MainActivity.this, ALLOW_KEY, true);
//                        }
//                    }else if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
//                        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
//                     selectImage();
//                    }
//                }
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//
//        }
//    }

    //*********************************************************************************************

    private void selectImage() {
        if(cus_id != null){

            final CharSequence[] items = {"Capture","Download Report","Go to home page"};
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Capture!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    checkAndRequestPermissions();
                    boolean result = Utility.checkPermission(MainActivity.this);

                    if (items[item].equals("Capture")) {
                        //userChoosenTask = "Capture";
                        if (result)
                            if (CameraUtils.checkPermissions(getApplicationContext()) ) {
                                //    Toast.makeText(MainActivity.this, "Capture image called", Toast.LENGTH_SHORT).show();
                                captureImage();
                                //     Toast.makeText(MainActivity.this, "we got result back", Toast.LENGTH_SHORT).show();

                                restoreFromBundle(savedInstanceState);
                            } else {
                                // requestCameraPermission(MEDIA_TYPE_IMAGE);
                            }
                        // cameraIntent();

                    }
                    else if (items[item].equals("Go to home page")) {
                        Intent ix = new Intent(MainActivity.this,MainRadioActivity.class);
                        ix.putExtra("cus_code",cus_id);
                        // Toast.makeText(MainActivity.this, "The customer id in go to home page is"+cus_id, Toast.LENGTH_SHORT).show();
                        startActivity(ix);

                        // android.os.Process.killProcess(android.os.Process.myPid());
                        // finish();
                        // moveTaskToBack(true);

                        // moveTaskToBack(true);
                        // dialog.dismiss();
                    }else if (items[item].equals("Download Report")){
                        Intent ix = new Intent(MainActivity.this,Gallery_view.class);
                        ix.putExtra("gallery_code",cus_id);
                        // Toast.makeText(MainActivity.this, "The customer id in go to home page is"+cus_id, Toast.LENGTH_SHORT).show();
                        startActivity(ix);

//API http://ihisaab.com/api/reportByCode?cut_code=SHRE1018-1
                    }
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCancelable(false);
        } else{
            //------------

            final CharSequence[] items = {"Capture","Go to home page"};
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Capture!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    checkAndRequestPermissions();
                    boolean result = Utility.checkPermission(MainActivity.this);

                    if (items[item].equals("Capture")) {
                        //userChoosenTask = "Capture";
                        if (result)
                            if (CameraUtils.checkPermissions(getApplicationContext()) ) {
                                //    Toast.makeText(MainActivity.this, "Capture image called", Toast.LENGTH_SHORT).show();
                                captureImage();
                                //     Toast.makeText(MainActivity.this, "we got result back", Toast.LENGTH_SHORT).show();

                                restoreFromBundle(savedInstanceState);
                            } else {
                                // requestCameraPermission(MEDIA_TYPE_IMAGE);
                            }
                        // cameraIntent();

                    }
                    else if (items[item].equals("Go to home page")) {
                        Intent ix = new Intent(MainActivity.this,MainRadioActivity.class);
                        ix.putExtra("cus_code",cus_id);
                        // Toast.makeText(MainActivity.this, "The customer id in go to home page is"+cus_id, Toast.LENGTH_SHORT).show();
                        startActivity(ix);

                        // android.os.Process.killProcess(android.os.Process.myPid());
                        // finish();
                        // moveTaskToBack(true);

                        // moveTaskToBack(true);
                        // dialog.dismiss();
                    }
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCancelable(false);
        }


    }

    private void ocrIntent() {
        Intent intent = new Intent(MainActivity.this, OcrCaptureActivity.class);
        startActivityForResult(intent, RC_OCR_CAPTURE);
    }


    //------------ private void galleryIntent()
    private void galleryIntent() {
     /*   Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);*/

        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
        startActivityForResult(i,Gallery_view);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    //---------------------------------------------------------------------------
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putString(KEY_IMAGE_STORAGE_PATH, imageStoragePath);
    }

    /**
     * Restoring image path from saved instance state
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
    }
    //--------------------------------------------------------------------


    private void restoreFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_IMAGE_STORAGE_PATH)) {
                imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
                if (!TextUtils.isEmpty(imageStoragePath)) {
                    if (imageStoragePath.substring(imageStoragePath.lastIndexOf(".")).equals("." + IMAGE_EXTENSION)) {
                        previewCapturedImage();
                    }
                }
            }
        }
    }
    private void previewCapturedImage() {

        try {
            // hide video preview
            Bitmap bitmap = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);
            new ImageCompression().execute(imageStoragePath);

         //   select_registrationplate.setImageBitmap(bitmap);
          /*  File imgFile = new  File(imageStoragePath);
            if(imgFile.exists()){
                select_registrationplate.setImageURI(Uri.fromFile(imgFile));
                //    show(imgFile);
                new ImageUploadTask(imgFile).execute();
                //  Toast.makeText(UploadImageMulti.this,"data:="+imgFile,Toast.LENGTH_LONG).show();
            }*/
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    //------------------------
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        /*Bitmap bitmap=null;*/
        if (data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

       // select_registrationplate.setImageBitmap(bitmap);
    }


    //------------------------------------
//
//    private void showPermissionsAlert() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Permissions required!")
//                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
//                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        CameraUtils.openSettings(MainActivity.this);
//                    }
//                })
//                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                }).show();
//    }

    //-----------------------------------------------------------------------------------------------------------------------------
    class  ImageUploadTask extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;
        String result="";
        File pic;

        public ImageUploadTask(File imgFile) {
            this.pic = imgFile;
          //  Log.e("File " , ""+imgFile.getName());
        }

        @Override
        protected void onPreExecute() {

      //      dialog = new ProgressDialog(MainActivity.this);
  //          dialog.setMessage("Processing");
    //        dialog.setCancelable(true);
//            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);
                     //  cus_id = getIntent().getStringExtra("cust_code");
                       //cus_id2 = getIntent().getStringExtra("cus");
                //++++++++++++++++++++++++++++++++++++For shared reference+++++++++++++++++++++++++++++++++++++++++++++
//                sharedpreferences_cus = getSharedPreferences(cus_preferences,
//                        Context.MODE_PRIVATE);
//                editor = sharedpreferences_cus.edit();
//                Id = getIntent().getStringExtra("cus_code");
//                editor.putString(cus_preferences , Id);
              //  editor.commit();

//                           Toast.makeText(MainActivity.this, "ID is null ", Toast.LENGTH_SHORT).show();
                         //  entity.addPart("cust_code", new StringBody(""+cus_id));
//                try{
//                    FileOutputStream fileOutputStream = new FileOutputStream(pic);
//                    pdfDocument.writeTo(fileOutputStream);
//
//                }catch (FileNotFoundException e){
////                    Log.e("File does" , "File does not found");
//                } catch (IOException e) {
////                    Log.e("File does" , "File does not found");
//                }
//                pdfDocument.close();

                if(cus_id != null){
                    entity.addPart("cust_code", new StringBody("" + cus_id));
                    entity.addPart("doc", new FileBody(pic));
                    Log.e("File is",""+pic.getName());

                    result = Utilities.postEntityAndFindJson("http://ihisaab.com/Api/customerDetails", entity);
                }else if(cus_id2 !=null){
                    entity.addPart("cust_code", new StringBody("" + cus_id2));
                    entity.addPart("doc", new FileBody(pic));
                    Log.e("File is",""+pic.getName());
                    result = Utilities.postEntityAndFindJson("http://ihisaab.com/Api/customerDetails", entity);


                }
                   // entity.addPart("cust_code", new StringBody("" + cus_id));
                        //   entity.addPart("doc", new FileBody(pic));

                //  result = Utilities.postEntityAndFindJson("http://api.jvmsch.com/api/PolicyNoimg", entity);

                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            String result1=result;
            if(result!=null){
              //  dialog.dismiss();
                Log.e("result",result1);
                JSONObject res = null;
                try {
                    res = new JSONObject(result1);
                    String response = res.getString("response");
                    String message = res.optString("message");
                    Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    Log.e("File does" , "File does not found");
                    e.printStackTrace();
                }

                //   Intent in=new Intent(MainActivity.this,NextActivity.class);
                //  in.putExtra("doc",doc);
                //     startActivity(in);

            }else{

                //  in.putExtra("doc",doc);
//                dialog.dismiss();
                Toast.makeText(MainActivity.this,"Some Problem",Toast.LENGTH_LONG).show();
            }

        }}


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //===================================================================================

    private void captureImage() {
        int ask_again = 0;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
      //      Toast.makeText(this, "File is not null", Toast.LENGTH_SHORT).show();
//            CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);
            // start the image capture Intent

//                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
      //      Toast.makeText(getApplicationContext() ,"Step 2",Toast.LENGTH_LONG).show();
        }
        if(file == null){
        //    Toast.makeText(this, "File is null", Toast.LENGTH_SHORT).show();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);
      //  Toast.makeText(this, ""+fileUri, Toast.LENGTH_SHORT).show();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
       // Toast.makeText(getApplicationContext() ,"Step 3",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       /* if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);

            else */ if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);
             //   Toast.makeText(this, "called once", Toast.LENGTH_SHORT).show();
                previewCapturedImage();
                captureImage();

            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
//                Toast.makeText(getApplicationContext(),
//                        "User cancelled image capture", Toast.LENGTH_SHORT)
//                        .show();

                final CharSequence[] items = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Do you want to upload more?");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        boolean result = Utility.checkPermission(MainActivity.this);

                        if (items[item].equals("Yes")) {
                            //userChoosenTask = "Yes";
                            if (result)
                                if (CameraUtils.checkPermissions(getApplicationContext())) {
                                //    Toast.makeText(MainActivity.this, "Capture image called", Toast.LENGTH_SHORT).show();
                                    captureImage();
                               //     Toast.makeText(MainActivity.this, "we got result back", Toast.LENGTH_SHORT).show();

                                    restoreFromBundle(savedInstanceState);
                                } else {
                                  //  requestCameraPermission(MEDIA_TYPE_IMAGE);
                                }
                            // cameraIntent();

                        }
                        else if (items[item].equals("No")) {
                            Intent intent = new Intent(MainActivity.this,MainRadioActivity.class);
                            String root =  Environment.getExternalStorageDirectory().toString();
                            File file_delete = new File(root + "/Pictures/"+GALLERY_DIRECTORY_NAME);
                            Log.e("Storage at ",""+file_delete);
                            if(Connectivity.isNetworkAvailable(MainActivity.this)){
                                delete_thegodamn_files(file_delete);
                            }else{
                                Log.e("Images will be","Saved");
                            }


//                            file_delete.delete();
//                            if(file_delete.exists()){
//                                try {
//                                    file_delete.getCanonicalFile().delete();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                                if(file_delete.exists()){
//                                    getApplicationContext().deleteFile(file_delete.getName());
//                                }
//                            }
//                            Log.e("File delete operation",""+file_delete.exists());
//                            Log.e("File name operation",""+file_delete.getAbsolutePath());

                            startActivity(intent);
                            // android.os.Process.killProcess(android.os.Process.myPid());
                             finish();

                          //  moveTaskToBack(true);
                            // System.exit(0);
                            // moveTaskToBack(true);
                            // dialog.dismiss();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.setCancelable(false);

              //  selectImage();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        if (requestCode == Gallery_view && data != null){
            Uri pickedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            // image.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            if (imagePath!=null) {
                new ImageCompression().execute(imagePath);
                //   select_registrationplate.setImageURI(Uri.fromFile(imgFile));
                //  show(imgFile);
                // new ImageUploadTask(new File(imagePath)).execute();
                //   Toast.makeText(ReportSummary.this, "data:=" + imgFile, Toast.LENGTH_LONG).show();
            }
            // showPreview(imagePath);
            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
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

    private void requestCameraPermission(final int type) {

        Dexter.withActivity(this)
                .withPermissions(android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                // capture picture
                                captureImage();
                            }

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                         //   requestCameraPermission(MEDIA_TYPE_IMAGE);
//                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    //-----------------------------------------------------

    public class ImageCompression extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (strings.length == 0 || strings[0] == null)
                return null;

            return  CommonUtils.compressImage(strings[0]);
        }

        protected void onPostExecute(String imagePath) {
            // imagePath is path of new compressed image.
//            mivImage.setImageBitmap(BitmapFactory.decodeFile(new File(imagePath).getAbsolutePath()));
            File imgFile = new File(imagePath);
            if(!Connectivity.isNetworkAvailable(MainActivity.this)){
            if (imgFile.exists()) {
                if(imgFile.exists() && get_offline_cd !=null && uploads ==0){
                    dbHelper_of_main = new DBHelper(MainActivity.this, "ihisaab_offline.sqlite", null, 1);
                    dbHelper_of_main.getWritableDatabase();
                    Log.e("Data inserted",""+imagePath);
                    dbHelper_of_main.insertDatatoAccountfor_image(n,imgFile.getName(),get_offline_cd);
                    uploads = uploads+1;
                }
                  if(uploads ==0 && get_offline_user == null && get_offline_cd ==null){
                      dbHelper_of_main = new DBHelper(MainActivity.this, "ihisaab_offline.sqlite", null, 1);
                      dbHelper_of_main.getWritableDatabase();
                     // dbHelper_of_main.queryData("CREATE TABLE IF NOT EXISTS ACCOUNT(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR NOT NULL,PAN VARCHAR , IMAGE_ADDRESS VARCHAR)");


                      //getIntent().getStringExtra("Mob_name")
                     // dbHelper_of_main.queryData("UPDATE ACCOUNT SET IMAGE_ADDRESS ="+imgFile.getName().trim()+" WHERE PAN =" +getIntent().getStringExtra("Pan_name"));
//                      dbHelper_of_main.updateData(getIntent().getStringExtra("id_of"),getIntent().getStringExtra("Nam_name"),getIntent().getStringExtra("Em_name"),getIntent().getStringExtra("Mob_name"),getIntent().getStringExtra("Loc_name"),getIntent().getStringExtra("Lan_name"),getIntent().getStringExtra("Pan_name"),getIntent().getStringExtra("Gst_name"),getIntent().getStringExtra("Cust_name"),imgFile.getName(),cus_id2);
                      Log.e("Data inserted",""+imagePath);
                      dbHelper_of_main.updateData(id,n,em,mb,lc,lk,pn,gst,cust,imgFile.getName(),cus_id2);
                      uploads = uploads+1;

                  }else if(uploads >=1&& get_offline_cd ==null ){

                      dbHelper_of_main = new DBHelper(MainActivity.this, "ihisaab_offline.sqlite", null, 1);
                          dbHelper_of_main.getWritableDatabase();
                          dbHelper_of_main.queryData("CREATE TABLE IF NOT EXISTS ACCOUNT_IMAGE(Id INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR,IMAGE_ADDRESS VARCHAR, CUSTOMER_CODE VARCHAR)");
                          dbHelper_of_main.insertDatatoAccountfor_image(n,imgFile.getName(),cus_id2);
                          Toast.makeText(MainActivity.this, "inserted successfully ", Toast.LENGTH_SHORT).show();



                  }else if(get_offline_user !=null){
                      dbHelper_of_main = new DBHelper(MainActivity.this, "ihisaab_offline.sqlite", null, 1);
                      dbHelper_of_main.getWritableDatabase();
                      dbHelper_of_main.queryData("CREATE TABLE IF NOT EXISTS ACCOUNT(Id INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR NOT NULL, EMAIL VARCHAR NOT NULL, MOBILE INTEGER, LOCATION  VARCHAR,LANDMARK VARCHAR,PAN VARCHAR ,GST VARCHAR,CUSTOMER_ID VARCHAR,IMAGE_ADDRESS VARCHAR, CUSTOMER_CODE VARCHAR)");
                      dbHelper_of_main.queryData("CREATE TABLE IF NOT EXISTS ACCOUNT_IMAGE(Id INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR NOT NULL,IMAGE_ADDRESS VARCHAR, CUSTOMER_CODE VARCHAR)");
                     Cursor offline_f_image = dbHelper_of_main.getData_for_cus("SELECT * FROM ACCOUNT_IMAGE WHERE CUSTOMER_CODE = ? ", get_offline_user);
                      Cursor offline_f_acc =  dbHelper_of_main.getData_for_cus("SELECT * FROM ACCOUNT WHERE CUSTOMER_CODE = ?" , get_offline_user);
                          if(offline_f_acc.moveToNext() && offline_f_acc.getCount() >=0 && offline_f_image.getCount() == 0){
                              String off_acc_n = offline_f_acc.getColumnName(1);
                              String off_acc_im = offline_f_acc.getColumnName(9);
                              String off_acc_cus = offline_f_acc.getColumnName(3);
                              dbHelper_of_main.insertDatatoAccountfor_image_for_acc(off_acc_n,imgFile.getName(),get_offline_user);
                              Toast.makeText(MainActivity.this, "found one", Toast.LENGTH_SHORT).show();



                          } else if(offline_f_image.moveToNext()){
                          String off_img_n = offline_f_image.getColumnName(1);
                          String off_img_im = offline_f_image.getColumnName(2);
                          String off_img_cus = offline_f_image.getColumnName(3);
                          dbHelper_of_main.insertDatatoAccountfor_image(off_img_n,imgFile.getName(),get_offline_user);
                          Toast.makeText(MainActivity.this, "found one", Toast.LENGTH_SHORT).show();
                      }




                  }
            }
              //  scanned_bitmap = BitmapFactory.decodeFile(imagePath);
                //pdfconvert try
//                 pdfDocument = new PdfDocument();
//                PdfDocument.PageInfo pi = new PdfDocument.PageInfo.Builder(scanned_bitmap.getWidth() , scanned_bitmap.getHeight() , 1).create();
//                PdfDocument.Page page = pdfDocument.startPage(pi);
//                Canvas canvas = page.getCanvas();
//                Paint paint = new Paint();
//                paint.setColor(Color.BLUE);
//                canvas.drawPaint(paint);
//                scanned_bitmap = Bitmap.createScaledBitmap(scanned_bitmap,scanned_bitmap.getWidth(),scanned_bitmap.getHeight(),true);
//                paint.setColor(Color.BLUE);
//                canvas.drawBitmap(scanned_bitmap,0,0,null);
//                pdfDocument.finishPage(page);
//                // offline_file  = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE).getParentFile();
//                //     File pdf_scan = new File(Environment.getExternalStorageDirectory() , "Pictures/Account");
//                File pdf = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
//                if(pdf.exists()){
//                    Toast.makeText(MainActivity.this, "Files exists", Toast.LENGTH_SHORT).show();
//                    Log.e("Storage at ",""+pdf.getAbsolutePath());
//                }



            }else{
                if(imgFile.exists()){
                    new ImageUploadTask(imgFile).execute();
                }else{
                    Toast.makeText(MainActivity.this, "Please select an image first", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }

    private void checkAndRequestPermissions() {
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }
}

