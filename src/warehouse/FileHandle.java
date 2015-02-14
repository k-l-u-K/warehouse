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
						for (Part parts : Warehouse.get().getRegale()[i].compartments[j][k].getPartList()) {
							out.writeObject(parts);
							out.writeObject(Warehouse.get().getRegale()[i].compartments[j][k]);
						}
		} catch (Exception e) {
			System.out.println("Speichern fehlgeschlagen");
		}
	}
  	
	public static void deserialize() {
		try (FileInputStream istream = new FileInputStream(file);) {
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(istream);
			while( istream.available() > 0) 
				Warehouse.loadPartsIntoWarehouse((Part) ois.readObject(), (Compartment) ois.readObject());
		} catch (Exception e) {
			System.out.println("Deserialization failed\n");
			System.err.println(e);
		}
	}

}
