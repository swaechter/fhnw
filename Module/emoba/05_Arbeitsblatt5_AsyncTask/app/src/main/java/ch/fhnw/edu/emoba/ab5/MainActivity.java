package ch.fhnw.edu.emoba.ab5;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final String STATE_RUNNING = "task.running";

    private AsyncTask<Void, RgbColor, Void> colorAsyncTask;

    private void updateHelloWorldView(RgbColor rgbColor) {
        TextView textView = (TextView) findViewById(R.id.txtView);
        textView.setBackgroundColor(Color.rgb(rgbColor.red, rgbColor.green, rgbColor.blue));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.txtView);
        textView.setText(getResources().getString(R.string.app_name));

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_RUNNING)) {
            colorAsyncTask = new ColorAsyncTask();
            colorAsyncTask.execute();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (colorAsyncTask != null) {
            outState.putBoolean(STATE_RUNNING, true);
            colorAsyncTask.cancel(true);
        }
    }

    public void start(View view) {
        if (colorAsyncTask == null) {
            colorAsyncTask = new ColorAsyncTask();
            colorAsyncTask.execute();
        }
    }

    public void stop(View view) {
        if (colorAsyncTask != null) {
            colorAsyncTask.cancel(true);
            colorAsyncTask = null;
        }
    }

    public class ColorAsyncTask extends AsyncTask<Void, RgbColor, Void> {

        private boolean errorFlag;

        @Override
        protected void onPreExecute() {
            errorFlag = false;
        }

        @Override
        protected Void doInBackground(Void[] voids) {
            try {
                while (!errorFlag && !isCancelled()) {
                    RgbColor rgbColor = new RgbColor();
                    rgbColor.red = (int) Math.round(Math.random() * 255);
                    rgbColor.green = (int) Math.round(Math.random() * 255);
                    rgbColor.blue = (int) Math.round(Math.random() * 255);
                    publishProgress(rgbColor);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException exception) {
                errorFlag = true;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(RgbColor[] values) {
            updateHelloWorldView(values[0]);
        }
    }

    class RgbColor {
        int red;
        int green;
        int blue;
    }
}
