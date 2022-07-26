package com.bluesoftx.noone.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bluesoftx.noone.R;

public class UserFragmentListViewHolder extends RecyclerView.ViewHolder{

    private Context context;
    private TextView userFragmentListTitle, userFragmentListBody;

    public UserFragmentListViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context=context;
        userFragmentListTitle = itemView.findViewById(R.id.user_fragment_list_title);
        userFragmentListBody = itemView.findViewById(R.id.user_fragment_list_body);
    }

    public TextView getUserFragmentListBody() {
        return userFragmentListBody;
    }

    public TextView getUserFragmentListTitle() {
        return userFragmentListTitle;
    }

    public void setUserFragmentListBody(TextView userFragmentListBody) {
        this.userFragmentListBody = userFragmentListBody;
    }

    public void setUserFragmentListTitle(TextView userFragmentListTitle) {
        this.userFragmentListTitle = userFragmentListTitle;
    }
}
