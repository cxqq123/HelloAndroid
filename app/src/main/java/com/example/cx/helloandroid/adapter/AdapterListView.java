package com.example.cx.helloandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cx.helloandroid.R;
import com.example.cx.helloandroid.view.PowerImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */

public class AdapterListView extends BaseAdapter{
    private List<PowerImageView> data;
    private List<String> tvData;
    private Context mContext;

    public AdapterListView(Context mContext, List<PowerImageView> data,List<String> tvData){
        this.mContext=mContext;
        this.data=data;
        this.tvData=tvData;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        ViewHolder holder=null;
//        if(view==null){
//            view=View.inflate(mContext, R.layout.my_list_view_item,null);
//            holder=new ViewHolder();
//            holder.textView=view.findViewById(R.id.tv_view);
//            view.setTag(holder);
//        }else{
//            holder=(ViewHolder) view.getTag();
//        }
//        holder.textView.setText(data.get(i));
//        return view;

        PowerViewHolder holder=null;
        if(view==null){
            view = View.inflate(mContext, R.layout.my_list_view_item, null);
            holder=new PowerViewHolder();
            holder.powerImageView= (PowerImageView) view.findViewById(R.id.power_view);
            holder.tv_text= (TextView) view.findViewById(R.id.tv_text);
            view.setTag(holder);
        }else{
            holder=(PowerViewHolder) view.getTag();
        }

        holder.powerImageView.addView(data.get(i));
        holder.tv_text.setText(tvData.get(i));
        return view;
    }
//
//    class ViewHolder{
//        public TextView textView;
//    }

    class PowerViewHolder{
        public PowerImageView powerImageView;
        public TextView tv_text;
    }
}
