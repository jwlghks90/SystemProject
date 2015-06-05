package AnalysisSystem;
//package kr.ac.kit.AnalysisSystem;

import java.util.Date;

public class KeywordInfo{
	private double weight;
	private int frequency;
	private String tag;
	private Date extractionTime;
	
	public KeywordInfo(double weight, int frequency, String tag) {
		super();
		this.weight = weight;
		this.frequency = frequency;
		this.tag = tag;
		this.extractionTime = new Date();
	}
	
	public KeywordInfo(int frequency, String tag){
		super();
		this.weight = 0.0;
		this.frequency = frequency;
		this.tag = tag;
		this.extractionTime = new Date();
	}
	
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public String getTag(){
		return tag;
	}
	public void setTag(String tag){
		this.tag = tag;
	}
	public Date getExtractionTime() {
		return extractionTime;
	}
}
