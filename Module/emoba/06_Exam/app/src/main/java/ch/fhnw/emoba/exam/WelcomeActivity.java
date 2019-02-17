package ch.fhnw.emoba.exam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    public static final int PAIRING_REQUEST_CODE = 0;

    public static final String KEY_NAME = "RESULT_CODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void handleButtonClick(View view) {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivityForResult(intent, PAIRING_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAIRING_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Intent intent = new Intent(this, MainActivity.class);
                //Intent intent = new Intent("ch.fhnw.edu.emoba.MAIN");
                startActivity(intent);
            }
        } else {
            Log.d("exam", "Unable to pair the device");
        }
    }
}
