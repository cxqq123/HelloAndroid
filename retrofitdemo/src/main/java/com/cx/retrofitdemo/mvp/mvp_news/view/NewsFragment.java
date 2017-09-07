package com.cx.retrofitdemo.mvp.mvp_news.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cx.retrofitdemo.R;
import com.cx.retrofitdemo.adapter.NewsRecycleAdapter;
import com.cx.retrofitdemo.base.BaseApiKey;
import com.cx.retrofitdemo.base.BaseFragment;
import com.cx.retrofitdemo.mvp.mvp_news.model.NewsModel;
import com.cx.retrofitdemo.mvp.mvp_news.presenter.NewsPresenter;
import com.cx.retrofitdemo.util.ToastUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */

public class NewsFragment extends BaseFragment implements NewsView{


    private PullLoadMoreRecyclerView pullView;
    private FloatingActionButton fabButton;

    private RecyclerView mRecyclerView;
    private NewsRecycleAdapter newsRecycleAdapter;
    private NewsPresenter newsPresenter =new NewsPresenter(this);
    private List<NewsModel.ResultBeanX.ResultBean.ListBean> newlist=new ArrayList<>();
    private int mCount =0;
    private String  strtype="";

    public NewsFragment(){

    }


    public static NewsFragment newInstance(String title) {
        NewsFragment fragment = new NewsFragment();
        Bundle b = new Bundle();
        b.putString("msg", title);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View initView() {
        view=View.inflate(context, R.layout.fragment_news,null);
        pullView = (PullLoadMoreRecyclerView) view.findViewById(R.id.pullView);
        fabButton = (FloatingActionButton) view.findViewById(R.id.fab_button);

        // 通过getArguments--->取值
        Bundle b=getArguments();
        if(b !=null){
            strtype =b.getString("msg");
        }

        mRecyclerView=pullView.getRecyclerView();//获取RecyclerView对象、
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));

        mRecyclerView.setVerticalScrollBarEnabled(true);
        //显示下拉刷新
        pullView.setRefreshing(true);
        //设置上拉刷新文字
        pullView.setFooterViewText("loading");
        pullView.setLinearLayout(); //设置为线性布局
        pullView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {

            @Override
            public void onRefresh() {
                setRefresh();
                newsPresenter.loadNewData(strtype,"10",0, BaseApiKey.apiKey); //加载10条数据
            }

            @Override
            public void onLoadMore() {
                mCount=mCount +10;
                newsPresenter.loadNewData("头条","10",mCount,BaseApiKey.apiKey);//再加载10条数据
            }
        });

        newsPresenter.loadNewData(strtype,"10",0,BaseApiKey.apiKey);
        newsRecycleAdapter =new NewsRecycleAdapter(context);
        pullView.setAdapter(newsRecycleAdapter);
        return view;
    }

    @Override
    public void initData() {
        //填充数据
        newsRecycleAdapter.setmOnItemClickListener(new NewsRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
//                Intent intent=new Intent(context, CardTopDetailActivity.class);
//                intent.putExtra("top_weburl",newlist.get(position).getWeburl()+"");
//                intent.putExtra("top_pic",newlist.get(position).getPic()+"");
//                context.startActivity(intent);
                ToastUtils.makeTextLong(context,"跳转详情页面");
            }
        });
    }

    @Override
    public void onSuccess(NewsModel newsModel) {
        if(newsModel.getCode().equals("10000")){
            newlist.clear();
            newlist.addAll(newsModel.getResult().getResult().getList());//若返回码正确，则将得到的对象全部添加到list中
            newsRecycleAdapter.addAllNewsData(newlist);
            pullView.setPullLoadMoreCompleted();
        }

    }

    @Override
    public void onError(Throwable e) {
        Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_SHORT).show();
        pullView.setPullLoadMoreCompleted();
    }

    //设置刷新
    private void setRefresh(){
        mCount=0;
        newsRecycleAdapter.clearNewsData();// 刷新数据时，把之前的数据清除
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(newsPresenter !=null){
            newsPresenter.detachView(); //释放内存
        }
    }
}
