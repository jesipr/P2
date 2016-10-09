package interfaces;

import java.util.Scanner;

/**
 * Specializes in reading a particular data type from a specified 
 * location in an array of type byte[]
 * @author pedroirivera-vega
 *
 */
public interface DataReader {
	Object readDataFromArrayOfBytes(byte[] a, int starting);
	Object readDataFromInputScanner(Scanner input); 
}
