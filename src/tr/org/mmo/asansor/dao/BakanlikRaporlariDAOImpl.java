package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.dto.KirmiziEtiketKontrolDTO;
import tr.org.mmo.asansor.dto.OdemeKontrolOtuzGunDTO;
import tr.org.mmo.asansor.dto.RaporTeslimKontrolDTO;
import tr.org.mmo.asansor.dto.SariEtiketKontrolDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.managedbeans.LoginBean;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;


public class BakanlikRaporlariDAOImpl implements BakanlikRaporlariDAO{
	Connection con = null;
	String iller="";
	String ilceler="";
	
	public List<OdemeKontrolOtuzGunDTO> getBinaOdemelerOtuzGun(Date tarih1,
			Date tarih2) throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<OdemeKontrolOtuzGunDTO> listOdemelerOtuzGun = new ArrayList<OdemeKontrolOtuzGunDTO>();
		
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sbOdemeler=new StringBuffer(); 
			sbOdemeler.append(Sorgular._GETODEMELEROTUZGUN_.qry);
			sbOdemeler.append(" and ");
			sbOdemeler.append(((LoginBean) Common.findBean("loginBean")).getKullaniciBinaKapsam());
		    sbOdemeler.append(" group by bel.kod,bel.adi,o.toplam_tutar,o.odeme_tutari,a.bina_id,b.basvuru_id,bk.adi,bk.soyadi,bk.telefon_no,bk.telefon_no_gsm ");
			
		    listOdemelerOtuzGun = (List<OdemeKontrolOtuzGunDTO>) runner.query(con, sbOdemeler.toString(),	new BeanListHandler<OdemeKontrolOtuzGunDTO>(OdemeKontrolOtuzGunDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime())});
		    
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return listOdemelerOtuzGun == null ? new ArrayList<OdemeKontrolOtuzGunDTO>() : listOdemelerOtuzGun;
	}

	public List<KirmiziEtiketKontrolDTO> getKirmiziEtiketKontrol(Date tarih1,
			Date tarih2) throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<KirmiziEtiketKontrolDTO> listKirmiziEtiketKontrol = new ArrayList<KirmiziEtiketKontrolDTO>();
		
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sbKirmiziEtiket=new StringBuffer(); 
			sbKirmiziEtiket.append(Sorgular._GETKIRMIZIETIKETKONTROL_.qry);
			sbKirmiziEtiket.append(" and ");
			sbKirmiziEtiket.append(((LoginBean) Common.findBean("loginBean")).getKullaniciBinaKapsam());
			
		    listKirmiziEtiketKontrol = (List<KirmiziEtiketKontrolDTO>) runner.query(con, sbKirmiziEtiket.toString(),	new BeanListHandler<KirmiziEtiketKontrolDTO>(KirmiziEtiketKontrolDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime())});
		    
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return listKirmiziEtiketKontrol == null ? new ArrayList<KirmiziEtiketKontrolDTO>() : listKirmiziEtiketKontrol;
	}

	public List<SariEtiketKontrolDTO> getSariEtiketKontrol(Date tarih1,
			Date tarih2) throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<SariEtiketKontrolDTO> listSariEtiketKontrol = new ArrayList<SariEtiketKontrolDTO>();
		
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sbSariEtiket=new StringBuffer(); 
			sbSariEtiket.append(Sorgular._GETSARIETIKETKONTROL_.qry);
			sbSariEtiket.append(" and ");
			sbSariEtiket.append(((LoginBean) Common.findBean("loginBean")).getKullaniciBinaKapsam());
			
			listSariEtiketKontrol = (List<SariEtiketKontrolDTO>) runner.query(con, sbSariEtiket.toString(),	new BeanListHandler<SariEtiketKontrolDTO>(SariEtiketKontrolDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime())});
		    
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return listSariEtiketKontrol == null ? new ArrayList<SariEtiketKontrolDTO>() : listSariEtiketKontrol;
	}

	public List<RaporTeslimKontrolDTO> getRaporTeslimKontrol(Date tarih1,
			Date tarih2) throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<RaporTeslimKontrolDTO> listRaporTeslimKontrol = new ArrayList<RaporTeslimKontrolDTO>();
		
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sbRaporTeslim=new StringBuffer(); 
			sbRaporTeslim.append(Sorgular._GETRAPORTESLIMKONTROL_.qry);
			sbRaporTeslim.append(" and ");
			sbRaporTeslim.append(((LoginBean) Common.findBean("loginBean")).getKullaniciBinaKapsam());
			
			listRaporTeslimKontrol = (List<RaporTeslimKontrolDTO>) runner.query(con, sbRaporTeslim.toString(),	new BeanListHandler<RaporTeslimKontrolDTO>(RaporTeslimKontrolDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime())});
		    
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return listRaporTeslimKontrol == null ? new ArrayList<RaporTeslimKontrolDTO>() : listRaporTeslimKontrol;
	}

	public void insertKirmiziRaporGonderBelediye(int raporId, int belediyeKod, String belediyeAdi,String etiket,
			int kontrolId,int randevuId,int basvuruId,int binaId) throws CreateException {
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			
			KirmiziEtiketKontrolDTO r = new KirmiziEtiketKontrolDTO();
			r = (KirmiziEtiketKontrolDTO) runner.query(con, Sorgular._BELEDIYERAPORGONDERKONTROL_.qry,
					new BeanHandler<KirmiziEtiketKontrolDTO>(KirmiziEtiketKontrolDTO.class),
					new Object[] { raporId });
			if(r == null || r.getRaporId() != raporId){
			runner.update(con,Sorgular._BELEDIYERAPORGONDERINSERT_.qry,
					new Object[] { raporId,belediyeKod,belediyeAdi,kontrolId,randevuId,basvuruId,binaId,etiket });
			}
			
			con.commit();
		} catch (Exception e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		
	}

	public List<KirmiziEtiketKontrolDTO> getBelediyeGonderilenlerKirmiziEtiketKontrol(
			Date tarih1, Date tarih2) throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<KirmiziEtiketKontrolDTO> listBelediyeGonderilenlerKirmiziEtiketKontrol = new ArrayList<KirmiziEtiketKontrolDTO>();
		
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sbBelediyeGonderilen=new StringBuffer(); 
			sbBelediyeGonderilen.append(Sorgular._GETBELEDIYEGONDERILENLERKIRMIZIKONTROL_.qry);
			sbBelediyeGonderilen.append(" and ");
			sbBelediyeGonderilen.append(((LoginBean) Common.findBean("loginBean")).getKullaniciBinaKapsam());
			
		    listBelediyeGonderilenlerKirmiziEtiketKontrol = (List<KirmiziEtiketKontrolDTO>) runner.query(con, sbBelediyeGonderilen.toString(),	new BeanListHandler<KirmiziEtiketKontrolDTO>(KirmiziEtiketKontrolDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime())});
		    
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return listBelediyeGonderilenlerKirmiziEtiketKontrol == null ? new ArrayList<KirmiziEtiketKontrolDTO>() : listBelediyeGonderilenlerKirmiziEtiketKontrol;
	}

	public List<KirmiziEtiketKontrolDTO> getBelediyeGonderilmeyenlerKirmiziEtiketKontrol(
			Date tarih1, Date tarih2) throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<KirmiziEtiketKontrolDTO> listBelediyeGonderilmeyenlerKirmiziEtiketKontrol = new ArrayList<KirmiziEtiketKontrolDTO>();
		
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sbBelediyeGonderilmeyen=new StringBuffer(); 
			sbBelediyeGonderilmeyen.append(Sorgular._GETBELEDIYEGONDERILMEYENLERKIRMIZIKONTROL_.qry);
			sbBelediyeGonderilmeyen.append(" and ");
			sbBelediyeGonderilmeyen.append(((LoginBean) Common.findBean("loginBean")).getKullaniciBinaKapsam());
			
			listBelediyeGonderilmeyenlerKirmiziEtiketKontrol = (List<KirmiziEtiketKontrolDTO>) runner.query(con, sbBelediyeGonderilmeyen.toString(),	new BeanListHandler<KirmiziEtiketKontrolDTO>(KirmiziEtiketKontrolDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime())});
		    
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return listBelediyeGonderilmeyenlerKirmiziEtiketKontrol == null ? new ArrayList<KirmiziEtiketKontrolDTO>() : listBelediyeGonderilmeyenlerKirmiziEtiketKontrol;
	}

	public List<SariEtiketKontrolDTO> getBelediyeGonderilmeyenlerSariEtiketKontrol(
			Date tarih1, Date tarih2) throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<SariEtiketKontrolDTO> listBelediyeGonderilmeyenlerSariEtiketKontrol = new ArrayList<SariEtiketKontrolDTO>();
		
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sbBelediyeGonderilmeyen=new StringBuffer(); 
			sbBelediyeGonderilmeyen.append(Sorgular._GETBELEDIYEGONDERILMEYENLERSARIKONTROL_.qry);
			sbBelediyeGonderilmeyen.append(" and ");
			sbBelediyeGonderilmeyen.append(((LoginBean) Common.findBean("loginBean")).getKullaniciBinaKapsam());
			
			listBelediyeGonderilmeyenlerSariEtiketKontrol = (List<SariEtiketKontrolDTO>) runner.query(con, sbBelediyeGonderilmeyen.toString(),	
					new BeanListHandler<SariEtiketKontrolDTO>(SariEtiketKontrolDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime())});
		    
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return listBelediyeGonderilmeyenlerSariEtiketKontrol == null ? new ArrayList<SariEtiketKontrolDTO>() : listBelediyeGonderilmeyenlerSariEtiketKontrol;
	}

	public List<SariEtiketKontrolDTO> getBelediyeGonderilenlerSariEtiketKontrol(
			Date tarih1, Date tarih2) throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<SariEtiketKontrolDTO> listBelediyeGonderilenlerSariEtiketKontrol = new ArrayList<SariEtiketKontrolDTO>();
		
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sbBelediyeGonderilen=new StringBuffer(); 
			sbBelediyeGonderilen.append(Sorgular._GETBELEDIYEGONDERILENLERSARIKONTROL_.qry);
			sbBelediyeGonderilen.append(" and ");
			sbBelediyeGonderilen.append(((LoginBean) Common.findBean("loginBean")).getKullaniciBinaKapsam());
			
			listBelediyeGonderilenlerSariEtiketKontrol = (List<SariEtiketKontrolDTO>) runner.query(con, sbBelediyeGonderilen.toString(),	
		    		new BeanListHandler<SariEtiketKontrolDTO>(SariEtiketKontrolDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime())});
		    
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return listBelediyeGonderilenlerSariEtiketKontrol == null ? new ArrayList<SariEtiketKontrolDTO>() : listBelediyeGonderilenlerSariEtiketKontrol;
	}

	public void insertSariRaporGonderBelediye(int raporId, int belediyeKod,
			String belediyeAdi, String etiket, int kontrolId, int randevuId,
			int basvuruId, int binaId) throws CreateException {
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			
			SariEtiketKontrolDTO r = new SariEtiketKontrolDTO();
			r = (SariEtiketKontrolDTO) runner.query(con, Sorgular._BELEDIYERAPORGONDERKONTROL_.qry,
					new BeanHandler<SariEtiketKontrolDTO>(SariEtiketKontrolDTO.class),
					new Object[] { raporId });
			if(r == null || r.getRaporId() != raporId){
			runner.update(con,Sorgular._BELEDIYERAPORGONDERINSERT_.qry,
					new Object[] { raporId,belediyeKod,belediyeAdi,kontrolId,randevuId,basvuruId,binaId,etiket });
			}
			
			con.commit();
		} catch (Exception e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);
		}
		
	}	
	
}
