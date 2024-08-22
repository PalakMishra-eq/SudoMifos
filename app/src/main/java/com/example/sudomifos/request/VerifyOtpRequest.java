package com.example.sudomifos.request;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class VerifyOtpRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("otpCode")
    private String otpCode;

    public VerifyOtpRequest(String email, String otpCode) {
        this.email = email;
        this.otpCode = otpCode;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    @NonNull
    @Override
    public String toString() {
        return "VerifyOtpRequest{" +
                ", email='" + email + '\'' +
                ", otp='" + otpCode + '\'' +
                '}';
    }
}
