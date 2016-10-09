package dataManagementClasses;

import generalUtilities.DataUtils;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Attribute {
	private String name; 
	private int tIndex;         // id of the attribute's data type
	
	public Attribute(String name, int tIndex) 
	{ 
		this.name = name; 
		this.tIndex = tIndex; 
	}

	public Attribute(RandomAccessFile file) throws IOException { 
		// reads the data to form this attribute from the RAF file; 
		// beginning reading from the current file pointer location
		// assumed to be located at the byte corresponding to the
		// data type for the attribute. 
		tIndex = file.readByte(); 
		name = ""; 
		int nLength = file.readByte(); 
		
		// read the name, one character at the time
		for (int i=0; i<nLength; i++) 
			name = name + file.readChar(); 	
	}
	

	public String getName() {
		return name;
	}

	public int gettIndex() {
		return tIndex;
	}

	public void writeToFile(RandomAccessFile file) throws IOException { 
		file.writeByte((byte) tIndex);
		file.writeByte((byte) name.length()); 
		file.writeChars(name);
	}
		
	public int getDataSize() { 
		return DataUtils.getTypeSize(tIndex); 
	}
	
	public String toString() { 
		String s="(" + this.name + ", ";
		
		s = s + DataUtils.getTypeName(this.tIndex) + ")"; 
		return s; 
	}
}
