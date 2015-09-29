package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.dto.BucakKoyDTO;
import tr.org.mmo.asansor.dto.IlIlceDTO;
import tr.org.mmo.asansor.dto.UavtBelediyeDTO;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;
import tr.org.mmo.beans.CaddeSokak;
import tr.org.mmo.beans.Mahalle;

public class ApplicationDAOImpl implements ApplicationDAO {

	private Connection con = null;
	private static ApplicationDAOImpl INSTANCE;

	public static ApplicationDAOImpl getINSTANCE() {
		return INSTANCE == null ? new ApplicationDAOImpl() : INSTANCE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IlIlceDTO getIlByIlKodu(int ilKodu) throws ReadException {
		QueryRunner runner = new QueryRunner();
		IlIlceDTO l = new IlIlceDTO();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			l = (IlIlceDTO) runner.query(con, Sorgular._GETIL_.qry,
					new BeanHandler<IlIlceDTO>(IlIlceDTO.class),
					new Object[] { ilKodu });
			if (l == null) {
				throw new ReadException(Messages._ILILCEOKUNAMADI_.getMesaj(),
						null);
			}
		} catch (Exception e) {
			throw new ReadException(e.getMessage(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return l;

	}

	@Override
	public ArrayList<IlIlceDTO> getIllerIlceler() throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<IlIlceDTO> list = new ArrayList<IlIlceDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (ArrayList<IlIlceDTO>) runner.query(con,
					Sorgular._ILLER_ILCELER_.qry,
					new BeanListHandler<IlIlceDTO>(IlIlceDTO.class));
			if (list == null) {
				throw new ReadException(Messages._ILILCEOKUNAMADI_.getMesaj(),
						null);
			}
		} catch (Exception e) {
			throw new ReadException(e.getMessage(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list;

	}

	@Override
	public ArrayList<UavtBelediyeDTO> getUavtBelediye(int ilKodu, int ilceKodu)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<UavtBelediyeDTO> list = new ArrayList<UavtBelediyeDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (ArrayList<UavtBelediyeDTO>) runner
					.query(con, Sorgular._GETUAVTBELEDIYE_.qry,
							new BeanListHandler<UavtBelediyeDTO>(
									UavtBelediyeDTO.class), new Object[] {
									ilKodu, ilceKodu });
			if (list == null) {
				throw new ReadException(
						Messages._UAVTBELEDIYEOKUNAMADI_.getMesaj(), null);
			}
		} catch (Exception e) {
			throw new ReadException(e.getMessage(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list;

	}

	@Override
	public ArrayList<IlIlceDTO> getIlceler(int ilKodu) throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<IlIlceDTO> list = new ArrayList<IlIlceDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (ArrayList<IlIlceDTO>) runner.query(con,
					Sorgular._GETILCELER_.qry, new BeanListHandler<IlIlceDTO>(
							IlIlceDTO.class), new Integer(ilKodu));
			if (list == null) {
				throw new ReadException(Messages._ILILCEOKUNAMADI_.getMesaj(),
						null);
			}
		} catch (Exception e) {
			throw new ReadException(e.getMessage(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list;
	}

	@Override
	public ArrayList<Mahalle> getMahalleByKoyKodu(int koyKodu)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<Mahalle> list = new ArrayList<Mahalle>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (ArrayList<Mahalle>) runner.query(con,
					Sorgular._GETMAHALLELER_.qry, new BeanListHandler<Mahalle>(
							Mahalle.class), new Object[] { koyKodu });
			if (list == null) {
				throw new ReadException(Messages._SQL001_.getMesaj(), null);
			}
		} catch (Exception e) {
			throw new ReadException(e.getMessage(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list;

	}

	@Override
	public ArrayList<CaddeSokak> getCaddeSokakByMahalleId(int mahalleId)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<CaddeSokak> list = new ArrayList<CaddeSokak>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (ArrayList<CaddeSokak>) runner.query(con,
					Sorgular._GETCADDESOKAK_.qry,
					new BeanListHandler<CaddeSokak>(CaddeSokak.class),
					new Integer(mahalleId));
			if (list == null) {
				throw new ReadException(Messages._SQL001_.getMesaj(), null);
			}
		} catch (Exception e) {
			throw new ReadException(e.getMessage(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list;

	}

	@Override
	public List<BucakKoyDTO> getBucakKoyByIlceKod(short ilce)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<BucakKoyDTO> list = new ArrayList<BucakKoyDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (ArrayList<BucakKoyDTO>) runner.query(con,
					Sorgular._GETBUCAKKOY_.qry,
					new BeanListHandler<BucakKoyDTO>(BucakKoyDTO.class),
					new Integer(ilce));
			if (list == null) {
				throw new ReadException(Messages._SQL001_.getMesaj(), null);
			}
		} catch (Exception e) {
			throw new ReadException(e.getMessage(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list;
	}

}
