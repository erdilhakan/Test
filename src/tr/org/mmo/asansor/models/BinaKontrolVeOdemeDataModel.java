package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.BinaKontrolVeOdemelerDTO;



public class BinaKontrolVeOdemeDataModel  extends ListDataModel<BinaKontrolVeOdemelerDTO> implements SelectableDataModel<BinaKontrolVeOdemelerDTO>,Serializable {    
	  
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public BinaKontrolVeOdemeDataModel() {  
    }  
  
    public BinaKontrolVeOdemeDataModel(List<BinaKontrolVeOdemelerDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public BinaKontrolVeOdemelerDTO getRowData(String rowKey) {  
       
		List<BinaKontrolVeOdemelerDTO> list = (List<BinaKontrolVeOdemelerDTO>) getWrappedData();  
          
        for(BinaKontrolVeOdemelerDTO b : list) {  
            if(b.getKontrolId()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(BinaKontrolVeOdemelerDTO arg) {  
        return arg.getKontrolId();
    }  
}