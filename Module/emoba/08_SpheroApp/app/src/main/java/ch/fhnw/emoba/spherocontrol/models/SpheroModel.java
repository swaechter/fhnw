package ch.fhnw.emoba.spherocontrol.models;

import android.content.Context;
import android.util.Log;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotDiscoveryListener;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

public class SpheroModel {

    private static final long DRIVE_BLOCKING_TIME = 300;

    private static long drivingTimestamp = System.currentTimeMillis();

    private static SpheroRobotProxy spheroRobotProxy;

    private static float robotAngle;

    private static float robotVelocity;

    public static boolean isDiscovering() {
        return spheroRobotProxy.isDiscovering();
    }

    public static void startDiscovering(Context context) {
        stopDiscovering();
        spheroRobotProxy.startDiscovering(context);
    }

    public static void stopDiscovering() {
        if (spheroRobotProxy.isDiscovering()) {
            spheroRobotProxy.stopDiscovering();
        }
    }

    public static void createSphero(boolean onEmulator) {
        if (spheroRobotProxy == null) {
            spheroRobotProxy = SpheroRobotFactory.createRobot(onEmulator);
        }
    }

    public static void disconnectFromSphero() {
            spheroRobotProxy.disconnect();
    }

    public static void setSpheroListener(SpheroRobotDiscoveryListener spheroRobotDiscoveryListener) {
        spheroRobotProxy.setDiscoveryListener(spheroRobotDiscoveryListener);
    }

    public static void setDiscoveryLight(SpheroWorkerThread spheroWorkerThread, final boolean discoverStatus) {
        spheroWorkerThread.postTask(new Runnable() {

            @Override
            public void run() {
                float ledRange = discoverStatus ? 0.0f : 0.5f;
                float backLedBrightness = discoverStatus ? 1.0f : 0.0f;

                spheroRobotProxy.setLed(ledRange, ledRange, ledRange);
                sleep(100); // Wait some time so the LED light can change its status
                spheroRobotProxy.setBackLedBrightness(backLedBrightness);
            }
        });
    }

    public static void setZeroHeading(SpheroWorkerThread spheroWorkerThread) {
        spheroWorkerThread.postTask(new Runnable() {

            @Override
            public void run() {
                spheroRobotProxy.setZeroHeading();
            }
        });
    }

    public static void turn(SpheroWorkerThread spheroWorkerThread, final float angle) {
        spheroWorkerThread.postTask(new Runnable() {

            @Override
            public void run() {
                robotAngle = angle;
                spheroRobotProxy.drive(robotAngle, 0);
            }
        });
    }

    public static void startDriving(SpheroWorkerThread spheroWorkerThread, final float angle, final float velocity) {
        spheroWorkerThread.postTask(new Runnable() {

            @Override
            public void run() {
                long currentTimestamp = System.currentTimeMillis();
                if (drivingTimestamp + DRIVE_BLOCKING_TIME < currentTimestamp) {
                    drivingTimestamp = currentTimestamp;
                    if (!SpheroMath.isAroundValue(robotAngle, angle, 30) || !SpheroMath.isAroundValue(robotVelocity, velocity, 0.3f)) {
                        Log.d("sphero", "Accepted at " + currentTimestamp);
                        robotAngle = angle;
                        robotVelocity = velocity;
                        spheroRobotProxy.drive(angle, velocity);
                    } else {
                        Log.d("sphero", "Similar value at " + currentTimestamp);
                    }
                } else {
                    Log.d("sphero", "Blocked at " + currentTimestamp);
                }
            }
        });
    }

    public static void stopDriving(SpheroWorkerThread spheroWorkerThread) {
        spheroWorkerThread.postTask(new Runnable() {

            @Override
            public void run() {
                if (robotVelocity != 0) {
                    robotVelocity = 0;
                    spheroRobotProxy.drive(robotAngle, robotVelocity);
                }
            }
        });
    }

    private static void sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException exception) {
            throw new RuntimeException("Unable to sleep: " + exception.getMessage(), exception);
        }
    }
}
