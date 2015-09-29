package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;



import tr.org.mmo.asansor.dto.MuayeneKurulusDTO;
import tr.org.mmo.asansor.dto.SubeDTO;
import tr.org.mmo.asansor.dto.TemsilcilikDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;

public class TemsilcilikDAOImpl {
	Connection con=null;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<TemsilcilikDTO> temsilcilikListesi() throws ReadException{
		QueryRunner runner=new QueryRunner();
		List<TemsilcilikDTO> list=new ArrayList<TemsilcilikDTO>();
		try {
			
				con = DAOBase.instance().getConnection();
			
				con.setAutoCommit(false);
				list=(List<TemsilcilikDTO>)runner.query(con,Sorgular._TEMSILCILIKLISTESI_.qry,new BeanListHandler(TemsilcilikDTO.class));
				for(TemsilcilikDTO t:list){
					t.setSube(getSube(t));
					
				}
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		}
		finally{
		DbUtils.closeQuietly(con);
		
		}
		return list==null?new ArrayList<TemsilcilikDTO>():list;
		
		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TemsilcilikDTO temsilcilikGetir(int temsilcilikKodu) throws ReadException{
		QueryRunner runner=new QueryRunner();
		TemsilcilikDTO t=new TemsilcilikDTO();
		try {
			
				con = DAOBase.instance().getConnection();
			
				con.setAutoCommit(false);
				t=(TemsilcilikDTO)runner.query(con,Sorgular._TEMSILCILIKBYKOD_.qry,new BeanHandler(TemsilcilikDTO.class),new Object[]{temsilcilikKodu});
				
				t.setSube(getSube(t));
					
				
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		}
		finally{
		DbUtils.closeQuietly(con);
		
		}
		return t==null?new TemsilcilikDTO():t;
		
		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private SubeDTO getSube(TemsilcilikDTO temsilcilik)throws ReadException{
		SubeDTO sube=new SubeDTO();
		
		QueryRunner runner=new QueryRunner();
		
		try {
			
				con = DAOBase.instance().getConnection();
			
				con.setAutoCommit(false);
				sube=(SubeDTO)runner.query(con,Sorgular._TEMSILCILIKSUBE_.qry,new BeanHandler(SubeDTO.class), new Object[]{temsilcilik.getSubesi()});
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		}
		finally{
		DbUtils.closeQuietly(con);
		
		}
		return sube==null?new SubeDTO():sube;
		
		
		
	}

	
	public MuayeneKurulusDTO getTemsilcilik(int ilKodu, int ilceKodu) throws ReadException{
		MuayeneKurulusDTO temsilcilik=new MuayeneKurulusDTO();
		
		QueryRunner runner=new QueryRunner();
		
		try {
			
				con = DAOBase.instance().getConnection();
			
				con.setAutoCommit(false);
				temsilcilik=(MuayeneKurulusDTO)runner.query(con,Sorgular._GETTEMSILCILIKFROMILILCE_.qry,new BeanHandler<MuayeneKurulusDTO>(MuayeneKurulusDTO.class), new Object[]{ilKodu,ilceKodu});
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		}
		finally{
		DbUtils.closeQuietly(con);
		
		}
		return temsilcilik==null?new MuayeneKurulusDTO():temsilcilik;
	}
	
	public MuayeneKurulusDTO getSube(int ilKodu) throws ReadException{
		MuayeneKurulusDTO sube=new MuayeneKurulusDTO();
		
		QueryRunner runner=new QueryRunner();
		
		try {
			
				con = DAOBase.instance().getConnection();
			
				con.setAutoCommit(false);
				sube=(MuayeneKurulusDTO)runner.query(con,Sorgular._GETSUBEFROMIL_.qry,new BeanHandler<MuayeneKurulusDTO>(MuayeneKurulusDTO.class), new Object[]{ilKodu});
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);
		}
		finally{
		DbUtils.closeQuietly(con);
		
		}
		return sube==null?new MuayeneKurulusDTO():sube;
	}
	
}
