package dataManagementClasses;

import generalUtilities.DataUtils;
import interfaces.DataWriter;

public class CharDataWriter implements DataWriter {

	private static final int CHARSIZE = Character.BYTES; 
	public static final CharDataWriter INSTANCE = new CharDataWriter(); 
	
	private CharDataWriter() {}; 
	
	public void writeDataToArrayOfBytes(byte[] b, int index, Object rvalue) {
		int value = (Character) rvalue; 
		int lSB; 
		for (int i=0; i < CHARSIZE; i++) { 
			lSB = 0x000000ff & value;
			value = value >> 8; 
		    b[index + CHARSIZE - i - 1] = (byte) (lSB & 0x000000ff); 
		}
 
	}

	@Override
	public String toString(Object value) {
		return String.format(DataUtils.CHARFORMAT, (Character) value); 
	}

}
