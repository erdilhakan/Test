package tr.org.mmo.asansor.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;

import tr.org.mmo.asansor.business.BakanlikRaporlariBusiness;
import tr.org.mmo.asansor.dto.KirmiziEtiketKontrolDTO;
import tr.org.mmo.asansor.dto.OdemeKontrolOtuzGunDTO;
import tr.org.mmo.asansor.dto.RaporTeslimKontrolDTO;
import tr.org.mmo.asansor.dto.SariEtiketKontrolDTO;
import tr.org.mmo.asansor.models.KirmiziEtiketKontrolDataModel;
import tr.org.mmo.asansor.models.OdemeKontrolOtuzGunDataModel;
import tr.org.mmo.asansor.models.RaporTeslimKontrolDataModel;
import tr.org.mmo.asansor.models.SariEtiketKontrolDataModel;
import tr.org.mmo.asansor.util.Messages;

@ManagedBean(name="bakanlikInformation")
@ViewScoped
public class BakanlikRaporlariBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1709385672592524886L;
	private Date tarih1;
	private Date tarih2;
	private Date tarihBaslangicKirmizi;
	private Date tarihBitisKirmizi;
	private Date tarihBaslangicSari;
	private Date tarihBitisSari;
	private Date tarihBaslangicRaporTeslim;
	private Date tarihBitisRaporTeslim;
	private OdemeKontrolOtuzGunDataModel odemeKontrolOtuzGunData;
	private List<OdemeKontrolOtuzGunDTO> odemeKontrolOtuzGunList;
	private List<OdemeKontrolOtuzGunDTO> filteredodemeKontrolOtuzGun;
	private KirmiziEtiketKontrolDataModel kirmiziEtiketKontrolData;
	private List<KirmiziEtiketKontrolDTO> kirmiziEtiketKontrolList;
	private List<KirmiziEtiketKontrolDTO> filteredKirmiziEtiketKontrol;
	private SariEtiketKontrolDataModel sariEtiketKontrolData;
	private List<SariEtiketKontrolDTO> sariEtiketKontrolList;
	private List<SariEtiketKontrolDTO> filteredSariEtiketKontrol;
	private RaporTeslimKontrolDataModel raporTeslimKontrolData;
	private List<RaporTeslimKontrolDTO> raporTeslimKontrolList;
	private List<RaporTeslimKontrolDTO> filteredRaporTeslimKontrol;
	List<KirmiziEtiketKontrolDTO> listKirmizi=new ArrayList<KirmiziEtiketKontrolDTO>();
	
	private KirmiziEtiketKontrolDataModel kirmiziGonderilenData;
	private List<KirmiziEtiketKontrolDTO> kirmiziGonderilenList;
	private List<KirmiziEtiketKontrolDTO> kirmiziGonderilenFilter;
	private KirmiziEtiketKontrolDataModel kirmiziGonderilmeyenData;
	private List<KirmiziEtiketKontrolDTO> kirmiziGonderilmeyenList;
	private List<KirmiziEtiketKontrolDTO> kirmiziGonderilmeyenFilter;
	
	private SariEtiketKontrolDataModel sariGonderilenData;
	private List<SariEtiketKontrolDTO> sariGonderilenList;
	private List<SariEtiketKontrolDTO> sariGonderilenFilter;
	private SariEtiketKontrolDataModel sariGonderilmeyenData;
	private List<SariEtiketKontrolDTO> sariGonderilmeyenList;
	private List<SariEtiketKontrolDTO> sariGonderilmeyenFilter;

	public void dateSelectListener(SelectEvent event){
		Object o = event.getObject();
		UIComponent uc = event.getComponent();
		
	odemeKontrolOtuzGunList = new ArrayList<OdemeKontrolOtuzGunDTO>();
	odemeKontrolOtuzGunData = new OdemeKontrolOtuzGunDataModel(odemeKontrolOtuzGunList);
	kirmiziEtiketKontrolList = new ArrayList<KirmiziEtiketKontrolDTO>();
	kirmiziEtiketKontrolData = new KirmiziEtiketKontrolDataModel(kirmiziEtiketKontrolList);
	sariEtiketKontrolList = new ArrayList<SariEtiketKontrolDTO>();
	sariEtiketKontrolData = new SariEtiketKontrolDataModel(sariEtiketKontrolList);
	raporTeslimKontrolList = new ArrayList<RaporTeslimKontrolDTO>();
	raporTeslimKontrolData = new RaporTeslimKontrolDataModel(raporTeslimKontrolList);
	kirmiziGonderilenList=new ArrayList<KirmiziEtiketKontrolDTO>();
	kirmiziGonderilenData=new KirmiziEtiketKontrolDataModel(kirmiziGonderilenList);
	kirmiziGonderilmeyenList=new ArrayList<KirmiziEtiketKontrolDTO>();
	kirmiziGonderilmeyenData=new KirmiziEtiketKontrolDataModel(kirmiziGonderilmeyenList);
	
	sariGonderilenList=new ArrayList<SariEtiketKontrolDTO>();
	sariGonderilenData=new SariEtiketKontrolDataModel(sariGonderilenList);
	sariGonderilmeyenList=new ArrayList<SariEtiketKontrolDTO>();
	sariGonderilmeyenData=new SariEtiketKontrolDataModel(sariGonderilmeyenList);
	
	
	
		if (!tarihKontrol(o, uc)) {

			FacesMessage msg = new FacesMessage(Messages._HATALITARIH_.getMesaj());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void dateBlurListener(AjaxBehaviorEvent event){
		Object o = event.getSource();
		UIComponent uc = event.getComponent();
		
		odemeKontrolOtuzGunList = new ArrayList<OdemeKontrolOtuzGunDTO>();
		odemeKontrolOtuzGunData = new OdemeKontrolOtuzGunDataModel(odemeKontrolOtuzGunList);
		kirmiziEtiketKontrolList = new ArrayList<KirmiziEtiketKontrolDTO>();
		kirmiziEtiketKontrolData = new KirmiziEtiketKontrolDataModel(kirmiziEtiketKontrolList);
		sariEtiketKontrolList = new ArrayList<SariEtiketKontrolDTO>();
		sariEtiketKontrolData = new SariEtiketKontrolDataModel(sariEtiketKontrolList);
		raporTeslimKontrolList = new ArrayList<RaporTeslimKontrolDTO>();
		raporTeslimKontrolData = new RaporTeslimKontrolDataModel(raporTeslimKontrolList);
		kirmiziGonderilenList=new ArrayList<KirmiziEtiketKontrolDTO>();
		kirmiziGonderilenData=new KirmiziEtiketKontrolDataModel(kirmiziGonderilenList);
		sariGonderilenList=new ArrayList<SariEtiketKontrolDTO>();
		sariGonderilenData=new SariEtiketKontrolDataModel(sariGonderilenList);
		sariGonderilmeyenList=new ArrayList<SariEtiketKontrolDTO>();
		sariGonderilmeyenData=new SariEtiketKontrolDataModel(sariGonderilmeyenList);
		

		if (!tarihKontrol(o, uc)) {

			FacesMessage msg = new FacesMessage(Messages._HATALITARIH_.getMesaj());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	@ManagedProperty("#{applicationBean}")
	private ApplicationBean applicationBean;

	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	
	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	@PostConstruct
	public void init(){}
		

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
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

public Date getTarihBaslangicKirmizi() {
	return tarihBaslangicKirmizi;
}

public void setTarihBaslangicKirmizi(Date tarihBaslangicKirmizi) {
	this.tarihBaslangicKirmizi = tarihBaslangicKirmizi;
}

public Date getTarihBitisKirmizi() {
	return tarihBitisKirmizi;
}

public void setTarihBitisKirmizi(Date tarihBitisKirmizi) {
	this.tarihBitisKirmizi = tarihBitisKirmizi;
}

public Date getTarihBaslangicSari() {
	return tarihBaslangicSari;
}

public void setTarihBaslangicSari(Date tarihBaslangicSari) {
	this.tarihBaslangicSari = tarihBaslangicSari;
}

public Date getTarihBitisSari() {
	return tarihBitisSari;
}

public void setTarihBitisSari(Date tarihBitisSari) {
	this.tarihBitisSari = tarihBitisSari;
}

public Date getTarihBaslangicRaporTeslim() {
	return tarihBaslangicRaporTeslim;
}

public void setTarihBaslangicRaporTeslim(Date tarihBaslangicRaporTeslim) {
	this.tarihBaslangicRaporTeslim = tarihBaslangicRaporTeslim;
}

public Date getTarihBitisRaporTeslim() {
	return tarihBitisRaporTeslim;
}

public void setTarihBitisRaporTeslim(Date tarihBitisRaporTeslim) {
	this.tarihBitisRaporTeslim = tarihBitisRaporTeslim;
}

private boolean tarihKontrol(Object o, UIComponent uc) {
	boolean bool = true;
	
	if (tarih1 != null && tarih2 != null
			&& tarih2.compareTo(tarih1)<=0) {
		bool = false;
	}
	return bool;

}

public LoginBean getLoginBean() {
	return loginBean;
}

public void setLoginBean(LoginBean loginBean) {
	this.loginBean = loginBean;
}

public ApplicationBean getApplicationBean() {
	return applicationBean;
}

public void setApplicationBean(ApplicationBean applicationBean) {
	this.applicationBean = applicationBean;
}

public OdemeKontrolOtuzGunDataModel getOdemeKontrolOtuzGunData() {
	return odemeKontrolOtuzGunData;
}

public void setOdemeKontrolOtuzGunData(
		OdemeKontrolOtuzGunDataModel odemeKontrolOtuzGunData) {
	this.odemeKontrolOtuzGunData = odemeKontrolOtuzGunData;
}


public List<OdemeKontrolOtuzGunDTO> getOdemeKontrolOtuzGunList() {
	return odemeKontrolOtuzGunList;
}


public void setOdemeKontrolOtuzGunList(
		List<OdemeKontrolOtuzGunDTO> odemeKontrolOtuzGunList) {
	this.odemeKontrolOtuzGunList = odemeKontrolOtuzGunList;
}

public List<OdemeKontrolOtuzGunDTO> getFilteredodemeKontrolOtuzGun() {
	return filteredodemeKontrolOtuzGun;
}


public void setFilteredodemeKontrolOtuzGun(
		List<OdemeKontrolOtuzGunDTO> filteredodemeKontrolOtuzGun) {
	this.filteredodemeKontrolOtuzGun = filteredodemeKontrolOtuzGun;
}

public KirmiziEtiketKontrolDataModel getKirmiziEtiketKontrolData() {
	return kirmiziEtiketKontrolData;
}

public void setKirmiziEtiketKontrolData(
		KirmiziEtiketKontrolDataModel kirmiziEtiketKontrolData) {
	this.kirmiziEtiketKontrolData = kirmiziEtiketKontrolData;
}

public List<KirmiziEtiketKontrolDTO> getKirmiziEtiketKontrolList() {
	return kirmiziEtiketKontrolList;
}

public void setKirmiziEtiketKontrolList(
		List<KirmiziEtiketKontrolDTO> kirmiziEtiketKontrolList) {
	this.kirmiziEtiketKontrolList = kirmiziEtiketKontrolList;
}

public List<KirmiziEtiketKontrolDTO> getFilteredKirmiziEtiketKontrol() {
	return filteredKirmiziEtiketKontrol;
}

public void setFilteredKirmiziEtiketKontrol(
		List<KirmiziEtiketKontrolDTO> filteredKirmiziEtiketKontrol) {
	this.filteredKirmiziEtiketKontrol = filteredKirmiziEtiketKontrol;
}

public SariEtiketKontrolDataModel getSariEtiketKontrolData() {
	return sariEtiketKontrolData;
}

public void setSariEtiketKontrolData(
		SariEtiketKontrolDataModel sariEtiketKontrolData) {
	this.sariEtiketKontrolData = sariEtiketKontrolData;
}

public List<SariEtiketKontrolDTO> getSariEtiketKontrolList() {
	return sariEtiketKontrolList;
}

public void setSariEtiketKontrolList(
		List<SariEtiketKontrolDTO> sariEtiketKontrolList) {
	this.sariEtiketKontrolList = sariEtiketKontrolList;
}

public List<SariEtiketKontrolDTO> getFilteredSariEtiketKontrol() {
	return filteredSariEtiketKontrol;
}

public void setFilteredSariEtiketKontrol(
		List<SariEtiketKontrolDTO> filteredSariEtiketKontrol) {
	this.filteredSariEtiketKontrol = filteredSariEtiketKontrol;
}

public RaporTeslimKontrolDataModel getRaporTeslimKontrolData() {
	return raporTeslimKontrolData;
}

public void setRaporTeslimKontrolData(
		RaporTeslimKontrolDataModel raporTeslimKontrolData) {
	this.raporTeslimKontrolData = raporTeslimKontrolData;
}

public List<RaporTeslimKontrolDTO> getRaporTeslimKontrolList() {
	return raporTeslimKontrolList;
}

public void setRaporTeslimKontrolList(
		List<RaporTeslimKontrolDTO> raporTeslimKontrolList) {
	this.raporTeslimKontrolList = raporTeslimKontrolList;
}

public List<RaporTeslimKontrolDTO> getFilteredRaporTeslimKontrol() {
	return filteredRaporTeslimKontrol;
}

public void setFilteredRaporTeslimKontrol(
		List<RaporTeslimKontrolDTO> filteredRaporTeslimKontrol) {
	this.filteredRaporTeslimKontrol = filteredRaporTeslimKontrol;
}

public KirmiziEtiketKontrolDataModel getKirmiziGonderilenData() {
	return kirmiziGonderilenData;
}

public void setKirmiziGonderilenData(
		KirmiziEtiketKontrolDataModel kirmiziGonderilenData) {
	this.kirmiziGonderilenData = kirmiziGonderilenData;
}

public List<KirmiziEtiketKontrolDTO> getKirmiziGonderilenList() {
	return kirmiziGonderilenList;
}

public void setKirmiziGonderilenList(
		List<KirmiziEtiketKontrolDTO> kirmiziGonderilenList) {
	this.kirmiziGonderilenList = kirmiziGonderilenList;
}

public List<KirmiziEtiketKontrolDTO> getKirmiziGonderilenFilter() {
	return kirmiziGonderilenFilter;
}

public void setKirmiziGonderilenFilter(
		List<KirmiziEtiketKontrolDTO> kirmiziGonderilenFilter) {
	this.kirmiziGonderilenFilter = kirmiziGonderilenFilter;
}

public KirmiziEtiketKontrolDataModel getKirmiziGonderilmeyenData() {
	return kirmiziGonderilmeyenData;
}

public void setKirmiziGonderilmeyenData(
		KirmiziEtiketKontrolDataModel kirmiziGonderilmeyenData) {
	this.kirmiziGonderilmeyenData = kirmiziGonderilmeyenData;
}

public List<KirmiziEtiketKontrolDTO> getKirmiziGonderilmeyenList() {
	return kirmiziGonderilmeyenList;
}

public void setKirmiziGonderilmeyenList(
		List<KirmiziEtiketKontrolDTO> kirmiziGonderilmeyenList) {
	this.kirmiziGonderilmeyenList = kirmiziGonderilmeyenList;
}

public List<KirmiziEtiketKontrolDTO> getKirmiziGonderilmeyenFilter() {
	return kirmiziGonderilmeyenFilter;
}

public void setKirmiziGonderilmeyenFilter(
		List<KirmiziEtiketKontrolDTO> kirmiziGonderilmeyenFilter) {
	this.kirmiziGonderilmeyenFilter = kirmiziGonderilmeyenFilter;
}

public SariEtiketKontrolDataModel getSariGonderilenData() {
	return sariGonderilenData;
}

public void setSariGonderilenData(SariEtiketKontrolDataModel sariGonderilenData) {
	this.sariGonderilenData = sariGonderilenData;
}

public List<SariEtiketKontrolDTO> getSariGonderilenList() {
	return sariGonderilenList;
}

public void setSariGonderilenList(List<SariEtiketKontrolDTO> sariGonderilenList) {
	this.sariGonderilenList = sariGonderilenList;
}

public List<SariEtiketKontrolDTO> getSariGonderilenFilter() {
	return sariGonderilenFilter;
}

public void setSariGonderilenFilter(
		List<SariEtiketKontrolDTO> sariGonderilenFilter) {
	this.sariGonderilenFilter = sariGonderilenFilter;
}

public SariEtiketKontrolDataModel getSariGonderilmeyenData() {
	return sariGonderilmeyenData;
}

public void setSariGonderilmeyenData(
		SariEtiketKontrolDataModel sariGonderilmeyenData) {
	this.sariGonderilmeyenData = sariGonderilmeyenData;
}

public List<SariEtiketKontrolDTO> getSariGonderilmeyenList() {
	return sariGonderilmeyenList;
}

public void setSariGonderilmeyenList(
		List<SariEtiketKontrolDTO> sariGonderilmeyenList) {
	this.sariGonderilmeyenList = sariGonderilmeyenList;
}

public List<SariEtiketKontrolDTO> getSariGonderilmeyenFilter() {
	return sariGonderilmeyenFilter;
}

public void setSariGonderilmeyenFilter(
		List<SariEtiketKontrolDTO> sariGonderilmeyenFilter) {
	this.sariGonderilmeyenFilter = sariGonderilmeyenFilter;
}

public void odemeKontrolOtuzGun(ActionEvent event) {
	try{
	if (tarih1!=null && tarih1.getTime()>0 && tarih2!=null && tarih2.getTime()>0){
		odemeKontrolOtuzGunList=new ArrayList<OdemeKontrolOtuzGunDTO>();
		odemeKontrolOtuzGunData=new OdemeKontrolOtuzGunDataModel(odemeKontrolOtuzGunList);
		
		List<OdemeKontrolOtuzGunDTO> list=new ArrayList<OdemeKontrolOtuzGunDTO>();	
		list=	BakanlikRaporlariBusiness.INSTANCE.getBinaOdemelerOtuzGun(tarih1,tarih2);
		
		if (list.size()>0){
			odemeKontrolOtuzGunData=new OdemeKontrolOtuzGunDataModel(list);
		}
		else
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._TARIHGIRINIZ_.getMesaj()));
		}
		}catch(Exception e){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			}
	}

public void kirmiziEtiketKontrol(ActionEvent event) {
	try{
		kirmiziGonderilenList=null;
		kirmiziGonderilmeyenList=null;
		kirmiziGonderilenFilter=null;
		kirmiziGonderilmeyenFilter=null;
	if (tarihBaslangicKirmizi!=null && tarihBaslangicKirmizi.getTime()>0 && tarihBitisKirmizi!=null && tarihBitisKirmizi.getTime()>0){
		kirmiziEtiketKontrolList=new ArrayList<KirmiziEtiketKontrolDTO>();
		kirmiziEtiketKontrolData=new KirmiziEtiketKontrolDataModel(kirmiziEtiketKontrolList);
		
		kirmiziEtiketKontrolList=	BakanlikRaporlariBusiness.INSTANCE.getKirmiziEtiketKontrol(tarihBaslangicKirmizi,tarihBitisKirmizi);
		
		if (kirmiziEtiketKontrolList.size()>0){
			kirmiziEtiketKontrolData=new KirmiziEtiketKontrolDataModel(kirmiziEtiketKontrolList);
		}
		else
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._TARIHGIRINIZ_.getMesaj()));
		}
		}catch(Exception e){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			}
	}

public void sariEtiketKontrol(ActionEvent event) {
	sariGonderilenList=null;
	sariGonderilenFilter=null;
	sariGonderilmeyenList=null;
	sariGonderilmeyenFilter=null;
	try{
	if (tarihBaslangicSari!=null && tarihBaslangicSari.getTime()>0 && tarihBitisSari!=null && tarihBitisSari.getTime()>0){
		sariEtiketKontrolList=new ArrayList<SariEtiketKontrolDTO>();
		sariEtiketKontrolData=new SariEtiketKontrolDataModel(sariEtiketKontrolList);
		
		sariEtiketKontrolList=	BakanlikRaporlariBusiness.INSTANCE.getSariEtiketKontrol(tarihBaslangicSari,tarihBitisSari);
		
		if (sariEtiketKontrolList.size()>0){
			sariEtiketKontrolData=new SariEtiketKontrolDataModel(sariEtiketKontrolList);
		}
		else
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._TARIHGIRINIZ_.getMesaj()));
		}
		}catch(Exception e){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			}
	}

public void raporTeslimKontrol(ActionEvent event) {
	try{
	if (tarihBaslangicRaporTeslim!=null && tarihBaslangicRaporTeslim.getTime()>0 && tarihBitisRaporTeslim!=null && tarihBitisRaporTeslim.getTime()>0){
		raporTeslimKontrolList=new ArrayList<RaporTeslimKontrolDTO>();
		raporTeslimKontrolData=new RaporTeslimKontrolDataModel(raporTeslimKontrolList);
		
		List<RaporTeslimKontrolDTO> list=new ArrayList<RaporTeslimKontrolDTO>();	
		list=	BakanlikRaporlariBusiness.INSTANCE.getRaporTeslimKontrol(tarihBaslangicRaporTeslim,tarihBitisRaporTeslim);
		
		if (list.size()>0){
			raporTeslimKontrolData=new RaporTeslimKontrolDataModel(list);
		}
		else
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._TARIHGIRINIZ_.getMesaj()));
		}
		}catch(Exception e){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			}
	}

public void belediyeKirmiziRaporGonder(ActionEvent event){
	try{ 
		kirmiziEtiketKontrolList=null;
		kirmiziGonderilmeyenList=null;
		filteredKirmiziEtiketKontrol=null;
		kirmiziGonderilmeyenFilter=null;
		if (tarihBaslangicKirmizi!=null && tarihBaslangicKirmizi.getTime()>0 && tarihBitisKirmizi!=null && tarihBitisKirmizi.getTime()>0){
			kirmiziGonderilenList=new ArrayList<KirmiziEtiketKontrolDTO>();
			kirmiziGonderilenData =new KirmiziEtiketKontrolDataModel(kirmiziGonderilenList);
			
			kirmiziGonderilenList=	BakanlikRaporlariBusiness.INSTANCE.getBelediyeGonderilenlerKirmiziEtiketKontrol(tarihBaslangicKirmizi,tarihBitisKirmizi);
			
			if (kirmiziGonderilenList.size()>0){
				kirmiziGonderilenData = new KirmiziEtiketKontrolDataModel(kirmiziGonderilenList);
			}
			else
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._TARIHGIRINIZ_.getMesaj()));
			}
			}catch(Exception e){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
				}
	
}

public void belediyeKirmiziRaporGonderilmeyen(){
	try{ 
		kirmiziEtiketKontrolList=null;
		kirmiziGonderilenList=null;
		filteredKirmiziEtiketKontrol=null;
		kirmiziGonderilenFilter=null;
		if (tarihBaslangicKirmizi!=null && tarihBaslangicKirmizi.getTime()>0 && tarihBitisKirmizi!=null && tarihBitisKirmizi.getTime()>0){
			kirmiziGonderilmeyenList=new ArrayList<KirmiziEtiketKontrolDTO>();
			kirmiziGonderilmeyenData =new KirmiziEtiketKontrolDataModel(kirmiziGonderilmeyenList);
			
			kirmiziGonderilmeyenList=	BakanlikRaporlariBusiness.INSTANCE.getBelediyeGonderilmeyenlerKirmiziEtiketKontrol(tarihBaslangicKirmizi,tarihBitisKirmizi);
			
			if (kirmiziGonderilmeyenList.size()>0){
				kirmiziGonderilmeyenData = new KirmiziEtiketKontrolDataModel(kirmiziGonderilmeyenList);
			}
			else
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._TARIHGIRINIZ_.getMesaj()));
			}
			}catch(Exception e){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
				}
}

public void belediyeKirmiziRaporGonderilmeyenKaydet(){
	try{ 
		if(kirmiziGonderilmeyenFilter!=null){
			if(kirmiziGonderilmeyenFilter.size() == kirmiziGonderilmeyenList.size()){
				kirmiziGonderilmeyenFilter=null;
			}
		}
		if(kirmiziGonderilmeyenList.size() > 0 &&  kirmiziGonderilmeyenFilter == null){
			for(KirmiziEtiketKontrolDTO k: kirmiziGonderilmeyenList){
					BakanlikRaporlariBusiness.belediyeKirmiziRaporGonder(k.getRaporId(),k.getBelediyeKod(),k.getBelediyeAdi(),k.getEtiket(),
							k.getKontrolId(),k.getRandevuId(),k.getBasvuruId(),k.getBinaId());
			}
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._BELEDIYEKIRMIZIKAYDET_.getMesaj()));
		}else if(kirmiziGonderilmeyenList.size() > 0 && kirmiziGonderilmeyenFilter.size() != kirmiziGonderilmeyenList.size()){
			for(KirmiziEtiketKontrolDTO k2 : kirmiziGonderilmeyenFilter){
				BakanlikRaporlariBusiness.belediyeKirmiziRaporGonder(k2.getRaporId(),k2.getBelediyeKod(),k2.getBelediyeAdi(),k2.getEtiket(),
						k2.getKontrolId(),k2.getRandevuId(),k2.getBasvuruId(),k2.getBinaId());
			}
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._BELEDIYEKIRMIZIKAYDET_.getMesaj()));
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
		}
			
		
	}catch(Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
	}
}

public void belediyeSariRaporGonder(ActionEvent event){
	try{ 
		sariEtiketKontrolList=null;
		sariGonderilmeyenList=null;
		filteredSariEtiketKontrol=null;
		sariGonderilmeyenFilter=null;
		if (tarihBaslangicSari!=null && tarihBaslangicSari.getTime()>0 && tarihBitisSari!=null && tarihBitisSari.getTime()>0){
			sariGonderilenList=new ArrayList<SariEtiketKontrolDTO>();
			sariGonderilenData =new SariEtiketKontrolDataModel(sariGonderilenList);
			
			sariGonderilenList=	BakanlikRaporlariBusiness.INSTANCE.getBelediyeGonderilenlerSariEtiketKontrol(tarihBaslangicSari,tarihBitisSari);
			
			if (sariGonderilenList.size()>0){
				sariGonderilenData = new SariEtiketKontrolDataModel(sariGonderilenList);
			}
			else
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._TARIHGIRINIZ_.getMesaj()));
			}
			}catch(Exception e){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
				}
	
}

public void belediyeSariRaporGonderilmeyen(){
	try{ 
		sariEtiketKontrolList=null;
		sariGonderilenList=null;
		filteredSariEtiketKontrol=null;
		sariGonderilenFilter=null;
		if (tarihBaslangicSari!=null && tarihBaslangicSari.getTime()>0 && tarihBitisSari!=null && tarihBitisSari.getTime()>0){
			sariGonderilmeyenList=new ArrayList<SariEtiketKontrolDTO>();
			sariGonderilmeyenData =new SariEtiketKontrolDataModel(sariGonderilmeyenList);
			
			sariGonderilmeyenList=	BakanlikRaporlariBusiness.INSTANCE.getBelediyeGonderilmeyenlerSariEtiketKontrol(tarihBaslangicSari,tarihBitisSari);
			
			if (sariGonderilmeyenList.size()>0){
				sariGonderilmeyenData = new SariEtiketKontrolDataModel(sariGonderilmeyenList);
			}
			else
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._TARIHGIRINIZ_.getMesaj()));
			}
			}catch(Exception e){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
				}
}

public void belediyeSariRaporGonderilmeyenKaydet(){
	try{ 
		if(sariGonderilmeyenFilter!=null){
			if(sariGonderilmeyenFilter.size() == sariGonderilmeyenList.size()){
				sariGonderilmeyenFilter=null;
			}
		}
		if(sariGonderilmeyenList.size() > 0 &&  sariGonderilmeyenFilter == null){
			for(SariEtiketKontrolDTO k: sariGonderilmeyenList){
					BakanlikRaporlariBusiness.belediyeSariRaporGonder(k.getRaporId(),k.getBelediyeKod(),k.getBelediyeAdi(),k.getEtiket(),
							k.getKontrolId(),k.getRandevuId(),k.getBasvuruId(),k.getBinaId());
			}
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._BELEDIYEKIRMIZIKAYDET_.getMesaj()));
		}else if(sariGonderilmeyenList.size() > 0 && sariGonderilmeyenFilter.size() != sariGonderilmeyenList.size()){
			for(SariEtiketKontrolDTO k2 : sariGonderilmeyenFilter){
				BakanlikRaporlariBusiness.belediyeSariRaporGonder(k2.getRaporId(),k2.getBelediyeKod(),k2.getBelediyeAdi(),k2.getEtiket(),
						k2.getKontrolId(),k2.getRandevuId(),k2.getBasvuruId(),k2.getBinaId());
			}
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._BELEDIYEKIRMIZIKAYDET_.getMesaj()));
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
		}
			
		
	}catch(Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
	}
}

}
