package tr.org.mmo.asansor.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import tr.org.mmo.asansor.dto.RolYetkiDTO;
import tr.org.mmo.asansor.managedbeans.RolBean;

@FacesConverter("rolConverter")
public class RolConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				RolBean rb = (RolBean) fc.getExternalContext().getRequestMap()
						.get("rolBean");
				return rb.getRolListesi().get(Integer.parseInt(value));
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Dönüştürme Hatası",
						"Hatalı rol id"));
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((RolYetkiDTO) object).getId());
		} else {
			return null;
		}
	}

}
