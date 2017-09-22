package com.cx.web.util;

import android.content.Context;

import com.cx.web.db.DBManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RexUtil {

	/***
	 * 获取网页的域名
	 * @param url   网页地址
	 * @return		网页的域名
	 */
	public static String getWebHost(String url){
		Pattern p = Pattern.compile("(?<=//|)(([\\w-])+\\.)+\\w+");
		Matcher m = p.matcher(url);
		if(m.find()){
			return m.group();
		}else{
			return "";
		}
	}
	
	
	/**
	 * 验证邮箱
	 * 
	 * @param str 待验证的字符串
	 * @return 如果是符合的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isEmail(String str) {
		String regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return match(regex, str);
	}

	/**
	 * 验证IP地址
	 * 
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isIP(String str) {
		String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
		String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";
		return match(regex, str);
	}

	/**
	 * 验证网址Url
	 * 
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean IsUrl(String str) {
		String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
		return match(regex, str);
	}

	/**
	 * 验证电话号码
	 * 
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean IsTelephone(String str) {
		String regex = "^(\\d{3,4}-)?\\d{6,8}$";

		return match(regex, str);
	}

	public static boolean isPhone(String str){
		if(str.startsWith("0") || str.startsWith("1") || str.startsWith("40") || str.startsWith("8")
				|| str.startsWith("9")){
			return true;
		}
		return false;
	}

	/**
	 * 验证输入密码条件(字符与数据同时出现)
	 * 
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean IsPassword(String str) {
		String regex = "[A-Za-z]+[0-9]";
		return match(regex, str);
	}

	/**
	 * 验证输入密码长度 (6-18位)
	 * 
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean IsPasswLength(String str) {
		String regex = "^\\d{6,18}$";
		return match(regex, str);
	}

	/**
	 * 验证输入邮政编号
	 * 
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean IsPostalcode(String str) {
		String regex = "^\\d{6}$";
		return match(regex, str);
	}

	/**
	 * 验证输入手机号码
	 * 
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean IsHandset(String str) {
		String regex = "^[1]+[3,5]+\\d{9}$";
		return match(regex, str);
	}

	/**
	* @param regex
	* 正则表达式字符串
	* @param str
	* 要匹配的字符串
	* @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	*/
	private static boolean match(String regex, String str) {
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(str);
	return matcher.matches();
	}

	/**
	 * 是否匹配全局域名
	 * @param mContext
	 * @param url
	 * @return
	 */
//	public static boolean matchSafeDomain(Context mContext, String url){
//		DBManager db = new DBManager(mContext);
//		String doMain = db.getConfig(IzdAppConstants.DO_MAIN);
//		if(!me.isNullOrEmpty(doMain)){
//			doMain = doMain.replaceAll(";", "|");
//			doMain = doMain.replaceAll("\\.", "\\\\.");
//			String rex = "(([\\w-])+(\\.))?("+ doMain+")$";
//			Pattern pattern = Pattern.compile(rex);
//			Matcher matcher = pattern.matcher(url);
//			if(matcher.matches()){
//				return true;
//			}else {
//				return false;
//			}
//		}
//		return false;
//	}

	/***
	 * 判断扫码二维码的地址
	 * @param url
	 * @return  返回true就跳转页面
	 */
	public static boolean isScanCode(String url){
		String rex = "^(https?://)?(go.izd.cn)/(\\w+)_(\\w*)";
		if(url.matches(rex)){
			return true;
		}
		return false;
	}
}
