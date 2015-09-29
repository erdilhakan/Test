package tr.org.mmo.asansor.util;


import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import tr.org.mmo.asansor.dto.BinaDTO;

public class BinaComparator implements Comparator<BinaDTO>,Serializable{
	
	
	/**
 * 
 */
private static final long serialVersionUID = -2323442874468493932L;

	@Override
	public int compare(BinaDTO arg0, BinaDTO arg1) {
		Collator collator = Collator.getInstance(Locale.getDefault());
		//Collator trCollator = Collator.getInstance(new Locale(“tr”, “TR”));
		return collator.compare(arg0.getBinaAdi(),arg1.getBinaAdi());
		//return arg0.compareTo(arg1);
	}


}