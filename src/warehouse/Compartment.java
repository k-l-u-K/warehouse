package warehouse;

import java.util.ArrayList;
import java.util.List;

public class Compartment {

	private int posX;
	private int posY;
	private int posZ;
	private int capacity;
	public List<Part> partList = new ArrayList<Part>();

	public Compartment(int posX, int posY, int posZ) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.capacity = 10;
	}

	@Override
	public String toString() {
		return "Compartment [posX=" + posX + ", posY=" + posY + ", posZ="
				+ posZ + ", capacity=" + capacity + "]";
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getPosZ() {
		return posZ;
	}

	public void setPosZ(int posZ) {
		this.posZ = posZ;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public List<Part> getPartList() {
		return partList;
	}

	public void setPartList(List<Part> partList) {
		this.partList = partList;
	}

	// gibt true/false zurück, ob ein spezielles! Fach frei ist
	public boolean isCompartmentFree(Part part, Compartment compartment) {
		if (compartment.partList.isEmpty())
			return true;
		else {
			// Das Fach ist ebenfalls "leer", wenn ein Teil mit gleicher
			// Beschreibung eingelagert werden soll.
			for (Part parts : compartment.partList) {
				if (part.getDescription().equals(parts.getDescription()))
					return true;
			}
		}
		return false;
	}

}
