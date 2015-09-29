package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;





import tr.org.mmo.asansor.beans_.AnaSoru;
import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.ReferansDenetimKayitKontrolSorularListesi;
import tr.org.mmo.asansor.beans_.Soru;
import tr.org.mmo.asansor.business.BasvuruBusiness;
import tr.org.mmo.asansor.business.RaporBusiness;
import tr.org.mmo.asansor.dto.BakanlikSoruDTO;
import tr.org.mmo.asansor.dto.CurrVal;
import tr.org.mmo.asansor.dto.KontrolDTO;
import tr.org.mmo.asansor.dto.KontrolFirmaKatilimDTO;
import tr.org.mmo.asansor.dto.KontrolTestDTO;
import tr.org.mmo.asansor.dto.OnTanimliTestDTO;
import tr.org.mmo.asansor.dto.ReferansDenetimSorularEslestirmeDTO;
import tr.org.mmo.asansor.dto.SoruDTO;
import tr.org.mmo.asansor.dto.SoruOnTanimliDTO;
import tr.org.mmo.asansor.dto.TestDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.managedbeans.LoginBean;
import tr.org.mmo.asansor.managedbeans.SessionBean;
import tr.org.mmo.asansor.util.Ayarlar;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;

public class SoruListDAOImpl implements SoruListDAO {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Soru soruGetir(Integer soruId) {
		Soru s = new Soru();

		Connection con = null;
		try {
			con = DAOBase.instance().getConnection();
		} catch (CreateException e) {

			e.printStackTrace();
		}
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		QueryRunner runner = new QueryRunner();

		try {
			if (soruId != null) {

				s = (Soru) runner.query(con, Sorgular._SORU_.qry,
						new BeanHandler(Soru.class), new Object[] { soruId });
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(con);

		}
		return s;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SoruDTO> listeGetir() {
		List<SoruDTO> liste = new ArrayList<SoruDTO>();

		Connection con = null;
		try {
			con = DAOBase.instance().getConnection();
		} catch (CreateException e) {

			e.printStackTrace();
		}
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		QueryRunner runner = new QueryRunner();

		try {

			liste = (List<SoruDTO>) runner.query(con, Sorgular._SORULIST_.qry,
					new BeanListHandler(SoruDTO.class));

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			DbUtils.closeQuietly(con);
		}

		return liste == null ? new ArrayList<SoruDTO>() : liste;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<AnaSoru> anaListeGetir(int cihazTipi, boolean asansorTest,java.util.Date tarih)
			throws ReadException {
		List<AnaSoru> liste = new ArrayList<AnaSoru>();

		Connection con = null;
		
		try {
			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date t=new java.util.Date();
		
			
				t=tarih==null?df.parse("0001-01-01"):tarih;
			
			
			QueryRunner runner = new QueryRunner();
			StringBuilder sb = new StringBuilder().append(
					Sorgular._ANASORULIST_.qry).append(
					asansorTest ? " and aktif='A'" : "");
			
			
			
			liste = (List<AnaSoru>) runner.query(con, sb.toString(),
					new BeanListHandler(AnaSoru.class),
					new Object[] { cihazTipi,new Date(t.getTime()) });
			for (AnaSoru s : liste) {
				s.setAltSorular(altListeGetir(con, s.getSorun(), cihazTipi,
						asansorTest,t));
				for (SoruDTO d : s.getAltSorular()) {
					d.setList(getOnTanimliSorular(con, d.getSoruId(),
							asansorTest));
				}
			}

		} catch (ParseException e) {
			throw new ReadException(Messages._SQL_514_.getMesaj(), null);
		}
		catch (CreateException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {

			DbUtils.closeQuietly(con);
		}

		return liste == null ? new ArrayList<AnaSoru>() : liste;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<AnaSoru> testSorulariGetir(int cihazTipi,boolean bakanlikSoru) throws ReadException {
		List<AnaSoru> liste = new ArrayList<AnaSoru>();

		Connection con = null;
		try {
			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);

			QueryRunner runner = new QueryRunner();
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			SessionBean sessionBean=(SessionBean)Common.findBean("sessionBean");
			java.util.Date t=new java.util.Date();
		
			try {
				t=!bakanlikSoru?df.parse("0001-01-01"):sessionBean.getBakanlikSoru().getTarih();
			} catch (ParseException e) {
				t=new java.util.Date();
			}
			
			
			liste = (List<AnaSoru>) runner.query(con,
					Sorgular._ANASORULISTTESTSORUGIRIS_.qry,
					new BeanListHandler(AnaSoru.class),
					new Object[] { cihazTipi , new Date(t.getTime())});

			for (AnaSoru s : liste) {
				s.setAltSorular(testSorulariAltListeGetir(con, s.getSoruId(),
						cihazTipi));
			}

		} catch (CreateException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {

			DbUtils.closeQuietly(con);
		}

		return liste;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<SoruDTO> altListeGetir(Connection con, String ustSorun,
			int cihazTipi, boolean asansorTest,java.util.Date tarih) throws ReadException {
		List<SoruDTO> liste = new ArrayList<SoruDTO>();

		QueryRunner runner = new QueryRunner();

		try {
			StringBuilder sb = new StringBuilder().append(
					Sorgular._ALTSORULIST_.qry).append(
					asansorTest ? " and aktif='A'" : "");
			liste = (List<SoruDTO>) runner.query(con, sb.toString(),
					new BeanListHandler(SoruDTO.class), new Object[] {
							ustSorun + '%', cihazTipi,new Date(tarih.getTime()) });

		} catch (SQLException e) {

			throw new ReadException(Messages._SQL_500_.getMesaj(), null);

		}

		return liste == null ? new ArrayList<SoruDTO>() : liste;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<SoruDTO> testSorulariAltListeGetir(Connection con, int parent,
			int cihazTipi) throws ReadException {
		List<SoruDTO> liste = new ArrayList<SoruDTO>();

		QueryRunner runner = new QueryRunner();

		try {

			liste = (List<SoruDTO>) runner.query(con,
					Sorgular._TESTALTSORULIST_.qry, new BeanListHandler(
							SoruDTO.class), new Object[] { parent, cihazTipi });

		} catch (SQLException e) {

			throw new ReadException(Messages._SQL_500_.getMesaj(), null);

		}

		return liste == null ? new ArrayList<SoruDTO>() : liste;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SoruDTO> testSorulariAltListeGetir(int parent, int cihazTipi)
			throws ReadException {
		List<SoruDTO> liste = new ArrayList<SoruDTO>();

		QueryRunner runner = new QueryRunner();
		Connection con = null;
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			liste = (List<SoruDTO>) runner.query(con,
					Sorgular._TESTALTSORULIST_.qry, new BeanListHandler(
							SoruDTO.class), new Object[] { parent, cihazTipi });

		} catch (CreateException e) {

			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {

			throw new ReadException(Messages._SQL_500_.getMesaj(), null);

		} finally {
			DbUtils.closeQuietly(con);
		}

		return liste == null ? new ArrayList<SoruDTO>() : liste;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<SoruOnTanimliDTO> getOnTanimliSorular(Connection con,
			int soruNo, boolean asansorTest) throws ReadException {
		List<SoruOnTanimliDTO> liste = new ArrayList<SoruOnTanimliDTO>();

		QueryRunner runner = new QueryRunner();

		try {
			String str = asansorTest ? Sorgular._ONTANIMLISORULAR_.qry
					: Sorgular._ONTANIMLISORULARALL_.qry;

			liste = (List<SoruOnTanimliDTO>) runner.query(con, str,
					new BeanListHandler(SoruOnTanimliDTO.class), new Integer(
							soruNo));

		} catch (SQLException e) {

			throw new ReadException(Messages._SQL_500_.getMesaj(), null);

		}

		return liste == null ? new ArrayList<SoruOnTanimliDTO>() : liste;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SoruOnTanimliDTO> getOnTanimliSorular(int soruNo)
			throws ReadException {
		List<SoruOnTanimliDTO> liste = new ArrayList<SoruOnTanimliDTO>();
		Connection con = null;

		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			liste = (List<SoruOnTanimliDTO>) runner.query(con,
					Sorgular._ONTANIMLISORULARALL_.qry, new BeanListHandler(
							SoruOnTanimliDTO.class), new Integer(soruNo));

		} catch (CreateException e) {

			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {

			throw new ReadException(Messages._SQL_500_.getMesaj(), null);

		} finally {
			DbUtils.closeQuietly(con);
		}

		return liste == null ? new ArrayList<SoruOnTanimliDTO>() : liste;
	}

	// 2013/11/05 kontrol tablosu eklendi, cihaz_id burada olacak. Polimorfik
	// listeKaydet eklendi
	private void listeKaydet(Connection con, int kontrolId, List<AnaSoru> liste)
			throws CreateException {

		QueryRunner runner = new QueryRunner();

		try {
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			for (AnaSoru a : liste) {

				if (a.getDurum() != null && !a.getDurum().equals("B")
						&& a.getDurum().contains("U")) {
					runner.update(
							con,
							Sorgular._CEVAPINSERT_.qry,
							new Object[] { kontrolId, a.getSoruId(),
									a.getDurum(), "",
									loginBean.getKullanici().getKullaniciAdi() });
					
					for (SoruDTO s:a.getAltSorular()){
						if (s.getDurum() != null && !s.getDurum().equals("B")
								&& s.getDurum().contains("U")) {
						runner.update(
								con,
								Sorgular._CEVAPINSERT_.qry,
								new Object[] { kontrolId, s.getSoruId(),
										s.getDurum(), "",
										loginBean.getKullanici().getKullaniciAdi() });
						}
					}
					for (SoruDTO s:a.getOptionalAltSorular()){
						if (s.getDurum() != null && !s.getDurum().equals("B")
								&& s.getDurum().contains("U")) {
						runner.update(
								con,
								Sorgular._CEVAPINSERT_.qry,
								new Object[] { kontrolId, s.getSoruId(),
										s.getDurum(), "",
										loginBean.getKullanici().getKullaniciAdi() });
						}
					}
					
				}

			}

		} catch (SQLException e) {

			throw new CreateException(Messages._SQL_500_.getMesaj(), null);

		}

	}

	/*
	 * tablodaki parent_id ana soru id si, soru id ise on tanimlinin bağlı
	 * olduğu soru id dir
	 */
	private void onTanimliTestKaydet(Connection con, int kontrolId,
			List<AnaSoru> liste) throws CreateException {

		QueryRunner runner = new QueryRunner();

		try {

			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			for (AnaSoru a : liste) {
				if (a.getDurum() != null && !a.getDurum().equals("B")
						&& a.getDurum().contains("U"))
					for (SoruDTO s : a.getAltSorular() == null ? new ArrayList<SoruDTO>()
							: a.getAltSorular()) {

						for (int onTanimliId : s.getSelectedListId()) {
							if (onTanimliId > 0) {
								runner.update(con,
										Sorgular._ONTANIMLITESTINSERT_.qry,
										new Object[] {
												kontrolId,
												a.getSoruId(),
												s.getSoruId(),
												onTanimliId,
												loginBean.getKullanici()
														.getKullaniciAdi() });
							}

						}
					}

				for (SoruDTO s : a.getOptionalAltSorular() == null ? new ArrayList<SoruDTO>()
						: a.getOptionalAltSorular()) {

					for (int onTanimliId : s.getSelectedListId()) {
						if (onTanimliId > 0) {
							runner.update(
									con,
									Sorgular._ONTANIMLITESTINSERT_.qry,
									new Object[] { kontrolId, a.getSoruId(),
											s.getSoruId(), onTanimliId,loginBean.getKullanici()
											.getKullaniciAdi() });
						}

					}
				}
			}

		} catch (SQLException e) {

			throw new CreateException(Messages._SQL_500_.getMesaj(), null);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void kontrolKaydet(KontrolDTO kontrolDTO, String etiket,
			List<AnaSoru> list) throws CreateException {

		Connection con = null;
		QueryRunner runner = new QueryRunner();

		CurrVal cv = null;
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			if (kontrolDTO.getOnayDurum() == null
					|| !kontrolDTO.getOnayDurum().equals("O")) {
				new RaporBusiness().raporSil(con, kontrolDTO.getKontrolId());
				testTablosuSil(con, kontrolDTO.getKontrolId());
				kontrolFirmaKatilimSil(con, kontrolDTO.getKontrolId());
				kontrolTablosuSil(con, kontrolDTO.getKontrolId());
				onTanimliTestTablosuSil(con, kontrolDTO.getKontrolId());

			}

			if (kontrolDTO.getOnayDurum() != null
					&& kontrolDTO.getOnayDurum().equals("O")) {
				if (!kontrolDTO.isRevizyonRapor()) {
					throw new CreateException(
							Messages._ONAYLIRAPORSILHATA_.getMesaj(), null);
				}
			}
			Calendar kBasTar = Calendar.getInstance();
			Calendar kBitTar = Calendar.getInstance();
			kBasTar.setTime(kontrolDTO.getKontrolBaslangicTarihi());
			kBitTar.setTime(kontrolDTO.getKontrolBitisTarihi());
			Calendar kBasTime = Calendar.getInstance();
			kBasTime.setTime(kontrolDTO.getKontrolBaslangicSaati());
			Calendar kBitTime = Calendar.getInstance();
			kBitTime.setTime(kontrolDTO.getKontrolBitisSaati());
			kBasTar.set(Calendar.HOUR_OF_DAY,
					kBasTime.get(Calendar.HOUR_OF_DAY));
			kBasTar.set(Calendar.MINUTE, kBasTime.get(Calendar.MINUTE));
			kBitTar.set(Calendar.HOUR_OF_DAY,
					kBitTime.get(Calendar.HOUR_OF_DAY));
			kBitTar.set(Calendar.MINUTE, kBitTime.get(Calendar.MINUTE));
			int muhendisSicilNo;
			try {
				muhendisSicilNo = Integer.parseInt(((LoginBean) Common
						.findBean("loginBean")).getKullanici().getSicilNo());
			} catch (Exception e) {
				muhendisSicilNo = 0;
			}
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			int i = runner.update(
					con,
					Sorgular._KONTROLINSERT_.qry,
					new Object[] {
							kontrolDTO.getRandevuId(),

							new java.sql.Date(kBasTar.getTime().getTime()),
							new java.sql.Time(kBasTar.getTime().getTime()),
							new java.sql.Date(kBitTar.getTime().getTime()),
							new java.sql.Time(kBitTar.getTime().getTime()),
							kontrolDTO.getCihazId(),
							etiket,
							kontrolDTO.getOnaylayanSicilNo(),

							kontrolDTO.getKontrolTuru(),
							kontrolDTO.getAciklamalar().replaceAll("i", "İ")
									.toUpperCase(),
							kontrolDTO.isRevizyonRapor(), muhendisSicilNo,
							kullanici });
			if (i > 0) {

				cv = (CurrVal) runner.query(con, Sorgular._CURRVALKONTROL_.qry,
						new BeanHandler(CurrVal.class));

				listeKaydet(con, cv.getCurrval(), list);
				onTanimliTestKaydet(con, cv.getCurrval(), list);
				if (kontrolDTO.getFirma() != null
						&& kontrolDTO.getFirma().getKod() != 999999
						&& kontrolDTO.getFirma().getKod() > 0) {
					kontrolFirmaKatilimKaydet(con, cv.getCurrval(),
							kontrolDTO.getKontrolFirmaKatilimDTO(), kontrolDTO
									.getFirma().getKod());
				}

				new BasvuruBusiness().basvuruAsansorKontrolUpdate(con,
						kontrolDTO.getCihazId(), kontrolDTO.getRandevuId(),
						kBitTar.getTime());
				con.commit();
			}

		} catch (CRUDException e) {
			throw new CreateException(e.getMessage(), null);
		} catch (SQLException e) {
			if (e.getSQLState().equals("23505")) {
				throw new CreateException(
						Messages._ONAYLIRAPORSILHATA_.getMesaj(), null);
			}
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}

	}

	private void kontrolFirmaKatilimKaydet(Connection con, int currval,
			KontrolFirmaKatilimDTO kontrolFirmaKatilimDTO, int firmaId)
			throws CreateException {

		QueryRunner runner = new QueryRunner();

		try {

			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			runner.update(
					con,
					Sorgular._INSERTKONTROLFIRMAKATILIM_.qry,
					new Object[] {
							currval,
							firmaId,
							kontrolFirmaKatilimDTO.isFirmaKontroleKatildi(),
							kontrolFirmaKatilimDTO.getFirmaGorevli1()
									.replace("i", "İ").toUpperCase(),
							kontrolFirmaKatilimDTO.getFirmaGorevli2()
									.replace("i", "İ").toUpperCase(),
							kontrolFirmaKatilimDTO.getFirmaGorevli3()
									.replace("i", "İ").toUpperCase(),
									kontrolFirmaKatilimDTO.getFirmaGorevli1Gorev()
									.replace("i", "İ").toUpperCase(),
									kontrolFirmaKatilimDTO.getFirmaGorevli2Gorev()
									.replace("i", "İ").toUpperCase(),
									kontrolFirmaKatilimDTO.getFirmaGorevli3Gorev()
									.replace("i", "İ").toUpperCase(),
							loginBean.getKullanici().getKullaniciAdi() });

		} catch (SQLException e) {

			throw new CreateException(Messages._SQL_500_.getMesaj(), null);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void kontrolFirmaKatilimSil(Connection con, int kontrolId)
			throws DeleteException, SQLException {
		QueryRunner runner = new QueryRunner();
		runner.update(con, Sorgular._KONTROLFIRMAKATILIMDELETE_.qry,
				new Object[] { kontrolId });

	}

	private void kontrolTablosuSil(Connection con, int kontrolId)
			throws DeleteException, SQLException {
		QueryRunner runner = new QueryRunner();
		runner.update(con, Sorgular._KONTROLDELETE_.qry,
				new Object[] { kontrolId });
	}

	private void testTablosuSil(Connection con, int kontrolId)
			throws DeleteException, SQLException {
		QueryRunner runner = new QueryRunner();
		runner.update(con, Sorgular._TESTDELETE_.qry,
				new Object[] { kontrolId });
	}

	private void onTanimliTestTablosuSil(Connection con, int kontrolId)
			throws DeleteException, SQLException {
		QueryRunner runner = new QueryRunner();
		runner.update(con, Sorgular._ONTANIMLITESTDELETE_.qry,
				new Object[] { kontrolId });
	}

	/*
	 * İlk olarak her bir alt soru için uygunsuzluk kaydediliyordu. daha sonra
	 * ana sorulara çekildi. bu yüzden sorguda ki list yapısı TestDTO Anasoru
	 * yapısı kaydedilmeye başladıktan sonra metod değiştirilmedi. sadece
	 * eklenti yapıldı. 29.11.2014
	 */

	@Override
	public List<KontrolTestDTO> getTestYanitlarForSanayiBakanlik(int kontrolId)
			throws ReadException {
		List<KontrolTestDTO> list = new ArrayList<KontrolTestDTO>();
		QueryRunner runner = new QueryRunner();

		Connection con = null;
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (List<KontrolTestDTO>) runner.query(con,
					Sorgular._TESTYANITBYKONTROLID_.qry,
					new BeanListHandler<KontrolTestDTO>(KontrolTestDTO.class),
					new Integer(kontrolId));
			if (list != null) {
				for (KontrolTestDTO k : list) {
					k.setAltSorular(onTanimliTestGetir(con, kontrolId,
							k.getSoruId()));
				}
			}

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {

			DbUtils.closeQuietly(con);
		}

		return list == null ? new ArrayList<KontrolTestDTO>() : list;

	}

	@Override
	public List<TestDTO> getTestYanitlari(int kontrolId) throws ReadException {
		List<TestDTO> list = new ArrayList<TestDTO>();
		QueryRunner runner = new QueryRunner();

		Connection con = null;
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (List<TestDTO>) runner.query(con,
					Sorgular._TESTYANITBYKONTROLID_.qry,
					new BeanListHandler<TestDTO>(TestDTO.class), new Integer(
							kontrolId));

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {

			DbUtils.closeQuietly(con);
		}

		return list;

	}

	@Override
	public List<OnTanimliTestDTO> onTanimliTestGetir(int kontrolId)
			throws ReadException {
		List<OnTanimliTestDTO> list = new ArrayList<OnTanimliTestDTO>();

		QueryRunner runner = new QueryRunner();
		Connection con = null;
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (List<OnTanimliTestDTO>) runner
					.query(con, Sorgular._ONTANIMLITESTBYKONTROLID_.qry,
							new BeanListHandler<OnTanimliTestDTO>(
									OnTanimliTestDTO.class),
							new Object[] { kontrolId });

		} catch (CreateException e) {

			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		}

		catch (SQLException e) {

			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list;

	}

	private List<OnTanimliTestDTO> onTanimliTestGetir(Connection con2,
			int kontrolId, int parentId) throws ReadException {
		List<OnTanimliTestDTO> list = new ArrayList<OnTanimliTestDTO>();

		QueryRunner runner = new QueryRunner();

		try {

			list = (List<OnTanimliTestDTO>) runner.query(con2,
					Sorgular._ONTANIMLITESTBYKONTROLIDPARENTID_.qry,
					new BeanListHandler<OnTanimliTestDTO>(
							OnTanimliTestDTO.class), new Object[] { kontrolId,
							parentId });

		} catch (SQLException e) {

			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		}
		return list == null ? new ArrayList<OnTanimliTestDTO>() : list;

	}

	@SuppressWarnings("resource")
	@Override
	public void testSorusuGuncelle(SoruDTO s) throws UpdateException {
		QueryRunner runner = new QueryRunner();

		Connection con = null;

		try {
			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			/************* ARRAY UPDATE HATA VERDİ ÇOKLU UPDATE E ÇEVİRDİM */
			/*
			 * Integer[] data = new Integer[s.getKapsam().length]; int j = 0;
			 * for (short sh : s.getKapsam()) { data[j] = (int) sh; j++; }
			 * 
			 * Array sqlArray = null;
			 * 
			 * sqlArray = con.createArrayOf("Integer", data);
			 * 
			 * 
			 * int i = runner.update( con, Sorgular._TESTSORUSUGUNCELLE_.qry,
			 * new Object[] { s.getSoru(), s.getParent(), s.getCihazTipi(),
			 * s.getSiraNo(), s.getYildiz(), s.getAktif(), (int[]) sqlArray,
			 * s.getSoruId() });
			 */
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			runner.update(con, Sorgular._UPDATETESTSORUSUSETKAPSAMNULL_.qry,
					new Object[] { s.getSoruId() });

			int i = runner.update(
					con,
					Sorgular._TESTSORUSUGUNCELLE_.qry,
					new Object[] { s.getSoru(), s.getParent(),
							s.getCihazTipi(), s.getSiraNo(), s.getYildiz(),
							s.getAktif(), kullanici, s.getSoruId() });

			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE ").append(Ayarlar.SHEMA).append(".SORU SET ");
			int sw = 0;
			int k = 1;
			for (short sh : s.getKapsam()) {
				if (sw == 0) {
					sw++;
					sb.append("KAPSAM[1]=").append(sh).append(" ");
				} else {
					sb.append(",").append("KAPSAM[").append(k).append("]=")
							.append(sh).append(" ");
				}
				k++;

			}
			sb.append(" WHERE SORU_ID=?");
			runner.update(con, sb.toString(), new Object[] { s.getSoruId() });

			if (i > 0)
				con.commit();
			else
				con.rollback();
		} catch (CreateException e) {

			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {

			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);

		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public void onTanimliSorusuGuncelle(SoruOnTanimliDTO s)
			throws UpdateException {
		QueryRunner runner = new QueryRunner();
		Connection con = null;

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			int i = runner.update(
					con,
					Sorgular._ONTANIMLISORUSUGUNCELLE_.qry,
					new Object[] { s.getAciklama(), kullanici, s.getDurum(),
							s.getId() });
			if (i > 0)
				con.commit();
			else
				con.rollback();
		} catch (CreateException e) {

			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {

			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);

		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public void soruEkle(SoruDTO s) throws CreateException {
		QueryRunner runner = new QueryRunner();
		Connection con = null;
		
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			int i = runner.update(
					con,
					Sorgular._TESTSORUSUINSERT_.qry,
					new Object[] { s.getSoru(), s.getParent(),
							s.getCihazTipi(), s.getSiraNo(), s.getYildiz(),
							s.getAktif(), kullanici,s.getTarih()==null?null:new Date(s.getTarih().getTime()) });
			CurrVal cv = (CurrVal) runner.query(con,
					Sorgular._CURRVALTESTSORU_.qry, new BeanHandler<CurrVal>(
							CurrVal.class));
			int soruId= cv.getCurrval();
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE ").append(Ayarlar.SHEMA).append(".SORU SET ");
			int sw = 0;
			int k = 1;
			for (short sh : s.getKapsam()) {
				if (sw == 0) {
					sw++;
					sb.append("KAPSAM[1]=").append(sh).append(" ");
				} else {
					sb.append(",").append("KAPSAM[").append(k).append("]=")
							.append(sh).append(" ");
				}
				k++;

			}
			sb.append(" WHERE SORU_ID=?");
			runner.update(con, sb.toString(), new Object[] { soruId });
			if (i > 0)
				con.commit();
			else
				con.rollback();
		} catch (CreateException e) {

			throw new CreateException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {

			throw new CreateException(Messages._SQL_500_.getMesaj(), null);

		} finally {
			DbUtils.closeQuietly(con);
		}
		
	}

	@Override
	public int getMaxSiraNo(Integer cihazTipi, Integer parent,java.util.Date tarih)
			throws ReadException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		int maxSiraNo = 0;
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date t=new java.util.Date();
		
			
				t=tarih==null?df.parse("0001-01-01"):tarih;
			if (parent != null) {
				stmt = con.prepareStatement(Sorgular._MAXSIRANO_.qry);
				stmt.setInt(1, cihazTipi);
				stmt.setInt(2, parent);
				stmt.setDate(3, new Date(t.getTime()));

			} else {
				stmt = con.prepareStatement(Sorgular._MAXSIRANOPARENTNULL_.qry);
				stmt.setInt(1, cihazTipi);
				stmt.setDate(2, new Date(t.getTime()));

			}
			rs = stmt.executeQuery();
			if (rs.next()) {
				maxSiraNo = rs.getInt("MAXSIRA");
			}
			maxSiraNo++;
		} catch (ParseException e) {

			throw new ReadException(Messages._SQL_514_.getMesaj(), null);

		}
		catch (CreateException e) {

			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {

			throw new ReadException(Messages._SQL_500_.getMesaj(), null);

		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(stmt);
			DbUtils.closeQuietly(con);
		}

		return maxSiraNo;
	}

	@Override
	public int insertOnTanimli(SoruOnTanimliDTO onTanimliDTO)
			throws CreateException {
		int id = 0;
		QueryRunner runner = new QueryRunner();
		Connection con = null;
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			int i = runner.update(
					con,
					Sorgular._ONTANIMLISORUEKLE_.qry,
					new Object[] { onTanimliDTO.getSoruId(),
							onTanimliDTO.getAciklama(), kullanici });
			CurrVal cv = (CurrVal) runner.query(con,
					Sorgular._CURRVALONTANIMLI_.qry, new BeanHandler<CurrVal>(
							CurrVal.class));
			id = cv.getCurrval();
			if (i > 0)
				con.commit();
			else
				con.rollback();
		} catch (CreateException e) {

			throw new CreateException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {

			throw new CreateException(Messages._SQL_500_.getMesaj(), null);

		} finally {
			DbUtils.closeQuietly(con);
		}
		return id;
	}

	@Override
	public List<ReferansDenetimKayitKontrolSorularListesi> getReferansDenetimKayitKontrolSorulari()
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		Connection con = null;
		List<ReferansDenetimKayitKontrolSorularListesi> list = new ArrayList<ReferansDenetimKayitKontrolSorularListesi>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (ArrayList<ReferansDenetimKayitKontrolSorularListesi>) runner
					.query(con,
							Sorgular._GETREFERANSDENETIMKAYITKONTROLSORULAR_.qry,
							new BeanListHandler<ReferansDenetimKayitKontrolSorularListesi>(
									ReferansDenetimKayitKontrolSorularListesi.class));

		} catch (CreateException e) {

			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {

			throw new ReadException(Messages._SQL_500_.getMesaj(), null);

		} finally {
			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<ReferansDenetimKayitKontrolSorularListesi>()
				: list;
	}
	
	@Override
	public List<ReferansDenetimSorularEslestirmeDTO> getReferansDenetimKayitEslestirme() throws ReadException{
		QueryRunner runner = new QueryRunner();
		Connection con = null;
		List<ReferansDenetimSorularEslestirmeDTO> list = new ArrayList<ReferansDenetimSorularEslestirmeDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (ArrayList<ReferansDenetimSorularEslestirmeDTO>) runner
					.query(con,
							Sorgular._GETREFERANSDENETIMKAYITESLESTIRME_.qry,
							new BeanListHandler<ReferansDenetimSorularEslestirmeDTO>(
									ReferansDenetimSorularEslestirmeDTO.class));

		} catch (CreateException e) {

			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {

			throw new ReadException(Messages._SQL_500_.getMesaj(), null);

		} finally {
			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<ReferansDenetimSorularEslestirmeDTO>()
				: list;
	}

	@Override
	public BakanlikSoruDTO getBakanlikSoruTarih() throws ReadException{
		QueryRunner runner = new QueryRunner();
		Connection con = null;
		BakanlikSoruDTO bakanlikSoru=new BakanlikSoruDTO();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			bakanlikSoru = (BakanlikSoruDTO) runner
					.query(con,
							Sorgular._GETBAKANLIKSORUTARIH_.qry,
							new BeanHandler<BakanlikSoruDTO>(
									BakanlikSoruDTO.class));

		} catch (CreateException e) {

			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {

			throw new ReadException(Messages._SQL_500_.getMesaj(), null);

		} finally {
			DbUtils.closeQuietly(con);
		}
		return bakanlikSoru == null ? new BakanlikSoruDTO():bakanlikSoru;
	}

}
