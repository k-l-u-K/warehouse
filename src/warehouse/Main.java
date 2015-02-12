package warehouse;

import java.io.*;
import java.util.Random;

public class Main {
	
	/*
	ToDo:
	- Infofeld mit Gesamtanzahl eines Teils mit gleichem Namen
	- freien Platz (Kapazität) und Anzahl ausgeben(Anzahl der leeren Fächer vllt mit der Fach suche machen und ein fach für ein objekt der größe 10 suchen?)
	- sinnvolles zufälligen
	- korrekte Regalnr.-/Fach-Anzeige
	- letzte Aktion einfügen (welches Teil wurde wo ein-/ausgelagert)
	- Daten in Datei schreiben / lesen
	- Exceptions sinnvoll abfangen (volles Lager, ungültige Eingaben beim Ein-/Auslagern)
	
	ToDo für Nacharbeiten:
	- Fenster sinnvoll aufteilen
	- Dialog schließen nach Ausführung einer Aktion
	- Variablen einheitlich in englischer Sprache 
	- sinnvoll kommentieren
	- isCompartmentFree nochmals umbauen (zumindest korrigiert schonmal)
	- Switch-Button beim Auslagern einfügen
	- Sortieren entscheidet gleichwertig bei Groß/Kleinbuchstaben
	
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
			Part part2 = new Part("Teil " + a, 0, zufall.nextInt(10)+1);
			Warehouse.teilEinlagern(part2, Warehouse.findCompartment(part2));
		}
		
		Part part = new Part("Das Fahrzeug lagert dieses Teil ein", 4, 3);
		Warehouse.teilEinlagern(part, Warehouse.findCompartment(part));

	}

}
