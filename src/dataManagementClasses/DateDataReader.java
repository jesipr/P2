package dataManagementClasses;

import java.util.Scanner;

// import com.sun.org.apache.xerces.internal.impl.dv.InvalidDateException;

import exceptionClasses.InvalidDateException;

import generalUtilities.DataUtils;
import interfaces.DataReader;

public class DateDataReader implements DataReader {

	public static final DateDataReader INSTANCE = new DateDataReader(); 
	
	private DateDataReader() {}; 

	public Date readDataFromArrayOfBytes(byte[] b, int index) throws InvalidDateException {
		return new Date(b[index], b[index+1], ShortDataReader.INSTANCE.readDataFromArrayOfBytes(b, index+2)); 
	}

	@Override
	public Object readDataFromInputScanner(Scanner input) {

		try{ 
			System.out.print("\tmonth: "); 
			byte month = input.nextByte(); 

			System.out.print("\tday: "); 
			byte day = input.nextByte(); 

			System.out.print("\tyear: "); 
			short year = input.nextShort(); 

			input.nextLine();    // clean the buffer
			if (DataUtils.isValidDate(month, day, year))
				return new Date(month, day, year); 
			
		} catch (Exception e) { }
			
		input.nextLine();    // clean the buffer
		
		return null;
	}
	
}
