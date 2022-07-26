package com.bluesoftx.noone.AsyncTask;

import static com.bluesoftx.noone.Activity.MainActivity.roomDao;

import android.content.Context;
import android.os.AsyncTask;

import com.bluesoftx.noone.Room.MessagesDB;

public class GetTalkingUser extends AsyncTask<Void, Void, Void> {

    private Context context;
    private MessagesDB messagesDB;

    public GetTalkingUser(Context context){

        this.context = context;

    }


    @Override
    protected Void doInBackground(Void... voids) {
        messagesDB = roomDao.selectTalkingUser();
        return null;
    }

    public MessagesDB getTalkingUser(){
        return messagesDB;
    }
}
