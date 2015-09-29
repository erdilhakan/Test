package tr.org.mmo.asansor.util;

import java.util.ResourceBundle;


public class Ayarlar {
    private static ResourceBundle rb;
    private static int DEPLOY_TYPE;
    public static String SHEMA;
    public static String SHEMAUAVT;
    
    
	
    
    public static void init() 
    {
        //reload();

        rb = ResourceBundle.getBundle("ayarlar");
        DEPLOY_TYPE = Integer.parseInt(rb.getString("DEPLOY_TYPE"));
        setDeployValues();
        System.out.println("S�STEM AYARLARI BAޞARILI B�R �EK�LDE  -> " + getDeployName() + 
        		" <-  SEV�YES�NE GET�R�LD�.");

    }

    private static  void setDeployValues() {
    	SHEMAUAVT=rb.getString("UAVT");
        switch (DEPLOY_TYPE) {
        case 0:
           SHEMA=rb.getString("SHEMA_DEVELOPER");
            break;
        case 1:
        	SHEMA=rb.getString("TEST");
            break;
        case 2:
        	SHEMA=rb.getString("PROD");
            break;
        default:
            break;
        }
    }

    @SuppressWarnings({  "unused" })
	private static void reload() {
    	/*    
    	try {
             	Class sinif = ResourceBundle.getBundle("ayarlar").getClass().getSuperclass();
            Field field =sinif.getDeclaredField("cacheList");
            field.setAccessible(true);
            sun.misc.SoftCache cache = (sun.misc.SoftCache) field.get(null);
            cache.clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        */
        
    }

    private static String getDeployName() {
        switch (DEPLOY_TYPE) {
        case 0:
            return "DEVELOPMENT";
        case 1:
            return "TEST";
        case 2:
            return "PRODUCTION";
        default:
            return "TANIMSIZ";
        }
    }

	
    
    
}