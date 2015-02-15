package warehouse;

public class Main {

	/*
	 * ToDo für Nacharbeiten:
	 * Buttonverteilung & Layout
	 * sinnvoll kommentieren
	 */

	public static void main(String[] args) {
		new TransportVehicle(0, 0, 0);
		Warehouse.get();
		new MainFrame();
	}

}
