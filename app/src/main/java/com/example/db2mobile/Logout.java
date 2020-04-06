package com.example.db2mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Logout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        SharedPreferences preferences = getSharedPreferences("Info", MODE_PRIVATE);
        preferences.edit().remove("user_id").apply();
        Intent next = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(next);
    }
}
