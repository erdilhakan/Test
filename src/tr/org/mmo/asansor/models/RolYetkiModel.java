package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.RolYetkiDTO;

public class RolYetkiModel extends ListDataModel<RolYetkiDTO> implements
		SelectableDataModel<RolYetkiDTO>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3280798839818116871L;

	public RolYetkiModel() {
	}

	public RolYetkiModel(List<RolYetkiDTO> data) {
		super(data);
	}

	@Override
	@SuppressWarnings("unchecked")
	public RolYetkiDTO getRowData(String rowKey) {

		List<RolYetkiDTO> list = (List<RolYetkiDTO>) getWrappedData();

		for (RolYetkiDTO b : list) {
			if (b.getId() == Integer.parseInt(rowKey))
				return b;
		}

		return null;
	}

	@Override
	public Object getRowKey(RolYetkiDTO r) {
		return r.getId();
	}
}
