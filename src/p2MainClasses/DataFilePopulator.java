package p2MainClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import dataManagementClasses.Attribute;
import dataManagementClasses.AttributeInSchema;
import dataManagementClasses.TableSchema;
import generalUtilities.DataUtils;
import tableCollectionClasses.Record;
import tableCollectionClasses.Table;

public class DataFilePopulator {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Illegal Argument. Try again");
			System.exit(0);
		}
		RandomAccessFile raf;
		

		Scanner sc = new Scanner(System.in);
		// To this point we have verified arguments and created a Random Access
		// File

		System.out.println("Enter number of attributes that it will contain");

		int numbersOfAttributes = sc.nextInt();
		sc.nextLine();

		TableSchema ts = TableSchema.getInstance(numbersOfAttributes);

		int attrOffset = 0;

		for (int i = 0; i < numbersOfAttributes; i++) {
			System.out.println("Enter name of attribute");
			String name = sc.nextLine().toLowerCase();

			System.out.println("Enter type of " + name + " attribute:");
			String atttributeType = sc.nextLine().toLowerCase();

			ts.addAttribute(new AttributeInSchema(name, DataUtils.getTypeID(atttributeType), attrOffset));
			attrOffset += DataUtils.getTypeSize(atttributeType);
		}

		// To this point our table schema have all the attributesinSchema with
		// their corresponding name, iD and offset (Columns)
		
		// Lets try to write the schema to our RAF
		
		
		try {
			raf = new RandomAccessFile(new File(args[0]), "rw");
			raf.seek(0);
			System.out.println("Random Access File created: " + raf.toString());
			ts.saveSchema(raf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Create a new table using the existing tableschema and start addinf records to it
		
		Table table = new Table(ts);
		for(int i = 0; i< numbersOfAttributes; i++){
			Record r = table.getNewRecordInstance();
			r.readDataRecordFromUser(sc);
			table.addRecord(r);
		}
		
		
		

		System.out.println("Terminated");

	}

}
