package tr.org.mmo.asansor.models;






import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.beans_.Istatistik;






public class IstatistikDataBasvuruModel extends ListDataModel<Istatistik> implements SelectableDataModel<Istatistik>,Serializable {  

    /**
	 * 
	 */
	private static final long serialVersionUID = 2080312815255790747L;


	public IstatistikDataBasvuruModel() {
    }

    public IstatistikDataBasvuruModel(List<Istatistik> data) {
        super(data);
    }
   

    @Override
    public Object getRowKey(Istatistik rapor) {
        return rapor.getBasvuruId();
    }

		 
	    @Override
	    public Istatistik getRowData(String rowKey) {
	        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data
	        
	        @SuppressWarnings("unchecked")
			List<Istatistik> raporlar = (List<Istatistik>) getWrappedData();
	        
	        for(Istatistik r : raporlar) {
	            if(r.getBasvuruId()==Integer.parseInt(rowKey))
	                return r;
	        }
	        
	        return null;
	   
	}
}

