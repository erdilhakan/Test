package tr.org.mmo.asansor.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import tr.org.mmo.asansor.util.Messages;

@FacesValidator("validators.EMailValidator")
public class EMailValidator implements Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern pattern;
	private Matcher matcher;

	public EMailValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		if (arg2!=null && arg2.toString()!=null && arg2.toString().trim().length()>0){
		matcher = pattern.matcher(arg2.toString());
		if (!matcher.matches()) {
			FacesMessage msg = new FacesMessage(Messages._GECERSIZEPOSTA_.getMesaj());
			throw new ValidatorException(msg);
		}

	}
	}

}
