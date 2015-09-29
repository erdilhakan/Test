package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.BelediyeKontrolDTO;
import tr.org.mmo.asansor.dto.DenetciKontrolDTO;






public class BelediyeKontrolDataModel  extends ListDataModel<BelediyeKontrolDTO> implements SelectableDataModel<BelediyeKontrolDTO>,Serializable {    
	  
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public BelediyeKontrolDataModel() {  
    }  
  
    public BelediyeKontrolDataModel(List<BelediyeKontrolDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public BelediyeKontrolDTO getRowData(String rowKey) {  
       
		List<BelediyeKontrolDTO> list = (List<BelediyeKontrolDTO>) getWrappedData();  
          
        for(BelediyeKontrolDTO b : list) {  
            if(b.getBelediyeKod()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(BelediyeKontrolDTO arg) {  
        return arg.getBelediyeKod();
    }  
}  
