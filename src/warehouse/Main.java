package warehouse;

import java.io.*;
import java.util.LinkedList;
import java.util.Random;

public class Main {
	
	/*
	ToDo:
	- Tabellenanzeige mit konkereten Daten
	- Sortieren nach Bezeichnung / Teilenr.
	- Einlagern von Teilen
	- Daten schreiben / lesen
	- nächste freie ID suchen
	- Auslagern
	- Teile suchen
	- Standort
	
	
	ToDo für Nacharbeiten:
	- isCompartmentFree nochmals umbauen
	- ggf. Listen doch wieder static um besser damit umgehen zu können --> später
	
	*/

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File(".\\data\\DateiZumEinlesen.txt");
		OutputStream ostream = new FileOutputStream(file);
		PrintWriter writer = new PrintWriter(ostream);

		new Warehouse();
		
		//Testaufrufe zur Suche von Fächern und Teilen
		//System.out.println(new TransportVehicle().findCompartment(part));
		//System.out.println(new TransportVehicle().findPart(2));
		

		//Gibt nur ein Teil zurück ohne Ort
		//System.out.println(Part.findPart(null, 5));
		//System.out.println(Part.findPart(part, -1));

		//Zeigt alle Teile mit Postion an
		//new TransportVehicle().teileAnzeigen();
		
		new MainFrame();
		
		Part part3 = new Part("22", 4, 3);
		Part part4 = new Part("22", 5, 3);
		TransportVehicle.teilEinlagern(part3, TransportVehicle.findCompartment(part3));
		TransportVehicle.teilEinlagern(part4, TransportVehicle.findCompartment(part4));
		
		Random zufall = new Random();
		for (int a=0; a < 20; a++) {
			Part part2 = new Part("Teil " + a, 0, zufall.nextInt(10));
			TransportVehicle.teilEinlagern(part2, TransportVehicle.findCompartment(part2));
			
		}
		/*
		new TransportVehicle();
		//System.out.println(TransportVehicle.findPartName("Testteil zum Suchen"));
		LinkedList<Part> saerchedParts = TransportVehicle.findPartName("Testteil zum Suchen");
		for (Part parts : saerchedParts) {
			System.out.println(parts);
			System.out.println(TransportVehicle.teilAuslagern(TransportVehicle.findPartID(parts.getPartnumber())));
		}
*/
	}

}
