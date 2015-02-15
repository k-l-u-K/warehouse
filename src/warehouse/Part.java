package warehouse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Part implements Serializable/*implements Comparable<Part>*/ {
	private static final long serialVersionUID = -7831438907397267073L;
	
	private String description;
	private int partnumber;
	private int size;
	private static List<Part> partList = new ArrayList<Part>();

	public Part(String description, int partnumber, int size) {
		this.description = description;
		if (partnumber <= 0 || findPart(null, partnumber) != null)
			partnumber = getFreeID();
		this.partnumber = partnumber;
		this.size = size;
		partList.add(this);
	}
	
	//public static List<Part> getPartList() {
	//	return partList;
	//}

	public static void setPartList(List<Part> partList) {
		Part.partList = partList;
	}
	
	public static void setNewPart(Part part) {
		partList.add(part);
	}
	
	public static void removePart(Part part) {
		partList.remove(part);
	}
	
	public static List<Part> getPartList() {
		return Collections.unmodifiableList(partList);
	}

	@Override
	public String toString() {
		return "Bezeichnung: " + description + ", Teilenummer: "+ partnumber + ", Größe in GE: " + size;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPartnumber() {
		return partnumber;
	}

	public void setPartnumber(int partnumber) {
		this.partnumber = partnumber;
	}

	public int getSize() {
		return size;
	}
	
	//Gibt ein Teil ohne Ort zurück
	public static Part findPart(Part part, int partid) {
		for (Part tempTeil : partList) {
			if (tempTeil.getPartnumber() == partid && partid != -1)
				return tempTeil;
			if (part != null && tempTeil.getDescription().equals(part.getDescription()))
				return tempTeil;
		}
		return null;
	}
	
	//gibt eine freie ID zurück
	public static int getFreeID() {
		return getFreeID(1);
	}
	private static int getFreeID(int testID) {
		if (findPart(null, testID) == null) {
			return testID;
			// Info-Dialog an den Benutzer, dass ID auf testID festgelegt wurde
		} else
			return getFreeID(++testID);
	}

}
