package com.bluesoftx.noone.AsyncTask;

import static com.bluesoftx.noone.Activity.MainActivity.roomDao;

import android.content.Context;
import android.os.AsyncTask;

import com.bluesoftx.noone.Room.RoomDao;
import com.bluesoftx.noone.Room.RoomIdDatabase;

public class DeleteUsersSQL extends AsyncTask<Void, Void, Void> {

    public Context context;
    public String uid;


    public DeleteUsersSQL(Context context, String uid){
        this.context = context;
        this.uid = uid;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        roomDao.deleteUser(uid);
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
    }

}
