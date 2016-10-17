package tableCollectionClasses;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Scanner;

import dataManagementClasses.AttributeInSchema;
import dataManagementClasses.TableSchema;

public class Record {
	private byte[] data;
	private TableSchema ts;

	public Record(TableSchema ts) {
		data = new byte[ts.getDataRecordLength()];
		this.ts = ts;
	}

	/**
	 * read the data value corresponding to the selected attribute
	 * 
	 * @param attrNumber
	 *            index of the selected attribute in schema.. 0, 1, ...
	 * @return the data value that is extracted from the internal array of bytes
	 */
	public Object readData(int attrNumber) {
		AttributeInSchema ais = ts.getAttr(attrNumber);
		return ais.readDataValueFromArrayOfBytes(data, ais.getDataOffset());
	}

	/**
	 * Writes a data value as a sequence of bytes in the array data[].
	 * 
	 * @param attrNumber
	 *            index of the selected attribute the value corresponds to
	 * @param value
	 *            the value to be written
	 */
	public void writeData(int attrNumber, Object value) {
		AttributeInSchema ais = ts.getAttr(attrNumber);
		ais.writeDataValueToArrayOfBytes(data, ais.getDataOffset(), value);
	}

	public void readFromFile(RandomAccessFile file) throws IOException {
		// reads record from the given file -- a sequence of bytes beginning at
		// current file pointer
		file.read(data);
	}

	public void readDataRecordFromUser(Scanner input) {
		for (int i = 0; i < ts.getNumberOfAttrs(); i++) {

			AttributeInSchema ais = ts.getAttr(i);

			Object value = null;
			do {
				System.out.print("  " + ais + ": ");
				value = ais.readDataValueFromInputScanner(input);
			} while (value == null);

			writeData(i, value);
		}
	}

	public void writeToFile(RandomAccessFile file) throws IOException {
		file.write(data);
	}

	public String toString() {
		String s = "|";
		int nAttrs = ts.getNumberOfAttrs();
		for (int a = 0; a < nAttrs; a++) {
			AttributeInSchema ais = ts.getAttr(a);
			s += ais.toString(readData(a)) + (a == nAttrs - 1 ? " |" : "");

		}
		return s;
	}
	
	/**
	 * This method returns a String representation of the record of a table
	 * This string representation is well fitted for the frequency table.
	 * @return a string representation of the record
	 */
	public String toFrequencyString() {
		String s = "|";
		int nAttrs = ts.getNumberOfAttrs();
		for (int a = 0; a < nAttrs; a++) {
			AttributeInSchema ais = ts.getAttr(a);
			s += ais.toString(readData(a));

		}
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Record other = (Record) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		return true;
	}

}
