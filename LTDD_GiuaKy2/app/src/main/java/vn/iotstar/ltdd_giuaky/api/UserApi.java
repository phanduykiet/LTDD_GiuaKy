package vn.iotstar.ltdd_giuaky.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.iotstar.ltdd_giuaky.dto.RegisterDto;

public interface UserApi {

    @POST("/register")
    Call<ApiResponse> register(@Body RegisterDto registerDto);
    //Call<String> register(@Body RegisterDto registerDto);

    @POST("/verify")
    @FormUrlEncoded
    Call<String> verifyOtp(@Field("email") String email, @Field("otp") String otp);

    @POST("/regenerate-otp")
    @FormUrlEncoded
    Call<String> regenerateOtp(@Field("email") String email);
}

