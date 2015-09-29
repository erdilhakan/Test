package tr.org.mmo.asansor.util;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.servlet.http.HttpSession;

public class ViewExpiredExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	public ViewExpiredExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public void handle() throws FacesException {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		for (Iterator<ExceptionQueuedEvent> iter = getUnhandledExceptionQueuedEvents()
				.iterator(); iter.hasNext();) {
			Throwable exception = iter.next().getContext().getException();
			try {
				if (exception instanceof ViewExpiredException
						|| exception instanceof IndexOutOfBoundsException
						|| facesContext.getExternalContext().getSession(false) == null
						|| exception == new FacesException(
								"IndexOutOfBoundsException")) {
					try {
						HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
								.getExternalContext().getSession(true);
						session.invalidate();
						facesContext.getExternalContext().redirect("error.jsf");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext,
					// null, "error");
					facesContext.renderResponse();
					iter.remove();
				}
			} catch (IndexOutOfBoundsException e) {
				try {
					HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
							.getExternalContext().getSession(true);
					session.invalidate();
					facesContext.getExternalContext().redirect("error.jsf");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		getWrapped().handle();
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

}