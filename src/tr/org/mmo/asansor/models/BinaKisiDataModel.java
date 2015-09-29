package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;



import org.primefaces.model.SelectableDataModel;


import tr.org.mmo.asansor.dto.BinaKisiDTO;

public class BinaKisiDataModel  extends ListDataModel<BinaKisiDTO> implements SelectableDataModel<BinaKisiDTO>,Serializable {    
	  
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public BinaKisiDataModel() {  
  }  

  public BinaKisiDataModel(List<BinaKisiDTO> data) {  
      super(data);  
  }  
    
  @Override
  @SuppressWarnings("unchecked")
  public BinaKisiDTO getRowData(String rowKey) {  
     
		List<BinaKisiDTO> kisiler = (List<BinaKisiDTO>) getWrappedData();  
        
      for(BinaKisiDTO b : kisiler) {  
          if(b.getId()==Integer.parseInt(rowKey))  
              return b;  
      }  
        
      return null;  
  }  

  @Override  
  public Object getRowKey(BinaKisiDTO kisi) {  
      return kisi.getId();
  }  
}  
