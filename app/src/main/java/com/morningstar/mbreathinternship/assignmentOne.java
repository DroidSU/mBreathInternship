/*
 * Created by Sujoy Datta. Copyright (c) 2018. All rights reserved.
 *
 * To the person who is reading this..
 * When you finally understand how this works, please do explain it to me too at sujoydatta26@gmail.com
 * P.S.: In case you are planning to use this without mentioning me, you will be met with mean judgemental looks and sarcastic comments.
 */

package com.morningstar.mbreathinternship;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class assignmentOne extends AppCompatActivity {

    private TextView contactList;
    private Button btnStart;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_one);

        contactList = findViewById(R.id.tv_contact_details);
        btnStart = findViewById(R.id.btn_assignment_start);
        progressBar = findViewById(R.id.progressBar);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProject();
            }
        });
    }

    private void startProject() {
        progressBar.setVisibility(View.VISIBLE);
        DownloadTask downloadTask = new DownloadTask();
        String res_contacts;
        try {
            res_contacts = downloadTask.execute("http://www.cs.columbia.edu/~coms6998-8/assignments/homework2/contacts/contacts.txt").get();
            contactList.setText(res_contacts);
            progressBar.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            String message = e.getMessage();
            Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


    private static class DownloadTask extends AsyncTask<String, Void, String> {

        private URL url;
        private String result = "";
        private HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                while (data != -1) {
                    char data_bit = (char) data;
                    result = result + data_bit;
                    data = inputStreamReader.read();
                }

                return result;
            }
            catch(Exception e){

                return "Failed";
                }
            }
        }
    }
