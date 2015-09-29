package tr.org.mmo.asansor.util;

public enum Messages {

	_KULLANICIGIRIS_("00001", "Kullanıcı Bilgileri Hatalı"), _UYEKAYDEDILDI_(
			"00002", "Kullanıcı Bilgileri Kaydedildi"), _SQL001_("SQL01",
			"Sorgu işleminde hata oluştu"), _GENEL_("99999", "Hata oluştu"), _NULLPOINTER_(
			"99999", "Null Kayıt ile karşılaşıldı."), _SQL_500_("50001",
			"Veri Tabanı Hatası"), _SQL_501_("0501",

	"Veri tabanı hatası. Nedeni : Aynı gün hem normal kontrol hem eksiklik kontrolü yapamazsınız!"),
	_SQL_5001_("5501",

			"Veri tabanı hatası. Nedeni : Cihazın aynı randevuya ilişkin iptal edilmiş raporu var !"),
	_SQL_503_(
			"00503", "Veri Kaynağı Hatası"), _SQL_504_("00504",
			"Veri Kaynağı Bulunamadı"), _SQL_505_("0505",

	"Silmek istediğiniz kayıtla ilişkili kayıtlar olduğundan, silinemez!"), _SQL_506_(
			"0506", "Aradığınız kriterlere uygun veri bulunamadı."), _SQL_507_(
			"0507", "Hata oluştu. Nedeni : {0}!"), _SQL_509_("00509",
			"Veri Tabanı ile Bağlantı Kurulamadı"), _CONNECTIONKAPAT_("00510",
			"Connection Kapatılamadı"), _CONNECTIONAC_("00511",
			"Connection Alınamadı"), _SQL_512_("00512", "İşlem Başarısız"), _SQL_513_(
			"00513", "İşlem Tamamlandı."), _SQL_514_("00514",
			"Beklenmeyen bir hata oluştu. Lütfen işlemi bilgi işlem birimine bildiriniz !"), _SQL_515_(
			"0515",

			"Veri tabanı hatası. Nedeni : Aynı  UAVT Koda sahip sistemde farklı bir cihaz var!"), _SQL_516_(
			"0516",

			"Veri tabanı hatası. Nedeni : Aynı UAVT Koda sahip sistemde farklı bir Bina var!"), _SQL_517_(
			"0517",

			"Veri tabanı hatası. Nedeni : Aynı UAVT Koda sahip sistemde farklı bir belediye var!"), _ALANA_DEGER_GIRMELISINIZ_(
			"0998", "Alana Değer Girmelisiniz"), _AYARLAR_OKUNAMADI_("9999",
			"Ortak Parametreler Uygulamanın ilk Açılışında Okunamadı!"), _GETBASVURUID_(
			"9003", "Başvuru Numaranız :"), _BASVURU_KAYDEDILDI_(
			"9001",
			"Başvuru başarıyla Kaydedildi.Başvuru numaranız ile başvurunuzu takip edebilirsiniz..."),

	_BASVURU_KAYDEDILEMEDI_("9002", "Başvuru sisteme kaydedilemedi."), _ILILCEOKUNAMADI_(
			"9003", "İller ve İlçeler Veri Tabanından Okunamadı"), _ILILCEHATALI_(
			"9099", "İl/İlçe bilgisi girilmedi"),

	_GECERSIZTELEFONNUMARASI_("9004", "Telefon numarası geçersiz"), _GECERSIZEPOSTA_(
			"9005", "E-posta adresi geçersiz"), _GECERSIZIP_(
					"9995", "IP adresi geçersiz"),_GECERSIZTCKIMLIKNO_("9006",
			"TC Kimlik No Geçersiz"),

	_RANDEVU_KAYDEDILDI_("9007", "Randevu Başarıyla Kaydedildi..."), _MUHENDISSECILMEDI_(
			"9008", "Mühendis Seçiniz..."), _FIRMASECILMEDI_("9009",
			"Firma Seçiniz..."), _RANDEVUZAMANISECILMEDI("9009",
			"Randevu Zamanını belirtiniz..."), _RANDEVUZAMANIDEGISTIRILDI_(
			"9010", "Randevu zamanı değiştirildi"), _RANDEVUZAMANIDEGISTIRILEMEDI_(
			"9011", "Randevu zamanı değiştirilemedi"),

	_RANDEVUSILINDI_(
			"9012",
			" numaralı randevu silindi...Başvuru Listesinden yeniden randevu verebilirsiniz..."), _RANDEVUSILINEMEDI_(
			"9013", "Randevu silinemedi.."), _BELEDIYESECILMEDI_("9014",
			"Belediye seçiniz..."), _TESTKAYDEDILDI_("9015",
			"Test Veri Tabanına Kaydedildi..."), _CIHAZKAYDEDILDI_("9016",
			"Cihaz Veri Tabanına Kaydedildi..."), _BELEDIYEKAYDEDILDI_("9017",
			"Belediye Veri Tabanına Kaydedildi..."), _ILETISIMKAYDEDILDI_(
			"9018", "İletişim Bilgisi Veri Tabanına Kaydedildi..."), _BELEDIYEDEGISIKLIKKAYDET_(
			"9019",
			"Lütfen belediye bilgilerinde yapılan değişikliği kaydediniz !"), _BELEDIYEILETISIMSILINDI_(
			"9020", "İletişim kaydı silindi !"), _BELEDIYEILETISIMSILINEMEDI_(
			"9021", "İletişim kaydı silinemedi.."), _SOZLESMETARIHLERIHATALI_(
			"9022", "Sözleşme tarihleri hatalı girildi..."), _DIGITSONLY_(
			"9023", "Sadece Rakam Giriniz..."), _SOZLESMESILINDI_("9024",
			"Belediye Sözleşmesi Veritabanından Silindi !"), _SOZLESMESILINEMEDI_(
			"9025", "Sözleşme silinemedi.."), _BINAKAYDEDILDI_("9026",
			"Bina Kaydedildi"), _BINAKISILERIEKLENEMEZ_(
			"9027",
			"Yeni bina ise binayı kaydediniz, var olan bina ise  bina bilgilerini getiriniz"), _MUHENDISCIHAZYETKIKONTROLUYARI_(
			"9050",
			" sicil no'lu mühendisin kontrol edilecek asansörlere kontrol yetkisi bulunmamaktadır ! "), _MUHENDISCIHAZYETKIKONTROLUYARIT_(
			"9051",
			"Cihaz tipleri ve seçilen mühendis cihaz kontrol yetkileri uyumsuz! "), _MUHENDISKONTROLUYARI_(
			"9028",
			"Görevlendirilen 1. Mühendisin aynı gün 1 saat içerisinde başka kontrol randevusu mevcut"),
			_MUHENDISADETKONTROLUYARIGENEL_(
					"9329", "Seçilen mühendislerden günlük kontrol adetini aşan(lar) var.Kontrol ediniz !"),
			_MUHENDISADETKONTROLUYARI_(
			"9029", "mühendis aynı gün içerisinde en fazla "), _MUHENDISKAYDEDILDI_(
			"9030", "Mühendis Bilgileri Veri Tabanına Kaydedildi..."), _FIRMAKAYDEDILDI_(
			"9031", "Firma Bilgileri Veri Tabanına Kaydedildi..."), _CIHAZSECMEDINIZ_(
			"9032", "Cihaz Seçmediniz"),

	_ETIKETSARI_(
			"9033",
			"Asansördeki eksik ve hatalar 2 ay içerisinde  giderilmelidir. (sarı etiket)"),
			_ETIKETMAVI_(
					"9133",
					"Asansördeki eksik ve hatalar takip eden yıllık kontrol yapılıncaya kadar giderilmelidir. (mavi etiket)"),
			_ETIKETKIRMIZI_(
			"9034",
			"Tespit edilen uygunsuzluklar giderilmeden asansörün kullanılması tehlikelidir. (kırmızı etiket)"), _YETKISILINDI_(
			"9035", "Kullanıcının yetkisi silindi"), _YETKIVERILDI_("9036",
			"Kullanıcıya yetki verildi"), _DOSYABULUNAMADI_("9037",
			"Dosya Bulunamadı"), _DOSYASILINDI_("9037", "Dosya Silindi"), _ETIKETYESIL_(
			"9038", "Asansör kullanıma uygundur(yeşil etiket)"), _RAPOROLUSTURULAMADI_(
			"9039", "Rapor Oluştururken hata oluştu"), _SOZLESMEKAYDEDILDI_(
			"9040", "Sözleşme Bilgisi Veri Tabanına Kaydedildi..."), _MUAYENETUTARBILDIRIM_(
			"9041", "Muayene İşlem Tutarınız :"), _MUAYENETUTARBANKAYAYATIRINIZ_(
			"9042",
			"TL. Tutarı yatırdıktan sonra asansörlerinizin kontrolü yapılacaktır.Ödeme bilgisi belirtmiş olduğunuz e-posta adresine gönderilmiştir."), _SISTEMEGIRISBASARILI_(
			"9043", "Sisteme giriş başarılı"), _MUHENDISGUNCELLENDI_("9044",
			"Mühendis Bilgileri Güncellendi..."),

	_ROLBELIRLE_("9045", "Kullanıcı için rol belirleyiniz"),

	_FIRMAGUNCELLENDI_("9046", "Firma Bilgileri Güncellendi..."), _ODEMEKAYDEDILDI_(
			"9047", "Ödeme Kaydedildi"), _PAROLABILGI_("9048",
			"Kullanıcının ilk parolası kullanıcı adıdır !"), _PAROLADEGISTIR_(
			"9049", "Parolanız değiştirildi"), _CIHAZDEGERGIRINIZ("9050",
			"Kaydedebilmek için en az bir cihaz değeri giriniz"), _YETKIBELIRLE_(
			"9051",
			"Yetkileriniz tanımlanmamış.Lütfen Yöneticinize başvurunuz..."), _KULLANICIAKTIFDEGIL_(
			"9052", "Kullanıcı aktif değil.Lütfen Yöneticinize başvurunuz..."), _SOZLESMEGUNCELLEMEBINATIPHATA_(
			"9053",
			"Sözleşmede değişiklik yaparken sadece 1 bina tipi seçebilirsiniz..."),
			_SOZLESMEGUNCELLEMESOZLESMETIPHATA_(
					"9154",
					"Sözleşmede değişiklik yaparken sadece 1 sozleşme tipi seçebilirsiniz..."),
			_SOZLESMEGUNCELLEMEASANSORTIPHATA_(
			"9054",
			"Sözleşmede değişiklik yaparken sadece 1 asansör tipi seçebilirsiniz..."), _RAPORACIK_(
			"9055", "Rapor farklı bir uygulama tarafından kullanılmaktadır..."), _HATALITARIH_(
			"9056", "Hatalı tarih girdiniz"), _ODEMETUTARIFAZLA_("8001",
			"Ödeme tutarı kontrol tutarından fazla olamaz"), _ODEMETUTARIGIRINIZ_(
			"8002", "Ödeme tutarı giriniz"), _ODEMETARIHIGIRINIZ_("8003",
			"Ödeme tarihi giriniz"), _PAROLAGIRINIZ_("8004", "Parola giriniz"), _TESTSORUSUBULUNAMADI_(
			"8005", "Asansör kapsamında test sorusu tanımlanmamıştır..."), _ALTSORUBULUNAMADI_(
			"8006", "Tanımlı alt test sorusu tanımlanmamıştır..."), _RANDEVUTARIHIHATALI_(
			"8007", "Hatalı Randevu Tarihi"),

	_ILHATALI_("8008", "İl bilgisi girilmedi"), _KONTROLTARIHIHATALI_("8009",
			"Hatalı Kontrol Tarihi"), _KONTROLSAATIHATALI_("8012",
			"Kontrol saati sabah 06:00 dan sonrasıdır..."), _RANDEVUSAATIHATALI_(
			"8010", "Randevuyu  08:00-21:00 saatleri arasında verebilirsiniz!"), _ONAYLIRAPORSILHATA_(
			"8011", "Onaylı Rapor Değiştiremezsiniz"), _RANDEVUTASIMAHATASI_(
			"8013", "Randevu tarihini geçmiş tarihe alamazsınız!"), _RANDEVUSILHATA_(
			"8014", "Randevuya bağlı kontroller var silemezsiniz !"), _BIRIMSECINIZ_(
			"8015",
			"Kullanıcının yetkili olduğu şube ve/veya temsilcilikleri seçiniz"), _BAKIMCIFIRMAGIRILMEDI_(
			"8016",
			"Kontrol edilecek asansörlerin bakımcı firma bilgilerini giriniz !"), _RANDEVUSIZINDEGIL_(
			"8017", "Size ait olmayan kontrol randevusu.İşlem yapamazsınız"), _UAVTBELEDIYEOKUNAMADI_(
			"8016", "Ulusal Adres Veri Tabanında Belediye Bulunamadı"), _RANDEVUVERILENBASVURU_(
			"8099", "Randevu verildiği için bu başvuruyu silemezsiniz !"), _BASVURUSILINDI_(
			"8098", "Başvuru silindi!"), _RAPORVARSILINEMEZ_(
			"8101",
			"Silmek istediğiniz kayda ait rapor oluşturulmuş.Raporu sildikten sonra kontrolü silebilirsiniz !"), _KONTROLSILINDI_(
			"8102", "Kayda ait test ve kontrol kayıtları silindi !"), _CIHAZAAITKONTROLVAR_(
			"8103",
			"Cihaza ait kontroller var.Cihazı silemezsiniz.Durumu pasif yapabilirsiniz ya da kontrolü sildikten sonra cihazı silebilirsiniz !"), _CIHAZAAITBASVURUVAR_(
			"8104",
			"Cihaza için yapılmış bir başvuru mevcut.Cihazı silemezsiniz.Başvuruyu sildikten sonra cihazı silebilirsiniz !"), _CIHAZSILINDI_(
			"8105", "Cihaz veri tabanından silindi!"), _UAVTKODBOSOLAMAZ_(
			"8107", "UAVT Kod boş olamaz !"), _EKLENECEKMAHALLEYOK_("8108",
			"UAVT Kayıtlarında farklı bir mahalle bulunamadı"), _EKLENECEKCADDESOKAKYOK_(
			"8109", "UAVT Kayıtlarında farklı bir cadde/sokak bulunamadı"), _BINAUAVTKODBOS_(
			"8110", "Binanın UAVT kodu boş.Önce binayı belirleyiniz..."), _UAVTYEASANSOREKLENECEK_(
			"8111",
			"Asansör kayıt işleminde bu asansör Ulusal Adres Veri Tabanına eklenecektir."), _SECILENCIHAZUAVTYEKAYITLIDEGIL_(
			"8112",
			"Cihaz Ulusal Adres Veri Tabanında kayıtlı değil. Öncelikle cihazı kaydediniz."), _SUBESECINIZ_(
			"8113", "Şube Seçiniz"), _TARIHGIRINIZ_("8114", "Tarih Giriniz"), _FIRMASILINMEDI_(
			"8115", "Firma Silinemedi!"), _FIRMASILINDI_("8116",
			"Firma Kayıtlardan Silindi !"), _EKLENECEKBINAYOK_("8117",
			"Ulusal Adres Veri tabanında ilgili caddeye ait farklı bir bina bulunamadı..."),
			_PROTOKOLIMZALAYANBELEDIYEUAVTKODYOK_("8118","Protokol imzalanan belediyenin uavt kaydı bulunamadı.Öncelikle belediyeyi kaydediniz..."), 
			_ARAMAKRITERIGIRINIZ_("8119","En az bir arama kriteri giriniz !"), _DUYURUDETAYGIRINIZ_("8120","Detay Bilgisi Giriniz !"), 
			_ASANSORTIPISECINIZ_("8120","Asansör Tipi Seçiniz"), 
			_KONTROLTURUSECINIZ_("8121","Kontrol Türü Seçiniz !"),
			_KONTROLZAMANIHATALI_("8122","Kontrol Zamanı Hatalı !"),
			_ONAYLAYANSECINIZ_("8123","Onaylayan Seçiniz !"),
			_FIRMAGOREVLIGIRINIZ_("8124","Firma Görevlisi Giriniz !"),
			_KONTROLTARIHIGUNUNTARINDENBUYUKOLAMAZ_("8125","Kontrol Tarihi Günün Tarihinden Sonrası Olamaz !"),
			_KONTROLTARIHGIRISIHATALI_("8126","Kontrol Başlangıcı Kontrol Bitişinden Büyük Olamaz !"), _SOZLESMEVAR_("8127","Cihaz tipinde aynı tarihleri kapsayan farklı bir sözleşme daha bulunmaktadır. Lütfen kontrol ediniz !"),
			_ZORUNLUALANLARIDOLDURUNUZ_("8128","Lütfen doldurulması zorunlu alanları doldurunuz !"), _RAPORBAKANLIGAKAYITLI_("8129","İptal etmek istediğiniz rapor bakanlığa kaydedilmiş.Önce Bakanlık kayıtlarından siliniz !"),
			_RAPORICINREVIZYONDUZENLENMIS_("8130","İptal etmek istediğiniz rapora ait revizyon rapor var.Önce revizyon raporu iptal ediniz !"),
			_RAPORTESLIMEDILMIS_("8131","İptal etmek istediğiniz rapor bina sorumlusuna teslim edilmiş.Raporu iptal edemezsiniz !"),
			_RAPORIPTALEDILDI_("8132","Rapor iptal edildi !"), _RAPORIPTALEDILEMEDI_("8133","Rapor iptal edilemedi !"),_ASANSORDENETIMKAYDIYAPILDI_("8134","Asansör Denetim Kaydı Yapıldı. Denetim Kayıt Id : "),
			_REVIZYONRAPORBAKANLIKTAKAYITLI_("8135","Kaydetmek istediğiniz raporun revizyon olan raporu bakanlığa kaydedilmiştir. Bu raporun denetim kaydını yapamazsınız !"),
			_CIHAZUAVTKODUKAYDEDILEMEDI_("8136","Cihaz Bakanlığa Kaydedildi ama uavt kodu AKM tablosunda güncellenemedi."),
			_ODEMEUYARI_("8134","Ödeme eksik veya yapılmamış. Rapor teslim işlemini yapamazsınız."),
			_UAVTVAR_("8137","Seçmiş olduğunuz bina sistemde kayıtlıdır."), _TESLIMRAPORSILHATA_("8138","Rapor teslim edilmiş silemezsiniz !"),
			_BELEDIYEKIRMIZIKAYDET_("8139","Listelenen raporlar belediyeye gönderildi olarak veri tabanına kaydedildi."), _NEGATIFDEGER_("8240","Negatif değer girdiniz !");
			
	private String mesaj;
	private String mesajNo;

	private Messages(String mesajNo, String mesaj) {
		this.mesajNo = mesajNo;
		this.mesaj = mesaj;
	}

	public String getMesajNo() {
		return mesajNo;
	}

	public void setMesajNo(String mesajNo) {
		this.mesajNo = mesajNo;
	}

	public String getMesaj() {
		return mesaj;
	}

	public void setMesaj(String mesaj) {
		this.mesaj = mesaj;
	}

}
