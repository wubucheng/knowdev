package edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.R;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.bean.News;

/**
 * ProjectName: knowdev
 * PackName：edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.adapter
 * Class describe:
 * Author: cheng
 * Create time: 2017/6/6 19:01
 */
public class EmptyAdapter extends RecyclerView.Adapter<EmptyAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    public List<News> newsList =new ArrayList<News>();
    public EmptyAdapter(Context context,List<News> newsList){
        this.context=context;
        this.newsList=newsList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.fragment_error_net1414080903220,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position==0){
            Log.i("position","is 0");
            holder.textView.setText("没有数据");
        }

    }

    @Override
    public int getItemCount() {
        if(newsList!=null&&newsList.size()>0){
            return newsList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.empty);
        }
    }
}
