package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.dto.BakimciFirmaIletisimDTO;
import tr.org.mmo.asansor.dto.BakimciFirmaKapsamDTO;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.CihazDTO;
import tr.org.mmo.asansor.dto.CihazFirmaDTO;
import tr.org.mmo.asansor.dto.CurrVal;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.managedbeans.LoginBean;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;

public class FirmaDAOImpl implements FirmaDAO {
	private Connection con = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<FirmaDTO> firmaListesi() throws ReadException {

		QueryRunner runner = new QueryRunner();
		List<FirmaDTO> list = new ArrayList<FirmaDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);

			StringBuffer sb = new StringBuffer();

			sb.append(Sorgular._FIRMALIST_.qry);

			sb.append(((LoginBean) Common.findBean("loginBean"))
					.getIlSorguKosul());
			sb.append(")");
			list = (List<FirmaDTO>) runner.query(con, sb.toString(),
					new BeanListHandler(FirmaDTO.class));
			for (FirmaDTO firma:list){
				DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				if (firma.getSozlesmeTarihi()!=null && df.format(firma.getSozlesmeTarihi()).equals("0001-01-01")){
					firma.setSozlesme_Tarihi(null);
					firma.setSozlesmeTarihi(null);
				}
				if (firma.getGecerlilikSuresi()!=null && df.format(firma.getGecerlilikSuresi()).equals("0001-01-01")){
					firma.setGecerlilik_Suresi(null);
					firma.setGecerlilikSuresi(null);
				}
				firma.setFirmaIller(getFirmaKapsam(con,firma.getKod()));
			}
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list == null ? new ArrayList<FirmaDTO>() : list;

	}

	public List<FirmaDTO> firmaListesi(int ilKodu) throws ReadException {

		QueryRunner runner = new QueryRunner();
		List<FirmaDTO> list = new ArrayList<FirmaDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);

			list = (List<FirmaDTO>) runner.query(con,
					Sorgular._FIRMALISTBASVURU_.qry,
					new BeanListHandler<FirmaDTO>(FirmaDTO.class), new Integer(
							ilKodu));
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list == null ? new ArrayList<FirmaDTO>() : list;

	}

	@Override
	public boolean firmaVarMi(int kod) throws ReadException {
		try {
			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			if (getFirma(kod).getKod() > -1) {
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

	private int firmaInsert(FirmaDTO firma, Connection con)
			throws CreateException {

		QueryRunner runner = new QueryRunner();
		int i = 0;
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		try {

			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			i = runner.update(
					con,
					Sorgular._FIRMAINSERT_.qry,
					new Object[] { firma.getUnvan(), firma.getAdSoyad(),
							firma.getGsmTelefon(), firma.getIl(),
							firma.getIlce(), firma.getAdres(),
							firma.getDurumu(), firma.getTescilNo(),
							firma.getUygunlukBelgesi(),
							new Date(firma.getGecerlilikSuresi()==null?df.parse("0001-01-01").getTime():firma.getGecerlilikSuresi().getTime()),
							firma.getServisSozlesme(),
							new Date(firma.getSozlesmeTarihi()==null?df.parse("0001-01-01").getTime():firma.getSozlesmeTarihi().getTime()),
							firma.getTseBelgeNo(), firma.getEposta(),
							firma.getMonteEden(), firma.getYetkiliServis(),
							firma.getTseBelgesi(), firma.getTelefonNo(),
							firma.getDahili(), firma.getCeBelgeTipi(),
							
							kullanici,firma.getFaks() });
			if (i > 0) {
				CurrVal cv = (CurrVal) runner.query(con,
						Sorgular._CURRVALFIRMA_.qry, new BeanHandler<CurrVal>(
								CurrVal.class));
				i = cv.getCurrval();
			}

		}

		catch (SQLException e) {

			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} catch (NullPointerException e) {
			throw new CreateException(Messages._NULLPOINTER_.getMesaj(), null);
		}
		catch (Exception e) {
			throw new CreateException(Messages._SQL001_.getMesaj(), null);
		}
		return i;

	}

	@Override
	public int firmaKaydet(FirmaDTO firma) throws UpdateException {

		QueryRunner runner = new QueryRunner();
		int firmaId = 0;
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";
			
			int i = runner.update(
					con,
					Sorgular._FIRMAUPDATE_.qry,
					new Object[] { firma.getUnvan(), firma.getAdSoyad(),
							firma.getGsmTelefon(), firma.getIl(),
							firma.getIlce(), firma.getAdres(),
							firma.getDurumu(), firma.getTescilNo(),
							firma.getUygunlukBelgesi(),
							new Date( firma.getGecerlilikSuresi()==null?df.parse("0001-01-01").getTime():firma.getGecerlilikSuresi().getTime()),
							firma.getServisSozlesme(),
							new Date(firma.getSozlesmeTarihi()==null?df.parse("0001-01-01").getTime():firma.getSozlesmeTarihi().getTime()),
							firma.getTseBelgeNo(), firma.getEposta(),
							firma.getMonteEden(), firma.getYetkiliServis(),
							firma.getTseBelgesi(), firma.getTelefonNo(),
							firma.getDahili(), firma.getCeBelgeTipi(),
							kullanici,firma.getFaks(), firma.getKod() });
			
			firmaId = (i <= 0 ? firmaInsert(firma, con) : firma.getKod());
			if (firmaId > 0) {
				firma.setKod(firmaId);
				firmaKapsamInsert(con,firma);
				con.commit();
			}
		} catch (CreateException e) {
			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		} catch (NullPointerException e) {
			throw new UpdateException(Messages._NULLPOINTER_.getMesaj(), null);
		}catch (Exception e) {
			throw new UpdateException(Messages._SQL001_.getMesaj(), null);
		}finally {
			DbUtils.closeQuietly(con);
		}
		return firmaId;

	}

	private void firmaKapsamInsert(Connection con2,FirmaDTO firma) throws SQLException{
		PreparedStatement pstmt=null;
		QueryRunner runner=new QueryRunner();
		try {
			runner.update(con2,Sorgular._FIRMAKAPSAMDELETE_.qry,new Integer(firma.getKod()));
			firma.setFirmaIller(firma.getFirmaIller()==null?new ArrayList<Integer>():firma.getFirmaIller());
			if (firma.getFirmaIller().size()<=0){
				firma.getFirmaIller().add(firma.getIl());
			}
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";
			pstmt=con2.prepareStatement(Sorgular._FIRMAKAPSAMINSERT_.qry);
		
			pstmt.setInt(1, firma.getKod());
			pstmt.setString(3, kullanici);
			for (int i=0;i<firma.getFirmaIller().size();i++){
				pstmt.setInt(2,Integer.parseInt(String.valueOf(firma.getFirmaIller().get(i))));
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			
		} catch (Exception e) {
			throw new SQLException(Messages._SQL_500_.getMesaj());
		} 
		
		
	}
	
	private List<Integer> getFirmaKapsam(Connection con2,int kod) throws ReadException{
		QueryRunner runner=new QueryRunner();
		List<Integer> list=new ArrayList<Integer>();
		List<BakimciFirmaKapsamDTO> list1=new ArrayList<BakimciFirmaKapsamDTO>();
		try {
			
			list1=(List<BakimciFirmaKapsamDTO>)runner.query(con2, Sorgular._GETBAKIMCIFIRMAKAPSAM_.qry,new BeanListHandler<BakimciFirmaKapsamDTO>(BakimciFirmaKapsamDTO.class), new Integer(kod));
			list1=(list1==null?new ArrayList<BakimciFirmaKapsamDTO>():list1);
			for (BakimciFirmaKapsamDTO b:list1){
				list.add(b.getIl());
			}
			
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(),null);
		} 
		
		return list==null?new ArrayList<Integer>():list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public FirmaDTO getFirma(int kod) throws ReadException {

		QueryRunner runner = new QueryRunner();
		FirmaDTO f = new FirmaDTO();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			f = (FirmaDTO) runner.query(con, Sorgular._FIRMABYKOD_.qry,
					new BeanHandler(FirmaDTO.class), new Object[] { kod });
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return f == null ? new FirmaDTO() : f;

	}

	@Override
	public FirmaDTO firmaDetay(Connection con, int firmaId)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		FirmaDTO firma = new FirmaDTO();
		try {

			firma = (FirmaDTO) runner.query(con, Sorgular._FIRMADETAY.qry,
					new BeanHandler<FirmaDTO>(FirmaDTO.class), new Integer(
							firmaId));
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}

		return firma;
	}

	@Override
	public void firmaIletisimKaydet(BakimciFirmaIletisimDTO firmaIletisim,
			int firmaId) throws CreateException {
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			runner.update(
					con,
					Sorgular._FIRMAILETISIMKAYDET_.qry,
					new Object[] { firmaId, firmaIletisim.getAdi(),
							firmaIletisim.getSoyadi(),
							firmaIletisim.getTelefonNo(),
							firmaIletisim.getTelefonNoGsm(),
							firmaIletisim.getePosta(), kullanici });
			con.commit();

		} catch (Exception e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public void firmaIletisimSil(BakimciFirmaIletisimDTO iletisim)
			throws DeleteException {
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			runner.update(con, Sorgular._FIRMAILETISIMSIL_.qry,
					new Object[] { iletisim.getId() });
			con.commit();

		} catch (Exception e) {
			throw new DeleteException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public List<BakimciFirmaIletisimDTO> firmaIletisimBilgisiGetir(int kod)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<BakimciFirmaIletisimDTO> list = new ArrayList<BakimciFirmaIletisimDTO>();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = runner.query(con, Sorgular._FIRMAILETISIMGETIR_.qry,
					new BeanListHandler<BakimciFirmaIletisimDTO>(
							BakimciFirmaIletisimDTO.class),
					new Object[] { kod });

		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list;
	}

	@Override
	public List<BasvuruAsansorDTO> asansorBakimciFirmasi(
			List<BasvuruAsansorDTO> asansorList,List<BasvuruAsansorDTO> basvuruAsansorler, int basvuruId) throws ReadException {
		QueryRunner runner = new QueryRunner();

		try {

			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			for (BasvuruAsansorDTO b : asansorList) {
				FirmaDTO firma = new FirmaDTO();
				boolean var=false;
				for (BasvuruAsansorDTO a:basvuruAsansorler){
					if (b.getCihazId()==a.getCihazId()){

						
						firma = (FirmaDTO) runner.query(con,
								Sorgular._BASVURUCIHAZBAKIMCIFIRMASI_.qry,
								new BeanHandler<FirmaDTO>(FirmaDTO.class),
								new Object[] { basvuruId, b.getCihazId() }) ;
						var =true;
						break;

					}
				}
				
				if (!var){
				firma = (FirmaDTO) runner
						.query(con, Sorgular._CIHAZBAKIMCIFIRMASI_.qry,
								new BeanHandler<FirmaDTO>(FirmaDTO.class),
								new Object[] { b.getCihazId() });
				}
				
				if (firma != null) {
					b.setBakimciFirmaAdi(firma.getUnvan());
					b.setBakimciFirmaId(firma.getKod());
				} else {
					b.setBakimciFirmaAdi("");
					b.setBakimciFirmaId(0);
				}
				

			}

		
		

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

		return asansorList;
	}



	@Override
	public List<FirmaDTO> asansorBakimciFirmalar(Connection con2, int basvuruId)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<FirmaDTO> firma = new ArrayList<FirmaDTO>();
		try {

			firma = (List<FirmaDTO>) runner.query(con2,
					Sorgular._BASVURUBAKIMCIFIRMALAR_.qry,
					new BeanListHandler<FirmaDTO>(FirmaDTO.class),
					new Object[] { basvuruId });

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (NullPointerException e) {
			firma = new ArrayList<FirmaDTO>();
		}

		return firma == null ? new ArrayList<FirmaDTO>() : firma;
	}

	@Override
	public HashMap<Integer, FirmaDTO> basvuruBakimciFirmalar(Connection con2,
			int basvuruId) throws ReadException {
		QueryRunner runner = new QueryRunner();
		HashMap<Integer, FirmaDTO> firmaMap = new HashMap<Integer, FirmaDTO>();
		try {

			List<FirmaDTO> firma = new ArrayList<FirmaDTO>();

			firma = (List<FirmaDTO>) runner.query(con2,
					Sorgular._BASVURUBAKIMCIFIRMALAR_.qry,
					new BeanListHandler<FirmaDTO>(FirmaDTO.class),
					new Object[] { basvuruId });

			for (FirmaDTO f : firma) {
				firmaMap.put(f.getKod(), f);
			}

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}

		return firmaMap;
	}

	@Override
	public List<FirmaDTO> asansorBakimciFirmalar(int cihazId)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<FirmaDTO> firma = new ArrayList<FirmaDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			firma = (List<FirmaDTO>) runner.query(con,
					Sorgular._CIHAZBAKIMCIFIRMALAR_.qry,
					new BeanListHandler<FirmaDTO>(FirmaDTO.class),
					new Object[] { cihazId });

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return firma;
	}
	
	

	@Override
	public FirmaDTO getCihazKontrolFirma(Connection con2, int randevuId,
			int cihazId) throws ReadException {

		QueryRunner runner = new QueryRunner();
		FirmaDTO firma = new FirmaDTO();
		try {

			firma = (FirmaDTO) runner.query(con2,
					Sorgular._CIHAZKONTROLFIRMASI_.qry,
					new BeanHandler<FirmaDTO>(FirmaDTO.class), new Object[] {
							cihazId, randevuId });

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}

		return firma;

	}
	
	@Override
	public FirmaDTO getCihazKontrolFirma( int randevuId,
			int cihazId) throws ReadException {

		QueryRunner runner = new QueryRunner();
		FirmaDTO firma = new FirmaDTO();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			firma = (FirmaDTO) runner.query(con,
					Sorgular._CIHAZKONTROLFIRMASI_.qry,
					new BeanHandler<FirmaDTO>(FirmaDTO.class), new Object[] {
							cihazId, randevuId });

		}catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}finally{
			DbUtils.closeQuietly(con);
		}

		return firma;

	}


	@Override
	public List<BasvuruAsansorDTO> getBasvuruKontrolFirma(Connection con2,
			int basvuruId) throws ReadException {

		QueryRunner runner = new QueryRunner();
		List<BasvuruAsansorDTO> list = new ArrayList<BasvuruAsansorDTO>();
		try {

			list = (List<BasvuruAsansorDTO>) runner.query(con2,
					Sorgular._BASVURUKONTROLFIRMASI_.qry,
					new BeanListHandler<BasvuruAsansorDTO>(
							BasvuruAsansorDTO.class),
					new Object[] { basvuruId });

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}

		return list;

	}

	@Override
	public List<FirmaDTO> getBasvuruKontrolFirma(int basvuruId)
			throws ReadException {

		QueryRunner runner = new QueryRunner();
		List<FirmaDTO> list = new ArrayList<FirmaDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (List<FirmaDTO>) runner.query(con,
					Sorgular._BASVURUBAKIMCIFIRMALAR_.qry,
					new BeanListHandler<FirmaDTO>(FirmaDTO.class),
					new Object[] { basvuruId });

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}

		return list;

	}
	
	@Override
	public void setAsansorBakimliFirma(CihazFirmaDTO cihazFirmaDTO) throws CreateException{
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";

			runner.update(
					con,
					Sorgular._FIRMACIHAZKAYDET_.qry,
					new Object[] { cihazFirmaDTO.getCihazId(),cihazFirmaDTO.getFirmaId(),new Date(cihazFirmaDTO.getSozlesmeTarih().getTime()), new Date(cihazFirmaDTO.getSozlesmeBitisTarih().getTime()),kullanici });
			con.commit();

		} catch (Exception e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	
	@Override
	public void setAsansorBakimliFirma(CihazFirmaDTO cihazFirmaDTO,List<CihazDTO> cihazList) throws CreateException{
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";
			for (CihazDTO c:cihazList){
			runner.update(
					con,
					Sorgular._FIRMACIHAZKAYDET_.qry,
					new Object[] { c.getCihazId(),cihazFirmaDTO.getFirmaId(),new Date(cihazFirmaDTO.getSozlesmeTarih().getTime()),new Date(cihazFirmaDTO.getSozlesmeBitisTarih().getTime()), kullanici });
			}
			con.commit();

		} catch (Exception e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	@Override
	public void setAsansorBakimciFirma(CihazFirmaDTO cihazFirmaDTO,BasvuruAsansorDTO[] cihazList) throws CreateException{
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : "";
			for (BasvuruAsansorDTO c:cihazList){
			runner.update(
					con,
					Sorgular._FIRMACIHAZKAYDET_.qry,
					new Object[] { c.getCihazId(),cihazFirmaDTO.getFirmaId(),new Date(cihazFirmaDTO.getSozlesmeTarih().getTime()),new Date(cihazFirmaDTO.getSozlesmeBitisTarih().getTime()), kullanici });
			}
			con.commit();

		} catch (Exception e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	@Override
	public CihazFirmaDTO getCihazAnlasmaliFirma(int firmaId,int cihazId) throws ReadException{
		CihazFirmaDTO cihazFirma=new CihazFirmaDTO();
		QueryRunner runner = new QueryRunner();
		
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			cihazFirma = (CihazFirmaDTO) runner.query(con,
					Sorgular._GETCIHAZANLASMALIFIRMA_.qry,
					new BeanHandler<CihazFirmaDTO>(CihazFirmaDTO.class),
					new Object[] { cihazId,firmaId });

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return cihazFirma==null?new CihazFirmaDTO():cihazFirma;
	}
	
	@Override
	public void firmaSil(int kod) throws DeleteException{
		QueryRunner runner = new QueryRunner();
		
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			int i=runner.update(con, Sorgular._FIRMASIL_.qry, new Integer(kod));
			if (i>0){
				con.commit();
			}else{
				con.rollback();
				throw new DeleteException(Messages._FIRMASILINMEDI_.getMesaj(), null);
			}
		} catch (CreateException e) {
			throw new DeleteException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new DeleteException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		
	}
	
	@Override
	public java.util.Date getCihazAnlasmaliFirmaTarih(int cihazId, int kod) throws ReadException{
		CihazFirmaDTO cihazFirma=new CihazFirmaDTO();
		QueryRunner runner = new QueryRunner();
		
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			cihazFirma = (CihazFirmaDTO) runner.query(con,
					Sorgular._GETCIHAZANLASMALIFIRMATARIH_.qry,
					new BeanHandler<CihazFirmaDTO>(CihazFirmaDTO.class),
					new Object[] { cihazId,kod });

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return cihazFirma==null?null:cihazFirma.getSozlesmeTarih();
	}
}
