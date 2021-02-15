package com.dyapp.anindaev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dyapp.anindaev.Models.IlanVerPojo;

import org.w3c.dom.Text;

public class IlanEkleActivity2 extends AppCompatActivity {
    private String[] odaSayisi = {"Oda Sayısı", "Stüdyo (1+0)", "1+1", "2+0", "2+1", "2+2", "3+1", "3+2", "4+1", "4+2", "4+3", "4+4", "5+1", "5+2", "5+3",
            "5+4", "6+1", "6+2", "6+3", "7+1", "7+2", "7+3", "8+1", "8+2", "8+3", "8+4", "9+1", "9+2", "9+3", "9+4", "9+5", "9+6", "10+1", "10+2",
            "10 ve üzeri"};
    private Spinner spinnerOdaSayisi;
    private ArrayAdapter<String> dataAdapterForOdaSayisi;

    private String[] bulunduguKat = {"Bulunduğu Kat", "Kot 4", "Kot 3", "Kot 2", "Kot 1", "Bodrum Kat", "Zemin Kat", "Bahçe Katı", "Giriş Katı",
            "Yüksek Giriş", "Müstakil", "Villa Tipi", "Çatı Katı", "1", "2", "3", "4", "5", "6", "7"};
    private Spinner spinnerBulunduguKatSayisi;
    private ArrayAdapter<String> dataAdapterForBulunduguKatSayisi;

    private String[] katSayisi = {"Kat Sayısı", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
            "22", "23", "24", "25", "26", "27", "28", "29", "30 ve üzeri"};
    private Spinner spinnerKatSayisi;
    private ArrayAdapter<String> dataAdapterForKatSayisi;

    private String[] isitma = {"Isıtma", "Yok", "Soba", "Doğalgaz Sobası", "Kat Kaloriferi", "Merkezi", "Merkezi(Pay Ölçer)", "Doğalgaz(Kombi)",
            "Yerden Isıtma", "Klima", "Fancoil Ünitesi", "Güneş Enerjisi", "Jeotermal", "Şömine", "VRV", "Isı Pompası"};
    private Spinner spinnerIsitma;
    private ArrayAdapter<String> dataAdapterForIsitma;

    private String[] banyoSayisi = {"Banyo Sayısı", "1", "2", "3", "4", "5", "6", "6 üzeri"};
    private Spinner spinnerBanyoSayisi;
    private ArrayAdapter<String> dataAdapterForBanyoSayisi;

    TextView btnIleri, btnGeri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_ekle2);
        tanimla();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IlanEkleActivity2.this, IlanEkleActivity1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in_ters, R.anim.anim_out_ters);
            }
        });

        btnIleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String odasayisi = spinnerOdaSayisi.getSelectedItem().toString();
                String bulundugukat = spinnerBulunduguKatSayisi.getSelectedItem().toString();
                String katsayisi = spinnerKatSayisi.getSelectedItem().toString();
                String isitma = spinnerIsitma.getSelectedItem().toString();
                String banyosayisi = spinnerBanyoSayisi.getSelectedItem().toString();
                if (odasayisi.equals("Oda Sayısı") || bulundugukat.equals("Bulunduğu Kat") || katsayisi.equals("Kat Sayısı")
                        || isitma.equals("Isıtma") || banyosayisi.equals("Banyo Sayısı"))
                {
                    Toast.makeText(getApplicationContext(), "Lütfen tüm seçimleri yapınız.", Toast.LENGTH_LONG).show();
                } else {

                    IlanVerPojo.setOdaSayisi(spinnerOdaSayisi.getSelectedItem().toString());
                    IlanVerPojo.setBulunduguKat(spinnerBulunduguKatSayisi.getSelectedItem().toString());
                    IlanVerPojo.setKatSayisi(spinnerKatSayisi.getSelectedItem().toString());
                    IlanVerPojo.setIsitma(spinnerIsitma.getSelectedItem().toString());
                    IlanVerPojo.setBanyoSayisi(spinnerBanyoSayisi.getSelectedItem().toString());
                    Intent intent = new Intent(IlanEkleActivity2.this, IlanEkleActivity3.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            }
        });
    }

    public void tanimla() {
        btnIleri = (TextView) findViewById(R.id.btnIleri);
        btnGeri = (TextView) findViewById(R.id.btnGeri);

        spinnerOdaSayisi = (Spinner) findViewById(R.id.odaSpinner);
        dataAdapterForOdaSayisi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, odaSayisi);
        dataAdapterForOdaSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOdaSayisi.setAdapter(dataAdapterForOdaSayisi);

        spinnerBulunduguKatSayisi = (Spinner) findViewById(R.id.bulunduguKatSpinner);
        dataAdapterForBulunduguKatSayisi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bulunduguKat);
        dataAdapterForBulunduguKatSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBulunduguKatSayisi.setAdapter(dataAdapterForBulunduguKatSayisi);

        spinnerKatSayisi = (Spinner) findViewById(R.id.katSayisiSpinner);
        dataAdapterForKatSayisi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, katSayisi);
        dataAdapterForKatSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKatSayisi.setAdapter(dataAdapterForKatSayisi);

        spinnerIsitma = (Spinner) findViewById(R.id.isitmaSpinner);
        dataAdapterForIsitma = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, isitma);
        dataAdapterForIsitma.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIsitma.setAdapter(dataAdapterForIsitma);

        spinnerBanyoSayisi = (Spinner) findViewById(R.id.banyoSpinner);
        dataAdapterForBanyoSayisi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, banyoSayisi);
        dataAdapterForBanyoSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBanyoSayisi.setAdapter(dataAdapterForBanyoSayisi);

      /*  spinnerOdaSayisi.setSelection(IlanVerPojo.getOdaSayisi());
        spinnerBulunduguKatSayisi.setSelection(IlanVerPojo.getBulunduguKat());
        spinnerKatSayisi.setSelection(IlanVerPojo.getKatSayisi());
        spinnerIsitma.setSelection(IlanVerPojo.getIsitma());
        spinnerBanyoSayisi.setSelection(IlanVerPojo.getBanyoSayisi());*/
    }
}