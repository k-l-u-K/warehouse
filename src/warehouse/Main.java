package warehouse;

import java.io.*;

public class Main {
	
	

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File(".\\data\\DateiZumEinlesen.txt");
		OutputStream ostream = new FileOutputStream(file);
		PrintWriter writer = new PrintWriter(ostream);

		new Warehouse();
		new MainFrame();
		
//		new TransportVehicle().teilEinlagern(new Part("Bezeichnung",2,3));
//		new TransportVehicle().teileAnzeigen();

		//		Part part = new Part("Hallo",2,3,4);
//		Part part2 = new Part("Hall2fo",22,31,44);
//
//		warenhaus.regaleFlo[0].getCompartments()[2][0].addPart(part);
//		warenhaus.regaleFlo[0].getCompartments()[2][0].addPart(part2);
		
	}

}
