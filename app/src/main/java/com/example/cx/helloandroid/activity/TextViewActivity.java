package com.example.cx.helloandroid.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.cx.helloandroid.R;
import com.example.cx.helloandroid.utils.LinkifySpannableUtils;
import com.example.cx.helloandroid.utils.ToastUtils;

public class TextViewActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textView2;

    private String content="欢迎访问深圳公安官方直达站！<br>\\n\" +\n" +
            "                \"报警电话：<a href=\\\\\\\"tel:110\\\\\\\">110</a><br>\\\\n\" +\n" +
            "                \"短信举报：<a href=\\\\\\\"tel:12110\\\\\\\">12110</a><br>\\\\n\" +\n" +
            "                \"举报热线：<a href=\\\\\\\"www.baidu.com\\\\\\\">0755-22222110</a><br>\\\\n\" +\n" +
            "                \"传真举报：<a href=\\\\\\\"tel:0755-84465145\\\\\\\">592445110@qq.com</a><br>\\\\n\" +\n" +
            "                \"反信息诈骗：<a href=\\\"tel:0755-81234567\\\">0755-81234567</a>\"";

    private String content2= "<a href=\\\\\\\"tel:111\\\\\\\">111</a><br>";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);

        textView.setText(content);
        LinkifySpannableUtils.getInstance().setSpan(TextViewActivity.this,textView);

        textView2.setText(content2);
        ToastUtils.makeTextShort(TextViewActivity.this,content2.length()+"");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=textView.getText().toString();
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });
    }

}
