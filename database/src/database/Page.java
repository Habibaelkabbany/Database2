package database;

import java.io.*;
import java.util.*;

public class Page implements Serializable{
	Object min, max;
	Vector<Hashtable<String,Object>> rows;
	public int maxrows;
	public int availablerows;
	public Page() throws Exception {
		availablerows=0;
		String configFilePath = "src/resources/DBApp.config";
		FileInputStream propsInput = new FileInputStream(configFilePath);
		Properties prop = new Properties();
		prop.load(propsInput);
		maxrows=Integer.parseInt(prop.getProperty("MaximumRowsCountinTablePage"));
		
	}

}
