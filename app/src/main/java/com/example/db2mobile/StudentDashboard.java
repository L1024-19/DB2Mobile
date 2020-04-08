package com.example.db2mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StudentDashboard extends AppCompatActivity {

    Button updateStudentInfo;
    Button viewSections;
    Button logout;
    TextView idDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        SharedPreferences preferences = getSharedPreferences("Info", MODE_PRIVATE);
        int id = preferences.getInt("user_id", -1);
        idDisplay = findViewById(R.id.idDisplay);
        idDisplay.setText(String.valueOf(id));
        updateStudentInfo = findViewById(R.id.updateStudentInfo);
        viewSections = findViewById(R.id.viewSections);
        logout = findViewById(R.id.logout);
        updateStudentInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), StudentUpdateStudentInfo.class);
                startActivity(next);
            }
        });
        viewSections.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), StudentSection.class);
                startActivity(next);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), Logout.class);
                startActivity(next);
            }
        });
    }
}
