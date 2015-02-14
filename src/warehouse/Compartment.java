package warehouse;

import java.io.Serializable;

public class Compartment implements Serializable {
	private static final long serialVersionUID = -4383443164283909126L;
	
	private int posX;
	private int posY;
	private int posZ;
	private int capacity;

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

	// gibt true/false zurück, ob ein spezielles! Fach frei ist
	public static boolean isCompartmentFree(Part part, Compartment compartment) {
		if (Part.getPartList().isEmpty())
			return true;
		else {
			// Das Fach ist ebenfalls "leer", wenn ein Teil mit gleicher
			// Beschreibung eingelagert werden soll.
			for (Part parts : Part.getPartList())
				if (part.getDescription().equals(parts.getDescription()))
					return true;
		}
		return false;
	}

	
	public Part findPart(Part part) {
		return Part.findPart(part, -1);
	}

}
