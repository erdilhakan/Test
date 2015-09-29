package tr.org.mmo.asansor.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.business.RaporBusiness;
import tr.org.mmo.asansor.dao.BaglantiYoneticisi;
import tr.org.mmo.asansor.dao.DAOBase;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.CihazDTO;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.dto.KontrolDTO;
import tr.org.mmo.asansor.dto.RaporDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.util.Ayarlar;
import tr.org.mmo.asansor.util.DateUtils;
import tr.org.mmo.asansor.util.DenetimKayitUtils;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;

public class Test {

	private static BaglantiYoneticisi dao = BaglantiYoneticisi.getInstance();

	static int upd = 0;
	public static List<RaporDTO> getDenetimKayitRaporlar(Date tarih1,Date tarih2,Integer kontrolId) throws ReadException{
		List<RaporDTO> raporlar=new ArrayList<RaporDTO>();
		List<CihazDTO> cihazlar=new ArrayList<CihazDTO>();
		QueryRunner runner = new QueryRunner();
		Connection con=null;

		try {
			con = new BaglantiYoneticisi().getConnection();
			con.setAutoCommit(false);
			
		//con=new BaglantiYoneticisi().getConnection();
		con.setAutoCommit(false);
				
				
				
				List<KontrolDTO> kontroller=new ArrayList<KontrolDTO>();
				kontroller=(ArrayList<KontrolDTO>)runner.query(con,"select cihaz_id as cihazid,kontrol_id as kontrolid from akm.kontrol a "
						+ "where kontrol_baslangic_tarihi between ? and ? and onay_durum='O' and kontrol_id>? order by kontrol_id"
						, new BeanListHandler<KontrolDTO>(KontrolDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime()),kontrolId.intValue()});
				
				List<KontrolDTO> k1=new ArrayList<KontrolDTO>();
				
				if (kontroller!=null){
					for (KontrolDTO k:kontroller){
						CihazDTO c=new CihazDTO();
						c =(CihazDTO)runner.query(con,"select cihaz_id as cihazid from akm.bina a,akm.cihaz c"
								+ " where c.cihaz_id=? and a.uavt_kod>0 and a.bina_id=c.bina_id", 
								new BeanHandler<CihazDTO>(CihazDTO.class),new Object[]{k.getCihazId()});
						if (c!=null && c.getCihazId()>0){
							k1.add(k);
						}
					}
				}
					if (k1!=null){
					for (KontrolDTO k:k1){
					RaporDTO r1=new RaporDTO();
					r1 = (RaporDTO) runner.query(con,
							"select rapor_id as raporid,kontrol_id as kontrolid,dosya_adi as dosyaadi,"
									+ "rapor_tarihi as raportarihi,dosya,kontrol_kod_eski as kontrolkodeski,rapor_iptal as raporiptal "
									+ " from "
									+ "akm.rapor rapor where rapor.kontrol_id =? and "
									+ " not rapor_iptal and "
									
									+ " not exists (Select 1 from "
									+ "uavt.rapor_kayit where raporid=rapor.rapor_id)",
							new BeanHandler<RaporDTO>(RaporDTO.class),new Object[]{k.getKontrolId()});	
					if (r1!=null && r1.getRaporId()>0){
						raporlar.add(r1);
					}
					}
					}
					
				
				

		} catch (SQLException e) {
			System.out.println();
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return raporlar==null?new ArrayList<RaporDTO>():raporlar;
	}
	public static void main(String[] args) {
		
		try {
    		int kayitAdet=0;
    		DateFormat df=new SimpleDateFormat("dd.MM.yyyy");
    		Date tarih1=null;
    		Date tarih2=null;
    		
			try {
				tarih1 = df.parse("01.01.2015");
				tarih2 = df.parse("05.01.2015");
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
			List<RaporDTO> raporlar=Test.getDenetimKayitRaporlar(tarih1,tarih2,0);
			if (raporlar.size()>0){
				System.out.println("Denetim Kayıt için gönderilecek rapor adet: "+raporlar.size() + " :" +new Date());
				for (RaporDTO r:raporlar){
					boolean denetimKaydedildi=false;
					denetimKaydedildi=new DenetimKayitUtils().batchAsansorDenetimKayit(r);
					if (denetimKaydedildi)
						kayitAdet++;
				}
				System.out.println("Kaydedilen rapor adet: "+kayitAdet + " :" +new Date());
			}else{
				System.out.println("Denetim Kayıt için gönderilecek rapor kaydı bulunamadı :"+new Date());
			}
		} catch (CRUDException e) {
			System.out.println("Rapor sorgusunda hata oluştu :"+new Date());
			e.printStackTrace();
		}
		/*
		List<String> strList=new ArrayList<String>();
		strList.add("AMASRA(BARTIN)");
		strList.add("ÇERKEZKÖY (OSB)");
		strList.add("ÇORLU(TEKİRDAĞ)");
		strList.add("PALANDÖKEN (ERZURUM)");
		strList.add("PALANDÖKEN (ERZURUM)_");
		strList.add("ESKİŞEHİR OSB");
		strList.add("TUZLA KİMYA (OSB)");
		strList.add("SEFERİHİSAR(İZMİR)");
		strList.add("KARACASU(BOLU)");
		strList.add("TOSB OTOMOTİV YAN SANAYİ OSB ");
		strList.add("ULUS (BARTIN)");
		strList.add("GÖYNÜK(ANTALYA)");
		strList.add("GÖYNÜK_(ANTALYA)");
		strList.add("İNEGÖL OSB");
		strList.add("İSTANBUL ANADOLU YAKASI OSB");
		strList.add("NOSAB ( NİLÜFER OSB )");
		strList.add("NİLÜFER OSB");
		strList.add("KAYAPINAR (DİYARBAKIR)");
		strList.add("İSKENDERUN (HATAY)");
		strList.add("GEDİZ(KÜTAHYA)");
		
		for (String str:strList){
			//str=str.replace("OSB", "");
			int io=str.indexOf("(");
			if (io>-1){
			str=str.substring(0,str.indexOf("("));
			}
			System.out.println(str.trim());
		}
		*/

		/*
		ServisSonucOfArrayOfBelediye8Zb117HL servisSonucOfArrayOfBelediye = new ServisSonucOfArrayOfBelediye8Zb117HL();
		BstbAsansorDenetimServiceClient client;
		IBstbAsansorDenetimServis IService;
		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();

			File file = new File(request.getSession().getServletContext()
					.getRealPath("/keystore/")
					+ "/dsl.jks");

			client = new BstbAsansorDenetimServiceClient(file.getAbsolutePath());
			// client= BstbAsansorDenetimServiceClient.INSTANCE;
			IService = client.port();
			servisSonucOfArrayOfBelediye = IService.ileGoreBelediyeSorgulama(6);
			if (!servisSonucOfArrayOfBelediye.isHata()) {
				List<Belediye> list = new ArrayList<Belediye>();
				list = servisSonucOfArrayOfBelediye.getSonuc().getValue()
						.getBelediye();

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
*/
	}

	public static void main1() {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			long l = DateUtils.differenceBetweenTwoDates(new Date(),
					df.parse("01-04-2015"));
			System.out.println(l);
		} catch (Exception e) {

		}

	}

	private static List<FirmaDTO> getUpdateFirmalar() throws ReadException {
		QueryRunner runner = new QueryRunner();
		Connection con = null;

		List<FirmaDTO> list = new ArrayList<FirmaDTO>();
		try {
			con = dao.getConnection();
			con.setAutoCommit(false);
			list = (List<FirmaDTO>) runner
					.query(con,
							"select  kod, unvan, adsoyad, gsm_telefon, il, ilce, adres, durumu, tescil_no,"
									+ "uygunluk_belgesi, gecerlilik_suresi, servis_sozlesme, sozlesme_tarihi,"
									+ "tse_belge_no, eposta,monte_eden as monteEden,"
									+ "yetkili_servis as yetkiliServis,"
									+ "tse_belgesi as tseBelgesi,"
									+ "telefon_no as telefonNo,"
									+ "telefon_no_dahili as dahili,"
									+ "ce_belge_tipi as ceBelgeTipi FROM "
									+ "akm.bakimci_firma WHERE durumu='1'",
							new BeanListHandler<FirmaDTO>(FirmaDTO.class));

		} catch (Exception e) {
			throw new ReadException(e.getMessage(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list;
	}

	private static List<TSEFirma> getTSEFirmalar() throws ReadException {
		QueryRunner runner = new QueryRunner();
		Connection con = null;
		List<TSEFirma> list = new ArrayList<TSEFirma>();
		try {
			con = dao.getConnection();
			con.setAutoCommit(false);
			list = (List<TSEFirma>) runner.query(con,
					"select * from akm.bakimci", new BeanListHandler<TSEFirma>(
							TSEFirma.class));

		} catch (Exception e) {
			throw new ReadException(e.getMessage(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list;
	}

	private static void updateFirma(List<TSEFirma> firmalar)
			throws UpdateException {
		QueryRunner runner = new QueryRunner();
		Connection con = null;
		List<FirmaDTO> list = new ArrayList<FirmaDTO>();
		try {
			con = dao.getConnection();
			con.setAutoCommit(false);
			for (TSEFirma t : firmalar) {
				list = (List<FirmaDTO>) runner
						.query(con,
								"select  kod, unvan, adsoyad, gsm_telefon, il, ilce, adres, durumu, tescil_no,"
										+ "uygunluk_belgesi, gecerlilik_suresi, servis_sozlesme, sozlesme_tarihi,"
										+ "tse_belge_no, eposta,monte_eden as monteEden,"
										+ "yetkili_servis as yetkiliServis,"
										+ "tse_belgesi as tseBelgesi,"
										+ "telefon_no as telefonNo,"
										+ "telefon_no_dahili as dahili,"
										+ "ce_belge_tipi as ceBelgeTipi FROM "
										+ "akm.bakimci_firma WHERE durumu='1' and ltrim(rtrim(replace(replace(replace(tse_belge_no,'-',''),'/',''),' ','')))=?",
								new BeanListHandler<FirmaDTO>(FirmaDTO.class),
								new String(t.getTsebelgeno().replace("-", "")
										.replace("/", "").replace(" ", "")
										.trim()));

				if (list.size() > 0) {
					for (FirmaDTO f : list) {
						FirmaDTO f1 = new FirmaDTO();
						f1.setUnvan(t.getUnvan());
						f1.setAdSoyad(nvl(t.getAdsoyad(), f.getAdSoyad()));
						f1.setGsmTelefon(nvl2(t.getGsm_telefon()));
						f1.setIl(nvl2(t.getIl()));
						f1.setIlce(f.getIlce());
						f1.setAdres(nvl(t.getAdres(), f.getAdres()));
						f1.setDurumu("1");
						f1.setTescil_No(f.getTescilNo());
						f1.setTse_Belge_No(t.getTsebelgeno());
						f1.setEposta(nvl(t.getEmail(), f.getEposta()));
						f1.setMonteEden(f.getMonteEden());
						f1.setYetkiliServis(f.getYetkiliServis());
						f1.setTseBelgesi(t.getTsebelge().equals("Var") ? "E"
								: "H");
						f1.setTelefonNo(nvl2(t.getTelefon()));
						f1.setDahili(nvl2(t.getDahili()));
						f1.setCeBelgeTipi(f1.getCeBelgeTipi());
						// f1.set
						// f1.setAdres(nvl(t.getAdres(),f.getAdres()));

					}

				}
			}

		} catch (Exception e) {
			throw new UpdateException(e.getMessage(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	public static String nvl(String str, String str1) {
		return str == null ? "" : (str.trim().replace("-", "").replace("/", "")
				.length() > 2 ? str.trim() : str1);
	}

	public static int nvl2(String str) {
		return str == null ? 0 : Integer.parseInt(str);
	}

}
