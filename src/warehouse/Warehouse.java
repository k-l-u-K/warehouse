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
		// erstelle Regale, momentan von Regal 0 bis Regal 7
		for (int i = 0; i < 8; i++) {
			regal.put(i, new Regal(i * 4));
		}
	}


	// Lagert ein Teil ein
	public static String teilEinlagern(Part part, Compartment compartment) {
		if (part.getSize() <= 0 || part.getSize() > 10)
			return "Größe muss zwischen 1 und 10 GE betragen!";
		if (compartment == null) 
			return "Lager voll!";
		// wenn noch Platz, dann einlagern
		if ((compartment.getCapacity() - part.getSize()) >= 0) {
			//Fahrzeug kann hier mit dem Teil zum Zielort fahren
			TransportVehicle.driveToCompartment(part, compartment);
			// einlagern
			Part.setNewPart(part);
			// Kapazität verringern
			compartment.setCapacity(compartment.getCapacity() - part.getSize());
			// Teil der Warenliste hinzufügen
			compartment.getPartList().add(part);
			//Zeile hinzufügen
			MainFrame.addARow(part, compartment);
			//Teil der Anzahlliste hinzufügen
			Warehouse.partCountAdd(part);	
			// Letzte Aktion aktualisieren
			MainFrame.setLastActionText("Einlagern von ", part);
		}
		if (part.getPartnumber() != TransferDialog.getInpPartNumber())
			return "Einlagern erfolgreich\nID war bereits vergeben oder ungültig und wurde daher auf " 
				+ part.getPartnumber() + " gesetzt.";
		return "Einlagern erfolgreich";
	}
	
	public static Compartment findPlace(Part part){
		for (int i = 0; i < 8; i++) {
			Compartment temp = regal.get(i).findCompartment(part);
			if (temp != null)
				return temp;
		}
		return null;
	}

	public static void teileAnzeigen() {
		for (int i = 0; i < regal.size(); i++)
			for (int j = 0; j < 10; j++)
				for (int k = 0; k < 10; k++)
					for (Part parts : regal.get(i).getCompartments()[j][k].getPartList()) {
						System.out.println(parts);
						System.out.println(i + " " + j + " " + k);
					}
	}

	// Findet Teile mit Position nach ID
	public static Part findPartID(int id) {
		for (int i = 0; i < regal.size(); i++)
			for (int j = 0; j < 10; j++)
				for (int k = 0; k < 10; k++)
					for (Part parts : regal.get(i).getCompartments()[j][k].getPartList())
						if (id == parts.getPartnumber())
							return parts;
		return null;
	}

	// Findet Teile mit Position nach Name
	public static LinkedList<Part> findPartName(String name) {
		LinkedList<Part> tempList = new LinkedList<Part>();
		for (int i = 0; i < regal.size(); i++)
			for (int j = 0; j < 10; j++)
				for (int k = 0; k < 10; k++)
					for (Part parts : regal.get(i).getCompartments()[j][k].getPartList())
						if (name.equals(parts.getDescription()))
							tempList.add(parts);
		return tempList.isEmpty() ? null : tempList;
	}
	
	// Findet Teile mit Position nach Name
	public static LinkedList<Part> returnAllParts() {
		LinkedList<Part> tempList = new LinkedList<Part>();
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 10; j++)
				for (int k = 0; k < 10; k++)
					for (Part parts : regal.get(i).getCompartments()[j][k].getPartList())
						tempList.add(parts);
		return tempList.isEmpty() ? null : tempList;
	}

	public static void teilAuslagern(Part part) {
		for (int i = 0; i < regal.size(); i++)
			for (int j = 0; j < 10; j++)
				for (int k = 0; k < 10; k++)
					if (regal.get(i).getCompartments()[j][k].getPartList().contains(part)) {
						// Transportfahrzeug kann zum Zielort fahren
						TransportVehicle.driveToCompartment(part, regal.get(i).getCompartments()[j][k]);
						// auslagern
						Part.removePart(part);
						// Kapazität vergrößern
						regal.get(i).getCompartments()[j][k].setCapacity(regal.get(i).getCompartments()[j][k].getCapacity() + part.getSize());
						// Zeile aus der Tabelle entfernen
						MainFrame.removeARow(part);
						// Teil aus der Anzahlliste entfernen
						Warehouse.partCountRemove(part);
						// Letzte Aktion aktualisieren
						MainFrame.setLastActionText("Auslagern von ", part);
						return;
					}
	}
	
	public static void partCountAdd(Part part) {		
		if (!partAmountMap.containsKey(part.getDescription())) {
			partAmountMap.put(part.getDescription(), 1);
			MainFrame.addARowNewPartDiscription(part);
		} else {
			partAmountMap.put(part.getDescription(), partAmountMap.get(part.getDescription()) + 1);
			MainFrame.editRowPartDis(part, partAmountMap.get(part.getDescription()));
		}		
	}
	
	public static void partCountRemove(Part part) {
		if (partAmountMap.get(part.getDescription()).equals(1)) {
			partAmountMap.remove(part.getDescription());
			MainFrame.removeRowPartDis(part);
		} else {
			partAmountMap.put(part.getDescription(), partAmountMap.get(part.getDescription()) - 1);
			MainFrame.editRowPartDis(part, partAmountMap.get(part.getDescription()));
		}
	}
	
	public static void loadPartsIntoWarehouse(List<Part> part, Compartment loadedCompartment, int i, int j, int k) {
		Compartment compartment = loadedCompartment;
		// einlagern
		regal.get(i).getCompartments()[j][k].setPartList(part);
				
		System.out.println(loadedCompartment.getPartList());
		// Kapazität verringern
		compartment.setCapacity(compartment.getCapacity());
		//Zeile hinzufügen
		for (Part parts : part) {
			MainFrame.addARow(parts, compartment);
			Warehouse.partCountAdd(parts);
		}
		//Teil der Anzahlliste hinzufügen
		// Letzte Aktion aktualisieren
		//MainFrame.setLastActionText("Einlagern von ", part);
	}

	public static Map<Integer, Regal> getRegal() {
		return regal;
	}

	public static void setRegal(Map<Integer, Regal> regal) {
		Warehouse.regal = regal;
	}

	public static void fillRandom(int fillCompleteWithThisSize) {
		Random zufall = new Random();
		removeAll();
		String partName = null;
		int partSize = 0;
		for (int i=0; i <= 800; i++) {
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
			if (fillCompleteWithThisSize != 0)
				partSize = fillCompleteWithThisSize;
			Part part = new Part(partName, 0, partSize);
			Warehouse.teilEinlagern(part, findPlace(part));
		}
	}
	
	public static void removeAll() {
		LinkedList<Part> searchedParts = returnAllParts();
		if (searchedParts != null)
			for (Part part : searchedParts)
				teilAuslagern(part);
	}


	/*
	 | 
	 |			 ___		 ___
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
	y=4
	z=0
	
	x = 2 + (i * 2)
	y = 2 + (i * 2)

		x
		|
		|
		|---------y
		z ist die Höhe
	 */
}
