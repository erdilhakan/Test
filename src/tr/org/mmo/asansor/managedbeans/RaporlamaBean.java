package tr.org.mmo.asansor.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import tr.org.mmo.asansor.business.InformationBusiness;
import tr.org.mmo.asansor.dto.BelediyeDTO;
import tr.org.mmo.asansor.dto.BelediyeKontrolDTO;
import tr.org.mmo.asansor.dto.BinaKontrolVeOdemelerDTO;
import tr.org.mmo.asansor.dto.BirimDTO;
import tr.org.mmo.asansor.dto.CihazOdemeKontrolDTO;
import tr.org.mmo.asansor.dto.DenetciKontrolDTO;
import tr.org.mmo.asansor.dto.EtiketYapiTipKontrolDTO;
import tr.org.mmo.asansor.dto.IlIlceDTO;
import tr.org.mmo.asansor.dto.OdemeKontrolDTO;
import tr.org.mmo.asansor.dto.SubeDTO;
import tr.org.mmo.asansor.dto.TemsilcilikDTO;
import tr.org.mmo.asansor.dto.TemsilcilikIlIlceDTO;
import tr.org.mmo.asansor.models.BelediyeKontrolDataModel;
import tr.org.mmo.asansor.models.BinaKontrolVeOdemeDataModel;
import tr.org.mmo.asansor.models.CihazOdemeKontrolDataModel;
import tr.org.mmo.asansor.models.DenetciKontrolDataModel;
import tr.org.mmo.asansor.models.EtiketYapiTipKontrolDataModel;
import tr.org.mmo.asansor.models.OdemeKontrolDataModel;
import tr.org.mmo.asansor.util.Messages;

@ManagedBean(name="information")
@ViewScoped
public class RaporlamaBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<BelediyeDTO> belediyeList;
	private int selectedIl;
	private List<TemsilcilikDTO> temsilcilikler = new ArrayList<TemsilcilikDTO>();
	private EtiketYapiTipKontrolDataModel etiketYapiTipKontrolData;
	private List<EtiketYapiTipKontrolDTO> etiketYapiTipKontrolList;
	private List<EtiketYapiTipKontrolDTO> filteredEtiketYapiTipKontrol;
	private List<BinaKontrolVeOdemelerDTO> filteredBinaKontrolVeOdeme;
	private List<BinaKontrolVeOdemelerDTO> binaKontrolVeOdemeList;
	
	private BinaKontrolVeOdemeDataModel binaKontrolVeOdemeData;
	private List<Integer> selectedSube;
	private List<Integer> selectedBelediye;
	private List<SubeDTO> subeList;
	private boolean subeHepsiniSec=false;
	private boolean belediyeHepsiniSec=false;
	private boolean temsilcilikHepsiniSec=false;
	private List<Integer> selectedTemsilcilik;
	private Date tarih1;
	private Date tarih2;
	private DenetciKontrolDataModel denetciKontrolData;
	private List<DenetciKontrolDTO> denetciKontrolList;
	private List<BelediyeKontrolDTO> belediyeKontrolList;
	private BelediyeKontrolDataModel belediyeKontrolData;
	private List<DenetciKontrolDTO> filteredDenetciKontrol;
	private List<BelediyeKontrolDTO> filteredBelediyeKontrol;
	private List<SubeDTO> selectedSubeler=new ArrayList<SubeDTO>();
	private List<BelediyeDTO> selectedBelediyeler=new ArrayList<BelediyeDTO>();
	@ManagedProperty("#{applicationBean}")
	private ApplicationBean applicationBean;
	private List<IlIlceDTO> kullaniciIller;
	private List<EtiketYapiTipKontrolDTO> belediyeOdemeKontrolList;
	private EtiketYapiTipKontrolDataModel belediyeOdemeKontrolData;
	private List<EtiketYapiTipKontrolDTO> filteredbelediyeOdemeKontrol;
	private List<OdemeKontrolDTO> odemeKontrolList;
	private OdemeKontrolDataModel odemeKontrolData;
	private List<EtiketYapiTipKontrolDTO> filteredOdemeKontrol;
	private List<CihazOdemeKontrolDTO> cihazOdemeKontrolList;
	private CihazOdemeKontrolDataModel cihazOdemeKontrolData;
	private List<CihazOdemeKontrolDTO> filteredCihazOdemeKontrol; 
	
	private List<TemsilcilikDTO> selectedTemsilcilikler=new ArrayList<TemsilcilikDTO>();
	public List<DenetciKontrolDTO> getFilteredDenetciKontrol() {
		return filteredDenetciKontrol;
	}




	public void setFilteredDenetciKontrol(
			List<DenetciKontrolDTO> filteredDenetciKontrol) {
		this.filteredDenetciKontrol = filteredDenetciKontrol;
	}




	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	
	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	@PostConstruct
	public void init(){
		belediyeList=new ArrayList<BelediyeDTO>();
		temsilcilikler = new ArrayList<TemsilcilikDTO>();
		kullaniciIller=new ArrayList<IlIlceDTO>();
	
		belediyeKontrolList=new ArrayList<BelediyeKontrolDTO>();
		belediyeKontrolData=new BelediyeKontrolDataModel(belediyeKontrolList);
		etiketYapiTipKontrolList=new ArrayList<EtiketYapiTipKontrolDTO>();
		etiketYapiTipKontrolData=new EtiketYapiTipKontrolDataModel(etiketYapiTipKontrolList);
		selectedTemsilcilik=new ArrayList<Integer>();
		selectedSube=new ArrayList<Integer>();
		selectedBelediye=new ArrayList<Integer>();
		selectedBelediyeler=new ArrayList<BelediyeDTO>();
		
		denetciKontrolList=new ArrayList<DenetciKontrolDTO>();
		denetciKontrolData=new DenetciKontrolDataModel(denetciKontrolList);
		binaKontrolVeOdemeList=new ArrayList<BinaKontrolVeOdemelerDTO>();
		
		binaKontrolVeOdemeData=new BinaKontrolVeOdemeDataModel(binaKontrolVeOdemeList);
		subeList=new ArrayList<SubeDTO>();
		try{
			for (SubeDTO s:sessionBean.getSubeler()){
				for (BirimDTO b:loginBean.getKullanici().getBirimler()){
					if (b.getIl()==s.getIl()){
						subeList.remove(s);
						subeList.add(s);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	

	
	




	public List<TemsilcilikDTO> getTemsilcilikler() {
		return temsilcilikler;
	}

	public void setTemsilcilikler(List<TemsilcilikDTO> temsilcilikler) {
		this.temsilcilikler = temsilcilikler;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}




	

	

	



public void setSelectedTemsilcilik(List<Integer> selectedTemsilcilik) {
		this.selectedTemsilcilik = selectedTemsilcilik;
	}




@SuppressWarnings("unchecked")
public void subeChangeEvent(ValueChangeEvent event){
	selectedSubeler=new ArrayList<SubeDTO>();
	
	selectedSube=new ArrayList<Integer>();
	selectedSube=(ArrayList<Integer>)event.getNewValue();
	temsilcilikler = new ArrayList<TemsilcilikDTO>();
	denetciKontrolList=new ArrayList<DenetciKontrolDTO>();
	denetciKontrolData=new DenetciKontrolDataModel(denetciKontrolList);
	belediyeKontrolList=new ArrayList<BelediyeKontrolDTO>();
	belediyeKontrolData=new BelediyeKontrolDataModel(belediyeKontrolList);
	etiketYapiTipKontrolList=new ArrayList<EtiketYapiTipKontrolDTO>();
	etiketYapiTipKontrolData=new EtiketYapiTipKontrolDataModel(etiketYapiTipKontrolList);
	
	for (int i=0;i<selectedSube.size();i++){
		for (SubeDTO s1 : subeList) {
			if (String.valueOf(selectedSube.get(i)).equals(String.valueOf(s1.getKod()))) {
				
				selectedSubeler.add(s1);
			}
	}

		for (TemsilcilikDTO t : sessionBean.getTemsilcilikler()) {
			if (String.valueOf(selectedSube.get(i)).equals(String.valueOf(t.getSubesi()))) {
				
				temsilcilikler.add(t);
			}
	}
	}
	
}

@SuppressWarnings("unchecked")
public void belediyeChangeEvent(ValueChangeEvent event){
	selectedBelediyeler=new ArrayList<BelediyeDTO>();
	
	selectedBelediye=new ArrayList<Integer>();
	selectedBelediye=(ArrayList<Integer>)event.getNewValue();
	belediyeHepsiniSec=false;
	tarih1=null;
	tarih2=null;
			
	binaKontrolVeOdemeList=new ArrayList<BinaKontrolVeOdemelerDTO>();
	binaKontrolVeOdemeData=new BinaKontrolVeOdemeDataModel(binaKontrolVeOdemeList);
	etiketYapiTipKontrolList=new ArrayList<EtiketYapiTipKontrolDTO>();	
	etiketYapiTipKontrolData=new EtiketYapiTipKontrolDataModel(etiketYapiTipKontrolList);
	for (int i=0;i<selectedBelediye.size();i++){
		for (BelediyeDTO s1 : loginBean.getBelediyeList()) {
			if (String.valueOf(selectedBelediye.get(i)).equals(String.valueOf(s1.getKod()))) {
				
				selectedBelediyeler.add(s1);
			}
	}

		
	}
	
}	

@SuppressWarnings("unchecked")
public void temsilcilikChangeEvent(ValueChangeEvent event){
	
	//ArrayList<Integer> t=(ArrayList<Integer>)event.getNewValue();
	selectedTemsilcilikler=new ArrayList<TemsilcilikDTO>();
	selectedTemsilcilik=new ArrayList<Integer>();
	selectedTemsilcilik=(ArrayList<Integer>)event.getNewValue();
	denetciKontrolList=new ArrayList<DenetciKontrolDTO>();
	denetciKontrolData=new DenetciKontrolDataModel(denetciKontrolList);
	belediyeKontrolList=new ArrayList<BelediyeKontrolDTO>();
	belediyeKontrolData=new BelediyeKontrolDataModel(belediyeKontrolList);
	etiketYapiTipKontrolList=new ArrayList<EtiketYapiTipKontrolDTO>();
	etiketYapiTipKontrolData=new EtiketYapiTipKontrolDataModel(etiketYapiTipKontrolList);
	
	for (int i=0;i<selectedTemsilcilik.size();i++){
	for (TemsilcilikDTO t : temsilcilikler) {
		if (String.valueOf(selectedTemsilcilik.get(i)).equals(String.valueOf(t.getKod()))) {
			
			selectedTemsilcilikler.add(t);
		}
	}
	}
	/*for (int i=0;i<t.size();i++){
		selectedTemsilcilik.add(Integer.parseInt(t.get(i)));
	}*/
	
	
}


public List<Integer> getSelectedSube() {
	return selectedSube;
}




public void setSelectedSube(List<Integer> selectedSube) {
	this.selectedSube = selectedSube;
}




public List<Integer> getSelectedTemsilcilik() {
	return selectedTemsilcilik;
}




public boolean isSubeHepsiniSec() {
	return subeHepsiniSec;
}




public void setSubeHepsiniSec(boolean subeHepsiniSec) {
	this.subeHepsiniSec = subeHepsiniSec;
}




public boolean isTemsilcilikHepsiniSec() {
	return temsilcilikHepsiniSec;
}




public void setTemsilcilikHepsiniSec(boolean temsilcilikHepsiniSec) {
	this.temsilcilikHepsiniSec = temsilcilikHepsiniSec;
}



public void boolSubeListener(AjaxBehaviorEvent event){
	
	
		selectedSube=new ArrayList<Integer>();
		selectedTemsilcilik=new ArrayList<Integer>();
		selectedSubeler=new ArrayList<SubeDTO>();
		selectedTemsilcilikler=new ArrayList<TemsilcilikDTO>();
		denetciKontrolList=new ArrayList<DenetciKontrolDTO>();
		denetciKontrolData=new DenetciKontrolDataModel(denetciKontrolList);
		belediyeKontrolList=new ArrayList<BelediyeKontrolDTO>();
		belediyeKontrolData=new BelediyeKontrolDataModel(belediyeKontrolList);
		etiketYapiTipKontrolList=new ArrayList<EtiketYapiTipKontrolDTO>();
		etiketYapiTipKontrolData=new EtiketYapiTipKontrolDataModel(etiketYapiTipKontrolList);
		
		tarih1=null;
		tarih2=null;
	if (subeHepsiniSec){
	
	for (SubeDTO s:sessionBean.getSubeler()){
		selectedSube.add(s.getKod());
	}
for (int i=0;i<selectedSube.size();i++){
	for (SubeDTO s1 : subeList) {
		if (String.valueOf(selectedSube.get(i)).equals(String.valueOf(s1.getKod()))) {
			
			selectedSubeler.add(s1);
		}
}
		for (TemsilcilikDTO t : sessionBean.getTemsilcilikler()) {
			if (String.valueOf(selectedSube.get(i)).equals(String.valueOf(t.getSubesi()))) {
				
				temsilcilikler.add(t);
				selectedTemsilcilikler.add(t);
			}
	}
	}
	
	
	}
	
}

public void boolBelediyeListener(AjaxBehaviorEvent event){
	selectedBelediye=new ArrayList<Integer>();
	selectedBelediyeler=new ArrayList<BelediyeDTO>();
	binaKontrolVeOdemeList=new ArrayList<BinaKontrolVeOdemelerDTO>();
	binaKontrolVeOdemeData=new BinaKontrolVeOdemeDataModel(binaKontrolVeOdemeList);
	
	tarih1=null;
	tarih2=null;
if (belediyeHepsiniSec){

for (BelediyeDTO s:loginBean.getBelediyeList()){
	selectedBelediye.add(s.getKod());
	selectedBelediyeler.add(s);
}
}
}

public void boolTemsilcilikListener(AjaxBehaviorEvent event){


		selectedTemsilcilik=new ArrayList<Integer>();
		selectedTemsilcilikler=new ArrayList<TemsilcilikDTO>();
		denetciKontrolList=new ArrayList<DenetciKontrolDTO>();
		denetciKontrolData=new DenetciKontrolDataModel(denetciKontrolList);
		belediyeKontrolList=new ArrayList<BelediyeKontrolDTO>();
		belediyeKontrolData=new BelediyeKontrolDataModel(belediyeKontrolList);
		etiketYapiTipKontrolList=new ArrayList<EtiketYapiTipKontrolDTO>();
		etiketYapiTipKontrolData=new EtiketYapiTipKontrolDataModel(etiketYapiTipKontrolList);
		
		if (temsilcilikHepsiniSec){
		for (TemsilcilikDTO t:temsilcilikler){
			selectedTemsilcilik.add(t.getKod());
			selectedTemsilcilikler.add(t);
		}
		
}


	
}




public Date getTarih1() {
	return tarih1;
}




public void setTarih1(Date tarih1) {
	this.tarih1 = tarih1;
}




public Date getTarih2() {
	return tarih2;
}




public void setTarih2(Date tarih2) {
	this.tarih2 = tarih2;
}




public DenetciKontrolDataModel getDenetciKontrolData() {
	return denetciKontrolData;
}




public void setDenetciKontrolData(DenetciKontrolDataModel denetciKontrolData) {
	this.denetciKontrolData = denetciKontrolData;
}




public List<DenetciKontrolDTO> getDenetciKontrolList() {
	return denetciKontrolList;
}




public void setDenetciKontrolList(List<DenetciKontrolDTO> denetciKontrolList) {
	this.denetciKontrolList = denetciKontrolList;
}



public List<EtiketYapiTipKontrolDTO> getBelediyeOdemeKontrolList() {
	return belediyeOdemeKontrolList;
}




public void setBelediyeOdemeKontrolList(
		List<EtiketYapiTipKontrolDTO> belediyeOdemeKontrolList) {
	this.belediyeOdemeKontrolList = belediyeOdemeKontrolList;
}




public EtiketYapiTipKontrolDataModel getBelediyeOdemeKontrolData() {
	return belediyeOdemeKontrolData;
}




public void setBelediyeOdemeKontrolData(
		EtiketYapiTipKontrolDataModel belediyeOdemeKontrolData) {
	this.belediyeOdemeKontrolData = belediyeOdemeKontrolData;
}




public List<EtiketYapiTipKontrolDTO> getFilteredbelediyeOdemeKontrol() {
	return filteredbelediyeOdemeKontrol;
}




public void setFilteredbelediyeOdemeKontrol(
		List<EtiketYapiTipKontrolDTO> filteredbelediyeOdemeKontrol) {
	this.filteredbelediyeOdemeKontrol = filteredbelediyeOdemeKontrol;
}




public List<OdemeKontrolDTO> getOdemeKontrolList() {
	return odemeKontrolList;
}




public void setOdemeKontrolList(List<OdemeKontrolDTO> odemeKontrolList) {
	this.odemeKontrolList = odemeKontrolList;
}




public OdemeKontrolDataModel getOdemeKontrolData() {
	return odemeKontrolData;
}




public void setOdemeKontrolData(OdemeKontrolDataModel odemeKontrolData) {
	this.odemeKontrolData = odemeKontrolData;
}




public List<EtiketYapiTipKontrolDTO> getFilteredOdemeKontrol() {
	return filteredOdemeKontrol;
}




public void setFilteredOdemeKontrol(
		List<EtiketYapiTipKontrolDTO> filteredOdemeKontrol) {
	this.filteredOdemeKontrol = filteredOdemeKontrol;
}




public List<CihazOdemeKontrolDTO> getCihazOdemeKontrolList() {
	return cihazOdemeKontrolList;
}




public void setCihazOdemeKontrolList(
		List<CihazOdemeKontrolDTO> cihazOdemeKontrolList) {
	this.cihazOdemeKontrolList = cihazOdemeKontrolList;
}




public CihazOdemeKontrolDataModel getCihazOdemeKontrolData() {
	return cihazOdemeKontrolData;
}




public void setCihazOdemeKontrolData(
		CihazOdemeKontrolDataModel cihazOdemeKontrolData) {
	this.cihazOdemeKontrolData = cihazOdemeKontrolData;
}




public List<CihazOdemeKontrolDTO> getFilteredCihazOdemeKontrol() {
	return filteredCihazOdemeKontrol;
}




public void setFilteredCihazOdemeKontrol(
		List<CihazOdemeKontrolDTO> filteredCihazOdemeKontrol) {
	this.filteredCihazOdemeKontrol = filteredCihazOdemeKontrol;
}




private boolean tarihKontrol(Object o, UIComponent uc) {
	boolean bool = true;

	
	
	if (tarih1 != null && tarih2 != null
			&& tarih2.compareTo(tarih1)<=0) {
		bool = false;

	}
	return bool;

}
public void dateBlurListener(AjaxBehaviorEvent event){
	Object o = event.getSource();
	UIComponent uc = event.getComponent();
	
denetciKontrolList=new ArrayList<DenetciKontrolDTO>();
denetciKontrolData=new DenetciKontrolDataModel(denetciKontrolList);
belediyeKontrolList=new ArrayList<BelediyeKontrolDTO>();
belediyeKontrolData=new BelediyeKontrolDataModel(belediyeKontrolList);
etiketYapiTipKontrolList=new ArrayList<EtiketYapiTipKontrolDTO>();
etiketYapiTipKontrolData=new EtiketYapiTipKontrolDataModel(etiketYapiTipKontrolList);
binaKontrolVeOdemeList=new ArrayList<BinaKontrolVeOdemelerDTO>();

binaKontrolVeOdemeData=new BinaKontrolVeOdemeDataModel(binaKontrolVeOdemeList);

	if (!tarihKontrol(o, uc)) {

		FacesMessage msg = new FacesMessage(
				Messages._HATALITARIH_.getMesaj());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
public void dateSelectListener(SelectEvent event){
	
	

	Object o = event.getObject();
	UIComponent uc = event.getComponent();
	
denetciKontrolList=new ArrayList<DenetciKontrolDTO>();
denetciKontrolData=new DenetciKontrolDataModel(denetciKontrolList);
belediyeKontrolList=new ArrayList<BelediyeKontrolDTO>();
belediyeKontrolData=new BelediyeKontrolDataModel(belediyeKontrolList);
etiketYapiTipKontrolList=new ArrayList<EtiketYapiTipKontrolDTO>();
etiketYapiTipKontrolData=new EtiketYapiTipKontrolDataModel(etiketYapiTipKontrolList);
binaKontrolVeOdemeList=new ArrayList<BinaKontrolVeOdemelerDTO>();

binaKontrolVeOdemeData=new BinaKontrolVeOdemeDataModel(binaKontrolVeOdemeList);

	if (!tarihKontrol(o, uc)) {

		FacesMessage msg = new FacesMessage(
				Messages._HATALITARIH_.getMesaj());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}

public void denetciBazliKontroller(ActionEvent event){
	try{
		if (selectedSubeler!=null && selectedSubeler.size()>0){
			if (tarih1!=null && tarih1.getTime()>0 && tarih2!=null && tarih2.getTime()>0){
				denetciKontrolList=new ArrayList<DenetciKontrolDTO>();
				boolean temsilcilikSecildi=false;
				Map<Integer,Integer> subeIller=new HashMap<Integer, Integer>();
				Map<Integer,ArrayList<Integer>> temsilcilikIllerIlceler=new HashMap<Integer, ArrayList<Integer>>();
				
				if (selectedTemsilcilikler!=null && selectedTemsilcilikler.size()>0){
				temsilcilikIllerIlceler=temsilcilikIlIlce(selectedTemsilcilikler);
				temsilcilikSecildi=true;
				}else{
					subeIller=subeIller(selectedSubeler,selectedTemsilcilikler);
				}
	denetciKontrolList=	InformationBusiness.INSTANCE.getDenetciBazliKontroller(tarih1,tarih2,subeIller,temsilcilikIllerIlceler,temsilcilikSecildi);
	if (denetciKontrolList.size()>0){
		for (DenetciKontrolDTO d:denetciKontrolList){
			toplam +=d.getToplam();
		}
		denetciKontrolData=new DenetciKontrolDataModel(denetciKontrolList);
	}
	else
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._TARIHGIRINIZ_.getMesaj()));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SUBESECINIZ_.getMesaj()));
		}
	}catch(Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
	}
}


public void belediyelereGoreKontrollerDagilimi(ActionEvent event){
	try{
		if (selectedSubeler!=null && selectedSubeler.size()>0){
			if (tarih1!=null && tarih1.getTime()>0 && tarih2!=null && tarih2.getTime()>0){
				belediyeKontrolList=new ArrayList<BelediyeKontrolDTO>();
				boolean temsilcilikSecildi=false;
				Map<Integer,Integer> subeIller=new HashMap<Integer, Integer>();
				Map<Integer,ArrayList<Integer>> temsilcilikIllerIlceler=new HashMap<Integer, ArrayList<Integer>>();
				
				if (selectedTemsilcilikler!=null && selectedTemsilcilikler.size()>0){
				temsilcilikIllerIlceler=temsilcilikIlIlce(selectedTemsilcilikler);
				temsilcilikSecildi=true;
				}else{
					subeIller=subeIller(selectedSubeler,selectedTemsilcilikler);
				}
				
	belediyeKontrolList=	InformationBusiness.INSTANCE.getBelediyelereGoreKontroller(tarih1,tarih2,subeIller,temsilcilikIllerIlceler,temsilcilikSecildi);
	if (belediyeKontrolList.size()>0){
		for (BelediyeKontrolDTO b:belediyeKontrolList){
			toplam +=b.getToplam();
		}
		belediyeKontrolData=new BelediyeKontrolDataModel(belediyeKontrolList);
	}
	else
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._TARIHGIRINIZ_.getMesaj()));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SUBESECINIZ_.getMesaj()));
		}
	}catch(Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
	}
}

public void etiketVeYapiTiplerineGoreKontrollerDagilimi(ActionEvent event){
	try{
		if (selectedSubeler!=null && selectedSubeler.size()>0){
			if (tarih1!=null && tarih1.getTime()>0 && tarih2!=null && tarih2.getTime()>0){
				etiketYapiTipKontrolList=new ArrayList<EtiketYapiTipKontrolDTO>();
				boolean temsilcilikSecildi=false;
				Map<Integer,Integer> subeIller=new HashMap<Integer, Integer>();
				Map<Integer,ArrayList<Integer>> temsilcilikIllerIlceler=new HashMap<Integer, ArrayList<Integer>>();
				
				if (selectedTemsilcilikler!=null && selectedTemsilcilikler.size()>0){
				temsilcilikIllerIlceler=temsilcilikIlIlce(selectedTemsilcilikler);
				temsilcilikSecildi=true;
				}else{
					subeIller=subeIller(selectedSubeler,selectedTemsilcilikler);
				}
			List<EtiketYapiTipKontrolDTO> list=new ArrayList<EtiketYapiTipKontrolDTO>();	
	list=	InformationBusiness.INSTANCE.getEtiketVeYapiTiplerineGoreKontroller(tarih1,tarih2,subeIller,temsilcilikIllerIlceler,temsilcilikSecildi);
	
	if (list.size()>0){
		Map<Integer, EtiketYapiTipKontrolDTO> map=new HashMap<Integer, EtiketYapiTipKontrolDTO>();	
		for (EtiketYapiTipKontrolDTO e:list){
			EtiketYapiTipKontrolDTO e1=map.get(e.getBelediyeKod())==null?new EtiketYapiTipKontrolDTO():map.get(e.getBelediyeKod());
			e1.setBelediyeAdi(e.getBelediyeAdi());
			e1.setBelediyeKod(e.getBelediyeKod());
			
			switch (e.getCihazTipId()) {
			case 66:
			e1.setElektrikliKontrolAdet(e1.getElektrikliKontrolAdet()+1);	
				break;
			case 68:
				e1.setHidrolikKontrolAdet(e1.getHidrolikKontrolAdet()+1);	
					break;
			default:
				break;
			}
			e1.setAsansorTipToplam(e1.getAsansorTipToplam()+1);
			
			if(e.getKontrolTuru().equals("N")){
				e1.setNormalKontrolAdet(e1.getNormalKontrolAdet()+1);
			}else if(e.getKontrolTuru().equals("E")){
				e1.setEksiklikKontrolAdet(e1.getEksiklikKontrolAdet()+1);
			}
			
			if (e.getEtiket().equals("K")){
				e1.setKirmiziEtiketAdet(e1.getKirmiziEtiketAdet()+1);
			}else if (e.getEtiket().equals("S")){
				e1.setSariEtiketAdet(e1.getSariEtiketAdet()+1);
			}else if (e.getEtiket().equals("Y")){
				e1.setYesilEtiketAdet(e1.getYesilEtiketAdet()+1);
			}else if (e.getEtiket().equals("M")){
				e1.setMaviEtiketAdet(e1.getMaviEtiketAdet()+1);
			}else{
				e1.setDigerEtiketAdet(e1.getDigerEtiketAdet()+1);
			}
			e1.setEtiketToplamAdet(e1.getEtiketToplamAdet()+1);
			switch (e.getYapiTipi()) {
			case 1:
				e1.setIsyeriAdet(e1.getIsyeriAdet()+1);
				break;
			case 2:
				e1.setIsyeriAdet(e1.getIsyeriAdet()+1);
				break;
			case 3:
				e1.setBinaAdet(e1.getBinaAdet()+1);
				break;
			case 4:
				e1.setBinaAdet(e1.getBinaAdet()+1);
				break;
			case 5:
				e1.setIsyeriAdet(e1.getIsyeriAdet()+1);
				break;
			case 6:
				e1.setDigerBinaTipiAdet(e1.getDigerBinaTipiAdet()+1);
				break;
			case 7:
				e1.setDigerBinaTipiAdet(e1.getDigerBinaTipiAdet()+1);
				break;
			case 8:
				e1.setIsyeriAdet(e1.getIsyeriAdet()+1);
				break;
			case 9:
				e1.setIsyeriAdet(e1.getIsyeriAdet()+1);
				break;
			case 10:
				e1.setDigerBinaTipiAdet(e1.getDigerBinaTipiAdet()+1);
				break;
			case 11:
				e1.setIsyeriAdet(e1.getIsyeriAdet()+1);
				break;
			case 12:
				e1.setKamuBinasiAdet(e1.getKamuBinasiAdet()+1);
				break;
			case 13:
				e1.setDigerBinaTipiAdet(e1.getDigerBinaTipiAdet()+1);
				break;
			case 14:
				e1.setKonutAdet(e1.getKonutAdet()+1);
				break;
			case 15:
				e1.setKonutAdet(e1.getKonutAdet()+1);
				break;
			case 16:
				e1.setKonutAdet(e1.getKonutAdet()+1);
				break;
			default:
				e1.setDigerBinaTipiAdet(e1.getDigerBinaTipiAdet()+1);
				break;
			}
			
			map.put(e.getBelediyeKod(), e1);
			
		}
			Set<Integer> set=map.keySet();
			Iterator<Integer> iter=set.iterator();
			while (iter.hasNext()){
				int i=iter.next();
				etiketYapiTipKontrolList.add(map.get(i));
			}
		etiketYapiTipKontrolData=new EtiketYapiTipKontrolDataModel(etiketYapiTipKontrolList);
	}
	else
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._TARIHGIRINIZ_.getMesaj()));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SUBESECINIZ_.getMesaj()));
		}
	}catch(Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
	}
	belediyeOdemeler();
}


public void binaKontrolVeOdemeler(ActionEvent event){
	try{
		if (selectedBelediyeler!=null && selectedBelediyeler.size()>0){
			if (tarih1!=null && tarih1.getTime()>0 && tarih2!=null && tarih2.getTime()>0){
				
				binaKontrolVeOdemeList=new ArrayList<BinaKontrolVeOdemelerDTO>();
				binaKontrolVeOdemeData=new BinaKontrolVeOdemeDataModel(binaKontrolVeOdemeList);
				binaKontrolVeOdemeList=	InformationBusiness.INSTANCE.getBinaKontrolVeOdeme(tarih1,tarih2,selectedBelediye);
	if (binaKontrolVeOdemeList.size()>0){
		
		binaKontrolVeOdemeData=new BinaKontrolVeOdemeDataModel(binaKontrolVeOdemeList);
	}
	else
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._TARIHGIRINIZ_.getMesaj()));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._BELEDIYESECILMEDI_.getMesaj()));
		}
	}catch(Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
	}
}


public void belediyeBilgi(ActionEvent event){
	try{
		if (selectedBelediyeler!=null && selectedBelediyeler.size()>0){
			if (tarih1!=null && tarih1.getTime()>0 && tarih2!=null && tarih2.getTime()>0){
				
				binaKontrolVeOdemeList=new ArrayList<BinaKontrolVeOdemelerDTO>();
				binaKontrolVeOdemeData=new BinaKontrolVeOdemeDataModel(binaKontrolVeOdemeList);
				binaKontrolVeOdemeList=	InformationBusiness.INSTANCE.getBelediyeBilgi(tarih1,tarih2,selectedBelediye);
	if (binaKontrolVeOdemeList.size()>0){
		
		binaKontrolVeOdemeData=new BinaKontrolVeOdemeDataModel(binaKontrolVeOdemeList);
		etiketYapiTipKontrolList=new ArrayList<EtiketYapiTipKontrolDTO>();	
		etiketYapiTipKontrolData=new EtiketYapiTipKontrolDataModel(etiketYapiTipKontrolList);
		
		
			Map<Integer, EtiketYapiTipKontrolDTO> map=new HashMap<Integer, EtiketYapiTipKontrolDTO>();	
			for (BinaKontrolVeOdemelerDTO e:binaKontrolVeOdemeList){
				EtiketYapiTipKontrolDTO e1=map.get(e.getBelediyeKod())==null?new EtiketYapiTipKontrolDTO():map.get(e.getBelediyeKod());
				e1.setBelediyeAdi(e.getBelediyeAdi());
				e1.setBelediyeKod(e.getBelediyeKod());
				
				switch (e.getCihazTipId()) {
				case 66:
				e1.setElektrikliKontrolAdet(e1.getElektrikliKontrolAdet()+1);	
					break;
				case 68:
					e1.setHidrolikKontrolAdet(e1.getHidrolikKontrolAdet()+1);	
						break;
				default:
					break;
				}
				e1.setAsansorTipToplam(e1.getAsansorTipToplam()+1);
				
				if (e.getEtiket().equals("K")){
					e1.setKirmiziEtiketAdet(e1.getKirmiziEtiketAdet()+1);
				}else if (e.getEtiket().equals("S")){
					e1.setSariEtiketAdet(e1.getSariEtiketAdet()+1);
				}else if (e.getEtiket().equals("Y")){
					e1.setYesilEtiketAdet(e1.getYesilEtiketAdet()+1);
				}else if (e.getEtiket().equals("M")){
					e1.setMaviEtiketAdet(e1.getMaviEtiketAdet()+1);
				}else{
					e1.setDigerEtiketAdet(e1.getDigerEtiketAdet()+1);
				}
				e1.setEtiketToplamAdet(e1.getEtiketToplamAdet()+1);
				switch (e.getYapiTipi()) {
				case 1:
					e1.setIsyeriAdet(e1.getIsyeriAdet()+1);
					break;
				case 2:
					e1.setIsyeriAdet(e1.getIsyeriAdet()+1);
					break;
				case 3:
					e1.setBinaAdet(e1.getBinaAdet()+1);
					break;
				case 4:
					e1.setBinaAdet(e1.getBinaAdet()+1);
					break;
				case 5:
					e1.setIsyeriAdet(e1.getIsyeriAdet()+1);
					break;
				case 6:
					e1.setDigerBinaTipiAdet(e1.getDigerBinaTipiAdet()+1);
					break;
				case 7:
					e1.setDigerBinaTipiAdet(e1.getDigerBinaTipiAdet()+1);
					break;
				case 8:
					e1.setIsyeriAdet(e1.getIsyeriAdet()+1);
					break;
				case 9:
					e1.setIsyeriAdet(e1.getIsyeriAdet()+1);
					break;
				case 10:
					e1.setDigerBinaTipiAdet(e1.getDigerBinaTipiAdet()+1);
					break;
				case 11:
					e1.setIsyeriAdet(e1.getIsyeriAdet()+1);
					break;
				case 12:
					e1.setKamuBinasiAdet(e1.getKamuBinasiAdet()+1);
					break;
				case 13:
					e1.setDigerBinaTipiAdet(e1.getDigerBinaTipiAdet()+1);
					break;
				case 14:
					e1.setKonutAdet(e1.getKonutAdet()+1);
					break;
				case 15:
					e1.setKonutAdet(e1.getKonutAdet()+1);
					break;
				case 16:
					e1.setKonutAdet(e1.getKonutAdet()+1);
					break;
				default:
					e1.setDigerBinaTipiAdet(e1.getDigerBinaTipiAdet()+1);
					break;
				}
				
				map.put(e.getBelediyeKod(), e1);
				
			}
				Set<Integer> set=map.keySet();
				Iterator<Integer> iter=set.iterator();
				while (iter.hasNext()){
					int i=iter.next();
					etiketYapiTipKontrolList.add(map.get(i));
				}
			etiketYapiTipKontrolData=new EtiketYapiTipKontrolDataModel(etiketYapiTipKontrolList);
	}
	else
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._TARIHGIRINIZ_.getMesaj()));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._BELEDIYESECILMEDI_.getMesaj()));
		}
	}catch(Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
	}
}
public LoginBean getLoginBean() {
	return loginBean;
}




public void setLoginBean(LoginBean loginBean) {
	this.loginBean = loginBean;
}




public List<SubeDTO> getSubeList() {
	return subeList;
}




public void setSubeList(List<SubeDTO> subeList) {
	this.subeList = subeList;
}




public List<BelediyeKontrolDTO> getBelediyeKontrolList() {
	return belediyeKontrolList;
}




public void setBelediyeKontrolList(List<BelediyeKontrolDTO> belediyeKontrolList) {
	this.belediyeKontrolList = belediyeKontrolList;
}




public BelediyeKontrolDataModel getBelediyeKontrolData() {
	return belediyeKontrolData;
}




public void setBelediyeKontrolData(BelediyeKontrolDataModel belediyeKontrolData) {
	this.belediyeKontrolData = belediyeKontrolData;
}




public List<BelediyeKontrolDTO> getFilteredBelediyeKontrol() {
	return filteredBelediyeKontrol;
}




public void setFilteredBelediyeKontrol(
		List<BelediyeKontrolDTO> filteredBelediyeKontrol) {
	this.filteredBelediyeKontrol = filteredBelediyeKontrol;
}

public int getToplam() {
    return toplam;
}

private int toplam=0;
public void setToplam(int toplam) {
	this.toplam = toplam;
}

private Map<Integer,ArrayList<Integer>>  temsilcilikIlIlce(List<TemsilcilikDTO> arg){
	Map<Integer,ArrayList<Integer>> retmap=new HashMap<Integer, ArrayList<Integer>>();
	Map<Integer,ArrayList<Integer>> map=new HashMap<Integer, ArrayList<Integer>>();
	
	for (TemsilcilikDTO t:arg){
			for (TemsilcilikIlIlceDTO ti:t.getTemsilcilikIller()){
			
				if (ti.getIlce()==0){
					ArrayList<Integer> l=new ArrayList<Integer>();
					for (IlIlceDTO ililce:applicationBean.getIlIlceList()){
						if (ililce.getIlKodu()==ti.getIl()){
							
							if (map.get(ti.getIl())!=null && map.get(ti.getIl()).size()>0){
								l=map.get(ti.getIl());
							}
							l.add(ililce.getIlceKodu());
						
						}
					}
					map.put(ti.getIl(),l);
				}
				
			}
		
	}
	Set<Integer> set=map.keySet();
	Iterator<Integer> iter=set.iterator();
	while (iter.hasNext()){
		int i=iter.next();
		retmap.put(i,map.get(i));
	}
	while (iter.hasNext()){
		int i=iter.next();
		for (int i1:map.get(i)){
			for (TemsilcilikDTO t:temsilcilikler){
				for (TemsilcilikIlIlceDTO ti:t.getTemsilcilikIller()){
					if (i==ti.getIl()){
						if (i1==ti.getIlce()){
							ArrayList<Integer> list1=new ArrayList<Integer>();
							try{
						list1=retmap.get(i);
						list1.remove(i1);
							}catch(Exception e){
								list1=new ArrayList<Integer>();
							}
						retmap.put(i, list1);
						}
					}
				}
			}
		}
		
	}
	for (TemsilcilikDTO t:arg){
		for (TemsilcilikIlIlceDTO t1:t.getTemsilcilikIller()){
			if (t1.getIlce()>0){
				ArrayList<Integer> list=new ArrayList<Integer>();
				list=retmap.get(t1.getIl());
				boolean b=false;
				for (int i:list){
					if (i==t1.getIlce()){
						b=true;
					}
				}
				 if (!b){
					 list.add(t1.getIlce());
					 retmap.put(t1.getIl(), list);
				 }
			}
		}
		
	}
	
	return retmap;
	}
	




private Map<Integer,Integer>  subeIller(List<SubeDTO> arg,List<TemsilcilikDTO> arg1){
	Map<Integer,Integer> map=new HashMap<Integer, Integer>();
		
	for (SubeDTO s:selectedSubeler){
		map.put(s.getIl(),s.getIl());
	}
	for (TemsilcilikDTO t:arg1){
		for (TemsilcilikIlIlceDTO t1:t.getTemsilcilikIller()){
			if (t1.getIl()>0){
				map.put(t1.getIl(), t1.getIl());
			}
		}
	}
	 return map;
}




public ApplicationBean getApplicationBean() {
	return applicationBean;
}




public void setApplicationBean(ApplicationBean applicationBean) {
	this.applicationBean = applicationBean;
}




public EtiketYapiTipKontrolDataModel getEtiketYapiTipKontrolData() {
	return etiketYapiTipKontrolData;
}




public void setEtiketYapiTipKontrolData(
		EtiketYapiTipKontrolDataModel etiketYapiTipKontrolData) {
	this.etiketYapiTipKontrolData = etiketYapiTipKontrolData;
}




public List<EtiketYapiTipKontrolDTO> getEtiketYapiTipKontrolList() {
	return etiketYapiTipKontrolList;
}




public void setEtiketYapiTipKontrolList(
		List<EtiketYapiTipKontrolDTO> etiketYapiTipKontrolList) {
	this.etiketYapiTipKontrolList = etiketYapiTipKontrolList;
}




public List<EtiketYapiTipKontrolDTO> getFilteredEtiketYapiTipKontrol() {
	return filteredEtiketYapiTipKontrol;
}




public void setFilteredEtiketYapiTipKontrol(
		List<EtiketYapiTipKontrolDTO> filteredEtiketYapiTipKontrol) {
	this.filteredEtiketYapiTipKontrol = filteredEtiketYapiTipKontrol;
}




public List<BinaKontrolVeOdemelerDTO> getFilteredBinaKontrolVeOdeme() {
	return filteredBinaKontrolVeOdeme;
}




public void setFilteredBinaKontrolVeOdeme(
		List<BinaKontrolVeOdemelerDTO> filteredBinaKontrolVeOdeme) {
	this.filteredBinaKontrolVeOdeme = filteredBinaKontrolVeOdeme;
}




public List<BinaKontrolVeOdemelerDTO> getBinaKontrolVeOdemeList() {
	return binaKontrolVeOdemeList;
}




public void setBinaKontrolVeOdemeList(
		List<BinaKontrolVeOdemelerDTO> binaKontrolVeOdemeList) {
	this.binaKontrolVeOdemeList = binaKontrolVeOdemeList;
}




public BinaKontrolVeOdemeDataModel getBinaKontrolVeOdemeData() {
	return binaKontrolVeOdemeData;
}




public void setBinaKontrolVeOdemeData(
		BinaKontrolVeOdemeDataModel binaKontrolVeOdemeData) {
	this.binaKontrolVeOdemeData = binaKontrolVeOdemeData;
}




public List<Integer> getSelectedBelediye() {
	return selectedBelediye;
}




public void setSelectedBelediye(List<Integer> selectedBelediye) {
	this.selectedBelediye = selectedBelediye;
}




public boolean isBelediyeHepsiniSec() {
	return belediyeHepsiniSec;
}




public void setBelediyeHepsiniSec(boolean belediyeHepsiniSec) {
	this.belediyeHepsiniSec = belediyeHepsiniSec;
}




public int getSelectedIl() {
	return selectedIl;
}




public void setSelectedIl(int selectedIl) {
	this.selectedIl = selectedIl;
}




public void IlSelectListener(AjaxBehaviorEvent event){
	belediyeList=new ArrayList<BelediyeDTO>();
selectedBelediyeler=new ArrayList<BelediyeDTO>();
	
	selectedBelediye=new ArrayList<Integer>();
	
	belediyeHepsiniSec=false;
	tarih1=null;
	tarih2=null;
			
	binaKontrolVeOdemeList=new ArrayList<BinaKontrolVeOdemelerDTO>();
	binaKontrolVeOdemeData=new BinaKontrolVeOdemeDataModel(binaKontrolVeOdemeList);
	if (selectedIl>0){
		for (BelediyeDTO b:loginBean.getBelediyeList()){
			if (b.getIl()==selectedIl)
			belediyeList.add(b);
		}
	}
	
	
	
}




public List<BelediyeDTO> getBelediyeList() {
	return belediyeList;
}




public void setBelediyeList(List<BelediyeDTO> belediyeList) {
	this.belediyeList = belediyeList;
	
}
public void changeTableViewListener(ActionEvent event){
	String viewId=FacesContext.getCurrentInstance().getViewRoot().getViewId();
	DataTable dataTable=new DataTable();
	if (viewId.contains("belediyebilgi")){
		if (((UIComponent)event.getSource()).getId().equals("table1Btn")){
			 dataTable=(DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent(":formraporara:etiketyapitipkontroltable");	
		}else
		dataTable=(DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent(":formraporara:belediyebilgi");
	}if (viewId.contains("kontrolveodemelerlistesi") || viewId.contains("kontrolugelenbinalar")){
	 dataTable=(DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent(":formraporara:binakontrolodemetable");
	}
	if (viewId.contains("etiketveyapitiplerinegorekontrolsayisidagilimi")){
		 dataTable=(DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent(":formraporara:etiketyapitipkontroltable");
		}
	if (dataTable!=null){
		Boolean bool=dataTable.isResizableColumns();
		dataTable.setResizableColumns(!bool);
		dataTable.setLiveResize(!bool);
	}
}

public void belediyeOdemeler(){
	try{
		if (selectedSubeler!=null && selectedSubeler.size()>0){
			if (tarih1!=null && tarih1.getTime()>0 && tarih2!=null && tarih2.getTime()>0){
				belediyeOdemeKontrolList=new ArrayList<EtiketYapiTipKontrolDTO>();
				boolean temsilcilikSecildi=false;
				Map<Integer,Integer> subeIller=new HashMap<Integer, Integer>();
				Map<Integer,ArrayList<Integer>> temsilcilikIllerIlceler=new HashMap<Integer, ArrayList<Integer>>();
				
				if (selectedTemsilcilikler!=null && selectedTemsilcilikler.size()>0){
				temsilcilikIllerIlceler=temsilcilikIlIlce(selectedTemsilcilikler);
				temsilcilikSecildi=true;
				}else{
					subeIller=subeIller(selectedSubeler,selectedTemsilcilikler);
				}
			List<EtiketYapiTipKontrolDTO> list=new ArrayList<EtiketYapiTipKontrolDTO>();	
	list=	InformationBusiness.INSTANCE.getBelediyeOdemeler(tarih1,tarih2,subeIller,temsilcilikIllerIlceler,temsilcilikSecildi);
	
	if (list.size()>0){
		Map<Integer, EtiketYapiTipKontrolDTO> map=new HashMap<Integer, EtiketYapiTipKontrolDTO>();	
		for (EtiketYapiTipKontrolDTO e:list){
			EtiketYapiTipKontrolDTO e1=map.get(e.getBelediyeKod())==null?new EtiketYapiTipKontrolDTO():map.get(e.getBelediyeKod());
			e1.setBelediyeAdi(e.getBelediyeAdi());
			e1.setBelediyeKod(e.getBelediyeKod());
			
		

			
			if(e.getKontrolTutari()>0){
			e1.setToplamTutar((e1.getToplamTutar()+e.getKontrolTutari()));
		}
		if(e.getOdenenTutar()>0){
			e1.setOdenenToplamTutar(e1.getOdenenToplamTutar()+e.getOdenenTutar());
		}
		e1.setOdenmeyenTutar(e1.getToplamTutar()-e1.getOdenenToplamTutar());
			
			map.put(e.getBelediyeKod(), e1);
			
		}
			Set<Integer> set=map.keySet();
			Iterator<Integer> iter=set.iterator();
			while (iter.hasNext()){
				int i=iter.next();
				belediyeOdemeKontrolList.add(map.get(i));
			}
			belediyeOdemeKontrolData=new EtiketYapiTipKontrolDataModel(belediyeOdemeKontrolList);
	}
	else
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._TARIHGIRINIZ_.getMesaj()));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SUBESECINIZ_.getMesaj()));
		}
	}catch(Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
	}
}

public void odemeKontrol(ActionEvent event){
	try{
		if (selectedSubeler!=null && selectedSubeler.size()>0){
			if (tarih1!=null && tarih1.getTime()>0 && tarih2!=null && tarih2.getTime()>0){
				odemeKontrolList=new ArrayList<OdemeKontrolDTO>();
				odemeKontrolData=new OdemeKontrolDataModel(odemeKontrolList);
				
				boolean temsilcilikSecildi=false;
				Map<Integer,Integer> subeIller=new HashMap<Integer, Integer>();
				Map<Integer,ArrayList<Integer>> temsilcilikIllerIlceler=new HashMap<Integer, ArrayList<Integer>>();
				
				if (selectedTemsilcilikler!=null && selectedTemsilcilikler.size()>0){
				temsilcilikIllerIlceler=temsilcilikIlIlce(selectedTemsilcilikler);
				temsilcilikSecildi=true;
				}else{
					subeIller=subeIller(selectedSubeler,selectedTemsilcilikler);
				}
			List<OdemeKontrolDTO> list=new ArrayList<OdemeKontrolDTO>();	
	list=	InformationBusiness.INSTANCE.getBinaOdemeler(tarih1,tarih2,subeIller,temsilcilikIllerIlceler,temsilcilikSecildi);
	
	if (list.size()>0){
		odemeKontrolData=new OdemeKontrolDataModel(list);
	}
	else
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._TARIHGIRINIZ_.getMesaj()));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SUBESECINIZ_.getMesaj()));
		}
	}catch(Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
	}
}

public void cihazOdemeKontrol(ActionEvent event){
	try{
		if (selectedSubeler!=null && selectedSubeler.size()>0){
			if (tarih1!=null && tarih1.getTime()>0 && tarih2!=null && tarih2.getTime()>0){
				cihazOdemeKontrolList=new ArrayList<CihazOdemeKontrolDTO>();
				cihazOdemeKontrolData=new CihazOdemeKontrolDataModel(cihazOdemeKontrolList);
				
				boolean temsilcilikSecildi=false;
				Map<Integer,Integer> subeIller=new HashMap<Integer, Integer>();
				Map<Integer,ArrayList<Integer>> temsilcilikIllerIlceler=new HashMap<Integer, ArrayList<Integer>>();
				
				if (selectedTemsilcilikler!=null && selectedTemsilcilikler.size()>0){
				temsilcilikIllerIlceler=temsilcilikIlIlce(selectedTemsilcilikler);
				temsilcilikSecildi=true;
				}else{
					subeIller=subeIller(selectedSubeler,selectedTemsilcilikler);
				}
			List<CihazOdemeKontrolDTO> list=new ArrayList<CihazOdemeKontrolDTO>();	
	list=	InformationBusiness.INSTANCE.getCihazOdemeler(tarih1,tarih2,subeIller,temsilcilikIllerIlceler,temsilcilikSecildi);
	
	if (list.size()>0){
		cihazOdemeKontrolData=new CihazOdemeKontrolDataModel(list);
	}
	else
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._TARIHGIRINIZ_.getMesaj()));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SUBESECINIZ_.getMesaj()));
		}
	}catch(Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
	}
}





}
