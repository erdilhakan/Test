package tr.org.mmo.asansor.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;

import tr.org.mmo.asansor.beans_.ReferansDenetimKayitKontrolSorularListesi;
import tr.org.mmo.asansor.business.AsansorBusiness;
import tr.org.mmo.asansor.business.BelediyeBusiness;
import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.business.KontrolBusiness;
import tr.org.mmo.asansor.business.RaporBusiness;
import tr.org.mmo.asansor.business.SoruListBusiness;
import tr.org.mmo.asansor.business.WebServiceBusiness;
import tr.org.mmo.asansor.dto.BelediyeDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.BinaKisiDTO;
import tr.org.mmo.asansor.dto.CihazDTO;
import tr.org.mmo.asansor.dto.CihazFirmaDTO;
import tr.org.mmo.asansor.dto.CihazTeknikDTO;
import tr.org.mmo.asansor.dto.DenetimKayitDTO;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.dto.KontrolDTO;
import tr.org.mmo.asansor.dto.KontrolTestDTO;
import tr.org.mmo.asansor.dto.OnTanimliTestDTO;
import tr.org.mmo.asansor.dto.RaporDTO;
import tr.org.mmo.asansor.dto.RaporKayitDTO;
import tr.org.mmo.asansor.dto.ReferansDenetimSorularEslestirmeDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.managedbeans.BelediyeBean;
import tr.org.mmo.asansor.managedbeans.CihazBean;
import btest.*;


public class DenetimKayitUtils {
	private List<ReferansDenetimKayitKontrolSorularListesi> referansDenetimKayitKontrolSorularListesi=new ArrayList<ReferansDenetimKayitKontrolSorularListesi>();
	private List<ReferansDenetimSorularEslestirmeDTO> referansDenetimSorularEslestirmeListesi=new ArrayList<ReferansDenetimSorularEslestirmeDTO>();
	
	private List<ReferansDenetimKayitKontrolSorularListesi> referansDenetimKayitKontrolSorularListesi15092015Sonrasi=new ArrayList<ReferansDenetimKayitKontrolSorularListesi>();
	private List<ReferansDenetimSorularEslestirmeDTO> referansDenetimSorularEslestirmeListesi15092015Sonrasi=new ArrayList<ReferansDenetimSorularEslestirmeDTO>();
	private Map<String,String> referansSoruSkalaMap=new HashMap<String, String>();
	public DenetimKayitUtils() {
		referansSoruSkalaMap=new HashMap<String, String>();
		referansSoruSkalaMap.put("Uygun","1");
		referansSoruSkalaMap.put("Uygun Degil", "2");
		referansSoruSkalaMap.put("Uygulanamaz", "3");
		try {
			referansDenetimKayitKontrolSorularListesi=referansDenetimKayitKontrolSorularListesi.size()<=0?new SoruListBusiness().getReferansDenetimKayitKontrolSorulari():referansDenetimKayitKontrolSorularListesi;
			referansDenetimSorularEslestirmeListesi=referansDenetimSorularEslestirmeListesi.size()<=0?new SoruListBusiness().getReferansDenetimKayitEslestirme():referansDenetimSorularEslestirmeListesi;
			
				referansDenetimSorularEslestirmeListesi15092015Sonrasi=new WebServiceBusiness().referansDenetimSorularEslestirmeListesiGetir();
				referansDenetimKayitKontrolSorularListesi15092015Sonrasi=new WebServiceBusiness().referansDenetimKayitKontrolSorularListesiGetir();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean batchAsansorDenetimKayit(RaporDTO rapor) {

		DenetimKayit denetimKayit = new DenetimKayit();
		boolean denetimKayitYapildi=false;
		KontrolDTO kontrol=new KontrolDTO();
		boolean asansorDurum=false;
		try {

			kontrol = new KontrolBusiness()
					.getCihazKontrolByKontrolId(rapor.getKontrolId());
		
			
		} catch (CRUDException e) {
			DenetimKayitDTO denetimKayitLog=new DenetimKayitDTO();
			denetimKayitLog.setRaporId(rapor.getRaporId());
			denetimKayitLog.setHataKodu(9999);
			denetimKayitLog.setHataMesaj(e.getMessage());
			setDenetimKayitLog(denetimKayitLog);
		}
			if (kontrol!=null && kontrol.getKontrolId()>0){
				if (kontrol.getBina().getUavtKod()<=0){
					DenetimKayitDTO denetimKayitLog=new DenetimKayitDTO();
					denetimKayitLog.setRaporId(rapor.getRaporId());
					try{
					denetimKayitLog.setHataKodu(Integer.parseInt(Messages._BINAUAVTKODBOS_.getMesajNo()));
					}catch(Exception e){
						denetimKayitLog.setHataKodu(8888);	
					}
					denetimKayitLog.setHataMesaj(Messages._BINAUAVTKODBOS_.getMesaj());
					setDenetimKayitLog(denetimKayitLog);
				}else{
					if (kontrol.getCihaz().getUavtKod()<= 0) {
						asansorDurum=true;
						int maxAsansorSiraNo=1;
						try{
						maxAsansorSiraNo=new CihazBean().binaBagliAsansorSorgulama(kontrol.getBina().getUavtKod());
						
						ServisSonucOfAsansorKimlikKodlar8Zb117HL  servisSonucAsansorKimlikKodlar=new ServisSonucOfAsansorKimlikKodlar8Zb117HL();
						servisSonucAsansorKimlikKodlar=		new WebServiceBusiness().binaAsansorKayit(kontrol.getBina().getUavtKod(), maxAsansorSiraNo);
						
						if (!servisSonucAsansorKimlikKodlar.isHata()){
							kontrol.getCihaz().setUavtKod(servisSonucAsansorKimlikKodlar.getSonuc().getValue().getAsansorNo());
							kontrol.getCihaz().setUavtEtiket(servisSonucAsansorKimlikKodlar.getSonuc().getValue().getAsansorEtiket().getValue());
							kontrol.getCihaz().setUavtSiraNo(servisSonucAsansorKimlikKodlar.getSonuc().getValue().getAsansorSiraNo().getValue().intValue());
							try{
							new AsansorBusiness().uavtKodEkle(kontrol.getCihaz());
							}
							catch (CRUDException e) {
								DenetimKayitDTO denetimKayitLog=new DenetimKayitDTO();
								denetimKayitLog.setRaporId(rapor.getRaporId());
								denetimKayitLog.setHataKodu(9999);
								denetimKayitLog.setHataMesaj(e.getMessage());
								setDenetimKayitLog(denetimKayitLog);
							}
							
						}else{
							DenetimKayitDTO denetimKayitLog=new DenetimKayitDTO();
							denetimKayitLog.setRaporId(rapor.getRaporId());
							denetimKayitLog.setHataKodu(servisSonucAsansorKimlikKodlar.getHataKodu());
							denetimKayitLog.setHataMesaj(servisSonucAsansorKimlikKodlar
									.getMesaj().getValue().toString());
							setDenetimKayitLog(denetimKayitLog);
						}
					}
					
					catch (Exception e) {
						DenetimKayitDTO denetimKayitLog=new DenetimKayitDTO();
						denetimKayitLog.setRaporId(rapor.getRaporId());
						denetimKayitLog.setHataKodu(9999);
						denetimKayitLog.setHataMesaj(e.getMessage());
						setDenetimKayitLog(denetimKayitLog);
					}
					
				}
					if (kontrol.getCihaz().getUavtKod()>0) {
						BelediyeDTO belediye = new BelediyeDTO();
						try {
							belediye = new BelediyeBusiness().getBelediyeByKod(kontrol
									.getBina().getBelediye());
							
						} catch (CRUDException e) {
							belediye = new BelediyeDTO();
						}
						if (belediye.getUavtKod()<=0){
							try{
							belediye.setUavtKod(new BelediyeBean().ileGoreBelediyeSorgulama(belediye));
							new BelediyeBusiness().belediyeUavtEkle(belediye);
							}catch(CRUDException e){
								e.printStackTrace();
							}catch(Exception e){
								DenetimKayitDTO denetimKayitLog=new DenetimKayitDTO();
								denetimKayitLog.setRaporId(rapor.getRaporId());
								denetimKayitLog.setHataKodu(2222);
								denetimKayitLog.setHataMesaj(e.getMessage());
								setDenetimKayitLog(denetimKayitLog);
							}
						
						
						}
						if (belediye.getUavtKod()>0){
							RaporKayitDTO kayitRapor=new RaporKayitDTO();
							try{
							kayitRapor= new KontrolBusiness().checkDenetimKayit(kontrol.getKontrolId(),kontrol.getRandevuId(),kontrol.getCihazId(),kontrol.isRevizyonRapor());
							}catch(CRUDException e){
								kayitRapor=new RaporKayitDTO();
							}
							ObjectFactory factory = new ObjectFactory();

							ObjectFactory objectFactory = new ObjectFactory();
							List<KontrolTestDTO> testYanitList = new ArrayList<KontrolTestDTO>();
							try {
								testYanitList = new SoruListBusiness().getTestYanitlari(rapor
										.getKontrolId());
							} catch (CRUDException e) {
								testYanitList=new ArrayList<KontrolTestDTO>();
							}
							denetimKayit.setAsansorNo(kontrol.getCihaz().getUavtKod());
							
							/*******************yeni eklendi * (16.09.2015)****************************/
							
							denetimKayit.setPeriyodikKontrolTipi(objectFactory.createDenetimKayitPeriyodikKontrolTipi(kontrol.getKontrolTuru().equals("N")?1:2));
							Calendar kontrolTarihi = Calendar.getInstance();
							kontrolTarihi.setTime(kontrol.getKontrolBaslangicTarihi());
							Calendar kontrolSaat=Calendar.getInstance();
							kontrolSaat.setTime(kontrol.getKontrolBaslangicSaati());
							kontrolTarihi.set(Calendar.HOUR,kontrolSaat.get(Calendar.HOUR));
							kontrolTarihi.set(Calendar.HOUR_OF_DAY,kontrolSaat.get(Calendar.HOUR_OF_DAY));
							kontrolTarihi.set(Calendar.MINUTE,kontrolSaat.get(Calendar.MINUTE));
							kontrolTarihi.set(Calendar.SECOND,kontrolSaat.get(Calendar.SECOND));
							try {
								denetimKayit.setDenetimTarih(DateUtils
										.CalendarToXMLGregorianCalendar(kontrolTarihi));
							} catch (DatatypeConfigurationException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							}
							
							
							kontrolTarihi.setTime(kontrol.getKontrolBitisTarihi());
							
							kontrolSaat.setTime(kontrol.getKontrolBitisSaati());
							kontrolTarihi.set(Calendar.HOUR,kontrolSaat.get(Calendar.HOUR));
							kontrolTarihi.set(Calendar.HOUR_OF_DAY,kontrolSaat.get(Calendar.HOUR_OF_DAY));
							kontrolTarihi.set(Calendar.MINUTE,kontrolSaat.get(Calendar.MINUTE));
							kontrolTarihi.set(Calendar.SECOND,kontrolSaat.get(Calendar.SECOND));
							try {
								denetimKayit.setDenetimBitis(objectFactory.createDenetimKayitDenetimBitis(DateUtils
										.CalendarToXMLGregorianCalendar(kontrolTarihi)));
							} catch (DatatypeConfigurationException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							}
							/***************************************************************************/
							
							
							 kontrolTarihi = Calendar.getInstance();
							kontrolTarihi.setTime(kontrol.getKontrolBitisTarihi());
							denetimKayit.setDenetimYil(kontrolTarihi.get(Calendar.YEAR));
							/******************************** Time Eklendi yukarıda yeni eklenenler içerisinde (16.09.2015) *******************
							
							denetimKayit.setDenetimTarih(DateUtils
									.CalendarToXMLGregorianCalendar(kontrolTarihi));
							***************************************************************************/
							
							
							

							DenetimKayitDetayAsansorBilgi denetimKayitDetayAsansorBilgi = new DenetimKayitDetayAsansorBilgi();
							denetimKayitDetayAsansorBilgi = setDenetimKayitAsansorBilgi(
									kontrol.getCihaz(), kontrol.getCihazTeknikBilgiler(),
									kontrol.getBina(),asansorDurum);
							ArrayOfDenetimKayitDetayAsansorBilgi arrayOfDenetimKayitDetayAsansorBilgi = new ArrayOfDenetimKayitDetayAsansorBilgi();
							arrayOfDenetimKayitDetayAsansorBilgi
									.getDenetimKayitDetayAsansorBilgi().add(
											denetimKayitDetayAsansorBilgi);

							denetimKayit
									.setDenetimKayitDetayAsansorBilgi(objectFactory.createDenetimKayitDenetimKayitDetayAsansorBilgi(arrayOfDenetimKayitDetayAsansorBilgi));
							DenetimKayitDetayBakimSozlesme denetimKayitDetayBakimSozlesme = new DenetimKayitDetayBakimSozlesme();
							denetimKayitDetayBakimSozlesme = setDenetimKayitDetayBakimSozlesme(
									kontrol.getFirma(), kontrol.getCihaz());
							ArrayOfDenetimKayitDetayBakimSozlesme arrayOfDenetimKayitDetayBakimSozlesme = new ArrayOfDenetimKayitDetayBakimSozlesme();
							arrayOfDenetimKayitDetayBakimSozlesme
									.getDenetimKayitDetayBakimSozlesme().add(
											denetimKayitDetayBakimSozlesme);
							denetimKayit
									.setDenetimKayitDetayBakimSozlesme(objectFactory.createDenetimKayitDenetimKayitDetayBakimSozlesme(arrayOfDenetimKayitDetayBakimSozlesme));
							
							DenetimKayitDetayBinaSorumlusuBilgileri denetimKayitDetayBinaSorumluBilgileri = new DenetimKayitDetayBinaSorumlusuBilgileri();
							denetimKayitDetayBinaSorumluBilgileri = setDenetimKayitDetayBinaSorumlusuBilgileri(
									kontrol.getBinaKisiList().size() > 0 ? kontrol
											.getBinaKisiList().get(0) : new BinaKisiDTO(),
									kontrol.getBina());
							ArrayOfDenetimKayitDetayBinaSorumlusuBilgileri arrayOfDenetimKayitDetayBinaSorumlusuBilgileri = new ArrayOfDenetimKayitDetayBinaSorumlusuBilgileri();
							denetimKayit
									.setDenetimKayitDetayBinaSorumlusuBilgileri(objectFactory.createDenetimKayitDenetimKayitDetayBinaSorumlusuBilgileri(arrayOfDenetimKayitDetayBinaSorumlusuBilgileri));
							DenetimKayitDetayFirmaBilgi denetimKayitDetayFirmaBilgi = new DenetimKayitDetayFirmaBilgi();
							denetimKayitDetayFirmaBilgi = setDenetimKayitDetayFirmaBilgi(kontrol
									.getFirma(),kontrol.getCihaz().getCihazId(),kontrol.getKontrolFirmaKatilimDTO()==null?false:kontrol.getKontrolFirmaKatilimDTO().isFirmaKontroleKatildi());
							ArrayOfDenetimKayitDetayFirmaBilgi arrayOfDenetimKayitDetayFirmaBilgi = new ArrayOfDenetimKayitDetayFirmaBilgi();
							arrayOfDenetimKayitDetayFirmaBilgi.getDenetimKayitDetayFirmaBilgi()
									.add(denetimKayitDetayFirmaBilgi);
							denetimKayit
									.setDenetimKayitDetayFirmaBilgi(objectFactory.createDenetimKayitDenetimKayitDetayFirmaBilgi(arrayOfDenetimKayitDetayFirmaBilgi));
							DenetimKayitDetayTechizatBilgi denetimKayitDetayTechizatBilgi = new DenetimKayitDetayTechizatBilgi();
							denetimKayit.setDenetimKayitDetayTechizatBilgi(null);
							List<DenetimKayitDetaySoru> denetimKayitDetaySoru = new ArrayList<DenetimKayitDetaySoru>();
//							try {
//								denetimKayit.setMuayeneEsasAlinacakStandard(new AsansorBusiness().getCihazKapsamId(kontrol.getCihaz()));
//							} catch (CRUDException e1) {
//								denetimKayit.setMuayeneEsasAlinacakStandard(0);
//							}
							int kapsam=0;
							try {
								kapsam=new AsansorBusiness().getCihazKapsamId(kontrol.getCihaz());
							} catch (ReadException e2) {
								kapsam=0;
							}
								
							if (kapsam>0){
								boolean yeniRaporMu=false;
								try {
									yeniRaporMu=kontrol.getKontrolBaslangicTarihi().compareTo(new SimpleDateFormat().parse("2015-09-14"))>=0?true:false;
								} catch (ParseException e) {
									
								}
							try {
								denetimKayitDetaySoru = setDenetimKayitDetaySoru(testYanitList,
										kontrol.getCihaz().getTip(),kapsam,yeniRaporMu);
							} catch (CRUDException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}}
							ArrayOfDenetimKayitDetaySoru arrayOfDenetimKayitDetaySoru = new ArrayOfDenetimKayitDetaySoru();
							for (DenetimKayitDetaySoru d : denetimKayitDetaySoru) {
								arrayOfDenetimKayitDetaySoru.getDenetimKayitDetaySoru().add(d);
							}
							denetimKayit
									.setDenetimKayitDetaySoru(objectFactory.createDenetimKayitDenetimKayitDetaySoru(arrayOfDenetimKayitDetaySoru));

							denetimKayit.setBasvuruKayitNo(objectFactory.createDenetimKayitBasvuruKayitNo(kontrol
									.getRandevuId()));
							
							DenetimKayitSonuc denetimKayitSonuc = new DenetimKayitSonuc();
							denetimKayitSonuc = setDenetimKayitSonuc(kontrol);
							if (kayitRapor.getDenetimKayitId()>0){
							denetimKayitSonuc.setDenetimKayitId(kayitRapor.getDenetimKayitId());
							}
							ArrayOfDenetimKayitSonuc arrayOfDenetimKayitSonuc = new ArrayOfDenetimKayitSonuc();
							arrayOfDenetimKayitSonuc.getDenetimKayitSonuc().add(
									denetimKayitSonuc);
							denetimKayit.setDenetimKayitSonuc(objectFactory.createDenetimKayitDenetimKayitSonuc(arrayOfDenetimKayitSonuc));
							Calendar denetimTarih = Calendar.getInstance();
							denetimTarih.setTime(kontrol.getKontrolBitisTarihi());
							try {
								denetimKayit.setDenetimTarih(DateUtils
										.CalendarToXMLGregorianCalendar(denetimTarih));
							} catch (DatatypeConfigurationException e1) {
								denetimKayit.setDenetimTarih(null);
							}
							denetimKayit.setDurum(true);
							
						

						
							
							denetimKayit
									.setMuayeneKisiAdSoyad(objectFactory.createDenetimKayitMuayeneKisiAdSoyad(kontrol
											.getKontrolMuhendisleri().size() > 0 ? kontrol
											.getKontrolMuhendisleri().get(0).getAdiSoyadi()
											: " "));
							denetimKayit.setMuayeneKisiUnvan(objectFactory.createDenetimKayitMuayeneKisiUnvan("Mühendis"));
							
							denetimKayit.setProtokolImzalayanBelediye(objectFactory.createDenetimKayitProtokolImzalayanBelediye(belediye.getUavtKod()));
						
							ServisSonucOfDenetimKayitSonuc8Zb117HL servisSonucOfDenetimKayitSonuc = new ServisSonucOfDenetimKayitSonuc8Zb117HL();
						
							
							if (kayitRapor.getDenetimKayitId()>0){
								try{
								servisSonucOfDenetimKayitSonuc=new WebServiceBusiness().asansorDenetimKayitGuncelle(denetimKayit);
								}catch(Exception ex){
									System.out.println("Exception encountered: " + ex.toString());
							          final Throwable[] suppressedExceptions = ex.getSuppressed();
							          final int numSuppressed = suppressedExceptions.length;
							          if (numSuppressed > 0)
							          {
							        	  System.out.println("tThere are " + numSuppressed + " suppressed exceptions:");
							              for (final Throwable exception : suppressedExceptions)
							              {
							            	  System.out.println("tt" + exception.toString());
							              }
							          }
							      
									DenetimKayitDTO denetimKayitLog=new DenetimKayitDTO();
									denetimKayitLog.setRaporId(rapor.getRaporId());
									denetimKayitLog.setHataKodu(9999);
									denetimKayitLog.setHataMesaj(ex.getMessage());
									setDenetimKayitLog(denetimKayitLog);
								}
								
							}else{
								try{
									
 						servisSonucOfDenetimKayitSonuc = new WebServiceBusiness()
 									.asansorDenetimKayit(denetimKayit);
							}catch(Exception ex){
								System.out.println("Exception encountered: " + ex.toString());
						          final Throwable[] suppressedExceptions = ex.getSuppressed();
						          final int numSuppressed = suppressedExceptions.length;
						          if (numSuppressed > 0)
						          {
						        	  System.out.println("tThere are " + numSuppressed + " suppressed exceptions:");
						              for (final Throwable exception : suppressedExceptions)
						              {
						            	  System.out.println("tt" + exception.toString());
						              }
						          }
						      
								DenetimKayitDTO denetimKayitLog=new DenetimKayitDTO();
								denetimKayitLog.setRaporId(rapor.getRaporId());
								denetimKayitLog.setHataKodu(9999);
								denetimKayitLog.setHataMesaj(ex.getMessage());
								setDenetimKayitLog(denetimKayitLog);
							}
							}

							if (!servisSonucOfDenetimKayitSonuc.isHata()) {
								try{
								if (kayitRapor.getDenetimKayitId()>0){
									new RaporBusiness().raporKayitSil(kayitRapor);
								}
								RaporKayitDTO raporKayit = new RaporKayitDTO();
								raporKayit.setDenetimDurumId(servisSonucOfDenetimKayitSonuc
										.getSonuc().getValue().getDenetimDurumId());
								raporKayit.setDenetimKayitId(servisSonucOfDenetimKayitSonuc
										.getSonuc().getValue().getDenetimKayitId());
								raporKayit.setCihazId(kontrol.getCihazId());
								raporKayit.setRaporId(rapor.getRaporId());
								new RaporBusiness().raporKayitKaydet(raporKayit);
								}catch(CRUDException e){
									e.printStackTrace();
								}
								denetimKayitYapildi=true;

							} else {
								DenetimKayitDTO denetimKayitLog=new DenetimKayitDTO();
								denetimKayitLog.setRaporId(rapor.getRaporId());
								denetimKayitLog.setHataKodu(servisSonucOfDenetimKayitSonuc.getHataKodu());
								denetimKayitLog.setHataMesaj(servisSonucOfDenetimKayitSonuc
										.getMesaj().getValue().toString());
								setDenetimKayitLog(denetimKayitLog);
								
							}
							
							
						}
					
					
				
			}
			}
			}
		return denetimKayitYapildi;
	}
	
	protected void setDenetimKayitLog(DenetimKayitDTO denetimKayit){
		try{
			new RaporBusiness().setDenetimKayitLog(denetimKayit);
		}catch(CRUDException e){
			e.printStackTrace();
		}
	}
	
	private DenetimKayitDetayAsansorBilgi setDenetimKayitAsansorBilgi(
			CihazDTO cihaz, List<CihazTeknikDTO> cihazTeknikBilgiler,
			BinaDTO bina,boolean asansorDurum) {
		DenetimKayitDetayAsansorBilgi denetimKayitDetayAsansorBilgi = new DenetimKayitDetayAsansorBilgi();
		
		ObjectFactory objectFactory = new ObjectFactory();
		denetimKayitDetayAsansorBilgi.setKimlikNumarasi(objectFactory.createDenetimKayitDetayAsansorBilgiKimlikNumarasi(String.valueOf(cihaz.getUavtKod())));
		//denetimKayitDetayAsansorBilgi.setKimlikNumarasi(objectFactory.createAnyURI(String.valueOf(cihaz.getUavtKod())));
		System.out.println(cihaz.getUavtEtiket());
		denetimKayitDetayAsansorBilgi.setAsansorTuru(cihaz.getTip() == 66 ? 2
				: 1);
		/***********************************(16.09.2015) Yeni Eklendi *************************/
		
		denetimKayitDetayAsansorBilgi.setAsansorDurumu(objectFactory.createDenetimKayitDetayAsansorBilgiAsansorDurumu(false));
		
		
		/****************************************************************************************/
		String makinaMotorSeriNo="";
		for (CihazTeknikDTO ctd : cihazTeknikBilgiler) {
			if (cihaz.getTip() == 66) {
				switch (ctd.getDegerId()) {
				case 1: // makinaMarkaTip
					denetimKayitDetayAsansorBilgi
							.setMarkaTipModel(objectFactory.createDenetimKayitDetayAsansorBilgiMarkaTipModel( ctd
									.getCevap()));
//					denetimKayitDetayAsansorBilgi
//					.setMarkaTipModel(objectFactory.createString(ctd
//							.getCevap()));
					break;
					
				case 164: //Makina Seri Numarası
					makinaMotorSeriNo=makinaMotorSeriNo+" "+ctd.getCevap()==null?"":ctd.getCevap().trim();
					
					break;
				case 2: // yonetmelikAdiTarihi

					break;
				case 151: // asansörün yeri

					break;
				case 152: // kapasite kisi

					break;
				case 153: // seri no

					denetimKayitDetayAsansorBilgi.setSeriNo(objectFactory.createDenetimKayitDetayAsansorBilgiSeriNo(ctd.getCevap()));

					break;
				case 158:// kapasiteAgirlik
					try {
						denetimKayitDetayAsansorBilgi.setBeyanYuku(Integer
								.parseInt(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setBeyanYuku(0);
					}
					break;
				case 155: // sinifi

					break;
				case 154: // imal yılı
					try {
						denetimKayitDetayAsansorBilgi.setMontajYil(Integer
								.parseInt(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setMontajYil(0);
					}
					break;
				case 160:
					try {
						denetimKayitDetayAsansorBilgi.setBeyanHiz(new BigDecimal(ctd.getCevap() == null ? "0" : ctd
								.getCevap()));
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setBeyanHiz(new BigDecimal(0));
					}
					break;
				case 161: // motor gücü

					break;
				case 190: // motor gücü birimi

					break;
				case 162: // motor seri no
					makinaMotorSeriNo=makinaMotorSeriNo+" "+ctd.getCevap()==null?"":ctd.getCevap().trim();
					
					break;
				
				case 194: // ce tse işaret
					break;
				case 167: // askı tipi

					break;
				case 168: // halat sayısı

					break;
				case 169: // halat çapı

					break;
				case 165: // onaylanmış kuruluş no

					break;
				case 157:// durakSayisi
					try {
						denetimKayitDetayAsansorBilgi.setDurakSayi(Integer
								.parseInt(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
						denetimKayitDetayAsansorBilgi.setKatSayisi(Integer
								.parseInt(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setDurakSayi(0);
						denetimKayitDetayAsansorBilgi.setKatSayisi(0);
					}
					break;
				case 195: // seyir mesafesi
					try {
						denetimKayitDetayAsansorBilgi.setSeyirMesafesi(objectFactory.createDenetimKayitDetayAsansorBilgiSeyirMesafesi(ctd.getCevap() == null ?new BigDecimal(0) : new BigDecimal(ctd.getCevap().trim())));
								
						
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setSeyirMesafesi(objectFactory.createDenetimKayitDetayAsansorBilgiSeyirMesafesi(new BigDecimal(0)));
					}
					break;
					

				case 209: // tanimi
					try {
						
						denetimKayitDetayAsansorBilgi.setMakinaMakaraDaireDurumu(objectFactory.createDenetimKayitDetayAsansorBilgiMakinaMakaraDaireDurumu(ctd.getCevap()==null?false:ctd.getCevap().trim().equalsIgnoreCase("MDRL")?true:false));
								
						
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setMakinaMakaraDaireDurumu(objectFactory.createDenetimKayitDetayAsansorBilgiMakinaMakaraDaireDurumu(false));
					}
					break;
				case 163: // fren tipi

					break;
				case 170: // kumanda tipi

					break;
				case 159: // montaj firması

					break;
				case 201: // kabin alanı

					break;
				case 166: // kapsam

					break;
				case 156: // özel şartlar
					break;
				case 191: // ruhsat tescil belgesi
					denetimKayitDetayAsansorBilgi.setTescilBelgesi(ctd
							.getCevap().equals("VAR") ? true : false);

					break;
				case 193: // ruhsat tescil belgesi tarihi
					Calendar cal = Calendar.getInstance();
					try {
						DateFormat df=new SimpleDateFormat("dd.MM.yyyy");
						cal.setTime(df
								.parse(ctd.getCevap() == null ? "01.01.0001" : ctd
										.getCevap().trim()));
						try {
							denetimKayitDetayAsansorBilgi
									.setTescilTarihi(DateUtils
											.CalendarToXMLGregorianCalendar(cal));
							denetimKayitDetayAsansorBilgi
							.setRuhsatSicilTarih(objectFactory.createDenetimKayitDetayAsansorBilgiRuhsatSicilTarih(DateUtils
									.CalendarToXMLGregorianCalendar(cal)));
						} catch (DatatypeConfigurationException e) {
							Calendar cal1=Calendar.getInstance();
							cal1.set(Calendar.YEAR, 1);
							cal1.set(Calendar.MONTH, 1);
							cal1.set(Calendar.DAY_OF_YEAR, 1);
							try {
								denetimKayitDetayAsansorBilgi.setTescilTarihi(DateUtils
										.CalendarToXMLGregorianCalendar(cal1));
								denetimKayitDetayAsansorBilgi
								.setRuhsatSicilTarih(objectFactory.createDenetimKayitDetayAsansorBilgiRuhsatSicilTarih(DateUtils
										.CalendarToXMLGregorianCalendar(cal1)));
							} catch (DatatypeConfigurationException e1) {
							
								e1.printStackTrace();
							}
						}
					} catch (ParseException e) {
						Calendar cal1=Calendar.getInstance();
					cal1.set(Calendar.YEAR, 1);
					cal1.set(Calendar.MONTH, 1);
					cal1.set(Calendar.DAY_OF_YEAR, 1);
					try {
						denetimKayitDetayAsansorBilgi.setTescilTarihi(DateUtils
								.CalendarToXMLGregorianCalendar(cal1));
						denetimKayitDetayAsansorBilgi
						.setRuhsatSicilTarih(objectFactory.createDenetimKayitDetayAsansorBilgiRuhsatSicilTarih(DateUtils
								.CalendarToXMLGregorianCalendar(cal1)));
					} catch (DatatypeConfigurationException e1) {
						
						e1.printStackTrace();
					}
					}

					break;

				case 192: // ruhsat tescil belgesi no
					break;
				default:
					break;
				}

			}

			if (cihaz.getTip() == 68) {
				switch (ctd.getDegerId()) {
				case 171: // asansorunYeri
					break;
				case 173: // montajFirmasi
					break;
				case 172: // kapasiteKisi
					break;
				case 174:// kapasiteAgirlik
					try {
						denetimKayitDetayAsansorBilgi.setBeyanYuku(Integer
								.parseInt(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setBeyanYuku(0);

					}
					break;
				case 175: // sinifi
					break;
				case 176: // imalYili
					try {
						denetimKayitDetayAsansorBilgi.setMontajYil(Integer
								.parseInt(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setMontajYil(0);

					}
					break;
				case 177: // seriNo
					denetimKayitDetayAsansorBilgi.setSeriNo(objectFactory.createDenetimKayitDetayAsansorBilgiSeriNo(ctd.getCevap()));
					//denetimKayitDetayAsansorBilgi.setMakinaMotorSeriNo(objectFactory.createDenetimKayitDetayAsansorBilgiMakinaMotorSeriNo(ctd.getCevap()));
//					denetimKayitDetayAsansorBilgi.setSeriNo(objectFactory
//							.createString(ctd.getCevap()));
					break;
				case 178: // kapsam
					break;
				case 179: // hiz
					try {
						denetimKayitDetayAsansorBilgi.setBeyanHiz(new BigDecimal(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setBeyanHiz(new BigDecimal(0));

					}
					break;
				case 180: // frenTipi
					break;
				case 181: // askiTipi
					break;
				case 182: // durakSayisi
					try {
						denetimKayitDetayAsansorBilgi.setDurakSayi(Integer
								.parseInt(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
						denetimKayitDetayAsansorBilgi.setKatSayisi(Integer
								.parseInt(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setDurakSayi(0);
						denetimKayitDetayAsansorBilgi.setKatSayisi(0);
					}
					break;

				case 196: // seyirMesafesi
					try {
						denetimKayitDetayAsansorBilgi.setSeyirMesafesi(objectFactory.createDenetimKayitDetayAsansorBilgiSeyirMesafesi(ctd.getCevap() == null ?new BigDecimal(0) : new BigDecimal(ctd.getCevap().trim())));
								
						
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setSeyirMesafesi(objectFactory.createDenetimKayitDetayAsansorBilgiSeyirMesafesi(new BigDecimal(0)));
					}
					break;
					

				case 210: // tanimi
					try {
						
						denetimKayitDetayAsansorBilgi.setMakinaMakaraDaireDurumu(objectFactory.createDenetimKayitDetayAsansorBilgiMakinaMakaraDaireDurumu(ctd.getCevap()==null?false:ctd.getCevap().trim().equalsIgnoreCase("MDRL")?true:false));
								
						
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setMakinaMakaraDaireDurumu(objectFactory.createDenetimKayitDetayAsansorBilgiMakinaMakaraDaireDurumu(false));
					}
					break;
					
				case 202: // kabinAlani
					break;
				case 183: // kumandaTipi
					break;

				case 4: // yonetmelikAdiTarihi

					break;

				case 184: // onKurNo

					break;

				case 197:// ceTseIsaret
					break;
				case 185: // halatSayisi
					break;
				case 186: // halatCapi
					break;
				case 187: // pompaMotorGucu
					break;
				case 188: // pompaMotorSeriNo
					break;

				case 3: // hidrolikGrubuMarkaModel
					denetimKayitDetayAsansorBilgi
							.setMarkaTipModel(objectFactory.createDenetimKayitDetayAsansorBilgiMarkaTipModel(ctd
									.getCevap()));
//					denetimKayitDetayAsansorBilgi
//					.setMarkaTipModel(objectFactory.createString(ctd
//							.getCevap()));
					break;
				case 189: // ozelSartlar
					break;
				case 200: // ruhsatTescilBelgesi
					denetimKayitDetayAsansorBilgi.setTescilBelgesi(ctd
							.getCevap().equals("VAR") ? true : false);
					break;

				case 199: // ruhsatTescilBelgesiTarihi
					
					Calendar cal = Calendar.getInstance();
					try {
						DateFormat df=new SimpleDateFormat("dd.MM.yyyy");
						cal.setTime(df
								.parse(ctd.getCevap() == null ?"01.01.0001" : ctd
										.getCevap().trim()));
						try {
							denetimKayitDetayAsansorBilgi
									.setTescilTarihi(DateUtils
											.CalendarToXMLGregorianCalendar(cal));
							denetimKayitDetayAsansorBilgi
							.setRuhsatSicilTarih(objectFactory.createDenetimKayitDetayAsansorBilgiRuhsatSicilTarih(DateUtils
									.CalendarToXMLGregorianCalendar(cal)));
						} catch (DatatypeConfigurationException e) {
							Calendar cal1=Calendar.getInstance();
							cal1.set(Calendar.YEAR, 1);
							cal1.set(Calendar.MONTH, 1);
							cal1.set(Calendar.DAY_OF_YEAR, 1);
							
							try {
								denetimKayitDetayAsansorBilgi.setTescilTarihi(DateUtils
										.CalendarToXMLGregorianCalendar(cal1));
								denetimKayitDetayAsansorBilgi
								.setRuhsatSicilTarih(objectFactory.createDenetimKayitDetayAsansorBilgiRuhsatSicilTarih(DateUtils
										.CalendarToXMLGregorianCalendar(cal1)));
							} catch (DatatypeConfigurationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} catch (ParseException e) {
						Calendar cal1=Calendar.getInstance();
						cal1.set(Calendar.YEAR, 1);
						cal1.set(Calendar.MONTH, 1);
						cal1.set(Calendar.DAY_OF_YEAR, 1);
						
						try {
							denetimKayitDetayAsansorBilgi.setTescilTarihi(DateUtils
									.CalendarToXMLGregorianCalendar(cal1));
							denetimKayitDetayAsansorBilgi
							.setRuhsatSicilTarih(objectFactory.createDenetimKayitDetayAsansorBilgiRuhsatSicilTarih(DateUtils
									.CalendarToXMLGregorianCalendar(cal1)));
						} catch (DatatypeConfigurationException e1) {
						
							e1.printStackTrace();
						}
					}

					break;

				case 198:// ruhsatTescilBelgesiNo
					break;

				default:
					break;
				}
			}
		}
		if (cihaz.getTip()==66){
		denetimKayitDetayAsansorBilgi.setMakinaMotorSeriNo(objectFactory.createDenetimKayitDetayAsansorBilgiMakinaMotorSeriNo(makinaMotorSeriNo));
		}
		denetimKayitDetayAsansorBilgi
				.setBinaAdresKodu((long) bina.getUavtKod());
		denetimKayitDetayAsansorBilgi.setKimlikNumarasi(objectFactory.createDenetimKayitDetayAsansorBilgiKimlikNumarasi(String.valueOf(cihaz.getUavtKod())));

	
		return denetimKayitDetayAsansorBilgi;
	}
	
	private DenetimKayitDetayBakimSozlesme setDenetimKayitDetayBakimSozlesme(
			FirmaDTO firma, CihazDTO cihaz) {
		DenetimKayitDetayBakimSozlesme denetimKayitDetayBakimSozlesme = new DenetimKayitDetayBakimSozlesme();
		ObjectFactory objectFactory = new ObjectFactory();
		CihazFirmaDTO cihazFirma = new CihazFirmaDTO();
		try {
			cihazFirma = new FirmaBusiness().getCihazAnlasmaliFirma(
					cihaz.getCihazId(), firma.getKod());
			denetimKayitDetayBakimSozlesme.setBakimSozlesmesiDurum(objectFactory.createDenetimKayitDetayBakimSozlesmeBakimSozlesmesiDurum(cihazFirma
					.getFirmaId() > 0 ? 1 : 0));
		} catch (ReadException e) {
			cihazFirma = new CihazFirmaDTO();
			denetimKayitDetayBakimSozlesme.setBakimSozlesmesiDurum(objectFactory.createDenetimKayitDetayBakimSozlesmeBakimSozlesmesiDurum(0));
		}
		catch (Exception e) {
			cihazFirma = new CihazFirmaDTO();
			denetimKayitDetayBakimSozlesme.setBakimSozlesmesiDurum(objectFactory.createDenetimKayitDetayBakimSozlesmeBakimSozlesmesiDurum(0));
		}
		
		

		
		try{
			denetimKayitDetayBakimSozlesme.setTseBelgeDurum(objectFactory.createDenetimKayitDetayBakimSozlesmeTseBelgeDurum(Util.nullif(firma.getTseBelgesi()).equals("E")?1:0));
			denetimKayitDetayBakimSozlesme
					.setYetkiliServisSozlesmeDurum(objectFactory.createDenetimKayitDetayBakimSozlesmeYetkiliServisSozlesmeDurum(Util.nullif(firma.getServisSozlesme()).equals("E")?1:0));
		}catch(NullPointerException e){
			denetimKayitDetayBakimSozlesme.setTseBelgeDurum(objectFactory.createDenetimKayitDetayBakimSozlesmeTseBelgeDurum(0));
			denetimKayitDetayBakimSozlesme
					.setYetkiliServisSozlesmeDurum(objectFactory.createDenetimKayitDetayBakimSozlesmeYetkiliServisSozlesmeDurum(0));
		}
		/************************** yeni eklendi (16.09.2015) ***************************/
		denetimKayitDetayBakimSozlesme.setBakimKlavuzu(objectFactory.createDenetimKayitDetayBakimSozlesmeBakimKlavuzu(false));
		/*********************************************************************************/
		return denetimKayitDetayBakimSozlesme;
	}
	

	private DenetimKayitDetayBinaSorumlusuBilgileri setDenetimKayitDetayBinaSorumlusuBilgileri(
			BinaKisiDTO kisi, BinaDTO bina) {
		DenetimKayitDetayBinaSorumlusuBilgileri denetimKayitDetayBinaSorumlusuBilgileri = new DenetimKayitDetayBinaSorumlusuBilgileri();
		if (bina!=null && kisi!=null){
		ObjectFactory objectFactory = new ObjectFactory();
		String adres;
		try {
			adres =  bina.getMahalle().trim() + " MAH. "
					+ bina.getCaddeSokak().trim()
					+ " NO:" + bina.getBinaNo().trim()+" "+bina.getIlceAdi().trim()+" "+bina.getIlAdi().trim();
				
		} catch (Exception e) {
			adres = bina.getAcikAdres();
		}
		denetimKayitDetayBinaSorumlusuBilgileri.setYapiAdres(objectFactory.createDenetimKayitDetayBinaSorumlusuBilgileriYapiAdres(adres));
		denetimKayitDetayBinaSorumlusuBilgileri
				.setBinaSorumluAdSoyad(objectFactory.createDenetimKayitDetayBinaSorumlusuBilgileriBinaSorumluAdSoyad(Util.nullif(kisi.getAdi())
						+ " " + Util.nullif(kisi.getSoyadi())));
		denetimKayitDetayBinaSorumlusuBilgileri
				.setBinaSorumluIletisimEPosta(objectFactory.createDenetimKayitDetayBinaSorumlusuBilgileriBinaSorumluIletisimEPosta(Util.nullif(kisi
						.getePosta())));
		denetimKayitDetayBinaSorumlusuBilgileri
				.setBinaSorumluIletisimTelNo(objectFactory.createDenetimKayitDetayBinaSorumlusuBilgileriBinaSorumluIletisimTelNo(String
						.valueOf(kisi.getTelefonNo())));
		denetimKayitDetayBinaSorumlusuBilgileri.setYapiAdres(objectFactory.createDenetimKayitDetayBinaSorumlusuBilgileriYapiAdres(Util.nullif(bina.getAcikAdres())));
		/*******************yeni eklendi * (16.09.2015)****************************/
		denetimKayitDetayBinaSorumlusuBilgileri.setAdaNo(objectFactory.createDenetimKayitDetayBinaSorumlusuBilgileriAdaNo(bina.getAda()));
		denetimKayitDetayBinaSorumlusuBilgileri.setParselNo(objectFactory.createDenetimKayitDetayBinaSorumlusuBilgileriParselNo(bina.getParsel()));
		/***************************************************************************/

		int yapiTip = 0;

		switch (bina.getYapiTip()) {
		case 1:
			yapiTip = 4; // İŞYERİ
			break;
		case 2:
			yapiTip = 4; // İŞYERİ
			break;
		case 3:
			yapiTip = 1; // APARTMAN
			break;
		case 4:
			yapiTip = 1; // APARTMAN
			break;
		case 5:
			yapiTip = 4; // İŞYERİ
			break;
		case 6:
			yapiTip = 5; // DİĞER
			break;
		case 7:
			yapiTip = 5; // DİĞER
			break;
		case 8:
			yapiTip = 4; // İŞYERİ
			break;
		case 9:
			yapiTip = 4; // İŞYERİ
			break;
		case 10:
			yapiTip = 5; // DİĞER
			break;
		case 11:
			yapiTip = 4; // İŞYERİ
			break;
		case 12:
			yapiTip = 2; // KAMU
			break;
		case 13:
			yapiTip = 5; // DİĞER
			break;
		case 14:
			yapiTip = 3; // MÜSTAKİL KONUT
			break;
		case 15:
			yapiTip = 3; // MÜSTAKİL KONUT
			break;
		case 16:
			yapiTip = 3; // MÜSTAKİL KONUT
			break;
		default:
			yapiTip = 5; // DİĞER
			break;
		}
		denetimKayitDetayBinaSorumlusuBilgileri.setYapininSinifi(objectFactory.createDenetimKayitDetayBinaSorumlusuBilgileriYapininSinifi(yapiTip));
		

	}
		return denetimKayitDetayBinaSorumlusuBilgileri;
	}
	
	private DenetimKayitDetayFirmaBilgi setDenetimKayitDetayFirmaBilgi(
			FirmaDTO firma,int cihazId,boolean firmaKatilimDurum) {
		DateFormat df=new SimpleDateFormat("dd.MM.yyyy");
		DenetimKayitDetayFirmaBilgi denetimKayitDetayFirmaBilgi = new DenetimKayitDetayFirmaBilgi();
		ObjectFactory objectFactory = new ObjectFactory();
		
		if (firma!=null){
			denetimKayitDetayFirmaBilgi.setBakimFirmaAd(objectFactory.createDenetimKayitDetayFirmaBilgiBakimFirmaAd(Util.nullif(firma.getUnvan())));
			denetimKayitDetayFirmaBilgi.setBakimFirmaAdresi(objectFactory.createDenetimKayitDetayFirmaBilgiBakimFirmaAdresi(Util.nullif(firma.getAdres())));
			denetimKayitDetayFirmaBilgi.setBakimFirmaTelefon(objectFactory.createDenetimKayitDetayFirmaBilgiBakimFirmaTelefon(String.valueOf(firma.getTelefonNo())));
			denetimKayitDetayFirmaBilgi.setBakimFirmaUnvan(objectFactory.createDenetimKayitDetayFirmaBilgiBakimFirmaUnvan(Util.nullif(firma.getUnvan())));
	
		int firmaYapi = 0;
		if (Util.nullif(firma.getMonteEden()).equals("E")) {
			firmaYapi = 1;
		}
		if (Util.nullif(firma.getYetkiliServis()).equals("E")) {
			firmaYapi = 2;
		}
		denetimKayitDetayFirmaBilgi.setBakimFirmaYapi(objectFactory.createDenetimKayitDetayFirmaBilgiBakimFirmaYapi(firmaYapi));
		denetimKayitDetayFirmaBilgi.setBakimFirmaYetkiliAdSoyad(objectFactory.createDenetimKayitDetayFirmaBilgiBakimFirmaYetkiliAdSoyad(Util.nullif(firma.getAdSoyad())));

		
		Date cihazFirmaSozlesmeTarih=null;
		try {
			cihazFirmaSozlesmeTarih=new FirmaBusiness().getCihazAnlasmaliFirmaTarih(cihazId, firma.getKod());
		} catch (CRUDException e2) {
			cihazFirmaSozlesmeTarih=null;
		}
		if (cihazFirmaSozlesmeTarih!=null){
			Calendar cal = Calendar.getInstance();
						
						
						cal.setTime(cihazFirmaSozlesmeTarih);
						try {
							denetimKayitDetayFirmaBilgi
									.setFirmaSozlesmeTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiFirmaSozlesmeTarihi(DateUtils
													.CalendarToXMLGregorianCalendar(cal)));

						} catch (DatatypeConfigurationException e) {
							Calendar cal1=Calendar.getInstance();
							cal1.set(Calendar.YEAR, 1);
							cal1.set(Calendar.MONTH, 1);
							cal1.set(Calendar.DAY_OF_YEAR, 1);
							try {
								denetimKayitDetayFirmaBilgi
										.setFirmaSozlesmeTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiFirmaSozlesmeTarihi(DateUtils
												.CalendarToXMLGregorianCalendar(cal1)));
							} catch (DatatypeConfigurationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
				

					}else{
						Calendar cal1=Calendar.getInstance();
						cal1.set(Calendar.YEAR, 1);
						cal1.set(Calendar.MONTH, 1);
						cal1.set(Calendar.DAY_OF_YEAR, 1);
						try {
							denetimKayitDetayFirmaBilgi
									.setFirmaSozlesmeTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiFirmaSozlesmeTarihi(DateUtils
											.CalendarToXMLGregorianCalendar(cal1)));
						} catch (DatatypeConfigurationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
		
		
	
		if (firma.getGecerlilikSuresi()!=null){
Calendar cal = Calendar.getInstance();
			
			
			cal.setTime(firma.getGecerlilikSuresi());
			try {
				denetimKayitDetayFirmaBilgi
						.setHizmetYeterlilikBelgesiGecerlilikTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiHizmetYeterlilikBelgesiGecerlilikTarihi(DateUtils
										.CalendarToXMLGregorianCalendar(cal)));

			} catch (DatatypeConfigurationException e) {
				Calendar cal1=Calendar.getInstance();
				cal1.set(Calendar.YEAR, 1);
				cal1.set(Calendar.MONTH, 1);
				cal1.set(Calendar.DAY_OF_YEAR, 1);
				try {
					denetimKayitDetayFirmaBilgi
							.setHizmetYeterlilikBelgesiGecerlilikTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiHizmetYeterlilikBelgesiGecerlilikTarihi(DateUtils
									.CalendarToXMLGregorianCalendar(cal1)));
				} catch (DatatypeConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}else{
			Calendar cal1=Calendar.getInstance();
			cal1.set(Calendar.YEAR, 1);
			cal1.set(Calendar.MONTH, 1);
			cal1.set(Calendar.DAY_OF_YEAR, 1);
			try {
				denetimKayitDetayFirmaBilgi
						.setHizmetYeterlilikBelgesiGecerlilikTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiHizmetYeterlilikBelgesiGecerlilikTarihi(DateUtils
								.CalendarToXMLGregorianCalendar(cal1)));
			} catch (DatatypeConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		denetimKayitDetayFirmaBilgi.setKontrolNezaretEtmeDurum(objectFactory.createDenetimKayitDetayFirmaBilgiKontrolNezaretEtmeDurum(firmaKatilimDurum));
		

		if (firma.getSozlesmeTarihi() != null) {
			Calendar cal = Calendar.getInstance();
			
			
			cal.setTime(firma
					.getSozlesmeTarihi());
			try {
				denetimKayitDetayFirmaBilgi
						.setYetkiliServisSozlesmeTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiYetkiliServisSozlesmeTarihi(DateUtils
										.CalendarToXMLGregorianCalendar(cal)));

			} catch (DatatypeConfigurationException e) {
				Calendar cal1=Calendar.getInstance();
				cal1.set(Calendar.YEAR, 1);
				cal1.set(Calendar.MONTH, 1);
				cal1.set(Calendar.DAY_OF_YEAR, 1);
				try {
					denetimKayitDetayFirmaBilgi
							.setYetkiliServisSozlesmeTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiYetkiliServisSozlesmeTarihi(DateUtils
									.CalendarToXMLGregorianCalendar(cal1)));
				} catch (DatatypeConfigurationException e1) {
					
					e1.printStackTrace();
				}
			}
		} else {
			Calendar cal1=Calendar.getInstance();
			cal1.set(Calendar.YEAR, 1);
			cal1.set(Calendar.MONTH, 1);
			cal1.set(Calendar.DAY_OF_YEAR, 1);
			try {
				denetimKayitDetayFirmaBilgi
						.setYetkiliServisSozlesmeTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiYetkiliServisSozlesmeTarihi(DateUtils
								.CalendarToXMLGregorianCalendar(cal1)));
			} catch (DatatypeConfigurationException e1) {
				
				e1.printStackTrace();
			}
			
		}
		}
		return denetimKayitDetayFirmaBilgi;
	}
	/* Hatalı olduğu için Değiştirildi. Metod sonuna Eski ibaresi eklendi (31.07.2015)
	 * 
	 * */
	private List<DenetimKayitDetaySoru> setDenetimKayitDetaySoruEski(
			List<KontrolTestDTO> list, int cihazTipi) {
		List<DenetimKayitDetaySoru> denetimDetaySoru = new ArrayList<DenetimKayitDetaySoru>();
		ObjectFactory objectFactory = new ObjectFactory();

		Map<Integer, ArrayList<ReferansDenetimKayitKontrolSorularListesi>> referansSorularMap = new HashMap<Integer, ArrayList<ReferansDenetimKayitKontrolSorularListesi>>();
		for (ReferansDenetimKayitKontrolSorularListesi r : referansDenetimKayitKontrolSorularListesi) {
			if (r.getCihazTipi() == cihazTipi) {
				if (referansSorularMap.get(r.getParent()) == null) {
					ArrayList<ReferansDenetimKayitKontrolSorularListesi> a = new ArrayList<ReferansDenetimKayitKontrolSorularListesi>();
					a.add(r);
					referansSorularMap.put(r.getParent(), a);
				} else {
					referansSorularMap.get(r.getParent()).add(r);
				}
			}

		}

		Map<Integer, ArrayList<KontrolTestDTO>> kontrolTestMap = new HashMap<Integer, ArrayList<KontrolTestDTO>>();
		for (KontrolTestDTO k : list) {
			if (kontrolTestMap.get(k.getSoruId()) == null) {
				ArrayList<KontrolTestDTO> a = new ArrayList<KontrolTestDTO>();
				a.add(k);
				kontrolTestMap.put(k.getSoruId(), a);
			} else {
				kontrolTestMap.get(k.getSoruId()).add(k);
			}

		}
		/*
		 * UAVT SORU SKALA <ReferansSoruSkalaListesiGetir> <MethodParameters>
		 * <ServisSonucOfArrayOfReferansSoruSkala8Zb117hL> <Sonuc
		 * attr0="ReferansSoruSkalaArray" isNull="false">
		 * <ReferansSoruSkalaArray0> <Aktif>True</Aktif>
		 * <DurumAciklama>Uygun</DurumAciklama> <Id>1</Id>
		 * </ReferansSoruSkalaArray0> <ReferansSoruSkalaArray1>
		 * <Aktif>True</Aktif> <DurumAciklama>Uygun Degil</DurumAciklama>
		 * <Id>2</Id> </ReferansSoruSkalaArray1> <ReferansSoruSkalaArray2>
		 * <Aktif>True</Aktif> <DurumAciklama>Uygulanamaz</DurumAciklama>
		 * <Id>3</Id> </ReferansSoruSkalaArray2> </Sonuc> <Hata>False</Hata>
		 * <HataKodu>-1</HataKodu> <Mesaj isNull="false" />
		 * </ServisSonucOfArrayOfReferansSoruSkala8Zb117hL> </MethodParameters>
		 * </ReferansSoruSkalaListesiGetir>
		 */

		Set<Integer> set = referansSorularMap.keySet();
		Iterator<Integer> iter = set.iterator();
		while (iter.hasNext()) {
			int uavtParent = iter.next();
			for (ReferansDenetimKayitKontrolSorularListesi r : referansSorularMap
					.get(uavtParent)) {
				DenetimKayitDetaySoru denetimKayitDetaySoru = new DenetimKayitDetaySoru();
				denetimKayitDetaySoru.setDenetimKayitSoruId(r.getSoruId());
				
				denetimKayitDetaySoru.setSoruSkala(objectFactory.createDenetimKayitDetaySoruSoruSkala(Integer.parseInt(referansSoruSkalaMap.get("Uygun")
								.replace("i", "").trim())));

				if (kontrolTestMap.get(uavtParent) != null) {
					for (KontrolTestDTO k : kontrolTestMap.get(uavtParent)) {
						for (OnTanimliTestDTO o : k.getAltSorular()) {
							if (o.getSoruId() == r.getAkmSoruId()) {
								String str = k.getCevap();
								str = str.equals("Uygulanmaz") ? "Uygulanamaz"
										: (str.equals("Uygun Değil") ? "Uygun Degil"
												: str);
								denetimKayitDetaySoru
										.setSoruSkala(objectFactory.createDenetimKayitDetaySoruSoruSkala(Integer
												.parseInt(referansSoruSkalaMap
														.get(str)
														.replace("i", "")
														.trim())));
								

							}
						}
					}
				}
				denetimDetaySoru.add(denetimKayitDetaySoru);
			}

		}
		/***** Bu Rutin akm test sorularından birden fazlasının sanayi bakanlığının soruları ile eşleştirilmesi sonucu
		 *     request.xml aynı id den birden fazla gönderildiği için hata alınması sonucu eklendi. 
		 *     Tekleştirme esnasında aynı id li farklı sorulara farklı değerler verilmesi durumunda 
		 *     hatalı sonuç gönderilebilir.16.06.2015 
		 */
		HashMap<Integer,DenetimKayitDetaySoru> map=new HashMap<Integer, DenetimKayitDetaySoru>();
		for (DenetimKayitDetaySoru d:denetimDetaySoru){
			map.put(d.getDenetimKayitSoruId(),d);
		}
		denetimDetaySoru=new ArrayList<DenetimKayitDetaySoru>();
		Set<Integer> st=map.keySet();
		Iterator<Integer> it=st.iterator();
		while (it.hasNext()){
			int i =it.next();
			denetimDetaySoru.add(map.get(i));
		}
		return denetimDetaySoru;
	}
	private List<DenetimKayitDetaySoru> setDenetimKayitDetaySoru (
			List<KontrolTestDTO> list, int cihazTipi,int tseStandart,boolean yeniRaporMu) throws CRUDException{
		List<DenetimKayitDetaySoru> denetimDetaySoru = new ArrayList<DenetimKayitDetaySoru>();
		if (list!=null){
		
			List<ReferansDenetimKayitKontrolSorularListesi> referansSorularList = new ArrayList<ReferansDenetimKayitKontrolSorularListesi>();
			int asansorTip=0;
			asansorTip=cihazTipi==66?2:1;
		List<ReferansDenetimSorularEslestirmeDTO> referansEslestirmeList = new ArrayList<ReferansDenetimSorularEslestirmeDTO>();
		if (yeniRaporMu){
			
			for (ReferansDenetimKayitKontrolSorularListesi r : referansDenetimKayitKontrolSorularListesi15092015Sonrasi) {
				if (r.getCihazTipi()==asansorTip) {
					r.setSoruId(r.getId());
					referansSorularList.add(r);
				}

			}
			
		}
		else{
		for (ReferansDenetimKayitKontrolSorularListesi r : referansDenetimKayitKontrolSorularListesi) {
			if (r.getCihazTipi() == cihazTipi) {
				referansSorularList.add(r);
			}

		}
		}
		
		
		if (yeniRaporMu){
			/* 
			 * Yeni Rapor da rapor gönderirken eşleştirme listesi kullanılmıyor.
			 * 
			 * 
			for (ReferansDenetimSorularEslestirmeDTO r : sessionBean.getReferansDenetimSorularEslestirmeListesi15092015Sonrasi()) {
				if (r.getAsansorTipi()==asansorTip) {
					referansEslestirmeList.add(r);
				}

			}
			*/
		}
		else{
		for (ReferansDenetimSorularEslestirmeDTO r : referansDenetimSorularEslestirmeListesi) {
			if (r.getAsansorTipi()==asansorTip && r.getTseStandartId()==tseStandart) {
				referansEslestirmeList.add(r);
			}

		}
		}
		
		Map<Integer, DenetimKayitDetaySoru> denetimDetaySoruMap=new HashMap<Integer, DenetimKayitDetaySoru>();
		if (yeniRaporMu){
			for (ReferansDenetimKayitKontrolSorularListesi refSoru : referansSorularList) {
				DenetimKayitDetaySoru denetimKayitDetaySoru = new DenetimKayitDetaySoru();
				denetimKayitDetaySoru.setDenetimKayitSoruId(refSoru.getSoruId());
				
				ObjectFactory factory=new ObjectFactory();
				denetimKayitDetaySoru.setSoruSkala(factory.createDenetimKayitDetaySoruSoruSkala(Integer.parseInt(referansSoruSkalaMap.get("Uygun"))));
				denetimDetaySoruMap.put(refSoru.getSoruId(),denetimKayitDetaySoru);
				
			}
		}
		else{
		for (ReferansDenetimSorularEslestirmeDTO eslestirme : referansEslestirmeList) {
			DenetimKayitDetaySoru denetimKayitDetaySoru = new DenetimKayitDetaySoru();
			denetimKayitDetaySoru.setDenetimKayitSoruId(eslestirme.getSoruId());
			
			ObjectFactory factory=new ObjectFactory();
			denetimKayitDetaySoru.setSoruSkala(factory.createDenetimKayitDetaySoruSoruSkala(Integer.parseInt(referansSoruSkalaMap.get("Uygun"))));
			denetimDetaySoruMap.put(eslestirme.getSoruId(),denetimKayitDetaySoru);
			
		}
		}
		
		Map<Integer, DenetimKayitDetaySoru> denetimDetaySoruMap1=new HashMap<Integer, DenetimKayitDetaySoru>();
		if (yeniRaporMu){
			for (KontrolTestDTO test : list) {
				break1:
				for (ReferansDenetimKayitKontrolSorularListesi refSoru : referansSorularList) {
					if (test.getBakanlikSoruId()==refSoru.getSoruId()){
						
						test.setCevap(test.getCevap().replaceAll("İ", "i").replaceAll("Ğ", "g").toLowerCase());
									DenetimKayitDetaySoru denetimKayitDetaySoru = new DenetimKayitDetaySoru();
									denetimKayitDetaySoru.setDenetimKayitSoruId(refSoru.getSoruId());
									
									String str = test.getCevap();
									if (str.contains("uygulanmaz"))
										str="Uygulanamaz";
									if (str.contains("degil") || str.contains("değil"))
										str="Uygun Degil";
									else{
										str="Uygun";
									}
									ObjectFactory factory=new ObjectFactory();
									denetimKayitDetaySoru
											.setSoruSkala(factory.createDenetimKayitDetaySoruSoruSkala(Integer
													.parseInt(referansSoruSkalaMap
															.get(str))));
									denetimDetaySoruMap1.put(refSoru.getSoruId(), denetimKayitDetaySoru);
									break break1;
									
				}
						
							
						}
					
					
				}
		}
		else{
			boolean altsorulardaUygunlukDurumuVar=false;
			for (KontrolTestDTO test : list) {
				if (test.getParent()!=null && !test.getParent().trim().equals("") && Integer.parseInt(test.getParent().trim())>0){
					altsorulardaUygunlukDurumuVar=true;
					break;
				}
			}
			
		
				
			
					for (KontrolTestDTO test : list) {
					test.setCevap(test.getCevap().replace("İ", "i").replace("Ğ", "g").toLowerCase());
					if (altsorulardaUygunlukDurumuVar){
						for (ReferansDenetimKayitKontrolSorularListesi refSoru : referansSorularList) {
							if (test.getSoruId()==refSoru.getAkmSoruId()){
								DenetimKayitDetaySoru denetimKayitDetaySoru = new DenetimKayitDetaySoru();
								denetimKayitDetaySoru.setDenetimKayitSoruId(refSoru.getSoruId());
								
								String str = test.getCevap();
								if (str.contains("uygulanmaz"))
									str="Uygulanamaz";
								if (str.contains("degil") || str.contains("değil"))
									str="Uygun Degil";
								else{
									str="Uygun";
								}
								ObjectFactory factory=new ObjectFactory();
								denetimKayitDetaySoru
										.setSoruSkala(factory.createDenetimKayitDetaySoruSoruSkala(Integer
												.parseInt(referansSoruSkalaMap
														.get(str))));
								denetimDetaySoruMap1.put(refSoru.getSoruId(), denetimKayitDetaySoru);
								
							}
						}
					}
					else{
						for (ReferansDenetimKayitKontrolSorularListesi refSoru : referansSorularList) {
						if (test.getSoruId()==refSoru.getParent()){
						for (OnTanimliTestDTO ott:test.getAltSorular()){
							if (ott.getSoruId()==refSoru.getAkmSoruId()){
								DenetimKayitDetaySoru denetimKayitDetaySoru = new DenetimKayitDetaySoru();
								denetimKayitDetaySoru.setDenetimKayitSoruId(refSoru.getSoruId());
								
								String str = test.getCevap();
								if (str.contains("uygulanmaz"))
									str="Uygulanamaz";
								if (str.contains("degil") || str.contains("değil"))
									str="Uygun Degil";
								else{
									str="Uygun";
								}
								ObjectFactory factory=new ObjectFactory();
								denetimKayitDetaySoru
										.setSoruSkala(factory.createDenetimKayitDetaySoruSoruSkala(Integer
												.parseInt(referansSoruSkalaMap
														.get(str))));
								denetimDetaySoruMap1.put(refSoru.getSoruId(), denetimKayitDetaySoru);
								
							}
						}
						}
					}
					}
					}
				
				
			
		}
		
		
		Set<Integer> set1=denetimDetaySoruMap1.keySet();
		Iterator<Integer> it1=set1.iterator();
		while (it1.hasNext()){
			int i=it1.next();
			if (denetimDetaySoruMap.get(i)!=null)
			denetimDetaySoruMap.get(i).setSoruSkala(denetimDetaySoruMap1.get(i).getSoruSkala());
		}
		
		
		Set<Integer> set=denetimDetaySoruMap.keySet();
		Iterator<Integer> it=set.iterator();
		while (it.hasNext()){
			int i=it.next();
			denetimDetaySoru.add(denetimDetaySoruMap.get(i));
		}
		
		
			
		
		/*
		 * UAVT SORU SKALA <ReferansSoruSkalaListesiGetir> <MethodParameters>
		 * <ServisSonucOfArrayOfReferansSoruSkala8Zb117hL> <Sonuc
		 * attr0="ReferansSoruSkalaArray" isNull="false">
		 * <ReferansSoruSkalaArray0> <Aktif>True</Aktif>
		 * <DurumAciklama>Uygun</DurumAciklama> <Id>1</Id>
		 * </ReferansSoruSkalaArray0> <ReferansSoruSkalaArray1>
		 * <Aktif>True</Aktif> <DurumAciklama>Uygun Degil</DurumAciklama>
		 * <Id>2</Id> </ReferansSoruSkalaArray1> <ReferansSoruSkalaArray2>
		 * <Aktif>True</Aktif> <DurumAciklama>Uygulanamaz</DurumAciklama>
		 * <Id>3</Id> </ReferansSoruSkalaArray2> </Sonuc> <Hata>False</Hata>
		 * <HataKodu>-1</HataKodu> <Mesaj isNull="false" />
		 * </ServisSonucOfArrayOfReferansSoruSkala8Zb117hL> </MethodParameters>
		 * </ReferansSoruSkalaListesiGetir>
		 */
		}
		
		return denetimDetaySoru;
	}
	private DenetimKayitSonuc setDenetimKayitSonuc(KontrolDTO kontrol) {
		
		DenetimKayitSonuc denetimKayitSonuc = new DenetimKayitSonuc();
		if (kontrol!=null){
		ObjectFactory objectFactory = new ObjectFactory();
		if (kontrol.getEtiket()!=null){
		if (kontrol.getEtiket().equals("K"))
		denetimKayitSonuc.setDenetimDurumId(new Integer(1));
		if (kontrol.getEtiket().equals("S"))
			denetimKayitSonuc.setDenetimDurumId(new Integer(2));
		if (kontrol.getEtiket().equals("Y"))
			denetimKayitSonuc.setDenetimDurumId(new Integer(3));
		if (kontrol.getEtiket().equals("M"))
			denetimKayitSonuc.setDenetimDurumId(new Integer(4));
		}else{
			denetimKayitSonuc.setDenetimDurumId(new Integer(0));
		}
		denetimKayitSonuc.setDenetimKayitId(0);
		
		}
		return denetimKayitSonuc;
	}

	public List<ReferansDenetimKayitKontrolSorularListesi> getReferansDenetimKayitKontrolSorularListesi() {
		return referansDenetimKayitKontrolSorularListesi;
	}

	public void setReferansDenetimKayitKontrolSorularListesi(
			List<ReferansDenetimKayitKontrolSorularListesi> referansDenetimKayitKontrolSorularListesi) {
		this.referansDenetimKayitKontrolSorularListesi = referansDenetimKayitKontrolSorularListesi;
	}

	public List<ReferansDenetimSorularEslestirmeDTO> getReferansDenetimSorularEslestirmeListesi() {
		return referansDenetimSorularEslestirmeListesi;
	}

	public void setReferansDenetimSorularEslestirmeListesi(
			List<ReferansDenetimSorularEslestirmeDTO> referansDenetimSorularEslestirmeListesi) {
		this.referansDenetimSorularEslestirmeListesi = referansDenetimSorularEslestirmeListesi;
	}

	public List<ReferansDenetimKayitKontrolSorularListesi> getReferansDenetimKayitKontrolSorularListesi15092015Sonrasi() {
		return referansDenetimKayitKontrolSorularListesi15092015Sonrasi;
	}

	public void setReferansDenetimKayitKontrolSorularListesi15092015Sonrasi(
			List<ReferansDenetimKayitKontrolSorularListesi> referansDenetimKayitKontrolSorularListesi15092015Sonrasi) {
		this.referansDenetimKayitKontrolSorularListesi15092015Sonrasi = referansDenetimKayitKontrolSorularListesi15092015Sonrasi;
	}

	public List<ReferansDenetimSorularEslestirmeDTO> getReferansDenetimSorularEslestirmeListesi15092015Sonrasi() {
		return referansDenetimSorularEslestirmeListesi15092015Sonrasi;
	}

	public void setReferansDenetimSorularEslestirmeListesi15092015Sonrasi(
			List<ReferansDenetimSorularEslestirmeDTO> referansDenetimSorularEslestirmeListesi15092015Sonrasi) {
		this.referansDenetimSorularEslestirmeListesi15092015Sonrasi = referansDenetimSorularEslestirmeListesi15092015Sonrasi;
	}

	public Map<String, String> getReferansSoruSkalaMap() {
		return referansSoruSkalaMap;
	}

	public void setReferansSoruSkalaMap(Map<String, String> referansSoruSkalaMap) {
		this.referansSoruSkalaMap = referansSoruSkalaMap;
	}
	
	
}
