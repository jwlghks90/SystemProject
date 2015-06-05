package AnalysisSystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class TFIDF {
	private Sort sort;
	private HashMap<String, Double> idfMap; 				// 誘몃━ �깮�꽦�븳 idf留�
	private int docSetSize; 

	public TFIDF(){
		sort = new Sort();
		readIdfFile("result.txt"); // IDF �뙆�씪
	}
	
	/**
	 * 誘몃━遺꾩꽍�빐 �넃�� Term, IDF媛믪씠 ���옣�맂 txt�뙆�씪�쓣
	 * �씫�뼱 硫ㅻ쾭蹂��닔 idfMap�뿉 put�븯�뒗 硫붿냼�뱶
	 * �뙆�씪�쓽 �삎�깭�뒗 term idf �삎�떇 怨듬갚臾몄옄濡� 援щ텇
	 * @param fileName
	 */
	public void readIdfFile(String fileName)
	{
		BufferedReader br;
		try{
			br = new BufferedReader(new FileReader(fileName));
		}
		catch (FileNotFoundException e){
			System.err.println("idf�뙆�씪�씠 �뾾�뒿�땲�떎.");
			return;
		}
		
		Scanner sc = new Scanner(br);
		HashMap<String, Double> readIdfMap = new HashMap<String, Double>();
		
		//System.out.println(sc.next());
		sc.next();
		//int docSize = 2585;
		int docSize = 83;
		
        while(sc.hasNext()){
        	String key = sc.next();
        	Double value = sc.nextDouble();     	
			//System.out.println(key + " " + value);		
        	readIdfMap.put(key, value);
        }
        
        idfMap = readIdfMap;
        setDocSetSize(docSize);
        
		try{br.close();}
		catch (IOException e){e.printStackTrace();}
	}
	
	/**
	 * 臾몄꽌瑜� �엯�젰�븯硫� �빐�떦 臾몄꽌 term�쓽
	 * TF*IDF媛믪쓣 異쒕젰�빐以��떎.
	 * �씤�뭼 : String�삎�깭�쓽 臾몄꽌 �븳媛�
	 * �븘�썐 : �뾾�쓬. 
	 */
	public HashMap<String, KeywordInfo> calTFIDF(HashMap<String, KeywordInfo> keywordList)
	{
		HashMap<String, Double> tfidfMap = new HashMap<String, Double>();
		
		for(Map.Entry<String, KeywordInfo> element : keywordList.entrySet()){
			String thisTerm = element.getKey();
			double thisValue = getTFIDF(thisTerm , keywordList);
			tfidfMap.put(thisTerm, thisValue);
		}
		
		//Iterator<String> it = KeywordAnalysis.sortByValue(tfidfMap).iterator();
		Iterator<String> it = sort.sortByValue(tfidfMap).iterator();
		
		while(it.hasNext()){
            String temp = it.next();
    		if(keywordList.containsKey(temp)){
    			Integer count = (Integer)keywordList.get(temp).getFrequency();
    			Double weight = Math.round(tfidfMap.get(temp)*100)/100.0;
    			String tag = (String)keywordList.get(temp).getTag();
    			keywordList.put(temp, new KeywordInfo((weight.doubleValue()),(count.intValue()), tag));
    		}
        }
		return keywordList;
	}
	
	//TF 怨꾩궛
	public HashMap<String, KeywordInfo> calTFMap(String noun, String tag, HashMap<String, KeywordInfo> keywordList)
	{	
		if(keywordList.containsKey(noun)){
			Integer count = (Integer)keywordList.get(noun).getFrequency();
			Double weight = (Double)keywordList.get(noun).getWeight();
			keywordList.put(noun, new KeywordInfo(weight.doubleValue(),(count.intValue()+1), tag));
		}
		else{
			keywordList.put(noun, new KeywordInfo(new Integer(1), tag));
		}	
		return keywordList;
	}
	
	public double getTFIDF(String term, HashMap<String, KeywordInfo> keywordList)
	{
		return getTF(term, keywordList) * getIDF(term);
	}
	
	public double getTF(String term, HashMap<String, KeywordInfo> keywordList)
	{
		return computeTF(keywordList.get(term).getFrequency());
	}
	
	public double computeTF(int tf)
	{
		return Math.log(1 + tf);
	}
	
	/**
	 * 誘몃━ 遺꾩꽍�븳 idfMap�쓣 �솢�슜�븯�뿬
	 * �빐�떦 term�쓽 idf媛믪쓣 由ы꽩;
	 * @param term : idf媛믪쓣 �뼸�쓣 term(String)
	 * 
	 */
	public double getIDF(String term)
	{
		Double idf = idfMap.get(term);
		
		if(idf != null)
			return idf.doubleValue();
		else
			return computeIDF(docSetSize, 1);
	}
	
	public double computeIDF(int docSetSize, int df)
	{
		return Math.log((double)(docSetSize/df));
	}
	
	public int getDocSetSize(){
		return docSetSize;
	}
	
	public void setDocSetSize(int docSize){
		// TODO Auto-generated method stub
		docSetSize = docSize;
	}
	
	public void increase_Document(){
		docSetSize++;
	}
}
