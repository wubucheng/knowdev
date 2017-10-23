package edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.litepal.LitePalApplication;

import cn.sharesdk.framework.ShareSDK;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.utils.LitePalHelper;


/**
 * ProjectName: knowdev
 * PackName：edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev
 * Class describe:
 * Author: cheng
 * Create time: 2017/5/31 16:28
 */
public class KnowApplication extends LitePalApplication {

    public static KnowApplication knowApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        if (knowApplication == null) {
            knowApplication = this;
        }
        LitePalHelper litePalHelper=new LitePalHelper();
        ShareSDK.initSDK(this);

    }
    public static Context geContext(){
        return knowApplication.getApplicationContext();
    }
    public static int getAppVersion(){
        Context context = geContext();
        try {
            PackageInfo info=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;

    }

}
