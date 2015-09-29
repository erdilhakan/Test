package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;


import tr.org.mmo.asansor.dto.BelediyeSozlesmeDTO;



public class BelediyeSozlesmeModel  extends ListDataModel<BelediyeSozlesmeDTO> implements SelectableDataModel<BelediyeSozlesmeDTO>,Serializable {    
	  
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public BelediyeSozlesmeModel() {  
    }  
  
    public BelediyeSozlesmeModel(List<BelediyeSozlesmeDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public BelediyeSozlesmeDTO getRowData(String rowKey) {  
       
		List<BelediyeSozlesmeDTO> sozlesmeler = (List<BelediyeSozlesmeDTO>) getWrappedData();  
          
        for(BelediyeSozlesmeDTO s : sozlesmeler) {  
            if(s.getId()==Integer.parseInt(rowKey))  
                return s;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(BelediyeSozlesmeDTO sozlesme) {  
        return sozlesme.getId();
    }  
}  