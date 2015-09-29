package tr.org.mmo.asansor.validators;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import tr.org.mmo.asansor.util.Messages;

@FacesValidator("validators.odemeValidator")
public class OdemeValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {

		String viewID = FacesContext.getCurrentInstance().getViewRoot()
				.getViewId();
		UIInput uic = null;
		if (new BigDecimal(arg2.toString()).doubleValue() <= 0) {
			throw new ValidatorException(new FacesMessage(
					"Ödeme tutarı giriniz"));

		} else {
			if (!viewID.equals("/odemeGiris.xhtml")) {
				if (viewID.equals("/basvuruListesi.xhtml")) {
					uic = (UIInput) FacesContext.getCurrentInstance()
							.getViewRoot()
							.findComponent(":formbasvuruliste:kontrolTutari");
				}
				if (viewID.equals("/odemeGiris.xhtml")) {
					uic = (UIInput) FacesContext.getCurrentInstance()
							.getViewRoot()
							.findComponent(":formodeme:kontrolTutari");
				}
				if (uic != null) {

					if (new BigDecimal(arg2.toString()).doubleValue() > new BigDecimal(
							uic.getValue().toString()).doubleValue()) {

						FacesMessage msg = new FacesMessage(
								Messages._ODEMETUTARIFAZLA_.getMesaj());
						throw new ValidatorException(msg);
					}
				} else {
					FacesMessage msg = new FacesMessage(
							Messages._GENEL_.getMesaj());
					throw new ValidatorException(msg);

				}
			}
		}

	}
}