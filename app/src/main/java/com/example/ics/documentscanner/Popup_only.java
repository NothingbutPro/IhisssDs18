package com.example.ics.documentscanner;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.File;
import java.util.List;

import static com.example.ics.documentscanner.MainActivity.MEDIA_TYPE_IMAGE;

public class Popup_only extends AppCompatActivity {
    private int REQUEST_CAMERA = 1, SELECT_FILE = 0;
    private String userChoosenTask;
    // public static ByteArrayBody bab_reg_plate = null;
    int call_back = 0;
    private int PICK_IMAGE_REQUEST = 1;
    //Uri to store the image uri
    private Uri filePath;
    private Bitmap bitmap = null;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private String id = "1";
    private String mess;
    private String response_code;
    private String TAG = "ProfileActivity";
    private final int SELECT_PHOTO = 1;
    private String updatedprofilepic;
    private Uri file;
    private ImageView select_registrationplate;
    private Button next_reg_plate,skip_regplate;
    private String flag=null;
    private EditText etImageName;
    Toolbar toolbar_case_registrationplate;
    int Gallery_view=2;

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
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";

    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";

    private static String imageStoragePath;
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_only);
        selectImage();

    }

    private void requestCameraPermission(final int mediaTypeImage) {
        Dexter.withActivity(this)
                .withPermissions(android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (mediaTypeImage == MEDIA_TYPE_IMAGE) {
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
                Toast.makeText(this, "called once", Toast.LENGTH_SHORT).show();

                captureImage();

            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
                selectImage();
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

    private void selectImage() {
        //------------
        final CharSequence[] items = {"Yes", "No"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Popup_only.this);
        builder.setTitle("Do you wan to upload more?");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Popup_only.this);

                if (items[item].equals("Yes")) {
                    if (result)
                        if (CameraUtils.checkPermissions(getApplicationContext())) {
                            Toast.makeText(Popup_only.this, "Capture image called", Toast.LENGTH_SHORT).show();
                            captureImage();
                            Toast.makeText(Popup_only.this, "we got result back", Toast.LENGTH_SHORT).show();

                            restoreFromBundle(savedInstanceState);
                        } else {
                            requestCameraPermission(MEDIA_TYPE_IMAGE);
                        }
                    // cameraIntent();

                }
                else if (items[item].equals("Exit")) {
                    //Intent intent = new Intent(MainActivity.this,MainRadioActivity.class);
                    //startActivity(intent);
                    // android.os.Process.killProcess(android.os.Process.myPid());
                    // moveTaskToBack(true);
                 //   System.runFinalizersOnExit(true);
                    finish();
                  //  moveTaskToBack(true);

                    // dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

    }


    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(Popup_only.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private void captureImage() {
        int ask_again = 0;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
            Toast.makeText(this, "File is not null", Toast.LENGTH_SHORT).show();
//            CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);
            // start the image capture Intent

//                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            Toast.makeText(getApplicationContext() ,"Step 2",Toast.LENGTH_LONG).show();
        }
        if(file == null){
            Toast.makeText(this, "File is null", Toast.LENGTH_SHORT).show();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);
        Toast.makeText(this, ""+fileUri, Toast.LENGTH_SHORT).show();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        Toast.makeText(getApplicationContext() ,"Step 3",Toast.LENGTH_LONG).show();

    }
    private void restoreFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_IMAGE_STORAGE_PATH)) {
                imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
                if (!TextUtils.isEmpty(imageStoragePath)) {
                    if (imageStoragePath.substring(imageStoragePath.lastIndexOf(".")).equals("." + IMAGE_EXTENSION)) {
                    }
                }
            }
        }
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
                new ImageUploadTask(imgFile).execute();

            }else{
                Toast.makeText(Popup_only.this, "Please select an image first", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    class ImageUploadTask extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;
        String result="";
        File pic;

        public ImageUploadTask(File imgFile) {
            this.pic = imgFile;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Popup_only.this);
            dialog.setMessage("Processing");

            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);


                entity.addPart("cust_code", new StringBody("435455"));
                entity.addPart("doc_type", new StringBody(etImageName.getText().toString()));
                entity.addPart("scan_date", new StringBody("14-08-2018"));
                entity.addPart("doc", new FileBody(pic));

                //  result = Utilities.postEntityAndFindJson("http://api.jvmsch.com/api/PolicyNoimg", entity);
                result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/customerDetails", entity);


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
                dialog.dismiss();
                Log.e("resultT",result);

                //   Intent in=new Intent(MainActivity.this,NextActivity.class);
                //  in.putExtra("doc",doc);
                //     startActivity(in);

            }else{
                dialog.dismiss();
                Toast.makeText(Popup_only.this,"Some Problem",Toast.LENGTH_LONG).show();
            }

        }
    }
}
