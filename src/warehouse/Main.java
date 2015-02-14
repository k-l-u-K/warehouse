package warehouse;

public class Main {

	/*
	 * ToDo:
	 * Hinweis, dass Speicherung erfolgreich/fehlgeschlagen als Dialogfenster
	 * freien Platz (Kapazität) und Anzahl ausgeben (Anzahl der leeren Fächer vllt mit der Fach suche machen und 
	 * 	ein Fach für ein Objekt der größe 10 suchen?
	 * 		Flo: Die Fachsuche bricht aber ab, wenn sie ein Fach gefunden hat, 
	 * 		aber die Schleife ansich müsste man verwenden können ju.)

	 * ToDo für Nacharbeiten:
	 * alle Variablen auf Korrektheit von private / public kontrollieren
	 * Fenster sinnvoll aufteilen
	 * Variablen einheitlich in englischer Sprache
	 * sinnvoll kommentieren
	 * isCompartmentFree nochmals umbauen (zumindest korrigiert schonmal)
	 * Switch-Button beim Auslagern einfügen
	 * Sortieren entscheidet nicht gleichwertig bei Groß/Kleinbuchstaben
	 * Exceptionsbehandlung ggf. überarbeiten
	 * ggf. Fehlerbehandlungen einbauen beim Einlesen der Daten
	 */

	public static void main(String[] args) {
		new TransportVehicle(0, 0, 0);
		new MainFrame();
		//Beim Aufruf des zufälligen Einordnens kann als Parameter eine Zahl (sinnvoll größer 5 ;-)) übergeben werden
		//damit jedes Fach im Lager mit einem Teil befüllt wird -> Lager voll kann getestet werden
		//bei 0 wird eine definierte Größe für jedes Teil eingetragen -> das Lager wird so nicht komplett gefüllt
		//Warehouse.fillRandom(9);

		MainFrame.loadFile();
	}

}
