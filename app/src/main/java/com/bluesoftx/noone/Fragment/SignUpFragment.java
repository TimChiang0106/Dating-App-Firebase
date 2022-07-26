package com.bluesoftx.noone.Fragment;

import static com.bluesoftx.noone.Utilities.FirebaseParameters.mAuth;
import static com.bluesoftx.noone.Utilities.Parameters.FCK_TOKEN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bluesoftx.noone.Activity.MainActivity;
import com.bluesoftx.noone.Activity.SplashActivity;
import com.bluesoftx.noone.AsyncTask.InsertMessagesTable;
import com.bluesoftx.noone.R;
import com.bluesoftx.noone.Room.MessagesDB;
import com.bluesoftx.noone.Utilities.FirebaseParameters;
import com.bluesoftx.noone.Utilities.Parameters;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class SignUpFragment extends Fragment {



    private Button signUpButton;
    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        signUpButton = view.findViewById(R.id.signup_anonymous_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), R.string.signup_auth_ing,
                        Toast.LENGTH_SHORT).show();
                mAuth.signInAnonymously().addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            SharedPreferences prefs = getActivity().getSharedPreferences(Parameters.PREFS_USER, Context.MODE_PRIVATE);
                            String fcmToken = prefs.getString(FCK_TOKEN,"");
                            assert user != null;
                            /**
                             *      Caused by: java.lang.NullPointerException: Attempt to invoke interface method 'void com.bluesoftx.noone.Room.RoomDao.insert(com.bluesoftx.noone.Room.MessagesDB)' on a null object referen
                             */
                            MessagesDB messagesDB = new MessagesDB(user.getUid() , fcmToken, 0);
                            InsertMessagesTable insertMessageTable = new InsertMessagesTable(getActivity(), messagesDB);
                            insertMessageTable.execute();
                            Toast.makeText(getActivity(), R.string.signup_auth_success,
                                    Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(getActivity(), MainActivity.class);
                            getActivity().startActivity(intent1);
                            getActivity().finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(), R.string.signup_auth_error,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        return view;
    }
}