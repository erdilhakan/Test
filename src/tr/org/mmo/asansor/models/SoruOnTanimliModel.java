package tr.org.mmo.asansor.models;





import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.SoruOnTanimliDTO;





public class SoruOnTanimliModel extends ListDataModel<SoruOnTanimliDTO> implements SelectableDataModel<SoruOnTanimliDTO>,Serializable {  

    /**
	 * 
	 */
	private static final long serialVersionUID = 2080312815255790747L;


	public SoruOnTanimliModel() {
    }

    public SoruOnTanimliModel(List<SoruOnTanimliDTO> data) {
        super(data);
    }
   

    @Override
    public Object getRowKey(SoruOnTanimliDTO arg0) {
        return arg0.getId();
    }

		 
	    @SuppressWarnings("unchecked")
		@Override
	    public SoruOnTanimliDTO getRowData(String rowKey) {
	        List<SoruOnTanimliDTO> list = (List<SoruOnTanimliDTO>) getWrappedData();
	        
	        for(SoruOnTanimliDTO l : list) {
	            if(l.getId()==Integer.parseInt(rowKey))
	                return l;
	        }
	        
	        return null;
	   
	}
}
