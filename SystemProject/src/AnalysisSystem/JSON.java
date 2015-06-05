package AnalysisSystem;
//package kr.ac.kit.AnalysisSystem;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSON {
	
	public JSON(){}
	
	public JSONObject getJsonFile(String doc, ArrayList<String> coreKeywordList, 
			HashMap<String, KeywordInfo> keywordList, ArrayList<KeywordFlowInfo> keywordFlowList){
		
		JSONObject mainJsonObj = new JSONObject();
		JSONArray jArray = new JSONArray();
		JSONArray jArray2 = new JSONArray();
		JSONArray jArray3 = new JSONArray();
		JSONArray jArray4 = new JSONArray();
		JSONArray jArray5 = new JSONArray();
		//JSONArray jArray6 = new JSONArray();
		
		JSONObject jObject = new JSONObject();
		JSONObject jObject2 = new JSONObject();
			
		try {
			mainJsonObj.put("name", "test");
			jObject.put("dialogue", doc);
			jObject2.put("name", "ncn");
			
			for (int i = 0; i < coreKeywordList.size(); i++) {
				String keyword = coreKeywordList.get(i);
				if (keywordList.containsKey(keyword)) {
					JSONObject jObject3 = new JSONObject();
					jObject3.put("name", keyword);
					jObject3.put("size", keywordList.get(keyword).getWeight());
					jArray3.put(i, jObject3);
				}
			}
			
			jObject2.put("children", jArray3);
			jArray2.put(0, jObject2);
			jObject.put("children", jArray2);
			jArray.put(0, jObject);
			
			JSONObject jObject4 = new JSONObject();

			for (int i = 0; i < keywordFlowList.size(); i++) {
				JSONObject jObject5 = new JSONObject();
				jObject5.put("keyword", keywordFlowList.get(i).getKeyword());
				jObject5.put("extractionTime", keywordFlowList.get(i).getExtractionTime());
				jObject5.put("decayTime", keywordFlowList.get(i).getDecayTime());
				jArray5.put(i, jObject5);
			}
			
			jObject4.put("keywordList",jArray5);
			jArray4.put(0, jObject4);
			
			mainJsonObj.put("timeLineInfo", jArray4);
			
			mainJsonObj.put("children", jArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//mainJsonObj.put("persons", jArray);
		//String jsonInfo = mainJsonObj.toString();
		//System.out.println(jsonInfo);		
		
		return mainJsonObj;
	}
}
