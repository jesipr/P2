package dataManagementClasses;

import generalUtilities.DataUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class TableSchema {
	private AttributeInSchema[] attrs; // the attributes
	private int size; // number of attributes added

	private TableSchema(int n) {
		attrs = new AttributeInSchema[n];
	}

	public void addAttribute(AttributeInSchema attr) throws IllegalStateException {
		if (size == attrs.length)
			throw new IllegalStateException("Table of attributes is full.");
		attrs[size++] = attr;
	}

	public int getNumberOfAttrs() {
		return size;
	}

	public AttributeInSchema getAttr(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("getAttr: Index out of bounds: " + index);
		return attrs[index];
	}

	public boolean isValid(RandomAccessFile file) throws IOException {

		return getFirstPositionOfRecords() == file.getFilePointer();
	}

	public long getFirstPositionOfRecords() {
		long par = 2 + 2 * getNumberOfAttrs();

		for (int i = 0; i < getNumberOfAttrs(); i++) {
			par += getAttr(i).getName().length() * 2;
		}

		return par;
	}

	public static TableSchema getInstance(int n) {
		// PRE: n is a valid positive integer value
		return new TableSchema(n);
	}

	/**
	 * Receives a RAF as a parameter. It reads the first two bytes to check the
	 * number of attributes. It then creates a local TableSchema and adds all
	 * the attributes and returns it.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static TableSchema getInstance(RandomAccessFile file) throws IOException {
		file.seek(0);
		short nAttrs = file.readShort();
		TableSchema st = new TableSchema(nAttrs);
		int offset = 0;
		// read attributes
		for (int i = 0; i < nAttrs; i++) {
			AttributeInSchema ais = new AttributeInSchema(file, offset);
			st.addAttribute(ais);
			offset += DataUtils.getTypeSize(ais.gettIndex());
		}
		return st;

	}

	public void saveSchema(RandomAccessFile file) throws IllegalStateException, IOException {
		// Saves this schema into the given RAF, beginning at its current file
		// pointer
		// location. The file is assumed to be open and with file pointer at 0.

		if (file.getFilePointer() != 0)
			throw new IllegalStateException("File pointer is not at 0.");

		// first write value for the number of attributes
		file.writeShort(size);

		// ready to save the schema; one attribute at the time...
		for (AttributeInSchema a : attrs)
			a.writeToFile(file);
	}

	public String toString() {
		String s = "|";
		for (int i = 0; i < attrs.length - 1; i++)
			s += String.format(DataUtils.STRINGFORMAT, attrs[i].getName());
		s += String.format(DataUtils.STRINGFORMAT, attrs[attrs.length - 1].getName()) + " |";
		s += "\n";
		for (int i = 0; i <= this.size * DataUtils.VALUEWIDE + 2; i++)
			s += '=';
		return s;
	}
	
	/**
	 * This method returns a String representation of the header of a table
	 * It contains the Frequency and Percentage Column title.
	 * @return a string representation of the table schema
	 */
	public String toFrequencyTableString() {
		String s = "|";
		for (int i = 0; i < attrs.length - 1; i++)
			s += String.format(DataUtils.STRINGFORMAT, attrs[i].getName());
		s += String.format(DataUtils.STRINGFORMAT, attrs[attrs.length - 1].getName()) + " ";
		s += String.format(DataUtils.STRINGFORMAT, "Frequency") + " ";
		s += String.format(DataUtils.STRINGFORMAT, "Percentage") + " |";
		s += "\n";
		for (int i = 0; i <= this.size * DataUtils.VALUEWIDE * 2 + 2; i++)
			s += '=';
		return s;
	}

	public int getDataRecordLength() {
		int len = 0;

		for (AttributeInSchema ais : this.attrs)
			len += ais.getDataSize();
		return len;
	}

	public TableSchema getSubschema(ArrayList<Integer> selectedAttrs) {
		TableSchema newSchema = new TableSchema(selectedAttrs.size());

		return newSchema;
	}
	
	public TableSchema(ArrayList<AttributeInSchema> t){
		this.attrs = new AttributeInSchema[t.size()]; 
		for(int i = 0; i<t.size();i++){
			attrs[i] = t.get(i);
			size++;
		}
	}
}
