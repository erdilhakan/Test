package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.dto.BirimDTO;
import tr.org.mmo.asansor.dto.SubeDTO;
import tr.org.mmo.asansor.dto.SubeTemsilcilikHesapDTO;
import tr.org.mmo.asansor.dto.TemsilcilikDTO;
import tr.org.mmo.asansor.dto.TemsilcilikIlIlceDTO;
import tr.org.mmo.asansor.dto.YetkiSayfaDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;

public class BirimDAOImpl implements BirimDAO {

	Connection con = null;

	@Override
	public List<SubeDTO> subeListesi() throws ReadException {

		QueryRunner runner = new QueryRunner();
		List<SubeDTO> list = new ArrayList<SubeDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			list = (List<SubeDTO>) runner.query(con, Sorgular._GETSUBELER_.qry,
					new BeanListHandler<SubeDTO>(SubeDTO.class));
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list == null ? new ArrayList<SubeDTO>() : list;

	}

	@Override
	public List<TemsilcilikDTO> temsilcilikListesi() throws ReadException {

		QueryRunner runner = new QueryRunner();
		List<TemsilcilikDTO> list = new ArrayList<TemsilcilikDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			list = (List<TemsilcilikDTO>) runner.query(con,
					Sorgular._GETTEMSILCILIKLER_.qry,
					new BeanListHandler<TemsilcilikDTO>(TemsilcilikDTO.class));
			for (TemsilcilikDTO t : list) {

				t.setTemsilcilikIller(getTemsilcilikIller(con, t.getKod()));
			}
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list == null ? new ArrayList<TemsilcilikDTO>() : list;

	}

	private List<TemsilcilikIlIlceDTO> getTemsilcilikIller(Connection con,
			int temsilcilikKod) throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<TemsilcilikIlIlceDTO> list = new ArrayList<TemsilcilikIlIlceDTO>();
		try {

			list = (List<TemsilcilikIlIlceDTO>) runner.query(con,
					Sorgular._GETTEMSILCILIKILLER_.qry,
					new BeanListHandler<TemsilcilikIlIlceDTO>(
							TemsilcilikIlIlceDTO.class), new Integer(
							temsilcilikKod));

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}
		return list == null ? new ArrayList<TemsilcilikIlIlceDTO>() : list;
	}

	@Override
	public List<TemsilcilikDTO> subeTemsilcilikListesi(int subeKod)
			throws ReadException {

		QueryRunner runner = new QueryRunner();
		List<TemsilcilikDTO> list = new ArrayList<TemsilcilikDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			list = (List<TemsilcilikDTO>) runner.query(con,
					Sorgular._SUBETEMSILCILIKLISTESI_.qry,
					new BeanListHandler<TemsilcilikDTO>(TemsilcilikDTO.class),
					new Integer(subeKod));
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list == null ? new ArrayList<TemsilcilikDTO>() : list;

	}

	@Override
	public List<BirimDTO> getBirimler(Connection con, Kullanici k)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<BirimDTO> list = new ArrayList<BirimDTO>();
		try {
			list = (List<BirimDTO>) runner.query(con,
					Sorgular._KULLANICIBIRIMLISTESI_.qry,
					new BeanListHandler<BirimDTO>(BirimDTO.class),
					new Object[] { k.getKullaniciId(), k.getKullaniciId() });

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}
		return list == null ? new ArrayList<BirimDTO>() : list;
	}

	@Override
	public List<BirimDTO> getBirimler(Kullanici k) throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<BirimDTO> list = new ArrayList<BirimDTO>();
		try {

			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (List<BirimDTO>) runner.query(con,
					Sorgular._KULLANICIBIRIMLISTESI_.qry,
					new BeanListHandler<BirimDTO>(BirimDTO.class),
					new Object[] { k.getKullaniciId(), k.getKullaniciId() });

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list == null ? new ArrayList<BirimDTO>() : list;
	}

	@Override
	public HashMap<Integer, List<SubeTemsilcilikHesapDTO>> getSubeTemsilcilikHesaplar()
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<SubeTemsilcilikHesapDTO> list = new ArrayList<SubeTemsilcilikHesapDTO>();
		HashMap<Integer, List<SubeTemsilcilikHesapDTO>> map = new HashMap<Integer, List<SubeTemsilcilikHesapDTO>>();

		try {

			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (List<SubeTemsilcilikHesapDTO>) runner.query(con,
					Sorgular._SUBETEMSILCILIKHESAPNUMARALARI_.qry,
					new BeanListHandler<SubeTemsilcilikHesapDTO>(
							SubeTemsilcilikHesapDTO.class));

			for (SubeTemsilcilikHesapDTO st : list) {
				if (map.get(st.getSubeKodu()) == null
						|| map.get(st.getSubeKodu()).size() == 0) {
					List<SubeTemsilcilikHesapDTO> l = new ArrayList<SubeTemsilcilikHesapDTO>();
					l.add(st);
					map.put((Integer) st.getSubeKodu(), l);
				} else {
					map.get(st.getSubeKodu()).add(st);

				}
			}

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return map == null ? new HashMap<Integer, List<SubeTemsilcilikHesapDTO>>()
				: map;
	}

	@Override
	public List<YetkiSayfaDTO> getSayfaYetkileri() throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<YetkiSayfaDTO> list = new ArrayList<YetkiSayfaDTO>();
		try {

			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (List<YetkiSayfaDTO>) runner.query(con,
					Sorgular._SAYFAYETKILERI_.qry,
					new BeanListHandler<YetkiSayfaDTO>(YetkiSayfaDTO.class));

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list == null ? new ArrayList<YetkiSayfaDTO>() : list;
	}

}
