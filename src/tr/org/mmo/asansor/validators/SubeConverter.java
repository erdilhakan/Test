package tr.org.mmo.asansor.validators;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import tr.org.mmo.asansor.dto.SubeDTO;

import tr.org.mmo.asansor.managedbeans.SessionBean;
 
@FacesConverter("subeler")
public class SubeConverter implements Converter {
 
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                SessionBean service = (SessionBean) fc.getExternalContext().getSessionMap().get("sessionBean");
                return service.getSubeler().get(Integer.parseInt(value));
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dönüştürme Hatası", "Hatalı Şube."));
            }
        }
        else {
            return null;
        }
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SubeDTO) object).getKod());
        }
        else {
            return null;
        }
    }   
} 
