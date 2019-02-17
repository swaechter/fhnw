package ch.fhnw.emoba.spherocontrol.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ch.fhnw.emoba.spherocontrol.R;
import ch.fhnw.emoba.spherocontrol.models.SpheroModel;

public class DisconnectFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disconnect, container, false);

        Button disconnectButton = view.findViewById(R.id.disconnectButton);
        disconnectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SpheroModel.disconnectFromSphero();
            }
        });

        return view;
    }
}
