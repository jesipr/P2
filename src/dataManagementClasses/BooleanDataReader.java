package dataManagementClasses;

import java.util.Scanner;

import interfaces.DataReader;

public class BooleanDataReader implements DataReader {

	public static final BooleanDataReader INSTANCE = new BooleanDataReader();

	private BooleanDataReader() {
	};

	public Boolean readDataFromArrayOfBytes(byte[] b, int index) {
		return b[index] != 0;
	}

	@Override
	public Object readDataFromInputScanner(Scanner input) {
		String s = input.nextLine();
		try {
			s.trim();
			if (s.equalsIgnoreCase("true"))
				return new Boolean(true);
			else if (s.equalsIgnoreCase("false"))
				return new Boolean(false);
		} catch (Exception e) {
		}
		return null;
	}
}
