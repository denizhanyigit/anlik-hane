package com.dyapp.anindaev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.dyapp.anindaev.Adapters.SliderAdapter;
import com.dyapp.anindaev.Models.FavoriIslemPojo;
import com.dyapp.anindaev.Models.FavoriKontrolPojo;
import com.dyapp.anindaev.Models.IlanDetayPojo;
import com.dyapp.anindaev.Models.SliderResimPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;

import org.w3c.dom.Text;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IlanDetayKisiTumIlanlariActivity extends AppCompatActivity {

    ViewPager viewPager;
    TextView ilanBaslik, ilanAciklama;
    EditText ilanSahibiAdSoyad, ilanFiyat, ilanTarih, ilanKategori, ilanBrutMetre, ilanNetMetre, ilanOdaSayisi, ilanBulunduguKat, ilanKatSayisi,
            ilanIsitma, ilanBanyoSayisi, ilanIl, ilanIlce, ilanMahalle, ilanSiteIcindemi;
    LinearLayout aciklamaLayout, layoutDetay;
    TextView aciklamaTxt, bilgilerText;
    ScrollView scrollView;
    String ilan_id;
    List<SliderResimPojo> list;
    CircleIndicator circleIndicator;
    Toolbar toolbar;
    ImageView favori;
    SharedPreferences sharedPreferences;
    String kul_id, otherID, kisiAd, ilanBilgi;
    Button btnAra, btnMesajGonder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_detay_kisi_tum_ilanlari);
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        kul_id = sharedPreferences.getString("uye_id", null);
        Bundle bundle = getIntent().getExtras();
        ilan_id = bundle.getString("ilan_id");
        tanimla();
        detaylariGetir(ilan_id);
        resimGetir();
        favoriKontrol();
        action();
        String txtttt = bilgilerText.getText().toString();
        SpannableString spanStringgggg = new SpannableString(txtttt);
        spanStringgggg.setSpan(new StyleSpan(Typeface.BOLD), 0, spanStringgggg.length(), 0);
        bilgilerText.setText(spanStringgggg);

        aciklamaTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutDetay.setVisibility(View.GONE);
                aciklamaLayout.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                String txt = aciklamaTxt.getText().toString();
                SpannableString spanString = new SpannableString(txt);
                spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                aciklamaTxt.setText(spanString);
                String txt2 = bilgilerText.getText().toString();
                SpannableString spanStringYenii = new SpannableString(txt2);
                bilgilerText.setText(spanStringYenii);
            }
        });

        bilgilerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutDetay.setVisibility(View.VISIBLE);
                aciklamaLayout.setVisibility(View.GONE);
                scrollView.setVisibility(View.GONE);
                String txt2 = bilgilerText.getText().toString();
                String txt = aciklamaTxt.getText().toString();
                SpannableString spanString = new SpannableString(txt2);
                spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                bilgilerText.setText(spanString);

                SpannableString spanStringYeni = new SpannableString(txt);
                aciklamaTxt.setText(spanStringYeni);

            }
        });


        btnMesajGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kul_id.equals(otherID)) {
                    Toast.makeText(getApplicationContext(), "İlan sahibi zaten sizsiniz.", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent = new Intent(IlanDetayKisiTumIlanlariActivity.this, ChatActivity.class);
                    intent.putExtra("otherID", otherID);
                    intent.putExtra("kisiAd", kisiAd);
                    intent.putExtra("ilan_id", ilan_id);
                    intent.putExtra("ilanBilgi", ilanBilgi);
                    startActivity(intent);
                }
            }
        });


    }


    public void detaylariGetir(String ilan_id) {
        final ProgressDialog progressDialog = new ProgressDialog(IlanDetayKisiTumIlanlariActivity.this);
        progressDialog.setMessage("İlan bilgileri yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<IlanDetayPojo> x = ManagerAll.getInstance().ilanDetayGetir(ilan_id);
        x.enqueue(new Callback<IlanDetayPojo>() {
            @Override
            public void onResponse(Call<IlanDetayPojo> call, final Response<IlanDetayPojo> response) {
                if (response.isSuccessful()) {

                    ilanBaslik.setText(response.body().getBaslik());
                    ilanSahibiAdSoyad.setText(response.body().getKulad() + " " + response.body().getKulsoyad());
                    ilanFiyat.setText(response.body().getFiyat() + "₺");
                    ilanTarih.setText(response.body().getTarih());
                    ilanKategori.setText(response.body().getKategori());
                    ilanBrutMetre.setText(response.body().getBrutMetre());
                    ilanNetMetre.setText(response.body().getNetMetre());
                    ilanOdaSayisi.setText(response.body().getOdaSayisi());
                    ilanBulunduguKat.setText(response.body().getBulunduguKat());
                    ilanKatSayisi.setText(response.body().getKatSayisi());
                    ilanIsitma.setText(response.body().getIsitma());
                    ilanBanyoSayisi.setText(response.body().getBanyoSayisi());
                    ilanIl.setText(response.body().getIl());
                    ilanIlce.setText(response.body().getIlce());
                    ilanMahalle.setText(response.body().getMahalle());
                    ilanSiteIcindemi.setText(response.body().getSiteIcindemi());
                    ilanAciklama.setText(response.body().getAciklama());

                    ilanBilgi = response.body().getBaslik();
                    otherID = response.body().getKulid();
                    kisiAd = response.body().getKulad() + " " + response.body().getKulsoyad();
                    // otherID =response.body().getKulid();
                    progressDialog.cancel();

                    btnAra.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String telno = response.body().getKultelno();
                            if (!telno.equals("")) {
                                String uri = "tel:" + telno.trim();
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse(uri));
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "İlan sahibi telefon bilgisini paylaşmamış.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<IlanDetayPojo> call, Throwable t) {

            }
        });
    }

    public void resimGetir() {
        Call<List<SliderResimPojo>> x = ManagerAll.getInstance().ilanSliderGetir(ilan_id);
        x.enqueue(new Callback<List<SliderResimPojo>>() {
            @Override
            public void onResponse(Call<List<SliderResimPojo>> call, Response<List<SliderResimPojo>> response) {
                list = response.body();
                SliderAdapter sliderAdapter = new SliderAdapter(list, getApplicationContext());
                viewPager.setAdapter(sliderAdapter);
                circleIndicator.setViewPager(viewPager);
                circleIndicator.bringToFront();
            }

            @Override
            public void onFailure(Call<List<SliderResimPojo>> call, Throwable t) {

            }
        });
    }

    public void favoriKontrol() {
        Call<FavoriKontrolPojo> x = ManagerAll.getInstance().favoriKontrolGetir(kul_id, ilan_id);
        x.enqueue(new Callback<FavoriKontrolPojo>() {
            @Override
            public void onResponse(Call<FavoriKontrolPojo> call, Response<FavoriKontrolPojo> response) {
                if (response.body().getText().equals("Favoriden cikar.")) {
                    favori.setImageResource(R.drawable.favoriselectedd);
                } else if (response.body().getText().equals("Favoriye ekle.")) {
                    favori.setImageResource(R.drawable.favoridefaultyeni);
                }
            }

            @Override
            public void onFailure(Call<FavoriKontrolPojo> call, Throwable t) {

            }
        });
    }

    public void action() {
        favori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kul_id.equals(otherID)) {
                    Toast.makeText(getApplicationContext(), "Kendi ilanınızı favorilere ekleyemezsiniz.", Toast.LENGTH_LONG).show();
                } else {
                    Call<FavoriIslemPojo> x = ManagerAll.getInstance().favoriIslemYap(kul_id, ilan_id);
                    x.enqueue(new Callback<FavoriIslemPojo>() {
                        @Override
                        public void onResponse(Call<FavoriIslemPojo> call, Response<FavoriIslemPojo> response) {
                            if (response.body().getText().equals("Favoriden cikartildi.")) {
                                Toast.makeText(getApplicationContext(), "İlan favorilerinizden çıkartıldı.", Toast.LENGTH_LONG).show();
                                favoriKontrol();
                            } else if (response.body().getText().equals("Favoriye eklendi.")) {
                                Toast.makeText(getApplicationContext(), "İlan favorilerinize eklendi.", Toast.LENGTH_LONG).show();
                                favoriKontrol();
                            }
                        }

                        @Override
                        public void onFailure(Call<FavoriIslemPojo> call, Throwable t) {

                        }
                    });
                }
            }
        });

    }

    public void tanimla() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        TextView toolbarTitle = findViewById(R.id.titleText);
        favori = (ImageView) findViewById(R.id.favoriIcon);

        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       /* toolbar.setTitle("");
        toolbarTitle.setText("Saasas");
        toolbarCounter.setText("1");
        setSupportActionBar(toolbar);*/

        ilanBaslik = (TextView) findViewById(R.id.ilanBaslik);
        ilanSahibiAdSoyad = (EditText) findViewById(R.id.ilanSahibiAdSoyad);
        ilanFiyat = (EditText) findViewById(R.id.ilanFiyat);
        ilanTarih = (EditText) findViewById(R.id.ilanTarih);
        ilanKategori = (EditText) findViewById(R.id.ilanKategori);
        ilanBrutMetre = (EditText) findViewById(R.id.ilanBrutMetre);
        ilanNetMetre = (EditText) findViewById(R.id.ilanNetMetre);
        ilanOdaSayisi = (EditText) findViewById(R.id.ilanOdaSayisi);
        ilanBulunduguKat = (EditText) findViewById(R.id.ilanBulunduguKat);
        ilanKatSayisi = (EditText) findViewById(R.id.ilanKatSayisi);
        ilanIsitma = (EditText) findViewById(R.id.ilanIsitma);
        ilanBanyoSayisi = (EditText) findViewById(R.id.ilanBanyoSayisi);
        ilanIl = (EditText) findViewById(R.id.ilanIl);
        ilanIlce = (EditText) findViewById(R.id.ilanIlce);
        ilanMahalle = (EditText) findViewById(R.id.ilanMahalle);
        ilanSiteIcindemi = (EditText) findViewById(R.id.ilanSiteIcindemi);
        ilanAciklama = (TextView) findViewById(R.id.ilanAciklama);

        btnAra = (Button) findViewById(R.id.btnAra);
        btnMesajGonder = (Button) findViewById(R.id.btnMesajGonder);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        circleIndicator = (CircleIndicator) findViewById(R.id.sliderNokta);
        aciklamaLayout = (LinearLayout) findViewById(R.id.aciklamaLayout);
        layoutDetay = (LinearLayout) findViewById(R.id.layoutDetay);
        aciklamaTxt = (TextView) findViewById(R.id.aciklamaTxt);
        bilgilerText = (TextView) findViewById(R.id.bilgiler);
        scrollView = (ScrollView) findViewById(R.id.scrollview2);
    }
}