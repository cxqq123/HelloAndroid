package com.cx.web;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cx.web.db.DBManager;
import com.cx.web.model.ModelUrl;
import com.cx.web.util.LogUtil;
import com.cx.web.util.RexUtil;
import com.cx.web.view.TitleView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ActivityWeb extends AppCompatActivity {

    private LinearLayout llWebView;
    private Button btnOne;
    private Button btnTwo;
    private Button btnThree;

    private Button btnShowWeb;

    private WebView webView;
    private Context mContext;


//    private String url="https://www.baidu.com";
    private String url="http://www.114best.com";
//    private String url="https://m.baidu.com/static/search/baiduapp_icon.png";

    private TitleView baiduTitleContent;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// 若Android 系统版本 高于5.0 则直接显示透明式状态栏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            //若是4.4以上的手机
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }else if(Build.VERSION.SDK_INT <Build.VERSION_CODES.KITKAT){
            //4.4以下的手机
//            ToastUtils.makeTextShort(mContext,"你的android 版本低于4.4太低，建议你升级android系统!!!");
        }
        setContentView(R.layout.activity_web_layout);
        mContext=ActivityWeb.this;
        dbManager=new DBManager(mContext);
        initView();
        initWebView();
    }

    public void initWebView(){
        //动态加载WebView
        webView =new WebView(mContext);
        ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(lp);
        llWebView.addView(webView);

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

        onLoad();
    }

    public void initView(){
        llWebView = (LinearLayout) findViewById(R.id.ll_WebView);
        btnOne = (Button) findViewById(R.id.btn_one);
        btnTwo = (Button) findViewById(R.id.btn_two);
        btnThree = (Button) findViewById(R.id.btn_three);
        baiduTitleContent = (TitleView) findViewById(R.id.baidu_title_content);


        btnShowWeb = (Button) findViewById(R.id.btn_showWeb);
        btnShowWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击事件 跳转到WebViewActivity
                Intent intent =new Intent(mContext,WebViewActivity.class);
                startActivity(intent);
            }
        });


        baiduTitleContent.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoBack()){
                    webView.goBack();
                }else{
                    finish();
                }
            }
        });

    }

    public String getHtmlFromUrl(String url){
        String res="";
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder()
                            .url(url)
                            .build();
        try {
            Response response=okHttpClient.newCall(request).execute();
            if(response.isSuccessful()){
                res=response.body().string();
                return res;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void onLoad(){
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, final WebResourceRequest request) {

                String host = RexUtil.getWebHost(request.getUrl().toString());
                String url=request.getUrl().toString();
                ModelUrl modelUrl=null;
                //判断url为css文件
                if (host.contains("114best.com") && (url.contains(".css"))) {
                    try {
                        URL uri = new URL(url);
                        URLConnection mConnection = uri.openConnection();
                        if (mConnection != null) {
                            InputStream input = mConnection.getInputStream();   //获取网络数据的输入流
                            ByteArrayOutputStream output = new ByteArrayOutputStream();
                            String mimeType=mConnection.getContentType();
                            byte[] buffer = new byte[4096];
                            int n = 0;
                            while (-1 != (n = input.read(buffer))) {
                                output.write(buffer, 0, n);
                            }
                            byte[] data = output.toByteArray();
                            input.close();
                            output.close();
                            //将css文件保存
                            modelUrl =new ModelUrl();
                            modelUrl.urlName = url;
                            modelUrl.urlBody = data;
                            modelUrl.urlType = 0;
                            modelUrl.mimeType=mimeType;
                            LogUtil.Log("CX", "css文件:" + data);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //判断url为js文件
                else if (host.contains("114best.com") && (url.contains(".js"))) {
                    try {
                        URL uri = new URL(url);
                        URLConnection mConnection = uri.openConnection();
                        if (mConnection != null) {
                            InputStream input = mConnection.getInputStream();   //获取网络数据的输入流
                            ByteArrayOutputStream output = new ByteArrayOutputStream();
                            String mimeType=mConnection.getContentType();
                            byte[] buffer = new byte[4096];
                            int n = 0;
                            while (-1 != (n = input.read(buffer))) {
                                output.write(buffer, 0, n);
                            }
                            byte[] data = output.toByteArray();
                            input.close();
                            output.close();
                            //将css文件保存
                            modelUrl =new ModelUrl();
                            modelUrl.urlName = url;
                            modelUrl.urlBody = data;
                            modelUrl.urlType = 1;
                            modelUrl.mimeType=mimeType;
                            LogUtil.Log("CX", "js文件:" + data);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                //判断url为图片
                else if (host.contains("114best.com") && (url.contains(".png") || url.contains(".jpg") || url.contains(".gif"))) {
                    try {
                        URL uri = new URL(url);
                        URLConnection mConnection = uri.openConnection();
                        if (mConnection != null) {
                            InputStream input = mConnection.getInputStream();   //获取网络数据的输入流
                            ByteArrayOutputStream output = new ByteArrayOutputStream();
                            String mimeType=mConnection.getContentType();
                            byte[] buffer = new byte[4096];
                            int n = 0;
                            while (-1 != (n = input.read(buffer))) {
                                output.write(buffer, 0, n);
                            }
                            byte[] data = output.toByteArray();
                            input.close();
                            output.close();
                            //将css文件保存
                            modelUrl =new ModelUrl();
                            modelUrl.urlName = url == null ? "" : url;
                            modelUrl.urlBody = data;
                            modelUrl.urlType = 2;
                            modelUrl.mimeType=mimeType;
                            LogUtil.Log("CX", "图片文件:" + data);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //判断url为html
                else if (host.contains("114best.com") && (url.contains(".html") || url.contains(".htm"))) {
                    try {
                        URL uri = new URL(url);
                        URLConnection mConnection = uri.openConnection();
                        if (mConnection != null) {
                            InputStream input = mConnection.getInputStream();   //获取网络数据的输入流
                            ByteArrayOutputStream output = new ByteArrayOutputStream();
                            String mimeType=mConnection.getContentType();
                            byte[] buffer = new byte[4096];
                            int n = 0;
                            while (-1 != (n = input.read(buffer))) {
                                output.write(buffer, 0, n);
                            }
                            byte[] data = output.toByteArray();
                            input.close();
                            output.close();
                            //将html文件保存
                            modelUrl =new ModelUrl();
                            modelUrl.urlName = url;
                            modelUrl.urlBody = data;
                            modelUrl.urlType = 3;
                            modelUrl.mimeType=mimeType;
                            LogUtil.Log("CX", "html文件:" + data);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                dbManager.AddUrl(modelUrl);
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                String host = RexUtil.getWebHost(url);
                ModelUrl modelUrl=null;
                //判断url为css文件
                if (host.contains("114best.com") && (url.contains(".css"))) {
                    try {
                        URL uri = new URL(url);
                        URLConnection mConnection = uri.openConnection();
                        if (mConnection != null) {
                            InputStream input = mConnection.getInputStream();   //获取网络数据的输入流
                            ByteArrayOutputStream output = new ByteArrayOutputStream();
                            String mimeType=mConnection.getContentType();
                            byte[] buffer = new byte[4096];
                            int n = 0;
                            while (-1 != (n = input.read(buffer))) {
                                output.write(buffer, 0, n);
                            }
                            byte[] data = output.toByteArray();
                            input.close();
                            output.close();
                            //将css文件保存
                            modelUrl =new ModelUrl();
                            modelUrl.urlName = url;
                            modelUrl.urlBody = data;
                            modelUrl.urlType = 0;
                            modelUrl.mimeType=mimeType;
                            LogUtil.Log("CX", "css文件:" + data);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //判断url为js文件
                else if (host.contains("114best.com") && (url.contains(".js"))) {
                    try {
                        URL uri = new URL(url);
                        URLConnection mConnection = uri.openConnection();
                        if (mConnection != null) {
                            InputStream input = mConnection.getInputStream();   //获取网络数据的输入流
                            ByteArrayOutputStream output = new ByteArrayOutputStream();
                            String mimeType=mConnection.getContentType();
                            byte[] buffer = new byte[4096];
                            int n = 0;
                            while (-1 != (n = input.read(buffer))) {
                                output.write(buffer, 0, n);
                            }
                            byte[] data = output.toByteArray();
                            input.close();
                            output.close();
                            //将css文件保存
                            modelUrl =new ModelUrl();
                            modelUrl.urlName = url;
                            modelUrl.urlBody = data;
                            modelUrl.urlType = 1;
                            modelUrl.mimeType=mimeType;
                            LogUtil.Log("CX", "js文件:" + data);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                //判断url为图片
                else if (host.contains("114best.com") && (url.contains(".png") || url.contains(".jpg") || url.contains(".gif"))) {
                    try {
                        URL uri = new URL(url);
                        URLConnection mConnection = uri.openConnection();
                        if (mConnection != null) {
                            InputStream input = mConnection.getInputStream();   //获取网络数据的输入流
                            ByteArrayOutputStream output = new ByteArrayOutputStream();
                            String mimeType=mConnection.getContentType();
                            byte[] buffer = new byte[4096];
                            int n = 0;
                            while (-1 != (n = input.read(buffer))) {
                                output.write(buffer, 0, n);
                            }
                            byte[] data = output.toByteArray();
                            input.close();
                            output.close();
                            //将css文件保存
                            modelUrl =new ModelUrl();
                            modelUrl.urlName = url == null ? "" : url;
                            modelUrl.urlBody = data;
                            modelUrl.urlType = 2;
                            modelUrl.mimeType=mimeType;
                            LogUtil.Log("CX", "图片文件:" + data);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //判断url为html
                else if (host.contains("114best.com") && (url.contains(".html") || url.contains(".htm"))) {
                    try {
                        URL uri = new URL(url);
                        URLConnection mConnection = uri.openConnection();
                        if (mConnection != null) {
                            InputStream input = mConnection.getInputStream();   //获取网络数据的输入流
                            ByteArrayOutputStream output = new ByteArrayOutputStream();
                            String mimeType=mConnection.getContentType();
                            byte[] buffer = new byte[4096];
                            int n = 0;
                            while (-1 != (n = input.read(buffer))) {
                                output.write(buffer, 0, n);
                            }
                            byte[] data = output.toByteArray();
                            input.close();
                            output.close();
                            //将html文件保存
                            modelUrl =new ModelUrl();
                            modelUrl.urlName = url;
                            modelUrl.urlBody = data;
                            modelUrl.urlType = 3;
                            modelUrl.mimeType=mimeType;
                            LogUtil.Log("CX", "html文件:" + data);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                dbManager.AddUrl(modelUrl);
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String title=view.getTitle(); //得到网页标题
                baiduTitleContent.setTitleText(title);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Toast.makeText(getApplicationContext(), "加载错误",
                        Toast.LENGTH_LONG).show();
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){

        });
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
/*        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("系统提示");
            // 设置对话框消息
            isExit.setMessage("确定要退出吗");
            // 添加选择按钮并注册监听
            isExit.setButton(AlertDialog.BUTTON_POSITIVE,"确定",listener);
            isExit.setButton(AlertDialog.BUTTON_NEGATIVE,"取消",listener);
            // 显示对话框
            isExit.show();

        }*/
        return false;
    }

    /**监听对话框里面的button点击事件*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(webView != null){
            webView.loadUrl("");
            llWebView.removeAllViews();
            webView.getSettings().setJavaScriptEnabled(false);
            webView.stopLoading();
            webView.destroy();
            webView = null;
        }
    }
}
