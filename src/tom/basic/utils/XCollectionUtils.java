package tom.basic.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class XCollectionUtils {

	/**
	 * ´«ÈëmapÎªMap<Integer, String>
	 * @param oldMap
	 * @return
	 */
	public static Map sortMap(Map oldMap) {  
        ArrayList<Map.Entry<Integer, String>> list = new ArrayList<Map.Entry<Integer, String>>(oldMap.entrySet());  
        Collections.sort(list, new Comparator<Map.Entry<Integer, String>>() {  
            @Override  
            public int compare(Entry<Integer, String> arg0,  
                    Entry<Integer, String> arg1) {  
                return arg0.getValue().compareTo(arg1.getValue());  
            }  
        });  
        Map newMap = new LinkedHashMap();  
        for (int i = 0; i < list.size(); i++) {  
            newMap.put(list.get(i).getKey(), list.get(i).getValue());  
        }  
        return newMap;  
    }  
}
