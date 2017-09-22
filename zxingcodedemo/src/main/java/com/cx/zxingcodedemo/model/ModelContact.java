package com.cx.zxingcodedemo.model;

import java.io.Serializable;

public class ModelContact implements Serializable
{
	public ModelContact()
	{
	}
	public ModelContact(String ctTitle, String ctValue, String ctTexExt, int ctType)
	{
		this.ctTitle = ctTitle;
		this.ctValue = ctValue;
		this.ctType = ctType;
		this.ctTelExt = ctTexExt;
	}

	public long ctID =0L;
	public long ctCompanyID=0L;
	public String ctTitle="";
	private String ctCountry="";
//	private String ctCode;
//	private String ctPhone;
	public String ctValue=""; //0区号-电话
	public int ctCallTimes;
	
	public String ctTelExt="";
	public String ctNote="";
	/**-1:分隔线-2:邮箱-3:网站-4:联系人-5:QQ-6:微信-7:skype-8:生日 0.电话 1.手机、2.固话、3.800、4.400、5.特号 */
	public int ctType ;
	public int ctOrder;
	public int ctGroup;
	public boolean ctIsRight = true;

	
//	public String ctTelExt_()
//	{
//		return ctTelExt;
//	}
//	public void ctTelExt__(String ctTexExt)
//	{
//		this.ctTelExt = ctTexExt;
//	}
//	public String ctNote__()
//	{
//		return ctNote;
//	}
//	public void ctNote__(String ctNote)
//	{
//		this.ctNote = ctNote;
//	}
//	public int ctType_()
//	{
//		return ctType;
//	}
//	public void ctType__(int ctType)
//	{
//		this.ctType = ctType;
//	}
//	public int ctOrder_()
//	{
//		return ctOrder;
//	}
//	public void ctOrder__(int ctOrder)
//	{
//		this.ctOrder = ctOrder;
//	}
//	public int ctGroup_()
//	{
//		return ctGroup;
//	}
//	public void ctGroup__(int ctGroup)
//	{
//		this.ctGroup = ctGroup;
//	}
	

}
