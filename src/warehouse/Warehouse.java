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
	
	public static Warehouse get() {
		if (warehouse == null) {
			warehouse = new Warehouse();
		}
		return warehouse;
	}

	public Warehouse() {
		regal = new HashMap<Integer,Regal>();
		// erstelle Regale
		for (int i = 0; i < Variables.REGALCOUNT; i++)
			// Regale haben eine Breite von der Breite der Fächer + den Abstand zwischen den Regalen (weiteres s. unten)
			regal.put(i, new Regal(i * Variables.COMPARTMENTDWIDTH + i * Variables.REGALDISTANCE + Variables.REGALDISTANCE));
	}

	public Map<Integer, Regal> getRegal() {
		return regal;
	}

	public static void setRegal(Map<Integer, Regal> regal) {
		Warehouse.regal = regal;
	}

	// Lagert ein Teil ein
	public static String storingParts(Part part, Compartment compartment) {
		if (part.getSize() <= 0 || part.getSize() > Variables.COMPARTMENTCAPACITY)
			return "Größe muss zwischen 1 und " + Variables.COMPARTMENTCAPACITY + " GE betragen!";
		if (compartment == null)
			return "Lager voll!";
		// wenn noch Platz, dann einlagern
		if ((compartment.getCapacity() - part.getSize()) >= 0) {
			//Fahrzeug kann hier mit dem Teil zum Zielort fahren
			TransportVehicle.driveToCompartment(part, compartment);
			// einlagern
			compartment.getPartList().add(part);
			// Kapazität verringern
			compartment.setCapacity(compartment.getCapacity() - part.getSize());
			//Zeile hinzufügen
			MainFrame.addRowMainTable(part, compartment);
			//Teil der Anzahlliste hinzufügen
			Warehouse.partCountAdd(part);	
			// Letzte Aktion aktualisieren
			MainFrame.setLastActionText("Einlagern von ", part);
			MainFrame.setRestCapacityText();
			MainFrame.setRestCompartmentText();
		}
		if (part.getPartnumber() != TransferDialog.getInpPartNumber()) {
			if (TransferDialog.getInpPartNumber() <= 0)
				return "Einlagern erfolgreich\nDie ID muss größer 0 sein. Daher wurde sie auf " 
					+ part.getPartnumber() + " gesetzt.";
			return "Einlagern erfolgreich\nID war bereits vergeben und wurde daher auf " 
				+ part.getPartnumber() + " gesetzt.";
		}
		return "Einlagern erfolgreich";
	}
	
	public static void outsourceParts(Part part) {
		for (int i = 0; i < regal.size(); i++)
			for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
				for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
					if (regal.get(i).getCompartments()[j][k].getPartList().contains(part)) {
						// Transportfahrzeug kann zum Zielort fahren
						TransportVehicle.driveToCompartment(part, regal.get(i).getCompartments()[j][k]);
						// auslagern
						Warehouse.get().getRegal().get(i).getCompartments()[j][k].getPartList().remove(part);
						// Kapazität vergrößern
						regal.get(i).getCompartments()[j][k].setCapacity(regal.get(i).getCompartments()[j][k].getCapacity() + part.getSize());
						// Zeile aus der Tabelle entfernen
						MainFrame.removeRowMainTable(part);
						// Teil aus der Anzahlliste entfernen
						Warehouse.partCountRemove(part);
						// Letzte Aktion aktualisieren
						MainFrame.setLastActionText("Auslagern von ", part);
						MainFrame.setRestCapacityText();
						MainFrame.setRestCompartmentText();
						return;
					}
	}

	public static void loadPartsIntoWarehouse(List<Part> part, Compartment loadedCompartment, int i, int j, int k) {
		// Teilelisten in Compartments einlagern
		regal.get(i).getCompartments()[j][k].setPartList(part);
		// Kapazität laden
		regal.get(i).getCompartments()[j][k].setCapacity(loadedCompartment.getCapacity());
		// Tabellezeilen hinzufügen
		for (Part parts : part) {
			MainFrame.addRowMainTable(parts, loadedCompartment);
			Warehouse.partCountAdd(parts);
		}

	}
	
	public static Compartment findRegal(Part part){
		for (int i = 0; i < regal.size(); i++) {
			Compartment temp = regal.get(i).findCompartment(part);
			if (temp != null) 
				return temp;
		}
		return null;
	}

	// Findet Teile mit Position nach ID
	public static Part findPartID(int id) {
		for (int i = 0; i < regal.size(); i++)
			for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
				for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
					for (Part parts : regal.get(i).getCompartments()[j][k].getPartList())
						if (id == parts.getPartnumber())
							return parts;
		return null;
	}

	// Findet Teile mit Position nach Name
	public static LinkedList<Part> findPartName(String name) {
		LinkedList<Part> tempList = new LinkedList<Part>();
		for (int i = 0; i < regal.size(); i++)
			for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
				for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
					for (Part parts : regal.get(i).getCompartments()[j][k].getPartList())
						if (name.equals(parts.getDescription()))
							tempList.add(parts);
		return tempList.isEmpty() ? null : tempList;
	}
	
	// Findet Teile mit Position nach Name
	public static LinkedList<Part> returnAllParts() {
		LinkedList<Part> tempList = new LinkedList<Part>();
		for (int i = 0; i < regal.size(); i++)
			for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
				for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
					for (Part parts : regal.get(i).getCompartments()[j][k].getPartList())
						tempList.add(parts);
		return tempList.isEmpty() ? null : tempList;
	}

	public static void partCountAdd(Part part) {
		if (!partAmountMap.containsKey(part.getDescription())) {
			partAmountMap.put(part.getDescription(), 1);
			MainFrame.addRowPartAmountTable(part);
		} else {
			partAmountMap.put(part.getDescription(), partAmountMap.get(part.getDescription()) + 1);
			MainFrame.editRowPartAmountTable(part, partAmountMap.get(part.getDescription()));
		}
	}

	public static void partCountRemove(Part part) {
		if (partAmountMap.get(part.getDescription()).equals(1)) {
			partAmountMap.remove(part.getDescription());
			MainFrame.removeRowPartAmountTable(part);
		} else {
			partAmountMap.put(part.getDescription(), partAmountMap.get(part.getDescription()) - 1);
			MainFrame.editRowPartAmountTable(part, partAmountMap.get(part.getDescription()));
		}
	}

	public static void fillRandom(boolean fillComplete) {
		Random zufall = new Random();
		removeAll();
		String partName = null;
		int partSize = 0;
		int countRandom = 0;
		if (fillComplete)
			countRandom = restCompartments();
		else
			if (Variables.FILLRANDOMCOUNT > 0)
				countRandom = Variables.FILLRANDOMCOUNT;
		// die Abfrage nach Variables.FILLEVERYCOMPARTMENT könnte man vorher weglassen, wenn man do while benutzt
		// allerdings wird dann immer mind. 1 Teil angelegt
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
	        	//sollte nie auftreten
	        	continue;
			}
			if (fillComplete)
				for (int j=partSize; j<=5; j++) {
					Part part = new Part(partName, 0, partSize);
					Warehouse.storingParts(part, findRegal(part));
				}
			Part part = new Part(partName, 0, partSize);
			Warehouse.storingParts(part, findRegal(part));
			if (fillComplete)
				if (Variables.MULTIPARTTYPPERCOMPARTMENT)
					countRandom = restCapacity();
				else
					countRandom = restCompartments();
			else
				countRandom--;
		}
	}
	
	public static void removeAll() {
		LinkedList<Part> searchedParts = returnAllParts();
		if (searchedParts != null)
			for (Part part : searchedParts)
				outsourceParts(part);
	}
	
	public static Part findPart(Part part, int partid) {
		for (int i = 0; i < regal.size(); i++)
			for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
				for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
					for (Part tempTeil : Warehouse.get().getRegal().get(i).getCompartments()[j][k].getPartList()) {
						if (tempTeil.getPartnumber() == partid && partid != -1)
							return tempTeil;
						if (part != null && tempTeil.getDescription().equals(part.getDescription()))
							return tempTeil;
					}
		return null;
	}

	// alle Regale ausgewählt
	public static int restCapacity() {
		int temp = 0;
			for (int i = 0; i < regal.size(); i++)
				for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
					for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
						temp += regal.get(i).getCompartments()[j][k].getCapacity();		
		return temp;
	}
	
	public static int restCompartments() {
		int temp = 0;
		for (int i = 0; i < regal.size(); i++)
			for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
				for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
					if (!regal.get(i).getCompartments()[j][k].getPartList().isEmpty())
						temp++;
		return temp = Variables.COMPARTMENTSIDEBYSIDE * Variables.COMPARTMENTONTOPOFEACHOTHER * Variables.REGALCOUNT - temp;
	}
	
	// ein Regal ausgewählt
	public static int restCapacitySingleRack(int regalNr) {
		int temp = 0;
		if (regalNr != 0) {
			int i = regalNr - 1;
			for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
				for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
					temp += regal.get(i).getCompartments()[j][k].getCapacity();
			return temp;
		}
		return -1;
	}

	public static int restCompartmentsSingleRack(int regalNr) {
		int temp = 0;
		if (regalNr != 0) {
			int i = regalNr - 1;
			for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
				for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++)
					if (!regal.get(i).getCompartments()[j][k].getPartList().isEmpty())
						temp++;
			return temp = Variables.COMPARTMENTSIDEBYSIDE * Variables.COMPARTMENTONTOPOFEACHOTHER - temp;
		}
		return -1;
	}	

	/*
	 | 
	 |2m
	 |	 2m	 	 2m_	2m	 2m_
	 -----------|	|-------|	|----
	 			|	|		|	|
				|	|		|	|
				|	|		|	|
				|	|		|	|
				|	|		|	|
				|	|		|	|
				|	|		|	|
				|	|		|	|
				|___|		|___|
	erstes Regal beginnt bei:
	x=2
	y=2
	z=0
	
	zweites Regal:
	x=2
	y=6 (2m Fahrweg vor dem ersten Regal + Fachbreite des 1. Regals + Fahrweg vor dem zweiten Regal)
	z=0
		x
		|
		|
		|---------y
		z ist die Höhe
	 */
}
