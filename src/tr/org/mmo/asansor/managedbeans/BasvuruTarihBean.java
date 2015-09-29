package tr.org.mmo.asansor.managedbeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.lightbox.LightBox;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.BasvuruListeDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.models.BasvuruListeModel;
import tr.org.mmo.asansor.util.DateUtils;
@ManagedBean
@ViewScoped
public class BasvuruTarihBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date ilkTarih=new Date();
	private Date sonTarih=new Date();
	
	@PostConstruct
	public void init(){
		
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.add(Calendar.DAY_OF_MONTH, +1);
		
		ilkTarih=cal.getTime();
		
	}
	
	public Date getIlkTarih() {
		return ilkTarih;
	}

	public void setIlkTarih(Date ilkTarih) {
		this.ilkTarih = ilkTarih;
	}

	public Date getSonTarih() {
		return sonTarih;
	}

	public void setSonTarih(Date sonTarih) {
		this.sonTarih = sonTarih;
	}

	
public void basvuruListele(ActionEvent event){
	 List<BasvuruListeDTO> basvuruListesi=new ArrayList<BasvuruListeDTO>();
	 BasvuruBean basvuruBean = (BasvuruBean) FacesContext
				.getCurrentInstance()
				.getApplication()
				.evaluateExpressionGet(FacesContext.getCurrentInstance(),
						"#{basvuruBean}", BasvuruBean.class);
	
	 List<BasvuruListeDTO> listt = new ArrayList<BasvuruListeDTO>();
	
	 if (basvuruBean!=null){
		 if (ilkTarih==null || sonTarih==null ){
			 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Tarih Seçiniz !"));
		 }
		
		 else{
			 boolean bool=true;
			 try{
					if (ilkTarih.compareTo(sonTarih)>0){
						bool=false;
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("İlk tarih son tarihten büyük olamaz !"));
					}
					if (DateUtils.differenceBetweenTwoDates(ilkTarih, sonTarih)>31){
						bool=false;
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Tarihler arası maximum 31 gün olabilir !"));
					}
				}catch(Exception e){
					
				}
			 if (bool){
		 
		try {
			basvuruBean.setSelectedBasvuru(new BasvuruListeDTO());
			basvuruBean.setBasvuruListesi(new ArrayList<BasvuruListeDTO>());
			basvuruBean.setBasvuruListe(new BasvuruListeModel(basvuruListesi));
			listt = basvuruBean.getBasvurular(ilkTarih,sonTarih);
	
		double kontrolTutari = 0.00;
		for (BasvuruListeDTO basvuruListe : listt) {
			boolean odemeYapilacak = true;
			kontrolTutari = 0.00;
			for (BasvuruAsansorDTO b : basvuruListe
					.getAsansorKontrolFiyatlari()) {
				kontrolTutari += b.getKontrolTutari();
				if (b.getKontrolTutari() <= 0
						&& !b.getKontrolTuru().equals("E")) {
					odemeYapilacak = false;
					break;
				}
			}
			
			String viewId = FacesContext.getCurrentInstance().getViewRoot()
					.getViewId();
			if (viewId.equals("/basvuruListesi.xhtml")) {
				if (odemeYapilacak) {
					if (kontrolTutari > 0) {
						basvuruListe
								.setKontrolTutari(kontrolTutari);
						basvuruListesi.add(basvuruListe);
					}
				}
			} else {
				if (!odemeYapilacak) {

					basvuruListesi.add(basvuruListe);
				}
			}
			

		}

		basvuruBean.setBasvuruListesi(basvuruListesi);
	
	basvuruBean.setBasvuruListe(new BasvuruListeModel(basvuruListesi));
		
	
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
		}
			 }
	 }
	 }
	
}

public void tarihListener(SelectEvent event){
	try{
		if (ilkTarih.compareTo(sonTarih)>0){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Hatalı Tarih Girişi"));
		}
		if (DateUtils.differenceBetweenTwoDates(ilkTarih, sonTarih)>31){
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Tarihler arası maximum 31 gün olabilir !"));
		}
		
	}catch(Exception e){
		
	}

}
}
