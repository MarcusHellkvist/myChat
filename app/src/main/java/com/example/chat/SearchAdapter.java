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
    private OnFriendListener mOnFriendListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvName;
        OnFriendListener mOnFriendListener;

        public MyViewHolder(View v, OnFriendListener onFriendListener){
            super(v);
            tvName = v.findViewById(R.id.tv_name);
            mOnFriendListener = onFriendListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mOnFriendListener.onFriendClick(getAdapterPosition());
        }
    }

    public SearchAdapter(ArrayList<User> myFriendList, OnFriendListener onFriendListener){
        this.mFriendList = myFriendList;
        this.mOnFriendListener = onFriendListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_row, parent, false);
        MyViewHolder vh = new MyViewHolder(v, mOnFriendListener);
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

    public interface OnFriendListener{
        void onFriendClick(int position);
    }
}
