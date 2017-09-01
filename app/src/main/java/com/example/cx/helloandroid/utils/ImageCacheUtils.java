package com.example.cx.helloandroid.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cx on 2017/8/24.
 */

/**
 *  当我们获取图片的时候，分三步
 *  1.从缓存中获取图片
 *  2.从本地的缓存目录中获取图片，并且获取到后，放到缓存中
 *  3.从网络去下载图片，下载完成之后，保存到本地缓存目录和放到缓存中
 */
public class ImageCacheUtils {
    private LruCache<String,Bitmap> lruCache;
    private File cacheDir;
    private ExecutorService newFixedThreadPool; //线程池
    private Handler handler;
    public static final int SUCCESS=100;
    public static final int FAIL=101;

    public ImageCacheUtils(Context context,Handler handler){
        //获取缓存的大小
        int i = 8;
        int maxsize = (int)Runtime.getRuntime().maxMemory() / i;
        //设置缓存的最大空间
        lruCache =new LruCache<String, Bitmap>(maxsize){
            //获取移出的图片所占用的空间，当图片移出的时候，需要将图片占用的缓存空间从缓存中移出

            @Override
            protected int sizeOf(String key, Bitmap value) {
                //计算图片所占用的缓存大小  (行字节数*行数)
                //getRowBytes:获取图片一行所占用的大小
                //getHeight:获取图片所占用行数
                return value.getRowBytes()*value.getHeight();
            }
        };

        //获取缓存目录
        cacheDir=context.getCacheDir();
        //获取线程池
        newFixedThreadPool= Executors.newFixedThreadPool(5);
        this.handler=handler;
    }

    /**
     * 获取图片的方法
     * @param url
     * @param position
     * @return
     */
    public Bitmap getBitmap(String url,int position){
        Bitmap bitmap=null;
        //1.从缓存中获取图片（LruCache<k,v>） key保存图片的标示，一般都是图片的url地址，value表示图片
        bitmap=lruCache.get(url);
        if(bitmap!=null){
            return bitmap;
        }
        //2.从本地的缓存目录中获取图片，并且获取到之后，放到缓存中
        bitmap=getFromLocal(url);
        if(bitmap!=null){
            return bitmap;
        }
        //3.从网络去下载图片，下载完成之后，保存到本地缓存目录和放到缓存中
        getFromNet(url,position);
        return null;
    }

    /**
     * 从本地缓存目录中获取图片
     * @param url
     * @return
     */
    private Bitmap getFromLocal(String url){
        //根据图片的名称获取图片
        try {
            //截取前10位作为图片的名称
            String fileName=MD5Encoder.encode(url).substring(10);
            File file = new File(cacheDir,fileName);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            //放到缓存中
            lruCache.put(url,bitmap);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从网络下载图片，异步方式，线程池
     * @param url
     * @param position
     */
    private void getFromNet(String url,int position){
        newFixedThreadPool.execute(new RunnbleTask(url,position));
    }

    public class RunnbleTask implements Runnable{
        private String imageUrl;
        private int position;;

        public RunnbleTask(String url,int position){
            this.imageUrl=url;
            this.position=position;
        }
        @Override
        public void run() {
            Message message=Message.obtain(); //初始化消息实例，不使用new Message()是因为它并不是每次都要new 对象，
                                              //而是会从它的对象池中拿取该对象
            //下载图片
            try {
                URL url=new URL(imageUrl);
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(3000);
                conn.setRequestMethod("GET");
                InputStream inputStream = conn.getInputStream();
                //解析从网上获取的字节流，将其转换成-->Bitmap 数据
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                //保存到本地缓存中
                writeToLocal(imageUrl,bitmap);
                //保存到系统缓冲中
                lruCache.put(imageUrl,bitmap);
                //显示图片，给handle发送消息
                message.what=SUCCESS;
                message.obj=bitmap;
                message.arg1=position;
                //发送成功的消息
                handler.sendMessage(message);
                return ;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //否则发送失败的消息
            message.what=FAIL;
            handler.sendMessage(message);
        }
    }

    /**
     * 将图片保存到本地缓存目录中，用文件保存到本地，若是一些文本数据，也可以存入到SQLite数据库中
     * @param url
     * @param bitmap
     */
    public void writeToLocal(String url,Bitmap bitmap){
        //url名称，通过MD5加密，并且截取前10位作为名称
        try {
            String fileName=MD5Encoder.encode(url).substring(10);
            File file=new File(cacheDir,fileName);
            FileOutputStream out=new FileOutputStream(file);
            //format:图片的格式
            //quality:压缩比例
            //stream:流信息
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out); //将图片保存到那个位置
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
