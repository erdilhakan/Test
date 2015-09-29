package tr.org.mmo.asansor.util;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class StringSort implements Comparator<Object>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8197555736085375513L;

	@Override
	public int compare(Object o1, Object o2) {
		Collator collator = Collator.getInstance(Locale.getDefault());
		//Collator trCollator = Collator.getInstance(new Locale(“tr”, “TR”));
		return collator.compare(o1.toString(),o2.toString());
		//return o1.toString().compareTo(o2.toString());
	}

}


