package com.dyapp.anindaev.RestApi;

import com.dyapp.anindaev.Models.AddTokenPojo;
import com.dyapp.anindaev.Models.BildirimGonderPojo;
import com.dyapp.anindaev.Models.BildirimMailPojo;
import com.dyapp.anindaev.Models.DogrulamaPojo;
import com.dyapp.anindaev.Models.FavoriIlanlarimPojo;
import com.dyapp.anindaev.Models.FavoriIslemPojo;
import com.dyapp.anindaev.Models.FavoriKontrolPojo;
import com.dyapp.anindaev.Models.IlanDetayPojo;
import com.dyapp.anindaev.Models.IlanGuncellePojo;
import com.dyapp.anindaev.Models.IlanSilPojo;
import com.dyapp.anindaev.Models.IlanTumResimSilPojo;
import com.dyapp.anindaev.Models.IlanVermePojo;
import com.dyapp.anindaev.Models.IlanlarimGuncellemePojo;
import com.dyapp.anindaev.Models.IlanlarimPojo;
import com.dyapp.anindaev.Models.LoginPojo;
import com.dyapp.anindaev.Models.MailGonderPojo;
import com.dyapp.anindaev.Models.ProfilGuncellePojo;
import com.dyapp.anindaev.Models.ProfilimPojo;
import com.dyapp.anindaev.Models.RegisterPojo;
import com.dyapp.anindaev.Models.ResimEklePojo;
import com.dyapp.anindaev.Models.SifreDegistirPojo;
import com.dyapp.anindaev.Models.SliderResimPojo;
import com.dyapp.anindaev.Models.TumIlanlarPojo;

import java.util.List;

import retrofit2.Call;

public class ManagerAll extends BaseManager {

    private static ManagerAll ourInstance = new ManagerAll();

    public static synchronized ManagerAll getInstance() {
        return ourInstance;
    }

    public Call<LoginPojo> login(String ad, String sifre)
    {
        Call<LoginPojo> call = getRestApiClient().kontrol(ad,sifre);
        return call;
    }
    public Call<RegisterPojo> register(String ad, String soyad, String email, String parola)
    {
        Call<RegisterPojo> call = getRestApiClient().kayitOl(ad,soyad,email,parola);
        return call;
    }

    public Call<DogrulamaPojo> dogrula(String kadi, String kod)
    {
        Call<DogrulamaPojo> call = getRestApiClient().dogrulama(kadi,kod);
        return call;
    }

    public Call<MailGonderPojo> mailgonderme(String email)
    {
        Call<MailGonderPojo> call = getRestApiClient().mailgonder(email);
        return call;
    }

    public Call<IlanVermePojo> ilanverme(String kul_id,String kategori,String baslik, String aciklama,String fiyat,String netMetre,
                                         String brutMetre,String odaSayisi, String bulunduguKat,String katSayisi,String isitma,
                                         String banyoSayisi,String il,String ilce,String mahalle,String siteIcindemi)
    {
        Call<IlanVermePojo> call = getRestApiClient().ilanver( kul_id, kategori, baslik,  aciklama, fiyat, netMetre,
                 brutMetre, odaSayisi,  bulunduguKat, katSayisi, isitma,
                 banyoSayisi, il, ilce, mahalle, siteIcindemi);
        return call;
    }
    public Call<ResimEklePojo> resimYukleme(String kul_id, String ilan_id, String base64String)
    {
        Call<ResimEklePojo> call = getRestApiClient().resimYukle(kul_id,ilan_id,base64String);
        return call;
    }
    public Call<ResimEklePojo> defaultResimYukleme(String kul_id, String ilan_id, String base64String)
    {
        Call<ResimEklePojo> call = getRestApiClient().defaultResimYukle(kul_id,ilan_id,base64String);
        return call;
    }

    public Call<List<IlanlarimPojo>> ilanGetirme(String kul_id)
    {
        Call<List<IlanlarimPojo>> call = getRestApiClient().ilanGetir(kul_id);
        return call;
    }

    public Call<IlanGuncellePojo> ilanBilgiGetirme(String ilan_id)
    {
        Call<IlanGuncellePojo> call = getRestApiClient().ilanBilgiGetir(ilan_id);
        return call;
    }

    public Call<IlanSilPojo> ilanSilme(String ilan_id)
    {
        Call<IlanSilPojo> call = getRestApiClient().ilanSil(ilan_id);
        return call;
    }

    public Call<List<TumIlanlarPojo>> tumIlanlarGetir(Integer sayfa)
    {
        Call<List<TumIlanlarPojo>> call = getRestApiClient().tumIlanlar(sayfa);
        return call;
    }

    public Call<IlanDetayPojo> ilanDetayGetir(String ilan_id)
    {
        Call<IlanDetayPojo> call = getRestApiClient().ilanDetay(ilan_id);
        return call;
    }

    public Call<List<SliderResimPojo>> ilanSliderGetir(String ilan_id)
    {
        Call<List<SliderResimPojo>> call = getRestApiClient().ilanSlider(ilan_id);
        return call;
    }
    public Call<FavoriKontrolPojo> favoriKontrolGetir(String kul_id,String ilan_id)
    {
        Call<FavoriKontrolPojo> call = getRestApiClient().favoriKontrol(kul_id,ilan_id);
        return call;
    }
    public Call<FavoriIslemPojo> favoriIslemYap(String kul_id, String ilan_id)
    {
        Call<FavoriIslemPojo> call = getRestApiClient().favoriIslem(kul_id,ilan_id);
        return call;
    }
    public Call<List<FavoriIlanlarimPojo>> favoriIlanGetir(String kul_id)
    {
        Call<List<FavoriIlanlarimPojo>> call = getRestApiClient().favoriIlanlarim(kul_id);
        return call;
    }

    public Call<ProfilimPojo> profilGetir(String kul_id)
    {
        Call<ProfilimPojo> call = getRestApiClient().profilim(kul_id);
        return call;
    }
    public Call<SifreDegistirPojo> sifreDegistirme(String kul_id,String parola,String yenisifre)
    {
        Call<SifreDegistirPojo> call = getRestApiClient().sifreDegistir(kul_id,parola,yenisifre);
        return call;
    }
    public Call<ProfilGuncellePojo> profilGuncelleme(String kul_id, String ad, String soyad,String mail,String tel)
    {
        Call<ProfilGuncellePojo> call = getRestApiClient().profilGuncelle(kul_id,ad,soyad,mail,tel);
        return call;
    }
    public Call<IlanlarimGuncellemePojo> ilanGuncelleme(String ilan_id, String baslik, String aciklama, String fiyat, String netMetre,String brutMetre)
    {
        Call<IlanlarimGuncellemePojo> call = getRestApiClient().ilanGuncelle(ilan_id,baslik,aciklama,fiyat,netMetre,brutMetre);
        return call;
    }
    public Call<IlanTumResimSilPojo> tumResimSilme(String ilan_id)
    {
        Call<IlanTumResimSilPojo> call = getRestApiClient().tumResimSil(ilan_id);
        return call;
    }

    //SIRALAMA FONKSİYONLARI
    public Call<List<TumIlanlarPojo>> onceEnyuksekFiyatGetir()
    {
        Call<List<TumIlanlarPojo>> call = getRestApiClient().onceEnYuksekFiyat();
        return call;
    }
    public Call<List<TumIlanlarPojo>> onceEndusukFiyatGetir()
    {
        Call<List<TumIlanlarPojo>> call = getRestApiClient().onceEnDusukFiyat();
        return call;
    }
    public Call<List<TumIlanlarPojo>> onceEnYeni()
    {
        Call<List<TumIlanlarPojo>> call = getRestApiClient().onceEnYeni();
        return call;
    }
    public Call<List<TumIlanlarPojo>> onceEnEski()
    {
        Call<List<TumIlanlarPojo>> call = getRestApiClient().onceEneski();
        return call;
    }
    //İlan Filtreleme
    public Call<List<TumIlanlarPojo>> ilanFiltreleme(String kategori,String il,String ilce,String fiyat1,String fiyat2)
    {
        Call<List<TumIlanlarPojo>> call = getRestApiClient().ilanFiltreleme(kategori, il, ilce, fiyat1, fiyat2);
        return call;
    }
    //İlan Arama
    public Call<List<TumIlanlarPojo>> ilanArama(String text)
    {
        Call<List<TumIlanlarPojo>> call = getRestApiClient().ilanAra(text);
        return call;
    }

    ////Bildirim Mail Gönderme
    public Call<BildirimMailPojo> favoriMailGonderme(String kul_id,String other_id,String ilan_id)
    {
        Call<BildirimMailPojo> call = getRestApiClient().favoriMail(kul_id,other_id,ilan_id);
        return call;
    }
    //Add Token
    public Call<AddTokenPojo> addTokenManager(String kul_id, String token)
    {
        Call<AddTokenPojo> call = getRestApiClient().addTokenApi(kul_id,token);
        return call;
    }
    //Gerçek Bildirim Gönder
    public Call<BildirimGonderPojo> bildirimGonderManager(String kul_id,String gonderenId)
    {
        Call<BildirimGonderPojo> call = getRestApiClient().bildirimSend(kul_id,gonderenId);
        return call;
    }

}
