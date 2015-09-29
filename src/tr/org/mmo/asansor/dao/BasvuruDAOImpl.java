package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.beans_.Basvuru;
import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.beans_.TaramaYapilmayanBinalar;
import tr.org.mmo.asansor.business.AsansorBusiness;
import tr.org.mmo.asansor.business.BasvuruBusiness;
import tr.org.mmo.asansor.business.BinaBusiness;
import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.business.OdemeBusiness;
import tr.org.mmo.asansor.business.RandevuBusiness;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.BasvuruDTO;
import tr.org.mmo.asansor.dto.BasvuruListeDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.CurrVal;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.dto.OdemeDTO;
import tr.org.mmo.asansor.dto.RaporDTO;
import tr.org.mmo.asansor.dto.TaramaDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.managedbeans.LoginBean;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;

public class BasvuruDAOImpl implements BasvuruDAO {

	private Connection con = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BasvuruAsansorDTO> basvuruKisiEkle(Basvuru basvuru,
			BasvuruAsansorDTO[] selectedAsansor) throws CreateException {
		con = DAOBase.instance().getConnection();
		QueryRunner runner = new QueryRunner();

		List<BasvuruAsansorDTO> asansorKontrolList = new ArrayList<BasvuruAsansorDTO>();
		try {
			con.setAutoCommit(false);
			BasvuruDTO basvuruKisi = new BasvuruDTO();
			basvuruKisi = basvuru.getBasvuru();
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			String kullanici;
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : String
					.valueOf(basvuruKisi.getBasvuruYapanTCKimlikNo());
			int i = runner
					.update(con,
							Sorgular._BASVURU_KISI_INSERT.qry,
							new Object[] {

									basvuruKisi.getBasvuruYapanTCKimlikNo(),
									basvuruKisi.getBinaId(),
									basvuruKisi.getBasvuruDurum(),
									basvuruKisi.getBasvuruYapanAdi(),
									basvuruKisi.getBasvuruYapanSoyadi(),

									Long.parseLong(basvuruKisi
											.getTelefonNoStr() == null ? "0"
											: (basvuruKisi.getTelefonNoStr()
													.equals("") ? "0"
													: basvuruKisi
															.getTelefonNoStr()
															.replace("(", "")
															.replace(")", "")
															.replace(" ", "")
															.trim())),

									basvuruKisi.getePosta(), kullanici,
									basvuruKisi.getTelefonNoDahili()});
			if (i > 0) {
				CurrVal cv = (CurrVal) runner.query(con,
						Sorgular._CURRVALBASVURU_.qry, new BeanHandler(
								CurrVal.class));
				asansorKontrolList = basvuruAsansorEkle(con, cv.getCurrval(),
						basvuru.getBina(), selectedAsansor);

				double bakimFiyat = 0.00;
				boolean odemeKaydet = true;
				for (BasvuruAsansorDTO a : asansorKontrolList) {
					bakimFiyat += a.getKontrolTutari();
					if (a.getKontrolTutari() <= 0.01 && a.getKontrolTuru().equals("N") ) {
						odemeKaydet = false;
						break;
					}
				}

				if (odemeKaydet && bakimFiyat>0) {
					OdemeDTO odeme = new OdemeDTO();
					odeme.setBasvuruId(cv.getCurrval());
					odeme.setToplamTutar(bakimFiyat);
					new OdemeBusiness().basvuruOdemeKayit(con, odeme);
				}

				con.commit();

			} else {
				con.rollback();
			}

		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return asansorKontrolList;
	}

	@SuppressWarnings("unused")
	@Override
	public void taramaYapilmadiEkle(TaramaDTO tarama) throws CreateException {
		con = DAOBase.instance().getConnection();
		QueryRunner runner = new QueryRunner();
		int id = 0;
		try {
			con.setAutoCommit(false);
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			int i = runner
					.update(con,
							Sorgular._TARAMA_YAPILMADI_INSERT_.qry,
							new Object[] {
									tarama.getBinaId(),
									tarama.getBinaSorumlusuTCKimlikNo(),
									tarama.getDurum(),
									tarama.getBinaSorumlusuAdi(),
									tarama.getBinaSorumlusuSoyadi(),
									Long.parseLong(tarama
											.getBinaSorumlusuTelefonNoStr() == null ? "0"
											: (tarama
													.getBinaSorumlusuTelefonNoStr()
													.equals("") ? "0"
													: tarama.getBinaSorumlusuTelefonNoStr()
															.replace("(", "")
															.replace(")", "")
															.replace(" ", "")
															.trim())),

									tarama.getBinaSorumlusuEPosta(),
									tarama.getTaramaYapilamamaNedenKod(),
									loginBean.getKullanici().getKullaniciAdi() });

			if (i > 0) {
				// CurrVal
				// cv=(CurrVal)runner.query(con,Sorgular._CURRVALTARAMA_.qry,new
				// BeanHandler(CurrVal.class));
				// id=cv.getCurrval();
				con.commit();
			}

		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}

	}

	@SuppressWarnings({ "unused" })
	@Override
	public void taramaEkle(TaramaDTO tarama) throws CreateException {
		con = DAOBase.instance().getConnection();
		QueryRunner runner = new QueryRunner();
		int id = 0;
		try {
			con.setAutoCommit(false);
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			int i = runner
					.update(con,
							Sorgular._TARAMA_INSERT_.qry,
							new Object[] {
									tarama.getId(),
									tarama.getBinaId(),
									tarama.getBinaSorumlusuTCKimlikNo(),
									tarama.getDurum(),
									tarama.getBinaSorumlusuAdi(),
									tarama.getBinaSorumlusuSoyadi(),
									Long.parseLong(tarama
											.getBinaSorumlusuTelefonNoStr() == null ? "0"
											: (tarama
													.getBinaSorumlusuTelefonNoStr()
													.equals("") ? "0"
													: tarama.getBinaSorumlusuTelefonNoStr()
															.replace("(", "")
															.replace(")", "")
															.replace(" ", "")
															.trim())),

									tarama.getBinaSorumlusuEPosta(),
									tarama.getTaramaYapilamamaNedenKod(),
									loginBean.getKullanici().getKullaniciAdi(),
									tarama.getBinaSorumlusuTelefonNoDahili()});

			if (i > 0) {
				// CurrVal
				// cv=(CurrVal)runner.query(con,Sorgular._CURRVALTARAMA_.qry,new
				// BeanHandler(CurrVal.class));
				// id=cv.getCurrval();
				con.commit();
			}

		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}
		// return id;
	}

	@Override
	public List<BasvuruAsansorDTO> basvuruGuncelle(Basvuru basvuru,
			BasvuruAsansorDTO[] selectedAsansor) throws CRUDException {

		QueryRunner runner = new QueryRunner();

		List<BasvuruAsansorDTO> list = new ArrayList<BasvuruAsansorDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			BasvuruDTO basvuruKisi = new BasvuruDTO();
			basvuruKisi = basvuru.getBasvuru();
			long telefonNo = 0l;
			try {
				telefonNo = Long.parseLong(basvuruKisi.getTelefonNoStr()
						.replace("(", "").replace(")", "").replace(" ", "")
						.trim());
			} catch (Exception e) {
				telefonNo = 0l;
			}
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			String kullanici;
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : String
					.valueOf(basvuruKisi.getBasvuruYapanTCKimlikNo());
			int i = runner.update(
					con,
					Sorgular._BASVURU_KISI_UPDATE.qry,
					new Object[] { basvuruKisi.getBasvuruYapanTCKimlikNo(),

					basvuru.getBina().getBinaId(),
							basvuruKisi.getBasvuruYapanAdi(),
							basvuruKisi.getBasvuruYapanSoyadi(), telefonNo,
							basvuru.getKisi().getePosta(), kullanici,
							basvuruKisi.getTelefonNoDahili(),
							basvuruKisi.getBasvuruId() });
			if (i > 0) {
				new RandevuBusiness().randevuSil(con, basvuru.getBasvuru()
						.getBasvuruId());
				basvuruAsansorSil(con, basvuru.getBasvuru().getBasvuruId());

				new OdemeBusiness().basvuruOdemeSil(con, basvuru.getBasvuru()
						.getBasvuruId());

				list = basvuruAsansorEkle(con, basvuru.getBasvuru()
						.getBasvuruId(), basvuru.getBina(), selectedAsansor);

				double bakimFiyat = 0.00;
				boolean odemeKaydet = true;
				for (BasvuruAsansorDTO a : list) {
					bakimFiyat += a.getKontrolTutari();
					if (a.getKontrolTutari() <= 0.01 && a.getKontrolTuru().equals("N")) {
						odemeKaydet = false;
						break;
					}
				}
				if (odemeKaydet && bakimFiyat>0) {
					OdemeDTO odeme = new OdemeDTO();
					odeme.setBasvuruId(basvuruKisi.getBasvuruId());
					odeme.setToplamTutar(bakimFiyat);
					new OdemeBusiness().basvuruOdemeKayit(con, odeme);
				}

				con.commit();
			} else {
				con.rollback();
			}

		} catch (CreateException e) {
			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), e);
		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), e);
		} catch (DeleteException e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<BasvuruListeDTO> getOdemeYapilanBasvurular()
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<BasvuruListeDTO> list = new ArrayList<BasvuruListeDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();
			sb.append(Sorgular._GETODEMEYAPILANBASVURULAR_.qry);
			sb.append(" where (");
			sb.append(((LoginBean) Common.findBean("loginBean"))
					.getKullaniciBinaKapsam().replace("a.ilce", "jt.ilcekodu").replace("a.il", "jt.ilkodu"));
			sb.append(" )");

list = (ArrayList<BasvuruListeDTO>) runner.query(con,
					sb.toString(), new BeanListHandler(BasvuruListeDTO.class));
			for (BasvuruListeDTO bld : list) {

				HashMap<Integer, FirmaDTO> firmaMap = new HashMap<Integer, FirmaDTO>();
				firmaMap = new FirmaBusiness().getBasvuruFirmasi(con,
						bld.getBasvuruId());
				Set<Integer> set = firmaMap.keySet();
				Iterator<Integer> iter = set.iterator();
				while (iter.hasNext()) {
					int i = iter.next();
					bld.getBakimciFirma().add(firmaMap.get(i));
				}

			}

			/*
			 * if (list!=null){ for (BasvuruListeDTO basvuru:list){
			 * basvuru.setBasvuruOdeme(new
			 * OdemeBusiness().getBasvuruOdeme(con,basvuru.getBasvuruId())); } }
			 */

		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<BasvuruListeDTO>() : list;
	}

	@Override
	public void basvuruDurumUpdate(Connection con, int basvuruId, String durum)
			throws UpdateException {
		QueryRunner runner = new QueryRunner();
		try {
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			int i = runner.update(con, Sorgular._BASVURU_DURUM_UPDATE_.qry,
					new Object[] { durum,
							loginBean.getKullanici().getKullaniciAdi(),
							basvuruId });
			if (i <= 0) {
				throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
			}
		} catch (Exception e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		}

	}
	
	@Override
	public void basvuruBakimciFirmaUpdate( int randevuId, int firmaId,int cihazId)
			throws UpdateException {
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			

			int i = runner.update(con, Sorgular._BASVURUBAKIMCIFIRMAUPDATE_.qry,
					new Object[] { firmaId,randevuId,cihazId });
			if (i <= 0) {
				con.rollback();
				throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
			}else{
				con.commit();
			}
		} catch (Exception e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		}finally {

			DbUtils.closeQuietly(con);
		}

	}


	@Override
	public void taramaDurumUpdate(Connection con, int basvuruId, String durum)
			throws UpdateException {
		QueryRunner runner = new QueryRunner();
		try {
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			int i = runner.update(con, Sorgular._TARAMA_DURUM_UPDATE_.qry,
					new Object[] { durum,
							loginBean.getKullanici().getKullaniciAdi(),
							basvuruId });
			if (i <= 0) {
				throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
			}
		} catch (Exception e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		}

	}

	@Override
	public List<BasvuruAsansorDTO> basvuruAsansorEkle(int basvuruId,
			BinaDTO bina, BasvuruAsansorDTO[] asansorList)
			throws CreateException {
		List<BasvuruAsansorDTO> asansorKontrolTutarlari = new ArrayList<BasvuruAsansorDTO>();

		PreparedStatement pstmt = null;
		try {
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : String
					.valueOf("Başvurudan");
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(Sorgular._BASVURU_ASANSOR_INSERT_.qry);
			pstmt.setInt(1, basvuruId);
			double fiyat = 0.00;
			boolean binadanOdemeAlinacak = true;
			binadanOdemeAlinacak = new BinaBusiness().isOdemeAlinacakMi(con,
					bina.getBinaId());
			for (BasvuruAsansorDTO a : asansorList) {
				pstmt.setInt(2, a.getCihazId());

				fiyat = binadanOdemeAlinacak ? new AsansorBusiness()
						.getAsansorBakimFiyat(con, a.getCihazTip(),
								bina.getBelediye(), a.getKapasiteDurak(),
								a.getKapasiteAgirlik(),
								bina.getSozlesmeBinaTipId(),a.getKontrolTuru()) : 0.00;

				pstmt.setDouble(3, fiyat);
				pstmt.setInt(4, a.getCihazId());
				pstmt.setString(5, kullanici);
				pstmt.addBatch();
				a.setKontrolTutari(fiyat);
				asansorKontrolTutarlari.add(a);
				fiyat = 0.00;
			}
			pstmt.executeBatch();

			con.commit();

		} catch (ReadException e) {
			throw new CreateException(e.getMessage(), null);
		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(con);
		}
		return asansorKontrolTutarlari;

	}

	private void basvuruAsansorSil(Connection con, int basvuruId)
			throws DeleteException {
		QueryRunner runner = new QueryRunner();
		try {
			runner.update(con, Sorgular._BASVURU_ASANSOR_DELETE_.qry,
					new Integer(basvuruId));
		} catch (SQLException e) {
			throw new DeleteException(Messages._SQL_500_.getMesaj(), null);
		}
	}

	private List<BasvuruAsansorDTO> basvuruAsansorEkle(Connection con,
			int basvuruId, BinaDTO bina, BasvuruAsansorDTO[] asansorList)
			throws CreateException {
		List<BasvuruAsansorDTO> list = new ArrayList<BasvuruAsansorDTO>();

		PreparedStatement pstmt = null;
		try {
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : String
					.valueOf("Başvurudan");
			pstmt = con.prepareStatement(Sorgular._BASVURU_ASANSOR_INSERT_.qry);
			pstmt.setInt(1, basvuruId);
			double fiyat = 0.00;
 			boolean binadanOdemeAlinacak = true;
 			binadanOdemeAlinacak = new BinaBusiness().isOdemeAlinacakMi(con,
 					bina.getBinaId());
			for (BasvuruAsansorDTO a : asansorList) {
				pstmt.setInt(2, a.getCihazId());
//				fiyat = binadanOdemeAlinacak ? new AsansorBusiness()
//						.getAsansorBakimFiyat(con, a.getCihazTip(),
//								bina.getBelediye(), a.getKapasiteDurak(),
//								a.getKapasiteAgirlik(),
//								bina.getSozlesmeBinaTipId()) : 0.00;
				pstmt.setString(3, a.getKontrolTuru());
				fiyat=a.getKontrolTutari();
 				pstmt.setDouble(4, fiyat);
				pstmt.setInt(5, a.getBakimciFirmaId());
				pstmt.setString(6, kullanici);
				pstmt.addBatch();
				a.setKontrolTutari(!binadanOdemeAlinacak ? 0.01 : fiyat);
				a.setBasvuruId(basvuruId);

				list.add(a);
				fiyat = 0.00;
			}
			pstmt.executeBatch();

		} catch (ReadException e) {
			throw new CreateException(e.getMessage(), null);
		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(pstmt);

		}
		return list;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BasvuruAsansorDTO> getBasvuruAsansor(int basvuruId)
			throws ReadException {
		List<BasvuruAsansorDTO> list = new ArrayList<BasvuruAsansorDTO>();
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (ArrayList<BasvuruAsansorDTO>) runner.query(con,
					Sorgular._GETBASVURUASANSOR_.qry, new BeanListHandler(
							BasvuruAsansorDTO.class), new Integer(basvuruId));

		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<BasvuruAsansorDTO>() : list;

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BasvuruAsansorDTO> getBasvuruAsansor(RaporDTO rapor)
			throws ReadException {
		List<BasvuruAsansorDTO> list = new ArrayList<BasvuruAsansorDTO>();
		QueryRunner runner = new QueryRunner();

		try {
			
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (ArrayList<BasvuruAsansorDTO>) runner.query(con,
					Sorgular._GETBASVURUASANSORIPTAL_.qry, new BeanListHandler(
							BasvuruAsansorDTO.class), new Integer(rapor.getBasvuruId()));

		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<BasvuruAsansorDTO>() : list;

	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BasvuruAsansorDTO> getBasvuruAsansorTip(int basvuruId)
			throws ReadException {
		List<BasvuruAsansorDTO> list = new ArrayList<BasvuruAsansorDTO>();
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (ArrayList<BasvuruAsansorDTO>) runner.query(con,
					Sorgular._GETBASVURUASANSORTIP_.qry, new BeanListHandler(
							BasvuruAsansorDTO.class), new Integer(basvuruId));

		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<BasvuruAsansorDTO>() : list;

	}
	private List<BasvuruAsansorDTO> getBasvuruAsansor(Connection con2,
			int basvuruId) throws ReadException {
		List<BasvuruAsansorDTO> list = new ArrayList<BasvuruAsansorDTO>();
		QueryRunner runner = new QueryRunner();

		try {

			list = (ArrayList<BasvuruAsansorDTO>) runner.query(con2,
					Sorgular._GETBASVURUASANSOR_.qry,
					new BeanListHandler<BasvuruAsansorDTO>(
							BasvuruAsansorDTO.class), new Integer(basvuruId));

		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		}

		return list == null ? new ArrayList<BasvuruAsansorDTO>() : list;

	}

	@Override
	public List<BasvuruListeDTO> getBasvurular(Date ilkTarih,Date sonTarih) throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<BasvuruListeDTO> list = new ArrayList<BasvuruListeDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();
			sb.append(Sorgular._GETBASVURULAR_.qry);
			sb.append(" and (");
			sb.append(((LoginBean) Common.findBean("loginBean"))
					.getKullaniciBinaKapsam());
			sb.append(" ) ");
			list = (ArrayList<BasvuruListeDTO>) runner.query(con,
					sb.toString(), new BeanListHandler<BasvuruListeDTO>(
							BasvuruListeDTO.class),new Object[]{new java.sql.Date(ilkTarih.getTime()),new java.sql.Date(sonTarih.getTime())});

			for (BasvuruListeDTO b : list) {
				b.setAsansorKontrolFiyatlari(getBasvuruAsansor(con,
						b.getBasvuruId()));
				b.setBakimciFirma(new FirmaBusiness()
						.setAsansorBakimciFirmalar(con, b.getBasvuruId()));
			}

		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<BasvuruListeDTO>() : list;
	}

	@Override
	public List<TaramaYapilmayanBinalar> getTaramaYapilamayanBinalar()
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<TaramaYapilmayanBinalar> list = new ArrayList<TaramaYapilmayanBinalar>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();
			sb.append(Sorgular._GETTARAMAYAPILAMAYANBINALAR_.qry);
			sb.append(" and (");
			sb.append(((LoginBean) Common.findBean("loginBean"))
					.getKullaniciBinaKapsam());
			sb.append(" )");
			list = (ArrayList<TaramaYapilmayanBinalar>) runner.query(con, sb
					.toString(), new BeanListHandler<TaramaYapilmayanBinalar>(
					TaramaYapilmayanBinalar.class));

		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<TaramaYapilmayanBinalar>() : list;
	}

	@Override
	public void basvuruAsansorKontrolGuncelle(Connection con2, int cihazId,
			int randevuId, Date kontrolTarihi) throws UpdateException {
		QueryRunner runner = new QueryRunner();
		String kullanici;
		LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
		kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

		try {
			runner.update(con2,
					Sorgular._BASVURU_ASANSOR_KONTROL_GUNCELLE_.qry,
					new Object[] { new java.sql.Date(kontrolTarihi.getTime()),

					kullanici, cihazId, randevuId });
		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		}

	}

	@Override
	public List<BasvuruListeDTO> getBasvurularByBinaId(int binaId)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<BasvuruListeDTO> list = new ArrayList<BasvuruListeDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (ArrayList<BasvuruListeDTO>) runner
					.query(con, Sorgular._GETBASVURULARBYBINAID_.qry,
							new BeanListHandler<BasvuruListeDTO>(
									BasvuruListeDTO.class),
							new Object[] { binaId });

		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<BasvuruListeDTO>() : list;
	}

	@Override
	public void taramaGuncelle(TaramaDTO tarama) throws UpdateException {
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			long telefonNo = 0l;
			try {
				telefonNo = Long.parseLong(tarama
						.getBinaSorumlusuTelefonNoStr().replace("(", "")
						.replace(")", "").replace(" ", "").trim());
			} catch (Exception e) {
				telefonNo = 0l;
			}
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			int i = runner.update(
					con,
					Sorgular._TARAMAUPDATE_.qry,
					new Object[] {
							new java.sql.Date(tarama.getTarih().getTime()),
							tarama.getBinaId(),
							tarama.getBinaSorumlusuTCKimlikNo(),
							tarama.getDurum(), tarama.getBinaSorumlusuAdi(),
							tarama.getBinaSorumlusuSoyadi(), telefonNo,
							tarama.getBinaSorumlusuEPosta(),
							tarama.getTaramaYapilamamaNedenKod(),
							loginBean.getKullanici().getKullaniciAdi(),
							tarama.getBinaSorumlusuTelefonNoDahili(),
							tarama.getId() });
			if (i > 0) {

				con.commit();
			} else {
				con.rollback();
			}

		} catch (CreateException e) {
			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), e);
		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}

	}

	@Override
	public List<Istatistik> getBasvurularByBinaIdAndTarih(int binaId, Date tarih) throws ReadException{
		QueryRunner runner = new QueryRunner();
		List<Istatistik> list = new ArrayList<Istatistik>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			
				list = (List<Istatistik>) runner.query(con, Sorgular._GETBASVURULARCIHAZLAR_.qry,
						new BeanListHandler<Istatistik>(Istatistik.class),
						new Object[] {binaId,new java.sql.Date(tarih.getTime()) });
			
				
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
	
	
	public void basvuruSil(int basvuruId,int binaId) throws DeleteException{
		QueryRunner runner = new QueryRunner();
		
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			basvuruAsansorSil(con, basvuruId);
			new OdemeBusiness().basvuruOdemeSil(con, basvuruId);
			taramaSil(con, basvuruId, binaId);
			basvuruSil(con, basvuruId);
			con.commit();
				
		} catch (CreateException e) {
			
			throw new DeleteException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			
			throw new DeleteException(Messages._SQL001_.getMesaj(), null);
		} 

		finally {
			DbUtils.closeQuietly(con);
		}
		
	}
	
	
	private void basvuruSil(Connection conn,int basvuruId) throws DeleteException{
		QueryRunner runner = new QueryRunner();
		
		try {
		
			runner.update(conn, Sorgular._DELETEBASVURU_.qry, new Object[] {basvuruId});
		}
		 catch (SQLException e) {
			throw new DeleteException(Messages._SQL001_.getMesaj(), null);
		} 

		
		
	}
	
	
	private void taramaSil(Connection conn,int basvuruId,int binaId) throws DeleteException{
		QueryRunner runner = new QueryRunner();
		
		try {
			
			runner.update(conn, Sorgular._DELETETARAMA_.qry, new Object[] {basvuruId,binaId});
				
		}  catch (SQLException e) {
			throw new DeleteException(Messages._SQL001_.getMesaj(), null);
		} 

		
		
	}
	@Override
	public BasvuruAsansorDTO basvuruVarmi(int cihazId) throws ReadException {
		QueryRunner runner = new QueryRunner();
		BasvuruAsansorDTO basvuruAsansor=new BasvuruAsansorDTO();
		
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			basvuruAsansor = (BasvuruAsansorDTO) runner.query(con, Sorgular._BASVURUASANSORBYCIHAZID_.qry,
					new BeanHandler<BasvuruAsansorDTO>(BasvuruAsansorDTO.class),
					new Object[] {cihazId });
				
		} catch (CreateException e) {
			
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} 

		finally {
			DbUtils.closeQuietly(con);
		}
		return basvuruAsansor==null?new BasvuruAsansorDTO():basvuruAsansor;
	}
	
	@Override
	public BasvuruAsansorDTO getBasvuruAsansorFirma(Connection con2,
			int basvuruId, int cihazId) throws ReadException{
		BasvuruAsansorDTO basvuruAsansor = new BasvuruAsansorDTO();
		QueryRunner runner = new QueryRunner();

		try {

			basvuruAsansor = (BasvuruAsansorDTO) runner.query(con2,
					Sorgular._GETBASVURUASANSORFIRMA_.qry,
					new BeanHandler<BasvuruAsansorDTO>(
							BasvuruAsansorDTO.class), new Object[]{basvuruId,cihazId});

		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		}

		return basvuruAsansor;

	}

}
