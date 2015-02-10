package warehouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//Klasse für die Teile, welche einsortiert werden sollen
public class Part {

	private String description;
	private int partnumber;
	private int amount;
	private static int size;
	private static List<Part> PartList = new ArrayList<Part>();

	
	public Part(String description, int partnumber, int amount, int size) {
		this.description = description;
		//ID sollte unbedingt vorher geprüft werden --> eigene Methode
		this.partnumber = partnumber++;
		this.amount = amount;
		this.size = size;
		PartList.add(this);
	}

	public static int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void delPart() {
		PartList.remove(this);
	}
	
	public static List<Part> getTeilListe() {
		return Collections.unmodifiableList(PartList);
	}
	/*
	public static List<Part> createPartList(int regalnr, int x, int z, Part part) {
		LinkedList<Part> tempList = new LinkedList<Part>();
		for (Part tempTeil: PartList) {
			if (tempTeil.equals(part))
					tempList.add(tempTeil);
		}
		return Collections.unmodifiableList(tempList);
	}*/
}
