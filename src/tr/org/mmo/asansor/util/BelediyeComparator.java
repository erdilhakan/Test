package tr.org.mmo.asansor.util;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import tr.org.mmo.asansor.dto.BelediyeDTO;

public class BelediyeComparator implements Comparator<BelediyeDTO>,Serializable{
	
	
	/**
 * 
 */
private static final long serialVersionUID = -2323442874468493932L;

	@Override
	public int compare(BelediyeDTO arg0, BelediyeDTO arg1) {
		Collator collator = Collator.getInstance(Locale.getDefault());
		//Collator trCollator = Collator.getInstance(new Locale(“tr”, “TR”));
		return collator.compare(arg0.getAdi(),arg1.getAdi());
		//return arg0.compareTo(arg1);
	}


}