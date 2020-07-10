package com.example.todo.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.todo.Activites.Add_task;
import com.example.todo.Model.Task;
import com.example.todo.R;
import com.example.todo.ViewHolder.TaskViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class TaskFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseRecyclerAdapter<Task , TaskViewHolder> adapter;
    ImageView  add_task;
    private AdView adView;
    AdRequest adRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task, container, false);

        adView = (AdView) view.findViewById(R.id.ad_view);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        //find ids
        add_task = (ImageView) view.findViewById(R.id.add_task);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);

        //recycle view set
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //firebase set
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(auth.getCurrentUser().getUid()).child("Tasks");
       // Query query = db.collection("Tasks");
        // add task activity call
        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Add_task.class));
            }
        });

        showTask();
        return view;
    }

    // data fetch from firebase database
    private void showTask() {

        FirebaseRecyclerOptions options = new
                FirebaseRecyclerOptions.Builder<Task>()
                .setQuery(databaseReference, Task.class)
                .build();
//        FirestoreRecyclerOptions<Task> options = new FirestoreRecyclerOptions.Builder<Task>()
//                .setQuery(query , Task.class)
//                .build();



        adapter = new FirebaseRecyclerAdapter<Task, TaskViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TaskViewHolder holder, int position, @NonNull Task model) {
                if(model.getTitle().equalsIgnoreCase(""))
                {
                    Toast.makeText(getContext(),"Data not found, Please add new task",Toast.LENGTH_LONG).show();
                }else
                {
                    holder.txt_title.setText(model.getTitle());
                    holder.txt_subPoints.setText(model.getSubPoints());
                    holder.txt_date.setText(model.getDate());
                    holder.from_time.setText(model.getFromTime());
                    //holder.to_time.setText(model.getToTime());
                }


            }

            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.task_item,parent,false);
                return new  TaskViewHolder(view);
            }
        };
        adapter.startListening();
        adapter.onDataChanged();
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}