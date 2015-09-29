package tr.org.mmo.asansor.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CompatibilityFilter
 */
@WebFilter("/CompatibilityFilter")
public class CompatibilityFilter implements Filter {

    /**
     * Default constructor. 
     */
    public CompatibilityFilter() {
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
	 public void doFilter(ServletRequest request, ServletResponse res,
	            FilterChain chain) throws IOException, ServletException {
	        HttpServletResponse resp = (HttpServletResponse) res;
	        resp.addHeader("X-UA-Compatible", "IE=EmulateIE8");
	        resp.addHeader("Cache-Control", "no-cache, must-revalidate");
	        chain.doFilter(request, resp);
	    }


	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
