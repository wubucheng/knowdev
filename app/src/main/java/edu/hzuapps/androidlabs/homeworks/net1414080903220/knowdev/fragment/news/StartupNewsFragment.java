package edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.squareup.okhttp.Response;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.R;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.activity.NewsDetailActivity1414080903220;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.adapter.KnowRecyclerView;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.adapter.NewsAdapter;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.bean.News;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.bean.NewsBaseBean;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.config.KnowAPIManager;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.net.OkHttpHelper;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.net.ResponseCallback;

/**
 * ProjectName: knowdev
 * PackName：edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment.news
 * Class describe:
 * Author: cheng
 * Create time: 2017/6/4 15:09
 */
public class StartupNewsFragment extends Fragment {
    private static final String TAG="StartupNewsFragment";
    NewsAdapter newsAdapter;
    List<News> newlist;
    View view;

    OkHttpHelper okHttpHelper= OkHttpHelper.getOkInstance();

    MaterialRefreshLayout materialRefreshLayout;
    private  static final int STATE_NORMAL=0;
    private  static final int STATE_REFREH=1;
    private  static final int STATE_MORE=2;
    private int state=STATE_NORMAL;
    Map<String,String> params=new LinkedHashMap<>();
    private String num="10";
    private KnowRecyclerView newsItemRV;
    private View mEmptyView;
    Button refreshBt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_net1414080903220_newslist,container,false);
       // newsItemRV= (RecyclerView) view.findViewById(R.id.recyclerview_news);
        materialRefreshLayout= (MaterialRefreshLayout) view.findViewById(R.id.refresh);
        initRefreshLayout();
        initEmptyView(view);
        requestData();
        return view;
    }

    private void initEmptyView(View view) {
        newsItemRV = (KnowRecyclerView) view.findViewById(R.id.emptyRecyclerView);
        mEmptyView = view.findViewById(R.id.empty);
        refreshBt = (Button) view.findViewById(R.id.empty_bt);
        refreshBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });
    }

    private void initRefreshLayout() {
        materialRefreshLayout.setLoadMore(true);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...
                loadMoreData();
            }
        });
    }



    private  void refreshData(){
        state=STATE_REFREH;
        requestData();
    }

    private void loadMoreData() {
        state=STATE_MORE;
        int n=Integer.parseInt(num);
        n=n+10;
        num=String.valueOf(n);
        requestData();
    }

    public void requestData(){
        Map<String,String> params=new LinkedHashMap<>();
        params.put("key", KnowAPIManager.KEY);
        params.put("num",num);
        Log.i(TAG,""+params);
        okHttpHelper.get(KnowAPIManager.TIANXIN,KnowAPIManager.API.TX_STARTUP_URL,params, new ResponseCallback<NewsBaseBean>(getContext()){


            @Override
            public void onSuccess(Response response, NewsBaseBean newsBaseBean) {
                Log.i(TAG,"cy success:"+newsBaseBean);
                newlist=newsBaseBean.getNewslist();
                //Log.i(TAG,"list:"+newlist);
                initData(newlist);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                Log.i(TAG,"error");
                e.printStackTrace();
            }
        });
    }

    private void initData(List<News>newses) {

        switch (state) {
            case STATE_NORMAL:
                if (newsAdapter == null) {
                    newsAdapter = new NewsAdapter(newses, getActivity());
                    newsItemRV.setAdapter(newsAdapter);
                    newsItemRV.setEmptyView(mEmptyView); //设置空布局,这是关键
                    newsItemRV.setLayoutManager(new LinearLayoutManager(StartupNewsFragment.this.getActivity()));
                } else {
                    newsAdapter.clearData();
                    newsAdapter.addData(newses);
                }
                break;
            case STATE_REFREH:
                if (newsAdapter == null) {
                    newsAdapter = new NewsAdapter(newses, getActivity());
                    newsItemRV.setAdapter(newsAdapter);
                    newsItemRV.setLayoutManager(new LinearLayoutManager(StartupNewsFragment.this.getActivity()));
                }else {
                    if(newsAdapter.getAllData()!=null&&newsAdapter.getAllData().size()>0){
                        newsAdapter.clearData();
                        Log.i(TAG,"clear data");
                    }

                    newsAdapter.addData(newses);
                    newsItemRV.scrollToPosition(0);
                    materialRefreshLayout.finishRefresh();
                }
                break;
            case STATE_MORE:
                int oldSize=newsAdapter.getAllData().size();
                newsAdapter.clearData();
                newsAdapter.addData(newses);
                newsItemRV.scrollToPosition(oldSize);
                materialRefreshLayout.finishRefreshLoadMore();
                break;
        }

        newsAdapter.setOnNewsTypeClickListener(new NewsAdapter.OnNewsTypeClickListener() {
            @Override
            public void onClick(View view, News news) {
                //Log.i(TAG,url);
                Intent intent = new Intent(getActivity(), NewsDetailActivity1414080903220.class);
                intent.putExtra(KnowAPIManager.NEW_ID, news);
                startActivity(intent);
            }
        });
    }
}
