package com.bluesoftx.noone.Activity;

import static com.bluesoftx.noone.Fragment.MessageFragment.mMessageAdapter;
import static com.bluesoftx.noone.Fragment.MessageFragment.messagesArrayList;
import static com.bluesoftx.noone.Fragment.MessageFragment.updateDB;
import static com.bluesoftx.noone.Utilities.Parameters.FIREBASE_REALDB_CHILD_STRING;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bluesoftx.noone.Adapter.MainViewPagerAdapter;
import com.bluesoftx.noone.AsyncTask.GetTalkingUser;
import com.bluesoftx.noone.AsyncTask.GetUserProfile;
import com.bluesoftx.noone.Fragment.HomeFragment;
import com.bluesoftx.noone.Fragment.MessageFragment;
import com.bluesoftx.noone.Models.Messages;
import com.bluesoftx.noone.R;
import com.bluesoftx.noone.Room.MessagesDB;
import com.bluesoftx.noone.Room.RoomDao;
import com.bluesoftx.noone.Room.RoomIdDatabase;
import com.bluesoftx.noone.Fragment.SearchFragment;
import com.bluesoftx.noone.Fragment.UserFragment;
import com.bluesoftx.noone.Utilities.FirebaseParameters;
import com.bluesoftx.noone.Utilities.Parameters;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    public static final String userUid = Parameters.UserUid();
    public static MainViewPagerAdapter mainViewPagerAdapter;
    public static BottomNavigationView mainBottomNavigationView;
    public static ViewPager2 mainViewpager2;
    public RoomIdDatabase db;
    public static DatabaseReference mDatabase;
    public final Context context = this;
    public static RoomDao roomDao;
    public static SupportSQLiteDatabase sdb, adb;
    public static final String myUid = FirebaseParameters.mAuth.getUid();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = RoomIdDatabase.getInstance(this);
        adb = db.getOpenHelper().getReadableDatabase();
        sdb = db.getOpenHelper().getWritableDatabase();
        roomDao = RoomIdDatabase.getInstance(context).roomDao();

        mDatabase = FirebaseParameters.myRef;

        mainViewpager2 = findViewById(R.id.main_viewpager2);
        mainBottomNavigationView = findViewById(R.id.main_bottom_navigation);

        /**
         * Who text me
         *
         *
         *
         *
         */
        mDatabase.child(FIREBASE_REALDB_CHILD_STRING).child(userUid).child(Parameters.WHO_SEND).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String senderId = snapshot.getKey();
                if(senderId != null){
                    GetUserProfile getUserProfile = new GetUserProfile(context, senderId);
                    try {
                        getUserProfile.execute().get();
                        MessagesDB messagesDB = getUserProfile.selectAllMessagesDB();
                        if(messagesDB != null){
                            for(DataSnapshot ds : snapshot.getChildren()) {
                                Messages messages = ds.getValue(Messages.class);
                                if(messages != null){
                                    String sender = messages.getSenderId();
                                    String senderText = messages.getMessage();
                                    long sendTimeStamp = messages.getTimestamp();
                                    String insertSQL =  "INSERT INTO '" + senderId + "' (sender, message, time) " +
                                            "VALUES ('" + sender + "','" + senderText + "'," + sendTimeStamp + ")";
                                    sdb.execSQL(insertSQL);
                                    updateDB(senderId);
                                }
                            }
                            mDatabase.child(FIREBASE_REALDB_CHILD_STRING).child(userUid).child(Parameters.WHO_SEND).child(senderId).removeValue();

                        }
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /**
         * ViewPager
         * 3 Fragment in ViewPager
         *
         */
        mainViewPagerAdapter = new MainViewPagerAdapter(this);
        GetTalkingUser getTalkingUser = new GetTalkingUser(this);
        try {
            getTalkingUser.execute().get();
            MessagesDB messagesDB = getTalkingUser.getTalkingUser();
            if(messagesDB != null) {
                changeMessageFragment();
            }else{
                mainViewPagerAdapter.addAllFragments();
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        mainViewpager2.setAdapter(mainViewPagerAdapter);

        mainViewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:{
                        mainBottomNavigationView.setSelectedItemId(R.id.bottom_button_home);
                        break;
                    }case 1: {
                        mainBottomNavigationView.setSelectedItemId(R.id.bottom_button_user);
                        break;
                    }default: {
                        break;
                    }
                }
            }
        });
        /**
         * BottomNavigationView
         * Bottom of home
         * Bottom of search
         * Bottom of user profile
         *
         */
        mainBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String itemTitle = (String) item.getTitle();
                switch (itemTitle) {
                    case "主頁":
                        mainViewpager2.setCurrentItem(0);
                        return true;
                    case "搜尋":
                        mainViewpager2.setCurrentItem(1);
                        return true;
                    case "我的資料":
                        mainViewpager2.setCurrentItem(2);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
    public void changeMessageFragment(){
        MessageFragment messageFragment = new MessageFragment();
        mainViewPagerAdapter.removeFragment();
        mainViewPagerAdapter.addFragment(messageFragment, "message",0);
        mainViewpager2.setAdapter(mainViewPagerAdapter);
        mainBottomNavigationView.setVisibility(View.GONE);
    }
}