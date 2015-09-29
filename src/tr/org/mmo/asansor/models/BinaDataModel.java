package tr.org.mmo.asansor.models;



import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;




import tr.org.mmo.asansor.dto.BinaDTO;

public class BinaDataModel extends ListDataModel<BinaDTO> implements SelectableDataModel<BinaDTO>,Serializable {  

    /**
	 * 
	 */
	private static final long serialVersionUID = 2080312815255790747L;


	public BinaDataModel() {
    }

    public BinaDataModel(List<BinaDTO> data) {
        super(data);
    }
   

    @Override
    public Object getRowKey(BinaDTO bina) {
        return bina.getBinaId();
    }

		 
	    @SuppressWarnings("unchecked")
		@Override
	    public BinaDTO getRowData(String rowKey) {
	        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data
	        
	        List<BinaDTO> bina = (List<BinaDTO>) getWrappedData();
	        if (bina!=null){
	        for(BinaDTO b : bina) {
	            if(b.getBinaId()==Integer.parseInt(rowKey))
	                return b;
	        }
	        }
	        
	        return null;
	   
	}
}
