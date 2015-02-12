package warehouse;

import java.util.Comparator;

public class SortDescription implements Comparator<Part> {

	@Override
	public int compare(Part p1, Part p2) {
		return p1.getDescription().compareTo(p2.getDescription());
	}

}
