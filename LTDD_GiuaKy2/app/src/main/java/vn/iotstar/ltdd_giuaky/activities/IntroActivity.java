package vn.iotstar.ltdd_giuaky.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import vn.iotstar.ltdd_giuaky.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // Ánh xạ nút
        Button startButton = findViewById(R.id.start_button);

        // Gán sự kiện click cho nút
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
