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

public class DocProfileImageActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView imageview_doc_profile_image;
    Button button_doc_profile_image_select,button_doc_profile_image_cancle;
    Bitmap bmp;
    ProgressDialog progressDialog;

    byte[] byteArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_profile_image);

        progressDialog = new ProgressDialog(DocProfileImageActivity.this);

        System.out.println("====get image===");
        imageview_doc_profile_image = (ImageView) findViewById(R.id.imageview_doc_profile_image);
        Bundle extras = getIntent().getExtras();
        System.out.println("====extra==");
        byteArray = extras.getByteArray("profileimage");

        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        imageview_doc_profile_image.setImageBitmap(bmp);

        button_doc_profile_image_select = (Button) findViewById(R.id.button_doc_profile_image_select);
        button_doc_profile_image_select.setOnClickListener(this);

        button_doc_profile_image_cancle=(Button) findViewById(R.id.button_doc_profile_image_cancle);
        button_doc_profile_image_cancle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_doc_profile_image_cancle:{
                Intent intent = new Intent(DocProfileImageActivity.this, DocProfileActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button_doc_profile_image_select:{
                System.out.println("====in click listener===");
                bmp = ((BitmapDrawable) imageview_doc_profile_image.getDrawable()).getBitmap();
                System.out.println("===bitmap====" + bmp);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                System.out.println("========bytearraystream" + byteArrayOutputStream);
                bmp.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
                System.out.println("==compres===");
                byte[] imageBytes = byteArrayOutputStream.toByteArray();
                System.out.println("imagebyte====" + imageBytes);
                String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                System.out.println("================base64imageString" + imageString);

                ProfileBean profileBean = new ProfileBean();
                profileBean.setProfile_image(imageString);
                System.out.println("==got ot services==");
               // ProfileServices profileServices = new ProfileServices();
                //profileServices.updateDocImage(progressDialog, profileBean, "profileimage", DocProfileActivity.this);


                //go back to profile

                Intent intent = new Intent(DocProfileImageActivity.this, DocProfileActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
