package tr.org.mmo.asansor.dao;


import java.util.List;

import tr.org.mmo.asansor.beans_.AnaMenu;
import tr.org.mmo.asansor.dto.MenuItemDTO;
import tr.org.mmo.asansor.exception.db.ReadException;

public interface MenuListDAO {
	public List<MenuItemDTO> listeGetir();
	public List<AnaMenu> anaListeGetir() throws ReadException;
	
}
