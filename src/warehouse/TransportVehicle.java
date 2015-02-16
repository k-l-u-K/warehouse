package warehouse;

public class TransportVehicle {
	private static int posX;
	private static int posY;
	private static int posZ;
	
	public TransportVehicle(int posX, int posY, int posZ) {
		TransportVehicle.posX = posX;
		TransportVehicle.posY = posY;
		TransportVehicle.posZ = posZ;
	}

	// Fahrzeug soll zum übergebenen Standort fahren
	public static void driveToCompartment(Part transportPart, Compartment compartment) {
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

}
