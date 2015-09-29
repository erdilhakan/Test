package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.KontrolHaberDTO;

public class KontrolHaberDataModel extends ListDataModel<KontrolHaberDTO> implements SelectableDataModel<KontrolHaberDTO> ,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1059004692492359406L;

	
	public KontrolHaberDataModel() {  
    }  
  
    public KontrolHaberDataModel(List<KontrolHaberDTO> data) {  
        super(data);  
    }  
	
	@SuppressWarnings("unchecked")
	@Override
	public KontrolHaberDTO getRowData(String rowKey) {
		List<KontrolHaberDTO> binalar = (List<KontrolHaberDTO>) getWrappedData();  
        
        for(KontrolHaberDTO b : binalar) {  
            if(b.getBinaId()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
	}

	@Override
	public Object getRowKey(KontrolHaberDTO bina) {
		// TODO Auto-generated method stub
		return bina.getBinaId();
	}

}
