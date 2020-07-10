package com.vclassrooms.todo.Fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vclassrooms.todo.Activites.WelcomeActivity;
import com.vclassrooms.todo.R;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class ProfileFragment extends Fragment {

    TextView profileName, profileProfession, profileEmail;
    Button btn_logout;
    DatabaseReference ref;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressDialog progressDialog;
    private AdView adView;
    AdRequest adRequest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // textView
        profileName = view.findViewById(R.id.profile_name);
        profileProfession = view.findViewById(R.id.profile_profession);
        profileEmail = view.findViewById(R.id.profile_email);
        adView = (AdView) view.findViewById(R.id.ad_view);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        //btnView
        btn_logout = view.findViewById(R.id.btn_logout);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        progressDialog = new ProgressDialog(getContext());


        progressDialog.setMessage("Check your internet connection !!");
        progressDialog.show();

        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.setMessage("Update profile...!!");

                String name = dataSnapshot.child("Name").getValue().toString();
                String profession = dataSnapshot.child("Profession").getValue().toString();
                String email = dataSnapshot.child("Email").getValue().toString();
                String profileImg = dataSnapshot.child("Profile Img").getValue().toString();

                profileName.setText(name);
                profileProfession.setText(profession);
                profileEmail.setText(email);

               // Picasso.get().load(profileImg).into(profile);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Check internet connection !!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),WelcomeActivity.class));
                getActivity().getSupportFragmentManager().popBackStack();
                startActivity(new Intent(getContext(),WelcomeActivity.class));
                onStop();
                Toast.makeText(getContext(), "Logout Successfully !!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
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