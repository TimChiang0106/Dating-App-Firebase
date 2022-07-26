package com.bluesoftx.noone.ViewHolder;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bluesoftx.noone.R;

public class MyMessageTextViewHolder extends RecyclerView.ViewHolder {


    private TextView messageTextView, timeTextView, readTextView;
    private ConstraintLayout relativeLayout;
    private Context context;

    public MyMessageTextViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        relativeLayout = itemView.findViewById(R.id.myChat_layout);
        messageTextView = itemView.findViewById(R.id.myChat_messageTextView);
        timeTextView = itemView.findViewById(R.id.myChat_timeTextView);
        readTextView = itemView.findViewById(R.id.myChat_readTextView);

    }
    public TextView getMessageTextView() {
        return messageTextView;
    }
    public void setMessageTextView(TextView messageTextView) {
        this.messageTextView = messageTextView;
    }

    public TextView getTimeTextView() {
        return timeTextView;
    }
    public void setTimeTextView(TextView timeTextView) {
        this.timeTextView = timeTextView;
    }

    public TextView getReadTextView() {
        return readTextView;
    }
    public void setReadTextView(TextView readTextView) {
        this.readTextView = readTextView;
    }


}
