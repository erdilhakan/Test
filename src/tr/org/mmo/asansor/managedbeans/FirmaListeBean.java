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

import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.models.FirmaDataModel;
import tr.org.mmo.asansor.util.FirmaComparator;
import tr.org.mmo.asansor.util.Messages;

@ManagedBean
@ViewScoped
public class FirmaListeBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8344847793557838646L;
	
	private List<FirmaDTO> firmaListSmall=new ArrayList<FirmaDTO>();
	private FirmaDTO slctFirma;
	private List<FirmaDTO> filteredList;
	private FirmaDataModel firmaListModel;
	
	
	public List<FirmaDTO> getFirmaListSmall() {
		return firmaListSmall;
	}

	public void setFirmaListSmall(List<FirmaDTO> firmaListSmall) {
		this.firmaListSmall = firmaListSmall;
	}
	
	
	public FirmaDTO getSlctFirma() {
		return slctFirma;
	}

	public void setSlctFirma(FirmaDTO slctFirma) {
		this.slctFirma = slctFirma;
	}

	public List<FirmaDTO> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<FirmaDTO> filteredList) {
		this.filteredList = filteredList;
	}

	public FirmaDataModel getFirmaListModel() {
		return firmaListModel;
	}

	public void setFirmaListModel(FirmaDataModel firmaListModel) {
		this.firmaListModel = firmaListModel;
	}

	public void listeGetir(){
		
			firmaListSmall= new ArrayList<FirmaDTO>();
			
			try {
				firmaListSmall=new FirmaBusiness().listeGetir();
				
				Collections.sort(firmaListSmall,new FirmaComparator());
				
				firmaListModel=new FirmaDataModel(firmaListSmall);
			} catch (CRUDException e) {

				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
				
				
				e.printStackTrace();
			}
			
			
		
		
	}
	
	@PostConstruct
	public void init(){
		  listeGetir();
	}
	
	
	public String onRowSelect(FirmaDTO firma) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedFirma", firma);
		
		return "bakimcifirmakayit?faces-redirect=true";
	}	
	
	public void firmaSil(FirmaDTO firma) {
		if (firma!=null){
		try {
			new FirmaBusiness().firmaSil(firma.getKod());
			try{
			firmaListSmall.remove(firma);
			}catch(Exception e){
				firmaListSmall=new ArrayList<FirmaDTO>();
			}
			Collections.sort(firmaListSmall,new FirmaComparator());
			
			firmaListModel=new FirmaDataModel(firmaListSmall);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._FIRMASILINDI_.getMesaj()));
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
		}
		}
		
	}	
	
	
}
