package warehouse;

import java.util.ArrayList;
import java.util.List;

public class Part {

	private String description;
	private int partnumber;
	private int size;
	//Liste, um nur nach Parts zu suchen - ggf. besser&schneller, wenn man nur nach doppelter o.ä. ID sucht
	public static List<Part> onlyPartList = new ArrayList<Part>();

	public Part(String description, int partnumber, int size) {
		this.description = description;
		if (partnumber == 0 || findPart(null, partnumber) != null)
			partnumber = getFreeID();
		this.partnumber = partnumber;
		this.size = size;
		onlyPartList.add(this);
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
	
	//Test
	/*
	public static List<Part> getOnlyPartList() {
		return Collections.unmodifiableList(onlyPartList);
	}*/
	
	//Gibt ein Teil ohne Ort zurück
	public static Part findPart(Part part, int partid) {
		for (Part tempTeil : onlyPartList) {
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
		} else
			return getFreeID(++testID);
	}

//	public void delPart() {
//		PartList.remove(this);
//	}

}
