package tr.org.mmo.asansor.beans_;


import java.io.Serializable;
import java.util.Map;

import javax.faces.context.FacesContext;


public class GuestPreferences implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = 7827083386941005933L;
                private static String theme ;
        
        @SuppressWarnings("unchecked")
        public String getTheme() {
                if (theme==null || theme.trim().equals("")){
        		
				Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getInitParameterMap();
        		
                if(params.containsKey("_THEME_")) {
                        theme = params.get("_THEME_");
                }
                
                }
                
              
                return theme;
        }

        
		public void setTheme(String theme) {
                 GuestPreferences.theme = theme;
               
                 
                
        }
        
        
}


