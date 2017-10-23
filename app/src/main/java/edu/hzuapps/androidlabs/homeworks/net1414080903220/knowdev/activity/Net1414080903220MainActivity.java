package edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.R;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.adapter.KnowdevViewpager;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment.DevBaseFragment;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment.NewsBaseFragment;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.widget.KnowDevToolbar;

/**
 * ProjectName: knowdev
 * PackName：edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.activity
 * Class describe:主活动类，主要用于管理Fragment
 * Author: cheng
 * Create time: 2017/5/3 12:43
 */


public class Net1414080903220MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private RadioGroup tabRg;
    private RadioButton newsRb;
    private RadioButton devRb;
    private ViewPager viewPager;

    DrawerLayout drawerLayout;
    KnowDevToolbar knowDevToolbar;
    private KnowdevViewpager knowdevViewpager;
    private FragmentTransaction transaction;
    public static final int NEWS_PAGE = 0;
    public static final int DEV_PAGE = 1;


    private NewsBaseFragment newsBaseFragment;
    private DevBaseFragment devFragment;
    List<Fragment> fragmentList=new ArrayList<Fragment>();
    private long exitTime = 0;
    private boolean isOpen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_net1414080903220_main);



        newsBaseFragment=new NewsBaseFragment();
        devFragment = new DevBaseFragment();
        fragmentList.add(newsBaseFragment);
        fragmentList.add(devFragment);

        knowdevViewpager = new KnowdevViewpager(this.getSupportFragmentManager(),fragmentList);
        initView();

        tabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_news:
                        viewPager.setCurrentItem(NEWS_PAGE);
                        break;
                    case R.id.rb_dev_article:
                        viewPager.setCurrentItem(DEV_PAGE);
                        break;
                }
            }
        });

    }

    private void initView() {
        tabRg = (RadioGroup) findViewById(R.id.rg_tab_bar);
        newsRb = (RadioButton) findViewById(R.id.rb_news);
        devRb = (RadioButton) findViewById(R.id.rb_dev_article);

        drawerLayout= (DrawerLayout)findViewById(R.id.dl_left);
        viewPager = (ViewPager) findViewById(R.id.vpager);
        viewPager.setAdapter(knowdevViewpager);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(0);
        newsRb.setChecked(true);
    }

    private void initDrawer() {


        knowDevToolbar.setMenuButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.i("activity","the position is"+position);
    }

    /**
     * 处理滑动完毕后radiobutton的状态
     *
     * @param state
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 2) {
            switch (viewPager.getCurrentItem()) {
                case NEWS_PAGE:
                    newsRb.setChecked(true);
                    break;
                case DEV_PAGE:
                    devRb.setChecked(true);
                    break;
            }
        }
    }



    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }else if(keyCode == KeyEvent.KEYCODE_MENU) {
            // 监控菜单键
            isOpen=true;
            if(isOpen){
                drawerLayout.openDrawer(GravityCompat.START);
                isOpen=false;
            }else{
                drawerLayout.closeDrawer(GravityCompat.START);
            }


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次后退键退出knowdev",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            //Log.e(TAG, "exit application");
            this.finish();
            //          System.exit(0);
        }
    }



}
