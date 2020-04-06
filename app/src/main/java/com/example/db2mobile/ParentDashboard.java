package com.example.db2mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.List;

public class ParentDashboard extends AppCompatActivity {

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);
        SharedPreferences preferences = getSharedPreferences("Info", MODE_PRIVATE);
        int id = preferences.getInt("user_id", -1);
        test = findViewById(R.id.test);
        test.setText(String.valueOf(id));
    }
}
