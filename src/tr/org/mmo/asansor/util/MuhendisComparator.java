package tr.org.mmo.asansor.util;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import tr.org.mmo.asansor.beans_.Kullanici;

public class MuhendisComparator implements Comparator<Kullanici>,Serializable{

	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3491950714519153093L;

	@Override
	public int compare(Kullanici arg0, Kullanici arg1) {
		Collator collator = Collator.getInstance(Locale.getDefault());
		// TODO Auto-generated method stub
		return collator.compare(arg0.getAdi(),arg1.getAdi());
	}
	
}
