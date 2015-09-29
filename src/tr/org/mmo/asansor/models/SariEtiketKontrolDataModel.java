package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.SariEtiketKontrolDTO;


public class SariEtiketKontrolDataModel extends ListDataModel<SariEtiketKontrolDTO> implements SelectableDataModel<SariEtiketKontrolDTO>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6944770378275158513L;
	
			public SariEtiketKontrolDataModel() {  
    }  
  
    public SariEtiketKontrolDataModel(List<SariEtiketKontrolDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public SariEtiketKontrolDTO getRowData(String rowKey) {  
       
		List<SariEtiketKontrolDTO> list = (List<SariEtiketKontrolDTO>) getWrappedData();  
          
        for(SariEtiketKontrolDTO b : list) {  
            if(b.getBinaId()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(SariEtiketKontrolDTO arg) {  
        return arg.getBinaId();
    }  		
			
}
