package com.example.ics.documentscanner;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
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
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class Single_user_act_2 extends AppCompatActivity {
    PdfDocument pdfDocument;
   Button br1,br2,br3,br4,br5,br0;
   Button brs0,brs1,brs2,brs3,brs4,brs5;
   File return_copy,form_copy,bank_copy,other_copy,house_loan_copy;
    int Gallery_view=2;
    //
   // https://www.spellclasses.co.in/DM/Api/taxreturn
   // parameters are : name,email,pan_no,adhan_no,mobile,ret_email,ret_mobile,return_copy,form_copy,bank_copy,other_copy,house_loan_copy
    private static final int RC_OCR_CAPTURE = 9003;
    //    private static final String TAG = "MainActivity";
    private static final int MY_CAMERA_REQUEST_CODE = 101;
    private static final int MY_GALLERY_REQUEST_CODE = 101;

    private Bitmap scanned_bitmapacc;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    // key to store image path in savedInstance state
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";

    public static final int MEDIA_TYPE_IMAGE = 1;
    public  String show_i_ref_id;
    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 8;

    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Account";
        private String gma_of_incometax;
    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";
    private static String imageStoragePath;
    private Bundle savedInstanceState;
    String pan,emai,adhan,mobi,r_mob,r_mai,nam,ref_co;
     int ret_co=0 ,f_c=0,b_c=0,o_c=0,h_l_c=0;
     String income_tax_id;
    int c1 =1,c2=1,c3=1,c4=1,c5=1;
    Button smit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user_act_2);
        br0 = (Button)findViewById(R.id.br0);
        br1 = (Button)findViewById(R.id.br1);
        br2 = (Button)findViewById(R.id.br2);
        br3 = (Button)findViewById(R.id.br3);
        br4 = (Button)findViewById(R.id.br4);
        smit = (Button)findViewById(R.id.smit);
        brs0 = (Button)findViewById(R.id.brs0);
        brs1 = (Button)findViewById(R.id.brs1);
        brs2 = (Button)findViewById(R.id.brs2);
        brs3 = (Button)findViewById(R.id.brs3);
        brs4 = (Button)findViewById(R.id.brs4);
        income_tax_id = getIntent().getStringExtra("pan_i");
        br0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openImageChooser();
//                    br0.setText("Upload"+c1);
                     c1++;
                     c2=0;
                     c3=0;
                     c4=0;
                     c5=0;
                //for scanning
                b_c=0 ;
                h_l_c=0;
                ret_co=0;
                o_c=0;
                f_c=0;

            }
        });
        br1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Form 16
                openImageChooser();
//                br0.setText("Upload"+c1);
                c2++;
                c1=0;
                c3=0;
                c4=0;
                c5=0;
                //for scanning
                b_c=0 ;
                h_l_c=0;
                ret_co=0;
                o_c=0;
                f_c=0;

            }
        });
        br2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bank statement
                openImageChooser();
//                br0.setText("Upload"+c1);
                c3++;
                c1=0;
                c2=0;
                c4=0;
                c5=0;
                //for scanning
                b_c=0 ;
                h_l_c=0;
                ret_co=0;
                o_c=0;
                f_c=0;

            }
        });
        br3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //other details
                openImageChooser();
//                br0.setText("Upload"+c4);
                c4++;
               c1=0;
               c2=0;
               c3=0;
               c5=0;
                //for scanning
                b_c=0 ;
                h_l_c=0;
                ret_co=0;
                o_c=0;
                f_c=0;

            }
        });
        br4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Home loan statement
                openImageChooser();
//                br0.setText("Upload"+c5);
                c5++;
                c1=0;
                c2=0;
                c3=0;
                c4=0;
                //for scanning
                b_c=0 ;
                h_l_c=0;
                ret_co=0;
                o_c=0;
                f_c=0;

            }
        });

//        Log.e("PanId",income_tax_id);
        if (income_tax_id!=null) {
          //  income_tax_id = getIntent().getStringExtra("pan_i");
            Log.e("PanId after checking is",income_tax_id);
        }
        initialComp();
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;

        Account[] accounts = AccountManager.get(Single_user_act_2.this).getAccounts();

        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                Log.d("Getting Account", String.format("%s - %s", account.name, account.type));
                gma_of_incometax = account.name;

            }
        }



        brs0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LASSSSSSSSSSSSSSSSSSSSSSSSSSSSSSTTTTTTTTTTTTT Year return//////////////////////////////////

            //    Toast.makeText(Single_user_act_2.this, "pan no is "+pan, Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(Single_user_act_2.this,
                        android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (getFromPref(Single_user_act_2.this, ALLOW_KEY)) {

                        showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(Single_user_act_2.this,
                            android.Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Single_user_act_2.this,
                                android.Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(Single_user_act_2.this,
                                    new String[]{android.Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    ret_co = ret_co+1;
                    o_c =0;
                    f_c=0;
                    b_c=0;
                    h_l_c=0;
                    c5 =0;
                    c1=0;
                    c2=0;
                    c3=0;
                    c4=0;
                    selectImage();
                }


            }
        });
        brs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // f_c = f_c+1;
            //    Toast.makeText(Single_user_act_2.this, "pan no is "+pan, Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(Single_user_act_2.this,
                        android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (getFromPref(Single_user_act_2.this, ALLOW_KEY)) {

                        showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(Single_user_act_2.this,
                            android.Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Single_user_act_2.this,
                                android.Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(Single_user_act_2.this,
                                    new String[]{android.Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    f_c = f_c+1;
                    o_c =0;
                //    f_c=0;
                    b_c=0;
                    h_l_c=0;
                    ret_co=0;
                    //for upload
                    c5 =0;
                    c1=0;
                    c2=0;
                    c3=0;
                    c4=0;
                    selectImage();
                }

            }
        });
        brs2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // b_c = b_c+1;
              //  Toast.makeText(Single_user_act_2.this, "pan no is "+pan, Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(Single_user_act_2.this,
                        android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (getFromPref(Single_user_act_2.this, ALLOW_KEY)) {

                        showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(Single_user_act_2.this,
                            android.Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Single_user_act_2.this,
                                android.Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(Single_user_act_2.this,
                                    new String[]{android.Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    b_c = b_c+1;
                  //  b_c =0;
                    ret_co = 0;
                    o_c = 0;
                    f_c = 0;
                    h_l_c = 0;
                    //for upload
                    c5 =0;
                    c1=0;
                    c2=0;
                    c3=0;
                    c4=0;
                    selectImage();
                }


            }
        });
        brs3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // o_c = o_c+1;
                Toast.makeText(Single_user_act_2.this, "pan no is "+pan, Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(Single_user_act_2.this,
                        android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (getFromPref(Single_user_act_2.this, ALLOW_KEY)) {

                        showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(Single_user_act_2.this,
                            android.Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Single_user_act_2.this,
                                android.Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(Single_user_act_2.this,
                                    new String[]{android.Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    o_c = o_c+1;
                    ret_co=0;
                    h_l_c=0;
                    b_c =0;
                    f_c=0;
                    //for upload
                    c5 =0;
                    c1=0;
                    c2=0;
                    c3=0;
                    c4=0;
                    selectImage();
                }


            }
        });
        brs4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // h_l_c = h_l_c+1;
                if (ContextCompat.checkSelfPermission(Single_user_act_2.this,
                        android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (getFromPref(Single_user_act_2.this, ALLOW_KEY)) {

                        showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(Single_user_act_2.this,
                            android.Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Single_user_act_2.this,
                                android.Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(Single_user_act_2.this,
                                    new String[]{android.Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    h_l_c = h_l_c+1;
                    ret_co=0;
                    ret_co=0;
                    o_c=0;
                    f_c=0;
                    //for upload
                    c5 =0;
                    c1=0;
                    c2=0;
                    c3=0;
                    c4=0;
                  //  ret_co = ret_co+1;
                    selectImage();
                }


            }
        });

        smit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c1>=1||c2>=1||c3>=1||c4>=1||c5>=1|| b_c>0 || h_l_c>0||ret_co>0||o_c>0||f_c>0) {
                    Intent last = new Intent(Single_user_act_2.this, MainRadioActivity.class);
                    String root =  Environment.getExternalStorageDirectory().toString();
                    File file_delete = new File(root + "/Pictures/"+GALLERY_DIRECTORY_NAME);
                    delete_thegodamn_filesinincome(file_delete);
                    startActivity(last);
                }
            }
        });
    }
//.................................For Choosing an image from Gallery...............///////////////////
    private void openImageChooser() {
        Intent  from_gallery = new Intent();
        from_gallery.setType("file/*");
        from_gallery.addCategory(Intent.CATEGORY_OPENABLE);
        from_gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(from_gallery , "Select File"),Gallery_view);
    }

    private void initialComp() {


    }

    private Boolean delete_thegodamn_filesinincome(File file_delete) {
        if(file_delete.isDirectory()){
            String[] children = file_delete.list();
            for(int i =0; i<children.length; i++){
                Boolean success = delete_thegodamn_filesinincome(new File(file_delete , children[i]));
                Log.e("File deleted","name was "+children[i].toString());
                if (!success){
                    Log.e("File can not be deleted","name was "+children[i].toString());
                    return  false;
                }
            }

        }
        return file_delete.delete();
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
             //   Toast.makeText(Single_user_act_2.this, " your pan no is "+pan, Toast.LENGTH_SHORT).show();
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);
              //  Toast.makeText(this, "called once", Toast.LENGTH_SHORT).show();
                previewCapturedImage();
                captureImage();

            } else if (resultCode == RESULT_CANCELED) {

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
            Log.e("Gallery image path is:" , ""+imagePath);

//            Uri pickedImage = data.getData();
//            String[] filePath = { MediaStore.Images.Media.DATA };
//            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
//            cursor.moveToFirst();
//            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
//            Log.e("Gallery image path is:" , ""+imagePath);
            // image.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            File pdfFile = new File(imagePath);
            if (pdfFile!=null) {

                if(pdfFile.exists()){
                    Log.e("PDF ",""+pdfFile.exists());
                    new ImageUploadTask(pdfFile).execute();

                }
             //   new ImageCompression().execute(imagePath);
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

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(Single_user_act_2.this,
                                new String[]{android.Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);

                    }
                });
        alertDialog.show();
    }
    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }
    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
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
    private void selectImage() {
        //------------


        final CharSequence[] items = {"Capture","Back"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Single_user_act_2.this);
        builder.setTitle("Capture!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Single_user_act_2.this);

                if (items[item].equals("Capture")) {
                    if (result)
                        if (CameraUtils.checkPermissions(getApplicationContext())) {
                        //    Toast.makeText(Single_user_act_2.this, "Capture image called", Toast.LENGTH_SHORT).show();
                            captureImage();
                       //     Toast.makeText(Single_user_act_2.this, "we got result back", Toast.LENGTH_SHORT).show();

                            restoreFromBundle(savedInstanceState);
                        } else {
                            requestCameraPermission(MEDIA_TYPE_IMAGE);
                        }
                    // cameraIntent();

                }

                else if (items[item].equals("Back")) {

                     dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

    }

    private void launchMediaScanIntent() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
      //  mediaScanIntent.setData(imageStoragePath);
        this.sendBroadcast(mediaScanIntent);
    }

    private void requestCameraPermission(final int type) {

        Dexter.withActivity(this)
                .withPermissions(android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                // capture picture
                                captureImage();
                            }

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(Single_user_act_2.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
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
    //===================================================================================

    private void captureImage() {
        int ask_again = 0;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
           // Toast.makeText(this, "File is not null", Toast.LENGTH_SHORT).show();
//            CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);
            // start the image capture Intent

//                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
         //   Toast.makeText(getApplicationContext() ,"Step 2",Toast.LENGTH_LONG).show();
        }
        if(file == null){
     //       Toast.makeText(this, "File is null", Toast.LENGTH_SHORT).show();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);
    //    Toast.makeText(this, ""+fileUri, Toast.LENGTH_SHORT).show();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
      //  Toast.makeText(getApplicationContext() ,"Step 3",Toast.LENGTH_LONG).show();

    }
    private void ocrIntent() {
        Intent intent = new Intent(Single_user_act_2.this, OcrCaptureActivity.class);
        startActivityForResult(intent, RC_OCR_CAPTURE);
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
            if (imgFile.exists()) {
//                scanned_bitmapacc = BitmapFactory.decodeFile(imagePath);
//                //pdfconvert try
//                pdfDocument = new PdfDocument();
//                PdfDocument.PageInfo pi = new PdfDocument.PageInfo.Builder(scanned_bitmapacc.getWidth() , scanned_bitmapacc.getHeight() , 1).create();
//                PdfDocument.Page page = pdfDocument.startPage(pi);
//                Canvas canvas = page.getCanvas();
//                Paint paint = new Paint();
//                paint.setColor(Color.BLUE);
//                canvas.drawPaint(paint);
//                scanned_bitmapacc = Bitmap.createScaledBitmap(scanned_bitmapacc,scanned_bitmapacc.getWidth(),scanned_bitmapacc.getHeight(),true);
//                paint.setColor(Color.BLUE);
//                canvas.drawBitmap(scanned_bitmapacc,0,0,null);
//                pdfDocument.finishPage(page);
//                // offline_file  = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE).getParentFile();
//                //     File pdf_scan = new File(Environment.getExternalStorageDirectory() , "Pictures/Account");
//                File pdf = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
                new ImageUploadTask(imgFile).execute();

            }else{
                Toast.makeText(Single_user_act_2.this, "Please select an image first", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //-----------------------------------------------------------------------------------------------------------------------------
    class ImageUploadTask extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;
        String result="";
        File pic0,pic1,pic2,pic3,pic4;
        

        public ImageUploadTask(File imgFile0) {
            this.pic0 = imgFile0;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Single_user_act_2.this);
            dialog.setMessage("Processing");

            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            nam = getIntent().getStringExtra("name");
            mobi = getIntent().getStringExtra("mobile");
            String r_mob_take = getIntent().getStringExtra("to_the_last_mob");
            String r_e_take = getIntent().getStringExtra("to_the_last_email");
            if(r_mob_take != null && r_e_take !=null ){
                r_mob = getIntent().getStringExtra(""+r_mob_take);

                r_mai = getIntent().getStringExtra(""+gma_of_incometax);
            }else{
                r_mob = getIntent().getStringExtra("mobile");
                r_mai = getIntent().getStringExtra("ret_email");
            }

            adhan = getIntent().getStringExtra("adhan_no");
            emai = gma_of_incometax;
            pan = getIntent().getStringExtra("pan_no");
           ref_co = getIntent().getStringExtra("ref_code");
//            try{
//                FileOutputStream fileOutputStream = new FileOutputStream(pic0);
//                pdfDocument.writeTo(fileOutputStream);
//
//            }catch (FileNotFoundException e){
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            pdfDocument.close();
            try {
              // String ref_co=  "41212";
//                MultipartEntity entity = new MultipartEntity(
//                        HttpMultipartMode.BROWSER_COMPATIBLE);
//                 entity.addPart("name",new StringBody(""+nam));
//                 entity.addPart("email",new StringBody(""+emai));
//                entity.addPart("adhan_no", new StringBody(""+adhan));
//                entity.addPart("pan_no",new StringBody(""+pan));
//                entity.addPart("mobile",new StringBody(""+mobi));
//                entity.addPart("ret_email",new StringBody(""+r_mai));
//                entity.addPart("ret_mobile",new StringBody(""+r_mob));

                    MultipartEntity entity = new MultipartEntity(
                            HttpMultipartMode.BROWSER_COMPATIBLE);
                if(income_tax_id == null) {
                    entity.addPart("name", new StringBody("" + nam));
                    entity.addPart("email", new StringBody("" + emai));
                    entity.addPart("adhan_no", new StringBody("" + adhan));
                    entity.addPart("pan_no", new StringBody("" + pan));
                    entity.addPart("mobile", new StringBody("" + mobi));
                    entity.addPart("ret_email", new StringBody("" + r_mai));
                    entity.addPart("ret_mobile", new StringBody("" + r_mob));
                    entity.addPart("reference_referal_code", new StringBody(""+ref_co));
                }else{

                }
                if(c1>=1){
//                    entity.addPart("return_copy_upload", new FileBody(pic0));
//                    result = Utilities.postEntityAndFindJson("http://ihisaab.com/ihisaabv2/Api/taxreturn", entity);
//                    c1=0;
                    if(income_tax_id !=null){
                        entity.addPart("pan_no", new StringBody("" + income_tax_id));
                        entity.addPart("return_upload", new FileBody(pic0));
////                        result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        result = Utilities.postEntityAndFindJson("https://ihisaab.com/Api/gettaxexistaccount", entity);

                    } else{
                        entity.addPart("return_upload", new FileBody(pic0));
////                        result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        result = Utilities.postEntityAndFindJson("http://ihisaab.com/Api/taxreturn", entity);
                    }
                }
                if(c2>=1){
                    if(income_tax_id !=null){
                        entity.addPart("pan_no", new StringBody("" + income_tax_id));
                        entity.addPart("form_upload", new FileBody(pic0));
////                        result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        result = Utilities.postEntityAndFindJson("https://ihisaab.com/Api/gettaxexistaccount", entity);

                    } else{
                        entity.addPart("form_upload", new FileBody(pic0));
////                        result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        result = Utilities.postEntityAndFindJson("http://ihisaab.com/Api/taxreturn", entity);
                    }
                }
                if(c3>=1){
                    if(income_tax_id !=null){
                        entity.addPart("pan_no", new StringBody("" + income_tax_id));
                        entity.addPart("bank_upload", new FileBody(pic0));
////                        result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        result = Utilities.postEntityAndFindJson("https://ihisaab.com/Api/gettaxexistaccount", entity);

                    } else{
                        entity.addPart("bank_upload", new FileBody(pic0));
////                        result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        result = Utilities.postEntityAndFindJson("http://ihisaab.com/Api/taxreturn", entity);
                    }
                }
                if(c4>=1){
                    if(income_tax_id !=null){
                        entity.addPart("pan_no", new StringBody("" + income_tax_id));
                        entity.addPart("other_upload", new FileBody(pic0));
////                        result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        result = Utilities.postEntityAndFindJson("https://ihisaab.com/Api/gettaxexistaccount", entity);

                    } else{
                        entity.addPart("other_upload", new FileBody(pic0));
////                        result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        result = Utilities.postEntityAndFindJson("http://ihisaab.com/Api/taxreturn", entity);
                    }
                }
                if(c5>=1){
                    if(income_tax_id !=null){
                        entity.addPart("pan_no", new StringBody("" + income_tax_id));
                        entity.addPart("house_loan_upload", new FileBody(pic0));
////                        result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        result = Utilities.postEntityAndFindJson("https://ihisaab.com/Api/gettaxexistaccount", entity);

                    } else{
                        entity.addPart("house_loan_upload", new FileBody(pic0));
////                        result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        result = Utilities.postEntityAndFindJson("http://ihisaab.com/Api/taxreturn", entity);
                    }
                }
//                else
//                {
//
////                    entity.addPart("return_copy", new FileBody(pic0));
//////                        result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
//
//                }
             //   entity.addPart("return_copy", new FileBody(pic0));
              //  result = Utilities.postEntityAndFindJson("http://ihisaab.com/Api/taxreturn", entity);
                //  ret_co =0;

                if(ret_co>0){
                    if(income_tax_id !=null){
                       entity.addPart("pan_no", new StringBody("" + income_tax_id));
                        entity.addPart("return_copy", new FileBody(pic0));
////                        result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        result = Utilities.postEntityAndFindJson("https://ihisaab.com/Api/gettaxexistaccount", entity);

                    } else{
                        entity.addPart("return_copy", new FileBody(pic0));
////                        result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        result = Utilities.postEntityAndFindJson("http://ihisaab.com/Api/taxreturn", entity);
                    }

                    }
                  if(f_c >0){
                      if(income_tax_id !=null){
                          entity.addPart("pan_no", new StringBody("" + income_tax_id));
                          entity.addPart("form_copy",new FileBody(pic0));
//                      result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
//                     // result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                          result = Utilities.postEntityAndFindJson("https://ihisaab.com/Api/gettaxexistaccount", entity);

                      }else{
                          entity.addPart("form_copy",new FileBody(pic0));
//                      result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
//                     // result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                          result = Utilities.postEntityAndFindJson("http://ihisaab.com/Api/taxreturn", entity);
                      }
//

                  }
               if(o_c>0){
                   if(income_tax_id !=null){
                       entity.addPart("pan_no", new StringBody("" + income_tax_id));
                       entity.addPart("other_copy",new FileBody(pic0));
//                   result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
//                   //result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                       result = Utilities.postEntityAndFindJson("https://ihisaab.com/Api/gettaxexistaccount", entity);

                   }else{

                       entity.addPart("other_copy",new FileBody(pic0));
//                   result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
//                   //result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                       result = Utilities.postEntityAndFindJson("http://ihisaab.com/Api/taxreturn", entity);
                   }

               }
                if(b_c>0){
                    if(income_tax_id !=null){
                        entity.addPart("pan_no", new StringBody("" + income_tax_id));
                        entity.addPart("bank_copy",new FileBody(pic0));
//                    result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
//                 //   result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        result = Utilities.postEntityAndFindJson("https://ihisaab.com/Api/gettaxexistaccount", entity);

                    }else{
                        entity.addPart("bank_copy",new FileBody(pic0));
//                    result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
//                 //   result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        result = Utilities.postEntityAndFindJson("http://ihisaab.com/Api/taxreturn", entity);
                    }
//                    ret_co =0;
//                    o_c =0;
//                    f_c=0;
//                    //  b_c=0;
//                    h_l_c=0;
                  //  entity.addPart("bank_copy",new FileBody(pic0));
      //              Toast.makeText(Single_user_act_2.this, ""+b_c, Toast.LENGTH_SHORT).show();
//                    MultipartEntity entity_b_c = new MultipartEntity(
//                            HttpMultipartMode.BROWSER_COMPATIBLE);
//                    entity_b_c.addPart("name",new StringBody(""+nam));
//                    entity_b_c.addPart("email",new StringBody(""+emai));
//                    entity_b_c.addPart("adhan_no", new StringBody(""+adhan));
//                    entity_b_c.addPart("pan_no",new StringBody(""+pan));
//                    entity_b_c.addPart("mobile",new StringBody(""+mobi));
//                    entity_b_c.addPart("ret_email",new StringBody(""+r_mai));
//                    entity_b_c.addPart("ret_mobile",new StringBody(""+r_mob));
//

                  //    b_c=0;


                }
                if(h_l_c>0){
                    if(income_tax_id !=null){
                        entity.addPart("pan_no", new StringBody("" + income_tax_id));
                        entity.addPart("house_loan_copy",new FileBody(pic0));
                        //            Toast.makeText(Single_user_act_2.this, ""+h_l_c, Toast.LENGTH_SHORT).show();
                        //  result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        //     result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        result = Utilities.postEntityAndFindJson("https://ihisaab.com/Api/gettaxexistaccount", entity);

                    }else{

                        entity.addPart("house_loan_copy",new FileBody(pic0));
                        //            Toast.makeText(Single_user_act_2.this, ""+h_l_c, Toast.LENGTH_SHORT).show();
                        //  result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        //     result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                        result = Utilities.postEntityAndFindJson("http://ihisaab.com/Api/taxreturn", entity);
                    }
//                    re
//                  //  h_l_c=0;
//                    ret_co =0;
//                    o_c =0;
//                    f_c=0;
//                    b_c=0;
//                    MultipartEntity entity_h_l_c = new MultipartEntity(
//                            HttpMultipartMode.BROWSER_COMPATIBLE);
//                    entity_h_l_c.addPart("name",new StringBody(""+nam));
//                    entity_h_l_c.addPart("email",new StringBody(""+emai));
//                    entity_h_l_c.addPart("adhan_no", new StringBody(""+adhan));
//                    entity_h_l_c.addPart("pan_no",new StringBody(""+pan));
//                    entity_h_l_c.addPart("mobile",new StringBody(""+mobi));
//                    entity_h_l_c.addPart("ret_email",new StringBody(""+r_mai));
//                    entity_h_l_c.addPart("ret_mobile",new StringBody(""+r_mob));




                  //  h_l_c=0;

                }

//                entity.addPart("form_copy", new FileBody(null));
//                entity.addPart("bank_copy", new FileBody(null));
//                entity.addPart("other_copy", new FileBody(null));
//                entity.addPart("house_loan_copy",  new FileBody(null));

                //  result = Utilities.postEntityAndFindJson("http://api.jvmsch.com/api/PolicyNoimg", entity);
               // result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
                return result;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            String result1=result;
            if(result1!=null){
                dialog.dismiss();
                Log.e("result1",result1);

              //  Toast.makeText(Single_user_act_2.this,""+result1,Toast.LENGTH_LONG).show();

                //   Intent in=new Intent(MainActivity.this,NextActivity.class);
                //  in.putExtra("doc",doc);
                //     startActivity(in);

            }else{
                dialog.dismiss();
                Toast.makeText(Single_user_act_2.this,"Some Problem",Toast.LENGTH_LONG).show();
            }

        }}

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Single_user_act_2.this , MainRadioActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
    private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 600;
        int targetH = 600;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(ctx.getContentResolver()
                .openInputStream(uri), null, bmOptions);
    }
}
