package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.business.AsansorBusiness;
import tr.org.mmo.asansor.business.BinaBusiness;
import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.business.KontrolBusiness;
import tr.org.mmo.asansor.business.MuhendisBusiness;
import tr.org.mmo.asansor.business.RandevuBusiness;
import tr.org.mmo.asansor.business.RaporBusiness;
import tr.org.mmo.asansor.dto.BasvuruDTO;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.dto.KontrolDTO;
import tr.org.mmo.asansor.dto.KontrolFirmaKatilimDTO;
import tr.org.mmo.asansor.dto.RaporKayitDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.managedbeans.LoginBean;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;

public class KontrolDAOImpl implements KontrolDAO {

	private Connection con = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<KontrolDTO> kontrolListesi() throws ReadException {
		List<KontrolDTO> kontrolList = new ArrayList<KontrolDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			Kullanici kullanici = ((LoginBean) Common.findBean("loginBean"))
					.getKullanici();
			int sicilNo = 0;
			StringBuffer sb = new StringBuffer();
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			java.util.Date kontrolTarihi = new java.util.Date();

			kontrolTarihi = df.parse("01.12.2014");

			boolean merkezKullanici = false;
			if (kullanici != null && kullanici.getSicilNo() != null) {
				sicilNo = Integer.parseInt(kullanici.getSicilNo());

			}
			sb.append(Sorgular._GETKONTROLFORKONTROLLISTESI_.qry);
			kontrolList = (List<KontrolDTO>) runner.query(
					con,
					sb.toString(),
					new BeanListHandler(KontrolDTO.class),
					new Object[] {sicilNo,
							new java.sql.Date(kontrolTarihi.getTime())
							 });
			/*
			 * TIME OUT BİLDİRİMİNDEN KAYNAKLI 
			 * 11.08.2015 TARİHİNDE SORGU DEĞİŞTİRİLDİ...(HAKAN)
			 * 
			 * 
			if (merkezKullanici) {
				sb.append(Sorgular._GETKONTROLLER_.qry);
				kontrolList = (List<KontrolDTO>) runner.query(
						con,
						sb.toString(),
						new BeanListHandler(KontrolDTO.class),
						new Object[] { new java.sql.Date(kontrolTarihi
								.getTime()) });
			} else {
				sb.append(Sorgular._GETKONTROLLER__.qry);
				kontrolList = (List<KontrolDTO>) runner.query(
						con,
						sb.toString(),
						new BeanListHandler(KontrolDTO.class),
						new Object[] {
								new java.sql.Date(kontrolTarihi.getTime()),
								sicilNo, sicilNo });

			}
			*/
			
			for (KontrolDTO kontrol : kontrolList) {
				kontrol.setKontrolMuhendisleri(new RandevuBusiness()
						.getRandevuMuhendis(con, kontrol.getRandevuId()));
				kontrol.setBina(new BinaBusiness().binaBulByRandevuId(con,
						kontrol.getRandevuId()));
				kontrol.setBinaKisiList(new BinaBusiness().binaKisiler(con,
						kontrol.getBina().getBinaId()));
				kontrol.setFirma(new FirmaBusiness().getCihazKontrolFirma(con,
						kontrol.getRandevuId(), kontrol.getCihazId()));
				if (kontrol.getKontrolFirmaKatilimDTO().isFirmaKontroleKatildi()){
					
				}
				if (kontrol.getCihazId() > 0) {
					kontrol.setCihaz(new AsansorBusiness().getCihaz(con,
							kontrol.getCihazId()));
					kontrol.setCihazTeknikBilgiler(new AsansorBusiness()
							.getCihaz(con, kontrol.getCihazId(), kontrol
									.getCihaz().getTip()));

					if (kontrol.getKontrolTuru().equals("N")) {
						kontrol.setOncekiKontroller(getCihazKontroller(con,
								kontrol.getCihazId(),
								kontrol.getKontrolBitisTarihi()));
					}

				}

				Kullanici muhendis = new Kullanici();

				muhendis = new Kullanici();
				muhendis = new MuhendisBusiness().getMuhendis(con,
						kontrol.getOnaylayanSicilNo());
				if (muhendis != null) {
					kontrol.setOnaylayanAdiSoyadi(muhendis.getAdi().trim()
							+ " " + muhendis.getSoyadi().trim());
				}
			}
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), e);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} catch (ParseException e) {
			throw new ReadException("Hatalı Tarih", null);
		} finally {
			DbUtils.closeQuietly(con);
		}

		return kontrolList == null ? new ArrayList<KontrolDTO>() : kontrolList;
	}

	@Override
	public KontrolDTO kontrolByKontrolId(int kontrolId) throws ReadException {
		KontrolDTO kontrol = new KontrolDTO();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			Kullanici kullanici = ((LoginBean) Common.findBean("loginBean"))
					.getKullanici();
			int sicilNo = 0;
			StringBuffer sb = new StringBuffer();
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			java.util.Date kontrolTarihi = new java.util.Date();

			kontrolTarihi = df.parse("01.12.2014");

			boolean merkezKullanici = false;
			if (kullanici != null && kullanici.getSicilNo() != null) {
				sicilNo = Integer.parseInt(kullanici.getSicilNo());

			}

			if (merkezKullanici) {
				sb.append(Sorgular._GETKONTROLLER_.qry);
				sb.append(" and a.kontrol_id=?");
				kontrol = (KontrolDTO) runner.query(
						con,
						sb.toString(),
						new BeanHandler<KontrolDTO>(KontrolDTO.class),
						new Object[] {
								new java.sql.Date(kontrolTarihi.getTime()),
								kontrolId });
			} else {
				sb.append(Sorgular._GETKONTROLLER__.qry);
				sb.append(" and a.kontrol_id=?");
				kontrol = (KontrolDTO) runner.query(
						con,
						sb.toString(),
						new BeanHandler<KontrolDTO>(KontrolDTO.class),
						new Object[] {
								new java.sql.Date(kontrolTarihi.getTime()),
								sicilNo, sicilNo, kontrolId });

			}

			if (kontrol != null) {
				kontrol.setKontrolMuhendisleri(new RandevuBusiness()
						.getRandevuMuhendis(con, kontrol.getRandevuId()));
				kontrol.setBina(new BinaBusiness().binaBulByRandevuId(con,
						kontrol.getRandevuId()));
				kontrol.setBinaKisiList(new BinaBusiness().binaKisiler(con,
						kontrol.getBina().getBinaId()));
				kontrol.setFirma(new FirmaBusiness().getCihazKontrolFirma(con,
						kontrol.getRandevuId(), kontrol.getCihazId()));
				if (kontrol.getCihazId() > 0) {
					kontrol.setCihaz(new AsansorBusiness().getCihaz(con,
							kontrol.getCihazId()));
					kontrol.setCihazTeknikBilgiler(new AsansorBusiness()
							.getCihaz(con, kontrol.getCihazId(), kontrol
									.getCihaz().getTip()));

					if (kontrol.getKontrolTuru().equals("N")) {
						kontrol.setOncekiKontroller(getCihazKontroller(con,
								kontrol.getCihazId(),
								kontrol.getKontrolBitisTarihi()));
					}

				}

				Kullanici muhendis = new Kullanici();

				muhendis = new Kullanici();
				muhendis = new MuhendisBusiness().getMuhendis(con,
						kontrol.getOnaylayanSicilNo());
				if (muhendis != null) {
					kontrol.setOnaylayanAdiSoyadi(muhendis.getAdi().trim()
							+ " " + muhendis.getSoyadi().trim());
				}
			}
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), e);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} catch (ParseException e) {
			throw new ReadException("Hatalı Tarih", null);
		} finally {
			DbUtils.closeQuietly(con);
		}

		return kontrol == null ? new KontrolDTO() : kontrol;
	}

	@Override
	public List<KontrolDTO> getCihazKontroller(int cihazId)
			throws ReadException {
		List<KontrolDTO> kontrolList = new ArrayList<KontrolDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			kontrolList = (List<KontrolDTO>) runner.query(con,
					Sorgular._GETCIHAZKONTROLTARIHLERI_.qry,
					new BeanListHandler<KontrolDTO>(KontrolDTO.class),
					new Integer(cihazId));
			Calendar cal = Calendar.getInstance();
			Calendar cal1 = Calendar.getInstance();
			for (KontrolDTO k : kontrolList) {
				FirmaDTO firma = new FirmaDTO();
				firma = new FirmaBusiness().getCihazKontrolFirma(con,
						k.getRandevuId(), cihazId);
				firma = firma == null ? new FirmaDTO() : firma;
				k.setFirma(firma);
				k.setKontrolFirmaKatilimDTO(setFirmaKatilim(con,
						k.getKontrolId()));
				k.setKontrolMuhendisleri(new RandevuBusiness()
						.getRandevuMuhendis(con, k.getRandevuId()));
				cal.setTime(k.getKontrolBaslangicSaati());
				cal1.setTime(k.getKontrolBaslangicTarihi());
				cal1.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
				cal1.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
				k.setKontrolBaslangicTarihi(cal1.getTime());
				cal = Calendar.getInstance();
				cal.setTime(k.getKontrolBitisSaati());
				cal1.setTime(k.getKontrolBitisTarihi());
				cal1.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
				cal1.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
				k.setKontrolBitisTarihi(cal1.getTime());

			}
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return kontrolList == null ? new ArrayList<KontrolDTO>() : kontrolList;
	}

	private KontrolFirmaKatilimDTO setFirmaKatilim(Connection con2,
			int kontrolId) throws ReadException {
		KontrolFirmaKatilimDTO kontrolFirmaKatilimDTO = new KontrolFirmaKatilimDTO();
		QueryRunner runner = new QueryRunner();
		try {

			kontrolFirmaKatilimDTO = (KontrolFirmaKatilimDTO) runner.query(
					con2, Sorgular._GETKONTROLFIRMAKATILIM_.qry,
					new BeanHandler<KontrolFirmaKatilimDTO>(
							KontrolFirmaKatilimDTO.class), new Integer(
							kontrolId));
		} catch (SQLException e) {
			throw new ReadException(e.getMessage(), null);
		}
		

		return kontrolFirmaKatilimDTO == null ? new KontrolFirmaKatilimDTO()
				: kontrolFirmaKatilimDTO;
	}
	
	@Override
	public KontrolFirmaKatilimDTO getFirmaKatilim(
			int kontrolId) throws ReadException {
		KontrolFirmaKatilimDTO kontrolFirmaKatilimDTO = new KontrolFirmaKatilimDTO();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			kontrolFirmaKatilimDTO = (KontrolFirmaKatilimDTO) runner.query(
					con, Sorgular._GETKONTROLFIRMAKATILIM_.qry,
					new BeanHandler<KontrolFirmaKatilimDTO>(
							KontrolFirmaKatilimDTO.class), new Integer(
							kontrolId));
		}catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		}  catch (SQLException e) {
			throw new ReadException(e.getMessage(), null);
		}
		 finally {
				DbUtils.closeQuietly(con);
			}
		return kontrolFirmaKatilimDTO == null ? new KontrolFirmaKatilimDTO()
				: kontrolFirmaKatilimDTO;
	}

	@Override
	public List<KontrolDTO> getCihazKontrollerForRapor(int cihazId,
			int kontrolId) throws ReadException {
		List<KontrolDTO> kontrolList = new ArrayList<KontrolDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			kontrolList = (List<KontrolDTO>) runner.query(con,
					Sorgular._GETCIHAZKONTROLTARIHLERIFORRAPOR_.qry,
					new BeanListHandler<KontrolDTO>(KontrolDTO.class),
					new Object[] { cihazId, kontrolId });
			Calendar cal = Calendar.getInstance();
			Calendar cal1 = Calendar.getInstance();
			for (KontrolDTO k : kontrolList) {
				k.setKontrolMuhendisleri(new RandevuBusiness()
						.getRandevuMuhendis(con, k.getRandevuId()));
				cal.setTime(k.getKontrolBaslangicSaati());
				cal1.setTime(k.getKontrolBaslangicTarihi());
				cal1.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
				cal1.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
				k.setKontrolBaslangicTarihi(cal1.getTime());
				cal = Calendar.getInstance();
				cal.setTime(k.getKontrolBitisSaati());
				cal1.setTime(k.getKontrolBitisTarihi());
				cal1.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
				cal1.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
				k.setKontrolBitisTarihi(cal1.getTime());

			}
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return kontrolList == null ? new ArrayList<KontrolDTO>() : kontrolList;
	}

	@Override
	public KontrolDTO getKontrolByKontrolId(int kontrolId) throws ReadException {
		KontrolDTO kontrol = new KontrolDTO();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			kontrol = (KontrolDTO) runner.query(con,
					Sorgular._GETKONTROLBYKONTROLID_.qry,
					new BeanHandler<KontrolDTO>(KontrolDTO.class),
					new Object[] { kontrolId });
			if (kontrol != null) {

				kontrol.setKontrolMuhendisleri(new RandevuBusiness()
						.getRandevuMuhendis(con, kontrol.getRandevuId()));
				kontrol.setBina(new BinaBusiness().binaBulByRandevuId(con,
						kontrol.getRandevuId()));
				if (kontrol.getBina()==null || kontrol.getBina().getBinaId()<=0){
					kontrol.setBina(new BinaBusiness().binaBulForRaporGonder(con,
							kontrol.getRandevuId()));
				}
				kontrol.setBinaKisiList(new BinaBusiness().binaKisiler(con,
						kontrol.getBina().getBinaId()));
				kontrol.setFirma(new FirmaBusiness().getCihazKontrolFirma(con,
						kontrol.getRandevuId(), kontrol.getCihazId()));
				kontrol.setKontrolFirmaKatilimDTO(setFirmaKatilim(con,
						kontrol.getKontrolId()));
				if (kontrol.getCihazId() > 0) {
					kontrol.setCihaz(new AsansorBusiness().getCihaz(con,
							kontrol.getCihazId()));
					kontrol.setCihazTeknikBilgiler(new AsansorBusiness()
							.getCihaz(con, kontrol.getCihazId(), kontrol
									.getCihaz().getTip()));

					if (kontrol.getKontrolTuru().equals("N")) {
						kontrol.setOncekiKontroller(getCihazKontroller(con,
								kontrol.getCihazId(),
								kontrol.getKontrolBitisTarihi()));
					}

				}

				Kullanici muhendis = new Kullanici();

				muhendis = new Kullanici();
				muhendis = new MuhendisBusiness().getMuhendis(con,
						kontrol.getOnaylayanSicilNo());
				if (muhendis != null) {
					kontrol.setOnaylayanAdiSoyadi(muhendis.getAdi().trim()
							+ " " + muhendis.getSoyadi().trim());
				}

			}

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return kontrol == null ? new KontrolDTO() : kontrol;
	}

	public List<Date> getCihazKontroller(Connection con, int cihazId,
			Date kontrolTarihi) throws ReadException {
		List<Date> oncekiKontroller = new ArrayList<Date>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(kontrolTarihi);
			pstmt = con
					.prepareStatement(Sorgular._GETCIHAZONCEKIKONTROLLER_.qry);
			pstmt.setInt(1, cihazId);
			pstmt.setDate(2, new java.sql.Date(kontrolTarihi.getTime()));
			rs = pstmt.executeQuery();
			while (rs.next()) {

				if (rs.getString("KONTROLTURU").equals("E")) {
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(rs.getDate("KONTROLBITISTARIHI"));
					if (cal.get(Calendar.MONTH) - cal1.get(Calendar.MONTH) <= 12)
						oncekiKontroller.add(rs.getDate("KONTROLBITISTARIHI"));

				}

			}
		}

		catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
		}

		return oncekiKontroller;
	}

	@Override
	public void kontrolDurumGuncelle(int kontrolId, String onayDurum)
			throws UpdateException {

		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			int adet = runner.update(con, Sorgular._KONTROLDURUMUPDATE_.qry,
					new Object[] { onayDurum, kullanici, kontrolId });
			if (adet > 0)
				con.commit();
			else
				con.rollback();
		} catch (CreateException e) {
			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public void kontrolDurumGuncelle(Connection con2, int kontrolId,
			String onayDurum) throws UpdateException {

		QueryRunner runner = new QueryRunner();
		try {
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			runner.update(con2, Sorgular._KONTROLDURUMUPDATE_.qry,
					new Object[] { onayDurum, kullanici, kontrolId });

		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL001_.getMesaj(), null);
		}

	}

	@Override
	public void kontrolSil(int kontrolId) throws DeleteException {
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			new SoruListDAOImpl().kontrolFirmaKatilimSil(con, kontrolId);
			kontrolOnTanimliSil(con, kontrolId);
			kontrolTestSil(con, kontrolId);

			int adet = runner.update(con, Sorgular._DELETEKONTROL_.qry,
					new Object[] { kontrolId });

			/*
			 * runner.update(con,Sorgular._UPDATEBASVURUASANSOR_.qry, new
			 * Object[] { });
			 */
			if (adet > 0)
				con.commit();
			else
				con.rollback();
		} catch (CreateException e) {
			throw new DeleteException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new DeleteException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	private void kontrolTestSil(Connection con2, int kontrolId)
			throws DeleteException {
		QueryRunner runner = new QueryRunner();
		try {

			runner.update(con2, Sorgular._DELETEKONTROLTEST_.qry,
					new Object[] { kontrolId });

		} catch (SQLException e) {
			throw new DeleteException(Messages._SQL001_.getMesaj(), null);
		}

	}

	private void kontrolOnTanimliSil(Connection con2, int kontrolId)
			throws DeleteException {

		QueryRunner runner = new QueryRunner();
		try {

			runner.update(con2, Sorgular._DELETEKONTROLONTANIMLI_.qry,
					new Object[] { kontrolId });

		} catch (SQLException e) {
			throw new DeleteException(Messages._SQL001_.getMesaj(), null);
		}
	}

	public int getBasvuruId(int randevuId) throws ReadException {
		BasvuruDTO b = new BasvuruDTO();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			b = runner.query(con, Sorgular._GETBASVURUID_.qry,
					new BeanHandler<BasvuruDTO>(BasvuruDTO.class),
					new Object[] { randevuId });

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return b == null ? 0 : b.getBasvuruId();

	}

	public void updateBasvuruAsansor(int basvuruId,int cihazId) throws UpdateException,
			CreateException {
		try {
			con = DAOBase.instance().getConnection();
		} catch (CreateException e1) {
			throw new CreateException(Messages._CONNECTIONAC_.getMesaj(), e1);
		}
		QueryRunner runner = new QueryRunner();
		try {
			con.setAutoCommit(false);
			int adet = runner.update(con, Sorgular._UPDATEBASVURUASANSOR_.qry,
					new Object[] { basvuruId,cihazId });
			if (adet > 0) {
				con.commit();

			}
		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public String getBakimciFirmaPersonel(int kontrolId) throws ReadException {

		KontrolFirmaKatilimDTO personel = new KontrolFirmaKatilimDTO();
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			personel = runner.query(con,
					Sorgular._GETBAKIMCIPERSONELADSOYAD_.qry,
					new BeanHandler<KontrolFirmaKatilimDTO>(
							KontrolFirmaKatilimDTO.class),
					new Object[] { kontrolId });

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return personel.getFirmaGorevli1();
	}
	
	@Override
	public void onayliKontrolIptal(Connection con2, int kontrolId) throws UpdateException{
		int i=0;
		QueryRunner runner = new QueryRunner();
		

		try {
			i=runner.update(con2, Sorgular._ONAYLIKONTROLIPTAL_.qry, new Object[]{kontrolId});
				
			if (i<=0){
				throw new UpdateException(Messages._RAPORIPTALEDILEMEDI_.getMesaj(), null);
			}

		}  catch (SQLException e) {
			if (e.getMessage().contains("duplicate key value violates unique constraint")){
				throw new UpdateException(Messages._SQL_5001_.getMesaj(), null);
				
			}else
			throw new UpdateException(Messages._SQL001_.getMesaj(), null);
			
		}
		
	}
	
	@Override
	public RaporKayitDTO checkDenetimKayit(int kontrolId,int randevuId, int cihazId,boolean revizyonRapor) throws ReadException{
		
		QueryRunner runner = new QueryRunner();
		RaporKayitDTO raporKayit=new RaporKayitDTO();
		
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			KontrolDTO kontrol = new KontrolDTO();
			kontrol = runner.query(con,
					Sorgular._RANDEVUYAAITDIGERKONTROL_.qry,
					new BeanHandler<KontrolDTO>(
							KontrolDTO.class),
					new Object[] { randevuId,cihazId,kontrolId });
			if (kontrol!=null && kontrol.getKontrolId()>0){
				
				
				raporKayit=new RaporBusiness().raporBakanliktaKayitliMi(con,kontrol.getKontrolId());
				if (raporKayit.getDenetimKayitId()>0){
					if (!revizyonRapor){
						throw new  ReadException(Messages._REVIZYONRAPORBAKANLIKTAKAYITLI_.getMesaj(),null);
					}
				}
				
			}

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return raporKayit==null?new RaporKayitDTO():raporKayit;
		
	}
}
