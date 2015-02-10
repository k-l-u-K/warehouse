package warehouse;

//import java.util.HashMap;
//import java.util.Map;

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

	public void setCompartment(int regalnr, int x, int z, int anzahl) {
		// regal.get(regalnr).getCompartments()[x][z]);
	}

	/*
	 * | | ___ ___ -----------| |-------| |---- | | | | | | | | | | | | | | | |
	 * | | | | | | | | | | | | | | | | |___| |___| erstes Regal beginnt bei: x=2
	 * y=4 z=0
	 * 
	 * x = 2 + (i * 2) y = 2 + (i * 2)
	 * 
	 * x | | |---------y z ist die Höhe
	 */
}
