package tr.org.mmo.asansor.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.business.MuhendisBusiness;
import tr.org.mmo.asansor.dto.IlIlceDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.models.MuhendisDataModel;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.MuhendisComparator;

@ManagedBean
@ViewScoped
public class MuhendisListeBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7371184568206579283L;

	private List<Kullanici> muhendisListSmall;

	private Kullanici slctMuhendis = new Kullanici();
	private List<Kullanici> filteredList;
	private MuhendisDataModel muhendisListModel;

	public List<Kullanici> getMuhendisListSmall() {
		return muhendisListSmall;
	}

	public void setMuhendisListSmall(List<Kullanici> muhendisListSmall) {
		this.muhendisListSmall = muhendisListSmall;
	}

	public Kullanici getSlctMuhendis() {
		return slctMuhendis;
	}

	public void setSlctMuhendis(Kullanici slctMuhendis) {
		this.slctMuhendis = slctMuhendis;
	}

	public MuhendisDataModel getMuhendisListModel() {
		return muhendisListModel;
	}

	public void setMuhendisListModel(MuhendisDataModel muhendisListModel) {
		this.muhendisListModel = muhendisListModel;
	}

	public List<Kullanici> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<Kullanici> filteredList) {
		this.filteredList = filteredList;
	}

	public void listeGetir() {

		muhendisListSmall = new ArrayList<Kullanici>();

		try {
			muhendisListSmall = new MuhendisBusiness().listeGetir();

			Collections.sort(muhendisListSmall, new MuhendisComparator());

			muhendisListModel = new MuhendisDataModel(muhendisListSmall);
		} catch (CRUDException e) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));

			e.printStackTrace();
		}
	}

	@PostConstruct
	public void init() {
		listeGetir();
	}

	public void muhendisKaydet() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		try {

			new MuhendisBusiness().muhendisUpdate(slctMuhendis);
			muhendisListSmall.remove(slctMuhendis);
			muhendisListSmall.add(slctMuhendis);
			muhendisListModel = new MuhendisDataModel(muhendisListSmall);

			msg = new FacesMessage(Messages._MUHENDISKAYDEDILDI_.getMesaj());

			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute(
					"PF('muhendisupdate').hide()");

		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		context.addMessage(null, msg);
	}

	public void handleClose(AjaxBehaviorEvent event) {
		boolean upd = false;
		for (IlIlceDTO i : ((ApplicationBean) Common
				.findBean("applicationBean")).getIlIlceList()) {
			if (slctMuhendis.getIl() == i.getIlKodu()) {
				slctMuhendis.setIlAdi(i.getIlAdi());
				upd = true;
			}
			if (upd)
				break;
		}

		for (Kullanici k : muhendisListSmall) {
			if (k.getKullaniciId() == slctMuhendis.getKullaniciId()) {

				try {
					k = (Kullanici) slctMuhendis.clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
