package warehouse;

import java.io.*;
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
	
	- ggf. Listen doch wieder static um besser damit umgehen zu können --> später
	*/

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File(".\\data\\DateiZumEinlesen.txt");
		OutputStream ostream = new FileOutputStream(file);
		PrintWriter writer = new PrintWriter(ostream);

		new Warehouse();
		
		Part part = new Part("Hallo",2,3);
		//new TransportVehicle().teilEinlagern(part);
		
		Random zufall = new Random();
		for (int a=0; a < 20; a++) {
		//	new TransportVehicle().teilEinlagern(new Part("Teil " + a, a, zufall.nextInt(10)));
		}

		//Testaufrufe zur Suche von Fächern und Teilen
		//System.out.println(new TransportVehicle().findCompartment(part));
		//System.out.println(new TransportVehicle().findPart(2));
		

		//Gibt nur ein Teil zurück ohne Ort
		//System.out.println(Part.findPart(null, 5));
		//System.out.println(Part.findPart(part, -1));
	
		//Zeigt alle Teile mit Postion an
		new TransportVehicle().teileAnzeigen();
	
		new MainFrame();

	}

}
