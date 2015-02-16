package warehouse;

import java.io.Serializable;
import java.util.Arrays;

public class Regal implements Serializable {
	private static final long serialVersionUID = -6667366784503647516L;
	private Compartment[][] compartments;

	public Regal(int y) {
		compartments = new Compartment[Variables.COMPARTMENTSIDEBYSIDE][Variables.COMPARTMENTONTOPOFEACHOTHER];
		for (int i = 0; i < Variables.COMPARTMENTSIDEBYSIDE; i++)		//Nebeneinander
			for (int j = 0; j < Variables.COMPARTMENTONTOPOFEACHOTHER; j++)	//Übereinander
				this.compartments[i][j] = new Compartment((2 + (i * 2)), y, (j * 2));
	}

	public Compartment[][] getCompartments() {
		return compartments;
	}

	// Findet ein freies Fach mit ausreichender Kapazität
	public Compartment findCompartment(Part part) {
		for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++) {
			for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++) {
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
		if (compartment.getPartList().isEmpty())
			return true;
		else if (Variables.MULTIPARTTYPPERCOMPARTMENT)
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
