package com.example.sudomifos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sudomifos.request.RegisterUserRequest;
import com.example.sudomifos.request.VerifyOtpRequest;
import com.example.sudomifos.service.ApiClient;
import com.example.sudomifos.service.ApiService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {
    private static final String TAG = "OtpActivity";

    private EditText etOtp;
    private Button btnVerify;
    private String generatedOtp;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        etOtp = findViewById(R.id.etOtp);
        btnVerify = findViewById(R.id.btnVerifyOtp);


//        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
//        generatedOtp = sharedPreferences.getString("otp", null);

        btnVerify.setOnClickListener(v -> {
            String enteredOtp = etOtp.getText().toString();

            if (enteredOtp.isEmpty()) {
                Toast.makeText(OtpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                verifyOtpApiCall(enteredOtp);
            }
//            if (enteredOtp.equals(generatedOtp)) {
//                Toast.makeText(OtpActivity.this, "OTP Verified!", Toast.LENGTH_SHORT).show();
//                // Navigate to the next activity (e.g., WelcomeActivity)
//            } else {
//                Toast.makeText(OtpActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
//            }
        });
    }

    private void verifyOtpApiCall(String otp) {
        showProgressDialog();

        VerifyOtpRequest request = new VerifyOtpRequest(otp,RegisterActivity.EMAIL);
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.verifyOtp(request);

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
            Log.d(TAG, "Verification successful. Response: " + responseBody);
            Toast.makeText(this, "Verification successful!", Toast.LENGTH_SHORT).show();

            //TODO IF REGISTER GET SUCCESS THAN WILL NAVIGATE TO OTP SCREEN
            Intent intent = new Intent(OtpActivity.this, WelcomeActivity.class);
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
            Log.e(TAG, "Verification failed. HTTP Status Code: " + response.code() + ", Error: " + errorBody);
            Toast.makeText(this, "Verification failed. Please try again.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e(TAG, "Error reading error response", e);
            Toast.makeText(this, "Error processing server response", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleFailure(Throwable t) {
        Log.e(TAG, "Network error during Verification", t);
        String errorMessage = "Network error: " + t.getMessage();
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }
}