package dataManagementClasses;

import generalUtilities.DataUtils;
import interfaces.DataWriter;

public class LongDataWriter implements DataWriter {

	private static final int LONGSIZE = Long.BYTES; 
	public static final LongDataWriter INSTANCE = new LongDataWriter(); 
	
	private LongDataWriter() {}; 
	
	public void writeDataToArrayOfBytes(byte[] b, int index, Object rvalue) {
		Long value = (Long) rvalue; 
		Long lSB; 
		for (int i=0; i < LONGSIZE; i++) { 
			lSB = 0x000000ff & value;
			value = value >> 8; 
		    b[index + LONGSIZE - i - 1] = (byte) (lSB & 0x000000ff); 
		}
 
	}

	@Override
	public String toString(Object value) {
		return String.format(DataUtils.INTEGERFORMAT, (Long) value); //TODO Change to LONGFORMAT
	}

}
