package tr.org.mmo.asansor.validators;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.autocomplete.AutoComplete;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.managedbeans.BinaBean;
import tr.org.mmo.asansor.managedbeans.LoginBean;
import tr.org.mmo.asansor.managedbeans.SessionBean;

@FacesConverter("binalar")
public class BinaConverter implements Converter {
	
	private BinaBean binaBean=Common.findBean("binaBean");
	
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String submittedValue) {
		int number = 0;

		if (submittedValue == null) {
			number = Integer.parseInt(((AutoComplete) component)
					.getSubmittedValue().toString());
		} else {

			if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
					.equals("/kontrol.xhtml")
					&& Integer.parseInt(((AutoComplete) component)
							.getSubmittedValue().toString()) == 0) {
				
				number = binaBean.getBinaId();
			} else {
				number = Integer.parseInt(submittedValue);
			}

		}

		if (((LoginBean) Common.findBean("loginBean")).isLoggedIn()) {
			for (BinaDTO b : ((LoginBean) Common.findBean("loginBean"))
					.getTumBinalar()) {
				if (b.getBinaId() == number) {
					return b;
				}
			}
		} else {
			for (BinaDTO b : ((SessionBean) Common.findBean("sessionBean"))
					.getTumBinalar()) {
				if (b.getBinaId() == number) {
					return b;
				}
			}
		}
		return new BinaDTO();

	}

	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((BinaDTO) value).getBinaId());
		}
	}

	public BinaBean getBinaBean() {
		return binaBean;
	}

	public void setBinaBean(BinaBean binaBean) {
		this.binaBean = binaBean;
	}

}
