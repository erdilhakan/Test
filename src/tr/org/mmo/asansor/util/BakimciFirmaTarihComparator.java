package tr.org.mmo.asansor.util;

import java.io.Serializable;
import java.util.Comparator;

import tr.org.mmo.asansor.dto.FirmaDTO;

public class BakimciFirmaTarihComparator implements Comparator<FirmaDTO>,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2323442874468493932L;

	@Override
	public int compare(FirmaDTO arg0, FirmaDTO arg1) {

		return arg0.getAsansorBakimTarihi().compareTo(
				arg1.getAsansorBakimTarihi());
	}
}
