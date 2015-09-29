package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.beans_.Randevu;
import tr.org.mmo.asansor.business.BasvuruBusiness;
import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.CurrVal;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.dto.MuhendisCihazYetkiDTO;
import tr.org.mmo.asansor.dto.RandevuListeDTO;
import tr.org.mmo.asansor.dto.RandevuMuhendisDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.managedbeans.LoginBean;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;

public class RandevuDAOImpl implements RandevuDAO {
	private Connection con = null;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public int randevuEkle(Randevu r) throws CreateException {

		QueryRunner runner = new QueryRunner();
		DateFormat df = new SimpleDateFormat("HH:mm");
		boolean eklendi = false;
		int randevuId = 0;
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			df.format(r.getRandevuTarih());
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			int i = runner.update(con, Sorgular._RANDEVU_INSERT_.qry,
					new Object[] {
							new java.sql.Date(r.getRandevuTarih().getTime()),
							new java.sql.Time(r.getRandevuTarih().getTime()),
							'R', r.getBasvuru().getBasvuruId(),
							loginBean.getKullanici().getKullaniciAdi() });

			if (i > 0) {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				CurrVal cv = (CurrVal) runner.query(con,
						Sorgular._CURRVALRANDEVU_.qry, new BeanHandler(
								CurrVal.class));
				randevuId = cv.getCurrval();

				if (randevuMuhendisEkle(con, randevuId,
						r.getSecilenMuhendisler())) {
					try {
						new BasvuruBusiness().basvuruDurumGuncelle(con, r
								.getBasvuru().getBasvuruId(), "R");
						con.commit();
						eklendi = true;
					} catch (UpdateException e) {
						throw new CreateException(Messages._GENEL_.getMesaj(),
								null);
					}

				}

			}
			if (!eklendi) {
				throw new CreateException(Messages._GENEL_.getMesaj(), null);
			}

		} catch (CreateException e) {
			throw new CreateException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return randevuId;
	}

	@Override
	public int randevuEkle(Randevu r, tr.org.mmo.asansor.beans_.Basvuru b)
			throws CreateException {

		QueryRunner runner = new QueryRunner();
		DateFormat df = new SimpleDateFormat("HH:mm");
		boolean eklendi = false;

		int randevuId = 0;
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			df.format(r.getRandevuTarih());
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			int i = runner.update(con, Sorgular._RANDEVU_INSERT_.qry,
					new Object[] {
							new java.sql.Date(r.getRandevuTarih().getTime()),
							new java.sql.Time(r.getRandevuTarih().getTime()),
							'R', b.getTarama().getId(),
							loginBean.getKullanici().getKullaniciAdi() });

			if (i > 0) {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				CurrVal cv = (CurrVal) runner.query(con,
						Sorgular._CURRVALRANDEVU_.qry, new BeanHandler(
								CurrVal.class));
				randevuId = cv.getCurrval();

				if (randevuMuhendisEkle(con, cv.getCurrval(),
						r.getSecilenMuhendisler())) {
					try {
						new BasvuruBusiness().basvuruDurumGuncelle(con, b
								.getTarama().getId(), "R");
						new BasvuruBusiness().taramaDurumGuncelle(con, b
								.getTarama().getId(), "R");
						con.commit();
						eklendi = true;
					} catch (UpdateException e) {
						throw new CreateException(Messages._GENEL_.getMesaj(),
								null);
					}

				}

			}
			if (!eklendi) {
				throw new CreateException(Messages._GENEL_.getMesaj(), null);
			}

		} catch (CreateException e) {
			throw new CreateException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return randevuId;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Randevu randevuGetir(Date tarih) throws ReadException {
		Randevu r = new Randevu();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			r = (Randevu) runner.query(con, Sorgular._RANDEVU_TARIHEGORE_.qry,
					new BeanHandler(Randevu.class),
					new Object[] { df.format(tarih) });
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}

		finally {
			DbUtils.closeQuietly(con);

		}

		return r;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Randevu randevuGetir(int randevuId) throws ReadException {
		Randevu r = new Randevu();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			r = (Randevu) runner.query(con, Sorgular._RANDEVU_IDEGORE_.qry,
					new BeanHandler(Randevu.class), new Object[] { randevuId });
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}

		finally {
			DbUtils.closeQuietly(con);

		}

		return r;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Override
	public ArrayList<RandevuListeDTO> listeGetir() throws ReadException {
		ArrayList<RandevuListeDTO> liste = new ArrayList<RandevuListeDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();

			sb.append(Sorgular._RANDEVULISTE_.qry);
			sb.append(((LoginBean) Common.findBean("loginBean"))
					.getIlSorguKosul());
			liste = (ArrayList<RandevuListeDTO>) runner.query(con,
					sb.toString(), new BeanListHandler(RandevuListeDTO.class));
			Calendar cal = Calendar.getInstance();
			Calendar cal1 = Calendar.getInstance();
			for (RandevuListeDTO l : liste) {
				cal.setTime(l.getRandevuSaati());
				cal1.setTime(l.getRandevuTarihi());
				cal1.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
				cal1.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
				l.setRandevuTarihi(cal1.getTime());

				ArrayList<RandevuMuhendisDTO> lst = new ArrayList<RandevuMuhendisDTO>();
				lst = getRandevuMuhendis(con, l.getRandevuId());
				List<Kullanici> muhendisListe = new ArrayList<Kullanici>();

				for (RandevuMuhendisDTO d : lst) {
					Kullanici k = new Kullanici();
					k.setSicilNo(String.valueOf(d.getSicilNo()));

					k.setAdi(d.getAdiSoyadi());
					muhendisListe.add(k);
					// l.getMuhendis().add(d.getAdiSoyadi());
					TreeNode sicilNo = new DefaultTreeNode(d.getSicilNo(),
							l.getMuhendis());
					TreeNode adi = new DefaultTreeNode(d.getAdiSoyadi(),
							l.getMuhendis());
					TreeNode eposta = new DefaultTreeNode("E-Posta : "
							+ d.getePosta(), adi);
					TreeNode telefon = new DefaultTreeNode("Telefon : "
							+ d.getTelefonNo(), adi);

				}
				l.setMuhendisler(muhendisListe);
				
				l.setFirmalar(new FirmaBusiness().setAsansorBakimciFirmalar(
						con, l.getBasvuruId()));
				Map<Integer,String> firmaMap=new HashMap<Integer,String>();
				for (FirmaDTO f:l.getFirmalar()){
					firmaMap.put(f.getKod(), f.getUnvan());
				}
				StringBuffer sb1=new StringBuffer();
				Set<Integer> set=firmaMap.keySet();
				Iterator<Integer> iter=set.iterator();
				while (iter.hasNext()){
					int i=iter.next();
					sb1.append(firmaMap.get(i));
					sb1.append(" ");
				}
				l.setUnvan(sb1.toString().trim());
				
			}

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}

		finally {
			DbUtils.closeQuietly(con);

		}

		return liste == null ? new ArrayList<RandevuListeDTO>() : liste;

	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation", "unused" })
	@Override
	public ArrayList<RandevuListeDTO> listeGetirForMuhendis(int muhendisSicilNo)
			throws ReadException {
		ArrayList<RandevuListeDTO> liste = new ArrayList<RandevuListeDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();

			liste = (ArrayList<RandevuListeDTO>) runner.query(con,
					Sorgular._RANDEVULISTEMUHENDISEGORE_.qry,
					new BeanListHandler(RandevuListeDTO.class), new Integer(
							muhendisSicilNo));

			for (RandevuListeDTO l : liste) {

				l.getRandevuSaati().setHours(l.getRandevuSaati().getHours());
				ArrayList<RandevuMuhendisDTO> lst = new ArrayList<RandevuMuhendisDTO>();
				lst = getRandevuMuhendis(con, l.getRandevuId());
				List<Kullanici> muhendisListe = new ArrayList<Kullanici>();

				for (RandevuMuhendisDTO d : lst) {
					Kullanici k = new Kullanici();
					k.setSicilNo(String.valueOf(d.getSicilNo()));

					k.setAdi(d.getAdiSoyadi());
					muhendisListe.add(k);

					// l.getMuhendis().add(d.getAdiSoyadi());
					TreeNode sicilNo = new DefaultTreeNode(d.getSicilNo(),
							l.getMuhendis());
					TreeNode adi = new DefaultTreeNode(d.getAdiSoyadi(),
							l.getMuhendis());
					TreeNode eposta = new DefaultTreeNode("E-Posta : "
							+ d.getePosta(), adi);
					TreeNode telefon = new DefaultTreeNode("Telefon : "
							+ d.getTelefonNo(), adi);

				}
				l.setMuhendisler(muhendisListe);
				l.setFirmalar(new FirmaBusiness().setAsansorBakimciFirmalar(
						con, l.getBasvuruId()));
				Map<Integer,String> firmaMap=new HashMap<Integer,String>();
				for (FirmaDTO f:l.getFirmalar()){
					firmaMap.put(f.getKod(), f.getUnvan());
				}
				StringBuffer sb1=new StringBuffer();
				Set<Integer> set=firmaMap.keySet();
				Iterator<Integer> iter=set.iterator();
				while (iter.hasNext()){
					int i=iter.next();
					sb1.append(firmaMap.get(i));
					sb1.append(" ");
				}
				l.setUnvan(sb1.toString().trim());
				
			}

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}

		finally {
			DbUtils.closeQuietly(con);

		}

		return liste == null ? new ArrayList<RandevuListeDTO>() : liste;

	}

	private boolean randevuMuhendisEkle(Connection con, int randevuId,
			ArrayList<Kullanici> muhendisList) throws CreateException {
		boolean eklendi = false;
		PreparedStatement stmt = null;

		try {
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			stmt = con.prepareStatement(Sorgular._RANDEVU_MUHENDIS_INSERT.qry);
			stmt.setInt(1, randevuId);

			for (Kullanici m : muhendisList) {
				stmt.setInt(2, Integer.parseInt(m.getSicilNo()));
				stmt.setString(3, m.getSorumlu());
				stmt.setString(4, loginBean.getKullanici().getKullaniciAdi());
				stmt.addBatch();

			}
			int[] insAdet = stmt.executeBatch();
			eklendi = (insAdet.length > 0 ? true : false);

		} catch (Exception e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		}
		return eklendi;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList<RandevuMuhendisDTO> getRandevuMuhendis(Connection con,
			int randevuId) throws ReadException {
		ArrayList<RandevuMuhendisDTO> liste = new ArrayList<RandevuMuhendisDTO>();
		QueryRunner runner = new QueryRunner();
		try {

			liste = (ArrayList<RandevuMuhendisDTO>) runner.query(con,
					Sorgular._GETRANDEVUMUHENDIS_.qry, new BeanListHandler(
							RandevuMuhendisDTO.class), new Integer(randevuId));
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}

		return liste == null ? new ArrayList<RandevuMuhendisDTO>() : liste;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean isRandevuMuhendis(int randevuId) throws ReadException {
		boolean bool = false;
		List<RandevuMuhendisDTO> liste = new ArrayList<RandevuMuhendisDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			liste = (ArrayList<RandevuMuhendisDTO>) runner.query(con,
					Sorgular._GETRANDEVUMUHENDIS_.qry, new BeanListHandler(
							RandevuMuhendisDTO.class), new Integer(randevuId));
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			if (loginBean.isLoggedIn()) {
				for (RandevuMuhendisDTO r : liste) {
					try {
						if (r.getSicilNo() == Integer.parseInt(loginBean
								.getKullanici().getSicilNo())) {
							bool = true;
							break;
						}
					} catch (Exception e) {
						bool = false;
					}
				}
			}
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		}
		finally{
			DbUtils.closeQuietly(con);
		}

		return bool;
	}

	@Override
	public void randevuGuncelle(Date tarih, int randevuId)
			throws UpdateException {

		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			int adet = runner.update(con, Sorgular._RANDEVU_UPDATE_.qry,
					new Object[] { new java.sql.Date(tarih.getTime()),
							new java.sql.Time(tarih.getTime()),
							loginBean.getKullanici().getKullaniciAdi(),
							randevuId });
			if (adet > 0) {
				con.commit();
			} else {
				throw new UpdateException(
						Messages._RANDEVUZAMANIDEGISTIRILEMEDI_.getMesaj(),
						null);
			}
		} catch (CreateException e) {
			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		}

		finally {
			DbUtils.closeQuietly(con);

		}
	}

	@Override
	public void randevuSil(Randevu r) throws DeleteException {

		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			randevuMuhendisSil(con, r.getRandevuId());
			int adet = runner.update(con, Sorgular._RANDEVU_SIL_.qry,
					new Integer(r.getRandevuId()));
			if (adet > 0) {
				try {
					new BasvuruBusiness().basvuruDurumGuncelle(con, r
							.getBasvuru().getBasvuruId(), "C");

					con.commit();
				} catch (UpdateException e) {
					throw new DeleteException(
							Messages._RANDEVUSILINEMEDI_.getMesaj(), null);
				}

			} else {
				throw new DeleteException(
						Messages._RANDEVUSILINEMEDI_.getMesaj(), null);
			}
		} catch (CreateException e) {
			throw new DeleteException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			if (e.getSQLState().equals("23503")) {
				throw new DeleteException(Messages._RANDEVUSILHATA_.getMesaj(),
						null);
			}
			throw new DeleteException(Messages._SQL_500_.getMesaj(), null);
		}

		finally {
			DbUtils.closeQuietly(con);

		}
	}

	@Override
	public void randevuSil(Connection con2, int basvuruId)
			throws DeleteException, ReadException {

		QueryRunner runner = new QueryRunner();
		try {
			int randevuId = getRandevuIdByBasvuruId(con2, basvuruId);
			if (randevuId > 0) {
				randevuMuhendisSil(con2, randevuId);
				int adet = runner.update(con2, Sorgular._RANDEVU_SIL_.qry,
						new Integer(randevuId));
				if (adet > 0) {

				} else {
					throw new DeleteException(
							Messages._RANDEVUSILINEMEDI_.getMesaj(), null);
				}
			}
		} catch (SQLException e) {
			if (e.getSQLState().equals("23503")) {
				throw new DeleteException(Messages._RANDEVUSILHATA_.getMesaj(),
						null);
			}
			throw new DeleteException(Messages._SQL_500_.getMesaj(), null);
		}

		finally {

		}
	}

	private int getRandevuIdByBasvuruId(Connection con2, int basvuruId)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		int randevuId = 0;
		Randevu r = new Randevu();
		try {
			r = (Randevu) runner.query(con2,
					Sorgular._GETRANDEVUBYBASVURUID_.qry,
					new BeanHandler<Randevu>(Randevu.class), new Integer(
							basvuruId));
			if (r != null) {
				randevuId = r.getRandevuId();
			}
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_501_.getMesaj(), null);
		}
		return randevuId;
	}

	private void randevuMuhendisSil(Connection con2, int randevuId)
			throws DeleteException {
		QueryRunner runner = new QueryRunner();
		try {
			runner.update(con2, Sorgular._RANDEVU_MUHENDIS_DELETE_.qry,
					new Integer(randevuId));
		} catch (Exception e) {
			throw new DeleteException(Messages._SQL_507_.getMesaj(), null);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean muhendisRandevuKontrol(int sicilNo, Date randevuTarih,
			Connection con) throws ReadException {
		boolean sonuc = false;
		ArrayList<RandevuListeDTO> randevular = new ArrayList<RandevuListeDTO>();
		QueryRunner runner = new QueryRunner();
		Calendar calendar = Calendar.getInstance();
		Calendar calendar1 = Calendar.getInstance();
		calendar.setTime(randevuTarih);
		calendar.add(Calendar.HOUR, -1);
		calendar1.setTime(randevuTarih);
		calendar1.add(Calendar.HOUR, 1);
		Date tarih1 = calendar.getTime();
		Date tarih2 = calendar1.getTime();

		try {

			randevular = (ArrayList<RandevuListeDTO>) runner.query(
					con,
					Sorgular._MUHENDISRANDEVUKONTROL_.qry,
					new BeanListHandler(RandevuListeDTO.class),
					new Object[] { sicilNo,
							new java.sql.Date(randevuTarih.getTime()),
							new Time(tarih1.getTime()),
							new Time(tarih2.getTime()) });
			if (randevular != null && randevular.size() > 0) {
				sonuc = true;
			}
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}

		return sonuc;

	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean muhendisRandevuAdetKontrol(Integer sicilNo,
			Date randevuTarih, int kontrolAdet,int basvuruId) throws ReadException {
		boolean sonuc = false;
		ArrayList<RandevuListeDTO> randevular = new ArrayList<RandevuListeDTO>();
		QueryRunner runner = new QueryRunner();

		try {
			List<BasvuruAsansorDTO> basvuruAsansorler=new ArrayList<BasvuruAsansorDTO>();
			basvuruAsansorler=	new BasvuruBusiness().getBasvuruAsansor(basvuruId);
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
		
			randevular = (ArrayList<RandevuListeDTO>) runner.query(
					con,
					Sorgular._MUHENDISRANDEVUADETKONTROL_.qry,
					new BeanListHandler(RandevuListeDTO.class),
					new Object[] { sicilNo,
							new java.sql.Date(randevuTarih.getTime()) });
			double kontrolAdetHesap=0.00;
			int eksiklikAdet=0;
			int normalAdet=0;
			if (randevular!=null &&  randevular.size()>0){
				for (RandevuListeDTO r:randevular){
					kontrolAdetHesap += (r.getKontrolTuru().equals("E")?0.5:1);
					if (r.getKontrolTuru().equals("E"))
						eksiklikAdet++;
					else
						normalAdet++;
				}
				
			}
			
			
			
			int bEksiklikAdet=0;
			int bNormalAdet=0;
			
			if (basvuruAsansorler!=null &&  basvuruAsansorler.size()>0){
				for (BasvuruAsansorDTO b:basvuruAsansorler){
					kontrolAdetHesap += (b.getKontrolTuru().equals("E")?0.5:1);
					if (b.getKontrolTuru().equals("E"))
						bEksiklikAdet++;
					else
						bNormalAdet++;
				}
				
			}
			
			if (kontrolAdetHesap>Double.parseDouble(String.valueOf(kontrolAdet))) {
				throw new ReadException(sicilNo +" sicil numaralı " + 
						Messages._MUHENDISADETKONTROLUYARI_.getMesaj()
								+ kontrolAdet + " kontrol muayenesi yapabilir."
								+ (eksiklikAdet+normalAdet>0?
								  " Randevu tarihinde  mühendisin " 
								+ (eksiklikAdet>0?eksiklikAdet + " adet eksiklik  ":" ")
								+ (normalAdet>0?normalAdet + " adet normal  ":" ") 
								+ " kontrol randevusu bulunmaktadır.":"")
								+ (bEksiklikAdet+bNormalAdet>0?
										  " Randevu verilmek istenen başvuruda  " 
											+ (bEksiklikAdet>0?bEksiklikAdet + " adet eksiklik  ":" ")
											+ (bNormalAdet>0?bNormalAdet + " adet normal  ":" ") 
											+ " kontrolü yapılacak asansör bulunmaktadır.":""),
						new Throwable("uyari"));
			} else {
				sonuc = muhendisRandevuKontrol(sicilNo, randevuTarih, con);
			}
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

		return sonuc;

	}
	
	@Override
	public List<MuhendisCihazYetkiDTO> getMuhendisCihazYetki(Integer sicilNo) throws ReadException {
		
		List<MuhendisCihazYetkiDTO> muhendisYetki = new ArrayList<MuhendisCihazYetkiDTO>();
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			muhendisYetki = (ArrayList<MuhendisCihazYetkiDTO>) runner.query(
					con,
					Sorgular._GETMUHENDISCIHAZYETKI.qry,
					new BeanListHandler<MuhendisCihazYetkiDTO>(MuhendisCihazYetkiDTO.class),
					new Object[] { sicilNo });
			
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

		return muhendisYetki;

	}


	@Override
	public void randevuDurumUpdate(int randevuId, String durum)
			throws UpdateException {
		QueryRunner runner = new QueryRunner();
		try {
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			int i = runner.update(Sorgular._RANDEVU_DURUM_UPDATE_.qry,
					new Object[] { durum,
							loginBean.getKullanici().getKullaniciAdi(),
							randevuId });
			if (i > 0)
				con.commit();

		} catch (Exception e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public List<RandevuListeDTO> getRandevularByBinaId(int binaId)
			throws ReadException {

		List<RandevuListeDTO> list = new ArrayList<RandevuListeDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (List<RandevuListeDTO>) runner
					.query(con, Sorgular._RANDEVUFORBINA_.qry,
							new BeanListHandler<RandevuListeDTO>(
									RandevuListeDTO.class),
							new Object[] { binaId });
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}

		finally {
			DbUtils.closeQuietly(con);

		}

		return list == null ? new ArrayList<RandevuListeDTO>() : list;

	}
	
	@Override
	public List<Istatistik> getRandevuKontrol(int cihazId, int basvuruId) throws ReadException{
		QueryRunner runner = new QueryRunner();
		List<Istatistik> list = new ArrayList<Istatistik>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();
					
				
				list = (List<Istatistik>) runner.query(con, Sorgular._GETRANDEVUKONTROL_.qry,
						new BeanListHandler<Istatistik>(Istatistik.class),
						new Object[] {cihazId,basvuruId});
			

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

	public List<RandevuListeDTO> getEtiketListe(Date etiketRandevuTarihi,
			int muhendisSicilNo) throws ReadException{
		QueryRunner runner= new QueryRunner();
		List<RandevuListeDTO> etiketListe = new ArrayList<RandevuListeDTO>();
		try{
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();
			sb.append(Sorgular._GETETIKETLISTE_.qry);
			etiketListe = (List<RandevuListeDTO>) runner.query(con,sb.toString(),
					      new BeanListHandler<RandevuListeDTO>(RandevuListeDTO.class),
					      new Object[] {muhendisSicilNo,new java.sql.Date(etiketRandevuTarihi.getTime())});
			
		}catch(CreateException e){
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		
		return etiketListe;
	}

}
