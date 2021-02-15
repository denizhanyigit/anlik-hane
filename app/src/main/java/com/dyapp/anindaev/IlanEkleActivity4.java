package com.dyapp.anindaev;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dyapp.anindaev.Models.DefaultResimPojo;
import com.dyapp.anindaev.Models.IlanVerPojo;
import com.dyapp.anindaev.Models.IlanVermePojo;
import com.dyapp.anindaev.Models.ResimEklePojo;
import com.dyapp.anindaev.RestApi.ManagerAll;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IlanEkleActivity4 extends AppCompatActivity {

    TextView btnResimSec, btnResimYukle, btnCik;
    ImageView secilenIlanImageView;
    Bitmap bitmap;
    String kul_id, ilan_id, image;
    TextView anasayfaDon, bilgilendirme, secimikaldir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_ekle4);
        tanimla();
        Bundle bundle = getIntent().getExtras();
        kul_id = String.valueOf(bundle.getInt("kul_id"));
        ilan_id = String.valueOf(bundle.getInt("ilan_id"));
        defaultResimEkle();
        final Dialog d = new Dialog(IlanEkleActivity4.this);
        d.setContentView(R.layout.ilan_yayinlandi);
        d.setCancelable(false);
        d.setTitle("Bilgi");
        d.show();
        Thread gecis;
        gecis = new Thread() {
            @Override
            public void run() {
                try {

                    synchronized (this) {
                        wait(3500);
                    }
                } catch (InterruptedException ex) {

                } finally {
                    d.cancel();
                }

            }
        };
        gecis.start();

        btnResimSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimGoster();
            }
        });

        btnResimYukle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resmiYukle();
                btnResimSec.setVisibility(View.VISIBLE);
                btnResimYukle.setVisibility(View.GONE);

            }
        });

        anasayfaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IlanEkleActivity4.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        secimikaldir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "İptal edildi.", Toast.LENGTH_LONG).show();
                secimikaldir.setVisibility(View.GONE);
                btnResimYukle.setVisibility(View.GONE);
                btnResimSec.setVisibility(View.VISIBLE);
                secilenIlanImageView.setImageResource(R.drawable.noimage);
            }
        });

    }

    public void defaultResimEkle() {
        Call<ResimEklePojo> x = ManagerAll.getInstance().defaultResimYukleme(kul_id, ilan_id, image);
        x.enqueue(new Callback<ResimEklePojo>() {
            @Override
            public void onResponse(Call<ResimEklePojo> call, Response<ResimEklePojo> response) {

            }

            @Override
            public void onFailure(Call<ResimEklePojo> call, Throwable t) {

            }
        });
    }

    public void resmiYukle() {
        final ProgressDialog progressDialog = new ProgressDialog(IlanEkleActivity4.this);
        progressDialog.setMessage("Fotoğraf yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        image = imageToString();
        Call<ResimEklePojo> x = ManagerAll.getInstance().resimYukleme(kul_id, ilan_id, image);
        x.enqueue(new Callback<ResimEklePojo>() {
            @Override
            public void onResponse(Call<ResimEklePojo> call, Response<ResimEklePojo> response) {
                if (response.body().isTf()) {
                    progressDialog.cancel();
                    secilenIlanImageView.setImageResource(R.drawable.noimage);
                    Toast.makeText(getApplicationContext(), "Fotoğraf yüklendi. Fotoğraf Seç butonuna basarak ilanınıza daha fazla fotoğraf " +
                            "yükleyebilirsiniz.", Toast.LENGTH_LONG).show();
                    secimikaldir.setVisibility(View.GONE);

                } else {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(), "Fotoğraf yüklenemedi.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResimEklePojo> call, Throwable t) {

            }
        });

    }

    public void tanimla() {
        btnResimSec = (TextView) findViewById(R.id.resimSec);
        btnResimYukle = (TextView) findViewById(R.id.resimYukle);
        // btnCik = (Button) findViewById(R.id.resimCik);
        secilenIlanImageView = (ImageView) findViewById(R.id.secilenIlanImageView);
        anasayfaDon = (TextView) findViewById(R.id.anasayfaDon);
        bilgilendirme = (TextView) findViewById(R.id.bilgilendirme);
        secimikaldir = (TextView) findViewById(R.id.secimiKaldir);

    }

    public void resimGoster() // galeriyi aç
    {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Bir Fotoğraf Seçin"), 777);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777 && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                secilenIlanImageView.setImageBitmap(bitmap);
                btnResimSec.setVisibility(View.GONE);
                btnResimYukle.setVisibility(View.VISIBLE);
                secimikaldir.setVisibility(View.VISIBLE);
                bilgilendirme.setVisibility(View.VISIBLE);
                bilgilendirme.setText("Yüklediğiniz resim sola dönmüş bir şekilde yüklendiyse telefonunuzu yan tutarak fotoğrafı yeniden çekin ve tekrar yükleyin.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byt = byteArrayOutputStream.toByteArray();
        String imageToString = Base64.encodeToString(byt, Base64.NO_WRAP);
        return imageToString;
    }

}