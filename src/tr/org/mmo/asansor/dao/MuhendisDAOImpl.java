package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.dto.BirimDTO;
import tr.org.mmo.asansor.dto.MuhendisCihazYetkiDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.managedbeans.LoginBean;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;

public class MuhendisDAOImpl implements MuhendisDAO {
	Connection con = null;

	@Override
	public List<Kullanici> muhendisListesi() throws ReadException {

		QueryRunner runner = new QueryRunner();
		List<Kullanici> list = new ArrayList<Kullanici>();
		List<Kullanici> sList = new ArrayList<Kullanici>();
		List<Kullanici> tList = new ArrayList<Kullanici>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			Kullanici kullanici = new Kullanici();
			kullanici = ((LoginBean) Common.findBean("loginBean"))
					.getKullanici();
			StringBuffer sb = new StringBuffer();
			sb.append("(");

			boolean kayitVar = false;
			for (BirimDTO b : kullanici.getBirimler()) {
				if (b.getBirimTipi().equals("S")) {
					if (kayitVar)
						sb.append(",");
					sb.append(b.getBirimKodu());
					kayitVar = true;

				}
			}
			sb.append(")");
			StringBuffer sbq = new StringBuffer();
			sbq.append(Sorgular._MUHENDISLISTFULL_.qry);
			sbq.append(" and birim_kodu in ");
			sbq.append(sb.toString());
			if (kayitVar) {
				sList = (List<Kullanici>) runner.query(con, sbq.toString(),
						new BeanListHandler<Kullanici>(Kullanici.class),
						new Object[] { "S" });

				for (Kullanici k : sList) {
					list.add(k);
				}

			}
			sb = new StringBuffer();
			sb.append("(");

			kayitVar = false;
			for (BirimDTO b : kullanici.getBirimler()) {
				if (b.getBirimTipi().equals("T")) {
					if (kayitVar)
						sb.append(",");
					sb.append(b.getBirimKodu());
					kayitVar = true;

				}
			}
			sb.append(")");
			sbq = new StringBuffer();
			sbq.append(Sorgular._MUHENDISLISTFULL_.qry);
			sbq.append(" and birim_kodu in ");
			sbq.append(sb.toString());
			if (kayitVar) {
				tList = (List<Kullanici>) runner.query(con, sbq.toString(),
						new BeanListHandler<Kullanici>(Kullanici.class),
						new Object[] { "T" });

				for (Kullanici k : tList) {
					boolean yok = true;
					for (Kullanici k1 : list) {
						if (k1.getSicilNo() == k.getSicilNo())
							yok = false;
					}
					if (yok)
						list.add(k);

				}

			}
			list = setMuhendisCihazKontrolYetki(con, list);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		HashMap<Integer, Kullanici> map = new HashMap<Integer, Kullanici>();

		for (Kullanici k : list) {
			map.put(k.getKullaniciId(), k);
		}
		list = new ArrayList<Kullanici>();
		Set<Integer> set = map.keySet();
		Iterator<Integer> it = set.iterator();
		while (it.hasNext()) {
			int i = it.next();
			list.add(map.get(i));
		}
		return list == null ? new ArrayList<Kullanici>() : list;

	}

	@Override
	public List<Kullanici> getMuhendis() throws ReadException {

		QueryRunner runner = new QueryRunner();
		List<Kullanici> list = new ArrayList<Kullanici>();
		List<Kullanici> sList = new ArrayList<Kullanici>();
		List<Kullanici> tList = new ArrayList<Kullanici>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			Kullanici kullanici = new Kullanici();
			kullanici = ((LoginBean) Common.findBean("loginBean"))
					.getKullanici();
			StringBuffer sb = new StringBuffer();
			sb.append("(");

			boolean kayitVar = false;
			for (BirimDTO b : kullanici.getBirimler()) {
				if (b.getBirimTipi().equals("S")) {
					if (kayitVar)
						sb.append(",");
					sb.append(b.getBirimKodu());
					kayitVar = true;

				}
			}
			sb.append(")");
			StringBuffer sbq = new StringBuffer();
			sbq.append(Sorgular._MUHENDISLIST_.qry);
			sbq.append(" and birim_kodu in ");
			sbq.append(sb.toString());
			if (kayitVar) {
				sList = (List<Kullanici>) runner.query(con, sbq.toString(),
						new BeanListHandler<Kullanici>(Kullanici.class),
						new Object[] { "S" });

				for (Kullanici k : sList) {
					list.add(k);
				}

			}
			sb = new StringBuffer();
			sb.append("(");

			kayitVar = false;
			for (BirimDTO b : kullanici.getBirimler()) {
				if (b.getBirimTipi().equals("T")) {
					if (kayitVar)
						sb.append(",");
					sb.append(b.getBirimKodu());
					kayitVar = true;

				}
			}
			sb.append(")");
			sbq = new StringBuffer();
			sbq.append(Sorgular._MUHENDISLIST_.qry);
			sbq.append(" and birim_kodu in ");
			sbq.append(sb.toString());
			if (kayitVar) {
				tList = (List<Kullanici>) runner.query(con, sbq.toString(),
						new BeanListHandler<Kullanici>(Kullanici.class),
						new Object[] { "T" });

				for (Kullanici k : tList) {
					boolean yok = true;
					for (Kullanici k1 : list) {
						if (k1.getSicilNo() == k.getSicilNo())
							yok = false;
					}
					if (yok)
						list.add(k);

				}
			}

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}

		HashMap<Integer, Kullanici> map = new HashMap<Integer, Kullanici>();

		for (Kullanici k : list) {
			map.put(k.getKullaniciId(), k);
		}
		list = new ArrayList<Kullanici>();
		Set<Integer> set = map.keySet();
		Iterator<Integer> it = set.iterator();
		while (it.hasNext()) {
			int i = it.next();
			list.add(map.get(i));
		}
		return list == null ? new ArrayList<Kullanici>() : list;

	}

	@Override
	public boolean muhendisVarMi(int sicilNo) throws ReadException {
		try {
			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			if (Integer.parseInt(getMuhendis(con, sicilNo).getSicilNo()) > -1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}

	}

	@Override
	public void muhendisUpdate(Kullanici muhendis) throws UpdateException {

		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";
			int sicilNo = Integer.parseInt(muhendis.getSicilNo().trim());
			muhendisCihazYetkiSil(con, sicilNo);
			muhendisCihazYetkiKaydet(con, sicilNo,
					muhendis.getMuhendisCihazYetki(), kullanici);
			int i = runner.update(
					con,
					Sorgular._MUHENDISUPDATE_.qry,
					new Object[] { Integer.parseInt(muhendis.getSicilNo()),
							muhendis.getAdi(), muhendis.getSoyadi(),
							muhendis.getePosta(), muhendis.getOnayYetkisi(),
							muhendis.getAkreditasyonDeger(),
							muhendis.getTelefonNo(),
							muhendis.getGsmTelefonNo(), muhendis.getIl(),
							muhendis.getMuhendisGunlukKontrolAdet(), kullanici,
							muhendis.getKullaniciId() });

			// update edemiyorsa insert et
			// Mühendis ve kullanıcı birleştirildi. Mühendis İnsert iptal
			// edildi. 21.07.2014
			// i=(i<=0?muhendisKaydet(muhendis, con):i);

			if (i > 0) {
				con.commit();
			}
		} catch (CreateException e) {
			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		} catch (Exception e) {
			throw new UpdateException(Messages._SQL_512_.getMesaj(), null);

		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public void muhendisCihazYetkiKaydet(Connection con2, int sicilNo,
			int[] cihazTipiList, String kullanici) throws SQLException {
		QueryRunner runner = new QueryRunner();
		for (int i : cihazTipiList) {
			runner.update(con2, Sorgular._MUHENDISCIHAZYETKIKAYDET_.qry,
					new Object[] { sicilNo, i, kullanici });
		}

	}

	private void muhendisCihazYetkiSil(Connection con2, int sicilNo)
			throws SQLException {

		QueryRunner runner = new QueryRunner();

		runner.update(con2, Sorgular._MUHENDISCIHAZYETKISIL_.qry,
				new Object[] { sicilNo });

	}

	@Override
	public List<Kullanici> getOnaycilar() throws ReadException {

		QueryRunner runner = new QueryRunner();

		List<Kullanici> list = new ArrayList<Kullanici>();
		List<Kullanici> sList = new ArrayList<Kullanici>();
		List<Kullanici> tList = new ArrayList<Kullanici>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			Kullanici kullanici = new Kullanici();
			kullanici = ((LoginBean) Common.findBean("loginBean"))
					.getKullanici();
			StringBuffer sb = new StringBuffer();
			sb.append("(");

			boolean kayitVar = false;
			for (BirimDTO b : kullanici.getBirimler()) {
				if (b.getBirimTipi().equals("S")) {
					if (kayitVar)
						sb.append(",");
					sb.append(b.getBirimKodu());
					kayitVar = true;

				}
			}
			sb.append(")");
			StringBuffer sbq = new StringBuffer();
			sbq.append(Sorgular._ONAYCILIST_.qry);
			sbq.append(" and birim_kodu in ");
			sbq.append(sb.toString());
			if (kayitVar) {
				sList = (List<Kullanici>) runner.query(con, sbq.toString(),
						new BeanListHandler<Kullanici>(Kullanici.class),
						new Object[] { "S" });

				for (Kullanici k : sList) {
					list.add(k);
				}

			}
			sb = new StringBuffer();
			sb.append("(");

			kayitVar = false;
			for (BirimDTO b : kullanici.getBirimler()) {
				if (b.getBirimTipi().equals("T")) {
					if (kayitVar)
						sb.append(",");
					sb.append(b.getBirimKodu());
					kayitVar = true;

				}
			}
			sb.append(")");
			sbq = new StringBuffer();
			sbq.append(Sorgular._ONAYCILIST_.qry);
			sbq.append(" and birim_kodu in ");
			sbq.append(sb.toString());
			if (kayitVar) {
				tList = (List<Kullanici>) runner.query(con, sbq.toString(),
						new BeanListHandler<Kullanici>(Kullanici.class),
						new Object[] { "T" });

			}
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		HashMap<Integer, Kullanici> map = new HashMap<Integer, Kullanici>();
		int sicilNo = 0;
		for (Kullanici k : sList) {
			try {
				sicilNo = Integer.parseInt(k.getSicilNo());
				map.put(sicilNo, k);
			} catch (Exception e) {
				sicilNo = 0;
			}
		}
		for (Kullanici k : tList) {
			try {
				sicilNo = Integer.parseInt(k.getSicilNo());
				map.put(sicilNo, k);
			} catch (Exception e) {
				sicilNo = 0;
			}

		}
		Set<Integer> s = map.keySet();
		Iterator<Integer> i = s.iterator();
		list = new ArrayList<Kullanici>();
		while (i.hasNext()) {
			int j = i.next();
			list.add(map.get(j));
		}
		return list == null ? new ArrayList<Kullanici>() : list;
	}

	@Override
	public Kullanici getMuhendis(Connection con2, int sicilNo)
			throws ReadException {
		Kullanici muhendis = new Kullanici();
		QueryRunner runner = new QueryRunner();

		try {

			muhendis = (Kullanici) runner.query(con2,
					Sorgular._MUHENDISGETIRBYSICILNO_.qry,
					new BeanHandler<Kullanici>(Kullanici.class), new Integer(
							sicilNo));

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}

		return muhendis;

	}

	@Override
	public void muhendisKaydet(Kullanici muhendis) throws UpdateException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getGunlukKontrolAdet(int sicilNo) throws ReadException {
		Kullanici muhendis = new Kullanici();
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			muhendis = (Kullanici) runner.query(con,
					Sorgular._GETKONTROLADET_.qry, new BeanHandler<Kullanici>(
							Kullanici.class), new Integer(sicilNo));

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

		return muhendis != null ? muhendis.getMuhendisGunlukKontrolAdet() : 0;

	}

	private List<Kullanici> setMuhendisCihazKontrolYetki(Connection con2,
			List<Kullanici> list) {
		QueryRunner runner = new QueryRunner();
		for (Kullanici k : list) {
			List<MuhendisCihazYetkiDTO> m = new ArrayList<MuhendisCihazYetkiDTO>();
			try {
				m = (List<MuhendisCihazYetkiDTO>) runner.query(con2,
						Sorgular._GETMUHENDISCIHAZYETKI.qry,
						new BeanListHandler<MuhendisCihazYetkiDTO>(
								MuhendisCihazYetkiDTO.class), new Integer(k
								.getSicilNo().trim()));
			} catch (NumberFormatException e) {
				m = new ArrayList<MuhendisCihazYetkiDTO>();
			} catch (SQLException e) {
				m = new ArrayList<MuhendisCihazYetkiDTO>();
			}
			int[] cy = new int[m.size()];
			int i = 0;
			for (MuhendisCihazYetkiDTO mcyd : m) {
				cy[i] = mcyd.getCihazTipi();
				i++;
			}
			k.setMuhendisCihazYetki(cy);

		}
		return list;
	}

}
