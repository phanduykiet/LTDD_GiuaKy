package vn.iotstar.ltdd_giuaky.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.ltdd_giuaky.R;
import vn.iotstar.ltdd_giuaky.api.ApiClient;
import vn.iotstar.ltdd_giuaky.api.ApiResponse;
import vn.iotstar.ltdd_giuaky.api.UserApi;

public class OTPActivity extends AppCompatActivity {

    private EditText etOtp;
    private Button btnVerifyOtp;
    private String email; // Email được truyền từ RegisterActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        // Ánh xạ các view
        etOtp = findViewById(R.id.etOtp);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);

        // Lấy email từ Intent
        email = getIntent().getStringExtra("email");

        // Xử lý sự kiện click nút xác nhận OTP
        btnVerifyOtp.setOnClickListener(v -> verifyOtp());
    }

    private void verifyOtp() {
        String otp = etOtp.getText().toString().trim();

        if (otp.isEmpty()) {
            etOtp.setError("Vui lòng nhập mã OTP!");
            return;
        }

        // Gọi API để xác thực OTP
        UserApi userApi = ApiClient.getRetrofitInstance().create(UserApi.class);
        Call<Map<String, String>> call = userApi.verifyOtp(email, otp);

        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, String>> call, @NonNull Response<Map<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, String> responseBody = response.body();
                    String message = responseBody.get("message"); // Lấy thông báo từ API

                    Log.d("OtpActivity", "Xác thực OTP thành công: " + message);
                    Toast.makeText(OTPActivity.this, message, Toast.LENGTH_SHORT).show();

                    // Chuyển sang LoginActivity
                    Intent intent = new Intent(OTPActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.d("OtpActivity", "Xác thực OTP thất bại: " + response.message());
                    Toast.makeText(OTPActivity.this, "Mã OTP không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, String>> call, @NonNull Throwable t) {
                Log.d("OtpActivity", "Lỗi: " + t.getMessage());
                Toast.makeText(OTPActivity.this, "Lỗi kết nối, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}