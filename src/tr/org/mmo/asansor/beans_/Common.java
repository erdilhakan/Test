package tr.org.mmo.asansor.beans_;

import javax.faces.context.FacesContext;

public class Common {
	@SuppressWarnings("unchecked")
	public static <T> T findBean(String beanName) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
	}
}
