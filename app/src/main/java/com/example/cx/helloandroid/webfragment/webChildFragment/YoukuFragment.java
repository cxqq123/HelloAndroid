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

public class YoukuFragment extends Fragment {

    private WebView webViewYouku;
    private TitleView youkuTitleContent;
    private String url="http://www.youku.com";

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View youkuFragment =inflater.inflate(R.layout.fragment_web_youku,null);
        webViewYouku = (WebView) youkuFragment.findViewById(R.id.webView_Youku);
        youkuTitleContent = (TitleView) youkuFragment.findViewById(R.id.youku_title_content);
        youkuTitleContent.setTitleText("Youku Fragment");
        webViewYouku.loadUrl(url);
        webViewYouku.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
        return youkuFragment;
    }
}
