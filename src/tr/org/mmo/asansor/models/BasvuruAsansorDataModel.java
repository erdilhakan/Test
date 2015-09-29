package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;





public class BasvuruAsansorDataModel  extends ListDataModel<BasvuruAsansorDTO> implements SelectableDataModel<BasvuruAsansorDTO>,Serializable {    
	  
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public BasvuruAsansorDataModel() {  
    }  
  
    public BasvuruAsansorDataModel(List<BasvuruAsansorDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public BasvuruAsansorDTO getRowData(String rowKey) {  
       
		List<BasvuruAsansorDTO> list = (List<BasvuruAsansorDTO>) getWrappedData();  
          
        for(BasvuruAsansorDTO b : list) {  
            if(b.getCihazId()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(BasvuruAsansorDTO asansor) {  
        return asansor.getCihazId();
    }  
}  