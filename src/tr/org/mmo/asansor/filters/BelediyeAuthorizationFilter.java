package tr.org.mmo.asansor.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.dto.KullaniciRolYetkiDTO;
import tr.org.mmo.asansor.dto.YetkiSayfaDTO;
import tr.org.mmo.asansor.managedbeans.LoginBean;

/**
 * Servlet Filter implementation class BelediyeAuthorizationFilter
 */
@WebFilter("/BelediyeAuthorizationFilter")
public class BelediyeAuthorizationFilter implements Filter {

    /**
     * Default constructor. 
     */
    public BelediyeAuthorizationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		if (!session.isNew()) {

			LoginBean loginBean = (LoginBean) req.getSession().getAttribute(
					"loginBean");

			if (loginBean != null && loginBean.isBelediyeloggedIn()) {
				
				chain.doFilter(request, response);
			} else {
				sayfaYonlendir(request, response, "login.jsf");

			}

		} else {
			sayfaYonlendir(request, response, "login.jsf");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}


	private void sayfaYonlendir(ServletRequest request,
			ServletResponse response, String sayfa) throws ServletException,
			IOException {
		RequestDispatcher rq = request.getRequestDispatcher(sayfa);
		rq.forward(request, response);
	}

}
