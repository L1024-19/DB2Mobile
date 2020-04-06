package com.example.db2mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class UpdateParentInfo extends AppCompatActivity {

    EditText email, password, name, phone;
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_parent_info);
        submit = (Button) findViewById(R.id.updateParentInfo);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getJSON("http://192.168.0.21/DB2Mobile/php/UpdateParentInfo.php");
            }
        });
    }

    private void getJSON(final String urlWebService) {
        class GetJSON extends AsyncTask<Void, Void, String> {

            String saveEmail, savePassword, saveName, savePhone;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                email = (EditText) findViewById(R.id.email);
                password = (EditText) findViewById(R.id.password);
                name = (EditText) findViewById(R.id.name);
                phone = (EditText) findViewById(R.id.phone);
                saveEmail = email.getText().toString();
                savePassword = password.getText().toString();
                saveName = name.getText().toString();
                savePhone = phone.getText().toString();
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    HashMap<String, String> params = new HashMap<>();
                    params.put("email", saveEmail);
                    params.put("password", savePassword);
                    params.put("name", saveName);
                    params.put("phone", savePhone);
                    SharedPreferences preferences = getSharedPreferences("Info", MODE_PRIVATE);
                    int id = preferences.getInt("user_id", -1);
                    params.put("user_id", Integer.toString(id));
                    StringBuilder sbParams = new StringBuilder();
                    int i = 0;
                    for (String key : params.keySet()) {
                        try {
                            if (i != 0) {
                                sbParams.append("&");
                            }
                            sbParams.append(key).append("=").append(URLEncoder.encode(params.get(key), "UTF-8"));
                        }
                        catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        i++;
                    }
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestMethod("POST");
                    String paramsString = sbParams.toString();
                    // writes data to php
                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.writeBytes(paramsString);
                    wr.flush();
                    wr.close();
                    // runs the php code and gets JSON from it
                    con.getInputStream();
                    return "Success";
                }
                catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Intent next = new Intent(getApplicationContext(), ParentDashboard.class);
                startActivity(next);
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
}
