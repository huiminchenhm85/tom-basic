package com.tom.basic.log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.net.SMTPAppender;

/**
 * 
 * @author TOM
 *
 */
public class LocateableSMTPAppender extends SMTPAppender{
	
	private static String strIp;
	
	public static String getCurrentIp() {
		if(strIp == null){
			try {
				strIp = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
			}
		}
		return strIp;
	}
	
	
	@Override
	public void setSubject(String subject) {
		String timestamp = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		super.setSubject(subject + "(Machine IP:"+getCurrentIp()+ " AT:"+timestamp+")");
	}
	
	

}
