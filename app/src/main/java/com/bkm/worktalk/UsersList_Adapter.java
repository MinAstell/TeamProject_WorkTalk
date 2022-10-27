package com.bkm.worktalk;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsersList_Adapter extends RecyclerView.Adapter<UsersList_Adapter.CustomViewHolder> {

    public DatabaseReference mDatabase;

    private ArrayList<UserListsDTO> arrayList;
    private Context context;

    public UsersList_Adapter(ArrayList<UserListsDTO> arrayList) {
        this.arrayList = arrayList;
//        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_userlist, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext()).load(R.drawable.profile_simple).apply(new RequestOptions().circleCrop()).into(holder.iv_userProfile);

        holder.tv_userName.setText(arrayList.get(position).name);
        holder.tv_userHp.setText(arrayList.get(position).hp);
        holder.tv_userEmail.setText(arrayList.get(position).email);

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position) {
        try {
            arrayList.remove(position);
            notifyItemRemoved(position);  // 새로고침
        }catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iv_userProfile;
        protected TextView tv_userName;
        protected TextView tv_userHp;
        protected TextView tv_userEmail;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_userProfile = (ImageView) itemView.findViewById(R.id.iv_userProfile);
            this.tv_userName = (TextView) itemView.findViewById(R.id.tv_userName);
            this.tv_userHp = (TextView) itemView.findViewById(R.id.tv_userHp);
            this.tv_userEmail = (TextView) itemView.findViewById(R.id.tv_userEmail);
        }
    }
}