package tr.org.mmo.asansor.managedbeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.primefaces.event.SelectEvent;

import btest.*;
import tr.org.mmo.asansor.beans_.AnaSoru;
import tr.org.mmo.asansor.dao.DAOBase;
import tr.org.mmo.asansor.dto.RaporDTO;
import tr.org.mmo.asansor.dto.SoruDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.sanayiws.WSClient;
import tr.org.mmo.asansor.test.Test;
import tr.org.mmo.asansor.util.Ayarlar;
import tr.org.mmo.asansor.util.DenetimKayitUtils;
import tr.org.mmo.asansor.util.TestSorulari;

@ManagedBean
public class TestBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Date tarih1=null;
	Date tarih2=null;
	private List<TestSorulari> sorular=new ArrayList<TestSorulari>();
	
	public void getPdf(ActionEvent event ){
	  
      
      String path= "////10.10.1.6:22//var//pdf";
      String path1= "//var//pdf";
      String path2 ="\\var\\pdf";
        
         File[] f=new File(path).listFiles();
         File[] f1=new File(path1).listFiles();
         File[] f2=new File(path2).listFiles();
         try {
        	 System.out.println(path);
        	 System.out.println(path1);
        	 System.out.println(path2);
        	 if (f!=null){
        		 
        		 
        		 System.out.println("p1 :"+f.length);
        		 
        		 p(f);
        	 }else{
        		 System.out.println("p1 null");
        		 if (f1!=null){
            		 System.out.println("p2 :"+f1.length);
            		 
            		 p(f1);
            	 }else{
            		 System.out.println("p2 null");
            		 if (f2!=null){
                		 System.out.println("p3 :"+f2.length);
                		 
                		 p(f2);
                	 }
            		 else{
            			 System.out.println("p3 null");
            		 }
            	 }
        	 }
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("İşlem Tamamlandı"));
	}
	
	private byte[] flush(File f){
        FileInputStream fileInputStream=null;
        byte[] bFile = new byte[(int) f.length()];
        
       try {
           //convert file into array of bytes
           fileInputStream = new FileInputStream(f);
           fileInputStream.read(bFile);
           fileInputStream.close();
           /*
           for (int i = 0; i < bFile.length; i++) {
                 System.out.print((char)bFile[i]);
           }
*/
           System.out.println("Done");
       }catch(Exception e){
         e.printStackTrace();
       }
       return bFile;
       
}
	
	public void p(File[] f){
		byte[] fileBytes;
		  for (File a:f){
	            System.out.println(a.getName());
	           fileBytes= flush(a);
	           HttpServletRequest request=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	                 request.getSession().setAttribute("dosya",fileBytes);
	                 request.getSession().setAttribute("dosyaAdi",a.getName());
	                         
	                         
	                  try {
	                                   FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +"/PDFServlet");
	                                   
	                                                             
	                           } catch (IOException e) {
	                                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
	                                 e.printStackTrace();
	                         }
	           
	            
	                  break;
	            
	      }
	}
	
	public String raporGonder(){
		try {
    		int kayitAdet=0;
    		
    		
    	
    		
			List<RaporDTO> raporlar=Test.getDenetimKayitRaporlar(tarih1,tarih2,kontrolId);
			if (raporlar.size()>0){
				System.out.println("Denetim Kayıt için gönderilecek rapor adet: "+raporlar.size() + " :" +new Date());
				for (RaporDTO r:raporlar){
					boolean denetimKaydedildi=false;
					denetimKaydedildi=new DenetimKayitUtils().batchAsansorDenetimKayit(r);
					if (denetimKaydedildi)
						kayitAdet++;
				}
				System.out.println("Kaydedilen rapor adet: "+kayitAdet + " :" +new Date());
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Denetim Kayıt için gönderilecek rapor kaydı bulunamadı :"+new Date()));
				
			}
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			System.out.println("Rapor sorgusunda hata oluştu :"+new Date());
			e.printStackTrace();
		}
		return "";
		
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
	private Integer kontrolId=new Integer(0);
	public void tarihChange(SelectEvent event){
		UIInput i=(UIInput)event.getComponent();
		if (i!=null){
			if (i.getId().toString().equals("tarih1")){
				if (tarih2!=null){
					if (tarih1.compareTo(tarih2)>0)
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Hatalı Tarih"));
				}
			}
			if (i.getId().toString().equals("tarih2")){
				if (tarih1!=null){
					if (tarih1.compareTo(tarih2)>0)
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Hatalı Tarih"));
				}
			}
		}
		
	}

	public Integer getKontrolId() {
		return kontrolId;
	}

	public void setKontrolId(Integer kontrolId) {
		this.kontrolId = kontrolId;
	}

	
	public void testSorulari(ActionEvent event){
		sorular=new ArrayList<TestSorulari>();
		Connection con=null;
		List<SoruDTO> sList=new ArrayList<SoruDTO>();
		Map<Integer, Integer> eMap=new HashMap<Integer, Integer>();
		try{
			QueryRunner runner=new QueryRunner();
			con=DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			
			sList=(ArrayList<SoruDTO>)runner.query(con,"SELECT soru_id as soruId,soru,cihaz_tipi as cihazTipi FROM "
					+ Ayarlar.SHEMA + ".soru_sirali",
					
					new BeanListHandler<SoruDTO>(SoruDTO.class));
			System.out.println("t");
			
		}catch(Exception e){
			
		}finally{
			DbUtils.closeQuietly(con);
		}
		try {
			int asansorTuru=2;
					
			ServisSonucOfArrayOfReferansKontrolSorular8Zb117HL s=new WSClient().referansDenetimKayitKontrolSorularListesiGetir();
			List<ReferansKontrolSorular> sorular1=new ArrayList<ReferansKontrolSorular>();
			if (!s.isHata()){ 
				sorular1=s.getSonuc().getValue().getReferansKontrolSorular();
				if (sorular1!=null){
					for (int i =0;i<sorular1.size();i++){
						ReferansKontrolSorular rk=new ReferansKontrolSorular();
						rk=sorular1.get(i);
						sorular.add(new TestSorulari(rk));
					}
				}
				
				for (SoruDTO a:sList){
					
					int tip=a.getCihazTipi()==66?2:1;
					a1:
					for (TestSorulari t:sorular){
						
					if (a.getSoru().replace("ç", "c").replace("ş", "s").replace("ı", "i").replace("ü", "u").replace("ö", "o").replace("ğ", "g").replace("i", "I").toUpperCase().trim().equalsIgnoreCase(t.getSoruAciklama().replace("ç", "c").replace("ş", "s").replace("ı", "i").replace("ü", "u").replace("ö", "o").replace("ğ", "g").replace("i", "I").toUpperCase().trim()) && tip==t.getAsansorTuru()){
						eMap.put(a.getSoruId(), t.getId());
						break a1;
					}
					}
				}
				Set<Integer> set=eMap.keySet();
				Iterator<Integer> it=set.iterator();
				try{
					QueryRunner runner=new QueryRunner();
					con=DAOBase.instance().getConnection();
					con.setAutoCommit(false);
					while (it.hasNext()){
						int ii=it.next();
						runner.update(con,"update akm.soru set bakanlik_soru_id=? where soru_id=?",new Object[]{eMap.get(ii),ii});
						
					}
					con.commit();
					
				}catch(Exception e){
					
				}finally{
					DbUtils.closeQuietly(con);
				}
				
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(s.getHataKodu().toString()+" - "+s.getMesaj().getValue()));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
	}

	
	
	public List<TestSorulari> getSorular() {
		return sorular;
	}

	public void setSorular(List<TestSorulari> sorular) {
		this.sorular = sorular;
	}



	

}
