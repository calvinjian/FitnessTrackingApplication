package com.example.fitnesstrackingapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText usernameView;
    EditText passwordView;
    Button login;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        usernameView = findViewById(R.id.Username);
        passwordView = findViewById(R.id.Password);
        register = findViewById(R.id.Register);
        login = findViewById(R.id.Login);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameView.getText().toString();
                String password = passwordView.getText().toString();
                Log.v("Tag", username + ", " + password);
                if (db.Login(username, password)) {
                    Intent i = new Intent(MainActivity.this, Distance.class);
                    int distance = db.getDistance(username);
                    Log.v("Tag", "" + distance);
                    i.putExtra("Username", username);
                    i.putExtra("Distance", distance);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Wrong Username/Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
