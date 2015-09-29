package tr.org.mmo.asansor.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.business.AsansorBusiness;
import tr.org.mmo.asansor.business.BinaBusiness;
import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.business.KontrolBusiness;
import tr.org.mmo.asansor.business.OdemeBusiness;
import tr.org.mmo.asansor.business.RandevuBusiness;
import tr.org.mmo.asansor.business.RaporBusiness;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.BasvuruDTO;
import tr.org.mmo.asansor.dto.CurrVal;
import tr.org.mmo.asansor.dto.DenetimKayitDTO;
import tr.org.mmo.asansor.dto.EskiRaporDTO;
import tr.org.mmo.asansor.dto.KontrolDTO;
import tr.org.mmo.asansor.dto.OdemeDTO;
import tr.org.mmo.asansor.dto.RaporDTO;
import tr.org.mmo.asansor.dto.RaporKayitDTO;
import tr.org.mmo.asansor.dto.RaporTeslimDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.managedbeans.LoginBean;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;

public class RaporDAOImpl implements RaporDAO {
	Connection con;

	@Override
	public int raporDosyasiOlustur(RaporDTO rapor) throws CreateException {
		QueryRunner runner = new QueryRunner();
		int raporNo = 0;

		try {

			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			new KontrolBusiness().kontrolDurumUpdate(con, rapor.getKontrolId(),
					"Y");
			int adet = runner.update(con, Sorgular._RAPORDOSYASIOLUSTUR_.qry,
					new Object[] { rapor.getKontrolId(), rapor.getDosyaAdi(),
							new Date(rapor.getRaporTarihi().getTime()),
							kullanici });

			if (adet > 0) {
				CurrVal cv = (CurrVal) runner.query(con,
						Sorgular._CURRVALRAPOR_.qry, new BeanHandler<CurrVal>(
								CurrVal.class));
				raporNo = cv.getCurrval();
				con.commit();
			} else {
				con.rollback();
			}
		} catch (CreateException e) {
			throw new CreateException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (UpdateException e) {
			throw new CreateException(e.getMessage(), null);

		} catch (SQLException e) {
			if (e.getSQLState().equals("23505")) {
				throw new CreateException(Messages._SQL_501_.getMesaj(), null);
			}
			throw new CreateException(Messages._SQL001_.getMesaj(), null);
		} finally {

			DbUtils.closeQuietly(con);
		}

		return raporNo;
	}

	@Override
	public void raporKaydet(int raporNo, FileInputStream fis, int len)
			throws UpdateException {

		PreparedStatement pstmt = null;

		// int len=0;

		try {
			// File file = new File(fileName);
			// FileInputStream fis = new FileInputStream(file);

			// len = (int)file.length();
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(Sorgular._RAPORKAYDET_.qry);

			pstmt.setBinaryStream(1, fis, len);
			pstmt.setInt(3, raporNo);
			pstmt.setString(2, kullanici);
			pstmt.executeUpdate();
			con.commit();

		}/*
		 * catch (FileNotFoundException e) { throw new
		 * UpdateException(Messages._RAPOROLUSTURULAMADI_.getMesaj(), null);
		 * //e.printStackTrace(); }
		 */
		catch (CreateException e) {
			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {

			throw new UpdateException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public boolean onaylanmayanRaporVarMi() throws ReadException {
		QueryRunner runner = new QueryRunner();

		List<RaporDTO> list = new ArrayList<RaporDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			Kullanici kullanici = ((LoginBean) Common.findBean("loginBean"))
					.getKullanici();
			int sicilNo = 0;
			StringBuffer sb = new StringBuffer();
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			java.util.Date raporTarihi = new java.util.Date();

			raporTarihi = df.parse("01.12.2014");

			if (kullanici != null && kullanici.getSicilNo() != null) {
				sicilNo = Integer.parseInt(kullanici.getSicilNo());
				sb.append(Sorgular._GETRAPORLARONLOGIN_.qry);
				list = (List<RaporDTO>) runner
						.query(con,
								sb.toString(),
								new BeanListHandler<RaporDTO>(RaporDTO.class),
								new Object[] { sicilNo,
										new Date(raporTarihi.getTime()) });

			}

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} catch (ParseException e) {
			throw new ReadException("Hatalı Tarih", null);
		}

		finally {
			DbUtils.closeQuietly(con);
		}
		return list == null ? false : (list.size() > 0 ? true : false);

	}
	
	@Override
	public boolean teslimEdilmeyenRaporVarmi() throws ReadException {
		QueryRunner runner = new QueryRunner();

		List<RaporDTO> list = new ArrayList<RaporDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			Kullanici kullanici = ((LoginBean) Common.findBean("loginBean"))
					.getKullanici();
			int sicilNo = 0;
			
			
			if (kullanici != null && kullanici.getSicilNo() != null) {
				sicilNo = Integer.parseInt(kullanici.getSicilNo());
				
				list = (List<RaporDTO>) runner
						.query(con,
								Sorgular._GETTESLIMEDILMEYENRAPORLARONLOGIN_.qry,
								new BeanListHandler<RaporDTO>(RaporDTO.class),
								new Object[] { sicilNo,sicilNo});

			}

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		}

		finally {
			DbUtils.closeQuietly(con);
		}
		return list == null ? false : (list.size() > 0 ? true : false);

	}
	
	@Override
	public List<EskiRaporDTO> teslimEdilmeyenRaporlarForOnaylayan() throws ReadException {
		QueryRunner runner = new QueryRunner();

		List<EskiRaporDTO> list = new ArrayList<EskiRaporDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			Kullanici kullanici = ((LoginBean) Common.findBean("loginBean"))
					.getKullanici();
			int sicilNo = 0;
		
			

			if (kullanici != null && kullanici.getSicilNo() != null) {
				sicilNo = Integer.parseInt(kullanici.getSicilNo());
			
				list = (List<EskiRaporDTO>) runner
						.query(con,
								Sorgular._GETTESLIMEDILMEMISRAPORLARFORONAYLAYAN_.qry,
								new BeanListHandler<EskiRaporDTO>(EskiRaporDTO.class),
								new Object[] { sicilNo,sicilNo
										});

			}

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		}

		finally {
			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<EskiRaporDTO>():list;

	}
	/*
	 * 07.04.2015 tarihinde metod date parametre aldı. default olarak verilen
	 * rapor tarihi kapatıldı.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<RaporDTO> getRaporlarOnay(java.util.Date raporTarihi)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<RaporDTO> list = new ArrayList<RaporDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			Kullanici kullanici = ((LoginBean) Common.findBean("loginBean"))
					.getKullanici();
			int sicilNo = 0;
			StringBuffer sb = new StringBuffer();
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

			// java.util.Date raporTarihi = new java.util.Date();

			// raporTarihi = df.parse("01.12.2014");

			boolean merkezKullanici = false;
			if (kullanici != null && kullanici.getSicilNo() != null) {
				sicilNo = Integer.parseInt(kullanici.getSicilNo());

			}
			if (merkezKullanici) {
				sb.append(Sorgular._GETRAPORLAR_.qry);
				list = (List<RaporDTO>) runner.query(con, sb.toString(),
						new BeanListHandler(RaporDTO.class),
						new Object[] { new Date(raporTarihi.getTime()) });
			} else {
				sb.append(Sorgular._GETRAPORLAR__.qry);
				list = (List<RaporDTO>) runner.query(con, sb.toString(),
						new BeanListHandler(RaporDTO.class), new Object[] {
								new Date(raporTarihi.getTime()), sicilNo,
								sicilNo });
			}

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		}

		finally {
			DbUtils.closeQuietly(con);
		}
		return list;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Istatistik> getRaporlar(java.util.Date tarih)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<Istatistik> list = new ArrayList<Istatistik>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();

			sb.append(Sorgular._GETRAPORLARFORISTATISTIK_.qry);
			if (((LoginBean) Common.findBean("loginBean"))
					.getKullaniciBinaKapsam() != null
					&& ((LoginBean) Common.findBean("loginBean"))
							.getKullaniciBinaKapsam().length() > 1) {
				sb.append(" AND ");
				sb.append(((LoginBean) Common.findBean("loginBean"))
						.getKullaniciBinaKapsam().replace("a.", "bina."));
			}
			list = (List<Istatistik>) runner.query(con, sb.toString(),
					new BeanListHandler(Istatistik.class),
					new Object[] { new Date(tarih.getTime()) });

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		}

		finally {
			DbUtils.closeQuietly(con);
		}
		return list;

	}

	@Override
	public List<Istatistik> getYazdirilmamisRaporlar(java.util.Date tarih)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<Istatistik> list = new ArrayList<Istatistik>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();

			sb.append(Sorgular._GETYAZDIRILMAMISRAPORLARIST_.qry);
			if (((LoginBean) Common.findBean("loginBean"))
					.getKullaniciBinaKapsam() != null
					&& ((LoginBean) Common.findBean("loginBean"))
							.getKullaniciBinaKapsam().length() > 1) {
				sb.append(" AND ");
				sb.append(((LoginBean) Common.findBean("loginBean"))
						.getKullaniciBinaKapsam().replace("a.", "bina."));
			}
			list = (List<Istatistik>) runner.query(con, sb.toString(),
					new BeanListHandler<Istatistik>(Istatistik.class),
					new Object[] { new Date(tarih.getTime()) });
			for (Istatistik kontrol : list) {
				kontrol.setKontrolMuhendisleri(new RandevuBusiness()
						.getRandevuMuhendis(con, kontrol.getRandevuId()));
				kontrol.setBina(new BinaBusiness().binaBulByRandevuId(con,
						kontrol.getRandevuId()));
				kontrol.setBinaKisiList(new BinaBusiness().binaKisiler(con,
						kontrol.getBinaId()));
				kontrol.setFirma(new FirmaBusiness().getCihazKontrolFirma(con,
						kontrol.getRandevuId(), kontrol.getCihazId()));
				if (kontrol.getCihazId() > 0) {
					kontrol.setCihaz(new AsansorBusiness().getCihaz(con,
							kontrol.getCihazId()));
					kontrol.setCihazTeknikBilgiler(new AsansorBusiness()
							.getCihaz(con, kontrol.getCihazId(), kontrol
									.getCihaz().getTip()));

				}

			}

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		}

		finally {
			DbUtils.closeQuietly(con);
		}
		return list;

	}

	@Override
	public void raporSil(RaporDTO rapor) throws DeleteException {
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			int adet = runner.update(con, Sorgular._RAPORSIL_.qry,
					new Object[] { rapor.getRaporId() });
			if (adet > 0)

				new KontrolBusiness().kontrolDurumUpdate(con,
						rapor.getKontrolId(), null);
			con.commit();
		} catch (CreateException e) {
			throw new DeleteException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (UpdateException e) {
			throw new DeleteException(e.getMessage(), null);

		} catch (SQLException e) {
			if (e.getSQLState().contains("23503") && e.getMessage().contains("rapor_teslim_fk01")){
				throw new DeleteException(Messages._TESLIMRAPORSILHATA_.getMesaj(), null);
			}else{
			throw new DeleteException(Messages._SQL001_.getMesaj(), null);
			}
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public void raporOnayla(int kontrolId) throws UpdateException {
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			int adet = runner.update(con, Sorgular._RAPOROANYLA_.qry,
					new Object[] { kullanici, kontrolId });
			if (adet > 0)
				con.commit();
		} catch (CreateException e) {
			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	public void getPDFData(int raporId) throws ReadException {

		byte[] fileBytes;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String path = ((ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext())
				.getRealPath("\\resources\\raporlar\\");
		try {

			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			ps = con.prepareStatement(Sorgular._GETPDFRAPOR_.qry);
			ps.setInt(1, raporId);

			rs = ps.executeQuery();
			if (rs.next()) {
				fileBytes = rs.getBytes(1);
				if (fileBytes == null)
					throw new ReadException(
							Messages._DOSYABULUNAMADI_.getMesaj(), null);
				OutputStream targetFile = new FileOutputStream(path + "\\"
						+ rs.getString(2));
				targetFile.write(fileBytes);
				targetFile.close();

			} else {
				throw new ReadException(Messages._DOSYABULUNAMADI_.getMesaj(),
						null);
			}

		} catch (Exception e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(con);
		}

	}

	public void setPDFData(byte[] dosya, String dosyaAdi) throws ReadException {
		String path = ((ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext())
				.getRealPath("\\resources\\raporlar\\");
		OutputStream targetFile;
		try {

			targetFile = new FileOutputStream(path + "\\" + dosyaAdi);
			targetFile.write(dosya);
			targetFile.close();
		} catch (FileNotFoundException e) {
			if (e.getMessage().contains(
					"file because it is being used by another process")) {
				throw new ReadException(Messages._RAPORACIK_.getMesaj(), null);
			} else

				throw new ReadException(Messages._DOSYABULUNAMADI_.getMesaj(),
						null);
		} catch (IOException e) {
			throw new ReadException(Messages._DOSYABULUNAMADI_.getMesaj(), null);
		}

	}

	@Override
	public List<EskiRaporDTO> getRaporlarByBinaId(int binaId)
			throws ReadException {

		QueryRunner runner = new QueryRunner();
		List<EskiRaporDTO> list = new ArrayList<EskiRaporDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sbf = new StringBuffer();
			try {
				if (((LoginBean) Common.findBean("loginBean"))
						.getKullaniciBinaKapsam() != null
						&& ((LoginBean) Common.findBean("loginBean"))
								.getKullaniciBinaKapsam().length() > 1) {
					sbf.append(" AND ");
					sbf.append(((LoginBean) Common.findBean("loginBean"))
							.getKullaniciBinaKapsam());
				}
			} catch (NullPointerException e) {
				sbf = new StringBuffer();
			}
			StringBuffer sb = new StringBuffer();
			sb.append(Sorgular._GETRAPORLARBYBINA1_.qry).append(sbf.toString())
					.append(" union all ")
					.append(Sorgular._GETRAPORLARBYBINA2_.qry)
					.append(sbf.toString());

			list = (List<EskiRaporDTO>) runner.query(con, sb.toString(),
					new BeanListHandler<EskiRaporDTO>(EskiRaporDTO.class),
					new Object[] { binaId, binaId });

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list;

	}

	@Override
	public void raporTeslimKaydet(RaporTeslimDTO rapor) throws CreateException,
			DeleteException {
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			raporTeslimSil(con, rapor.getRaporId());
			int i = runner
					.update(con,
							Sorgular._RAPORTESLIMINSERT_.qry,
							new Object[] {
									rapor.getRaporId(),
									rapor.getTeslimEdilenKisi()
											.replace('i', 'İ').toUpperCase(),
									new Date(rapor.getTeslimTarihi().getTime()),
									rapor.getTelefonNo(),
									rapor.getTcKimlikNo(),
									rapor.getAciklama().replace('i', 'İ')
											.toUpperCase(), kullanici });

			if (i > 0)
				con.commit();
			else
				con.rollback();

		} catch (SQLException e) {
			throw new CreateException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@SuppressWarnings("unused")
	private void raporTeslimSil(Connection con, int raporId)
			throws DeleteException {
		QueryRunner runner = new QueryRunner();
		try {

			int i = runner.update(con, Sorgular._RAPORTESLIMDELETE_.qry,
					new Object[] { raporId });

		} catch (SQLException e) {
			throw new DeleteException(Messages._SQL001_.getMesaj(), null);
		}

	}

	@Override
	public RaporTeslimDTO raporTeslimGetir(int raporId) throws ReadException {
		QueryRunner runner = new QueryRunner();
		RaporTeslimDTO rapor = new RaporTeslimDTO();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			rapor = (RaporTeslimDTO) runner.query(con,
					Sorgular._RAPORTESLIMGETIR_.qry,
					new BeanHandler<RaporTeslimDTO>(RaporTeslimDTO.class),
					new Object[] { raporId });
			rapor = rapor == null ? new RaporTeslimDTO() : rapor;
			rapor.setRaporId(raporId);

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return rapor;

	}

	@Override
	public List<EskiRaporDTO> getEskiRaporlar(int binaId, int cihazId)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<EskiRaporDTO> list = new ArrayList<EskiRaporDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (List<EskiRaporDTO>) runner.query(con,
					Sorgular._GETESKIRAPORLAR_.qry,
					new BeanListHandler<EskiRaporDTO>(EskiRaporDTO.class),
					new Object[] { binaId, cihazId, binaId, cihazId });

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list;

	}
	
	@Override
	public EskiRaporDTO getEskiRaporByKontrolId(int kontrolId)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		EskiRaporDTO list = new EskiRaporDTO();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (EskiRaporDTO) runner.query(con,
					Sorgular._GETESKIRAPORBYKONTROLID_.qry,
					new BeanHandler<EskiRaporDTO>(EskiRaporDTO.class),
					new Object[] { kontrolId,kontrolId });

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list;

	}

	@Override
	public void raporSil(Connection con2, int kontrolId)
			throws DeleteException, SQLException {

		QueryRunner runner = new QueryRunner();
		runner.update(con2, Sorgular._RAPORSILBYKONTROLID_.qry,
				new Object[] { kontrolId });

	}

	@Override
	public boolean isEskiRaporMu(int raporId) throws ReadException {
		boolean eskiRapor = false;
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			RaporDTO r = new RaporDTO();
			r = (RaporDTO) runner.query(con, Sorgular._RAPORGETIRBYID_.qry,
					new BeanHandler<RaporDTO>(RaporDTO.class),
					new Object[] { raporId });
			try {
				eskiRapor = r.getDosya() == null ? true : false;
			} catch (NullPointerException e) {
				eskiRapor = true;
			}
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return eskiRapor;
	}

	@Override
	public void raporByKontrolId(int kontrolId) throws ReadException {
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			RaporDTO rapor = new RaporDTO();
			rapor = (RaporDTO) runner.query(con,
					Sorgular._GETRAPORBYKONTROLID_.qry,
					new BeanHandler<RaporDTO>(RaporDTO.class),
					new Object[] { kontrolId });
			if (rapor != null && rapor.getRaporId() > 0) {
				throw new ReadException(
						Messages._RAPORVARSILINEMEZ_.getMesaj(), null);
			}

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public void raporKayitKaydet(RaporKayitDTO raporKayit)
			throws CreateException {
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			int i = runner.update(
					con,
					Sorgular._INSERTUAVTRAPORKAYIT_.qry,
					new Object[] { raporKayit.getDenetimDurumId(),
							raporKayit.getDenetimKayitId(),
							raporKayit.getRaporId(), raporKayit.getCihazId(),
							kullanici });

			if (i > 0)
				con.commit();
			else
				con.rollback();

		} catch (SQLException e) {
			throw new CreateException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public void raporKayitSil(RaporKayitDTO raporKayit) throws DeleteException,
			CreateException {
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			int i = runner.update(
					con,
					Sorgular._DELETEUAVTRAPORKAYIT_.qry,
					new Object[] { raporKayit.getDenetimKayitId(),
							raporKayit.getRaporId() });

			if (i > 0)
				con.commit();
			else
				con.rollback();

		} catch (SQLException e) {
			throw new CreateException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public List<RaporDTO> getKayitliRaporlar(List<RaporDTO> onayliRaporlar)
			throws ReadException {
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			for (RaporDTO r : onayliRaporlar) {
				RaporKayitDTO raporKayit = new RaporKayitDTO();
				raporKayit = (RaporKayitDTO) runner.query(con,
						Sorgular._GETKAYITLIRAPORLAR_.qry,
						new BeanHandler<RaporKayitDTO>(RaporKayitDTO.class),
						new Object[] { r.getRaporId() });
				raporKayit = raporKayit == null ? new RaporKayitDTO()
						: raporKayit;
				r.setRaporKayit(raporKayit);
			}

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return onayliRaporlar;
	}

	@Override
	public boolean isRevizyonRaporVarMi(int kontrolId) throws ReadException{
		QueryRunner runner = new QueryRunner();
		int i=0;

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
		
			KontrolDTO k=new KontrolDTO();
				
				k = (KontrolDTO) runner.query(con,
						Sorgular._GETREVIZYONKONTROL_.qry,
						new BeanHandler<KontrolDTO>(KontrolDTO.class),
						new Object[] {kontrolId });
				k=k==null?new KontrolDTO():k;
				i=k.getKontrolId();
				
		

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return i>0?true:false;
	}
	
	@Override
	public void onayliRaporIptal(RaporDTO rapor,int iptalKod,BasvuruAsansorDTO cihaz) throws UpdateException{
		QueryRunner runner = new QueryRunner();
		int i=0;

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
		
			i=runner.update(con, Sorgular._ONAYLIRAPORIPTAL_.qry, new Object[]{rapor.getRaporId()});
				
			if (i>0){
				
				new KontrolBusiness().onayliKontrolIptal(con,rapor.getKontrolId());
				
				new OdemeBusiness().odemeIptal(con,rapor.getBasvuruId(),rapor.getCihazId(),iptalKod,cihaz);
				con.commit();
				
			}else{
				con.rollback();
				throw new UpdateException(Messages._RAPORIPTALEDILEMEDI_.getMesaj(), null);
			}

		} catch (CreateException e) {
			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			if (con!=null){
				try {
					con.rollback();
				} catch (SQLException e1) {
					
				}
			}
			if (e.getMessage().contains("duplicate key value violates unique constraint")){
				throw new UpdateException(Messages._SQL_5001_.getMesaj(), null);
				
			}else
			throw new UpdateException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		
		
	}
	
	@Override
	public RaporKayitDTO raporBakanliktaKayitliMi(Connection con2, int kontrolId) throws ReadException{
		RaporKayitDTO raporKayit=new RaporKayitDTO();
		
		QueryRunner runner = new QueryRunner();

		try {
		
			raporKayit = runner.query(con2,
					Sorgular._GETBAKANLIKKAYITLIRAPOR_.qry,
					new BeanHandler<RaporKayitDTO>(
							RaporKayitDTO.class),
					new Object[] { kontrolId});
			
			

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		}  
		return raporKayit==null?new RaporKayitDTO():raporKayit;
	}

	public List<RaporDTO> getDenetimKayitRaporlar() throws ReadException{
		List<RaporDTO> raporlar=new ArrayList<RaporDTO>();
		QueryRunner runner = new QueryRunner();
		

		try {
			con = DAOBase.instance().getConnection();
			
		//con=new BaglantiYoneticisi().getConnection();
		con.setAutoCommit(false);
				
				raporlar = (ArrayList<RaporDTO>) runner.query(con,
						Sorgular._GETDENETIMKAYITRAPORLAR_.qry,
						new BeanListHandler<RaporDTO>(RaporDTO.class));
				
		

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return raporlar==null?new ArrayList<RaporDTO>():raporlar;
	}
  
	@Override
	public void denetimKayitLogKaydet(DenetimKayitDTO denetimKayit) throws CreateException{
		QueryRunner runner = new QueryRunner();
		

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
		
			int i= runner.update(con,
						Sorgular._SETDENETIMKAYITLOG_.qry,
						new Object[]{denetimKayit.getRaporId(),denetimKayit.getHataKodu(),denetimKayit.getHataMesaj()});
				
			if (i>0) con.commit();
			else con.rollback();

		} catch (CreateException e) {
			throw new CreateException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new CreateException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		
	}
	
	public int getBasvuruId(int raporId) throws ReadException {
		BasvuruDTO b = new BasvuruDTO();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			
			b = runner.query(con, Sorgular._GETBASVURUIDBYRAPORID_.qry,
					new BeanHandler<BasvuruDTO>(BasvuruDTO.class),
					new Integer( raporId ));
			

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return b == null ? 0 : b.getBasvuruId();

	}	
	
	/*public boolean odemeAlindiMi(int basvuruId) throws ReadException {
		boolean kontrol=false;
		OdemeDTO odeme = new OdemeDTO();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			
			odeme = runner.query(con, Sorgular._ODEMEALINDIMI_.qry,
					new BeanHandler<OdemeDTO>(OdemeDTO.class),
					new Integer( basvuruId ));
			
			if(odeme.getToplamTutar()>0 && odeme.getOdemeTutari()>0 && odeme.getToplamTutar()==odeme.getOdemeTutari()){
				kontrol=true;
			}else if(odeme.getToplamTutar()==0 && odeme.getOdemeTutari()==0){
				kontrol=true;
			}else if(odeme.getToplamTutar()>0 && odeme.getOdemeTutari()>0 && odeme.getToplamTutar()!=odeme.getOdemeTutari()){
				kontrol=false;
			}else if(odeme.getToplamTutar()>0 && odeme.getOdemeTutari()==0){
				kontrol=false;
			}
			

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return kontrol?true:false;

	}*/
}
