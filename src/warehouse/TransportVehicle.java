package warehouse;

import java.util.LinkedList;

public class TransportVehicle {

	// Findet ein freies Fach mit ausreichender Kapazität
	public static Compartment findCompartment(Part part) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++) {
					if (Warehouse.get().regale[i].compartments[j][k].getCapacity() >= part.getSize()
						&& Compartment.isCompartmentFree(part)) {
							return Warehouse.get().regale[i].compartments[j][k];
					}
				}
			}
		}
		System.out.println("Kein Fach gefunden! Error!");
		return null;
	}

	// Lagert ein Teil ein
	public static void teilEinlagern(Part part, Compartment compartment) {
		int partSize = part.getSize();
		// wenn noch Platz, dann einlagern
		if ((compartment.getCapacity() - partSize) >= 0) {
			// einlagern
			compartment.getPartList().add(part);
			// Kapazität verringern
			compartment.setCapacity(compartment.getCapacity() - partSize);
			//Zeile hinzufügen
			MainFrame.addARow(part, compartment);
		}
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
		return tempList.isEmpty() ? null: tempList;
	}

	public static String teilAuslagern(Part part) {
		int partSize = part.getSize();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++) {
					// wenn Fach das Teil enthält
					if ((Warehouse.get().regale[i].compartments[j][k].getPartList()).contains(part)) {
						// auslagern
						Warehouse.get().regale[i].compartments[j][k].getPartList().remove(part);
						// Kapazität vergrößern
						Warehouse.get().regale[i].compartments[j][k].setCapacity(Warehouse.get().regale[i].compartments[j][k]
										.getCapacity() + partSize);
						
						MainFrame.removeARow(part,Warehouse.get().regale[i].compartments[j][k]);
						
						return "Auslagern erfolgreich!";
						// Schleife anders abbrechen
					}
				}
			}
		}
		return "Auslagern fehlgeschlagen!";
	}

}
