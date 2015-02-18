package warehouse;

import java.io.Serializable;

// Teile haben eine Beschreibung, eine Nummer und eine Größe
public class Part implements Serializable {
	private static final long serialVersionUID = -7831438907397267073L;

	private String description;
	private int partnumber;
	private int size;

	public Part(String description, int partnumber, int size) {
		this.description = description;
		// Überprüfen, ob Teilenummer eindeutig ist
		// Teilenummer darf bei der Eingabe nicht <= 0 sein und die Teilenummer darf nicht bereits vergeben sein
		if (partnumber <= 0 || Warehouse.findPart(null, partnumber) != null)
			partnumber = getFreePartNumber();
		this.partnumber = partnumber;
		this.size = size;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPartnumber() {
		return partnumber;
	}

	public void setPartnumber(int partnumber) {
		this.partnumber = partnumber;
	}

	public int getSize() {
		return size;
	}

	//gibt eine freie Teilenummer zurück
	public static int getFreePartNumber() {
		// Teilenummer sollen mit 1 beginnen
		return getFreePartNumber(1);
	}

	private static int getFreePartNumber(int newPartNumber) {
		// Wenn die Teilenummer noch nicht vergeben, dann gib diese zurück
		if (Warehouse.findPart(null, newPartNumber) == null)
			return newPartNumber;
		// ansonsten rufe dich selber auf mit der nächsthöheren Teilenummer
		else
			return getFreePartNumber(++newPartNumber);
	}

	@Override
	public String toString() {
		return "Bezeichnung: " + description + ", Teilenummer: "+ partnumber + ", Größe in GE: " + size;
	}

}
