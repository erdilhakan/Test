package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.util.Util;

public class RandevuListeDTO implements Serializable, Cloneable {

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3477327741741967883L;
	private int randevuId;
	private Date randevuTarihi;
	private Date randevuSaati;

	private int basvuruId;
	private String basvuruYapan;
	private double kontrolTutari = 0.00;
	private String kontrolTuru="";
	private long telefonNo;
	private int telefonNoDahili;
	private int binaId;
	private String binaAdi;
	private List<FirmaDTO> firmalar = new ArrayList<FirmaDTO>();
	private String unvan;
	private List<Kullanici> muhendisler = new ArrayList<Kullanici>();
	private TreeNode muhendis = new DefaultTreeNode("Muhendis", null);
	private int asansorAdet;
	private int muhendisSicilNo=0;
	private int cihazId;
	private long asansorUavtKod;
	private String asansorUavtEtiket;
	private String binaAdres;
	private int binaUavtKod;
	private String muhendisAdSoyad;
	public int getRandevuId() {
		return randevuId;
	}

	public void setRandevuId(int randevuId) {
		this.randevuId = randevuId;
	}

	public Date getRandevuTarihi() {
		return randevuTarihi;
	}

	public void setRandevuTarihi(Date randevuTarihi) {
		this.randevuTarihi = randevuTarihi;
	}

	public Date getRandevuSaati() {
		return randevuSaati;
	}

	public void setRandevuSaati(Date randevuSaati) {
		this.randevuSaati = randevuSaati;
	}

	public int getBasvuruId() {
		return basvuruId;
	}

	public void setBasvuruId(int basvuruId) {
		this.basvuruId = basvuruId;
	}

	public String getBasvuruYapan() {
		return basvuruYapan;
	}

	public void setBasvuruYapan(String basvuruYapan) {
		this.basvuruYapan = Util.toUpperCase(basvuruYapan);
	}

	public TreeNode getMuhendis() {
		return muhendis;
	}

	public void setMuhendis(TreeNode muhendis) {
		this.muhendis = muhendis;
	}

	public long getTelefonNo() {
		return telefonNo;
	}

	public void setTelefonNo(long telefonNo) {
		this.telefonNo = telefonNo;
	}

	public String getBinaAdi() {
		return binaAdi;
	}

	public void setBinaAdi(String binaAdi) {
		this.binaAdi = Util.toUpperCase(binaAdi);
	}

	public List<Kullanici> getMuhendisler() {
		return muhendisler;
	}

	public void setMuhendisler(List<Kullanici> muhendisler) {
		this.muhendisler = muhendisler;
	}

	public double getKontrolTutari() {
		return kontrolTutari;
	}

	public void setKontrolTutari(double kontrolTutari) {
		this.kontrolTutari = kontrolTutari;
	}

	public int getBinaId() {
		return binaId;
	}

	public void setBinaId(int binaId) {
		this.binaId = binaId;
	}

	public List<FirmaDTO> getFirmalar() {
		return firmalar;
	}

	public void setFirmalar(List<FirmaDTO> firmalar) {
		this.firmalar = firmalar;
	}

	public String getUnvan() {
		return unvan;
	}

	public void setUnvan(String unvan) {
		this.unvan = unvan;
	}

	public int getAsansorAdet() {
		return asansorAdet;
	}

	public void setAsansorAdet(int asansorAdet) {
		this.asansorAdet = asansorAdet;
	}

	public int getTelefonNoDahili() {
		return telefonNoDahili;
	}

	public void setTelefonNoDahili(int telefonNoDahili) {
		this.telefonNoDahili = telefonNoDahili;
	}

	public String getKontrolTuru() {
		return kontrolTuru;
	}

	public void setKontrolTuru(String kontrolTuru) {
		this.kontrolTuru = kontrolTuru;
	}

	public int getMuhendisSicilNo() {
		return muhendisSicilNo;
	}

	public void setMuhendisSicilNo(int muhendisSicilNo) {
		this.muhendisSicilNo = muhendisSicilNo;
	}

	public int getCihazId() {
		return cihazId;
	}

	public void setCihazId(int cihazId) {
		this.cihazId = cihazId;
	}

	public long getAsansorUavtKod() {
		return asansorUavtKod;
	}

	public void setAsansorUavtKod(long asansorUavtKod) {
		this.asansorUavtKod = asansorUavtKod;
	}

	public String getAsansorUavtEtiket() {
		return asansorUavtEtiket;
	}

	public void setAsansorUavtEtiket(String asansorUavtEtiket) {
		this.asansorUavtEtiket = asansorUavtEtiket;
	}

	public String getBinaAdres() {
		return binaAdres;
	}

	public void setBinaAdres(String binaAdres) {
		this.binaAdres = binaAdres;
	}

	public int getBinaUavtKod() {
		return binaUavtKod;
	}

	public void setBinaUavtKod(int binaUavtKod) {
		this.binaUavtKod = binaUavtKod;
	}

	public String getMuhendisAdSoyad() {
		return muhendisAdSoyad;
	}

	public void setMuhendisAdSoyad(String muhendisAdSoyad) {
		this.muhendisAdSoyad = muhendisAdSoyad;
	}
	
	

}
