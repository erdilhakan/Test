package tr.org.mmo.asansor.util;



public enum Sorgular {
	_TARAMA_INSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".tarama (id,tarih, bina_id, bina_sorumlusu_tckimlikno, durum, bina_sorumlusu_adi, "
					+ "bina_sorumlusu_soyadi, bina_sorumlusu_telefon_no, bina_sorumlusu_eposta,tarama_yapilamama_neden_kod,userid,bina_sorumlusu_telefon_no_dahili) "
					+ "VALUES (?, current_date,?,?,?,?,?,?,?,?,?,?)"), _TARAMA_YAPILMADI_INSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".tarama (tarih, bina_id, bina_sorumlusu_tckimlikno, durum, bina_sorumlusu_adi, "
					+ "bina_sorumlusu_soyadi, bina_sorumlusu_telefon_no, bina_sorumlusu_eposta,tarama_yapilamama_neden_kod,userid) "
					+ "VALUES ( current_date,?,?,?,?,?,?,?,?,?)"), _BASVURU_KISI_INSERT(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".basvuru (basvuru_tarihi,  basvuru_yapan_tckimlikno, bina_id,basvuru_durum,"
					+ "basvuru_yapan_adi, basvuru_yapan_soyadi, telefon_no,eposta,userid,telefon_no_dahili) "
					+ "VALUES ( current_date,?,?,?,?,?,?,?,?,?)"), _BASVURU_KISI_UPDATE(
			"UPDATE "
					+ Ayarlar.SHEMA
					+ ".basvuru "
					+ "set  basvuru_yapan_tckimlikno=?,bina_id=?, "
					+ "basvuru_yapan_adi=?, basvuru_yapan_soyadi=?, telefon_no=?, eposta=?,userid=?,telefon_no_dahili=?  "
					+ "where basvuru_id=?"), _TARAMAUPDATE_(
			"update "
					+ Ayarlar.SHEMA
					+ ".tarama set "
					+ "tarih=?, bina_id=?, bina_sorumlusu_tckimlikno=?, durum=?,"
					+ "bina_sorumlusu_adi=?, bina_sorumlusu_soyadi=?, bina_sorumlusu_telefon_no=?, "
					+ " bina_sorumlusu_eposta=?, tarama_yapilamama_neden_kod=?,userid=?,bina_sorumlusu_telefon_no_dahili=? where id=?"), _BASVURU_ASANSOR_INSERT_(
			"insert into "
					+ Ayarlar.SHEMA
					+ ".basvuru_asansor (basvuru_id,cihaz_id,kontrol_turu,kontrol_tutari,bakimci_firma_id,userid) values(?,?,?,?,?,?)"), _BASVURU_ASANSOR_DELETE_(
			"delete from " + Ayarlar.SHEMA
					+ ".basvuru_asansor where basvuru_id=?"), _BASVURU_DURUM_UPDATE_(
			"Update "
					+ Ayarlar.SHEMA
					+ ".basvuru set basvuru_durum=?,userid=? where basvuru_id=?"), _TARAMA_DURUM_UPDATE_(
			"Update " + Ayarlar.SHEMA
					+ ".tarama set durum=?,userid=? where id=?"), _RANDEVU_DURUM_UPDATE_(
			"Update "
					+ Ayarlar.SHEMA
					+ ".randevu set randevu_durum=?,userid=? where randevu_id=?"), _BINA_INSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".bina(il, ilce, mahalle, cadde_sokak, "
					+ "bina_no, bina_adi,belediye, ada, parsel, pafta,yapitip,vergi_no,vergi_dairesi,sozlesme_bina_tip_id,userid,uavt_kod,koy_kodu) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?)"), _BINA_UPDATE_(
			"UPDATE "
					+ Ayarlar.SHEMA
					+ ".bina  SET il=?, ilce=?, mahalle=?, cadde_sokak=?, bina_no=?, bina_adi=?, belediye=?,"
					+ "ada=?, parsel=?, pafta=?, yapitip=?,vergi_no=?,vergi_dairesi=?,sozlesme_bina_tip_id=?,userid=?,uavt_kod=?,koy_kodu=? where bina_id=?"), _BINA_ARA_BY_TCKIMLIK(
			"SELECT a.bina_id, il, ilce,b.il_Adi as ilAdi,b.ilce_adi as ilceAdi, mahalle, cadde_sokak, bina_no,"
					+ " bina_adi, belediye,ada, parsel, pafta,vergi_no as vergiNo,vergi_dairesi as vergiDairesi,yapitip,"
					+ "sozlesme_bina_tip_id as sozlesmebinatipid ,acik_adres as acikadres,a.uavt_kod as uavtkod,tescilno,koy_kodu as bucakkoykod   FROM "
					+ Ayarlar.SHEMA
					+ ".bina a join "
					+ Ayarlar.SHEMA
					+ ".bina_kisi x on (x.bina_id=a.bina_id) "
					+ " left outer join "
					+ Ayarlar.SHEMA
					+ ".ililcetb b on (b.il_kodu=a.il and b.ilce_kodu=a.ilce) where "
					+ "x.tckimlikno=? "), _GETYAPIKONULARI_(
			"select id,tip from " + Ayarlar.SHEMA + ".yapikonusu"), _BINA_ARA_BY_ADAPARSELPAFTA(
			"SELECT bina_id, il, ilce,b.il_Adi as ilAdi,b.ilce_adi as ilceAdi, mahalle, cadde_sokak, bina_no,"
					+ " bina_adi, belediye,ada, parsel, pafta,vergi_no as vergiNo,vergi_dairesi as vergiDairesi,yapitip,"
					+ "sozlesme_bina_tip_id as sozlesmebinatipid ,acik_adres as acikadres,a.uavt_kod as uavtkod,koy_kodu as bucakkoykod   FROM "
					+ Ayarlar.SHEMA
					+ ".bina a left outer join "
					+ Ayarlar.SHEMA
					+ ".ililcetb b on (b.il_kodu=a.il and b.ilce_kodu=a.ilce) where "
					+ "a.ada=? and a.parsel=? and a.pafta=? "), _BINA_ARA_ILILCEDEN_(
			"SELECT bina_id, il, ilce, mahalle, cadde_sokak, bina_no,"
					+ " bina_adi, belediye,ada, parsel, pafta,vergi_no as vergiNo,vergi_dairesi as vergiDairesi,yapitip,"
					+ "sozlesme_bina_tip_id as sozlesmebinatipid,acik_adres as acikadres,a.uavt_kod as uavtkod,koy_kodu as bucakkoykod    FROM "
					+ Ayarlar.SHEMA + ".bina a where " + "a.il=? and a.ilce=?"), _BINA_ARA_BIRIM_(
			"SELECT bina_id, il, ilce, mahalle, cadde_sokak, bina_no,"
					+ " bina_adi, belediye,ada, parsel, pafta,b.il_Adi as ilAdi,b.ilce_adi as ilceAdi,vergi_no as vergiNo,"
					+ "vergi_dairesi as vergiDairesi,yapitip,"
					+ "sozlesme_bina_tip_id as sozlesmebinatipid ,a.tescilno,a.enlem,a.boylam,acik_adres as acikadres,a.uavt_kod as uavtkod,koy_kodu as bucakkoykod   FROM "
					+ Ayarlar.SHEMA
					+ ".bina a left outer join "
					+ Ayarlar.SHEMA
					+ ".ililcetb b on (b.il_kodu=a.il and b.ilce_kodu=a.ilce) where "),
					_BINA_ARA_BY_BELEDIYEKOD_(
							"SELECT bina_id, il, ilce, mahalle, cadde_sokak, bina_no,"
									+ " bina_adi, belediye,ada, parsel, pafta,b.il_Adi as ilAdi,b.ilce_adi as ilceAdi,vergi_no as vergiNo,"
									+ "vergi_dairesi as vergiDairesi,yapitip,"
									+ "sozlesme_bina_tip_id as sozlesmebinatipid ,a.tescilno,a.enlem,a.boylam,acik_adres as acikadres,a.uavt_kod as uavtkod,koy_kodu as bucakkoykod   FROM "
									+ Ayarlar.SHEMA
									+ ".bina a left outer join "
									+ Ayarlar.SHEMA
									+ ".ililcetb b on (b.il_kodu=a.il and b.ilce_kodu=a.ilce) where belediye=?"),
	// "a.il=?"),

	_TUMBINALAR_(
			"SELECT bina_id, il, ilce, mahalle, cadde_sokak, bina_no,"
					+ " bina_adi, belediye,ada, parsel, pafta,b.il_Adi as ilAdi,b.ilce_adi as ilceAdi,"
					+ "vergi_no as vergiNo,vergi_dairesi as vergiDairesi,yapitip,"
					+ "sozlesme_bina_tip_id as sozlesmebinatipid,a.tescilno,a.enlem,a.boylam,acik_adres as acikadres,a.uavt_kod as uavtkod,koy_kodu as bucakkoykod   FROM "
					+ Ayarlar.SHEMA + ".bina a left outer join "
					+ Ayarlar.SHEMA
					+ ".ililcetb b on (b.il_kodu=a.il and b.ilce_kodu=a.ilce)"),

	_BINA_ARA_ID_(
			"SELECT bina_id, il, ilce,"
					+ "(select distinct il_Adi from "
					+ Ayarlar.SHEMA
					+ ".ililcetb where il_kodu=a.il)"
					+ "  as ilAdi,"
					+ "(select distinct ilce_Adi from "
					+ Ayarlar.SHEMA
					+ ".ililcetb where il_kodu=a.il and ilce_kodu=a.ilce)"
					+ " as ilceAdi,"
					+ " mahalle, cadde_sokak, bina_no,"
					+ " bina_adi, belediye,ada, parsel, pafta,vergi_no as vergiNo,"
					+ "vergi_dairesi as vergiDairesi,yapitip,"
					+ "sozlesme_bina_tip_id as sozlesmebinatipid,a.tescilno,enlem,boylam,acik_adres as acikadres,a.uavt_kod as uavtkod,koy_kodu as bucakkoykod  FROM "
					+ Ayarlar.SHEMA + ".bina a  where " + "a.bina_id=? "), _BINA_ARA_BY_FULLPARAM_(
			"SELECT bina_id as binaid FROM "
					+ Ayarlar.SHEMA
					+ ".bina a where "
					+ "il=? and ilce=? and mahalle=? and cadde_sokak=? and bina_no=? "), _BINA_ARA_RANDEVUID_(
			"SELECT bina_id, il, ilce,"
					+ "(select distinct il_Adi from "
					+ Ayarlar.SHEMA
					+ ".ililcetb where il_kodu=a.il)"
					+ "  as ilAdi,"
					+ "(select distinct ilce_Adi from "
					+ Ayarlar.SHEMA
					+ ".ililcetb where il_kodu=a.il and ilce_kodu=a.ilce)"
					+ " as ilceAdi,"
					+ "mahalle, cadde_sokak, bina_no,"
					+ " bina_adi, belediye,ada, parsel, pafta,vergi_no as vergiNo,"
					+ "vergi_dairesi as vergiDairesi,yapitip,"
					+ "sozlesme_bina_tip_id as sozlesmebinatipid,acik_adres as acikadres,a.uavt_kod as uavtkod,koy_kodu as bucakkoykod    FROM "
					+ Ayarlar.SHEMA
					+ ".bina a "
					+ " where "
					+ "a.bina_id=(SELECT bas.bina_id FROM "
					+ Ayarlar.SHEMA
					+ ".basvuru bas,"
					+ Ayarlar.SHEMA
					+ ".randevu ran  WHERE ran.basvuru_id=bas.basvuru_id and ran.randevu_id=? and randevu_durum='R')"),
					_BINA_ARA_RANDEVUID1_(
							"SELECT bina_id, il, ilce,"
									+ "(select distinct il_Adi from "
									+ Ayarlar.SHEMA
									+ ".ililcetb where il_kodu=a.il)"
									+ "  as ilAdi,"
									+ "(select distinct ilce_Adi from "
									+ Ayarlar.SHEMA
									+ ".ililcetb where il_kodu=a.il and ilce_kodu=a.ilce)"
									+ " as ilceAdi,"
									+ "mahalle, cadde_sokak, bina_no,"
									+ " bina_adi, belediye,ada, parsel, pafta,vergi_no as vergiNo,"
									+ "vergi_dairesi as vergiDairesi,yapitip,"
									+ "sozlesme_bina_tip_id as sozlesmebinatipid,acik_adres as acikadres,a.uavt_kod as uavtkod,koy_kodu as bucakkoykod    FROM "
									+ Ayarlar.SHEMA
									+ ".bina a "
									+ " where "
									+ "a.bina_id=(SELECT bas.bina_id FROM "
									+ Ayarlar.SHEMA
									+ ".basvuru bas,"
									+ Ayarlar.SHEMA
									+ ".randevu ran  WHERE ran.basvuru_id=bas.basvuru_id and ran.randevu_id=?)"),
					_CURRVALBELEDIYESOZLESME_(
			"SELECT currval(pg_get_serial_sequence('" + Ayarlar.SHEMA
					+ ".belediye_sozlesme', 'id'))"), _CURRVALONTANIMLI_(
			"SELECT currval(pg_get_serial_sequence('" + Ayarlar.SHEMA
					+ ".soru_ontanimli', 'soru_ontanimli_id'))"), _CURRVALKONTROLTESTCEVAP_(
			"SELECT currval(pg_get_serial_sequence('" + Ayarlar.SHEMA
					+ ".kontrol_test', 'cevap_id'))"), _CURRVALTESTSORU_(
			"SELECT currval(pg_get_serial_sequence('" + Ayarlar.SHEMA
					+ ".soru', 'soru_id'))"), _CURRVALBELEDIYEILETISIM_(
			"SELECT currval(pg_get_serial_sequence('" + Ayarlar.SHEMA
					+ ".belediye_iletisim', 'id'))"), _CURRVALBINAKISI_(
			"SELECT currval(pg_get_serial_sequence('" + Ayarlar.SHEMA
					+ ".bina_kisi', 'id'))"), _CURRVALBELEDIYE_(
			"SELECT currval(pg_get_serial_sequence('" + Ayarlar.SHEMA
					+ ".belediye', 'kod'))"), _CURRVALBINA_(
			"SELECT currval(pg_get_serial_sequence('" + Ayarlar.SHEMA
					+ ".bina', 'bina_id'))"), _CURRVALRANDEVU_(
			"SELECT currval(pg_get_serial_sequence('" + Ayarlar.SHEMA
					+ ".randevu', 'randevu_id'))"), _CURRVALBASVURU_(
			"SELECT currval(pg_get_serial_sequence('" + Ayarlar.SHEMA
					+ ".basvuru', 'basvuru_id'))"), _CURRVALROL_(
			"SELECT currval(pg_get_serial_sequence('" + Ayarlar.SHEMA
					+ ".rol', 'rol_id'))"),

	_CURRVALRAPOR_("select currval(pg_get_serial_sequence('" + Ayarlar.SHEMA
			+ ".rapor', 'rapor_id'))"), _GETIL_(
			"SELECT distinct il_kodu,il_adi,enlem,boylam FROM " + Ayarlar.SHEMA
					+ ".ililcetb where il_kodu=?"), _ILLER_ILCELER_(
			"SELECT il_kodu, ilce_kodu, il_adi, ilce_adi,enlem,boylam FROM "
					+ Ayarlar.SHEMA + ".ililcetb order by il_kodu"), _GETILCELER_(
			"Select ilce_kodu,ilce_adi from " + Ayarlar.SHEMA
					+ ".ililcetb where il_kodu=?"), _GETMAHALLELER_(
			"Select kod, upper(replace(replace(ad,'i','İ'),'ı','I')) as ad,tanitimkodu, tip, yetkiliidarekodu, koykodu from "
					+ Ayarlar.SHEMAUAVT + ".mahalle where koykodu=?"), _GETCADDESOKAK_(
			"Select c.kod, rtrim(ltrim(concat(upper(replace(replace(c.ad,'i','İ'),'ı','I')) ,' ',ct.ad))) as ad,c.tanitimkodu, c.tip, c.mahallekodu from "
					+ Ayarlar.SHEMAUAVT + ".csbm c "
					+ " left outer join "
					+ Ayarlar.SHEMAUAVT + ".csbmtip ct on (c.tip is not null and rtrim(ltrim(c.tip))!='' and ltrim(rtrim(c.tip))=ltrim(rtrim(cast(ct.kod as char)))) "
					+ " where c.mahallekodu=?"),

	_GETUAVTBELEDIYE_(
			"SELECT kod, belediyeadi, prmbelediyeturkod, ilkod, ilcekod, koykod,  ustbelediyekod from "
					+ Ayarlar.SHEMAUAVT
					+ ".belediye where ilkod=? and ilcekod=?"),

	_GETBASVURULAR_(
			"select q.basvuru_id as basvuruid,basvuru_tarihi as basvurutarihi,basvuru_yapan_adi||chr(32)||basvuru_yapan_soyadi as adisoyadi,"
					+ "basvuru_yapan_adi as adi,basvuru_yapan_soyadi as soyadi,"
					+ "basvuru_yapan_tckimlikno as tckimlikno,q.telefon_no as telefonNo,mahalle,cadde_sokak as caddesokak,bina_no as binano,q.bina_id as binaid,"
					+ "il_adi as il,ilce_adi as ilce, il as ilKodu,ilce as ilcekodu,q.eposta,a.bina_adi as binaadi,q.basvuru_durum as basvurudurum,"
					+ "0 as kontroltutari,"
					+ "bk.sorumluluk_turu as basvuruyapansorumluluktur,(select id  from "
					+ Ayarlar.SHEMA
					+ ".tarama where id=q.basvuru_id and bina_id=q.bina_id and tarih=q.basvuru_tarihi) as taramaId,q.telefon_no_dahili as telefonnodahili "
					+ "from "
					+ Ayarlar.SHEMA
					+ ".basvuru q join "
					+ Ayarlar.SHEMA
					+ ".bina a using (bina_id) join "
					+ Ayarlar.SHEMA
					+ ".ililcetb on (il_kodu=il and ilce_kodu=ilce) left outer join "
					+ Ayarlar.SHEMA
					+ ".bina_kisi bk on bk.bina_id=q.bina_id and bk.tckimlikno=q.basvuru_yapan_tckimlikno "
					+ "where  basvuru_tarihi between ? and ? and  not exists  "
					+ "(select 1 from "
					+ Ayarlar.SHEMA
					+ ".odeme where (odeme_tarihi is not null or odemekontroldealinsin) and basvuru_id=q.basvuru_id) and "
					+ "basvuru_durum in ('C','I') "
					+ "and not exists (select 1 from "
					+ Ayarlar.SHEMA
					+ ".odeme_alinmayacak_binalar where iptal_kodu='H' and bina_id=q.bina_id)"),

	_GETODEMEYAPILANBASVURULAR_(
			"select * from ( "
					+ "select q.basvuru_id as basvuruid,basvuru_tarihi as basvurutarihi,basvuru_yapan_adi||chr(32)||basvuru_yapan_soyadi as adisoyadi,"
					+ "basvuru_yapan_adi as adi,basvuru_yapan_soyadi as soyadi,a.bina_id as binaid,q.eposta,"
					+ "basvuru_yapan_tckimlikno as tckimlikno,q.telefon_no as telefonNo,mahalle,cadde_sokak as caddesokak,bina_no as binano,"
					+ "il_adi as il,ilce_adi as ilce, il as ilKodu,ilce as ilcekodu,o.odeme_tutari as odemeTutari,o.toplam_tutar as kontroltutari,"
					+ "o.odeme_tarihi as odemetarihi,odemekontroldealinsin,'H' as odemeyapilmayacakbina,a.bina_adi as binaAdi,tarama.id as taramaid,"
					+ "bk.sorumluluk_turu as basvuruyapansorumluluktur,q.telefon_no_dahili as telefonnodahili "
					+ "from "
					+ Ayarlar.SHEMA
					+ ".basvuru q join "
					+ Ayarlar.SHEMA
					+ ".bina a using (bina_id) join "
					+ Ayarlar.SHEMA
					+ ".ililcetb on (il_kodu=il and ilce_kodu=ilce) "
					+ "join "
					+ Ayarlar.SHEMA
					+ ".odeme o on (o.basvuru_id=q.basvuru_id and "
					+ "(odeme_tarihi is not null or odemekontroldealinsin or toplam_tutar=0)) left outer join "
					+ Ayarlar.SHEMA
					+ ".tarama tarama on (tarama.id=q.basvuru_id) left outer join "
					+ Ayarlar.SHEMA
					+ ".bina_kisi bk on bk.bina_id=q.bina_id and bk.tckimlikno=q.basvuru_yapan_tckimlikno "
					+ "where basvuru_durum in ('C','I') "
					+ "union all "
					+ " select q.basvuru_id as basvuruid,basvuru_tarihi as basvurutarihi,"
					+ "basvuru_yapan_adi||chr(32)||basvuru_yapan_soyadi as adisoyadi,"
					+ "basvuru_yapan_adi as adi,basvuru_yapan_soyadi as soyadi,a.bina_id as binaid,q.eposta,"
					+ "basvuru_yapan_tckimlikno as tckimlikno,q.telefon_no as telefonNo,mahalle,"
					+ "cadde_sokak as caddesokak,bina_no as binano,"
					+ "il_adi as il,ilce_adi as ilce, il as ilKodu,ilce as ilcekodu,0 as odemeTutari,"
					+ "0 as kontroltutari,"
					+ "null as odemetarihi,'f' as odemekontroldealinsin,"
					+ "'E' as odemeyapilmayacakbina,a.bina_adi as binaAdi,tarama.id as taramaid, "
					+ "bk.sorumluluk_turu as basvuruyapansorumluluktur,q.telefon_no_dahili as telefonnodahili "
					+ "from "
					+ Ayarlar.SHEMA
					+ ".basvuru q join "
					+ Ayarlar.SHEMA
					+ ".bina a using (bina_id) join "
					+ Ayarlar.SHEMA
					+ ".ililcetb on (il_kodu=il and ilce_kodu=ilce) "
					+ " left outer join "
					+ Ayarlar.SHEMA
					+ ".tarama tarama on (tarama.id=q.basvuru_id)  left outer join "
					+ Ayarlar.SHEMA
					+ ".bina_kisi bk on bk.bina_id=q.bina_id and bk.tckimlikno=q.basvuru_yapan_tckimlikno "
					+ "where basvuru_durum in ('C','I') and (exists "
					+ "(select 1 from "
					+ Ayarlar.SHEMA
					+ ".odeme_alinmayacak_binalar where iptal_kodu='H' and bina_id=q.bina_id)  "
					+ " or not exists (select * from "
					+ Ayarlar.SHEMA+".odeme where basvuru_id=q.basvuru_id)"
					+ ")) as jt "),

	_GETBASVURUASANSORTIP_("select b.cihaz_tip_id as cihaztip " + "from "
			+ Ayarlar.SHEMA + ".basvuru_asansor a left outer join   "
			+ Ayarlar.SHEMA + ".cihaz b using (cihaz_id) "
			+ " where basvuru_id=?"), _GETBASVURUASANSOR_(
			"select basvuru_id as basvuruid,id,b.cihaz_id as cihazid,kontrol_turu as kontrolturu,kontrol_tutari as kontroltutari,kontrol_tarihi as kontroltarihi,b.kimlik_no as kimlikno,"
					+ "c.cevap as asansorunYeri "
					+ "from "
					+ Ayarlar.SHEMA
					+ ".basvuru_asansor a left outer join   "
					+ Ayarlar.SHEMA
					+ ".cihaz b using (cihaz_id) "
					+ "left outer join "
					+ Ayarlar.SHEMA
					+ ".cihaz_deger c on c.cihaz_id=b.cihaz_id "
					+ " and "
					+ "(case (b.cihaz_tip_id) "
					+ "when 66 then c.deger_id=151 "
					+ "when 68 then c.deger_id=171 "
					+ " end) "
					+ "  where basvuru_id=?"),
					
					_GETBASVURUASANSORIPTAL_(
							"select a.basvuru_id as basvuruid,id,a.cihaz_id as cihazid,a.kontrol_turu as kontrolturu,"
							+ "a.kontrol_tutari as kontroltutari,a.kontrol_tarihi as kontroltarihi "
									+ "from "
									+ Ayarlar.SHEMA
									+ ".basvuru_asansor a  "
									+ "  where a.basvuru_id=?"),
					
					_GETBASVURUASANSORFIRMA_(
			"select kontrol_tutari as kontroltutari,f.unvan as bakimciFirmaAdi,"
					+ "f.tse_belge_no as bakimciFirmaHYBNo,f.gecerlilik_suresi as bakimciFirmaHYBGecerlilikTarihi,"
					+ "(case b.cihaz_tip_id " + "when 66 then "
					+ "(select cevap from "
					+ Ayarlar.SHEMA
					+ ".cihaz_deger where cihaz_id=b.cihaz_id and deger_id=152) "
					+ "when 68 then "
					+ "(select cevap from "
					+ Ayarlar.SHEMA
					+ ".cihaz_deger where cihaz_id=b.cihaz_id and deger_id=172)"
					+ "else '' end) as agirlikkapasite,"
					+ "(case b.cihaz_tip_id "
					+ "when 66 then "
					+ "(select cevap from "
					+ Ayarlar.SHEMA
					+ ".cihaz_deger where cihaz_id=b.cihaz_id and deger_id=157) "
					+ "when 68 then "
					+ "(select cevap from "
					+ Ayarlar.SHEMA
					+ ".cihaz_deger where cihaz_id=b.cihaz_id and deger_id=182) "
					+ "else '' end) as durakkapasite "
					+ " from "
					+ Ayarlar.SHEMA
					+ ".basvuru_asansor a left outer join "
					+ Ayarlar.SHEMA
					+ ".cihaz b using (cihaz_id)  "
					+ " left outer join "
					+ Ayarlar.SHEMA
					+ ".bakimci_firma f on (f.kod=a.bakimci_firma_id) "
					+ "  where basvuru_id=? and  b.cihaz_id=?"),

	/*
	 * _BINA_ARA_(
	 * "SELECT a.bina_id, il, ilce, mahalle, cadde_sokak, bina_no,tckimlikno,adi,soyadi,vergi_no,vergi_dairesi,bina_sorumlusu,telefon_no,"
	 * + "telefon_no_gsm,e_posta," +
	 * " bina_adi, belediye,ada, parsel, pafta   FROM " +Ayarlar.SHEMA
	 * +".bina a left outer join " + Ayarlar.SHEMA +
	 * ".basvuru using (bina_id) where " + "ada=? and parsel=? and pafta=?");
	 */
	_KULLANICIPAROLAUPDATE_("update " + Ayarlar.SHEMA
			+ ".kullanici set parola=?,userid=? where kullanici_adi=?"), _KULLANICI_(
			"SELECT kullanici_id as kullaniciid,kullanici_adi as kullaniciadi,parola,initcap(adi) as adi,"
					+ "initcap(soyadi) as soyadi,sicilno,eposta,durum,kullanici_turu as kullanicituru,"
					+ "telefon_no as telefonno,gsm_telefon_no as gsmtelefonno,"
					+ "onay_yetkisi as onayyetkisi,akreditasyon as akreditasyondeger,il,belediye_ip as belediyeip,"
					+ "'('||substr(trim(cast(telefon_no as character(10))),1,3)||') '||"
					+ "substr(trim(cast(telefon_no as character(10))),4,3)||' '||substr(trim(cast(telefon_no as character(10))),7) as telefonnostr,"
					+ "'('||substr(trim(cast(gsm_telefon_no as character(10))),1,3)||') '||"
					+ "substr(trim(cast(gsm_telefon_no as character(10))),4,3)||' '||substr(trim(cast(gsm_telefon_no as character(10))),7) as gsmtelefonnostr"
					+ " FROM "
					+ Ayarlar.SHEMA
					+ ".kullanici WHERE kullanici_adi=?"), _KULLANICIBYID_(
			"SELECT "
					+ "kullanici_id as kullaniciid,kullanici_adi as kullaniciadi,parola,adi,"
					+ "soyadi,sicilno,eposta,durum,kullanici_turu as kullanicituru,"
					+ "telefon_no as telefonno,gsm_telefon_no as gsmtelefonno,"
					+ "onay_yetkisi as onayyetkisi,akreditasyon as akreditasyondeger,il,belediye_ip as belediyeip,"
					+ "'('||substr(trim(cast(telefon_no as character(10))),1,3)||') '||"
					+ "substr(trim(cast(telefon_no as character(10))),4,3)||' '||substr(trim(cast(telefon_no as character(10))),7) as telefonnostr,"
					+ "'('||substr(trim(cast(gsm_telefon_no as character(10))),1,3)||') '||"
					+ "substr(trim(cast(gsm_telefon_no as character(10))),4,3)||' '||substr(trim(cast(gsm_telefon_no as character(10))),7) as gsmtelefonnostr"
					+ " FROM " + Ayarlar.SHEMA
					+ ".kullanici WHERE kullanici_id=? "), _KULLANICIBYKULLANICIADI_(
			"SELECT "
					+ "kullanici_id as kullaniciid,kullanici_adi as kullaniciadi,parola,adi,"
					+ "soyadi,sicilno,eposta,durum,kullanici_turu as kullanicituru,"
					+ "telefon_no as telefonno,gsm_telefon_no as gsmtelefonno,"
					+ "onay_yetkisi as onayyetkisi,akreditasyon as akreditasyondeger,il,belediye_ip as belediyeip,"
					+ "'('||substr(trim(cast(telefon_no as character(10))),1,3)||') '||"
					+ "substr(trim(cast(telefon_no as character(10))),4,3)||' '||substr(trim(cast(telefon_no as character(10))),7) as telefonnostr,"
					+ "'('||substr(trim(cast(gsm_telefon_no as character(10))),1,3)||') '||"
					+ "substr(trim(cast(gsm_telefon_no as character(10))),4,3)||' '||substr(trim(cast(gsm_telefon_no as character(10))),7) as gsmtelefonnostr"
					+ " FROM " + Ayarlar.SHEMA
					+ ".kullanici WHERE kullanici_adi=? "),

	_KULLANICIROLYETKILISTESI_(
			"SELECT kr.id,kr.rol_id as rolId,r.rol_adi as rolAdi,"
					+ "kr.yetki_id as yetkiid,y.yetki_adi as yetkiadi,"
					+ "kr.guncelle,kr.sil FROM " + Ayarlar.SHEMA
					+ ".kullanici_rol_yetki kr join " + Ayarlar.SHEMA
					+ ".yetki y on (y.id=kr.yetki_id) " + "left outer join "
					+ Ayarlar.SHEMA + ".rol r on (r.rol_id=kr.rol_id) "
					+ " WHERE  kr.kullanici_id=?"), _KULLANICIBIRIMLISTESI_(
			"select birim_id as birimid,birim_kodu as birimkodu,kullanici_id as kullaniciid,birim_tip as birimtipi,il,"
					+ "0 as ilce from "
					+ Ayarlar.SHEMA
					+ ".kullanici_yetkili_birim a join "
					+ Ayarlar.SHEMA
					+ ".sube b on (kod=a.birim_kodu) "
					+ "where kullanici_id=? and a.birim_tip='S' "
					+ "union all "
					+ "select distinct birim_id as birimid,birim_kodu as birimkodu,kullanici_id as kullaniciid,birim_tip as birimtipi,il,"
					+ "ilce from "
					+ Ayarlar.SHEMA
					+ ".kullanici_yetkili_birim a join "
					+ Ayarlar.SHEMA
					+ ".temsilcilik_ililce b on (temsilcilik=a.birim_kodu) "
					+ "where kullanici_id=? and a.birim_tip='T'"), _KULLANICIDURUMGUNCELLE_(
			"update "
					+ Ayarlar.SHEMA
					+ ".kullanici set durum=?,eposta=?,soyadi=?,adi=?,kullanici_adi=?,"
					+ "kullanici_turu=?,telefon_no=?,gsm_telefon_no=?,onay_yetkisi=?,akreditasyon=?,il=?,sicilno=?,userid=?,belediye_ip=? where kullanici_id=?"), _KULLANICIBIRIMINSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".kullanici_yetkili_birim (birim_kodu, kullanici_id, birim_tip,userid) VALUES (?,?,?,?)"), _BIRIMDELETE_(
			"Delete from " + Ayarlar.SHEMA
					+ ".kullanici_yetkili_birim where kullanici_id=?"), _ROLLISTESI_(
			"SELECT rol_id as id,rol_adi as adi FROM " + Ayarlar.SHEMA
					+ ".rol r order by rol_id"), _YETKILISTESI_(
			"SELECT  id,yetki_adi as adi FROM " + Ayarlar.SHEMA
					+ ".yetki y order by id"), _SUBETEMSILCILIKLISTESI_(
			"SELECT kod,adi FROM " + Ayarlar.SHEMA
					+ ".temsilcilik where subesi=?"), _TEMSILCILIKLISTESI_(
			"SELECT kod,adi,subesi FROM " + Ayarlar.SHEMA + ".temsilcilik "), _TEMSILCILIKSUBE_(
			"SELECT kod,sube,eposta FROM " + Ayarlar.SHEMA
					+ ".sube WHERE kod=?"), _TEMSILCILIKBYKOD_(
			"SELECT kod,adi,subesi FROM " + Ayarlar.SHEMA
					+ ".temsilcilik WHERE kod=?"), _GETSUBELER_(
			"select kod, sube, eposta, sicilno, adres, smtp, port, kullanici_adi as kullaniciadi,il,"
					+ " parola, telefon_no as telefonno, faks, host_adres as hostadres from "
					+ Ayarlar.SHEMA + ".sube"), _GETTEMSILCILIKLER_(
			"select "
					+ "kod, adi, subesi, aciklama, vizeyetkisi,"
					+ " durumu, sicilno, eposta, adres, smtp, port, kullanici_adi as kullaniciadi, parola, "
					+ " telefon_no as telefonno, faks, host_adres as hostadres "
					+ " from " + Ayarlar.SHEMA
					+ ".temsilcilik where durumu='A'"), _ROLYETKIINSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".kullanici_rol_yetki (kullanici_id, rol_id, yetki_id, guncelle, sil,userid) VALUES (?,?,?,?,?,?)"), _ROLYETKIDELETE_(
			"DELETE FROM " + Ayarlar.SHEMA
					+ ".kullanici_rol_yetki where kullanici_id=?"), _KULLLANICI_INSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".kullanici(kullanici_adi, parola, adi, soyadi, sicilno,eposta,durum,kullanici_turu,telefon_no,"
					+ "gsm_telefon_no,onay_yetkisi,akreditasyon,il,userid,belediye_ip) "
					+ "VALUES ( ?,?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?)"), _GETTEMSILCILIKILLER_(
			"select distinct il from " + Ayarlar.SHEMA
					+ ".temsilcilik_ililce  where temsilcilik=?"), _RANDEVULIST_PARAMETRIK_(
			"SELECT randevu_tarihi,randevu_saati FROM " + Ayarlar.SHEMA
					+ ".randevu WHERE randevu_tarihi>? and randevu_tarihi<?+1"), _RANDEVULIST_(
			"SELECT randevu_tarihi, randevu_saati FROM " + Ayarlar.SHEMA
					+ ".randevu"), _RANDEVU_INSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".randevu("
					+ "randevu_tarihi, randevu_saati, randevu_durum, basvuru_id,userid) "
					+ "VALUES (?, ?, ?, ?,?)"), _RANDEVU_UPDATE_("UPDATE "
			+ Ayarlar.SHEMA
			+ ".randevu set randevu_tarihi=?,randevu_saati=?,userid=? where "
			+ "randevu_id=?"), _RANDEVU_SIL_("DELETE FROM " + Ayarlar.SHEMA
			+ ".randevu  where randevu_id=?"),

	_RANDEVU_MUHENDIS_INSERT(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".randevu_muhendis(randevu_id, muhendis_sicilno,sorumlu,userid) VALUES (?, ?,?,?)"),

	_RANDEVU_TARIHEGORE_("SELECT randevu_tarihi,randevu_saati FROM "
			+ Ayarlar.SHEMA
			+ ".randevu WHERE randevu_tarihi=to_date(?,'YYYY-MM-DD')"), _RANDEVU_IDEGORE_(
			"SELECT randevu_tarihi,randevu_saati,randevu_id FROM "
					+ Ayarlar.SHEMA + ".randevu WHERE randevu_id=?"), _RANDEVUFORBINA_(
			"select randevu_id as randevuid,randevu_tarihi as randevutarihi from "
					+ Ayarlar.SHEMA
					+ ".randevu a,"
					+ Ayarlar.SHEMA
					+ ".basvuru b where b.bina_id=? and a.basvuru_id=b.basvuru_id "
					+ "and  basvuru_durum='R'"),

	_MUHENDISLIST_(
			"SELECT "
					+ "distinct a.kullanici_id as kullaniciid, sicilno, adi, soyadi, gsm_telefon_no as gsmtelefonno,telefon_no as telefonno , eposta,il,onay_yetkisi as onayyetkisi,durum,"
					+ "akreditasyon as akreditasyondeger ,"
					+ "(select distinct il_adi from "
					+ Ayarlar.SHEMA
					+ ".ililcetb where il_kodu=a.il) as iladi,"
					+ "'('||substr(trim(cast(telefon_no as character(10))),1,3)||') '||"
					+ "substr(trim(cast(telefon_no as character(10))),4,3)||' '||substr(trim(cast(telefon_no as character(10))),7) as telefonnostr,"
					+ "'('||substr(trim(cast(gsm_telefon_no as character(10))),1,3)||') '||"
					+ "substr(trim(cast(gsm_telefon_no as character(10))),4,3)||' '||substr(trim(cast(gsm_telefon_no as character(10))),7) as gsmtelefonnostr,"
					+ "muhendis_gunluk_kontrol_adet as muhendisgunlukkontroladet,a.kullanici_turu as kullanicituru "
					+ " FROM "
					+ Ayarlar.SHEMA
					+ ".kullanici a,"
					+ Ayarlar.SHEMA
					+ ".kullanici_yetkili_birim b "
					+ "where  durum='A' and kullanici_turu in (1,5) and a.kullanici_id=b.kullanici_id and "
					+ "b.birim_tip=?"),

	_MUHENDISLISTFULL_(
			"SELECT "
					+ "distinct a.kullanici_id as kullaniciid,sicilno, adi, soyadi, gsm_telefon_no as gsmtelefonno,telefon_no as telefonno , eposta,il,onay_yetkisi as onayyetkisi,durum,"
					+ "akreditasyon as akreditasyondeger ,"
					+ "(select distinct il_adi from "
					+ Ayarlar.SHEMA
					+ ".ililcetb where il_kodu=a.il) as iladi,"
					+ "'('||substr(trim(cast(telefon_no as character(10))),1,3)||') '||"
					+ "substr(trim(cast(telefon_no as character(10))),4,3)||' '||substr(trim(cast(telefon_no as character(10))),7) as telefonnostr,"
					+ "'('||substr(trim(cast(gsm_telefon_no as character(10))),1,3)||') '||"
					+ "substr(trim(cast(gsm_telefon_no as character(10))),4,3)||' '||substr(trim(cast(gsm_telefon_no as character(10))),7) as gsmtelefonnostr,"
					+ "muhendis_gunluk_kontrol_adet as muhendisgunlukkontroladet,a.kullanici_turu as kullanicituru "
					+ " FROM "
					+ Ayarlar.SHEMA
					+ ".kullanici a,"
					+ Ayarlar.SHEMA
					+ ".kullanici_yetkili_birim b "
					+ "where kullanici_turu in (1,5) and a.kullanici_id=b.kullanici_id and "
					+ "b.birim_tip=?"), _ONAYCILIST_(
			"SELECT distinct sicilno, adi, soyadi, gsm_telefon_no as gsmtelefonno , eposta,il "
					+ "FROM "
					+ Ayarlar.SHEMA
					+ ".kullanici a,"
					+ Ayarlar.SHEMA
					+ ".kullanici_yetkili_birim b "
					+ "where  onay_yetkisi='E' and durum='A' and a.kullanici_id=b.kullanici_id and "
					+ "b.birim_tip=?"), _KULLANICILIST_(
			"SELECT a.kullanici_id,a.kullanici_adi,a.parola,a.adi,a.soyadi,a.sicilno,a.eposta,a.durum,a.kullanici_turu as kullanicituru,"
					+ "gsm_telefon_no as gsmtelefono,telefon_no as telefonno,akreditasyon as akreditasyondeger,onay_yetkisi as onayyetkisi,il,belediye_ip as belediyeip,"
					+ "'('||substr(trim(cast(telefon_no as character(10))),1,3)||') '||"
					+ "substr(trim(cast(telefon_no as character(10))),4,3)||' '||substr(trim(cast(telefon_no as character(10))),7) as telefonnostr,"
					+ "'('||substr(trim(cast(gsm_telefon_no as character(10))),1,3)||') '||"
					+ "substr(trim(cast(gsm_telefon_no as character(10))),4,3)||' '||substr(trim(cast(gsm_telefon_no as character(10))),7) as gsmtelefonnostr,"
					+ "b.tur as kullanicituradi "
					+ "FROM " + Ayarlar.SHEMA + ".kullanici a join "
					+Ayarlar.SHEMA + ".kullanici_turu b on (b.id=a.kullanici_turu)"), _FIRMALISTBASVURU_(
			"SELECT kod, unvan, adsoyad, gsm_telefon, il, ilce, adres, durumu, tescil_no,"
					+ "uygunluk_belgesi, gecerlilik_suresi, servis_sozlesme, sozlesme_tarihi,"
					+ "tse_belge_no, eposta,monte_eden as monteEden,"
					+ "yetkili_servis as yetkiliServis,"
					+ "tse_belgesi as tseBelgesi,"
					+ "telefon_no as telefonNo,"
					+ "telefon_no_dahili as dahili,"
					+ "ce_belge_tipi as ceBelgeTipi,"
					+ "'('||substr(trim(cast(faks as character(10))),1,3)||') '||"
					+ "substr(trim(cast(faks as character(10))),4,3)||' '||substr(trim(cast(faks as character(10))),7) as faksstr "
					+ " FROM "
					+ Ayarlar.SHEMA
					+ ".bakimci_firma where durumu='A' and exists (select 1 from "
					+ Ayarlar.SHEMA
					+ ".bakimci_firma_kapsam where firma_kod=kod and il=?)"),

	_FIRMALIST_(
			"SELECT kod, unvan, adsoyad, gsm_telefon, il, ilce, adres, durumu, tescil_no,"
					+ "uygunluk_belgesi, gecerlilik_suresi,"
					+ " servis_sozlesme,sozlesme_tarihi,"
					+ "tse_belge_no, eposta,monte_eden as monteEden,"
					+ "yetkili_servis as yetkiliServis,"
					+ "tse_belgesi as tseBelgesi," + "telefon_no as telefonNo,"
					+ "telefon_no_dahili as dahili,"
					+ "ce_belge_tipi as ceBelgeTipi ,"
					+ "'('||substr(trim(cast(faks as character(10))),1,3)||') '||"
					+ "substr(trim(cast(faks as character(10))),4,3)||' '||substr(trim(cast(faks as character(10))),7) as faksstr, "
					+ "(select distinct il_adi from " + Ayarlar.SHEMA
					+ ".ililcetb where il_kodu=c.il) as iladi" + " FROM "
					+ Ayarlar.SHEMA
					+ ".bakimci_firma c where exists (select 1 from "
					+ Ayarlar.SHEMA
					+ ".bakimci_firma_kapsam a where firma_kod=c.kod and  "), // il=?"),
	_FIRMABYKOD_(
			"SELECT kod, unvan, adsoyad, gsm_telefon, il, ilce, adres, durumu, tescil_no,"
					+ "uygunluk_belgesi, gecerlilik_suresi, servis_sozlesme, sozlesme_tarihi,"
					+ "tse_belge_no, eposta,monte_eden as monteEden,"
					+ "yetkili_servis as yetkiliServis,"
					+ "tse_belgesi as tseBelgesi," + "telefon_no as telefonNo,"
					+ "telefon_no_dahili as dahili,"
					+ "ce_belge_tipi as ceBelgeTipi,"
					+ "'('||substr(trim(cast(faks as character(10))),1,3)||') '||"
					+ "substr(trim(cast(faks as character(10))),4,3)||' '||substr(trim(cast(faks as character(10))),7) as faksstr "
					+ " FROM " + Ayarlar.SHEMA
					+ ".bakimci_firma WHERE kod=?"), _BASVURUCIHAZBAKIMCIFIRMASI_(
			"select "
					+ "b.bakimci_firma_id as kod, (case b.bakimci_firma_id when 999999 then 'Bina Yönetiminin yetki belgeli asansör bakım firmasıyla sözleşmesi bulunmamaktadır !' "
					+ "else a.unvan end) as unvan, "
					+ "a.adsoyad, a.gsm_telefon, il, ilce, a.adres, a.durumu, a.tescil_no,"
					+ "uygunluk_belgesi, gecerlilik_suresi, servis_sozlesme, sozlesme_tarihi,"
					+ "tse_belge_no, eposta,monte_eden as monteEden,"
					+ "yetkili_servis as yetkiliServis,"
					+ "tse_belgesi as tseBelgesi,"
					+ "telefon_no as telefonNo,"
					+ "telefon_no_dahili as dahili,"
					+ "ce_belge_tipi as ceBelgeTipi,"
					+ "'('||substr(trim(cast(faks as character(10))),1,3)||') '||"
					+ "substr(trim(cast(faks as character(10))),4,3)||' '||substr(trim(cast(faks as character(10))),7) as faksstr "
					+ " FROM "
					+ Ayarlar.SHEMA
					+ ".basvuru_asansor b left outer join "
					+ Ayarlar.SHEMA
					+ ".bakimci_firma a on  a.kod=b.bakimci_firma_id "
					+ "where b.basvuru_id=?  and b.cihaz_id=? and b.bakimci_firma_id>0"), _CIHAZBAKIMCIFIRMASI_(
			"select "
					+ "b.firma_id as kod, (case b.firma_id when 999999 then 'Bina Yönetiminin yetki belgeli asansör bakım firmasıyla sözleşmesi bulunmamaktadır !' "
					+ "else a.unvan end) as unvan, "
					+ "a.adsoyad, a.gsm_telefon, il, ilce, a.adres, a.durumu, a.tescil_no,"
					+ "uygunluk_belgesi, gecerlilik_suresi, servis_sozlesme, sozlesme_tarihi,"
					+ "tse_belge_no, eposta,monte_eden as monteEden,"
					+ "yetkili_servis as yetkiliServis,"
					+ "tse_belgesi as tseBelgesi," + "telefon_no as telefonNo,"
					+ "telefon_no_dahili as dahili,"
					+ "ce_belge_tipi as ceBelgeTipi ,"
					+ "'('||substr(trim(cast(faks as character(10))),1,3)||') '||"
					+ "substr(trim(cast(faks as character(10))),4,3)||' '||substr(trim(cast(faks as character(10))),7) as faksstr "
					 + " FROM "
					+ Ayarlar.SHEMA
					+ ".cihaz_anlasmali_firma b left outer join "
					+ Ayarlar.SHEMA
					+ ".bakimci_firma a on a.kod=b.firma_id and a.durumu='A' "
					+ "where b.cihaz_id=? and  b.sozlesme_tarih<=current_date and  b.sozlesme_bitis_tarih>=current_date order by b.sozlesme_tarih desc limit 1"),

	_FIRMADETAY(
			"SELECT "
					+ "kod, unvan, adsoyad,"
					+ "telefon_no as telefonno,"
					+ "telefon_no_dahili as dahili,"
					+ " gsm_telefon as gsmTelefon, il, ilce, adres, durumu, tescil_no as tescilno,"
					+ "uygunluk_belgesi as uygunlukBelgesi, gecerlilik_suresi as gecerliliksuresi,"
					+ " servis_sozlesme as servissozlesme, sozlesme_tarihi as sozlesmetarihi,"
					+ "tse_belge_no as tseBelgeNo,  eposta,monte_eden as monteEden,"
					+ "yetkili_servis as yetkiliServis,"
					+ "tse_belgesi as tseBelgesi,"
					+ "ce_belge_tipi as ceBelgeTipi,"
					+ "'('||substr(trim(cast(faks as character(10))),1,3)||') '||"
					+ "substr(trim(cast(faks as character(10))),4,3)||' '||substr(trim(cast(faks as character(10))),7) as faksstr " 
					+ " from " + Ayarlar.SHEMA
					+ ".bakimci_firma a " + "left outer join " + Ayarlar.SHEMA
					+ ".ililcetb b 	on (il_kodu=il and ilce_kodu=ilce)"
					+ " where kod=?"), _RANDEVULISTE_(
			"select q.randevu_id as randevuId,q.randevu_tarihi as randevuTarihi,q.randevu_saati as randevuSaati,a.bina_id as binaId,"
					+ "q.basvuru_id as basvuruId,c.basvuru_yapan_adi||' '||c.basvuru_yapan_soyadi as basvuruyapan,"
					+ "c.telefon_no as telefonno,a.bina_adi as binaAdi,concat(a.cadde_sokak,' ',a.mahalle,' MAHALLESİ NO:',a.bina_no) as binaAdres,"
					+ "(select toplam_tutar from "
					+ Ayarlar.SHEMA
					+ ".odeme where basvuru_id=c.basvuru_id) as kontroltutari,c.telefon_no_dahili as telefonnodahili "
					+ "from "
					+ Ayarlar.SHEMA
					+ ".randevu q,"
					+ Ayarlar.SHEMA
					+ ".basvuru c,"
					+ Ayarlar.SHEMA
					+ ".bina a "
					+ "where randevu_durum='R' and  q.basvuru_id=c.basvuru_id and a.bina_id=c.bina_id and "
					+ "exists (select 1 from "
					+ Ayarlar.SHEMA
					+ ".basvuru_asansor where basvuru_id=q.basvuru_id "
					+ "and  kontrol_tarihi is null ) and "), // d.il=?"),
					_BASVURUBAKIMCIFIRMAUPDATE_("update "
							+ Ayarlar.SHEMA+".basvuru_asansor set bakimci_firma_id=? where basvuru_id =(select basvuru_id from "
									+ Ayarlar.SHEMA+".randevu where randevu_id=?) and cihaz_id=?"),
	_BASVURUBAKIMCIFIRMALAR_(
			"select "
					+ "b.bakimci_firma_id as kod, (case b.bakimci_firma_id when 999999 then 'Bina Yönetiminin yetki belgeli asansör bakım firmasıyla sözleşmesi bulunmamaktadır !' "
					+ "else a.unvan end) as unvan, "
					+ "a.adsoyad, a.gsm_telefon, il, ilce, a.adres, a.durumu, a.tescil_no,"
					+ "uygunluk_belgesi, gecerlilik_suresi, servis_sozlesme, sozlesme_tarihi,"
					+ "tse_belge_no, eposta,monte_eden as monteEden,"
					+ "yetkili_servis as yetkiliServis,"
					+ "tse_belgesi as tseBelgesi,"
					+ "telefon_no as telefonNo,"
					+ "telefon_no_dahili as dahili,"
					+ "ce_belge_tipi as ceBelgeTipi "
					+ " FROM "
					+ Ayarlar.SHEMA
					+ ".basvuru_asansor b left outer join "
					+ Ayarlar.SHEMA
					+ ".bakimci_firma a on a.kod=(case b.bakimci_firma_id when null then 0 else bakimci_firma_id end) "
					+ "where b.basvuru_id=? and b.bakimci_firma_id>0"), _RANDEVULISTEMUHENDISEGORE_(
			"select q.randevu_id as randevuId,q.randevu_tarihi as randevuTarihi,q.randevu_saati as randevuSaati,a.bina_id as binaid,"
					+ "q.basvuru_id as basvuruId,c.basvuru_yapan_adi||' '||c.basvuru_yapan_soyadi as basvuruyapan,"
					+ "c.telefon_no as telefonno,a.bina_adi as binaAdi,concat(a.cadde_sokak,' ',a.mahalle,' MAHALLESİ NO:',a.bina_no) as binaAdres,"
					+ "(select toplam_tutar from "
					+ Ayarlar.SHEMA
					+ ".odeme where basvuru_id=c.basvuru_id) as kontroltutari,c.telefon_no_dahili as telefonnodahili "
					+ "from "
					+ Ayarlar.SHEMA
					+ ".randevu q,"
					+ Ayarlar.SHEMA
					+ ".basvuru c,"
					+ Ayarlar.SHEMA
					+ ".bina a,"
					+ Ayarlar.SHEMA
					+ ".randevu_muhendis m "
					+ "where randevu_durum='R' and  q.basvuru_id=c.basvuru_id and a.bina_id=c.bina_id "
					+ "and m.randevu_id=q.randevu_id and m.muhendis_sicilno=? and "
					+ "exists (select 1 from "
					+ Ayarlar.SHEMA
					+ ".basvuru_asansor where basvuru_id=q.basvuru_id "
					+ "and kontrol_tarihi is null)"), _GETRANDEVUMUHENDIS_(
			"select b.sicilno,b.adi||' '||b.soyadi as adisoyadi,b.gsm_telefon_no as telefonNo,b.eposta,a.sorumlu,"
			+ "case kt.id when 1 then 'MAKİNA MÜHENDİSİ' else upper(replace(kt.tur,'i','İ')) end as unvan from "
					+ Ayarlar.SHEMA
					+ ".randevu_muhendis a,"
					+ Ayarlar.SHEMA
					+ ".kullanici b "
					+ "left outer join "
					
					+ Ayarlar.SHEMA+".kullanici_turu kt on (kt.id=b.kullanici_turu) "
					+ "where a.muhendis_sicilno=b.sicilno and a.randevu_id=? and b.kullanici_turu in (1,5) and b.durum='A'"),

	_TESTYANITBYKONTROLID_(
			"select a.cevap_id as cevapid,a.kontrol_id as kontrolid,a.soru_id as soruid,a.cevap,a.aciklama,b.soru,b.parent,b.bakanlik_soru_id as bakanlikSoruId  from "
					+ Ayarlar.SHEMA + ".kontrol_test a left outer join "
							+Ayarlar.SHEMA+ ".soru b on (b.soru_id=a.soru_id) where kontrol_id=?"), _SORULIST_(
			"SELECT soru_id,parent,soru,cihaz_tipi,sorun,yildiz,aktif FROM "
					+ Ayarlar.SHEMA + ".soru_sirali"), _GETSORUBYSORUID_(
			"soru_id,parent,soru,cihaz_tipi,sorun,yildiz,aktif,sira_no as sirano,"
					+ "kapsam_id as kapsamid FROM " + Ayarlar.SHEMA
					+ ".soru_sirali WHERE soru_id=?"), _ANASORULISTTESTSORUGIRIS_(
			"SELECT soru_id,parent,soru,cihaz_tipi,sorun,yildiz,aktif,sira_no as sirano,"
					+ "kapsam as kapsamarr,tarih  FROM " + Ayarlar.SHEMA
					+ ".soru_sirali WHERE parent is null AND cihaz_tipi=? and (case when tarih is null then '0001-01-01' else tarih end )=?"), _ANASORULIST_(
			"SELECT soru_id,parent,soru,cihaz_tipi,sorun,yildiz,aktif,sira_no as sirano,"
					+ "kapsam as kapsamarr,tarih,sinif FROM " + Ayarlar.SHEMA
					+ ".soru_sirali WHERE parent is null AND cihaz_tipi=? and (case when tarih is null then '0001-01-01' else tarih end )=? "), _ALTSORULIST_(
			"SELECT soru_id,parent,soru,cihaz_tipi,sorun,yildiz,aktif,sira_no as sirano,kapsam   as kapsamarr,tarih,sinif "
					+ " FROM "
					+ Ayarlar.SHEMA
					+ ".soru_sirali WHERE sorun like ? AND cihaz_tipi=?  and parent is not null and (case when tarih is null then '0001-01-01' else tarih end )=? "), _TESTALTSORULIST_(
			"SELECT soru_id,parent,soru,cihaz_tipi,sorun,yildiz,aktif,sira_no as sirano,"
					+ "kapsam as kapsamarr,tarih,sinif FROM "
					+ Ayarlar.SHEMA
					+ ".soru_sirali WHERE parent=? AND cihaz_tipi=? order by sira_no"), _ONTANIMLISORULAR_(
			"SELECT soru_ontanimli_id as id, aciklama, soru_id as soruid, durum FROM "
					+ Ayarlar.SHEMA
					+ ".soru_ontanimli where soru_id=? and durum='A'"), _ONTANIMLISORULARALL_(
			"SELECT soru_ontanimli_id as id, aciklama, soru_id as soruid, durum FROM "
					+ Ayarlar.SHEMA + ".soru_ontanimli where soru_id=?"), _SORU_(
			"SELECT soru_id,parent,soru,cihaz_tipi,sorun,yildiz,aktif,sinif FROM "
					+ Ayarlar.SHEMA + ".soru_sirali WHERE soru_id=? "),

	_MENULIST_("SELECT menuitem_id,baslik,ustmenuitem_id,link,sira_no FROM "
			+ Ayarlar.SHEMA + ".menu_tree ORDER BY path"), _ANAMENULIST_(
			"SELECT menuitem_id,baslik,ustmenuitem_id,link,sira_no  FROM "
					+ Ayarlar.SHEMA
					+ ".menu_tree WHERE ustmenuitem_id is null ORDER BY path"), _ALTMENULIST_(
			"SELECT menuitem_id,baslik,ustmenuitem_id,link,sira_no FROM "
					+ Ayarlar.SHEMA
					+ ".menu_tree WHERE ustmenuitem_id =? AND ustmenuitem_id is not null ORDER BY path"),

	_GETALLBELEDIYE(
			"select kod,adi,temsilcilik,sube,ilce,il,(select distinct il_adi from "
					+ Ayarlar.SHEMA
					+ ".ililcetb where il_kodu=a.il) as ilstr,"
					+ "(select ilce_adi from "
					+ Ayarlar.SHEMA
					+ ".ililcetb where il_kodu=a.il and ilce_kodu=a.ilce) as ilcestr,uavt_kod as uavtkod "
					+ " from " + Ayarlar.SHEMA + ".belediye a where "), _GETSOZLESMESIBITECEKBELEDIYELER(
			"select distinct kod,adi,temsilcilik,sube,ilce,il,(select distinct il_adi from "
					+ Ayarlar.SHEMA
					+ ".ililcetb where il_kodu=a.il) as ilstr,"
					+ "(select ilce_adi from "
					+ Ayarlar.SHEMA
					+ ".ililcetb where il_kodu=a.il and ilce_kodu=a.ilce) as ilcestr "
					+ " from "
					+ Ayarlar.SHEMA
					+ ".belediye a,"
					+ Ayarlar.SHEMA
					+ ".belediye_sozlesme b  where a.kod=b.belediye_kod and "
					+ "extract(year from sozlesme_bitis_tarihi)=extract(year from current_date) and "
					+ " extract(day from sozlesme_bitis_tarihi)>=extract(day from current_date) and "
					+ " extract(month from sozlesme_bitis_tarihi)=extract(month from current_date) "),

	_CIHAZTEKNIKBILGILER_(
			"select a.kod,a.sira,a.baslik,b.deger_id as degerid,b.tipi,b.deger,b.birim,c.cevap,c.cevap_id as cevapid from "
					+ Ayarlar.SHEMA
					+ ".cihaz_turu_teknik_ozellikleri a,"
					+ Ayarlar.SHEMA
					+ ".cihaz_turu_teknik_ozellik_deger b "
					+ "left outer join "
					+ Ayarlar.SHEMA
					+ ".cihaz_deger c on (c.deger_id=b.deger_id and c.cihaz_id=? and cihaz_id>0)"
					+ " where a.kod=b.teknik_ozellik_kod and a.cihaz_turu_kod=? order by sira,b.deger_id"),

	_CIHAZINSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".cihaz(cihaz_tip_id, bina_id,kimlik_no,userid,durum,uavt_kod,uavt_sira_no,uavt_etiket)    VALUES (?, ?,"
					+ "(select " + Ayarlar.SHEMA
					+ ".asansorkimliknextval(?,?,?,?)),?,?,?,?,?)"), _CURRVALCIHAZ_(
			"SELECT currval(pg_get_serial_sequence('" + Ayarlar.SHEMA
					+ ".cihaz', 'cihaz_id'))"), _CIHAZTEKNIKBILGILERINSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".cihaz_deger(cihaz_id, deger_id, cevap,userid)  VALUES ( ?, ?, ?,?)"), _CIHAZTEKNIKBILGILERDELETE_(
			"DELETE from " + Ayarlar.SHEMA + ".cihaz_deger where cihaz_id=?"),

	_GETCIHAZ_(
			"select distinct c.kod,c.sira,c.baslik,d.deger_id as degerid,c.cihaz_turu_kod as tipi,d.birim,0 as deger,"
					+ "a.cevap from "
					+ Ayarlar.SHEMA
					+ ".cihaz_turu_teknik_ozellikleri c join "
					+ Ayarlar.SHEMA
					+ ".cihaz_turu_teknik_ozellik_deger d on (d.teknik_ozellik_kod=c.kod) "
					+ "left outer join "
					+ Ayarlar.SHEMA
					+ ".cihaz_deger a  on (a.deger_id=d.deger_id and a.cihaz_id=?) "
					+ "where c.cihaz_turu_kod=? order by c.sira"), 
					_GETBINABYCIHAZFIRMA_("select distinct c.bina_id as binaid from "
							+ Ayarlar.SHEMA+".cihaz c join "
							+  Ayarlar.SHEMA+".cihaz_anlasmali_firma caf on (caf.cihaz_id=c.cihaz_id and caf.firma_id=?) "
							+ " where c.bina_id=?"),
					_GETCIHAZLAR_(
			"select a.cihaz_id as cihazid,a.cihaz_tip_id as tip,a.bina_id as binaid ,a.kimlik_no as kimlikno,"
					+ "b.cihaz_tip_aciklama as tipAciklama,k.etiket as sonKontrolEtiketi,"
					+ "k.kontrol_Bitis_Tarihi as sonkontroltarihi,a.durum,a.uavt_kod as uavtkod,a.uavt_etiket as uavtetiket,a.uavt_sira_no as uavtsirano "
					+ " from "
					+ Ayarlar.SHEMA
					+ ".cihaz a join "
					+ Ayarlar.SHEMA
					+ ".cihaz_turu b using (cihaz_tip_id) "
					+ "left outer join"
					+ "(select distinct cihaz_id,etiket,kontrol_bitis_tarihi  from "
					+ Ayarlar.SHEMA
					+ ".kontrol knt where "
					+ "kontrol_bitis_tarihi in (select max(kontrol_bitis_tarihi) "
					+ "from "
					+ Ayarlar.SHEMA
					+ ".kontrol where cihaz_id=knt.cihaz_id)) k on (k.cihaz_id=a.cihaz_id) "
					+ " where  bina_id=?"), _GETCIHAZLARFORKONTROL_(
			"select  a.cihaz_id as cihazid,a.cihaz_tip_id as tip,a.bina_id as binaid ,a.kimlik_no as kimlikno,"
					+ "b.cihaz_tip_aciklama as tipAciklama,ba.kontrol_tarihi as kontroltarihi,k.etiket as sonkontroletiketi,"
					+ "k.kontrol_Bitis_Tarihi as sonkontroltarihi,a.uavt_kod as uavtkod,a.uavt_etiket as uavtetiket,a.uavt_sira_no as uavtsirano "
					+ " from "
					+ Ayarlar.SHEMA
					+ ".cihaz a join "
					+ Ayarlar.SHEMA
					+ ".cihaz_turu b using (cihaz_tip_id) join "
					+ Ayarlar.SHEMA
					+ ".randevu r on (r.randevu_id=?) join "
					+ Ayarlar.SHEMA
					+ ".basvuru_asansor ba on (ba.basvuru_id=r.basvuru_id) "
					+ "left outer join"
					+ "(select distinct cihaz_id,etiket,kontrol_bitis_tarihi  from "
					+ Ayarlar.SHEMA
					+ ".kontrol knt where "
					+ "kontrol_bitis_tarihi in (select max(kontrol_bitis_tarihi) "
					+ "from "
					+ Ayarlar.SHEMA
					+ ".kontrol where cihaz_id=knt.cihaz_id)) k on (k.cihaz_id=a.cihaz_id) "
					+ "where  bina_id=? and " + "a.cihaz_id=ba.cihaz_id"),

	_GETCIHAZ_BY_CIHAZID_(
			"select a.cihaz_id as cihazid,a.cihaz_tip_id as tip,a.bina_id as binaid,a.kimlik_no as kimlikno,b.cihaz_tip_aciklama as tipAciklama,a.uavt_kod as uavtkod,a.uavt_etiket as uavtetiket,a.uavt_sira_no as uavtsirano from "
					+ Ayarlar.SHEMA
					+ ".cihaz a,"
					+ Ayarlar.SHEMA
					+ ".cihaz_turu b where a.cihaz_tip_id=b.cihaz_tip_id and a.cihaz_id=?"), _ONTANIMLITESTINSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".kontrol_ontanimli_test (kontrol_id,parent_id,soru_id,ontanimli_id,userid) "
					+ "VALUES(?,?,?,?,?)"), _CEVAPINSERT_("INSERT INTO "
			+ Ayarlar.SHEMA
			+ ".kontrol_test(kontrol_id,soru_id,cevap,aciklama,userid) "
			+ "VALUES(?,?,?,?,?)"), _BELEDIYEILETISIMINSERT_(
			"insert into "
					+ Ayarlar.SHEMA
					+ ".belediye_iletisim  (belediye_kod, adi, soyadi, unvani,telefonno, eposta,userid) values (?, ?, ?, ?, ?, ?,?)"), _BELEDIYEILETISIMDELETE_(
			"delete from " + Ayarlar.SHEMA + ".belediye_iletisim  where id =?"), _BELEDIYESOZLESMEDELETE_(
			"delete from " + Ayarlar.SHEMA + ".belediye_sozlesme  where id =?"), _BELEDIYEILETISIMUPDATE_(
			"update "
					+ Ayarlar.SHEMA
					+ ".belediye_iletisim  "
					+ "SET belediye_kod=?, adi=?, soyadi=?, unvani=?, telefonno=?, eposta=?,userid=? "
					+ "where id =?"), _BELEDIYESOZLESMEUPDATE_(
			"update "
					+ Ayarlar.SHEMA
					+ ".belediye_sozlesme  "
					+ "SET belediye_kod=?, yil=?,  cihaz_tipi=?, sozlesme_tarihi=?, sozlesme_bitis_tarihi=?, "
					+ "fiyat=?,kapasite_olcut=?,kapasite=?,sozlesme_bina_tip_id=?,kontrol_tipi=?,userid=? "
					+ "where id =?"),

	_BELEDIYEINSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".belediye(adi,  temsilcilik, sube, il, ilce,userid,uavt_kod)  VALUES (?, ?,  ?, ?, ?,?,?)"), _BELEDIYEUPDATE_(
			"UPDATE "
					+ Ayarlar.SHEMA
					+ ".belediye set adi=?,  temsilcilik=?, sube=?, il=?, ilce=?,userid=?,uavt_kod=? where kod=?"), _BELEDIYESOZLESMEINSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".belediye_sozlesme(belediye_kod, yil,  cihaz_tipi, sozlesme_tarihi,"
					+ "sozlesme_bitis_tarihi,fiyat, kapasite_olcut, kapasite,sozlesme_bina_tip_id,kontrol_tipi,userid) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)"), _GETBELEDIYEILETISIM_(
			"SELECT id,belediye_kod as belediyekod, adi, soyadi, unvani, telefonno, eposta from "
					+ Ayarlar.SHEMA + ".belediye_iletisim where belediye_kod=?"), _GETBELEDIYESOZLESME_(
			"SELECT belediye_kod as belediyekod, yil,cihaz_tipi as cihaztipi,"
					+ "sozlesme_tarihi as sozlesmebaslangictarihi, sozlesme_bitis_tarihi as sozlesmebitistarihi,"
					+ "fiyat, kapasite_olcut as kapasiteolcut, kapasite, id,sozlesme_bina_tip_id as sozlesmebinatipid,kontrol_tipi as kontroltipi from "
					+ Ayarlar.SHEMA + ".belediye_sozlesme where belediye_kod=?"), _GETBINASORUMLULUKTUR_(
			"select id,tip,aciklama from " + Ayarlar.SHEMA
					+ ".bina_kisi_sorumluluk_tip"), _BINAKISIINSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".bina_kisi("
					+ "kayit_tarihi, bina_id, tckimlikno, adi, soyadi, sorumluluk_turu,"
					+ "telefon_no, telefon_no_gsm, e_posta,userid,telefon_no_dahili) "
					+ "VALUES (current_date, ?, ?, ?, ?, ?, ?, ?, ?,?,?)"),

	_BINAKISIUPDATE_("UPDATE " + Ayarlar.SHEMA + ".bina_kisi set "
			+ "tckimlikno=?, adi=?, soyadi=?, sorumluluk_turu=?,"
			+ "telefon_no=?, telefon_no_gsm=?, e_posta=?,userid=?,telefon_no_dahili=? where id=? "), _GETBINAKISI_(
			"select id from " + Ayarlar.SHEMA
					+ ".bina_kisi where tckimlikno=? and bina_id=?"), _GETBINAKISILER_(
			"select id,kayit_tarihi as kayittarihi,bina_id as binaid,tckimlikno,adi,soyadi,sorumluluk_turu as sorumlulukturu,"
					+ "telefon_no as telefonno,'('||substr(trim(cast(telefon_no as character(10))),1,3)||') '||"
					+ "substr(trim(cast(telefon_no as character(10))),4,3)||' '||substr(trim(cast(telefon_no as character(10))),7) as telefonnostr,"
					+ "telefon_no_gsm as telefonnogsm,'('||substr(trim(cast(telefon_no_gsm as character(10))),1,3)||') '||"
					+ "substr(trim(cast(telefon_no_gsm as character(10))),4,3)||' '||"
					+ "substr(trim(cast(telefon_no_gsm as character(10))),7) as telefonnogsmstr,"
					+ "e_posta as eposta,telefon_no_dahili as telefonnodahili  from "
					+ Ayarlar.SHEMA
					+ ".bina_kisi where bina_id=? order by kayit_tarihi desc"),

	_MUHENDISRANDEVUKONTROL_(
			"select a.randevu_id as randevuid from "
					+ Ayarlar.SHEMA
					+ ".randevu_muhendis a,"
					+ Ayarlar.SHEMA
					+ ".randevu b where a.muhendis_sicilno=? and a.randevu_id=b.randevu_id and "
					+ "b.randevu_tarihi=? and cast(b.randevu_saati as time) between ? and ?"), _MUHENDISRANDEVUADETKONTROL_(
			"select a.randevu_id as randevuid,c.kontrol_turu as kontrolturu from "
					+ Ayarlar.SHEMA
					+ ".randevu_muhendis a,"
					+ Ayarlar.SHEMA
					+ ".randevu b,"
					+ Ayarlar.SHEMA
					+ ".basvuru_asansor c where a.muhendis_sicilno=? and a.randevu_id=b.randevu_id and c.basvuru_id=b.basvuru_id and "
					+ "b.randevu_tarihi=?"),

	_FIRMAINSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".bakimci_firma ("
					+ " unvan, adsoyad, gsm_telefon, il, ilce, adres, durumu, tescil_no, "
					+ " uygunluk_belgesi, gecerlilik_suresi, servis_sozlesme, sozlesme_tarihi,"
					+ " tse_belge_no, eposta, monte_eden, yetkili_servis, tse_belgesi,"
					+ " telefon_no, telefon_no_dahili, ce_belge_tipi,userid,faks) "
					+ "VALUES ( ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?,"
					+ " ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?,?,?)"),

	_FIRMAUPDATE_(
			"UPDATE "
					+ Ayarlar.SHEMA
					+ ".bakimci_firma "
					+ "SET unvan=?, adsoyad=?, gsm_telefon=?, il=?, ilce=?, adres=?, durumu=?, tescil_no=?,"
					+ "uygunluk_belgesi=?, gecerlilik_suresi=?, servis_sozlesme=?, sozlesme_tarihi=?,"
					+ "tse_belge_no=?, eposta=?,monte_eden=?,yetkili_Servis=?,tse_belgesi=?,"
					+ "telefon_no=?,telefon_no_dahili=?,ce_belge_tipi=?,userid=?,faks=? "
					+ " WHERE kod=?"), _GETKONTROLLER_(
			"select kontrol_id as kontrolid,randevu_id as randevuid,kontrol_bitis_tarihi as kontrolbitistarihi,"

					+ "onaylayan_kullanici_id as onaylayanSicilNo,"
					+ "kontrol_bitis_saati as kontrolbitissaati,a.cihaz_id as cihazid,"
					+ "etiket,case etiket when 'S' then 1 when 'M' then 2 when 'K' then 3 when 'Y' then 4 else 0 end as etiketint,"
					+ "b.kimlik_no as kimlikno,kontrol_turu as kontrolturu,"
					+ "a.aciklamalar, a.revizyon_rapor as revizyonrapor,a.rapor_iptal as raporiptal from "
					+ Ayarlar.SHEMA
					+ ".kontrol a join "
					+ Ayarlar.SHEMA
					+ ".cihaz b using (cihaz_id) where not exists (select 1 from "
					+ Ayarlar.SHEMA
					+ ".rapor where kontrol_id=a.kontrol_id) and a.kontrol_baslangic_tarihi>? "),
					_GETKONTROLFORKONTROLLISTESI_(
							"select kontrol_id as kontrolid,a.randevu_id as randevuid,kontrol_bitis_tarihi as kontrolbitistarihi,"
							+ "kontrol_baslangic_tarihi as kontrolbaslangictarihi,kontrol_baslangic_saati as kontrolbaslangicsaati,"
									+ "onaylayan_kullanici_id as onaylayanSicilNo,"
									+ "kontrol_bitis_saati as kontrolbitissaati,a.cihaz_id as cihazid,"
									+ "etiket,case etiket when 'S' then 1 when 'M' then 2 when 'K' then 3 when 'Y' then 4 else 0 end as etiketint,"
									+ "b.kimlik_no as kimlikno,kontrol_turu as kontrolturu,"
									+ "a.aciklamalar, a.revizyon_rapor as revizyonrapor,a.rapor_iptal as raporiptal from "
									+ Ayarlar.SHEMA
									+ ".kontrol a join "
									+ Ayarlar.SHEMA+".randevu_muhendis rm on (rm.randevu_id=a.randevu_id and rm.muhendis_sicilno=?) join "
									+ Ayarlar.SHEMA
									+ ".cihaz b using (cihaz_id) where (onay_durum is null or onay_durum=' ' or onay_durum='') and  a.kontrol_baslangic_tarihi>=? "),

	_GETKONTROLLER__(
			"select kontrol_id as kontrolid,randevu_id as randevuid,kontrol_bitis_tarihi as kontrolbitistarihi,"
					+ "onaylayan_kullanici_id as onaylayanSicilNo,"
					+ "kontrol_bitis_saati as kontrolbitissaati,a.cihaz_id as cihazid,"
					+ "etiket,case etiket when 'S' then 1 when 'M' then 2 when 'K' then 3 when 'Y' then 4 else 0 end as etiketint,"
					+ "b.kimlik_no as kimlikno,kontrol_turu as kontrolturu,"
					+ "a.aciklamalar, a.revizyon_rapor as revizyonrapor,a.rapor_iptal as raporiptal from "
					+ Ayarlar.SHEMA
					+ ".kontrol a join "
					+ Ayarlar.SHEMA
					+ ".cihaz b using (cihaz_id) where not exists (select 1 from "
					+ Ayarlar.SHEMA
					+ ".rapor where kontrol_id=a.kontrol_id) and a.kontrol_baslangic_tarihi>=? and (a.onaylayan_kullanici_id =? or "
					+ Ayarlar.SHEMA + ".getRandevuMuhendis(?,a.randevu_id)>0)"),

	_GETCIHAZKONTROLTARIHLERI_(
			"select "
					+ "kontrol_id as kontrolid,randevu_id as randevuid,kontrol_bitis_tarihi as kontrolbitistarihi,"
					+ "kontrol_bitis_saati as kontrolbitissaati,"
					+ "kontrol_baslangic_tarihi as kontrolbaslangictarihi,"
					+ "kontrol_baslangic_saati as kontrolbaslangicsaati,"
					+ "etiket,onay_durum as onaydurum,onaylayan_kullanici_id as onaylayansicilno,"
					+ "onay_tarihi as onaytarihi,"
					+ "kontrol_turu as kontrolturu,a.aciklamalar,revizyon_rapor as revizyonRapor,kontrol_muhendisi_sicilno as kontrolMuhendisiSicilNo, "
					+ "(select adi||' '||soyadi from "
					+ Ayarlar.SHEMA
					+ ".KULLANICI where sicilno=onaylayan_kullanici_id) as onaylayanadisoyadi,a.rapor_iptal as raporiptal "

					+ " from " + Ayarlar.SHEMA + ".kontrol a "
					+ "where cihaz_id=? order by kontrol_bitis_tarihi desc"), _GETKONTROLBYKONTROLID_(
			"SELECT kontrol_id as kontrolid, randevu_id as randevuid, kontrol_baslangic_tarihi as kontrolBaslangicTarihi,"
					+ " kontrol_baslangic_saati as kontrolBaslangicSaati,"
					+ "kontrol_bitis_tarihi as kontrolBitisTarihi, kontrol_bitis_saati as kontrolBitisSaati, cihaz_id as cihazId, etiket,"
					+ "onay_durum as onayDurum, onaylayan_kullanici_id as onaylayanSicilNo, onay_tarihi as onayTarihi, kontrol_turu as kontrolTuru,"
					+ "aciklamalar, revizyon_rapor as revizyonRapor, kontrol_muhendisi_sicilno as kontrolMuhendisiSicilNo, userid"
					+ " FROM " + Ayarlar.SHEMA + ".kontrol where kontrol_id=?"),

	_GETCIHAZKONTROLTARIHLERIFORRAPOR_(
			"select "
					+ "kontrol_id as kontrolid,randevu_id as randevuid,kontrol_bitis_tarihi as kontrolbitistarihi,"
					+ "kontrol_bitis_saati as kontrolbitissaati,"
					+ "kontrol_baslangic_tarihi as kontrolbaslangictarihi,"
					+ "kontrol_baslangic_saati as kontrolbaslangicsaati,"
					+ "etiket,onay_durum as onaydurum,onaylayan_kullanici_id as onaylayansicilno,"
					+ "onay_tarihi as onaytarihi,"
					+ "kontrol_turu as kontrolturu,a.aciklamalar,revizyon_rapor as revizyonRapor,kontrol_muhendisi_sicilno as kontrolMuhendisiSicilNo, "
					+ "(select adi||' '||soyadi from "
					+ Ayarlar.SHEMA
					+ ".KULLANICI where sicilno=onaylayan_kullanici_id) as onaylayanadisoyadi "

					+ " from "
					+ Ayarlar.SHEMA
					+ ".kontrol a "
					+ "where cihaz_id=? and kontrol_id!=? order by kontrol_bitis_tarihi desc"), _GETCIHAZONCEKIKONTROLLER_(
			"select kontrol_bitis_tarihi as kontrolBitisTarihi,kontrol_turu as kontrolturu from "
					+ Ayarlar.SHEMA
					+ ".kontrol a "
					+ "where cihaz_id=? and kontrol_bitis_tarihi<? order by kontrol_bitis_tarihi"), _KONTROLINSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".kontrol("
					+ "randevu_id, kontrol_baslangic_tarihi, kontrol_baslangic_saati,kontrol_bitis_tarihi,kontrol_bitis_saati, cihaz_id,"
					+ "etiket,onaylayan_kullanici_id,kontrol_turu,aciklamalar,revizyon_rapor,kontrol_muhendisi_sicilno,userid)"
					+ "VALUES ( ?, ?, ?, ?,?,?,?,?,?,?,?,?,?)"), _CURRVALKONTROL_(
			"SELECT currval(pg_get_serial_sequence('" + Ayarlar.SHEMA
					+ ".kontrol', 'kontrol_id'))"), _RAPORDOSYASIOLUSTUR_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".rapor(kontrol_id, dosya_adi, rapor_tarihi,userid,rapor_iptal)   VALUES (?, ?, ?,?,false)"), _RAPORKAYDET_(
			"UPDATE " + Ayarlar.SHEMA
					+ ".rapor set dosya=? ,userid=? where rapor_id=?"), _RAPORSIL_(
			"Delete from " + Ayarlar.SHEMA + ".rapor where rapor_id=?"), _RAPORSILBYKONTROLID_(
			"Delete from " + Ayarlar.SHEMA + ".rapor where kontrol_id=?"), _RAPOROANYLA_(
			"Update "
					+ Ayarlar.SHEMA
					+ ".kontrol set onay_durum='O',onay_tarihi=current_date,userid=? where kontrol_id=?"), _KONTROLDURUMUPDATE_(
			"Update " + Ayarlar.SHEMA
					+ ".kontrol set onay_durum=?,onay_tarihi=null,userid=? where kontrol_id=?"),

	_GETRAPORLARBYBINA1_(

			"select rapor_id as raporid, q.kontrol_id as kontrolid, dosya, dosya_adi as dosyaadi,a.bina_adi as binaAdi, "
					+ "q.kontrol_kod_eski as eskiKontrolKodu,  d.basvuru_id as basvuruId,b.revizyon_rapor as revizyonRapor,"
					+ "rapor_tarihi as raportarih,cihaz.uavt_etiket as asansorUavtEtiket, "
					+ "(case when b.onay_durum='O' then 'ONAYLANDI' "
					+ " when b.onay_durum='M' then 'ONAYCI ONAYI BEKLENİYOR' "
					+ " when b.onay_durum='Y' then 'YAZDIRILMAMIŞ' "
					+ " when b.onay_durum='T' then 'MÜHENDİS ONAYI BEKLENİYOR' end) as onayDurumu,"
					+ "b.onay_tarihi as onayTarihi ,"
					+ "b.etiket ,c.randevu_id as randevuid,"
					+ "a.tescilno as binaTescilNo,"
					+ "b.kontrol_Bitis_Tarihi as kontroltarihi, "
					+ "a.bina_adi," + "(select cevap from "
					+ Ayarlar.SHEMA
					+ ".cihaz_deger where cihaz_id=b.cihaz_id and deger_id in (151,171)) as asansorunyeri,q.rapor_iptal as raporiptal "
					+ " from "
					+ Ayarlar.SHEMA
					+ ".rapor q,"
					+ Ayarlar.SHEMA
					+ ".kontrol b,"
					+ Ayarlar.SHEMA
					+ ".randevu c,"
					+ Ayarlar.SHEMA
					+ ".basvuru d ,"
					+ Ayarlar.SHEMA
					+ ".bina a, "
					+ Ayarlar.SHEMA
					+ ".cihaz "

					+ "where "
					+ " q.kontrol_id=b.kontrol_id "
					+ " and "
					+ " c.basvuru_id=d.basvuru_id "
					+ " and "
					+ " c.randevu_id=b.randevu_id "
					+ " and "
					+ " a.bina_id=d.bina_id "
					+ " and "
					+ " a.bina_id=? and "
					+ " cihaz.cihaz_id=b.cihaz_id "
					+ " and q.rapor_tarihi<='2014-12-07' and "
					+ "position(rtrim(ltrim(cast(cihaz.cihaz_id as varchar(20)))) in q.dosya_adi)>0 "), _GETRAPORLARBYBINA2_(
			"select rapor_id as raporid, q.kontrol_id as kontrolid, dosya, dosya_adi as dosyaadi,a.bina_adi as binaAdi, "
					+ "q.kontrol_kod_eski as eskiKontrolKodu,  d.basvuru_id as basvuruId,b.revizyon_rapor as revizyonRapor,"
					+ "rapor_tarihi as raportarih,cihaz.uavt_etiket as asansorUavtEtiket, "
					+ "(case when b.onay_durum='O' then 'ONAYLANDI' "
					+ " when b.onay_durum='M' then 'ONAYCI ONAYI BEKLENİYOR' "
					+ " when b.onay_durum='Y' then 'YAZDIRILMAMIŞ' "
					+ " when b.onay_durum='T' then 'MÜHENDİS ONAYI BEKLENİYOR' end) as onayDurumu,"
					+ "b.onay_tarihi as onayTarihi ,"
					+ "b.etiket ,c.randevu_id as randevuid,"
					+ "a.tescilno as binaTescilNo,"
					+ "b.kontrol_Bitis_Tarihi as kontroltarihi, "
					+ "a.bina_adi ," + "(select cevap from "
					+ Ayarlar.SHEMA
					+ ".cihaz_deger where cihaz_id=b.cihaz_id and deger_id in (151,171)) as asansorunyeri,q.rapor_iptal as raporiptal "
					+ " from "
					+ Ayarlar.SHEMA
					+ ".rapor q,"
					+ Ayarlar.SHEMA
					+ ".kontrol b,"
					+ Ayarlar.SHEMA
					+ ".randevu c,"
					+ Ayarlar.SHEMA
					+ ".basvuru d ,"
					+ Ayarlar.SHEMA
					+ ".bina a, "
					+ Ayarlar.SHEMA
					+ ".cihaz "
					+ "where "
					+ " q.kontrol_id=b.kontrol_id "
					+ " and "
					+ " c.basvuru_id=d.basvuru_id "
					+ " and "
					+ " c.randevu_id=b.randevu_id "
					+ " and "
					+ " a.bina_id=d.bina_id "
					+ " and "
					+ " a.bina_id=? and "
					+ " cihaz.cihaz_id=b.cihaz_id "
					+ " and q.rapor_tarihi>'2014-12-07' "), _GETRAPORLARONLOGIN_(
			"select rapor_id as raporid, a.kontrol_id as kontrolid "
					+ " from "
					+ Ayarlar.SHEMA
					+ ".rapor a join "
					+ Ayarlar.SHEMA
					+ ".kontrol b on (b.kontrol_id=a.kontrol_id and b.onay_durum='M' and onaylayan_kullanici_id=?) "
					+ "where a.rapor_tarihi>? limit 1"),
					 _GETTESLIMEDILMEYENRAPORLARONLOGIN_(
								"select rapor_id as raporid, a.kontrol_id as kontrolid "
										+ " from "
										+ Ayarlar.SHEMA
										+ ".rapor a join "
										+ Ayarlar.SHEMA
										+ ".kontrol b on (b.kontrol_id=a.kontrol_id and b.onay_durum='O' and not b.rapor_iptal and ( onaylayan_kullanici_id=? or kontrol_muhendisi_sicilno=?)) "
										+ "where current_date - a.rapor_tarihi between 0 and 30 and not exists (select 1 from "
										+Ayarlar.SHEMA+ ".rapor_teslim where rapor_id=a.rapor_id) limit 1"),
	/*
	 * 07.04.2015 tarihinde aşağıdaki sorgu ile değiştirildi * _GETRAPORLAR_(
	 * "select rapor_id as raporid, a.kontrol_id as kontrolid, dosya, dosya_adi as dosyaadi, "
	 * +
	 * "rapor_tarihi as raportarihi,b.onay_durum as onayDurum,b.onay_tarihi as onayTarihi ,"
	 * + "b.onaylayan_kullanici_id as onaylayanSicilNo," +
	 * "b.kontrol_bitis_tarihi as kontroltarihi,b.etiket," +
	 * "(select cevap from " + Ayarlar.SHEMA +
	 * ".cihaz_deger where cihaz_id=b.cihaz_id and deger_id in (151,171)) as asansorunyeri,"
	 * +
	 * "bina.tescilno as binatescilno,bina.bina_adi as binaadi,a.kontrol_kod_eski as eskiKontrolKodu,bina.basvuru_id as basvuruid "
	 * 
	 * + " from " + Ayarlar.SHEMA + ".rapor a join " + Ayarlar.SHEMA +
	 * ".kontrol b on (b.kontrol_id=a.kontrol_id) " + "join " +
	 * "(select bina.bina_adi,tescilno,randevu.randevu_id,basvuru.basvuru_id from "
	 * + Ayarlar.SHEMA + ".randevu randevu," + Ayarlar.SHEMA +
	 * ".basvuru basvuru," + Ayarlar.SHEMA + ".bina bina " +
	 * "where randevu.basvuru_id=basvuru.basvuru_id and bina.bina_id=basvuru.bina_id) as bina "
	 * + " on  ( bina.randevu_id=b.randevu_id) " +
	 * "where a.rapor_tarihi>? and current_date-rapor_tarihi<=?"),
	 */
	_GETRAPORLAR_(
			"select rapor_id as raporid, a.kontrol_id as kontrolid, dosya, dosya_adi as dosyaadi, "
					+ "rapor_tarihi as raportarihi,b.onay_durum as onayDurum,b.onay_tarihi as onayTarihi ,"
					+ "b.onaylayan_kullanici_id as onaylayanSicilNo,"
					+ "b.kontrol_bitis_tarihi as kontroltarihi,b.etiket,"
					+ "cd.cevap as asansorunyeri,b.cihaz_id as cihazid,"
					+ "bina.tescilno as binatescilno,bina.bina_adi as binaadi,a.kontrol_kod_eski as eskiKontrolKodu,rand.basvuru_id as basvuruid,a.rapor_iptal as raporiptal "
					+ " from "
					+ Ayarlar.SHEMA
					+ ".rapor a join "
					+ Ayarlar.SHEMA
					+ ".kontrol b on (b.kontrol_id=a.kontrol_id) "
					+ "join "
					+ Ayarlar.SHEMA
					+ ".cihaz c on (c.cihaz_id=b.cihaz_id) "
					+ "join "
					+ Ayarlar.SHEMA
					+ ".bina bina on (bina.bina_id=c.bina_id) "
					+ " join "
					+ Ayarlar.SHEMA
					+ ".randevu rand on (rand.randevu_id=b.randevu_id) "
					+ " left outer join "
					+ Ayarlar.SHEMA
					+ ".cihaz_deger cd on (cd.cihaz_id=b.cihaz_id and cd.deger_id in (151,171)) "

					+ "where a.rapor_tarihi>=?"),

	_GETYAZDIRILMAMISRAPORLARIST_(
			"select   "
					+ "kontrol.kontrol_id as kontrolid,kontrol.onay_durum as onaydurum,"
					+ "kontrol.kontrol_baslangic_tarihi as kontrolbaslangictarihi,"
					+ "kontrol.kontrol_baslangic_saati as kontrolbaslangicsaati,kontrol.kontrol_bitis_tarihi as kontrolbitistarihi,"
					+ "kontrol.onaylayan_kullanici_id as onaylayanSicilNo,kontrol.kontrol_bitis_saati as kontrolbitissaati,"
					+ "kontrol.kontrol_bitis_tarihi as kontroltarihi,kontrol.kontrol_bitis_saati as kontrolSaati,"
					+ "case kontrol.etiket when 'S' then 1 when 'M' then 2 when 'K' then 3 when 'Y' then 4 else 0 end as etiketint,"
					+ "kontrol.kontrol_turu as kontrolturu,bina.bina_id as binaid,"
					+ "kontrol.etiket," + "kontrol.aciklamalar,"
					+ "(select cevap from "
					+ Ayarlar.SHEMA
					+ ".cihaz_deger where cihaz_id=kontrol.cihaz_id and deger_id in (151,171)) as asansorunyeri,"
					+ "bina.bina_adi as binaadi,muhendis.adi||' '||muhendis.soyadi as muhendis,"
					+ " randevu.randevu_id as randevuId,basvuru.basvuru_id as basvuruId,randevu.randevu_tarihi as randevuTarihi,"
					+ "basvuru.basvuru_tarihi as basvurutarihi,cihaz.kimlik_no as asansorKimlikNo,cihaz.cihaz_tip_id as cihaztipi,"
					+ "cihaz.cihaz_id as cihazid,bina.bina_adi as binaadi,kontrol.revizyon_rapor as revizyonrapor,kontrol.rapor_iptal as raporiptal "
					+ " from "
					+ Ayarlar.SHEMA
					+ ".kontrol kontrol join "
					+ Ayarlar.SHEMA
					+ ".randevu randevu on (randevu.randevu_id=kontrol.randevu_id) join "
					+ Ayarlar.SHEMA
					+ ".randevu_muhendis randevumuhendis on (randevumuhendis.randevu_id=randevu.randevu_id and sorumlu='E') join "
					+ Ayarlar.SHEMA
					+ ".basvuru basvuru on (basvuru.basvuru_id =randevu.basvuru_id) join "
					+ Ayarlar.SHEMA
					+ ".bina bina on (bina.bina_id=basvuru.bina_id) left outer join "
					+ Ayarlar.SHEMA
					+ ".kullanici muhendis on (sicilno=randevumuhendis.muhendis_sicilno) left outer join "
					+ Ayarlar.SHEMA
					+ ".cihaz cihaz on (cihaz.cihaz_id=kontrol.cihaz_id) "
					+ "where "
					+ " kontrol.kontrol_bitis_tarihi>='2014-12-01' and kontrol.kontrol_bitis_tarihi>? "), _GETRAPORLARFORISTATISTIK_(
			"select  rapor.rapor_id as raporid, kontrol.kontrol_id as kontrolid, rapor.dosya, "
					+ "rapor.dosya_adi as dosyaadi,rapor.rapor_tarihi as raportarihi,kontrol.onay_durum as onayDurum,kontrol.onay_tarihi as onayTarihi ,"
					+ "kontrol.kontrol_bitis_tarihi as kontroltarihi,kontrol.etiket,"
					+ "(select cevap from "
					+ Ayarlar.SHEMA
					+ ".cihaz_deger where cihaz_id=kontrol.cihaz_id and deger_id in (151,171)) as asansorunyeri,"
					+ "bina.bina_adi as binaadi,muhendis.adi||' '||muhendis.soyadi as muhendis,onayci.adi||' '||onayci.soyadi as onaylayan ,rapor.rapor_iptal as raporiptal "
					+ "from "
					+ Ayarlar.SHEMA
					+ ".rapor rapor join "
					+ Ayarlar.SHEMA
					+ ".kontrol kontrol on (kontrol.kontrol_id=rapor.kontrol_id) join "
					+ Ayarlar.SHEMA
					+ ".randevu randevu on (randevu.randevu_id=kontrol.randevu_id) join "
					+ Ayarlar.SHEMA
					+ ".basvuru basvuru on (basvuru.basvuru_id =randevu.basvuru_id) join "
					+ Ayarlar.SHEMA
					+ ".bina bina on (bina.bina_id=basvuru.bina_id) left outer join "
					+ Ayarlar.SHEMA
					+ ".kullanici as muhendis on (muhendis.sicilno=kontrol.kontrol_muhendisi_sicilno) left outer join "
					+ Ayarlar.SHEMA
					+ ".kullanici as onayci on (onayci.sicilno=kontrol.onaylayan_kullanici_id) "
					+ " where "
					+ "rapor.rapor_tarihi>'2014-12-08'  and  kontrol.kontrol_bitis_tarihi>? "),
					
	/*
	 * 07.04.2015 tarihinde aşağıdaki sorgu ile değiştirildi.
	 * 
	 * _GETRAPORLAR__(
	 * "select rapor_id as raporid, a.kontrol_id as kontrolid, dosya, dosya_adi as dosyaadi, "
	 * +
	 * "rapor_tarihi as raportarihi,b.onay_durum as onayDurum,b.onay_tarihi as onayTarihi ,b.onaylayan_kullanici_id as onaylayanSicilNo,"
	 * + "b.kontrol_bitis_tarihi as kontroltarihi,b.etiket," +
	 * "(select cevap from " + Ayarlar.SHEMA +
	 * ".cihaz_deger where cihaz_id=b.cihaz_id and deger_id in (151,171)) as asansorunyeri,"
	 * +
	 * "bina.tescilno as binatescilno,bina.bina_adi as binaadi,a.kontrol_kod_eski as eskiKontrolKodu,bina.basvuru_id as basvuruid "
	 * + " from " + Ayarlar.SHEMA + ".rapor a join " + Ayarlar.SHEMA +
	 * ".kontrol b on (b.kontrol_id=a.kontrol_id) " + "join " +
	 * "(select bina.bina_adi,tescilno,randevu.randevu_id,basvuru.basvuru_id from "
	 * + Ayarlar.SHEMA + ".randevu randevu," + Ayarlar.SHEMA +
	 * ".basvuru basvuru," + Ayarlar.SHEMA + ".bina bina " +
	 * "where randevu.basvuru_id=basvuru.basvuru_id and bina.bina_id=basvuru.bina_id) as bina "
	 * + " on  ( bina.randevu_id=b.randevu_id) " +
	 * "where a.rapor_tarihi>? and  current_date-rapor_tarihi<=? and  (b.onaylayan_kullanici_id =? or "
	 * + Ayarlar.SHEMA + ".getRandevuMuhendis(?,b.randevu_id)>0)"),
	 */
	_GETRAPORLAR__(
			"select rapor_id as raporid, a.kontrol_id as kontrolid, dosya, dosya_adi as dosyaadi, "
					+ "rapor_tarihi as raportarihi,b.onay_durum as onayDurum,b.onay_tarihi as onayTarihi ,"
					+ "b.onaylayan_kullanici_id as onaylayanSicilNo,"
					+ "b.kontrol_bitis_tarihi as kontroltarihi,b.etiket,"
					+ "cd.cevap as asansorunyeri,b.cihaz_id as cihazid,"
					+ "bina.tescilno as binatescilno,bina.bina_adi as binaadi,a.kontrol_kod_eski as eskiKontrolKodu,rand.basvuru_id as basvuruid,a.rapor_iptal as raporiptal "
					+ " from "
					+ Ayarlar.SHEMA
					+ ".rapor a join "
					+ Ayarlar.SHEMA
					+ ".kontrol b on (b.kontrol_id=a.kontrol_id) "
					+ "join "
					+ Ayarlar.SHEMA
					+ ".cihaz c on (c.cihaz_id=b.cihaz_id) "
					+ "join "
					+ Ayarlar.SHEMA
					+ ".bina bina on (bina.bina_id=c.bina_id) "
					+ " join "
					+ Ayarlar.SHEMA
					+ ".randevu rand on (rand.randevu_id=b.randevu_id) "
					+ " left outer join "
					+ Ayarlar.SHEMA
					+ ".cihaz_deger cd on (cd.cihaz_id=b.cihaz_id and cd.deger_id in (151,171)) "

					+ "where a.rapor_tarihi>=?  and  (b.onaylayan_kullanici_id =? or "
					+ Ayarlar.SHEMA + ".getRandevuMuhendis(?,b.randevu_id)>0)"), _MUHENDISGETIRBYSICILNO_(
			"SELECT sicilno,adi,soyadi,onay_yetkisi as onayYetkisi,akreditasyon as akreditasyondeger,gsm_telefon_no as gsmtelefonno,telefon_no as telefonno"
					+ ",eposta,il  "
					+ "FROM "
					+ Ayarlar.SHEMA
					+ ".kullanici where sicilno=? and durum='A'"), _GETFIYAT_FOR_CIHAZ_(
			"select fiyat from "
					+ Ayarlar.SHEMA
					+ ".belediye_Sozlesme where belediye_kod=? and "
					+ "cihaz_tipi=? and current_date>=sozlesme_tarihi and current_date<=sozlesme_bitis_tarihi  "
					+ "and case kapasite_olcut when 'D' then kapasite>=? when 'K' then kapasite>=? end  and sozlesme_bina_tip_id=? and case kontrol_tipi when null then 'N' when '' then 'N'  else kontrol_tipi end =? "
					+ "  order by kapasite"), _GETCIHAZKAPASITE_(
			"select deger_id as degerid,cevap from " + Ayarlar.SHEMA
					+ ".cihaz_deger where cihaz_id=?"), _SETBASVURUODEME_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".odeme(basvuru_id, toplam_tutar, odeme_tutari,  odeme_tarihi,odemekontroldealinsin,userid) "
					+ "VALUES ( ?, ?, ?, ?,false,?)"),

	_DELETEBASVURUODEME_("DELETE FROM " + Ayarlar.SHEMA
			+ ".odeme where basvuru_id=?"), _GETBASVURUODEME_(
			"SELECT id, basvuru_id, toplam_tutar, odeme_tutari, odeme_tarihi from "
					+ Ayarlar.SHEMA + ".odeme where basvuru_id=?"), _BASVURUODEMEUPDATE_(
			"Update "
					+ Ayarlar.SHEMA
					+ ".odeme set odeme_tutari=?,odeme_tarihi=?,aciklama=?,odeme_sekli=?,odeme_belge_no=?,userid=? where basvuru_id=?"), _ODEMEUPDATE_(
			"Update "
					+ Ayarlar.SHEMA
					+ ".odeme set odeme_tutari=?,odeme_tarihi=?,odemekontroldealinsin=false,aciklama=?,odeme_sekli=?,odeme_belge_no=?,userid=? "
					+ " where id=?"), _BASVURUODEMESEKLIUPDATE_(
			"Update "
					+ Ayarlar.SHEMA
					+ ".odeme set odemeKontroldeAlinsin=true,userid=? where basvuru_id=?"), _BASVURUVERANDEVULAR(
			"select a.basvuru_id,basvuru_tarihi,basvuru_yapan_adi||' '||basvuru_yapan_soyadi as basvuruyapan,"
					+ "a.telefon_no,randevu_tarihi,randevu_saati,randevu_id,c.muhendis_sicilno,c.sorumlu,"
					+ "d.adi,d.soyadi "
					+ "from "
					+ Ayarlar.SHEMA
					+ ".basvuru a left outer join "
					+ Ayarlar.SHEMA
					+ ".randevu  b using (basvuru_id) left outer join "
					+ Ayarlar.SHEMA
					+ ".randevu_muhendis c using (randevu_id)  left outer join "
					+ Ayarlar.SHEMA
					+ ".kullanici d on (sicilno=c.muhendis_sicilno) "
					+ "where a.bina_id=? order by basvuru_id"), _GETSOZLESMEBINATIPLERI_(
			"select id,tip,aciklama from " + Ayarlar.SHEMA
					+ ".sozlesme_bina_tipleri"), _GETPDFRAPOR_(
			"select dosya,dosya_adi as dosyaAdi from " + Ayarlar.SHEMA
					+ ".rapor where rapor_id=?"), _GETKONTROLYAPILMAMANEDENKODLARI_(
			"select id,adi from " + Ayarlar.SHEMA
					+ ".bina_kontrol_yapilamama_nedenleri"), _KULLANICITURLERI_(
			"select id,tur,aciklama from " + Ayarlar.SHEMA + ".kullanici_turu "), _MUHENDISUPDATE_(
			"update "
					+ Ayarlar.SHEMA
					+ ".kullanici set sicilno=?,adi=?,soyadi=?,eposta=?,onay_yetkisi=?,"
					+ "akreditasyon=?,telefon_no=?,gsm_telefon_no=?,il=?,muhendis_gunluk_kontrol_adet=?,userid=? where kullanici_id=? "), _GETASANSORUNYERI_(
			"select cevap as asansorunyeri from " + Ayarlar.SHEMA
					+ ".cihaz_deger where deger_id=? and cihaz_id=?"), _BINA_ODEME_ALINMAMA_DURUMU_INSERT_(
			"insert into "
					+ Ayarlar.SHEMA
					+ ".odeme_alinmayacak_binalar "
					+ "(bina_id,islem_tarihi,aciklama,islem_yapan_kullanici,iptal_kodu,userid) "
					+ "values (?,current_date,?,?,'H',?)"), _BINA_ODEME_ALINMAMA_DURUMU_(
			"select id,bina_id as binaid,aciklama,islem_tarihi as islemtarihi,iptal_tarihi as iptaltarihi,"
					+ "iptal_kodu as iptalkodu,islem_yapan_kullanici as islemyapankullanici,iptal_eden_kullanici as iptaledenkullanici from "
					+ Ayarlar.SHEMA
					+ ".odeme_alinmayacak_binalar where bina_id=?"), _BINA_ODEME_ALINMAMA_DURUMU_IPTAL_(
			"update "
					+ Ayarlar.SHEMA
					+ ".odeme_alinmayacak_binalar set iptal_kodu='E',iptal_tarihi=current_date,iptal_eden_kullanici=?,userid=? where id=?"), _ODEME_ALINMADURUMU_(
			"select 1 from "
					+ Ayarlar.SHEMA
					+ ".odeme_alinmayacak_binalar where bina_id=? and iptal_kodu='H'"), _GETKONTROLADET_(
			"select muhendis_gunluk_kontrol_adet as muhendisgunlukkontroladet from "
					+ Ayarlar.SHEMA + ".kullanici where sicilno=?"), _RAPORTESLIMINSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".rapor_teslim(rapor_id, teslim_edilen_kisi, teslim_tarihi, telefon_no,tc_kimlik_no, aciklama,userid) "
					+ " VALUES (?, ?, ?, ?, ?, ?,?)"), _RAPORTESLIMDELETE_(
			"DELETE from " + Ayarlar.SHEMA + ".rapor_teslim where rapor_id=?"), _RAPORTESLIMGETIR_(
			"SELECT id, rapor_id as raporid, teslim_edilen_kisi as teslimedilenkisi, teslim_tarihi as teslimtarihi,"
					+ " telefon_no as telefonno,"
					+ "'('||substr(trim(cast(telefon_no as character(10))),1,3)||') '||"
					+ "substr(trim(cast(telefon_no as character(10))),4,3)||' '||substr(trim(cast(telefon_no as character(10))),7) as telefonnostr,"
					+ "tc_kimlik_no as tckimlikno, aciklama   FROM "
					+ Ayarlar.SHEMA + ".rapor_teslim where rapor_id=?"), _GETASANSORKAPSAMID_(
			"select id from "
					+ Ayarlar.SHEMA
					+ ".cihaz_deger d,"
					+ Ayarlar.SHEMA
					+ ".asansor_kapsam k "
					+ "where cihaz_id=? and d.deger_id=? and k.cihaz_tipi=? and d.cevap=k.kapsam_adi"), _GETKAPSAMTURLERI(
			"select id,kapsam_adi as kapsamadi,cihaz_tipi as cihaztipi,durum  from "
					+ Ayarlar.SHEMA + ".asansor_kapsam where durum='A'"), _TESTSORUSUGUNCELLE_(
			"UPDATE "
					+ Ayarlar.SHEMA
					+ ".soru "
					+ "SET  soru=?, parent=?, cihaz_tipi=?, sira_no=?, yildiz=?,  aktif=?,userid=? "
					+ "where soru_id=?"),

	_ONTANIMLISORUSUGUNCELLE_("UPDATE " + Ayarlar.SHEMA + ".soru_ontanimli"
			+ " set aciklama=?,userid=?,durum=? where soru_ontanimli_id=?"), _TESTSORUSUINSERT_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".soru("
					+ "soru, parent, cihaz_tipi, sira_no, yildiz, aktif,userid,tarih) "
					+ " VALUES (?, ?, ?, ?, ?, ?,?,?)"), _MAXSIRANO_(
			"select max(sira_no) as maxsira from " + Ayarlar.SHEMA
					+ ".soru where cihaz_tipi=? aND parent=? and (case when tarih is null then '0001-01-01' else tarih end )=?"), _MAXSIRANOPARENTNULL_(
			"select max(sira_no) as maxsira from " + Ayarlar.SHEMA
					+ ".soru where cihaz_tipi=? aND parent is null and (case when tarih is null then '0001-01-01' else tarih end )=?"), _ONTANIMLISORUEKLE_(
			"insert into "
					+ Ayarlar.SHEMA
					+ ".soru_ontanimli(soru_id,aciklama,userid, durum) values(?,?,?,'A')"), _GETTARAMAYAPILAMAYANBINALAR_(
			"select il_adi as iladi,ilce_adi as ilceadi, il as ilKodu,"
					+ " ilce as ilcekodu,mahalle,cadde_sokak as caddesokak,bina_id as binaid,bina_no as binano,"
					+ " bina_adi as binaadi,b.id as taramaid,b.tarih as taramatarih,bina_sorumlusu_tckimlikno as binasorumlusutckimlikno,"
					+ "bina_sorumlusu_adi||' '||bina_sorumlusu_soyadi as binasorumlusuadisoyadi ,bina_sorumlusu_telefon_no as binasorumlusutelefonno,"
					+ "d.adi as taramayapilamamanedeni from "
					+ Ayarlar.SHEMA
					+ ".bina a join "
					+ Ayarlar.SHEMA
					+ ".tarama b using (bina_id) join "
					+ Ayarlar.SHEMA
					+ ".ililcetb c on (il_kodu=a.il and ilce_kodu=a.ilce) join "
					+ Ayarlar.SHEMA
					+ ".bina_kontrol_yapilamama_nedenleri d on (d.id=cast(b.tarama_yapilamama_neden_kod as int)) "
					+ "where  b.tarama_yapilamama_neden_kod!='0'"), _BASVURU_ASANSOR_KONTROL_GUNCELLE_(
			"update "
					+ Ayarlar.SHEMA
					+ ".basvuru_asansor set kontrol_tarihi=?,userid=? where cihaz_id=? and "
					+ "basvuru_id in  " + "(select basvuru_id from "
					+ Ayarlar.SHEMA + ".randevu where randevu_id=?)"), _FIRMAILETISIMKAYDET_(
			"insert into "
					+ Ayarlar.SHEMA
					+ ".bakimci_firma_iletisim (bakimci_firma_kod, adi, soyadi, telefon_no, gsm_telefon_no,"
					+ "e_posta,userid) " + " VALUES (?, ?, ?, ?, ?,?,?)"),

	_FIRMAILETISIMSIL_("delete from " + Ayarlar.SHEMA
			+ ".bakimci_firma_iletisim where id=?"),

	_FIRMAILETISIMGETIR_(
			"SELECT id, bakimci_firma_kod as bakimciFirmaKod,"
					+ " adi, soyadi, telefon_no as telefonno, gsm_telefon_no as telefonnogsm,e_posta as eposta FROM "
					+ Ayarlar.SHEMA
					+ ".bakimci_firma_iletisim where bakimci_firma_kod=?"), _CURRVALFIRMA_(
			"SELECT currval(pg_get_serial_sequence('" + Ayarlar.SHEMA
					+ ".bakimci_firma', 'kod'))"), _GETBASVURULARBYBINAID_(
			"select basvuru_id as basvuruId,basvuru_tarihi as basvuruTarihi from "
					+ Ayarlar.SHEMA
					+ ".basvuru where bina_id=? order by basvuru_tarihi"),

	_GETODEMEBYBASVURUID_(
			"SELECT id, basvuru_id as basvuruid, toplam_tutar as toplamtutar,"
					+ " odeme_tutari as odemetutari, odeme_tarihi as odemetarihi,odeme_belge_no as odemeBelgeNo,"
					+ "odemekontroldealinsin,aciklama,odeme_sekli as odemesekli from " + Ayarlar.SHEMA
					+ ".odeme where basvuru_id=? "), _BINA_ARA_BY_BASVURU_ID_(
			"SELECT bina_adi as binaadi  FROM " + Ayarlar.SHEMA + ".bina a,"
					+ Ayarlar.SHEMA + ".basvuru b  where "
					+ "a.bina_id=b.bina_id and b.basvuru_id=? "), _GETODEMEBYBINAID_(
			"select o.id, o.basvuru_id as basvuruid,k.kontrol_bitis_tarihi as kontrolTarihi,"
					+ "toplam_tutar as toplamtutar,odeme_tutari as odemetutari,"
					+ "odeme_tarihi as odemetarihi,odemekontroldealinsin,odeme_sekli as odemesekli,odeme_belge_no as odemebelgeno,"
					+ "aciklama from "
					+ Ayarlar.SHEMA+".bina a "
					+ " join "
					+ Ayarlar.SHEMA+".basvuru b on(a.bina_id=b.bina_id) " 
					+" join "
					+ Ayarlar.SHEMA+".randevu r on(r.basvuru_id=b.basvuru_id) "
					+ " join "
					+ Ayarlar.SHEMA+".kontrol k on(k.randevu_id=r.randevu_id) "
					+" join "
					+ Ayarlar.SHEMA+".odeme o on(o.basvuru_id=b.basvuru_id) "
					+ "where a.bina_id=? and  a.bina_id=b.bina_id and b.basvuru_id=o.basvuru_id"),

	_GETESKIRAPORLAR_(
			"SELECT cihaz.cihaz_id as cihazId, basvuru.basvuru_id as basvuruId,rapor.kontrol_kod_eski as eskiKontrolKodu,"
					+ "randevu.randevu_id as randevuid,kontrol.kontrol_id as kontrolid,rapor.rapor_id as raporid,"
					+ " rapor.dosya, rapor.dosya_adi as dosyaadi,kontrol.etiket,"
					+ "rapor.rapor_tarihi as raportarih,kontrol.revizyon_rapor as revizyonRapor,  "
					+ " case when kontrol.kontrol_turu='N' then 'Normal Kontrol'"
					+ " when kontrol.kontrol_turu='E' then 'Eksiklik Kontrolü' end kontrolTuru,rapor.rapor_iptal as raporiptal "
					+ " FROM "
					+ Ayarlar.SHEMA
					+ ".rapor rapor,"
					+ Ayarlar.SHEMA
					+ ".kontrol kontrol,"
					+ Ayarlar.SHEMA
					+ ".randevu randevu,"
					+ Ayarlar.SHEMA
					+ ".basvuru basvuru,"
					+ Ayarlar.SHEMA
					+ ".cihaz cihaz "
					+ "where rapor.kontrol_id=kontrol.kontrol_id "
					+ "and randevu.randevu_id=kontrol.randevu_id "
					+ "and basvuru.basvuru_id=randevu.basvuru_id "
					+ "and cihaz.cihaz_id=kontrol.cihaz_id "
					+ "and rapor.rapor_tarihi<='2014-12-07' and "
					+ "position(rtrim(ltrim(cast(cihaz.cihaz_id as varchar(20)))) in rapor.dosya_adi)>0 "

					+ "and basvuru.bina_id=? and cihaz.cihaz_id=? "
					+ "union all "
					+ "SELECT cihaz.cihaz_id as cihazId, basvuru.basvuru_id as basvuruId,rapor.kontrol_kod_eski as eskiKontrolKodu,"
					+ "randevu.randevu_id as randevuid,kontrol.kontrol_id as kontrolid,rapor.rapor_id as raporid,"
					+ " rapor.dosya, rapor.dosya_adi as dosyaadi,kontrol.etiket,"
					+ "rapor.rapor_tarihi as raportarih,kontrol.revizyon_rapor as revizyonRapor,  "
					+ " case when kontrol.kontrol_turu='N' then 'Normal Kontrol'"
					+ " when kontrol.kontrol_turu='E' then 'Eksiklik Kontrolü' end kontrolTuru,rapor.rapor_iptal as raporiptal "
					+ " FROM "
					+ Ayarlar.SHEMA
					+ ".rapor rapor,"
					+ Ayarlar.SHEMA
					+ ".kontrol kontrol,"
					+ Ayarlar.SHEMA
					+ ".randevu randevu,"
					+ Ayarlar.SHEMA
					+ ".basvuru basvuru,"
					+ Ayarlar.SHEMA
					+ ".cihaz cihaz "
					+ "where rapor.kontrol_id=kontrol.kontrol_id "
					+ "and randevu.randevu_id=kontrol.randevu_id "
					+ "and basvuru.basvuru_id=randevu.basvuru_id "
					+ "and cihaz.cihaz_id=kontrol.cihaz_id "
					+ "and rapor.rapor_tarihi>'2014-12-07' "

					+ "and basvuru.bina_id=? and cihaz.cihaz_id=?"), 
					
					_GETESKIRAPORBYKONTROLID_(
							"SELECT cihaz.cihaz_id as cihazId, basvuru.basvuru_id as basvuruId,rapor.kontrol_kod_eski as eskiKontrolKodu,"
									+ "randevu.randevu_id as randevuid,kontrol.kontrol_id as kontrolid,rapor.rapor_id as raporid,"
									+ " rapor.dosya, rapor.dosya_adi as dosyaadi,kontrol.etiket,"
									+ "rapor.rapor_tarihi as raportarih,kontrol.revizyon_rapor as revizyonRapor,  "
									+ " case when kontrol.kontrol_turu='N' then 'Normal Kontrol'"
									+ " when kontrol.kontrol_turu='E' then 'Eksiklik Kontrolü' end kontrolTuru"
									+ " FROM "
									+ Ayarlar.SHEMA
									+ ".rapor rapor,"
									+ Ayarlar.SHEMA
									+ ".kontrol kontrol,"
									+ Ayarlar.SHEMA
									+ ".randevu randevu,"
									+ Ayarlar.SHEMA
									+ ".basvuru basvuru,"
									+ Ayarlar.SHEMA
									+ ".cihaz cihaz "
									+ "where rapor.kontrol_id=kontrol.kontrol_id "
									+ "and randevu.randevu_id=kontrol.randevu_id "
									+ "and basvuru.basvuru_id=randevu.basvuru_id "
									+ "and cihaz.cihaz_id=kontrol.cihaz_id "
									+ "and rapor.rapor_tarihi<='2014-12-07' and "
									+ "position(rtrim(ltrim(cast(cihaz.cihaz_id as varchar(20)))) in rapor.dosya_adi)>0 "

									+ "and rapor.kontrol_id=? "
									+ "union all "
									+ "SELECT cihaz.cihaz_id as cihazId, basvuru.basvuru_id as basvuruId,rapor.kontrol_kod_eski as eskiKontrolKodu,"
									+ "randevu.randevu_id as randevuid,kontrol.kontrol_id as kontrolid,rapor.rapor_id as raporid,"
									+ " rapor.dosya, rapor.dosya_adi as dosyaadi,kontrol.etiket,"
									+ "rapor.rapor_tarihi as raportarih,kontrol.revizyon_rapor as revizyonRapor,  "
									+ " case when kontrol.kontrol_turu='N' then 'Normal Kontrol'"
									+ " when kontrol.kontrol_turu='E' then 'Eksiklik Kontrolü' end kontrolTuru"
									+ " FROM "
									+ Ayarlar.SHEMA
									+ ".rapor rapor,"
									+ Ayarlar.SHEMA
									+ ".kontrol kontrol,"
									+ Ayarlar.SHEMA
									+ ".randevu randevu,"
									+ Ayarlar.SHEMA
									+ ".basvuru basvuru,"
									+ Ayarlar.SHEMA
									+ ".cihaz cihaz "
									+ "where rapor.kontrol_id=kontrol.kontrol_id "
									+ "and randevu.randevu_id=kontrol.randevu_id "
									+ "and basvuru.basvuru_id=randevu.basvuru_id "
									+ "and cihaz.cihaz_id=kontrol.cihaz_id "
									+ "and rapor.rapor_tarihi>'2014-12-07' "

									+ "and rapor.kontrol_id=?"),
					_KONTROLDELETE_(
			"delete from " + Ayarlar.SHEMA + ".kontrol where kontrol_id=?"), _TESTDELETE_(
			"delete from " + Ayarlar.SHEMA + ".kontrol_test where kontrol_id=?"), _GETODEMESEKILLERI_(
			"select id,odeme_sekli as odemesekli from " + Ayarlar.SHEMA
					+ ".odeme_sekilleri order by odeme_sekli"), _GETODEMEYAPILANBINALAR_(
			"select id,"
					+ "o.basvuru_id as basvuruid,toplam_tutar as toplamtutar,odeme_tutari as odemetutari,"
					+ "odeme_tarihi as odemetarihi,odemekontroldealinsin,aciklama,odeme_sekli as odemesekli,"
					+ "odeme_belge_no as odemebelgeno,bina_adi as binaadi from "
					+ Ayarlar.SHEMA
					+ ".odeme o,"
					+ Ayarlar.SHEMA
					+ ".basvuru b,"
					+ Ayarlar.SHEMA
					+ ".bina a "
					+ " where o.odeme_sekli>0 and "
					+ " o.basvuru_id=b.basvuru_id and"
					+ "  b.bina_id=a.bina_id and odeme_tarihi between ? and current_date "), _GETODEMELISTESI_(
			"select c.id,q.basvuru_id as basvuruid,q.basvuru_tarihi as basvurutarihi,basvuru_yapan_adi||chr(32)||basvuru_yapan_soyadi as adisoyadi,"
					+ " basvuru_yapan_tckimlikno as tckimlikno,q.telefon_no as telefonNo,mahalle,cadde_sokak as caddesokak,bina_no as binano,q.bina_id as binaid,"
					+ "il_adi as il,ilce_adi as ilce, il as ilKodu,ilce as ilcekodu,q.eposta,"
					+ " c.toplam_tutar as kontroltutari,c.odemekontroldealinsin,"
					+ "a.bina_adi as binaAdi "
					+ " from "
					+ Ayarlar.SHEMA
					+ ".odeme c join "
					+ Ayarlar.SHEMA
					+ ".basvuru q using (basvuru_id) join "
					+ Ayarlar.SHEMA
					+ ".bina a using (bina_id) join "
					+ Ayarlar.SHEMA
					+ ".ililcetb on (il_kodu=il and ilce_kodu=ilce)  "
					+ " where (c.odeme_sekli is null or c.odeme_sekli<=0)"), _GETKONTROLEDILECEKBINALAR_(
			"select bina.*,kontrol.kontrol_bitis_tarihi,"
					+ "kontrol.etiket from "
					+ Ayarlar.SHEMA
					+ ".bina_kontrol kontrol "
					+ "join "
					+ Ayarlar.SHEMA
					+ ".cihaz cihaz on (cihaz.cihaz_id=kontrol.cihaz_id) "
					+ "join "
					+ Ayarlar.SHEMA
					+ ".randevu randevu on (randevu.randevu_id=kontrol.randevu_id) "
					+ "join "
					+ Ayarlar.SHEMA
					+ ".basvuru basvuru on (basvuru.basvuru_id=randevu.basvuru_id) "
					+ "join " + Ayarlar.SHEMA
					+ ".bina bina on (bina.bina_id=basvuru.bina_id) "
					+ "where kontrol.kontrol_bitis_tarihi<?"), _SUBETEMSILCILIKHESAPNUMARALARI_(
			"SELECT id, sube_kodu as subekodu,"
					+ " temsilcilik_kodu as temsilcilikkodu, hesap_no as hesapno, iban,banka_adi bankaAdi   FROM "
					+ Ayarlar.SHEMA + ".sube_temsilcilik_hesap"), _ROLEKLE_(
			"insert into " + Ayarlar.SHEMA + ".rol (rol_adi) values(?)"), _ROLSIL_(
			"delete from " + Ayarlar.SHEMA + ".rol where rol_id=?"), _GETROLYETKILER_(
			"select a.id,rol_id as rolid,yetki_id as yetkiid,b.yetki_adi as yetkiadi from "
					+ Ayarlar.SHEMA + ".rol_yetki a join " + Ayarlar.SHEMA
					+ ".yetki b on (b.id=a.yetki_id) " + "where a.rol_id=?"), _ROLYETKISIL_(
			"delete from " + Ayarlar.SHEMA
					+ ".rol_yetki where rol_id=? and yetki_id=?"), _ROLYETKISILBYROLID_(
			"delete from " + Ayarlar.SHEMA + ".rol_yetki where rol_id=?"), _ROLYETKIEKLE_(
			"insert into " + Ayarlar.SHEMA
					+ ".rol_yetki (rol_id,yetki_id) values (?,?)"), _KULLROLVEYETKILER_(
			"SELECT id, kullanici_id as kullaniciid,"
					+ " rol_id as rolid, yetki_id as yetkiid, guncelle, sil "
					+ "FROM " + Ayarlar.SHEMA
					+ ".kullanici_rol_yetki where kullanici_id=?"), _SAYFAYETKILERI_(
			"select id,yetki_id as yetkiid,sayfa_adi as sayfaadi,sayfa_id as sayfaid from "
					+ Ayarlar.SHEMA + ".yetki_sayfa_iliski"), _CIHAZBAKIMCIFIRMALAR_(
			"select "
					+ "b.firma_id as kod, (case b.firma_id when 999999 then 'Bina Yönetiminin yetki belgeli asansör bakım firmasıyla sözleşmesi bulunmamaktadır !' "
					+ "else a.unvan end) as unvan, "
					+ "a.adsoyad, a.gsm_telefon, il, ilce, a.adres, a.durumu, a.tescil_no,"
					+ "uygunluk_belgesi, gecerlilik_suresi, servis_sozlesme, b.sozlesme_bitis_tarih as sozlesmetarihi,"
					+ "tse_belge_no, eposta,monte_eden as monteEden,"
					+ "yetkili_servis as yetkiliServis,"
					+ "tse_belgesi as tseBelgesi," + "telefon_no as telefonNo,"
					+ "telefon_no_dahili as dahili,"
					+ "ce_belge_tipi as ceBelgeTipi,"
					+ "b.sozlesme_tarih as asansorbakimtarihi " + " FROM "
					+ Ayarlar.SHEMA
					+ ".cihaz_anlasmali_firma b left outer join "
					+ Ayarlar.SHEMA + ".bakimci_firma a on a.kod=b.firma_id "
					+ "where b.cihaz_id=? order by b.sozlesme_tarih desc "), _CIHAZKONTROLFIRMASI_(
			"select "
					+ "b.bakimci_firma_id as kod, (case b.bakimci_firma_id when 999999 then 'Bina Yönetiminin yetki belgeli asansör bakım firmasıyla sözleşmesi bulunmamaktadır !' "
					+ "else a.unvan end) as unvan, "
					+ "a.adsoyad, a.gsm_telefon, il, ilce ,ii.il_adi as ilAdi,ii.ilce_adi as ilceAdi, a.adres, a.durumu, a.tescil_no,"
					+ "uygunluk_belgesi, gecerlilik_suresi, servis_sozlesme, sozlesme_tarihi,"
					+ "tse_belge_no, eposta,monte_eden as monteEden,"
					+ "yetkili_servis as yetkiliServis,"
					+ "tse_belgesi as tseBelgesi,"
					+ "telefon_no as telefonNo,"
					+ "telefon_no_dahili as dahili,"
					+ "ce_belge_tipi as ceBelgeTipi,"
					+ "b.kontrol_tarihi as asansorbakimtarihi,"
					+ "'('||substr(trim(cast(a.faks as character(10))),1,3)||') '||"
					+ "substr(trim(cast(a.faks as character(10))),4,3)||' '||substr(trim(cast(a.faks as character(10))),7) as faksstr "
					+ " FROM "
					+ Ayarlar.SHEMA
					+ ".randevu r join "
					+ Ayarlar.SHEMA
					+ ".basvuru_asansor b on (b.basvuru_id=r.basvuru_id and b.cihaz_id=? and b.bakimci_firma_id>0) left outer join "
					+ Ayarlar.SHEMA
					+ ".bakimci_firma a on a.kod=b.bakimci_firma_id left outer join "
					+ Ayarlar.SHEMA
					+ ".ililcetb ii on (il_kodu=il and ilce_kodu=ilce) "
					+ "where r.randevu_id=?"),

	_KONTROLHABER_(
			"select a.bina_id,i.il_adi as ilstr,"
					+ "i.ilce_adi as ilcestr,"
					+ " a.mahalle,"
					+ " a.cadde_sokak as caddeSokak,"
					+ " a.bina_adi as binaAdi, kontrol.kontrol_bitis_tarihi as sonKontrolTarihi, cihaz.cihaz_id as cihazId ,cihaz.kimlik_no as cihazKimlikNo, kontrol.etiket as etiket, "
					+ "concat(binakisi.adi,' ',binakisi.soyadi) as binaKisi, "
					+ "binakisi.telefon_no as telefonNoStr, "
					+ "binakisi.telefon_no_gsm as telefonNoGsmStr " + "from "
					+ Ayarlar.SHEMA
					+ ".ililcetb i, "
					+ Ayarlar.SHEMA
					+ ".bina_kontrol kontrol "
					+ "join "
					+ Ayarlar.SHEMA
					+ ".cihaz cihaz on (cihaz.cihaz_id=kontrol.cihaz_id) "
					+ "join "
					+ Ayarlar.SHEMA
					+ ".randevu randevu on (randevu.randevu_id=kontrol.randevu_id) "
					+ "join "
					+ Ayarlar.SHEMA
					+ ".basvuru basvuru on (basvuru.basvuru_id=randevu.basvuru_id) "
					+ "join "
					+ Ayarlar.SHEMA
					+ ".bina a on (a.bina_id=basvuru.bina_id) "
					+ "left outer join "
					+ Ayarlar.SHEMA
					+ ".bina_kisi binakisi on (binakisi.bina_id=a.bina_id and sorumluluk_turu=1 and "
					+ " kayit_tarihi = (select max(kayit_tarihi) "
					+ " from "
					+ Ayarlar.SHEMA
					+ ".bina_kisi where bina_id=binakisi.bina_id  and sorumluluk_turu=1) ) "

					+ " where kontrol.kontrol_bitis_tarihi<?"
					+ " and i.il_kodu=a.il" + " and i.ilce_kodu=a.ilce"), _BASVURUKONTROLFIRMASI_(
			"select "
					+ "b.bakimci_firma_id as bakimciFirmaId, (case b.bakimci_firma_id when 999999 then 'Bina Yönetiminin yetki belgeli asansör bakım firmasıyla sözleşmesi bulunmamaktadır !' "
					+ "else a.unvan end) as bakimciFirmaAdi, c.kimlik_no as kimlikno,c.cihaz_id as cihazid,b.kontrol_tarihi as kontroltarihi,"
					+ "b.kontrol_tutari as kontroltutari,b.kontrol_iptal as kontroliptal "
					+ " FROM "
					+ Ayarlar.SHEMA
					+ ".basvuru_asansor b  left outer join "
					+ Ayarlar.SHEMA
					+ ".bakimci_firma a on a.kod=b.bakimci_firma_id left outer join "
					+ Ayarlar.SHEMA + ".cihaz c on (c.cihaz_id=b.cihaz_id) "
					+ "where b.basvuru_id=?"), _ONTANIMLITESTDELETE_(
			"delete from " + Ayarlar.SHEMA
					+ ".kontrol_ontanimli_test where kontrol_id=?"), _ONTANIMLITESTBYKONTROLID_(
			"select "
					+ "id ,kontrol_id as kontrolid,parent_id as parentid,soru_id as soruid,ontanimli_id as ontanimliid "
					+ " FROM " + Ayarlar.SHEMA
					+ ".kontrol_ontanimli_test where kontrol_id=?"), _ONTANIMLITESTBYKONTROLIDPARENTID_(
			"select "
					+ "id ,kontrol_id as kontrolid,parent_id as parentid,soru_id as soruid,ontanimli_id as ontanimliid "
					+ " FROM "
					+ Ayarlar.SHEMA
					+ ".kontrol_ontanimli_test where kontrol_id=? and parent_id=?"), _SETBINACOORDINAT_(
			"update " + Ayarlar.SHEMA
					+ ".bina set enlem=?,boylam=?,userid=? where bina_id=?"), _LOGINLOGKAYDET_(
			"insert into "
					+ Ayarlar.SHEMA
					+ ".kullanici_login (kullanici_adi,ip,giristarih) values(?,?,?)"), _LOGINLOGCIKISTARIHIUDATE_(
			"update "
					+ Ayarlar.SHEMA
					+ ".kullanici_login set cikistarih=? where kullanici_adi=? and ip=? and cikistarih is null"), _UPDATETESTSORUSUSETKAPSAMNULL_(
			"UPDATE " + Ayarlar.SHEMA + ".soru set kapsam=null where soru_id=?"), _RANDEVU_MUHENDIS_DELETE_(
			"delete from " + Ayarlar.SHEMA
					+ ".randevu_muhendis where randevu_id=?"), _GETRANDEVUBYBASVURUID_(
			"select randevu_id as randevuid from " + Ayarlar.SHEMA
					+ ".randevu where basvuru_id=?"), _CIHAZDURUMUPDATE_(
			"update "
					+ Ayarlar.SHEMA
					+ ".cihaz set durum=?,userid=?,uavt_kod=?,uavt_sira_no=?,uavt_etiket=? where cihaz_id=?"), _CIHAZDURUMUPDATEKONTROLDE_(
			"update " + Ayarlar.SHEMA
					+ ".cihaz set durum=?,userid=? where cihaz_id=?"), _MUHENDISCIHAZYETKIKAYDET_(
			"insert into "
					+ Ayarlar.SHEMA
					+ ".muhendis_cihaz_yetki (muhendis_sicilno,cihaz_tipi,userid) values (?,?,?)"), _MUHENDISCIHAZYETKISIL_(
			"delete from " + Ayarlar.SHEMA
					+ ".muhendis_cihaz_yetki where muhendis_sicilno=?"), _GETMUHENDISCIHAZYETKI(
			"select id,muhendis_sicilno as muhendissicilno,"
					+ "cihaz_tipi as cihaztipi from " + Ayarlar.SHEMA
					+ ".muhendis_cihaz_yetki where muhendis_Sicilno=?"), _SONCALISILANBINALAR_(
			"select randevu.randevu_id as randevuId,basvuru.basvuru_id as basvuruid, bina_adi as binaAdi,bina.bina_Id as binaId,"
					+ "mahalle,"
					+ "cadde_sokak caddesokak,"
					+ "bina_no as binano,"
					+ "(Select adi from "
					+ Ayarlar.SHEMA
					+ ".belediye where kod=bina.belediye) as belediyeAdi,"
					+ "(select distinct il_adi from "
					+ Ayarlar.SHEMA
					+ ".ililcetb "
					+ " where il_kodu=bina.il) as il,"
					+ "(select distinct ilce_adi from "
					+ Ayarlar.SHEMA
					+ ".ililcetb "
					+ " where il_kodu=bina.il and ilce_kodu=bina.ilce) as ilce "
					+ " from "
					+ Ayarlar.SHEMA
					+ ".basvuru basvuru join  "
					+ Ayarlar.SHEMA
					+ ".bina bina on (bina.bina_id=basvuru.bina_id) left outer join "
					+ Ayarlar.SHEMA
					+ ".randevu randevu on (randevu.basvuru_id=basvuru.basvuru_id) "
					+ " where basvuru.basvuru_tarihi>? "), _BASVURULANASANSOR_(
			"select 1 as asansorAdet,(case kontrol_turu when 'E' then 1 else 0 end) as eksiklikKontrolu,"
					+ "(case kontrol_turu when 'N' then 1 else 0 end) as normalkontrol  from "
					+ Ayarlar.SHEMA + ".basvuru_asansor where basvuru_id=?"), _RANDEVUADET_(
			"select 1   from " + Ayarlar.SHEMA + ".randevu where basvuru_id=?"),

	_GETBASVURULARCIHAZLAR_(
			"select  basvuru.basvuru_id as basvuruid,basvuru.basvuru_tarihi as basvurutarihi,"
					+ "basvuruasansor.cihaz_id as cihazid,basvuruasansor.kontrol_turu as kontrolturu,"
					+ "basvuruasansor.kontrol_tutari as kontroltutari,"
					+ "cihaz.kimlik_no as asansorKimlikNo,cihazturu.cihaz_tip_aciklama as cihaztipaciklama,"
					+ "(select cevap from "
					+ Ayarlar.SHEMA
					+ ".cihaz_deger where cihaz_id=cihaz.cihaz_id and deger_id in (151,171)) as asansorunyeri "
					+ " from "
					+ Ayarlar.SHEMA
					+ ".basvuru basvuru join "
					+ Ayarlar.SHEMA
					+ ".basvuru_asansor basvuruasansor on (basvuruasansor.basvuru_id=basvuru.basvuru_id) join "
					+ Ayarlar.SHEMA
					+ ".cihaz cihaz on (cihaz.cihaz_id=basvuruasansor.cihaz_id) join "
					+ Ayarlar.SHEMA
					+ ".cihaz_turu cihazturu on (cihazturu.cihaz_tip_id=cihaz.cihaz_tip_id) "
					+ "where  "
					+ "basvuru.bina_id=? and basvuru_tarihi>=? order by basvuru_tarihi"),

	_GETRANDEVUKONTROL_(
			"select "

					+ " randevu.randevu_id as randevuid,"
					+ " randevu.randevu_tarihi as randevutarihi,"
					+ "rapor.rapor_id as raporid, kontrol.kontrol_id as kontrolid, rapor.dosya,"
					+ "rapor.dosya_adi as dosyaadi,rapor.rapor_tarihi as raportarihi,"
					+ "kontrol.onay_durum as onayDurum,kontrol.onay_tarihi as onayTarihi ,"
					+ " kontrol.kontrol_bitis_tarihi as kontroltarihi,kontrol.etiket,"
					+ "muhendis.adi||' '||muhendis.soyadi as muhendis,onayci.adi||' '||onayci.soyadi as onaylayan,odeme.odeme_tutari as odemetutari "
					+ " from "
					+ Ayarlar.SHEMA
					+ ".randevu randevu left outer join "
					+ Ayarlar.SHEMA
					+ ".kontrol kontrol on (kontrol.randevu_id=randevu.randevu_id and cihaz_id=?) left outer join "
					+ Ayarlar.SHEMA
					+ ".rapor rapor on (rapor.kontrol_id=kontrol.kontrol_id) left outer join "
					+ Ayarlar.SHEMA
					+ ".kullanici as muhendis on (muhendis.sicilno=kontrol.kontrol_muhendisi_sicilno) left outer join "
					+ Ayarlar.SHEMA
					+ ".kullanici as onayci on (onayci.sicilno=kontrol.onaylayan_kullanici_id) left outer join "
					+ Ayarlar.SHEMA
					+ ".odeme odeme on (odeme.basvuru_id=randevu.basvuru_id) "
					+ "where randevu.basvuru_id=?"), _FIRMACIHAZKAYDET_(
			"insert into "
					+ Ayarlar.SHEMA
					+ ".cihaz_anlasmali_firma (cihaz_id,firma_id,sozlesme_tarih,sozlesme_bitis_tarih,userid) values (?,?,?,?,?)"), _GETCIHAZANLASMALIFIRMA_(
			"select id,cihaz_id as cihazid,firma_id as firmaid,sozlesme_tarih as sozlesmeTarih,sozlesme_bitis_tarih as sozlesmebitistarih,"
			+ "sozlesme_bitis_tarih-sozlesme_tarih as sozlesmesure from "
					+ Ayarlar.SHEMA
					+ ".cihaz_anlasmali_firma where cihaz_id=? and firma_id=? and  sozlesme_tarih<=current_date and  sozlesme_bitis_tarih>=current_date order by sozlesme_tarih desc limit 1"), _RAPORGETIRBYID_(
			"select rapor_id as raporid,dosya_adi as dosyaadi,dosya from "
					+ Ayarlar.SHEMA + ".rapor where rapor_id=?"), _DELETEBASVURU_(
			"delete from " + Ayarlar.SHEMA + ".basvuru where basvuru_id=?"),

	_DELETETARAMA_("delete from " + Ayarlar.SHEMA
			+ ".tarama where id=? and bina_id=?"), _GETRAPORBYKONTROLID_(
			"select * from " + Ayarlar.SHEMA + ".rapor where kontrol_id=?"), _DELETEKONTROL_(
			"delete from " + Ayarlar.SHEMA + ".kontrol where kontrol_id=?"), _DELETEKONTROLTEST_(
			"delete from " + Ayarlar.SHEMA + ".kontrol_test where kontrol_id=?"), _DELETEKONTROLONTANIMLI_(
			"delete from " + Ayarlar.SHEMA
					+ ".kontrol_ontanimli_test where kontrol_id=?"), _UPDATEBASVURUASANSOR_(
			"update " + Ayarlar.SHEMA + ".basvuru_asansor "
					+ "set kontrol_tarihi=null where basvuru_id=? and cihaz_id=?"),

	_BINA_ARA_TESCILNO_(
			"SELECT bina_id, il, ilce,"
					+ "(select distinct il_Adi from "
					+ Ayarlar.SHEMA
					+ ".ililcetb where il_kodu=a.il)"
					+ "  as ilAdi,"
					+ "(select distinct ilce_Adi from "
					+ Ayarlar.SHEMA
					+ ".ililcetb where il_kodu=a.il and ilce_kodu=a.ilce)"
					+ " as ilceAdi,"
					+ " mahalle, cadde_sokak, bina_no,"
					+ " bina_adi, belediye,ada, parsel, pafta,vergi_no as vergiNo,"
					+ "vergi_dairesi as vergiDairesi,yapitip,"
					+ "sozlesme_bina_tip_id as sozlesmebinatipid,a.tescilno,enlem,boylam,acik_adres as acikadres,a.uavt_kod as uavtkod,koy_kodu as bucakkoykod   FROM "
					+ Ayarlar.SHEMA + ".bina a  where " + "a.tescilno=? "), _BASVURUASANSORBYCIHAZID_(
			"select basvuru_id as basvuruid from " + Ayarlar.SHEMA
					+ ".basvuru_asansor where cihaz_id=? limit 1"), _DELETECIHAZDEGER_(
			"Delete from " + Ayarlar.SHEMA + ".cihaz_deger where cihaz_id=?"), _DELETECIHAZ_(
			"Delete from " + Ayarlar.SHEMA + ".cihaz where cihaz_id=?"),

	_GETBASVURUID_("select basvuru_id as basvuruId from " + Ayarlar.SHEMA
			+ ".randevu where randevu_id=?"), _INSERTMAHALLE_(
			"insert into "
					+ Ayarlar.SHEMAUAVT
					+ ".mahalle (kod, ad, tanitimkodu, tip, yetkiliidarekodu, koykodu) VALUES (?, ?, ?, ?, ?, ?)"),

	_INSERTCSBM_(
			"insert into "
					+ Ayarlar.SHEMAUAVT
					+ ".csbm (kod, ad, tanitimkodu, tip, mahallekodu) VALUES (?, ?, ?, ?, ?)"), _GETREFERANSDENETIMKAYITKONTROLSORULAR_(
			"SELECT id,soru_id as soruid, kritikseviyeid, soruaciklama, gecerliliktarihi, aktif, akm_soru_id as akmsoruid,"
					+ " eslestirme, cihaz_tipi as cihazTipi,parent "
					+ "FROM "
					+ Ayarlar.SHEMAUAVT
					+ ".referansdenetimkayitkontrolsorularlistesi order by cihaz_tipi,soru_id"), _GETDENETCIKONTROL_(
			"select kont.kontrol_muhendisi_sicilno as muhendissicilno,"
					+ "(select  adi||' '||soyadi from akm.kullanici where sicilno=kont.kontrol_muhendisi_sicilno) as muhendisadisoyadi,"
					+ "sum((case c.cihaz_tip_id "
					+ " when 66 then 1 "
					+ " when null then 1 "
					+ " when 0 then 1 "
					+ " else 0 "
					+ " end)) as elektriklikontrolAdet,"
					+ " sum((case c.cihaz_tip_id "
					+ " when 68 then 1 "
					+ " else 0  end)) as hidrolikkontrolAdet,"
					+ " count(1) as toplam "
					+ " from akm.kontrol kont "
					+ " join akm.kullanici kul on(kont.kontrol_muhendisi_sicilno=kul.sicilno) "
					+ " join akm.cihaz c on(kont.cihaz_id=c.cihaz_id) "
					+ " join akm.bina f on(f.bina_id=c.bina_id) "
					+ " where kont.kontrol_bitis_tarihi >= ? and kont.kontrol_bitis_tarihi <= ? "), _GETBELEDIYEKONTROLLER_(
			"select bel.kod as belediyekod,"
					+ "(case ltrim(rtrim(nullif(bel.adi,'Belediye Bulunamadı')))  when ''  then 'Belediye Bulunamadı' else  nullif(bel.adi,'Belediye Bulunamadı') end)"
					+ "  as belediyeadi,"
					+ " sum((case when a.kontrol_turu='N' then 1 else 0 end )) as normalKontrol,"
					+ " sum((case when a.kontrol_turu='E' then 1 else 0 end )) as eksiklikKontrol,"
					+ "sum((case d.cihaz_tip_id  when 66 then 1  when null then 1  when 0 then 1  else 0  end)) as elektriklikontrolAdet,"
					+ "sum((case d.cihaz_tip_id  when 68 then 1  else 0  end)) as hidrolikkontrolAdet, count(1) as toplam   from "
					+ Ayarlar.SHEMA + ".kontrol a join  " + Ayarlar.SHEMA
					+ ".randevu b on (b.randevu_id=a.randevu_id) join  "
					+ Ayarlar.SHEMA
					+ ".cihaz d on (d.cihaz_id=a.cihaz_id) join "
					+ Ayarlar.SHEMA
					+ ".basvuru e on (e.basvuru_id=b.basvuru_id) join "
					+ Ayarlar.SHEMA
					+ ".bina f on (f.bina_id=e.bina_id) left outer join "
					+ Ayarlar.SHEMA + ".belediye bel on (bel.kod=f.belediye) "
					+ " where a.kontrol_bitis_tarihi >= ? and a.kontrol_bitis_tarihi <= ? "), _GETETIKETVEYAPITIPLERINEGOREKONTROLLER_(
			"select bel.kod as belediyekod,"
					+ " (case ltrim(rtrim(nullif(bel.adi,'Belediye Bulunamadı')))  when ''  then 'Belediye Bulunamadı' else  "
					+ "nullif(bel.adi,'Belediye Bulunamadı') end) "
					+ "  as belediyeadi,"
					+ " f.yapitip as yapitipi,y.tip as yapitipiadi,a.etiket,d.cihaz_tip_id as cihaztipid,a.kontrol_turu as kontrolTuru "
					+ " from " + Ayarlar.SHEMA + ".kontrol a join "
					+ Ayarlar.SHEMA
					+ ".randevu b on (b.randevu_id=a.randevu_id) join "
					+ Ayarlar.SHEMA
					+ ".cihaz d on (d.cihaz_id=a.cihaz_id) join "
					+ Ayarlar.SHEMA
					+ ".basvuru e on (e.basvuru_id=b.basvuru_id) join "
					+ Ayarlar.SHEMA
					+ ".bina f on (f.bina_id=e.bina_id) left outer join "
					+ Ayarlar.SHEMA
					+ ".belediye bel on (bel.kod=f.belediye) left outer join "
					+ Ayarlar.SHEMA + ".yapikonusu y on (id=f.yapitip) "
					+ " where a.kontrol_bitis_tarihi >= ? and a.kontrol_bitis_tarihi <= ? "
					+ "  and a.kontrol_bitis_tarihi="
					+ "(select max(kontrol_bitis_tarihi) from " + Ayarlar.SHEMA
					+ ".kontrol  where cihaz_id=a.cihaz_id) "), _GETBINAKONTROLVEODEME_(
			"select a.randevu_id as randevuid, a.kontrol_id as kontrolid,a.kontrol_baslangic_tarihi as kontroltarihi,"
					+ "a.etiket,"
					+ "a.cihaz_id as cihazid, f.bina_id as binakodu,f.tescilno as tescilno,f.bina_adi as binaadi,"
					+ " /*rtrim(ltrim(nullif(f.acik_adres,''))) as acikadres,*/ concat(f.cadde_sokak,' ',f.mahalle,' Mahallesi No: ',f.bina_no,' ',"
					+ "i.il_adi,' ',i.ilce_adi) as acikAdres,"
					+ "bel.kod as belediyeKod,"
					+ " (case ltrim(rtrim(nullif(bel.adi,'Belediye Bulunamadı')))  when ''  then 'Belediye Bulunamadı' else "
					+ "nullif(bel.adi,'Belediye Bulunamadı') end)  as belediyeadi,"
					+ "f.yapitip as yapitipi,y.tip as yapitipiadi,kisi.adi||' '||kisi.soyadi as yoneticiadisoyadi,"
					+ "(case kisi.telefon_no when 0 then kisi.telefon_no_gsm else kisi.telefon_no end) as yoneticitelefon,"
					+ "(case d.cihaz_tip_id when 66 then 'Elektrikli' when 68 then 'Hidrolik' else '' end) as cihaztipi,"
					+ " d.cihaz_tip_id as cihazTipId,"
					+ "e.basvuru_id as basvuruid from "
					+ Ayarlar.SHEMA
					+ ".bina_kontrol a join "
					+ Ayarlar.SHEMA
					+ ".randevu b on (b.randevu_id=a.randevu_id) join "
					+ Ayarlar.SHEMA
					+ ".cihaz d on (d.cihaz_id=a.cihaz_id) join "
					+ Ayarlar.SHEMA
					+ ".basvuru e on (e.basvuru_id=b.basvuru_id) join "
					+ Ayarlar.SHEMA
					+ ".bina f on (f.bina_id=e.bina_id) left outer join "
					+ Ayarlar.SHEMA
					+ ".belediye bel on (bel.kod=f.belediye) left outer join "
					+ Ayarlar.SHEMA
					+ ".yapikonusu y on (id=f.yapitip) left outer join "
					+ Ayarlar.SHEMA
					+ ".ililcetb i on (i.il_kodu=f.il and i.ilce_kodu=f.ilce) left outer join "
					+ Ayarlar.SHEMA
					+ ".bina_kisi kisi on kisi.bina_id=f.bina_id and kisi.sorumluluk_turu=1 and "
					+ " kisi.kayit_tarihi=(select max(kayit_tarihi) from "
					+ Ayarlar.SHEMA
					+ ".bina_kisi where bina_id=kisi.bina_id and sorumluluk_turu=1) "
					+ " where a.kontrol_bitis_tarihi>= ? and a.kontrol_bitis_tarihi<= ?  "), 
					_GETBINAKONTROLFORBELEDIYE_(
							"select a.randevu_id as randevuid, a.kontrol_id as kontrolid,a.kontrol_baslangic_tarihi as kontroltarihi,"
									+ "a.etiket,"
									+ "a.cihaz_id as cihazid, f.bina_id as binakodu,f.tescilno as tescilno,f.bina_adi as binaadi,"
									+ " rtrim(ltrim(nullif(f.acik_adres,''))) as acikadres, f.mahalle,f.cadde_sokak as caddesokak,"
									+ "i.il_adi as iladi,i.ilce_adi as ilceadi,f.bina_no as binano,"
									+ "bel.kod as belediyeKod,"
									+ " (case ltrim(rtrim(nullif(bel.adi,'Belediye Bulunamadı')))  when ''  then 'Belediye Bulunamadı' else "
									+ "nullif(bel.adi,'Belediye Bulunamadı') end)  as belediyeadi,"
									+ "f.yapitip as yapitipi,y.tip as yapitipiadi,kisi.adi||' '||kisi.soyadi as yoneticiadisoyadi,"
									+ "(case kisi.telefon_no when 0 then kisi.telefon_no_gsm else kisi.telefon_no end) as yoneticitelefon,"
									+ "(case d.cihaz_tip_id when 66 then 'Elektrikli' when 68 then 'Hidrolik' else '' end) as cihaztipi,"
									+ " d.cihaz_tip_id as cihazTipId,"
									+ "e.basvuru_id as basvuruid from "
									+ Ayarlar.SHEMA
									+ ".bina_kontrol a join "
									+ Ayarlar.SHEMA
									+ ".randevu b on (b.randevu_id=a.randevu_id) join "
									+ Ayarlar.SHEMA
									+ ".cihaz d on (d.cihaz_id=a.cihaz_id) join "
									+ Ayarlar.SHEMA
									+ ".basvuru e on (e.basvuru_id=b.basvuru_id) join "
									+ Ayarlar.SHEMA
									+ ".bina f on (f.bina_id=e.bina_id) left outer join "
									+ Ayarlar.SHEMA
									+ ".belediye bel on (bel.kod=f.belediye) left outer join "
									+ Ayarlar.SHEMA
									+ ".yapikonusu y on (id=f.yapitip) left outer join "
									+ Ayarlar.SHEMA
									+ ".ililcetb i on (i.il_kodu=f.il and i.ilce_kodu=f.ilce) left outer join "
									+ Ayarlar.SHEMA
									+ ".bina_kisi kisi on kisi.bina_id=f.bina_id and kisi.sorumluluk_turu=1 and "
									+ " kisi.kayit_tarihi=(select max(kayit_tarihi) from "
									+ Ayarlar.SHEMA
									+ ".bina_kisi where bina_id=kisi.bina_id and sorumluluk_turu=1) "
									+ " where a.kontrol_bitis_tarihi>= ? and a.kontrol_bitis_tarihi<= ? and a.onay_durum='O' and f.belediye=?  "),
					_FIRMASIL_(
			"delete from " + Ayarlar.SHEMA + ".bakimci_firma where kod=?"), _FIRMAKAPSAMINSERT_(
			"insert into "
					+ Ayarlar.SHEMA
					+ ".bakimci_firma_kapsam (firma_kod,il,userid) values(?,?,?)"), _GETBAKIMCIFIRMAKAPSAM_(
			"select id,firma_kod as firmaKod,il from  " + Ayarlar.SHEMA
					+ ".bakimci_firma_kapsam where firma_kod=?"), _FIRMAKAPSAMDELETE_(
			"delete from " + Ayarlar.SHEMA
					+ ".bakimci_firma_kapsam where firma_kod=?"), _GETUAVTBINA_(
			"SELECT kod, diskapino, siteadi, blokadi, postakodu, esbinakodu, nitelik,"
					+ "csbmkodu, ada, pafta, parsel, binano from "
					+ Ayarlar.SHEMAUAVT + ".bina where csbmkodu=?"), _INSERTUAVTBINA_(
			"INSERT INTO "
					+ Ayarlar.SHEMAUAVT
					+ ".bina ( "
					+ "kod, diskapino, siteadi, blokadi, postakodu, esbinakodu, nitelik,"
					+ "csbmkodu, ada, pafta, parsel, binano) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?)"), _INSERTUAVTRAPORKAYIT_(
			"INSERT INTO "
					+ Ayarlar.SHEMAUAVT
					+ ".rapor_kayit("
					+ "denetimdurumid, denetimkayitid, raporid, cihazid, kayittarihi,userid) "
					+ " VALUES (?, ?, ?, ?, current_date,?)"), _DELETEUAVTRAPORKAYIT_(
			"Delete from " + Ayarlar.SHEMAUAVT
					+ ".rapor_kayit where denetimkayitid=? and raporid=?"), _GETKAYITLIRAPORLAR_(
			"SELECT denetimdurumid, denetimkayitid, raporid, cihazid, kayittarihi, userid from "
					+ Ayarlar.SHEMAUAVT + ".rapor_kayit where raporid=?"), _GETKONTROLFIRMAKATILIM_(
			"SELECT id, kontrol_id as kontrolid, firma_id as firmaid, firma_kontrole_katildi as firmakontrolekatildi, firma_gorevli1 as firmagorevli1,"
					+ "firma_gorevli2 as firmagorevli2, firma_gorevli3 as firmagorevli3,firma_gorevli1_gorev as firmagorevli1gorev,"
					+ "firma_gorevli2_gorev as firmagorevli2gorev,firma_gorevli3_gorev as firmagorevli3gorev, userid "
					+ "FROM "
					+ Ayarlar.SHEMA
					+ ".kontrol_firma_katilim where kontrol_id=?"), _INSERTKONTROLFIRMAKATILIM_(
			"INSERT INTO "
					+ Ayarlar.SHEMA
					+ ".kontrol_firma_katilim("
					+ " kontrol_id, firma_id, firma_kontrole_katildi, firma_gorevli1,"
					+ "firma_gorevli2, firma_gorevli3,firma_gorevli1_gorev,firma_gorevli2_gorev,firma_gorevli3_gorev, userid) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?)"), _UPDATEKONTROLFIRMAKATILIM_(
			"UPDATE  "
					+ Ayarlar.SHEMA
					+ ".kontrol_firma_katilim "
					+ "SET firma_id=?, firma_kontrole_katildi=?, firma_gorevli1=?, "
					+ "firma_gorevli2=?, firma_gorevli3=?,firma_gorevli1_gorev=?,firma_gorevli2_gorev=?,firma_gorevli3_gorev=?, userid=? "
					+ " WHERE kontrol_id=?"), _KONTROLFIRMAKATILIMDELETE_(
			"delete from " + Ayarlar.SHEMA
					+ ".kontrol_firma_katilim where kontrol_id=?"), _GETBAKIMCIPERSONELADSOYAD_(
			"select firma_gorevli1 as firmaGorevli1,firma_gorevli1_gorev as firmagorevli1gorev from " + Ayarlar.SHEMA
					+ ".kontrol_firma_katilim where kontrol_id=?"), _GETBELEDIYEODEMELER_(
			"select bel.kod as belediyekod,bel.adi as belediyeAdi,b.basvuru_id as basvuruId,o.toplam_tutar as kontrolTutari,o.odeme_tutari as odenenTutar"
					+ " from akm.odeme o "
					+ " join "
					+ Ayarlar.SHEMA
					+ ".basvuru b on(o.basvuru_id=b.basvuru_id) "
					+ " join "
					+ Ayarlar.SHEMA
					+ ".basvuru_asansor bas on(o.basvuru_id=bas.basvuru_id) "
					+ " join "
					+ Ayarlar.SHEMA
					+ ".bina bi on(bi.bina_id=b.bina_id) "
					+ " join "
					+ Ayarlar.SHEMA
					+ ".belediye bel on(bel.kod=bi.belediye) "
					+ "where bas.kontrol_tarihi>= ? and bas.kontrol_tarihi<= ? and bas.kontrol_turu='N' and bas.kontrol_iptal='f' "), _GETODEMEKONTROL_(
			"select bel.kod as belediyekod,bel.adi as belediyeAdi,bi.bina_id as binaId,bi.bina_adi as binaAdi,"
					+ "(select max(kontrol_tarihi) from akm.basvuru_asansor where b.basvuru_id=basvuru_id) as kontroltarihi,"
					+ "sum(case when c.cihaz_tip_id=66 then 1 else 0 end) as elektrikliAsansor,"
					+ " sum(case when c.cihaz_tip_id=68 then 1 else 0 end) as hidrolikAsansor,"
					+ "b.basvuru_id as basvuruId,o.toplam_tutar as kontrolTutari,o.odeme_tutari as odenenTutar,"
					+ "(o.toplam_tutar-o.odeme_tutari) as odenmeyenTutar,"
					+ "bi.acik_adres as acikAdres,bi.mahalle as mahalle,bi.cadde_sokak as caddeSokak,bi.bina_no as binaNo "
					+ " from akm.odeme o "
					+ " join "
					+ Ayarlar.SHEMA
					+ ".basvuru b on(o.basvuru_id=b.basvuru_id) "
					+ " join "
					+ Ayarlar.SHEMA
					+ ".basvuru_asansor bas on(o.basvuru_id=bas.basvuru_id) "
					+ " join "
					+ Ayarlar.SHEMA
					+ ".bina bi on(bi.bina_id=b.bina_id) "
					+ " join "
					+ Ayarlar.SHEMA
					+ ".belediye bel on(bel.kod=bi.belediye) "
					+ " join "
					+ Ayarlar.SHEMA
					+ ".cihaz c on(c.cihaz_id=bas.cihaz_id) "
					+ " join "
					+ Ayarlar.SHEMA+".randevu r on(b.basvuru_id=r.basvuru_id) "
					+ " join "
					+ Ayarlar.SHEMA+".kontrol k on(k.randevu_id=r.randevu_id and k.cihaz_id=c.cihaz_id) "
					+ "where k.kontrol_bitis_tarihi>= ? and k.kontrol_bitis_tarihi<= ? and k.kontrol_turu='N' and bas.kontrol_iptal='f' and k.onay_durum='O' and (k.revizyon_rapor!='t' or k.revizyon_rapor is null ) "),
					_GETBUCAKKOY_(
			"SELECT a.ad as bucakadi,"
					+ "b.ad as koyadi,b.kod as koykodu,a.kod as bucakkodu "
					+ " FROM " + Ayarlar.SHEMAUAVT + ".bucak a join "
					+ Ayarlar.SHEMAUAVT + ".koy b on (b.bucakkod=a.kod) "
					+ " where a.ilcekodu=? "),_GETBELEDIYEBILGI_(
							"select a.randevu_id as randevuid, a.kontrol_id as kontrolid,a.kontrol_bitis_tarihi as kontroltarihi,"
									+ "case when o.odeme_tutari>0 and o.odeme_tutari=o.toplam_tutar then 'EVET' "
									+" when o.toplam_tutar=0 and "
									+" o.odeme_tutari=0 then 'EKSİKLİK KONTROLÜ' "
									+" when o.toplam_tutar>0 and o.odeme_tutari=0 then 'HAYIR' "
									+ "when o.odeme_tutari>0 and o.odeme_tutari!=o.toplam_tutar then 'ÖDEME EKSİK' end odemeYapildimi,"
									+ "a.etiket,"
									+ "a.cihaz_id as cihazid, f.bina_id as binakodu,f.tescilno as tescilno,f.bina_adi as binaadi,"
									+ " /*rtrim(ltrim(nullif(f.acik_adres,''))) as acikadres,*/ concat(f.cadde_sokak,' ',f.mahalle,' Mahallesi No: ',f.bina_no,' ',"
									+ "i.il_adi,' ',i.ilce_adi) as acikAdres,"
									+ "bel.kod as belediyeKod,"
									+ " (case ltrim(rtrim(nullif(bel.adi,'Belediye Bulunamadı')))  when ''  then 'Belediye Bulunamadı' else "
									+ "nullif(bel.adi,'Belediye Bulunamadı') end)  as belediyeadi,"
									+ "f.yapitip as yapitipi,y.tip as yapitipiadi,kisi.adi||' '||kisi.soyadi as yoneticiadisoyadi,"
									+ "(case when kisi.telefon_no is null or kisi.telefon_no=0 then kisi.telefon_no_gsm else kisi.telefon_no end) as yoneticitelefon,"
									+ "(case d.cihaz_tip_id when 66 then 'Elektrikli' when 68 then 'Hidrolik' else '' end) as cihaztipi,"
									+ " d.cihaz_tip_id as cihazTipId,"
									+ "e.basvuru_id as basvuruid from "
									+ Ayarlar.SHEMA
									+ ".asansor_son_etiket a join "
									+ Ayarlar.SHEMA
									+ ".randevu b on (b.randevu_id=a.randevu_id) join "
									+ Ayarlar.SHEMA
									+ ".cihaz d on (d.cihaz_id=a.cihaz_id) join "
									+ Ayarlar.SHEMA
									+ ".basvuru e on (e.basvuru_id=b.basvuru_id) join "
									+ Ayarlar.SHEMA
									+ ".odeme o on (o.basvuru_id=e.basvuru_id) join "
									+ Ayarlar.SHEMA
									+ ".bina f on (f.bina_id=e.bina_id) left outer join "
									+ Ayarlar.SHEMA
									+ ".belediye bel on (bel.kod=f.belediye) left outer join "
									+ Ayarlar.SHEMA
									+ ".yapikonusu y on (y.id=f.yapitip) left outer join "
									+ Ayarlar.SHEMA
									+ ".ililcetb i on (i.il_kodu=f.il and i.ilce_kodu=f.ilce) left outer join "
									+ Ayarlar.SHEMA
									+ ".bina_kisi kisi on kisi.bina_id=f.bina_id and kisi.sorumluluk_turu=1 and "
									+ " kisi.kayit_tarihi=(select max(kayit_tarihi) from "
									+ Ayarlar.SHEMA
									+ ".bina_kisi where bina_id=kisi.bina_id and sorumluluk_turu=1) "
									+ " where a.kontrol_bitis_tarihi>= ? and a.kontrol_bitis_tarihi<= ?  "		
							),
							_GETDUYURULAR_("SELECT id, konu, detay, tarih,"
									+ "case  when current_date - tarih<8 then true else false end  as yenimi " 
									+ "FROM "
									+ Ayarlar.SHEMA+ ".duyurular   order by tarih desc,konu"), _DUYURUINSERT_("INSERT INTO "
											+ Ayarlar.SHEMA+".duyurular(konu, detay, tarih) VALUES (?, ?,current_date)"), _DUYURUUPDATE_("UPDATE "
													+ Ayarlar.SHEMA+".duyurular SET konu=?, detay=?  WHERE id=?"),
													_CURRVALDUYUID_("select currval(pg_get_serial_sequence('" + Ayarlar.SHEMA
															+ ".duyurular', 'id'))"), _GETREVIZYONKONTROL_("select b.kontrol_id as kontrolid from "
																	+ Ayarlar.SHEMA+".kontrol a ,"
																			+  Ayarlar.SHEMA+".kontrol b "
																			+ "where a.kontrol_id=? and a.randevu_id=b.randevu_id  and a.kontrol_id!=b.kontrol_id and b.revizyon_rapor"), 
																			_ONAYLIRAPORIPTAL_("update "
																					+ Ayarlar.SHEMA+".rapor set rapor_iptal=true where rapor_id=?"),
																					_ONAYLIKONTROLIPTAL_("update "
																							+ Ayarlar.SHEMA+".kontrol set rapor_iptal=true where kontrol_id=?"),
																							_SETODEMEIPTALKOD_("update "
																									+Ayarlar.SHEMA +".basvuru_asansor set kontrol_iptal=true where basvuru_id=? and cihaz_id=?"),
	_ODEMETUTARUPDATE_("update "
			+Ayarlar.SHEMA +".odeme set toplam_tutar=toplam_tutar-?,"
					+ "odeme_tutari=case when odeme_tutari>? then odeme_tutari-? else odeme_tutari end where basvuru_id=?"),
					_GETCIHAZANLASMALIFIRMATARIH_("select max(sozlesme_tarih) as tarih from "
							+ Ayarlar.SHEMA+".cihaz_anlasmali_firma where cihaz_id=? and  firma_id=?"), 
							_RANDEVUYAAITDIGERKONTROL_("SELECT kontrol_id as kontrolid, randevu_id as randevuid, kontrol_baslangic_tarihi as kontrolbaslangictarihi,"
									+ " kontrol_baslangic_saati as kontrolbaslangicsaati,"
									+ "kontrol_bitis_tarihi as kontrolbitistarihi, kontrol_bitis_saati as kontrolbitissaati, cihaz_id as cihazid, etiket,"
									+ "onay_durum as onaydurum, onaylayan_kullanici_id as onaylayanSicilNo, onay_tarihi as onayTarihi, kontrol_turu as kontrolTuru,"
									+ "aciklamalar, revizyon_rapor as revizyonrapor, kontrol_muhendisi_sicilno as kontrolMuhendisiSicilNo,rapor_iptal as raporiptal"
									+ " from "
									+ Ayarlar.SHEMA+".kontrol where randevu_id=? and  cihaz_id=? and kontrol_id!=? and not rapor_iptal"),
									_GETBAKANLIKKAYITLIRAPOR_("select denetimdurumid,denetimkayitid,raporid,cihazid,kayittarihi "
											+ "from "
											+ Ayarlar.SHEMAUAVT+".rapor_kayit where raporid in "
													+ "(select rapor_id from "
													+ Ayarlar.SHEMA+".rapor where kontrol_id=?)"),
		_GETDENETIMKAYITRAPORLAR_("select rapor_id as raporid,kontrol_id as kontrolid,dosya_adi as dosyaadi,"
				+ "rapor_tarihi as raportarihi,dosya,kontrol_kod_eski as kontrolkodeski,rapor_iptal as raporiptal "
				+ " from "
				+ Ayarlar.SHEMA+".rapor rapor where "
				+ " not rapor_iptal and "
				+ "  exists  (select 1 from "
				+ Ayarlar.SHEMA+".kontrol where onay_tarihi=current_date and kontrol_id=rapor.kontrol_id) and "
				+ " not exists (Select 1 from "
				+ Ayarlar.SHEMAUAVT+".rapor_kayit where raporid=rapor.rapor_id)"), _SETDENETIMKAYITLOG_("INSERT INTO "
						+ Ayarlar.SHEMAUAVT+".denetim_kayit_log(rapor_id, tarih, hata_kodu, hata_mesaj) "
						+ " VALUES (?, now(), ?, ?);"), _CIHAZUAVTUPDATE_("UPDATE "
								+ Ayarlar.SHEMA+".cihaz SET uavt_kod=?, uavt_etiket=?, uavt_sira_no=?  WHERE cihaz_id=?"),
		_CIHAZODEMEKONTROL_("select bina.bina_id as binaId,bina.bina_adi as binaAdi,bina.mahalle as mahalle,bina.cadde_sokak as caddeSokak,"
				            + "bina.bina_no as binaNo,bina.acik_adres as acikAdres,yk.tip as yapiTip,"
				            +" bk.adi||' '||bk.soyadi as yonetici,"
							+"(case when bk.telefon_no is null or bk.telefon_no=0 then bk.telefon_no_gsm else bk.telefon_no end) as telefonNo,"
				            + "bel.adi as belediyeAdi,"
				            + "case when k.etiket='K' then 'KIRMIZI' when k.etiket='S' then 'SARI' when k.etiket='M' then 'MAVİ' when k.etiket='Y' then 'YEŞİL' end etiket, "
				            + " bina.ada as ada,bina.parsel as parsel,bina.pafta as pafta, "
							+" b.basvuru_id as basvuruId,cihaz.kimlik_no as kimlikNo,bf.unvan as bakimciFirmaAdi,bf.tse_belge_no as tseBelgeNo,bf.gecerlilik_suresi as gecerlilikSuresi, "
							+" case cihaz_tip_id when 66 then 'Elektrikli' when 68 then 'Hidrolik' else '' end cihazTipi, "
							+" (select nullif(cevap,'') from "
							+Ayarlar.SHEMA +".cihaz_deger where cihaz_id=cihaz.cihaz_id and (deger_id=157 or deger_id=182)) as durakSayisi, "
							+" k.kontrol_bitis_tarihi as kontrolTarihi,bas.kontrol_turu as kontrolTuru,bas.kontrol_tutari as kontrolTutari,o.odeme_tarihi as odemeTarihi, "
							+" case when o.odeme_tutari>0 and odeme_tutari=toplam_tutar then 'EVET' " 
							+" when o.toplam_tutar=0 and o.odeme_tutari=0 then 'EKSİKLİK KONTROLÜ' "
							+" when o.toplam_tutar>0 and o.odeme_tutari=0 then 'HAYIR' "
							+" when o.odeme_tutari>0 and o.odeme_tutari!=toplam_tutar then 'ÖDEME EKSİK'	end odendiMi "
							+" from " 
							+Ayarlar.SHEMA +".bina bina " 
							+" join " 
							+Ayarlar.SHEMA +".belediye bel on(bel.kod=bina.belediye) " 
							+" join "
							+Ayarlar.SHEMA +".basvuru b on(b.bina_id=bina.bina_id) "
							+" join "
							+Ayarlar.SHEMA +".basvuru_asansor bas on(bas.basvuru_id=b.basvuru_id) "
							+" join "
							+Ayarlar.SHEMA +".bakimci_firma bf on(bf.kod=bas.bakimci_firma_id) "
							+" join "
							+Ayarlar.SHEMA +".odeme o on(o.basvuru_id=bas.basvuru_id) "
							+" join "
							+Ayarlar.SHEMA +".randevu r on(r.basvuru_id=b.basvuru_id) "
							+" join " 
							+Ayarlar.SHEMA +".kontrol k on(k.randevu_id=r.randevu_id and bas.cihaz_id=k.cihaz_id) "
						    +" join "
							+Ayarlar.SHEMA +".cihaz cihaz on(cihaz.cihaz_id=k.cihaz_id and cihaz.cihaz_id=bas.cihaz_id) "
							+" join "
							+Ayarlar.SHEMA +".yapikonusu yk on(yk.id=bina.yapitip) "
							+" left outer join "
							+Ayarlar.SHEMA +".bina_kisi bk on(bk.bina_id=bina.bina_id and bk.sorumluluk_turu=1 " 
							+" and bk.kayit_tarihi=(select max(kayit_tarihi) from akm.bina_kisi where bina_id=bk.bina_id and sorumluluk_turu=1)) " 
							+"where k.kontrol_bitis_tarihi >= ? and k.kontrol_bitis_tarihi <= ? and (k.revizyon_rapor!='t' or k.revizyon_rapor is null ) and k.onay_durum='O' "),
	_GETBASVURUIDBYRAPORID_("select b.basvuru_id as basvuruId"
						  +" from "
						  +Ayarlar.SHEMA +".rapor rapor "
						  + " join "
						  +Ayarlar.SHEMA +".kontrol k on(k.kontrol_id=rapor.kontrol_id) "
						  +" join "
						  +Ayarlar.SHEMA +".randevu randevu on(randevu.randevu_id=k.randevu_id) "
						  +" join "
						  +Ayarlar.SHEMA +".basvuru b on(randevu.basvuru_id=b.basvuru_id) "
						  +" where rapor.rapor_id=? "),
	      _ODEMEALINDIMI_("select id,basvuru_id as basvuruId,toplam_tutar as toplamTutar,"
						  + "odeme_tutari as odemeTutari,odeme_tarihi as odemeTarihi,odemekontroldealinsin as odemeKontroldeAlinsin,"
						  +"aciklama as aciklama,odeme_sekli as odemeSekli,odeme_belge_no as odemeBelgeNo "
						  +" from "
						  +Ayarlar.SHEMA +".odeme where basvuru_id=? "),
						  _GETETIKETLISTE_("select r.randevu_id as randevuId,r.randevu_tarihi as randevuTarihi,bas.basvuru_id as basvuruId, "
							        + "rm.muhendis_sicilno as muhendisSicilNo,c.cihaz_id as cihazId,concat(k.adi,' ',k.soyadi) as muhendisAdSoyad, "
									+"c.uavt_kod as asansorUavtKod,c.uavt_etiket as asansorUavtEtiket,b.bina_id as binaId,b.bina_adi as binaAdi, "
									+"concat(b.cadde_sokak,' NO:',b.bina_no,' ',b.mahalle,' ',ilce.ad,' ',il.ad) as binaAdres,b.uavt_kod as binaUavtKod "
									+" from "
									+Ayarlar.SHEMA +".randevu r "
									+" join " 
									+Ayarlar.SHEMA +".randevu_muhendis rm on(r.randevu_id=rm.randevu_id) "
									+" join " 
									+Ayarlar.SHEMA +".basvuru_asansor bas on(bas.basvuru_id=r.basvuru_id) "
									+" join " 
									+Ayarlar.SHEMA +".cihaz c on(c.cihaz_id=bas.cihaz_id) "
									+" join " 
									+Ayarlar.SHEMA +".bina b on(b.bina_id=c.bina_id)"
									+" join " 
									+ Ayarlar.SHEMAUAVT+".ilce ilce on(ilce.kod=b.ilce) "
									+" join "
									+ Ayarlar.SHEMAUAVT+".il il on(il.kod=b.il)"
									+ "join "
									+Ayarlar.SHEMA +".kullanici k on(k.sicilno=rm.muhendis_sicilno)  "
									+" where rm.muhendis_sicilno=? and r.randevu_tarihi = ? "),
		_UAVTKODKONTROL_("select uavt_kod as uavtKod,bina_id as binaId from "
						+Ayarlar.SHEMA +".bina where uavt_kod=? "),
						 _GETREFERANSDENETIMKAYITESLESTIRME_("SELECT  id, soruid, asansortipi, tsestandartid from "
									+ Ayarlar.SHEMAUAVT+".referansdenetimsorulareslestirmelistesi"), _GETBAKANLIKSORUTARIH_("select id,tarih from  "
											+ Ayarlar.SHEMA+".bakanlik_soru_vo"),
		_GETODEMELEROTUZGUN_("select bel.kod as belediyekod,bel.adi as belediyeAdi,a.bina_id as binaId,a.bina_adi as binaAdi,"
					        +" (select max(kontrol_tarihi) from akm.basvuru_asansor where b.basvuru_id=basvuru_id) as kontroltarihi,"
							+" sum(case when c.cihaz_tip_id=66 then 1 else 0 end) as elektrikliAsansor,concat(bk.adi,' ',bk.soyadi) as binaKisi, "
							+"  sum(case when c.cihaz_tip_id=68 then 1 else 0 end) as hidrolikAsansor,"
							+ "(case when bk.telefon_no is null or bk.telefon_no=0 then bk.telefon_no_gsm else bk.telefon_no end) as telefonNo,"
							+" b.basvuru_id as basvuruId,o.toplam_tutar as kontrolTutari,o.odeme_tutari as odenenTutar,"
							+" (o.toplam_tutar-o.odeme_tutari) as odenmeyenTutar,"
							+" a.acik_adres as acikAdres,a.mahalle as mahalle,a.cadde_sokak as caddeSokak,a.bina_no as binaNo "
							+"  from akm.odeme o "
							+"  join "
							+Ayarlar.SHEMA +".basvuru b on(o.basvuru_id=b.basvuru_id) "
							+"  join "
							+Ayarlar.SHEMA +".basvuru_asansor bas on(o.basvuru_id=bas.basvuru_id) "
							+"  join "
							+Ayarlar.SHEMA +".bina a on(a.bina_id=b.bina_id) "
							+"  join "
							+Ayarlar.SHEMA +".belediye bel on(bel.kod=a.belediye) "
							+"  join "
							+Ayarlar.SHEMA +".cihaz c on(c.cihaz_id=bas.cihaz_id) "
							+"  join "
							+Ayarlar.SHEMA +".randevu r on(b.basvuru_id=r.basvuru_id) "
							+"  join "
							+Ayarlar.SHEMA +".kontrol k on(k.randevu_id=r.randevu_id and k.cihaz_id=c.cihaz_id) "
							+"  join "
							+Ayarlar.SHEMA +".kullanici kul on(kul.sicilno=k.kontrol_muhendisi_sicilno)"
							+"  join "
							+Ayarlar.SHEMA +".bakimci_firma bf on(bf.kod=bas.bakimci_firma_id)"
							+"  join " 
							+Ayarlar.SHEMA +".kullanici_yetkili_birim kyb on(kyb.kullanici_id=kul.kullanici_id)"
							+" left outer join " 
							+Ayarlar.SHEMA +".bina_kisi bk on(bk.bina_id=a.bina_id and bk.sorumluluk_turu=1  "
							+" and bk.kayit_tarihi=(select max(kayit_tarihi) from akm.bina_kisi where bina_id=bk.bina_id and sorumluluk_turu=1)) "
							+" where k.kontrol_bitis_tarihi>= ? and k.kontrol_bitis_tarihi<= ? and "
							+" k.kontrol_turu='N' and bas.kontrol_iptal='f' and (k.revizyon_rapor!='t' or k.revizyon_rapor is null ) "
							+" and (kyb.birim_kodu IN (select sube.kod from akm.sube as sube where kyb.birim_tip='S' ) "
							+" or kyb.birim_kodu IN (select temsilcilik.kod from akm.temsilcilik as temsilcilik where   kyb.birim_tip='T') )"),
	_GETKIRMIZIETIKETKONTROL_("select distinct(r.rapor_id) as raporId,k.kontrol_id as kontrolId,rand.randevu_id as randevuId,b.basvuru_id as basvuruId,bas.cihaz_id as cihazId, "
							 +" case when c.cihaz_tip_id=66 then 'ELEKTRİKLİ' else 'HİDROLİK' END cihazTuru,k.kontrol_bitis_tarihi as kontrolTarihi, "
							 +" case when k.etiket='K' then 'KIRMIZI' END etiket,case when k.kontrol_turu='N' then 'NORMAL' else 'EKSİKLİK' end kontrolTuru,concat(bk.adi,' ',bk.soyadi) as binaKisi, " 
							 +" (case when bk.telefon_no is null or bk.telefon_no=0 then bk.telefon_no_gsm else bk.telefon_no end) as telefonNo, "
							 +" c.uavt_etiket as asansorUavtEtiket,a.bina_id as binaId,a.bina_adi as binaAdi,a.mahalle as mahalle,a.cadde_sokak as caddeSokak,a.bina_no as binaNo,a.acik_adres as acikAdres, "
							 +" concat(kul.adi,' ',kul.soyadi) as muhendisAdiSoyadi,bf.unvan as bakimciFirmaAdi,bel.adi as belediyeAdi,bel.kod as belediyeKod "  
							 +" from "
							 +Ayarlar.SHEMA +".rapor r "
							 +" join " 
							 +Ayarlar.SHEMA +".rapor_teslim rt on(r.rapor_id=rt.rapor_id) "
							 +" join " 
							 +Ayarlar.SHEMA +".asansor_son_etiket k on(k.kontrol_id=r.kontrol_id) "
							 +" join "  
							 +Ayarlar.SHEMA +".randevu rand on(rand.randevu_id=k.randevu_id) "
							 +" join " 
							 +Ayarlar.SHEMA +".basvuru b on(b.basvuru_id=rand.basvuru_id) "
							 +" join "
							 +Ayarlar.SHEMA +".bina a on(a.bina_id=b.bina_id) "
							 +" join " 
							 +Ayarlar.SHEMA +".basvuru_asansor bas on(bas.basvuru_id=b.basvuru_id and k.cihaz_id=bas.cihaz_id) "
							 +" join " 
							 +Ayarlar.SHEMA +".cihaz c on(c.cihaz_id=k.cihaz_id and c.cihaz_id=bas.cihaz_id) "
							 +" join " 
							 +Ayarlar.SHEMA +".kullanici kul on(kul.sicilno=k.kontrol_muhendisi_sicilno) "
							 +" join "  
							 +Ayarlar.SHEMA +".bakimci_firma bf on(bf.kod=bas.bakimci_firma_id)  "
							 +" join "
							 +Ayarlar.SHEMA +".belediye bel on(bel.kod=a.belediye)"
							 +" join " 
							 +Ayarlar.SHEMA +".kullanici_yetkili_birim kyb on(kyb.kullanici_id=kul.kullanici_id) "
							 +" left outer join "  
							 +Ayarlar.SHEMA +".bina_kisi bk on(bk.bina_id=a.bina_id and bk.sorumluluk_turu=1  "
							 +" and bk.kayit_tarihi=(select max(kayit_tarihi) from akm.bina_kisi where bina_id=bk.bina_id and sorumluluk_turu=1)) " 
							 +" where rt.teslim_tarihi >= ? and rt.teslim_tarihi <= ? and k.etiket='K' and k.onay_durum='O' " 
							 +" and (kyb.birim_kodu IN (select sube.kod from akm.sube as sube where kyb.birim_tip='S' ) "  
							+" or kyb.birim_kodu IN (select temsilcilik.kod from akm.temsilcilik as temsilcilik where   kyb.birim_tip='T') )"), 
	_GETSARIETIKETKONTROL_("select distinct(r.rapor_id) as raporId,k.kontrol_id as kontrolId,rand.randevu_id as randevuId,b.basvuru_id as basvuruId,bas.cihaz_id as cihazId, "
							 +" case when c.cihaz_tip_id=66 then 'ELEKTRİKLİ' else 'HİDROLİK' END cihazTuru,k.kontrol_bitis_tarihi as kontrolTarihi, "
							 +" case when k.etiket='S' then 'SARI' END etiket,case when k.kontrol_turu='N' then 'NORMAL' else 'EKSİKLİK' end kontrolTuru,concat(bk.adi,' ',bk.soyadi) as binaKisi, " 
							 +" (case when bk.telefon_no is null or bk.telefon_no=0 then bk.telefon_no_gsm else bk.telefon_no end) as telefonNo, "
							 +" c.uavt_etiket as asansorUavtEtiket,a.bina_id as binaId,a.bina_adi as binaAdi,a.mahalle as mahalle,a.cadde_sokak as caddeSokak,a.bina_no as binaNo,a.acik_adres as acikAdres, "
							 +" concat(kul.adi,' ',kul.soyadi) as muhendisAdiSoyadi,bf.unvan as bakimciFirmaAdi "  
							 +" from "
							 +Ayarlar.SHEMA +".rapor r "
							 +" join " 
							 +Ayarlar.SHEMA +".rapor_teslim rt on(r.rapor_id=rt.rapor_id) "
							 +" join " 
							 +Ayarlar.SHEMA +".asansor_son_etiket k on(k.kontrol_id=r.kontrol_id) "
							 +" join "  
							 +Ayarlar.SHEMA +".randevu rand on(rand.randevu_id=k.randevu_id) "
							 +" join " 
							 +Ayarlar.SHEMA +".basvuru b on(b.basvuru_id=rand.basvuru_id) "
							 +" join "
							 +Ayarlar.SHEMA +".bina a on(a.bina_id=b.bina_id) "
							 +" join " 
							 +Ayarlar.SHEMA +".basvuru_asansor bas on(bas.basvuru_id=b.basvuru_id and k.cihaz_id=bas.cihaz_id) "
							 +" join " 
							 +Ayarlar.SHEMA +".cihaz c on(c.cihaz_id=k.cihaz_id and c.cihaz_id=bas.cihaz_id) "
							 +" join " 
							 +Ayarlar.SHEMA +".kullanici kul on(kul.sicilno=k.kontrol_muhendisi_sicilno) "
							 +" join "  
							 +Ayarlar.SHEMA +".bakimci_firma bf on(bf.kod=bas.bakimci_firma_id)  "
							 +" join " 
							 +Ayarlar.SHEMA +".kullanici_yetkili_birim kyb on(kyb.kullanici_id=kul.kullanici_id) "
							 +" left outer join "  
							 +Ayarlar.SHEMA +".bina_kisi bk on(bk.bina_id=a.bina_id and bk.sorumluluk_turu=1  "
							 +" and bk.kayit_tarihi=(select max(kayit_tarihi) from akm.bina_kisi where bina_id=bk.bina_id and sorumluluk_turu=1)) " 
							 +" where rt.teslim_tarihi >= ? and rt.teslim_tarihi <= ? and k.etiket='S' and k.onay_durum='O' " 
							 +" and (kyb.birim_kodu IN (select sube.kod from akm.sube as sube where kyb.birim_tip='S' ) "  
						   	 +" or kyb.birim_kodu IN (select temsilcilik.kod from akm.temsilcilik as temsilcilik where   kyb.birim_tip='T') )"),
							_GETTEMSILCILIKFROMILILCE_("select kod,adi,telefon_no as telefonno,faks,eposta,adres from "
									+ Ayarlar.SHEMA+".temsilcilik where kod in ( "
									+ "SELECT temsilcilik "
									+ " FROM "
									+ Ayarlar.SHEMA+".temsilcilik_ililce where il=? and ilce=?)"), _GETSUBEFROMIL_("select kod,sube||' ŞUBE' as adi,telefon_no as telefonno,faks,eposta,adres from "
											+ Ayarlar.SHEMA+".sube where il=?"),
			_GETRAPORTESLIMKONTROL_("select distinct(r.rapor_id) as raporId,a.bina_id as binaId,a.bina_adi as binaAdi,a.mahalle,a.cadde_sokak as caddeSokak,a.bina_no as binaNo, "
									+" a.acik_adres as acikAdres,concat(bk.adi,' ',bk.soyadi) as binaKisi,c.uavt_etiket as asansorUavtEtiket, "
									+" k.onay_tarihi as onayTarihi,(case when bk.telefon_no is null or bk.telefon_no=0 then bk.telefon_no_gsm else bk.telefon_no end) as telefonNo, "
									+" case when bas.bakimci_firma_id in(999999,0) then 'BAKIMCI FİRMA SEÇİLMEMİŞ' "
									+" else bf.unvan end bakimciFirma " 
									+" from "
									+Ayarlar.SHEMA +".kontrol k "
									+" join " 
									+Ayarlar.SHEMA +".rapor r on(r.kontrol_id=k.kontrol_id) "
									+" join " 
									+Ayarlar.SHEMA +".randevu rand on(rand.randevu_id=k.randevu_id) "
									+" join " 
									+Ayarlar.SHEMA +".basvuru b on(b.basvuru_id=rand.basvuru_id) "
									+" join " 
									+Ayarlar.SHEMA +".bina a on(a.bina_id=b.bina_id) "
									+" join " 
									+Ayarlar.SHEMA +".cihaz c on(c.cihaz_id=k.cihaz_id) "
									+" join " 
									+Ayarlar.SHEMA +".kullanici kul on(kul.sicilno=k.kontrol_muhendisi_sicilno) "
									+" join " 
									+Ayarlar.SHEMA +".kullanici_yetkili_birim kyb on(kyb.kullanici_id=kul.kullanici_id) "
									+" join " 
									+Ayarlar.SHEMA +".basvuru_asansor bas on(bas.basvuru_id=b.basvuru_id and bas.cihaz_id=k.cihaz_id) " 
									+" join " 
									+Ayarlar.SHEMA +".bakimci_firma bf on(bf.kod=bas.bakimci_firma_id or bas.bakimci_firma_id in (999999,0)) " 
									+" left outer join "
									+Ayarlar.SHEMA +".bina_kisi bk on(bk.bina_id=a.bina_id and bk.sorumluluk_turu=1 " 
									+" and bk.kayit_tarihi=(select max(kayit_tarihi) from akm.bina_kisi where bina_id=bk.bina_id and sorumluluk_turu=1)) " 
									+" where k.onay_tarihi >= ? and k.onay_tarihi <= ? "
									+" and r.rapor_id in(select rapor_id from akm.rapor except(select rapor_id from akm.rapor_teslim)) "
									+" and (kyb.birim_kodu IN (select sube.kod from akm.sube as sube where kyb.birim_tip='S' ) "
									+" or kyb.birim_kodu IN (select temsilcilik.kod from akm.temsilcilik as temsilcilik where   kyb.birim_tip='T')) "),
_GETTESLIMEDILMEMISRAPORLARFORONAYLAYAN_("select q.rapor_id as raporid, q.kontrol_id as kontrolid ,dosya,dosya_adi as dosyaadi,"
										+ "q.kontrol_kod_eski as eskiKontrolKodu,  d.basvuru_id as basvuruId,b.revizyon_rapor as revizyonRapor,"
										+ " rapor_tarihi as raportarih,cihaz.uavt_etiket as asansorUavtEtiket, "
										+ "(case when b.onay_durum='O' then 'ONAYLANDI' "
										+ "when b.onay_durum='M' then 'ONAYCI ONAYI BEKLENİYOR' "
										+ " when b.onay_durum='Y' then 'YAZDIRILMAMIŞ' "
										+ "  when b.onay_durum='T' then 'MÜHENDİS ONAYI BEKLENİYOR' end) as onayDurumu,"
										+ "b.onay_tarihi as onayTarihi ,"
										+ " b.etiket ,c.randevu_id as randevuid, a.tescilno as binaTescilNo,"
										+ "b.kontrol_Bitis_Tarihi as kontroltarihi, a.bina_adi as binaAdi, cd.cevap as asansorunyeri,q.rapor_iptal as raporiptal "
										+ " from "
										+ Ayarlar.SHEMA +".rapor q join "
										+ Ayarlar.SHEMA +".kontrol b on (b.kontrol_id=q.kontrol_id and b.onay_durum='O' and "
												+ " not b.rapor_iptal and ( onaylayan_kullanici_id=? or kontrol_muhendisi_sicilno=?)) "
										+ " join "
										+ Ayarlar.SHEMA +".randevu c on (c.randevu_id=b.randevu_id) "
										+ " join "
										+ Ayarlar.SHEMA +".basvuru d on ( d.basvuru_id=c.basvuru_id ) "
										+ " join "
										+ Ayarlar.SHEMA +".bina a on (a.bina_id=d.bina_id ) "
										+ " join "
										+ Ayarlar.SHEMA +".cihaz cihaz on (cihaz.cihaz_id=b.cihaz_id ) "
										+ " left outer join "
										+ Ayarlar.SHEMA +".cihaz_deger cd on ( cd.cihaz_id=b.cihaz_id and cd.deger_id in (151,171)) "
										
										+ " where current_date-q.rapor_tarihi  between 0 and 15  and not exists (select 1 from "
										+ Ayarlar.SHEMA +".rapor_teslim where rapor_id=q.rapor_id)"),
			_BELEDIYERAPORGONDERINSERT_("INSERT INTO "
									   + Ayarlar.SHEMA +".belediye_rapor_bildirim("
							           +" rapor_id, belediye_kod, belediye_adi, kontrol_id, randevu_id, "
							           +" basvuru_id, bina_id, etiket, tarih) "
							           +" VALUES (?, ?, ?, ?, ?, ?, ?, ?, current_date)"),
		    _BELEDIYERAPORGONDERKONTROL_("select rapor_id as raporId from " 
										+ Ayarlar.SHEMA +".belediye_rapor_bildirim where rapor_id=?"),
_GETBELEDIYEGONDERILENLERKIRMIZIKONTROL_("select distinct(brb.rapor_id),a.ilce,brb.kontrol_id,c.uavt_etiket as asansorUavtEtiket,a.uavt_kod as binaUavtKod,a.bina_id as binaId,a.bina_adi as binaAdi, "
										+" concat(a.cadde_sokak,' No: ',a.bina_no,' ',a.mahalle,' ',i.ad) as acikAdres,case when k.kontrol_turu='N' then 'Normal'"
										+ " when k.kontrol_turu='E' then '1.Eksiklik' when k.kontrol_turu='K' then '2.Eksiklik' end kontrolTuru ,  "
										+" brb.belediye_adi as belediyeAdi,brb.tarih as kayitTarihi from "
										+ Ayarlar.SHEMA +".belediye_rapor_bildirim brb"
										+" join "
										+ Ayarlar.SHEMA +".kontrol k on(k.kontrol_id=brb.kontrol_id) "
										+" join " 
										+ Ayarlar.SHEMA +".randevu r on(r.randevu_id=k.randevu_id) "
										+" join "
										+ Ayarlar.SHEMA +".basvuru b on(b.basvuru_id=r.basvuru_id) "
										+" join "
										+ Ayarlar.SHEMA +".bina a on(a.bina_id=b.bina_id) "
										+" join "
										+ Ayarlar.SHEMA +".kullanici kul on(kul.sicilno=k.kontrol_muhendisi_sicilno) "
										+" join "
										+ Ayarlar.SHEMA +".kullanici_yetkili_birim kyb on(kyb.kullanici_id=kul.kullanici_id) "
										+" join "
										+ Ayarlar.SHEMA +".cihaz c on(k.cihaz_id=c.cihaz_id and c.bina_id=a.bina_id) "
										+" join "
										+ Ayarlar.SHEMAUAVT+ ".ilce i on(i.kod=a.ilce)"
										+" join "
										+ Ayarlar.SHEMA +".rapor_teslim rt on(rt.rapor_id=brb.rapor_id) where "
										+ " rt.teslim_tarihi >= ? and rt.teslim_tarihi <= ? and brb.etiket='K' "),
_GETBELEDIYEGONDERILMEYENLERKIRMIZIKONTROL_("select distinct(rt.rapor_id) as raporId,a.ilce,k.kontrol_id as kontrolId,c.uavt_etiket as asansorUavtEtiket, "
										+ " a.uavt_kod as binaUavtKod,a.bina_id as binaId,a.bina_adi as binaAdi,r.randevu_id as randevuId,k.etiket as etiket, "
										+" concat(a.cadde_sokak,' No: ',a.bina_no,' ',a.mahalle,' ',i.ad) as acikAdres,bel.kod as belediyeKod,b.basvuru_id as basvuruId, "
										+" case when k.kontrol_turu='N' then 'Normal' when k.kontrol_turu='E' then '1.Eksiklik' when k.kontrol_turu='K' then '2.Eksiklik' end kontrolTuru , "
										+" bel.adi as belediyeAdi,rt.teslim_tarihi as raporteslimTarihi "
										+" from akm.rapor_teslim rt " 
										+" join "
										+ Ayarlar.SHEMA +".rapor rapor on(rt.rapor_id=rapor.rapor_id) "
										+" join "
										+ Ayarlar.SHEMA +".asansor_son_etiket k on(k.kontrol_id=rapor.kontrol_id) "  
										+" join " 
										+ Ayarlar.SHEMA +".randevu r on(r.randevu_id=k.randevu_id) "  
										+" join "
										+ Ayarlar.SHEMA +".basvuru b on(b.basvuru_id=r.basvuru_id) "  
										+" join "
										+ Ayarlar.SHEMA +".bina a on(a.bina_id=b.bina_id) "
										+" join "
										+ Ayarlar.SHEMA +".belediye bel on(bel.kod=a.belediye) "  
										+" join "
										+ Ayarlar.SHEMA +".kullanici kul on(kul.sicilno=k.kontrol_muhendisi_sicilno) " 
										+" join "
										+ Ayarlar.SHEMA +".kullanici_yetkili_birim kyb on(kyb.kullanici_id=kul.kullanici_id) " 
										+" join "
										+ Ayarlar.SHEMA +".cihaz c on(k.cihaz_id=c.cihaz_id and c.bina_id=a.bina_id) " 
										+" join "
										+ Ayarlar.SHEMAUAVT+".ilce i on(i.kod=a.ilce) " 
										+" where rt.teslim_tarihi >= ? and rt.teslim_tarihi <= ? "
										+" and rt.rapor_id not in (select rapor_id from "
										+ Ayarlar.SHEMA +".belediye_rapor_bildirim) and k.etiket='K' "),
_GETBELEDIYEGONDERILENLERSARIKONTROL_("select distinct(brb.rapor_id),a.ilce,brb.kontrol_id,c.uavt_etiket as asansorUavtEtiket,a.uavt_kod as binaUavtKod,a.bina_id as binaId,a.bina_adi as binaAdi, "
										+" concat(a.cadde_sokak,' No: ',a.bina_no,' ',a.mahalle,' ',i.ad) as acikAdres,case when k.kontrol_turu='N' then 'Normal'"
										+ " when k.kontrol_turu='E' then '1.Eksiklik' when k.kontrol_turu='K' then '2.Eksiklik' end kontrolTuru ,  "
										+" brb.belediye_adi as belediyeAdi,brb.tarih as kayitTarihi from "
										+ Ayarlar.SHEMA +".belediye_rapor_bildirim brb"
										+" join "
										+ Ayarlar.SHEMA +".kontrol k on(k.kontrol_id=brb.kontrol_id) "
										+" join " 
										+ Ayarlar.SHEMA +".randevu r on(r.randevu_id=k.randevu_id) "
										+" join "
										+ Ayarlar.SHEMA +".basvuru b on(b.basvuru_id=r.basvuru_id) "
										+" join "
										+ Ayarlar.SHEMA +".bina a on(a.bina_id=b.bina_id) "
										+" join "
										+ Ayarlar.SHEMA +".kullanici kul on(kul.sicilno=k.kontrol_muhendisi_sicilno) "
										+" join "
										+ Ayarlar.SHEMA +".kullanici_yetkili_birim kyb on(kyb.kullanici_id=kul.kullanici_id) "
										+" join "
										+ Ayarlar.SHEMA +".cihaz c on(k.cihaz_id=c.cihaz_id and c.bina_id=a.bina_id) "
										+" join "
										+ Ayarlar.SHEMAUAVT+ ".ilce i on(i.kod=a.ilce)"
										+" join "
										+ Ayarlar.SHEMA +".rapor_teslim rt on(rt.rapor_id=brb.rapor_id) where "
										+ " rt.teslim_tarihi >= ? and rt.teslim_tarihi <= ? AND brb.etiket='S' "),
_GETBELEDIYEGONDERILMEYENLERSARIKONTROL_("select distinct(rt.rapor_id) as raporId,a.ilce,k.kontrol_id as kontrolId,c.uavt_etiket as asansorUavtEtiket, "
										+ " a.uavt_kod as binaUavtKod,a.bina_id as binaId,a.bina_adi as binaAdi,r.randevu_id as randevuId,k.etiket as etiket, "
										+" concat(a.cadde_sokak,' No: ',a.bina_no,' ',a.mahalle,' ',i.ad) as acikAdres,bel.kod as belediyeKod,b.basvuru_id as basvuruId, "
										+" case when k.kontrol_turu='N' then 'Normal' when k.kontrol_turu='E' then '1.Eksiklik' when k.kontrol_turu='K' then '2.Eksiklik' end kontrolTuru , "
										+" bel.adi as belediyeAdi,rt.teslim_tarihi as raporteslimTarihi "
										+" from akm.rapor_teslim rt " 
										+" join "
										+ Ayarlar.SHEMA +".rapor rapor on(rt.rapor_id=rapor.rapor_id) "
										+" join "
										+ Ayarlar.SHEMA +".asansor_son_etiket k on(k.kontrol_id=rapor.kontrol_id) "  
										+" join " 
										+ Ayarlar.SHEMA +".randevu r on(r.randevu_id=k.randevu_id) "  
										+" join "
										+ Ayarlar.SHEMA +".basvuru b on(b.basvuru_id=r.basvuru_id) "  
										+" join "
										+ Ayarlar.SHEMA +".bina a on(a.bina_id=b.bina_id) "
										+" join "
										+ Ayarlar.SHEMA +".belediye bel on(bel.kod=a.belediye) "  
										+" join "
										+ Ayarlar.SHEMA +".kullanici kul on(kul.sicilno=k.kontrol_muhendisi_sicilno) " 
										+" join "
										+ Ayarlar.SHEMA +".kullanici_yetkili_birim kyb on(kyb.kullanici_id=kul.kullanici_id) " 
										+" join "
										+ Ayarlar.SHEMA +".cihaz c on(k.cihaz_id=c.cihaz_id and c.bina_id=a.bina_id) " 
										+" join "
										+ Ayarlar.SHEMAUAVT+".ilce i on(i.kod=a.ilce) " 
										+" where rt.teslim_tarihi >= ? and rt.teslim_tarihi <= ? "
										+" and rt.rapor_id not in (select rapor_id from "
										+ Ayarlar.SHEMA +".belediye_rapor_bildirim) and k.etiket='S' ");										


				
					 
					 

				


																


	

	public String qry;

	private Sorgular(String qry) {
		this.qry = qry;

	}

}
