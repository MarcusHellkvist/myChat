package com.example.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Message> listOfMessages;
    private String currentUserId;

    public static class ViewHolder0 extends RecyclerView.ViewHolder{
        public TextView userMessage;

        public ViewHolder0(View v){
            super(v);
            userMessage = v.findViewById(R.id.tv_user_message);
        }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder{
        public TextView friendMessage;

        public ViewHolder2(View v){
            super(v);
            friendMessage = v.findViewById(R.id.tv_friend_message);
        }
    }

    public MessageAdapter(ArrayList<Message> listOfMessages, String currentUserId) {
        this.listOfMessages = listOfMessages;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0: return new ViewHolder0(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_message_row, parent, false));
            case 2: return new ViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_message_row, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                ViewHolder0 viewHolder0 = (ViewHolder0) holder;
                viewHolder0.userMessage.setText(listOfMessages.get(position).getText());
                break;
            case 2:
                ViewHolder2 viewHolder2 = (ViewHolder2) holder;
                viewHolder2.friendMessage.setText(listOfMessages.get(position).getText());
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {

        if (listOfMessages.get(position).getSender().contentEquals(currentUserId)){
            return 0;
        } else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return listOfMessages.size();
    }


}
