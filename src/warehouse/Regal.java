package warehouse;

import java.util.Arrays;

public class Regal {

	public Compartment[][] compartments;

	public Regal(int y) {
		compartments = new Compartment[10][10];
		for (int i = 0; i < 10; i++)		//Nebeneinander
			for (int j = 0; j < 10; j++)	//Übereinander
				compartments[i][j] = new Compartment((2 + (i * 2)), y, (j * 2));
	}

	public Compartment[][] getCompartments() {
		return compartments;
	}

	@Override
	public String toString() {
		return "Regal [compartments=" + Arrays.toString(compartments) + "]";
	}

}
