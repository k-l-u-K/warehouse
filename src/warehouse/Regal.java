package warehouse;

import java.io.Serializable;
import java.util.Arrays;

public class Regal implements Serializable {
	private static final long serialVersionUID = -6667366784503647516L;
	private Compartment[][] compartments;

	public Regal(int y) {
		compartments = new Compartment[10][10];
		for (int i = 0; i < 10; i++)		//Nebeneinander
			for (int j = 0; j < 10; j++)	//Übereinander
				this.compartments[i][j] = new Compartment((2 + (i * 2)), y, (j * 2));
	}

	public Compartment[][] getCompartments() {
		return compartments;
	}
	
	public void setCompartments(Compartment[][] compartments) {
		this.compartments = compartments;
	}

	// Findet ein freies Fach mit ausreichender Kapazität
	public Compartment findCompartment(Part part) {	
		for (int i = 0 ; i < Warehouse.getRegal().size(); i++) {
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++) {
					if ((Warehouse.getRegal().get(i).getCompartments()[j][k].getCapacity() >= part.getSize())
							&& this.isCompartmentFree(part, Warehouse.getRegal().get(i).getCompartments()[j][k])) {
							return Warehouse.getRegal().get(i).getCompartments()[j][k];
					}
				}
			}
		}
		return null;
	}
	
	// gibt true/false zurück, ob ein spezielles! Fach frei ist
	public boolean isCompartmentFree(Part part, Compartment compartment) {
		if (compartment.getPartList().isEmpty())
			return true;
		else {
			// Das Fach ist ebenfalls "leer", wenn ein Teil mit gleicher
			// Beschreibung eingelagert werden soll.
			for (Part parts : compartment.getPartList())
				if (part.getDescription().equals(parts.getDescription()))
					return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Regal [Regale=" + Arrays.toString(compartments) + "]";
	}

}
