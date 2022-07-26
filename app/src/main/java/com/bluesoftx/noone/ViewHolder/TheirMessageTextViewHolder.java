package com.bluesoftx.noone.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bluesoftx.noone.R;

public class TheirMessageTextViewHolder extends RecyclerView.ViewHolder {


    private TextView messageTextView, timeTextView;
    private Context context;

    public TheirMessageTextViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        messageTextView = itemView.findViewById(R.id.theirChat_messageTextView);
        timeTextView = itemView.findViewById(R.id.theirChat_timeTextView);

    }

    public TextView getMessageTextView() {
        return messageTextView;
    }

    public TextView getTimeTextView() {
        return timeTextView;
    }

    public void setMessageTextView(TextView messageTextView) {
        this.messageTextView = messageTextView;
    }

    public void setTimeTextView(TextView timeTextView) {
        this.timeTextView = timeTextView;
    }
}
