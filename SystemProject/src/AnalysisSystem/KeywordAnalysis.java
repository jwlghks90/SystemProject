package AnalysisSystem;
//package kr.ac.kit.AnalysisSystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;
import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.hannanum.WorkflowFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.*;

/* 키워드 분석 */
public class KeywordAnalysis {
	private Workflow workflow; 			// 형태소 분석기
	private static int dialogueCnt = 0; // 대화 수
	private Date startTime;				// 회의내용 분석 시작 시간
	private Array array;
	private Sort sort;
	private TFIDF tfidf;
	private JSON json;
	
	private static HashMap<String, KeywordInfo> keywordList;// 키워드 list
	private HashMap<Integer, DialogueInfo> keySeqList;		// 대화 순서 list
	private ArrayList<String> coreKeywordList; 				// 핵심 키워드 list
	private ArrayList<String> preCoreKeywordList;
	
	private boolean[][] coOccurrence; 						// 단어간 공기 관계
	private double[][] probabilityMatrix; 					// 확률 메트릭스
	
	//private ArrayList<String> talkView;					// 대화뷰
	private HashMap<String, String> talkView;
	private ArrayList<String> backupId;
	private ArrayList<KeywordFlowInfo> keywordFlowList; 	// 시점별 키워드 list
	private HashMap<Integer, ArrayList<String>> eojeolList; // 스페이스 바 단위
	private String coreSentence;
	
	private HashMap<Integer, String> summaryKeyword; 		// 요약 키워드
	private String summaryDocument; 						// 요약문
	private int summaryDepth;							    // 요약 깊이
	
	public KeywordAnalysis(){
		try {
			startTime = new Date();
			workflow = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_HMM_POS_TAGGER);
			workflow.activateWorkflow(true);
			init(); // 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//초기화
	private void init(){
		keywordList = new HashMap<String, KeywordInfo>();
		keySeqList = new HashMap<Integer, DialogueInfo>();
		//talkView = new ArrayList<String>();
		talkView = new HashMap<String, String>();
		keywordFlowList = new ArrayList<KeywordFlowInfo>();
		backupId = new ArrayList<String>();
		eojeolList = new HashMap<Integer, ArrayList<String>>();
		preCoreKeywordList = null;
		tfidf = new TFIDF(); 
		array = new Array();
		json = new JSON();
		sort = new Sort();
	}
	
	private void modify_Init(){
		keywordList = new HashMap<String, KeywordInfo>();
		tfidf = new TFIDF();
		keywordFlowList = new ArrayList<KeywordFlowInfo>();
		eojeolList = new HashMap<Integer, ArrayList<String>>();
		preCoreKeywordList = null;
		keySeqList = new HashMap<Integer, DialogueInfo>();
		init_DialogueCnt();
	}
	
	private JSONObject modify_Analysis(){
		int size = backupId.size();
		String id;
		String doc;
		
		for(int i = 0; i < size-1; i++){
			id = backupId.get(i);
			doc = talkView.get(id);
			keywordExtraction2(id, doc);
		}
		
		id = backupId.get(size-1);
		doc = talkView.get(id);
		
		backupId_Init(size);
		
		return keywordExtraction(id, doc);
	}
	
	private void backupId_Init(int scope){
		for(int i = 0; i < scope; i++)
			backupId.remove(i);
	}
	
	private void increase_DialogueCnt(){
		dialogueCnt++;
	}
	
	private void init_DialogueCnt(){
		dialogueCnt = 0;
	}
	
	private void update(String id, String doc, ArrayList<String> eojeolList){		
		this.eojeolList.put(dialogueCnt, eojeolList); // 어절 리스트 추가
		backupId.add(id);
		talkView.put(id, doc);  					  // 대화 추가
		increase_DialogueCnt(); 					  // 대화 증가
	}
	
	private void update2(String id, ArrayList<String> eojeolList){
		this.eojeolList.put(dialogueCnt, eojeolList); // 어절 리스트 추가
		backupId.add(id);
		increase_DialogueCnt(); 					  // 대화 증가
	}
	
	public JSONObject getModifyInfo(String id, String doc){
		talkView.replace(id, doc);
		modify_Init();
		return modify_Analysis();
	}
	
	public JSONObject keywordExtraction(String id, String doc){ // 키워드 추출
		try {
			ArrayList<String> strList = getTokenizer(doc);
			tfidf.increase_Document();
			workflow.analyze(doc); // Analysis using the work flow
			LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			ArrayList<String> seqkeyList = new ArrayList<String>();
			double weight = 0.0;
			
			for (Sentence s : resultList) {
				Eojeol[] eojeolArray = s.getEojeols();
				for (int i = 0; i < eojeolArray.length; i++){
					if (eojeolArray[i].length > 0) {
						String tag = eojeolArray[i].getTag(0);
						String morpheme = eojeolArray[i].getMorpheme(0);
						
						if(tag.equals("ncpa") || tag.equals("ncn")){
							// 동작성 명사, 비서술성 명사
							weight += 1.0;
							/*if((i+1) < eojeolArray.length && 
									eojeolArray[i+1].getTag(0).equals("ncn")){
								calTFMap(morpheme + eojeolArray[i+1].getMorpheme(0), tag);
								seqkeyList.add(morpheme + eojeolArray[i+1].getMorpheme(0));
								i++;
							}
							else{*/
								
							if((i+1) < eojeolArray.length && 
									eojeolArray[i+1].getTag(0).equals("pvg")){
								keywordList = tfidf.calTFMap(morpheme, tag, keywordList);
								seqkeyList.add(morpheme);
								String tmp = eojeolArray[i+1].getMorpheme(0);
								for(String s2 : strList){
									if(s2.matches(".*"+tmp+".*")){
										seqkeyList.add(s2.substring(s2.indexOf(tmp)));
										break;
									}
								}
								i++;
							}
							else{
								boolean check = false;
								for(String s2 : strList){
									if(s2.matches(".*"+morpheme+"의.*")){
										if((i+1) < eojeolArray.length && 
											eojeolArray[i+1].getTag(0).equals("ncn")){
											keywordList = tfidf.calTFMap(morpheme + eojeolArray[i+1].getMorpheme(0), tag, keywordList);
											seqkeyList.add(morpheme + eojeolArray[i+1].getMorpheme(0));
											check = true;
											i++;
											break;
										}
									}
								}
								if(!check){
									keywordList = tfidf.calTFMap(morpheme,tag,keywordList);
									for(String s2 : strList){
										if(s2.matches(".*"+morpheme+".*")){
											seqkeyList.add(s2);
											break;
										}
									}
								}
							}
						}
						else if(tag.equals("nnc") || tag.equals("nno")){
							// 양수사, 서수사
							weight += 1;
							if((i+1) < eojeolArray.length){
								String temp = eojeolArray[i+1].getMorpheme(0);
								if(temp.equals("년") || temp.equals("월") || temp.equals("일") || temp.equals("시") || 
										temp.equals("개") || temp.equals("대") || temp.equals("번")){
									keywordList = tfidf.calTFMap((morpheme + temp), tag, keywordList);
									seqkeyList.add(morpheme+temp);
								}
								else{
									for(String s2 : strList){
										if(s2.matches(".*"+morpheme+"년.*") || s2.matches(".*"+morpheme+"월.*") || s2.matches(".*"+morpheme+"일.*") || s2.matches(".*"+morpheme+"시.*")
												|| s2.matches(".*"+morpheme+"분.*") || s2.matches(".*"+morpheme+"개.*") || s2.matches(".*"+morpheme+"대.*") || s2.matches(".*"+morpheme+"번.*")){	
											keywordList = tfidf.calTFMap(s2.substring(0, morpheme.length()+1), tag, keywordList);
											seqkeyList.add(s2.substring(0, morpheme.length()+1));
											break;
										}
										if((i+1) < eojeolArray.length && 
												eojeolArray[i+1].getTag(0).equals("pvg")){
											String tmp = eojeolArray[i+1].getMorpheme(0);
											for(String s3 : strList){
												if(s3.matches(".*"+tmp+".*")){
													seqkeyList.add(s3.substring(s3.indexOf(tmp)));
													break;
												}
											}
											i++;
										}
									}
								}
							}
						}
						else if(tag.equals("mag")){ 
							// 일반 부사
							if(morpheme.equals("매우") 
									|| morpheme.equals("가장") 
									|| morpheme.equals("정말") 
									|| morpheme.equals("아주")){
								seqkeyList.add(morpheme);
							}
						}
						else if(tag.equals("paa")){ 
							// 성상형용사
							for(String s2 : strList){
								if(s2.matches(".*"+morpheme+".*")){
									seqkeyList.add(s2);
									break;
								}
							}
						}
						else if(morpheme.equals("중요")){
							// 중요, ncps
							for(String s2 : strList){
								if(s2.matches(".*"+morpheme+".*")){
									seqkeyList.add(s2);
									break;
								}
							}
						}
					}
				}
			}
			keySeqList.put(dialogueCnt, new DialogueInfo(weight, seqkeyList));
			update(id, doc, strList);
			keywordList = tfidf.calTFIDF(keywordList);
			
			coreKeywordExtraction();
			coreSentenceExtraction();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getJSON(doc);
	}
	
	public void keywordExtraction2(String id, String doc){ // 키워드 추출
		try {
			ArrayList<String> strList = getTokenizer(doc);
			tfidf.increase_Document();
			workflow.analyze(doc); // Analysis using the work flow
			LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			ArrayList<String> seqkeyList = new ArrayList<String>();
			double weight = 0.0;
			
			for (Sentence s : resultList) {
				Eojeol[] eojeolArray = s.getEojeols();
				for (int i = 0; i < eojeolArray.length; i++){
					if (eojeolArray[i].length > 0) {
						String tag = eojeolArray[i].getTag(0);
						String morpheme = eojeolArray[i].getMorpheme(0);
						
						if(tag.equals("ncpa") || tag.equals("ncn")){
							// 동작성 명사, 비서술성 명사
							weight += 1.0;
							/*if((i+1) < eojeolArray.length && 
									eojeolArray[i+1].getTag(0).equals("ncn")){
								calTFMap(morpheme + eojeolArray[i+1].getMorpheme(0), tag);
								seqkeyList.add(morpheme + eojeolArray[i+1].getMorpheme(0));
								i++;
							}
							else{*/
								
							if((i+1) < eojeolArray.length && 
									eojeolArray[i+1].getTag(0).equals("pvg")){
								keywordList = tfidf.calTFMap(morpheme, tag, keywordList);
								seqkeyList.add(morpheme);
								String tmp = eojeolArray[i+1].getMorpheme(0);
								for(String s2 : strList){
									if(s2.matches(".*"+tmp+".*")){
										seqkeyList.add(s2.substring(s2.indexOf(tmp)));
										break;
									}
								}
								i++;
							}
							else{
								boolean check = false;
								for(String s2 : strList){
									if(s2.matches(".*"+morpheme+"의.*")){
										if((i+1) < eojeolArray.length && 
											eojeolArray[i+1].getTag(0).equals("ncn")){
											keywordList = tfidf.calTFMap(morpheme + eojeolArray[i+1].getMorpheme(0), tag, keywordList);
											seqkeyList.add(morpheme + eojeolArray[i+1].getMorpheme(0));
											check = true;
											i++;
											break;
										}
									}
								}
								if(!check){
									keywordList = tfidf.calTFMap(morpheme,tag,keywordList);
									for(String s2 : strList){
										if(s2.matches(".*"+morpheme+".*")){
											seqkeyList.add(s2);
											break;
										}
									}
								}
							}
						}
						else if(tag.equals("nnc") || tag.equals("nno")){
							// 양수사, 서수사
							weight += 1;
							if((i+1) < eojeolArray.length){
								String temp = eojeolArray[i+1].getMorpheme(0);
								if(temp.equals("년") || temp.equals("월") || temp.equals("일") || temp.equals("시") || 
										temp.equals("개") || temp.equals("대") || temp.equals("번")){
									keywordList = tfidf.calTFMap((morpheme + temp), tag, keywordList);
									seqkeyList.add(morpheme+temp);
								}
								else{
									for(String s2 : strList){
										if(s2.matches(".*"+morpheme+"년.*") || s2.matches(".*"+morpheme+"월.*") || s2.matches(".*"+morpheme+"일.*") || s2.matches(".*"+morpheme+"시.*")
												|| s2.matches(".*"+morpheme+"분.*") || s2.matches(".*"+morpheme+"개.*") || s2.matches(".*"+morpheme+"대.*") || s2.matches(".*"+morpheme+"번.*")){	
											keywordList = tfidf.calTFMap(s2.substring(0, morpheme.length()+1), tag, keywordList);
											seqkeyList.add(s2.substring(0, morpheme.length()+1));
											break;
										}
										if((i+1) < eojeolArray.length && 
												eojeolArray[i+1].getTag(0).equals("pvg")){
											String tmp = eojeolArray[i+1].getMorpheme(0);
											for(String s3 : strList){ //s2 전체
												if(s3.matches(".*"+tmp+".*")){
													seqkeyList.add(s3.substring(s3.indexOf(tmp)));
													break;
												}
											}
											i++;
										}
									}
								}
							}
						}
						else if(tag.equals("mag")){ 
							// 일반 부사
							if(morpheme.equals("매우") 
									|| morpheme.equals("가장") 
									|| morpheme.equals("정말") 
									|| morpheme.equals("아주")){
								seqkeyList.add(morpheme);
							}
						}
						else if(tag.equals("paa")){ 
							// 성상형용사
							for(String s2 : strList){
								if(s2.matches(".*"+morpheme+".*")){
									seqkeyList.add(s2);
									break;
								}
							}
						}
						else if(morpheme.equals("중요")){
							// 중요, ncps
							for(String s2 : strList){
								if(s2.matches(".*"+morpheme+".*")){
									seqkeyList.add(s2);
									break;
								}
							}
						}
					}
				}
			}
			keySeqList.put(dialogueCnt, new DialogueInfo(weight, seqkeyList));
			update2(id, strList);
			keywordList = tfidf.calTFIDF(keywordList);
			
			coreKeywordExtraction();
			coreSentenceExtraction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void keywordExtraction3(String id, String doc){ // 키워드 추출
		try {
			talkView.put(id,doc);
			tfidf.increase_Document();
			workflow.analyze(doc); // Analysis using the work flow
			LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			
			for (Sentence s : resultList) {
				Eojeol[] eojeolArray = s.getEojeols();
				for (int i = 0; i < eojeolArray.length; i++) {
					if (eojeolArray[i].length > 0) {
						System.out.println(eojeolArray[i].getMorpheme(0) + " " + eojeolArray[i].getTag(0) + "\n");
					}
				}
			}
			keywordList = tfidf.calTFIDF(keywordList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void keywordExtraction4(String id, String doc){ // 키워드 추출
		try {
			//workflow = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_POS_SIMPLE_22);	
			talkView.put(id, doc);
			tfidf.increase_Document();
			workflow.analyze(doc); // Analysis using the work flow
			
			LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			
			for (Sentence s : resultList) {
				Eojeol[] eojeolArray = s.getEojeols();
				for (int i = 0; i < eojeolArray.length; i++) {
					if (eojeolArray[i].length > 0) {
						System.out.println(eojeolArray[i].getMorpheme(0) + " " + eojeolArray[i].getTag(0) + "\n");
					}
				}
			}
			keywordList = tfidf.calTFIDF(keywordList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getTokenizer(String text){
		StringTokenizer st = new StringTokenizer(text);
		ArrayList<String> strList = new ArrayList<String>();
		
		while(st.hasMoreTokens())
			strList.add(st.nextToken());
		
		return strList;
	}
	
	//핵심 키워드 추출
	public void coreKeywordExtraction(){
		Iterator<String> it = sort.sortByValue2(keywordList).iterator();
		Iterator<String> it2 = sort.sortByValue2(keywordList).iterator();
		
		double sum = 0.0;
        String tag;
        String temp;
        int size = 0;
        System.out.print("키워드 : ");
        
        while(it.hasNext()){
        	temp = it.next();
        	tag = keywordList.get(temp).getTag();
        	System.out.print(temp + ", ");
        	if(tag.equals("ncpa") || tag.equals("ncn")){
        		size++;
        		sum += keywordList.get(temp).getWeight();
        	}
        }

        System.out.print("핵심 키워드 : ");
        double avr = (sum/size);
        
        coreKeywordList = new ArrayList<String>();
        
        while(it2.hasNext()){
        	temp = it2.next();
        	if(avr <= keywordList.get(temp).getWeight()){
    			System.out.print(temp + "(" + keywordList.get(temp).getWeight() + "), ");
        		coreKeywordList.add(temp);
        	}
        }
        
        if(preCoreKeywordList == null){
        	preCoreKeywordList = coreKeywordList;
        }
        else{
        	checkCoreKeyword();
        	preCoreKeywordList = coreKeywordList;
        }
        //System.out.println("\n");
	}
	
	public void checkCoreKeyword(){
		boolean flag = false;
		for(int i = 0; i < preCoreKeywordList.size(); i++){
			for(int j = 0; j < coreKeywordList.size(); j++){
				if(preCoreKeywordList.get(i).equals(coreKeywordList.get(j))){
					flag = true;
				}
			}
			if(!flag){
				String keyword = preCoreKeywordList.get(i);
				KeywordFlowInfo kfi = new KeywordFlowInfo(keyword, keywordList.get(keyword).getExtractionTime());
				keywordFlowList.add(kfi);
			}
		}
	}

	//핵심 문장 추출
	public void coreSentenceExtraction(){	
		HashMap<Integer, DialogueInfo> sl = keySeqList;
        for(String ck : coreKeywordList){
        	for(int i = 0; i < sl.size(); i++){
        		ArrayList<String> tmpList = sl.get(i).getSeqKeyList();
        		
        		for(int j = 0; j < tmpList.size(); j++){
        			if(tmpList.get(j).matches(".*"+ ck +".*")){
        				sl.get(i).increase_weight();
        				//System.out.print(j+"번째 : " + sl.get(i).getWeight() + "\n");
        			}
        		}
        	}
        }
        
        double greatVal = sl.get(0).getWeight();
        int position = 0;
        int prePosition = 0;
        for(int i = 1; i < sl.size(); i++){
    		if(sl.get(i).getWeight() >= greatVal){
    			prePosition = position;
    			position = i;
    			greatVal = sl.get(i).getWeight();
    		}
    	}
        
        //System.out.print("핵심 문장 : ");
        ArrayList<String> cs = sl.get(position).getSeqKeyList();
        
        //System.out.print("1. ");
        for(int i = 0; i < cs.size(); i++){
        	String sentence = cs.get(i) + " ";
        	coreSentence = coreSentence + sentence;
        	//System.out.print(cs.get(i) + " ");
        }
        
        if(sl.size() > 1){	
        	if(prePosition == position){
        		position = 1;
        		prePosition = 1;
        		greatVal = sl.get(1).getWeight();
        		for(int i = 2; i < sl.size(); i++){
        			if(sl.get(i).getWeight() >= greatVal){
        				prePosition = position;
        				position = i;
        				greatVal = sl.get(i).getWeight();
        			}
        		}	
        	}

        	System.out.println();
        	cs = sl.get(prePosition).getSeqKeyList();
        
        	//System.out.print("            2. ");
        	//for(int i = 0; i < cs.size(); i++)
        		//System.out.print(cs.get(i) + " ");
        }
        System.out.println("\n");
	}
	
	//핵심 키워드 출력
	public void showKeywordInfo(boolean sw, String doc){
        Iterator<String> it = sort.sortByValue2(keywordList).iterator();
        Iterator<String> it2 = sort.sortByValue2(keywordList).iterator();

        if(sw){       
        	coreKeywordExtraction();
        }
        else{
			while(it.hasNext()){
	            String temp = it.next();   		
	    			System.out.println("키워드 : " + temp);
	    			System.out.println("빈도 : " + keywordList.get(temp).getFrequency());
	    			System.out.println("가중치 : " + keywordList.get(temp).getWeight()+ "\n");
	        }
        }
	}

	//공기 관계 Matrix
	public void co_Occurrence(){		
		HashMap<Integer, DialogueInfo> tmpkeySeqList = keySeqList;
		
		// 단어 간 공기 관계 테이블
		coOccurrence = array.getBooleanMatrix(dialogueCnt, coreKeywordList.size());
		
		for(int i = 0; i < tmpkeySeqList.size(); i++){
			ArrayList<String> tmpList = tmpkeySeqList.get(i).getSeqKeyList();
			for(int j = 0; j < coreKeywordList.size(); j++){
				String coreKey = coreKeywordList.get(j);
				for(int k = 0; k < tmpList.size(); k++){
					if(tmpList.get(k).matches(".*"+coreKey+".*")){
						coOccurrence[i][j] = true;
						break;
					}
				}
			}
		}
	}
	
	//회의 종료
	public void end_Conference(){
        //coreSentenceExtraction(); 	// 핵심 문장 추출
		co_Occurrence(); 			 // 공기 관계
		conditional_probability(5);  // 조건부 확률 확인
		makeSummaryDocument();       // 요약문 생성
		showSummaryDocument();
	}
	
	// 조건부 확률
	public void conditional_probability(int depth){
		int size = coreKeywordList.size();
		probabilityMatrix = array.getSquareMatrix(size);
		int cnt[] = array.getIntArr(size);
		int overlapCnt[] = array.getIntArr(size);
		
		for(int i = 0; i < size; i++){
			int frequency = 0;
			for(int j = 0; j < dialogueCnt; j++){
				if(coOccurrence[j][i]){
					frequency++;
				}
			}
			cnt[i] = frequency;
		}

		for(int k = 0; k < size; k++){
			for(int i = 0; i < dialogueCnt; i++) {
				int j = 0;
				if(coOccurrence[i][k]) { 
					for(j = 0; j < size; j++) {
						if(coOccurrence[i][j])
							overlapCnt[j] += 1;
					}
				}
			}

			for (int j = 0; j < size; j++){
				probabilityMatrix[k][j] = 
						(double)overlapCnt[j] / (double)cnt[k];
				probabilityMatrix[k][j] = Math.round(probabilityMatrix[k][j]*100)/100.0;
			}
			
			for(int i = 0; i < overlapCnt.length; i++)
				overlapCnt[i] = 0;
		}
		
		double[] arr = array.getDoubleArr(size); 
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				arr[i] += probabilityMatrix[i][j];
			}
			arr[i] = (arr[i]/(double)size);
		}
		
		summaryKeyword = new HashMap<Integer, String>(); // 요약을 위한 키워드
		
		this.summaryDepth = depth;
		for(int i = 0; i < summaryDepth; i++){
			int index = 0;
			double greateVal = arr[0];
			for(int j = 0; j < coreKeywordList.size(); j++){
				if(greateVal < arr[j]){
					index = j;
					greateVal = arr[j];
				}
			}
			summaryKeyword.put(i, coreKeywordList.get(index));
			arr[index] = 0;
		}
	}

	public void showCoOccurrence(){
		for(int i = 0; i < dialogueCnt; i++){
			for(int j = 0; j < coreKeywordList.size(); j++){
				System.out.print(coOccurrence[i][j] + " ");
			}
			System.out.println("\n");
		}
	}
	
	public void showProbabilityMatrix(){
		for(int i = 0; i < coreKeywordList.size(); i++){
			for(int j = 0; j < coreKeywordList.size(); j++){
				System.out.print(probabilityMatrix[i][j] + " ");
			}
			System.out.println("\n");
		}
	}
	
	//요약본 출력
	public void makeSummaryDocument(){
		summaryDocument = "";
		
		ArrayList<String> s = new ArrayList<String>();
		
		for(int i = 0; i < summaryKeyword.size(); i++) {
			for (int j = 0; j < talkView.size(); j++) {
				String id = backupId.get(j);
				if (talkView.get(id).matches(
						".*" + summaryKeyword.get(i) + ".*")) {
					s.add(talkView.get(id));
				}
			}
		}
		
		for(int i = 0; i < talkView.size(); i++){
			for(int j = 0; j < s.size(); j++){
				if(talkView.get(backupId.get(i)).equals(s.get(j))){
					String d = s.get(j) + "\n";
					summaryDocument += d;
					break;
				}
			}
		}
	}
	
	public void showSummaryDocument(){
		System.out.println("요약문 : \n" + summaryDocument);
	}
	
	public String getSummaryDocument(){
		return summaryDocument;
	}
	
	public int getDialogueCnt(){
		return dialogueCnt;
	}
	
	public Date getStartTime(){
		return startTime;
	}

	public JSONObject getJSON(String doc){
		return json.getJsonFile(doc, coreKeywordList, keywordList, keywordFlowList);
	}
}