package tr.org.mmo.asansor.business;

import tr.org.mmo.asansor.dao.MailDAOImpl;
import tr.org.mmo.asansor.dto.BasvuruDTO;
import tr.org.mmo.asansor.dto.BilgilendirmeDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;

public class MailBusiness {

	public void mailGonder(String recipientMail, String content, int il)
			throws CRUDException {
		new MailDAOImpl(il).prepareSifre(content, recipientMail);
	}

	public void randevuBilgisiEPostaGonder(BilgilendirmeDTO bilgi, int il)
			throws CRUDException {
		new MailDAOImpl(il).prepareRandevu(bilgi);

	}

	public void basvuruBilgisiEPostaGonder(BasvuruDTO basvuru, int il,
			double odemeTutari) throws CRUDException {
		new MailDAOImpl(il).prepareBasvuru(basvuru, odemeTutari);

	}

}
