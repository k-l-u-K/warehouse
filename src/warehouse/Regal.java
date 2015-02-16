package warehouse;

import java.io.Serializable;
import java.util.Arrays;

public class Regal implements Serializable {
	private static final long serialVersionUID = -6667366784503647516L;
	private Compartment[][] compartments;

	public Regal(int y) {
		compartments = new Compartment[Variables.COMPARTMENTSIDEBYSIDE][Variables.COMPARTMENTONTOPOFEACHOTHER];
		for (int i = 0; i < Variables.COMPARTMENTSIDEBYSIDE; i++)						//Fächer nebeneinander
			for (int j = 0; j < Variables.COMPARTMENTONTOPOFEACHOTHER; j++)				//Fächer übereinander
				this.compartments[i][j] = new Compartment((2 + (i * 2)), y, (j * 2));	// neues Fach erzeugen
	}

	public Compartment[][] getCompartments() {
		return compartments;
	}

	// findet ein freies Fach mit ausreichender Kapazität
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
	
	// Gibt true/false zurück, ob ein spezielles Fach frei ist
	public boolean isCompartmentFree(Part part, Compartment compartment) {
		if (compartment.getPartList().isEmpty())
			// wenn in dem Fach noch nichts drin, ist es leer
			return true;
		else if (Variables.MULTIPARTTYPPERCOMPARTMENT)
			// wenn in dem Fach etwas drin, aber Teile mit verschiedener Bezeichnung in ein Fach eingelagert werden dürfen,
			// ist gibt die Methode ebenfalls true zurück
			return true;
		else
			// Fach ist ebenfalls "leer", wenn ein Teil mit gleicher Beschreibung eingelagert werden soll
			for (Part parts : compartment.getPartList())
				if (part.getDescription().equals(parts.getDescription()))
					return true;
		return false;
	}

	@Override
	public String toString() {
		return "Regal [Regale=" + Arrays.toString(compartments) + "]";
	}

}
