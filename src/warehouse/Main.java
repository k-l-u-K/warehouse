package warehouse;

import java.io.*;
import java.util.Random;

public class Main {
	
	/*
	ToDo:
	- Auslagern: Switch-Button einfügen, der zwischen Suche nach Teil-Nr. und Bezeichnung switcht --> entweder-oder-Suche
	- bei mehreren Teilen mit gleicher Bezeichnung -> Abfrage nach ID
	- Lageranzeige bei Auswahl eines oder mehreren Regalen
	- korrekte Regalnr.-Anzeige
	- Standort
	- Daten in Datei schreiben / lesen
	- Sortieren nach Bezeichnung / Teilenr.
	
	ToDo für Nacharbeiten:
	- sinnvoll kommentieren
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
		
		Part part3 = new Part("Ein Teil zum Suchen mit übel viel Text, damit das Suchen richtig Spaß macht und Freude aufkommt", 4, 3);
		Part part4 = new Part("Ein Teil zum Suchen mit übel viel Text, damit das Suchen richtig Spaß macht und Freude aufkommt", 5, 3);
		TransportVehicle.teilEinlagern(part3, TransportVehicle.findCompartment(part3));
		TransportVehicle.teilEinlagern(part4, TransportVehicle.findCompartment(part4));
		
		Random zufall = new Random();
		for (int a=0; a < 20; a++) {
			Part part2 = new Part("Teil " + a, 0, zufall.nextInt(10));
			TransportVehicle.teilEinlagern(part2, TransportVehicle.findCompartment(part2));
			
		}

	}

}
