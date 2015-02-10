package warehouse;

import java.util.ArrayList;
import java.util.List;

//Klasse für die Teile, welche einsortiert werden sollen
public class Part {

	private String description;
	private int partnumber;
	private int size;
		
	public Part(String description, int partnumber, int size) {
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

	public void setSize(int size) {
		this.size = size;
	}

}
