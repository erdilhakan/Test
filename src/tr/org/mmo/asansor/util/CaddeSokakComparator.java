package tr.org.mmo.asansor.util;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import tr.org.mmo.beans.CaddeSokak;



public class CaddeSokakComparator implements Comparator<CaddeSokak>,Serializable{
	
	
		/**
	 * 
	 */
	private static final long serialVersionUID = -2323442874468493932L;

		@Override
		public int compare(CaddeSokak arg0, CaddeSokak arg1) {
			Collator collator = Collator.getInstance(Locale.getDefault());
			//Collator trCollator = Collator.getInstance(new Locale(“tr”, “TR”));
			return collator.compare(arg0.getAd(),arg1.getAd());
			//return arg0.compareTo(arg1);
		}
	

}
