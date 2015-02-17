package warehouse;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

// Klasse zum Speichern der Daten als Objekte
// dazu wird (de-)serialisiert  
public class FileHandle implements Serializable {
	private static final long serialVersionUID = -7725023475097213226L;
	private static String file = ".\\data\\WarehouseFile.ser";

	// Objekte serialisieren
	public static void serialize() {
		// Fehler abfangen, wenn mit der Datei etwas nicht stimmt
		try (FileOutputStream fos = new FileOutputStream(file)){
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			for (int i = 0; i < Variables.REGALCOUNT; i++)
				for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
					for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++) {
						// Fächer in die Datei schreiben
						oos.writeObject(Warehouse.get().getRegal().get(i).getCompartments()[j][k]);
						// Teile in die Datei schreiben
						oos.writeObject(Warehouse.get().getRegal().get(i).getCompartments()[j][k].getPartList());
					}
		 	oos.close();
		 	JOptionPane.showMessageDialog(null,
		 		    "Lagerbestand wurde erfolgreich gespeichert.",
		 		    "Speichern erfolgreich",
		 		    JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception a) {
			JOptionPane.showMessageDialog(null,
		 		    "Lagerbestand konnte nicht gespeichert werden.",
		 		    "Speichern fehlgeschlagen",
		 		    JOptionPane.ERROR_MESSAGE);
		}
	}

	// Objekte deserialisieren
	@SuppressWarnings("unchecked")
	public static void deserialize() {
		List<Part> loadedPartList = new ArrayList<Part>();
		Compartment loadedCompartment = null;
		try (FileInputStream istream = new FileInputStream(file);) {
			ObjectInputStream ois = new ObjectInputStream(istream);
			while (istream.available() > 0) {
				for (int i = 0; i < Variables.REGALCOUNT; i++)
					for (int j = 0; j < Variables.COMPARTMENTSIDEBYSIDE; j++)
						for (int k = 0; k < Variables.COMPARTMENTONTOPOFEACHOTHER; k++) {
							// Fächer und Teileliste in Variable speichern
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
		} catch (StreamCorruptedException e) {
			JOptionPane.showMessageDialog(null,
		 		    "Die Informationen aus " + file + " sind beschädigt.\nEin leeres, neues Lager wurde automatisch generiert.",
		 		    "Laden fehlgeschlagen",
		 		    JOptionPane.ERROR_MESSAGE);		
		} catch (EOFException e) {
			JOptionPane.showMessageDialog(null,
		 		    "Die zu ladende Datei enthält keine Lagerinformation.\nEin leeres, neues Lager wurde automatisch generiert.",
		 		    "Laden fehlgeschlagen",
		 		    JOptionPane.ERROR_MESSAGE);			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
		 		    "Im erwarteten Dateipfad befindet sich keine Datei.\nEin leeres, neues Lager wurde automatisch generiert.",
		 		    "Laden fehlgeschlagen",
		 		    JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
		 		    "Der Lagerbestand konnte nicht korrekt geladen werden.",
		 		    "Laden fehlgeschlagen",
		 		    JOptionPane.ERROR_MESSAGE);
		}
	}
}
