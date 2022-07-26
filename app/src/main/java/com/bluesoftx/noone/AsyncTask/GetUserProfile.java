package com.bluesoftx.noone.AsyncTask;

import static com.bluesoftx.noone.Activity.MainActivity.roomDao;

import android.content.Context;
import android.os.AsyncTask;

import com.bluesoftx.noone.Room.MessagesDB;
import com.bluesoftx.noone.Room.RoomDao;
import com.bluesoftx.noone.Room.RoomIdDatabase;

public class GetUserProfile extends AsyncTask<Void, Void, Void> {

    public Context context;
    public String uid;
    public MessagesDB messagesDB;

    public GetUserProfile(Context context, String uid){

        this.context = context;
        this.uid = uid;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        messagesDB = roomDao.selectAllMessagesDB(uid);
        return null;
    }

    public MessagesDB selectAllMessagesDB(){
        return messagesDB;
    }
}