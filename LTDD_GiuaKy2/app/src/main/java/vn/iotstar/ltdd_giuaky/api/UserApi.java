package vn.iotstar.ltdd_giuaky.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import vn.iotstar.ltdd_giuaky.dto.RegisterDto;

public interface UserApi {

    @POST("/register")
    Call<ApiResponse> register(@Body RegisterDto registerDto);

    @POST("/verify")
    Call<Map<String, String>> verifyOtp(@Query("email") String email, @Query("otp") String otp);

    @POST("/regenerate-otp")
    Call<Map<String, String>> regenerateOtp(@Query("email") String email);
}

