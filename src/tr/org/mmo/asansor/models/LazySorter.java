package tr.org.mmo.asansor.models;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import tr.org.mmo.asansor.dto.RaporDTO;

public class LazySorter implements Comparator<RaporDTO> {

	private String sortField;

	private SortOrder sortOrder;

	public LazySorter(String sortField, SortOrder sortOrder) {
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	public int compare(RaporDTO r1, RaporDTO r2) {
		try {
			Object value1 = RaporDTO.class.getField(this.sortField).get(r1);
			Object value2 = RaporDTO.class.getField(this.sortField).get(r2);

			int value = ((Comparable) value1).compareTo(value2);

			return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
