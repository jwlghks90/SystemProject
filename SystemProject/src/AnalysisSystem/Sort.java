//package kr.ac.kit.AnalysisSystem;
package AnalysisSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Sort {
	public Sort(){}
	
	public static ArrayList sortByValue(final HashMap map)
	{
        ArrayList<String> list = new ArrayList();
        list.addAll(map.keySet());
        
        Collections.sort(list,new Comparator(){
			public int compare(Object o1,Object o2)
        	{
        		Object v1 = map.get(o1);
        		Object v2 = map.get(o2);
        		return ((Comparable) v1).compareTo(v2);
        	}
             
        });	
        
        Collections.reverse(list); // 주석시 오름차순
        return list;
    }
	
	public static ArrayList sortByValue2(HashMap<String, KeywordInfo> keywordList)
	{
        ArrayList<String> list = new ArrayList();
        list.addAll(keywordList.keySet());
        
        Collections.sort(list,new Comparator(){
			public int compare(Object o1,Object o2)
        	{
        		Object v1 = keywordList.get(o1).getWeight();
        		Object v2 = keywordList.get(o2).getWeight();
        		return ((Comparable) v1).compareTo(v2);
        	}
             
        });
        
        Collections.reverse(list); // 주석시 오름차순
        return list;
    }
}
