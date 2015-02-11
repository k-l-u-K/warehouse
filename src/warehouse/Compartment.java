package warehouse;

import java.util.ArrayList;
import java.util.List;

public class Compartment {

	private int posX;
	private int posY;
	private int posZ;
	private int capacity = 10;
	private int idCompartment = 0;
	public List<Part> partList = new ArrayList<Part>();

	public Compartment(int posX, int posY, int posZ) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		idCompartment = idCompartment++;
	}

	@Override
	public String toString() {
		return "Compartment [posX=" + posX + ", posY=" + posY + ", posZ="
				+ posZ + ", capacity=" + capacity + ", idCompartment="
				+ idCompartment + "]";
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

	public int getIdCompartment() {
		return idCompartment;
	}

	public void setIdCompartment(int idCompartment) {
		this.idCompartment = idCompartment;
	}
	
	public List<Part> getPartList() {
		return partList;
	}

	public void setPartList(List<Part> partList) {
		this.partList = partList;
	}
	
	//gibt true/false zurück, ob ein Fach frei ist
	public static boolean isCompartmentFree(Part part) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++) {
					for (Part parts : Warehouse.get().regale[i].compartments[j][k].partList) {
						if (Warehouse.get().regale[i].compartments[j][k].partList.isEmpty() ||
							part.getDescription().equals(parts.getDescription()))
							return true;
					}
				}
			}
		}
		return false;
	}

}
