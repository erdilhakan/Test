package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.TemsilcilikDTO;


public class TemsilcilikDataModel  extends ListDataModel<TemsilcilikDTO> implements SelectableDataModel<TemsilcilikDTO>,Serializable {    
	  
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public TemsilcilikDataModel() {  
    }  
  
    public TemsilcilikDataModel(List<TemsilcilikDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public TemsilcilikDTO getRowData(String rowKey) {  
       
		List<TemsilcilikDTO> list = (List<TemsilcilikDTO>) getWrappedData();  
          
        for(TemsilcilikDTO b : list) {  
            if(b.getKod()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(TemsilcilikDTO temsilcilik) {  
        return temsilcilik.getKod();
    }  
}  
