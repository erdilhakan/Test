package tr.org.mmo.asansor.util;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Comparator;

import tr.org.mmo.asansor.dto.RandevuListeDTO;

public class RandevuSaatComparator implements Comparator<RandevuListeDTO>,Serializable{
	
	
	/**
 * 
 */
private static final long serialVersionUID = -2323442874468493932L;

	@Override
	public int compare(RandevuListeDTO arg0, RandevuListeDTO arg1) {
		Calendar cArg0=Calendar.getInstance();
		Calendar cArg1=Calendar.getInstance();
		Calendar c=Calendar.getInstance();
		
		cArg0.setTime(arg0.getRandevuTarihi());
		cArg1.setTime(arg1.getRandevuTarihi());
		
		
		c.setTime((Time)arg0.getRandevuSaati());
		cArg0.set(Calendar.HOUR, c.get(Calendar.HOUR));
		cArg0.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
		c=Calendar.getInstance();
		c.setTime((Time)arg1.getRandevuSaati());
		cArg1.set(Calendar.HOUR, c.get(Calendar.HOUR));
		cArg1.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
		return cArg0.compareTo(cArg1);
	}


}