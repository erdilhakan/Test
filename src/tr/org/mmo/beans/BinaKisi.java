package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the bina_kisi database table.
 * 
 */
@Entity
@Table(name="bina_kisi",schema="test")
public class BinaKisi implements Serializable {
	private static final long serialVersionUID = 1L;

	private String adi;

	@Column(name="e_posta")
	private String ePosta;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name="kayit_tarihi")
	private Date kayitTarihi;

	@Column(name="sorumluluk_turu")
	private Integer sorumlulukTuru;

	private String soyadi;

	private Long tckimlikno;

	@Column(name="telefon_no")
	private Long telefonNo;

	@Column(name="telefon_no_gsm")
	private Long telefonNoGsm;

	//bi-directional many-to-one association to Bina
	@ManyToOne
	@JoinColumn(name="bina_id")
	private Bina bina;

	public BinaKisi() {
	}

	public String getAdi() {
		return this.adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public String getEPosta() {
		return this.ePosta;
	}

	public void setEPosta(String ePosta) {
		this.ePosta = ePosta;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getKayitTarihi() {
		return this.kayitTarihi;
	}

	public void setKayitTarihi(Date kayitTarihi) {
		this.kayitTarihi = kayitTarihi;
	}

	public Integer getSorumlulukTuru() {
		return this.sorumlulukTuru;
	}

	public void setSorumlulukTuru(Integer sorumlulukTuru) {
		this.sorumlulukTuru = sorumlulukTuru;
	}

	public String getSoyadi() {
		return this.soyadi;
	}

	public void setSoyadi(String soyadi) {
		this.soyadi = soyadi;
	}

	public Long getTckimlikno() {
		return this.tckimlikno;
	}

	public void setTckimlikno(Long tckimlikno) {
		this.tckimlikno = tckimlikno;
	}

	public Long getTelefonNo() {
		return this.telefonNo;
	}

	public void setTelefonNo(Long telefonNo) {
		this.telefonNo = telefonNo;
	}

	public Long getTelefonNoGsm() {
		return this.telefonNoGsm;
	}

	public void setTelefonNoGsm(Long telefonNoGsm) {
		this.telefonNoGsm = telefonNoGsm;
	}

	public Bina getBina() {
		return this.bina;
	}

	public void setBina(Bina bina) {
		this.bina = bina;
	}

}