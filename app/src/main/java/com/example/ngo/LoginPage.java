package com.example.ngo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginPage extends AppCompatActivity {
    EditText lEmail;
    EditText lPassword;

    Button lSignup;
    Button lLogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        lEmail = findViewById(R.id.lEmail);
        lPassword = findViewById(R.id.lPassword);
        lSignup = findViewById(R.id.lSignup);
        lLogin = findViewById(R.id.lLogin);

        mAuth = FirebaseAuth.getInstance();

        lLogin.setOnClickListener(view -> loginUser());
        lSignup.setOnClickListener(view -> startActivity(new Intent(LoginPage.this, Signup.class)));
    }

    private void loginUser(){
        String email = lEmail.getText().toString();
        String password = lPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            lEmail.setError("Email cannot be empty");
            lEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password)){
            lPassword.setError("Password cannot be empty");
            lPassword.requestFocus();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(LoginPage.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                  Intent intent=new Intent(LoginPage.this, NavBar.class);
                  intent.putExtra("user",email);
                  startActivity(intent);

                }else{
                    Toast.makeText(LoginPage.this, "Log in Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}