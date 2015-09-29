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


	@FacesValidator("validators.dateValidator")
	public class DateValidator implements Validator{
		private static final String regex="^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
		private Pattern pattern;
		private Matcher matcher;
		public DateValidator() {
			pattern = Pattern.compile(regex);
		}
		@Override
		public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
					throws ValidatorException {
			
			if (arg2!=null && arg2.toString()!=null && arg2.toString().trim().length()>0){
				matcher = pattern.matcher(arg2.toString());
				if (!matcher.matches()) {
					FacesMessage msg = new FacesMessage(Messages._HATALITARIH_.getMesaj());
					throw new ValidatorException(msg);
				}

			}
					
					
				
				


		}
	}
