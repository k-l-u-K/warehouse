package warehouse;

import java.io.*;

public class Main {

	/*
	 * ToDo:
	 * Infofeld mit Gesamtanzahl eines Teils mit gleichem Namen
	 * freien Platz (Kapazität) und Anzahl ausgeben (Anzahl der leeren Fächer vllt mit der Fach suche machen und 
	 * 	ein Fach für ein Objekt der größe 10 suchen?
	 * 		Flo: Die Fachsuche bricht aber ab, wenn sie ein Fach gefunden hat, 
	 * 		aber die Schleife ansich müsste man verwenden können ju.)
	 * korrekte Regalnr.-/Fach-Anzeige
	 * Beim zufälligen Befüllen werden IDs übersprungen, die man nachträglich auch nicht nutzen kann
	 * Daten in Datei schreiben / lesen

	 * ToDo für Nacharbeiten:
	 * alle Variablen auf Korrektheit von private / public kontrollieren
	 * Fenster sinnvoll aufteilen
	 * Variablen einheitlich in englischer Sprache
	 * sinnvoll kommentieren
	 * isCompartmentFree nochmals umbauen (zumindest korrigiert schonmal)
	 * Switch-Button beim Auslagern einfügen
	 * Sortieren entscheidet nicht gleichwertig bei Groß/Kleinbuchstaben
	 * Exceptionsbehandlung ggf. überarbeiten
	 */

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File(".\\data\\DateiZumEinlesen.txt");
		OutputStream ostream = new FileOutputStream(file);
		PrintWriter writer = new PrintWriter(ostream);

		//new Warehouse();
		new TransportVehicle(0, 0, 0);
		
		new MainFrame();
		//Beim Aufruf des zufälligen Einordnens kann als Parameter eine Zahl (sinnvoll größer 5 ;-)) übergeben werden
		//damit jedes Fach im Lager mit einem Teil befüllt wird -> Lager voll kann getestet werden
		//bei 0 wird eine definierte Größe für jedes Teil eingetragen -> das Lager wird so nicht komplett gefüllt
		Warehouse.fillRandom(0);
	}

}
