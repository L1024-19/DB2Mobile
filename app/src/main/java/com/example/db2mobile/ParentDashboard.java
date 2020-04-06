package com.example.db2mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ParentDashboard extends AppCompatActivity {

    Button updateParentInfo;
    Button updateStudentInfo;
    Button logout;
    TextView idDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);
        SharedPreferences preferences = getSharedPreferences("Info", MODE_PRIVATE);
        int id = preferences.getInt("user_id", -1);
        idDisplay = findViewById(R.id.idDisplay);
        idDisplay.setText(String.valueOf(id));
        updateParentInfo = findViewById(R.id.updateParentInfo);
        updateStudentInfo = findViewById(R.id.updateStudentInfo);
        logout = findViewById(R.id.logout);
        updateParentInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), UpdateParentInfo.class);
                startActivity(next);
            }
        });
        updateStudentInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), UpdateStudentInfo.class);
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
