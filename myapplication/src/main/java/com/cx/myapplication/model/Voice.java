package com.cx.myapplication.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/22.
 */

public class Voice {

    public ArrayList<WSBean> ws;

    public class WSBean{
        public ArrayList<CWBean> cw;
    }

    public class CWBean{
        public String w;
    }
}
