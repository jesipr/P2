package dataManagementClasses;

import generalUtilities.DataUtils;
import interfaces.DataWriter;

public class FloatDataWriter implements DataWriter{

	private static final int FLOATSIZE = Float.BYTES; 
	public static final FloatDataWriter INSTANCE = new FloatDataWriter(); 
	
	private FloatDataWriter() {}; 
	
	public void writeDataToArrayOfBytes(byte[] b, int index, Object rv) {
		Float v = (Float) rv; 
		int value = Float.floatToIntBits(v); 
		int lSB; 
		for (int i=0; i < FLOATSIZE; i++) { 
			lSB = 0x000000ff & value;
			value = value >> 8; 
		    b[index + FLOATSIZE - i - 1] = (byte) (lSB & 0x000000ff); 
		}
 
	}

	@Override
	public String toString(Object value) {
		return String.format(DataUtils.FLOATFORMAT, (Float) value); 
	}
	
}