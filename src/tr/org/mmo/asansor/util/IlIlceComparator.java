package tr.org.mmo.asansor.util;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;



public class IlIlceComparator implements Comparator<String>,Serializable{
	
	
		/**
	 * 
	 */
	private static final long serialVersionUID = -2323442874468493932L;

		@Override
		public int compare(String arg0, String arg1) {
			Collator collator = Collator.getInstance(Locale.getDefault());
			//Collator trCollator = Collator.getInstance(new Locale(“tr”, “TR”));
			return collator.compare(arg0,arg1);
			//return arg0.compareTo(arg1);
		}
	

}
