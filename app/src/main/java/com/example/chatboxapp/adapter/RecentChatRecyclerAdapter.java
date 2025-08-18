package com.example.chatboxapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatboxapp.ChatActivicty;
import com.example.chatboxapp.R;
import com.example.chatboxapp.Util.FirebaseUtil;
import com.example.chatboxapp.Util.Utils;
import com.example.chatboxapp.model.UserModel;
import com.example.chatboxapp.model.chatroomModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RecentChatRecyclerAdapter extends FirestoreRecyclerAdapter<chatroomModel, RecentChatRecyclerAdapter.chatroomModelViewHolder> {

    Context context;


    public RecentChatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<chatroomModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull chatroomModelViewHolder holder, int position, @NonNull chatroomModel model) {
        FirebaseUtil.getOtherUserFromChatroom(model.getUserId())
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        boolean lastMsgSentByMe = model.getLastmessage().equals(FirebaseUtil.currentUserid());
                        UserModel otherUserModel = task.getResult().toObject(UserModel.class);
                        String imageUrl = otherUserModel.getProfileImageUrl();
                        if (imageUrl != null && !imageUrl.isEmpty()) {

                            Glide.with(context)
                                    .load(imageUrl)
                                    .placeholder(R.drawable.default_profile)
                                    .error(R.drawable.error_profile)
                                    .circleCrop()
                                    .into(holder.profilepic);
                        }
                        holder.usernameText.setText(otherUserModel.getUsername());
                        if(lastMsgSentByMe){
                            holder.lastMessageText.setText("You: "+model.getLastMsg());
                        }else{
                            holder.lastMessageText.setText(model.getLastMsg());
                        }
                        holder.lastMessageTime.setText(FirebaseUtil.timestampTOString(model.getTimestamp()));
                        holder.itemView.setOnClickListener(v -> {
                            Intent intent = new Intent(context, ChatActivicty.class);
                            Utils.passUserModelAsIntent(intent, otherUserModel);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        });
                    }
                });

    }

    @NonNull
    @Override
    public chatroomModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_chat_recycler_row,parent,false);
        return new chatroomModelViewHolder(view);
    }

    class chatroomModelViewHolder extends RecyclerView.ViewHolder{
        TextView usernameText,lastMessageText,lastMessageTime;
        ImageView profilepic;

        public chatroomModelViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
            lastMessageText = itemView.findViewById(R.id.last_msg);
            lastMessageTime = itemView.findViewById(R.id.time_last_msg);
            profilepic = itemView.findViewById(R.id.profile_pic_image_view);
        }
    }
}

