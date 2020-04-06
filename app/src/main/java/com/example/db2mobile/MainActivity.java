package com.example.db2mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button parentRegister;
    Button studentRegister;
    Button parentSignIn;
    Button studentSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentRegister = findViewById(R.id.parentRegister);
        studentRegister = findViewById(R.id.studentRegister);
        parentSignIn = findViewById(R.id.parentSignIn);
        studentSignIn = findViewById(R.id.studentSignIn);
        parentRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), ParentRegister.class);
                startActivity(next);
            }
        });
        studentRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), StudentRegister.class);
                startActivity(next);
            }
        });
        parentSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), ParentSignIn.class);
                startActivity(next);
            }
        });
        studentSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), DisplayEmails.class);
                startActivity(next);
            }
        });
    }
}
