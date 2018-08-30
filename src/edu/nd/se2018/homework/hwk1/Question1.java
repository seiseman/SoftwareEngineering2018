package edu.nd.se2018.homework.hwk1;
import java.util.HashSet;

public class Question1 {

	public Question1(){}

	public int getSumWithoutDuplicates(int[] numbers){
		//Create a Hashset that containts the unique elements of the array
		HashSet<Integer> hs = new HashSet<Integer>();
		int sum = 0;
		for (int i=0; i<numbers.length; i++) {
			//attempt to add each element to the array, if the add is successful, add it to the running sum
			if (hs.add(numbers[i])) {
				sum = sum + numbers[i];
			}
		}
		return sum;
	}
}
