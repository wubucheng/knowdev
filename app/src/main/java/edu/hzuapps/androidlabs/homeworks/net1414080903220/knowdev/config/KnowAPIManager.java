package edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.config;

/**
 * ProjectName: knowdev
 * PackName：edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.config
 * Class describe:
 * Author: cheng
 * Create time: 2017/6/2 15:43
 */
public class KnowAPIManager {

    public static final String NEW_ID="newId";
    public static final String DEV_ID="devId";
    public static final String TIANXIN="tx";
    public static final String GANK="gank";
    public static final String KEY="cc8c7d619f5cd22145eaa9be0e6ffa98";
    private static final String KEJI="keji/";
    private static final String STARTUP="startup/";
    private static final String MOBILE="mobile/";
    private static final String IT="it/";
    private static final String ANDROID="Android";
    private static final String IOS ="iOS";
    private static final String WEB="%e5%89%8d%e7%ab%af";
    private static final String EXPAND="%e6%8b%93%e5%b1%95%e8%b5%84%e6%ba%90";

    public static class API{
        public static final String TX_BASE_URL="https://api.tianapi.com/";
        public static final String TX_KEJI_URL=TX_BASE_URL+KEJI;
        public static final String TX_STARTUP_URL=TX_BASE_URL+STARTUP;
        public static final String TX_MOBILE_URL=TX_BASE_URL+MOBILE;
        public static final String TX_IT_URL=TX_BASE_URL+IT;

        public static final String GANK_BASE_URL="http://gank.io/api/data/";
        public static final String GANK_ANDROID_URL=GANK_BASE_URL+ANDROID;
        public static final String GANK_IOS_URL=GANK_BASE_URL+IOS;
        public static final String GANK_WEB_URL=GANK_BASE_URL+WEB;
        public static final String GANK_EXPAND_URL=GANK_BASE_URL+EXPAND;

        public static final String ERROR_URL="http://www.wubucheng.cn";

    }
}
