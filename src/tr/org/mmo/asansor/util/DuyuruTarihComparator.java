package tr.org.mmo.asansor.util;


import java.io.Serializable;
import java.util.Comparator;

import tr.org.mmo.asansor.beans_.Duyuru;


public class DuyuruTarihComparator implements Comparator<Duyuru>,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2323442874468493932L;

	@Override
	public int compare(Duyuru arg0, Duyuru arg1) {

		return arg1.getTarih().compareTo(
				arg0.getTarih());
	}
}