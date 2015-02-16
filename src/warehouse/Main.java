package warehouse;

public class Main {

	/*
	 * ToDo für Nacharbeiten:
	 * Buttonverteilung & Layout
	 * beim Auslagern: wenn keine Teilenummer vorhanden -> Info an Benutzer 
	 * sinnvoll kommentieren
	 * Kurze Entwurfsbeschreibung (wenige Seiten)
	 * Anleitung
	 * Testbeispiele
	 */

	public static void main(String[] args) {
		new TransportVehicle(0, 0, 0);
		Warehouse.get();
		new MainFrame();
	}

}
