package tr.org.mmo.asansor.beans_;

import java.io.Serializable;

import tr.org.mmo.asansor.dto.BasvuruDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.BinaKisiDTO;
import tr.org.mmo.asansor.dto.TaramaDTO;

public class Basvuru implements Serializable {

	
	private static final long serialVersionUID = -7182143755707494671L;
	
	
	private BasvuruDTO basvuru=new BasvuruDTO();
	private BinaDTO bina=new BinaDTO();
	private BinaKisiDTO kisi=new BinaKisiDTO();
	private TaramaDTO tarama=new TaramaDTO();
	
	public BinaKisiDTO getKisi() {
		return kisi;
	}
	public void setKisi(BinaKisiDTO kisi) {
		this.kisi = kisi;
	}
	public BasvuruDTO getBasvuru() {
		return basvuru;
	}
	public void setBasvuru(BasvuruDTO basvuru) {
		this.basvuru = basvuru;
		
	}
	public BinaDTO getBina() {
		return bina;
	}
	public void setBina(BinaDTO bina) {
		this.bina = bina;
	}
	public TaramaDTO getTarama() {
		return tarama;
	}
	public void setTarama(TaramaDTO tarama) {
		this.tarama = tarama;
	}
	
	
	
	
	
}
