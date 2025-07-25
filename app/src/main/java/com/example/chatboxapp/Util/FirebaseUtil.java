package com.example.chatboxapp.Util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtil {

    public static String currentUserid(){
        return FirebaseAuth.getInstance().getUid();
    }
    public static boolean IsLoggedIn(){
        if(currentUserid()!=null){
            return true;
        }
        return false;
    }
    public static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserid());
    }
}
