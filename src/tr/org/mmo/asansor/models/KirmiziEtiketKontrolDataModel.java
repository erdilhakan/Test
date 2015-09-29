package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.KirmiziEtiketKontrolDTO;


public class KirmiziEtiketKontrolDataModel extends ListDataModel<KirmiziEtiketKontrolDTO> implements SelectableDataModel<KirmiziEtiketKontrolDTO>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6944770378275158513L;
	
			public KirmiziEtiketKontrolDataModel() {  
    }  
  
    public KirmiziEtiketKontrolDataModel(List<KirmiziEtiketKontrolDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public KirmiziEtiketKontrolDTO getRowData(String rowKey) {  
       
		List<KirmiziEtiketKontrolDTO> list = (List<KirmiziEtiketKontrolDTO>) getWrappedData();  
          
        for(KirmiziEtiketKontrolDTO b : list) {  
            if(b.getBinaId()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(KirmiziEtiketKontrolDTO arg) {  
        return arg.getBinaId();
    }  		
			
}
