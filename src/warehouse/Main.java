package warehouse;

public class Main {

	/*
		 | 
		 |2m
		 |	 2m	 	 2m_	2m	 2m_
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
			y=2

			zweites Regal:
			x=2
			y=6 (2m Fahrweg vor dem ersten Regal + Fachbreite des 1. Regals + Fahrweg vor dem zweiten Regal)

			x
			|
			|
			|---------y
			z ist die Höhe
	 */

	public static void main(String[] args) {
		new TransportVehicle(0, 0, 0);
		Warehouse.get();
		new MainFrame();
	}

}
