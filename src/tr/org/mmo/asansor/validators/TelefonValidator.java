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
@FacesValidator("validators.TelefonValidator")
public class TelefonValidator implements Validator{

        //private static final String TELEFON_PATTERN =   "\\([1-9]{1}\\d\\d\\)([\\s])?\\d\\d\\d([\\s])?\\d\\d\\d\\d";
        private static final String TELEFON_PATTERN =   "\\([1-9]{1}\\d{2}\\)([\\s])?(\\d{3})([\\s])?(\\d{4})";
        private Pattern pattern;
        private Matcher matcher;

        public TelefonValidator() {
                pattern = Pattern.compile(TELEFON_PATTERN);
        }

        @Override
        public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
                        throws ValidatorException {
        	 FacesMessage msg = new FacesMessage(Messages._GECERSIZTELEFONNUMARASI_.getMesaj());
        		if (arg2.toString().replace("(", "").replace(")","").replace(" ","").trim().length()<10){
                    throw new ValidatorException(msg);
        		}else{
                matcher = pattern.matcher(arg2.toString());
                if (!matcher.matches()) {
                       
                        throw new ValidatorException(msg);
                }
        		}

        }

}
