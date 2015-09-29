package tr.org.mmo.asansor.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.TCKimlikKontrol;
@FacesValidator("validators.tcKimlikValidator")
public class TCKimlikValidator implements Validator{

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		if (!TCKimlikKontrol.tcKimlikDogruMu(arg2.toString().trim())) {
			FacesMessage msg = new FacesMessage(Messages._GECERSIZTCKIMLIKNO_.getMesaj());
			throw new ValidatorException(msg);
		}
		
	}

}
