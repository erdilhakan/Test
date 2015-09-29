package tr.org.mmo.asansor.business;

import java.util.List;

import tr.org.mmo.asansor.beans_.AnaMenu;

import tr.org.mmo.asansor.dao.MenuListDAOImpl;
import tr.org.mmo.asansor.dto.MenuItemDTO;

import tr.org.mmo.asansor.exception.db.ReadException;

public class MenuListBusiness {
	private MenuListDAOImpl dao = new MenuListDAOImpl();
	public List<MenuItemDTO> soruListesiGetir(){
		return dao.listeGetir();
		
	}
	
	public List<AnaMenu> anaSoruListesiGetir() throws ReadException{
		return dao.anaListeGetir();
		
	}
	
}
