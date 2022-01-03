package run.ccfish.android.autoupdateapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_update_system;
    EditText et_download_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "http://192.168.31.66:8000/signed_AutoUpdateApp-debug.apk";

        et_download_url = findViewById(R.id.et_download_url);

        et_download_url.setText(url);

        btn_update_system = findViewById(R.id.btn_update_system);

        btn_update_system.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        btn_update_system.setEnabled(false);
        new Thread(() -> {
            try {
                UpdateUtil.updateApp(MainActivity.this, et_download_url.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> btn_update_system.setEnabled(true));
        }).start();
    }
}