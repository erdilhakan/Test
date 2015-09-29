package tr.org.mmo.asansor.util;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import tr.org.mmo.asansor.beans_.Kullanici;

public class KullaniciComparator implements Comparator<Kullanici>,Serializable{

	


	/**
	 * 
	 */
	private static final long serialVersionUID = -5532381936546687068L;

	@Override
	public int compare(Kullanici arg0, Kullanici arg1) {
		// TODO Auto-generated method stub
		Collator collator = Collator.getInstance(Locale.getDefault());
		// TODO Auto-generated method stub
		return collator.compare(arg0.getKullaniciAdi(),arg1.getKullaniciAdi());
	}
	
}
