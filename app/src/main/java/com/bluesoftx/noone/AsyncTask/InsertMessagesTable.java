package com.bluesoftx.noone.AsyncTask;

import static com.bluesoftx.noone.Activity.MainActivity.roomDao;

import android.content.Context;
import android.os.AsyncTask;

import com.bluesoftx.noone.Room.MessagesDB;
import com.bluesoftx.noone.Room.RoomDao;
import com.bluesoftx.noone.Room.RoomIdDatabase;

public class InsertMessagesTable extends AsyncTask<Void, Void, Void> {

    public Context context;
    public MessagesDB messagesDB;


    public InsertMessagesTable(Context context, MessagesDB messagesDB){
        this.context = context;
        this.messagesDB = messagesDB;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        roomDao.insert(messagesDB);
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
    }

}
