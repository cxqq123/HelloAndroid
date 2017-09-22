package com.cx.web;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cx.web.db.DBManager;
import com.cx.web.model.ModelUrl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

//    private String url="http://www.baidu.com";
    private String url="http://www.114best.com";
    private boolean isMatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView=(WebView) findViewById(R.id.web_view);
        webView.requestFocus();
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
    /*    //设置缓存模式
        if(NetUtil.getNetworkState(context) ==-1){
            //无网
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        }else{
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
*/
        //支持多窗口
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);

        final DBManager dbManager=new DBManager(WebViewActivity.this);
        final List<ModelUrl> list=dbManager.getAllUrl();
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, final WebResourceRequest request) {

                if(dbManager!=null) {
                    for (ModelUrl modelUrl : list) {
                        //若匹配url成功
                        if (modelUrl.urlName.equals(request.getUrl().toString())) {
                            if (modelUrl != null) {
                                URL uri = null;
                                try {
                                    uri = new URL(request.getUrl().toString());
                                    URLConnection mConnection = uri.openConnection();
                                    String urlName = modelUrl.urlName;
                                    byte[] data = modelUrl.urlBody;
                                    int type = modelUrl.urlType;
                                    InputStream is = new ByteArrayInputStream(data);
                                    isMatch = true;
//                            InputStream is=getApplicationContext().getAssets().open("images/ic_logo.png");
//                            return new WebResourceResponse("image/png", "utf-8", is);

                                    return new WebResourceResponse("text/html", "utf-8", is);

                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                }
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

                if(dbManager!=null){
                    ModelUrl modelUrl=dbManager.getDefaultUrl(url);  //验证数据库去查找这条数据
                    if(modelUrl!=null && modelUrl.urlName != null){
                        try {
                            URL uri = new URL(url);
                            URLConnection mConnection = uri.openConnection();
                            String urlName=modelUrl.urlName;
                            byte[] data=modelUrl.urlBody;
                            int type=modelUrl.urlType;
                            InputStream is = new ByteArrayInputStream(data);
                            isMatch =true;
//                            InputStream is=getApplicationContext().getAssets().open("images/ic_logo.png");
                            return new WebResourceResponse(mConnection.getContentType(), mConnection.getHeaderField("encoding"), is);
//                            return new WebResourceResponse("image/png", "utf-8", is);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                return super.shouldInterceptRequest(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){

        });

        if(!isMatch){
            webView.loadUrl(url);
        }

    }
}
