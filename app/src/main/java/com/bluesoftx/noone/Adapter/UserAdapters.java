package com.bluesoftx.noone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bluesoftx.noone.Adapter.ViewHolder.UserFragmentListViewHolder;
import com.bluesoftx.noone.R;
import com.bluesoftx.noone.Utilities.Parameters;

public class UserAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context context;

    public UserAdapters(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v1 = layoutInflater.inflate(R.layout.user_fragment_list, parent, false);
        viewHolder = new UserFragmentListViewHolder(v1, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserFragmentListViewHolder vh1 = (UserFragmentListViewHolder) holder;
        vh1.getUserFragmentListTitle().setText(Parameters.LIST_TITLE[0]);
        vh1.getUserFragmentListBody().setText("01/07/2022");
    }

    @Override
    public int getItemCount() {
        return Parameters.LIST_TITLE.length;
    }
}
