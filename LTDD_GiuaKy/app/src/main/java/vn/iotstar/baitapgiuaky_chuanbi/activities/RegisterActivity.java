package vn.iotstar.baitapgiuaky_chuanbi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vn.iotstar.baitapgiuaky_chuanbi.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private ImageView arrowButton;
    private ExecutorService executorService;

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

        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        arrowButton = findViewById(R.id.arrowButton);

        executorService = Executors.newSingleThreadExecutor();

        arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFormSubmission();
            }
        });

        TextView loginHere = findViewById(R.id.loginHere);
        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void handleFormSubmission() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Log the values to check if they are being retrieved correctly
        Log.d("RegisterActivity", "Name: " + name);
        Log.d("RegisterActivity", "Email: " + email);
        Log.d("RegisterActivity", "Password: " + password);
        Log.d("RegisterActivity", "Confirm Password: " + confirmPassword);

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a JSON object with the form data
        JSONObject formData = new JSONObject();
        try {
            formData.put("name", name);
            formData.put("email", email);
            formData.put("password", password);
            formData.put("avatar", "1234"); // Fixed value for avatar
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Send the form data to the server
        executorService.execute(new RegisterTask(formData.toString()));
    }

    private class RegisterTask implements Runnable {
        private final String formData;

        RegisterTask(String formData) {
            this.formData = formData;
        }

        @Override
        public void run() {
            try {
                // Thay đổi URL để sử dụng địa chỉ IP của máy tính của bạn
                URL url = new URL("http://192.168.1.100:8080/register"); // Thay đổi 192.168.1.100 bằng địa chỉ IP của máy tính của bạn
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = formData.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    runOnUiThread(() -> {
                        Toast.makeText(RegisterActivity.this, "Form submitted successfully", Toast.LENGTH_SHORT).show();
                        Log.d("RegisterActivity", "Form submitted successfully");
                    });
                } else {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        Log.e("RegisterActivity", "Server response: " + response.toString());
                        runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Failed to submit form: " + response.toString(), Toast.LENGTH_SHORT).show());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Failed to submit form: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }
    }
}