package warehouse;

import java.util.LinkedList;
import java.util.Random;

public class Warehouse {

	public static Warehouse warehouse;
	public Regal[] regale = new Regal[8];

	public static Warehouse get() {
		if (warehouse == null) {
			warehouse = new Warehouse();
		}
		return warehouse;
	}

	public Warehouse() {
		for (int i = 0; i < 8; i++) {
			regale[i] = new Regal(4 * i);
		}
	}

	// Findet ein freies Fach mit ausreichender Kapazität
	public static Compartment findCompartment(Part part) {		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++) {
					if ((Warehouse.get().regale[i].compartments[j][k].getCapacity() >= part.getSize())
							&& Warehouse.get().regale[i].compartments[j][k]	.isCompartmentFree(part,Warehouse.get().regale[i].compartments[j][k])) {
						return Warehouse.get().regale[i].compartments[j][k];
					}
				}
			}
		}
		return null;
	}

	// Lagert ein Teil ein
	public static String teilEinlagern(Part part, Compartment compartment) {
		int partSize = part.getSize();
		if (partSize <= 0)
			return "Größe muss größer null sein!";
		if (compartment == null) 
			return "Lager voll!";
		// wenn noch Platz, dann einlagern
		if ((compartment.getCapacity() - partSize) >= 0) {
			//Fahrzeug kann hier mit dem Teil zum Zielort fahren
			TransportVehicle.driveToCompartment(part, compartment);
			// einlagern
			compartment.getPartList().add(part);
			// Kapazität verringern
			compartment.setCapacity(compartment.getCapacity() - partSize);
			//Zeile hinzufügen
			MainFrame.addARow(part, compartment);
			// Letzte Aktion aktualisieren
			MainFrame.setLastActionText("Einlagern von ", part);
		}
		System.out.println(TransferDialog.getInpPartNumber());
		System.out.println(part.getPartnumber());
		if (part.getPartnumber() != TransferDialog.getInpPartNumber())
			return "Einlagern erfolgreich\nID war bereits vergeben und wurde daher auf " + part.getPartnumber() + " gesetzt.";
		return "Einlagern erfolgreich";
	}

	public void teileAnzeigen() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++) {
					for (Part parts : Warehouse.get().regale[i].compartments[j][k].partList) {
						// wenn Liste static, würde das gehen:
						// for (Part parts : Compartment.partList) {
						System.out.println(parts);
						System.out.println(i + " " + j + " " + k);

					}
				}
			}
		}
	}

	// Findet Teile mit Position nach ID
	public static Part findPartID(int id) {
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 10; j++)
				for (int k = 0; k < 10; k++)
					for (Part parts : Warehouse.get().regale[i].compartments[j][k].partList)
						if (id == parts.getPartnumber())
							return parts;
		return null;
	}

	// Findet Teile mit Position nach Name
	public static LinkedList<Part> findPartName(String name) {
		LinkedList<Part> tempList = new LinkedList<Part>();
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 10; j++)
				for (int k = 0; k < 10; k++)
					for (Part parts : Warehouse.get().regale[i].compartments[j][k].partList)
						if (name.equals(parts.getDescription()))
							tempList.add(parts);
		return tempList.isEmpty() ? null : tempList;
	}

	public static void teilAuslagern(Part part) {
		int partSize = part.getSize();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++) {
					// wenn Fach das Teil enthält
					if ((Warehouse.get().regale[i].compartments[j][k]
							.getPartList()).contains(part)) {
						// Transportfahrzeug kann zum Zielort fahren
						TransportVehicle.driveToCompartment(part,
								Warehouse.get().regale[i].compartments[j][k]);
						// auslagern
						Warehouse.get().regale[i].compartments[j][k]
								.getPartList().remove(part);
						// Kapazität vergrößern
						Warehouse.get().regale[i].compartments[j][k]
								.setCapacity(Warehouse.get().regale[i].compartments[j][k]
										.getCapacity() + partSize);
						// Zeile aus der Tabelle entfernen
						MainFrame.removeARow(part);
						// Teil aus der Liste aller eingelagerten Teile (ohne Pos.) entfernen
						Part.removePart(part);
						// Letzte Aktion aktualisieren
						MainFrame.setLastActionText("Auslagern von ", part);
						return;
					}
				}
			}
		}
	}
	
	public static void fillRandom(int fillCompleteWithThisSize) {
		Random zufall = new Random();
		String partName = null;
		int partSize = 0;
		for (int i=0; i <= 800; i++) {
			switch(zufall.nextInt(5)) {
			case 0:
				//Ein Teil der Größe 0 wird nicht eingelagert
				partName = "nur Luft";
				partSize = 0;
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
			Warehouse.teilEinlagern(part, findCompartment(part));
		}
	}

	// alle Teile mit den gleichen Namen zurückgeben
	/*
	 * public LinkedList<Part> findeTeile(Part part) { LinkedList<Part> tempList
	 * = new LinkedList<Part>(); for (int i = 0; i < regale.size(); i++)
	 * tempList.addAll(regale.get(i).findeTeile(typ)); return tempList.isEmpty()
	 * ? null: tempList; }
	 */

	// public void setCompartment(int regalnr, int x, int z, int anzahl) {
	// // regal.get(regalnr).getCompartments()[x][z]);

	// public static void setCompartment(int regalnr, int x, int z, Part part) {
	// //regal.get(regalnr).getCompartments()[x][z]);
	// //Aufruf wo ist was frei
	//
	// //System.out.println(regalnr);
	// //Part.createPartList(1, 2, 0, part);
	// }
	//
	// public static void setCompartment(String description, int partnr, int
	// amount, int size) {
	// new Part(description, 45, 1, 3);
	// System.out.println(Part.getSize());
	// System.out.println(Part.getTeilListe());
	//

	// }

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
