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
@FacesValidator("validators.ip")
public class IPValidator implements Validator{



	private static final String IP_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	private Pattern pattern;
	private Matcher matcher;

	public IPValidator() {
		pattern = Pattern.compile(IP_PATTERN);
	}

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		if (arg2!=null && arg2.toString()!=null && arg2.toString().trim().length()>0){
		matcher = pattern.matcher(arg2.toString());
		if (!matcher.matches()) {
			FacesMessage msg = new FacesMessage(Messages._GECERSIZIP_.getMesaj());
			throw new ValidatorException(msg);
		}

	}
	}





}
