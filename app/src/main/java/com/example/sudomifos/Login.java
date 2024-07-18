package com.example.sudomifos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            // TODO: Validate login credentials

            String registeredEmail = getSharedPreferences("user_prefs", MODE_PRIVATE)
                    .getString("email", null);
            String registeredPassword = getSharedPreferences("user_prefs", MODE_PRIVATE)
                    .getString("password", null);

            if (email.equals(registeredEmail) && password.equals(registeredPassword)) {
                startActivity(new Intent(Login.this, WelcomeActivity.class));
            } else {
                // Show error message for incorrect credentials
                Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}