package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.SoruDTO;

public class SoruDataModel  extends ListDataModel<SoruDTO> implements SelectableDataModel<SoruDTO>,Serializable {    
	  
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public SoruDataModel() {  
    }  
  
    public SoruDataModel(List<SoruDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public SoruDTO getRowData(String rowKey) {  
       
		List<SoruDTO> list = (List<SoruDTO>) getWrappedData();  
          
        for(SoruDTO b : list) {  
            if(b.getSoruId()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(SoruDTO asansor) {  
        return asansor.getSoruId();
    }  
}  
