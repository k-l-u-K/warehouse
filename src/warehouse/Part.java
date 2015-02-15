package warehouse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Part implements Serializable {
	private static final long serialVersionUID = -7831438907397267073L;

	private String description;
	private int partnumber;
	private int size;

	public Part(String description, int partnumber, int size) {
		this.description = description;
		if (partnumber <= 0 || Compartment.findPart(null, partnumber) != null)
			partnumber = getFreeID();
		this.partnumber = partnumber;
		this.size = size;
		Compartment.setNewPart(this);
	}
	
	//public static List<Part> getPartList() {
	//	return partList;
	//}

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
	
	//gibt eine freie ID zurück
	public static int getFreeID() {
		return getFreeID(1);
	}
	private static int getFreeID(int testID) {
		if (Compartment.findPart(null, testID) == null) {
			return testID;
			// Info-Dialog an den Benutzer, dass ID auf testID festgelegt wurde
		} else
			return getFreeID(++testID);
	}

}
