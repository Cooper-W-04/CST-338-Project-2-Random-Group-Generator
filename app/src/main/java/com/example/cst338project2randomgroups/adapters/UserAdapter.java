package com.example.cst338project2randomgroups.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst338project2randomgroups.database.entities.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final List<User> users;
    private final Context context;

    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Button button = new Button(context);
        button.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        //modifying button styling here
        button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f9a54e")));
        button.setTextColor(Color.parseColor("#000000"));
        
        return new UserViewHolder(button);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.button.setText(users.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        Button button;

        UserViewHolder(@NonNull Button itemView) {
            super(itemView);
            button = itemView;
        }
    }
}