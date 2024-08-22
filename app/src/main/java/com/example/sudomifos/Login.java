package com.example.sudomifos;
import android.content.Intent;
import android.content.SharedPreferences;
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
        // Load saved email and password from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", null);
        String savedPassword = sharedPreferences.getString("password", null);
        // Set the saved values in the EditText fields if available
        if (savedEmail != null && savedPassword != null) {
            etEmail.setText(savedEmail);
            etPassword.setText(savedPassword);
        }
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            // Validate login credentials (for demonstration, we'll use the saved credentials)
            if (email.equals(savedEmail) && password.equals(savedPassword)) {
                startActivity(new Intent(Login.this, WelcomeActivity.class));
            } else {
                // Show error message for incorrect credentials
                Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
