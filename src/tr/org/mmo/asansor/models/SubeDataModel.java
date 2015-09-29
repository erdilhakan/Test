package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.SubeDTO;


public class SubeDataModel  extends ListDataModel<SubeDTO> implements SelectableDataModel<SubeDTO>,Serializable {    
	  
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public SubeDataModel() {  
    }  
  
    public SubeDataModel(List<SubeDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public SubeDTO getRowData(String rowKey) {  
       
		List<SubeDTO> list = (List<SubeDTO>) getWrappedData();  
          
        for(SubeDTO b : list) {  
            if(b.getKod()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(SubeDTO sube) {  
        return sube.getKod();
    }  
}  
