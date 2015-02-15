package warehouse;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileHandle implements Serializable {
	private static final long serialVersionUID = -7725023475097213226L;
	private static String file = ".\\data\\DateiZumEinlesen.ser";

	public static void serialize() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 10; j++)
					for (int k = 0; k < 10; k++)
						for (Part parts : Warehouse.get().getRegal().get(i).getCompartments()[j][k].getPartList()) {
							out.writeObject(Warehouse.get().getRegal().get(i));
							out.writeObject(Warehouse.get().getRegal().get(i).getCompartments()[j][k]);
							out.writeObject(parts);
						}
		} catch (Exception e) {
			System.out.println("Speichern fehlgeschlagen");
		}
	}

	public static Compartment deserialize() {
		Part loadedPart = null;
		Compartment loadedCompartment = null;
		Regal loadedRegal = null;
		try (FileInputStream istream = new FileInputStream(file);) {
			ObjectInputStream ois = new ObjectInputStream(istream);
			while (istream.available() > 0) {
				for (int i = 0; i < 8; i++)
					for (int j = 0; j < 10; j++)
						for (int k = 0; k < 10; k++) {
							loadedRegal = (Regal) ois.readObject();
							loadedCompartment = (Compartment) ois.readObject();
							loadedPart = (Part) ois.readObject();
							Warehouse.get().getRegal().replace(i, Warehouse.get().getRegal().get(i), loadedRegal);
							Warehouse.get().getRegal().get(i).setCompartments(loadedCompartment, j, k);
							Warehouse.loadPartsIntoWarehouse(loadedPart, loadedCompartment, i, j, k);
						}
			}
			ois.close();
			System.out.println("Deserialization succeeded\n");
		} catch (Exception e) {
			System.out.println("Deserialization failed\n");
			System.err.println(e);
		}
		return null;
	}

}
