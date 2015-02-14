package warehouse;

import java.io.Serializable;
import java.util.Arrays;

public class Regal implements Serializable {
	private static final long serialVersionUID = -6667366784503647516L;
	private static Compartment[][] compartments;

	public Regal(int y) {
		compartments = new Compartment[10][10];
		for (int i = 0; i < 10; i++)		//Nebeneinander
			for (int j = 0; j < 10; j++)	//Übereinander
				compartments[i][j] = new Compartment((2 + (i * 2)), y, (j * 2));
	}

	public static Compartment[][] getCompartments() {
		return compartments;
	}
	
	public static void setCompartments(Compartment[][] compartments) {
		Regal.compartments = compartments;
	}

	// Findet ein freies Fach mit ausreichender Kapazität
	public static Compartment findCompartment(Part part) {		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++) {
					if ((compartments[j][k].getCapacity() >= part.getSize()) 
						&& Compartment.isCompartmentFree(part, compartments[j][k])) {
							return compartments[j][k];
					}
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "Regal [Regale=" + Arrays.toString(compartments) + "]";
	}

}
