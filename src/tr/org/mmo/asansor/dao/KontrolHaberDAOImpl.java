package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.dto.KontrolHaberDTO;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.managedbeans.LoginBean;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;

public class KontrolHaberDAOImpl implements KontrolHaberDAO {
	Connection con=null;
	 @Override
	public List<KontrolHaberDTO> getKontroller(Date gununTarihi) throws ReadException {
		QueryRunner runner=new QueryRunner();
		List<KontrolHaberDTO> binaListe = new ArrayList<KontrolHaberDTO>();
		Calendar bugun = Calendar.getInstance();
		bugun.setTime(gununTarihi);
		bugun.set(Calendar.MONTH,bugun.get(Calendar.MONTH)-11);
	
		
		try {
			con=DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuilder sb=new StringBuilder();
			sb.append(Sorgular._KONTROLHABER_.qry);
			try{
				
			
			if (((LoginBean)Common.findBean("loginBean")).getKullaniciBinaKapsam()!=null && 
					((LoginBean)Common.findBean("loginBean")).getKullaniciBinaKapsam().length()>1){	
			sb.append(" AND ");
			sb.append(((LoginBean)Common.findBean("loginBean")).getKullaniciBinaKapsam());
			sb.append(" order by kontrol.kontrol_bitis_tarihi desc");
			}
			}catch(NullPointerException e){
				throw new ReadException(Messages._NULLPOINTER_.getMesaj(),e);
			}	
		binaListe=(List<KontrolHaberDTO>)runner.query(con,sb.toString(),new BeanListHandler<KontrolHaberDTO>(KontrolHaberDTO.class),new Object[]{new java.sql.Date(bugun.getTime().getTime()) });
		
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(),e);
		}
		finally{
			
			DbUtils.closeQuietly(con);
		}
		return binaListe;
	}
	
}
