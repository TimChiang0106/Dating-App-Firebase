package com.bluesoftx.noone.Fragment;

import static com.bluesoftx.noone.Activity.MainActivity.adb;
import static com.bluesoftx.noone.Activity.MainActivity.mainBottomNavigationView;
import static com.bluesoftx.noone.Activity.MainActivity.mainViewPagerAdapter;
import static com.bluesoftx.noone.Activity.MainActivity.mainViewpager2;
import static com.bluesoftx.noone.Activity.MainActivity.sdb;
import static com.bluesoftx.noone.Activity.MainActivity.userUid;
import static com.bluesoftx.noone.Utilities.FirebaseParameters.myRef;
import static com.bluesoftx.noone.Utilities.Parameters.FIREBASE_REALDB_CHILD_STRING;
import static com.bluesoftx.noone.Utilities.Parameters.WHO_SEND;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.room.Update;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bluesoftx.noone.Adapter.MessageAdapter;
import com.bluesoftx.noone.AsyncTask.DeleteUsersSQL;
import com.bluesoftx.noone.AsyncTask.GetTalkingUser;
import com.bluesoftx.noone.AsyncTask.UpdateStatus;
import com.bluesoftx.noone.Models.Messages;
import com.bluesoftx.noone.R;
import com.bluesoftx.noone.Room.MessagesDB;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class MessageFragment extends Fragment {


    // Chat
    private EditText inputMessage;
    private ImageButton mInputButton;
    public static  MessageAdapter mMessageAdapter;
    public static  RecyclerView mMessageRecycler;
    private MaterialToolbar mTool;
    public static ArrayList<Messages> messagesArrayList = new ArrayList<>();
    int messageCount = 20;
    private MessagesDB messagesDB;
    private LinearLayoutManager layoutManager;


    public MessageFragment() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message,container,false);
        inputMessage = view.findViewById(R.id.chat_room_editext);
        mInputButton = view.findViewById(R.id.m_room_send_botton);
        mMessageRecycler = view.findViewById(R.id.m_room_chats_recyclerView);
        mTool = view.findViewById(R.id.m_room_TopAppBar);
        mTool.setTitle("聊天室");


        materialTool();
        sendButton();



        GetTalkingUser getTalkingUser = new GetTalkingUser(getContext());
        try {
            getTalkingUser.execute().get();
            messagesDB = getTalkingUser.getTalkingUser();
            readDB();
            mMessageRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int currentFirstVisible = layoutManager.findFirstVisibleItemPosition();
                    if (currentFirstVisible == 0 ) {
                        String countQuery = "SELECT  * FROM '" + messagesDB.getMyUid() + "' order by Time desc limit " + messageCount + ", 20";
                        Cursor cursor = adb.query(countQuery);
                        if(cursor.moveToFirst()){
                            do{
                                String message = cursor.getString(cursor.getColumnIndex(Messages.database.MESSAGE));
                                String sender = cursor.getString(cursor.getColumnIndex(Messages.database.SENDER));
                                long time = cursor.getLong(cursor.getColumnIndex(Messages.database.TIME));
                                Messages messages = new Messages();
                                messages.setSenderId(sender);
                                messages.setMessage(message);
                                messages.setTimestamp(time);
                                messagesArrayList.add(0,messages);
                            }while (cursor.moveToNext());
                        }
                        mMessageAdapter.notifyItemInserted(messagesArrayList.size());
                        messageCount += 20;
                    }
                }
            });
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return view;
    }


    @Override
    public void onStart(){
        super.onStart();
    }


    /**
     * Send button
     */
    private void sendButton(){
        mInputButton.setOnClickListener(view12 -> {
            if(!inputMessage.getText().toString().isEmpty()){
                String message = inputMessage.getText().toString();
                long sendTime = System.currentTimeMillis();
                String uniqueKey = UUID.randomUUID().toString();
                Messages mMessages = new Messages();
                mMessages.setMessage(message);
                mMessages.setTimestamp(sendTime);
                mMessages.setSenderId("1");
                //Update to realtime
                myRef.child(FIREBASE_REALDB_CHILD_STRING).child(messagesDB.getMyUid()).child(WHO_SEND).child(userUid).child(uniqueKey).setValue(mMessages);
                sdb.execSQL("INSERT INTO '" +  messagesDB.getMyUid() +"' (sender, message, time) VALUES ('0','" + message + "','" + sendTime +"')");
                updateDB(messagesDB.getMyUid());
                inputMessage.getText().clear();
            }
        });
    }

    /**
     * MaterialToolbar
     * Return to Main pager.
     * 2022/07/07 Tim Chiang
     *
     * Leave room, del data.
     *
     */
    private void materialTool(){
        mTool.setNavigationOnClickListener(view1 -> {
            DeleteUsersSQL deleteUsersSQL = new DeleteUsersSQL(getContext(), messagesDB.getMyUid());
            deleteUsersSQL.execute();
//            UpdateStatus updateStatus = new UpdateStatus(getContext(), messagesDB.getMyUid());
//            updateStatus.execute();
            sdb.execSQL("DROP TABLE if exists " +  messagesDB.getMyUid()  );
            mainViewPagerAdapter.removeFragment();
            mainViewPagerAdapter.addAllFragments();
            mainViewpager2.setAdapter(mainViewPagerAdapter);
            mainBottomNavigationView.setVisibility(View.VISIBLE);
        });
    }


    public static void updateDB(String senderId){
        String countQuery = "SELECT  * FROM '" + senderId + "' order by time desc limit 1";
        Cursor cursor = adb.query(countQuery);
        if(cursor.moveToLast()){
            do{
                String message = cursor.getString(cursor.getColumnIndex(Messages.database.MESSAGE));
                String sender = cursor.getString(cursor.getColumnIndex(Messages.database.SENDER));
                long time = cursor.getLong(cursor.getColumnIndex(Messages.database.TIME));
                Messages messages = new Messages();
                messages.setSenderId(sender);
                messages.setMessage(message);
                messages.setTimestamp(time);
                messagesArrayList.add(messages);
            }while (cursor.moveToPrevious());
        }
        mMessageAdapter.notifyItemInserted(messagesArrayList.size());
        mMessageRecycler.scrollToPosition(mMessageAdapter.getItemCount() - 1 );

    }
    private void readDB(){
        messagesArrayList.clear();
        String countQuery = "SELECT  * FROM '" + messagesDB.getMyUid() + "'  order by time desc limit 0,20";
        Cursor cursor = adb.query(countQuery);
        if(cursor.moveToLast()){
            do{
                String message = cursor.getString(cursor.getColumnIndex(Messages.database.MESSAGE));
                String sender = cursor.getString(cursor.getColumnIndex(Messages.database.SENDER));
                long time = cursor.getLong(cursor.getColumnIndex(Messages.database.TIME));
                Messages messages = new Messages();
                messages.setSenderId(sender);
                messages.setMessage(message);
                messages.setTimestamp(time);
                messagesArrayList.add(messages);
            }while (cursor.moveToPrevious());
        }
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        mMessageRecycler.setLayoutManager(layoutManager);
        ((SimpleItemAnimator)mMessageRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        mMessageAdapter = new MessageAdapter(getContext(), messagesArrayList);
        mMessageRecycler.setAdapter(mMessageAdapter);

    }
}