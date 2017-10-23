package edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment.dev;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.activity.DevArticleDetailActivity1414080903220;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.adapter.DevAdapter;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.adapter.KnowRecyclerView;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.bean.DevArticle;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.bean.DevBaseBean;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.config.KnowAPIManager;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.net.OkHttpHelper;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.net.ResponseCallback;

/**
 * ProjectName: knowdev
 * PackName：edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.fragment.dev
 * Class describe:
 * Author: cheng
 * Create time: 2017/6/4 16:02
 */
public class ExpandFragment extends Fragment {
    OkHttpHelper okHttpHelper=OkHttpHelper.getOkInstance();
    List<DevArticle> results;
    DevAdapter devAdapter;

    KnowRecyclerView devRv;
    View mEmptyView;
    Button refreshBt;

    MaterialRefreshLayout materialRefreshLayout;
    private  static final int STATE_NORMAL=0;
    private  static final int STATE_REFREH=1;
    private  static final int STATE_MORE=2;
    private int state=STATE_NORMAL;
    Map<String,String> params=new LinkedHashMap<>();
    private String num="1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net1414080903220_dev_article, container, false);


        materialRefreshLayout= (MaterialRefreshLayout) view.findViewById(R.id.refresh);

        initEmptyView(view);
        initRefreshLayout();
        requestData();

        return view;
    }

    private void initEmptyView(View view) {

        devRv = (KnowRecyclerView) view.findViewById(R.id.emptyRecyclerView);

        mEmptyView = view.findViewById(R.id.empty_bt);
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
        n=n+1;
        num=String.valueOf(n);
        requestData();
    }

    private void requestData() {
        params.put("total","10");
        params.put("now",num);
        okHttpHelper.get(KnowAPIManager.GANK,KnowAPIManager.API.GANK_EXPAND_URL,params,new ResponseCallback<DevBaseBean>(getContext()) {
            @Override
            public void onSuccess(Response response, DevBaseBean devBaseBean) {
                results=devBaseBean.getResults();
                initData();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void initData() {
        switch (state) {
            case STATE_NORMAL:
                if (devAdapter == null) {
                    devAdapter = new DevAdapter(getActivity(), results);
                    devRv.setAdapter(devAdapter);
                    devRv.setLayoutManager(new LinearLayoutManager(ExpandFragment.this.getActivity()));
                } else {
                    devAdapter.clearData();
                    devAdapter.addData(results);
                }
                break;
            case STATE_REFREH:
                if (devAdapter == null) {
                    devAdapter = new DevAdapter(getContext(),results);
                    devRv.setAdapter(devAdapter);
                    devRv.setLayoutManager(new LinearLayoutManager(ExpandFragment.this.getActivity()));
                }else {
                    if(devAdapter.getAllData()!=null&&devAdapter.getAllData().size()>0){
                        devAdapter.clearData();
                        //Log.i(TAG,"clear data");
                    }
                    devAdapter.addData(results);
                    devRv.scrollToPosition(0);
                    materialRefreshLayout.finishRefresh();
                }
                break;
            case STATE_MORE:
                int oldSize = devAdapter.getAllData().size();
                devAdapter.addData(oldSize,results);
                devRv.scrollToPosition(oldSize);
                materialRefreshLayout.finishRefreshLoadMore();
                break;
        }

        devAdapter.setOnDevTypeClickListener(new DevAdapter.OnDevTypeClickListener() {
            @Override
            public void onClick(View view,DevArticle devArticle) {
                Intent intent = new Intent(getActivity(), DevArticleDetailActivity1414080903220.class);
                intent.putExtra(KnowAPIManager.DEV_ID, devArticle);
                startActivity(intent);
            }
        });
    }
}
