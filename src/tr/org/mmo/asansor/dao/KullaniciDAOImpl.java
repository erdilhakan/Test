package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.Duyuru;
import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.business.BirimBusiness;
import tr.org.mmo.asansor.business.MuhendisBusiness;
import tr.org.mmo.asansor.business.RolBusiness;
import tr.org.mmo.asansor.dto.BirimDTO;
import tr.org.mmo.asansor.dto.CurrVal;
import tr.org.mmo.asansor.dto.KullaniciRolYetkiDTO;
import tr.org.mmo.asansor.dto.KullaniciTurDTO;
import tr.org.mmo.asansor.exception.SystemUnavailableException;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.managedbeans.ApplicationBean;
import tr.org.mmo.asansor.managedbeans.LoginBean;
import tr.org.mmo.asansor.util.IlIlceComparator;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.PassEncript;
import tr.org.mmo.asansor.util.Sorgular;

public class KullaniciDAOImpl implements KullaniciDAO {

	Connection con = null;

	@Override
	public void kullaniciEkle(Kullanici kullanici) throws CreateException {

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
			int sicilNo = 0;
			try {
				sicilNo = Integer.parseInt(kullanici.getSicilNo());
			} catch (Exception e) {
				sicilNo = 0;
			}
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			if (kullanici.getKullaniciTuru() == 1 && sicilNo > 0) {
				int[] i = new int[] { 66, 68 };
				new MuhendisBusiness().muhendisCihazYetkiKaydet(con, sicilNo,
						i, loginBean.getKullanici().getKullaniciAdi());
			}
			
			int i = runner.update(
					con,
					Sorgular._KULLLANICI_INSERT_.qry,
					new Object[] {
							kullanici.getKullaniciAdi(),
							PassEncript.getInstance().encrypt(
									kullanici.getKullaniciAdi().trim()),
							kullanici.getAdi(), kullanici.getSoyadi(), sicilNo,
							kullanici.getePosta(), kullanici.getDurum(),
							kullanici.getKullaniciTuru(),
							kullanici.getTelefonNo(),
							kullanici.getGsmTelefonNo(),
							kullanici.getOnayYetkisi(),
							kullanici.getAkreditasyonDeger(),
							kullanici.getIl(),
							loginBean.getKullanici().getKullaniciAdi(),
							kullanici.getBelediyeIp()==null?"":kullanici.getBelediyeIp().trim()});

			if (i > 0) {
				con.commit();
			}
		} catch (SystemUnavailableException e) {
			throw new CreateException(e.getMessage(), null);
		} catch (SQLException e) {
			if (e.getSQLState().equals("23505")) {
				throw new CreateException(Messages._SQL_501_.getMesaj(), null);
			} else {
				throw new CreateException(Messages._SQL_500_.getMesaj(), null);
			}
		} finally {
			DbUtils.closeQuietly(con);

		}

	}

	@Override
	public Kullanici girisKontrol(Kullanici kullanici) throws ReadException,
			SystemUnavailableException {
		Kullanici k = null;
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			k = (Kullanici) runner.query(con, Sorgular._KULLANICI_.qry,
					new BeanHandler<Kullanici>(Kullanici.class),
					new Object[] { kullanici.getKullaniciAdi() });
			if (k != null) {
				if (k.getDurum() == null || !k.getDurum().equals("A")) {
					throw new ReadException(
							Messages._KULLANICIAKTIFDEGIL_.getMesaj(), null);
				} else {
					if (k.getParola().equals(
							PassEncript.getInstance().encrypt(
									kullanici.getParola()))) {

						k.setRoller(new RolBusiness().kullaniciRolListesi(con,
								k));

						k.setBirimler(new BirimBusiness().getKullaniciBirimler(
								con, k));

						TreeMap<String, Integer> iller = new TreeMap<String, Integer>(
								new IlIlceComparator());
						Set<String> key = ((ApplicationBean) Common
								.findBean("applicationBean")).getIller()
								.keySet();
						Iterator<String> i = key.iterator();

						while (i.hasNext()) {
							String s = i.next();
							for (BirimDTO b : k.getBirimler()) {

								if (((ApplicationBean) Common
										.findBean("applicationBean"))
										.getIller().get(s) == b.getIl()) {
									iller.put(s, b.getIl());
									break;
								}
							}
						}
						k.setIller(iller);

						k.setAkreditasyon(k.getAkreditasyonDeger().equals("E") ? true
								: false);
						k.setOnaylayan(k.getOnayYetkisi().equals("E") ? true
								: false);

					} else {
						throw new ReadException(
								Messages._KULLANICIGIRIS_.getMesaj(), null);
					}
				}
			} else {
				throw new ReadException(Messages._KULLANICIGIRIS_.getMesaj(),
						null);
			}

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {

			DbUtils.closeQuietly(con);
		}

		return k;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Kullanici> kullaniciListesi() throws ReadException {

		QueryRunner runner = new QueryRunner();
		List<Kullanici> list = new ArrayList<Kullanici>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			list = (List<Kullanici>) runner.query(con,
					Sorgular._KULLANICILIST_.qry, new BeanListHandler(
							Kullanici.class));

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list == null ? new ArrayList<Kullanici>() : list;

	}

	@Override
	public void yetkiInsert(Kullanici kullanici) throws CreateException {
		PreparedStatement pstmt = null;

		try {

			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			rolDelete(con, kullanici);
			pstmt = con.prepareStatement(Sorgular._ROLYETKIINSERT_.qry);

			pstmt.setInt(1, kullanici.getKullaniciId());

			for (KullaniciRolYetkiDTO k : kullanici.getRoller()) {
				pstmt.setInt(2, k.getRolId());
				pstmt.setInt(3, k.getYetkiId());
				pstmt.setBoolean(4, k.isGuncelle());
				pstmt.setBoolean(5, k.isSil());
				pstmt.setString(6, loginBean.getKullanici().getKullaniciAdi());
				pstmt.addBatch();
			}

			int[] rolInsert = pstmt.executeBatch();
			if (rolInsert.length > 0) {

				con.commit();

			} else {
				con.rollback();
				throw new CreateException(Messages._ROLBELIRLE_.getMesaj(),
						null);
			}

		} catch (DeleteException e) {
			throw new CreateException(e.getMessage(), null);
		} catch (CreateException e) {
			throw new CreateException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(con);

		}

	}

	private void rolDelete(Connection con, Kullanici kullanici)
			throws DeleteException {
		QueryRunner runner = new QueryRunner();
		try {
			runner.update(con, Sorgular._ROLYETKIDELETE_.qry, new Integer(
					kullanici.getKullaniciId()));
		} catch (SQLException e) {
			throw new DeleteException(Messages._SQL_500_.getMesaj(), null);
		}
	}

	private void birimDelete(Connection con, Kullanici kullanici)
			throws DeleteException {
		QueryRunner runner = new QueryRunner();
		try {
			runner.update(con, Sorgular._BIRIMDELETE_.qry, new Integer(
					kullanici.getKullaniciId()));
		} catch (SQLException e) {
			throw new DeleteException(Messages._SQL_500_.getMesaj(), null);
		}
	}

	@Override
	public void birimInsert(Kullanici kullanici) throws CreateException,
			DeleteException {
		PreparedStatement pstmt = null;
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			birimDelete(con, kullanici);
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			pstmt = con.prepareStatement(Sorgular._KULLANICIBIRIMINSERT_.qry);
			pstmt.setInt(2, kullanici.getKullaniciId());

			for (BirimDTO b : kullanici.getBirimler()) {
				pstmt.setInt(1, b.getBirimKodu());
				pstmt.setString(3, b.getBirimTipi());
				pstmt.setString(4, loginBean.getKullanici().getKullaniciAdi());
				pstmt.addBatch();

			}
			pstmt.executeBatch();
			con.commit();

		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(con);
		}
	}

	@Override
	public void parolaDegistir(String parola) throws UpdateException {
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			try {
				LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

				String kullaniciAdi = loginBean.getUser();

				int i = runner.update(con,
						Sorgular._KULLANICIPAROLAUPDATE_.qry, new Object[] {
								PassEncript.getInstance().encrypt(parola),
								loginBean.getKullanici().getKullaniciAdi(),
								kullaniciAdi });
				if (i > 0) {
					con.commit();
				} else {
					con.rollback();
				}
			} catch (SystemUnavailableException e) {
				throw new UpdateException(e.getMessage(), null);
			}

		} catch (CreateException e) {
			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public void yeniParola(String kullaniciAdi, String parola)
			throws UpdateException {
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			try {

				int i = runner.update(con,
						Sorgular._KULLANICIPAROLAUPDATE_.qry, new Object[] {
								PassEncript.getInstance().encrypt(parola),
								"POSTGRES", kullaniciAdi });
				if (i > 0) {
					con.commit();
				} else {
					con.rollback();
				}
			} catch (SystemUnavailableException e) {
				throw new UpdateException(e.getMessage(), null);
			}

		} catch (CreateException e) {
			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public Kullanici kullaniciGetirByKullaniciAdi(String kullaniciAdi)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		Kullanici k = new Kullanici();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			k = (Kullanici) runner.query(con,
					Sorgular._KULLANICIBYKULLANICIADI_.qry,
					new BeanHandler<Kullanici>(Kullanici.class),
					new Object[] { kullaniciAdi });

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj() + " "
					+ e.getMessage(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return k == null ? new Kullanici() : k;
	}

	@Override
	public void kullaniciDurumGuncelle(Kullanici kullanici)
			throws UpdateException {
		QueryRunner runner = new QueryRunner();

		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			int sicilNo = 0;
			try {
				sicilNo = Integer.parseInt(kullanici.getSicilNo());
			} catch (Exception e) {
				sicilNo = 0;
			}
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			
			int i = runner.update(
					con,
					Sorgular._KULLANICIDURUMGUNCELLE_.qry,
					new Object[] {
							kullanici.getDurum(),
							kullanici.getePosta(),
							kullanici.getSoyadi()==null?"":kullanici.getSoyadi().replace('i', 'İ')
									.toUpperCase(),
							kullanici.getAdi()==null?"":kullanici.getAdi().replace('i', 'İ').toUpperCase(),
							kullanici.getKullaniciAdi(),
							kullanici.getKullaniciTuru(),
							kullanici.getTelefonNo(),
							kullanici.getGsmTelefonNo(),
							kullanici.getOnayYetkisi(),
							kullanici.getAkreditasyonDeger(),
							kullanici.getIl(), sicilNo,
							
							loginBean.getKullanici().getKullaniciAdi(),
							kullanici.getBelediyeIp()==null?"":kullanici.getBelediyeIp().trim(),
							kullanici.getKullaniciId() });

			if (i > 0) {
				con.commit();
			} else {
				con.rollback();
			}

		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj() + " "
					+ e.getMessage(), null);
		} catch (CreateException e) {
			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}

	}

	@Override
	public List<KullaniciTurDTO> getKullaniciTurleri() throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<KullaniciTurDTO> kList = new ArrayList<KullaniciTurDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			kList = (ArrayList<KullaniciTurDTO>) runner
					.query(con, Sorgular._KULLANICITURLERI_.qry,
							new BeanListHandler<KullaniciTurDTO>(
									KullaniciTurDTO.class));

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj() + " "
					+ e.getMessage(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return kList == null ? new ArrayList<KullaniciTurDTO>() : kList;

	}

	@Override
	public List<KullaniciRolYetkiDTO> getKullaniciRoller(int kullaniciId)
			throws ReadException {
		List<KullaniciRolYetkiDTO> list = new ArrayList<KullaniciRolYetkiDTO>();
		QueryRunner runner = new QueryRunner();

		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			list = (ArrayList<KullaniciRolYetkiDTO>) runner.query(con,
					Sorgular._KULLROLVEYETKILER_.qry,
					new BeanListHandler<KullaniciRolYetkiDTO>(
							KullaniciRolYetkiDTO.class),
					new Object[] { kullaniciId });

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj() + " "
					+ e.getMessage(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

		return list == null ? new ArrayList<KullaniciRolYetkiDTO>() : list;

	}

	@Override
	public void loginLogInsert() throws CreateException, UpdateException {
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			String ipAddress = request.getRemoteAddr();
			String kullaniciAdi = ((LoginBean) Common.findBean("loginBean"))
					.getKullanici().getKullaniciAdi();
			loginLogUpdate(con, kullaniciAdi, ipAddress);
			int i = 0;
			i = runner.update(con, Sorgular._LOGINLOGKAYDET_.qry, new Object[] {
					kullaniciAdi, ipAddress,
					new Timestamp(new Date().getTime()) });
			if (i > 0)
				con.commit();
			else
				con.rollback();
		} catch (CreateException e) {
			throw new CreateException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new CreateException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	private void loginLogUpdate(Connection con2, String kullaniciAdi,
			String ipAddress) throws UpdateException {
		QueryRunner runner = new QueryRunner();
		try {

			int i = 0;
			i = runner.update(con2, Sorgular._LOGINLOGCIKISTARIHIUDATE_.qry,
					new Object[] { new Timestamp(new Date().getTime()),
							kullaniciAdi, ipAddress });

		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL001_.getMesaj(), null);
		}
	}

	@Override
	public void loginLogUpdate() throws UpdateException {
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			String ipAddress = request.getRemoteAddr();
			int i = 0;
			i = runner.update(con, Sorgular._LOGINLOGCIKISTARIHIUDATE_.qry,
					new Object[] {
							new Timestamp(new Date().getTime()),
							((LoginBean) Common.findBean("loginBean"))
									.getKullanici().getKullaniciAdi(),
							ipAddress });
			if (i > 0)
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
	public List<Duyuru> getDuyurular() throws ReadException{
		QueryRunner runner = new QueryRunner();
		List<Duyuru> list=new ArrayList<Duyuru>();
		try {
			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			list=runner.query(con, Sorgular._GETDUYURULAR_.qry, new BeanListHandler<Duyuru>(Duyuru.class));
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list==null?new ArrayList<Duyuru>():list;
	}
	
	@Override
	public int duyuruKaydet(Duyuru duyuru) throws CRUDException {
		QueryRunner runner = new QueryRunner();
		int id=0;
		try {
			con = DAOBase.instance().getConnection();
		
			con.setAutoCommit(false);
			int i=0;
			if (duyuru.getId()>0)
			i=runner.update(con,Sorgular._DUYURUUPDATE_.qry, new Object[]{duyuru.getKonu().trim(),duyuru.getDetay(),duyuru.getId()});
			else{
				i=runner.update(con,Sorgular._DUYURUINSERT_.qry, new Object[]{duyuru.getKonu().trim(),duyuru.getDetay()});	
				CurrVal cv = (CurrVal) runner.query(con,
						Sorgular._CURRVALDUYUID_.qry, new BeanHandler<CurrVal>(
								CurrVal.class));
				id = cv.getCurrval();
			}
			if (i>0){
				con.commit();
			}else{
				con.rollback();
			}
		} catch (CreateException e) {
			throw new CRUDException(Messages._CONNECTIONAC_.getMesaj(),null);
		}
		 catch (SQLException e) {
			 throw new CRUDException(Messages._SQL001_.getMesaj(),null);
			}
		finally{
			DbUtils.closeQuietly(con);
		}
		return id;
		
		
	}

	/*
	 * public void setSorguBirimler(List<BirimDTO> kullaniciYetkiliBirimler)
	 * throws ReadException{ QueryRunner runner=new QueryRunner();
	 * 
	 * try {
	 * 
	 * con = DAOBase.instance().getConnection(); con.setAutoCommit(false); int
	 * i=runner.update(con,Sorgular._KULLANICIBIRIMINSERT_.qry,new
	 * Object[]{k.getKullaniciId(),temsilcilikKodu,'A'}); if (i > 0){
	 * con.commit(); } } catch (SQLException e) { throw new
	 * UpdateException(Messages._SQL_500_.getMesaj(), null); } catch
	 * (CreateException e) { throw new
	 * UpdateException(Messages._CONNECTIONAC_.getMesaj(), null); } finally{
	 * DbUtils.closeQuietly(con);
	 * 
	 * }
	 * 
	 * }
	 */
}
