package tr.org.mmo.asansor.util;

import java.io.Serializable;
import java.util.Comparator;

import tr.org.mmo.asansor.dto.BelediyeSozlesmeDTO;

public class BelediyeSozlesmeComparator implements Comparator<BelediyeSozlesmeDTO>,Serializable{
	
	
	/**
 * 
 */
private static final long serialVersionUID = -2323442874468493932L;

	@Override
	public int compare(BelediyeSozlesmeDTO arg0, BelediyeSozlesmeDTO arg1) {
		
		return arg1.getSozlesmeBaslangicTarihi().compareTo(arg0.getSozlesmeBaslangicTarihi());
		
	}


}
