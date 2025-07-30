package com.example.chatboxapp.Util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
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
    public static CollectionReference allUserCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");
    }
    public static DocumentReference getChatroomReference(String chatroomId){
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
    }
    public static CollectionReference getChatMessageReference(String chatroomId){
        return getChatroomReference(chatroomId).collection("chats");
    }
    public static String getchatroomId(String userId1, String userId2){
        if(userId1.hashCode()< userId2.hashCode()){
            return userId1+"_"+userId2;
        }
        else{
            return userId2+"_"+userId1;
        }
    }
}
