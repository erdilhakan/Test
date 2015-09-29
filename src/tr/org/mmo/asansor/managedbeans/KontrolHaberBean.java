package tr.org.mmo.asansor.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.fieldset.Fieldset;

import tr.org.mmo.asansor.business.BinaBusiness;
import tr.org.mmo.asansor.dto.CihazDTO;
import tr.org.mmo.asansor.dto.KontrolHaberDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.models.KontrolHaberDataModel;

@ManagedBean(eager = true)
@ViewScoped
public class KontrolHaberBean implements Serializable {

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 8055414672631507833L;
    private List<KontrolHaberDTO> binalarListe = new ArrayList<KontrolHaberDTO>();
	private List<CihazDTO> cihazList=new ArrayList<CihazDTO>();
	private KontrolHaberDataModel kontrolDataModel;
	HashMap<Integer, List<CihazDTO>> cihazMap=new HashMap<Integer, List<CihazDTO>>();
	private List<KontrolHaberDTO> filteredVal;
	private KontrolHaberDTO selectedVal;
	private Date kontrolTarihi = new Date();

	public List<KontrolHaberDTO> getBinalarListe() {
		return binalarListe;
	}
	
	public void setBinalarListe(List<KontrolHaberDTO> binalarListe) {
		this.binalarListe = binalarListe;
	}

	public void kontrolListener(ActionEvent event){
		List<KontrolHaberDTO> binalar=new ArrayList<KontrolHaberDTO>();
		try {
			binalar=new BinaBusiness().kontrolListesiGetir(kontrolTarihi);
			
			HashMap<Integer, KontrolHaberDTO> binaMap=new HashMap<Integer, KontrolHaberDTO>();
			
			
			for (KontrolHaberDTO b:binalar){
				binaMap.put(b.getBinaId(), b);
				CihazDTO cihaz=new CihazDTO();
				cihaz.setKimlikNo(b.getCihazKimlikNo());
				cihaz.setCihazId(b.getCihazId());
				cihaz.setKontrolTarihi(b.getSonKontrolTarihi());
				cihaz.setSonKontrolEtiketi(b.getEtiket());
				
				if (cihazMap.get(b.getBinaId())==null || cihazMap.get(b.getBinaId()).isEmpty()){
					List<CihazDTO> l=new ArrayList<CihazDTO>();
					l.add(cihaz);
					cihazMap.put(b.getBinaId(), l);
				}else{
					List<CihazDTO> l=cihazMap.get(b.getBinaId());
					l.add(cihaz);
					cihazMap.put(b.getBinaId(), l);
				}
			}
			this.binalarListe=new ArrayList<KontrolHaberDTO>();
			Set<Integer> set=binaMap.keySet();
			Iterator<Integer> iterator=set.iterator();
			while(iterator.hasNext()){
				int i=iterator.next();
				this.binalarListe.add(binaMap.get(i));
			}
			
			kontrolDataModel=new KontrolHaberDataModel();
			kontrolDataModel=new KontrolHaberDataModel(this.binalarListe);
			Fieldset f=(Fieldset)FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:fset");
			try {
				f.setRendered(true);
			} catch (NullPointerException e) {
				// TODO: handle exception
			}
		} 
		
		
		catch (CRUDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void onRowSelect(KontrolHaberDTO bina) {
		cihazList=cihazMap.get(bina.getBinaId());
	}
	public List<CihazDTO> getCihazList() {
		return cihazList;
	}
	public void setCihazList(List<CihazDTO> cihazList) {
		this.cihazList = cihazList;
	}
	public List<KontrolHaberDTO> getFilteredVal() {
		return filteredVal;
	}
	public void setFilteredVal(List<KontrolHaberDTO> filteredVal) {
		this.filteredVal = filteredVal;
	}
	public KontrolHaberDataModel getKontrolDataModel() {
		return kontrolDataModel;
	}
	
	
	public KontrolHaberDTO getSelectedVal() {
		return selectedVal;
	}

	public void setSelectedVal(KontrolHaberDTO selectedVal) {
		this.selectedVal = selectedVal;
	}

	public void setKontrolDataModel(KontrolHaberDataModel kontrolDataModel) {
		this.kontrolDataModel = kontrolDataModel;
	}
	public Date getKontrolTarihi() {
		return kontrolTarihi;
	}
	public void setKontrolTarihi(Date kontrolTarihi) {
		this.kontrolTarihi = kontrolTarihi;
	}
	
	
	
}
