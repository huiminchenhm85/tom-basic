package com.tom.basic.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

public class XTimeUtils {
	
	public static Long getDayDifference(Date smallDate, Date maxDate) throws Exception{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date day1 = df.parse(DateFormatUtils.format(maxDate, "yyyy-MM-dd"));
	    Date day2 = df.parse(DateFormatUtils.format(smallDate, "yyyy-MM-dd"));
	    Long diff = day1.getTime() - day2.getTime();
	    Long days = diff / (1000 * 60 * 60 * 24);
	    return days;
	}

}
