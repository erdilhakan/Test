package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.KontrolDTO;



public class KontrolDataModel extends ListDataModel<KontrolDTO> implements SelectableDataModel<KontrolDTO> ,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1059004692492359406L;

	
	public KontrolDataModel() {  
    }  
  
    public KontrolDataModel(List<KontrolDTO> data) {  
        super(data);  
    }  
	
	@SuppressWarnings("unchecked")
	@Override
	public KontrolDTO getRowData(String rowKey) {
		List<KontrolDTO> kontroller = (List<KontrolDTO>) getWrappedData();  
        
        for(KontrolDTO k : kontroller) {  
            if(k.getKontrolId()==Integer.parseInt(rowKey))  
                return k;  
        }  
        return null;  
	}

	@Override
	public Object getRowKey(KontrolDTO kontrol) {
		// TODO Auto-generated method stub
		return kontrol.getKontrolId();
	}

}
