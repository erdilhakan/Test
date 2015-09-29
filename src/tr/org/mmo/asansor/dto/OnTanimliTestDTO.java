package tr.org.mmo.asansor.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OnTanimliTestDTO implements Serializable {
	private int id;
	private int kontrolId;
	private int parentId;
	private int soruId;
	private int onTanimliId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getKontrolId() {
		return kontrolId;
	}

	public void setKontrolId(int kontrolId) {
		this.kontrolId = kontrolId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getSoruId() {
		return soruId;
	}

	public void setSoruId(int soruId) {
		this.soruId = soruId;
	}

	public int getOnTanimliId() {
		return onTanimliId;
	}

	public void setOnTanimliId(int onTanimliId) {
		this.onTanimliId = onTanimliId;
	}

}
