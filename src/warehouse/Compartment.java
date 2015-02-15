package warehouse;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Compartment implements Serializable {
	private static final long serialVersionUID = -4383443164283909126L;

	private int posX;
	private int posY;
	private int posZ;
	private int capacity;
	private List<Part> partList = new LinkedList<Part>();

	public Compartment(int posX, int posY, int posZ) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.capacity = Variables.CAPACITY;
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

}
