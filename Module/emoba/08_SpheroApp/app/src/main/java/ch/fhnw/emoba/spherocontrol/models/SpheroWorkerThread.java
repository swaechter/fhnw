package ch.fhnw.emoba.spherocontrol.models;

import android.os.Handler;
import android.os.HandlerThread;

public class SpheroWorkerThread extends HandlerThread {

    private Handler handler;

    public SpheroWorkerThread(String name) {
        super(name);
    }

    @Override
    public void onLooperPrepared() {
        handler = new Handler(getLooper());
    }

    public void postTask(Runnable runnable) {
        handler.post(runnable);
    }
}
