package warehouse;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Warehouse implements Serializable {
	private static final long serialVersionUID = 957905515144635530L;
	private static Warehouse warehouse;
	private static Map<Integer,Regal> regal;
	private static Map<String, Integer> partAmountMap = new HashMap<String, Integer>();

	// Warehouse ist ein Singleton
	public static Warehouse get() {
		if (warehouse == null) {
			warehouse = new Warehouse();
		}
		return warehouse;
	}

	// Regale liegen in einer HashMap
	public Warehouse() {
		regal = new HashMap<Integer,Regal>();
		// erstelle Regale
		for (int i = 0; i < Variables.REGALCOUNT; i++)
			// Regale haben eine Breite von der Breite der Fächer + den Abstand zwischen den Regalen (weiteres s. Main)
			regal.put(i, new Regal(i * Variables.COMPARTMENTDWIDTH + i * Variables.REGALDISTANCE + Variables.REGALDISTANCE));
	}

	public Map<Integer, Regal> getRegal() {
		return regal;
	}

	public static void setRegal(Map<Integer, Regal> regal) {
		Warehouse.regal = regal;
	}

	// Einlagern eines Teils
	public static String storingParts(Part part, Compartment compartment) {
		if (part.getSize() <= 0 || part.getSize() > Variables.COMPARTMENTCAPACITY)
			return "Größe muss zwischen 1 und " + Variables.COMPARTMENTCAPACITY + " GE betragen!";
		if (compartment == null)
			return "Lager voll!";
		// wenn noch Platz, dann einlagern
		if ((compartment.getCapacity() - part.getSize()) >= 0) {
			// Fahrzeug soll mit dem Teil zum Zielort fahren
			TransportVehicle.driveToCompartment(part, compartment);
			// Einlagern
			compartment.getPartList().add(part);
			// Kapazität verringern
			compartment.setCapacity(compartment.getCapacity() - part.getSize());
			// Zeile in Tabelle hinzufügen
			MainFrame.addRowMainTable(part, compartment);
			// Teil der Anzahlliste hinzufügen
			Warehouse.partCountAdd(part);	
			// Letzte Aktion, freie Fächer & freie Kapazität aktualisieren
			MainFrame.setLastActionText("Einlagern von ", part);
			MainFrame.setRestCapacityText();
			MainFrame.setRestCompartmentText();
		}
		if (part.getPartnumber() != TransferDialog.getInpPartNumber()) {
			if (TransferDialog.getInpPartNumber() <= 0)
				return "Einlagern erfolgreich\nDie Teilenummer muss größer 0 sein. Daher wurde sie auf " 
					+ part.getPartnumber() + " gesetzt.";
			return "Einlagern erfolgreich\nDie Teilenummer war bereits vergeben und wurde daher auf " 
				+ part.getPartnumber() + " gesetzt.";
		}
		return "Einlagern erfolgreich (Teilenummer: " + part.getPartnumber() + ")!";
	}

	// Auslagern eines Teils
	public static void outsourceParts(Part part) {
		for (int i = 0; i < regal.size(); i++)
			for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
				for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
					if (regal.get(i).getCompartments()[j][k].getPartList().contains(part)) {
						// Transportfahrzeug kann zum Zielort fahren
						TransportVehicle.driveToCompartment(part, regal.get(i).getCompartments()[j][k]);
						// Auslagern
						Warehouse.get().getRegal().get(i).getCompartments()[j][k].getPartList().remove(part);
						// Kapazität vergrößern
						regal.get(i).getCompartments()[j][k].setCapacity(regal.get(i).getCompartments()[j][k].getCapacity() + part.getSize());
						// Zeile aus der Tabelle entfernen
						MainFrame.removeRowMainTable(part);
						// Teil aus der Anzahlliste entfernen
						Warehouse.partCountRemove(part);
						// Letzte Aktion, freie Fächer & freie Kapazität aktualisieren
						MainFrame.setLastActionText("Auslagern von ", part);
						MainFrame.setRestCapacityText();
						MainFrame.setRestCompartmentText();
						return;
					}
	}

	// von der Datei eingelesene Daten in die Regale/Fächer einfügen
	public static void loadPartsIntoWarehouse(List<Part> loadedParts, Compartment loadedCompartment, int i, int j, int k) {
		// Teilelisten in Compartments einlagern
		regal.get(i).getCompartments()[j][k].setPartList(loadedParts);
		// Kapazität laden
		regal.get(i).getCompartments()[j][k].setCapacity(loadedCompartment.getCapacity());
		// Tabellezeilen hinzufügen
		for (Part parts : loadedParts) {
			MainFrame.addRowMainTable(parts, loadedCompartment);
			Warehouse.partCountAdd(parts);
		}

	}

	// geht die Regale durch und gibt letztendlich ein "freies" Fach in einem Regal (oder null) zurück
	public static Compartment findRegal(Part part){
		for (int i = 0; i < regal.size(); i++) {
			Compartment compartment = regal.get(i).findCompartment(part);
			if (compartment != null) 
				return compartment;
		}
		return null;
	}

	// gibt ein gefundes Teil (oder null) zurück
	public static Part findPart(Part findPart, int partid) {
		for (int i = 0; i < regal.size(); i++)
			for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
				for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
					for (Part part : Warehouse.get().getRegal().get(i).getCompartments()[j][k].getPartList()) {
						if (part.getPartnumber() == partid && partid != -1)
							return part;
						if (findPart != null && part.getDescription().equals(findPart.getDescription()))
							return part;
					}
		return null;
	}

	// findet Teile nach Name
	public static LinkedList<Part> findPartName(String name) {
		LinkedList<Part> partListName = new LinkedList<Part>();
		for (int i = 0; i < regal.size(); i++)
			for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
				for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
					for (Part parts : regal.get(i).getCompartments()[j][k].getPartList())
						if (name.equals(parts.getDescription()))
							partListName.add(parts);
		return partListName.isEmpty() ? null : partListName;
	}

	// gibt alle Teile zurück (für das komplette Leeren erforderlich)
	public static LinkedList<Part> returnAllParts() {
		LinkedList<Part> partList = new LinkedList<Part>();
		for (int i = 0; i < regal.size(); i++)
			for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
				for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
					for (Part parts : regal.get(i).getCompartments()[j][k].getPartList())
						partList.add(parts);
		return partList.isEmpty() ? null : partList;
	}

	// durch eingelagertes Teil muss eine neue Zeile bei der Anzahlanzeige hinzugefügt bzw. aktualisiert werden 
	public static void partCountAdd(Part part) {
		if (!partAmountMap.containsKey(part.getDescription())) {
			partAmountMap.put(part.getDescription(), 1);
			MainFrame.addRowPartAmountTable(part);
		} else {
			partAmountMap.put(part.getDescription(), partAmountMap.get(part.getDescription()) + 1);
			MainFrame.editRowPartAmountTable(part, partAmountMap.get(part.getDescription()));
		}
	}

	// durch ausgelagertes Teil muss eine Zeile bei der Anzahlanzeige entfernt bzw. aktualisiert werden
	public static void partCountRemove(Part part) {
		if (partAmountMap.get(part.getDescription()).equals(1)) {
			partAmountMap.remove(part.getDescription());
			MainFrame.removeRowPartAmountTable(part);
		} else {
			partAmountMap.put(part.getDescription(), partAmountMap.get(part.getDescription()) - 1);
			MainFrame.editRowPartAmountTable(part, partAmountMap.get(part.getDescription()));
		}
	}

	// zufälliges Einlagern von Teilen
	public static void fillRandom(boolean fillComplete) {
		Random zufall = new Random();
		removeAll();
		String partName = null;
		int partSize = 0;
		int countRandom = 0;
		if (fillComplete)
			// wenn das Lager komplett gefüllt werden soll, dann setze countRandom mit der Anzahl restlichen Fächer 
			countRandom = restCompartments();
		else
			// ansonsten nimm den Wert aus Variables (Anz. der anzulegenden Teile, wenn größer 0)
			if (Variables.FILLRANDOMCOUNT > 0)
				countRandom = Variables.FILLRANDOMCOUNT;
		// durchlaufe die Schleife, solange countRandom nicht 0 ist
		while (countRandom != 0) {
			switch(zufall.nextInt(6)) {
			case 0:
				partName = "Schrank";
				partSize = 10;
				break;
			case 1:
				partName = "Computer";
				partSize = 5;
				break;
			case 2:
				partName = "Buch";
				partSize = 1;
				break;
			case 3:
				partName = "Drucker";
				partSize = 4;
				break;
			case 4:
				partName = "Tisch";
				partSize = 9;
				break;
			case 5:
	        	partName = "Stuhl";
				partSize = 6;
				break;
	        default:
	        	//sollte in der Form nie auftreten
	        	continue;
			}
			Part part = new Part(partName, 0, partSize);
			Warehouse.storingParts(part, findRegal(part));
			if (fillComplete)
				if (Variables.MULTIPARTTYPPERCOMPARTMENT)
					// wenn es erlaubt ist, verschiedene Teile in Fach zu stecken, dann gehe die Schleife solange durch, bis die Restkapazität 0 ist
					countRandom = restCapacity();
				else
					// ansonsten mache solange bis alle Fächer belegt wurden
					countRandom = restCompartments();
			else
				countRandom--;
		}
	}

	// alle Teile entfernen
	public static void removeAll() {
		LinkedList<Part> searchedParts = returnAllParts();
		if (searchedParts != null)
			for (Part part : searchedParts)
				outsourceParts(part);
	}

	// alle Regale ausgewählt
	public static int restCapacity() {
		int capacity = 0;
			for (int i = 0; i < regal.size(); i++)
				for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
					for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
						capacity += regal.get(i).getCompartments()[j][k].getCapacity();		
		return capacity;
	}

	// alle Regale ausgewählt
	public static int restCompartments() {
		int compartments = 0;
		for (int i = 0; i < regal.size(); i++)
			for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
				for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
					if (!regal.get(i).getCompartments()[j][k].getPartList().isEmpty())
						compartments++;
		return compartments = Variables.COMPARTMENTSIDEBYSIDE * Variables.COMPARTMENTONTOPOFEACHOTHER * Variables.REGALCOUNT - compartments;
	}
	
	// ein Regal ausgewählt
	public static int restCapacitySingleRack(int regalNr) {
		int capacity = 0;
		if (regalNr != 0) {
			int i = regalNr - 1;
			for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
				for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
					capacity += regal.get(i).getCompartments()[j][k].getCapacity();
			return capacity;
		}
		return -1;
	}

	// ein Regal ausgewählt
	public static int restCompartmentsSingleRack(int regalNr) {
		int compartments = 0;
		if (regalNr != 0) {
			int i = regalNr - 1;
			for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
				for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
					if (!regal.get(i).getCompartments()[j][k].getPartList().isEmpty())
						compartments++;
			return compartments = Variables.COMPARTMENTSIDEBYSIDE * Variables.COMPARTMENTONTOPOFEACHOTHER - compartments;
		}
		return -1;
	}

}
