package com.example.cst338project2randomgroups.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst338project2randomgroups.ActivityAuthorityViewSelectedClassroom;
import com.example.cst338project2randomgroups.database.entities.Classroom;

import java.util.ArrayList;
import java.util.List;

public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.ClassroomViewHolder> {

    private final List<Classroom> classrooms = new ArrayList<>();
    private final Context context;
    private final boolean auth;

    public ClassroomAdapter(Context context, LiveData<List<Classroom>> classroomLiveData, LifecycleOwner lifecycleOwner, boolean auth) {
        this.context = context;
        this.auth = auth;

        classroomLiveData.observe(lifecycleOwner, updatedList -> {
            classrooms.clear();
            if (updatedList != null) {
                classrooms.addAll(updatedList);
            }
            notifyDataSetChanged();
        });
    }

    @NonNull
    @Override
    public ClassroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Button button = new Button(context);
        button.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new ClassroomViewHolder(button);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassroomViewHolder holder, int position) {
        Classroom classroom = classrooms.get(position);
        holder.button.setText(classroom.getClassName());
        holder.button.setOnClickListener(v -> {
            if (auth) {
                Intent intent = new Intent(context, ActivityAuthorityViewSelectedClassroom.class);
                intent.putExtra("CLASSROOM_ID", classroom.getClassroomId());
                context.startActivity(intent);
            } else {
                // TODO: Replace with intent for student version of viewing a classroom
            }
        });
    }

    @Override
    public int getItemCount() {
        return classrooms.size();
    }

    static class ClassroomViewHolder extends RecyclerView.ViewHolder {
        Button button;

        ClassroomViewHolder(@NonNull Button itemView) {
            super(itemView);
            button = itemView;
        }
    }
}
