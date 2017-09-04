package com.example.cx.helloandroid.webfragment.webChildFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.cx.helloandroid.R;
import com.example.cx.helloandroid.view.TitleView;

/**
 * Created by Administrator on 2017/9/4.
 */

public class BaiduFragment extends Fragment{

    private WebView webViewBaidu;
    private TitleView baiduTitleContent;


    private String url="http://www.baidu.com";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View baiduFragment =inflater.inflate(R.layout.fragment_web_baidu,null);
        webViewBaidu = (WebView)baiduFragment.findViewById(R.id.webView_Baidu);
        baiduTitleContent = (TitleView) baiduFragment.findViewById(R.id.baidu_title_content);
        baiduTitleContent.setTitleText("Baidu Fragment");
        webViewBaidu.loadUrl(url);
        webViewBaidu.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
        return baiduFragment;
    }
}
