package com.example.todo.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.todo.Fragments.DashboardFragment;
import com.example.todo.Fragments.ProfileFragment;
import com.example.todo.Fragments.TaskFragment;
import com.example.todo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    Fragment selectFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.task_list :
                        selectFragment = new TaskFragment();
                       // startActivity(new Intent(MainActivity.this, Task.class));
                        break;

//                    case R.id.dashboard :
//                        selectFragment = new DashboardFragment();
//                        break;


                    case R.id.profile :
                        selectFragment = new ProfileFragment();
                        break;
                }

                if (selectFragment != null)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).commit();
                }

                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TaskFragment()).commit();

    }

}