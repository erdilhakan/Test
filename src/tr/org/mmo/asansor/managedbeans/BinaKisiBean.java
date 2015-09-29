package tr.org.mmo.asansor.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.SelectEvent;

import tr.org.mmo.asansor.business.BinaBusiness;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.BinaKisiDTO;
import tr.org.mmo.asansor.dto.BinaSorumlulukDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.models.BinaKisiDataModel;

@ManagedBean
@ViewScoped
public class BinaKisiBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<BinaSorumlulukDTO> sTurList=new ArrayList<BinaSorumlulukDTO>();
	private BinaKisiDTO binaKisi=new BinaKisiDTO();
	private BinaKisiDTO slctKisi=new BinaKisiDTO();
	private BinaKisiDataModel kisiData;
	private List<BinaKisiDTO> kisiList=new ArrayList<BinaKisiDTO>();
	private List<BinaKisiDTO> filterKisi;
	private BinaDTO bina=new BinaDTO();
	private  int binaId;
	
	@PostConstruct
	public void init(){
		Object binaId=((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().getAttribute("binaId");
		bina=((BinaDTO)((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().getAttribute("bina"));
		this.binaId=Integer.parseInt(binaId.toString());
		((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().removeAttribute("binaId");
		((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().removeAttribute("bina");

		if (this.binaId>0){
			//this.binaId=(Integer)binaId;
			try {
				kisiList=new BinaBusiness().binaKisiler(this.binaId);
				kisiData=new BinaKisiDataModel(kisiList);
			} catch (CRUDException e) {
			
				e.printStackTrace();
			}
		}
		
		if (sTurList==null || sTurList.size()<=0){
			try {
				
				
				
				sTurList=new BinaBusiness().getBinaSorumlulukTur();
				
			
			} catch (CRUDException e) {
				e.printStackTrace();
			}
			}
			
	
	}
	
	

	public int getBinaId() {
		return binaId;
	}



	public void setBinaId(int binaId) {
		this.binaId = binaId;
	}



	public BinaKisiDTO getBinaKisi() {
		return binaKisi;
	}


	public void setBinaKisi(BinaKisiDTO binaKisi) {
		this.binaKisi = binaKisi;
	}
	public void binaKisiEkle(AjaxBehaviorEvent event){
		FacesMessage msg;
		try {
			binaKisi.setTelefonNo(Long.parseLong((binaKisi.getTelefonNoStr().equals("")?"0":binaKisi.getTelefonNoStr()).replace("(","").replace(")", "").replace(" ","")));
			binaKisi.setTelefonNoGsm(Long.parseLong(binaKisi.getTelefonNoGsmStr().replace("(","").replace(")", "").replace(" ","")));
			if (binaKisi.getId()==0){
				binaKisi.setBinaId(this.binaId);	
				int id=new BinaBusiness().setBinaKisi(binaKisi);
				binaKisi.setId(id);
			
			}else{
				new BinaBusiness().updateBinaKisi(binaKisi);
				for (BinaKisiDTO k:kisiList){
					if (k.getId()==binaKisi.getId()){
						kisiList.remove(k);
						break;
					}
				}
			}
			binaKisi.setKayitTarihi(new Date());
			kisiList.add(binaKisi);
			kisiData=new BinaKisiDataModel(kisiList);
			binaKisi=new BinaKisiDTO();
		} catch (Exception e) {
			msg=new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null,msg);
		}
	}
		 public void kisiSelect(SelectEvent event) throws CloneNotSupportedException {
		       
		       
				String id = String.valueOf(((BinaKisiDTO) event.getObject()).getId());
				if (id!=null){
					binaKisi=new BinaKisiDTO();
					binaKisi=(BinaKisiDTO)slctKisi.clone();
					 
				}
				
		    }
		

	public List<BinaKisiDTO> getKisiList() {
		return kisiList;
	}


	public void setKisiList(List<BinaKisiDTO> kisiList) {
		this.kisiList = kisiList;
	}
	public BinaKisiDataModel getKisiData() {
		return kisiData;
	}
	

	public void setKisiData(BinaKisiDataModel kisiData) {
		this.kisiData = kisiData;
	}
	
	public BinaKisiDTO getSlctKisi() {
		return slctKisi;
	}


	public void setSlctKisi(BinaKisiDTO slctKisi) {
		this.slctKisi = slctKisi;
	}
	
	public List<BinaSorumlulukDTO> getsTurList() {
		return sTurList;
	}


	public void setsTurList(List<BinaSorumlulukDTO> sTurList) {
		this.sTurList = sTurList;
	}



	public BinaDTO getBina() {
		return bina;
	}



	public void setBina(BinaDTO bina) {
		this.bina = bina;
	}



	public List<BinaKisiDTO> getFilterKisi() {
		return filterKisi;
	}



	public void setFilterKisi(List<BinaKisiDTO> filterKisi) {
		this.filterKisi = filterKisi;
	}


public void resetPanel(AjaxBehaviorEvent event){
	this.binaKisi=new BinaKisiDTO();
	this.slctKisi=new BinaKisiDTO();
}
	
	
	

}
