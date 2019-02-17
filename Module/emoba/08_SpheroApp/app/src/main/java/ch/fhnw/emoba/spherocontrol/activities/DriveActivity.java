package ch.fhnw.emoba.spherocontrol.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotDiscoveryListener;
import ch.fhnw.emoba.spherocontrol.R;
import ch.fhnw.emoba.spherocontrol.fragments.AimFragment;
import ch.fhnw.emoba.spherocontrol.fragments.DisconnectFragment;
import ch.fhnw.emoba.spherocontrol.fragments.SensorFragment;
import ch.fhnw.emoba.spherocontrol.fragments.TouchFragment;
import ch.fhnw.emoba.spherocontrol.models.SpheroModel;
import ch.fhnw.emoba.spherocontrol.models.SpheroWorkerThread;
import ch.fhnw.emoba.spherocontrol.tabs.TabListener;
import ch.fhnw.emoba.spherocontrol.tabs.TabsPagerAdapter;

public class DriveActivity extends FragmentActivity implements SpheroRobotDiscoveryListener {

    public static SpheroWorkerThread spheroWorkerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);

        spheroWorkerThread = new SpheroWorkerThread("sphero");
        spheroWorkerThread.start();

        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        tabsPagerAdapter.addFragment(new AimFragment(), "Aim");
        tabsPagerAdapter.addFragment(new TouchFragment(), "Touch");
        tabsPagerAdapter.addFragment(new SensorFragment(), "Sensor");
        tabsPagerAdapter.addFragment(new DisconnectFragment(), "Disconnect");

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(tabsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.addOnTabSelectedListener(new TabListener(tabsPagerAdapter));
        tabLayout.setupWithViewPager(viewPager);

        SpheroModel.setSpheroListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        spheroWorkerThread.quit();
    }

    @Override
    public void handleRobotChangedState(SpheroRobotBluetoothNotification spheroRobotBluetoothNotification) {
        switch (spheroRobotBluetoothNotification) {
            case Offline: {
                Intent intent = new Intent(this, PairingActivity.class);
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                break;
            }
        }
    }
}
