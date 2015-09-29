package tr.org.mmo.asansor.managedbeans;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.hibernate.mapping.Array;

import tr.org.mmo.asansor.business.RaporBusiness;
import tr.org.mmo.asansor.dto.RaporDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.util.DenetimKayitUtils;
 
public class RaporGonder extends TimerTask {
 
    @Override
    public void run() {
        System.out.println("Asansör Denetim Kayıt İşlemi Başladı :"+new Date());
        completeTask();
        System.out.println("Asansör Denetim Kayıt İşlemi Bitti :"+new Date());
    }
 
    private static void completeTask() {
    	/*
        try {
            //assuming it takes 20 secs to complete the task
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    	
    	try {
    		int kayitAdet=0;
			//List<RaporDTO> raporlar=new RaporBusiness().getDenetimKayitRaporlar();
			List<RaporDTO> raporlar=new ArrayList<RaporDTO>();
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
				System.out.println("Denetim Kayıt için gönderilecek rapor kaydı bulunamadı :"+new Date());
			}
		}catch(Exception e1){
			
		}
    	/*catch (CRUDException e) {
			System.out.println("Rapor sorgusunda hata oluştu :"+new Date());
			e.printStackTrace();
		}*/
    	
    }
     
    public static void main(String args[]){
    	 completeTask();
    	/*
        TimerTask timerTask = new RaporGonder();
        //running timer task as daemon thread
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 30*1000);
        System.out.println("TimerTask started");
        //cancel after sometime
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("TimerTask cancelled");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }
 
}
	
	


