import java.util.Vector;

public class HashTableTwo {
	//Declare variables for probing count and for a universal variable representing the hashcode key.
	int totalProbe = 0;
	int totalSearch = 0;
	int hKey;
	//This vector will represent the table. 
	Vector table = new Vector();

	public int gethKey() {
		return hKey;
	}

	public void sethKey(int hKey) {
		this.hKey = hKey;
	}

	public HashTableTwo(String[] args) {
		//Set the universal hashcode key
		int hashKey = 30;
		sethKey(hashKey);
		//create a vector in each index of the original vector. Creating a vector of vectors.
		for (int i = 0; i < hKey; i++) {
			table.add(new Vector());
		}
		for (int i = 0; i < args.length; i++) {
			// Looks at input for a SEARCH command
			if (scanForSearch(args[i])) {
				String searchableArea = args[i].substring(7, args[i].length());
				int sc = (Math.abs(Driver.encrypt(searchableArea))) % hashKey;
				System.out.println(search(searchableArea, sc, false));
			}
			// Looks at input for a DELETE command
			else if (scanForDelete(args[i])) {
				String searchableArea = args[i].substring(7, args[i].length());
				int sc = (Math.abs(Driver.encrypt(searchableArea))) % hashKey;
				System.out.println(search(searchableArea, sc, true));
			}// if no command, add value to the table
			else {
				sethKey(hashKey);
				int hc = (Math.abs(Driver.encrypt(args[i]))) % hashKey;
				System.out.println(args[i] + " " + hc + " ");
				//Since we are using a vector, the value will simply be added wherever there is room.
				addV(table, hc, args[i]);
			}
		}
		//This will print a representation of the table, line by line.
		System.out.println("\n\n*****HASH TABLE REPRESENTATION*****\n");
		for (int i=0; i<table.size(); i++){
			System.out.print(i + " ");
			System.out.println(((Vector) table.get(i)).toString());
		}
		//Display the probing statistics
		System.out.println("\nTotal number of probes: " + totalProbe + ". Total number of searches: " + totalSearch + 
				". Average number of probes per search: " + ((float) totalProbe/(float)totalSearch));

	}
	//Adding an element to a bucket
	void addV(Vector v, int row, String get) {
		((Vector) v.get(row)).add(get);
	}
	//Removing an element from a bucket
	void remV(Vector v, int row, String get) {
		((Vector) v.get(row)).remove(get);
	}
	//Reading the String stored in a bucket
	String read(Vector v, int row, int bucket) {
		return (String) ((Vector) v.get(row)).get(bucket);
	}
	//Reading the number of buckets in a hash code
	int getSz(Vector v, int row){
		return (int) ((Vector) v.get(row)).size();
	}
	//This function will search for an element throughout the buckets. It will also delete them.
	public String search(String arg, int searchCode, boolean delete){
		//the counter is the numbers of probe that will be used
		int counter = 0;
		//search through the buckets of a hashcode to try and find the value
		for (int i=0; i<getSz(table, searchCode); i++){
			counter++;
			//If the method is only performing a search and not a deletion:
			if(read(table, searchCode, i).equals(arg) && !delete){
				totalProbe += counter;
				totalSearch++;
				return arg + " has been found at Index " + searchCode + ". Probe count: " + counter;
			}
			//If the method is performing a deletion:
			else if(read(table, searchCode, i).equals(arg) && delete){
				tombStone(arg, searchCode, i);
				totalProbe += counter;
				totalSearch++;
				return arg + " has been deleted from Index " + searchCode + ". Probe count: " + counter; 
			}
		}
		return "No match found for " + arg;
	}
	
	//Will delete elements from the buckets. The vectors automatically resize themselves.
	void tombStone(String arg, int row, int bucket){
		if (arg.equals(read(table, row, bucket))){
			remV(table, row, arg);
		}
	}
	
	//Read the input to find a SEARCH command
	public boolean scanForSearch(String arg) {
		if (arg.length() > 6) {
			if (arg.substring(0, 6).equals("SEARCH")) {
				return true;
			} else
				return false;
		} else
			return false;
	}
	//Read input to find a DELETE command
	public boolean scanForDelete(String arg){
	if (arg.length() > 6) {
		if (arg.substring(0, 6).equals("DELETE")) {
			return true;
		} else
			return false;
	} else
		return false;
	}

}
