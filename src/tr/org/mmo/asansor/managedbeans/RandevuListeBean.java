package tr.org.mmo.asansor.managedbeans;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.beans_.Randevu;
import tr.org.mmo.asansor.beans_.RandevuList;
import tr.org.mmo.asansor.beans_.RandevuListe;
import tr.org.mmo.asansor.business.BasvuruBusiness;
import tr.org.mmo.asansor.business.MuhendisBusiness;
import tr.org.mmo.asansor.business.RandevuBusiness;
import tr.org.mmo.asansor.common.ColumnModel;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.dto.RandevuListeDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.models.EtiketBasmaDataModel;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.RandevuSaatComparator;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@ManagedBean(name = "randevuListBean")
@ViewScoped
public class RandevuListeBean implements Serializable {

	/**
	 * 
	 */
	DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
	DateFormat tf = new SimpleDateFormat("HH:mm a");
	private final static List<String> VALID_COLUMN_KEYSS = Arrays.asList(
			"randevuTarihi", "randevuSaati", "basvuruId", "basvuruYapan",
			"telefonNo", "binaAdi", "unvan", "muhendisler", "cihazlar",
			"kontrolTutari");

	private final static List<String> VALID_COLUMN_HEADER = Arrays.asList(
			"RANDEVU TARİHİ", "RANDEVU SAATİ", "TEBLİGAT NO", "BAŞVURU YAPAN",
			"BAŞVURU YAPAN TELEFON", "BİNA ADI", "BAKIMCI FİRMA",
			"GÖREVLİ MÜHENDİSLER", "KONTROL EDİLECEK CİHAZLAR",
			"KONTROL TUTARI");

	private String columnTemplates = "randevuTarihi&randevuSaati&basvuruId&basvuruYapan&telefonNo&binaAdi&unvan&muhendisler&cihazlar&kontrolTutari";

	private static final long serialVersionUID = 7787317890330258221L;
	private final static List<String> VALID_COLUMN_KEYS = Arrays.asList(
			"basvuruYapan", "randevuTarihi", "firmaYetkilisi", "muhendis");
	private Map<String, String> HEADER_KEY;

	private String columnTemplate = "basvuruYapan randevuTarihi firmaYetkilisi muhendis";
	private List<ColumnModel> columns = new ArrayList<ColumnModel>();
	private List<ColumnModel> columnss = new ArrayList<ColumnModel>();
	private List<RandevuListeDTO> filteredBasvuru;
	private RandevuListeDTO randevuListeDTO = new RandevuListeDTO();
	private RandevuListe randevu;
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private boolean render = true;
	private List<RandevuList> randevuListesi;
	private Date etiketRandevuTarihi;
	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;
	@ManagedProperty(value = "#{loginBean.muhendisList}")
	private List<Kullanici> muhendisList;
	private List<RandevuListeDTO> etiketRandevuListe = new ArrayList<RandevuListeDTO>();
	private EtiketBasmaDataModel etiketBasmaDataModel = new EtiketBasmaDataModel();
	private List<RandevuListeDTO> selected;
	
	@ManagedProperty(value="#{binaBean}")
	private BinaBean binaBean;
	
	
	public List<RandevuListeDTO> getSelected() {
		return selected;
	}

	public void setSelected(List<RandevuListeDTO> selected) {
		this.selected = selected;
	}

	public EtiketBasmaDataModel getEtiketBasmaDataModel() {
		return etiketBasmaDataModel;
	}

	public void setEtiketBasmaDataModel(EtiketBasmaDataModel etiketBasmaDataModel) {
		this.etiketBasmaDataModel = etiketBasmaDataModel;
	}

	public List<RandevuListeDTO> getEtiketRandevuListe() {
		return etiketRandevuListe;
	}

	public void setEtiketRandevuListe(List<RandevuListeDTO> etiketRandevuListe) {
		this.etiketRandevuListe = etiketRandevuListe;
	}

	public Date getEtiketRandevuTarihi() {
		return etiketRandevuTarihi;
	}

	public void setEtiketRandevuTarihi(Date etiketRandevuTarihi) {
		this.etiketRandevuTarihi = etiketRandevuTarihi;
	}

	public boolean isRender() {
		return render;
	}

	public void setRender(boolean render) {
		this.render = render;
	}

	private int muhendisSicilNo = 0;

	private Date editDate = new Date();

	public int getMuhendisSicilNo() {
		return muhendisSicilNo;
	}

	public void setMuhendisSicilNo(int muhendisSicilNo) {
		this.muhendisSicilNo = muhendisSicilNo;
	}

	public String kisiListener(RandevuListeDTO randevu) {
		StringBuffer sb = new StringBuffer();

		sb.append("Bina Adı :");
		sb.append(randevu.getBinaAdi());
		sb.append("\n");
		sb.append(" ");
		sb.append("Telefon No :");
		sb.append(randevu.getTelefonNo()).append(randevu.getTelefonNoDahili()>0?" dahili :"+randevu.getTelefonNoDahili():"");

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(sb.toString()));
		return "";
	}

	@PostConstruct
	public void init() {

		HEADER_KEY = new HashMap<String, String>();
		HEADER_KEY.put("basvuruYapan", "Başvuru Yapan");
		HEADER_KEY.put("randevuTarihi", "Tarih-Saat");
		// HEADER_KEY.put("randevuSaati", "Saat");
		HEADER_KEY.put("firmaYetkilisi", "Firma Yetkilisi");
		HEADER_KEY.put("muhendis", "Görevli Mühendisler");
		randevu = new RandevuListe();
		render = false;
		String viewId = FacesContext.getCurrentInstance().getViewRoot()
				.getViewId();
		render = true;

		if (viewId.equals("/randevuListesi.xhtml")) {
			getRandevuListe();
		} else if (viewId.equals("/muhendisIcinRandevuListesi.xhtml")) {
			String muhendisSicilNo = loginBean.getKullanici().getSicilNo();
			if (muhendisSicilNo != null
					&& Integer.parseInt(muhendisSicilNo) > 0) {
				getRandevuListeForMuhendis(Integer.parseInt(muhendisSicilNo));
			}
		} else if (viewId.equals("/randevuListe.xhtml")) {
			render = false;

		}

		createDynamicColumns();
		createDynamicColumnss();

	}

	public void createDynamicColumns() {
		String[] columnKeys = columnTemplate.split(" ");
		columns.clear();
		int index = 1;
		for (String columnKey : columnKeys) {
			String key = columnKey.trim();

			if (VALID_COLUMN_KEYS.contains(key)) {
				columns.add(new ColumnModel(HEADER_KEY.get(columnKey),
						columnKey, index));
				index++;
			}
		}
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}

	public List<RandevuListeDTO> getFilteredBasvuru() {
		return filteredBasvuru;
	}

	public void setFilteredBasvuru(List<RandevuListeDTO> filteredBasvuru) {
		this.filteredBasvuru = filteredBasvuru;
	}

	public RandevuListe getRandevu() {
		return randevu;
	}

	public void setRandevu(RandevuListe randevu) {
		this.randevu = randevu;
	}

	private void ajandayaEkle(ArrayList<RandevuListeDTO> l) {
		eventModel = new DefaultScheduleModel();
		Collections.sort((l == null ? new ArrayList<RandevuListeDTO>() : l),
				new RandevuSaatComparator());
		for (RandevuListeDTO r : l) {
			String data;
			if (r.getMuhendis() != null
					&& r.getMuhendis().getChildren() != null
					&& r.getMuhendis().getChildren().size() > 1) {
				data = (String) r.getMuhendis().getChildren().get(1).getData();
			} else {
				data = "";
			}
			eventModel.addEvent(new DefaultScheduleEvent("   Bina Adı : "
					+ r.getBinaAdi() + " - " + "Görevli Mühendis : " + data,
					startEventDay(r.getRandevuTarihi(), r.getRandevuSaati()),
					endEventDay(r.getRandevuTarihi(), r.getRandevuSaati()), r));
		}
	}

	private Date startEventDay(Date d, Date s) {
		Calendar t = Calendar.getInstance();
		t.setTime(d);
		Calendar c = Calendar.getInstance();
		Time h = (Time) s;
		c.setTime(h);

		if (c.get(Calendar.HOUR_OF_DAY) > 12
				|| (c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MINUTE) / 60) > 12) {
			t.set(Calendar.AM_PM, Calendar.PM);
		} else {
			t.set(Calendar.AM_PM, Calendar.AM);
		}
		t.set(Calendar.HOUR, c.get(Calendar.HOUR));
		t.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
		return t.getTime();
	}

	private Date endEventDay(Date d, Date s) {
		Calendar t = Calendar.getInstance();
		t.setTime(d);

		Calendar c = Calendar.getInstance();
		Time h = (Time) s;

		c.setTime(h);

		if (c.get(Calendar.HOUR_OF_DAY) + 1 > 12
				|| (c.get(Calendar.HOUR_OF_DAY) + 1 + c.get(Calendar.MINUTE) / 60) > 12) {
			t.set(Calendar.AM_PM, Calendar.PM);
		} else {
			t.set(Calendar.AM_PM, Calendar.AM);
		}
		t.set(Calendar.HOUR, c.get(Calendar.HOUR));
		t.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
		return t.getTime();
	}

	private void getRandevuListe() {

		try {

			randevu.setRandevuList(new RandevuBusiness().randevuListesiGetir());
			randevuListesi = new ArrayList<RandevuList>();
			for (RandevuListeDTO r : randevu.getRandevuList()) {
				RandevuList rl = new RandevuList();
				rl.setBasvuruId(r.getBasvuruId());
				rl.setBasvuruYapan(r.getBasvuruYapan());
				rl.setBinaAdi(r.getBinaAdi());

				rl.setKontrolTutari(r.getKontrolTutari());
				rl.setRandevuId(r.getRandevuId());
				rl.setRandevuSaati(tf.format(r.getRandevuSaati()));
				rl.setRandevuTarihi(df.format(r.getRandevuTarihi()));
				rl.setUnvan(r.getUnvan());
				rl.setTelefonNo(r.getTelefonNo());

				StringBuilder sb = new StringBuilder();

				for (Kullanici k : r.getMuhendisler()) {
					sb.append("*").append(k.getAdi()).append("  ");
				}
				rl.setMuhendisler(sb.toString());
				List<BasvuruAsansorDTO> l = new ArrayList<BasvuruAsansorDTO>();
				l = new BasvuruBusiness().getBasvuruAsansor(r.getBasvuruId());
				StringBuilder c = new StringBuilder();
				for (BasvuruAsansorDTO b : l) {
					c.append("*").append(b.getKimlikNo()).append(" --> ")
							.append(b.getAsansorunYeri()).append("  ");
				}
				rl.setCihazlar(c.toString());
				randevuListesi.add(rl);

			}
		} catch (CRUDException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
			e.printStackTrace();
		} finally {
			ajandayaEkle(randevu.getRandevuList());
		}
	}

	private void getRandevuListeForMuhendis(int sicilNo) {
		try {

			if (sicilNo > 0)
				randevu.setRandevuList(new RandevuBusiness()
						.randevuListesiGetirForMuhendis(sicilNo));
			else
				randevu.setRandevuList(new ArrayList<RandevuListeDTO>());

			randevuListesi = new ArrayList<RandevuList>();
			for (RandevuListeDTO r : randevu.getRandevuList()) {
				RandevuList rl = new RandevuList();
				rl.setBasvuruId(r.getBasvuruId());
				rl.setBasvuruYapan(r.getBasvuruYapan());
				rl.setBinaAdi(r.getBinaAdi());

				rl.setKontrolTutari(r.getKontrolTutari());
				rl.setRandevuId(r.getRandevuId());
				rl.setRandevuSaati(tf.format(r.getRandevuSaati()));
				rl.setRandevuTarihi(df.format(r.getRandevuTarihi()));
				rl.setUnvan(r.getUnvan());
				rl.setTelefonNo(r.getTelefonNo());

				StringBuilder sb = new StringBuilder();

				for (Kullanici k : r.getMuhendisler()) {
					sb.append("*").append(k.getAdi()).append("  ");
				}
				rl.setMuhendisler(sb.toString());
				List<BasvuruAsansorDTO> l = new ArrayList<BasvuruAsansorDTO>();
				l = new BasvuruBusiness().getBasvuruAsansor(r.getBasvuruId());
				StringBuilder c = new StringBuilder();
				for (BasvuruAsansorDTO b : l) {
					c.append("*").append(b.getKimlikNo()).append(" --> ")
							.append(b.getAsansorunYeri()).append("  ");
				}
				rl.setCihazlar(c.toString());
				randevuListesi.add(rl);

			}
		} catch (CRUDException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
			e.printStackTrace();
		} finally {
			ajandayaEkle(randevu.getRandevuList());
		}
	}

	public void handleOver(ActionEvent event) {

	}

	public String edit(RowEditEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		int randevuId = ((RandevuListeDTO) event.getObject()).getRandevuId();
		int basvuruId = ((RandevuListeDTO) event.getObject()).getBasvuruId();
		for (RandevuListeDTO r : randevu.getRandevuList()) {
			if (r.getRandevuId() == randevuId) {

				r.setRandevuTarihi(new java.sql.Date(editDate.getTime()));
				r.setRandevuSaati(new java.sql.Time(editDate.getTime()));

				try {
					int kontrolAdet = new MuhendisBusiness()
							.getGunlukKontrolAdet((Integer) r.getMuhendis()
									.getChildren().get(0).getData());
					if (kontrolAdet > 0) {
						boolean baskaRandevuVarMi = new RandevuBusiness()
								.muhendisMuayeneAdetKontrol((Integer) r
										.getMuhendis().getChildren().get(0)
										.getData(), editDate, kontrolAdet,basvuruId);

						if (baskaRandevuVarMi) {
							msg = new FacesMessage(
									Messages._MUHENDISKONTROLUYARI_.getMesaj());
							msg.setSeverity(FacesMessage.SEVERITY_INFO);
							FacesContext.getCurrentInstance().addMessage(null,
									msg);
						}
					}
					try {
						new RandevuBusiness().randevuGuncelle(editDate,
								randevuId);
						msg = new FacesMessage(
								Messages._RANDEVUZAMANIDEGISTIRILDI_.getMesaj());
						context.addMessage(null, msg);
					} catch (CRUDException e) {
						msg = new FacesMessage(
								Messages._RANDEVUZAMANIDEGISTIRILEMEDI_
										.getMesaj());
						msg.setSeverity(FacesMessage.SEVERITY_ERROR);
						context.addMessage(null, msg);
					}

				} catch (CRUDException e) {

					msg = new FacesMessage(e.getMessage());
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}

				break;

			}
		}
		return "";
	}

	public void randevuGuncelle(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		if (this.randevuListeDTO != null
				&& this.randevuListeDTO.getRandevuId() > 0) {
			int randevuId = this.randevuListeDTO.getRandevuId();

			try {
				int kontrolAdet = new MuhendisBusiness()
						.getGunlukKontrolAdet((Integer) this.randevuListeDTO
								.getMuhendis().getChildren().get(0).getData());
				if (kontrolAdet > 0) {
					boolean baskaRandevuVarMi = new RandevuBusiness()
							.muhendisMuayeneAdetKontrol(
									(Integer) this.randevuListeDTO
											.getMuhendis().getChildren().get(0)
											.getData(),
									randevuListeDTO.getRandevuTarihi(),
									kontrolAdet,this.randevuListeDTO.getBasvuruId());

					if (baskaRandevuVarMi) {
						msg = new FacesMessage(
								Messages._MUHENDISKONTROLUYARI_.getMesaj());
						msg.setSeverity(FacesMessage.SEVERITY_INFO);
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}
				}
				try {
					new RandevuBusiness().randevuGuncelle(
							randevuListeDTO.getRandevuTarihi(), randevuId);
					try {
						for (RandevuListeDTO r : randevu.getRandevuList()) {
							if (r.getRandevuId() == randevuListeDTO
									.getRandevuId()) {
								randevu.getRandevuList().remove(r);
								break;
							}
						}
					} catch (NullPointerException e) {
						randevu.setRandevuList(new ArrayList<RandevuListeDTO>());
					}
					randevu.getRandevuList().add(randevuListeDTO);

					randevuListeDTO = new RandevuListeDTO();
					msg = new FacesMessage(
							Messages._RANDEVUZAMANIDEGISTIRILDI_.getMesaj());
					context.addMessage(null, msg);

				} catch (CRUDException e) {
					msg = new FacesMessage(
							Messages._RANDEVUZAMANIDEGISTIRILEMEDI_.getMesaj());
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					context.addMessage(null, msg);
				}

			} catch (CRUDException e) {

				msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} finally {
				ajandayaEkle(randevu.getRandevuList());
			}

		}

	}

	public String deleteRow(RandevuListeDTO randevu) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;

		if (randevu != null && randevu.getBasvuruId() != 0
				&& randevu.getRandevuId() != 0) {
			try {
				Randevu r = new Randevu();

				r.getBasvuru().setBinaId(randevu.getBinaId());
				r.setRandevuTarih(randevu.getRandevuTarihi());
				new RandevuBusiness().randevuSil(r);
				this.randevu.getRandevuList().remove(randevu);
				StringBuilder sb = new StringBuilder();
				sb.append(randevu.getRandevuId());
				sb.append(Messages._RANDEVUSILINDI_.getMesaj());
				msg = new FacesMessage(sb.toString());

				context.addMessage(null, msg);
			} catch (CRUDException e) {
				msg = new FacesMessage(Messages._RANDEVUSILINEMEDI_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				context.addMessage(null, msg);
			}
		}
		return "";

	}

	public void randevuSil(ActionEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;

		if (randevuListeDTO != null && randevuListeDTO.getBasvuruId() != 0
				&& randevuListeDTO.getRandevuId() != 0) {
			try {

				Randevu r1 = new Randevu();
				r1.setRandevuId(randevuListeDTO.getRandevuId());
				r1.getBasvuru().setBasvuruId(randevuListeDTO.getBasvuruId());

				r1.getBasvuru().setBinaId(randevuListeDTO.getBinaId());
				r1.setRandevuTarih(randevuListeDTO.getRandevuTarihi());
				new RandevuBusiness().randevuSil(r1);

				try {
					for (RandevuListeDTO r : randevu.getRandevuList()) {
						if (r.getRandevuId() == randevuListeDTO.getRandevuId()) {
							randevu.getRandevuList().remove(r);
							break;
						}
					}
				} catch (NullPointerException e) {
					randevu.setRandevuList(new ArrayList<RandevuListeDTO>());
				}

				StringBuilder sb = new StringBuilder();
				sb.append(randevuListeDTO.getRandevuId());
				sb.append(Messages._RANDEVUSILINDI_.getMesaj());
				msg = new FacesMessage(sb.toString());
				this.randevuListeDTO = new RandevuListeDTO();

				context.addMessage(null, msg);
			} catch (Exception e) {
				msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				context.addMessage(null, msg);
			} finally {
				ajandayaEkle(randevu.getRandevuList());
			}
		}

	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public String toKontrol() {
		boolean kontrolSayfasınaGit = false;
		try {
			int sicilNo = Integer.parseInt(loginBean.getKullanici()
					.getSicilNo().trim());
			for (Kullanici k : this.randevuListeDTO.getMuhendisler()) {
				int s1 = Integer.parseInt(k.getSicilNo().trim());
				if (s1 == sicilNo) {
					kontrolSayfasınaGit = true;
					break;
				}
			}
		} catch (Exception e) {
			kontrolSayfasınaGit = false;
		}
		if (kontrolSayfasınaGit) {
			this.binaBean.setRandevuId(this.randevuListeDTO.getRandevuId());
			this.binaBean.setRandevuStr(String.valueOf(binaBean.getRandevuId()));
			((HttpServletRequest) FacesContext.getCurrentInstance()
					.getExternalContext().getRequest()).getSession()
					.setAttribute("parameterFromRandevuListe", this.randevuListeDTO.getRandevuId());
			((HttpServletRequest) FacesContext.getCurrentInstance()
					.getExternalContext().getRequest()).getSession()
					.setAttribute("parameterRandevu", this.randevuListeDTO);
			this.binaBean.asansorTest();
			this.binaBean.setBinalar(this.binaBean.getBinaDTO());
			try {
				this.binaBean.bListener();
			} catch (CloneNotSupportedException e) {
			
			}
			return "kontrol.jsf?faces-redirect=true";
		} else {
			RequestContext
					.getCurrentInstance()
					.execute(
							"alert('Size ait olmayan bir kontrolü kontrol sayfasında görüntüleyemezsiniz!"
									+ "Bina bölümünden test sonuçlarını görüntüleyebilirsiniz !')");
		}
		return "";

	}

	public List<Kullanici> getMuhendisList() {
		return muhendisList;
	}

	public void setMuhendisList(List<Kullanici> muhendisList) {
		this.muhendisList = muhendisList;
	}

	public void muhendisChangeListener(AjaxBehaviorEvent event) {
	try {

				if (muhendisSicilNo > 0)
					randevu.setRandevuList(new RandevuBusiness()
							.randevuListesiGetirForMuhendis(muhendisSicilNo));
				else
					randevu.setRandevuList(new ArrayList<RandevuListeDTO>());

				randevuListesi = new ArrayList<RandevuList>();
				for (RandevuListeDTO r : randevu.getRandevuList()) {
					RandevuList rl = new RandevuList();
					rl.setBasvuruId(r.getBasvuruId());
					rl.setBasvuruYapan(r.getBasvuruYapan());
					rl.setBinaAdi(r.getBinaAdi());

					rl.setKontrolTutari(r.getKontrolTutari());
					rl.setRandevuId(r.getRandevuId());
					rl.setRandevuSaati(tf.format(r.getRandevuSaati()));
					rl.setRandevuTarihi(df.format(r.getRandevuTarihi()));
					rl.setUnvan(r.getUnvan());
					rl.setTelefonNo(r.getTelefonNo());

					StringBuilder sb = new StringBuilder();

					for (Kullanici k : r.getMuhendisler()) {
						sb.append("*").append(k.getAdi()).append("  ");
					}
					rl.setMuhendisler(sb.toString());
					List<BasvuruAsansorDTO> l = new ArrayList<BasvuruAsansorDTO>();
					l = new BasvuruBusiness().getBasvuruAsansor(r.getBasvuruId());
					StringBuilder c = new StringBuilder();
					for (BasvuruAsansorDTO b : l) {
						c.append("*").append(b.getKimlikNo()).append(" --> ")
								.append(b.getAsansorunYeri()).append("  ");
					}
					rl.setCihazlar(c.toString());
					randevuListesi.add(rl);
				}
				}
			catch (CRUDException e) {
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				context.addMessage(null, msg);
				e.printStackTrace();
			}
			
		catch (NullPointerException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(Messages._SQL_507_.getMesaj()));
		}
	render = true;
	}

	public Date getRandomDate(Date base) {
		Calendar date = Calendar.getInstance();
		date.setTime(base);
		date.add(Calendar.DATE, ((int) (Math.random() * 30)) + 1);

		return date.getTime();
	}

	public Date getInitialDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY,
				calendar.get(Calendar.DATE), 0, 0, 0);

		return calendar.getTime();
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	@SuppressWarnings("unused")
	private Calendar today() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), 0, 0, 0);

		return calendar;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public void addEvent(ActionEvent actionEvent) {
		if (event.getId() == null)
			eventModel.addEvent(event);
		else
			eventModel.updateEvent(event);

		event = new DefaultScheduleEvent();

	}

	public void onEventSelect(SelectEvent selectEvent) {
		this.randevuListeDTO = new RandevuListeDTO();
		event = (ScheduleEvent) selectEvent.getObject();
		this.randevuListeDTO = (RandevuListeDTO) event.getData();
		this.randevuListeDTO.setAsansorAdet(this.randevuListeDTO.getFirmalar().size());
		Map<Integer,FirmaDTO> firmaMap=new HashMap<Integer,FirmaDTO>();
			for (FirmaDTO f:this.randevuListeDTO.getFirmalar()){
				firmaMap.put(f.getKod(), f);
				
			}
		Set<Integer> set=firmaMap.keySet();
		Iterator<Integer> it=set.iterator();
		this.randevuListeDTO.setFirmalar(new ArrayList<FirmaDTO>());
		while (it.hasNext()){
			int i=it.next();
			this.randevuListeDTO.getFirmalar().add(firmaMap.get(i));
			
		}
	}

	public void onDateSelect(SelectEvent selectEvent) {
		FacesMessage message = new FacesMessage(
				FacesMessage.SEVERITY_INFO,
				"İşlem yapmak istediğiniz tarihe ait bir randevu bulunmamaktadır !",
				"Lütfen bina adı üzerine tıklayınız");
		addMessage(message);
		/*
		 * event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(),
		 * (Date) selectEvent.getObject());
		 */
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {

		if (event.getScheduleEvent().getEndDate().compareTo(new Date()) < 0) {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "",
							Messages._RANDEVUTASIMAHATASI_.getMesaj()));

		} else {

			for (RandevuListeDTO ok : this.randevu.getRandevuList()) {

				try {
					if (ok.getRandevuId() == ((RandevuListeDTO) event
							.getScheduleEvent().getData()).getRandevuId()) {

						new RandevuBusiness().randevuGuncelle(event
								.getScheduleEvent().getStartDate(), ok
								.getRandevuId());

						FacesMessage message = new FacesMessage(
								FacesMessage.SEVERITY_INFO, "Randevu  taşındı",
								"Gün Farkı:" + event.getDayDelta()
										+ ", Saat Farkı:"
										+ event.getMinuteDelta());
						addMessage(message);
						break;
					}
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(e.getMessage()));
					e.printStackTrace();
				}
			}
		}

	}

	@SuppressWarnings("unused")
	private Date getStartDate(Date d, int delta) {
		Calendar t = Calendar.getInstance();
		t.get(Calendar.DATE);
		t.setTime(d);
		// t.set(Calendar.AM_PM, Calendar.PM);
		t.set(Calendar.DATE, t.get(Calendar.DATE) + (-1) * delta);

		return t.getTime();
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Event resized", "Day delta:" + event.getDayDelta()
						+ ", Minute delta:" + event.getMinuteDelta());

		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public RandevuListeDTO getRandevuListeDTO() {
		return randevuListeDTO;
	}

	public void setRandevuListeDTO(RandevuListeDTO randevuListeDTO) {
		this.randevuListeDTO = randevuListeDTO;
	}

	public void preProcessPDF(Object document) throws Exception {
		try {

			Document pdf = (Document) document;

			pdf.open();

			// pdf.setHtmlStyleClass("color:red;font-size:0.6em;");
			// pdf.setPageSize(PageSize.LETTER);

			// pdf.setPageSize(PageSize.A4.rotate());
			pdf.setPageSize(PageSize.LETTER);
			pdf.setMargins(0, 0, 5, 5);

			BaseFont helvetica = null;

			try {
				helvetica = BaseFont.createFont(BaseFont.HELVETICA,
						BaseFont.CP1252, BaseFont.EMBEDDED);
			} catch (Exception e) {
				// font exception
			}
			Font font = new Font(helvetica, 5, Font.NORMAL);

			ServletContext servletContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();
			String logo = servletContext
					.getRealPath("\\resources\\image\\tmmob.png");

			pdf.add(Image.getInstance(logo));
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			pdf.add(new Paragraph(" ", FontFactory.getFont(
					FontFactory.HELVETICA, 5, Font.BOLD, new Color(0, 0, 0))));
			pdf.add(new Paragraph(
					"Randevu Listesi" + format.format(new Date()), FontFactory
							.getFont(FontFactory.HELVETICA, 5, Font.BOLD,
									new Color(0, 0, 0))));

			// pdf.newPage();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}
	}

	public void createPDF(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		try {

			Document document = new Document();
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(0, 0, 2, 2);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			PdfWriter.getInstance(document, baos);

			if (!document.isOpen()) {
				document.open();
			}

			document.add(new Paragraph("Randevu Listesi"
					+ df.format(new Date()), FontFactory.getFont(
					FontFactory.HELVETICA, 6, Font.BOLD, new Color(0, 0, 0))));
			PdfPTable pdfTable = exportPDFTable();
			document.add(pdfTable);

			document.close();
			String fileName = new StringBuilder().append("RandevuListesi(")
					.append(df.format(new Date())).append(")").toString();

			writePDFToResponse(context.getExternalContext(), baos, fileName);

			context.responseComplete();

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
		}
	}

	private PdfPTable exportPDFTable() {
		int numberOfColumns = 10;
		BaseFont helvetica = null;

		try {
			helvetica = BaseFont.createFont(BaseFont.HELVETICA, "ISO-8859-9",
					BaseFont.EMBEDDED);

		} catch (Exception e) {
			// font exception
		}

		PdfPTable pdfTable = new PdfPTable(numberOfColumns);

		pdfTable.setWidthPercentage(100);

		Font font = new Font(helvetica, 3, Font.NORMAL);
		Font fontHeader = new Font(helvetica, 5, Font.BOLD);

		pdfTable.addCell(new Paragraph("RANDEVU TARİHİ", fontHeader));
		pdfTable.addCell(new Paragraph("RANDEVU SAATİ", fontHeader));

		pdfTable.addCell(new Paragraph("TEBLİGAT NO", fontHeader));
		pdfTable.addCell(new Paragraph("BAŞVURU YAPAN", fontHeader));
		pdfTable.addCell(new Paragraph("BAŞVURU YAPAN TELEFON", fontHeader));
		pdfTable.addCell(new Paragraph("BİNA ADI", fontHeader));
		pdfTable.addCell(new Paragraph("BAKIMCI FİRMA", fontHeader));
		pdfTable.addCell(new Paragraph("KONTROL MÜHENDİSLERİ", fontHeader));
		pdfTable.addCell(new Paragraph("KONTROL EDİLECEK CİHAZLAR", fontHeader));
		pdfTable.addCell(new Paragraph("KONTROL TUTARI", fontHeader));

		for (RandevuList r : randevuListesi) {
			pdfTable.addCell(new Paragraph(r.getRandevuTarihi(), font));
			pdfTable.addCell(new Paragraph(r.getRandevuSaati(), font));

			pdfTable.addCell(new Paragraph(String.valueOf(r.getBasvuruId()),
					font));
			pdfTable.addCell(new Paragraph(r.getBasvuruYapan(), font));
			pdfTable.addCell(new Paragraph(String.valueOf(r.getTelefonNo()),
					font));
			pdfTable.addCell(new Paragraph(r.getBinaAdi(), font));
			pdfTable.addCell(new Paragraph(r.getUnvan(), font));
			pdfTable.addCell(new Paragraph(r.getMuhendisler(), font));
			pdfTable.addCell(new Paragraph(r.getCihazlar(), font));
			pdfTable.addCell(new Paragraph(
					String.valueOf(r.getKontrolTutari()), font));
		}
		return pdfTable;
	}

	private void writePDFToResponse(ExternalContext externalContext,
			ByteArrayOutputStream baos, String fileName) throws Exception {

		externalContext.responseReset();
		externalContext.setResponseContentType("application/pdf");

		externalContext.setResponseHeader("Expires", "0");
		externalContext.setResponseHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		externalContext.setResponseHeader("Pragma", "public");
		externalContext.setResponseHeader("Content-disposition",
				"attachment;filename=" + fileName + ".pdf");
		externalContext.setResponseContentLength(baos.size());

		OutputStream out = externalContext.getResponseOutputStream();
		baos.writeTo(out);
		externalContext.responseFlushBuffer();

	}

	/*********************
	 * 
	 * 
	 */

	public String getColumnTemplates() {
		return columnTemplates;
	}

	public void setColumnTemplates(String columnTemplates) {
		this.columnTemplates = columnTemplates;
	}

	public List<ColumnModel> getColumnss() {
		return columnss;
	}

	public void setColumnss(List<ColumnModel> columnss) {
		this.columnss = columnss;
	}

	private void createDynamicColumnss() {
		String[] columnKeys = columnTemplates.split("&");
		columnss = new ArrayList<ColumnModel>();
		columnss.clear();
		int index = 1;
		for (String columnKey : columnKeys) {
			String key = columnKey.trim();

			if (VALID_COLUMN_KEYSS.contains(key)) {
				columnss.add(new ColumnModel(
						VALID_COLUMN_HEADER.get(index - 1), columnKey, index));

				index++;
			}
		}
	}

	public void updateColumns() {

		UIComponent table = FacesContext.getCurrentInstance().getViewRoot()
				.findComponent(":form1:randevuListeTable");
		table.setValueExpression("sortBy", null);

		createDynamicColumns();

	}

	public List<RandevuList> getRandevuListesi() {
		return randevuListesi;
	}

	public void setRandevuListesi(List<RandevuList> randevuListesi) {
		this.randevuListesi = randevuListesi;
	}

	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);

		HSSFRow header = sheet.getRow(0);

		HSSFFont arialFont = wb.createFont();
		arialFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		arialFont.setFontName("Arial");

		HSSFFont arialBoldFont = wb.createFont();
		arialBoldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		arialBoldFont.setFontName("Arial");

		HSSFCellStyle cellStyleHeader = wb.createCellStyle();
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(arialFont);
		// cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyleHeader.setFont(arialBoldFont);
		// cellStyleHeader.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		// cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			HSSFCell cell = header.getCell(i);
			if (i < 9) {
				cell.setCellStyle(cellStyleHeader);
			} else {
				cell.setCellStyle(cellStyle);
			}

		}
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public BinaBean getBinaBean() {
		return binaBean;
	}

	public void setBinaBean(BinaBean binaBean) {
		this.binaBean = binaBean;
	}
	
	@SuppressWarnings("unused")
	public void etiketBasma() {
		
	    
		try {

				if(etiketRandevuTarihi != null && muhendisSicilNo > 0){
					etiketRandevuListe= new ArrayList<RandevuListeDTO>(); 
					
					etiketBasmaDataModel = new EtiketBasmaDataModel(etiketRandevuListe);
					List<RandevuListeDTO> list= new ArrayList<RandevuListeDTO>();
					list = new RandevuBusiness().getEtiketListe(etiketRandevuTarihi, muhendisSicilNo);
					if(list.size()>0){
						etiketBasmaDataModel=new EtiketBasmaDataModel(list);
					}
					else
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._SQL_506_.getMesaj()));
				}else{
					FacesMessage message= new FacesMessage("Bilgiler eksik girildi!");
				} 
		}catch (ReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public void openSelected(ActionEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();
		List<RandevuListeDTO> rld =new ArrayList<RandevuListeDTO>();
		
		if (selected != null && selected.size() > 0) {
			for(RandevuListeDTO r : selected){
				if(r.getAsansorUavtEtiket().isEmpty()){
					//context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Seçilen asansörlerden UAVT etiketi boş olan(lar) var!", ""));
				}else{
					rld.add(r);
				}
			}
		if(rld != null && rld.size() == selected.size()){
			System.out.println(rld);
			etiketBasma(rld);
		}else{
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Seçilen asansörlerden UAVT etiketi boş olan(lar) var!", ""));
		}



		} else {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Etiket basılacak asansör(ler) seçiniz!", ""));
		}
	}

	private void etiketBasma(List<RandevuListeDTO> rld) {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		request.getSession().setAttribute("etiketList", rld);
		FacesContext context=FacesContext.getCurrentInstance();
		try {
		context.getExternalContext().redirect(
				context.getExternalContext().getRequestContextPath()
						+ "/EtiketServlet");
	} catch (IOException e) {
		context.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		e.printStackTrace();
	}
	}	

}
