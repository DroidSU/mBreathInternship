package com.morningstar.mbreathinternship;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button assignment_one, assignment_two, assignment_three;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignment_one = findViewById(R.id.btnAssignment1);

        assignment_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent assignmentIntent = new Intent(MainActivity.this, assignmentOne.class);
                startActivity(assignmentIntent);
            }
        });

        assignment_two = findViewById(R.id.btnAssignment2);
        assignment_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent assignmentIntent = new Intent(MainActivity.this,assignmentTwo.class);
                startActivity(assignmentIntent);
            }
        });

        assignment_three = findViewById(R.id.btnAssignment3);
        assignment_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent assignmentIntent = new Intent(MainActivity.this, assignmentThree.class);
                startActivity(assignmentIntent);
            }
        });

    }
}
