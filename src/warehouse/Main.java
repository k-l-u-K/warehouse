package warehouse;

import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File(".\\data\\DateiZumEinlesen.txt");
		OutputStream ostream = new FileOutputStream(file);
		PrintWriter writer = new PrintWriter(ostream);//change
		Random random = new Random();
		Scanner reader = new Scanner(System.in);
		System.out.print("Eingabe > ");

	}
	

}
