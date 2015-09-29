package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.CihazOdemeKontrolDTO;


public class CihazOdemeKontrolDataModel extends ListDataModel<CihazOdemeKontrolDTO> implements 

SelectableDataModel<CihazOdemeKontrolDTO>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6944770378275158513L;
	
			public CihazOdemeKontrolDataModel() {  
    }  
  
    public CihazOdemeKontrolDataModel(List<CihazOdemeKontrolDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public CihazOdemeKontrolDTO getRowData(String rowKey) {  
       
		List<CihazOdemeKontrolDTO> list = (List<CihazOdemeKontrolDTO>) getWrappedData();  
          
        for(CihazOdemeKontrolDTO b : list) {  
            if(b.getBelediyeKod()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(CihazOdemeKontrolDTO arg) {  
        return arg.getBelediyeKod();
    }  		
			
}