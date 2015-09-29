package tr.org.mmo.asansor.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;

import btest.*;

import org.primefaces.component.panel.Panel;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.business.BelediyeBusiness;
import tr.org.mmo.asansor.business.WebServiceBusiness;
import tr.org.mmo.asansor.dao.ApplicationDAOImpl;
import tr.org.mmo.asansor.dto.BelediyeDTO;
import tr.org.mmo.asansor.dto.BelediyeIletisimDTO;
import tr.org.mmo.asansor.dto.BelediyeSozlesmeDTO;
import tr.org.mmo.asansor.dto.TemsilcilikDTO;
import tr.org.mmo.asansor.dto.UavtBelediyeDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.models.BelediyeSozlesmeModel;
import tr.org.mmo.asansor.util.BelediyeComparator;
import tr.org.mmo.asansor.util.BelediyeSozlesmeComparator;
import tr.org.mmo.asansor.util.IlIlceComparator;
import tr.org.mmo.asansor.util.Messages;

@ManagedBean
@ViewScoped
public class BelediyeBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8291308623380907551L;
	private BelediyeIletisimDTO iletisim = new BelediyeIletisimDTO();
	private boolean iletisimReq = false;
	private TreeMap<String, Integer> ilceler;
	private List<BelediyeIletisimDTO> iletisimList;
	private boolean kaydedildi = false;
	private BelediyeDTO belediye;
	private int belediyeKod;
	private List<Integer> yillar;
	private boolean degisti = false;
	private boolean navigate = false;
	private BelediyeSozlesmeDTO sozlesme = new BelediyeSozlesmeDTO();
	private int[] sozlesmeBinaTipList;

	private List<BelediyeSozlesmeDTO> sozlesmeList = new ArrayList<BelediyeSozlesmeDTO>();
	private BelediyeSozlesmeDTO slctSozlesme;

	private BelediyeSozlesmeModel sozlesmeDataModel;
	private List<TemsilcilikDTO> temsilcilikler = new ArrayList<TemsilcilikDTO>();

	@ManagedProperty("#{belediyeListeBean.slctBelediye}")
	private BelediyeDTO slctBelediye;

	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	public List<Integer> getYillar() {
		return yillar;
	}

	public void setYillar(List<Integer> yillar) {
		this.yillar = yillar;
	}

	private List<Kullanici> filteredVal;

	public List<Kullanici> getFilteredVal() {
		return filteredVal;
	}

	public void setFilteredVal(List<Kullanici> filteredVal) {
		this.filteredVal = filteredVal;
	}

	public BelediyeDTO getBelediye() {
		return belediye;
	}

	public void setBelediye(BelediyeDTO belediye) {
		this.belediye = belediye;
	}

	public String onFlowProcess(FlowEvent event) {

		if (navigate && !kaydedildi && degisti) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(Messages._BELEDIYEDEGISIKLIKKAYDET_
							.getMesaj()));
			return event.getOldStep();
		}
		if (navigate && !degisti) {
			return event.getNewStep();
		}
		if (event.getOldStep().equals("genel") && !kaydedildi) {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(Messages._BELEDIYEDEGISIKLIKKAYDET_
							.getMesaj()));
			return event.getOldStep();
		}
		if ((event.getOldStep().equals("genel") && kaydedildi)
				|| (!event.getOldStep().equals("genel"))) {
			return event.getNewStep();
		} else {
			return event.getOldStep();
		}
	}

	@PostConstruct
	public void init() {

		slctBelediye = (BelediyeDTO) ((HttpServletRequest) (FacesContext
				.getCurrentInstance().getExternalContext().getRequest()))
				.getSession().getAttribute("belediye");
		belediye = slctBelediye == null ? new BelediyeDTO() : slctBelediye;
		navigate = slctBelediye == null ? false : true;
		belediyeKod = slctBelediye == null ? 0 : slctBelediye.getKod();
		sozlesmeDataModel = new BelediyeSozlesmeModel(sozlesmeList);
		if (slctBelediye == null) {
			ilceler = new TreeMap<String, Integer>();
			sozlesmeList = new ArrayList<BelediyeSozlesmeDTO>();
			temsilcilikler = new ArrayList<TemsilcilikDTO>();
		}

		else {
			ApplicationBean bean = (ApplicationBean) FacesContext
					.getCurrentInstance().getExternalContext()
					.getApplicationMap().get("applicationBean");
			ilceler = new TreeMap<String, Integer>(new IlIlceComparator());
			if (slctBelediye.getIl() > 0) {
				ilceler.putAll(bean.getIlceler().get(slctBelediye.getIl()));
			}

			getBelediyeSozlesme(slctBelediye.getKod());
			temsilcilikler = new ArrayList<TemsilcilikDTO>();
			for (TemsilcilikDTO t : sessionBean.getTemsilcilikler()) {
				if (t.getSubesi() == slctBelediye.getSube()) {
					temsilcilikler.add(t);
				}
			}

		}
		iletisimList = slctBelediye == null ? new ArrayList<BelediyeIletisimDTO>()
				: getBelediyeIletisim(slctBelediye.getKod());

		((HttpServletRequest) (FacesContext.getCurrentInstance()
				.getExternalContext().getRequest())).getSession()
				.removeAttribute("belediye");

		iletisim = new BelediyeIletisimDTO();
		Calendar cal = Calendar.getInstance();

		cal.setTime(new Date());
		yillar = new ArrayList<Integer>();
		yillar.add(cal.get(Calendar.YEAR) - 3);
		yillar.add(cal.get(Calendar.YEAR) - 2);
		yillar.add(cal.get(Calendar.YEAR) - 1);
		yillar.add(cal.get(Calendar.YEAR));
		yillar.add(cal.get(Calendar.YEAR) + 1);
		yillar.add(cal.get(Calendar.YEAR) + 2);
		yillar.add(cal.get(Calendar.YEAR) + 3);
		sozlesme = new BelediyeSozlesmeDTO();

	}

	private List<BelediyeIletisimDTO> getBelediyeIletisim(int kod) {
		List<BelediyeIletisimDTO> iletisimList = new ArrayList<BelediyeIletisimDTO>();
		try {
			iletisimList = new BelediyeBusiness().getBelediyeIletisim(kod);
			for (BelediyeIletisimDTO iletisim : iletisimList) {
				StringBuilder sb = new StringBuilder();
				String str = String.valueOf(iletisim.getTelefonNo()).trim();
				sb.append("(");
				sb.append(str.substring(0, 3));
				sb.append(")");
				sb.append(str.substring(3));
				iletisim.setTelefonNoStr(sb.toString());
			}
		} catch (CRUDException e) {
			e.printStackTrace();
		}

		return iletisimList;
	}

	private void getBelediyeSozlesme(int kod) {
		sozlesmeList = new ArrayList<BelediyeSozlesmeDTO>();
		try {
			sozlesmeList = new BelediyeBusiness().getBelediyeSozlesme(kod);
			Collections.sort(sozlesmeList, new BelediyeSozlesmeComparator());
			sozlesmeDataModel = new BelediyeSozlesmeModel(sozlesmeList);
		} catch (CRUDException e) {
			e.printStackTrace();
		}

	}

	public List<BelediyeIletisimDTO> getIletisimList() {
		return iletisimList;
	}

	public void setIletisimList(List<BelediyeIletisimDTO> iletisimList) {
		this.iletisimList = iletisimList;
	}

	public void ilChange(AjaxBehaviorEvent event) {
		int ilKodu = Integer.parseInt(((UIInput) (event.getComponent()))
				.getValue().toString());

		ApplicationBean bean = (ApplicationBean) FacesContext
				.getCurrentInstance().getExternalContext().getApplicationMap()
				.get("applicationBean");
		ilceler = new TreeMap<String, Integer>(new IlIlceComparator());
		ilceler.putAll(bean.getIlceler().get(ilKodu));
		degisti = true;

	}

	public TreeMap<String, Integer> getIlceler() {
		return ilceler;
	}

	public int getBelediyeKod() {
		return belediyeKod;
	}

	public void setBelediyeKod(int belediyeKod) {
		this.belediyeKod = belediyeKod;
	}

	@SuppressWarnings("unused")
	public void ilceChange(AjaxBehaviorEvent event) {
		int ilceKodu = Integer.parseInt(((UIInput) (event.getComponent()))
				.getValue().toString());
		this.belediye.setUavtKod(0);
		degisti = true;

	}

	@SuppressWarnings("unused")
	public String sozlesmeKaydet() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		try {

			if (sozlesme.getSozlesmeBaslangicTarihi().getTime() >= sozlesme
					.getSozlesmeBitisTarihi().getTime()) {
				msg = new FacesMessage(
						Messages._SOZLESMETARIHLERIHATALI_.getMesaj());
			} else {
				boolean sozlesmeHatali = false;
				boolean sozlesmeVar=false;
				for (BelediyeSozlesmeDTO s : sozlesmeList) {
					if (sozlesmeHatali)
						break;
					for (int i : sozlesmeBinaTipList) {
						for (int j : sozlesme.getArrCihazTip()) {
							boolean bool=false;
							ss:
							for (String str:sozlesme.getKontrolTipiArr()){
								if (s.getKontrolTipi()!=null && s.getKontrolTipi().equals(str)){
									bool =true;
									break ss;
								}
								
							}
							if (bool && sozlesme.getKapasiteOlcut().equals(s.getKapasiteOlcut())){
							if (j == s.getCihazTipi()
									&& i == s.getSozlesmeBinaTipId()
									&& sozlesme.getKapasite() == s
											.getKapasite()
									&& (
											(( sozlesme
											.getSozlesmeBaslangicTarihi().after(s.getSozlesmeBaslangicTarihi()) 
											|| sozlesme.getSozlesmeBaslangicTarihi().equals(s.getSozlesmeBaslangicTarihi()))
											&& 
											(
											sozlesme
											.getSozlesmeBaslangicTarihi().before(s.getSozlesmeBitisTarihi()) || sozlesme
											.getSozlesmeBaslangicTarihi().equals(s.getSozlesmeBitisTarihi())) && ((sozlesme.getId()>0 && sozlesme.getId()!=s.getId()) || sozlesme.getId()==0 ))
										
											||
											( ( sozlesme
													.getSozlesmeBitisTarihi().after(s.getSozlesmeBaslangicTarihi())
													||
													sozlesme
													.getSozlesmeBitisTarihi().equals(s.getSozlesmeBaslangicTarihi())
													)
													&& 
													(
													sozlesme
													.getSozlesmeBitisTarihi().before(s.getSozlesmeBitisTarihi())
													||
													sozlesme
													.getSozlesmeBitisTarihi().equals(s.getSozlesmeBitisTarihi())
													) && ((sozlesme.getId()>0 && sozlesme.getId()!=s.getId()) || sozlesme.getId()==0 ))
													
													
													||
													( (s
															.getSozlesmeBaslangicTarihi().after(sozlesme.getSozlesmeBaslangicTarihi())
															||
															s
															.getSozlesmeBaslangicTarihi().equals(sozlesme.getSozlesmeBaslangicTarihi())
															)
															&& 
															(
															s
															.getSozlesmeBaslangicTarihi().before(sozlesme.getSozlesmeBitisTarihi()) 
															||
															s
															.getSozlesmeBaslangicTarihi().equals(sozlesme.getSozlesmeBitisTarihi())
															)
															&& ((sozlesme.getId()>0 && sozlesme.getId()!=s.getId()) || sozlesme.getId()==0 ))
															
															||
															((s
																	.getSozlesmeBitisTarihi().after(sozlesme.getSozlesmeBaslangicTarihi())
																	||
																	s
																	.getSozlesmeBitisTarihi().equals(sozlesme.getSozlesmeBaslangicTarihi())
																	)
																	&& 
																	(
																	s
																	.getSozlesmeBitisTarihi().before(sozlesme.getSozlesmeBitisTarihi())
																	||
																	s
																	.getSozlesmeBitisTarihi().equals(sozlesme.getSozlesmeBitisTarihi())
																	)
																	&& ((sozlesme.getId()>0 && sozlesme.getId()!=s.getId()) || sozlesme.getId()==0 ))
																	
											
											)){
								sozlesmeHatali = true;
								sozlesmeVar=true;
							break;
							}

						}
					}
					}

				}
				if (sozlesmeHatali){
					msg = new FacesMessage(
							Messages._SOZLESMETARIHLERIHATALI_.getMesaj());
					if (sozlesmeVar)
						msg = new FacesMessage(
								Messages._SOZLESMEVAR_.getMesaj());
				}
				else {
					sozlesme.setBelediyeKod(belediyeKod);
					List<BelediyeSozlesmeDTO> l1 = new ArrayList<BelediyeSozlesmeDTO>();
					if (sozlesme.getId() == 0) {

						l1 = new BelediyeBusiness().sozlesmeEkle(sozlesme,
								sozlesmeBinaTipList);
						getBelediyeSozlesme(belediyeKod);

					} else {
						if (sozlesme.getKontrolTipiArr().length > 1)
							throw new UpdateException(
									Messages._SOZLESMEGUNCELLEMESOZLESMETIPHATA_
											.getMesaj(),
									null);
						if (sozlesmeBinaTipList.length > 1)
							throw new UpdateException(
									Messages._SOZLESMEGUNCELLEMEBINATIPHATA_
											.getMesaj(),
									null);
						if (sozlesme.getArrCihazTip().length > 1)
							throw new UpdateException(
									Messages._SOZLESMEGUNCELLEMEASANSORTIPHATA_
											.getMesaj(),
									null);
						sozlesme.setCihazTipi(sozlesme.getArrCihazTip()[0]);
						new BelediyeBusiness().sozlesmeGuncelle(sozlesme,
								sozlesmeBinaTipList[0]);
						sozlesme.setSozlesmeBinaTipId(sozlesmeBinaTipList[0]);
						for (BelediyeSozlesmeDTO s : sozlesmeList) {
							if (s.getId() == sozlesme.getId()) {
								sozlesmeList.remove(s);

								break;
							}
						}
						sozlesme.setKontrolTipi(sozlesme.getKontrolTipiArr()[0]);
						sozlesmeList.add(sozlesme);
						
						Collections.sort(sozlesmeList,
								new BelediyeSozlesmeComparator());
						sozlesmeDataModel = new BelediyeSozlesmeModel(
								sozlesmeList);
					}

					sozlesme = new BelediyeSozlesmeDTO();
					sozlesmeBinaTipList = new int[0];
					slctSozlesme = new BelediyeSozlesmeDTO();
					msg = new FacesMessage(
							Messages._SOZLESMEKAYDEDILDI_.getMesaj());
					msg.setSeverity(FacesMessage.SEVERITY_INFO);
				}
			}

		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		context.addMessage(null, msg);

		return "";

	}

	public void iletisimKaydet(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		try {
			/*
			 * if (iletisim.getAdi().trim().equals("") ||
			 * iletisim.getSoyadi().trim().equals("") ||
			 * iletisim.getTelefonNoStr().trim().equals("") ||
			 * iletisim.getePosta().trim().equals("") ||
			 * iletisim.getUnvani().trim().equals("") ){ msg=new
			 * FacesMessage(Messages._ALANA_DEGER_GIRMELISINIZ_.getMesaj());
			 * msg.setSeverity(FacesMessage.SEVERITY_INFO); }else{
			 */
			iletisim.setBelediyeKod(belediyeKod);
			iletisim.setTelefonNo(Long.parseLong(iletisim.getTelefonNoStr()
					.replace("(", "").replace(")", "").replace(" ", "").trim()));
			int id = new BelediyeBusiness().iletisimEkle(iletisim);
			iletisim.setId(id);
			iletisimList.add(iletisim);
			iletisim = new BelediyeIletisimDTO();
			iletisimReq = false;
			msg = new FacesMessage(Messages._ILETISIMKAYDEDILDI_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			Panel p = (Panel) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent(":formb:pnl11");
			p.setVisible(false);
			// }
		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		context.addMessage(null, msg);

	}

	public BelediyeIletisimDTO getIletisim() {
		return iletisim;
	}

	public void setIletisim(BelediyeIletisimDTO iletisim) {
		this.iletisim = iletisim;
	}

	public String onEdit(RowEditEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		int id = ((BelediyeIletisimDTO) event.getObject()).getId();
		for (BelediyeIletisimDTO b : iletisimList) {
			if (b.getId() == id) {

				try {
					b.setTelefonNo(Long.parseLong(b.getTelefonNoStr()
							.replace("(", "").replace(")", "").replace(" ", "")
							.trim()));
					new BelediyeBusiness().iletisimGuncelle(b);
					msg = new FacesMessage(
							Messages._ILETISIMKAYDEDILDI_.getMesaj());
					context.addMessage(null, msg);
				} catch (CRUDException e) {
					msg = new FacesMessage(e.getMessage());
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					context.addMessage(null, msg);
				}

				break;

			}

		}
		return "";
	}

	public void onCancel(RowEditEvent event) {

	}

	public void belediyeKaydet(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		try {
			if (!navigate) {
				belediyeKod = new BelediyeBusiness().belediyeEkle(belediye);
			} else {
				belediyeKod = belediye.getKod();
				new BelediyeBusiness().belediyeKaydet(belediye);
				for (BelediyeDTO b : loginBean.getBelediyeList()) {
					if (b.getKod() == belediyeKod) {
						loginBean.getBelediyeList().remove(b);
						break;
					}
				}
				loginBean.getBelediyeList().add(belediye);
				Collections.sort(loginBean.getBelediyeList(),
						new BelediyeComparator());
			}

			kaydedildi = true;
			msg = new FacesMessage(Messages._BELEDIYEKAYDEDILDI_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);

		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		context.addMessage(null, msg);

	}

	public void submit() {
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.put("belediyeBean", this);

	}

	public BelediyeDTO getSlctBelediye() {
		return slctBelediye;
	}

	public void setSlctBelediye(BelediyeDTO slctBelediye) {
		this.slctBelediye = slctBelediye;
	}

	public String deleteRow(BelediyeIletisimDTO belediyeIletisim) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;

		if (belediyeIletisim != null) {
			try {
				new BelediyeBusiness().iletisimSil(belediyeIletisim.getId());
				this.iletisimList.remove(belediyeIletisim);
				if (this.iletisimList == null || this.iletisimList.size() <= 0) {
					iletisimList = new ArrayList<BelediyeIletisimDTO>();
				}
				msg = new FacesMessage(
						Messages._BELEDIYEILETISIMSILINDI_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_INFO);

			} catch (CRUDException e) {
				msg = new FacesMessage(
						Messages._BELEDIYEILETISIMSILINEMEDI_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);

			}
			context.addMessage(null, msg);
		}
		return "";

	}

	public BelediyeSozlesmeDTO getSozlesme() {
		return sozlesme;
	}

	public void setSozlesme(BelediyeSozlesmeDTO sozlesme) {
		this.sozlesme = sozlesme;
	}

	public void dateListener(SelectEvent event) {

		if (sozlesme.getSozlesmeBaslangicTarihi() != null
				&& sozlesme.getSozlesmeBitisTarihi() != null) {
			if (sozlesme.getSozlesmeBaslangicTarihi().getTime() >= sozlesme
					.getSozlesmeBitisTarihi().getTime()) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(Messages._SOZLESMETARIHLERIHATALI_
								.getMesaj()));
			}

		}

	}

	public List<BelediyeSozlesmeDTO> getSozlesmeList() {
		return sozlesmeList;
	}

	public void setSozlesmeList(List<BelediyeSozlesmeDTO> sozlesmeList) {
		this.sozlesmeList = sozlesmeList;
	}

	public BelediyeSozlesmeDTO getSlctSozlesme() {
		return slctSozlesme;
	}

	public void setSlctSozlesme(BelediyeSozlesmeDTO slctSozlesme) {
		this.slctSozlesme = slctSozlesme;
	}

	public BelediyeSozlesmeModel getSozlesmeDataModel() {
		return sozlesmeDataModel;
	}

	public void setSozlesmeDataModel(BelediyeSozlesmeModel sozlesmeDataModel) {
		this.sozlesmeDataModel = sozlesmeDataModel;
	}

	public void onRowSelect(SelectEvent event)
			throws CloneNotSupportedException {

		String id = String.valueOf(((BelediyeSozlesmeDTO) event.getObject())
				.getId());

		if (id != null) {

			sozlesme = new BelediyeSozlesmeDTO();

			sozlesme = (BelediyeSozlesmeDTO) slctSozlesme.clone();
			sozlesme.setKontrolTipiArr(new String[]{slctSozlesme.getKontrolTipi()});
			sozlesmeBinaTipList = new int[1];
			sozlesmeBinaTipList[0] = sozlesme.getSozlesmeBinaTipId();
			sozlesme.setArrCihazTip(new int[1]);
			sozlesme.getArrCihazTip()[0] = sozlesme.getCihazTipi();

		}
	}

	public String deleteRow() {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;

		if (slctSozlesme != null && slctSozlesme.getId() != 0) {
			try {
				new BelediyeBusiness().sozlesmeSil(slctSozlesme.getId());
				this.sozlesmeList.remove(slctSozlesme);
				if (this.sozlesmeList.size() == 0) {
					this.sozlesmeList = new ArrayList<BelediyeSozlesmeDTO>();
				}
				sozlesmeDataModel = new BelediyeSozlesmeModel(sozlesmeList);
				msg = new FacesMessage(Messages._SOZLESMESILINDI_.getMesaj());
				sozlesme = new BelediyeSozlesmeDTO();
				slctSozlesme = new BelediyeSozlesmeDTO();

				sozlesmeBinaTipList = new int[0];
				context.addMessage(null, msg);
			} catch (CRUDException e) {
				msg = new FacesMessage(Messages._SOZLESMESILINEMEDI_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				context.addMessage(null, msg);
			}
		}
		return "";

	}

	public List<TemsilcilikDTO> getTemsilcilikler() {
		return temsilcilikler;
	}

	public void setTemsilcilikler(List<TemsilcilikDTO> temsilcilikler) {
		this.temsilcilikler = temsilcilikler;
	}

	public void changeSube(ValueChangeEvent event) {
		String sbKod = event.getNewValue().toString();
		if (sbKod != null && Integer.parseInt(sbKod) > 0) {
			belediye.setTemsilcilik(0);

			temsilcilikler = new ArrayList<TemsilcilikDTO>();
			for (TemsilcilikDTO t : sessionBean.getTemsilcilikler()) {
				if (t.getSubesi() == Integer.parseInt(sbKod)) {
					temsilcilikler.add(t);
				}
			}
		}
		degisti = true;
	}

	public int[] getSozlesmeBinaTipList() {
		return sozlesmeBinaTipList;
	}

	public void setSozlesmeBinaTipList(int[] sozlesmeBinaTipList) {
		this.sozlesmeBinaTipList = sozlesmeBinaTipList;
	}

	public void iletisimPanelListener(ActionEvent event) {
		iletisim = new BelediyeIletisimDTO();
		iletisimReq = true;
	}

	public boolean isIletisimReq() {
		return iletisimReq;
	}

	public void setIletisimReq(boolean iletisimReq) {
		this.iletisimReq = iletisimReq;
	}

	public void closePanel(CloseEvent event) {
		iletisimReq = false;

	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public void onRowUnselect(ActionEvent event) {
		slctSozlesme = new BelediyeSozlesmeDTO();
		sozlesme = new BelediyeSozlesmeDTO();
		sozlesmeBinaTipList = new int[0];
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
public void ileGoreBelediyeSorgulama(ActionEvent event){
		try {
			List<UavtBelediyeDTO> uavtBelediyeList = new ArrayList<UavtBelediyeDTO>();

			uavtBelediyeList = ApplicationDAOImpl.getINSTANCE()
					.getUavtBelediye(belediye.getIl(),belediye.getIlce());
			String belediyeAdi="";
			belediyeAdi=belediye.getAdi();
			int io=belediyeAdi.indexOf("(");
			if (io>-1){
				belediyeAdi=belediyeAdi.substring(0,belediyeAdi.indexOf("("));
			}
			belediyeAdi=belediyeAdi.replace("i", "İ").toUpperCase().trim();
			if (uavtBelediyeList!=null && uavtBelediyeList.size()>0){
				for (UavtBelediyeDTO u:uavtBelediyeList){
					u.setBelediyeAdi(u.getBelediyeAdi().replace("i", "İ").toUpperCase().replace("BELEDİYESİ", "").replace("BELEDIYESI","").trim());
					if (belediyeAdi.equals(u.getBelediyeAdi()) || u.getBelediyeAdi().equals(belediyeAdi.replaceAll("İ","I"))){
					this.belediye.setUavtKod(u.getKod());
					break;
					}
				}
			}
			if (this.belediye.getUavtKod()<=0){
			ServisSonucOfArrayOfBelediye8Zb117HL servisSonucOfBelediye=	new WebServiceBusiness().ileGoreBelediyeSorgulama(this.belediye.getIl());
			if (!servisSonucOfBelediye.isHata()){
			ArrayOfBelediye aob=	servisSonucOfBelediye.getSonuc().getValue();
			if (aob!=null && aob.getBelediye()!=null){
				for (int i=0;i<aob.getBelediye().size();i++){
					Belediye belediye=new Belediye();
					belediye=aob.getBelediye().get(i);
					ObjectFactory of=new ObjectFactory();
					belediye.setBelediyeAdi(of.createBelediyeBelediyeAdi(belediye.getBelediyeAdi().getValue().replace("i", "İ").toUpperCase().replace("BELEDİYESİ", "").replace("BELEDIYESI","").trim()));
					if (belediyeAdi.equals(belediye.getBelediyeAdi()) || belediye.getBelediyeAdi().equals(belediyeAdi.replaceAll("İ","I"))){
						this.belediye.setUavtKod(belediye.getKod());
						break;
						}
				}
			}
			
			
			
			
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(servisSonucOfBelediye.getMesaj().getValue()));
			}
			}
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
		}
	}



public int ileGoreBelediyeSorgulama(BelediyeDTO belediye) throws Exception{
	int belediyeKod=0;
	try {
		List<UavtBelediyeDTO> uavtBelediyeList = new ArrayList<UavtBelediyeDTO>();

		uavtBelediyeList = ApplicationDAOImpl.getINSTANCE()
				.getUavtBelediye(belediye.getIl(),belediye.getIlce());
		String belediyeAdi="";
		belediyeAdi=belediye.getAdi();
		int io=belediyeAdi.indexOf("(");
		if (io>-1){
			belediyeAdi=belediyeAdi.substring(0,belediyeAdi.indexOf("("));
		}
		belediyeAdi=belediyeAdi.replace("i", "İ").toUpperCase().trim();
		if (uavtBelediyeList!=null && uavtBelediyeList.size()>0){
			for (UavtBelediyeDTO u:uavtBelediyeList){
				u.setBelediyeAdi(u.getBelediyeAdi().replace("i", "İ").toUpperCase().replace("BELEDİYESİ", "").replace("BELEDIYESI","").trim());
				if (belediyeAdi.equals(u.getBelediyeAdi()) || u.getBelediyeAdi().equals(belediyeAdi.replaceAll("İ","I"))){
				belediyeKod=u.getIlKod();
				break;
				}
			}
		}
		if (belediyeKod<=0){
			ServisSonucOfArrayOfBelediye8Zb117HL servisSonucOfBelediye=	new WebServiceBusiness().ileGoreBelediyeSorgulama(this.belediye.getIl());
			if (!servisSonucOfBelediye.isHata()){
			ArrayOfBelediye aob=	servisSonucOfBelediye.getSonuc().getValue();
			if (aob!=null && aob.getBelediye()!=null){
				for (int i=0;i<aob.getBelediye().size();i++){
					Belediye belediye1=new Belediye();
					belediye1=aob.getBelediye().get(i);
					ObjectFactory of=new ObjectFactory();
					belediye1.setBelediyeAdi(of.createBelediyeBelediyeAdi(belediye1.getBelediyeAdi().getValue().replace("i", "İ").toUpperCase().replace("BELEDİYESİ", "").replace("BELEDIYESI","").trim()));
					if (belediyeAdi.equals(belediye1.getBelediyeAdi()) || belediye1.getBelediyeAdi().equals(belediyeAdi.replaceAll("İ","I"))){
						belediyeKod=belediye1.getKod();
						break;
						}
				}
			}
			
			
			
			
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(servisSonucOfBelediye.getMesaj().getValue()));
			}
			
		
		
		}
	} catch (CRUDException e) {
		throw new Exception(e.getMessage());
		
	} 
	return belediyeKod;
}


}
