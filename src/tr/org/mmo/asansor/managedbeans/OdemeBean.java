package tr.org.mmo.asansor.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.fieldset.Fieldset;
import org.primefaces.event.SelectEvent;

import tr.org.mmo.asansor.business.BasvuruBusiness;
import tr.org.mmo.asansor.business.BinaBusiness;
import tr.org.mmo.asansor.business.OdemeBusiness;
import tr.org.mmo.asansor.dto.BasvuruListeDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.OdemeDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.models.BasvuruListeModel;
import tr.org.mmo.asansor.models.OdemeDataModel;
import tr.org.mmo.asansor.util.Messages;

@ManagedBean
@ViewScoped
public class OdemeBean implements Serializable {

	private static final long serialVersionUID = 6731731222549640804L;
	private OdemeDataModel odemeDataModel = new OdemeDataModel();
	private BasvuruListeModel odemeYapModel = new BasvuruListeModel();
	private OdemeDataModel odenenDataModel = new OdemeDataModel();
	private BasvuruListeModel basvuruListeModel = new BasvuruListeModel(
			new ArrayList<BasvuruListeDTO>());
	private List<BasvuruListeDTO> basvuruList = new ArrayList<BasvuruListeDTO>();
	private List<OdemeDTO> odemeList = new ArrayList<OdemeDTO>();
	private List<BasvuruListeDTO> filteredOdenecek;
	private BasvuruListeDTO selectedOdenecek;
	private List<OdemeDTO> filteredVal;
	private OdemeDTO selectedVal;
	private BasvuruListeDTO basvuru = new BasvuruListeDTO();
	private String headerText;
	private boolean textAra = false;
	private String basvuruId;
	private OdemeDTO odeme = new OdemeDTO();
	private boolean odemeReq = false;
	private List<OdemeDTO> odenenList = new ArrayList<OdemeDTO>();
	private List<BasvuruListeDTO> odemeYapilacakListe = new ArrayList<BasvuruListeDTO>();
	
	@ManagedProperty("#{binaBean}")
	private BinaBean binaBean;
	@PostConstruct
	public void init() {
	
		if (FacesContext.getCurrentInstance().getViewRoot().getViewId().contains("odemeGorme")){
			
			if (binaBean!=null && binaBean.getSessionBina()!=null && binaBean.getSessionBina().getBinaId()>0 && (odeme==null ||  odeme.getId()<=0)){
				odemeListener(binaBean.getSessionBina());	
			}
			
			
		}
		if (FacesContext.getCurrentInstance().getViewRoot().getViewId().contains("odemeGiris")){
		try {

			odenenList = new OdemeBusiness().getYapilanOdemeler();
			odenenDataModel = new OdemeDataModel(odenenList);
			odemeYapilacakListe = new OdemeBusiness().getOdemeListe();
			for (BasvuruListeDTO b : odemeYapilacakListe) {
				b.setAsansorKontrolFiyatlari(new BasvuruBusiness()
						.getBasvuruAsansor(b.getBasvuruId()));
			}

			odemeYapModel = new BasvuruListeModel(odemeYapilacakListe);
			OdemeDTO odm=(OdemeDTO)FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
			.get("odeme");
			if (odm!=null && odm.getId()>0){
				try {
					this.odeme=(OdemeDTO)odm.clone();
				} catch (CloneNotSupportedException e) {
					this.odeme=new OdemeDTO();
				}
			}

		} catch (ReadException e1) {

			e1.printStackTrace();
		}
		}
		if (odeme != null && odeme.getId() > 0) {

			try {
				this.odeme = (OdemeDTO) odeme.clone();
				basvuruId = String.valueOf(odeme.getBasvuruId());
				headerText = new BinaBusiness().binaBulByBasvuruId(basvuruId);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CRUDException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}

	public OdemeDTO getOdeme() {
		return odeme;
	}

	public void setOdeme(OdemeDTO odeme) {
		this.odeme = odeme;
	}

	public String getBasvuruId() {
		return basvuruId;
	}

	public void setBasvuruId(String basvuruId) {
		this.basvuruId = basvuruId;
	}

	public BasvuruListeModel getBasvuruListeModel() {
		return basvuruListeModel;
	}

	public void setBasvuruListeModel(BasvuruListeModel basvuruListeModel) {
		this.basvuruListeModel = basvuruListeModel;
	}

	public List<BasvuruListeDTO> getBasvuruList() {
		return basvuruList;
	}

	public void setBasvuruList(List<BasvuruListeDTO> basvuruList) {
		this.basvuruList = basvuruList;
	}

	public BasvuruListeDTO getBasvuru() {
		return basvuru;
	}

	public void setBasvuru(BasvuruListeDTO basvuru) {
		this.basvuru = basvuru;
	}

	public void basvuruSelectListener(SelectEvent event) {

		String id = String.valueOf(((BasvuruListeDTO) event.getObject())
				.getBasvuruId());

		if (id != null) {
			basvuruId = id;
			odemeGetirByBasvuruId();
		}

	}

	public void odemeListener(BinaDTO binaDto) {
	
		headerText = "";
		if (binaDto != null) {
			headerText = binaDto.getBinaAdi();

			try {
				odemeList = new ArrayList<OdemeDTO>();
				odemeList = new OdemeBusiness().getOdemeListe(binaDto
						.getBinaId());
				if (odemeList.size() > 0) {
					odemeDataModel = new OdemeDataModel(odemeList);

				} else {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(Messages._SQL_506_.getMesaj()));
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}
		}
	}

	public void odemeListener() {
	

		headerText = "";
		if (binaBean != null && binaBean.getBinalar() != null) {
			headerText = binaBean.getBinalar().getBinaAdi();

			try {
				odemeList = new ArrayList<OdemeDTO>();
				odemeList = new OdemeBusiness().getOdemeListe(binaBean
						.getBinalar().getBinaId());
				if (odemeList.size() > 0) {
					odemeDataModel = new OdemeDataModel(odemeList);

				} else {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(Messages._SQL_506_.getMesaj()));
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}
		}
	}

	public void basvuruListener(SelectEvent event) {
		BinaDTO binaDto = (BinaDTO) event.getObject();
		headerText = "";
		if (binaDto != null) {
			headerText = binaDto.getBinaAdi();
			textAra = true;
			try {
				basvuruList = new ArrayList<BasvuruListeDTO>();
				basvuruList = new BasvuruBusiness().getBasvuruByBinaId(binaDto
						.getBinaId());
				basvuruListeModel = new BasvuruListeModel(basvuruList);
				textAra = false;
			} catch (CRUDException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}
		}
	}

	public void basvuruListener() {
	

		headerText = "";
		if (binaBean != null && binaBean.getBinalar() != null) {
			headerText = binaBean.getBinalar().getBinaAdi();
			textAra = true;
			try {
				basvuruList = new ArrayList<BasvuruListeDTO>();
				basvuruList = new BasvuruBusiness().getBasvuruByBinaId(binaBean
						.getBinalar().getBinaId());
				basvuruListeModel = new BasvuruListeModel(basvuruList);
				textAra = false;
			} catch (CRUDException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}
		}
	}

	public void odemeGetirByBasvuruId() {

		try {
			odemeReq=false;
			odeme = new OdemeBusiness().getOdemeByBasvuruId(Integer
					.parseInt(basvuruId));

			if (!textAra)
				headerText = new BinaBusiness().binaBulByBasvuruId(basvuruId);
			textAra = false;
			if (odeme == null || odeme.getId() <= 0) {

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(Messages._SQL_506_.getMesaj()));
			} else {
				if (odeme.getKalan()>0){
					odemeReq=true;
				}
				
				Fieldset fieldset = (Fieldset) FacesContext
						.getCurrentInstance().getViewRoot()
						.findComponent(":formodeme:panelodeme");
				if (fieldset != null) {
					fieldset.setRendered(true);
				}
			}
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}

	public void odemeAl() {

		FacesMessage msg;

		if (odeme.getOdemeTutari() > odeme.getToplamTutar()) {
			msg = new FacesMessage(Messages._ODEMETUTARIFAZLA_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {

			try {
				new OdemeBusiness().odemeKaydet(odeme);
				for (OdemeDTO o : odenenList) {
					if (o.getId() == odeme.getId()) {
						odenenList.remove(o);
						break;
					}
				}
				try {
					
					odenenList = new OdemeBusiness().getYapilanOdemeler();
					odenenDataModel = new OdemeDataModel(odenenList);
					
				} catch (ReadException e1) {
					
					e1.printStackTrace();
				}
				odenenDataModel = new OdemeDataModel(odenenList);
				for (BasvuruListeDTO b : odemeYapilacakListe) {
					if (b.getId() == odeme.getId()) {
						odemeYapilacakListe.remove(b);
						break;
					}
				}

				odemeYapModel = new BasvuruListeModel(
						odemeYapilacakListe == null ? new ArrayList<BasvuruListeDTO>()
								: odemeYapilacakListe);
				odeme = new OdemeDTO();
				basvuruId = "";

				msg = new FacesMessage(Messages._ODEMEKAYDEDILDI_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, msg);

			} catch (CRUDException e) {
				msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}

	}

	public List<OdemeDTO> getOdemeList() {
		return odemeList;
	}

	public void setOdemeList(List<OdemeDTO> odemeList) {
		this.odemeList = odemeList;
	}

	public List<OdemeDTO> getFilteredVal() {
		return filteredVal;
	}

	public void setFilteredVal(List<OdemeDTO> filteredVal) {
		this.filteredVal = filteredVal;
	}

	public String odemeDetay(OdemeDTO odeme) {
		String str = "";
		if (odeme != null) {
			try {
				this.odeme=(OdemeDTO) odeme.clone();
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("odeme",this.odeme);
			} catch (CloneNotSupportedException e) {
				this.odeme=new OdemeDTO();
			}
			str = "odemeGiris.jsf?faces-redirect=true";
		}
		return str;
	}

	public OdemeDataModel getOdemeDataModel() {
		return odemeDataModel;
	}

	public void setOdemeDataModel(OdemeDataModel odemeDataModel) {
		this.odemeDataModel = odemeDataModel;
	}

	public boolean isOdemeReq() {
		return odemeReq;
	}

	public void setOdemeReq(boolean odemeReq) {
		this.odemeReq = odemeReq;
	}

	public List<OdemeDTO> getOdenenList() {
		return odenenList;
	}

	public void setOdenenList(List<OdemeDTO> odenenList) {
		this.odenenList = odenenList;
	}

	public OdemeDataModel getOdenenDataModel() {
		return odenenDataModel;
	}

	public void setOdenenDataModel(OdemeDataModel odenenDataModel) {
		this.odenenDataModel = odenenDataModel;
	}

	public List<BasvuruListeDTO> getOdemeYapilacakListe() {
		return odemeYapilacakListe;
	}

	public void setOdemeYapilacakListe(List<BasvuruListeDTO> odemeYapilacakListe) {
		this.odemeYapilacakListe = odemeYapilacakListe;
	}

	public BasvuruListeModel getOdemeYapModel() {
		return odemeYapModel;
	}

	public void setOdemeYapModel(BasvuruListeModel odemeYapModel) {
		this.odemeYapModel = odemeYapModel;
	}

	public List<BasvuruListeDTO> getFilteredOdenecek() {
		return filteredOdenecek;
	}

	public void setFilteredOdenecek(List<BasvuruListeDTO> filteredOdenecek) {
		this.filteredOdenecek = filteredOdenecek;
	}

	public BasvuruListeDTO getSelectedOdenecek() {
		return selectedOdenecek;
	}

	public void setSelectedOdenecek(BasvuruListeDTO selectedOdenecek) {
		this.selectedOdenecek = selectedOdenecek;
	}

	public OdemeDTO getSelectedVal() {
		return selectedVal;
	}

	public void setSelectedVal(OdemeDTO selectedVal) {
		this.selectedVal = selectedVal;
	}

	public void odenecekListener(SelectEvent event) {
		BasvuruListeDTO b = (BasvuruListeDTO) (event.getObject());
		if (b != null) {
			odemeReq = true;
			this.odeme = new OdemeDTO();
			odeme.setBasvuruId(b.getBasvuruId());
			odeme.setBinaId(b.getBinaId());
			odeme.setBinaAdi(b.getBinaAdi());
			odeme.setOdemeKontroldeAlinsin(b.isOdemeKontroldeAlinsin());
			odeme.setToplamTutar(b.getKontrolTutari());
			odeme.setId(b.getId());
			headerText = b.getBinaAdi();

			Fieldset fieldset = (Fieldset) FacesContext.getCurrentInstance()
					.getViewRoot().findComponent(":formodeme:panelodeme");
			if (fieldset != null) {
				fieldset.setRendered(true);
			}

		}

	}

	public void odenenListener(SelectEvent event) {
		try {

			OdemeDTO b = (OdemeDTO) (event.getObject());
			if (b != null) {
				if (b.getKalan() > 0) {
					odemeReq = true;
				} else {
					odemeReq = false;
				}
				this.odeme = new OdemeDTO();
				this.odeme = (OdemeDTO) b.clone();
				headerText = b.getBinaAdi();

				Fieldset fieldset = (Fieldset) FacesContext
						.getCurrentInstance().getViewRoot()
						.findComponent(":formodeme:panelodeme");
				if (fieldset != null) {
					fieldset.setRendered(true);
				}

			}
		} catch (Exception e) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, fm);
		}

	}

	public BinaBean getBinaBean() {
		return binaBean;
	}

	public void setBinaBean(BinaBean binaBean) {
		this.binaBean = binaBean;
	}
	
	

}
