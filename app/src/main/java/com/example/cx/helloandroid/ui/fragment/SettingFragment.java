package com.example.cx.helloandroid.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.cx.helloandroid.R;
import com.example.cx.helloandroid.view.TitleView;

/**
 * Created by Administrator on 2017/8/4.
 */

public class SettingFragment extends Fragment implements View.OnClickListener{

    private WebView webView;
    private Button btnBaidu;
    private Button btnYouku;
    private Button btnTaobaos;

    private TitleView titleContent;

    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View settingFragment=inflater.inflate(R.layout.fragment_setting,null);
        btnBaidu = (Button) settingFragment.findViewById(R.id.btn_baidu);
        btnYouku = (Button) settingFragment.findViewById(R.id.btn_youku);
        btnTaobaos = (Button) settingFragment.findViewById(R.id.btn_taobaos);
        titleContent = (TitleView) settingFragment.findViewById(R.id.title_content);
        titleContent.setTitleText("WebView 测试Demo");
        titleContent.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoBack()){
                    webView.goBack();
                }else{
                    getActivity().finish();
                }
            }
        });
        btnBaidu.setOnClickListener(this);
        btnYouku.setOnClickListener(this);
        btnTaobaos.setOnClickListener(this);
        webView = (WebView) settingFragment.findViewById(R.id.setting_WebView);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);  //webView中设置可以调用js
        //webView.addJavascriptInterface(this,"wv"); //传递参数 （可以在js代码中写 wv.sayHello("msg")）这段话
//        webView.loadUrl("http://www.baidu.com"); //加载本地中的html网页中的js代码
        return settingFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_baidu:
                url="http://www.baidu.com";
                loadWebUrl(url);
                break;
            case R.id.btn_youku:
                url="http://www.youku.com";
                loadWebUrl(url);
                break;
            case R.id.btn_taobaos:
                url="http://www.taobao.com";
                loadWebUrl(url);
                break;
        }
    }

    public void loadWebUrl(final String url){
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
