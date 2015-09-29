package tr.org.mmo.asansor.models;






import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.RaporDTO;





public class RaporDataModel extends ListDataModel<RaporDTO> implements SelectableDataModel<RaporDTO>,Serializable {  

    /**
	 * 
	 */
	private static final long serialVersionUID = 2080312815255790747L;


	public RaporDataModel() {
    }

    public RaporDataModel(List<RaporDTO> data) {
        super(data);
    }
   

    @Override
    public Object getRowKey(RaporDTO rapor) {
        return rapor.getRaporId();
    }

		 
	    @Override
	    public RaporDTO getRowData(String rowKey) {
	        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data
	        
	        @SuppressWarnings("unchecked")
			List<RaporDTO> raporlar = (List<RaporDTO>) getWrappedData();
	        
	        for(RaporDTO r : raporlar) {
	            if(r.getRaporId()==Integer.parseInt(rowKey))
	                return r;
	        }
	        
	        return null;
	   
	}
}

