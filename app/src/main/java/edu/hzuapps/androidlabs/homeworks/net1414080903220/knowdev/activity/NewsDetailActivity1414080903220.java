package edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;

import com.syd.oden.circleprogressdialog.core.CircleProgressDialog;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.R;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.bean.News;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.config.KnowAPIManager;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.net.WebUtil;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.widget.KnowDevToolbar;

/**
 * ProjectName: knowdev
 * PackName：edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.activity
 * Class describe:新闻详情界面
 * Author: cheng
 * Create time: 2017/5/16 0:09
 */
public class NewsDetailActivity1414080903220 extends AppCompatActivity {

    private CircleProgressDialog circleProgressDialog;
    private WebView webView;
    private KnowDevToolbar knowDevToolbar;
    private News news;
    private WebUtil webUtil;
    String url=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_net1414080903220);
        webView= (WebView) findViewById(R.id.webView);
       // webView.saveState(savedInstanceState);
        knowDevToolbar= (KnowDevToolbar) findViewById(R.id.knowDevToolbar);

        initToolbar();
        news= (News) getIntent().getSerializableExtra(KnowAPIManager.NEW_ID);
        if(news!=null){
            url=news.getUrl();
        }
//        Log.i("the new url is",url);
        if(url==null){
            finish();
        }
        circleProgressDialog=new CircleProgressDialog(this);
        circleProgressDialog.setText("内容努力加载中~~~☺");



        webUtil=new WebUtil(url);
        webUtil.initSetting(webView,this,circleProgressDialog,false);
        webView.loadUrl(url);


        knowDevToolbar.setMenuButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        knowDevToolbar.setShareButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });



    }

    private void initToolbar() {

        knowDevToolbar.setMenuIcon(R.drawable.vector_back_net1414080903220);
        knowDevToolbar.setTitle("新闻");
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(news.getTitle());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(news.getUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(news.getTitle());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        //下面的是外链的一张图片，如果是内网的图片，新浪微博解析不出来，会出错。
        oks.setImageUrl(news.getPicUrl());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(news.getUrl());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment(wares.getName());
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(news.getDescription());
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(news.getUrl());

// 启动分享GUI
        oks.show(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        ShareSDK.stopSDK(this);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            showShare();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
