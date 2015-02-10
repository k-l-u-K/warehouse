package warehouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//Klasse für die Teile, welche einsortiert werden sollen
public class Part {

	private String description;
	private int partnumber;
<<<<<<< HEAD
	private int size;
		
	public Part(String description, int partnumber, int size) {
=======
	private int amount;
	private static int size;
	private static List<Part> PartList = new ArrayList<Part>();

	
	public Part(String description, int partnumber, int amount, int size) {
>>>>>>> origin/master
		this.description = description;
		//ID sollte unbedingt vorher geprüft werden --> eigene Methode
		this.partnumber = partnumber++;
				this.size = size;
	}

	@Override
	public String toString() {
		return "Part [description=" + description + ", partnumber="
				+ partnumber + ", size=" + size + "]";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPartnumber() {
		return partnumber;
	}

	public void setPartnumber(int partnumber) {
		this.partnumber = partnumber;
	}

	public int getSize() {
		return size;
	}

<<<<<<< HEAD
	public void setSize(int size) {
		this.size = size;
=======
	public static int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void delPart() {
		PartList.remove(this);
>>>>>>> origin/master
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
