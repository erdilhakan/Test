package tr.org.mmo.asansor.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import tr.org.mmo.asansor.util.Messages;
@FacesValidator("validators.belediye")
public class BelediyeValidator implements Validator{

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		if (Integer.parseInt(arg2.toString().trim())<=0) {
			FacesMessage msg = new FacesMessage(Messages._BELEDIYESECILMEDI_.getMesaj());
			throw new ValidatorException(msg);
		}
		
	}



}
