package tr.org.mmo.asansor.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;

import tr.org.mmo.asansor.dto.BirimDTO;

@ManagedBean
public class BirimBean {

	public List<BirimDTO> getBirimListesi() {
		return birimListesi;
	}

	public void setBirimListesi(List<BirimDTO> birimListesi) {
		this.birimListesi = birimListesi;
	}

	public BirimDTO getSecilenRol() {
		return secilenRol;
	}

	public void setSecilenRol(BirimDTO secilenRol) {
		this.secilenRol = secilenRol;
	}

	private List<BirimDTO> birimListesi;
	private BirimDTO secilenRol=new BirimDTO();
	
	
	/*
	@PostConstruct
	public void init(){
		listeGetir();
	}
	
	public void listeGetir(){
		try {
			birimListesi=new BirimBusiness().listeGetir();
		} catch (CRUDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
}
