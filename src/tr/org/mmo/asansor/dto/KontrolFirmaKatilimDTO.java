package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class KontrolFirmaKatilimDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int kontrolId;
	private int firmaId;
	private boolean firmaKontroleKatildi=true;
	private String firmaGorevli1;
	private String firmaGorevli2;
	private String firmaGorevli3;
	private String firmaGorevli1Gorev;
	private String firmaGorevli2Gorev;
	private String firmaGorevli3Gorev;
	private String userId;
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
	public int getFirmaId() {
		return firmaId;
	}
	public void setFirmaId(int firmaId) {
		this.firmaId = firmaId;
	}
	public boolean isFirmaKontroleKatildi() {
		return firmaKontroleKatildi;
	}
	public void setFirmaKontroleKatildi(boolean firmaKontroleKatildi) {
		this.firmaKontroleKatildi = firmaKontroleKatildi;
	}
	public String getFirmaGorevli1() {
		return firmaGorevli1;
	}
	public void setFirmaGorevli1(String firmaGorevli1) {
		this.firmaGorevli1 = firmaGorevli1;
	}
	public String getFirmaGorevli2() {
		return firmaGorevli2;
	}
	public void setFirmaGorevli2(String firmaGorevli2) {
		this.firmaGorevli2 = firmaGorevli2;
	}
	public String getFirmaGorevli3() {
		return firmaGorevli3;
	}
	public void setFirmaGorevli3(String firmaGorevli3) {
		this.firmaGorevli3 = firmaGorevli3;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirmaGorevli1Gorev() {
		return firmaGorevli1Gorev;
	}
	public void setFirmaGorevli1Gorev(String firmaGorevli1Gorev) {
		this.firmaGorevli1Gorev = firmaGorevli1Gorev;
	}
	public String getFirmaGorevli2Gorev() {
		return firmaGorevli2Gorev;
	}
	public void setFirmaGorevli2Gorev(String firmaGorevli2Gorev) {
		this.firmaGorevli2Gorev = firmaGorevli2Gorev;
	}
	public String getFirmaGorevli3Gorev() {
		return firmaGorevli3Gorev;
	}
	public void setFirmaGorevli3Gorev(String firmaGorevli3Gorev) {
		this.firmaGorevli3Gorev = firmaGorevli3Gorev;
	}
	
	
	
}
