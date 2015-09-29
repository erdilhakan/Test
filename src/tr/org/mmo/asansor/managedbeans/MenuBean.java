package tr.org.mmo.asansor.managedbeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.component.panelmenu.PanelMenu;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;

import tr.org.mmo.asansor.beans_.AnaMenu;
import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.business.MenuListBusiness;
import tr.org.mmo.asansor.exception.db.ReadException;

@ManagedBean
public class MenuBean {
	private PanelMenu menu = new PanelMenu();

	@SuppressWarnings("unused")
	public void listener(javax.faces.event.ActionEvent event) {
		MenuItem item = (MenuItem) event.getComponent();
		LoginBean l = (LoginBean) Common.findBean("loginBean");

		// UIMenuItem item=(UIMenuItem)event.getComponent();

		// System.out.println(item.getUrl());

		// item.setInView(true);

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("basvuru.jsf");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<AnaMenu> menuListSmall;

	public List<AnaMenu> getMenuListSmall() {
		return menuListSmall;
	}

	public void setMenuListSmall(List<AnaMenu> menuListSmall) {
		this.menuListSmall = menuListSmall;
	}

	public void getAnaMenu() {
		MenuListBusiness mlb = new MenuListBusiness();
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage("");
		try {
			menuListSmall = mlb.anaSoruListesiGetir();
		} catch (ReadException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
		}
	}

	public MenuBean() {

		menuListSmall = new ArrayList<AnaMenu>();

		getAnaMenu();

		model = new DefaultMenuModel();

		// First submenu
		DefaultSubMenu firstSubmenu = new DefaultSubMenu("Dynamic Submenu");

		DefaultMenuItem item = new DefaultMenuItem("External");
		item.setUrl("http://www.primefaces.org");
		item.setIcon("ui-icon-home");
		firstSubmenu.addElement(item);
		// firstSubmenu.setStyleClass("ui-state-passive");
		firstSubmenu.setId("Dynamic");
		model.addElement(firstSubmenu);

		// Second submenu

		DefaultSubMenu secondSubmenu = new DefaultSubMenu("Asansör Kontrol");

		item = new DefaultMenuItem("Başvuru");
		item.setIcon("ui-icon-close");
		item.setCommand("#{menuBean.setInclude('basvuru_.xhtml')}");
		// item.setAjax(false);
		// item.setUpdate("sag");
		secondSubmenu.addElement(item);

		item = new DefaultMenuItem("Başvuru Listesi");
		item.setIcon("ui-icon-search");
		item.setCommand("#{menuBean.setInclude('basvuruListesi_.xhtml')}");
		// item.setAjax(false);
		// item.setUpdate("form1");
		secondSubmenu.addElement(item);

		item = new DefaultMenuItem("Bina");
		item.setIcon("ui-icon-close");
		item.setCommand("#{menuBean.setInclude('bina_.xhtml')}");
		// item.setAjax(false);
		// item.setUpdate("form1");
		secondSubmenu.addElement(item);

		item = new DefaultMenuItem("Kontrol");
		item.setIcon("ui-icon-disk");
		item.setCommand("#{menuBean.setInclude('kontroltest_.xhtml')}");
		// item.setAjax(false);
		// item.setUpdate("form1");
		secondSubmenu.addElement(item);

		secondSubmenu.setId("Asansor");

		model.addElement(secondSubmenu);

		menu.setModel(model);

	}

	private MenuModel model;

	public MenuModel getModel() {
		return model;
	}

	public PanelMenu getMenu() {
		return menu;
	}

	public void setMenu(PanelMenu menu) {
		this.menu = menu;
	}

	public void setId(String id) {
		System.out.println(id);
	}

	private String sayfa = "basvuruListesi_.xhtml";

	public String getSayfa() {
		return sayfa;
	}

	public void setSayfa(String sayfa) {
		this.sayfa = sayfa;
	}

	public void setInclude(String yeniSayfa) {

		sayfa = yeniSayfa;

	}

}