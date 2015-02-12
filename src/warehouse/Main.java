package warehouse;

import java.io.*;
import java.util.Random;

public class Main {
	
	/*
	ToDo:
	- Infofeld mit Gesamtanzahl eines Teils mit gleichem Namen
	- freien Platz (Kapazität) und Anzahl ausgeben(Anzahl der leeren Fächer vllt mit der Fach suche machen und ein fach für ein objekt der größe 10 suchen? 
		Flo: Die Fachsuche bricht aber ab, wenn sie ein Fach gefunden hat, aber die Schleife ansich müsste man verwenden können ju.)
	- sinnvolles zufälligen
	- korrekte Regalnr.-/Fach-Anzeige
	- Hinweis auf automatische Setzung der Teile-ID
	- Daten in Datei schreiben / lesen
	
	ToDo für Nacharbeiten:
	- Fenster sinnvoll aufteilen
	- Variablen einheitlich in englischer Sprache 
	- sinnvoll kommentieren
	- isCompartmentFree nochmals umbauen (zumindest korrigiert schonmal)
	- Switch-Button beim Auslagern einfügen
	- Sortieren entscheidet nicht gleichwertig bei Groß/Kleinbuchstaben
	- Exceptionsbehandlung ggf. überarbeiten
	
	*/

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File(".\\data\\DateiZumEinlesen.txt");
		OutputStream ostream = new FileOutputStream(file);
		PrintWriter writer = new PrintWriter(ostream);

		new Warehouse();
		new TransportVehicle(0, 0, 0);
		
		new MainFrame();
		
		Random zufall = new Random();
		for (int a=0; a < 20; a++) {
			Part part2 = new Part("Teil " + a, 0, zufall.nextInt(10)+1);
			Warehouse.teilEinlagern(part2, Warehouse.findCompartment(part2));
		}
		
		// Lager automatisch vollständig befüllen
		// for (int a=0; a < 801; a++) {
		//		Part part2 = new Part("Teil " + a, 0, zufall.nextInt(10)+1);
		//		System.out.println(Warehouse.teilEinlagern(part2, Warehouse.findCompartment(part2)));
		// }
	}

}
