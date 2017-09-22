package com.example.cx.helloandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cx.helloandroid.R;
import com.example.cx.helloandroid.utils.ToastUtils;
import com.example.cx.helloandroid.view.TitleView;
import com.google.zxing.WriterException;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.encoding.EncodingHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.R.attr.path;

/**
 * ScanCodeActivity 模块 -->用来展示二维码扫描以及生成二维码
 */
public class ScanCodeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE=100;

    private Button btnScan;
    private TextView tvShowResult;
    private EditText etUrl;
    private Button btnBuildCode;
    private ImageView ivShowCode;
    private Bitmap bitmap; //生成的二维码图片
    private TitleView title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        btnScan = (Button) findViewById(R.id.btnScan);
        tvShowResult = (TextView) findViewById(R.id.tvShowResult);
        etUrl = (EditText) findViewById(R.id.etUrl);
        btnBuildCode = (Button) findViewById(R.id.btnBuildCode);
        ivShowCode = (ImageView) findViewById(R.id.ivShowCode);
        title = (TitleView) findViewById(R.id.title);

        title.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title.setTitleText("扫描二维码功能");
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ScanCodeActivity.this, CaptureActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        btnBuildCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=etUrl.getText().toString();
                if(!url.equals("")){
                    try {
                        bitmap= EncodingHandler.createQRCode(url,300);
                        ivShowCode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ivShowCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
               // saveImageToGallery(ScanCodeActivity.this,bitmap);
                ToastUtils.makeTextShort(ScanCodeActivity.this,"二维码的长按事件");
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case REQUEST_CODE:
                if(resultCode ==RESULT_OK){
                    String scanResult=data.getStringExtra("qr_scan_result");
                    tvShowResult.setText(scanResult);
                    ToastUtils.makeTextShort(ScanCodeActivity.this,scanResult);
                }else if(resultCode ==RESULT_CANCELED){
                    tvShowResult.setText("扫描出错");
                    ToastUtils.makeTextShort(ScanCodeActivity.this,"扫描出错");

                }
                break;
        }
    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "ScanCode");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }
}
