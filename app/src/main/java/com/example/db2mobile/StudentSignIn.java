package com.example.db2mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class StudentSignIn extends AppCompatActivity {

    EditText email, password;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_in);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getJSON("http://10.0.0.234/db2/DB2Mobile/php/StudentSignIn.php");
            }
        });
    }

    private void getJSON(final String urlWebService) {
        class GetJSON extends AsyncTask<Void, Void, String> {

            String saveEmail, savePassword;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                email = (EditText) findViewById(R.id.email);
                password = (EditText) findViewById(R.id.password);
                saveEmail = email.getText().toString();
                savePassword = password.getText().toString();
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    HashMap<String, String> params = new HashMap<>();
                    params.put("studentemail", saveEmail);
                    params.put("studentpassword", savePassword);
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
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                }
                catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    login(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void login(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String user;
        JSONObject obj = jsonArray.getJSONObject(0);
        user = obj.getString("user_id");
        SharedPreferences preferences = getSharedPreferences("Info", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("user_id", Integer.parseInt(user));
        editor.apply();
        Intent next = new Intent(getApplicationContext(), StudentDashboard.class);
        startActivity(next);
    }
}
