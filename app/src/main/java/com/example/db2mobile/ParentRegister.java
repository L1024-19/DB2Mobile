package com.example.db2mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class ParentRegister extends AppCompatActivity {

    EditText email, password, name, phone;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_register);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getJSON("http://192.168.0.21/DB2Mobile/php/ParentRegister.php");
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
//                    StringBuilder sb = new StringBuilder();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                    String json;
//                    while ((json = bufferedReader.readLine()) != null) {
//                        sb.append(json + "\n");
//                    }
//                    return sb.toString().trim();
                    return "Success";
                }
                catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
}
