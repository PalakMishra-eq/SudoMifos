package com.example.sudomifos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sudomifos.request.RegisterUserRequest;
import com.example.sudomifos.service.ApiClient;
import com.example.sudomifos.service.ApiService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private EditText etName, etEmail, etPassword;
    private DatabaseHelper dbHelper;
    private ProgressDialog progressDialog;
    public static String NAME = "";
    public static String EMAIL = "";
    public static String PASSWORD = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button registerButton = findViewById(R.id.registerButton);

        dbHelper = new DatabaseHelper(this);

        registerButton.setOnClickListener(v -> {
            NAME = etName.getText().toString();
            EMAIL = etEmail.getText().toString();
            PASSWORD = etPassword.getText().toString();

            // Validate inputs
            if (NAME.isEmpty() || EMAIL.isEmpty() || PASSWORD.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                registerUserApiCall(NAME, EMAIL, PASSWORD);
            }

//            // Validate inputs
//            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//            } else {
//
//                // Insert data into the database
//                long result = dbHelper.addUser(name, email, password);
//                if (result != -1) {
//                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
//
//                    // Generate OTP
//                    String otp = OTPGenerator.generateOTP(6);
//
//                    // Send OTP via email
//                    new SendMailTask(RegisterActivity.this).execute(email, "Your OTP Code", "Your OTP code is: " + otp);
//
//                    // Store OTP in SharedPreferences (or pass it to OTP activity via Intent)
//                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("otp", otp);
//                    editor.apply();
//
//                    // Navigate to OTP screen
//                    Intent intent = new Intent(RegisterActivity.this, OTP.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
//                }
//            }
        });
    }

    private void registerUserApiCall(String name, String email, String password) {
        showProgressDialog();

        RegisterUserRequest request = new RegisterUserRequest(name, email, password);
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.registerUser(request);

        // Log the request details
        Log.d(TAG, "Making register user API call");
        Log.d(TAG, "Request URL: " + call.request().url());
        Log.d(TAG, "Request Method: " + call.request().method());
        Log.d(TAG, "Request Body: " + request);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    handleSuccessfulResponse(response);
                } else {
                    handleUnsuccessfulResponse(response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                dismissProgressDialog();
                handleFailure(t);
            }
        });
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering user...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void handleSuccessfulResponse(Response<ResponseBody> response) {
        try {
            String responseBody = response.body() != null ? response.body().string() : "No response body";
            Log.d(TAG, "Registration successful. Response: " + responseBody);
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();

            //TODO IF REGISTER GET SUCCESS THAN WILL NAVIGATE TO OTP SCREEN
            Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
            startActivity(intent);
            // You can parse the responseBody here if needed
        } catch (IOException e) {
            Log.e(TAG, "Error reading successful response", e);
            Toast.makeText(this, "Error processing response", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleUnsuccessfulResponse(Response<ResponseBody> response) {
        try {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "No error body";
            Log.e(TAG, "Registration failed. HTTP Status Code: " + response.code() + ", Error: " + errorBody);
            Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e(TAG, "Error reading error response", e);
            Toast.makeText(this, "Error processing server response", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleFailure(Throwable t) {
        Log.e(TAG, "Network error during registration", t);
        String errorMessage = "Network error: " + t.getMessage();
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }
}