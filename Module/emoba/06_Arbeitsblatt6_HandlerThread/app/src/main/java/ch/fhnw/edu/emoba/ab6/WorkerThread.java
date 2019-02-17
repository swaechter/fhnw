package ch.fhnw.edu.emoba.ab6;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

public class WorkerThread extends HandlerThread {

    public static final int WRITE_TEXT = 0;

    public static final int PONG_MESSAGE = 1;

    private Handler mainThreadHandler;

    private Handler workerThreadHandler = null;

    public WorkerThread(String name, Handler handler) {
        super(name);
        this.mainThreadHandler = handler;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();

        //final Handler handler = new Handler(Looper.getMainLooper());
        final Handler handler = new Handler(mainThreadHandler.getLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                // here we send something to the Main thread
                String name = Thread.currentThread().getName();
                Log.d(name, "Worker Thread is ready");
            }
        });

        // this.workerThreadHandler = new Handler() {
        this.workerThreadHandler = new Handler(getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MainActivity.PING_MESSAGE) {
                    String message = msg.getData().getString("message");
                    if (mainThreadHandler != null) {
                        Message pong = mainThreadHandler.obtainMessage();
                        pong.what = PONG_MESSAGE;
                        Bundle bundle = new Bundle();
                        bundle.putString("message", message + "/PONG");
                        pong.setData(bundle);
                        mainThreadHandler.sendMessage(pong);
                    }
                }
            }
        };
    }

    public void showText() {
        if (mainThreadHandler != null) {
            String name = Thread.currentThread().getName();
            Message message = mainThreadHandler.obtainMessage();
            message.what = WRITE_TEXT;
            Bundle content = new Bundle();
            content.putString("message", "Hello from WorkerThread: " + name);
            message.setData(content);
            mainThreadHandler.sendMessage(message);
        }
    }

    public Handler getWorkerThreadHandler() {
        return this.workerThreadHandler;
    }
}
