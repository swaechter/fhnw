package ch.fhnw.emoba.exam;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private TextView textView;

    private PairingTask pairingTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView = findViewById(R.id.textView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        pairingTask = new PairingTask();
        pairingTask.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (pairingTask != null) {
            pairingTask.cancel(true);
        }
    }

    public class PairingTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            int counter = 5;
            boolean isError = false;
            while (counter > 0 && !isError && !pairingTask.isCancelled()) {
                try {
                    counter--;
                    publishProgress(counter);
                    Thread.sleep(1000);
                } catch (InterruptedException exception) {
                    isError = true;
                }
            }
            return 0; // Simulate status code
        }

        @Override
        protected void onProgressUpdate(Integer[] update) {
            textView.setText("Remaining " + update[0] + " seconds!");
        }

        @Override
        protected void onPostExecute(Integer result) {
            Intent data = new Intent();
            data.putExtra(WelcomeActivity.KEY_NAME, result);
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
