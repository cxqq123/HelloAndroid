package com.example.cx.helloandroid.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cx.helloandroid.R;
import com.example.cx.helloandroid.utils.ImageCacheUtils;

public class BitmapCacheActivity extends AppCompatActivity {

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ImageCacheUtils.SUCCESS:
                    //给控件设置图片
                    Bitmap bitmap= (Bitmap) msg.obj;
                    int pos=msg.arg1;
                    ImageView imageView= (ImageView) findViewById(R.id.image);
                    if(imageView!=null && bitmap!=null){
                        imageView.setImageBitmap(bitmap);
                    }
                    break;
                case ImageCacheUtils.FAIL:
                    Toast.makeText(getApplicationContext(), "图片下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_cache);
        ImageCacheUtils imageCacheUtils=new ImageCacheUtils(getApplicationContext(),handler);
    }
}