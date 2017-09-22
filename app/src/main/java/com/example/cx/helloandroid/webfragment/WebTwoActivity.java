package com.example.cx.helloandroid.webfragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cx.helloandroid.R;
import com.example.cx.helloandroid.utils.NetUtil;
import com.example.cx.helloandroid.view.TitleView;

public class WebTwoActivity extends AppCompatActivity {

    private LinearLayout llWebView;
    private Button btnOne;
    private Button btnTwo;
    private Button btnThree;

    private WebView webView;
    private Context context;

    private FragmentTransaction transaction;

    private String url="https://www.baidu.com";
    private TitleView baiduTitleContent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_two);

        context=WebTwoActivity.this;
        initView();
        initWebView();
    }

    public void initWebView(){
        //动态加载WebView
        webView =new WebView(context);
        ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(lp);
        llWebView.addView(webView);

        webView.requestFocus();
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);

        webView.getSettings().setJavaScriptEnabled(true);
        //设置缓存模式
        if(NetUtil.getNetworkState(context) ==-1){
            //无网
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        }else{
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

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

    public void onLoad(){
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
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
        if (keyCode == KeyEvent.KEYCODE_BACK )
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

        }

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
