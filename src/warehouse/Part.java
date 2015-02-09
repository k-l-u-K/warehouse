package warehouse;

import java.util.ArrayList;
import java.util.List;

//Klasse für die Teile, welche einsortiert werden sollen
public class Part {

	private String description;
	private int partnumber;
	private int amount;
	private String typ;
	private int size;
	private static List<Part> PartList = new ArrayList<Part>();

	
	public Part(String description, int partnumber, int amount, String typ, int size) {
		this.description = description;
		//ID sollte unbedingt vorher geprüft werden --> eigene Methode
		this.partnumber = partnumber++;
		this.amount = amount;
		this.typ = typ;
		this.size = size;
		PartList.add(this);
	}

	public void delPart() {
		PartList.remove(this);
	}

}
