package com.example.fitnesstrackingapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText username;
    EditText password;
    Button register;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        username = findViewById(R.id.regUsername);
        password = findViewById(R.id.regPassword);
        register = findViewById(R.id.Submit);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userString = username.getText().toString();
                String passString = password.getText().toString();
                if (userString.equals("") || passString.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else if(db.exist(userString)) {
                    Toast.makeText(getApplicationContext(), "Username exists", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (db.insert(userString, passString)) {
                        Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }
}
