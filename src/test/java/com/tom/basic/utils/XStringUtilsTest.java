package com.tom.basic.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

import com.google.common.collect.Maps;

import junit.framework.TestCase;

public class XStringUtilsTest  extends TestCase{
	
	public void testConvetMap2Json() {
		Map<String,Integer> sources1 = Maps.newConcurrentMap();
		sources1.put("01", 1);
		sources1.put("02", 2);
		assertEquals("[['02','2'],['01','1']]", XStringUtils.convetMap2Json(sources1));
		
		Map<String,String> sources2 = Maps.newConcurrentMap();
		sources2.put("04", "04");
		
		assertEquals("[['02','2'],['01','1'],['04','04']]", XStringUtils.convetMap2Json(sources1,sources2));
	}
	
	public void testFullLong2String(){
		assertEquals("000000123", XStringUtils.fullLong2String("0", 123L, 9));
		assertEquals("222222123", XStringUtils.fullLong2String("2", 123L, 9));
	}
	
	public void testFullInt2String(String prefix,Integer key,int toSzie){
		assertEquals("000000123", XStringUtils.fullInt2String("0", 123, 9));
		assertEquals("222222123", XStringUtils.fullInt2String("2", 123, 9));
	}
	
	public void testIsStringSame(){
		assertTrue(XStringUtils.isStringSame(null, null));
		assertTrue(XStringUtils.isStringSame(" ab1 ", "ab1"));
		assertTrue(XStringUtils.isStringSame(" ", ""));
		
		assertFalse(XStringUtils.isStringSame(null, ""));
		assertFalse(XStringUtils.isStringSame("ad", "ac"));
	}
	
	public void testThousandth(){
		assertEquals("10",XStringUtils.thousandth(new BigDecimal(10)));
		assertEquals("10,000",XStringUtils.thousandth(new BigDecimal(10000)));
		assertEquals("10,000,000",XStringUtils.thousandth(new BigDecimal(10000000)));
		assertEquals("10,000,000,000",XStringUtils.thousandth(new BigDecimal(10000000000L)));
	}
	
	
	public void testCharactersEscape(String str){
	}
	
	public void testInputStream2String() throws Exception{
		String source = "this is test resouce";
		
		InputStream is = new ByteArrayInputStream(source.getBytes());
		
		assertEquals("this is test resouce",XStringUtils.inputStream2String(is));
		
	}
	
	public void testEmojiFilter(String inputStr){
	}
	

}
