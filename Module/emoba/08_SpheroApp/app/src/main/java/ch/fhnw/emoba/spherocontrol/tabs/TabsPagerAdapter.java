package ch.fhnw.emoba.spherocontrol.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentObjects;

    private final List<String> fragmentTitles;

    public TabsPagerAdapter(FragmentManager manager) {
        super(manager);
        this.fragmentObjects = new ArrayList<>();
        this.fragmentTitles = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentObjects.get(position);
    }

    @Override
    public int getCount() {
        return fragmentObjects.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentObjects.add(fragment);
        fragmentTitles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }
}
