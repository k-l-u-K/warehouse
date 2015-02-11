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
		new TransportVehicle(0, 0, 0);
		
		//Zeigt alle Teile mit Postion an
		//new TransportVehicle().teileAnzeigen();
		
		new MainFrame();
		
		Random zufall = new Random();
		for (int a=0; a < 20; a++) {
			Part part2 = new Part("Teil " + a, 0, zufall.nextInt(10));
			Warehouse.teilEinlagern(part2, Warehouse.findCompartment(part2));
		}

		Part part = new Part("Das Fahrzeug lagert dieses Teil ein", 4, 3);
		Warehouse.teilEinlagern(part, Warehouse.findCompartment(part));

	}

}
