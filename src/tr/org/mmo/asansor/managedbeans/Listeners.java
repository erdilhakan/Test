package tr.org.mmo.asansor.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;





import org.primefaces.model.DualListModel;

import tr.org.mmo.asansor.beans_.Kullanici;


@ManagedBean
public class Listeners {
public void muhendisComboChange(ValueChangeEvent event){
	FacesContext context=FacesContext.getCurrentInstance();
	RandevuBean randevuBean=(RandevuBean)context.getViewRoot().getViewMap().get("randevuBean");
	for (Kullanici m:randevuBean.getRandevu().getMuhendisler()){
		if (Integer.parseInt(m.getSicilNo())==Integer.parseInt(event.getNewValue().toString())){
			randevuBean.getRandevu().getMuhendisler().remove(m);
			break;
		}
	}
	List<Kullanici> s=new ArrayList<Kullanici>();
	List<Kullanici> t=new ArrayList<Kullanici>();
	s=randevuBean.getRandevu().getMuhendisler();
	randevuBean.setMuhendisModel(new DualListModel<Kullanici>(s,t));
}
}
