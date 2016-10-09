package dataManagementClasses;

import generalUtilities.DataUtils;
import interfaces.DataWriter;

public class BooleanDataWriter implements DataWriter {

	public static final BooleanDataWriter INSTANCE = new BooleanDataWriter(); 
	
	private BooleanDataWriter() {}; 

	public void writeDataToArrayOfBytes(byte[] b, int index, Object rvalue) {
		Boolean value = (Boolean) rvalue; 
		b[index] = (byte) (value ? 1 : 0);  
	}

	@Override
	public String toString(Object value) {
		return String.format(DataUtils.BOOLEANFORMAT, (Boolean) value); 
	}
}
