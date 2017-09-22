package com.cx.crashhandlerdemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.Y;

/**
 * Created by Administrator on 2017/9/16.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler{

    public static final String TAG="CrashHandler";

    //系统默认的UncaughtExceptionHandler 处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    //CrashHandler 实例
    private static CrashHandler INSTANCE;
    private Context mContext;

    //用来存储设备信息和异常信息
    private Map<String,String> infos=new HashMap<>();
    private DateFormat format =new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    //私有构造，单例模式
    private CrashHandler(){

    }

    public static CrashHandler getInstance(){
        if(INSTANCE ==null){
            INSTANCE =new CrashHandler();
            return INSTANCE;
        }else{
            return INSTANCE;
        }
    }

    public void init(Context context){
        mContext =context;
        // 获取系统默认的UncaughtException
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    //当UncaughtException发生时会转入该函数来处理
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        if(!handleException(throwable) && mDefaultHandler !=null ){
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread,throwable);
        }else{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //退出程序
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
     */
    private boolean handleException(Throwable ex){
        if(ex ==null){
            return false;
        }

        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext,"很抱歉，程序出现异常，正在收集日志，即将退出",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        // 收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        String fileName=saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx){
        PackageManager pm =ctx.getPackageManager();
        try {
            PackageInfo pi=pm.getPackageInfo(ctx.getPackageName(),PackageManager.GET_ACTIVITIES);
            if(pi!=null){
                //版本名称
                String versionName =pi.versionName ==null ? "null":pi.versionName;
                //版本号
                String versionCode=pi.versionCode +"";
                infos.put("versionName",versionName);
                infos.put("versionCode",versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //利用反射获取字段信息
        Field[] fields= Build.class.getDeclaredFields(); //获取该类的全部字段
        for(Field field: fields){
            try {
                field.setAccessible(true);
                infos.put(field.getName(),field.get(null).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 保存错误信息到文件中
     * @param ex
     * @return 返回文件名称，便于将文件传送到服务器中
     */
    private String saveCrashInfo2File(Throwable ex){
        StringBuffer sb =new StringBuffer();
        for(Map.Entry<String,String> entry :infos.entrySet()){
            String key =entry.getKey();
            String value=entry.getValue();
            sb.append(key +"="+value +"\n");
        }

        Writer writer=new StringWriter();
        PrintWriter printWriter=new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause =ex.getCause();
        while(cause !=null){
            cause.printStackTrace(printWriter);
            cause=cause.getCause();
        }
        printWriter.close();
        String result=writer.toString();
        sb.append(result);

        long timeStamp=System.currentTimeMillis();
        String time =format.format(new Date());
        String fileName =time +"-"+timeStamp+".txt";
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String path =Environment.getExternalStorageDirectory().toString()+"cx";
            File dir =new File(path);
            if(!dir.exists()){
                dir.mkdirs();  // 创建新文件夹
            }
            try {
                FileOutputStream fos =new FileOutputStream(path +fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
                return fileName;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
