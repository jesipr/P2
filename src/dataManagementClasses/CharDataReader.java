package dataManagementClasses;

import java.util.Scanner;

import interfaces.DataReader;

public class CharDataReader implements DataReader {

	private static final int CHARSIZE = Short.BYTES;
	public static final CharDataReader INSTANCE = new CharDataReader();

	private CharDataReader() {
	};

	public Character readDataFromArrayOfBytes(byte[] b, int index) {
		int value = 0;
		int lSB;
		for (int i = 0; i < CHARSIZE; i++) {
			value = value << 8;
			lSB = 0x000000ff & b[index + i];
			value = value | lSB;
		}
		return (char) value; // Explicit casting knowing that value will always
								// contain a char
	}

	@Override
	public Object readDataFromInputScanner(Scanner input) {
		String s = input.nextLine();
		if (s.length() == 1)
			return new Character(s.charAt(0));
		return null;

	}

}
