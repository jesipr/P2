package dataManagementClasses;

import generalUtilities.DataUtils;
import interfaces.DataReader;
import interfaces.DataWriter;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class AttributeInSchema extends Attribute {
	private int dataOffset; // offset of its value in the records of table it is
							// part of
	private DataReader dataReader;
	private DataWriter dataWriter;

	public AttributeInSchema(String name, int tIndex, int dataOffset) {
		super(name, tIndex);
		this.dataOffset = dataOffset;
		this.dataReader = DataUtils.getTypeDataReader(tIndex);
		this.dataWriter = DataUtils.getTypeDataWriter(tIndex);
	}

	public AttributeInSchema(RandomAccessFile file, int dataOffset) throws IOException {
		super(file);
		this.dataOffset = dataOffset;
		this.dataReader = DataUtils.getTypeDataReader(super.gettIndex());
		this.dataWriter = DataUtils.getTypeDataWriter(super.gettIndex());
	}

	public int getDataOffset() {
		return dataOffset;
	}

	/**
	 * .. just for testing purposes... public String toString() { return
	 * super.toString()+":"+dataOffset; }
	 **/

	public Object readDataValueFromArrayOfBytes(byte[] a, int starting) {
		return dataReader.readDataFromArrayOfBytes(a, starting);
	}

	public Object readDataValueFromInputScanner(Scanner input) {
		return dataReader.readDataFromInputScanner(input);
	}

	public void writeDataValueToArrayOfBytes(byte[] a, int starting, Object value) {
		dataWriter.writeDataToArrayOfBytes(a, starting, value);
	}

	public String toString(Object value) {
		return dataWriter.toString(value);
	}
}
