package tr.org.mmo.asansor.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;

import tr.org.mmo.asansor.dto.BelediyeDTO;
import tr.org.mmo.asansor.models.BelediyeDataModel;
import tr.org.mmo.asansor.util.BelediyeComparator;

@ManagedBean
@ViewScoped
public class BelediyeListeBean implements Serializable {
	/**
	 * 
	 */
	private List<BelediyeDTO> belediyeList = new ArrayList<BelediyeDTO>();
	private List<BelediyeDTO> filteredList;
	private BelediyeDTO slctBelediye;
	private BelediyeDataModel belediyeListModel;
	private static final long serialVersionUID = -7268654626051281117L;

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	@PostConstruct
	public void init() {
		getBelediyeler();

	}

	public void onRowSelect(SelectEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();
		String id = String.valueOf(((BelediyeDTO) event.getObject()).getKod());

		if (id != null) {

			BelediyeBean belediyeBean = (BelediyeBean) context.getApplication()
					.evaluateExpressionGet(context, "#{belediyeBean}",
							BelediyeBean.class);
			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();
			HttpSession session = request.getSession();

			session.setAttribute("belediye", slctBelediye);
			belediyeBean.submit();

		}
	}

	private void getBelediyeler() {
		belediyeList = new ArrayList<BelediyeDTO>();

		for (BelediyeDTO b : loginBean.getBelediyeList()) {
			belediyeList.add(b);
		}

		Collections.sort(belediyeList, new BelediyeComparator());

		belediyeListModel = new BelediyeDataModel(belediyeList);

	}

	public List<BelediyeDTO> getBelediyeList() {
		return belediyeList;
	}

	public void setBelediyeList(List<BelediyeDTO> belediyeList) {
		this.belediyeList = belediyeList;
	}

	public List<BelediyeDTO> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<BelediyeDTO> filteredList) {
		this.filteredList = filteredList;
	}

	public BelediyeDTO getSlctBelediye() {
		return slctBelediye;
	}

	public void setSlctBelediye(BelediyeDTO slctBelediye) {
		this.slctBelediye = slctBelediye;
	}

	public BelediyeDataModel getBelediyeListModel() {
		return belediyeListModel;
	}

	public void setBelediyeListModel(BelediyeDataModel belediyeListModel) {
		this.belediyeListModel = belediyeListModel;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

}
