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
	
	public void setCompartments(Compartment compartment, int index1, int index2){
		this.compartments[index1][index2] = compartment;
	}

	// Findet ein freies Fach mit ausreichender Kapazität
	public Compartment findCompartment(Part part) {	
		for (int j = 0; j < 10; j++) {
			for (int k = 0; k < 10; k++) {
				if ((compartments[j][k].getCapacity() >= part.getSize())
						&& this.isCompartmentFree(part, compartments[j][k])) {
						return compartments[j][k];
				}
			}
		}
		return null;
	}
	
	// gibt true/false zurück, ob ein spezielles! Fach frei ist
	public boolean isCompartmentFree(Part part, Compartment compartment) {
		if (Compartment.getPartList().isEmpty())
			return true;
		else {
			// Das Fach ist ebenfalls "leer", wenn ein Teil mit gleicher
			// Beschreibung eingelagert werden soll.
			for (Part parts : Compartment.getPartList())
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
