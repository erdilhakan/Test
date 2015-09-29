package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.beans_.Kullanici;

public class KullaniciDataModel extends ListDataModel<Kullanici> implements SelectableDataModel<Kullanici>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3866093272758815099L;

	
	public KullaniciDataModel() {  
    }  
  
    public KullaniciDataModel(List<Kullanici> data) {  
        super(data);  
    }  
	
	@SuppressWarnings("unchecked")
	@Override
	public Kullanici getRowData(String rowKey) {
		// TODO Auto-generated method stub
		List<Kullanici> kullanicilar = (List<Kullanici>) getWrappedData();  
        
        for(Kullanici k : kullanicilar) {  
            if(k.getKullaniciId()==Integer.parseInt(rowKey))  
                return k;  
        }  
          
        return null;  
	}

	@Override
	public Object getRowKey(Kullanici k) {
		// TODO Auto-generated method stub
		return k.getKullaniciId();
	}

}
