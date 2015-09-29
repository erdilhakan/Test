package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import tr.org.mmo.asansor.dto.BilgilendirmeDTO;

public class RandevuBilgiDataModel extends ListDataModel<BilgilendirmeDTO>
		implements SelectableDataModel<BilgilendirmeDTO>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2080312815255790747L;

	public RandevuBilgiDataModel() {
	}

	public RandevuBilgiDataModel(List<BilgilendirmeDTO> data) {
		super(data);
	}

	@Override
	public Object getRowKey(BilgilendirmeDTO bilgi) {
		return bilgi.getTcKimlikNo();
	}

	@SuppressWarnings("unchecked")
	@Override
	public BilgilendirmeDTO getRowData(String rowKey) {
		// In a real app, a more efficient way like a query by rowKey should be
		// implemented to deal with huge data

		List<BilgilendirmeDTO> bilgi = (List<BilgilendirmeDTO>) getWrappedData();

		for (BilgilendirmeDTO b : bilgi) {
			if (b.getTcKimlikNo() == Long.parseLong(rowKey))
				return b;
		}

		return null;

	}
}
