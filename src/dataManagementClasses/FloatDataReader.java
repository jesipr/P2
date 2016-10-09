package dataManagementClasses;

import java.util.Scanner;

import interfaces.DataReader;

public class FloatDataReader implements DataReader {

	private static final int FLOATSIZE = Float.BYTES; 
	public static final FloatDataReader INSTANCE = new FloatDataReader(); 
	
	private FloatDataReader() {}; 

	public Float readDataFromArrayOfBytes(byte[] b, int index) {
		int value = 0; 
		int lSB; 
		for (int i=0; i < FLOATSIZE; i++) { 
			value = value << 8; 
			lSB = 0x000000ff & b[index + i];
			value = value | lSB; 
		}
		return Float.intBitsToFloat(value); 
	}
	
	@Override
	public Object readDataFromInputScanner(Scanner input) {
		String s = input.nextLine(); 
		try {
			Float v = Float.parseFloat(s); 
			return new Float(v); 
		} catch (Exception e) { 
			return null; 
		}
	}

}

