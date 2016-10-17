package p2MainClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import dataManagementClasses.AttributeInSchema;
import dataManagementClasses.TableSchema;
import generalUtilities.DataUtils;
import tableCollectionClasses.Record;
import tableCollectionClasses.Table;

public class TableAnalyzer {

	public static void main(String[] args) {

		if (!DataUtils.isValidArgs(args)) {
			System.out.println("Illegal Argument. Try again");
			System.exit(0);
		}

		RandomAccessFile raf = null;
		File file = null;
		Scanner sc = new Scanner(System.in);
		TableSchema ts = null;
		Table table = null;

		// To this point we have verified arguments

		// Lets manage the RAF. The algorithm will behave different if the file
		// already exist.
		try {
			file = new File("inputData/" + args[0]);
			if (!file.exists()) {
				// FILE DOES NOT EXIST
				System.out.println("File does not exist. Now closing,");
				System.exit(0);
				// We have to add records to this file if the user wants.
			} else {

				// FILE DOES EXIST

				raf = new RandomAccessFile(file, "rw");
				ts = TableSchema.getInstance(raf);

				if (!ts.isValid(raf)) {
					System.out.println("File is not valid, closing...");
					System.exit(0);
				}
				// File have a Valid Schema
				// Lets verify if it have data, if it does, we need to verify is
				// valid data, if it doesn't have data then we need to ask user
				// to start adding data

				table = new Table(ts);
				if (!(raf.getFilePointer() == raf.length())) {
					// RAF has records.
					table.readTableDataFromFile(raf);
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.getMessage();
			System.out.println("Terminating");
			System.exit(0);
		}

		table.displayTable();

		// Until here I have a table and have displayed it to the user.
		// Lets display now the attributes, type and index number of attribute
		// to user in a table

		System.out.println("The attributes, type and index of the tables are as follow:");
		System.out.print("| ");
		for (int i = 0; i < table.getNumberOfAttrs(); i++) {
			System.out.print(table.getAttribute(i).toString() + ": " + (i + 1) + " | ");
			// ToString() method is automatically called. Added just for reading
			// purpose.
		}
		System.out.println("");

		// To this point I have already showed the table of attributes with
		// type to the user as well as the number of the column.

		// Need now to ask user which attributes would he like to analyze
		System.out.println(
				"Which tables would you like to anylyze?:\n(Input integer value corresponding to above table, non positive value to cancel input)");

		ArrayList<AttributeInSchema> attrsChosen = new ArrayList<AttributeInSchema>();
		ArrayList<Integer> selectionInts = new ArrayList<Integer>();
		int counter = 0;
		int selection = 1;
		int dataOffSet = 0;

		//Ask and verify user inputs for attribute to analyze 
		while (counter < table.getNumberOfAttrs() && selection > 0) {
			System.out.println("Next attribute index:");
			try{
				selection = sc.nextInt();
			}catch(InputMismatchException e){
				System.out.println("Input must be Integer. Try again");
				sc.nextLine();
				continue;
			}
			sc.nextLine();
			if (selection <= 0)
				continue;
			if (selection > table.getNumberOfAttrs()){
				System.out.println("Numbered entered to big");
				System.out.println("Must be number equal or lower than " + table.getNumberOfAttrs());
				continue;
			}
			if (selectionInts.contains(selection - 1)) {
				System.out.println("Attribute already chosen.");
			} else {
				
				//Adds the selection to ArrayList
				AttributeInSchema oldAIS = table.getAttribute(selection - 1);
				AttributeInSchema newAIS = new AttributeInSchema(oldAIS.getName(), oldAIS.gettIndex(), dataOffSet);
				attrsChosen.add(newAIS);
				selectionInts.add(selection - 1);
				System.out.println(table.getAttribute(selection - 1) + " was added.");
				dataOffSet += DataUtils.getTypeSize(newAIS.gettIndex());
				counter++;
			}
		}

		// Construct a new schema with the ArrayList of AttributeInSchema
		TableSchema newTS = new TableSchema(attrsChosen);
		Table subTable = new Table(newTS);

		// For each record in the old table, copy the values of the attributes
		// the user wants and add the record to the subTable.
		for (int i = 0; i < table.getNumberOfRecords(); i++) {
			Record oldRec = table.getRecord(i);
			Record newRec = new Record(newTS);

			// Lets add all the records value needed from the original table
			// To a new record and add the record o the new table.
			for (int k = 0; k < newTS.getNumberOfAttrs(); k++) {
				// Each attribute selection in
				Object obj = oldRec.readData(selectionInts.get(k));
				newRec.writeData(k, obj);

			}
			subTable.addRecord(newRec);
		}


		// Create a MapTable of the Records
		Map<Record, Integer> freq = new HashMap<Record, Integer>();
		for (int i = 0; i < subTable.getNumberOfRecords(); i++) {
			Record temp = subTable.getRecord(i);
			if (freq.containsKey(temp)) {
				freq.put(temp, freq.get(temp) + 1);
			} else {
				freq.put(temp, 1);
			}
		}

		//Prints the TableSchema with Frequency and Percentage Columns
		System.out.println( newTS.toFrequencyTableString());
		
		DecimalFormat f = new DecimalFormat("#.##");
		
		//Prints the frequency distributing records. It includes the frequency and the percentage.
		for (Record s : freq.keySet()) {
			System.out.println(s.toFrequencyString() + "\t" + freq.get(s) + "\t" + 
					f.format((double)(freq.get(s)/(double)subTable.getNumberOfRecords())*100 )+"%\t\t|");
		}

		sc.close();
		System.out.println("Terminated");

	}

}
