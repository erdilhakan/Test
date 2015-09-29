package tr.org.mmo.asansor.validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.autocomplete.AutoComplete;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.managedbeans.LoginBean;




@FacesConverter("kullanicilar")
public class KullaniciConverter implements Converter {
		
	   
   
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) { 
     int number=0;
    	if (submittedValue==null){
    		number=Integer.parseInt(((AutoComplete)component).getSubmittedValue().toString());
    	}else{
    		number=Integer.parseInt(submittedValue);
    	}
    	
    
    	
        for (Kullanici k:((LoginBean)Common.findBean("loginBean")).getMuhendisList()){
        	if (k.getKullaniciId()==number){
        		return k;
        	}
        }
        return submittedValue;  
        
    }  
  
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
    	if (value == null || value.equals("")) {  
            return "";  
        } else {  
        	return String.valueOf(((Kullanici) value).getKullaniciId());  
        }  
    }
    
}

