package com.example.todo.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    TextView login;
    EditText name;
    EditText profession;
    EditText email;
    EditText password;
    Button register;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    private AdView adView;
    AdRequest adRequest;

    String img_url = "https://firebasestorage.googleapis.com/v0/b/to-do-64ab4.appspot.com/o/profile_img.jpeg?alt=media&token=318fe055-f6a4-4ca9-9ef5-417ddc154050";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        login = (TextView) findViewById(R.id.login);
       // img_return = (ImageView) findViewById(R.id.btn_return);

        name = (EditText) findViewById(R.id.reg_name);
        profession = (EditText) findViewById(R.id.reg_profession);
        email = (EditText) findViewById(R.id.reg_email);
        password = (EditText) findViewById(R.id.reg_password);
        progressDialog = new ProgressDialog(this);

        register = (Button) findViewById(R.id.btn_register);
        progressDialog = new ProgressDialog(this);
        adView = (AdView) findViewById(R.id.ad_view);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_name = name.getText().toString();
                String txt_profession = profession.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_name) || TextUtils.isEmpty(txt_profession) || TextUtils.isEmpty(txt_email)
                        || TextUtils.isEmpty(txt_password)){

                    Toast.makeText(RegisterActivity.this, "fill all information\nfor registration !!", Toast.LENGTH_SHORT).show();
                }
                else if (password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "password is too short !! ", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.setMessage("Wait a moment...");
                    UserRegister(txt_name , txt_profession , txt_email , txt_password);
                    progressDialog.setMessage("Plz wait a moment...");
                    progressDialog.show();

                }
            }
        });


    }

    private void UserRegister(final String name, final String profession, final String email, String password) {


        auth.createUserWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String , Object> map = new HashMap<>();
                map.put("Name" , name);
                map.put("Profession" , profession);
                map.put("Email" , email);
                map.put("Profile Img", img_url);

                databaseReference.child("Users").child(auth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Registration Successfully :)", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                            progressDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

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