package dataManagementClasses;

import java.util.Scanner;

import interfaces.DataReader;

public class ByteDataReader implements DataReader {

	private static final int BYTESIZE = Byte.BYTES; 
	public static final ByteDataReader INSTANCE = new ByteDataReader(); 
	
	private ByteDataReader() {}; 
	
	public Byte readDataFromArrayOfBytes(byte[] b, int index) {
		int value = 0; 
		int lSB; 
		for (int i=0; i < BYTESIZE; i++) { 
			value = value << 8; 
			lSB = 0x000000ff & b[index + i];
			value = value | lSB; 
		}
		return (byte) value; //Explicit casting knowing that value will always contain a byte
	}
	
	@Override
	public Object readDataFromInputScanner(Scanner input) {
		String s = input.nextLine(); 
		try {
			byte v = Byte.parseByte(s); 
			return new Byte(v); 
		} catch (Exception e) { 
			return null; 
		}
	}

}
