package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.beans_.Randevu;
import tr.org.mmo.asansor.beans_.UavtBina;
import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.dto.BasvuruListeDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.BinaKisiDTO;
import tr.org.mmo.asansor.dto.BinaSorumlulukDTO;
import tr.org.mmo.asansor.dto.CurrVal;
import tr.org.mmo.asansor.dto.KontrolYapilamamaNedenDTO;
import tr.org.mmo.asansor.dto.OdemeAlinmayacakBinalarDTO;
import tr.org.mmo.asansor.dto.SozlesmeBinaTipleriDTO;
import tr.org.mmo.asansor.dto.YapiKonusuDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.managedbeans.BasvuruBean;
import tr.org.mmo.asansor.managedbeans.LoginBean;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Sorgular;
import tr.org.mmo.beans.CaddeSokak;
import tr.org.mmo.beans.Mahalle;

public class BinaDAOImpl implements BinaDAO {
	Connection con = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<YapiKonusuDTO> getYapiTipleri() throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<YapiKonusuDTO> list = new ArrayList<YapiKonusuDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (List<YapiKonusuDTO>) runner.query(con,
					Sorgular._GETYAPIKONULARI_.qry, new BeanListHandler(
							YapiKonusuDTO.class));
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<YapiKonusuDTO>() : list;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<BinaDTO> binaByAdaParselPafta(String ada, String parsel,
			String pafta) throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<BinaDTO> list = new ArrayList<BinaDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuilder sb = new StringBuilder();
			sb.append(Sorgular._BINA_ARA_BY_ADAPARSELPAFTA.qry);
			if (((LoginBean) Common.findBean("loginBean"))
					.getKullaniciBinaKapsam() != null
					&& ((LoginBean) Common.findBean("loginBean"))
							.getKullaniciBinaKapsam().length() > 1) {
				sb.append(" AND ");
				sb.append(((LoginBean) Common.findBean("loginBean"))
						.getKullaniciBinaKapsam());
			}
			list = (ArrayList<BinaDTO>) runner.query(con, sb.toString(),
					new BeanListHandler(BinaDTO.class), new Object[] { ada,
							parsel, pafta });
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<BinaDTO>() : list;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<BinaDTO> binaByTcKimlik(long tcKimlikNo)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<BinaDTO> list = new ArrayList<BinaDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuilder sb = new StringBuilder();
			sb.append(Sorgular._BINA_ARA_BY_TCKIMLIK.qry);
			if (((LoginBean) Common.findBean("loginBean")).isLoggedIn()) {
				if (((LoginBean) Common.findBean("loginBean"))
						.getKullaniciBinaKapsam() != null
						&& ((LoginBean) Common.findBean("loginBean"))
								.getKullaniciBinaKapsam().length() > 1) {
					sb.append(" AND ");
					sb.append(((LoginBean) Common.findBean("loginBean"))
							.getKullaniciBinaKapsam());
				}
			}
			list = (ArrayList<BinaDTO>) runner.query(con, sb.toString(),
					new BeanListHandler(BinaDTO.class), new Long(tcKimlikNo));
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<BinaDTO>() : list;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<BinaDTO> binaByIlIlce(int il, int ilce)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<BinaDTO> list = new ArrayList<BinaDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (ArrayList<BinaDTO>) runner.query(con,
					Sorgular._BINA_ARA_ILILCEDEN_.qry, new BeanListHandler(
							BinaDTO.class), new Object[] { il, ilce });
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<BinaDTO>() : list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<BinaDTO> binaByIlIlce() throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<BinaDTO> list = new ArrayList<BinaDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sorgu = new StringBuffer();

			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			if (loginBean != null && loginBean.isLoggedIn()) {
				sorgu.append(Sorgular._BINA_ARA_BIRIM_.qry);
				sorgu.append(loginBean.getKullaniciBinaKapsam());

			} else {
				sorgu.append(Sorgular._TUMBINALAR_.qry);
			}
			list = (ArrayList<BinaDTO>) runner.query(con, sorgu.toString(),
					new BeanListHandler(BinaDTO.class));
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<BinaDTO>() : list;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<BinaDTO> binaByBelediye(int belediyeKod) throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<BinaDTO> list = new ArrayList<BinaDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			

				

			 
			list = (ArrayList<BinaDTO>) runner.query(con, Sorgular._BINA_ARA_BY_BELEDIYEKOD_.qry,
					new BeanListHandler(BinaDTO.class),new Integer(belediyeKod));
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<BinaDTO>() : list;
	}
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public int binaEkle(BinaDTO bina) throws CreateException {
		con = DAOBase.instance().getConnection();
		QueryRunner runner = new QueryRunner();
		int binaId = 0;
        boolean uavtVar= false;
        BinaDTO kontrol = new BinaDTO();
       
        
		try {
			con.setAutoCommit(false);
			String kullaniciAdi = "";
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			if ((loginBean == null || !loginBean.isLoggedIn())
					&& FacesContext.getCurrentInstance().getViewRoot()
							.getViewId().equals("/basvuru.xhtml")) {
				try {
					BasvuruBean basvuruBean = (BasvuruBean) FacesContext
							.getCurrentInstance()
							.getApplication()
							.evaluateExpressionGet(
									FacesContext.getCurrentInstance(),
									"#{basvuruBean}", BasvuruBean.class);
					kullaniciAdi = String.valueOf(basvuruBean.getBasvuru()
							.getBasvuru().getBasvuruYapanTCKimlikNo());
				} catch (Exception e) {
					kullaniciAdi = "";
				}
			} else {
				kullaniciAdi = loginBean.getKullanici().getKullaniciAdi();
			}
			StringBuffer sb=new StringBuffer();
			// System.out.println(bina.getUavtKod());
			sb.append(Sorgular._UAVTKODKONTROL_.qry);
			kontrol= (BinaDTO)runner.query(con, sb.toString(), new BeanHandler(BinaDTO.class),new Object[] {bina.getUavtKod()});
			if(kontrol ==null){
				uavtVar=false;
			}else{
				 uavtVar=true;
				 FacesMessage msg=new FacesMessage(Messages._UAVTVAR_.getMesaj()+ " Mevcut olan bina id "+kontrol.getBinaId()+".");
				 FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			if(uavtVar==false){
			int i = runner.update(
					con,
					Sorgular._BINA_INSERT_.qry,
					new Object[] { bina.getIl(), bina.getIlce(),
							bina.getMahalle().trim(),
							bina.getCaddeSokak().trim(),
							bina.getBinaNo().trim(), bina.getBinaAdi().trim(),
							bina.getBelediye(), bina.getAda().trim(),
							bina.getParsel().trim(), bina.getPafta().trim(),
							bina.getYapiTip(), bina.getVergiNo(),
							bina.getVergiDairesi(),
							bina.getSozlesmeBinaTipId(), kullaniciAdi,
							bina.getUavtKod(), bina.getBucakKoyKod() });

			if (i > 0) {
				CurrVal cv = (CurrVal) runner.query(con,
						Sorgular._CURRVALBINA_.qry, new BeanHandler(
								CurrVal.class));
				binaId = cv.getCurrval();

				con.commit();
			}
			bina.setBinaId(binaId);
			((LoginBean) Common.findBean("loginBean")).getTumBinalar()
					.add(bina);
			 }
		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return binaId;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int binaEkle(BinaDTO bina, Connection con2) throws CreateException {
		
		QueryRunner runner = new QueryRunner();
		int binaId = 0;
		try {
		
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			int i = runner.update(
					con2,
					Sorgular._BINA_INSERT_.qry,
					new Object[] { bina.getIl(), bina.getIlce(),
							bina.getMahalle().trim(),
							bina.getCaddeSokak().trim(),
							bina.getBinaNo().trim(), bina.getBinaAdi().trim(),
							bina.getBelediye(), bina.getAda().trim(),
							bina.getParsel().trim(), bina.getPafta().trim(),
							bina.getYapiTip(), bina.getVergiNo(),
							bina.getVergiDairesi(),
							bina.getSozlesmeBinaTipId(),
							loginBean.getKullanici().getKullaniciAdi() });

			if (i > 0) {
				CurrVal cv = (CurrVal) runner.query(con,
						Sorgular._CURRVALBINA_.qry, new BeanHandler(
								CurrVal.class));
				binaId = cv.getCurrval();
				con2.commit();
			}
			bina.setBinaId(binaId);
			((LoginBean) Common.findBean("loginBean")).getTumBinalar()
					.add(bina);
		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), e);
		} 
		return binaId;

	}

	@Override
	public void binaGuncelle(BinaDTO bina) throws UpdateException,
			CreateException {
		try {
			con = DAOBase.instance().getConnection();
		} catch (CreateException e1) {
			throw new CreateException(Messages._CONNECTIONAC_.getMesaj(), e1);
		}
		QueryRunner runner = new QueryRunner();

		try {
			con.setAutoCommit(false);
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			String kullaniciAdi = "";

			if ((loginBean == null || !loginBean.isLoggedIn())
					&& FacesContext.getCurrentInstance().getViewRoot()
							.getViewId().equals("/basvuru.xhtml")) {
				try {
					BasvuruBean basvuruBean = (BasvuruBean) FacesContext
							.getCurrentInstance()
							.getApplication()
							.evaluateExpressionGet(
									FacesContext.getCurrentInstance(),
									"#{basvuruBean}", BasvuruBean.class);
					kullaniciAdi = String.valueOf(basvuruBean.getBasvuru()
							.getBasvuru().getBasvuruYapanTCKimlikNo());
				} catch (Exception e) {
					kullaniciAdi = "";
				}
			} else {
				kullaniciAdi = loginBean.getKullanici().getKullaniciAdi();
			}
			int i = runner.update(
					con,
					Sorgular._BINA_UPDATE_.qry,
					new Object[] { bina.getIl(), bina.getIlce(),
							bina.getMahalle().trim(),
							bina.getCaddeSokak().trim(),
							bina.getBinaNo().trim(), bina.getBinaAdi().trim(),
							bina.getBelediye(), bina.getAda().trim(),
							bina.getParsel().trim(), bina.getPafta().trim(),
							bina.getYapiTip(), bina.getVergiNo(),
							bina.getVergiDairesi(),
							bina.getSozlesmeBinaTipId(), kullaniciAdi,
							bina.getUavtKod(), bina.getBucakKoyKod(),
							bina.getBinaId() });

			if (i > 0) {
				con.commit();

			}
		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BinaDTO binaById(int binaId) throws ReadException {
		QueryRunner runner = new QueryRunner();
		BinaDTO bina = new BinaDTO();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuilder sb = new StringBuilder();
			sb.append(Sorgular._BINA_ARA_ID_.qry);
			try {
				if (((LoginBean) Common.findBean("loginBean")).isLoggedIn()){
				if (((LoginBean) Common.findBean("loginBean"))
						.getKullaniciBinaKapsam() != null
						&& ((LoginBean) Common.findBean("loginBean"))
								.getKullaniciBinaKapsam().length() > 1) {
					sb.append(" AND ");
					sb.append(((LoginBean) Common.findBean("loginBean"))
							.getKullaniciBinaKapsam());
				}
				}else{
					sb.append(" AND ");
					sb.append(" a.belediye =");
					sb.append(((LoginBean) Common.findBean("loginBean"))
							.getKullanici().getSicilNo());
				}
			} catch (NullPointerException e) {
				// e.printStackTrace() ;
			}
			bina = (BinaDTO) runner.query(con, sb.toString(), new BeanHandler(
					BinaDTO.class), new Object[] { binaId });
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return bina == null ? new BinaDTO() : bina;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BinaDTO binaByTescilno(int tescilno) throws ReadException {
		QueryRunner runner = new QueryRunner();
		BinaDTO bina = new BinaDTO();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			
			StringBuilder sb = new StringBuilder();
			sb.append(Sorgular._BINA_ARA_TESCILNO_.qry);
			try {
				if (((LoginBean) Common.findBean("loginBean")).isLoggedIn()){
				if (((LoginBean) Common.findBean("loginBean"))
						.getKullaniciBinaKapsam() != null
						&& ((LoginBean) Common.findBean("loginBean"))
								.getKullaniciBinaKapsam().length() > 1) {
					sb.append(" AND ");
					sb.append(((LoginBean) Common.findBean("loginBean"))
							.getKullaniciBinaKapsam());
				}
				}else{
					sb.append(" AND ");
					sb.append(" a.belediye =");
					sb.append(((LoginBean) Common.findBean("loginBean"))
							.getKullanici().getSicilNo());
				}
				
			} catch (NullPointerException e) {
				// e.printStackTrace() ;
			}
			bina = (BinaDTO) runner.query(con, sb.toString(), new BeanHandler(
					BinaDTO.class), new Object[] { tescilno });
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return bina == null ? new BinaDTO() : bina;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public BinaDTO binaByRandevuId(int randevuId) throws ReadException {
		QueryRunner runner = new QueryRunner();
		BinaDTO bina = new BinaDTO();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			bina = (BinaDTO) runner.query(con,
					Sorgular._BINA_ARA_RANDEVUID_.qry, new BeanHandler(
							BinaDTO.class), new Object[] { randevuId });
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return bina == null ? new BinaDTO() : bina;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public BinaDTO binaByRandevuId(Connection con, int randevuId)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		BinaDTO bina = new BinaDTO();
		try {

			bina = (BinaDTO) runner.query(con,
					Sorgular._BINA_ARA_RANDEVUID_.qry, new BeanHandler(
							BinaDTO.class), new Object[] { randevuId });
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		}

		return bina == null ? new BinaDTO() : bina;

	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public BinaDTO binaForRaporGonder(Connection con, int randevuId)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		BinaDTO bina = new BinaDTO();
		try {

			bina = (BinaDTO) runner.query(con,
					Sorgular._BINA_ARA_RANDEVUID1_.qry, new BeanHandler(
							BinaDTO.class), new Object[] { randevuId });
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		}

		return bina == null ? new BinaDTO() : bina;

	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<BinaSorumlulukDTO> getBinaSorumlulukTur()
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<BinaSorumlulukDTO> list = new ArrayList<BinaSorumlulukDTO>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (ArrayList<BinaSorumlulukDTO>) runner.query(con,
					Sorgular._GETBINASORUMLULUKTUR_.qry, new BeanListHandler(
							BinaSorumlulukDTO.class));
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int binaKisiEkle(BinaKisiDTO binaKisi) throws CreateException {

		con = DAOBase.instance().getConnection();
		QueryRunner runner = new QueryRunner();
		int id = 0;

		try {
			con.setAutoCommit(false);
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");

			int i = runner.update(
					con,
					Sorgular._BINAKISIINSERT_.qry,
					new Object[] { binaKisi.getBinaId(),
							binaKisi.getTcKimlikNo(), binaKisi.getAdi(),
							binaKisi.getSoyadi(), binaKisi.getSorumlulukTuru(),
							binaKisi.getTelefonNo(),
							binaKisi.getTelefonNoGsm(), binaKisi.getePosta(),
							loginBean.getKullanici().getKullaniciAdi(),binaKisi.getTelefonNoDahili() });

			if (i > 0) {
				CurrVal cv = (CurrVal) runner.query(con,
						Sorgular._CURRVALBINAKISI_.qry, new BeanHandler(
								CurrVal.class));
				id = cv.getCurrval();
				con.commit();
			}
		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return id;

	}

	@Override
	public void binaKisiEkleBasvurudan(BinaKisiDTO binaKisi)
			throws CreateException {

		con = DAOBase.instance().getConnection();
		QueryRunner runner = new QueryRunner();

		try {
			con.setAutoCommit(false);

			try {

				String kullanici;
				LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
				kullanici = loginBean.isLoggedIn() ? loginBean.getUser()
						: String.valueOf("Başvurudan");

				if (!getBinaKisi(con, binaKisi.getTcKimlikNo(),
						binaKisi.getBinaId())) {
					int i = runner.update(
							con,
							Sorgular._BINAKISIINSERT_.qry,
							new Object[] { binaKisi.getBinaId(),
									binaKisi.getTcKimlikNo(),
									binaKisi.getAdi(), binaKisi.getSoyadi(),
									binaKisi.getSorumlulukTuru(),
									binaKisi.getTelefonNo(),
									binaKisi.getTelefonNoGsm(),
									binaKisi.getePosta(), kullanici,
									binaKisi.getTelefonNoDahili()});

					if (i > 0)
						con.commit();
					else
						con.rollback();
				}
			} catch (ReadException e) {
				throw new CreateException(Messages._SQL_500_.getMesaj(), e);
			}
		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}

	}

	@Override
	public void binaKisiGuncelle(BinaKisiDTO binaKisi) throws UpdateException {

		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			String kullanici;
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			kullanici = loginBean.isLoggedIn() ? loginBean.getUser() : String
					.valueOf("Başvurudan");

			int i = runner.update(con, Sorgular._BINAKISIUPDATE_.qry,
					new Object[] { binaKisi.getTcKimlikNo(), binaKisi.getAdi(),
							binaKisi.getSoyadi(), binaKisi.getSorumlulukTuru(),
							binaKisi.getTelefonNo(),
							binaKisi.getTelefonNoGsm(), binaKisi.getePosta(),
							kullanici,binaKisi.getTelefonNoDahili(), binaKisi.getId() });

			if (i > 0) {

				con.commit();
			}
		} catch (CreateException ex) {
			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), ex);
		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}

	}

	private boolean getBinaKisi(Connection con, long tcKimlikNo, int binaId)
			throws ReadException {

		QueryRunner runner = new QueryRunner();

		BinaKisiDTO binaKisi = new BinaKisiDTO();
		try {

			binaKisi = (BinaKisiDTO) runner.query(con,
					Sorgular._GETBINAKISI_.qry, new BeanHandler<BinaKisiDTO>(
							BinaKisiDTO.class), new Object[] { tcKimlikNo,
							binaId });

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		}
		return (binaKisi == null ? new BinaKisiDTO() : binaKisi).getId() > 0 ? true
				: false;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BinaKisiDTO> getBinaKisiler(Connection con, int binaId)
			throws ReadException {
		List<BinaKisiDTO> list = new ArrayList<BinaKisiDTO>();
		QueryRunner runner = new QueryRunner();

		try {

			list = (ArrayList<BinaKisiDTO>) runner.query(con,
					Sorgular._GETBINAKISILER_.qry, new BeanListHandler(
							BinaKisiDTO.class), new Integer(binaId));

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		}

		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BinaKisiDTO> getBinaKisiler(int binaId) throws ReadException {
		List<BinaKisiDTO> list = new ArrayList<BinaKisiDTO>();
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (ArrayList<BinaKisiDTO>) runner.query(con,
					Sorgular._GETBINAKISILER_.qry, new BeanListHandler(
							BinaKisiDTO.class), new Integer(binaId));

		} catch (CreateException ex) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), ex);
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}

		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int binaKontrol(BinaDTO bina) throws CRUDException {
		int binaId = 0;
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			BinaDTO b = (BinaDTO) runner.query(con,
					Sorgular._BINA_ARA_BY_FULLPARAM_.qry, new BeanHandler(
							BinaDTO.class),
					new Object[] { bina.getIl(), bina.getIlce(),
							bina.getMahalle().trim().toUpperCase(),
							bina.getCaddeSokak().trim().toUpperCase(),
							bina.getBinaNo().trim().toUpperCase() });
			binaId = b == null ? 0 : b.getBinaId();
			if (binaId <= 0) {
				binaId = binaEkle(bina, con);
			}
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}

		return binaId;

	}

	@Override
	public List<Randevu> getBasvuruRandevuByBinaId(int binaId)
			throws ReadException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Randevu> list = new ArrayList<Randevu>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(Sorgular._BASVURUVERANDEVULAR.qry);
			pstmt.setInt(1, binaId);
			rs = pstmt.executeQuery();
			int basvuruId = 0;
			Randevu r = new Randevu();
			ArrayList<Kullanici> muhendisler = new ArrayList<Kullanici>();
			boolean eklensin = false;
			while (rs.next()) {

				if (basvuruId == rs.getInt("BASVURU_ID")) {
					Kullanici m = new Kullanici();
					m.setAdi(rs.getString("ADI"));
					m.setSoyadi(rs.getString("SOYADI"));
					m.setOnayYetkisi(rs.getString("SORUMLU"));
					m.setSicilNo(String.valueOf(rs.getInt("MUHENDIS_SICILNO")));
					muhendisler.add(m);
					eklensin = false;
				} else {
					if (basvuruId != 0) {
						r.setMuhendisler(muhendisler);
						list.add(r);
						eklensin = true;
						r = new Randevu();
						muhendisler = new ArrayList<Kullanici>();
					}
					basvuruId = rs.getInt("BASVURU_ID");
					BasvuruListeDTO b = new BasvuruListeDTO();
					b.setBasvuruId(rs.getInt("BASVURU_ID"));
					b.setBasvuruTarihi(rs.getDate("BASVURU_TARIHI"));
					b.setAdiSoyadi(rs.getString("BASVURUYAPAN"));
					b.setTelefonNo(rs.getString("TELEFON_NO"));
					r.setBasvuru(b);
					Kullanici m = new Kullanici();
					m.setAdi(rs.getString("ADI"));
					m.setSoyadi(rs.getString("SOYADI"));
					m.setOnayYetkisi(rs.getString("SORUMLU"));
					m.setSicilNo(String.valueOf(rs.getInt("MUHENDIS_SICILNO")));
					muhendisler.add(m);
					r.setRandevuId(rs.getInt("RANDEVU_ID"));
					r.setRandevuTarih(rs.getDate("RANDEVU_TARIHI"));
					r.setRandevuSaati(rs.getTime("RANDEVU_SAATI"));

					r.setFirmaCihazList(new FirmaBusiness()
							.getAsansorBakimciFirmalar(con, basvuruId));

				}

			}
			if (!eklensin) {
				r.setMuhendisler(muhendisler);
				list.add(r);
			}
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(con);
		}
		return list;
	}

	@Override
	public List<SozlesmeBinaTipleriDTO> getSozlesmeBinaTipleri()
			throws ReadException {

		List<SozlesmeBinaTipleriDTO> list = new ArrayList<SozlesmeBinaTipleriDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (ArrayList<SozlesmeBinaTipleriDTO>) runner.query(con,
					Sorgular._GETSOZLESMEBINATIPLERI_.qry,
					new BeanListHandler<SozlesmeBinaTipleriDTO>(
							SozlesmeBinaTipleriDTO.class), new Object[] {});

		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}

		return list;

	}

	@Override
	public List<KontrolYapilamamaNedenDTO> getKontrolYapilmamaNedenKodlari()
			throws ReadException {
		List<KontrolYapilamamaNedenDTO> list = new ArrayList<KontrolYapilamamaNedenDTO>();
		QueryRunner runner = new QueryRunner();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			list = (ArrayList<KontrolYapilamamaNedenDTO>) runner.query(con,
					Sorgular._GETKONTROLYAPILMAMANEDENKODLARI_.qry,
					new BeanListHandler<KontrolYapilamamaNedenDTO>(
							KontrolYapilamamaNedenDTO.class), new Object[] {});

		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}

		return list;
	}

	@Override
	public void odemeAlinmamaDurumuEkle(int binaId, String odemeAlinmazAciklama)
			throws CreateException {
		con = DAOBase.instance().getConnection();
		QueryRunner runner = new QueryRunner();

		try {
			con.setAutoCommit(false);

			String kullanici = (String) ((LoginBean) Common
					.findBean("loginBean")).getUser();
			int i = runner.update(con,
					Sorgular._BINA_ODEME_ALINMAMA_DURUMU_INSERT_.qry,
					new Object[] { binaId, odemeAlinmazAciklama, kullanici,
							kullanici });

			if (i > 0) {

				con.commit();
			}

		} catch (SQLException e) {
			throw new CreateException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}

	}

	@Override
	public void odemeAlinmamaDurumIptal(int id) throws UpdateException {
		try {
			con = DAOBase.instance().getConnection();
		} catch (CreateException e1) {
			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), e1);
		}
		QueryRunner runner = new QueryRunner();

		try {
			con.setAutoCommit(false);

			String kullanici = (String) ((LoginBean) Common
					.findBean("loginBean")).getUser();
			int i = runner.update(con,
					Sorgular._BINA_ODEME_ALINMAMA_DURUMU_IPTAL_.qry,
					new Object[] { kullanici, kullanici, id });

			if (i > 0) {

				con.commit();
			}

		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}

	}

	@Override
	public List<OdemeAlinmayacakBinalarDTO> getOdemeAlinmamaDurumu(int binaId)
			throws ReadException {
		try {
			con = DAOBase.instance().getConnection();
		} catch (CreateException e1) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), e1);
		}
		QueryRunner runner = new QueryRunner();
		List<OdemeAlinmayacakBinalarDTO> list = new ArrayList<OdemeAlinmayacakBinalarDTO>();
		try {
			con.setAutoCommit(false);

			list = (ArrayList<OdemeAlinmayacakBinalarDTO>) runner.query(con,
					Sorgular._BINA_ODEME_ALINMAMA_DURUMU_.qry,
					new BeanListHandler<OdemeAlinmayacakBinalarDTO>(
							OdemeAlinmayacakBinalarDTO.class),
					new Object[] { binaId });

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}
		return list;

	}

	@SuppressWarnings("unused")
	public boolean odemeAlinacakMi(Connection con2, int binaId)
			throws ReadException {

		List<OdemeAlinmayacakBinalarDTO> list = new ArrayList<OdemeAlinmayacakBinalarDTO>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con2.prepareStatement(Sorgular._ODEME_ALINMADURUMU_.qry);
			stmt.setInt(1, binaId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

		}
	}

	@SuppressWarnings("unused")
	@Override
	public boolean odemeAlinacakMi(int binaId) throws ReadException,
			CreateException {

		List<OdemeAlinmayacakBinalarDTO> list = new ArrayList<OdemeAlinmayacakBinalarDTO>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(Sorgular._ODEME_ALINMADURUMU_.qry);
			stmt.setInt(1, binaId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {
			DbUtils.closeQuietly(con);

		}
	}

	@Override
	public String getBinaAdiByBasvuruId(int basvuruId) throws ReadException {
		QueryRunner runner = new QueryRunner();
		BinaDTO bina = new BinaDTO();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuilder sb = new StringBuilder();
			sb.append(Sorgular._BINA_ARA_BY_BASVURU_ID_.qry);
			try {

				if (((LoginBean) Common.findBean("loginBean"))
						.getKullaniciBinaKapsam() != null
						&& ((LoginBean) Common.findBean("loginBean"))
								.getKullaniciBinaKapsam().length() > 1) {
					sb.append(" AND ");
					sb.append(((LoginBean) Common.findBean("loginBean"))
							.getKullaniciBinaKapsam());
				}
			} catch (NullPointerException e) {
				// e.printStackTrace() ;
			}
			bina = (BinaDTO) runner.query(con, sb.toString(),
					new BeanHandler<BinaDTO>(BinaDTO.class),
					new Object[] { basvuruId });
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return bina == null ? "" : bina.getBinaAdi();
	}

	public BinaDTO getDeneme(int binaId) {
		BinaDTO bina = new BinaDTO();
		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			bina = runner.query(con,
					"select bina_adi as binaAdi from akm.bina where bina_id=?",
					new BeanHandler<BinaDTO>(BinaDTO.class),
					new Object[] { binaId });
			//
			// pstmt=con.prepareStatement("select * from akm.bina where bina_id=?");
			// pstmt.setInt(1, binaId);
			// rs=pstmt.executeQuery();
			// if(rs.next()){
			// bina.setBinaAdi(rs.getString("BINAADI"));
			// }
			//

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CreateException e) {

			e.printStackTrace();
		}

		finally {
			// rs.close();
			// pstmt.close();
			// con.close();
			DbUtils.closeQuietly(con);
		}
		return bina;
	}

	@Override
	public void setCoordinat(int binaId, double lat, double lon)
			throws UpdateException {

		QueryRunner runner = new QueryRunner();

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
			int i = runner
					.update(con, Sorgular._SETBINACOORDINAT_.qry,
							new Object[] { lat, lon,
									loginBean.getKullanici().getKullaniciAdi(),
									binaId });
			if (i > 0)
				con.commit();
			else
				con.rollback();

		} catch (SQLException e) {
			throw new UpdateException(Messages._SQL_500_.getMesaj(), null);
		} catch (CreateException e) {
			throw new UpdateException(Messages._CONNECTIONAC_.getMesaj(), null);
		}

		finally {

			DbUtils.closeQuietly(con);
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Istatistik> sonCalisilanBinalar(Date tarih)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<Istatistik> list = new ArrayList<Istatistik>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();

			sb.append(Sorgular._SONCALISILANBINALAR_.qry);
			if (((LoginBean) Common.findBean("loginBean"))
					.getKullaniciBinaKapsam() != null
					&& ((LoginBean) Common.findBean("loginBean"))
							.getKullaniciBinaKapsam().length() > 1) {
				sb.append(" AND ");
				sb.append(((LoginBean) Common.findBean("loginBean"))
						.getKullaniciBinaKapsam().replace("a.", "bina."));
			}
			list = (List<Istatistik>) runner.query(con, sb.toString(),
					new BeanListHandler(Istatistik.class),
					new Object[] { new java.sql.Date(tarih.getTime()) });

			for (Istatistik i : list) {
				List<Istatistik> l = new ArrayList<Istatistik>();
				l = basvurulanAsansorler(con, i.getBasvuruId());
				int asansorAdet = 0;
				int eksiklikKontrolAdet = 0;
				int normalKontrolAdet = 0;

				for (Istatistik i1 : l) {
					asansorAdet += i1.getAsansorAdet();
					eksiklikKontrolAdet += i1.getEksiklikKontrolu();
					normalKontrolAdet += i1.getNormalKontrol();
				}
				if (i.getRandevuId() > 0)
					i.setRandevuAdet(1);
				i.setEksiklikKontrolu(eksiklikKontrolAdet);
				i.setAsansorAdet(asansorAdet);
				i.setNormalKontrol(normalKontrolAdet);

			}
		} catch (CreateException e) {
			throw new ReadException(Messages._CONNECTIONAC_.getMesaj(), null);

		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		}

		finally {
			DbUtils.closeQuietly(con);
		}
		return list;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Istatistik> basvurulanAsansorler(Connection con, int basvuruId)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		List<Istatistik> list = new ArrayList<Istatistik>();
		try {

			list = (List<Istatistik>) runner.query(con,
					Sorgular._BASVURULANASANSOR_.qry, new BeanListHandler(
							Istatistik.class), new Object[] { basvuruId });
		} catch (SQLException e) {
			throw new ReadException(Messages._SQL001_.getMesaj(), null);
		}

		return list;
	}

	@Override
	public void setMahalleFromUavt(List<Mahalle> list) throws CreateException,
			DeleteException {
		PreparedStatement pstm = null;

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);	

			pstm = con.prepareStatement(Sorgular._INSERTMAHALLE_.qry);
			for (Mahalle m1 : list) {

				pstm.setInt(1, m1.getKod());
				pstm.setString(2, m1.getAd());
				pstm.setInt(3, m1.getTanitimKodu());
				pstm.setString(4, m1.getTip());
				pstm.setInt(5, m1.getYetkiliIdareKodu());
				pstm.setInt(6, m1.getKoyKodu());
				pstm.addBatch();
			}

			int[] i = pstm.executeBatch();
			if (i.length > 0) {
				con.commit();
			} else {
				con.rollback();
			}
		} catch (SQLException e) {
			throw new CreateException(Messages._SQL001_.getMesaj(), null);

		} finally {
			DbUtils.closeQuietly(pstm);
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public void setCsbmFromUavt(List<CaddeSokak> list) throws CreateException,
			DeleteException {
		PreparedStatement pstm = null;

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			pstm = con.prepareStatement(Sorgular._INSERTCSBM_.qry);
			for (CaddeSokak c1 : list) {

				pstm.setInt(1, c1.getKod());
				pstm.setString(2, c1.getAd());
				pstm.setString(3, c1.getTanitimKodu());
				pstm.setString(4, c1.getTip());
				pstm.setInt(5, c1.getMahalleKodu());
				pstm.addBatch();
			}
			int[] i = pstm.executeBatch();
			if (i.length > 0) {
				con.commit();
			} else {
				con.rollback();
			}
		} catch (SQLException e) {
			throw new CreateException(Messages._SQL001_.getMesaj(), null);

		} finally {
			DbUtils.closeQuietly(pstm);
			DbUtils.closeQuietly(con);
		}

	}

	@Override
	public ArrayList<UavtBina> getUavtBinaTableByCsbmKod(long csbmKod)
			throws ReadException {
		QueryRunner runner = new QueryRunner();
		ArrayList<UavtBina> list = new ArrayList<UavtBina>();
		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			list = (ArrayList<UavtBina>) runner.query(con,
					Sorgular._GETUAVTBINA_.qry, new BeanListHandler<UavtBina>(
							UavtBina.class), new Long(csbmKod));
		} catch (Exception e) {
			throw new ReadException(Messages._SQL_500_.getMesaj(), e);
		} finally {

			DbUtils.closeQuietly(con);
		}
		return list == null ? new ArrayList<UavtBina>() : list;

	}

	@Override
	public void setBinaFromUavt(List<UavtBina> uavtdenEklenecek)
			throws CreateException {
		PreparedStatement pstm = null;

		try {
			con = DAOBase.instance().getConnection();
			con.setAutoCommit(false);

			pstm = con.prepareStatement(Sorgular._INSERTUAVTBINA_.qry);
			for (UavtBina c1 : uavtdenEklenecek) {
				pstm.setLong(1, c1.getKod());
				pstm.setString(2, c1.getDisKapiNo());
				pstm.setString(3, c1.getSiteAdi());
				pstm.setString(4, c1.getBlokAdi());
				pstm.setString(5, c1.getPostaKodu());
				pstm.setString(6, c1.getEsBinaKodu());
				pstm.setString(7, c1.getNitelik());
				pstm.setInt(8, c1.getCsbmKodu());
				pstm.setString(9, c1.getAda());
				pstm.setString(10, c1.getPafta());
				pstm.setString(11, c1.getParsel());
				pstm.setInt(12, c1.getBinaNo());
				pstm.addBatch();
			}
			int[] i = pstm.executeBatch();
			if (i.length > 0) {
				con.commit();
			} else {
				con.rollback();
			}
		} catch (SQLException e) {
			throw new CreateException(Messages._SQL001_.getMesaj(), null);

		} finally {
			DbUtils.closeQuietly(pstm);
			DbUtils.closeQuietly(con);
		}

	}
}
