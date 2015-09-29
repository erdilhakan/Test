package tr.org.mmo.asansor.managedbeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import tr.org.mmo.asansor.business.TemsilcilikBusiness;
import tr.org.mmo.asansor.dto.TemsilcilikDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;

@ManagedBean
public class TemsilcilikBean {

	private List<TemsilcilikDTO> temsilcilikListesi;

	public List<TemsilcilikDTO> getTemsilcilikListesi() {
		return temsilcilikListesi;
	}

	public void setTemsilcilikListesi(List<TemsilcilikDTO> temsilcilikListesi) {
		this.temsilcilikListesi = temsilcilikListesi;
	}
	
	@PostConstruct
	public void init(){
		listeGetir();
	}

	private void listeGetir(){
		try {
			temsilcilikListesi=new TemsilcilikBusiness().listeGetir();
		} catch (CRUDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

