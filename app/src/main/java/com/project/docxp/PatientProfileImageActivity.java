package com.project.docxp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.project.docxp.bean.ProfileBean;
import com.project.docxp.services.ProfileServices;

import java.io.ByteArrayOutputStream;

public class PatientProfileImageActivity extends AppCompatActivity implements View.OnClickListener {


    ImageView imageview_pateint_profile_image;
    Button button_pateint_profile_image_select, button_pateint_profile_image_cancle;
    Bitmap bmp;
    ProgressDialog progressDialog;

    byte[] byteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pateint_profile_image);

        progressDialog = new ProgressDialog(PatientProfileImageActivity.this);
        System.out.println("====get image===");
        imageview_pateint_profile_image = (ImageView) findViewById(R.id.imageview_pateint_profile_image);
        Bundle extras = getIntent().getExtras();
        System.out.println("====extra==");
        byteArray = extras.getByteArray("profileimage");

        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        imageview_pateint_profile_image.setImageBitmap(bmp);

        button_pateint_profile_image_select = (Button) findViewById(R.id.button_pateint_profile_image_select);
        button_pateint_profile_image_select.setOnClickListener(this);

        button_pateint_profile_image_cancle = (Button) findViewById(R.id.button_pateint_profile_image_cancle);
        button_pateint_profile_image_cancle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_pateint_profile_image_cancle: {
                Intent intent = new Intent(PatientProfileImageActivity.this, PatientProfileActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button_pateint_profile_image_select: {
                Intent intent = new Intent(PatientProfileImageActivity.this, PatientProfileActivity.class);
                startActivity(intent);
                break;
                 /* System.out.println("====in click listener===");
                bmp = ((BitmapDrawable) imageview_pateint_profile_image.getDrawable()).getBitmap();
                System.out.println("===bitmap====" + bmp);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                System.out.println("========bytearraystream" + byteArrayOutputStream);
                bmp.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
                System.out.println("==compres===");
                byte[] imageBytes = byteArrayOutputStream.toByteArray();
                System.out.println("imagebyte====" + imageBytes);
                String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                System.out.println("================base64imageString" + imageString);


                Intent intent= new Intent(PatientProfileImageActivity.this,PatientProfileActivity.class);
                intent.putExtra("profileimage",byteArray);
                intent.putExtra("imagestring",imageString);
                startActivity(intent);

              ProfileBean profileBean = new ProfileBean();
                profileBean.setProfile_image(imageString);
                System.out.println("==got ot services==");
                ProfileServices profileServices = new ProfileServices();
                profileServices.updatePateintImage(progressDialog, profileBean, "profileimage", PatientProfileImageActivity.this);

                break;*/
            }
        }
    }
}
