package tr.org.mmo.asansor.util;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import tr.org.mmo.beans.Mahalle;



public class MahalleComparator implements Comparator<Mahalle>,Serializable{
	
	
		/**
	 * 
	 */
	private static final long serialVersionUID = -2323442874468493932L;

		@Override
		public int compare(Mahalle arg0, Mahalle arg1) {
			Collator collator = Collator.getInstance(Locale.getDefault());
			//Collator trCollator = Collator.getInstance(new Locale(“tr”, “TR”));
			return collator.compare(arg0.getAd(),arg1.getAd());
			//return arg0.compareTo(arg1);
		}
	

}
