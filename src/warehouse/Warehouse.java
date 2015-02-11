package warehouse;

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
	
	//alle Teile mit den gleichen Namen zurückgeben
	/*
	public LinkedList<Part> findeTeile(Part part) {
		LinkedList<Part> tempList = new LinkedList<Part>(); 
		for (int i = 0; i < regale.size(); i++)
			tempList.addAll(regale.get(i).findeTeile(typ));
		return tempList.isEmpty() ? null: tempList;
	}
	*/

//<<<<<<< HEAD
//	public void setCompartment(int regalnr, int x, int z, int anzahl) {
//		// regal.get(regalnr).getCompartments()[x][z]);
//=======
//	public static void setCompartment(int regalnr, int x, int z, Part part) {
//		//regal.get(regalnr).getCompartments()[x][z]);
//		//Aufruf wo ist was frei
//		
//		//System.out.println(regalnr);
//		//Part.createPartList(1, 2, 0, part);
//	}
//
//	public static void setCompartment(String description, int partnr, int amount, int size) {
//		new Part(description, 45, 1, 3);
//		System.out.println(Part.getSize());
//		System.out.println(Part.getTeilListe());
//
//>>>>>>> origin/master
//	}

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
