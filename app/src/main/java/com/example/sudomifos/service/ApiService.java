package com.example.sudomifos.service;

import com.example.sudomifos.request.RegisterUserRequest;
import com.example.sudomifos.request.VerifyOtpRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/v1/register")
    Call<ResponseBody> registerUser(@Body RegisterUserRequest request);

    @POST("/api/v1/verify-otp")
    Call<ResponseBody> verifyOtp(@Body VerifyOtpRequest request);
}
