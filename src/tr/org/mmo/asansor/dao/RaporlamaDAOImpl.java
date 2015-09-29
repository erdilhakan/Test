package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.business.BasvuruBusiness;
import tr.org.mmo.asansor.business.OdemeBusiness;
import tr.org.mmo.asansor.dto.BelediyeDTO;
import tr.org.mmo.asansor.dto.BelediyeKontrolDTO;
import tr.org.mmo.asansor.dto.BinaKontrolVeOdemelerDTO;
import tr.org.mmo.asansor.dto.CihazOdemeKontrolDTO;
import tr.org.mmo.asansor.dto.DenetciKontrolDTO;
import tr.org.mmo.asansor.dto.EtiketYapiTipKontrolDTO;
import tr.org.mmo.asansor.dto.OdemeKontrolDTO;
import tr.org.mmo.asansor.dto.OdemeKontrolOtuzGunDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.managedbeans.LoginBean;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;


public class RaporlamaDAOImpl implements RaporlamaDAO{
	Connection con = null;
	String iller="";
	String ilceler="";
	@Override
	public List<DenetciKontrolDTO> getDenetciBazliKontroller(Date tarih1,
			Date tarih2, Map<Integer, Integer> subeIller,
			Map<Integer, ArrayList<Integer>> temsilcilikIllerIlceler,
			boolean temsilcilikSecildi) throws ReadException{
		QueryRunner runner = new QueryRunner();
		List<DenetciKontrolDTO> list = new ArrayList<DenetciKontrolDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sb=new StringBuffer();
			sb.append(Sorgular._GETDENETCIKONTROL_.qry);
			
			if (temsilcilikSecildi){
				Set<Integer> set=temsilcilikIllerIlceler.keySet();
				Iterator<Integer> iter=set.iterator();
				int sw=0;
				while (iter.hasNext()){
					int i=iter.next();
					for (int i1:temsilcilikIllerIlceler.get(i)){
						if (sw==0){
							sw++;
							sb.append(" and ( ");
							sb.append("( ").append("f.il=").append(i).append(" and ").append("f.ilce=").append(i1).append(" )");
						}else{
							sb.append(" or ").append("( ").append("f.il=").append(i).append(" and ").append("f.ilce=").append(i1).append(" )");
						}
						
					}
				}
				sb.append(" ) ");
			}else{
				Set<Integer> set=subeIller.keySet();
				Iterator<Integer> iter=set.iterator();
				int sw=0;
				while (iter.hasNext()){
					int i=iter.next();
					if (sw==0){
						sw++;
						sb.append(" and f.il in ").append("(").append(subeIller.get(i));
					}else{
						sb.append(",").append(subeIller.get(i));
					}
				}
				sb.append(" ) ");
			}
			
			
			
			sb.append(" group by kont.kontrol_muhendisi_Sicilno,muhendisadisoyadi");
			
        
			
			list = (List<DenetciKontrolDTO>) runner.query(con, sb.toString(),
					new BeanListHandler<DenetciKontrolDTO>(DenetciKontrolDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime())});
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list == null ? new ArrayList<DenetciKontrolDTO>() : list;
		
	}
	
	@Override
	public List<BelediyeKontrolDTO> getBelediyelereGoreKontroller(Date tarih1,
			Date tarih2, Map<Integer, Integer> subeIller,
			Map<Integer, ArrayList<Integer>> temsilcilikIllerIlceler,
			boolean temsilcilikSecildi) throws ReadException{
		QueryRunner runner = new QueryRunner();
		List<BelediyeKontrolDTO> list = new ArrayList<BelediyeKontrolDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sb=new StringBuffer();
			sb.append(Sorgular._GETBELEDIYEKONTROLLER_.qry);
			
			if (temsilcilikSecildi){
				Set<Integer> set=temsilcilikIllerIlceler.keySet();
				Iterator<Integer> iter=set.iterator();
				int sw=0;
				while (iter.hasNext()){
					int i=iter.next();
					for (int i1:temsilcilikIllerIlceler.get(i)){
						if (sw==0){
							sw++;
							sb.append(" and ( ");
							sb.append("( ").append("f.il=").append(i).append(" and ").append("f.ilce=").append(i1).append(" )");
						}else{
							sb.append(" or ").append("( ").append("f.il=").append(i).append(" and ").append("f.ilce=").append(i1).append(" )");
						}
						
					}
				}
				sb.append(" ) ");
			}else{
				Set<Integer> set=subeIller.keySet();
				Iterator<Integer> iter=set.iterator();
				int sw=0;
				while (iter.hasNext()){
					int i=iter.next();
					if (sw==0){
						sw++;
						sb.append(" and f.il in ").append("(").append(subeIller.get(i));
					}else{
						sb.append(",").append(subeIller.get(i));
					}
				}
				sb.append(" ) ");
			}
			
			
			
			sb.append(" group by belediyekod,nullif(bel.adi,'Belediye Bulunamadı')");
			list = (List<BelediyeKontrolDTO>) runner.query(con, sb.toString(),	new BeanListHandler<BelediyeKontrolDTO>(BelediyeKontrolDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime())});
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list == null ? new ArrayList<BelediyeKontrolDTO>() : list;
		
	}
	@Override
	public List<EtiketYapiTipKontrolDTO> getEtiketVeYapiTiplerineGoreKontroller(Date tarih1,
			Date tarih2, Map<Integer, Integer> subeIller,
			Map<Integer, ArrayList<Integer>> temsilcilikIllerIlceler,
			boolean temsilcilikSecildi) throws ReadException{
		QueryRunner runner = new QueryRunner();
		List<EtiketYapiTipKontrolDTO> list = new ArrayList<EtiketYapiTipKontrolDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sb=new StringBuffer();
			sb.append(Sorgular._GETETIKETVEYAPITIPLERINEGOREKONTROLLER_.qry);
			
			if (temsilcilikSecildi){
				Set<Integer> set=temsilcilikIllerIlceler.keySet();
				Iterator<Integer> iter=set.iterator();
				int sw=0;
				while (iter.hasNext()){
					int i=iter.next();
					for (int i1:temsilcilikIllerIlceler.get(i)){
						if (sw==0){
							sw++;
							sb.append(" and ( ");
							sb.append("( ").append("f.il=").append(i).append(" and ").append("f.ilce=").append(i1).append(" )");
						}else{
							sb.append(" or ").append("( ").append("f.il=").append(i).append(" and ").append("f.ilce=").append(i1).append(" )");
						}
						
					}
				}
				sb.append(" ) ");
			}else{
				Set<Integer> set=subeIller.keySet();
				Iterator<Integer> iter=set.iterator();
				int sw=0;
				while (iter.hasNext()){
					int i=iter.next();
					if (sw==0){
						sw++;
						sb.append(" and f.il in ").append("(").append(subeIller.get(i));
					}else{
						sb.append(",").append(subeIller.get(i));
					}
				}
				sb.append(" ) ");
			}
			
			list = (List<EtiketYapiTipKontrolDTO>) runner.query(con, sb.toString(),	new BeanListHandler<EtiketYapiTipKontrolDTO>(EtiketYapiTipKontrolDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime())});
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list == null ? new ArrayList<EtiketYapiTipKontrolDTO>() : list;
		
	}
	
	
	@Override
	public List<BinaKontrolVeOdemelerDTO> getBinaKontrolVeOdeme(Date tarih1,
			Date tarih2, List<Integer> belediyeler) throws ReadException{
		QueryRunner runner = new QueryRunner();
		List<BinaKontrolVeOdemelerDTO> list = new ArrayList<BinaKontrolVeOdemelerDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sb=new StringBuffer();
			sb.append(Sorgular._GETBINAKONTROLVEODEME_.qry);
			int sw=0;
			for (int i=0;i<belediyeler.size();i++){
				if (sw==0){
					sw++;
					sb.append(" and f.belediye in (").append(belediyeler.get(i));
				}else{
					sb.append(",").append(belediyeler.get(i));
				}
			}
			sb.append(")");
			
			list = (List<BinaKontrolVeOdemelerDTO>) runner.query(con, sb.toString(),	new BeanListHandler<BinaKontrolVeOdemelerDTO>(BinaKontrolVeOdemelerDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime())});
			list=list==null?new ArrayList<BinaKontrolVeOdemelerDTO>():list;
			for (BinaKontrolVeOdemelerDTO b:list){
				b.setBasvuruAsansor(new BasvuruBusiness().getBasvuruAsansorFirma(con,b.getBasvuruId(),b.getCihazId()));
				b.setOdeme(new OdemeBusiness().getBasvuruOdeme(con, b.getBasvuruId()));
			}
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list ;
		
	}
	@Override
	public List<BinaKontrolVeOdemelerDTO> getBinaKontrol(Date tarih1,
			Date tarih2) throws ReadException{
		QueryRunner runner = new QueryRunner();
		List<BinaKontrolVeOdemelerDTO> list = new ArrayList<BinaKontrolVeOdemelerDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sb=new StringBuffer();
			sb.append(Sorgular._GETBINAKONTROLFORBELEDIYE_.qry);
			int belediye=Integer.parseInt(((LoginBean) Common.findBean("loginBean")).getKullanici().getSicilNo());
			
		
			
			list = (List<BinaKontrolVeOdemelerDTO>) runner.query(con, sb.toString(),	new BeanListHandler<BinaKontrolVeOdemelerDTO>(BinaKontrolVeOdemelerDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime()),belediye});
			list=list==null?new ArrayList<BinaKontrolVeOdemelerDTO>():list;
			for (BinaKontrolVeOdemelerDTO b:list){
				b.setBasvuruAsansor(new BasvuruBusiness().getBasvuruAsansorFirma(con,b.getBasvuruId(),b.getCihazId()));
			
			}
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		}catch (Exception e) {
			throw new ReadException(Messages._NULLPOINTER_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list ;
		
	}
	public List<BinaKontrolVeOdemelerDTO> getBelediyeBilgi(Date tarih1,
			Date tarih2, List<Integer> belediyeler) throws ReadException{
		QueryRunner runner = new QueryRunner();
		List<BinaKontrolVeOdemelerDTO> list = new ArrayList<BinaKontrolVeOdemelerDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sb=new StringBuffer();
			sb.append(Sorgular._GETBELEDIYEBILGI_.qry);
			int sw=0;
			for (int i=0;i<belediyeler.size();i++){
				if (sw==0){
					sw++;
					sb.append(" and f.belediye in (").append(belediyeler.get(i));
				}else{
					sb.append(",").append(belediyeler.get(i));
				}
			}
			sb.append(")");
			
			list = (List<BinaKontrolVeOdemelerDTO>) runner.query(con, sb.toString(),	new BeanListHandler<BinaKontrolVeOdemelerDTO>(BinaKontrolVeOdemelerDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime())});
			list=list==null?new ArrayList<BinaKontrolVeOdemelerDTO>():list;
			for (BinaKontrolVeOdemelerDTO b:list){
				b.setBasvuruAsansor(new BasvuruBusiness().getBasvuruAsansorFirma(con,b.getBasvuruId(),b.getCihazId()));
				b.setOdeme(new OdemeBusiness().getBasvuruOdeme(con, b.getBasvuruId()));
			}
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list ;
		
	}
	
	public List<EtiketYapiTipKontrolDTO> getBelediyeOdemeler(Date tarih1,
			Date tarih2, Map<Integer, Integer> subeIller,
			Map<Integer, ArrayList<Integer>> temsilcilikIllerIlceler,
			boolean temsilcilikSecildi) throws ReadException{
		QueryRunner runner = new QueryRunner();
		List<EtiketYapiTipKontrolDTO> listOdemeler = new ArrayList<EtiketYapiTipKontrolDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sbOdemeler=new StringBuffer(); 
			sbOdemeler.append(Sorgular._GETBELEDIYEODEMELER_.qry);
			
			if (temsilcilikSecildi){
				Set<Integer> set=temsilcilikIllerIlceler.keySet();
				Iterator<Integer> iter=set.iterator();
				int sw=0;
				while (iter.hasNext()){
					int i=iter.next();
					for (int i1:temsilcilikIllerIlceler.get(i)){
						if (sw==0){
							sw++;
							sbOdemeler.append(" and ( ");
							sbOdemeler.append("( ").append("bi.il=").append(i).append(" and ").append("bi.ilce=").append(i1).append(" )");
						}else{
							sbOdemeler.append(" or ").append("( ").append("bi.il=").append(i).append(" and ").append("bi.ilce=").append(i1).append(" )");
						}
						
					}
				}
				sbOdemeler.append(" ) group by bel.kod,bel.adi,o.toplam_tutar,o.odeme_tutari,bi.bina_id,b.basvuru_id ");
			}else{
				Set<Integer> set=subeIller.keySet();
				Iterator<Integer> iter=set.iterator();
				int sw=0;
				while (iter.hasNext()){
					int i=iter.next();
					if (sw==0){
						sw++;
						sbOdemeler.append(" and bi.il in ").append("(").append(subeIller.get(i));
					}else{
						sbOdemeler.append(",").append(subeIller.get(i));
					}
				}
				sbOdemeler.append(" ) group by bel.kod,bel.adi,o.toplam_tutar,o.odeme_tutari,bi.bina_id,b.basvuru_id ");
			}
			
			listOdemeler = (List<EtiketYapiTipKontrolDTO>) runner.query(con, sbOdemeler.toString(),	new BeanListHandler<EtiketYapiTipKontrolDTO>(EtiketYapiTipKontrolDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime())});
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return listOdemeler == null ? new ArrayList<EtiketYapiTipKontrolDTO>() : listOdemeler;
		
	}
	
	public List<OdemeKontrolDTO> getBinaOdemeler(Date tarih1,
			Date tarih2, Map<Integer, Integer> subeIller,
			Map<Integer, ArrayList<Integer>> temsilcilikIllerIlceler,
			boolean temsilcilikSecildi) throws ReadException{
		QueryRunner runner = new QueryRunner();
		List<OdemeKontrolDTO> listOdemeler = new ArrayList<OdemeKontrolDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sbOdemeler=new StringBuffer(); 
			sbOdemeler.append(Sorgular._GETODEMEKONTROL_.qry);
			
			if (temsilcilikSecildi){
				Set<Integer> set=temsilcilikIllerIlceler.keySet();
				Iterator<Integer> iter=set.iterator();
				int sw=0;
				while (iter.hasNext()){
					int i=iter.next();
					for (int i1:temsilcilikIllerIlceler.get(i)){
						if (sw==0){
							sw++;
							sbOdemeler.append(" and ( ");
							sbOdemeler.append("( ").append("bi.il=").append(i).append(" and ").append("bi.ilce=").append(i1).append(" )");
						}else{
							sbOdemeler.append(" or ").append("( ").append("bi.il=").append(i).append(" and ").append("bi.ilce=").append(i1).append(" )");
						}
						
					}
				}
				sbOdemeler.append(" ) group by bel.kod,bel.adi,o.toplam_tutar,o.odeme_tutari,bi.bina_id,b.basvuru_id ");
			}else{
				Set<Integer> set=subeIller.keySet();
				Iterator<Integer> iter=set.iterator();
				int sw=0;
				while (iter.hasNext()){
					int i=iter.next();
					if (sw==0){
						sw++;
						sbOdemeler.append(" and bi.il in ").append("(").append(subeIller.get(i));
					}else{
						sbOdemeler.append(",").append(subeIller.get(i));
					}
				}
				sbOdemeler.append(" ) group by bel.kod,bel.adi,o.toplam_tutar,o.odeme_tutari,bi.bina_id,b.basvuru_id ");
			}
			
			listOdemeler = (List<OdemeKontrolDTO>) runner.query(con, sbOdemeler.toString(),	new BeanListHandler<OdemeKontrolDTO>(OdemeKontrolDTO.class),new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime())});
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return listOdemeler == null ? new ArrayList<OdemeKontrolDTO>() : listOdemeler;
		
	}
	
	public List<CihazOdemeKontrolDTO> getCihazOdemeler(Date tarih1,
			Date tarih2, Map<Integer, Integer> subeIller,
			Map<Integer, ArrayList<Integer>> temsilcilikIllerIlceler,
			boolean temsilcilikSecildi) throws ReadException{
		QueryRunner runner = new QueryRunner();
		List<CihazOdemeKontrolDTO> listOdemeler = new ArrayList<CihazOdemeKontrolDTO>();
		try {

			con = DAOBase.instance().getConnection();

			con.setAutoCommit(false);
			StringBuffer sbOdemeler=new StringBuffer(); 
			sbOdemeler.append(Sorgular._CIHAZODEMEKONTROL_.qry);
			
			if (temsilcilikSecildi){
				Set<Integer> set=temsilcilikIllerIlceler.keySet();
				Iterator<Integer> iter=set.iterator();
				int sw=0;
				while (iter.hasNext()){
					int i=iter.next();
					for (int i1:temsilcilikIllerIlceler.get(i)){
						if (sw==0){
							sw++;
							sbOdemeler.append(" and ( ");
							sbOdemeler.append("( ").append("bina.il=").append(i).append(" and ").append("bina.ilce=").append(i1).append(" )");
						}else{
							sbOdemeler.append(" or ").append("( ").append("bina.il=").append(i).append(" and ").append("bina.ilce=").append(i1).append(" )");
						}
						
					}
				}
				sbOdemeler.append(" ) /*group by bel.kod,bel.adi,o.toplam_tutar,o.odeme_tutari,bina.bina_id,b.basvuru_id*/ ");
			}else{
				Set<Integer> set=subeIller.keySet();
				Iterator<Integer> iter=set.iterator();
				int sw=0;
				while (iter.hasNext()){
					int i=iter.next();
					if (sw==0){
						sw++;
						sbOdemeler.append(" and bina.il in ").append("(").append(subeIller.get(i));
					}else{
						sbOdemeler.append(",").append(subeIller.get(i));
					}
				}
				sbOdemeler.append(" ) order by cihaz.kimlik_no ");
			}
			
			listOdemeler = (List<CihazOdemeKontrolDTO>) runner.query(con, sbOdemeler.toString(),new BeanListHandler<CihazOdemeKontrolDTO>(CihazOdemeKontrolDTO.class),
				           new Object[]{new java.sql.Date(tarih1.getTime()),new java.sql.Date(tarih2.getTime())});
			
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return listOdemeler == null ? new ArrayList<CihazOdemeKontrolDTO>() : listOdemeler;
		
	}

	@Override
	public List<OdemeKontrolOtuzGunDTO> getBinaOdemelerOtuzGun(Date tarih1,
			Date tarih2) throws ReadException {
		// TODO Auto-generated method stub
		return null;
	}	
	
}
