package com.bluesoftx.noone.Utilities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseParameters {
    public static final DatabaseReference myRef = FirebaseDatabase.getInstance("https://nimble-factor-315013.asia-southeast1.firebasedatabase.app/").getReference();
    public static final StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    public static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final FirebaseAuth mAuth = FirebaseAuth.getInstance();

}
