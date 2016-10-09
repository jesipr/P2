 package dataManagementClasses;

import generalUtilities.DataUtils;
import interfaces.DataWriter;

public class IntDataWriter implements DataWriter {

	private static final int INTSIZE = Integer.BYTES; 
	public static final IntDataWriter INSTANCE = new IntDataWriter(); 
	
	private IntDataWriter() {}; 
	
	public void writeDataToArrayOfBytes(byte[] b, int index, Object rvalue) {
		Integer value = (Integer) rvalue; 
		int lSB; 
		for (int i=0; i < INTSIZE; i++) { 
			lSB = 0x000000ff & value;
			value = value >> 8; 
		    b[index + INTSIZE - i - 1] = (byte) (lSB & 0x000000ff); 
		}
 
	}

	@Override
	public String toString(Object value) {
		return String.format(DataUtils.INTEGERFORMAT, (Integer) value); 
	}
	
}

