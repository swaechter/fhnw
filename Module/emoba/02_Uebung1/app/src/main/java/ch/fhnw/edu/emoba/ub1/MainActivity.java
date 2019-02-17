package ch.fhnw.edu.emoba.ub1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final Integer REQUEST_ID = 1;

    public static final String COUNTER_KEY = "counter";

    private Integer counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClicked(View view) {
        Log.d("MainActivity", "onButtonClicked()");

        Intent intent;

        // Explicit intent
        intent = new Intent(this, CounterActivity.class);

        // Implicit intent
        //intent = new Intent("ch.fhnw.edu.helloworld.HELLOWORLD");

        intent.putExtra(COUNTER_KEY, counter);

        startActivityForResult(intent, REQUEST_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_ID && resultCode == RESULT_OK) {
            counter = intent.getIntExtra(COUNTER_KEY, 0);
        }
    }
}
