package com.project.docxp.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Lenovo on 16/12/2017.
 */

public class DocXpPermissions {
    public static Context context;
    public static final int VERIFY_READ_WRITE_CAMERA_PERMISSIONS_REQUEST = 1;
    public static final int VERIFY_LOCATION_PERMISSIONS_REQUEST = 2;

    public static final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    public static final String[] LOCATION_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    //Verify Read Write Camera Permission------------------------------------------------------------------
    public static void verifyPermissionsReadWriteCamera(String[] permissions, Context context){
        ActivityCompat.requestPermissions(
                (Activity) context,
                permissions,
                VERIFY_READ_WRITE_CAMERA_PERMISSIONS_REQUEST);
    }

    //Verify Read Write Camera Permission------------------------------------------------------------------
    public static void verifyLocationPermission(String[] permissions, Context context){
        ActivityCompat.requestPermissions(
                (Activity) context,
                permissions,
                VERIFY_LOCATION_PERMISSIONS_REQUEST);
    }


    //Check an array of permissions
    public static boolean checkPermissionsArray(String[] permissions, Context context){
        for(int i = 0; i< permissions.length; i++){
            String check = permissions[i];
            if(!checkPermissions(check, context)){
                return false;
            }
        }
        return true;
    }

    //Check a single permission is it has been verified
    public static boolean checkPermissions(String permission, Context context){
        int permissionRequest = ActivityCompat.checkSelfPermission(context, permission);
        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            return false;
        }
        else{
            return true;
        }
    }

}
