package com.cx.zxingcodedemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cx.zxingcodedemo.model.ModelContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private ArrayList<ModelContent> newContact;
    private ModelContent modelContent ;
    private Button btnScan;
    private List<String> list=new ArrayList<>();
    private List<String> key=new ArrayList<>();

    private Map<String,String> map=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext =MainActivity.this;


        btnScan = (Button) findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();  //初始化数据

//                addCompanyTel();//添加到系统联系人中
            }
        });

    }

    public void initData(){
//        modelContent=new ModelContent("VCARD","3.0","任侠","15201280000","010-62100000","010-62100001"
//                                            ,"lzw#lzw.me","http://lzw.me","志文工作室","产品部","CTO"
//                                            ,"北京市朝阳区北四环中路35号;100101","2012-12-27T08:30:02Z","VCARD");
//
//        newContact=new ArrayList<>();
//        newContact.add(modelContent);

        String[] strings=splitString(createContent());
        for(String str:strings){
            Log.v("CX------------",str);

            if(str.contains(":")){
                int index=str.indexOf(":");
                String newStr=str.substring(index+1,str.length());
                String keyStr=str.substring(0,index);
                map.put(keyStr,newStr);
            }
        }
    }

    private String createContent(){
        return "BEGIN:VCARD\n" +
                "VERSION:3.0\n" +
                "FN:任侠\n" +
                "TEL;CELL;VOICE:15201280000\n" +
                "TEL;WORK;VOICE:010-62100000\n" +
                "TEL;WORK;FAX:010-62100001\n" +
                "EMAIL;PREF;INTERNET:lzw#lzw.me\n" +
                "URL:http://lzw.me\n" +
                "orG:志文工作室\n" +
                "ROLE:产品部\n" +
                "TITLE:CTO\n" +
                "ADR;WORK;POSTAL:北京市朝阳区北四环中路35号;100101\n" +
                "REV:2012-12-27T08:30:02Z\n" +
                "END:VCARD";
    }


    public String[]  splitString(String str){
        String strArray[]=str.split("\n");
        return strArray;
    }

    public void addCompanyTel(){
        Uri mUri = ContactsContract.Contacts.CONTENT_URI;
        Intent mIntent = new Intent(Intent.ACTION_INSERT, mUri);

        mIntent.putExtra(ContactsContract.Intents.Insert.NAME, modelContent.fn);
//        if(newContact.size()==0){
//            Toast.makeText(mContext, "该公司没有联系人信息", Toast.LENGTH_SHORT).show();
//            return;
//        }else if(newContact.size()==1){
//            mIntent.putExtra(ContactsContract.Intents.Insert.PHONE, newContact.get(0).tel_cell);
//        }else if(newContact.size()==2){
//            mIntent.putExtra(ContactsContract.Intents.Insert.PHONE, newContact.get(0).tel_cell);
//            mIntent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, newContact.get(0).tel_work);
//        }else if(newContact.size()>=3){
//            mIntent.putExtra(ContactsContract.Intents.Insert.PHONE, newContact.get(0).tel_cell);
//            mIntent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, newContact.get(0).tel_work);
//            mIntent.putExtra(ContactsContract.Intents.Insert.TERTIARY_PHONE, newContact.get(0).tel_fax);
//        }
        mIntent.putExtra(ContactsContract.Intents.Insert.PHONE, newContact.get(0).tel_cell);
        mIntent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, newContact.get(0).tel_work);
        mIntent.putExtra(ContactsContract.Intents.Insert.TERTIARY_PHONE, newContact.get(0).tel_fax);

        mContext.startActivity(mIntent);
    }

}
