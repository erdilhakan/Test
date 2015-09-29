package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.beans_.Randevu;

public class BasvuruRandevuModel extends ListDataModel<Randevu> implements SelectableDataModel<Randevu>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3866093272758815099L;

	
	public BasvuruRandevuModel() {  
    }  
  
    public BasvuruRandevuModel(List<Randevu> data) {  
        super(data);  
    }  
	
	@SuppressWarnings("unchecked")
	@Override
	public Randevu getRowData(String rowKey) {
		// TODO Auto-generated method stub
		List<Randevu> br = (List<Randevu>) getWrappedData();  
       
        for(Randevu r : br) {  
            if(r.getBasvuru().getBasvuruId()==Integer.parseInt(rowKey))  
                return r;  
        }  
          
        return null;  
	}

	@Override
	public Object getRowKey(Randevu r) {
		// TODO Auto-generated method stub
		return r.getBasvuru().getBasvuruId();
	}

}

