package vn.iotstar.ltdd_giuaky.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import vn.iotstar.ltdd_giuaky.R;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private ImageView btnLogin;
    private EditText edtUsername, edtPassword;
    TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        TextView txtRegister = findViewById(R.id.registerText);

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });


        // Set login button on click:
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser();
            }
        });


    }

    public void authenticateUser() {
        if (!validateEmail() || !validatePassword()) {
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        String url = "http://10.0.2.2:8080/login";

        // Tạo JSON Object từ dữ liệu nhập vào
        JSONObject params = new JSONObject();
        try {
            params.put("email", edtUsername.getText().toString().trim());
            params.put("password", edtPassword.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        // Tạo Request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Kiểm tra phản hồi từ server
                            if (response.has("email")) {
                                String email = response.getString("email");
                                String name = response.optString("name", "Unknown"); // Lấy tên từ JSON
                                String avatar = response.optString("avatar", null); // Lấy avatar từ JSON

                                // Điều hướng đến RetrofitActivity
                                Intent goToRetrofitActivity = new Intent(LoginActivity.this, RetrofitActivity.class);
                                goToRetrofitActivity.putExtra("email", email);
                                goToRetrofitActivity.putExtra("name", name);
                                goToRetrofitActivity.putExtra("avatar", avatar);

                                startActivity(goToRetrofitActivity);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Lỗi đăng nhập!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Lỗi xử lý dữ liệu!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        String errorMsg = "Đăng nhập thất bại!";

                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                String responseBody = new String(error.networkResponse.data, "utf-8");
                                JSONObject data = new JSONObject(responseBody);
                                errorMsg = data.optString("message", "Đăng nhập thất bại!");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    }
                });

        // Thêm request vào hàng đợi
        queue.add(jsonObjectRequest);
    }


    public boolean validateEmail() {
        String email = edtUsername.getText().toString();
        if (email.isEmpty()) {
            edtUsername.setError("Email không được để trống!");
            return false;
        } else {
            edtUsername.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String password = edtPassword.getText().toString();
        if (password.isEmpty()) {
            edtPassword.setError("Mật khẩu không được để trống!");
            return false;
        } else {
            edtPassword.setError(null);
            return true;
        }
    }

}