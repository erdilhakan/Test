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
 * Servlet Filter implementation class AuthorizationFilter
 */
@WebFilter("/AuthorizationFilter")
public class AuthorizationFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public AuthorizationFilter() {

	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		if (!session.isNew()) {

			LoginBean loginBean = (LoginBean) req.getSession().getAttribute(
					"loginBean");

			if (loginBean != null && loginBean.isLoggedIn()) {

				if (!req.getRequestURL().toString().contains("index.jsf")
						&& !req.getRequestURL().toString()
								.contains("basvuru.jsf")
						&& !req.getRequestURL().toString()
								.contains("sifredegistir.jsf")
								&& !req.getRequestURL().toString()
								.contains("duyuru")
								
						) {
					boolean yetkili = false;

					int yetkiId = 0;

					for (YetkiSayfaDTO y : loginBean.getSayfaYetkileri()) {
						if (req.getRequestURL().toString()
								.contains(y.getSayfaAdi())) {
							yetkiId = y.getYetkiId();
							break;

						}
					}
					Kullanici kullanici = loginBean.getKullanici();
					if (req.getRequestURL().toString()
							.contains("testsorulari.jsf")
							|| req.getRequestURL().toString()
									.contains("kullaniciListe.jsf")
							|| req.getRequestURL().toString()
									.contains("kullanicikayit.jsf")
							|| req.getRequestURL().toString()
									.contains("randevuListe.jsf")) {

						for (KullaniciRolYetkiDTO k : kullanici.getRoller()) {

							if (k.getRolId() == 1) {
								yetkili = true;
								break;
							}
						}
						if (!yetkili) {
							sayfaYonlendir(request, response, "auth.jsf");
						}

					} else {

						yetkili = yetkiliMi(kullanici, yetkiId);

						if (!yetkili) {
							sayfaYonlendir(request, response, "auth.jsf");
						}
					}
				}
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

	}

	private boolean yetkiliMi(Kullanici kullanici, int yetkiId) {
		boolean yetkili = false;
		for (KullaniciRolYetkiDTO k : kullanici.getRoller()) {
			if (yetkiId == k.getYetkiId()) {
				yetkili = true;
				break;
			}

		}
		return yetkili;
	}

	private void sayfaYonlendir(ServletRequest request,
			ServletResponse response, String sayfa) throws ServletException,
			IOException {
		RequestDispatcher rq = request.getRequestDispatcher(sayfa);
		rq.forward(request, response);
	}

}
