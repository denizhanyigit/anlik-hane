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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestApi {

    @FormUrlEncoded
    @POST("/login.php")
    Call<LoginPojo> kontrol(@Field("email") String ad, @Field("parola") String sifre);

    @FormUrlEncoded
    @POST("/signup.php")
    Call<RegisterPojo> kayitOl(@Field("ad") String ad, @Field("soyad") String soyad, @Field("email") String email, @Field("parola")
            String parola);

    @FormUrlEncoded
    @POST("/dogrulama.php")
    Call<DogrulamaPojo> dogrulama(@Field("kadi") String kadi, @Field("kod") String kod);

    @FormUrlEncoded
    @POST("/mailgonder.php")
    Call<MailGonderPojo> mailgonder(@Field("email") String email);

    @FormUrlEncoded
    @POST("/ilanver.php")
    Call<IlanVermePojo> ilanver(@Field("kul_id") String kul_id, @Field("kategori") String kategori, @Field("baslik") String baslik,
                                @Field("aciklama") String aciklama, @Field("fiyat") String fiyat, @Field("netMetre") String netMetre,
                                @Field("netBrut") String netBrut, @Field("odaSayisi") String odaSayisi, @Field("bulunduguKat") String bulunduguKat,
                                @Field("katSayisi") String katSayisi, @Field("isitma") String isitma, @Field("banyoSayisi") String banyoSayisi,
                                @Field("il") String il, @Field("ilce") String ilce, @Field("mahalle") String mahalle, @Field("siteIcindemi") String siteIcindemi);

    @FormUrlEncoded
    @POST("/ilanresimekle.php")
    Call<ResimEklePojo> resimYukle(@Field("kul_id") String kul_id, @Field("ilan_id") String ilan_id,
                                   @Field("resim") String base64String);

    //default
    @FormUrlEncoded
    @POST("/defresimekle.php")
    Call<ResimEklePojo> defaultResimYukle(@Field("kul_id") String kul_id, @Field("ilan_id") String ilan_id,
                                          @Field("resim") String base64String);


    @GET("/ilanlarim.php")
    Call<List<IlanlarimPojo>> ilanGetir(@Query("kul_id") String kul_id);

    @GET("/ilanguncelle.php")
    Call<IlanGuncellePojo> ilanBilgiGetir(@Query("ilan_id") String ilan_id);

    @GET("/ilansil.php")
    Call<IlanSilPojo> ilanSil(@Query("ilan_id") String ilan_id);

    @GET("/tumilanlar.php")
    Call<List<TumIlanlarPojo>> tumIlanlar(@Query("sayfa") Integer sayfa);

    @GET("/ilandetay.php")
    Call<IlanDetayPojo> ilanDetay(@Query("ilan_id") String ilan_id);

    @GET("/ilanresim.php")
    Call<List<SliderResimPojo>> ilanSlider(@Query("ilan_id") String ilan_id);

    @GET("/favori.php")
    Call<FavoriKontrolPojo> favoriKontrol(@Query("kul_id") String kul_id, @Query("ilan_id") String ilan_id);

    @GET("/favoriislem.php")
    Call<FavoriIslemPojo> favoriIslem(@Query("kul_id") String kul_id, @Query("ilan_id") String ilan_id);

    @GET("/favoriilanlistele.php")
    Call<List<FavoriIlanlarimPojo>> favoriIlanlarim(@Query("kul_id") String kul_id);

    @GET("/profilim.php")
    Call<ProfilimPojo> profilim(@Query("kul_id") String kul_id);

    //@FormUrlEncoded
    @GET("/sifre.php")
    Call<SifreDegistirPojo> sifreDegistir(@Query("kul_id") String kul_id, @Query("parola") String parola, @Query("yenisifre") String yenisifre);
    //@FormUrlEncoded

    @FormUrlEncoded
    @POST("/profilguncelle.php")
    Call<ProfilGuncellePojo> profilGuncelle(@Field("kul_id") String kul_id, @Field("ad") String ad, @Field("soyad") String soyad,
                                            @Field("mail") String mail, @Field("tel") String tel);

    @FormUrlEncoded
    @POST("/ilanlarimguncelle.php")
    Call<IlanlarimGuncellemePojo> ilanGuncelle(@Field("ilan_id") String ilan_id, @Field("baslik") String baslik, @Field("aciklama") String aciklama,
                                               @Field("fiyat") String fiyat, @Field("netMetre") String netMetre, @Field("brutMetre") String brutMetre);


    @GET("/ilantumresimsil.php")
    Call<IlanTumResimSilPojo> tumResimSil(@Query("ilan_id") String ilan_id);

    //SIRALAMA APİLERİ
    @GET("/ilanSirala/onceEnyuksekFiyat.php")
    Call<List<TumIlanlarPojo>> onceEnYuksekFiyat();

    @GET("/ilanSirala/onceEndusukFiyat.php")
    Call<List<TumIlanlarPojo>> onceEnDusukFiyat();

    @GET("/ilanSirala/onceEnyeni.php")
    Call<List<TumIlanlarPojo>> onceEnYeni();

    @GET("/ilanSirala/onceEneski.php")
    Call<List<TumIlanlarPojo>> onceEneski();
    //SIRALAMA BİTTİ

    //FİLTRELEME APİSİ
    @FormUrlEncoded
    @POST("/ilanSirala/ilanFiltrele.php")
    Call<List<TumIlanlarPojo>> ilanFiltreleme(@Field("kategori") String kategori, @Field("il") String il, @Field("ilce") String ilce,
                                               @Field("fiyat1") String fiyat1, @Field("fiyat2") String fiyat2);

    //İLAN ARAMA APİSİ
    @FormUrlEncoded
    @POST("/ilanSirala/ilanara.php")
    Call<List<TumIlanlarPojo>> ilanAra(@Field("baslik") String text);

   // Bildirim Mail API
    @GET("/favoriMail.php")
    Call<BildirimMailPojo> favoriMail(@Query("kul_id") String kul_id,@Query("other_id") String other_id,@Query("ilan_id")String ilan_id);



    // Add Token
    @GET("/bildirim/addToken.php")
    Call<AddTokenPojo> addTokenApi(@Query("kul_id") String kul_id, @Query("token") String token);

    // Gerçek Bildirim Gönder API
    @GET("/bildirim/bildirim.php")
    Call<BildirimGonderPojo> bildirimSend(@Query("kul_id") String kul_id,@Query("gonderen_id") String gonderenId);
}
