package tr.org.mmo.asansor.models;



import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.UavtCihaz;




public class UavtCihazDataModel extends ListDataModel<UavtCihaz> implements SelectableDataModel<UavtCihaz>,Serializable {  

    /**
	 * 
	 */
	private static final long serialVersionUID = 2080312815255790747L;


	public UavtCihazDataModel() {
    }

    public UavtCihazDataModel(List<UavtCihaz> data) {
        super(data);
    }
   

    @Override
    public Object getRowKey(UavtCihaz cihaz) {
        return cihaz.getAsansorNo();
    }

		 
	    @SuppressWarnings("unchecked")
		@Override
	    public UavtCihaz getRowData(String rowKey) {
	        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data
	        
	        List<UavtCihaz> cihaz = (List<UavtCihaz>) getWrappedData();
	        
	        for(UavtCihaz u : cihaz) {
	            if(u.getAsansorNo()==Integer.parseInt(rowKey))
	                return u;
	        }
	        
	        return null;
	   
	}
}
