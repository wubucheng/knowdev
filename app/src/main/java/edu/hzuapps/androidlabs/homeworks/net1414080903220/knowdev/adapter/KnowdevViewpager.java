package edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * ProjectName: knowdev
 * PackName：edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.adapter
 * Class describe:通用的viewpager
 * Author: cheng
 * Create time: 2017/6/5 10:44
 */
public class KnowdevViewpager extends FragmentPagerAdapter {

    List<Fragment> fragments;
    public KnowdevViewpager(FragmentManager fm,List<Fragment> fragments){
        super(fm);
        this.fragments=fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);

    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
