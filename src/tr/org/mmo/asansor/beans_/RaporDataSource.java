package tr.org.mmo.asansor.beans_;

public class RaporDataSource {
	/*
	 * değişken adları ireport field isimleri ile aynı olmalı ireport' a
	 * gönderilecek datasource list generic tipi bu class olmalı
	 */
	private String sira;
	private String fKonu1;
	private String fSonuc1;
	private String fKonu2;
	private String fSonuc2;

	public String getfKonu1() {
		return fKonu1;
	}

	public void setfKonu1(String fKonu1) {
		this.fKonu1 = fKonu1;
	}

	public String getfSonuc1() {
		return fSonuc1;
	}

	public void setfSonuc1(String fSonuc1) {
		this.fSonuc1 = fSonuc1;
	}

	public String getfKonu2() {
		return fKonu2;
	}

	public void setfKonu2(String fKonu2) {
		this.fKonu2 = fKonu2;
	}

	public String getfSonuc2() {
		return fSonuc2;
	}

	public void setfSonuc2(String fSonuc2) {
		this.fSonuc2 = fSonuc2;
	}

	public String getSira() {
		return sira;
	}

	public void setSira(String sira) {
		this.sira = sira;
	}

}
