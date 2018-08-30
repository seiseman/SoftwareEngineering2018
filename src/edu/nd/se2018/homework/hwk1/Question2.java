package edu.nd.se2018.homework.hwk1;
import java.util.HashMap;
import java.util.Map;


public class Question2 {

	public Question2(){}

	public String getMostFrequentWord(String input, String stopwords){
		HashMap<String, Integer> valid_words = new HashMap<String, Integer>();
		HashMap<String, Integer> invalid_words = new HashMap<String, Integer>();
		String[] input_words = input.split(" ");
		String[] stop_words = stopwords.split(" ");

		for (int i=0; i<stop_words.length; i++) {
			//add all the stop words to a map
			invalid_words.put(stop_words[i], 1);
		}

		for (int i=0; i<input_words.length; i++) {
			//see if the word in in the invalid (stop) set, if it is skip it
			//else, see if it an existing key in the valid set, if it is get the value and add 1
			//if it is not in the map, add it as a new key to the valid map with a value of 1
			if (!invalid_words.containsKey(input_words[i])) {
				if (valid_words.containsKey(input_words[i])) {
					int v = valid_words.get(input_words[i]);
					valid_words.replace(input_words[i], v+1);
				}
				else {
					valid_words.put(input_words[i], 1);
				}
			}
		}
		//logic for finding greatest key adapted from:
		//https://stackoverflow.com/questions/5911174/finding-key-associated-with-max-value-in-a-java-map
		Map.Entry<String, Integer> maxEntry = null;
		boolean dup = false;
		for (Map.Entry<String, Integer> entry : valid_words.entrySet()) {
			//if the current max is null or smaller than the current entry, set the max as the current entry
			//also, set the boolean dup to false to signify that it is a unique max
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
				dup = false;
			}
			//if if max is equal to the current value, flag that there is a duplicate
			else if (entry.getValue().compareTo(maxEntry.getValue()) == 0) {
				dup = true;
			}
		}
		if (dup) {
			return null;
		}
		else {
			return maxEntry.getKey();
		}
	}
}
