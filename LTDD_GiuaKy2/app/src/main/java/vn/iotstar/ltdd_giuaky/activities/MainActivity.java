package vn.iotstar.ltdd_giuaky.activities;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import vn.iotstar.ltdd_giuaky.R;

public class MainActivity extends AppCompatActivity {

    private TextView infoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Gán layout chứa infoUser TextView

        infoUser = findViewById(R.id.infoUser);

        // Nhận dữ liệu từ LoginActivity
        String name = getIntent().getStringExtra("name");

        if (name != null) {
            infoUser.setText("Hi, " + name);
        }
    }
}
