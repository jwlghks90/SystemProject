package AnalysisSystem;
//package kr.ac.kit.AnalysisSystem;

import java.util.ArrayList;

public class DialogueInfo {
	private int a;
	private double weight;
	private ArrayList<String> seqKeyList;
	
	public DialogueInfo(ArrayList<String> seqKeyList){
		this.weight = 0.0;
		this.seqKeyList = seqKeyList;
	}
	
	public DialogueInfo(double weight, ArrayList<String> seqKeyList){
		this.weight = weight;
		this.seqKeyList = seqKeyList;
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
	
	public ArrayList<String> getSeqKeyList() {
		return seqKeyList;
	}
	
	public void setSeqKeyList(ArrayList<String> seqKeyList) {
		this.seqKeyList = seqKeyList;
	}
}
