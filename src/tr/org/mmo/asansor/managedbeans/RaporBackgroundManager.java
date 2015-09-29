package tr.org.mmo.asansor.managedbeans;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class RaporBackgroundManager implements ServletContextListener  {
	 private ScheduledExecutorService scheduler;

	    @Override
	    public void contextInitialized(ServletContextEvent event) {
	        scheduler = Executors.newSingleThreadScheduledExecutor();
	      
	       
	       int h= Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	       
	        scheduler.scheduleAtFixedRate(new RaporGonder(), 23-h,24, TimeUnit.HOURS);
	       
	        
	        //int h= Calendar.getInstance().get(Calendar.MINUTE);
		       
	        //scheduler.scheduleAtFixedRate(new RaporGonder(), 1,10, TimeUnit.MINUTES);
	    }

	    @Override
	    public void contextDestroyed(ServletContextEvent event) {
	        scheduler.shutdownNow();
	    }
}
