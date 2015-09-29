package tr.org.mmo.asansor.managedbeans;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import tr.org.mmo.asansor.business.BinaBusiness;
import tr.org.mmo.asansor.dao.ApplicationDAOImpl;
import tr.org.mmo.asansor.dto.IlIlceDTO;
import tr.org.mmo.asansor.dto.SozlesmeBinaTipleriDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.util.Ayarlar;
import tr.org.mmo.asansor.util.IlIlceComparator;
import tr.org.mmo.asansor.util.Messages;

@ManagedBean(name = "applicationBean", eager = true)
@ApplicationScoped
public class ApplicationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4209877869559704266L;

	private HashMap<Integer, List<File>> fileMap = new HashMap<Integer, List<File>>();

	private TreeMap<String, Integer> iller = new TreeMap<String, Integer>(
			new IlIlceComparator());
	private HashMap<Integer, HashMap<String, Integer>> ilceler = new HashMap<Integer, HashMap<String, Integer>>();
	private List<SozlesmeBinaTipleriDTO> sozlesmeBinaTipList = new ArrayList<SozlesmeBinaTipleriDTO>();
	private List<IlIlceDTO> ilIlceList = new ArrayList<IlIlceDTO>();
	private ScheduledExecutorService scheduler; 
	public ApplicationBean() {
		try {
			Ayarlar.init();
			/*
			String path = "//var//pdf";

			try {

				File[] files = new File(path).listFiles();
				int j = 0;
				for (File f : files) {
					int binaTescilNo = 0;
					j = 0;
					for (String str : f.getName().split("__")) {
						binaTescilNo = Integer.parseInt(str);
						break;
					}
					j = (binaTescilNo / 1000) % 1000;
					List<File> l = new ArrayList<File>();
					l = fileMap.get(j);
					l.add(f);
					fileMap.put(j, l);
				}

			} catch (NullPointerException e) {

			}*/
			sozlesmeBinaTipList = new BinaBusiness().getSozlesmeBinaTipleri();

			ilIlceList = ApplicationDAOImpl.getINSTANCE().getIllerIlceler();
			HashMap<String, Integer> ilMap = new HashMap<String, Integer>();

			HashMap<String, Integer> ilceMap = new HashMap<String, Integer>();
			int i = 0;
			for (IlIlceDTO dto : ilIlceList) {
				if (i != dto.getIlKodu()) {

					if (i != 0) {
						ilceler.put(i, ilceMap);
						ilceMap = new HashMap<String, Integer>();
					}
					ilMap.put(dto.getIlAdi(), dto.getIlKodu());
					i = dto.getIlKodu();
				}
				ilceMap.put(dto.getIlceAdi(), dto.getIlceKodu());

			}

			ilceler.put(i, ilceMap);
			iller.putAll(ilMap);

		} catch (CRUDException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

		} catch (Exception e) {
			System.out.println(Messages._AYARLAR_OKUNAMADI_.getMesaj());
			e.printStackTrace();

		}
	}

	public TreeMap<String, Integer> getIller() {
		return iller;
	}

	public void setIller(TreeMap<String, Integer> iller) {
		this.iller = iller;
	}

	public HashMap<Integer, HashMap<String, Integer>> getIlceler() {
		return ilceler;
	}

	public void setIlceler(HashMap<Integer, HashMap<String, Integer>> ilceler) {
		this.ilceler = ilceler;
	}

	public List<SozlesmeBinaTipleriDTO> getSozlesmeBinaTipList() {
		return sozlesmeBinaTipList;
	}

	public void setSozlesmeBinaTipList(
			List<SozlesmeBinaTipleriDTO> sozlesmeBinaTipList) {
		this.sozlesmeBinaTipList = sozlesmeBinaTipList;
	}

	public List<IlIlceDTO> getIlIlceList() {
		return ilIlceList;
	}

	public void setIlIlceList(List<IlIlceDTO> ilIlceList) {
		this.ilIlceList = ilIlceList;
	}

	public HashMap<Integer, List<File>> getFileMap() {
		return fileMap;
	}

	public void setFileMap(HashMap<Integer, List<File>> fileMap) {
		this.fileMap = fileMap;
	}
	
	  @PostConstruct
	    public void init() {
	       // scheduler = Executors.newSingleThreadScheduledExecutor();

	       //int h= Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	       
	     //   scheduler.scheduleAtFixedRate(new RaporGonder(), 23-h,1, TimeUnit.DAYS);
	     
	       
	    }

	    @PreDestroy
	    public void destroy() {
	       // scheduler.shutdownNow();
	    }


}
