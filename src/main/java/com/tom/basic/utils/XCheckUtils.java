package com.tom.basic.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XCheckUtils {
	
	public static boolean isMobileNO(String mobiles){
		Pattern p = Pattern.compile("^((17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,3-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	public static boolean checkEmail(String email){
        boolean flag = false;
        try{
                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(email);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }

}
