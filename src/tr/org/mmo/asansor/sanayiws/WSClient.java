package tr.org.mmo.asansor.sanayiws;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import tr.org.mmo.asansor.beans_.ReferansDenetimKayitKontrolSorularListesi;
import tr.org.mmo.asansor.dto.ReferansDenetimSorularEslestirmeDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import btest.*;


public class WSClient implements IWSClient {
	private static WSClient instance;
	
	public static WSClient getInstance() throws CRUDException{
		return instance==null?new WSClient():instance;
	}
	BstbAsansorDenetimServiceClient client;
	IBstbAsansorDenetimServis IService;
	public WSClient() throws CRUDException {
	     
	     try {
	    	
	    		ResourceBundle rb = ResourceBundle.getBundle("ayarlar");
		    	 String line;
		 		/*
		 			URL url = new URL(rb.getString("URLPROD")); 
	 			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream())); 
	 			line = in.readLine(); 

	 		//	System.out.println( line );	

	 			in.close(); 
	 			*/
	 		
	    	
	    	 HttpServletRequest request = (HttpServletRequest) FacesContext
		 				.getCurrentInstance().getExternalContext().getRequest();
		 	
		 						
		 		File file=new File(request.getSession()
		 				.getServletContext()
		 				.getRealPath(
		 						"/keystore/")+"/dsl.jks");
		 					
		 					
		 		
	    	// File file=new File(line);
		    client=	new BstbAsansorDenetimServiceClient(file.getAbsolutePath());
	    	 //client=		BstbAsansorDenetimServiceClient.INSTANCE;
			IService =client.port();
		} catch (Exception ex) {
			   throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
		}
	}
	
    @Override
	public  ServisSonucOfArrayOfReferansTseStandard8Zb117HL getReferansStandart() throws CRUDException{
    	ServisSonucOfArrayOfReferansTseStandard8Zb117HL servisSonucReferansStandart=new ServisSonucOfArrayOfReferansTseStandard8Zb117HL();
		  try {
            servisSonucReferansStandart=   IService.referansTseStandardListesiGetir();
          
          
          } catch (IBstbAsansorDenetimServisReferansTseStandardListesiGetirServiceExceptionFaultFaultMessage ex) {
              throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
          }
		  return servisSonucReferansStandart;
    }
    @Override
	public  ServisSonucOfArrayOfIl8Zb117HL getIller() throws CRUDException{
    	ServisSonucOfArrayOfIl8Zb117HL servisSonucIl=new ServisSonucOfArrayOfIl8Zb117HL();
		 try {
             servisSonucIl=IService.tumIlleriSorgulama();
            
             
         } catch (IBstbAsansorDenetimServisTumIlleriSorgulamaServiceExceptionFaultFaultMessage ex) {
            
        	 throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
         }
		 return servisSonucIl;
	}
    @Override
	public ServisSonucOfArrayOfMahalle8Zb117HL getMahalleOfIlceKod(int ilceKod) throws CRUDException{
		
		 ServisSonucOfArrayOfMahalle8Zb117HL servisSonucMahalle=new ServisSonucOfArrayOfMahalle8Zb117HL();
		try {
			servisSonucMahalle = IService.ilceyeBagliMahalleSorgulama(ilceKod);
		    
	         
	       
		} catch (IBstbAsansorDenetimServisIlceyeBagliMahalleSorgulamaServiceExceptionFaultFaultMessage ex) {
			throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
		}
     
         
		return servisSonucMahalle;
	}
    @Override
	public  ServisSonucOfArrayOfCsbm8Zb117HL getCsbmKodOfMahalleKod(int mahalleKod) throws CRUDException{
		
		 ServisSonucOfArrayOfCsbm8Zb117HL servisSonucCsbm=new ServisSonucOfArrayOfCsbm8Zb117HL();
		try {
			servisSonucCsbm = IService.mahalleyeBagliCsbmSorgulama(mahalleKod);
			
		} catch (IBstbAsansorDenetimServisMahalleyeBagliCsbmSorgulamaServiceExceptionFaultFaultMessage ex) {
			throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
		}
    	
     
         
		return servisSonucCsbm;
	}
    @Override
	public  ServisSonucOfArrayOfBina8Zb117HL getBinaOfCsbmKod(int csbmKod) throws CRUDException{
		
		ServisSonucOfArrayOfBina8Zb117HL servisSonucBina=new ServisSonucOfArrayOfBina8Zb117HL();
		try {
			servisSonucBina = IService.csbmBagliBinaSorgulama(csbmKod);
			
	       
		} catch (IBstbAsansorDenetimServisCsbmBagliBinaSorgulamaServiceExceptionFaultFaultMessage ex) {
			throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
		}
		return servisSonucBina;
         
	}
    @Override
	public  ServisSonucOfAsansorKimlikKodlar8Zb117HL getAsansorKimlikKodOfBinaKodAsansorSiraNo(int binaKod,int asansorSiraNo) throws CRUDException{
		
		 ServisSonucOfAsansorKimlikKodlar8Zb117HL servisSonucAsansor=new ServisSonucOfAsansorKimlikKodlar8Zb117HL();
	
			try {
				servisSonucAsansor=IService.asansorKimlikKodSorgula(binaKod,asansorSiraNo);
				
				
			} catch (IBstbAsansorDenetimServisAsansorKimlikKodSorgulaServiceExceptionFaultFaultMessage ex) {
				throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
			}
			
	        	
		return servisSonucAsansor;
        
	}
    @Override
    public ServisSonucOfDenetimKayitSonuc8Zb117HL asansorDenetimKayit(DenetimKayit denetimKayit) throws CRUDException{
    	ServisSonucOfDenetimKayitSonuc8Zb117HL  servisSonucDenetimKayit=new ServisSonucOfDenetimKayitSonuc8Zb117HL();
    	try {
    		
			servisSonucDenetimKayit=IService.asansorDenetimKayit(denetimKayit);
			
		} catch (IBstbAsansorDenetimServisAsansorDenetimKayitServiceExceptionFaultFaultMessage ex) {
			throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
		}
    	return servisSonucDenetimKayit;
    }
    @Override
    public ServisSonucOfDenetimKayitSonuc8Zb117HL asansorDenetimKayitGuncelle(DenetimKayit denetimKayit) throws CRUDException{
    	ServisSonucOfDenetimKayitSonuc8Zb117HL  servisSonucDenetimKayit=new ServisSonucOfDenetimKayitSonuc8Zb117HL();
    	
    		
    		try {
				servisSonucDenetimKayit=IService.asansorDenetimKayitGuncelle(denetimKayit);
			} catch (IBstbAsansorDenetimServisAsansorDenetimKayitGuncelleServiceExceptionFaultFaultMessage ex) {
				throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
			}
		
			
		
    	return servisSonucDenetimKayit;
    }
    @Override
    public ServisSonuc asansorDenetimKayitSilme(int kayitNo) throws CRUDException{
    	ServisSonuc  servisSonuc=new ServisSonuc();
    	
    	
				try {
					servisSonuc=IService.asansorDenetimKayitSilme(kayitNo);
				} catch (IBstbAsansorDenetimServisAsansorDenetimKayitSilmeServiceExceptionFaultFaultMessage ex) {
					throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
				}
		
				
		
		
			
		
    	return servisSonuc;
    }
    
    @Override
    public ServisSonucOfArrayOfDenetimKayit8Zb117HL asansorDenetimKayitSorgula(int kayitNo) throws CRUDException{
    	ServisSonucOfArrayOfDenetimKayit8Zb117HL  servisSonucDenetimKayit=new ServisSonucOfArrayOfDenetimKayit8Zb117HL();
    	
    				try {
						servisSonucDenetimKayit=IService.asansorDenetimKayitSorgula(kayitNo);
					} catch (IBstbAsansorDenetimServisAsansorDenetimKayitSorgulaServiceExceptionFaultFaultMessage ex) {
						throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
					}
			
		
		
    	return servisSonucDenetimKayit;
    }

	@Override
	public ServisSonucOfBelediye8Zb117HL ilceBelediyeSorgulama(int ilceKod) throws CRUDException{
		ServisSonucOfBelediye8Zb117HL  servisSonucBelediye=new ServisSonucOfBelediye8Zb117HL();
    	
		
			try {
				servisSonucBelediye=IService.ilceBelediyeSorgulama(ilceKod);
			} catch (IBstbAsansorDenetimServisIlceBelediyeSorgulamaServiceExceptionFaultFaultMessage ex) {
				throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
			}
		



return servisSonucBelediye;
		
	}
	
	

	@Override
	public ServisSonucOfArrayOfBina8Zb117HL csbmBagliBinaSorgulama(int csbmKod) throws CRUDException {
		ServisSonucOfArrayOfBina8Zb117HL  servisSonucOfArrayOfBina=new ServisSonucOfArrayOfBina8Zb117HL();
    	
			try {
				servisSonucOfArrayOfBina=IService.csbmBagliBinaSorgulama(csbmKod);
			} catch (IBstbAsansorDenetimServisCsbmBagliBinaSorgulamaServiceExceptionFaultFaultMessage ex) {
				throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
			}
	



return servisSonucOfArrayOfBina;
		
	}

	@Override
	public ServisSonucOfAsansorKimlikKodlar8Zb117HL asansorKimlikKodSorgula(
			int uavtBinaKod,int asansorSiraNo) throws CRUDException{
		ServisSonucOfAsansorKimlikKodlar8Zb117HL  servisSonucOfAsansorKimlikKodlar=new ServisSonucOfAsansorKimlikKodlar8Zb117HL();
		try {
			servisSonucOfAsansorKimlikKodlar=IService.asansorKimlikKodSorgula(uavtBinaKod, asansorSiraNo);
		} catch (IBstbAsansorDenetimServisAsansorKimlikKodSorgulaServiceExceptionFaultFaultMessage ex) {
			throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
		}
		return servisSonucOfAsansorKimlikKodlar;
	}
	
	@Override
	public ServisSonucOfAsansorKimlikKodlar8Zb117HL binaAsansorKayit(
			int uavtBinaKod,int asansorSiraNo) throws CRUDException{
		ServisSonucOfAsansorKimlikKodlar8Zb117HL  servisSonucOfAsansorKimlikKodlar =new ServisSonucOfAsansorKimlikKodlar8Zb117HL(); 
		
			try {
				servisSonucOfAsansorKimlikKodlar=	IService.binaAsansorKayit(uavtBinaKod, asansorSiraNo);
			} catch (IBstbAsansorDenetimServisBinaAsansorKayitServiceExceptionFaultFaultMessage ex) {
				throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
			}
		
		return servisSonucOfAsansorKimlikKodlar;
	}
	@Override
	public ServisSonucOfArrayOfReferansKontrolSorular8Zb117HL referansDenetimKayitKontrolSorularListesiGetir() throws CRUDException{
		ServisSonucOfArrayOfReferansKontrolSorular8Zb117HL servisSonucOfArrayOfReferansKontrolSorular=new ServisSonucOfArrayOfReferansKontrolSorular8Zb117HL();
		try {
			servisSonucOfArrayOfReferansKontrolSorular= IService.referansDenetimKayitKontrolSorularListesiGetir();
		} catch (IBstbAsansorDenetimServisReferansDenetimKayitKontrolSorularListesiGetirServiceExceptionFaultFaultMessage ex) {
			throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
		}
		return servisSonucOfArrayOfReferansKontrolSorular;
		
	}
	
	
	@Override
	public ServisSonucOfArrayOfReferansKontrolSorular8Zb117HL referansDenetimKayitKontrolSorulariAsansorTuruneGore(int asansorTuru) throws CRUDException{
		ServisSonucOfArrayOfReferansKontrolSorular8Zb117HL servisSonucOfArrayOfReferansKontrolSorular=new ServisSonucOfArrayOfReferansKontrolSorular8Zb117HL();
		
			try {
				servisSonucOfArrayOfReferansKontrolSorular= IService.referansDenetimKayitKontrolSorulariAsansorTuruneGore(asansorTuru);
			} catch (IBstbAsansorDenetimServisReferansDenetimKayitKontrolSorulariAsansorTuruneGoreServiceExceptionFaultFaultMessage ex) {
				throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
			}
		
		return servisSonucOfArrayOfReferansKontrolSorular;
		
	}
	
	@Override
	public List<ReferansDenetimKayitKontrolSorularListesi> referansDenetimKayitKontrolSorularListeGetir() throws CRUDException{
		List<ReferansDenetimKayitKontrolSorularListesi> referansDenetimKontrolSorular=new ArrayList<ReferansDenetimKayitKontrolSorularListesi>();
		ServisSonucOfArrayOfReferansKontrolSorular8Zb117HL servisSonucOfArrayOfReferansKontrolSorular=new ServisSonucOfArrayOfReferansKontrolSorular8Zb117HL();
		try {
			servisSonucOfArrayOfReferansKontrolSorular= IService.referansDenetimKayitKontrolSorularListesiGetir();
			if (!servisSonucOfArrayOfReferansKontrolSorular.isHata()){
				ArrayOfReferansKontrolSorular aorks=  servisSonucOfArrayOfReferansKontrolSorular.getSonuc().getValue();
				if (aorks!=null){
					for (int i=0;i<aorks.getReferansKontrolSorular().size();i++)
				{
					referansDenetimKontrolSorular.add(new ReferansDenetimKayitKontrolSorularListesi(aorks.getReferansKontrolSorular().get(i)));
				}
			}
			}
		} catch (IBstbAsansorDenetimServisReferansDenetimKayitKontrolSorularListesiGetirServiceExceptionFaultFaultMessage ex) {
			throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
		}
		return referansDenetimKontrolSorular;
		
	}

	@Override
	public ServisSonucOfArrayOfReferansSoruSkala8Zb117HL referansSoruSkalaListesiGetir() throws CRUDException{
		ServisSonucOfArrayOfReferansSoruSkala8Zb117HL  servisSonucOfArrayOfReferansSoruSkala=new ServisSonucOfArrayOfReferansSoruSkala8Zb117HL();
		try {
			servisSonucOfArrayOfReferansSoruSkala=IService.referansSoruSkalaListesiGetir();
			
		} catch (IBstbAsansorDenetimServisReferansSoruSkalaListesiGetirServiceExceptionFaultFaultMessage ex) {
			throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
		}
		return servisSonucOfArrayOfReferansSoruSkala;
	}

	@Override
	public ServisSonucOfArrayOfBelediye8Zb117HL ileGoreBelediyeSorgulama(int i) throws CRUDException{
		ServisSonucOfArrayOfBelediye8Zb117HL servisSonucOfArrayOfBelediye=new ServisSonucOfArrayOfBelediye8Zb117HL();
		
			try {
				servisSonucOfArrayOfBelediye=IService.ileGoreBelediyeSorgulama(i);
			} catch (IBstbAsansorDenetimServisIleGoreBelediyeSorgulamaServiceExceptionFaultFaultMessage ex) {
				throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
			}
		
			
		
		return servisSonucOfArrayOfBelediye;
		
	}

	@Override
	public List<ReferansDenetimSorularEslestirmeDTO> referansDenetimSorularEslestirmeListesiGetir() throws CRUDException{
		List<ReferansDenetimSorularEslestirmeDTO> referansDenetimKontrolSorular=new ArrayList<ReferansDenetimSorularEslestirmeDTO>();
		ServisSonucOfArrayOfReferansDenetimSorularEslestirme8Zb117HL servisSonucOfArrayOfReferansDenetimSorularEslestirme=new ServisSonucOfArrayOfReferansDenetimSorularEslestirme8Zb117HL();
		
			try {
				servisSonucOfArrayOfReferansDenetimSorularEslestirme= IService.referansDenetimSorularEslestirmeListesiGetir();
			
			if (!servisSonucOfArrayOfReferansDenetimSorularEslestirme.isHata()){
				ArrayOfReferansDenetimSorularEslestirme aordse=  servisSonucOfArrayOfReferansDenetimSorularEslestirme.getSonuc().getValue();
				if (aordse!=null){
					for (int i=0;i<aordse.getReferansDenetimSorularEslestirme().size();i++)
				{
						
					referansDenetimKontrolSorular.add(new ReferansDenetimSorularEslestirmeDTO(aordse.getReferansDenetimSorularEslestirme().get(i)));
				}
			}
			}
			} catch (IBstbAsansorDenetimServisReferansDenetimSorularEslestirmeListesiGetirServiceExceptionFaultFaultMessage ex) {
				throw new CRUDException(ex.getMessage(),new Throwable(ex.getCause()));
			}
		return referansDenetimKontrolSorular;
	}
	
	
	
}
