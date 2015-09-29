package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.beans_.Kullanici;



public class MuhendisDataModel extends ListDataModel<Kullanici> implements SelectableDataModel<Kullanici>,Serializable {    
	  
    /**
	 * 
	 */
	private static final long serialVersionUID = -6291945592170468398L;

	public MuhendisDataModel() {  
    }  
  
    public MuhendisDataModel(List<Kullanici> data) {  
        super(data);  
    }  
      
    @Override
    @SuppressWarnings("unchecked")
    public Kullanici getRowData(String rowKey) {  
      Kullanici k=new Kullanici();
		List<Kullanici> muhendisler = (List<Kullanici>) getWrappedData();  
         try{ 
        	 k=new Kullanici();
        for(Kullanici m : muhendisler) {  
            if(Integer.parseInt(m.getSicilNo())==Integer.parseInt(rowKey))  
                k=(Kullanici)m.clone();
          
        	}  
        }
         catch(Exception e){
        	
        	k=new Kullanici();
        }
         return k;
      
    }  
  
    @Override  
    public Object getRowKey(Kullanici m) {  
        return m.getSicilNo();  
    }  
}  