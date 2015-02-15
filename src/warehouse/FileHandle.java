package warehouse;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileHandle implements Serializable {
	private static final long serialVersionUID = -7725023475097213226L;
	private static String file = ".\\data\\DateiZumEinlesen.ser";

	public void serialize() {
		FileInputStream fins = new FileInputStream(file);
		   ObjectInputStream oins = new ObjectInputStream(fins);
		   LinkedList<Part> temp = (LinkedList<Part>)oins.readObject();
		   fins.close();
		   oins.close();
		   temp.clear();
		   FileOutputStream fos = new FileOutputStream(file);
		   ObjectOutputStream oos = new ObjectOutputStream(fos);
		   oos.writeObject(temp);
		   fos.close();
		   oos.close();
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 10; j++)
					for (int k = 0; k < 10; k++) {
						for (Part parts : Warehouse.get().getRegal().get(i).getCompartments()[j][k].getPartList()) 
							System.out.println(parts);
							//out.writeObject(Warehouse.getRegal().get(i));
							//out.writeObject(Warehouse.getRegal().get(i).getCompartments()[j][k]);
							//out.writeObject(Warehouse.getRegal().get(i).getCompartments()[j][k].findPart(parts));
						}
		} catch (Exception e) {
			System.out.println("Speichern fehlgeschlagen");
		}
	}

	@SuppressWarnings("unchecked")
	public static Compartment deserialize() {
		List<Part> loadedPartList = new ArrayList<Part>();
		Compartment loadedCompartment = null;
		Regal loadedRegal = null;
		try (FileInputStream istream = new FileInputStream(file);) {
			ObjectInputStream ois = new ObjectInputStream(istream);
			while (istream.available() > 0) {
				loadedPartList = (List<Part>) ois.readObject();
				for (int i = 0; i < Warehouse.getRegal().size(); i++)
					for (int j = 0; j < 10; j++)
						for (int k = 0; k < 10; k++) {
							loadedRegal = (Regal) ois.readObject();
							loadedCompartment = (Compartment) ois.readObject();
							Warehouse.getRegal().replace(i, Warehouse.getRegal().get(i), loadedRegal);
							Warehouse.getRegal().get(i).setCompartments(loadedCompartment, j, k);
							Warehouse.loadPartsIntoWarehouse(loadedPartList, loadedCompartment, i, j, k);
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
