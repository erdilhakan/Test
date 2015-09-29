package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.dto.CurrVal;
import tr.org.mmo.asansor.dto.KullaniciRolYetkiDTO;
import tr.org.mmo.asansor.dto.RolYetkiDTO;
import tr.org.mmo.asansor.dto.RolYetkiRelationDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;

public class RolDAOImpl implements RolDAO {

	Connection con = null;

	@Override
	public List<RolYetkiDTO> rolListesi() throws ReadException {

		QueryRunner runner = new QueryRunner();
		List<RolYetkiDTO> list = new ArrayList<RolYetkiDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			list = (List<RolYetkiDTO>) runner.query(con,
					Sorgular._ROLLISTESI_.qry,
					new BeanListHandler<RolYetkiDTO>((RolYetkiDTO.class)));
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list == null ? new ArrayList<RolYetkiDTO>() : list;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<KullaniciRolYetkiDTO> getKullaniciRoller(Kullanici k)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<KullaniciRolYetkiDTO> list = new ArrayList<KullaniciRolYetkiDTO>();
		try {

			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (List<KullaniciRolYetkiDTO>) runner.query(con,
					Sorgular._KULLANICIROLYETKILISTESI_.qry,
					new BeanListHandler(KullaniciRolYetkiDTO.class),
					new Object[] { k.getKullaniciId() });

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list == null ? new ArrayList<KullaniciRolYetkiDTO>() : list;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<KullaniciRolYetkiDTO> getKullaniciRoller(Connection con,
			Kullanici k) throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<KullaniciRolYetkiDTO> list = new ArrayList<KullaniciRolYetkiDTO>();
		try {

			list = (List<KullaniciRolYetkiDTO>) runner.query(con,
					Sorgular._KULLANICIROLYETKILISTESI_.qry,
					new BeanListHandler(KullaniciRolYetkiDTO.class),
					new Object[] { k.getKullaniciId() });

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}
		return list == null ? new ArrayList<KullaniciRolYetkiDTO>() : list;

	}

	@Override
	public List<RolYetkiDTO> yetkiListesi() throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<RolYetkiDTO> list = new ArrayList<RolYetkiDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			list = (List<RolYetkiDTO>) runner.query(con,
					Sorgular._YETKILISTESI_.qry,
					new BeanListHandler<RolYetkiDTO>((RolYetkiDTO.class)));
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list == null ? new ArrayList<RolYetkiDTO>() : list;
	}

	@Override
	public int rolEkle(RolYetkiDTO r) throws CreateException {
		QueryRunner runner = new QueryRunner();
		int rolId;
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			runner.update(con, Sorgular._ROLEKLE_.qry,
					new Object[] { r.getAdi() });
			CurrVal cv = (CurrVal) runner.query(con, Sorgular._CURRVALROL_.qry,
					new BeanHandler<CurrVal>(CurrVal.class));
			rolId = cv.getCurrval();
			con.commit();
		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new CreateException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return rolId;

	}

	@Override
	public void rolSil(int id) throws DeleteException {
		QueryRunner runner = new QueryRunner();

		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			runner.update(con, Sorgular._ROLSIL_.qry, new Object[] { id });
			deleteRolYetkileri(con, id);
			con.commit();
		} catch (SQLException e) {
			throw new DeleteException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new DeleteException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}

	}

	private void deleteRolYetkileri(Connection conn, int rolId)
			throws DeleteException {

		QueryRunner runner = new QueryRunner();

		try {

			runner.update(conn, Sorgular._ROLYETKISILBYROLID_.qry,
					new Object[] { rolId });
		} catch (SQLException e) {
			throw new DeleteException(Messages._SQL_500_.getMesaj(), null);
		}

	}

	@Override
	public List<RolYetkiRelationDTO> getRolYetkiler(int rolId)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<RolYetkiRelationDTO> liste = new ArrayList<RolYetkiRelationDTO>();

		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			liste = (List<RolYetkiRelationDTO>) runner.query(con,
					Sorgular._GETROLYETKILER_.qry,
					new BeanListHandler<RolYetkiRelationDTO>(
							RolYetkiRelationDTO.class), new Object[] { rolId });
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return liste;
	}

	@Override
	public void rolYetkiSil(RolYetkiRelationDTO yetki) throws DeleteException {
		QueryRunner runner = new QueryRunner();

		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			runner.update(con, Sorgular._ROLYETKISIL_.qry,
					new Object[] { yetki.getRolId(), yetki.getYetkiId() });

			con.commit();
		} catch (SQLException e) {
			throw new DeleteException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new DeleteException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}

	}

	@Override
	public void yetkiTasi(List<RolYetkiDTO> secilenYetki, int rolId)
			throws CreateException {
		PreparedStatement pstmt = null;

		try {
			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(Sorgular._ROLYETKIEKLE_.qry);
			pstmt.setInt(1, rolId);
			for (RolYetkiDTO r : secilenYetki) {
				pstmt.setInt(2, r.getId());
				pstmt.addBatch();
			}
			pstmt.executeBatch();

			con.commit();
		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new CreateException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}

	}
}
