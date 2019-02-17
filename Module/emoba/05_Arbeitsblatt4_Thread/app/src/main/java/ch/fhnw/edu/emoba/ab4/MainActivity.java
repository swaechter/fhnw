package ch.fhnw.edu.emoba.ab4;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends Activity {

    private static final String STATE_RUNNING = "task.running";

    private Thread colorThread;

    private AtomicBoolean isCancelled;

    private void updateHelloWorldView(int red, int green, int blue) {
        TextView textView = findViewById(R.id.txtView);
        textView.setBackgroundColor(Color.rgb(red, green, blue));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.txtView);
        textView.setText(getResources().getString(R.string.app_name));

        // Check if we have to restore and rerun the thread
        isCancelled = new AtomicBoolean();
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_RUNNING)) {
            // Restart
            colorThread = new ColorThread();
            colorThread.start();
        } else {
            // Idle
            isCancelled.set(true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Check if the thread is running and we have to rerun it later on
        super.onSaveInstanceState(outState);
        if (colorThread != null) {
            outState.putBoolean(STATE_RUNNING, true);
            isCancelled.set(true);
        }
    }

    public void start(View view) {
        // Wipe the cancel flag and fire up the thread
        if (colorThread == null) {
            isCancelled.set(false);
            colorThread = new ColorThread();
            colorThread.start();
        }
    }

    public void stop(View view) {
        // Set the cancel flag and dereference the thread
        if (colorThread != null) {
            isCancelled.set(true);
            colorThread = null;
        }
    }

    class ColorThread extends Thread {

        private boolean errorFlag;

        @Override
        public void run() {
            try {
                while (!errorFlag && !isCancelled.get()) {
                    final int red = (int) Math.round(Math.random() * 255);
                    final int green = (int) Math.round(Math.random() * 255);
                    final int blue = (int) Math.round(Math.random() * 255);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            updateHelloWorldView(red, green, blue);
                        }
                    });
                    Thread.sleep(1000);
                }
            } catch (InterruptedException exception) {
                errorFlag = true;
            }
        }
    }
}
