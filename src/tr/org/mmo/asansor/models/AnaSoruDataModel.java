package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.beans_.AnaSoru;


public class AnaSoruDataModel  extends ListDataModel<AnaSoru> implements SelectableDataModel<AnaSoru>,Serializable {    
	  
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public AnaSoruDataModel() {  
    }  
  
    public AnaSoruDataModel(List<AnaSoru> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public AnaSoru getRowData(String rowKey) {  
       
		List<AnaSoru> list = (List<AnaSoru>) getWrappedData();  
          
        for(AnaSoru b : list) {  
            if(b.getSoruId()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(AnaSoru asansor) {  
        return asansor.getSoruId();
    }  
}  
