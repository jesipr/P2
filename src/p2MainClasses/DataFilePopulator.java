package p2MainClasses;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class DataFilePopulator {

	public static void main(String[] args) {
		DataFilePopulator d = new DataFilePopulator();
System.out.println("Finnished");
	}

	RandomAccessFile raf;

	public DataFilePopulator() {

		try {
			raf = new RandomAccessFile(System.getProperty("user.dir") + "/inputData/rand.txt", "rw");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("File created");
		System.out.println("Enter Name for column, -1 to finish");
		Scanner s = new Scanner(System.in);
		String st = s.next();

		int m = 0;
		while (m > -1) {
			try {
				if (Integer.parseInt(st) < 0) {
					m = Integer.parseInt(st);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			
			
		}

		s.close();
	}

}
