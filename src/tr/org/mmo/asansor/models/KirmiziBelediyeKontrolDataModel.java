package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.KirmiziBelediyeKontrolDTO;


public class KirmiziBelediyeKontrolDataModel extends ListDataModel<KirmiziBelediyeKontrolDTO> implements SelectableDataModel<KirmiziBelediyeKontrolDTO>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6944770378275158513L;
	
			public KirmiziBelediyeKontrolDataModel() {  
    }  
  
    public KirmiziBelediyeKontrolDataModel(List<KirmiziBelediyeKontrolDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public KirmiziBelediyeKontrolDTO getRowData(String rowKey) {  
       
		List<KirmiziBelediyeKontrolDTO> list = (List<KirmiziBelediyeKontrolDTO>) getWrappedData();  
          
        for(KirmiziBelediyeKontrolDTO b : list) {  
            if(b.getBinaId()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(KirmiziBelediyeKontrolDTO arg) {  
        return arg.getBinaId();
    }  		
			
}
