package dataManagementClasses;

import java.util.Scanner;

import interfaces.DataReader;

public class DoubleDataReader implements DataReader {
	private static final int DOUBLESIZE = Double.BYTES; 
	public static final DoubleDataReader INSTANCE = new DoubleDataReader(); 
	
	private DoubleDataReader() {}; 
	
	public Double readDataFromArrayOfBytes(byte[] b, int index) {
		long value = 0; 
		long lSB; 
		for (int i=0; i < DOUBLESIZE; i++) { 
			value = value << 8; 
			lSB = 0x000000ff & b[index + i];
			value = value | lSB; 
		}
		return Double.longBitsToDouble(value);
	}
	
	@Override
	public Object readDataFromInputScanner(Scanner input) {
		String s = input.nextLine(); 
		try {
			double v = Double.parseDouble(s); 
			return new Double(v); 
		} catch (Exception e) { 
			return null; 
		}
	}

}
