package com.project.docxp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.docxp.bean.ProfileBean;
import com.project.docxp.clicklistener.ProfilerecylerclickListener;
import com.project.docxp.customadapter.CustomProfileAdapter;
import com.project.docxp.services.ProfileServices;
import com.project.docxp.utility.CameraExternalStoragePermission;
import com.project.docxp.utility.ConnectivityReceiver;
import com.project.docxp.utility.ServerCrendentialsUtility;
import com.project.docxp.utility.SharedPreferenceData;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class DocProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private List<ProfileBean> listProfileBeen;
    private RecyclerView recyclerView;
    private CustomProfileAdapter customProfileAdapter;
    ProfileBean profileBean = new ProfileBean();
    private EditText dialogbox_edittext_old, dialogbox_edittext_new;
    private TextView dialogbox_textview_title;
    SharedPreferences preferences;
    private ProfileServices profileServices;
    ProgressDialog progressDialog;
    View view;
    private CircleImageView doc_profile_image;
    private FloatingActionButton fabbutton_doc_profile;
    private Bitmap imgBitmap, bitmap;
    private String imageFileName;
    private String mCurrentPhotoPath;
    private static int CAMERA_REQUEST_CODE = 1000;
    private static int GALLERY_REQUEST_CODE = 1020;
    Context context = this;
    private boolean networkConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_profile);

        System.out.println("=======in doc profile");
        doc_profile_image = (CircleImageView) findViewById(R.id.doc_profile_image);
        fabbutton_doc_profile = (FloatingActionButton) findViewById(R.id.fabbutton_doc_profile);
        fabbutton_doc_profile.setOnClickListener(this);
        progressDialog = new ProgressDialog(DocProfileActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.doc_recyclerview_profile);
        preferences = getSharedPreferences(SharedPreferenceData.SHAREPREF, MODE_PRIVATE);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        preferences = getSharedPreferences(SharedPreferenceData.SHAREPREF, MODE_PRIVATE);
        getProfileData();

        recyclerView.addOnItemTouchListener(new ProfilerecylerclickListener(getApplicationContext(), recyclerView, new ProfilerecylerclickListener.ClickListerner() {
            @Override
            public void onClick(View view, int position) {
                if (position == 2 || position == 0) {
                    Toast.makeText(DocProfileActivity.this, "You cannot change this field", Toast.LENGTH_SHORT).show();
                } else {
                    createDialogBox(view, position);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(DocProfileActivity.this, "longclick", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public void prepareData(ProfileBean profileBean) {
        listProfileBeen = new ArrayList<>();

        this.profileBean = new ProfileBean("Name", profileBean.getName(), R.drawable.update_btn, R.drawable.ic_user);
        listProfileBeen.add(this.profileBean);

        this.profileBean = new ProfileBean("Password", "* * * * *", R.drawable.update_btn, R.drawable.ic_password);
        listProfileBeen.add(this.profileBean);

        this.profileBean = new ProfileBean("Speciality", profileBean.getSpeciality(), R.drawable.update_btn, R.drawable.ic_stethoscope);
        listProfileBeen.add(this.profileBean);

        this.profileBean = new ProfileBean("Email", profileBean.getEmail(), R.drawable.update_btn, R.drawable.ic_email);
        listProfileBeen.add(this.profileBean);

        this.profileBean = new ProfileBean("Mobile", profileBean.getMobile(), R.drawable.update_btn, R.drawable.ic_mobile);
        listProfileBeen.add(this.profileBean);

        String imagepath = profileBean.getProfile_image();
        System.out.println("=====image" + imagepath);
        //Toast.makeText(context, "image" + imagepath, Toast.LENGTH_SHORT).show();
        Picasso.with(getApplicationContext()).load(ServerCrendentialsUtility.URL + imagepath).placeholder(R.drawable.profile).error(R.drawable.profile).into(doc_profile_image);
        setAdapter();
    }

    public void setAdapter() {
        customProfileAdapter = new CustomProfileAdapter(listProfileBeen);
        recyclerView.setAdapter(customProfileAdapter);
        customProfileAdapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    private void createDialogBox(View view, final int position) {
        this.view = view;
        final AlertDialog.Builder customDialog = new AlertDialog.Builder(DocProfileActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        view = inflater.inflate(R.layout.custom_dialogbox, null);
        customDialog.setView(view);

        dialogbox_edittext_old = (EditText) view.findViewById(R.id.dialogbox_edittext_old);
        dialogbox_edittext_new = (EditText) view.findViewById(R.id.dialogbox_edittext_new);
        dialogbox_textview_title = (TextView) view.findViewById(R.id.dialogbox_textview_title);

        verifyDialogBoxAccessibility(position);

        customDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String edittext_old, edittext_new;
                switch (position) {

                    case 1: {
                        edittext_old = dialogbox_edittext_old.getText().toString();
                        edittext_new = dialogbox_edittext_new.getText().toString();
                        dialogbox_edittext_old.setText(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_PASSWORD, ""));

                        if (TextUtils.isEmpty(edittext_old) || TextUtils.isEmpty(edittext_new)) {
                            Toast.makeText(DocProfileActivity.this, "All fields are compulsory!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (edittext_old.equals(preferences.getString(SharedPreferenceData.LOGIN_PASSWORD, ""))) {
                                if (networkConnection = checkConnection(getApplicationContext())) {
                                    progressDialog.setTitle("Please wait");
                                    progressDialog.setMessage("Updating password...");
                                    progressDialog.show();
                                    profileServices = new ProfileServices();
                                    profileBean.setPassword(edittext_new);
                                    profileServices.updateDoctorName(progressDialog, profileBean, "password", DocProfileActivity.this, position);
                                }
                                } else {
                                Toast.makeText(DocProfileActivity.this, "Please enter correct old password", Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                    case 3: {
                        edittext_old = dialogbox_edittext_old.getText().toString();
                        edittext_new = dialogbox_edittext_new.getText().toString();
                        if (TextUtils.isEmpty(edittext_old) || TextUtils.isEmpty(edittext_new)) {
                            Toast.makeText(DocProfileActivity.this, "All fields are compulsory!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (networkConnection = checkConnection(getApplicationContext())) {
                                progressDialog.setTitle("Please wait");
                                progressDialog.setMessage("Updating email...");
                                progressDialog.show();
                                profileServices = new ProfileServices();
                                profileBean.setEmail(edittext_new);
                                profileServices.updateDoctorName(progressDialog, profileBean, "email", DocProfileActivity.this, position);
                            }
                        }
                        break;
                    }
                    case 4: {
                        edittext_old = dialogbox_edittext_old.getText().toString();
                        edittext_new = dialogbox_edittext_new.getText().toString();
                        if (TextUtils.isEmpty(edittext_old) || TextUtils.isEmpty(edittext_new)) {
                            Toast.makeText(DocProfileActivity.this, "All fields are compulsory!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (networkConnection = checkConnection(getApplicationContext())) {
                                progressDialog.setTitle("Please wait");
                                progressDialog.setMessage("Updating mobile...");
                                progressDialog.show();
                                profileServices = new ProfileServices();
                                profileBean.setMobile(edittext_new);
                                profileServices.updateDoctorName(progressDialog, profileBean, "mobile", DocProfileActivity.this, position);
                            }
                        }
                        break;
                    }
                }
            }
        });

        customDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        customDialog.create();
        customDialog.show();

    }

    public void verifyDialogBoxAccessibility(int position) {
        switch (position) {
            case 1:
                dialogbox_textview_title.setText("Change Password");
                dialogbox_edittext_old.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                dialogbox_edittext_new.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                return;
            case 3:
                dialogbox_textview_title.setText("Change Email");
                dialogbox_edittext_old.setText(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_EMAIL, ""));
                dialogbox_edittext_old.setEnabled(false);
                return;
            case 4:
                dialogbox_textview_title.setText("Change Mobile");
                dialogbox_edittext_old.setText(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_MOBILE, ""));
                dialogbox_edittext_old.setEnabled(false);
                return;
        }
    }

    public void getProfileData() {
        System.out.println("======in get profile doc data ");
        String userEmail = preferences.getString(SharedPreferenceData.LOGIN_EMAIL, "");
        String userPassword = preferences.getString(SharedPreferenceData.LOGIN_PASSWORD, "");
        profileBean.setEmail(userEmail);
        profileBean.setPassword(userPassword);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading Profile...");
        progressDialog.show();
        System.out.println("=====g=o to doc profile services");
        profileServices = new ProfileServices();
        profileServices.getDoctorProfile(DocProfileActivity.this, profileBean, progressDialog);
        Log.d("getProfileData", "Next step");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabbutton_doc_profile: {
                boolean permission = CameraExternalStoragePermission.checkPermissionsArray(CameraExternalStoragePermission.PERMISSIONS, this);
                networkConnection = checkConnection(getApplicationContext());
                if (permission  && networkConnection) {

                    selectImage();


                } else {
                    CameraExternalStoragePermission.verifyPermissionsReadWriteCamera(CameraExternalStoragePermission.PERMISSIONS, this);
                }

                break;
            }
        }
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DocProfileActivity.this);

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
               /* if (imgBitmap != null) {
                    imgBitmap.recycle();
                }*/
                imgBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                doc_profile_image.setImageBitmap(imgBitmap);
                bitmap = ((BitmapDrawable) doc_profile_image.getDrawable()).getBitmap();
                System.out.println("===bitmap====" + bitmap);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                System.out.println("========bytearraystream" + byteArrayOutputStream);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                System.out.println("==compres===");
                byte[] imageBytes = byteArrayOutputStream.toByteArray();
                System.out.println("imagebyte====" + imageBytes);
                String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                System.out.println("================base64imageString" + imageString);
                /*progressDialog.setTitle("Please wait...");
                progressDialog.setMessage("Updating Profile...");
                progressDialog.show();*/
                profileBean.setProfile_image(imageString);
                profileServices.updateDocImage(progressDialog, profileBean, "profileimage", DocProfileActivity.this);
                /*System.out.println("===============start===");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                System.out.println("============sream=="+stream);
                imgBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] byteArray = stream.toByteArray();
                System.out.println("====bytearray=="+byteArray);
                Intent intent = new Intent(this, DocProfileImageActivity.class);
                System.out.println("===intent=="+intent);
                intent.putExtra("profileimage", byteArray);
                System.out.println("=====puextra==go to profile image");
                startActivity(intent);*/

                saveProfilePicture();

            } catch (IOException e) {
                System.out.println("=====exception========" + e);
            }


        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetw = doc_profile_image.getWidth();
        int targeth = doc_profile_image.getHeight();

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
        doc_profile_image.setImageBitmap(imgBitmap);
        bitmap = ((BitmapDrawable) doc_profile_image.getDrawable()).getBitmap();
        System.out.println("===bitmap====" + bitmap);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.out.println("========bytearraystream" + byteArrayOutputStream);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        System.out.println("==compres===");
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        System.out.println("imagebyte====" + imageBytes);
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        System.out.println("================base64imageString" + imageString);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Updating Profile...");
        progressDialog.show();
        profileBean.setProfile_image(imageString);
        profileServices.updateDocImage(progressDialog, profileBean, "profileimage", DocProfileActivity.this);

       /* System.out.println("===============start===");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        System.out.println("============sream=="+stream);
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        System.out.println("====bytearray=="+byteArray);
        Intent intent = new Intent(this, DocProfileImageActivity.class);
        System.out.println("===intent=="+intent);
        intent.putExtra("profileimage", byteArray);
        System.out.println("=====puextra==go to profile image");
        startActivity(intent);*/
        // pateint_profile_image.setImageBitmap(imgBitmap);

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
    public void onBackPressed() {
        DocProfileActivity.super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
    }

    private boolean checkConnection(Context applicationContext) {
        boolean isConnected = ConnectivityReceiver.isConnected(applicationContext);
        System.out.println("======isConnected======="+isConnected);
        boolean networkStatus = showSnack(isConnected);
        System.out.println("======networkStatus======"+networkStatus);
        return networkStatus;
    }

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
                .make(findViewById(R.id.doc_profile_image), message, Snackbar.LENGTH_LONG);

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


}

