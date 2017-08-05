package com.example.cx.webviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button androidCallJs;
    private WebView webView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidCallJs = (Button) findViewById(R.id.androidCallJs);
        androidCallJs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:androidCallJs()");
                    }
                });
            }
        });
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this,"wv");
        webView.loadUrl("file:///android_asset/js_webView.html");
    }

    @JavascriptInterface
    public void sayHello(String msg){
        Toast.makeText(MainActivity.this,"JsInterface--"+msg,Toast.LENGTH_SHORT).show();
    }
}
