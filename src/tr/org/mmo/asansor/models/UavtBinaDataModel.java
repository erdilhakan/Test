package tr.org.mmo.asansor.models;



import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.beans_.UavtBina;

public class UavtBinaDataModel extends ListDataModel<UavtBina> implements SelectableDataModel<UavtBina>,Serializable {  

    /**
	 * 
	 */
	private static final long serialVersionUID = 2080312815255790747L;


	public UavtBinaDataModel() {
    }

    public UavtBinaDataModel(List<UavtBina> data) {
        super(data);
    }
   

    @Override
    public Object getRowKey(UavtBina bina) {
        return bina.getKod();
    }

		 
	    @SuppressWarnings("unchecked")
		@Override
	    public UavtBina getRowData(String rowKey) {
	        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data
	        
	        List<UavtBina> bina = (List<UavtBina>) getWrappedData();
	        
	        for(UavtBina b : bina) {
	            if(b.getKod()==Integer.parseInt(rowKey))
	                return b;
	        }
	        
	        return null;
	   
	}
}
