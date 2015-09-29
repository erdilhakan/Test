package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.beans_.AnaMenu;

import tr.org.mmo.asansor.dto.MenuItemDTO;


import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;

public class MenuListDAOImpl implements MenuListDAO{

	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<MenuItemDTO> listeGetir() {
		List<MenuItemDTO> liste = new ArrayList<MenuItemDTO>();
		
		Connection con=null;
		try {
			con = DAOBase.instance().getConnection();
		} catch (CreateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueryRunner runner=new QueryRunner();
		
		try {

				liste= (List<MenuItemDTO>)runner.query(con, Sorgular._MENULIST_.qry, new BeanListHandler(MenuItemDTO.class) );
				
	
			
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			
			DbUtils.closeQuietly(con);
		}
		
	return liste==null?new ArrayList<MenuItemDTO>():liste;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<AnaMenu> anaListeGetir() throws ReadException {
		List<AnaMenu> liste = new ArrayList<AnaMenu>();
		
		Connection con=null;
		try {
			con = DAOBase.instance().getConnection();
	
		
			con.setAutoCommit(false);
	
		QueryRunner runner=new QueryRunner();
		
		

				liste= (List<AnaMenu>)runner.query(con, Sorgular._ANAMENULIST_.qry, new BeanListHandler(AnaMenu.class));
				for(AnaMenu s:liste){
					s.setAltMenuler(altListeGetir(con, s.getMenuitemId()));
				}
				
		} catch (CreateException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		
			
			} catch (SQLException e) {
				throw new ReadException(Messages._SQL_500_.getMesaj(), null);
		}
		finally{
			
			DbUtils.closeQuietly(con);
		}
		
	return liste==null?new ArrayList<AnaMenu>():liste;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<MenuItemDTO> altListeGetir(Connection con, int ustMenuId) throws ReadException {
		List<MenuItemDTO> liste = new ArrayList<MenuItemDTO>();
		

		QueryRunner runner=new QueryRunner();
		
		try {

				liste= (List<MenuItemDTO>)runner.query(con, Sorgular._ALTMENULIST_.qry, new BeanListHandler(MenuItemDTO.class),new Integer(ustMenuId) );
				
	
			
			} catch (SQLException e) {
			// TODO Auto-generated catch block
				throw new ReadException(Messages._SQL_500_.getMesaj(), null);
			
		}

		
	return liste==null?new ArrayList<MenuItemDTO>():liste;
	} 
	
}
