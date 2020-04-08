package com.example.db2mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EnrollTeach extends AppCompatActivity {

    TextView title;
    Button enroll;
    Button teach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_teach);
        final String className = getIntent().getStringExtra("className");
        title = findViewById(R.id.className);
        title.setText(className);
        enroll = findViewById(R.id.enroll);
        teach = findViewById(R.id.teach);
        enroll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), Enroll.class);
                next.putExtra("className", className);
                startActivity(next);
            }
        });
        teach.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), Teach.class);
                next.putExtra("className", className);
                startActivity(next);
            }
        });
    }
}
