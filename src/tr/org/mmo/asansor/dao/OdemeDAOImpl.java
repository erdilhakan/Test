package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.BasvuruListeDTO;
import tr.org.mmo.asansor.dto.OdemeDTO;
import tr.org.mmo.asansor.dto.OdemeSekliDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.managedbeans.LoginBean;
import tr.org.mmo.asansor.util.Ayarlar;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;

public class OdemeDAOImpl implements OdemeDAO {
	private Connection con = null;

	@Override
	public void basvuruOdemeKayit(Connection con2, OdemeDTO odeme)
			throws CreateException {
		QueryRunner qr = new QueryRunner();
		try {
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser()
					: "BAŞVURUDAN";

			qr.update(con2, Sorgular._SETBASVURUODEME_.qry,
					new Object[] { odeme.getBasvuruId(),
							odeme.getToplamTutar(), odeme.getOdemeTutari(),
							odeme.getOdemeTarihi(), kullanici });
		} catch (Exception e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		}
	}

	@Override
	public void basvuruOdemeSil(Connection con2, int basvuruId)
			throws DeleteException {
		QueryRunner qr = new QueryRunner();
		try {
			qr.update(con2, Sorgular._DELETEBASVURUODEME_.qry,
					new Object[] { basvuruId });
		} catch (Exception e) {
			throw new DeleteException(Messages._SQL_500_.getMesaj(), null);
		}
	}

	@Override
	public OdemeDTO basvuruOdeme(Connection con2, int basvuruId)
			throws ReadException {
		QueryRunner qr = new QueryRunner();
		OdemeDTO odeme = new OdemeDTO();
		try {
			odeme = (OdemeDTO) qr.query(con2, Sorgular._GETBASVURUODEME_.qry,
					new BeanHandler<OdemeDTO>(OdemeDTO.class),
					new Object[] { basvuruId });
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}
		return odeme == null ? new OdemeDTO() : odeme;
	}

	public void odemeKaydet(BasvuruListeDTO selectedBasvuru, OdemeDTO odeme)
			throws CRUDException {
		QueryRunner qr = new QueryRunner();
		try {
			try {
				con = DAOBase.instance().getConnection();
			} catch (CreateException e) {
				throw new CRUDException(Messages._CONNECTIONAC_.getMesaj(),
						null);
			}
			con.setAutoCommit(false);
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : String
					.valueOf("BAŞVURUDAN");

			int i = qr.update(
					con,
					Sorgular._BASVURUODEMEUPDATE_.qry,
					new Object[] { odeme.getOdemeTutari(),
							new Date(odeme.getOdemeTarihi().getTime()),
							odeme.getAciklama(), odeme.getOdemeSekli(),
							odeme.getOdemeBelgeNo(), kullanici,
							selectedBasvuru.getBasvuruId() });
			if (i > 0) {
				con.commit();
			} else {

				con.rollback();
				throw new UpdateException(Messages._SQL_512_.getMesaj(), null);

			}
		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public void odemeKaydet(OdemeDTO odeme) throws UpdateException {
		QueryRunner qr = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			int i = qr
					.update(con,
							Sorgular._ODEMEUPDATE_.qry,
							new Object[] { odeme.getOdemeTutari(),
									new Date(odeme.getOdemeTarihi().getTime()),
									odeme.getAciklama(), odeme.getOdemeSekli(),
									odeme.getOdemeBelgeNo(), kullanici,
									odeme.getId() });
			if (i > 0) {
				con.commit();
			} else {
				con.rollback();
			}
		} catch (Exception e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public void odemeSekliGuncelle(BasvuruListeDTO selectedBasvuru)
			throws UpdateException {
		QueryRunner qr = new QueryRunner();
		try {
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			int i = qr.update(con, Sorgular._BASVURUODEMESEKLIUPDATE_.qry,
					new Object[] { kullanici, selectedBasvuru.getBasvuruId() });
			if (i > 0) {
				con.commit();
			} else {
				con.rollback();
			}
		} catch (Exception e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public OdemeDTO getOdemeByBasvuruId(int basvuruId) throws ReadException {
		QueryRunner qr = new QueryRunner();
		OdemeDTO odeme = new OdemeDTO();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			odeme = (OdemeDTO) qr.query(con,
					Sorgular._GETODEMEBYBASVURUID_.qry,
					new BeanHandler<OdemeDTO>(OdemeDTO.class),
					new Object[] { basvuruId });
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return odeme == null ? new OdemeDTO() : odeme;
	}

	@Override
	public List<OdemeDTO> getOdemeListesiByBinaId(int binaId)
			throws ReadException {
		QueryRunner qr = new QueryRunner();
		List<OdemeDTO> odemeList = new ArrayList<OdemeDTO>();
		StringBuffer sb=new StringBuffer();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			sb.append(Sorgular._GETODEMEBYBINAID_.qry);
			sb.append(" group by b.basvuru_id,o.id,k.kontrol_bitis_tarihi ");
			odemeList = (ArrayList<OdemeDTO>) qr.query(con,
					sb.toString(),
					new BeanListHandler<OdemeDTO>(OdemeDTO.class),
					new Object[] { binaId });
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return odemeList == null ? new ArrayList<OdemeDTO>() : odemeList;
	}

	public List<OdemeSekliDTO> getOdemeSekilleri() throws ReadException {
		QueryRunner qr = new QueryRunner();
		List<OdemeSekliDTO> list = new ArrayList<OdemeSekliDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (ArrayList<OdemeSekliDTO>) qr.query(con,
					Sorgular._GETODEMESEKILLERI_.qry,
					new BeanListHandler<OdemeSekliDTO>(OdemeSekliDTO.class));
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<OdemeSekliDTO>() : list;
	}

	@Override
	public List<OdemeDTO> getYapilanOdemeler() throws ReadException {
		QueryRunner qr = new QueryRunner();
		List<OdemeDTO> list = new ArrayList<OdemeDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			Calendar calendar = Calendar.getInstance();
			calendar.set(calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),
					0, 0, 0);

			calendar.add(Calendar.MONTH, -1);
			StringBuffer sb = new StringBuffer();
			sb.append(Sorgular._GETODEMEYAPILANBINALAR_.qry)
					.append(" and (")
					.append(((LoginBean) Common.findBean("loginBean"))
							.getKullaniciBinaKapsam()).append(" )");
			list = (ArrayList<OdemeDTO>) qr.query(con, sb.toString(),
					new BeanListHandler<OdemeDTO>(OdemeDTO.class),
					new Object[] { new java.sql.Date(calendar.getTime()
							.getTime()) });

		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<OdemeDTO>() : list;
	}

	@Override
	public List<BasvuruListeDTO> getOdemeListesi() throws ReadException {
		QueryRunner qr = new QueryRunner();
		List<BasvuruListeDTO> list = new ArrayList<BasvuruListeDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();
			sb.append(Sorgular._GETODEMELISTESI_.qry)
					.append(" and (")
					.append(((LoginBean) Common.findBean("loginBean"))
							.getKullaniciBinaKapsam()).append(" )");
			list = (ArrayList<BasvuruListeDTO>) qr
					.query(con, sb.toString(),
							new BeanListHandler<BasvuruListeDTO>(
									BasvuruListeDTO.class));

		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<BasvuruListeDTO>() : list;
	}

	public void odemeIptal(Connection con2, int basvuruId, int cihazId,int iptalKod,BasvuruAsansorDTO cihaz) throws UpdateException{
		QueryRunner qr = new QueryRunner();
		
		try {
			
		 qr.update(con2, Sorgular._SETODEMEIPTALKOD_.qry,new Object[]{basvuruId,cihazId}
							);
		 if (cihaz.getKontrolTutari()>0 && iptalKod>0) {
			 qr.update(con2,Sorgular._ODEMETUTARUPDATE_.qry,new Object[]{cihaz.getKontrolTutari(),cihaz.getKontrolTutari(),cihaz.getKontrolTutari(),basvuruId});
		 }
		 

		} catch (Exception e) {
			throw new UpdateException(e.getMessage(), null);
		} 
		
		
		
	}

}
