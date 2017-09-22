package com.cx.zxingcodedemo.model;

/**
 * Created by Administrator on 2017/9/16.
 */

public class ModelContent {

    public String begin;
    public String version;
    public String fn;
    public String tel_cell;
    public String tel_work;
    public String tel_fax;
    public String email;
    public String url;
    public String org;
    public String role;
    public String title;
    public String postal;
    public String rev;
    public String end;

    public ModelContent() {

    }

    public ModelContent(String begin, String version, String fn, String tel_cell, String tel_work, String tel_fax, String email, String url, String org, String role, String title, String postal, String rev, String end) {
        this.begin = begin;
        this.version = version;
        this.fn = fn;
        this.tel_cell = tel_cell;
        this.tel_work = tel_work;
        this.tel_fax = tel_fax;
        this.email = email;
        this.url = url;
        this.org = org;
        this.role = role;
        this.title = title;
        this.postal = postal;
        this.rev = rev;
        this.end = end;
    }


}
