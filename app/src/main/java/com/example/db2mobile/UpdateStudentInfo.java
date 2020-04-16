package com.example.db2mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class UpdateStudentInfo extends AppCompatActivity {

    EditText oldStudentEmail, newStudentEmail, oldStudentPassword, newStudentPassword, name, phone;
    Button submit;
    Spinner grade, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student_info);
        grade = (Spinner) findViewById(R.id.grade);
        ArrayAdapter<CharSequence> gradeAdapter = ArrayAdapter.createFromResource(this, R.array.grade, android.R.layout.simple_spinner_item);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grade.setAdapter(gradeAdapter);
        role = (Spinner) findViewById(R.id.role);
        ArrayAdapter<CharSequence> roleAdapter = ArrayAdapter.createFromResource(this, R.array.role, android.R.layout.simple_spinner_item);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(roleAdapter);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getJSON("http://10.0.0.234/db2/DB2Mobile/php/UpdateStudentInfo.php");
            }
        });
    }

    private void getJSON(final String urlWebService) {
        class GetJSON extends AsyncTask<Void, Void, String> {

            String saveOldStudentEmail, saveNewStudentEmail, saveOldStudentPassword, saveNewStudentPassword, saveName, savePhone, saveGrade, saveRole;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                oldStudentEmail = (EditText) findViewById(R.id.oldStudentEmail);
                newStudentEmail = (EditText) findViewById(R.id.newStudentEmail);
                oldStudentPassword = (EditText) findViewById(R.id.oldStudentPassword);
                newStudentPassword = (EditText) findViewById(R.id.newStudentPassword);
                name = (EditText) findViewById(R.id.name);
                phone = (EditText) findViewById(R.id.phone);
                grade = (Spinner)findViewById(R.id.grade);
                role = (Spinner)findViewById(R.id.role);
                saveOldStudentEmail = oldStudentEmail.getText().toString();
                saveNewStudentEmail = newStudentEmail.getText().toString();
                saveOldStudentPassword = oldStudentPassword.getText().toString();
                saveNewStudentPassword = newStudentPassword.getText().toString();
                saveName = name.getText().toString();
                savePhone = phone.getText().toString();
                saveGrade = grade.getSelectedItem().toString();
                saveRole = role.getSelectedItem().toString();
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    HashMap<String, String> params = new HashMap<>();
                    params.put("oldemail", saveOldStudentEmail);
                    params.put("newemail", saveNewStudentEmail);
                    params.put("oldpassword", saveOldStudentPassword);
                    params.put("newpassword", saveNewStudentPassword);
                    params.put("newname", saveName);
                    params.put("newphone", savePhone);
                    params.put("newgrade", saveGrade);
                    params.put("newrole", saveRole);
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
