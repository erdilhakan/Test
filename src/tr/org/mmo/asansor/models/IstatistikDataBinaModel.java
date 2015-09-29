
package tr.org.mmo.asansor.models;






import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.beans_.Istatistik;






public class IstatistikDataBinaModel extends ListDataModel<Istatistik> implements SelectableDataModel<Istatistik>,Serializable {  

    /**
	 * 
	 */
	private static final long serialVersionUID = 2080312815255790747L;


	public IstatistikDataBinaModel() {
    }

    public IstatistikDataBinaModel(List<Istatistik> data) {
        super(data);
    }
   

    @Override
    public Object getRowKey(Istatistik bina) {
        return bina.getBinaId();
    }

		 
	    @Override
	    public Istatistik getRowData(String rowKey) {
	        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data
	        
	        @SuppressWarnings("unchecked")
			List<Istatistik> binalar = (List<Istatistik>) getWrappedData();
	        
	        for(Istatistik r : binalar) {
	            if(r.getBinaId()==Integer.parseInt(rowKey))
	                return r;
	        }
	        
	        return null;
	   
	}
}


