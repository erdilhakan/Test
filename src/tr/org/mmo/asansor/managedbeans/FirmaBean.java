package tr.org.mmo.asansor.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.dialog.Dialog;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;

import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.dto.BakimciFirmaIletisimDTO;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.util.IlIlceComparator;
import tr.org.mmo.asansor.util.Messages;

@ManagedBean
@ViewScoped
public class FirmaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5196681017438730700L;
	private FirmaDTO firma=new FirmaDTO();
	private boolean firmaIletisimReq=false;
	
	
	private TreeMap<String,Integer> ilceler=new TreeMap<String, Integer>(new IlIlceComparator());
	public FirmaDTO getFirma() {
		if (firma!=null){
			if (firma.getTseBelgesi()!=null && firma.getTseBelgesi().equals("E")){
				tseBelgesiVar=true;
			}else{
				tseBelgesiVar=false;
			}
		}
		return firma;
	}

	public void setFirma(FirmaDTO firma) {
		if (firma!=null){
			if (firma.getTseBelgesi()!=null && firma.getTseBelgesi().equals("E")){
				tseBelgesiVar=true;
			}else{
				tseBelgesiVar=false;
			}
		}
		this.firma = firma;
	}

	
	 public void submit() {
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
					.put("firmaBean", this);
			

		}
	
	 @PostConstruct
	 public void init(){
		if ( FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedFirma")!=null){;
		 			firma=(FirmaDTO)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedFirma");
		 			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selectedFirma");
					if (firma.getIl()>0){
						FacesContext context=FacesContext.getCurrentInstance();
						ApplicationBean bean= (ApplicationBean)context.getExternalContext().getApplicationMap().get("applicationBean");
					
						if (ilceler.size()<=0){
						ilceler=new TreeMap<String, Integer>(new IlIlceComparator());
						ilceler.putAll(bean.getIlceler().get(firma.getIl()));
						}
					}
			
		 	}
		   
	 }
	 
	 
	 public void firmaKaydet() {
			FacesContext context=FacesContext.getCurrentInstance();
	    	FacesMessage msg;
	    	Dialog d=(Dialog)context.getViewRoot().findComponent(":formb:firmaIletisim");
	    	try {
	    			
	    			
	    			firmaIletisimReq=d.isVisible()?true:false;
	    			int firmaId=0;
	    			firmaId=new FirmaBusiness().firmaKaydet(firma);
	    			firma.setKod(firmaId);	
		    			msg=new FacesMessage(Messages._FIRMAKAYDEDILDI_.getMesaj());
	    			
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				
			} catch (CRUDException e) {
				msg=new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			}
	     	context.addMessage(null,msg);
		}

	public TreeMap<String, Integer> getIlceler() {
		return ilceler;
	}

	public void setIlceler(TreeMap<String, Integer> ilceler) {
		this.ilceler = ilceler;
	}

	public void ilChange(AjaxBehaviorEvent event) {
		int ilKodu = Integer.parseInt(((UIInput) (event.getComponent())).getValue().toString());
		FacesContext context=FacesContext.getCurrentInstance();
		ApplicationBean bean= (ApplicationBean)context.getExternalContext().getApplicationMap().get("applicationBean");
	
		
		ilceler=new TreeMap<String, Integer>(new IlIlceComparator());
		ilceler.putAll(bean.getIlceler().get(ilKodu));
		
		
		
		
	}
	
	public void closeDialogListener(CloseEvent event){
		firmaIletisimReq=false;
		firma.setFirmaIletisim(new BakimciFirmaIletisimDTO());
		firma.setFirmaIletisimList(new ArrayList<BakimciFirmaIletisimDTO>());
		RequestContext.getCurrentInstance().reset(":formb:firmaIletisim");
	}
	
	public void iletisimKaydetListener(ActionEvent event){
		FacesContext context=FacesContext.getCurrentInstance();	
		FacesMessage msg;
		try {
			new FirmaBusiness().iletisimKaydet(firma.getFirmaIletisim(),firma.getKod());
			firma.getFirmaIletisimList().add(firma.getFirmaIletisim());
			firma.setFirmaIletisim(new BakimciFirmaIletisimDTO());
			RequestContext.getCurrentInstance().reset(":formb:firmaIletisimPanel");
		} catch (CRUDException e) {
			msg=new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null,msg);
		}
		
	}
	
	public void iletisimSil(BakimciFirmaIletisimDTO iletisim){
		if (iletisim!=null){
			FacesContext context=FacesContext.getCurrentInstance();	
			FacesMessage msg;
			try {
				new FirmaBusiness().iletisimSil(iletisim);
				firma.getFirmaIletisimList().remove(iletisim);
				
			} catch (CRUDException e) {
				msg=new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				context.addMessage(null,msg);
			}
		}
	}
	
	
	public void firmaIletisimListener(ActionEvent event){
		FacesContext context=FacesContext.getCurrentInstance();	
		FacesMessage msg;
		try {
			firma.setFirmaIletisimList(new FirmaBusiness().iletisimBilgisiGetir(firma.getKod()));
			firmaIletisimReq=true;
		} catch (CRUDException e) {
			msg=new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null,msg);
		}
		
	}

	public boolean isFirmaIletisimReq() {
		return firmaIletisimReq;
	}

	public void setFirmaIletisimReq(boolean firmaIletisimReq) {
		this.firmaIletisimReq = firmaIletisimReq;
	}
	private boolean tseBelgesiVar=false;
	
	public void tseBelgesiListener(AjaxBehaviorEvent event){
		try {
			UIInput inp=(UIInput)(event.getComponent());
			
			Object o=inp.getValue();
			
				tseBelgesiVar=o.toString().equals("E")?true:false;
			
		} catch (Exception e) {
			
		}
		
		
	}

	public boolean isTseBelgesiVar() {
		return tseBelgesiVar;
	}

	public void setTseBelgesiVar(boolean tseBelgesiVar) {
		this.tseBelgesiVar = tseBelgesiVar;
	}

	
	
	public void changeTarihListener(AjaxBehaviorEvent event){
		UIInput uii=(UIInput)event.getComponent();
		if (uii!=null){
			if (uii.getValue()!=null && uii.getValue().toString().equals("H")){
				if (uii.getId().equals("firmaServisSozlesme")){
					this.firma.setSozlesme_Tarihi(null);
					this.firma.setSozlesmeTarihi(null);
				}
				if (uii.getId().equals("firmaUygunlukBelgesi")){
					this.firma.setGecerlilik_Suresi(null);
					this.firma.setGecerlilikSuresi(null);
				}
			}
		}
		
	}
	
}
