package tr.org.mmo.asansor.util;

import org.primefaces.model.SortOrder;

import java.util.Comparator;



import tr.org.mmo.asansor.dto.KontrolHaberDTO;
 
public class Sorter implements Comparator<KontrolHaberDTO> {
 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public Sorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public int compare(KontrolHaberDTO kontrol1, KontrolHaberDTO kontrol2) {
        try {
            Object value1 = KontrolHaberDTO.class.getField(this.sortField).get(kontrol1);
            Object value2 = KontrolHaberDTO.class.getField(this.sortField).get(kontrol2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
