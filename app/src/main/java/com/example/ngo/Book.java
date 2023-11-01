package com.example.ngo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Book extends AppCompatActivity {
    Button wcon;
    EditText loca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        wcon = findViewById(R.id.wcon);
        loca = findViewById(R.id.loca);
        wcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri mUri = Uri.parse("smsto:+8248372659");
                Intent mIntent = new Intent(Intent.ACTION_SENDTO, mUri);
                mIntent.setPackage("com.whatsapp");
                loca.getText();
                mIntent.putExtra("sms_body", "The text goes here");
                mIntent.putExtra("chat",true);
                startActivity(mIntent);

            }
        });



    }
}