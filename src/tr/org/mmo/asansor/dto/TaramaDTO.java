package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.Date;

import tr.org.mmo.asansor.beans_.Basvuru;
import tr.org.mmo.asansor.util.Util;



public class TaramaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6239376350268325694L;
	
	private int id;
	private Date tarih;
	private long binaSorumlusuTCKimlikNo;
	private String binaSorumlusuAdi;
	private String binaSorumlusuSoyadi;
	private int  binaSorumlusuSorumlulukTuru;
	private long binaId;
	private String durum;
	private long binaSorumlusuTelefonNo;
	private String binaSorumlusuTelefonNoStr;
	private String binaSorumlusuEPosta;
	private int taramaYapilamamaNedenKod;
	private long binaSorumlusuTelefonNoDahili;
	
	public TaramaDTO(){
		
	}
	public TaramaDTO(Basvuru basvuru) {
		setBinaId(basvuru.getBina().getBinaId());
		setBinaSorumlusuAdi(basvuru.getBasvuru().getBasvuruYapanAdi());
		setBinaSorumlusuEPosta(basvuru.getKisi().getePosta());
		setBinaSorumlusuSorumlulukTuru(basvuru.getBasvuru().getBasvuruYapanSorumlulukTuru());
		setBinaSorumlusuSoyadi(basvuru.getBasvuru().getBasvuruYapanSoyadi());
		setBinaSorumlusuTCKimlikNo(basvuru.getBasvuru().getBasvuruYapanTCKimlikNo());
		setBinaSorumlusuTelefonNo(basvuru.getBasvuru().getTelefonNo());
		setBinaSorumlusuTelefonNoStr(basvuru.getBasvuru().getTelefonNoStr());
		setBinaSorumlusuTelefonNoDahili(basvuru.getBasvuru().getTelefonNoDahili());
		setDurum(basvuru.getBasvuru().getBasvuruDurum());
		setTarih(basvuru.getBasvuru().getBasvuruTarihi());
	}
	
	public String getBinaSorumlusuAdi() {
		return binaSorumlusuAdi;
	}
	public void setBinaSorumlusuAdi(String binaSorumlusuAdi) {
		this.binaSorumlusuAdi = Util.toUpperCase(binaSorumlusuAdi);
	}
	public String getBinaSorumlusuSoyadi() {
		return binaSorumlusuSoyadi;
	}
	public void setBinaSorumlusuSoyadi(String binaSorumlusuSoyadi) {
		this.binaSorumlusuSoyadi = Util.toUpperCase(binaSorumlusuSoyadi);
	}
	public long getBinaSorumlusuTelefonNo() {
		return binaSorumlusuTelefonNo;
	}
	public void setBinaSorumlusuTelefonNo(long binaSorumlusuTelefonNo) {
		this.binaSorumlusuTelefonNo = binaSorumlusuTelefonNo;
	}
	public String getBinaSorumlusuTelefonNoStr() {
		return binaSorumlusuTelefonNoStr;
	}
	public void setBinaSorumlusuTelefonNoStr(String binaSorumlusuTelefonNoStr) {
		this.binaSorumlusuTelefonNoStr = binaSorumlusuTelefonNoStr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getTarih() {
		return tarih;
	}
	public void setTarih(Date tarih) {
		this.tarih = tarih;
	}
	public long getBinaSorumlusuTCKimlikNo() {
		return binaSorumlusuTCKimlikNo;
	}
	public void setBinaSorumlusuTCKimlikNo(long binaSorumlusuTCKimlikNo) {
		this.binaSorumlusuTCKimlikNo = binaSorumlusuTCKimlikNo;
	}
	
	
	public long getBinaId() {
		return binaId;
	}
	public void setBinaId(long binaId) {
		this.binaId = binaId;
	}
	public String getDurum() {
		return durum;
	}
	
	public void setDurum(String durum) {
		this.durum = Util.toUpperCase(durum);
	}
	
	public int getBinaSorumlusuSorumlulukTuru() {
		return binaSorumlusuSorumlulukTuru;
	}
	public void setBinaSorumlusuSorumlulukTuru(int binaSorumlusuSorumlulukTuru) {
		this.binaSorumlusuSorumlulukTuru = binaSorumlusuSorumlulukTuru;
	}
	public String getBinaSorumlusuEPosta() {
		return binaSorumlusuEPosta;
	}
	public void setBinaSorumlusuEPosta(String binaSorumlusuEPosta) {
		this.binaSorumlusuEPosta = binaSorumlusuEPosta;
	}
	public int getTaramaYapilamamaNedenKod() {
		return taramaYapilamamaNedenKod;
	}
	public void setTaramaYapilamamaNedenKod(int taramaYapilamamaNedenKod) {
		this.taramaYapilamamaNedenKod = taramaYapilamamaNedenKod;
	}
	public long getBinaSorumlusuTelefonNoDahili() {
		return binaSorumlusuTelefonNoDahili;
	}
	public void setBinaSorumlusuTelefonNoDahili(long binaSorumlusuTelefonNoDahili) {
		this.binaSorumlusuTelefonNoDahili = binaSorumlusuTelefonNoDahili;
	}
	
	
	
	
	
	
	
	
	
}
