package warehouse;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {
	Map<Integer,Regal> regal = new HashMap<Integer,Regal>();
	for (int i = 1; i < 8; i++)
		regal.put(i, new Regal(2 + (i * 2)));

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

public void setCompartment(int regalnr, int x, int z,int anzahl) {
	//regal.get(regalnr).getCompartments()[x][z]);
}
