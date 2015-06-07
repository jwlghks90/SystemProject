package AnalysisSystem;
//package kr.ac.kit.AnalysisSystem;

public class Array{
	public Array(){}
	
	public int[] getIntArr(int size){
		int[] arr = new int[size];
		
		for(int i =0; i < size; i++)
			arr[i] = 0;
		
		return arr;
	}
	
	public double[] getDoubleArr(int size){
		double[] arr = new double[size];
		
		for(int i =0; i < size; i++)
			arr[i] = 0.0;
		
		return arr;
	}
	
	public double[][] getSquareMatrix(int size){		
		double[][] squareMatrix = new double[size][size];
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				squareMatrix[i][j] = 0;
			}
		}
		return squareMatrix;
	}
	
	public boolean[][] getBooleanMatrix(int col, int row){	
		boolean[][] booleanMatrix = new boolean[col][row];
		
		for(int i = 0; i < col; i++)
			for(int j = 0; j < row; j++)
				booleanMatrix[i][j] = false;

		return booleanMatrix;
	}
}