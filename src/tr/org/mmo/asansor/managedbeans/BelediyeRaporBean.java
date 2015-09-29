package tr.org.mmo.asansor.managedbeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.business.InformationBusiness;
import tr.org.mmo.asansor.business.RaporBusiness;
import tr.org.mmo.asansor.dto.BinaKontrolVeOdemelerDTO;
import tr.org.mmo.asansor.dto.EskiRaporDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.models.BinaKontrolVeOdemeDataModel;
import tr.org.mmo.asansor.util.Messages;

@ManagedBean
@ViewScoped
public class BelediyeRaporBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date tarih1;
	private Date tarih2;
	private List<BinaKontrolVeOdemelerDTO> filteredBinaKontrol;
	private BinaKontrolVeOdemelerDTO selected=new BinaKontrolVeOdemelerDTO();
	private List<BinaKontrolVeOdemelerDTO> belediyeKontroller=new ArrayList<BinaKontrolVeOdemelerDTO>();
	private BinaKontrolVeOdemeDataModel belediyeKontrolData=new BinaKontrolVeOdemeDataModel(new ArrayList<BinaKontrolVeOdemelerDTO>());

	@ManagedProperty(value = "#{applicationBean.fileMap}")
	private HashMap<Integer, List<File>> fileMap;
	public List<BinaKontrolVeOdemelerDTO> getBelediyeKontroller() {
		return belediyeKontroller;
	}

	public void setBelediyeKontroller(
			List<BinaKontrolVeOdemelerDTO> belediyeKontroller) {
		this.belediyeKontroller = belediyeKontroller;
	}
	

public void dateBlurListener(AjaxBehaviorEvent event){
	Object o = event.getSource();
	UIComponent uc = event.getComponent();
	







belediyeKontroller=new ArrayList<BinaKontrolVeOdemelerDTO>();
belediyeKontrolData=new BinaKontrolVeOdemeDataModel(belediyeKontroller);

	if (!tarihKontrol(o, uc)) {

		FacesMessage msg = new FacesMessage(
				Messages._HATALITARIH_.getMesaj());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
public void dateSelectListener(SelectEvent event){
	
	

	Object o = event.getObject();
	UIComponent uc = event.getComponent();
	

belediyeKontroller=new ArrayList<BinaKontrolVeOdemelerDTO>();
belediyeKontrolData=new BinaKontrolVeOdemeDataModel(belediyeKontroller);

	if (!tarihKontrol(o, uc)) {

		FacesMessage msg = new FacesMessage(
				Messages._HATALITARIH_.getMesaj());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}

public BinaKontrolVeOdemeDataModel getBelediyeKontrolData() {
	return belediyeKontrolData;
}

public void setBelediyeKontrolData(
		BinaKontrolVeOdemeDataModel belediyeKontrolData) {
	this.belediyeKontrolData = belediyeKontrolData;
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

public void binaKontroller(ActionEvent event){
	try{
		
			if (tarih1!=null && tarih1.getTime()>0 && tarih2!=null && tarih2.getTime()>0){
				
				belediyeKontroller=new ArrayList<BinaKontrolVeOdemelerDTO>();
				belediyeKontrolData=new BinaKontrolVeOdemeDataModel(belediyeKontroller);
				belediyeKontroller=	InformationBusiness.INSTANCE.getBinaKontroller(tarih1,tarih2);
	if (belediyeKontroller.size()>0){
		
		belediyeKontrolData=new BinaKontrolVeOdemeDataModel(belediyeKontroller);
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

public List<BinaKontrolVeOdemelerDTO> getFilteredBinaKontrol() {
	return filteredBinaKontrol;
}

public void setFilteredBinaKontrol(
		List<BinaKontrolVeOdemelerDTO> filteredBinaKontrol) {
	this.filteredBinaKontrol = filteredBinaKontrol;
}

private boolean tarihKontrol(Object o, UIComponent uc) {
	boolean bool = true;

	
	
	if (tarih1 != null && tarih2 != null
			&& tarih2.compareTo(tarih1)<=0) {
		bool = false;

	}
	return bool;

}
public void changeTableViewListener(ActionEvent event){
	
	DataTable dataTable=new DataTable();
	dataTable=(DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent(":formraporara:binakontrolodemetable");
	
	if (dataTable!=null){
		Boolean bool=dataTable.isResizableColumns();
		dataTable.setResizableColumns(!bool);
		dataTable.setLiveResize(!bool);
	}
}

public BinaKontrolVeOdemelerDTO getSelected() {
	return selected;
}

public void setSelected(BinaKontrolVeOdemelerDTO selected) {
	this.selected = selected;
}


public EskiRaporDTO eskiRaporlarListener(int kontrolId) {
	
	FacesMessage msg = new FacesMessage();
	EskiRaporDTO rapor=new EskiRaporDTO();
	
		try {
			
			rapor = new RaporBusiness().eskiRaporGetirByKontrolId(kontrolId);
		
		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}
		return rapor;
	
}

@SuppressWarnings("unused")
private void diger(EskiRaporDTO eskiRaporDTO,int tescilNo,int basvuruId) {

	String path = "//var//pdf//";

	String raporAdi = "";
	int binaTescilNo = 0;
	
		
		
			raporAdi = tescilNo + "__" + basvuruId + "__"
					+ eskiRaporDTO.getEskiKontrolKodu() + ".pdf";

			

	File file = null;

	if (!raporAdi.equals("")) {

		file = new File(path + raporAdi);

		if (file != null) {
			try {
				raporGonder(file);
			} catch (CRUDException e1) {
				try {
					raporFromMap(raporAdi, binaTescilNo);
				} catch (CRUDException e2) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(e1.getMessage()));
					e2.printStackTrace();
				}

			}
		} else {
			try {
				raporFromMap(raporAdi, binaTescilNo);
			} catch (CRUDException e2) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e2.getMessage()));
				e2.printStackTrace();
			}
		}

	} else {
		addMessage();
	}

}
private void raporFromMap(String raporAdi, int binaTescilNo)
		throws CRUDException {

	int j = 0;
	j = (binaTescilNo / 1000) % 1000;
	List<File> fileList = new ArrayList<File>();
	try {
		fileList = fileMap.get(j);
		if (fileList.size() > 0)
			readFileFromMap(fileList, raporAdi);
		else
			throw new CRUDException("Dosya Bulunamadı", null);
	} catch (NullPointerException e) {
		throw new CRUDException(Messages._NULLPOINTER_.getMesaj(), null);
	}

}

private void addMessage() {
	FacesContext.getCurrentInstance().addMessage(null,
			new FacesMessage("Dosya Bulunamadı"));
}
public void raporGetir(BinaKontrolVeOdemelerDTO binaKontrol) {
	DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
	try {
		EskiRaporDTO eskiRaporDTO=new EskiRaporDTO();
		eskiRaporDTO=eskiRaporlarListener(binaKontrol.getKontrolId());
		boolean eskiRapor = false;
		
		eskiRapor = new RaporBusiness().isEskiRaporMu(eskiRaporDTO
				.getRaporId());
		// try {
		// if
		// (eskiRaporDTO.getRaporTarih().compareTo(df.parse("08.12.2014"))
		// == -1) {
		if (eskiRapor) {
			diger(eskiRaporDTO,binaKontrol.getTescilNo(),binaKontrol.getBasvuruId());

		} else {
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			request.getSession().setAttribute("dosya",
					eskiRaporDTO.getDosya());
			request.getSession().setAttribute("dosyaAdi",
					eskiRaporDTO.getDosyaAdi());

			try {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								FacesContext.getCurrentInstance()
										.getExternalContext()
										.getRequestContextPath()
										+ "/PDFServlet");

			} catch (IOException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
				e.printStackTrace();
			}

		}
		// }
		/*
		 * catch (ParseException e) {
		 * 
		 * e.printStackTrace(); }
		 */
	} catch (CRUDException e) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(e.getMessage()));
	}
}
public void raporGonder(File file) throws CRUDException {
	byte[] fileBytes;

	fileBytes = raporToByteArray(file);
	HttpServletRequest request = (HttpServletRequest) FacesContext
			.getCurrentInstance().getExternalContext().getRequest();
	request.getSession().setAttribute("dosya", fileBytes);
	request.getSession().setAttribute("dosyaAdi", file.getName());

	try {
		FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.redirect(
						FacesContext.getCurrentInstance()
								.getExternalContext()
								.getRequestContextPath()
								+ "/PDFServlet");

	} catch (IOException e) {

		e.printStackTrace();
		throw new CRUDException("Rapor bulunamadı", null);

	}

}

public void readFileFromMap(List<File> files, String raporAdi)
		throws CRUDException {
	byte[] fileBytes;
	for (File file : files) {
		if (file.getName().equals(raporAdi)) {

			fileBytes = raporToByteArray(file);
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			request.getSession().setAttribute("dosya", fileBytes);
			request.getSession().setAttribute("dosyaAdi", file.getName());

			try {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								FacesContext.getCurrentInstance()
										.getExternalContext()
										.getRequestContextPath()
										+ "/PDFServlet");

			} catch (IOException e) {
				e.printStackTrace();
				throw new CRUDException("Rapor bulunamadı", null);
			}

			break;
		}

	}

}
private byte[] raporToByteArray(File f) throws CRUDException {
	FileInputStream fileInputStream = null;
	byte[] bFile = new byte[(int) f.length()];

	try {
		// convert file into array of bytes
		fileInputStream = new FileInputStream(f);
		fileInputStream.read(bFile);
		fileInputStream.close();
		/*
		 * for (int i = 0; i < bFile.length; i++) {
		 * System.out.print((char)bFile[i]); }
		 */
		// System.out.println("Done");
	} catch (FileNotFoundException e) {
		throw new CRUDException("Rapor Bulunamadı !", null);
	} catch (Exception e) {
		throw new CRUDException(Messages._SQL_507_.getMesaj(), null);
	}
	return bFile;

}

public HashMap<Integer, List<File>> getFileMap() {
	return fileMap;
}

public void setFileMap(HashMap<Integer, List<File>> fileMap) {
	this.fileMap = fileMap;
}



}
