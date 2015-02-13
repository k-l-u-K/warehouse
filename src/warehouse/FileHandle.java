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
	private static String file =".\\data\\DateiZumEinlesen.ser";

	public static void serialize() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 10; j++)
					for (int k = 0; k < 10; k++)
						for (Part parts : Warehouse.get().getRegale()[i].compartments[j][k].getPartList())
							out.writeObject(parts);
			System.out.println("Speichern erfolgreich");
			System.out.println();
		} catch (Exception e) {
			System.out.println("Speichern fehlgeschlagen");
		}
	}
  	
	public static Part deserialize() {
		List<Part> loadedPartList = new ArrayList<Part>();
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
  			for (int i = 0; i < 800; i++) {
  				//if (!in.readObject().equals(null))
  					//loadedPartList.add((Part) in.readObject());
  				//return (Part) in.readObject();
  				//data[i] = (Data)  in.readObject();
  				System.out.println(in.readObject());
  			}
  			System.out.println("Deserialization succeeded\n");
  		} catch (Exception e) {
  			System.out.println("Deserialization failed\n");
  			System.err.println(e);
  		}
  	    return null;
	}
}
