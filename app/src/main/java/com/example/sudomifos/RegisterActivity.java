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

public class RegisterActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            // TODO: Validate inputs and send OTP

            Intent intent = new Intent(RegisterActivity.this, OTP.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });
    }
//    private void openWelcomeScreen() {
//        String username = usernameEditText.getText().toString();
//        Intent intent = new Intent(this, WelcomeActivity.class);
//        intent.putExtra("username", username);
//        startActivity(intent);
}
