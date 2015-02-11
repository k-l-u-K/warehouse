package warehouse;

import java.util.LinkedList;

public class Warehouse {

	public static Warehouse warehouse;
	// public Map<Integer,Regal> regal;

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
			//Fahrzeug kann hier mit dem Teil zum Zielort fahren
			TransportVehicle.driveToCompartment(part, compartment);
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
						// Transportfahrzeug kann zum Zielort fahren
						TransportVehicle.driveToCompartment(part, Warehouse.get().regale[i].compartments[j][k]);
						// auslagern
						Warehouse.get().regale[i].compartments[j][k].getPartList().remove(part);
						// Kapazität vergrößern
						Warehouse.get().regale[i].compartments[j][k].setCapacity(Warehouse.get().regale[i].compartments[j][k]
										.getCapacity() + partSize);
						MainFrame.removeARow(part,Warehouse.get().regale[i].compartments[j][k]);
						return "Auslagern erfolgreich!";
					}
				}
			}
		}
		return "Auslagern fehlgeschlagen!";
	}

	
	
	
	
	
	//alle Teile mit den gleichen Namen zurückgeben
	/*
	public LinkedList<Part> findeTeile(Part part) {
		LinkedList<Part> tempList = new LinkedList<Part>(); 
		for (int i = 0; i < regale.size(); i++)
			tempList.addAll(regale.get(i).findeTeile(typ));
		return tempList.isEmpty() ? null: tempList;
	}
	*/

//<<<<<<< HEAD
//	public void setCompartment(int regalnr, int x, int z, int anzahl) {
//		// regal.get(regalnr).getCompartments()[x][z]);
//=======
//	public static void setCompartment(int regalnr, int x, int z, Part part) {
//		//regal.get(regalnr).getCompartments()[x][z]);
//		//Aufruf wo ist was frei
//		
//		//System.out.println(regalnr);
//		//Part.createPartList(1, 2, 0, part);
//	}
//
//	public static void setCompartment(String description, int partnr, int amount, int size) {
//		new Part(description, 45, 1, 3);
//		System.out.println(Part.getSize());
//		System.out.println(Part.getTeilListe());
//
//>>>>>>> origin/master
//	}

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
