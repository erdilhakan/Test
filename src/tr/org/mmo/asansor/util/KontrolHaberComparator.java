package tr.org.mmo.asansor.util;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import tr.org.mmo.asansor.dto.KontrolHaberDTO;



public class KontrolHaberComparator implements Comparator<KontrolHaberDTO>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8255416338139457776L;

	@Override
	public int compare(KontrolHaberDTO arg0, KontrolHaberDTO arg1) {
		Collator collator = Collator.getInstance(Locale.getDefault());
		// TODO Auto-generated method stub
		return collator.compare(arg0.getBinaId(),arg1.getBinaId());
	}

}
