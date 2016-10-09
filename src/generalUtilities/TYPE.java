package generalUtilities;

import interfaces.DataReader;
import interfaces.DataWriter;

public class TYPE {
	private String name;
	private int size; // number of bytes
	private DataReader dataReader; // data reader for this data type
	private DataWriter dataWriter; // data writer for this data type

	public TYPE(String name, int size, DataReader dr, DataWriter dw) {
		this.name = name;
		this.size = size;
		this.dataReader = dr;
		this.dataWriter = dw;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public DataReader getDataReader() {
		return dataReader;
	}

	public void setDataReader(DataReader dr) {
		this.dataReader = dr;
	}

	public DataWriter getDataWriter() {
		return dataWriter;
	}

	public void setDataWriter(DataWriter dw) {
		this.dataWriter = dw;
	}

}