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

public class TaobaoFragment extends Fragment{

    private WebView webViewTaobao;
    private TitleView taobaoTitleContent;


    private String url="http://www.taobao.com";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View taoBaoFragment =inflater.inflate(R.layout.fragment_web_taobao,null);
        webViewTaobao = (WebView) taoBaoFragment.findViewById(R.id.webView_Taobao);
        taobaoTitleContent = (TitleView) taoBaoFragment.findViewById(R.id.taobao_title_content);
        taobaoTitleContent.setTitleText("Taobao Fragment");

        taobaoTitleContent.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webViewTaobao.canGoBack()){
                    webViewTaobao.goBack(); //若页面能返回,则返回
                }else{
                    getActivity().finish();
                }
            }
        });
        webViewTaobao.loadUrl(url);
        webViewTaobao.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
        return taoBaoFragment;
    }
}
