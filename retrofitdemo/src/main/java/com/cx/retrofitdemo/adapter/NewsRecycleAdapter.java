package com.cx.retrofitdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cx.retrofitdemo.R;
import com.cx.retrofitdemo.mvp.mvp_news.model.NewsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */

public class NewsRecycleAdapter extends RecyclerView.Adapter<NewsRecycleAdapter.ViewHolder> implements View.OnClickListener{

    private List<NewsModel.ResultBeanX.ResultBean.ListBean> newlist=new ArrayList<>();
    private OnItemClickListener mOnItemClickListener =null;
    private Context mContext;

    public void addAllNewsData(List<NewsModel.ResultBeanX.ResultBean.ListBean> newlist){
        this.newlist.addAll(newlist);
        notifyDataSetChanged();
    }

    public NewsRecycleAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(newlist.get(position).getTitle());
        Glide.with(mContext).load(newlist.get(position).getPic()).into(holder.newsPhoto);
        holder.newsDesc.setText(newlist.get(position).getContent().substring(17,newlist.get(position).getContent().length())+"");
        holder.newsTvtime.setText(newlist.get(position).getTime()+"");
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return newlist.size();
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener !=null){
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    //清除数据
    public void clearNewsData(){
        this.newlist.clear();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView newsDesc;
        public TextView newsTvtime;
        public ImageView newsPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            newsPhoto = (ImageView) itemView.findViewById(R.id.news_photo);
            title = (TextView) itemView.findViewById(R.id.title);
            newsDesc = (TextView) itemView.findViewById(R.id.news_desc);
            newsTvtime = (TextView) itemView.findViewById(R.id.news_tvtime);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view ,int pos);
    }


}
