package com.cx.retrofitdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.cx.retrofitdemo.model.ApiResult;
import com.cx.retrofitdemo.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private TextView tvTest;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContext=TestActivity.this;
        tvTest = (TextView) findViewById(R.id.tv_Test);
        String str=addData();
        tvTest.setText(str);
        parseData();
    }

    //-------------对于普通类型的Gson-----------------------
    //生成Json,通过字符串生成json
    public String addData(){
        Gson gson=new Gson();
        User user=new User("cx羽",20,"744185734@qq.com");
        String jsonObject =gson.toJson(user);
        return jsonObject;
    }

    //解析Json
    public void parseData(){
        Gson gson=new Gson();
        String jsonString="{\"name\":\"cx羽\",\"age\":\"24\"}";
        User user=gson.fromJson(jsonString,User.class);
        Toast.makeText(mContext,user.toString(),Toast.LENGTH_LONG).show();
    }

    //------------------------------------------------------

    //-------------对于普通类型的Gson----------------------

    public void parseDataForNumbers(){
        //给字符串数组
        Gson gson=new Gson();
        String jsonArray="[\"Android\",\"Java\",\"PHP\"]";
        String[] strings=gson.fromJson(jsonArray,String[].class);
    }

    //通过解析Json生成返回类型为List<String>的list
    public void parseDataForList(){
        //给List
        Gson gson=new Gson();
        String jsonArray="[\"Android\",\"Java\",\"PHP\"]";
        List<String> stringList=gson.fromJson(jsonArray,new TypeToken<List<String>>(){}.getType());
    }
    //------------------------------------------------------

    //---------------Gson中使用泛型-------------------------
     public void gsonGeneric(){
         Gson gson=new Gson();
         String json="{...............}";
         //单个User对象元素
         Type userType =new TypeToken<ApiResult<User>>(){}.getType();
         ApiResult<User> userApiResult=gson.fromJson(json,userType);
         User user=userApiResult.data;

         //多个User对象元素(List)
         Type userListType =new TypeToken<ApiResult<List<User>>>(){}.getType();
         ApiResult<List<User>> listApiResult=gson.fromJson(json,userListType);
         List<User> list=listApiResult.data;
     }
    //------------------------------------------------------

    //-------使用GsonBuilder导出null值、格式化输出、日期时间----
    public Gson generteGson(){
        Gson gson= new GsonBuilder()
                //序列化null
                .serializeNulls()
                //设置日期时间格式
                .setDateFormat("yyyy-mm--dd")
                .disableInnerClassSerialization()
                .generateNonExecutableJson()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
        return gson;
    }

    //----------------------------------------------------------
}
