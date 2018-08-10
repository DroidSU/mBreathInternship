/*
 * Created by Sujoy Datta. Copyright (c) 2018. All rights reserved.
 *
 * To the person who is reading this..
 * When you finally understand how this works, please do explain it to me too at sujoydatta26@gmail.com
 * P.S.: In case you are planning to use this without mentioning me, you will be met with mean judgemental looks and sarcastic comments.
 */

package com.morningstar.mbreathinternship;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
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

    private TextView contactListOne;
    private Button btnStart;
    private Button btnContactOne, btnContactTwo, btnContactThree, btnContactFour;
    private ProgressBar progressBar;
    private boolean isContactDownloaded = false;

    String res_contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_one);

        contactListOne = findViewById(R.id.tv_contact_details_one);
        btnStart = findViewById(R.id.btn_assignment_start);
        progressBar = findViewById(R.id.progressBar);
        btnContactOne = findViewById(R.id.btn_addFirstContact);
        btnContactTwo = findViewById(R.id.btn_addSecondContact);
        btnContactThree = findViewById(R.id.btn_addThirdContact);
        btnContactFour = findViewById(R.id.btn_addFourthContact);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProject();
            }
        });

        btnContactOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactOne();
            }
        });

        btnContactThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactThree();
            }
        });

        btnContactTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactTwo();
            }
        });

        btnContactFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactFour();
            }
        });
    }

    private void addContactFour() {
        if (isContactDownloaded) {
            String[] split_contacts = res_contacts.split("\n");
            Contact contact = new Contact(split_contacts[3]);
            Intent contactIntent = new Intent(Intent.ACTION_INSERT);
            contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            contactIntent.putExtra(ContactsContract.Intents.Insert.NAME, contact.getName());
            contactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL, contact.getEmail());
            contactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, contact.getMobl());
            contactIntent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, contact.getPhone());
            startActivity(contactIntent);
        }
        else{
            Toast.makeText(this, "Download contact list first!", Toast.LENGTH_SHORT).show();
        }
    }

    private void addContactThree() {
        if (isContactDownloaded) {
            String[] split_contacts = res_contacts.split("\n");
            Contact contact = new Contact(split_contacts[2]);
            Intent contactIntent = new Intent(Intent.ACTION_INSERT);
            contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            contactIntent.putExtra(ContactsContract.Intents.Insert.NAME, contact.getName());
            contactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL, contact.getEmail());
            contactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, contact.getMobl());
            contactIntent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, contact.getPhone());
            startActivity(contactIntent);
        }
        else{
            Toast.makeText(this, "Download contact list first!", Toast.LENGTH_SHORT).show();
        }
    }

    private void addContactTwo() {
        if (isContactDownloaded) {
            String[] split_contacts = res_contacts.split("\n");
            Contact contact = new Contact(split_contacts[1]);
            Intent contactIntent = new Intent(Intent.ACTION_INSERT);
            contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            contactIntent.putExtra(ContactsContract.Intents.Insert.NAME, contact.getName());
            contactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL, contact.getEmail());
            contactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, contact.getMobl());
            contactIntent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, contact.getPhone());
            startActivity(contactIntent);
        }
        else{
            Toast.makeText(this, "Download contact list first!", Toast.LENGTH_SHORT).show();
        }
    }


    private void addContactOne() {
        if (isContactDownloaded) {
            String[] split_contacts = res_contacts.split("\n");
            Contact contact = new Contact(split_contacts[0]);
            Intent contactIntent = new Intent(Intent.ACTION_INSERT);
            contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            contactIntent.putExtra(ContactsContract.Intents.Insert.NAME, contact.getName());
            contactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL, contact.getEmail());
            contactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, contact.getMobl());
            contactIntent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, contact.getPhone());
            startActivity(contactIntent);
        }
        else{
            Toast.makeText(this, "Download contact list first!", Toast.LENGTH_SHORT).show();
        }
    }

    private void startProject() {
        progressBar.setVisibility(View.VISIBLE);
        DownloadTask downloadTask = new DownloadTask();
        try {
            res_contacts = downloadTask.execute("http://www.cs.columbia.edu/~coms6998-8/assignments/homework2/contacts/contacts.txt").get();
            contactListOne.setText(res_contacts);
            progressBar.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            String message = e.getMessage();
            Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
        isContactDownloaded = true;
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
