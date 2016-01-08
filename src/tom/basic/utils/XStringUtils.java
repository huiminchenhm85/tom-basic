package tom.basic.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

import com.google.common.base.Strings;

public class XStringUtils {
	
	public static String convetMap2Json(Map... maps) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int index = 0;
		for(Map eachMap : maps){
			for (Object eachKey : eachMap.keySet()) {
				if (index == 0) {
					if( eachKey instanceof Integer || eachKey instanceof Long ){
						sb.append("[" + eachKey + ",'" + eachMap.get(eachKey) + "']");
					}else{
						sb.append("['" + eachKey + "','" + eachMap.get(eachKey) + "']");
					}
				} else {
					if( eachKey instanceof Integer || eachKey instanceof Long ){
						sb.append(",[" + eachKey + ",'" + eachMap.get(eachKey) + "']");
					}else{
						sb.append(",['" + eachKey + "','" + eachMap.get(eachKey) + "']");
					}
				}
				index++;
			}
		}
		sb.append("]");
		String typeString = sb.toString();
		return typeString;
	}
	
	public static String fullLong2String(String prefix,Long key,int toSzie){
		int needAppendSize = toSzie - String.valueOf(key).length();
		StringBuilder result = new StringBuilder(prefix);
		for(int i=0;i<needAppendSize;i++){
			result.append("0");
		}
		result.append(key);
		return result.toString();
	}
	
	public static String fullInt2String(String prefix,Integer key,int toSzie){
		int needAppendSize = toSzie - String.valueOf(key).length();
		StringBuilder result = new StringBuilder(prefix);
		for(int i=0;i<needAppendSize;i++){
			result.append("0");
		}
		result.append(key);
		return result.toString();
	}
	
	public static boolean isStringSame(String s1,String s2){
		if(s1 == null && s2 == null){
			return true;
		}
		if(s1 == null && s2 != null){
			return false;
		}
		if(s2 == null && s1 != null){
			return false;
		}
		return s1.trim().equals(s2.trim());
	}
	
	public static String thousandth(BigDecimal bd){
		DecimalFormat df=new DecimalFormat("##,####,###");
	    return df.format(bd);
	}
	
	
	public static String charactersEscape(String str){
		if(org.apache.commons.lang.StringUtils.isBlank(str)){
			return "";
		}
		String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|","'","\n","\t","\n\r","\r"};  
        for (String key : fbsArr) {  
            if (str.contains(key)) {  
            	str = str.replace(key, "\\" + key);  
            }  
        }
		return str;
	}
	
	public static String inputStream2String(InputStream in) throws Exception{
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		String line = null;
		try{
			br = new BufferedReader(new InputStreamReader(in));
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			return sb.toString();
		} catch(Exception e){
			throw new Exception("将inputStream转换成字符串失败",e);
		} finally {
			if(br!=null){
				try{
					br.close();
				}catch(Exception e){
					
				}
			}
		}
	}
	
	public static String emojiFilter(String inputStr){
		if(Strings.isNullOrEmpty(inputStr)){
			return inputStr;
		}
		
		// 判断有没有emoji的字符，如果没有直接返回原字符串
		if(!containsEmoji(inputStr)){
			return inputStr;
		}
		// 过滤掉emoji字符
		int len = inputStr.length();
		
		StringBuilder resultSB = new StringBuilder();
		for(int i=0; i<len ; i++){
			char code = inputStr.charAt(i);
			if(isNotEmojiCharacter(code)){
				resultSB.append(code);
			}
		}
		
		if(resultSB.length() == 0){
			return "";
		}
		return resultSB.toString();
		
	}
	
	private static boolean containsEmoji(String source){
		if(Strings.isNullOrEmpty(source)){
			return false;
		}
		int len = source.length();
		for(int i=0; i < len; i++){
			char code = source.charAt(i);
			if(!isNotEmojiCharacter(code)){
				return true;
			}
		}
		return false;
	} 
	
	/**
	 * @param code
	 * @return
	 */
	private static boolean isNotEmojiCharacter(char code){
		return (code == 0x0) ||
			   (code == 0x9) ||
			   (code == 0xA) ||
			   (code == 0xD) ||
			   ((code >= 0x20) && (code <= 0xD7FF)) ||
			   ((code >= 0xE000) && (code <= 0xFFFD)) ||
			   ((code >= 0x10000) && (code <= 0x10FFFF));
	}
	
	

}
