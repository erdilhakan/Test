package tr.org.mmo.asansor.dao;

import javax.mail.MessagingException;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.managedbeans.SessionBean;

public interface MailDAO {
	SessionBean sessionBean = Common.findBean("sessionBean");
	static String subject = "Parola Yenileme";
	static String subjectRandevu = "Asansör Kontrolü için Randevu Bilgilendirmesi";
	static String subjectBasvuru = "Başvurunuz Hakkında";
	static String userNameK = "noreply";
	static String passwordK = "oRekPwKk";
	static String hostAddressK = "noreply@mmo.org.tr";
	static String connectK = "smtp.mmo.org.tr";

	static int portK = 587;

	void sendMail(String recipientEMail, String subject, String content)
			throws MessagingException;

	void prepareSifre(String content, String recipientMail)
			throws CRUDException;

	void prepareMail(String recipientMail, String subject, String body)
			throws CRUDException;

}
