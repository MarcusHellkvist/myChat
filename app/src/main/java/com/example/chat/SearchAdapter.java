package com.example.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private ArrayList<User> mFriendList;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName;

        public MyViewHolder(View v){
            super(v);
            tvName = v.findViewById(R.id.tv_name);
        }
    }

    public SearchAdapter(ArrayList<User> myFriendList){
        mFriendList = myFriendList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_row, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvName.setText(mFriendList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }
}
