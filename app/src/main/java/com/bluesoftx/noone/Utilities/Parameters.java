package com.bluesoftx.noone.Utilities;

import android.Manifest;

import com.google.firebase.auth.FirebaseAuth;

public class Parameters {

    /**
     * Init
     */
    public static final String[] permissionsRequired = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static final int MY_PERMISSIONS_REQUEST_PICTURES = 99;
    public static final String PERMISSIONS_RESULT_NOT_MESSAGE = "We need your permission";
    public static String UserUid(){
        if(!FirebaseAuth.getInstance().getCurrentUser().getUid().isEmpty()){
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        }else{
            return null;
        }
    }




    /**
     * Message
     */
    public static final String PREFS_USER = "user";
    public static final String FCK_TOKEN = "fcmToken";
    public static final String WHO_SEND = "whoSend";
    public static final String MESSAGE = "message";
    /**
     *      Firebase
      */
    public static final String NO_USER_PAIRING = "1-999999999999";
    public static final String USER_PAIRING = "0-";
    public static final String FIREBASE_REALDB_CHILE_NAME_ID = "id";
    public static final String FIREBASE_REALDB_CHILE_PAIRING_NU = "0";
    public static final String FIREBASE_REALDB_CHILE_PAIRED_NU = "1";
    public static final String FIREBASE_REALDB_CHILD_STRING = "user";


    /**
     * User Fragment
     */
    public static final String[] LIST_TITLE = {"註冊時間"};


}
