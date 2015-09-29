package tr.org.mmo.asansor.models;





import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;




import tr.org.mmo.asansor.dto.CihazDTO;

public class CihazDataModel extends ListDataModel<CihazDTO> implements SelectableDataModel<CihazDTO>,Serializable {  

    /**
	 * 
	 */
	private static final long serialVersionUID = 2080312815255790747L;


	public CihazDataModel() {
    }

    public CihazDataModel(List<CihazDTO> data) {
        super(data);
    }
   

    @Override
    public Object getRowKey(CihazDTO cihaz) {
        return cihaz.getCihazId();
    }

		 
	    @SuppressWarnings("unchecked")
		@Override
	    public CihazDTO getRowData(String rowKey) {
	        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data
	        
	        List<CihazDTO> cihazlar = (List<CihazDTO>) getWrappedData();
	        
	        for(CihazDTO c : cihazlar) {
	            if(c.getCihazId()==Integer.parseInt(rowKey))
	                return c;
	        }
	        
	        return null;
	   
	}
}
