package ch.fhnw.emoba.spherocontrol.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.fhnw.emoba.spherocontrol.activities.DriveActivity;
import ch.fhnw.emoba.spherocontrol.models.SpheroModel;
import ch.fhnw.emoba.spherocontrol.tabs.TabbedFragment;
import ch.fhnw.emoba.spherocontrol.views.VectorView;
import ch.fhnw.emoba.spherocontrol.views.VectorViewListener;

public class TouchFragment extends Fragment implements TabbedFragment, VectorViewListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new VectorView(getContext(), this, VectorView.DrawStrategy.TOUCH);
    }

    @Override
    public void onFragmentTabGainedFocus() {
        // Do nothing
    }

    @Override
    public void onFragmentTabLostFocus() {
        // Do nothing
    }

    @Override
    public void onMove(float angle, float velocity) {
        SpheroModel.startDriving(DriveActivity.spheroWorkerThread, angle, velocity);
    }

    @Override
    public void onRelease() {
        SpheroModel.stopDriving(DriveActivity.spheroWorkerThread);
    }
}
