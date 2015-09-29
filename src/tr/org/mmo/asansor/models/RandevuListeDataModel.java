package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.RandevuListeDTO;





public class RandevuListeDataModel  extends ListDataModel<RandevuListeDTO> implements SelectableDataModel<RandevuListeDTO>,Serializable {    
	  
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public RandevuListeDataModel() {  
    }  
  
    public RandevuListeDataModel(List<RandevuListeDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public RandevuListeDTO getRowData(String rowKey) {  
       
		List<RandevuListeDTO> list = (List<RandevuListeDTO>) getWrappedData();  
          
        for(RandevuListeDTO b : list) {  
            if(b.getRandevuId()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(RandevuListeDTO r) {  
        return r.getRandevuId();
    }  
}  