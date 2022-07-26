package com.bluesoftx.noone.Utilities;

import static com.bluesoftx.noone.Utilities.Parameters.FCK_TOKEN;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.work.OneTimeWorkRequest;

import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    SharedPreferences prefs = getSharedPreferences(Parameters.PREFS_USER, Context.MODE_PRIVATE);
    private static String time;
    private String title;
    private String body;
//    private Messages messages;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            body = remoteMessage.getNotification().getBody();
            title = remoteMessage.getNotification().getTitle();
            getDate(remoteMessage.getSentTime());
            int checked = prefs.getInt("notification_0",0);
            if(checked == 1){
//                NoOneNotification.messageSendNotification(this,title,body);
            }


        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    private void scheduleJob() {
        // [START dispatch_job]
//        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
//                .build();
//        WorkManager.getInstance().beginWith(work).enqueue();
        // [END dispatch_job]
    }
    private void handleNow() {
    }
    // [START on_new_token]
    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    @Override
    public void onNewToken(String token) {
        SharedPreferences prefs = getSharedPreferences(Parameters.PREFS_USER, Context.MODE_PRIVATE);
        prefs.edit().putString(FCK_TOKEN, token).apply();
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    public static void getDate(long milliSeconds) {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.TAIWAN);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        time = formatter.format(new Date(milliSeconds));
    }
}
