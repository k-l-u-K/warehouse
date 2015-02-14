package warehouse;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FileHandle implements Serializable {
	private static final long serialVersionUID = -7725023475097213226L;
	private static String file = ".\\data\\DateiZumEinlesen.ser";

	public static void serialize() {
		try (ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream(file))) {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 10; j++)
					for (int k = 0; k < 10; k++) {
						out.writeObject(Warehouse.getRegal().get(i)
								.getCompartments()[j][k]);
						out.writeObject(Warehouse.getRegal().get(i)
								.getCompartments()[j][k].getPartList());
						
					}
		} catch (Exception e) {
			System.out.println("Speichern fehlgeschlagen");
		}
	}

	public static Compartment deserialize() {
		List<Part> loadedPartList = new ArrayList<Part>();
		Compartment loadedCompartment = null;
		try (FileInputStream istream = new FileInputStream(file);) {
			ObjectInputStream ois = new ObjectInputStream(istream);
			while (istream.available() > 0) {
				for (int i = 0; i < 8; i++)
					for (int j = 0; j < 10; j++)
						for (int k = 0; k < 10; k++) {
							loadedCompartment = (Compartment) ois.readObject();
							loadedPartList = (List<Part>) ois.readObject();
							
							Warehouse.loadPartsIntoWarehouse(loadedPartList,
									loadedCompartment,i,j,k);
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
