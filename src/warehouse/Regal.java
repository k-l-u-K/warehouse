package warehouse;

import java.io.Serializable;
import java.util.Arrays;

public class Regal implements Serializable {
	private static final long serialVersionUID = -6667366784503647516L;

	private static int position;
	
	public Compartment[][] compartments;

	public Regal(int y) {
		compartments = new Compartment[10][10];
		for (int i = 0; i < 10; i++)		//Nebeneinander
			for (int j = 0; j < 10; j++)	//Übereinander
				compartments[i][j] = new Compartment((2 + (i * 2)), y, (j * 2));
		setPosition(y);
	}

	public Compartment[][] getCompartments() {
		return compartments;
	}

	public int getPosition() {
		return position;
	}

	public static void setPosition(int newPosition) {
		position = newPosition;
	}

	@Override
	public String toString() {
		return "Regal [compartments=" + Arrays.toString(compartments) + "]";
	}

}
