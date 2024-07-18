package com.example.sudomifos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class OTP extends AppCompatActivity {
    private EditText etOtp;
    private Button btnVerifyOtp;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        etOtp = findViewById(R.id.etOtp);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);

        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        btnVerifyOtp.setOnClickListener(v -> {
            String otp = etOtp.getText().toString();


            // TODO: Verify OTP

            // Save user info (for simplicity, using email as unique ID)
            getSharedPreferences("user_prefs", MODE_PRIVATE).edit()
                    .putString("email", email)
                    .putString("password", password)
                    .apply();
            startActivity(new Intent(OTP.this, WelcomeActivity.class));
        });
    }
}