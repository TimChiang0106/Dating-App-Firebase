package com.bluesoftx.noone.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bluesoftx.noone.Models.Messages;
import com.bluesoftx.noone.R;
import com.bluesoftx.noone.Room.MessagesDB;
import com.bluesoftx.noone.ViewHolder.MyMessageTextViewHolder;
import com.bluesoftx.noone.ViewHolder.TheirMessageTextViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int MY_TEXT_MESSAGE = 0, THEIR_TEXT_MESSAGE = 1;
    private final ArrayList<Messages> messagesArrayList;
    private final Context context;

    public MessageAdapter(Context context, ArrayList<Messages> messagesArrayList){
        this.context = context;
        this.messagesArrayList = messagesArrayList;


    }
    @Override
    public int getItemViewType(int position) {
        if(messagesArrayList.get(position).getSenderId().equals("0")){
            return MY_TEXT_MESSAGE;
        }else{
            return THEIR_TEXT_MESSAGE;
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == MY_TEXT_MESSAGE){
            View v1 = layoutInflater.inflate(R.layout.activity_message_items_my_text_message, parent, false);
            viewHolder = new MyMessageTextViewHolder(v1,context);
        }else{
            View v1 = layoutInflater.inflate(R.layout.activity_message_items_their_text_message, parent, false);
            viewHolder = new TheirMessageTextViewHolder(v1,context);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if(holder.getItemViewType() == MY_TEXT_MESSAGE){
                MyMessageTextViewHolder vh1 = (MyMessageTextViewHolder) holder;
                configureMyTextMessageViewHolder(vh1, position);

            }else{
                TheirMessageTextViewHolder vh1 = (TheirMessageTextViewHolder) holder;
                configureTheirTextMessageViewHolder(vh1, position);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void configureTheirTextMessageViewHolder(TheirMessageTextViewHolder viewHolder, int position) {
        viewHolder.getMessageTextView().setText(messagesArrayList.get(position).getMessage());
        viewHolder.getTimeTextView().setText(setTime(messagesArrayList.get(position).getTimestamp()));

    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }
    private void configureMyTextMessageViewHolder(MyMessageTextViewHolder viewHolder, int position) throws ParseException {
        viewHolder.getMessageTextView().setText(messagesArrayList.get(position).getMessage());
        viewHolder.getTimeTextView().setText(setTime(messagesArrayList.get(position).getTimestamp()));
    }

    private String setTime(long currentTime){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        Date date = new Date(currentTime);
        return simpleDateFormat.format(date);
    }
}
