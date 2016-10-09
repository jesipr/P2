package p2MainClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import dataManagementClasses.TableSchema;

public class DataFilePopulator {

	public static void main(String[] args) {
		if (args.length!=1){ 
			System.out.println("Illegal Argument. Try again");
			System.exit(0);
		}
		
		try {
			RandomAccessFile raf = new RandomAccessFile(new File(args[0]), "rw");
			System.out.println("Random Access File created: " + raf.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Scanner sc = new Scanner(System.in);
		//To this point we have verified arguments and created a Random Access File
		
		System.out.println("Enter enter first attribute");

	}

}
