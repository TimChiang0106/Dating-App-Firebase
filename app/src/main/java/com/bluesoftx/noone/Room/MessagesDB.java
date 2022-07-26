package com.bluesoftx.noone.Room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "messages_db")
public class MessagesDB implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "myuid")
    public String myUid;

    @ColumnInfo(name = "status")
    public int status;

    @ColumnInfo(name = "fcm_token")
    public String fcmToken;

    public MessagesDB(String myUid,String fcmToken, int status) {
        this.myUid=myUid;
        this.fcmToken=fcmToken;
        this.status=status;
    }

    @NonNull
    public String getMyUid() {
        return myUid;
    }

    public int getStatus() {
        return status;
    }


}
