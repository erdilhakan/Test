package tr.org.mmo.asansor.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import tr.org.mmo.asansor.dto.RaporDTO;

public class LazyRaporDataModel extends LazyDataModel<RaporDTO> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2080312815255790747L;
	private List<RaporDTO> dataSource;

	public LazyRaporDataModel(List<RaporDTO> dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public RaporDTO getRowData(String rowKey) {
		for (RaporDTO rapor : dataSource) {
			if (rapor.getRaporId() == Integer.parseInt(rowKey.trim()))
				return rapor;
		}

		return null;
	}

	@Override
	public Object getRowKey(RaporDTO rapor) {
		return rapor.getRaporId();
	}

	@Override
	public List<RaporDTO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {

		List<RaporDTO> data = new ArrayList<RaporDTO>();

		// filter
		for (RaporDTO rapor : dataSource) {
			boolean match = true;

			if (filters != null) {
				for (Iterator<String> it = filters.keySet().iterator(); it
						.hasNext();) {
					try {
						String filterProperty = it.next();
						Object filterValue = filters.get(filterProperty);
						String fieldValue = String.valueOf(rapor.getClass()
								.getField(filterProperty).get(rapor));

						if (filterValue == null
								|| fieldValue
										.startsWith(filterValue.toString())) {
							match = true;
						} else {
							match = false;
							break;
						}
					} catch (Exception e) {
						match = false;
					}
				}
			}

			if (match) {
				data.add(rapor);
			}
		}

		// sort
		if (sortField != null) {
			Collections.sort(data, new LazySorter(sortField, sortOrder));
		}

		// rowCount
		int dataSize = data.size();
		this.setRowCount(dataSize);

		// paginate
		if (dataSize > pageSize) {
			try {
				return data.subList(first, first + pageSize);
			} catch (IndexOutOfBoundsException e) {
				return data.subList(first, first + (dataSize % pageSize));
			}
		} else {
			return data;
		}
	}

}

