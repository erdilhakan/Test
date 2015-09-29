package tr.org.mmo.asansor.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tr.org.mmo.asansor.util.CharResponseWrapper;

/**
 * Servlet Filter implementation class CustomCharacterEncodingFilter
 */
@WebFilter("/CustomCharacterEncodingFilter")
public class CustomCharacterEncodingFilter implements Filter {
	


	 public void init(FilterConfig config) throws ServletException {
		
	    }

	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
	                                                           throws IOException, ServletException {
	    	/*
	        request.setCharacterEncoding("UTF-8");
	        response.setCharacterEncoding("UTF-8");
	    
	    		    ServletResponse newResponse = response;

	    		    if (request instanceof HttpServletRequest) {
	    		      newResponse = new CharResponseWrapper((HttpServletResponse) response);
	    		    }

	    		    chain.doFilter(request, newResponse);

	    		    if (newResponse instanceof CharResponseWrapper) {
	    		      String text = newResponse.toString();
	    		      if (text != null) {
	    		        text = text.toUpperCase();
	    		        response.getWriter().write(text);
	    		      }
	    		    }
	    		    */
	    }

	    public void destroy() {
	       
	    }


}
