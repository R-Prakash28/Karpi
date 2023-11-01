package com.example.ngo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Signup extends AppCompatActivity {

    EditText Email,Name;
    EditText Password;
    TextView LoginNow;
    Button Register;

    EditText mobilenumber;
    EditText otp;
    Button otpbutton;

    FirebaseAuth mAuth;
    private boolean otpSent =false;
    private String countryCode = "+91";
    private String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //otp
        final EditText mobilenumber = findViewById(R.id.mobilenumber);
        final EditText otp = findViewById(R.id.otp);
        final Button otpbutton = findViewById(R.id.otpbutton);

        FirebaseApp.initializeApp(this);
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        otpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(otpSent){
                    final String getOTP = otp.getText().toString();

                    if(id.isEmpty()){
                        Toast.makeText(Signup.this,"Unable to verify OTP",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        PhoneAuthCredential credential =PhoneAuthProvider.getCredential(id,getOTP);

                        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    FirebaseUser userDetail = task.getResult().getUser();
                                    Toast.makeText(Signup.this,"Verified \uD83D\uDE04",Toast.LENGTH_SHORT).show();


                                }
                                else{
                                    Toast.makeText(Signup.this,"Something Wrong!",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }
                }
                else {
                    final String getMobile = mobilenumber.getText().toString();

                    PhoneAuthOptions options=PhoneAuthOptions.newBuilder(firebaseAuth)
                            .setPhoneNumber(countryCode+""+getMobile)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(Signup.this)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    Toast.makeText(Signup.this,"OTP Successfully Send",Toast.LENGTH_SHORT).show();


                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(Signup.this,"Something Wrong"+e.getMessage(),Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(s, forceResendingToken);

                                    otp.setVisibility(View.VISIBLE);
                                    otpbutton.setText("Verify OTP");

                                    id=s;
                                    otpSent=true;
                                }
                            }).build();

                    PhoneAuthProvider.verifyPhoneNumber(options);
                }


            }
        });


        //registration

        Email =findViewById(R.id.Email);
        Name =findViewById(R.id.Name);
        Password=findViewById(R.id.Password);
        LoginNow=findViewById(R.id.Loginnow);
        Register=findViewById(R.id.Register);

        mAuth = FirebaseAuth.getInstance();

        Register.setOnClickListener(view -> createUser());

        LoginNow.setOnClickListener(view -> startActivity(new Intent(Signup.this, LoginPage.class)));
    }
    private void createUser(){
        String email = Email.getText().toString();
        String password= Password.getText().toString();

        if (TextUtils.isEmpty(email)){
            Email.setError("Email cannot be empty");
            Email.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            Password.setError("Password cannot be empty");
            Password.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(Signup.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Signup.this, LoginPage.class));
                }else{
                    Toast.makeText(Signup.this, "Registration Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }









}