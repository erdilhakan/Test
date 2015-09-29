package tr.org.mmo.asansor.managedbeans;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;
import tr.org.mmo.asansor.beans_.AnaSoru;
import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.RaporSource;
import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.business.KontrolBusiness;
import tr.org.mmo.asansor.business.RaporBusiness;
import tr.org.mmo.asansor.business.TemsilcilikBusiness;
import tr.org.mmo.asansor.dto.AsansorKapsamDTO;
import tr.org.mmo.asansor.dto.BelediyeDTO;
import tr.org.mmo.asansor.dto.BinaKisiDTO;
import tr.org.mmo.asansor.dto.CihazFirmaDTO;
import tr.org.mmo.asansor.dto.CihazTeknikDTO;
import tr.org.mmo.asansor.dto.KontrolDTO;
import tr.org.mmo.asansor.dto.KontrolFirmaKatilimDTO;
import tr.org.mmo.asansor.dto.MuayeneKurulusDTO;
import tr.org.mmo.asansor.dto.OnTanimliTestDTO;
import tr.org.mmo.asansor.dto.RandevuMuhendisDTO;
import tr.org.mmo.asansor.dto.RaporDTO;
import tr.org.mmo.asansor.dto.SoruDTO;
import tr.org.mmo.asansor.dto.SoruOnTanimliDTO;
import tr.org.mmo.asansor.dto.YapiKonusuDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.StringSort;

@ManagedBean
@ViewScoped
public class KontrolBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7094768898501566031L;
	private List<KontrolDTO> kontrolList;
	private List<KontrolDTO> filteredVal;
	DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;
	
	@ManagedProperty("#{binaBean}")
	private BinaBean binaBean;
	
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	@PostConstruct
	public void init() {

		kontrolList = new ArrayList<KontrolDTO>();
		getKontroller();

	}

	private void getKontroller() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		try {
			kontrolList = new KontrolBusiness().getKontroller();
			/*
			 * TIMEOUT ÇALIŞMASINDA PREPARE RAPOR METODUNA TAŞINDI. 11.08.2015 (HAKAN)
			 * 
			 * 
			 * 
			 * 
			for (KontrolDTO k : kontrolList) {
				SoruTableBean soruTableBean = new SoruTableBean();
				int kapsamId = 0;
				for (CihazTeknikDTO c : k.getCihazTeknikBilgiler()) {
					for (AsansorKapsamDTO akd : ((SessionBean) Common
							.findBean("sessionBean")).getKapsamTurleri()) {
						if (c.getCevap().equals(akd.getKapsamAdi())) {
							kapsamId = akd.getId();
							break;
						}

					}
				}
				Date tarih=new Date();
				try{
				tarih=k.getKontrolBaslangicTarihi().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?sessionBean.getBakanlikSoru().getTarih():null;
				}catch(Exception e){
					tarih=null;
				}
				soruTableBean.getAnaSoru(k.getCihaz().getTip(), kapsamId, true,tarih);
				soruTableBean.cihazTestSonucGetir(k.getKontrolId());
				k.setTestSorular(soruTableBean.getSoruListSmall());
			}*/
		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
		}

	}

	public List<KontrolDTO> getKontrolList() {
		return kontrolList;
	}

	public void setKontrolList(List<KontrolDTO> kontrolList) {
		this.kontrolList = kontrolList;
	}

	public List<KontrolDTO> getFilteredVal() {
		return filteredVal;
	}

	public void setFilteredVal(List<KontrolDTO> filteredVal) {
		this.filteredVal = filteredVal;
	}

	public void prepareRapor(KontrolDTO kontrol) {
		/*
		 * raporun değişmesi ile birlikte bu method hazırlandı. hidrolik asansör
		 * raporu değişirse pdfrapor methodu silinecek. 28.11.2014
		 */
		
		/*
		 * 11.08.2015- TIMEOUT ÇALIŞMASINDA GETKONTROLLER DEN ALINIP BURAYA EKLENDİ
		 */
		
		SoruTableBean soruTableBean = new SoruTableBean();
		int kapsamId = 0;
		for (CihazTeknikDTO c : kontrol.getCihazTeknikBilgiler()) {
			for (AsansorKapsamDTO akd : ((SessionBean) Common
					.findBean("sessionBean")).getKapsamTurleri()) {
				if (c.getCevap().equals(akd.getKapsamAdi())) {
					kapsamId = akd.getId();
					break;
				}

			}
		}
		Date tarih=new Date();
		try{
		tarih=kontrol.getKontrolBaslangicTarihi().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?sessionBean.getBakanlikSoru().getTarih():null;
		}catch(Exception e){
			tarih=null;
		}
		soruTableBean.getAnaSoru(kontrol.getCihaz().getTip(), kapsamId, true,tarih);
		soruTableBean.cihazTestSonucGetir(kontrol.getKontrolId());
		kontrol.setTestSorular(soruTableBean.getSoruListSmall());
		
		/*
		 * 
		 */
		
		if (kontrol.getKontrolBaslangicTarihi().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0){
			yeniRapor(kontrol);
		}else{
		if (kontrol.getCihaz().getTip() == 66) {
			elektrikliAsansorRapor(kontrol);
		} else {
			pdfRapor(kontrol);
		}
		}
	}
	private void yeniRapor(KontrolDTO kontrol) {

		try {
			RaporBean rapor = new RaporBean();
			
				rapor.setRaporAdi("asansorkontrolraporu.jasper");
			
			String path = ((ServletContext) FacesContext.getCurrentInstance()
					.getExternalContext().getContext())
					.getRealPath("/resources/raporlar/");

			path.replace("\\", "/");
			path += "/";
			// System.out.println(path);
			rapor.setRaporPath(path);

			rapor.setFileName(kontrol.getBina().getBinaId() + "-"
					+ kontrol.getCihazId() + "-" + df.format(new Date())
					+ (kontrol.isRevizyonRapor() ? "(REV)" : "") + ".pdf");

			HashMap<Object, Object> parameter = new HashMap<Object, Object>();

			parameter = kontrol.getCihaz().getTip() == 66 ? getElektrikliAsansorParametreler(kontrol)
					: getHidrolikAsansorParametreler(kontrol);
			try{
				kontrol.setKontrolFirmaKatilimDTO(new KontrolBusiness().getFirmaKatilim(kontrol.getKontrolId()));
			}catch(CRUDException e){
				kontrol.setKontrolFirmaKatilimDTO(new KontrolFirmaKatilimDTO());
			}
			parameter.put("firmaPersonelAdSoyad", kontrol.getKontrolFirmaKatilimDTO().getFirmaGorevli1());
			parameter.put("firmaPersonelGorev", kontrol.getKontrolFirmaKatilimDTO().getFirmaGorevli1Gorev());
			MuayeneKurulusDTO muayeneKurulus=new MuayeneKurulusDTO();
			try{
				if (kontrol.getBina().getIl()>0 && kontrol.getBina().getIlce()>0){
					muayeneKurulus=new TemsilcilikBusiness().getTemsilcilik(kontrol.getBina().getIl(), kontrol.getBina().getIlce());
					if (muayeneKurulus.getKod()<=0){
						muayeneKurulus=new TemsilcilikBusiness().getSube(kontrol.getBina().getIl());
					}
				}else{
					if (kontrol.getBina().getIl()>0){
						muayeneKurulus=new TemsilcilikBusiness().getSube(kontrol.getBina().getIl());
					}
				}
				
			}catch(CRUDException e){
				muayeneKurulus=new MuayeneKurulusDTO();
			}
			parameter.put("aTipiMuayeneKurulusuAdres", muayeneKurulus.getAdres());
			parameter.put("aTipiMuayeneKurulusuTelefon", muayeneKurulus.getTelefonNo());
			parameter.put("aTipiMuayeneKurulusuFaks",muayeneKurulus.getFaks());
			parameter.put("aTipiMuayeneKurulusuEPosta", muayeneKurulus.getePosta());
			
			for(BinaKisiDTO kisi : kontrol.getBinaKisiList()){
				parameter.put("binaSorumlusuEPosta", kisi.getePosta());
			if (kisi.getSorumlulukTuru() == 1)
				break;
			}
			
			parameter.put("raporOnayTarihi", kontrol.getOnayTarihi());
			parameter.put("etiket",kontrol.getEtiket());
			parameter.put("pAciklamalar", kontrol.getAciklamalar() == null ? ""
					: kontrol.getAciklamalar().replace('İ', 'i').toLowerCase());
			parameter.put(
					"raporOnayDurum",
					kontrol.getOnayDurum() == null ? "R" : (kontrol
							.getOnayDurum().equals("O") ? kontrol
							.getOnayDurum() : "R"));
			parameter.put("logo", setAsansorRaporLogo());

			

			Map<Integer, List<String>> map=new HashMap<Integer, List<String>>();
		
			
			for (AnaSoru as : kontrol.getTestSorular()) {
				int uygunsuzlukKisim=0;
				 List<String> aciklamaList=new ArrayList<String>();
				for (SoruDTO s:as.getAltSorular()){
					aciklamaList=new ArrayList<String>();
					uygunsuzlukKisim=s.getYildiz().trim().equalsIgnoreCase("**")?1:s.getYildiz().trim().equalsIgnoreCase("*")?2:s.getYildiz().trim().equalsIgnoreCase("")?3:0;
					String kapsam=(String)parameter.get("kapsam");
					if (!kapsam.contains("TS EN 81-1") && !kapsam.contains("TS EN 81-2") && !kapsam.contains("TS EN 81-1+A3") && !kapsam.contains("TS EN 81-2+A3") && !kapsam.contains("TS EN 81-20")){
					
					if (uygunsuzlukKisim==3){
						if (s.getSinif()==4){
							uygunsuzlukKisim=4;
						}
					}
					}
					
					 for (int i : s.getSelectedListId()) {
							if (i > 0) {
								for (SoruOnTanimliDTO sod : s.getList()) {
									if (i == sod.getId()) {
										aciklamaList.add(s.getSorun() + "-" +sod.getAciklama());
									}
								}
							}
						}
					 
					 List<String> strList=new ArrayList<String>();
						int j=0;
						if (map.get(uygunsuzlukKisim)!=null && map.get(uygunsuzlukKisim).size()>0){
							strList=map.get(uygunsuzlukKisim);
							j=strList.size();
						}
						for (String s1:aciklamaList){
							strList.add(new StringBuffer().append(++j).append(")").append(s1).toString());
						}
						map.put(uygunsuzlukKisim, strList);
					
				}
				
				
				for (SoruDTO s:as.getOptionalAltSorular()){
					 aciklamaList=new ArrayList<String>();
					uygunsuzlukKisim=s.getYildiz().trim().equalsIgnoreCase("**")?1:s.getYildiz().trim().equalsIgnoreCase("*")?2:s.getYildiz().trim().equalsIgnoreCase("")?3:0;
					String kapsam=(String)parameter.get("kapsam");
					if (!kapsam.contains("TS EN 81-1") && !kapsam.contains("TS EN 81-2") && !kapsam.contains("TS EN 81-1+A3") && !kapsam.contains("TS EN 81-2+A3") && !kapsam.contains("TS EN 81-20")){
					
					if (uygunsuzlukKisim==3){
						if (s.getSinif()==4){
							uygunsuzlukKisim=4;
						}
					}}
					 
					 for (int i : s.getSelectedListId()) {
							if (i > 0) {
								for (SoruOnTanimliDTO sod : s.getList()) {
									if (i == sod.getId()) {
										aciklamaList.add(sod.getAciklama());
									}
								}
							}
						}
				List<String> strList=new ArrayList<String>();
						int j=0;
						if (map.get(uygunsuzlukKisim)!=null && map.get(uygunsuzlukKisim).size()>0){
							strList=map.get(uygunsuzlukKisim);
							j=strList.size();
						}
						for (String s1:aciklamaList){
							strList.add(new StringBuffer().append(++j).append(")").append(s1).toString());
						}
						map.put(uygunsuzlukKisim, strList);
					
				}
				
				
				
				
				
				
			}
			Set<Integer> set=map.keySet();
			Iterator<Integer> it=set.iterator();
			List<RaporSource> list = new ArrayList<RaporSource>();
			int mapSize=map.size();
			int mapESize=0;
			while (it.hasNext()){
			int k=it.next();
			if (map.get(k)!=null && map.get(k).size()>0){
				mapESize=map.get(k).size();
				break;
			}
			}
			if (map!=null && mapSize>0 && mapESize>0){
				
				RaporSource r = new RaporSource();
				r.setfKriter("HEADER");
				list.add(r);
				
				
			}
		
			//while (it.hasNext()){
				
				
		//		int i=it.next();
			if (map!=null && mapSize>0 && mapESize>0){
				int q=0;
			for (int i=1;i<5;i++){
				if (map.get(i)!=null && map.get(i).size()>0){
				int j=0;
				q++; /*Uygunsuzluk bölümlerini taşımak için i yerine kondu. */
				int arrSize=map.get(i).size();
				for (String s:map.get(i)){
					RaporSource r = new RaporSource();
					 if (j==arrSize-1){
						 r.setfKriter("DETAIL1");
					 }else{
						r.setfKriter("DETAIL");
					 }
						if (j==0){
							r.setSira(q+".");
							
						}
						else{
							r.setSira("");
						}
						r.setfAciklama(s);
					
								
					 	list.add(r);
						j++;
					}
				}/* UYGUNSUZLUK OLMAYAN BÖLÜMLER ÇIKMASIN
				else{
					RaporSource r = new RaporSource();	
					 r.setfKriter("DETAIL1");
					 r.setfAciklama("");
					 r.setSira(i+".");
					 list.add(r);
				}*/
				
				}
			}
				
				
			
			
		
			if (map!=null && mapSize>0 && mapESize>0){
				RaporSource r = new RaporSource();
				r.setfKriter("FOOTER0");
				list.add(r);
				 r = new RaporSource();
				
				r.setfKriter("FOOTER1");
				r.setfAciklama("Notlar :");
				list.add(r);
				//if (map.get(1)!=null && map.get(1).size()>0){
				int q=0;
				Set<Integer> set1=map.keySet();
				Iterator<Integer> it1=set1.iterator();
				while (it1.hasNext()){
				int ii=it1.next();
				if (map.get(ii).size()>0){
				r = new RaporSource();
				r.setfKriter("FOOTER1");
				q++;
				switch (ii) {
				
				
				
				case 1:
					r.setfAciklama(" "+q+".Kısımdaki uygunsuzluklar GÜVENSİZ olarak ifade edilmektedir."+(kontrol.getKontrolTuru().equals("N") ?"Düzeltme süresi 1 aydır.":""));
					break;
				case 2:
					r.setfAciklama(" "+q+".Kısımdaki uygunsuzluklar KUSURLU olarak ifade edilmektedir."+(kontrol.getKontrolTuru().equals("N") ?" Düzeltme süresi 2 aydır.":""));
					break;
				case 3:
					r.setfAciklama(" "+q+".Kısımdaki uygunsuzluklar HAFİF KUSURLU olarak ifade edilmektedir. Düzeltme süresi 12 aydır. ");
					break;
				case 4:
					r.setfAciklama(" "+q+".Kısımdaki Uygunsuzluklar HAFİF KUSURLU olarak ifade edilmektedir. Düzeltme süresi 48 aydır. ");
					break;
				default:
					break;
				}
				list.add(r);
				}
				
				
				}
					
				
				if (kontrol.getKontrolTuru().equals("E") && (kontrol.getEtiket().equals("S") || kontrol.getEtiket().equals("K") )) {
					r = new RaporSource();
					r.setfKriter("FOOTER1");
					r.setfAciklama("Not:Asansör İşletme,Bakım ve Periyodik Kontrol Yönetmeliği Madde 24 gereği takip kontrolü sonucu güvensiz/kusurlu olan asansörünüz güvenli hale getirilinceye kadar ilgili idare tarafından mühürlenir. ");
					list.add(r);
					}
			
				
				
				
				
				r = new RaporSource();
				
				r.setfKriter("FOOTER2");
				
				list.add(r);
				
			}

			rapor.setParameter(parameter);
			rapor.setListt(list);

			RaporDTO raporDTO = new RaporDTO();
			raporDTO.setDosyaAdi(rapor.getFileName());

			raporDTO.setKontrolId(kontrol.getKontrolId());
			raporDTO.setRaporTarihi(new Date());
			int raporNo = 0;

			raporNo = raporKaydet(raporDTO);

			parameter.put("raporNo", String.valueOf(raporNo));
			parameter.put("raporTarihi", new Date());

			rapor.pdf(raporNo, kontrol.getCihaz().getTip());

			for (KontrolDTO k : kontrolList) {
				if (kontrol.getKontrolId() == k.getKontrolId()) {
					k.setRaporYazildi(true);
					break;
				}
			}
		} catch (CRUDException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (JRException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().responseComplete();
			if (e.getMessage().contains(
					"Dosya başka bir uygulama tarafından kullanılıyor")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}

		} catch (IOException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().responseComplete();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}
	private void elektrikliAsansorRapor(KontrolDTO kontrol) {

		try {
			RaporBean rapor = new RaporBean();
			if (kontrol.getCihaz().getTip() == 66) {
				rapor.setRaporAdi("periyodikKontrolRapor(ElektrikliAsansor).jasper");
			} else {
				rapor.setRaporAdi("periyodikKontrolRapor(HidrolikAsansor).jasper");
			}

			String path = ((ServletContext) FacesContext.getCurrentInstance()
					.getExternalContext().getContext())
					.getRealPath("/resources/raporlar/");

			path.replace("\\", "/");
			path += "/";
			// System.out.println(path);
			rapor.setRaporPath(path);

			rapor.setFileName(kontrol.getBina().getBinaId() + "-"
					+ kontrol.getCihazId() + "-" + df.format(new Date())
					+ (kontrol.isRevizyonRapor() ? "(REV)" : "") + ".pdf");

			HashMap<Object, Object> parameter = new HashMap<Object, Object>();

			parameter = kontrol.getCihaz().getTip() == 66 ? getElektrikliAsansorParametreler(kontrol)
					: getHidrolikAsansorParametreler(kontrol);

			parameter.put("pAciklamalar", kontrol.getAciklamalar() == null ? ""
					: kontrol.getAciklamalar().replace('İ', 'i').toLowerCase());
			parameter.put(
					"raporOnayDurum",
					kontrol.getOnayDurum() == null ? "R" : (kontrol
							.getOnayDurum().equals("O") ? kontrol
							.getOnayDurum() : "R"));
			parameter.put("logo", setAsansorRaporLogo());

			List<RaporSource> list = new ArrayList<RaporSource>();

			RaporSource r = new RaporSource();
			r.setSira("HEADER");
			list.add(r);

			List<String> aciklamalarList = new ArrayList<String>();

			for (AnaSoru as : kontrol.getTestSorular()) {
				boolean raporaAl = true;
				as.setDurum(as.getDurum() == null ? "" : as.getDurum());
				// if (as.getDurum() != null && as.getDurum().contains("U")) {
				r = new RaporSource();
				r.setSira("DETAIL");
				aciklamalarList = new ArrayList<String>();
				r.setfKriter(as.getSorun() + " " + as.getSoru());
				r.setfSonuc(as.getDurum().replace('i', 'İ').toUpperCase());
				for (SoruDTO s : as.getAltSorular()) {
					for (int i : s.getSelectedListId()) {
						if (i > 0) {
							for (SoruOnTanimliDTO sod : s.getList()) {
								if (i == sod.getId()) {
									aciklamalarList.add(sod.getAciklama());
								}
							}
						}
					}
				}
				Collections.sort(aciklamalarList, new StringSort());
				StringBuilder sb = new StringBuilder();
				int k = 0;
				for (String s : aciklamalarList) {

					sb.append(++k).append(" ) ").append(s).append(" ");
				}

				r.setfAciklama(sb.toString().trim());
				if (as.getKapsam()[0] == (short) -1 && !as.isChecked()) {
					raporaAl = false;
				}
				if (raporaAl) {
					list.add(r);
				}
				// }

			}
			r = new RaporSource();
			r.setSira("RESULT");
			list.add(r);

			rapor.setParameter(parameter);
			rapor.setListt(list);

			RaporDTO raporDTO = new RaporDTO();
			raporDTO.setDosyaAdi(rapor.getFileName());

			raporDTO.setKontrolId(kontrol.getKontrolId());
			raporDTO.setRaporTarihi(new Date());
			int raporNo = 0;

			raporNo = raporKaydet(raporDTO);

			parameter.put("raporNo", String.valueOf(raporNo));
			parameter.put("raporTarihi", new Date());

			rapor.pdf(raporNo, kontrol.getCihaz().getTip());

			for (KontrolDTO k : kontrolList) {
				if (kontrol.getKontrolId() == k.getKontrolId()) {
					k.setRaporYazildi(true);
					break;
				}
			}
		} catch (CRUDException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (JRException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().responseComplete();
			if (e.getMessage().contains(
					"Dosya başka bir uygulama tarafından kullanılıyor")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}

		} catch (IOException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().responseComplete();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}

	public String pdfRapor(KontrolDTO kontrol) {

		try {
			RaporBean rapor = new RaporBean();
			if (kontrol.getCihaz().getTip() == 66) {
				rapor.setRaporAdi("periyodikKontrolRapor(ElektrikliAsansor).jasper");
			} else {
				rapor.setRaporAdi("periyodikKontrolRapor(HidrolikAsansor).jasper");
			}

			String path = ((ServletContext) FacesContext.getCurrentInstance()
					.getExternalContext().getContext())
					.getRealPath("/resources/raporlar/");

			path.replace("\\", "/");
			path += "/";
			// System.out.println(path);
			rapor.setRaporPath(path);

			rapor.setFileName(kontrol.getBina().getBinaId() + "-"
					+ kontrol.getCihazId() + "-" + df.format(new Date())
					+ (kontrol.isRevizyonRapor() ? "(REV)" : "") + ".pdf");

			HashMap<Object, Object> parameter = new HashMap<Object, Object>();

			parameter = kontrol.getCihaz().getTip() == 66 ? getElektrikliAsansorParametreler(kontrol)
					: getHidrolikAsansorParametreler(kontrol);

			parameter.put("pAciklamalar", kontrol.getAciklamalar() == null ? ""
					: kontrol.getAciklamalar().replace('İ', 'i').toLowerCase());
			parameter.put(
					"raporOnayDurum",
					kontrol.getOnayDurum() == null ? "R" : (kontrol
							.getOnayDurum().equals("O") ? kontrol
							.getOnayDurum() : "R"));
			parameter.put("logo", setAsansorRaporLogo());

			List<RaporSource> list = new ArrayList<RaporSource>();

			RaporSource r = new RaporSource();
			r.setSira("HEADER");
			list.add(r);

			List<String> aciklamalarList = new ArrayList<String>();

			for (AnaSoru as : kontrol.getTestSorular()) {
				boolean raporaAl = true;
				as.setDurum(as.getDurum() == null ? "" : as.getDurum());
				// if (as.getDurum() != null && as.getDurum().contains("U")) {
				r = new RaporSource();
				r.setSira("DETAIL");
				aciklamalarList = new ArrayList<String>();
				r.setfKriter(as.getSorun() + " " + as.getSoru());
				r.setfSonuc(as.getDurum().replace('i', 'İ').toUpperCase());
				for (SoruDTO s : as.getAltSorular()) {
					for (int i : s.getSelectedListId()) {
						if (i > 0) {
							for (SoruOnTanimliDTO sod : s.getList()) {
								if (i == sod.getId()) {
									aciklamalarList.add(sod.getAciklama());
								}
							}
						}
					}
				}
				Collections.sort(aciklamalarList, new StringSort());
				StringBuilder sb = new StringBuilder();
				int k = 0;
				for (String s : aciklamalarList) {

					sb.append(++k).append(" ) ").append(s).append(" ");
				}

				r.setfAciklama(sb.toString().trim());
				if (as.getKapsam()[0] == (short) -1 && !as.isChecked()) {
					raporaAl = false;
				}
				if (raporaAl) {
					list.add(r);
				}

				// }

			}
			r = new RaporSource();
			r.setSira("RESULT");
			list.add(r);

			rapor.setParameter(parameter);
			rapor.setListt(list);

			RaporDTO raporDTO = new RaporDTO();
			raporDTO.setDosyaAdi(rapor.getFileName());

			raporDTO.setKontrolId(kontrol.getKontrolId());
			raporDTO.setRaporTarihi(new Date());
			int raporNo = 0;

			raporNo = raporKaydet(raporDTO);

			parameter.put("raporNo", String.valueOf(raporNo));
			parameter.put("raporTarihi", new Date());

			rapor.pdf(raporNo, kontrol.getCihaz().getTip());

			for (KontrolDTO k : kontrolList) {
				if (kontrol.getKontrolId() == k.getKontrolId()) {
					k.setRaporYazildi(true);
					break;
				}
			}
		} catch (CRUDException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (JRException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().responseComplete();
			if (e.getMessage().contains(
					"Dosya başka bir uygulama tarafından kullanılıyor")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}

		} catch (IOException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().responseComplete();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

		return "";

	}

	public Image setAsansorRaporLogo() throws IOException {
		BufferedImage bm;
		Image im;
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext();

		bm = ImageIO.read(new File(ctx.getRealPath("/resources/image/" + "/"
				+ "mmoraporlogo.jpg")));

		im = Toolkit.getDefaultToolkit().createImage(bm.getSource());

		return im;
	}

	@SuppressWarnings("unused")
	private Image setAsansorImageHeader() throws IOException {
		BufferedImage bm;
		Image im;
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext();

		bm = ImageIO.read(new File(ctx.getRealPath("\\resources\\image\\"
				+ "resim_04_y1_03.gif")));

		im = Toolkit.getDefaultToolkit().createImage(bm.getSource());

		return im;
	}

	public int raporKaydet(RaporDTO rapor) throws CreateException {

		return new RaporBusiness().raporDosyasiOlusturKaydet(rapor);

	}

	public HashMap<Object, Object> getElektrikliAsansorParametreler(
			KontrolDTO kontrol) {
		HashMap<Object, Object> parameter = new HashMap<Object, Object>();
		String kapsam="";
		// parameter.put("asansorTipi",kontrol.getCihaz().getTipAciklama().toUpperCase());
		parameter.put("asansorTip", kontrol.getCihaz().getTip());
		parameter.put("binaAdi", kontrol.getBina().getBinaAdi());
		parameter.put("asansorCinsi", "");
		parameter.put("asansorKimlikNo", kontrol.getCihaz().getUavtEtiket());
		
		String adres;
		try {
			adres =  kontrol.getBina().getMahalle().trim() + " MAHALLESİ "
					+ kontrol.getBina().getCaddeSokak().trim()
					+ " NO:" + kontrol.getBina().getBinaNo().trim()+" "+kontrol.getBina().getIlceAdi().trim()+" "+kontrol.getBina().getIlAdi().trim();
					
		} catch (Exception e) {
			adres = kontrol.getBina().getAcikAdres();
		}
		boolean yeniRapor=false;
		yeniRapor=kontrol.getKontrolBaslangicTarihi().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?true:false; 
		if (yeniRapor){
			adres=kontrol.getBina().getBinaAdi()+" "+adres;
		}
		parameter.put("binaAdresi", adres);
		parameter.put("binaKod", String.valueOf(kontrol.getBina().getBinaId()));
		for (BelediyeDTO b : loginBean.getBelediyeList()) {
			if (b.getKod() == kontrol.getBina().getBelediye()) {
				parameter.put("belediye", b.getAdi());
				break;
			}
		}

		

		parameter.put("ilce", kontrol.getBina().getIlceAdi());
		parameter.put("il", kontrol.getBina().getIlAdi());
		for (YapiKonusuDTO y : binaBean.getYapiTipler()) {
			if (y.getId() == kontrol.getBina().getYapiTip()) {
				parameter.put("yapiKonusu", y.getTip());
				break;
			}

		}
		parameter.put("kontrolTuru",
				kontrol.getKontrolTuru().equals("E") ? "Eksiklik Kontrolü"
						: "Normal Kontrol");
		parameter.put("pafta", kontrol.getBina().getPafta());
		parameter.put("ada", kontrol.getBina().getAda());
		parameter.put("parsel", kontrol.getBina().getParsel());
		List<KontrolDTO> oncekiKontrolList = new ArrayList<KontrolDTO>();
		try {
			oncekiKontrolList = new KontrolBusiness()
					.getCihazKontrollerForRapor(kontrol.getCihazId(),
							kontrol.getKontrolId());
		} catch (ReadException e) {
			oncekiKontrolList = new ArrayList<KontrolDTO>();
		}
		Calendar cal = Calendar.getInstance();

		if (kontrol.getKontrolTuru().equals("N")) {
			parameter.put("kontrolTarihi1", kontrol.getKontrolBitisTarihi());
			cal.setTime(kontrol.getKontrolBitisTarihi());
			parameter.put("kontrolTarihi2", null);
			parameter.put("kontrolTarihi3", null);
		} else {

			boolean eksiklikYazildi = false;
			for (KontrolDTO k : oncekiKontrolList) {
				if (k.getKontrolTuru().equals("E") && !eksiklikYazildi) {

					parameter.put("kontrolTarihi2", k.getKontrolBitisTarihi());
					parameter.put("kontrolTarihi3",
							kontrol.getKontrolBitisTarihi());
					eksiklikYazildi = true;
				}
				if (k.getKontrolTuru().equals("N")) {

					parameter.put("kontrolTarihi1", k.getKontrolBitisTarihi());
					cal.setTime(k.getKontrolBitisTarihi());

					if (!eksiklikYazildi) {
						parameter.put("kontrolTarihi2",
								kontrol.getKontrolBitisTarihi());
					}
					break;
				}
			}

		}
		for (BinaKisiDTO kisi : kontrol.getBinaKisiList()) {
			String binaSorumlusu = new StringBuffer(kisi.getAdi() == null ? ""
					: kisi.getAdi().trim())
					.append(" ")
					.append(kisi.getSoyadi() == null ? "" : kisi.getSoyadi()
							.trim()).toString();
			String irtibatTelefon="";
			if (kisi.getTelefonNoGsmStr()!=null && kisi.getTelefonNoGsmStr().trim().length()>5){
				irtibatTelefon=kisi.getTelefonNoGsmStr();
			}else{
				if (kisi.getTelefonNoStr()!=null && kisi.getTelefonNoStr().trim().length()>5){
					irtibatTelefon=kisi.getTelefonNoStr();
				}
			}
			
			parameter.put("binaSorumlusu", binaSorumlusu);
			parameter.put("irtibatTelefon", irtibatTelefon);

			if (kisi.getSorumlulukTuru() == 1)
				break;
		}

		if (kontrol.getFirma() != null) {
			CihazFirmaDTO cihazFirma = new CihazFirmaDTO();
			String bakimciFirmaPersonel = null;
			try {
				cihazFirma = new FirmaBusiness().getCihazAnlasmaliFirma(kontrol
						.getCihaz().getCihazId(), kontrol.getFirma().getKod());
			} catch (Exception e) {
				cihazFirma = new CihazFirmaDTO();
			}
			try {
				int kontrolId = kontrol.getKontrolId();
				bakimciFirmaPersonel = new KontrolBusiness()
						.getBakimciFirmaPersonel(kontrolId);
			} catch (Exception e1) {
				e1.getMessage();
			}

			parameter.put("binaBakimSozlesmesi", cihazFirma.getId() > 0 ? "E"
					: "H");
			parameter.put(
					"binaBakimSozlesmesiTarihi",
					cihazFirma.getSozlesmeTarih() == null ? "" : df.format(cihazFirma
							.getSozlesmeTarih()));
			try{
				String sure="";
			int sozlesmeSure=	Integer.parseInt(cihazFirma.getSozlesmeSure().trim());
			int yil = sozlesmeSure / 365;       
			sure=yil>0?yil+" YIL":"";
			int ay = (sozlesmeSure - (yil*365)) / 30; 
			sure=ay>0?sure+" "+ay+" AY":sure;
			int gun = (sozlesmeSure - ((yil*365) + (ay*30))); 
			sure=gun>0?sure+" "+gun+" GÜN":sure;
				parameter.put(
						"binaBakimSozlesmeSure",sure);
			}catch(Exception e){
				parameter.put(
						"binaBakimSozlesmeSure","");
			}

			parameter.put("bakimciFirma", kontrol.getFirma().getUnvan());
			parameter.put("bakimciFirmaAdres", kontrol.getFirma().getAdres());

			parameter.put("bakimciFirmaIlce", kontrol.getFirma().getIlceAdi());
			parameter.put("bakimciFirmaIl", kontrol.getFirma().getIlAdi());
			kontrol.getFirma().setUygunlukBelgesi(
					kontrol.getFirma().getUygunlukBelgesi() == null ? ""
							: kontrol.getFirma().getUygunlukBelgesi());
			parameter.put("ceUygunlukBelgesi", kontrol.getFirma()
					.getUygunlukBelgesi());
			parameter.put(
					"ceBelgeTarih",
					kontrol.getFirma().getGecerlilikSuresi() == null ? "" : df
							.format(kontrol.getFirma().getGecerlilikSuresi()));
			kontrol.getFirma().setMonteEden(
					kontrol.getFirma().getMonteEden() == null ? "" : kontrol
							.getFirma().getMonteEden());
			parameter.put("monteEden", kontrol.getFirma().getMonteEden());
			kontrol.getFirma().setYetkiliServis(
					kontrol.getFirma().getYetkiliServis() == null ? ""
							: kontrol.getFirma().getYetkiliServis());
			parameter.put("yetkiliServis", kontrol.getFirma()
					.getYetkiliServis());
			kontrol.getFirma().setServisSozlesme(
					kontrol.getFirma().getServisSozlesme() == null ? ""
							: kontrol.getFirma().getServisSozlesme());
			parameter.put("yetkiliServisSozlesme", kontrol.getFirma()
					.getServisSozlesme());
			parameter.put(
					"yetkiliServisSozlesmeTarih",
					kontrol.getFirma().getSozlesmeTarihi() == null ? "" : df
							.format(kontrol.getFirma().getSozlesmeTarihi()));
			kontrol.getFirma().setTseBelgeNo(
					kontrol.getFirma().getTseBelgeNo() == null ? "" : kontrol
							.getFirma().getTseBelgeNo().trim());
			parameter.put("tseHybBelgesi", kontrol.getFirma().getTseBelgesi());
			parameter.put("bakimciFirmaTelefon", kontrol.getFirma()
					.getTelefonNoStr());
			parameter.put("bakimciFirmaFaks", kontrol.getFirma()
					.getFaksStr());
			parameter.put("bakimciFirmaEPosta", kontrol.getFirma().getEposta());
			parameter
					.put("tseHybBelgesiNo", kontrol.getFirma().getTseBelgeNo());
			parameter.put("firmaPersonelAdSoyad", bakimciFirmaPersonel);
		}
		int sm = 0;
		for (RandevuMuhendisDTO rm : kontrol.getKontrolMuhendisleri()) {
			if (rm.getSorumlu().equals("E")) {
				parameter.put("muhendisSicilNo", rm.getSicilNo());
				parameter.put("muhendisAdiSoyadi", rm.getAdiSoyadi());
				parameter.put("muhendisUnvani",rm.getUnvan());
			}
			if (rm.getSorumlu().equals("H") || rm.getSorumlu().equals("O")) {
				switch (sm) {
				case 0:
					parameter.put("muhendisSicilNo1", rm.getSicilNo());
					parameter.put("muhendisAdiSoyadi1", rm.getAdiSoyadi());
					parameter.put("muhendisUnvani1",rm.getUnvan());
					sm++;
					break;
				case 1:
					parameter.put("muhendisSicilNo2", rm.getSicilNo());
					parameter.put("muhendisAdiSoyadi2", rm.getAdiSoyadi());
					parameter.put("muhendisUnvani2",rm.getUnvan());
					sm++;
					break;

				default:
					break;
				}
			}
		}

		parameter.put("onaylayanAdiSoyadi", kontrol.getOnaylayanAdiSoyadi());
		parameter.put("onaylayanSicilNo",
				String.valueOf(kontrol.getOnaylayanSicilNo()));

		String str = "";
		str = kontrol.getEtiket().equals("S") ? Messages._ETIKETSARI_
				.getMesaj() : str;
		str = kontrol.getEtiket().equals("Y") ? Messages._ETIKETYESIL_
				.getMesaj() : str;
		str = kontrol.getEtiket().equals("K") ? Messages._ETIKETKIRMIZI_
				.getMesaj() : str;

		Date birSonrakiKontrolTarihi = null;
		/*
		 * if (kontrol.getEtiket().equals("K")) { cal.set(Calendar.MONTH,
		 * cal.get(Calendar.MONTH) + 1);
		 * 
		 * } if (kontrol.getEtiket().equals("S")) { cal.set(Calendar.MONTH,
		 * cal.get(Calendar.MONTH) + 6);
		 * 
		 * } if (kontrol.getEtiket().equals("Y")) { cal.set(Calendar.MONTH,
		 * cal.get(Calendar.MONTH) + 12);
		 * 
		 * }
		 * 
		 * birSonrakiKontrolTarihi = cal.getTime();
		 */
		if (!kontrol.getEtiket().equals("K")) {
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 12);
			birSonrakiKontrolTarihi = cal.getTime();

		}
		parameter.put("birSonrakiKontrolTarihi", birSonrakiKontrolTarihi);
		parameter.put("sonuc", str);
		/*
		 * Elektrikli Asansör Periyodik Kontrol Rapor Sorgusu ile yazıldı Bknz.
		 * Sayfa Sonu
		 */
		
		for (CihazTeknikDTO ctd : kontrol.getCihazTeknikBilgiler()) {
			ctd.setCevap(ctd.getCevap() == null ? "" : ctd.getCevap());
			switch (ctd.getDegerId()) {
			case 1:
				parameter.put("makinaMarkaTip", ctd.getCevap());
				break;
			case 2:
				parameter.put("yonetmelikAdiTarihi", ctd.getCevap());
				break;
			case 151:
				parameter.put("asansorunYeri", ctd.getCevap());
				break;
			case 152:
				parameter.put("kapasiteKisi", ctd.getCevap());
				break;
			case 153:
				parameter.put("seriNo", ctd.getCevap());
				break;
			case 158:
				parameter.put("kapasiteAgirlik", ctd.getCevap());
				break;
			case 155:
				parameter.put("sinifi", ctd.getCevap());
				break;
			case 154:
				parameter.put("imalYili", ctd.getCevap());
				break;
			case 160:
				
				if (yeniRapor){
					double hizd=0.00;
					try{
					hizd=Double.parseDouble(ctd.getCevap().trim());
					}catch(Exception e){
						hizd=0.00;
					}
					parameter.put("hiz", hizd);
				}else
				parameter.put("hiz", ctd.getCevap());
				break;
			case 161:
				parameter.put("motorGucu", ctd.getCevap());
				break;
			case 190:
				parameter.put("motorGucuBirimi", ctd.getCevap());
				break;
			case 162:
				parameter.put("motorSeriNo", ctd.getCevap());
				break;
			case 164:
				parameter.put("makinaSeriNo", ctd.getCevap());
				break;
			case 194:
				parameter.put("ceTseIsaret", ctd.getCevap());
				parameter.put("tseIsaret", ctd.getCevap());
				break;
			case 167:
				parameter.put("askiTipi", ctd.getCevap());
				break;
			case 168:
				parameter.put("halatSayisi", ctd.getCevap());
				break;
			case 169:
				parameter.put("halatCapi", ctd.getCevap());
				break;
			case 165:
				parameter.put("onKurNo", ctd.getCevap());
				break;
			case 157:
				parameter.put("durakSayisi", ctd.getCevap());
				break;
			case 207:
				parameter.put("katSayisi", ctd.getCevap());
				break;
			case 195:
				parameter.put("seyirMesafesi", ctd.getCevap());
				break;
			case 163:
				parameter.put("frenTipi", ctd.getCevap());
				break;
			case 170:
				parameter.put("kumandaTipi", ctd.getCevap());
				break;
			case 159:
				parameter.put("montajFirmasi", ctd.getCevap());
				break;
			case 201:
				parameter.put("kabinAlani", ctd.getCevap());
				break;
			case 166:
				if (!yeniRapor)
				parameter.put("kapsam", ctd.getCevap());
				else
					kapsam+=kapsam.trim().length()>0?";"+ctd.getCevap():ctd.getCevap();
				break;
			case 156:
				String[] s=ctd.getCevap().split(";");
				String kapsam_="";
				if (s!=null && s.length>0){
					for (String s1:s){
						if (s1.indexOf("(")>=0){
						String ss=s1.substring(s1.indexOf("(")+1,s1.indexOf(")"));
						kapsam_+=kapsam_.trim().length()>0?";"+ss:ss;
						}
					}
				}
				if (!yeniRapor)
				parameter.put("ozelSartlar",kapsam_);
				else{
					kapsam+=kapsam.trim().length()>0?";"+kapsam_:kapsam_;
				}
				break;
			case 191:
				parameter.put("ruhsatTescilBelgesi", ctd.getCevap());
				break;
			case 192:
				parameter.put("ruhsatTescilBelgesiNo", ctd.getCevap());
				break;
			case 193:
				parameter.put("ruhsatTescilBelgesiTarihi", ctd.getCevap()
						.trim());
				break;
			case 205:
				parameter.put("asansorCinsi", ctd.getCevap()
						.trim().equalsIgnoreCase("İNSAN")?"I":"Y");
				break;
			default:
				break;
			}

		}
		if (yeniRapor){
			parameter.put("kapsam",kapsam);
		}
		return parameter;

	}

	public HashMap<Object, Object> getHidrolikAsansorParametreler(
			KontrolDTO kontrol) {

		HashMap<Object, Object> parameter = new HashMap<Object, Object>();
		String kapsam="";
		// parameter.put("asansorTipi",kontrol.getCihaz().getTipAciklama().toUpperCase());
		parameter.put("asansorTip", kontrol.getCihaz().getTip());
		parameter.put("binaAdi", kontrol.getBina().getBinaAdi());
		parameter.put("asansorCinsi", "");
		parameter.put("asansorKimlikNo", kontrol.getCihaz().getUavtEtiket());
		String adres;
		try {
			adres =  kontrol.getBina().getMahalle().trim() + " MAHALLESİ "
					+ kontrol.getBina().getCaddeSokak().trim()
					+ " NO:" + kontrol.getBina().getBinaNo().trim()+" "+kontrol.getBina().getIlceAdi().trim()+" "+kontrol.getBina().getIlAdi().trim();
				
		} catch (Exception e) {
			adres = kontrol.getBina().getAcikAdres();
		}
		boolean yeniRapor=false;
		yeniRapor=kontrol.getKontrolBaslangicTarihi().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?true:false;
		if (yeniRapor){
			adres=kontrol.getBina().getBinaAdi()+" "+adres;
		}
		parameter.put("binaAdresi", adres);
		parameter.put("binaKod", String.valueOf(kontrol.getBina().getBinaId()));
		for (BelediyeDTO b : loginBean.getBelediyeList()) {
			if (b.getKod() == kontrol.getBina().getBelediye()) {
				parameter.put("belediye", b.getAdi());
				break;
			}
		}

		

		parameter.put("ilce", kontrol.getBina().getIlceAdi());
		parameter.put("il", kontrol.getBina().getIlAdi());
		for (YapiKonusuDTO y : binaBean.getYapiTipler()) {
			if (y.getId() == kontrol.getBina().getYapiTip()) {
				parameter.put("yapiKonusu", y.getTip());
				break;
			}

		}
		parameter.put("kontrolTuru",
				kontrol.getKontrolTuru().equals("E") ? "Eksiklik Kontrolü"
						: "Normal Kontrol");
		parameter.put("pafta", kontrol.getBina().getPafta());
		parameter.put("ada", kontrol.getBina().getAda());
		parameter.put("parsel", kontrol.getBina().getParsel());

		List<KontrolDTO> oncekiKontrolList = new ArrayList<KontrolDTO>();
		Calendar cal = Calendar.getInstance();
		try {

			oncekiKontrolList = new KontrolBusiness()
					.getCihazKontrollerForRapor(kontrol.getCihazId(),
							kontrol.getKontrolId());
		} catch (ReadException e) {
			oncekiKontrolList = new ArrayList<KontrolDTO>();
		}

		if (kontrol.getKontrolTuru().equals("N")) {
			parameter.put("kontrolTarihi1", kontrol.getKontrolBitisTarihi());

			cal.setTime(kontrol.getKontrolBitisTarihi());
			parameter.put("kontrolTarihi2", null);
			parameter.put("kontrolTarihi3", null);
		} else {

			boolean eksiklikYazildi = false;
			for (KontrolDTO k : oncekiKontrolList) {
				if (k.getKontrolTuru().equals("E") && !eksiklikYazildi) {

					parameter.put("kontrolTarihi2", k.getKontrolBitisTarihi());
					parameter.put("kontrolTarihi3",
							kontrol.getKontrolBitisTarihi());
					eksiklikYazildi = true;
				}
				if (k.getKontrolTuru().equals("N")) {

					parameter.put("kontrolTarihi1", k.getKontrolBitisTarihi());

					cal.setTime(k.getKontrolBitisTarihi());
					if (!eksiklikYazildi) {
						parameter.put("kontrolTarihi2",
								kontrol.getKontrolBitisTarihi());
					}
					break;
				}
			}

		}

		for (BinaKisiDTO kisi : kontrol.getBinaKisiList()) {
			String binaSorumlusu = new StringBuffer(kisi.getAdi() == null ? ""
					: kisi.getAdi().trim())
					.append(" ")
					.append(kisi.getSoyadi() == null ? "" : kisi.getSoyadi()
							.trim()).toString();
			String irtibatTelefon="";
			if (kisi.getTelefonNoGsmStr()!=null && kisi.getTelefonNoGsmStr().trim().length()>5){
				irtibatTelefon=kisi.getTelefonNoGsmStr();
			}else{
				if (kisi.getTelefonNoStr()!=null && kisi.getTelefonNoStr().trim().length()>5){
					irtibatTelefon=kisi.getTelefonNoStr();
				}
			}
			parameter.put("binaSorumlusu", binaSorumlusu);
			parameter.put("irtibatTelefon", irtibatTelefon);

			if (kisi.getSorumlulukTuru() == 1)
				break;

		}

		if (kontrol.getFirma() != null) {
			CihazFirmaDTO cihazFirma = new CihazFirmaDTO();
			String bakimciFirmaPersonel = null;

			try {
				cihazFirma = new FirmaBusiness().getCihazAnlasmaliFirma(kontrol
						.getCihaz().getCihazId(), kontrol.getFirma().getKod());
			} catch (Exception e) {
				cihazFirma = new CihazFirmaDTO();
			}
			try {
				int kontrolId = kontrol.getKontrolId();
				bakimciFirmaPersonel = new KontrolBusiness()
						.getBakimciFirmaPersonel(kontrolId);
			} catch (Exception e1) {
				e1.getMessage();
			}

			parameter.put("binaBakimSozlesmesi", cihazFirma.getId() > 0 ? "E"
					: "H");
			parameter.put(
					"binaBakimSozlesmesiTarihi",
					cihazFirma.getSozlesmeTarih() == null ? "" : df.format(cihazFirma
							.getSozlesmeTarih()));
			try{
				String sure="";
			int sozlesmeSure=	Integer.parseInt(cihazFirma.getSozlesmeSure().trim());
			int yil = sozlesmeSure / 365;       
			sure=yil>0?yil+" YIL":"";
			int ay = (sozlesmeSure - (yil*365)) / 30; 
			sure=ay>0?sure+" "+ay+" AY":sure;
			int gun = (sozlesmeSure - ((yil*365) + (ay*30))); 
			sure=gun>0?sure+" "+gun+" GÜN":sure;
				parameter.put(
						"binaBakimSozlesmeSure",sure);
			}catch(Exception e){
				parameter.put(
						"binaBakimSozlesmeSure","");
			}
			parameter.put("bakimciFirma", kontrol.getFirma().getUnvan());
			parameter.put("bakimciFirmaAdres", kontrol.getFirma().getAdres());

			parameter.put("bakimciFirmaIlce", kontrol.getFirma().getIlceAdi());
			parameter.put("bakimciFirmaIl", kontrol.getFirma().getIlAdi());
			kontrol.getFirma().setUygunlukBelgesi(
					kontrol.getFirma().getUygunlukBelgesi() == null ? ""
							: kontrol.getFirma().getUygunlukBelgesi());
			parameter.put("ceUygunlukBelgesi", kontrol.getFirma()
					.getUygunlukBelgesi());
			parameter.put(
					"ceBelgeTarih",
					kontrol.getFirma().getGecerlilikSuresi() == null ? "" : df
							.format(kontrol.getFirma().getGecerlilikSuresi()));
			kontrol.getFirma().setMonteEden(
					kontrol.getFirma().getMonteEden() == null ? "" : kontrol
							.getFirma().getMonteEden());
			parameter.put("monteEden", kontrol.getFirma().getMonteEden());
			kontrol.getFirma().setYetkiliServis(
					kontrol.getFirma().getYetkiliServis() == null ? ""
							: kontrol.getFirma().getYetkiliServis());
			parameter.put("yetkiliServis", kontrol.getFirma()
					.getYetkiliServis());
			kontrol.getFirma().setServisSozlesme(
					kontrol.getFirma().getServisSozlesme() == null ? ""
							: kontrol.getFirma().getServisSozlesme());
			parameter.put("yetkiliServisSozlesme", kontrol.getFirma()
					.getServisSozlesme());
			parameter.put(
					"yetkiliServisSozlesmeTarih",
					kontrol.getFirma().getSozlesmeTarihi() == null ? "" : df
							.format(kontrol.getFirma().getSozlesmeTarihi()));
			kontrol.getFirma().setTseBelgeNo(
					kontrol.getFirma().getTseBelgeNo() == null ? "" : kontrol
							.getFirma().getTseBelgeNo().trim());
			parameter.put("tseHybBelgesi", kontrol.getFirma().getTseBelgesi());
			parameter.put("bakimciFirmaTelefon", kontrol.getFirma()
					.getTelefonNoStr());
			parameter.put("bakimciFirmaFaks", kontrol.getFirma()
					.getFaksStr());
			parameter.put("bakimciFirmaEPosta", kontrol.getFirma().getEposta());
			parameter
					.put("tseHybBelgesiNo", kontrol.getFirma().getTseBelgeNo());
			parameter.put("firmaPersonelAdSoyad", bakimciFirmaPersonel);
		}
		int sm = 0;
		for (RandevuMuhendisDTO rm : kontrol.getKontrolMuhendisleri()) {
			if (rm.getSorumlu().equals("E")) {
				parameter.put("muhendisSicilNo", rm.getSicilNo());
				parameter.put("muhendisAdiSoyadi", rm.getAdiSoyadi());
				parameter.put("muhendisUnvani",rm.getUnvan());
			}
			if (rm.getSorumlu().equals("H")) {
				switch (sm) {
				case 0:
					parameter.put("muhendisSicilNo1", rm.getSicilNo());
					parameter.put("muhendisAdiSoyadi1", rm.getAdiSoyadi());
					parameter.put("muhendisUnvani1",rm.getUnvan());
					sm++;
					break;
				case 1:
					parameter.put("muhendisSicilNo2", rm.getSicilNo());
					parameter.put("muhendisAdiSoyadi2", rm.getAdiSoyadi());
					parameter.put("muhendisUnvani2",rm.getUnvan());
					sm++;
					break;

				default:
					break;
				}
			}
		}

		parameter.put("onaylayanAdiSoyadi", kontrol.getOnaylayanAdiSoyadi());
		parameter.put("onaylayanSicilNo",
				String.valueOf(kontrol.getOnaylayanSicilNo()));

		String str = "";
		str = kontrol.getEtiket().equals("S") ? Messages._ETIKETSARI_
				.getMesaj() : str;
		str = kontrol.getEtiket().equals("Y") ? Messages._ETIKETYESIL_
				.getMesaj() : str;
		str = kontrol.getEtiket().equals("K") ? Messages._ETIKETKIRMIZI_
				.getMesaj() : str;

		Date birSonrakiKontrolTarihi = null;
		/*
		 * if (kontrol.getEtiket().equals("K")) { cal.set(Calendar.MONTH,
		 * cal.get(Calendar.MONTH) + 1);
		 * 
		 * } if (kontrol.getEtiket().equals("S")) { cal.set(Calendar.MONTH,
		 * cal.get(Calendar.MONTH) + 6);
		 * 
		 * } if (kontrol.getEtiket().equals("Y")) { cal.set(Calendar.MONTH,
		 * cal.get(Calendar.MONTH) + 12);
		 * 
		 * } birSonrakiKontrolTarihi = cal.getTime();
		 */
		if (!kontrol.getEtiket().equals("K")) {
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 12);
			birSonrakiKontrolTarihi = cal.getTime();

		}
		parameter.put("birSonrakiKontrolTarihi", birSonrakiKontrolTarihi);
		parameter.put("sonuc", str);

		/*
		 * Hidrolik Asansör Periyodik Kontrol Rapor Sorgusu ile yazıldı Bknz.
		 * Sayfa Sonu
		 */
		parameter.put("seriNo", "-");
		
		for (CihazTeknikDTO ctd : kontrol.getCihazTeknikBilgiler()) {
			ctd.setCevap(ctd.getCevap() == null ? "" : ctd.getCevap());
			switch (ctd.getDegerId()) {
			case 171:
				parameter.put("asansorunYeri", ctd.getCevap());
				break;
			case 173:
				parameter.put("montajFirmasi", ctd.getCevap());
				break;
			case 172:
				parameter.put("kapasiteKisi", ctd.getCevap());
				break;
			case 174:
				parameter.put("kapasiteAgirlik", ctd.getCevap());
				break;
			case 175:
				parameter.put("sinifi", ctd.getCevap());
				break;
			case 176:
				parameter.put("imalYili", ctd.getCevap());
				break;
			case 177:
				parameter.put("seriNo", ctd.getCevap());
				break;
			
			case 179:
				
				if (yeniRapor){
					double hizd=0.00;
					try{
					hizd=Double.parseDouble(ctd.getCevap().trim());
					}catch(Exception e){
						hizd=0.00;
					}
					parameter.put("hiz", hizd);
				}else
				parameter.put("hiz", ctd.getCevap());
				break;
				
			case 180:
				parameter.put("frenTipi", ctd.getCevap());
				break;
			case 181:
				parameter.put("askiTipi", ctd.getCevap());
				break;
			case 182:
				parameter.put("durakSayisi", ctd.getCevap());
				break;
			case 208:
				parameter.put("katSayisi", ctd.getCevap());
				break;
			case 196:
				parameter.put("seyirMesafesi", ctd.getCevap());
				break;
			case 202:
				parameter.put("kabinAlani", ctd.getCevap());
				break;
			case 183:
				parameter.put("kumandaTipi", ctd.getCevap());
				break;

			case 4:
				parameter.put("yonetmelikAdiTarihi", ctd.getCevap());
				break;

			case 184:
				parameter.put("onKurNo", ctd.getCevap());
				break;

			case 197:
				parameter.put("ceTseIsaret", ctd.getCevap());
				parameter.put("tseIsaret", ctd.getCevap());
				break;
			case 185:
				parameter.put("halatSayisi", ctd.getCevap());
				break;
			case 186:
				parameter.put("halatCapi", ctd.getCevap());
				break;
			case 187:
				parameter.put("pompaMotorGucu", ctd.getCevap());
				break;
			case 188:
				parameter.put("pompaMotorSeriNo", ctd.getCevap());
				break;

			case 3:
				parameter.put("hidrolikGrubuMarkaModel", ctd.getCevap());
				break;
			case 178:
				if (!yeniRapor)
				parameter.put("kapsam", ctd.getCevap());
				else
					kapsam+=kapsam.trim().length()>0?";"+ctd.getCevap():ctd.getCevap();
				break;
			
			case 189:
				String[] s=ctd.getCevap().split(";");
				String kapsam_="";
				if (s!=null && s.length>0){
					for (String s1:s){
						if (s1.indexOf("(")>=0){
						String ss=s1.substring(s1.indexOf("(")+1,s1.indexOf(")"));
						kapsam_+=kapsam_.trim().length()>0?";"+ss:ss;
						}
					}
				}
				if (!yeniRapor)
				parameter.put("ozelSartlar",kapsam_);
				else{
					kapsam+=kapsam.trim().length()>0?";"+kapsam_:kapsam_;
				}
				break;	
				
				
		
			case 200:
				parameter.put("ruhsatTescilBelgesi", ctd.getCevap());
				break;

			case 198:
				parameter.put("ruhsatTescilBelgesiTarihi", ctd.getCevap()
						.trim());
				break;

			case 199:
				parameter.put("ruhsatTescilBelgesiNo", ctd.getCevap());
				break;
			case 206:
				parameter.put("asansorCinsi", ctd.getCevap()
						.trim().equalsIgnoreCase("İNSAN")?"I":"Y");
				break;
			default:
				break;
			}

		}
		if (yeniRapor){
			parameter.put("kapsam", kapsam);
		}
		return parameter;

	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public void kontrolSil(KontrolDTO kontrol) {
		FacesMessage msg = new FacesMessage();
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			new RaporBusiness().isRaporVarMi(kontrol.getKontrolId());
			int randevuId = kontrol.getRandevuId();
			int basvuruId;
			basvuruId = new KontrolBusiness().getBasvuruId(randevuId);
			new KontrolBusiness().updateBasvuruAsansor(basvuruId,kontrol.getCihazId());
			new KontrolBusiness().kontrolSil(kontrol.getKontrolId());
			msg = new FacesMessage(Messages._KONTROLSILINDI_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			context.addMessage(null, msg);
			try {
				for (KontrolDTO k : kontrolList) {
					if (k.getKontrolId() == kontrol.getKontrolId()) {
						kontrolList.remove(k);
						break;
					}
				}
			} catch (Exception e) {

			}

		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
		}

	}

	public BinaBean getBinaBean() {
		return binaBean;
	}

	public void setBinaBean(BinaBean binaBean) {
		this.binaBean = binaBean;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	
	

}

/*
 * ELEKTRİKLİ ASANSÖR PERİYODİK KONTROL RAPORUNDA KULLANILAN CİHAZ PARAMETRELERİ
 * SORGUSU select a.kod,a.sira,a.baslik,b.deger_id,b.tipi,b.deger,b.birim from
 * test.cihaz_turu_teknik_ozellikleri a,test.cihaz_turu_teknik_ozellik_deger b
 * where a.cihaz_turu_kod=66 and a.kod=b.teknik_ozellik_kod order by sira
 * 
 * 1602;1;"Asansörün Yeri";151;"metin";"";"" 1603;3;"Kapasite";152;"coklu";
 * "1 kişi;2 kişi;3 kişi;4 kişi;5 kişi;6 kişi;7 kişi;8 kişi;9 kişi;10 kişi;11 kişi;12 kişi;13 kişi;14 kişi;15 kişi;16 kişi;17 kişi;18 kişi;19 kişi;20 kişi;21 kişi;22 kişi;23 kişi;24 kişi;25 kişi;26 kişi;40 kişi;42 kişi;86 kişi"
 * ;"KİŞİ" 1604;7;"Asansör Seri No";153;"metin";"";""
 * 1605;6;"İmal Yılı";154;"sayi";"";"YIL"
 * 1606;5;"Sınıfı";155;"coklu";"I;II;III;IV;V;VI";""
 * 1607;20;"Özel Asansör Şartlar";156;"coklu";
 * " ;Erişim (Engelli kullanımına uygunluk) (EN 81-70);Kasıtlı tahribatlara karşı tedbirler (EN 81-71);Yangın durumunda çalışmaya yönelik tedbirler (EN 81-73);İtfaiyeci asansörü (EN 81-72)"
 * ;"" 1608;8;"Durak Sayısı";157;"sayi";"";""
 * 1609;4;"Kapasite";158;"sayi";"";"kg"
 * 1610;2;"Montaj Firması";159;"metin";"";"" 1611;10;"Hız";160;"sayi";"";"m/s"
 * 1612;11;"Motor Gücü";161;"sayi";"";""
 * 1613;12;"Motor Seri No";162;"metin";"";""
 * 1614;13;"Fren Tipi";163;"coklu";"ANİ FREN;ANİ TAMPON ETKİLİ FREN;KAYMALI FREN"
 * ;"" 1615;14;"Makina Seri No";164;"metin";"";""
 * 1616;15;"Onaylanmış Kuruluş Kimlik No";165;"sayi";"";""
 * 1617;9;"Kapsam";166;"coklu";"TS 863;TS 10922;TS EN 81-1";""
 * 1618;16;"Askı Tipi";167;"coklu";"1:1;1:2;1:3;1:4";""
 * 1619;17;"Halat Sayısı";168;"coklu";
 * "2 adet;3 adet;4 adet;5 adet;6 adet;7 adet;8 adet;9 adet;10 adet;11 adet;12 adet;13 adet;14 adet;15 adet;16 adet;17 adet;18 adet;19 adet;20 adet;Kayışlı"
 * ;"adet" 1620;18;"Halat Ölçüsü";169;"coklu";
 * "6 mm;6,7 mm;8 mm;9 mm;10 mm;11 mm;12 mm;13 mm;14 mm;15 mm;16 mm;18 mm;19 mm;20 mm;22 mm;Kayışlı"
 * ;"mm"
 * 1621;19;"Kumanda Tipi";170;"coklu";"BASİT;TOPLAMALI;DUBLEKS;TRİPLEKS;DÖRTLÜ GRUP"
 * ;"" 1641;11;"Motor Gücü Birimi";190;"coklu";"HP;kW";""
 * 1642;21;"Ruhsat/Tescil Belgesi";191;"coklu";"VAR;YOK;ULAŞILAMIYOR";""
 * 1643;22;"Ruhsat No/Tescil No";192;"sayi";"";""
 * 1644;23;"Ruhsat/Tescil Tarihi";193;"sayi";"";""
 * 1645;15;"CE İşareti/TSE İşareti";194;"coklu";"VAR;YOK";""
 * 1646;8;"Seyir Mesafesi";195;"sayi";"";"m"
 * 1652;8;"Kabin Alanı";201;"sayi";"";"cm ((100x90) cm gibi))"
 */

/*
 * HİDROLİK ASANSÖR PERİYODİK KONTROL RAPORUNDA KULLANILAN CİHAZ PARAMETRELERİ
 * SORGUSU select a.kod,a.sira,a.baslik,b.deger_id,b.tipi,b.deger,b.birim from
 * test.cihaz_turu_teknik_ozellikleri a,test.cihaz_turu_teknik_ozellik_deger b
 * where a.cihaz_turu_kod=68 and a.kod=b.teknik_ozellik_kod order by sira
 * 
 * 1622;1;"Asansörün Yeri";171;"metin";"";""
 * 1624;2;"Montaj Firması";173;"metin";"";"" 1623;3;"Kapasite";172;"coklu";
 * "1 kişi;2 kişi;3 kişi;4 kişi;5 kişi;6 kişi;7 kişi;8 kişi;9 kişi;10 kişi;11 kişi;12 kişi;13 kişi;14 kişi;15 kişi;16 kişi;17 kişi;18 kişi;19 kişi;20 kişi;21 kişi;22 kişi;23 kişi;24 kişi;25 kişi;26 kişi;27 kişi;28 kişi;29 kişi;30 kişi;40 kişi;42 kişi;86 kişi"
 * ;"kişi" 1625;4;"Kapasite";174;"sayi";"";"kg"
 * 1626;5;"Sınıfı";175;"coklu";"I;II;III;IV;V";""
 * 1627;6;"İmal Yılı";176;"sayi";"";"yıl"
 * 1628;7;"Asansör Seri No";177;"metin";"";""
 * 1629;8;"Kapsam";178;"coklu";"TS EN 81-2";"" 1630;9;"Hızı";179;"sayi";"";"m/s"
 * 1631
 * ;10;"Fren Tipi";180;"coklu";"Ani Fren;Ani Tampon Etkili Fren;Kaymalı Fren";""
 * 1632;11;"Askı Tipi";181;"coklu";"1:1;1:2;1:3;1:4";""
 * 1653;12;"Kabin Alanı";202;"sayi";"";"cm ((100x90) cm gibi.))"
 * 1633;12;"Durak Sayısı";182;"sayi";"";"durak"
 * 1647;12;"Seyir Mesafesi";196;"sayi";"";"m"
 * 1634;13;"Kumanda Tipi";183;"coklu";"Basit;Toplamalı;Dubleks;Tripleks";""
 * 1635;14;"Onaylanmış Kuruluş Kimlik No";184;"sayi";"";""
 * 1648;14;"CE/TSE İşareti";197;"coklu";"VAR;YOK";""
 * 1636;15;"Halat Sayısı";185;"coklu";
 * "2 adet;3 adet;4 adet;5 adet;6 adet;7 adet;8 adet;10 adet;11 adet;12 adet;13 adet;14 adet;2x3 adet;2x4 adet;2x5 adet;2x6 adet;2x7 adet;2x8 adet"
 * ;"adet" 1637;16;"Halat Çapı";186;"coklu";
 * "6 mm;6,5 mm;7 mm;8 mm;9 mm;10 mm;11 mm;12 mm;13 mm;14 mm; 15 mm;16 mm;17 mm;18 mm;19 mm;20 mm"
 * ;"mm" 1638;17;"Pompa Motor Gücü";187;"sayi";"";"kW"
 * 1654;18;"Pompa Motor Gücü Birimi";203;"coklu";"kW;HP;BG";""
 * 1639;19;"Pompa Motor Seri No";188;"metin";"";""
 * 1640;20;"Özel Asansör Şartlar";189;"coklu";
 * " ;Erişim (Engelli kullanımına uygunluk) (EN 81-70);Kasıtlı tahribatlara karşı tedbirler (EN 81-71);Yangın durumunda çalışmaya yönelik tedbirler (EN 81-73);İtfaiyeci asansörü (EN 81-72)"
 * ;"" 1651;21;"Ruhsat/Tescil Belgesi";200;"coklu";"VAR;YOK;ULAŞILAMIYOR";""
 * 1650;22;"Ruhsat/Tescil No";199;"sayi";"";""
 * 1649;23;"Ruhsat/Tescil Tarihi";198;"sayi";"";""
 */