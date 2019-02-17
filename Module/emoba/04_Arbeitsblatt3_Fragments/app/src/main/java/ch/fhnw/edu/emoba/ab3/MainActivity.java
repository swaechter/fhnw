package ch.fhnw.edu.emoba.ab3;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isTwoPane = findViewById(R.id.helloworld_container) != null;
    }

    public void onButtonClick(View v) {
        if (isTwoPane) {
            FragmentManager fragmentManager = getFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.helloworld_container);
            if (fragment == null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.helloworld_container, new CounterFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        } else {
            Intent intent = new Intent(this, CounterActivity.class);
            startActivity(intent);
        }
    }
}
