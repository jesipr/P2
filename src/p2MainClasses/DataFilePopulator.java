package p2MainClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

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

		RandomAccessFile raf = null;
		File file = null;
		Scanner sc = new Scanner(System.in);
		int numbersOfAttributes;
		String numbersOfAttributeString, attributeType, name;
		TableSchema ts = null;

		// To this point we have verified arguments

		// Lets manage the RAF. The algorithm will behave different if the file
		// already exist.
		try {
			file = new File("inputData/" + args[0] + ".txt");
			if (!file.exists()) {
				do {
					do {
						System.out.println("Enter positive value of attributes that this table will contain");
						numbersOfAttributeString = sc.nextLine();
					} while (!DataUtils.isValidInt(numbersOfAttributeString));
					numbersOfAttributes = Integer.parseInt(numbersOfAttributeString);
				} while (numbersOfAttributes <= 0); // Look for the comparator.
				// To this point we have how many attributes the user want.

				// Create a TableSchema with the numbers of attributes specified
				// by the
				// user.
				ts = TableSchema.getInstance(numbersOfAttributes);

				int attrOffset = 0;

				for (int i = 0; i < numbersOfAttributes; i++) {
					do {
						System.out.println("Enter name of attribute #" + (i + 1) + ":");
						name = sc.nextLine().toLowerCase();
					} while (!DataUtils.isValidName(name));
					do {
						System.out.println("Enter type of " + name + " attribute:");
						attributeType = sc.nextLine().toLowerCase();
					} while (!DataUtils.isValidDataType(attributeType));

					ts.addAttribute(new AttributeInSchema(name, DataUtils.getTypeID(attributeType), attrOffset));
					attrOffset += DataUtils.getTypeSize(attributeType);
				}
				file.createNewFile();
				raf = new RandomAccessFile(file, "rw");
				raf.seek(0);
				ts.saveSchema(raf);
			} else {
				raf = new RandomAccessFile(file, "rw");
				ts = TableSchema.getInstance(raf);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Table table = new Table(ts);
		Boolean keep = false;

		do {
			Record r = table.getNewRecordInstance();
			r.readDataRecordFromUser(sc);
			table.addRecord(r);
			System.out.println("Enter more values? (true/false)");
			keep = sc.nextBoolean();
			sc.nextLine();
		} while (keep);

		for (int i = 0; i < table.getNumberOfRecords(); i++) {
			try {
				table.getRecord(i).writeToFile(raf);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		table.displayTable();

		System.out.println("Terminated");

	}

}
