package com.example.db2mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

public class Materialinfo extends AppCompatActivity {

    String materialId;
    TextView title, author, type, url, assignedDate, notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materialinfo);
        materialId = getIntent().getStringExtra("material_id");
        getJSON("http://10.0.0.234/db2/DB2Mobile/php/MaterialInfo.php");
    }

    private void getJSON(final String urlWebService) {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                title = findViewById(R.id.title);
                author = findViewById(R.id.author);
                type = findViewById(R.id.type);
                url = findViewById(R.id.url);
                assignedDate = findViewById(R.id.assignedDate);
                notes = findViewById(R.id.notes);
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    HashMap<String, String> params = new HashMap<>();
                    SharedPreferences preferences = getSharedPreferences("Info", MODE_PRIVATE);
                    int id = preferences.getInt("user_id", -1);
                    params.put("user_id", Integer.toString(id));
                    params.put("material_id", materialId);
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
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loadIntoView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String titleInfo, authorInfo, typeInfo, urlInfo, assignedDateInfo, notesInfo;
        JSONObject obj = jsonArray.getJSONObject(0);
        titleInfo = obj.getString("title");
        authorInfo = obj.getString("author");
        typeInfo = obj.getString("type");
        urlInfo = obj.getString("url");
        assignedDateInfo = obj.getString("assigned_date");
        notesInfo = obj.getString("notes");
        title.setText(titleInfo);
        author.setText(authorInfo);
        type.setText(typeInfo);
        url.setText(urlInfo);
        assignedDate.setText(assignedDateInfo);
        notes.setText(notesInfo);
    }
}
