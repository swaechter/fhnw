package ch.fhnw.edu.emoba.ub1;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        textView = findViewById(R.id.counterView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Integer value = getIntent().getIntExtra(MainActivity.COUNTER_KEY, 0);
        textView.setText(String.valueOf(value));
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Intent intent = getIntent();
        Integer value = intent.getIntExtra(MainActivity.COUNTER_KEY, 0) + 1;
        intent.putExtra(MainActivity.COUNTER_KEY, value);
        textView.setText(String.valueOf(value));
    }
}
