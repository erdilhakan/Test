package tr.org.mmo.asansor.util;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class CihazTeknikOzellikleriComparator implements Comparator<String>,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2323442874468493932L;

	@Override
	public int compare(String arg0, String arg1) {
		Collator collator = Collator.getInstance(Locale.getDefault());
		// Collator trCollator = Collator.getInstance(new Locale(“tr”,
		// “TR”));
		if (arg0 != null) {
			if (arg0.contains("KİŞİ") || arg0.contains("ADET")) {

				String s = arg0.replace("KİŞİ", "").trim();
				s = s.replace("ADET", "").trim();

				String s1 = arg1.replace("KİŞİ", "").trim();
				s1 = s1.replace("ADET", "").trim();
				try {

					Integer a = Integer.parseInt(s);
					Integer b = Integer.parseInt(s1);

					return a.compareTo(b);

				} catch (Exception e) {
					return collator.compare(arg0 == null ? "" : arg0,
							arg1 == null ? "" : arg1);
				}
			} else {

				return collator.compare(arg0 == null ? "" : arg0,
						arg1 == null ? "" : arg1);
				// return arg0.compareTo(arg1);
			}
		}
		return 0;

	}
}