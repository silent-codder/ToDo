package com.example.todo.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    public TextView txt_title;
    public TextView txt_subPoints;
    public TextView txt_date;
    public TextView from_time;
   // public TextView to_time;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);

        txt_title = (TextView) itemView.findViewById(R.id.title);
        txt_subPoints = (TextView) itemView.findViewById(R.id.subPoints);
        txt_date = (TextView) itemView.findViewById(R.id.date);
        from_time = (TextView) itemView.findViewById(R.id.fromTime);
       // to_time = (TextView) itemView.findViewById(R.id.toTime);

    }
}
