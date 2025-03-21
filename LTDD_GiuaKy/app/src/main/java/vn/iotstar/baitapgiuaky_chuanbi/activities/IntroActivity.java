package vn.iotstar.baitapgiuaky_chuanbi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import vn.iotstar.baitapgiuaky_chuanbi.MainActivity;
import vn.iotstar.baitapgiuaky_chuanbi.R;

public class IntroActivity extends AppCompatActivity {
    private Button btnGoToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        btnGoToMain = findViewById(R.id.start_button);
        btnGoToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Đóng IntroActivity để không quay lại màn hình này khi nhấn Back
            }
        });
    }
}
