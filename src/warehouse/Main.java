package warehouse;

public class Main {

	/*
	 * ToDo:
	 * Unterscheiden von Fehlern beim Laden aus der Datei

	 * ToDo für Nacharbeiten:
	 * alle Variablen auf Korrektheit von private / public kontrollieren
	 * Buttonverteilung (Layout)
	 * Variablen einheitlich in englischer Sprache
	 * sinnvoll kommentieren
	 * Switch-Button beim Auslagern einfügen
	 * Exceptionsbehandlung ggf. überarbeiten
	 * (ggf. Fehlerbehandlungen einbauen beim Einlesen der Daten)?
	 */

	public static void main(String[] args) {
		new TransportVehicle(0, 0, 0);
		Warehouse.get();
		new MainFrame();
	}

}
