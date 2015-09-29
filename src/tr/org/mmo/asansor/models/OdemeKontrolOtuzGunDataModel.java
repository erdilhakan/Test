package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.OdemeKontrolOtuzGunDTO;


public class OdemeKontrolOtuzGunDataModel extends ListDataModel<OdemeKontrolOtuzGunDTO> implements SelectableDataModel<OdemeKontrolOtuzGunDTO>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6944770378275158513L;
	
			public OdemeKontrolOtuzGunDataModel() {  
    }  
  
    public OdemeKontrolOtuzGunDataModel(List<OdemeKontrolOtuzGunDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public OdemeKontrolOtuzGunDTO getRowData(String rowKey) {  
       
		List<OdemeKontrolOtuzGunDTO> list = (List<OdemeKontrolOtuzGunDTO>) getWrappedData();  
          
        for(OdemeKontrolOtuzGunDTO b : list) {  
            if(b.getBelediyeKod()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(OdemeKontrolOtuzGunDTO arg) {  
        return arg.getBelediyeKod();
    }  		
			
}
