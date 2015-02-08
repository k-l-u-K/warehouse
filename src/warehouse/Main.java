package warehouse;

import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File(".\\data\\DateiZumEinlesen.txt");
		OutputStream ostream = new FileOutputStream(file);
		PrintWriter writer = new PrintWriter(ostream);
		
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
		new MainFrame();
	}

}
