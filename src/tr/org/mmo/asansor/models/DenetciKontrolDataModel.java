package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.DenetciKontrolDTO;






public class DenetciKontrolDataModel  extends ListDataModel<DenetciKontrolDTO> implements SelectableDataModel<DenetciKontrolDTO>,Serializable {    
	  
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public DenetciKontrolDataModel() {  
    }  
  
    public DenetciKontrolDataModel(List<DenetciKontrolDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public DenetciKontrolDTO getRowData(String rowKey) {  
       
		List<DenetciKontrolDTO> list = (List<DenetciKontrolDTO>) getWrappedData();  
          
        for(DenetciKontrolDTO b : list) {  
            if(b.getMuhendisSicilNo()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(DenetciKontrolDTO arg) {  
        return arg.getMuhendisSicilNo();
    }  
}  
