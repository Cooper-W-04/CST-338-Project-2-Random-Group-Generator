package com.example.cst338project2randomgroups.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst338project2randomgroups.ActivityAuthorityViewSelectedClassroom;
import com.example.cst338project2randomgroups.database.entities.Classroom;
import com.example.cst338project2randomgroups.databinding.ItemStudentViewAllClassroomsBinding;

import java.util.ArrayList;
import java.util.List;
import com.example.cst338project2randomgroups.StudentViewSELECTEDClassActivity;


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

    //view all classes offered here
    @NonNull
    @Override
    public ClassroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (auth) {
            // Admin/teacher: keep old button layout
            Button button = new Button(context);
            button.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f9a54e")));
            button.setTextColor(Color.parseColor("#000000"));
            return new ClassroomViewHolder(button);
        } else {
            ItemStudentViewAllClassroomsBinding binding = ItemStudentViewAllClassroomsBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false);
            return new ClassroomViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ClassroomViewHolder holder, int position) {
        Classroom classroom = classrooms.get(position);

        if (auth && holder.adminButton != null) {
            // Admin/Teacher layout (simple button)
            holder.adminButton.setText(classroom.getClassName());
            holder.adminButton.setOnClickListener(v -> {
                Intent intent = new Intent(context, ActivityAuthorityViewSelectedClassroom.class);
                intent.putExtra("CLASSROOM_ID", classroom.getClassroomId());
                context.startActivity(intent);
            });
        } else if (holder.studentBinding != null) {
            // Student layout (custom layout via ViewBinding)
            holder.studentBinding.classNameTextView.setText(classroom.getClassName());
            holder.studentBinding.viewClassButton.setOnClickListener(v -> {
                Intent intent = StudentViewSELECTEDClassActivity
                        .studentViewSELECTEDClassesIntentFactory(context, classroom.getClassroomId());
                intent.putExtra("CLASSROOM_ID", classroom.getClassroomId());
                context.startActivity(intent);
            });
        }
    }


    @Override
    public int getItemCount() {
        return classrooms.size();
    }

//    static class ClassroomViewHolder extends RecyclerView.ViewHolder {
//        Button button;
//
//        ClassroomViewHolder(@NonNull Button itemView) {
//            super(itemView);
//            button = itemView;
//        }
//    }

    static class ClassroomViewHolder extends RecyclerView.ViewHolder {
        Button adminButton;
        ItemStudentViewAllClassroomsBinding studentBinding;

        // for admin/teacher layout (simple button)
        ClassroomViewHolder(@NonNull Button button) {
            super(button);
            this.adminButton = button;
            this.studentBinding = null;
        }

        // for student layout (ViewBinding)
        ClassroomViewHolder(@NonNull ItemStudentViewAllClassroomsBinding binding) {
            super(binding.getRoot());
            this.studentBinding = binding;
            this.adminButton = null;
        }
    }

}
