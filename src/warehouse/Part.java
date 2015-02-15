package warehouse;

import java.io.Serializable;

public class Part implements Serializable/*implements Comparable<Part>*/ {
	private static final long serialVersionUID = -7831438907397267073L;
	
	private String description;
	private int partnumber;
	private int size;
	
	public Part(String description, int partnumber, int size) {
		this.description = description;
		if (partnumber <= 0 || Warehouse.findPart(null, partnumber) != null)
			partnumber = Warehouse.getFreeID();
		this.partnumber = partnumber;
		this.size = size;
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
}
