package warehouse;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class FileHandle implements Serializable {
	private static final long serialVersionUID = -7725023475097213226L;
	private static String file =".\\data\\DateiZumEinlesen.ser";

	public static void serialize() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 10; j++)
					for (int k = 0; k < 10; k++)
						for (Part parts : Part.getPartList()) {
							out.writeObject(parts);
							System.out.println(parts);
							//out.writeObject(Regal.getCompartments()[j][k]);
						}
		} catch (Exception e) {
			System.out.println("Speichern fehlgeschlagen");
		}
	}
  	
	public static Compartment deserialize() {
		Part loadedPart = null;
		Compartment loadedCompartment = null;
		try (FileInputStream istream = new FileInputStream(file);) {
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(istream);
			while(istream.available() > 0) {
				for (int i = 0; i < 8; i++)
					for (int j = 0; j < 10; j++)
						for (int k = 0; k < 10; k++)
							for (Part parts : Part.getPartList()) {
								loadedPart = (Part) ois.readObject();
								loadedCompartment = (Compartment) ois.readObject();
								//out.writeObject(Regal.setCompartments(loadedCompartment));
							}

				System.out.println(loadedCompartment);
				Part part = new Part(loadedPart.getDescription(),loadedPart.getPartnumber(),loadedPart.getSize());
				Warehouse.loadPartsIntoWarehouse(part, loadedCompartment);
			}
		} catch (Exception e) {
			System.out.println("Deserialization failed\n");
			System.err.println(e);
		}
		return null;
	}

}
