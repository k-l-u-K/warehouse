package warehouse;

public class TransportVehicle {

	public Compartment findCompartment(Part part) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++) {
					if ((Warehouse.get().regale[i].compartments[j][k].getPartList().isEmpty()) &&
						!Warehouse.get().regale[i].compartments[j][k].getPartList().contains(part))
						return Warehouse.get().regale[i].compartments[j][k];
				}
			}
		}
		return null;
	}
	
	public Part teilEinlagern(Part part) {
		int partSize = part.getSize();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++) {
					//wenn noch Platz, dann einlagern
					if ((Warehouse.get().regale[i].compartments[j][k].getCapacity() - partSize) > 0) {
						//einlagern
						Warehouse.get().regale[i].compartments[j][k].getPartList().add(part);
						//Kapazität verringern
						Warehouse.get().regale[i].compartments[j][k].setCapacity(Warehouse.get().regale[i].compartments[j][k].
								getCapacity() - partSize);
						return null;
						//Schleife anders abbrechen
					}
				}
			}
		}
		return null;
	}

	public void teileAnzeigen() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++) {
					for (Part parts : Warehouse.get().regale[i].compartments[j][k].partList) {
						System.out.println(parts);
						System.out.println(i + " " + j + " " + k);
					}
				}
			}
		}
	}

}
