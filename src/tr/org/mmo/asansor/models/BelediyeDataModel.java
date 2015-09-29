package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.BelediyeDTO;



public class BelediyeDataModel  extends ListDataModel<BelediyeDTO> implements SelectableDataModel<BelediyeDTO>,Serializable {    
	  
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public BelediyeDataModel() {  
    }  
  
    public BelediyeDataModel(List<BelediyeDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public BelediyeDTO getRowData(String rowKey) {  
       
		List<BelediyeDTO> belediyeler = (List<BelediyeDTO>) getWrappedData();  
          
        for(BelediyeDTO b : belediyeler) {  
            if(b.getKod()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(BelediyeDTO belediye) {  
        return belediye.getKod();
    }  
}  