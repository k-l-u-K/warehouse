package warehouse;

import java.util.Comparator;

public class SortPartnumber implements Comparator<Part> {

	@Override
	public int compare(Part p1, Part p2) {
		return p1.getPartnumber() - p2.getPartnumber();
	}

}
