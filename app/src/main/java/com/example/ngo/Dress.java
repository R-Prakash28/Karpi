package com.example.ngo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Dress extends AppCompatActivity {
    Button dcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dress);
        dcon = findViewById(R.id.dcon);
        dcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri mUri = Uri.parse("smsto:+8248372659");
                Intent mIntent = new Intent(Intent.ACTION_SENDTO, mUri);
                mIntent.setPackage("com.whatsapp");
                mIntent.putExtra("sms_body", "The text goes here");
                mIntent.putExtra("chat",true);
                startActivity(mIntent);

            }
        });

    }
}