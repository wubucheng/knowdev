package edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.R;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.activity.Net1414080903220MainActivity;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.adapter.KnowdevViewpager;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment.news.ITFragment;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment.news.MobileFragment;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment.news.StartupNewsFragment;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment.news.TechnoNewsFragment;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.widget.KnowDevToolbar;


/**
 * ProjectName: knowdev
 * PackName：edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment
 * Class describe:
 * Author: cheng
 * Create time: 2017/6/3 20:58
 */
public class NewsBaseFragment extends Fragment  implements ViewPager.OnPageChangeListener,TabLayout.OnTabSelectedListener {

    TabLayout newsTypeTabLayout;

    private DrawerLayout drawerLayout;

    private ViewPager viewPager;

    public static final int TECH_NEWS_PAGE = 0;
    public static final int START_NEWS_PAGE = 1;
    public static final int MOBILE_NEWS_PAGE = 2;
    public static final int IT_NEWS_PAGE = 3;


    public static final int TAG_KEJI=0;
    public static final int TAG_STARTUP=1;
    public static final int TAG_MOBILE=2;
    public static final int TAG_IT=3;

    int texts[]={R.string.keji,R.string.startup,R.string.mobile,R.string.it};
    int tags[]={TAG_KEJI,TAG_STARTUP,TAG_MOBILE,TAG_IT};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_newsbase_net1414080903220,container,false);
        viewPager = (ViewPager)view.findViewById(R.id.vpager);

        initTab(view);

        KnowDevToolbar knowDevToolbar= (KnowDevToolbar) view.findViewById(R.id.knowDevToolbar);

        knowDevToolbar.setMenuButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        TechnoNewsFragment technoNewsFragment =new TechnoNewsFragment();
        StartupNewsFragment startupNewsFragment=new StartupNewsFragment();
        MobileFragment mobileFragment=new MobileFragment();
        ITFragment itFragment=new ITFragment();

        List<Fragment> fragments=new ArrayList<Fragment>();
        fragments.add(technoNewsFragment);
        fragments.add(startupNewsFragment);
        fragments.add(mobileFragment);
        fragments.add(itFragment);

        KnowdevViewpager knowdevViewpager = new KnowdevViewpager(this.getChildFragmentManager(),fragments);


        viewPager.setAdapter(knowdevViewpager);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(4);

        return view;
    }

    public void initTab(View view){
        newsTypeTabLayout=(TabLayout)view.findViewById(R.id.tab_news_layout);
        for(int i=0;i<4;i++){
            TabLayout.Tab tab=newsTypeTabLayout.newTab();
            tab.setText(texts[i]);
            tab.setTag(tags[i]);
            newsTypeTabLayout.addTab(tab);
        }
        newsTypeTabLayout.addOnTabSelectedListener(this);

    }


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Log.i("news","attach");
        if(context instanceof Net1414080903220MainActivity){
            Net1414080903220MainActivity mainActivity= (Net1414080903220MainActivity) context;
            drawerLayout= (DrawerLayout) mainActivity.findViewById(R.id.dl_left);


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
        if (state == 2) {
            newsTypeTabLayout.getTabAt(viewPager.getCurrentItem()).select();
            }


    }




    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch ((int)tab.getTag()){
            case TAG_KEJI:
                viewPager.setCurrentItem(TAG_KEJI);
                break;
            case TAG_STARTUP:
                viewPager.setCurrentItem(TAG_STARTUP);
                break;
            case TAG_MOBILE:
                viewPager.setCurrentItem(TAG_MOBILE);
                break;
            case TAG_IT:
                viewPager.setCurrentItem(TAG_IT);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        switch ((int)tab.getTag()){
            case TAG_KEJI:
                viewPager.setCurrentItem(TAG_KEJI);
                break;
            case TAG_STARTUP:
                viewPager.setCurrentItem(TAG_STARTUP);
                break;
            case TAG_MOBILE:
                viewPager.setCurrentItem(TAG_MOBILE);
                break;
            case TAG_IT:
                viewPager.setCurrentItem(TAG_IT);
                break;
        }
    }
}
