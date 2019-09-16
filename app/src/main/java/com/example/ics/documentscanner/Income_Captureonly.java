package com.example.ics.documentscanner;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class Income_Captureonly extends AppCompatActivity {
    PdfDocument pdfDocument;
    private int REQUEST_CAMERA = 1, SELECT_FILE = 0;
    private String userChoosenTask;
    // public static ByteArrayBody bab_reg_plate = null;
    int call_back = 0;
    SharedPreferences sharedpreferences_cus;
    SharedPreferences.Editor editor_cus;
    String store_cus;
    String income_tax_id;
    private int PICK_IMAGE_REQUEST = 1;
    //Uri to store the image uri
    private Uri filePath;
    private Bitmap bitmap = null;
    private Bitmap scanned_bitmap;
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

    private static String imageStoragePath;
    private Bundle savedInstanceState;
    String cus_id2;
    String to_show_cus_id;
    File offline_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income__captureonly);
        income_tax_id = getIntent().getStringExtra("cust_code");
        cus_id2 = getIntent().getStringExtra("cus");
        //   offline_concept();



    }
}
