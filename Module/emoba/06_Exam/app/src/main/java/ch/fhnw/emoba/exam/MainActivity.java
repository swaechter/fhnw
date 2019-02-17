package ch.fhnw.emoba.exam;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final int DO_SOMETHING = 1;

    private static final String KEY_DATA = "data";

    private WorkerThread workerThread;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        workerThread = new WorkerThread("worker");
        workerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        workerThread.quit();
    }

    public void doSomething(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_DATA, "DO_SOMETHING");

        Message message = handler.obtainMessage();
        message.what = DO_SOMETHING;
        message.setData(bundle);
        message.sendToTarget();
        //handler.sendMessage(message);
    }

    public class WorkerThread extends HandlerThread {

        public WorkerThread(String name) {
            super(name);
        }

        @Override
        public void onLooperPrepared() {
            handler = new Handler(getLooper()) {

                @Override
                public void handleMessage(Message message) {
                    if (message.what == DO_SOMETHING) {
                        Bundle bundle = message.getData();
                        Log.d("exam", bundle.getString(KEY_DATA) + " message received");
                    }
                }
            };
        }
    }
}
