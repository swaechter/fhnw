package ch.fhnw.edu.emoba.ab6;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    public static final String TAG = "MainActivity";

    public static int PING_MESSAGE = 0;

    private TextView helloWorldView;

    private WorkerThread workerThread = null;

    private void updateHelloWorldView(String text) {
        helloWorldView.setText(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helloWorldView = findViewById(R.id.txtView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Handler mainThreadHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                // what will be sent to the main thread? What and data.
                if (msg.what == WorkerThread.WRITE_TEXT) {
                    String message = msg.getData().getString("message");
                    updateHelloWorldView(message);
                }
                if (msg.what == WorkerThread.PONG_MESSAGE) {
                    String message = msg.getData().getString("message");
                    updateHelloWorldView(message);
                }
            }
        };

        workerThread = new WorkerThread("worker", mainThreadHandler);
        workerThread.start(); // Thread wird gestartet.

        Handler handler = new Handler(workerThread.getLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                Log.d(name, "Main Thread is ready");
            }
        });
    }

    // gets executed when "text message" button gets clicked
    public void showText(View view) {
        if (workerThread != null) {
            workerThread.showText();

        }
    }

    public void showPingPong(View view) {
        if (workerThread != null) {
            Handler workerThreadHandler = workerThread.getWorkerThreadHandler();
            if (workerThreadHandler != null) {
                Message message = workerThreadHandler.obtainMessage();
                message.what = PING_MESSAGE;
                Bundle bundle = new Bundle();
                bundle.putString("message", "PING");
                message.setData(bundle);
                workerThreadHandler.sendMessage(message);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // see if a worker is started
        if (workerThread != null) {
            // if so... quit it. Give resources free...
            workerThread.quit();
            workerThread = null;
            Log.d(TAG, "Shutting down");
        }
    }
}
