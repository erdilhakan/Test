package tr.org.mmo.asansor.util;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import tr.org.mmo.asansor.dto.FirmaDTO;

public class BakimciFirmaComparator implements Comparator<FirmaDTO>,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2323442874468493932L;

	@Override
	public int compare(FirmaDTO arg0, FirmaDTO arg1) {
		Collator collator = Collator.getInstance(Locale.getDefault());
		// Collator trCollator = Collator.getInstance(new Locale(“tr”, “TR”));
		return collator.compare(arg0.getUnvan() == null ? "" : arg0.getUnvan(),
				arg1.getUnvan() == null ? "" : arg1.getUnvan());
		// return arg0.compareTo(arg1);
	}
}
