package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import btest.*;
import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.business.WebServiceBusiness;
import tr.org.mmo.asansor.dto.AsansorKapsamDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.CihazDTO;
import tr.org.mmo.asansor.dto.CihazTeknikDTO;
import tr.org.mmo.asansor.dto.CurrVal;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.managedbeans.LoginBean;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;

public class AsansorDAOImpl implements AsansorDAO {
	Connection con = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<CihazTeknikDTO> getTeknikBilgiler(int cihazTipi, int cihazId)
			throws ReadException {
		List<CihazTeknikDTO> cihaz = new ArrayList<CihazTeknikDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			List<CihazTeknikDTO> c1=new ArrayList<CihazTeknikDTO>();
			c1 = (ArrayList<CihazTeknikDTO>) runner.query(con,
					Sorgular._CIHAZTEKNIKBILGILER_.qry, new BeanListHandler(
							CihazTeknikDTO.class), new Object[] { cihazId,
							cihazTipi });
			if (c1!=null){
				for (CihazTeknikDTO c:c1){
					if (cihazTipi==66){
					if (c.getKod()!=1606 && c.getKod()!=1610 && c.getKod()!=1621 && c.getKod()!=1656){
						cihaz.add(c);
					}
					}
					if (cihazTipi==68){
						if (c.getKod()!=1624 && c.getKod()!=1626 && c.getKod()!=1634 && c.getKod()!=1658){
							cihaz.add(c);
						}
					}
				}
			}
			
		
			
			
			for (CihazTeknikDTO c : cihaz) {
				c.setBaslik(c.getBaslik()
						+ (c.getBirim().trim().length() > 0 ? "("
								+ c.getBirim().trim().toLowerCase() + ")" : ""));
			}
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}finally{
			DbUtils.closeQuietly(con);
		}
		return cihaz == null ? new ArrayList<CihazTeknikDTO>() : cihaz;
	}
	
	
	@Override
	public void uavtCihazKaydet(CihazDTO cihaz)
			throws UpdateException {

		QueryRunner runner = new QueryRunner();
		try {
		

			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			
				int i = runner.update(con, Sorgular._CIHAZUAVTUPDATE_.qry,
						new Object[] {cihaz.getUavtKod(),cihaz.getUavtEtiket(),cihaz.getUavtSiraNo(),cihaz.getCihazId() });
			if (i>0)	con.commit();
			else {
				throw new UpdateException(Messages._CIHAZUAVTKODUKAYDEDILEMEDI_.getMesaj(), null);
			}
		} catch (CreateException e) {
			throw new UpdateException(Messages._CIHAZUAVTKODUKAYDEDILEMEDI_.getMesaj(), null);
		} catch (SQLException e) {
			throw new UpdateException(Messages._CIHAZUAVTKODUKAYDEDILEMEDI_.getMesaj(), null);
		}
		finally{
			DbUtils.closeQuietly(con);
		}

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void cihazEkle(int asansorAdet, CihazDTO cihaz,int id, int ilKodu,
			int ilceKodu, String ilceAdi, List<CihazTeknikDTO> list,int uavtBinaKod,int asansorSiraNo)
			throws CRUDException,Exception {

		QueryRunner runner = new QueryRunner();
		try {
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			ArrayList<Integer> asansorIdList = new ArrayList<Integer>();
			for (int j = 0; j < asansorAdet; j++) {
				/*
				ServisSonucOfAsansorKimlikKodlar8Zb117HL  servisSonucAsansorKimlikKodlar=new ServisSonucOfAsansorKimlikKodlar8Zb117HL();
						
				servisSonucAsansorKimlikKodlar=		new WebServiceBusiness().binaAsansorKayit(uavtBinaKod, asansorSiraNo+j);
				if (!servisSonucAsansorKimlikKodlar.isHata()){
				cihaz.setUavtKod(servisSonucAsansorKimlikKodlar.getSonuc().getValue().getAsansorNo());
				cihaz.setUavtEtiket(servisSonucAsansorKimlikKodlar.getSonuc().getValue().getAsansorEtiket().getValue());
				cihaz.setUavtSiraNo(servisSonucAsansorKimlikKodlar.getSonuc().getValue().getAsansorSiraNo().getValue());
				}
				*/
				int i = runner.update(con, Sorgular._CIHAZINSERT_.qry,
						new Object[] { cihaz.getTip(),id, ilKodu, ilceKodu, id, ilceAdi,
								loginBean.getKullanici().getKullaniciAdi(),cihaz.getDurum(),cihaz.getUavtKod(),cihaz.getUavtSiraNo(),cihaz.getUavtEtiket() });
				if (i > 0) {

					CurrVal cv = (CurrVal) runner.query(con,
							Sorgular._CURRVALCIHAZ_.qry, new BeanHandler(
									CurrVal.class));
					asansorIdList.add(cv.getCurrval());
				}
			
			}
			cihazDegerEkle(con, asansorIdList, list);
			con.commit();
		} catch (CreateException e) {
			throw new CreateException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			if (e.getMessage().contains("duplicate key value violates unique constraint")){
				throw new CreateException(Messages._SQL_515_.getMesaj(), e);
			}
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		}
		finally{
			DbUtils.closeQuietly(con);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void cihazEkleBasvurudan(CihazDTO cihaz, int id,int ilKodu, int ilceKodu,
			String ilceAdi, List<CihazTeknikDTO> list,int uavtBinaKod,int asansorSiraNo) throws CreateException,Exception {

		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : String
					.valueOf("Başvurudan");
			/*
			ServisSonucOfAsansorKimlikKodlar8Zb117HL  servisSonucAsansorKimlikKodlar=new ServisSonucOfAsansorKimlikKodlar8Zb117HL();
			servisSonucAsansorKimlikKodlar=		new WebServiceBusiness().binaAsansorKayit(uavtBinaKod, asansorSiraNo);
			if (!servisSonucAsansorKimlikKodlar.isHata()){
			cihaz.setUavtKod(servisSonucAsansorKimlikKodlar.getSonuc().getValue().getAsansorNo());
			cihaz.setUavtEtiket(servisSonucAsansorKimlikKodlar.getSonuc().getValue().getAsansorEtiket().getValue());
			cihaz.setUavtSiraNo(servisSonucAsansorKimlikKodlar.getSonuc().getValue().getAsansorSiraNo().getValue());
			}
			*/
			int i = runner.update(con, Sorgular._CIHAZINSERT_.qry,
					new Object[] { cihaz.getTip(), id, ilKodu, ilceKodu, cihaz.getCihazId(), ilceAdi,
							kullanici,"A",cihaz.getUavtKod(),cihaz.getUavtSiraNo(),cihaz.getUavtEtiket()});
			if (i > 0) {

				CurrVal cv = (CurrVal) runner.query(con,
						Sorgular._CURRVALCIHAZ_.qry, new BeanHandler(
								CurrVal.class));
				cihazDegerEkleBasvurudan(con, cv.getCurrval(), list);
				con.commit();
			}
		} catch (CreateException e) {
			throw new CreateException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		}
		finally{
		DbUtils.closeQuietly(con);
		}

	}

	@Override
	public void CihazDegerGuncelle(CihazDTO cihaz, List<CihazTeknikDTO> list,boolean uavtAsansorKayit,int uavtBinaKod)
			throws UpdateException {

		QueryRunner runner = new QueryRunner();
		try {
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : String
					.valueOf("");
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			cihazDurumUpdate(con, cihaz,kullanici,uavtAsansorKayit,uavtBinaKod);
			runner.update(con, Sorgular._CIHAZTEKNIKBILGILERDELETE_.qry,
					new Integer(cihaz.getCihazId()));
			cihazDegerEkle1(con, cihaz.getCihazId(), list,kullanici);
			con.commit();

		} catch (Exception e) {
			if (e.getMessage().contains("duplicate key value violates unique constraint")){
				throw new UpdateException(Messages._SQL_515_.getMesaj(), e);
			}
			throw new UpdateException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}
	}
	
	@Override
	public void CihazDegerGuncelleKontrolde(CihazDTO cihaz, List<CihazTeknikDTO> list)
			throws UpdateException {

		QueryRunner runner = new QueryRunner();
		try {
			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : String
					.valueOf("");
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			cihazDurumUpdate(con, cihaz,kullanici);
			runner.update(con, Sorgular._CIHAZTEKNIKBILGILERDELETE_.qry,
					new Integer(cihaz.getCihazId()));
			cihazDegerEkle1(con, cihaz.getCihazId(), list,kullanici);
			con.commit();

		} catch (Exception e) {
			if (e.getMessage().contains("duplicate key value violates unique constraint")){
				throw new UpdateException(Messages._SQL_515_.getMesaj(), e);
			}
			throw new UpdateException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}
	}
	private void cihazDurumUpdate(Connection con2,CihazDTO cihaz,String kullanici,boolean uavtAsansorKayit,int uavtBinaKod) throws UpdateException{
		QueryRunner runner = new QueryRunner();
		try {
			if (uavtAsansorKayit && cihaz.getUavtKod()>0){
			ServisSonucOfAsansorKimlikKodlar8Zb117HL  servisSonucAsansorKimlikKodlar=new ServisSonucOfAsansorKimlikKodlar8Zb117HL();
			
			servisSonucAsansorKimlikKodlar=		new WebServiceBusiness().binaAsansorKayit(uavtBinaKod, cihaz.getUavtSiraNo());
			if (!servisSonucAsansorKimlikKodlar.isHata()){
			cihaz.setUavtKod(servisSonucAsansorKimlikKodlar.getSonuc().getValue().getAsansorNo());
			cihaz.setUavtEtiket(servisSonucAsansorKimlikKodlar.getSonuc().getValue().getAsansorEtiket().getValue());
			cihaz.setUavtSiraNo(servisSonucAsansorKimlikKodlar.getSonuc().getValue().getAsansorSiraNo().getValue());
			}}
			runner.update(con2, Sorgular._CIHAZDURUMUPDATE_.qry,
					new Object[]{cihaz.getDurum(),kullanici,cihaz.getUavtKod(),cihaz.getUavtSiraNo(),cihaz.getUavtEtiket(),cihaz.getCihazId()});
			
			

		}
		catch (Exception e) {
			if (e.getMessage().contains("duplicate key value violates unique constraint")){
				throw new UpdateException(e.getMessage(), e);
			}
			throw new UpdateException(Messages._SQL_500_.getMesaj(), e);
		} 
		
	}
	
	private void cihazDurumUpdate(Connection con2,CihazDTO cihaz,String kullanici) throws UpdateException{
		QueryRunner runner = new QueryRunner();
		try {
		
			runner.update(con2, Sorgular._CIHAZDURUMUPDATEKONTROLDE_.qry,
					new Object[]{cihaz.getDurum(),kullanici,cihaz.getCihazId()});
			
			

		}
		catch (Exception e) {
			if (e.getMessage().contains("duplicate key value violates unique constraint")){
				throw new UpdateException(e.getMessage(), e);
			}
			throw new UpdateException(Messages._SQL_500_.getMesaj(), e);
		} 
		
	}
	private void cihazDegerEkle(Connection con,
			ArrayList<Integer> asansorIdList, List<CihazTeknikDTO> list)
			throws CreateException {
		PreparedStatement pstmt = null;
		try {

			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			pstmt = con
					.prepareStatement(Sorgular._CIHAZTEKNIKBILGILERINSERT_.qry);
			for (int i : asansorIdList) {
				pstmt.setInt(1, i);
				for (CihazTeknikDTO c : list) {

					pstmt.setInt(2, c.getDegerId());
					pstmt.setString(3, c.getCevap().trim());
					pstmt.setString(4, loginBean.getKullanici()
							.getKullaniciAdi());
					pstmt.addBatch();
				}
				pstmt.executeBatch();
			}

		} catch (Exception e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), e);
		}

	}
	
	private void cihazDegerSil(Connection con2,int cihazId)
			throws DeleteException {
		QueryRunner runner=new QueryRunner();
		try {
			runner.update(con2,Sorgular._DELETECIHAZDEGER_.qry,new Integer(cihazId));
			
		} catch (Exception e) {
			throw new DeleteException(Messages._SQL_500_.getMesaj(), e);
		}

	}


	private void cihazDegerEkleBasvurudan(Connection con, int cihazId,
			List<CihazTeknikDTO> list) throws CreateException {
		PreparedStatement pstmt = null;
		try {

			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : String
					.valueOf("Başvurudan");

			pstmt = con
					.prepareStatement(Sorgular._CIHAZTEKNIKBILGILERINSERT_.qry);
			pstmt.setInt(1, cihazId);
			for (CihazTeknikDTO c : list) {

				pstmt.setInt(2, c.getDegerId());
				pstmt.setString(3, c.getCevap().trim());
				pstmt.setString(4, kullanici);
				pstmt.addBatch();
			}
			pstmt.executeBatch();

		} catch (Exception e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), e);
		}

	}
	
	private void cihazDegerEkle1(Connection con, int cihazId,
			List<CihazTeknikDTO> list,String kullanici) throws CreateException {
		PreparedStatement pstmt = null;
		try {


			pstmt = con
					.prepareStatement(Sorgular._CIHAZTEKNIKBILGILERINSERT_.qry);
			pstmt.setInt(1, cihazId);
			for (CihazTeknikDTO c : list) {

				pstmt.setInt(2, c.getDegerId());
				pstmt.setString(3, c.getCevap().trim());
				pstmt.setString(4, kullanici);
				pstmt.addBatch();
			}
			pstmt.executeBatch();

		} catch (Exception e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), e);
		}

	}


	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CihazDTO> getCihazlar(int binaId) throws ReadException {
		List<CihazDTO> cihazlar = new ArrayList<CihazDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			cihazlar = (ArrayList<CihazDTO>)runner.query(con, Sorgular._GETCIHAZLAR_.qry,
					new BeanListHandler(CihazDTO.class), new Integer(binaId));
			cihazlar = getAsansorunYeri(con, cihazlar);
			cihazlar = getKapsamId(con, cihazlar);

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return cihazlar;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BinaDTO> getCihazlar(List<BinaDTO> list,int firmaId) throws ReadException {
		List<BinaDTO> binaList = new ArrayList<BinaDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			for (BinaDTO b:list){
				BinaDTO bina=new BinaDTO();
				bina = (BinaDTO)runner.query(con, Sorgular._GETBINABYCIHAZFIRMA_.qry,
					new BeanHandler(BinaDTO.class), new Object[] {firmaId,b.getBinaId()});
				if (bina!=null && bina.getBinaId()>0)
				binaList.add(b);
			}

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return binaList;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CihazDTO> getKontrolEdilecekCihazlar(int binaId, int randevuId)
			throws ReadException {
		List<CihazDTO> cihazlar = new ArrayList<CihazDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			cihazlar = (ArrayList<CihazDTO>)runner.query(con, Sorgular._GETCIHAZLARFORKONTROL_.qry,
					new BeanListHandler(CihazDTO.class), new Object[] {
							randevuId, binaId });
			cihazlar = getAsansorunYeri(con, cihazlar);
			cihazlar = getKapsamId(con, cihazlar);

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return cihazlar;
	}

	private List<CihazDTO> getAsansorunYeri(Connection con,
			List<CihazDTO> cihazlar) throws ReadException {
		QueryRunner runner = new QueryRunner();
		try {

			for (CihazDTO c : cihazlar) {

				CihazDTO c1 = new CihazDTO();
				c1 = runner.query(
						con,
						Sorgular._GETASANSORUNYERI_.qry,
						new BeanHandler<CihazDTO>(CihazDTO.class),
						new Object[] { c.getTip() == 66 ? 151 : 171,
								c.getCihazId() });
				c.setAsansorunYeri(c1 != null ? c1.getAsansorunYeri() : "");
			}

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}

		return cihazlar;

	}

	private List<CihazDTO> getKapsamId(Connection con, List<CihazDTO> cihazlar)
			throws ReadException {

		QueryRunner runner = new QueryRunner();
		try {

			for (CihazDTO c : cihazlar) {

				AsansorKapsamDTO c1 = new AsansorKapsamDTO();
				c1 = runner.query(con, Sorgular._GETASANSORKAPSAMID_.qry,
						new BeanHandler<AsansorKapsamDTO>(
								AsansorKapsamDTO.class),
						new Object[] { c.getCihazId(),
								c.getTip() == 66 ? 166 : 178, c.getTip() });
				c.setKapsamId(c1 != null ? c1.getId() : 0);
			}

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}

		return cihazlar;

	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CihazTeknikDTO> getCihaz(int cihazId, int cihazTuru)
			throws ReadException {
		List<CihazTeknikDTO> cihaz = new ArrayList<CihazTeknikDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			List<CihazTeknikDTO> c1 = new ArrayList<CihazTeknikDTO>();
			c1 = (ArrayList<CihazTeknikDTO>)runner.query(con, Sorgular._GETCIHAZ_.qry,
					new BeanListHandler(CihazTeknikDTO.class), new Object[] {
							cihazId, cihazTuru });
			
			
			
			if (c1!=null){
				for (CihazTeknikDTO c:c1){
					if (cihazTuru==66){
					if (c.getKod()!=1606 && c.getKod()!=1610 && c.getKod()!=1621 && c.getKod()!=1656){
						cihaz.add(c);
					}
					}
					if (cihazTuru==68){
						if (c.getKod()!=1624 && c.getKod()!=1626 && c.getKod()!=1634 && c.getKod()!=1658){
							cihaz.add(c);
						}
					}
				}
			}

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return cihaz;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CihazTeknikDTO> getCihaz(Connection con, int cihazId,
			int cihazTuru) throws ReadException {
		List<CihazTeknikDTO> cihaz = new ArrayList<CihazTeknikDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			List<CihazTeknikDTO> c1 = new ArrayList<CihazTeknikDTO>();
			c1 = (ArrayList<CihazTeknikDTO>)runner.query(con, Sorgular._GETCIHAZ_.qry,
					new BeanListHandler(CihazTeknikDTO.class), new Object[] {
							cihazId, cihazTuru });
			if (c1!=null){
				for (CihazTeknikDTO c:c1){
					if (cihazTuru==66){
					if (c.getKod()!=1606 && c.getKod()!=1610 && c.getKod()!=1621 && c.getKod()!=1656){
						cihaz.add(c);
					}
					}
					if (cihazTuru==68){
						if (c.getKod()!=1624 && c.getKod()!=1626 && c.getKod()!=1634 && c.getKod()!=1658){
							cihaz.add(c);
						}
					}
				}
			}

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}

		return cihaz;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CihazDTO getCihaz(Connection con, int cihazId) throws ReadException {
		CihazDTO cihaz = new CihazDTO();
		QueryRunner runner = new QueryRunner();
		try {

			cihaz = (CihazDTO) runner.query(con,
					Sorgular._GETCIHAZ_BY_CIHAZID_.qry, new BeanHandler(
							CihazDTO.class), new Integer(cihazId));

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}

		return cihaz;
	}

	@Override
	public double getCihazFiyat(Connection con, int cihazTipi, int belediye,
			int kapasiteDurak, int kapasiteAgirlik, int sozlesmeBinaTipId,String kontrolTipi)
			throws ReadException {
		double fiyat = 0.00;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if (kapasiteAgirlik>0 || kapasiteDurak>0){
		try {
			pstmt = con.prepareStatement(Sorgular._GETFIYAT_FOR_CIHAZ_.qry);
			pstmt.setInt(1, belediye);
			pstmt.setInt(2, cihazTipi);
			
			pstmt.setInt(3, kapasiteDurak);
			pstmt.setInt(4, kapasiteAgirlik);
			pstmt.setInt(5, sozlesmeBinaTipId);
			pstmt.setString(6, kontrolTipi);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				fiyat = rs.getDouble("FIYAT");
			}

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}
		}
		return fiyat;

	}
	@Override
	public double getCihazFiyat(int cihazTipi, int belediye,
			int kapasiteDurak, int kapasiteAgirlik, int sozlesmeBinaTipId,String kontrolTipi)
			throws CRUDException {
		double fiyat = 0.00;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if (kapasiteAgirlik>0 || kapasiteDurak>0){
		try {
			con=DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(Sorgular._GETFIYAT_FOR_CIHAZ_.qry);
			pstmt.setInt(1, belediye);
			pstmt.setInt(2, cihazTipi);

			pstmt.setInt(3, kapasiteDurak);
			pstmt.setInt(4, kapasiteAgirlik);
			pstmt.setInt(5, sozlesmeBinaTipId);
			pstmt.setString(6, kontrolTipi);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				fiyat = rs.getDouble("FIYAT");
			}

		} catch (SQLException e) {
			throw new CRUDException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new CRUDException(Messages._CONNECTIONAC_.getMesaj(), null);
		}finally{
			DbUtils.closeQuietly(con);
		}
		}

		return fiyat;

	}
	@Override
	public List<CihazTeknikDTO> getCihazKapasite(int cihazId)
			throws ReadException {
		List<CihazTeknikDTO> cihaz = new ArrayList<CihazTeknikDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			cihaz = (List<CihazTeknikDTO>) runner.query(con,
					Sorgular._GETCIHAZKAPASITE_.qry,
					new BeanListHandler<CihazTeknikDTO>(CihazTeknikDTO.class),
					new Object[] { cihazId });

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return cihaz;

	}

	public List<AsansorKapsamDTO> getKapsamTurleri() throws ReadException {
		List<AsansorKapsamDTO> list = new ArrayList<AsansorKapsamDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (List<AsansorKapsamDTO>) runner.query(con,
					Sorgular._GETKAPSAMTURLERI.qry,
					new BeanListHandler<AsansorKapsamDTO>(
							AsansorKapsamDTO.class));

		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<AsansorKapsamDTO>() : list;
	}
	
	@Override
	public void cihazSil(int cihazId) throws DeleteException {
		QueryRunner runner = new QueryRunner();
		try {
		
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			cihazDegerSil(con, cihazId);
				int i = runner.update(con, Sorgular._DELETECIHAZ_.qry,
						new Object[] { cihazId });
				if (i > 0) {
					con.commit();
				
				}else{
					con.rollback();
			
			}
			
			
		} catch (CreateException e) {
			throw new DeleteException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new DeleteException(Messages._SQL_500_.getMesaj(), null);
		}
		finally{
			DbUtils.closeQuietly(con);
			}
		
	}
	
	@Override
	public int getKapsamId(CihazDTO  cihaz)
			throws ReadException {

		QueryRunner runner = new QueryRunner();
		int kapsamId;
		try {

			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			
			AsansorKapsamDTO a =new AsansorKapsamDTO();
				a= runner.query(con, Sorgular._GETASANSORKAPSAMID_.qry,
						new BeanHandler<AsansorKapsamDTO>(
								AsansorKapsamDTO.class),
						new Object[] { cihaz.getCihazId(),
								cihaz.getTip() == 66 ? 166 : 178, cihaz.getTip() });
				kapsamId=a!=null?a.getId():0;
			
		}catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}
		finally {
			DbUtils.closeQuietly(con);
		}
		return kapsamId;

	}
	
	


}
