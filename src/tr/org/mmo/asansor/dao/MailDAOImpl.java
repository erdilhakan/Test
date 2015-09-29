package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import tr.org.mmo.asansor.dto.BasvuruDTO;
import tr.org.mmo.asansor.dto.BilgilendirmeDTO;
import tr.org.mmo.asansor.dto.SubeDTO;
import tr.org.mmo.asansor.dto.SubeTemsilcilikHesapDTO;
import tr.org.mmo.asansor.dto.TemsilcilikDTO;
import tr.org.mmo.asansor.dto.TemsilcilikIlIlceDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.util.Messages;

import com.sun.mail.smtp.SMTPTransport;

public class MailDAOImpl implements MailDAO {

	Connection con = null;
	String userName;
	String password;
	String hostAddress;
	String connect;
	int port;
	List<SubeTemsilcilikHesapDTO> hesapNumaralari = new ArrayList<SubeTemsilcilikHesapDTO>();

	public MailDAOImpl() {
		super();
		userName = userNameK;
		hostAddress = hostAddressK;
		connect = connectK;
		port = portK;

	}

	public MailDAOImpl(int il) {
		boolean ok = false;

		for (SubeDTO s : sessionBean.getSubeler()) {
			if (s.getIl() == il) {
				userName = s.getKullaniciAdi();
				password = s.getParola();
				hostAddress = s.getHostAdres();
				connect = s.getSmtp();
				port = s.getPort();
				hesapNumaralari = sessionBean.getSubeTemsilcilikHesapMap().get(
						s.getKod());
				ok = true;
				break;

			}
		}
		if (!ok) {
			for (TemsilcilikDTO t : sessionBean.getTemsilcilikler()) {
				if (ok)
					break;
				for (TemsilcilikIlIlceDTO tiid : t.getTemsilcilikIller()) {
					if (tiid.getIl() == il) {
						setMailSettings(t.getSubesi());
						hesapNumaralari = sessionBean
								.getSubeTemsilcilikHesapMap()
								.get(t.getSubesi());
						ok = true;
						break;

					}
				}
			}
		}
	}

	private void setMailSettings(int subeKodu) {
		for (SubeDTO s : sessionBean.getSubeler()) {
			if (s.getKod() == subeKodu) {
				userName = s.getKullaniciAdi();
				password = s.getParola();
				hostAddress = s.getHostAdres();
				connect = s.getSmtp();
				port = s.getPort();
				break;

			}
		}

	}

	@Override
	public void prepareSifre(String content, String recipientMail)
			throws CRUDException {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8' ?>");

		sb.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
		sb.append("<html xmlns='http://www.w3.org/1999/xhtml'>");

		sb.append("<head>");
		sb.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");
		sb.append("<style type=\"text/css\">");

		sb.append("div.example {border: #603 solid; padding: 0.5em; margin: 1em 2em; -moz-box-shadow: -1px 0 black, 0 1px black ;box-shadow: -1px 0 black, 0 1px black; -webkit-box-shadow: -1px 0 black, 0 1px black ;}");

		sb.append("div.example h3 {font-size: x-large; display: block; text-align: center;    border: none}");

		sb.append("  div.example1 {background: #CCF; padding: 1em}");
		sb.append(" h3.example2 {color: blue;  text-shadow: -1px 0 black, 0 1px black,  1px 0 black, 0 -1px black;}");
		sb.append("h1.example3 {color:red; text-shadow: -1px 0 black, 0 1px black,  1px 0 black, 0 -1px black;text-decoration:underline}");
		sb.append(" div.example4 {font-size:9px;color: #000;  text-shadow: -1px 0 black, 0 1px black,  1px 0 black, 0 -1px black;font-style:italic}");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");

		sb.append("<div class=example>");
		sb.append(" <div class=example1>");
		sb.append(" <h3 class=example2>Parolanız</h3>");
		sb.append(" <h1 class=example3>");
		sb.append(content);
		sb.append("</h1>");
		sb.append(" <h3 class=example2>olarak değiştirilmiştir</h3>");
		sb.append("</br>");

		sb.append(" <div class=example4>");
		sb.append("<marquee width=\"400px\" direction=\"left\" scrollamount=\"5\">");
		sb.append("Parolanızı değiştirerek kullanınız");
		sb.append("</marquee>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append(" </div>");
		sb.append("<body>");
		prepareMail(recipientMail, subject, sb.toString());
	}

	@Override
	public void prepareMail(String recipientMail, String subject, String body)
			throws CRUDException {
		try {

			sendMail(recipientMail, subject, body);

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CRUDException(e.getMessage(), null);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CRUDException(e.getMessage(), null);
		}

	}

	@Override
	public void sendMail(String recipientEMail, String subject, String content)
			throws MessagingException, RuntimeException {
		if (hostAddress != null && !hostAddress.trim().equals("")) {
			// Security.addProvider(new
			// com.sun.net.ssl.internal.ssl.Provider());
			// önceki

			final String SSL_FACTORY /*="";*/
			
			= "javax.net.ssl.SSLSocketFactory";

			// Get a properties object

			Properties props = System.getProperties();
			props.setProperty("mail.smtps.host",
					String.format("smtp.%s", hostAddress));
			props.setProperty("mail.transport.protocol", "smtp");
			
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.port", String.valueOf(port));
			props.setProperty("mail.smtp.socketFactory.port",
					String.valueOf(port));
			props.setProperty("mail.smtps.auth", "true");
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtps.quitwait", "false");

			// Session session=Session.getInstance(props,null); önceki
			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(userName,
									password);
						}
					});
			// Create a new message

			final MimeMessage msg = new MimeMessage(session);

			// set the from and to fields

			msg.setFrom(new InternetAddress(hostAddress));
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipientEMail, false));

			msg.setSubject(subject, "UTF-8");
			msg.setText(content, "UTF-8");
			msg.setSentDate(new Date());

			msg.setContent(content, "text/html; charset=UTF-8");
			msg.setHeader("Content-Encoding", "UTF-8");

			SMTPTransport smtpTransport = (SMTPTransport) session
					.getTransport("smtps");
			smtpTransport.connect(connect, userName, password);
			smtpTransport.sendMessage(msg, msg.getAllRecipients());
			smtpTransport.close();
			//System.out.println(props.);
		} else {
			new MessagingException("E-posta için tanımlı bir adres bulunamadı");
		}

	}

	public void prepareRandevu(BilgilendirmeDTO bilgi) throws CRUDException {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version='1.0' encoding='UTF-8' ?>");

			sb.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
			sb.append("<html xmlns='http://www.w3.org/1999/xhtml'>");

			sb.append("<head>");
			sb.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");

			sb.append("</head>");
			sb.append("<body>");

			sb.append("<table>");
			sb.append("<tr>");
			sb.append("<td>");
			sb.append("Bina Adı :");
			sb.append("</td>");
			sb.append("<td>");
			sb.append(bilgi.getBinaAdi());
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>");
			sb.append("Mahalle :");
			sb.append("</td>");
			sb.append("<td>");
			sb.append(bilgi.getMahalle());
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>");
			sb.append("Cadde/Sokak :");
			sb.append("</td>");
			sb.append("<td>");
			sb.append(bilgi.getCaddeSokak());
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>");
			sb.append("Bina No :");
			sb.append("</td>");
			sb.append("<td>");
			sb.append(bilgi.getBinaNo());
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>");
			sb.append("İlçe :");
			sb.append("</td>");
			sb.append("<td>");
			sb.append(bilgi.getIlce());
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>");
			sb.append("İl:");
			sb.append("</td>");
			sb.append("<td>");
			sb.append(bilgi.getIl());
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>");
			sb.append("Kontrol Tarihi/Saati :");
			sb.append("</td>");
			sb.append("<td>");
			sb.append(bilgi.getRandevuTarihi());
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("<table>");
			sb.append("<br/>");
			sb.append("Sayın ").append(bilgi.getAdiSoyadi()).append("<br/>");
			sb.append("Yukarıda adres bilgisi bulunan binanın  asasörlerinin kontrolü başvuruya ilişkin yukarıda belirtilen ");
			sb.append("tarih ve saatte yapılacaktır.");
			sb.append("<br/>").append("Bilgi edinmenizi rica ederiz...")
					.append("<br/>")
					.append("                     Makina Mühendisleri Odası");

			sb.append("<body>");
			prepareMail(bilgi.getePosta(), subjectRandevu, sb.toString());
		} catch (Exception e) {
			throw new CRUDException(Messages._GENEL_.getMesaj(), null);
		}
	}

	public void prepareBasvuru(BasvuruDTO basvuru, double odemeTutari)
			throws CRUDException {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version='1.0' encoding='UTF-8' ?>");

			sb.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
			sb.append("<html xmlns='http://www.w3.org/1999/xhtml' ").append(
					" xmlns:p='http://primefaces.org/ui'>");

			sb.append("<head>");
			sb.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");

			sb.append("</head>");
			sb.append("<body>");

			sb.append("<br/>");
			sb.append("Sayın ").append(basvuru.getBasvuruYapanAdi())
					.append(" ").append(basvuru.getBasvuruYapanSoyadi())
					.append("<br/>");
			sb.append(basvuru.getBasvuruId())
					.append(" numaralı asansör kontrol başvurunuza ilişkin ödeme tutarınız,")
					.append(odemeTutari).append(" TL dir.").append("<br/>");
			sb.append(
					"Ödemeyi aşağıda belirtilen IBAN numaralarından birisine yapabilirsiniz.")
					.append("<br/>");
			sb.append("<table>");
			for (SubeTemsilcilikHesapDTO sbt : hesapNumaralari == null ? new ArrayList<SubeTemsilcilikHesapDTO>()
					: hesapNumaralari) {
				sb.append("<tr>");
				sb.append("<td>");
				sb.append(sbt.getBankaAdi());
				sb.append("</td>");
				sb.append("<td>");
				sb.append(sbt.getIban());
				sb.append("</td>");
				sb.append("</tr>");
			}
			sb.append("<table>");

			sb.append("<br/>").append("Bilgi edinmenizi rica ederiz...")
					.append("<br/>")
					.append("                     Makina Mühendisleri Odası");

			sb.append("<body>");
			prepareMail(basvuru.getePosta(), subjectBasvuru, sb.toString());
		} catch (Exception e) {
			throw new CRUDException(Messages._GENEL_.getMesaj(), null);
		}

	}
}
