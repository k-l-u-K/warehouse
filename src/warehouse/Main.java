package warehouse;

public class Main {

	/*
	 * ToDo für Nacharbeiten:
	 * alle Variablen auf Korrektheit von private / public kontrollieren
	 * Buttonverteilung (Layout)
	 * sinnvoll kommentieren
	 */

	public static void main(String[] args) {
		new TransportVehicle(0, 0, 0);
		Warehouse.get();
		new MainFrame();
	}

}
