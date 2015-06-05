package AnalysisSystem;
//package kr.ac.kit.AnalysisSystem;

import java.util.ArrayList;

public class TalkInfo {
	private ArrayList<String> talkSeqList;
	private double weight;
	
	public TalkInfo(ArrayList<String> talkSeqList){
		this.talkSeqList = talkSeqList;
		this.weight = 0.0;
	}
	
	public TalkInfo(double weight, ArrayList<String> talkSeqList){
		this.talkSeqList = talkSeqList;
		this.weight = weight;
	}
	
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public void increase_weight(){
		this.weight += 2;
	}
	
	public ArrayList<String> gettalkSeqList() {
		return talkSeqList;
	}
	
	public void settalkSeqList(ArrayList<String> talkSeqList) {
		this.talkSeqList = talkSeqList;
	}
}
