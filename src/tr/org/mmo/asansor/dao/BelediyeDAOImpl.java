package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.dto.BelediyeDTO;
import tr.org.mmo.asansor.dto.BelediyeIletisimDTO;
import tr.org.mmo.asansor.dto.BelediyeSozlesmeDTO;
import tr.org.mmo.asansor.dto.CurrVal;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.managedbeans.LoginBean;
import tr.org.mmo.asansor.util.Ayarlar;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;

public class BelediyeDAOImpl implements BelediyeDAO {
	Connection con = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BelediyeDTO> getAllBelediye() throws ReadException {
		List<BelediyeDTO> belediyeList = new ArrayList<BelediyeDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();
			sb.append(Sorgular._GETALLBELEDIYE.qry);

			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			sb.append(loginBean.getIlSorguKosul());

			belediyeList = (List<BelediyeDTO>) runner.query(con, sb.toString(),
					new BeanListHandler(BelediyeDTO.class));
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return belediyeList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BelediyeDTO> geBelediyeByIl(int ilKodu, int binaTip)
			throws ReadException {
		List<BelediyeDTO> belediyeList = new ArrayList<BelediyeDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();
			sb.append(Sorgular._GETALLBELEDIYE.qry);

			if (ilKodu > 0) {
				sb.append(" a.il=");
				sb.append(ilKodu);
			} else {
				sb.append(" a.il between 1 and 99");
			}
			sb.append(" and kod in (select distinct belediye_kod from "
					+ Ayarlar.SHEMA
					+ ".belediye_sozlesme where sozlesme_bitis_tarihi>=current_date and sozlesme_bina_tip_id=?)  ");
			belediyeList = (List<BelediyeDTO>) runner.query(con, sb.toString(),
					new BeanListHandler(BelediyeDTO.class),
					new Object[] { binaTip });
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return belediyeList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int iletisimEkle(BelediyeIletisimDTO iletisim)
			throws CreateException {
		QueryRunner runner = new QueryRunner();
		int iletisimId = 0;
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			runner.update(
					con,
					Sorgular._BELEDIYEILETISIMINSERT_.qry,
					new Object[] { iletisim.getBelediyeKod(),
							iletisim.getAdi(), iletisim.getSoyadi(),
							iletisim.getUnvani(), iletisim.getTelefonNo(),
							iletisim.getePosta(),
							loginBean.getKullanici().getKullaniciAdi() });

			CurrVal cv = (CurrVal) runner.query(con,
					Sorgular._CURRVALBELEDIYEILETISIM_.qry, new BeanHandler(
							CurrVal.class));
			iletisimId = cv.getCurrval();
			con.commit();
		} catch (Exception e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return iletisimId;

	}

	@Override
	public void iletisimSil(int id) throws DeleteException {
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			runner.update(con, Sorgular._BELEDIYEILETISIMDELETE_.qry,
					new Integer(id));

			con.commit();
		} catch (Exception e) {
			throw new DeleteException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public void sozlesmeSil(int id) throws DeleteException {
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			runner.update(con, Sorgular._BELEDIYESOZLESMEDELETE_.qry,
					new Integer(id));

			con.commit();
		} catch (Exception e) {
			throw new DeleteException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public void iletisimGuncelle(BelediyeIletisimDTO iletisim)
			throws UpdateException {
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			runner.update(
					con,
					Sorgular._BELEDIYEILETISIMUPDATE_.qry,
					new Object[] { iletisim.getBelediyeKod(),
							iletisim.getAdi(), iletisim.getSoyadi(),
							iletisim.getUnvani(), iletisim.getTelefonNo(),
							iletisim.getePosta(),
							loginBean.getKullanici().getKullaniciAdi(),
							iletisim.getId() });

			con.commit();
		} catch (Exception e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public void sozlesmeGuncelle(BelediyeSozlesmeDTO sozlesme,
			int sozlesmeBinaTip) throws UpdateException {
		QueryRunner runner = new QueryRunner();

		try {

			try {
				con = DAOBase.instance().getConnection();
				con.setAutoCommit(false);
			} catch (Exception e) {
				throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(),
						null);
			}
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			runner.update(
					con,
					Sorgular._BELEDIYESOZLESMEUPDATE_.qry,
					new Object[] {
							sozlesme.getBelediyeKod(),
							sozlesme.getYil(),
							sozlesme.getCihazTipi(),
							new Date(sozlesme.getSozlesmeBaslangicTarihi()
									.getTime()),
							new Date(sozlesme.getSozlesmeBitisTarihi()
									.getTime()), sozlesme.getFiyat(),
							sozlesme.getKapasiteOlcut(),
							sozlesme.getKapasite(), sozlesmeBinaTip,
							sozlesme.getKontrolTipiArr()[0],
							loginBean.getKullanici().getKullaniciAdi(),
							sozlesme.getId() });

			con.commit();
		} catch (SQLException e) {
			if (e.getSQLState().equals("23505")) {
				throw new UpdateException(Messages._SQL_501_.getMesaj(), null);
			} else
				throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int belediyeEkle(BelediyeDTO belediye) throws CreateException {

		QueryRunner runner = new QueryRunner();
		int belediyeKod = 0;
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			runner.update(con, Sorgular._BELEDIYEINSERT_.qry,
					new Object[] { belediye.getAdi(),
							belediye.getTemsilcilik(), belediye.getSube(),
							belediye.getIl(), belediye.getIlce(),
							loginBean.getKullanici().getKullaniciAdi(),belediye.getUavtKod() });

			CurrVal cv = (CurrVal) runner.query(con,
					Sorgular._CURRVALBELEDIYE_.qry, new BeanHandler(
							CurrVal.class));
			belediyeKod = cv.getCurrval();

			con.commit();
		} catch (SQLException e) {
			if (e.getSQLState().equals("23505")) {
				throw new CreateException(Messages._SQL_501_.getMesaj(), null);
			} else
				throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} catch (Exception e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return belediyeKod;

	}

	@Override
	public int belediyeKaydet(BelediyeDTO belediye) throws UpdateException {

		QueryRunner runner = new QueryRunner();
		int belediyeKod = 0;
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			runner.update(con, Sorgular._BELEDIYEUPDATE_.qry,
					new Object[] { belediye.getAdi(),
							belediye.getTemsilcilik(), belediye.getSube(),
							belediye.getIl(), belediye.getIlce(),
							loginBean.getKullanici().getKullaniciAdi(),
							belediye.getUavtKod(),
							belediye.getKod() });
			con.commit();
		} catch (Exception e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return belediyeKod;

	}

	@Override
	public List<BelediyeSozlesmeDTO> sozlesmeKaydet(
			BelediyeSozlesmeDTO sozlesme, int[] sozlesmeBinaTipList)
			throws CreateException {
		QueryRunner runner = new QueryRunner();

		List<BelediyeSozlesmeDTO> list = new ArrayList<BelediyeSozlesmeDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			for (int i : sozlesmeBinaTipList) {
				sozlesme.setSozlesmeBinaTipId(i);
				for (int j : sozlesme.getArrCihazTip()) {
					sozlesme.setCihazTipi(j);
					for (String st:sozlesme.getKontrolTipiArr()){
					runner.update(
							con,
							Sorgular._BELEDIYESOZLESMEINSERT_.qry,
							new Object[] {
									sozlesme.getBelediyeKod(),
									sozlesme.getYil(),

									j,
									new Date(sozlesme
											.getSozlesmeBaslangicTarihi()
											.getTime()),
									new Date(sozlesme.getSozlesmeBitisTarihi()
											.getTime()), sozlesme.getFiyat(),
									sozlesme.getKapasiteOlcut(),
									sozlesme.getKapasite(), i,st,
									loginBean.getKullanici().getKullaniciAdi() });

					CurrVal cv = (CurrVal) runner.query(con,
							Sorgular._CURRVALBELEDIYESOZLESME_.qry,
							new BeanHandler<CurrVal>(CurrVal.class));
					sozlesme.setId(cv.getCurrval());
					list.add(sozlesme);
					}
				}

			}
			con.commit();
		} catch (SQLException e) {
			if (e.getSQLState().equals("23505")) {
				throw new CreateException(Messages._SQL_501_.getMesaj(), null);
			} else
				throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} catch (Exception e) {

			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<BelediyeSozlesmeDTO>() : list;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BelediyeIletisimDTO> getBelediyeIletisim(int kod)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<BelediyeIletisimDTO> list = new ArrayList<BelediyeIletisimDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (ArrayList<BelediyeIletisimDTO>) runner.query(con,
					Sorgular._GETBELEDIYEILETISIM_.qry, new BeanListHandler(
							BelediyeIletisimDTO.class), new Integer(kod));

		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BelediyeSozlesmeDTO> getBelediyeSozlesme(int kod)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<BelediyeSozlesmeDTO> list = new ArrayList<BelediyeSozlesmeDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (ArrayList<BelediyeSozlesmeDTO>) runner.query(con,
					Sorgular._GETBELEDIYESOZLESME_.qry, new BeanListHandler(
							BelediyeSozlesmeDTO.class), new Integer(kod));

		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list;

	}

	@Override
	public List<BelediyeDTO> getSozlesmesiBitecekBelediyeler()
			throws ReadException {
		List<BelediyeDTO> belediyeList = new ArrayList<BelediyeDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();
			sb.append(Sorgular._GETSOZLESMESIBITECEKBELEDIYELER.qry);

			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			if (loginBean.getIlSorguKosul().length() > 2) {
				sb.append(" and ");
				sb.append(loginBean.getIlSorguKosul());
			}

			belediyeList = (List<BelediyeDTO>) runner.query(con, sb.toString(),
					new BeanListHandler<BelediyeDTO>(BelediyeDTO.class));
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return belediyeList;

	}

	@Override
	public BelediyeDTO getBelediyeByKod(int belediye) throws ReadException{
		BelediyeDTO belediyeDTO = new BelediyeDTO();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();
			sb.append(Sorgular._GETALLBELEDIYE.qry);
			sb.append(" a.kod=?");
			

			belediyeDTO = (BelediyeDTO) runner.query(con, sb.toString(),
					new BeanHandler<BelediyeDTO>(BelediyeDTO.class),new Integer(belediye));
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return belediyeDTO;
	}

	@Override
	public void setBelediyeUavtKod(BelediyeDTO belediye) throws UpdateException {
		QueryRunner runner = new QueryRunner();
		try {
		

			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			
				int i = runner.update(con, Sorgular._CIHAZUAVTUPDATE_.qry,
						new Object[] {belediye.getUavtKod(),belediye.getKod()});
			if (i>0)	con.commit();
			else con.rollback();
		} catch (CreateException e) {
			throw new UpdateException(e.getMessage(), null);
		} catch (SQLException e) {
			throw new UpdateException(e.getMessage(), null);
		}
		finally{
			DbUtils.closeQuietly(con);
		}

		
	}
	
	
	

}
