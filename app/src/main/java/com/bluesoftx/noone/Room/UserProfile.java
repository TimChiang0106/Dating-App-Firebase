package com.bluesoftx.noone.Room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "user_profile")
public class UserProfile implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "uid")
    public String userFirebaseUid;

}
