package database;

import java.io.*;

import java.util.*;


public class DBApp {
	File file;
	Vector<Table> tables= new Vector<Table>();
	public DBApp() throws IOException {
		this.init();
	}
	
	
	public void init() throws IOException 		// this does whatever initialization you would like 

	{
		file = new File("metadata.csv");
		File config = new File("src/resources");
		boolean bool = config.mkdir();
		if(bool) {
			System.out.println("Directory created successfully");
		}
		else {
			System.out.println("Sorry couldn’t create specified directory");
		}
		File newconfig= new File("src/resources/DBApp.config");
		newconfig.createNewFile();
		Properties prop1= new Properties();
		prop1.setProperty("MaximumRowsCountinTablePage", "200");
		Properties prop2= new Properties();
		prop2.setProperty("MaximumEntriesinOctreeNode", "16");
		FileWriter writer = new FileWriter(newconfig);
		prop1.store(writer, null);
		prop2.store(writer, null);
		writer.close();
		
		
		
	}
							// or leave it empty if there is no code you want to
							// execute at application startup
	// following method creates one table only 
	// strClusteringKeyColumn is the name of the column that will be the primary key and the clustering column as well. 
	//The data type of that column will be passed in htblColNameType 
	// htblColNameValue will have the column name as key and the data 
	// type as value 
	// htblColNameMin and htblColNameMax for passing minimum and maximum values
	// for data in the column. Key is the name of the column
	public void createTable(String strTableName, String strClusteringKeyColumn, Hashtable<String,String> htblColNameType, Hashtable<String,String> htblColNameMin, Hashtable<String,String> htblColNameMax ) throws DBAppException, IOException 
	{
		Table t=new Table(strTableName,strClusteringKeyColumn,htblColNameType,htblColNameMin,htblColNameMax,file);
		tables.add(t);
	}
	
	// following method creates an octree 
	// depending on the count of column names passed. 
	// If three column names are passed, create an octree. 
	// If only one or two column names is passed, throw an Exception. 
	public void createIndex(String strTableName,String[] strarrColName) throws DBAppException
	{
	
	}
	
	// following method inserts one row only. 
	// htblColNameValue must include a value for the primary key 
	public void insertIntoTable(String strTableName, Hashtable<String,Object> htblColNameValue) throws Exception
	{
		
		
			try {
                check(strTableName,htblColNameValue, file);
			}
            catch (DBAppException e) {
                System.out.println("ERROR");
                return;
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		
		
		
		
		Table wants= new Table();
		for(Table row: tables) {
			if (row.tableName==strTableName)
				wants=row;
		}
		if(wants.empty) {
			Page p=new Page();
			wants.pages.add(p);
			
		}
		
		
		
	}
	public boolean check(String strTableName,Hashtable<String,Object> htblColNameValue, File filePath) throws DBAppException, IOException{
		BufferedReader br= new BufferedReader(new FileReader(filePath));
		boolean flag=false;
		String temp=br.readLine();
		while(flag || temp!=null) {
			String[] splitted=temp.split(",");
			if (splitted[0]==strTableName) {
				Set<String> keys = htblColNameValue.keySet();
				for (String key : keys) {
					if(key==splitted[1]) {
						if(htblColNameValue.get(key).getClass().getTypeName()!=splitted[2]) {
							flag=false;
							throw new DBAppException("Wrong Data Type");
							
							
						}
						
					}
					else {
						temp=br.readLine();
					}
				}
			}
			else {
				temp=br.readLine();
			}

			
		}
		return flag;
		


	}
	
	
	// following method updates one row only 
	// htblColNameValue holds the key and new value 
	// htblColNameValue will not include clustering key as column name 
	// strClusteringKeyValue is the value to look for to find the row to update. 
	public void updateTable(String strTableName, String strClusteringKeyValue, Hashtable<String,Object> htblColNameValue ) throws DBAppException 
	{
		
		
	}
	
	// following method could be used to delete one or more rows. 
	// htblColNameValue holds the key and value. This will be used in search 
	// to identify which rows/tuples to delete. 
	// htblColNameValue enteries are ANDED together 
	public void deleteFromTable(String strTableName, Hashtable<String,Object> htblColNameValue) throws DBAppException
	{
		
	}
	
//	public Iterator selectFromTable(SQLTerm[] arrSQLTerms, String[] strarrOperators) throws DBAppException
//	{
//		
//	}
	public static void main (String[] args) throws Exception {
		
		String strTableName = "Student";
		DBApp dbApp = new DBApp( );
		Hashtable<String,String> htblColNameType = new Hashtable<String,String>( );
		htblColNameType.put("id", "java.lang.Integer");
		htblColNameType.put("name", "java.lang.String");
		htblColNameType.put("gpa", "java.lang.double");
		
		Hashtable<String,String> htblColNameMin= new Hashtable<String,String>();
		htblColNameMin.put("ID", "0");
		htblColNameMin.put("name", "A");
		htblColNameMin.put("BD", "0");
		htblColNameMin.put("Address", "A");
		
		Hashtable<String,String> htblColNameMax= new Hashtable<String,String>();
		htblColNameMax.put("ID", "0");
		htblColNameMax.put("name", "A");
		htblColNameMax.put("BD", "0");
		htblColNameMax.put("Address", "A");
		
		dbApp.createTable(strTableName, "id", htblColNameType, htblColNameMin, htblColNameMax);
		
		Hashtable htblColNameValue = new Hashtable( );
		htblColNameValue.put("id", new Integer( 2343432 ));
		htblColNameValue.put("name", new String("Ahmed Noor" ) );
		htblColNameValue.put("gpa", new Double( 0.95 ) );
		dbApp.insertIntoTable( strTableName , htblColNameValue );
		
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 5674567 ));
		htblColNameValue.put("name", new String("Dalia Noor" ) );
		htblColNameValue.put("gpa", new Double( 1.25 ) );
		dbApp.insertIntoTable( strTableName , htblColNameValue );
		
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 23498 ));
		htblColNameValue.put("name", new String("John Noor" ) );
		htblColNameValue.put("gpa", new Double( 1.5 ) );
		dbApp.insertIntoTable( strTableName , htblColNameValue );
		
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 78452 ));
		htblColNameValue.put("name", new String("Zaky Noor" ) );
		htblColNameValue.put("gpa", new Double( 0.88 ) );
		dbApp.insertIntoTable( strTableName , htblColNameValue );
		
		System.out.println(dbApp.tables.get(0).pages.get(0).maxrows);
		System.out.println(dbApp.tables.get(0).pages.get(0).availablerows);
		
		
		
	}
	
}
