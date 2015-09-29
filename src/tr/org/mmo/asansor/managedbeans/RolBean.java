package tr.org.mmo.asansor.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.dialog.Dialog;
import org.primefaces.component.fieldset.Fieldset;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import tr.org.mmo.asansor.business.RolBusiness;
import tr.org.mmo.asansor.dto.RolYetkiDTO;
import tr.org.mmo.asansor.dto.RolYetkiRelationDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.models.RolYetkiModel;

@ManagedBean
@ViewScoped
public class RolBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1598091252485656056L;
	private List<RolYetkiDTO> rolListesi;
	private List<RolYetkiDTO> yetkiListesi;
	private List<RolYetkiDTO> filteredRol;
	private List<RolYetkiDTO> filteredYetki;
	private RolYetkiDTO rolDTO = new RolYetkiDTO();
	private RolYetkiModel rolModel;
	private RolYetkiModel yetkiModel;
	private List<RolYetkiDTO> tumYetkiler = new ArrayList<RolYetkiDTO>();
	private List<RolYetkiDTO> secilenYetki = new ArrayList<RolYetkiDTO>();
	private RolYetkiDTO secilenRol = new RolYetkiDTO();
	private List<RolYetkiRelationDTO> secilenRolYetkileri = new ArrayList<RolYetkiRelationDTO>();

	public List<RolYetkiDTO> getRolListesi() {
		return rolListesi;
	}

	public void setRolListesi(List<RolYetkiDTO> rolListesi) {
		this.rolListesi = rolListesi;
	}

	public RolYetkiDTO getSecilenRol() {
		return secilenRol;
	}

	public void setSecilenRol(RolYetkiDTO secilenRol) {
		this.secilenRol = secilenRol;
	}

	@PostConstruct
	public void init() {
		rolListesi = new ArrayList<RolYetkiDTO>();
		yetkiListesi = new ArrayList<RolYetkiDTO>();
		rolModel = new RolYetkiModel();
		yetkiModel = new RolYetkiModel();
		try {
			rolListesi = rolListesiGetir();
			rolModel = new RolYetkiModel(rolListesi);
			yetkiListesi = yetkiListesiGetir();
			for (RolYetkiDTO y : yetkiListesi) {
				tumYetkiler.add(y);
			}

			yetkiModel = new RolYetkiModel(yetkiListesi);

		} catch (CRUDException e) {

			e.printStackTrace();
		}

	}

	public List<RolYetkiDTO> rolListesiGetir() throws CRUDException {
		List<RolYetkiDTO> list = new ArrayList<RolYetkiDTO>();

		list = new RolBusiness().listeGetir();

		return list;

	}

	public List<RolYetkiDTO> yetkiListesiGetir() throws CRUDException {
		List<RolYetkiDTO> list = new ArrayList<RolYetkiDTO>();

		list = new RolBusiness().yetkiListesiGetir();
		return list;

	}

	public List<RolYetkiDTO> getYetkiListesi() {
		return yetkiListesi;
	}

	public void setYetkiListesi(List<RolYetkiDTO> yetkiListesi) {
		this.yetkiListesi = yetkiListesi;
	}

	public List<RolYetkiDTO> getFilteredYetki() {
		return filteredYetki;
	}

	public void setFilteredYetki(List<RolYetkiDTO> filteredYetki) {
		this.filteredYetki = filteredYetki;
	}

	public List<RolYetkiDTO> getFilteredRol() {
		return filteredRol;
	}

	public void setFilteredRol(List<RolYetkiDTO> filteredRol) {
		this.filteredRol = filteredRol;
	}

	public RolYetkiModel getRolModel() {
		return rolModel;
	}

	public void setRolModel(RolYetkiModel rolModel) {
		this.rolModel = rolModel;
	}

	public RolYetkiModel getYetkiModel() {
		return yetkiModel;
	}

	public void setYetkiModel(RolYetkiModel yetkiModel) {
		this.yetkiModel = yetkiModel;
	}

	public List<RolYetkiDTO> getSecilenYetki() {
		return secilenYetki;
	}

	public void setSecilenYetki(List<RolYetkiDTO> secilenYetki) {
		this.secilenYetki = secilenYetki;
	}

	public void rolEkleListener(ActionEvent event) {
		RolYetkiDTO r = new RolYetkiDTO();
		rolListesi.add(r);
		rolModel = new RolYetkiModel(rolListesi);
	}

	public void rolKaydet(ActionEvent event) {

		try {
			int rolId = new RolBusiness().rolEkle(rolDTO);
			rolDTO.setId(rolId);
			rolListesi.add(rolDTO);
			rolModel = new RolYetkiModel(rolListesi);
			RequestContext.getCurrentInstance().execute("PF('rolEkle').hide()");
			Dialog d = (Dialog) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent(":form:rolEkle");
			d.setVisible(false);
			rolDTO = new RolYetkiDTO();
			Fieldset fset = (Fieldset) FacesContext.getCurrentInstance()
					.getViewRoot().findComponent(":form:rolyetkifield");
			fset.setRendered(false);

		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), null));
		}

	}

	public void rolSil(RolYetkiDTO rol) {

		try {
			new RolBusiness().rolSil(rol.getId());

			rolListesi.remove(rol);

			rolModel = new RolYetkiModel(rolListesi);
			secilenRolYetkileri = new ArrayList<RolYetkiRelationDTO>();
			Fieldset fset = (Fieldset) FacesContext.getCurrentInstance()
					.getViewRoot().findComponent(":form:rolyetkifield");
			fset.setRendered(false);
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), null));
		}

	}

	public void rolYetkiSil(RolYetkiRelationDTO yetki) {
		if (yetki != null) {

			try {
				new RolBusiness().rolYetkiSil(yetki);

				secilenRolYetkileri.remove(yetki);

				yetkiListesi = new ArrayList<RolYetkiDTO>();
				for (RolYetkiDTO y : tumYetkiler) {
					boolean var = false;
					for (RolYetkiRelationDTO yr : secilenRolYetkileri) {
						if (y.getId() == yr.getYetkiId()) {
							var = true;
							break;
						}
					}
					if (!var)
						yetkiListesi.add(y);
				}
				yetkiModel = new RolYetkiModel(yetkiListesi);

			} catch (CRUDException e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e
								.getMessage(), null));
			}
		}

	}

	public RolYetkiDTO getRolDTO() {
		return rolDTO;
	}

	public void setRolDTO(RolYetkiDTO rolDTO) {
		this.rolDTO = rolDTO;
	}

	public void onRolSelect(SelectEvent event) {
		if (secilenRol != null) {
			try {
				secilenRolYetkileri = new ArrayList<RolYetkiRelationDTO>();
				secilenRolYetkileri = new RolBusiness()
						.getRolYetkiler(secilenRol.getId());

				yetkiListesi = new ArrayList<RolYetkiDTO>();
				for (RolYetkiDTO y : tumYetkiler) {
					boolean var = false;
					for (RolYetkiRelationDTO yr : secilenRolYetkileri) {
						if (y.getId() == yr.getYetkiId()) {
							var = true;
							break;
						}
					}
					if (!var)
						yetkiListesi.add(y);
				}
				yetkiModel = new RolYetkiModel(yetkiListesi);
				Fieldset fSet = (Fieldset) FacesContext.getCurrentInstance()
						.getViewRoot().findComponent(":form:rolyetkifield");
				fSet.setRendered(true);
			} catch (CRUDException e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e
								.getMessage(), null));
			}

		}
	}

	public void yetkiTasi(ActionEvent event) {
		if (secilenYetki != null && secilenYetki.size() > 0
				&& secilenRol != null && secilenRol.getId() > 0) {
			try {
				new RolBusiness().yetkiTasi(secilenYetki, secilenRol.getId());
				Fieldset fSet = (Fieldset) FacesContext.getCurrentInstance()
						.getViewRoot().findComponent(":form:rolyetkifield");
				fSet.setRendered(true);
				for (RolYetkiDTO r : secilenYetki) {
					RolYetkiRelationDTO ryr = new RolYetkiRelationDTO();
					ryr.setRolId(secilenRol.getId());
					ryr.setYetkiId(r.getId());
					ryr.setYetkiAdi(r.getAdi());
					secilenRolYetkileri.add(ryr);
				}
				yetkiListesi = new ArrayList<RolYetkiDTO>();
				for (RolYetkiDTO y : tumYetkiler) {
					boolean var = false;
					for (RolYetkiRelationDTO yr : secilenRolYetkileri) {
						if (y.getId() == yr.getYetkiId()) {
							var = true;
							break;
						}
					}
					if (!var)
						yetkiListesi.add(y);
				}
				yetkiModel = new RolYetkiModel(yetkiListesi);
			} catch (CRUDException e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e
								.getMessage(), null));
			}

		}
	}

	public List<RolYetkiRelationDTO> getSecilenRolYetkileri() {
		return secilenRolYetkileri;
	}

	public void setSecilenRolYetkileri(
			List<RolYetkiRelationDTO> secilenRolYetkileri) {
		this.secilenRolYetkileri = secilenRolYetkileri;
	}

}
