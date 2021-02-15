package com.dyapp.anindaev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dyapp.anindaev.Models.IlanVerPojo;

public class IlanEkleActivity1 extends AppCompatActivity {

    private String[] kategoriler = {"Kategori Seçiniz", "Daire", "Residence", "Müstakil Ev", "Villa", "Çiftlik Evi", "Köşk & Konak", "Yalı", "Yalı Dairesi", "Yazlık"};
    private Spinner spinnerKategoriler;
    private ArrayAdapter<String> dataAdapterForKategoriler;


    EditText etBaslik, etAciklama, etFiyat, etNet, etBrut;
    TextView btnIleri, btnGeri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_ekle1);
        tanimla();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnIleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kategoriSpinner = spinnerKategoriler.getSelectedItem().toString();
                if (!kategoriSpinner.equals("Kategori Seçiniz")) {
                    if (!etBaslik.getText().toString().equals("") || !etAciklama.getText().toString().equals("") ||
                            !etFiyat.getText().toString().equals("") ||
                            !etNet.getText().toString().equals("") || !etBrut.getText().toString().equals("")) {

                        IlanVerPojo.setBaslik(etBaslik.getText().toString());
                        IlanVerPojo.setAciklama(etAciklama.getText().toString());
                        IlanVerPojo.setFiyat(etFiyat.getText().toString());
                        IlanVerPojo.setNetMetre(etNet.getText().toString());
                        IlanVerPojo.setBrutMetre(etBrut.getText().toString());
                        IlanVerPojo.setKategori(spinnerKategoriler.getSelectedItem().toString());


                        Intent intent = new Intent(IlanEkleActivity1.this, IlanEkleActivity2.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Tüm alanları doldurunuz.", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IlanEkleActivity1.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in_ters, R.anim.anim_out_ters);

            }
        });

    }

    public void tanimla() {

        spinnerKategoriler = (Spinner) findViewById(R.id.kategoriSpinner);
        dataAdapterForKategoriler = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kategoriler);
        dataAdapterForKategoriler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategoriler.setAdapter(dataAdapterForKategoriler);

        etBaslik = (EditText) findViewById(R.id.txtIlanBasligi);
        etAciklama = (EditText) findViewById(R.id.txtAciklama);
        etFiyat = (EditText) findViewById(R.id.txtFiyat);
        etNet = (EditText) findViewById(R.id.txtNet);
        etBrut = (EditText) findViewById(R.id.txtBrut);

        etBaslik.setText(IlanVerPojo.getBaslik());
        etAciklama.setText(IlanVerPojo.getAciklama());
        etFiyat.setText(IlanVerPojo.getFiyat());
        etNet.setText(IlanVerPojo.getNetMetre());
        etBrut.setText(IlanVerPojo.getBrutMetre());
       /* String kategori = spinnerKategoriler.getSelectedItem().toString();
        spinnerKategoriler.getSelectedItem(IlanVerPojo.getKategori());*/

        btnIleri = (TextView) findViewById(R.id.btnIleri);
        btnGeri = (TextView) findViewById(R.id.btnGeri);


    }
}