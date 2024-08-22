package com.example.sudomifos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {

            // TODO: Validate inputs and send OTP

            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            //intent.putExtra("email", email);
            startActivity(intent);
        });

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {


            // TODO: Validate inputs and send OTP

            Intent intent = new Intent(MainActivity.this, Login.class);
            //intent.putExtra("email", email);
            startActivity(intent);
        });


    }

    }

