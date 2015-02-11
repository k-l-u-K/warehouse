package warehouse;

import javax.swing.JTextField;

public class TransportVehicle {
	private static int posX;
	private static int posY;
	private static int posZ;
	
	public TransportVehicle(int posX, int posY, int posZ) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	public static void driveToCompartment(Part transportPart, Compartment compartment) {
		System.out.println("Transportfahrzeug beladen mit: " + transportPart.getDescription());
		setPosX(compartment.getPosX());
		setPosY(compartment.getPosY());
		setPosZ(compartment.getPosZ());
		MainFrame.setDrivewayText();
		setPosX(0);
		setPosY(0);
		setPosZ(0);
	}
	

	public static int getPosX() {
		return posX;
	}

	public static int getPosY() {
		return posY;
	}

	public static int getPosZ() {
		return posZ;
	}

	public static void setPosX(int newPosX) {
		posX = newPosX;
	}
	
	public static void setPosY(int newPosY) {
		posY = newPosY;
	}
	
	public static void setPosZ(int newPosZ) {
		posZ = newPosZ;
	}
/*
 * Durch Angabe einer Teilenummer oder Bezeichnung wird das Transportsystem angewiesen,
 * das Fach anzufahren und die Ware zu entnehmen.
 * Geben Sie die benötigten Fahrwege in x, y und z-Richtung bis zum Fach aus.
 * Das Transportsystem befindet sich immer in Nullstellung und kehrt am Ende wieder dorthin zurück.
 */
}
