package com.mind.loginregisterapps;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{
    EditText txtEmail,txtPassword;
    ProgressBar progressBar;
    private TextView tvLogin;
    private TextView tvRegister;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtEmail=findViewById(R.id.txtEmail);
        txtPassword=findViewById(R.id.txtPassword);
        tvLogin     = findViewById(R.id.tvLogin);
        tvRegister=findViewById(R.id.tvRegister);
        progressBar=findViewById(R.id.progressBar);
        firebaseAuth=FirebaseAuth.getInstance();


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                final String email =txtEmail.getText().toString().trim();
                String password=txtPassword.getText().toString().trim();


                if(TextUtils.isEmpty(email)){
                    Toast.makeText(MainActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(MainActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(password.length()<6){
                    Toast.makeText(MainActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                                startActivity(new Intent(MainActivity.this,Main2Activity.class));

                                            }else{
                                                Toast.makeText(MainActivity.this,"Please verify your email address",Toast.LENGTH_SHORT).show();

                                            }
                                } else {
                                    Toast.makeText(MainActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                }

                                // ...
                            }
                        });
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }


}
