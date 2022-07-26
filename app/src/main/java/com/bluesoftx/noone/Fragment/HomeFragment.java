package com.bluesoftx.noone.Fragment;

import static com.bluesoftx.noone.Activity.MainActivity.mDatabase;
import static com.bluesoftx.noone.Activity.MainActivity.mainBottomNavigationView;
import static com.bluesoftx.noone.Activity.MainActivity.mainViewPagerAdapter;
import static com.bluesoftx.noone.Activity.MainActivity.mainViewpager2;
import static com.bluesoftx.noone.Activity.MainActivity.myUid;
import static com.bluesoftx.noone.Activity.MainActivity.sdb;
import static com.bluesoftx.noone.Utilities.Parameters.FIREBASE_REALDB_CHILE_NAME_ID;
import static com.bluesoftx.noone.Utilities.Parameters.FIREBASE_REALDB_CHILE_PAIRED_NU;
import static com.bluesoftx.noone.Utilities.Parameters.FIREBASE_REALDB_CHILE_PAIRING_NU;
import static com.bluesoftx.noone.Utilities.Parameters.NO_USER_PAIRING;
import static com.bluesoftx.noone.Utilities.Parameters.USER_PAIRING;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bluesoftx.noone.AsyncTask.GetTalkingUser;
import com.bluesoftx.noone.AsyncTask.InsertMessagesTable;
import com.bluesoftx.noone.Fragment.MessageFragment;
import com.bluesoftx.noone.R;
import com.bluesoftx.noone.Room.MessagesDB;
import com.bluesoftx.noone.Room.RoomIdDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class HomeFragment extends Fragment {

    private Button homeButtonStart;
    public boolean paring = false;
    final Handler handler = new Handler();
    public TextView homeTextCounters;
    private int seconds = 0, startTime;
    private String clientId;
    private ValueEventListener pairingListener;

    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeTextCounters = view.findViewById(R.id.home_text_counters);
        homeButtonStart = view.findViewById(R.id.home_button_start);

        /**
         * Created By Tim at 2022/06/23 0800
         * Counter
         * Show Message Room.
         * Next: Paring
         */
        pairingListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clientId = (String) snapshot.getValue();
                if (clientId != null && !clientId.equals(FIREBASE_REALDB_CHILE_PAIRING_NU)) {
                    changeViewPaired();
                    createUserTable();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };

        homeButtonStart.setOnClickListener(view1 -> {
            if (!paring){
                mDatabase.child(FIREBASE_REALDB_CHILE_NAME_ID).child(FIREBASE_REALDB_CHILE_PAIRING_NU).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        handler.post(timerRunnable);
                        homeButtonStart.setText(R.string.stop_paring);
                        paring = true;
                        Log.i("message", String.valueOf(snapshot.exists()));
                        if(snapshot.exists()){
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                clientId = postSnapshot.getKey();
                                assert clientId != null;
                                if(clientId.equals(NO_USER_PAIRING)){
                                    waitingPair();
                                }else{
                                    mDatabase.child(FIREBASE_REALDB_CHILE_NAME_ID).child(FIREBASE_REALDB_CHILE_PAIRED_NU).child(clientId).setValue(myUid);
                                    mDatabase.child(FIREBASE_REALDB_CHILE_NAME_ID).child(FIREBASE_REALDB_CHILE_PAIRED_NU).child(myUid).setValue(clientId);
                                    changeViewPaired();
                                    createUserTable();
                                }
                                break;
                            }
                        }else{
                            waitingPair();
                        }
                    }
                    @Override
                    public void onCancelled( @NonNull DatabaseError error) { Log.i("Message",error.getMessage()); }
                });
            }else{
                stopCounters();
            }

        });
        return view;
    }
    private void waitingPair(){
        mDatabase.child(FIREBASE_REALDB_CHILE_NAME_ID).child(FIREBASE_REALDB_CHILE_PAIRING_NU).child(myUid).setValue(FIREBASE_REALDB_CHILE_PAIRING_NU);
        mDatabase.child(FIREBASE_REALDB_CHILE_NAME_ID).child(FIREBASE_REALDB_CHILE_PAIRED_NU).child(myUid).setValue(FIREBASE_REALDB_CHILE_PAIRING_NU);
        mDatabase.child(FIREBASE_REALDB_CHILE_NAME_ID).child(FIREBASE_REALDB_CHILE_PAIRED_NU).child(myUid).addValueEventListener(pairingListener);
    }


    private void stopCounters(){
        homeButtonStart.setText(R.string.start_pairing);
        handler.removeCallbacks(timerRunnable);
        homeTextCounters.setText("");
        mDatabase.child(FIREBASE_REALDB_CHILE_NAME_ID).child(FIREBASE_REALDB_CHILE_PAIRED_NU).child(myUid).removeEventListener(pairingListener);
        mDatabase.child(FIREBASE_REALDB_CHILE_NAME_ID).child(FIREBASE_REALDB_CHILE_PAIRING_NU).child(myUid).removeValue();
        mDatabase.child(FIREBASE_REALDB_CHILE_NAME_ID).child(FIREBASE_REALDB_CHILE_PAIRED_NU).child(myUid).removeValue();
        seconds = 0;
        paring = false;
    }
    private void changeViewPaired(){
        stopCounters();
        MessageFragment messageFragment = new MessageFragment();
        mainViewPagerAdapter.removeFragment();
        mainViewPagerAdapter.addFragment(messageFragment, "message",0);
        mainViewpager2.setAdapter(mainViewPagerAdapter);
        mainBottomNavigationView.setVisibility(View.GONE);

//        String delFirst = clientId.substring(2);
//        String delLast = delFirst.substring(0, delFirst.length() - 10);

    }
    private void createUserTable(){
        /**
         * Create a table for chatting
         * 1. client id
         * 2. who send the message
         * 3. message
         * 4. time
         * 2Create a user table for feature in the future,
         * 1. uid
         * 2. fcm token
         * 3. status
         */
        sdb.execSQL("CREATE TABLE if not exists '" +  clientId + "'(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "sender TEXT  NOT NULL," +
                "message TEXT  NOT NULL," +
                "time INTEGER  NOT NULL) "
        );
        MessagesDB messagesDB = new MessagesDB(clientId,"",1);
        InsertMessagesTable insertMessagesTable = new InsertMessagesTable(getContext(), messagesDB);
        insertMessagesTable.execute();
    }
    private final Runnable timerRunnable = new Runnable(){
        public void run() {
            int hours = seconds / 3600;
            int minutes = (seconds % 3600) / 60;
            int secs = seconds % 60;
            String time = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours,
                    minutes, secs);
            homeTextCounters.setText(time);
            seconds++;
            handler.postDelayed(this, 1000);
        }
    };
}