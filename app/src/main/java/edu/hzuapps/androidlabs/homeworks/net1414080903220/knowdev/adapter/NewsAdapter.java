package edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.R;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.bean.News;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.utils.DiskLruCacheUtil;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.utils.MD5Util;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.utils.NewsDao;

/**
 * ProjectName: knowdev
 * PackName：edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.adapter
 * Class describe:
 * Author: cheng
 * Create time: 2017/5/15 22:12
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    public List<News>newsList=new ArrayList<News>();
    private LayoutInflater layoutInflater;

    private Context context;

    OnNewsTypeClickListener listener;

    NewsDao newsDao;
    DiskLruCacheUtil diskLruCacheUtil;
    private  static int HAVA_DATA=0;
    private  static int NO_DATA=1;

    public NewsAdapter(List<News>newsList,Context context){
        this.newsList=newsList;
        this.context=context;
        newsDao=new NewsDao();
        diskLruCacheUtil=new DiskLruCacheUtil(context,"cachedata");
    }

    public NewsAdapter(Context context){
        this.context=context;
        newsDao=new NewsDao();
        setData();
        getData();

        diskLruCacheUtil=new DiskLruCacheUtil(context,"cachedata");




    }

    public void setOnNewsTypeClickListener(OnNewsTypeClickListener listener){
        this.listener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.template_newlist_item_net1414080903220,parent,false);

        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
           Picasso.with(context).load(newsList.get(position).getPicUrl()).into(holder.tecNewsImg);
           holder.tecNewstTitle.setText(newsList.get(position).getTitle());
           holder.tecNewsTime.setText(newsList.get(position).getCtime());
           holder.tecNewsDesc.setText("来源:"+newsList.get(position).getDescription());

           String key=newsList.get(position).getPicUrl();
           // Log.i("the adapter key is",key);
           //diskLruCacheUtil.writeObject(key,newsList.get(position));
            //holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        if(newsList!=null&&newsList.size()>0){
            return newsList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView tecNewsImg;
        private TextView tecNewstTitle;
        private TextView tecNewsTime;
        private TextView tecNewsDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            tecNewsImg= (ImageView) itemView.findViewById(R.id.img_news);
            tecNewstTitle= (TextView) itemView.findViewById(R.id.title_news);
            tecNewsTime= (TextView) itemView.findViewById(R.id.time_news);
            tecNewsDesc= (TextView) itemView.findViewById(R.id.source_tv);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            News news=newsList.get(getLayoutPosition());
            //测试的数据，无用
            news.setCtime("2017-06-23 16:34");
            news.setTitle("自动驾驶技术的领军者不是汽车公司，而是它");
            news.updateAll("description=? and picUrl=?","腾讯科技","http://inews.gtimg.com/newsapp_ls/0/1699675331_300240/0");

            news.setDescription("腾讯科技");
            news.setPicUrl("http://inews.gtimg.com/newsapp_ls/0/1699675331_300240/0");
            news.save();
            DataSupport.deleteAll(News.class,"description<?","腾讯科技");
            List<News>newsList=DataSupport.findAll(News.class);
            //News news =DataSupport.findFirst(News.class);
           // News news =DataSupport.findLast(News.class);
            //List<News>newsList=DataSupport.select("title").find(News.class);

            if(listener!=null){
                Log.i("NewAdapter",news.getUrl());
                listener.onClick(v,news);
                }
            }
    }

    public  interface OnNewsTypeClickListener{
        void onClick(View view,News news);
    };


    public void addData(List<News>datas){
        addData(0,datas);
    }

    public void addData(int i,List<News>datas){
        if(datas!=null&&datas.size()>0){
            newsList.addAll(datas);//这里有问题,null?
            notifyItemRangeInserted(i,datas.size());
            Log.i("newsadater","add");
        }

    }

    public void clearData(){
        if(newsList!=null&&newsList.size()>0){
            int size=newsList.size();
            newsList.clear();
            notifyItemRangeRemoved(0,size);
        }
    }

    public List<News>getAllData(){
        return newsList;
    }




    public void setData(){

            newsDao.addData(newsList);

    }

    public List<News> getData(){
        newsList=newsDao.getAllData();
        return newsList;
    }





    public void setCacheData(){

    }



/*    public List<News> getCacheData(){

        String cachePath=null;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())|| !Environment.isExternalStorageRemovable()){
            cachePath=context.getExternalCacheDir().getPath();
        }else{
            cachePath=context.getCacheDir().getPath();
        }
        cachePath=cachePath+File.separator+"cachedata";

        File file=new File(cachePath);
        if(file.isDirectory()){
             String []caches=file.list();
             for(int i=0;i<caches.length&&!caches[i].equals("journal");i++){
                 News news=new News();
                 news=(News)diskLruCacheUtil.readObject(caches[i]);
                 newsList.add(news);
                 Log.i("has data",caches[i]);
             }
        }
        return newsList;
    }*/

}
