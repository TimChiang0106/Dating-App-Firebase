package com.bluesoftx.noone.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bluesoftx.noone.Adapter.UserAdapters;
import com.bluesoftx.noone.R;
import com.bluesoftx.noone.Utilities.FirebaseParameters;
import com.google.firebase.database.DatabaseReference;


public class MainFragment extends Fragment {

    // Go to chat

    private Button goChat;
    private DatabaseReference mDatabase;
    private RecyclerView recyclerView;

    public MainFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
//        initFirebase();
//        recyclerView = view.findViewById(R.id.recyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//        UserAdapters textAdapter = new UserAdapters(getActivity());
//        recyclerView.setAdapter(textAdapter);
        return view;
    }

}