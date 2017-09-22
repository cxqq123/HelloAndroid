package com.cx.zxingcodedemo.model;

import java.util.Date;

public class ModelCompany implements Cloneable
{
	//private int CpID; // 公司ID（本地）
	public long cpForUserID=0l;
	//private String cpKey="";
	public long cpID=0l;

	public String cpName="";
	public String cpShortName="";
	public String cpIcon="";
	public String cpArea="";
	public String cpAddress="";
	public String cpPost="";
	public String cpBusiness="";
	public String cpAbout="";
	public long cpVisitDate;
	public long cpAddDate;
	public long cpEditDate;
	public int cpCategory=0; //分类在哪个组里 -1表示未加（可能只看过，缓存了）
	public int cpState=0;//0 表示未被认领   1  表示已被认领
	public String sn = "";
	public String cpNote = "";
	public int cpCertified = 0; //认证状态（0未认证 1绿信 2橙信 3金信）

	public String cpTelNumber = "";
	/**该公司的直达站ID*/
	public long cpZDZID = 0l;
	
	public boolean isChecked=false;//长按加商友
	public ModelCompany()
	{
	}

	public ModelCompany(Long cpForUserID, Long cpID, String cpName,
			String cpShortName, String cpIcon, String cpArea, String cpAddress,
			String cpPost, String cpBusiness, String cpAbout, Date cpVisitDate,
			int cpState)
	{
		//this.CpID = cpID;
		this.cpForUserID = cpForUserID;
		this.cpID = cpID;
		this.cpName = cpName;
		this.cpShortName = cpShortName;
		this.cpIcon = cpIcon;
		this.cpArea = cpArea;
		this.cpAddress = cpAddress;
		this.cpPost = cpPost;
		this.cpBusiness = cpBusiness;
		this.cpAbout = cpAbout;
		//this.cpVisitDate = cpVisitDate;
		this.cpState = cpState;
	}

	@Override
	public String toString() {
		return "cpID=" + cpID + "<br> cpName=" + cpName
				+ "<br> cpShortName=" + cpShortName + "<br> cpIcon=" + cpIcon
				+ "<br> cpArea=" + cpArea + "<br> cpAddress=" + cpAddress
				+ "<br> cpBusiness=" + cpBusiness+ "<br> cpAbout=" + cpAbout;
	}

	@Override
	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
