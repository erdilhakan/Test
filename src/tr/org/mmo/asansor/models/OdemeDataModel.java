package tr.org.mmo.asansor.models;
import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.OdemeDTO;




public class OdemeDataModel  extends ListDataModel<OdemeDTO> implements SelectableDataModel<OdemeDTO>,Serializable {    
	  
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public OdemeDataModel() {  
    }  
  
    public OdemeDataModel(List<OdemeDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public OdemeDTO getRowData(String rowKey) {  
       
		List<OdemeDTO> list = (List<OdemeDTO>) getWrappedData();  
          
        for(OdemeDTO b : list) {  
            if(b.getId()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(OdemeDTO odeme) {  
        return odeme.getId();
    }  
}  
