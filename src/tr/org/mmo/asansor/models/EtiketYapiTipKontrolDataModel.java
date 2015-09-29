package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.EtiketYapiTipKontrolDTO;






public class EtiketYapiTipKontrolDataModel  extends ListDataModel<EtiketYapiTipKontrolDTO> implements SelectableDataModel<EtiketYapiTipKontrolDTO>,Serializable {    
	  
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public EtiketYapiTipKontrolDataModel() {  
    }  
  
    public EtiketYapiTipKontrolDataModel(List<EtiketYapiTipKontrolDTO> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public EtiketYapiTipKontrolDTO getRowData(String rowKey) {  
       
		List<EtiketYapiTipKontrolDTO> list = (List<EtiketYapiTipKontrolDTO>) getWrappedData();  
          
        for(EtiketYapiTipKontrolDTO b : list) {  
            if(b.getBelediyeKod()==Integer.parseInt(rowKey))  
                return b;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(EtiketYapiTipKontrolDTO arg) {  
        return arg.getBelediyeKod();
    }  
}  
