package edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.R;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.activity.Net1414080903220MainActivity;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.adapter.KnowdevViewpager;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment.dev.AndroidFragment;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment.dev.ExpandFragment;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment.dev.IOSFragment;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment.dev.WebFragment;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.widget.KnowDevToolbar;

/**
 * ProjectName: knowdev
 * PackName：edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment
 * Class describe:
 * Author: cheng
 * Create time: 2017/6/4 15:52
 */
public class DevBaseFragment extends Fragment implements ViewPager.OnPageChangeListener,TabLayout.OnTabSelectedListener  {


    public static final String TAG="DevBaseFragment";

    DrawerLayout drawerLayout;
    private KnowDevToolbar knowDevToolbar;
    TabLayout devTypeTabLayout;

    public static final int TAG_ANDROID=0;
    public static final int TAG_IOS=1;
    public static final int TAG_WEB=2;
    public static final int TAG_EXPAND=3;

    private ViewPager viewPager;

    int texts[]={R.string.android,R.string.ios,R.string.web,R.string.expand};
    int tags[]={TAG_ANDROID,TAG_IOS,TAG_WEB,TAG_EXPAND};

    AndroidFragment androidFragment;
    IOSFragment iosFragment;
    WebFragment webFragment;
    ExpandFragment expandFragment;
    List<Fragment> fragments=new ArrayList<Fragment>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_devbase_net1414080903220,container,false);

        knowDevToolbar= (KnowDevToolbar) view.findViewById(R.id.knowDevToolbar);

        viewPager = (ViewPager)view.findViewById(R.id.vpager);

        knowDevToolbar.setMenuButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        initTab(view);

        androidFragment=new AndroidFragment();
        iosFragment=new IOSFragment();
        webFragment=new WebFragment();
        expandFragment=new ExpandFragment();

        fragments.add(androidFragment);
        fragments.add(iosFragment);
        fragments.add(webFragment);
        fragments.add(expandFragment);

        KnowdevViewpager knowdevViewpager=new KnowdevViewpager(this.getChildFragmentManager(),fragments);
        viewPager.setAdapter(knowdevViewpager);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(4);

        return view;
    }





    private void initTab(View view) {
        devTypeTabLayout=(TabLayout)view.findViewById(R.id.tab_dev_layout);

        for(int i=0;i<texts.length;i++){
            TabLayout.Tab tab=devTypeTabLayout.newTab();
            tab.setText(texts[i]);
            tab.setTag(tags[i]);
            devTypeTabLayout.addTab(tab);
        }
        devTypeTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof Net1414080903220MainActivity){
            Net1414080903220MainActivity mainActivity= (Net1414080903220MainActivity) context;
            drawerLayout= (DrawerLayout) mainActivity.findViewById(R.id.dl_left);

        }


    }



    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        switch ((int)tab.getTag()){
            case TAG_ANDROID:
                viewPager.setCurrentItem(TAG_ANDROID);
                break;
            case TAG_IOS:
                viewPager.setCurrentItem(TAG_IOS);
                break;
            case TAG_WEB:
                viewPager.setCurrentItem(TAG_WEB);
                break;
            case TAG_EXPAND:
                viewPager.setCurrentItem(TAG_EXPAND);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        switch ((int)tab.getTag()){
            case TAG_ANDROID:
                viewPager.setCurrentItem(TAG_ANDROID);
                break;
            case TAG_IOS:
                viewPager.setCurrentItem(TAG_IOS);
                break;
            case TAG_WEB:
                viewPager.setCurrentItem(TAG_WEB);
                break;
            case TAG_EXPAND:
                viewPager.setCurrentItem(TAG_EXPAND);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(state==2){
            devTypeTabLayout.getTabAt(viewPager.getCurrentItem()).select();
        }
    }
}
