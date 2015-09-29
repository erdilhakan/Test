package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.BasvuruListeDTO;





public class BasvuruListeModel  extends ListDataModel<BasvuruListeDTO> implements SelectableDataModel<BasvuruListeDTO>,Serializable {    
	  
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public BasvuruListeModel() {  
    }  
  
    public BasvuruListeModel(List<BasvuruListeDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public BasvuruListeDTO getRowData(String rowKey) {  
       
		List<BasvuruListeDTO> basvurular = (List<BasvuruListeDTO>) getWrappedData();  
          
        for(BasvuruListeDTO b : basvurular) {  
            if(b.getBasvuruId()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(BasvuruListeDTO basvuru) {  
        return basvuru.getBasvuruId();
    }  
}  