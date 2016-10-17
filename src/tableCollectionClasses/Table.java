package tableCollectionClasses;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

import dataManagementClasses.AttributeInSchema;
import dataManagementClasses.TableSchema;

public class Table {
	private TableSchema ts;
	private ArrayList<Record> rList;
	private int recordLength;

	public Table(TableSchema ts) {
		this.ts = ts;
		recordLength = ts.getDataRecordLength();
		rList = new ArrayList<>();
	}

	public AttributeInSchema getAttribute(int index) {
		return ts.getAttr(index);
	}

	public int getNumberOfAttrs() {
		return ts.getNumberOfAttrs();
	}

	public Record getNewRecordInstance() {
		return new Record(ts);
	}

	public void addRecord(Record r) {
		rList.add(r);
	}

	public Record getRecord(int index) {
		return rList.get(index);
	}

	/**
	 * Getter for Record List Size
	 * 
	 * @return the number of records in this table
	 */
	public int getNumberOfRecords() {
		return rList.size();
	}

	public int getRecordLength() {
		return recordLength;
	}

	public void displaySchema() {
		System.out.println(ts);
	}

	public void displayTable() {
		this.displaySchema();
		for (Record r : rList)
			System.out.println(r);
	}

	public void readTableDataFromFile(RandomAccessFile file) throws IOException {

		long numberOfDataRecords = (file.length() - file.getFilePointer()) / ts.getDataRecordLength();

		for (int dr = 1; dr <= numberOfDataRecords; dr++) {
			Record record = getNewRecordInstance();
			record.readFromFile(file);
			addRecord(record);
		}

	}
	/**
	public static Table readTableDataFromFile2(RandomAccessFile file) throws IOException {

		TableSchema ts = TableSchema.getInstance(file);
		Table table = new Table(ts);
		long numberOfDataRecords = (file.length() - file.getFilePointer()) / ts.getDataRecordLength();

		for (int dr = 1; dr <= numberOfDataRecords; dr++) {
			Record record = table.getNewRecordInstance();
			record.readFromFile(file);
			table.addRecord(record);
		}
		return table;
	}
	*/
	
	

}
