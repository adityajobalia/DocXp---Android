package com.project.docxp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.project.docxp.bean.RegisterBean;
import com.project.docxp.services.RegisterServices;
import com.project.docxp.utility.CameraExternalStoragePermission;
import com.project.docxp.utility.ConnectivityReceiver;
import com.project.docxp.utility.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private CardView register_cardView;

    private CircleImageView login_circular_logo;
    private Bitmap imgBitmap;
    private String imageFileName;
    private String mCurrentPhotoPath;

    private static int CAMERA_REQUEST_CODE = 1000;
    private static int GALLERY_REQUEST_CODE = 1020;
    Context context = this;

    private ProgressDialog progressDialog;
    private Button register_button_register;
    private EditText register_edittext_name, register_edittext_password, register_edittext_email, register_edittext_mobile;
    private AwesomeValidation awesomeValidation;
    boolean networkConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        checkConnection(getApplicationContext());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getRegisterActivityId();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.detectFileUriExposure();
        }
        login_circular_logo.setOnClickListener(this);

        register_cardView.setElevation(24.0f);
        progressDialog = new ProgressDialog(RegisterActivity.this);

    }

    private void getRegisterActivityId() {
        register_cardView = (CardView) findViewById(R.id.register_cardview);

        register_edittext_name = (EditText) findViewById(R.id.register_edittext_name);
        register_edittext_email = (EditText) findViewById(R.id.register_edittext_email);
        register_edittext_password = (EditText) findViewById(R.id.register_edittext_password);
        register_edittext_mobile = (EditText) findViewById(R.id.register_edittext_mobile);

        login_circular_logo = (CircleImageView) findViewById(R.id.login_circular_logo);
        register_button_register = (Button) findViewById(R.id.register_button_register);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //adding validation to edittexts

        awesomeValidation.addValidation(this, R.id.register_edittext_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.register_edittext_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.register_edittext_password, ".{6,}", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.register_edittext_mobile, "^[0-9]{10}$", R.string.mobileerror);

        register_button_register.setOnClickListener(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED) {
                    Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * Profile picture code implementation
             */
            case R.id.login_circular_logo: {
                boolean permission = CameraExternalStoragePermission.checkPermissionsArray(CameraExternalStoragePermission.PERMISSIONS, context);
                if (permission) {

                    selectImage();
                } else {
                    CameraExternalStoragePermission.verifyPermissionsReadWriteCamera(CameraExternalStoragePermission.PERMISSIONS, context);
                }


                break;
            }
            case R.id.register_button_register: {
                networkConnection = checkConnection(getApplicationContext());
                if (networkConnection) {
                    String name = register_edittext_name.getText().toString().trim();
                    String email = register_edittext_email.getText().toString().trim();
                    String password = register_edittext_password.getText().toString().trim();
                    String mobile = register_edittext_mobile.getText().toString().trim();

                    imgBitmap = ((BitmapDrawable) login_circular_logo.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    imgBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();
                    final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    System.out.println("================base64imageString" + imageString);
                    Toast.makeText(context, "imagesuccess", Toast.LENGTH_SHORT).show();

                    RegisterBean regBean = new RegisterBean();

                    if (awesomeValidation.validate()) {
                        regBean.setProfileimage(imageString);
                        regBean.setName(name);
                        regBean.setEmail(email);
                        regBean.setPassword(password);
                        regBean.setMobile(mobile);

                        progressDialog.setMessage("Registering User...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();

                        RegisterServices registerServices = new RegisterServices();
                        registerServices.verifyUser(regBean, RegisterActivity.this, progressDialog);
                    }
                }
                break;
            }
        }
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {
                    //capture image from Camera
                    captureImage();

                } else if (options[item].equals("Choose from Gallery"))

                {
                    //choose photo from gallary

                    Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(galleryintent, GALLERY_REQUEST_CODE);

                } else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }

    //camera
    private void captureImage() {
        Intent cameraiIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraiIntent.resolveActivity(getPackageManager()) != null) {
            File photofile = null;

            try {
                photofile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photofile != null) {
                cameraiIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photofile));
                startActivityForResult(cameraiIntent, CAMERA_REQUEST_CODE);
            }

        }
    }

    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "CJPEG_" + timestamp + "-";
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/HealingTouch/Images");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            setPic();
        }

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data.getData() != null) {
            Uri uri = data.getData();
            try {
                if (imgBitmap != null) {
                    imgBitmap.recycle();
                }
                imgBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                login_circular_logo.setImageBitmap(imgBitmap);
                saveProfilePicture();

            } catch (IOException e) {
                System.out.println("=====exception========" + e);
            }
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetw = login_circular_logo.getWidth();
        int targeth = login_circular_logo.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetw, photoH / targeth);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        imgBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        login_circular_logo.setImageBitmap(imgBitmap);
    }

    //for gallery

    private void saveProfilePicture() {

        String path = Environment.getExternalStorageDirectory() + "/HealingTouch/Images";

        File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            Calendar cal = Calendar.getInstance();
            int yyyy = cal.get(Calendar.YEAR);
            int MM = cal.get(Calendar.MONTH);
            int dd = cal.get(Calendar.DATE);
            int mm = cal.get(Calendar.MINUTE);
            int ss = cal.get(Calendar.SECOND);
            int HH = cal.get(Calendar.HOUR);

            imageFileName = "GJPEG_" + yyyy + "" + MM + "" + dd + "_" + HH + "" + mm + "" + ss + ".jpg";
            File imgfile = new File(file, imageFileName);
            FileOutputStream fileOutputStream = new FileOutputStream(imgfile);
            imgBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    // Method to manually check connection status
    private boolean checkConnection(Context applicationContext) {
        boolean isConnected = ConnectivityReceiver.isConnected(applicationContext);
        boolean networkStatus = showSnack(isConnected);
        return networkStatus;
    }

    // Showing the status in Snackbar
    private boolean showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "";
            color = Color.WHITE;
        } else {
            message = "No Internet Connection";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.register_button_register), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);

        if (message.equals("")) {
            return true;
        } else {
            snackbar.show();
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }
    @Override
    public void onBackPressed() {
        RegisterActivity.super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
    }
}
