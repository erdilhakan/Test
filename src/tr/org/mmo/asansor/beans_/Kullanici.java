package tr.org.mmo.asansor.beans_;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import tr.org.mmo.asansor.dto.BirimDTO;
import tr.org.mmo.asansor.dto.KullaniciRolYetkiDTO;
import tr.org.mmo.asansor.dto.MuhendisCihazYetkiDTO;
import tr.org.mmo.asansor.util.IlIlceComparator;

public class Kullanici implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8673842162349847497L;
	private int kullaniciId;
	private String kullaniciAdi = "";
	private String adi;
	private String soyadi;
	private String parola = "";
	private String durum;
	private long telefonNo;
	private long gsmTelefonNo;
	private String telefonNoStr;
	private String gsmTelefonNoStr;
	private int muhendisGunlukKontrolAdet = 0;
	private int kullaniciTuru;
	private String kullaniciTurAdi;
	private String onayYetkisi;
	private String akreditasyonDeger;
	private int il;
	private String sorumlu;
	private String ilAdi;
	private String belediyeIp;
	private List<KullaniciRolYetkiDTO> roller = new ArrayList<KullaniciRolYetkiDTO>();
	private List<MuhendisCihazYetkiDTO> muhendisCihazYetkiList=new ArrayList<MuhendisCihazYetkiDTO>();
	public String getDurum() {
		return durum;
	}

	public void setDurum(String durum) {
		this.durum = durum;
	}

	private List<BirimDTO> birimler;
	private boolean onaylayan = false;
	private boolean akreditasyon = false;
	private String sicilNo;
	private String ePosta;

	public String getePosta() {
		return ePosta;
	}

	public void setePosta(String ePosta) {
		this.ePosta = ePosta;
	}

	private TreeMap<String, Integer> iller = new TreeMap<String, Integer>(
			new IlIlceComparator());

	public TreeMap<String, Integer> getIller() {
		return iller;
	}

	public void setIller(TreeMap<String, Integer> iller) {
		this.iller = iller;
	}

	public Kullanici() {

	}

	public Kullanici(String kullaniciAdi, String parola) {
		super();
		this.kullaniciAdi = kullaniciAdi;
		this.parola = parola;
	}

	public String getKullaniciAdi() {
		return kullaniciAdi;
	}

	public void setKullaniciAdi(String kullaniciAdi) {
		this.kullaniciAdi = kullaniciAdi;
	}

	public void setKullanici_Adi(String kullaniciAdi) {
		this.kullaniciAdi = kullaniciAdi;
	}

	public String getParola() {
		return parola;
	}

	public void setParola(String parola) {
		this.parola = parola;
	}

	public int getKullaniciId() {
		return kullaniciId;
	}

	public void setKullaniciId(int kullaniciId) {
		this.kullaniciId = kullaniciId;
	}

	public void setKullanici_Id(int kullaniciId) {
		this.kullaniciId = kullaniciId;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public List<BirimDTO> getBirimler() {
		return birimler;
	}

	public void setBirimler(List<BirimDTO> birimler) {
		this.birimler = birimler;
	}

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public String getSoyadi() {
		return soyadi;
	}

	public void setSoyadi(String soyadi) {
		this.soyadi = soyadi;
	}

	public boolean isOnaylayan() {
		return onaylayan;
	}

	public void setOnaylayan(boolean onaylayan) {
		this.onaylayan = onaylayan;
	}

	public boolean isAkreditasyon() {
		return akreditasyon;
	}

	public void setAkreditasyon(boolean akreditasyon) {
		this.akreditasyon = akreditasyon;
	}

	public String getSicilNo() {
		return sicilNo;
	}

	public void setSicilNo(String sicilNo) {
		this.sicilNo = sicilNo;
	}

	public long getTelefonNo() {
		return telefonNo;
	}

	public void setTelefonNo(long telefonNo) {
		this.telefonNo = telefonNo;
	}

	public long getGsmTelefonNo() {
		return gsmTelefonNo;
	}

	public void setGsmTelefonNo(long gsmTelefonNo) {
		this.gsmTelefonNo = gsmTelefonNo;
	}

	public int getKullaniciTuru() {
		return kullaniciTuru;
	}

	public void setKullaniciTuru(int kullaniciTuru) {
		this.kullaniciTuru = kullaniciTuru;
	}

	public String getOnayYetkisi() {
		return onayYetkisi;
	}

	public void setOnayYetkisi(String onayYetkisi) {
		this.onayYetkisi = onayYetkisi;
	}

	public String getAkreditasyonDeger() {
		return akreditasyonDeger;
	}

	public void setAkreditasyonDeger(String akreditasyonDeger) {
		this.akreditasyonDeger = akreditasyonDeger;
	}

	public int getIl() {
		return il;
	}

	public void setIl(int il) {
		this.il = il;
	}

	public String getIlAdi() {
		return ilAdi;
	}

	public void setIlAdi(String ilAdi) {
		this.ilAdi = ilAdi;
	}

	public String getTelefonNoStr() {
		return telefonNoStr;
	}

	public void setTelefonNoStr(String telefonNoStr) {
		this.telefonNoStr = telefonNoStr;
		this.telefonNo = Long.parseLong(telefonNoStr == null ? "0"
				: (telefonNoStr.equals("") ? "0" : telefonNoStr
						.replace("(", "").replace(")", "").replace(" ", "")
						.trim()));
	}

	public String getGsmTelefonNoStr() {
		return gsmTelefonNoStr;
	}

	public void setGsmTelefonNoStr(String gsmTelefonNoStr) {
		this.gsmTelefonNoStr = gsmTelefonNoStr;
		this.gsmTelefonNo = Long.parseLong(gsmTelefonNoStr == null ? "0"
				: (gsmTelefonNoStr.equals("") ? "0" : gsmTelefonNoStr
						.replace("(", "").replace(")", "").replace(" ", "")
						.trim()));
	}

	public int getMuhendisGunlukKontrolAdet() {
		return muhendisGunlukKontrolAdet;
	}

	public void setMuhendisGunlukKontrolAdet(int muhendisGunlukKontrolAdet) {
		this.muhendisGunlukKontrolAdet = muhendisGunlukKontrolAdet;
	}

	public String getSorumlu() {
		return sorumlu;
	}

	public void setSorumlu(String sorumlu) {
		this.sorumlu = sorumlu;
	}

	public List<KullaniciRolYetkiDTO> getRoller() {
		return roller;
	}

	public void setRoller(List<KullaniciRolYetkiDTO> roller) {
		this.roller = roller;
	}

	private int[] muhendisCihazYetki = new int[2];

	public int[] getMuhendisCihazYetki() {
		return muhendisCihazYetki;
	}

	public void setMuhendisCihazYetki(int[] muhendisCihazYetki) {
		this.muhendisCihazYetki = muhendisCihazYetki;
	}

	public List<MuhendisCihazYetkiDTO> getMuhendisCihazYetkiList() {
		return muhendisCihazYetkiList;
	}

	public void setMuhendisCihazYetkiList(
			List<MuhendisCihazYetkiDTO> muhendisCihazYetkiList) {
		this.muhendisCihazYetkiList = muhendisCihazYetkiList;
	}

	public String getBelediyeIp() {
		return belediyeIp;
	}

	public void setBelediyeIp(String belediyeIp) {
		this.belediyeIp = belediyeIp;
	}

	public String getKullaniciTurAdi() {
		return kullaniciTurAdi;
	}

	public void setKullaniciTurAdi(String kullaniciTurAdi) {
		this.kullaniciTurAdi = kullaniciTurAdi;
	}

	
	
	
}
