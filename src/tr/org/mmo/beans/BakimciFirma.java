package tr.org.mmo.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the bakimci_firma database table.
 * 
 */
@Entity
@Table(name = "bakimci_firma", schema = "test")
public class BakimciFirma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer kod;

	private String adres;

	private String adsoyad;

	@Column(name = "ce_belge_tipi")
	private String ceBelgeTipi;

	private String durumu;

	private String eposta;

	@Temporal(TemporalType.DATE)
	@Column(name = "gecerlilik_suresi")
	private Date gecerlilikSuresi;

	@Column(name = "gsm_telefon")
	private Long gsmTelefon;

	private Integer il;

	private Integer ilce;

	@Column(name = "monte_eden")
	private String monteEden;

	@Column(name = "servis_sozlesme")
	private String servisSozlesme;

	@Temporal(TemporalType.DATE)
	@Column(name = "sozlesme_tarihi")
	private Date sozlesmeTarihi;

	@Column(name = "telefon_no")
	private Long telefonNo;

	@Column(name = "telefon_no_dahili")
	private Integer telefonNoDahili;

	@Column(name = "tescil_no")
	private Long tescilNo;

	@Column(name = "tse_belge_no")
	private String tseBelgeNo;

	@Column(name = "tse_belgesi")
	private String tseBelgesi;

	private String unvan;

	@Column(name = "uygunluk_belgesi")
	private String uygunlukBelgesi;

	@Column(name = "yetkili_servis")
	private String yetkiliServis;

	// bi-directional many-to-one association to BakimciFirmaBirim
	@OneToMany(mappedBy = "bakimciFirma")
	private List<BakimciFirmaBirim> bakimciFirmaBirims;

	public BakimciFirma() {
	}

	public Integer getKod() {
		return this.kod;
	}

	public void setKod(Integer kod) {
		this.kod = kod;
	}

	public String getAdres() {
		return this.adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public String getAdsoyad() {
		return this.adsoyad;
	}

	public void setAdsoyad(String adsoyad) {
		this.adsoyad = adsoyad;
	}

	public String getCeBelgeTipi() {
		return this.ceBelgeTipi;
	}

	public void setCeBelgeTipi(String ceBelgeTipi) {
		this.ceBelgeTipi = ceBelgeTipi;
	}

	public String getDurumu() {
		return this.durumu;
	}

	public void setDurumu(String durumu) {
		this.durumu = durumu;
	}

	public String getEposta() {
		return this.eposta;
	}

	public void setEposta(String eposta) {
		this.eposta = eposta;
	}

	public Date getGecerlilikSuresi() {
		return this.gecerlilikSuresi;
	}

	public void setGecerlilikSuresi(Date gecerlilikSuresi) {
		this.gecerlilikSuresi = gecerlilikSuresi;
	}

	public Long getGsmTelefon() {
		return this.gsmTelefon;
	}

	public void setGsmTelefon(Long gsmTelefon) {
		this.gsmTelefon = gsmTelefon;
	}

	public Integer getIl() {
		return this.il;
	}

	public void setIl(Integer il) {
		this.il = il;
	}

	public Integer getIlce() {
		return this.ilce;
	}

	public void setIlce(Integer ilce) {
		this.ilce = ilce;
	}

	public String getMonteEden() {
		return this.monteEden;
	}

	public void setMonteEden(String monteEden) {
		this.monteEden = monteEden;
	}

	public String getServisSozlesme() {
		return this.servisSozlesme;
	}

	public void setServisSozlesme(String servisSozlesme) {
		this.servisSozlesme = servisSozlesme;
	}

	public Date getSozlesmeTarihi() {
		return this.sozlesmeTarihi;
	}

	public void setSozlesmeTarihi(Date sozlesmeTarihi) {
		this.sozlesmeTarihi = sozlesmeTarihi;
	}

	public Long getTelefonNo() {
		return this.telefonNo;
	}

	public void setTelefonNo(Long telefonNo) {
		this.telefonNo = telefonNo;
	}

	public Integer getTelefonNoDahili() {
		return this.telefonNoDahili;
	}

	public void setTelefonNoDahili(Integer telefonNoDahili) {
		this.telefonNoDahili = telefonNoDahili;
	}

	public Long getTescilNo() {
		return this.tescilNo;
	}

	public void setTescilNo(Long tescilNo) {
		this.tescilNo = tescilNo;
	}

	public String getTseBelgeNo() {
		return this.tseBelgeNo;
	}

	public void setTseBelgeNo(String tseBelgeNo) {
		this.tseBelgeNo = tseBelgeNo;
	}

	public String getTseBelgesi() {
		return this.tseBelgesi;
	}

	public void setTseBelgesi(String tseBelgesi) {
		this.tseBelgesi = tseBelgesi;
	}

	public String getUnvan() {
		return this.unvan;
	}

	public void setUnvan(String unvan) {
		this.unvan = unvan;
	}

	public String getUygunlukBelgesi() {
		return this.uygunlukBelgesi;
	}

	public void setUygunlukBelgesi(String uygunlukBelgesi) {
		this.uygunlukBelgesi = uygunlukBelgesi;
	}

	public String getYetkiliServis() {
		return this.yetkiliServis;
	}

	public void setYetkiliServis(String yetkiliServis) {
		this.yetkiliServis = yetkiliServis;
	}

	public List<BakimciFirmaBirim> getBakimciFirmaBirims() {
		return this.bakimciFirmaBirims;
	}

	public void setBakimciFirmaBirims(List<BakimciFirmaBirim> bakimciFirmaBirims) {
		this.bakimciFirmaBirims = bakimciFirmaBirims;
	}

	public BakimciFirmaBirim addBakimciFirmaBirim(
			BakimciFirmaBirim bakimciFirmaBirim) {
		getBakimciFirmaBirims().add(bakimciFirmaBirim);
		bakimciFirmaBirim.setBakimciFirma(this);

		return bakimciFirmaBirim;
	}

	public BakimciFirmaBirim removeBakimciFirmaBirim(
			BakimciFirmaBirim bakimciFirmaBirim) {
		getBakimciFirmaBirims().remove(bakimciFirmaBirim);
		bakimciFirmaBirim.setBakimciFirma(null);

		return bakimciFirmaBirim;
	}

}