package dataManagementClasses;

import java.util.Scanner;

import interfaces.DataReader;

public class LongDataReader implements DataReader {

	private static final int LONGSIZE = Long.BYTES; 
	public static final LongDataReader INSTANCE = new LongDataReader(); 
	
	private LongDataReader() {}; 
	
	public Long readDataFromArrayOfBytes(byte[] b, int index) {
		long value = 0; 
		long lSB; 
		for (int i=0; i < LONGSIZE; i++) { 
			value = value << 8; 
			lSB = 0x000000ff & b[index + i];
			value = value | lSB; 
		}
		return value; 
	}
	
	@Override
	public Object readDataFromInputScanner(Scanner input) {
		String s = input.nextLine(); 
		try {
			long v = Long.parseLong(s); 
			return new Long(v); 
		} catch (Exception e) { 
			return null; 
		}
	}

}
