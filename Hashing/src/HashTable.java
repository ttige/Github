public class HashTable {
	int hKey;
	String[] table;

	public int gethKey() {
		return hKey;
	}

	public void sethKey(int hKey) {
		this.hKey = hKey;
	}

	public HashTable(String[] args) {
		int hashKey = 30;
		table = new String[56];
		for (int i = 0; i < args.length; i++) {
//		for (int i = 0; i < 32; i++) {
			System.out.print(i+" ");
			//Looks at input for a SEARCH command
			if (scanForSearch(args[i])) {
				String searchableArea = args[i].substring(7,args[i].length());
				int sc = (Math.abs(encrypt(searchableArea))) % hashKey;
				System.out.println(search(searchableArea, sc, false));
			}
			//Looks at input for a DELETE command
			else if(scanForDelete(args[i])){
				String searchableArea = args[i].substring(7,args[i].length());
				int sc = (Math.abs(encrypt(searchableArea))) % hashKey;
				System.out.println(search(searchableArea, sc, true));
			}//if no command, add value to the table
			else {
				sethKey(hashKey);
				int hc = (Math.abs(encrypt(args[i]))) % hashKey;
				System.out.println(args[i] + " " + hc + " ");
				boolean flag;
				do {
					flag = false;
					if (table[hc] == null || table[hc] == "") {
						table[hc] = args[i];
					} else {
						hc++;
						flag = true;
					}
				} while (flag == true);
			}
		}

		for (int i = 0; i < table.length; i++) {
			System.out.print(i + "] [ " + table[i] + " ]");
			System.out.println();
		}
	}

	public String search(String arg, int searchCode, boolean delete){
		if (table[searchCode] == null){
			return "No match found for " + arg + searchCode;
		}
		else{
			int sc = searchCode;
			try{
				if(table[sc] != null){
					for (int i = searchCode; i<table.length; i++){
						if (table[i].equals("")){
							System.out.println("*****TOMBSTONE HERE*****");
						}
						if(table[i].equals(arg) && delete == false){
							return arg + " was found at Index " + sc;
						}
						else if (table[i].equals(arg) && delete == true){
							table[i] = "";
							return arg + " was found at Index " + sc + " and has been deleted.";
						}
						else if (table[i] == null){
							System.out.println("ASDasd");
						}
					}
				}
			}
			catch(Exception e){
				System.out.println("YOU SUCK, THE PROGRAM IS FUBAR");
					}
		}
		return "No match found for " + arg + searchCode;
	}
	
//	public String search(String arg, int searchCode, boolean delete) {
//		//searchCode = (Math.abs(encrypt(arg))) % hKey;
//		if (hash[searchCode] == null && hash[searchCode] == null){
//			return "No match found for " + arg + searchCode;
//		}
//		else if (hash[searchCode].equals(arg)){
//			if (delete){
//				hash[searchCode] = "";
//				return (arg + " successfully deleted");
//			}
//			else{ 
//				return (arg + " found at Index " + searchCode);
//			}
//		}
//		else {
//			while (!hash[searchCode].equals(arg) && hash[searchCode] != null) {
//				if (hash[searchCode] != null) {
//					searchCode = Integer.parseInt(hash[searchCode]);
//					if (hash[searchCode].equals(arg)) {
//						if (delete){
//							hash[searchCode] = "";
//							return (arg + " successfully deleted");
//						}
//						else{ 
//							return (arg + " found at Index " + searchCode);
//						}
//					}
//				}
//			}
//			return "No match found for " + arg;
//		}
//
//	}

	public static int encrypt(String arg) {
		int encValue = 0;
		for (int i = 0; i < arg.length(); i++) {
			char character = arg.charAt(i);
			encValue += character;
		}
		return encValue;
	}

	public static int encrypt(String[] arg) {
		int encValue = 0;
		for (int i = 0; i < arg.length; i++) {
			for (int j = 0; j < arg[i].length(); j++) {
				char character = arg[i].charAt(j);
				encValue += character;
			}
		}
		return encValue;
	}

	public boolean scanForSearch(String arg) {
		if (arg.length() > 6) {
			if (arg.substring(0, 6).equals("SEARCH")) {
				return true;
			} else
				return false;
		} else
			return false;
	}
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
