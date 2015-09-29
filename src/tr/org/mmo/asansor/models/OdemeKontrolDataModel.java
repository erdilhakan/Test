package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.OdemeKontrolDTO;


public class OdemeKontrolDataModel extends ListDataModel<OdemeKontrolDTO> implements SelectableDataModel<OdemeKontrolDTO>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6944770378275158513L;
	
			public OdemeKontrolDataModel() {  
    }  
  
    public OdemeKontrolDataModel(List<OdemeKontrolDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public OdemeKontrolDTO getRowData(String rowKey) {  
       
		List<OdemeKontrolDTO> list = (List<OdemeKontrolDTO>) getWrappedData();  
          
        for(OdemeKontrolDTO b : list) {  
            if(b.getBelediyeKod()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(OdemeKontrolDTO arg) {  
        return arg.getBelediyeKod();
    }  		
			
}
