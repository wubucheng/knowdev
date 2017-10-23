package edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.R;
import edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.bean.DevArticle;


/**
 * ProjectName: knowdev
 * PackName：edu.hzuapps.androidlabs.homeworks.net1414080903220.knowdev.adapter
 * Class describe:
 * Author: cheng
 * Create time: 2017/5/16 15:09
 */
public class DevAdapter extends RecyclerView.Adapter<DevAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    OnDevTypeClickListener listener;
    public List<DevArticle> devArticles =new ArrayList<DevArticle>();
    private Context context;

    public DevAdapter(Context context,List<DevArticle> devArticles){
        this.context=context;
        this.devArticles = devArticles;
    }


    public DevAdapter(Context context){
        this.context=context;

    }


    public void setOnDevTypeClickListener(OnDevTypeClickListener listener){
        this.listener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.template_dev_item_net1414080903220,parent,false);
        DevAdapter.ViewHolder viewHolder=new DevAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<String>imagList= devArticles.get(position).getImages();
        String pubTime="";

        //处理返回的图片是否空的情况
        if(imagList!=null&&imagList.size()>0){
            Picasso.with(context).load(imagList.get(0)).into(holder.imageView);
        }
        else {
            switch(devArticles.get(position).getType()) {
                case "Android":
                    holder.imageView.setImageResource(R.drawable.default_android1414080903220);
                    break;
                case "iOS":
                    holder.imageView.setImageResource(R.drawable.default_ios1414080903220);
                    break;
                case "前端":
                    holder.imageView.setImageResource(R.drawable.default_web1414080903220);
                    break;
                case "扩展资源":
                    holder.imageView.setImageResource(R.drawable.default_expand1414080903220);
                    break;
            }
            //空时设置一张默认的图片
           // holder.imageView.setImageResource (R.drawable.default_android1414080903220);
        }
        SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateformat.parse(devArticles.get(position).getPublishedAt());
            pubTime=dateformat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.typeTv.setText(devArticles.get(position).getType());
        holder.descTv.setText(devArticles.get(position).getDesc());
        holder.authorTv.setText(devArticles.get(position).getWho());
        holder.timeTv.setText("于"+pubTime);
    }

    @Override
    public int getItemCount() {
        return devArticles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView id;
        TextView createTv;
        TextView typeTv;
        ImageView imageView;
        TextView descTv;
        TextView authorTv;
        TextView timeTv;
        public ViewHolder(View itemView) {
            super(itemView);
            typeTv= (TextView) itemView.findViewById(R.id.tv_type);
            imageView= (ImageView) itemView.findViewById(R.id.img_devarticle);
            descTv= (TextView) itemView.findViewById(R.id.title_article);
            authorTv= (TextView) itemView.findViewById(R.id.tv_author);
            timeTv= (TextView) itemView.findViewById(R.id.time_article);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            DevArticle devArticle= devArticles.get(getLayoutPosition());
            if(listener!=null){
                listener.onClick(v,devArticle);
            }
        }
    }


    public void addData(List<DevArticle>datas){
        addData(0,datas);
    }

    public void addData(int i,List<DevArticle>datas){
        if(datas!=null&&datas.size()>0){
            devArticles.addAll(datas);
            notifyItemRangeInserted(i,datas.size());
        }
    }



    public void clearData(){
        if(devArticles !=null&& devArticles.size()>0){
            int size= devArticles.size();
            devArticles.clear();
            notifyItemRangeRemoved(0,size);
        }
    }

    public List<DevArticle>getAllData(){
        return devArticles;
    }

    public  interface OnDevTypeClickListener{
        void onClick(View view,DevArticle devArticle);
    };
}
