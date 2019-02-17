package ch.fhnw.emoba.spherocontrol.fragments;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import ch.fhnw.emoba.spherocontrol.activities.DriveActivity;
import ch.fhnw.emoba.spherocontrol.models.SpheroMath;
import ch.fhnw.emoba.spherocontrol.models.SpheroModel;
import ch.fhnw.emoba.spherocontrol.tabs.TabbedFragment;
import ch.fhnw.emoba.spherocontrol.views.VectorView;
import ch.fhnw.emoba.spherocontrol.views.VectorViewListener;

public class SensorFragment extends Fragment implements TabbedFragment, VectorViewListener, SensorEventListener {

    private static final double MIN_ANGLE = 2;

    private static final double THROTTLE_RATIO = 0.2;

    private SensorManager sensorManager;

    private Sensor sensor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new VectorView(getActivity(), this, VectorView.DrawStrategy.SENSOR);
    }

    @Override
    public void onStart() {
        super.onStart();
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager == null) {
            throw new RuntimeException("Unable to get the system sensor manager");
        }

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor == null) {
            throw new RuntimeException("Unable to initialize the rotation sensor");
        }
    }

    @Override
    public void onFragmentTabGainedFocus() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onFragmentTabLostFocus() {
        sensorManager.unregisterListener(this);
        SpheroModel.stopDriving(DriveActivity.spheroWorkerThread);
    }

    @Override
    public void onMove(float angle, float velocity) {
    }

    @Override
    public void onRelease() {
        // Do nothing
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double sensorX = event.values[0];
        double sensorY = event.values[1];

        float circleAngle = SpheroMath.calculateTouchAngle(sensorX, sensorY);
        float spheroAngle = SpheroMath.calculateSensorAngle(sensorX, sensorY);
        float sensorVelocity = (float) Math.max(0, (spheroAngle - MIN_ANGLE) / 6d);
        double sensorSum = Math.abs(sensorX) + Math.abs(sensorY);

        if (sensorVelocity > 1.0) {
            sensorVelocity = 1.0f;
        }

        if (sensorSum > MIN_ANGLE) {
            float spheroVelocity = (float) (sensorVelocity * THROTTLE_RATIO);
            triggerTouchEvent(circleAngle, sensorVelocity);
            SpheroModel.startDriving(DriveActivity.spheroWorkerThread, spheroAngle, spheroVelocity);
        } else {
            triggerTouchEvent(circleAngle, 0.0f);
            SpheroModel.stopDriving(DriveActivity.spheroWorkerThread);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }

    private void triggerTouchEvent(float angle, float velocity) {
        View view = getView();
        if (view != null && view instanceof VectorView) {
            VectorView vectorView = (VectorView) view;

            Point displayPoint = vectorView.calculateDisplayPoint(angle, velocity);
            long downTime = SystemClock.uptimeMillis();
            long eventTime = downTime + 100;
            int metaState = 0;

            MotionEvent motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, displayPoint.x, displayPoint.y, metaState);
            view.dispatchTouchEvent(motionEvent);
        }
    }
}
