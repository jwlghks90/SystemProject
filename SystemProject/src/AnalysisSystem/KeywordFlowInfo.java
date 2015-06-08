package AnalysisSystem;
//package kr.ac.kit.AnalysisSystem;

import java.util.Date;

public class KeywordFlowInfo {
	private String keyword;
	private Date extractionTime;
	private Date decayTime;
	
	public KeywordFlowInfo(String keyword, Date extractionTime){
		this.keyword = keyword;
		this.extractionTime = extractionTime;
		this.decayTime = new Date();
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getExtractionTime() {
		return extractionTime;
	}

	public Date getDecayTime() {
		return decayTime;
	}
}
