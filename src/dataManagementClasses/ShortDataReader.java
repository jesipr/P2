package dataManagementClasses;

import java.util.Scanner;

import interfaces.DataReader;

public class ShortDataReader implements DataReader {

	private static final int SHORTSIZE = Short.BYTES; 
	public static final ShortDataReader INSTANCE = new ShortDataReader(); 
	
	private ShortDataReader() {}; 
	
	public Short readDataFromArrayOfBytes(byte[] b, int index) {
		int value = 0; 
		int lSB; 
		for (int i=0; i < SHORTSIZE; i++) { 
			value = value << 8; 
			lSB = 0x000000ff & b[index + i];
			value = value | lSB; 
		}
		return (short) value; //Explicit casting knowing that value will always contain a short number
	}
	
	@Override
	public Object readDataFromInputScanner(Scanner input) {
		String s = input.nextLine(); 
		try {
			short v = Short.parseShort(s); 
			return new Short(v); 
		} catch (Exception e) { 
			return null; 
		}
	}

}
