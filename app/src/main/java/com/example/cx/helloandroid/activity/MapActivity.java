package com.example.cx.helloandroid.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cx.helloandroid.R;
import com.example.cx.helloandroid.utils.ToastUtils;
import com.example.cx.helloandroid.view.TitleView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 *  MapActivity 模块 -->用来实现地理位置，经纬度的展现
 */
public class MapActivity extends ActivtyBase {

    private TextView positionTextView;
    private LocationManager locationManager;
    private String provider;
    private Button btnGetJWD;

    private Location location;
    private Context mContext;
    private TitleView titleMap;

    @Override
    public int gerLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    public void initBaseView() {
        mContext = MapActivity.this;
        positionTextView = (TextView) findViewById(R.id.position_text_view);
        btnGetJWD = (Button) findViewById(R.id.btn_getJWD);
        titleMap = (TitleView) findViewById(R.id.title_map);

        titleMap.setTitleText("MapActivity");

        btnGetJWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                //获取所有可用的位置提供器
                List<String> providerList = locationManager.getProviders(true);
                if (providerList.contains(LocationManager.GPS_PROVIDER)) {
                    provider = LocationManager.GPS_PROVIDER;
                    ToastUtils.makeTextShort(mContext,"GPS Provider");
                } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
                    provider = LocationManager.NETWORK_PROVIDER;
                    ToastUtils.makeTextShort(mContext,"NETWORK Provider");
                }
                else {
                    //当没有可用的位置提供器时，弹出Toast提示用户
                    ToastUtils.makeTextShort(mContext, "No Location provider to Use");
                }


                if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //android 平台为7.0以上时，会使用权限检测
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                    location= locationManager.getLastKnownLocation(provider);
                    if(location==null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        provider=LocationManager.NETWORK_PROVIDER;
                    }
                    showLocation(location);
                    locationManager.requestLocationUpdates(provider, 5000, 1,locationListener);  //更新位置的时间，和距离
                }else{
                    //无法定位：1.提示用户打开定位服务;2、跳转到设置页面
                    ToastUtils.makeTextShort(mContext,"无法定位，请打开定位服务");
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }

            }
        });
    }

    LocationListener locationListener=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //更新当前设备的位置信息
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(locationManager!=null){
            locationManager.removeUpdates(locationListener);
        }
    }

    private void showLocation(Location location){
        Geocoder gc=new Geocoder(this, Locale.getDefault());
        List<Address> locationList=null;
        try {
            if(location!=null){
                locationList=gc.getFromLocation(location.getLatitude(),location.getLongitude(),1);  //根据经纬度获取 国家，城市，街道
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        String countryName="";
        String locality="";
        String addressLine="";
        Address address;
        if(locationList!=null){
           address=locationList.get(0);
            if(address!=null){
                countryName=address.getCountryName();
                locality=address.getLocality();  //获取城市
                for(int i=0;address.getAddressLine(i)!=null;i++){
                    addressLine=address.getAddressLine(i);//得到周边信息，包括街道等，i=0,得到街道名称
                }
            }
        }
        if(location!=null){
            String currentPosition="经度:"+location.getLatitude()+"\n"+"纬度:"+location.getLongitude()+"\n国家:"+countryName
                    + "\n城市:"+locality+"\n街道:"+addressLine;
            positionTextView.setText(currentPosition);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            finish();
            return false;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }
}
