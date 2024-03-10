package test1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class HelloWorld {
	
	
	static int getBiggestGoatThatFits(ArrayList<Integer> arr, int weightLimit) {
		if (arr.isEmpty()) return -1;
		else {
			int currentBest = -1;
			int indexOfCurrentBest = -1;
			
			for(int i=0; i<arr.size(); i++) {
				if(arr.get(i) > currentBest && arr.get(i) <= weightLimit) {
					currentBest = arr.get(i);
					indexOfCurrentBest = i;
				}
			}
			
			if(currentBest == -1) return -1;
			else {
				arr.remove(indexOfCurrentBest);
				return currentBest;
			}
		}
	}
	
	static boolean arrHasBiggerThan(ArrayList<Integer> arr, int bigElem) {
		for(int i=0; i<arr.size(); i++) {
			if(arr.get(i) > bigElem) return true;
		}
		return false;
	}
	
	static boolean simulateGoats(int goatNum, int courseCount, ArrayList<Integer> goatWeights, int testSize ) {

		if(arrHasBiggerThan(goatWeights, testSize)) return false;
		
		ArrayList<Integer> goatArr = new ArrayList<Integer>(goatWeights);
		
		
		int crossingsCount = 0;
		
		while(!goatArr.isEmpty()) {
			
			int currentCrossWeight = 0;
			do {
				int goat = getBiggestGoatThatFits(goatArr, testSize - currentCrossWeight);
				if(goat == -1 || currentCrossWeight + goat > testSize) break;
				else currentCrossWeight += goat;
			}
			while(currentCrossWeight <= testSize);
			
			crossingsCount ++;
		}
		
		return crossingsCount <= courseCount;
	}
	
	
	static int estimateWeight(int goatNum, int crossingsCount, ArrayList<Integer> goatWeights, int lowPoint, int highPoint) {
		if(lowPoint == highPoint) return highPoint;
		else {
			int testPoint = (lowPoint + highPoint)/2;
			if(simulateGoats(goatNum, crossingsCount, goatWeights, testPoint)){
				return estimateWeight(goatNum, crossingsCount, goatWeights, lowPoint, testPoint);
			} else {
				return estimateWeight(goatNum, crossingsCount, goatWeights, testPoint+1, highPoint);
			}
		}
	}
	
	
	public static void main(String args[]) {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	     
		Scanner scanner = new Scanner(System.in);
		
		int n = 0, k = 0;
		ArrayList<Integer> goatArray = new ArrayList<Integer>();
		
		if (scanner.hasNextInt()) {
			n = scanner.nextInt();
			if(n < 1 || n > 1000) {
				System.out.println("N is not in desired bounds, exiting program");
				System.exit(0);
			}
		}
		if (scanner.hasNextInt()) {
			k = scanner.nextInt();
			if(k < 1 || k > 1000) {
				System.out.println("K is not in desired bounds, exiting program");
				System.exit(0);
			}
		}
		
		for(int i=0; i<n; i++) {
			if (scanner.hasNextInt()) {
				int Ai = scanner.nextInt();
				if(Ai < 1 || Ai > 100000) {
					System.out.println("Goat weight is not in desired bounds, exiting program");
					System.exit(0);
				}
		        goatArray.add(Ai);
		     }
		}
		
		int weightSum = 0;
		for(int i=0; i<goatArray.size(); i++) weightSum += goatArray.get(i);
		
		System.out.println("Lowest capacity of boat is: " + estimateWeight(n, k, goatArray, 0, weightSum));

	}
}
