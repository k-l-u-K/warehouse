package warehouse;

import java.io.*;
import java.util.Random;

public class Main {
	
	

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File(".\\data\\DateiZumEinlesen.txt");
		OutputStream ostream = new FileOutputStream(file);
		PrintWriter writer = new PrintWriter(ostream);

		new Warehouse();
		
		Part part = new Part("Hallo",2,3);
		Random zufall = new Random();
		for (int a=0; a < 802; a++) {
			new TransportVehicle().teilEinlagern(new Part("Teil " + a, a, 5));
		}
		//zufall.nextInt(10)
		System.out.println(new TransportVehicle().findCompartment(part));
		
		new MainFrame();
		
//		new TransportVehicle().teilEinlagern(new Part("Bezeichnung",2,3));
//		new TransportVehicle().teileAnzeigen();

		//		Part part = new Part("Hallo",2,3,4);
//		Part part2 = new Part("Hall2fo",22,31,44);
//
//		warenhaus.regaleFlo[0].getCompartments()[2][0].addPart(part);
//		warenhaus.regaleFlo[0].getCompartments()[2][0].addPart(part2);
		
	}

	private static void If(boolean b) {
		// TODO Auto-generated method stub
		
	}

}
