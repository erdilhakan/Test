package tr.org.mmo.asansor.util;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import tr.org.mmo.asansor.dto.FirmaDTO;



public class FirmaComparator implements Comparator<FirmaDTO>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8255416338139457776L;

	@Override
	public int compare(FirmaDTO arg0, FirmaDTO arg1) {
		Collator collator = Collator.getInstance(Locale.getDefault());
		// TODO Auto-generated method stub
		return collator.compare(arg0.getUnvan(),arg1.getUnvan());
	}

}
