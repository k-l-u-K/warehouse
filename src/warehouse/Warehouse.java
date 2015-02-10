package warehouse;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Warehouse {
	
	private static Warehouse warehouse;
	private Map<Integer,Regal> regal;

	public static Warehouse get() {
		if (warehouse == null)
			warehouse = new Warehouse();
		return warehouse;
	}
	
	private Warehouse() {
		for (int i = 1; i < 8; i++)
			regal.put(i, new Regal(2 + (i * 2)));

	}

	public static void setCompartment(int regalnr, int x, int z, Part part) {
		//regal.get(regalnr).getCompartments()[x][z]);
		//Aufruf wo ist was frei
		
		//System.out.println(regalnr);
		//Part.createPartList(1, 2, 0, part);
	}

	public static void setCompartment(String description, int partnr, int amount, int size) {
		new Part(description, 45, 1, 3);
		System.out.println(Part.getSize());
		System.out.println(Part.getTeilListe());

	}

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

