package tr.org.mmo.asansor.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
@ManagedBean(eager=true)
public class IndexBean {
	 private List<String> images;  
	 private List<String> img1;
	 private String img;
	     public IndexBean() {
	    	   images = new ArrayList<String>();
	    	   img1=new ArrayList<String>();
		        images.add("1.jpg");  
		        images.add("2.jpg");  
		        
		        img1.add("62c17754099367a_ek.jpg");
		        img1.add("cd4e254ef2f19b4_ek.jpg");
		        img1.add("yardimci.png");
		}  
	       
	     public void listener(int i){
	    	 img=img1.get(i);
	     }
	  
	    public List<String> getImages() {  
	        return images;  
	    }

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}

}
