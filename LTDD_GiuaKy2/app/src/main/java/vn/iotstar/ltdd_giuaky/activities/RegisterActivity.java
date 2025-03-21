package vn.iotstar.ltdd_giuaky.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.ltdd_giuaky.R;
import vn.iotstar.ltdd_giuaky.api.ApiClient;
import vn.iotstar.ltdd_giuaky.api.ApiResponse;
import vn.iotstar.ltdd_giuaky.api.UserApi;
import vn.iotstar.ltdd_giuaky.dto.RegisterDto;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etConfirmPassword;
    String name, email, password, confirmPassword;
    ImageView ivRegister;

    RegisterDto registerDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        addEvents();
    }

    private void addEvents() {
        ivRegister.setOnClickListener(v -> ivRegisterClick());
    }

    private void ivRegisterClick() {
        initializeData();

        // Kiểm tra thông tin nhập vào
        if (!validateInput()) {
            return;
        }

        // Tạo đối tượng RegisterDto từ thông tin đã nhập
        createRegisterDto();

        // Gọi API để thực hiện đăng ký
        callAPI();
    }

    private void callAPI() {
        UserApi userApi = ApiClient.getRetrofitInstance().create(UserApi.class);
        Call<ApiResponse> call = userApi.register(registerDto);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d("RegisterActivity", "API phản hồi với mã: " + response.code());
                if (response.isSuccessful()) {
                    // Đăng ký thành công, chuyển sang OTPActivity
                    Log.d("RegisterActivity", "Đăng ký thành công");
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, OTPActivity.class);
                    intent.putExtra("email", email); // Truyền email sang OTPActivity
                    startActivity(intent);
                    finish();
                } else {
                    // Đăng ký thất bại
                    Log.d("RegisterActivity", "Đăng ký thất bại: " + response.message());
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Lỗi khi gọi API
                Log.d("RegisterActivity", "Lỗi khi gọi API: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "Lỗi kết nối, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void createRegisterDto() {
        registerDto = new RegisterDto();
        registerDto.setName(name);
        registerDto.setEmail(email);
        registerDto.setPassword(password);
        registerDto.setAvatar("profile");
    }

    private void initializeViews() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        ivRegister = findViewById(R.id.ivRegister);
    }

    private void initializeData() {
        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        confirmPassword = etConfirmPassword.getText().toString().trim();
    }

    private boolean validateInput() {
        boolean isValid = true;

        // Kiểm tra các trường không được để trống
        if (name.isEmpty()) {
            etName.setError("Vui lòng nhập tên!");
            isValid = false;
        } else {
            etName.setError(null); // Xóa lỗi nếu đã nhập đúng
        }

        if (email.isEmpty()) {
            etEmail.setError("Vui lòng nhập email!");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email không hợp lệ!");
            isValid = false;
        } else {
            etEmail.setError(null);
        }

        if (password.isEmpty()) {
            etPassword.setError("Vui lòng nhập mật khẩu!");
            isValid = false;
        } else if (!isPasswordStrong()) {
            etPassword.setError("Mật khẩu phải ít nhất 8 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt!");
            isValid = false;
        } else {
            etPassword.setError(null);
        }

        if (confirmPassword.isEmpty()) {
            etConfirmPassword.setError("Vui lòng nhập xác nhận mật khẩu!");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Mật khẩu và xác nhận mật khẩu không khớp!");
            isValid = false;
        } else {
            etConfirmPassword.setError(null);
        }

        return isValid;
    }

    private boolean isPasswordStrong() {
        // Kiểm tra mật khẩu có độ dài tối thiểu 8 ký tự
        if (password.length() < 8) return false;

        // Kiểm tra có ít nhất một chữ hoa
        boolean hasUpperCase = !password.equals(password.toLowerCase());
        // Kiểm tra có ít nhất một chữ thường
        boolean hasLowerCase = !password.equals(password.toUpperCase());
        // Kiểm tra có ít nhất một số
        boolean hasDigit = password.matches(".*\\d.*");

        return hasUpperCase && hasLowerCase && hasDigit;
    }
}