package tr.org.mmo.asansor.common;

import java.io.Serializable;

 public  class ColumnModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 254946476779424621L;
	private String header;
    private String property;
    private int index;

    public ColumnModel(String header, String property,int index) {
        this.header = header;
        this.property = property;
        this.index=index;
    }

    public String getHeader() {
        return header;
    }

    public String getProperty() {
        return property;
    }

	public int getIndex() {
		return index;
	}
    
    
}