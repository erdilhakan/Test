package tr.org.mmo.asansor.validators;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import tr.org.mmo.asansor.util.Messages;
@FacesValidator("validators.mustGreatThanZero")
public class NumberMustGreatThanZero implements Validator{



	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		if (Integer.parseInt(arg2.toString().trim())<=0) {
			
			FacesMessage msg = arg1.getId().equals("il")?new FacesMessage(Messages._ILHATALI_.getMesaj()):(arg1.getId().equals("ilce")?new FacesMessage(Messages._ILILCEHATALI_.getMesaj()):new FacesMessage(Messages._UAVTKODBOSOLAMAZ_.getMesaj()));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
	}


}
