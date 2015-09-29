package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;



import tr.org.mmo.asansor.dto.FirmaDTO;

public class FirmaDataModel extends ListDataModel<FirmaDTO> implements SelectableDataModel<FirmaDTO> ,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1059004692492359406L;

	
	public FirmaDataModel() {  
    }  
  
    public FirmaDataModel(List<FirmaDTO> data) {  
        super(data);  
    }  
	
	@SuppressWarnings("unchecked")
	@Override
	public FirmaDTO getRowData(String rowKey) {
		List<FirmaDTO> firmalar = (List<FirmaDTO>) getWrappedData();  
        
        for(FirmaDTO b : firmalar) {  
            if(b.getKod()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
	}

	@Override
	public Object getRowKey(FirmaDTO firma) {
		// TODO Auto-generated method stub
		return firma.getKod();
	}

}
