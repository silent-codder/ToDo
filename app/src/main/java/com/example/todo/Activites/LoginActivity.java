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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    Button btn_signIn;
    EditText email;
    EditText password;
    TextView register;
    ImageView img_return;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    private AdView adView;
    AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_signIn = (Button) findViewById(R.id.btn_signIn);
        email = (EditText) findViewById(R.id.signIn_email);
        password = (EditText) findViewById(R.id.signIn_password);
        register = (TextView) findViewById(R.id.register);
        img_return = (ImageView) findViewById(R.id.btn_return);
        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        adView = (AdView) findViewById(R.id.ad_view);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });


        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_email) | TextUtils.isEmpty(txt_password)){
                    Toast.makeText(LoginActivity.this, "Please fill correct details :(", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setMessage("Wait moment ");
                    progressDialog.show();
                    LoginUser(txt_email, txt_password);
                }
            }
        });

    }

    private void LoginUser(String email, String password) {
        progressDialog.setMessage("wait a sec..");
        progressDialog.show();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login successfully :)", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this , MainActivity.class));
                    finish();
                    progressDialog.dismiss();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

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