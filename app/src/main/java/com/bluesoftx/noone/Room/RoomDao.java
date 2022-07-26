package com.bluesoftx.noone.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MessagesDB messagesDB);

    @Query("Update messages_db set status=2 Where myuid =  :uid")
    void updateStatus(String uid);

    @Query("Delete From messages_db  Where myuid =  :uid")
    void deleteUser(String uid);

    @Query("SELECT * FROM messages_db Where myuid =  :uid")
    MessagesDB selectAllMessagesDB(String uid);


    @Query("SELECT * FROM messages_db Where status = 1")
    MessagesDB selectTalkingUser();
}