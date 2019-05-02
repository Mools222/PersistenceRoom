package com.example.persistence_room;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.persistence_room.database.User;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<User> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        private MyViewHolder(@NonNull View v) {
            super(v);
            textView = v.findViewById(R.id.adapter_tw);
        }
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_layout, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder myViewHolder, int i) {
        User user = list.get(i);

        myViewHolder.textView.setText("Id: " + user.getUid() + " Name: " + user.getFirstName() + " " + user.getLastName());
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void changeData(List<User> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
