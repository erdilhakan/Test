package tr.org.mmo.asansor.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.IntegerConverter;

import tr.org.mmo.asansor.util.Messages;

@FacesConverter("digitsOnly")
public class DigitsOnlyConverter extends IntegerConverter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        if (!value.matches("\\d*")) {
        	FacesMessage msg = new FacesMessage(Messages._DIGITSONLY_.getMesaj()+"( "+component.getId()+" )");
			msg.setSeverity(FacesMessage.SEVERITY_WARN);
			//throw new ValidatorException(msg);
            throw new ConverterException(msg);
        }

        return super.getAsObject(context, component, value);
    }

}