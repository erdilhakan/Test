package tr.org.mmo.asansor.validators;

import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import tr.org.mmo.asansor.util.Messages;

@FacesValidator("validators.randevu")
public class RandevuSaatiValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {

		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();

		cal1.setTime(new Date());
		/* Randevu 3. gün öncesine kadar verilebiliyor. */
		cal1.set(Calendar.DAY_OF_YEAR, cal1.get(Calendar.DAY_OF_YEAR) - 4);

		cal.setTime((Date) arg2);
		if (cal.getTimeInMillis() < cal1.getTimeInMillis()) {
			FacesMessage msg = new FacesMessage(
					Messages._RANDEVUTARIHIHATALI_.getMesaj());
			throw new ValidatorException(msg);
		} else {
			if (cal.get(Calendar.HOUR_OF_DAY) < 8
					|| cal.get(Calendar.HOUR_OF_DAY) > 21) {
				FacesMessage msg = new FacesMessage(
						Messages._RANDEVUSAATIHATALI_.getMesaj());
				throw new ValidatorException(msg);
			}
		}

	}

}
