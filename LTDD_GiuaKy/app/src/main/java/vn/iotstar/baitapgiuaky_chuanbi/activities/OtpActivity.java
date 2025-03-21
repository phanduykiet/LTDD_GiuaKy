package vn.iotstar.baitapgiuaky_chuanbi.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import vn.iotstar.baitapgiuaky_chuanbi.R;

public class OtpActivity extends AppCompatActivity {

    private EditText otpInput;
    private Button confirmOtpButton;
    private TextView resendOtpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp);

        // Ánh xạ View từ XML
        otpInput = findViewById(R.id.otpInput);
        confirmOtpButton = findViewById(R.id.otpButton);
        resendOtpText = findViewById(R.id.resendOtpText);

        // Sự kiện nhấn nút Confirm OTP
        confirmOtpButton.setOnClickListener(v -> {
            String otp = otpInput.getText().toString().trim();
            if (otp.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập OTP!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Xác nhận OTP: " + otp, Toast.LENGTH_SHORT).show();
                // TODO: Gửi OTP lên server để xác thực
            }
        });

        // Sự kiện nhấn Resend OTP
        resendOtpText.setOnClickListener(v -> {
            Toast.makeText(this, "OTP mới đã được gửi!", Toast.LENGTH_SHORT).show();
            // TODO: Gửi yêu cầu gửi lại OTP từ server
        });

        // Xử lý hiển thị full màn hình (Edge to Edge UI)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
