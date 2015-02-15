package warehouse;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class FileHandle implements Serializable {
	private static final long serialVersionUID = -7725023475097213226L;
	private static String file = ".\\data\\DateiZumEinlesen.ser";

	public static void serialize() {
		
		try (FileOutputStream fos = new FileOutputStream(file)){
			ObjectOutputStream oos = new ObjectOutputStream(fos);
		 	
		 	for (int i = 0; i < 8; i++)
				for (int j = 0; j < 10; j++)
					for (int k = 0; k < 10; k++) {
						oos.writeObject(Warehouse.get().getRegal().get(i).getCompartments()[j][k]);
						oos.writeObject(Warehouse.get().getRegal().get(i).getCompartments()[j][k].getPartList());
					}		 	
		 	oos.close();
		 	JOptionPane.showMessageDialog(null,
		 		    "Lagerbestand wurde erfolgreich gespeichert.",
		 		    "Speichern erfolgreich",
		 		    JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception a) {
			//System.out.println("Speichern fehlgeschlagen");
			//System.err.println(a);
			JOptionPane.showMessageDialog(null,
		 		    "Lagerbestand konnte nicht gespeichert werden.",
		 		    "Speichern fehlgeschlagen",
		 		    JOptionPane.ERROR_MESSAGE);
		}
	}

	@SuppressWarnings("unchecked")
	public static void deserialize() {
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
							Warehouse.loadPartsIntoWarehouse(loadedPartList, loadedCompartment, i, j, k);
						}
			}
			ois.close();
			JOptionPane.showMessageDialog(null,
		 		    "Lagerbestand wurde erfolgreich geladen.",
		 		    "Laden erfolgreich",
		 		    JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			//System.out.println("Deserialization failed\n");
			//System.err.println(e);
			JOptionPane.showMessageDialog(null,
		 		    "Lagerbestand konnte nicht geladen werden.",
		 		    "Laden fehlgeschlagen",
		 		    JOptionPane.ERROR_MESSAGE);
		}
	}
}
