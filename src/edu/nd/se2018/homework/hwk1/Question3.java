package edu.nd.se2018.homework.hwk1;

public class Question3 {

	public Question3(){}

    public int getMirrorCount(int[] numbers){
    	int longest = 0;
    	int current = 0;
    	//for each element in numbers, check the array in reverse for that number
    	//if it is not found, or is in the position of the current number,
    	//move to the next element
    	for (int i=0; i<numbers.length; i++) {
    		current = 0;
    		for (int j=numbers.length-1; j > -1 && i+current < numbers.length; j--) {
    			//if the j element is the same as the i element increment current
    			if (numbers[i+current] == numbers[j]) {
    				current += 1;
    			}
    			else {
    				//if there was a mirrored run, see if it is the new longest
    				if (current > 0) {
    					if (current > longest) {
    						longest = current;
    					}
    					current = 0;
    				}
    			}
    		}
    		//after inner loop terminates before running out of elements, check if
    		//it is the longest
    		if (current > longest) {
				longest = current;
			}
    	}
		return longest;
	}
}
