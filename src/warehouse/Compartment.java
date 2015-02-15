package warehouse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Compartment implements Serializable {
	private static final long serialVersionUID = -4383443164283909126L;

	private int posX;
	private int posY;
	private int posZ;
	private int capacity;
	private static List<Part> partList = new ArrayList<Part>();

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
	
	public static void setPartList(List<Part> partList) {
		Compartment.partList = partList;
	}
	
	public static void setNewPart(Part part) {
		partList.add(part);
	}
	
	public static void removePart(Part part) {
		partList.remove(part);
	}
	
	public static List<Part> getPartList() {
		return Collections.unmodifiableList(partList);
	}

	public static Part findPart(Part part, int partid) {
		for (Part tempTeil : partList) {
			if (tempTeil.getPartnumber() == partid && partid != -1)
				return tempTeil;
			if (part != null && tempTeil.getDescription().equals(part.getDescription()))
				return tempTeil;
		}
		return null;
	}

}
