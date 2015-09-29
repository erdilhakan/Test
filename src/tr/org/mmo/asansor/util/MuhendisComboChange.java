package tr.org.mmo.asansor.util;


import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import org.primefaces.model.DualListModel;

import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.managedbeans.RandevuBean;


public class MuhendisComboChange implements ValueChangeListener{

	@Override
	public void processValueChange(ValueChangeEvent event)
			throws AbortProcessingException {
		FacesContext context=FacesContext.getCurrentInstance();
			RandevuBean randevuBean=(RandevuBean)context.getViewRoot().getViewMap().get("randevuBean");
		
		for (Kullanici m:randevuBean.getRandevu().getMuhendisler()){
			if (Integer.parseInt(m.getSicilNo())==Integer.parseInt((String)event.getNewValue())){
				randevuBean.getRandevu().getMuhendisler().remove(m);
				break;
			}
		}
			
		List<Kullanici> source=new ArrayList<Kullanici>();
		List<Kullanici> target=new ArrayList<Kullanici>();
		source=randevuBean.getRandevu().getMuhendisler();
		randevuBean.setMuhendisModel(new DualListModel<Kullanici>(source,target));
		
		
		
		
	}

}