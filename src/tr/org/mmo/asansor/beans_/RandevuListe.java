package tr.org.mmo.asansor.beans_;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import tr.org.mmo.asansor.dto.RandevuListeDTO;


public class RandevuListe implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -378708126975581863L;
private ArrayList<RandevuListeDTO> randevuList;



@PostConstruct
public void init(){
	randevuList=new ArrayList<RandevuListeDTO>();
	
}



public ArrayList<RandevuListeDTO> getRandevuList() {
	return randevuList;
}



public void setRandevuList(ArrayList<RandevuListeDTO> randevuList) {
	this.randevuList = randevuList;
}


}
