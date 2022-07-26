package com.bluesoftx.noone.Models;

/**
 * @param userUid = ID from Firebase Authentication
 * @param name
 * @param phone
 * @param fcmToken
 * @param status
 */


public class User {

    String userUid;
    String name;
    String phone;
    String fcmToken;


    public User(String userUid, String name, String phone, String fcm){
        this.userUid = userUid;
        this.name = name;
        this.phone = phone;
        this.fcmToken = fcmToken;
    }
}
