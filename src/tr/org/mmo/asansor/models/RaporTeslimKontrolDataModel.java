package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.RaporTeslimKontrolDTO;


public class RaporTeslimKontrolDataModel extends ListDataModel<RaporTeslimKontrolDTO> implements SelectableDataModel<RaporTeslimKontrolDTO>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6944770378275158513L;
	
			public RaporTeslimKontrolDataModel() {  
    }  
  
    public RaporTeslimKontrolDataModel(List<RaporTeslimKontrolDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public RaporTeslimKontrolDTO getRowData(String rowKey) {  
       
		List<RaporTeslimKontrolDTO> list = (List<RaporTeslimKontrolDTO>) getWrappedData();  
          
        for(RaporTeslimKontrolDTO b : list) {  
            if(b.getBinaId()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(RaporTeslimKontrolDTO arg) {  
        return arg.getRaporId();
    }  		
			
}
