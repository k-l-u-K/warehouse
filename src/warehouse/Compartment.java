package warehouse;

import java.util.ArrayList;
import java.util.List;


//Klasse für die Fächer
public class Compartment {
	
	private int posX;
	private int posY;
	private int posZ;
	private int capacity;
	private int idCompartment = 0;
	private static List<Compartment> CompartmentList = new ArrayList<Compartment>();

	public Compartment(int posX, int posY, int posZ) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		idCompartment = idCompartment++;
		CompartmentList.add(this);
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

	public static List<Compartment> getCompartmentList() {
		return CompartmentList;
	}

	public static void setCompartmentList(List<Compartment> compartmentList) {
		CompartmentList = compartmentList;
	}

}
