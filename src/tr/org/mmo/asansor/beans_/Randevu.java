package tr.org.mmo.asansor.beans_;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.BasvuruListeDTO;

public class Randevu implements Serializable, Cloneable {

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7478291417308608365L;
	private int randevuId;
	private BasvuruListeDTO basvuru = new BasvuruListeDTO();

	// private TreeMap<String,Integer> muhendisler=new TreeMap<String,
	// Integer>();
	private ArrayList<Kullanici> muhendisler = new ArrayList<Kullanici>();

	private Date randevuTarih;
	private Date randevuSaati;
	private String firmaAdi;
	private List<BasvuruAsansorDTO> firmaCihazList = new ArrayList<BasvuruAsansorDTO>();

	public Date getRandevuSaati() {
		return randevuSaati;
	}

	public void setRandevuSaati(Date randevuSaati) {
		this.randevuSaati = randevuSaati;
	}

	private ArrayList<Kullanici> secilenMuhendisler = new ArrayList<Kullanici>();

	public int getRandevuId() {
		return randevuId;
	}

	public ArrayList<Kullanici> getSecilenMuhendisler() {
		return secilenMuhendisler;
	}

	public void setSecilenMuhendisler(ArrayList<Kullanici> secilenMuhendisler) {
		this.secilenMuhendisler = secilenMuhendisler;
	}

	public void setRandevuId(int randevuId) {
		this.randevuId = randevuId;
	}

	public void setRandevu_Id(int randevuId) {
		this.randevuId = randevuId;
	}

	public BasvuruListeDTO getBasvuru() {
		return basvuru;
	}

	public void setBasvuru(BasvuruListeDTO basvuru) {
		this.basvuru = basvuru;
	}

	public Date getRandevuTarih() {
		return randevuTarih;
	}

	public void setRandevuTarih(Date randevuTarih) {
		this.randevuTarih = randevuTarih;
	}

	public ArrayList<Kullanici> getMuhendisler() {
		return muhendisler;
	}

	public void setMuhendisler(ArrayList<Kullanici> muhendisler) {
		this.muhendisler = muhendisler;
	}

	public String getFirmaAdi() {
		return firmaAdi;
	}

	public void setFirmaAdi(String firmaAdi) {
		this.firmaAdi = firmaAdi;
	}

	public List<BasvuruAsansorDTO> getFirmaCihazList() {
		return firmaCihazList;
	}

	public void setFirmaCihazList(List<BasvuruAsansorDTO> firmaCihazList) {
		this.firmaCihazList = firmaCihazList;
	}

}
