package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DenetimKayitDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private int id;
	 private int raporId;
	 private Timestamp tarih;
	 private int hataKodu;
	 private String hataMesaj;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRaporId() {
		return raporId;
	}
	public void setRaporId(int raporId) {
		this.raporId = raporId;
	}
	public Timestamp getTarih() {
		return tarih;
	}
	public void setTarih(Timestamp tarih) {
		this.tarih = tarih;
	}
	public int getHataKodu() {
		return hataKodu;
	}
	public void setHataKodu(int hataKodu) {
		this.hataKodu = hataKodu;
	}
	public String getHataMesaj() {
		return hataMesaj;
	}
	public void setHataMesaj(String hataMesaj) {
		this.hataMesaj = hataMesaj;
	}
	 
	 
}
