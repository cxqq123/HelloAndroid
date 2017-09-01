package com.example.cx.helloandroid.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.cx.helloandroid.R;

public class SensorActivity extends AppCompatActivity implements SensorEventListener{

    private TextView tvSensors;
    private float[] gravity=new float[3];
    private SensorManager sensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        tvSensors = (TextView) findViewById(R.id.tvSensors);
        //获取传感器中的所有数据
        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
//        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
//        for(Sensor sensor:sensorList){
//            tvSensors.append(sensor.getName()+"\n");  //获取传感器中的所有数据
//            Log.v("CX_LOGCAT",sensor.getName());
//        }
    }

    /**
     * 传感器数据变化时回调
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        switch(event.sensor.getType()){
            //重力感应
            case Sensor.TYPE_GRAVITY:
                gravity[0]=event.values[0];
                gravity[1]=event.values[1];
                gravity[2]=event.values[2];
                String xValue=String.valueOf(gravity[0]);
                String yValue=String.valueOf(gravity[1]);
                String zValue=String.valueOf(gravity[2]);
                String endValue=caluXYZ(xValue,yValue,zValue);
                String value="x:"+xValue+"\n"+"y:"+yValue+"\n"+"z:"+zValue+"\n"+endValue+"\n"+"----------------------"+"\n";
                tvSensors.append(value);
                break;
        }
    }

    /**
     * 传感器精度变化时回调
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册重力传感器
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),SensorManager.SENSOR_DELAY_UI);//第三个参数为采集频率
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销该事件
        sensorManager.unregisterListener(this);
    }

    private String caluXYZ(String x,String y,String z){
        float x1=Float.parseFloat(x);
        float y1=Float.parseFloat(y);
        float z1=Float.parseFloat(z);
        float end= (float) Math.sqrt(x1*x1+y1*y1+z1*z1);
        String endValue=String.valueOf(end);
        return endValue;
    }
}
