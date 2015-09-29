package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.beans_.Duyuru;


public class DuyuruModel  extends ListDataModel<Duyuru> implements SelectableDataModel<Duyuru>,Serializable {    
	  
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public DuyuruModel() {  
    }  
  
    public DuyuruModel(List<Duyuru> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public Duyuru getRowData(String rowKey) {  
       
		List<Duyuru> list = (List<Duyuru>) getWrappedData();  
          
        for(Duyuru b : list) {  
            if(b.getId()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(Duyuru duyuru) {  
        return duyuru.getId();
    }  
}  
