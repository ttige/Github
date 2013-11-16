import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Driver {
	public static void main(String[] args) {
		HashTable ht = new HashTable(readFile("/Users/thomastige/Documents/Eclipse workspace/Hashing/Assignment3.txt"));
//		HashTableTwo ht = new HashTableTwo(readFile("/Users/thomastige/Documents/Eclipse workspace/Hashing/Assignment3.txt"));
	}
//This method reads the text file and stores it in an array
	public static String[] readFile(String filename){
		String line = null;
		ArrayList<String> list = new ArrayList<String>();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while((line = reader.readLine()) != null){
				list.add(line);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		//This script will trim the resulting array to remove superfluous spaces. This ensures that
		//the strings have the same hashcodes as the ones included in the SEARCH and DELETE commands
		String[] arr = new String[list.size()];
		list.toArray(arr);
		String[] trimmedArr = new String[arr.length];
		for (int i = 0; i < arr.length; i++){
			trimmedArr[i] = arr[i].trim();
		}
		return trimmedArr;
	}
	//This will take the ascii value of each character in a string and sum them together. The resulting value is returned.
	public static int encrypt(String arg) {
		int encValue = 0;
		for (int i = 0; i < arg.length(); i++) {
			char character = arg.charAt(i);
			encValue += character;
		}
		return encValue;
	}
}
