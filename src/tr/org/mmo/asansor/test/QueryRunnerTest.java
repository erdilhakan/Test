package tr.org.mmo.asansor.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;


import tr.org.mmo.asansor.dao.DAOBase;
import tr.org.mmo.asansor.dto.BasvuruDTO;
import tr.org.mmo.asansor.exception.db.CreateException;


public class QueryRunnerTest {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BasvuruDTO getBasvuru(long tcKimlikNo){
		BasvuruDTO basvuru=new BasvuruDTO();
		String sql="";
		//where kosulunu tckimlikno=? olarak d�s�n
		Connection con=null;
		QueryRunner run=new QueryRunner();
		try {
			con=DAOBase.instance().getConnection();
			
			try {
				basvuru=(BasvuruDTO)run.query(con, sql, new BeanHandler(BasvuruDTO.class), new Long(tcKimlikNo));
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (CreateException e) {
			e.printStackTrace();
		}
		finally{
			DbUtils.closeQuietly(con);
		}
		return basvuru==null?new BasvuruDTO():basvuru;
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<BasvuruDTO> getBasvuruList(long tcKimlikNo){
		ArrayList<BasvuruDTO> basvuru=new ArrayList<BasvuruDTO>();
		String sql="";
		//where kosulunu tckimlikno=? olarak d�s�n
		Connection con=null;
		QueryRunner run=new QueryRunner();
		try {
			con=DAOBase.instance().getConnection();
			
			try {
				basvuru=(ArrayList<BasvuruDTO>)run.query(con, sql, new BeanListHandler(BasvuruDTO.class), new Long(tcKimlikNo));
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (CreateException e) {
			e.printStackTrace();
		}
		finally{
			DbUtils.closeQuietly(con);
		}
		return basvuru==null?new ArrayList<BasvuruDTO>():basvuru;
		
	}


}
