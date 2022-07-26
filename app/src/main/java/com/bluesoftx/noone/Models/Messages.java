package com.bluesoftx.noone.Models;

import com.bluesoftx.noone.Room.MessagesDB;

public class Messages {

    private String id, message, senderId;
    private long timestamp;
    private MessagesDB db;

    public Messages(String id,String message, String senderId, long timestamp) {
        this.id = id;
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }

    public Messages() {

    }

    public Messages(MessagesDB messagesDB) {
        this.db = messagesDB;
    }

    public MessagesDB getDb() {
        return db;
    }

    public void setDb(MessagesDB db) {
        this.db = db;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
    public class database{
        public static final String ID = "Id";
        public static final String SENDER = "sender";
        public static final String MESSAGE = "message";
        public static final String TIME = "time";
    }

}
