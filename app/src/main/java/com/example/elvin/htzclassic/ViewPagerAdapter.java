package com.example.elvin.htzclassic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by elvin on 17-9-11.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mlist;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void init(List<Fragment> mlist){
        this.mlist = mlist;
    }

    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }
}
