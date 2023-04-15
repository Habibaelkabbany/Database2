package database;

import java.io.*;
import java.util.*;




public class Table implements Serializable {
	public String tableName;
	public boolean empty;
	public Vector<Page> pages= new Vector<Page>();
	public Table() {
		
	}
	
	
	public Table(String strTableName, String strClusteringKeyColumn, Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameMin, Hashtable<String, String> htblColNameMax, File filepath) throws IOException {

		this.tableName = strTableName;
		empty=true;

		
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(filepath,true));
		

		Set<String> keys = htblColNameType.keySet();
		for (String key : keys) {
			boolean primary = false;
			if (key == strClusteringKeyColumn) {
				primary = true;
			}
			try {

				bWriter.append(strTableName + "," + key + "," + htblColNameType.get(key) + "," + primary + "," + null
						+ "," + null + "," + htblColNameMin.get(key) + "," + htblColNameMax.get(key));
				bWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		bWriter.close();
		
	}
	

	
	public static void main (String[] args) throws IOException {
		
	}
	
}
