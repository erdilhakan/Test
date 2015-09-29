package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.EskiRaporDTO;

public class EskiRaporDataModel extends ListDataModel<EskiRaporDTO> implements
		SelectableDataModel<EskiRaporDTO>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2080312815255790747L;

	public EskiRaporDataModel() {
	}

	public EskiRaporDataModel(List<EskiRaporDTO> data) {
		super(data);
	}

	@Override
	public Object getRowKey(EskiRaporDTO rapor) {
		return rapor.getRaporId();
	}

	@Override
	public EskiRaporDTO getRowData(String rowKey) {
		// In a real app, a more efficient way like a query by rowKey should be
		// implemented to deal with huge data

		@SuppressWarnings("unchecked")
		List<EskiRaporDTO> raporlar = (List<EskiRaporDTO>) getWrappedData();

		for (EskiRaporDTO r : raporlar) {
			if (r.getRaporId() == Integer.parseInt(rowKey))
				return r;
		}

		return null;

	}
}
