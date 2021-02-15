package com.dyapp.anindaev;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dyapp.anindaev.Adapters.SliderAdapter;
import com.dyapp.anindaev.Models.IlanTumResimSilPojo;
import com.dyapp.anindaev.Models.ResimEklePojo;
import com.dyapp.anindaev.Models.SliderResimPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IlanResimGuncelleActivity extends AppCompatActivity {

    ImageView secilenIlanImageView;
    TextView resimSil, resimSec, resimYukle;
    Bitmap bitmap;
    List<SliderResimPojo> list;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    String kul_id, ilan_id, image;
    LinearLayout linear;
    TextView secimiKaldir,bilgilendirme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_resim_guncelle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        tanimla();
        ilan_id = bundle.getString("ilan_id");
        kul_id = bundle.getString("kul_id");
        secilenIlanImageView.setVisibility(View.GONE);
        secimiKaldir=(TextView) findViewById(R.id.secimiKaldir);
        bilgilendirme=(TextView) findViewById(R.id.bilgilendirme);
        resimGetir();

        resimSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ilanlarimAlertDialog();

            }
        });

        resimSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimGoster();

            }
        });

        resimYukle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resmiYukle();
                resimSec.setVisibility(View.VISIBLE);
                resimYukle.setVisibility(View.GONE);
                linear.setVisibility(View.GONE);
                secilenIlanImageView.setVisibility(View.GONE);
                resimGetir();


            }
        });

        secimiKaldir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"İptal edildi.",Toast.LENGTH_LONG).show();
                secimiKaldir.setVisibility(View.GONE);
                resimYukle.setVisibility(View.GONE);
                resimSec.setVisibility(View.VISIBLE);
                secilenIlanImageView.setVisibility(View.GONE);
                resimSil.setVisibility(View.VISIBLE);

            }
        });



    }
    public void ilanlarimAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.resimler_sil, null);
        TextView sil, iptal;

        sil = (TextView) view.findViewById(R.id.sil);
        iptal = (TextView) view.findViewById(R.id.iptal);

        AlertDialog.Builder alert = new AlertDialog.Builder(IlanResimGuncelleActivity.this);
        alert.setView(view);
        alert.setCancelable(false);
        final AlertDialog dialog = alert.create();
        dialog.show();
        iptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                tumResimSil();
                resimGetir();
                bilgilendirme.setVisibility(View.GONE);
               /* Intent intent = new Intent(IlanResimGuncelleActivity.this, IlanResimGuncelleActivity.class);
                startActivity(intent);
                finish();*/
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
    public void tumResimSil()
    {
        final ProgressDialog progressDialog = new ProgressDialog(IlanResimGuncelleActivity.this);
        progressDialog.setMessage("Fotoğraflar siliniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<IlanTumResimSilPojo> x = ManagerAll.getInstance().tumResimSilme(ilan_id);
        x.enqueue(new Callback<IlanTumResimSilPojo>() {
            @Override
            public void onResponse(Call<IlanTumResimSilPojo> call, Response<IlanTumResimSilPojo> response) {
                if(response.body().getResult().equals("Silindi."))
                {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(),"Tüm fotoğraflar silindi.",Toast.LENGTH_LONG).show();
                    resimGetir();
                }
                else{
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(),"Fotoğraflar silinemedi.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<IlanTumResimSilPojo> call, Throwable t) {

            }
        });
    }

    public void resmiYukle() {
        final ProgressDialog progressDialog = new ProgressDialog(IlanResimGuncelleActivity.this);
        progressDialog.setMessage("Resim yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        image = imageToString();
        Call<ResimEklePojo> x = ManagerAll.getInstance().resimYukleme(kul_id, ilan_id, image);
        x.enqueue(new Callback<ResimEklePojo>() {
            @Override
            public void onResponse(Call<ResimEklePojo> call, Response<ResimEklePojo> response) {
                if (response.body().isTf()) {
                    progressDialog.cancel();
                    secilenIlanImageView.setVisibility(View.GONE);
                    resimGetir();
                    Toast.makeText(getApplicationContext(), "Fotoğraf yüklendi.", Toast.LENGTH_LONG).show();
                    secimiKaldir.setVisibility(View.GONE);
                    resimSil.setVisibility(View.VISIBLE);
                    linear.setVisibility(View.VISIBLE);
                    secilenIlanImageView.setVisibility(View.GONE);
                    bilgilendirme.setVisibility(View.VISIBLE);
                    bilgilendirme.setText("Yüklediğiniz fotoğraf sola dönmüş bir şekilde yüklendiyse telefonunuzu yan tutarak fotoğrafı yeniden çekin ve tekrar yükleyin.");
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

    public String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byt = byteArrayOutputStream.toByteArray();
        String imageToString = Base64.encodeToString(byt, Base64.NO_WRAP);
        return imageToString;
    }

    public void resimGoster() // galeriyi aç
    {

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Bir Fotoğraf Seçin"), 777);
       /* Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 777);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 777 && resultCode == RESULT_OK ) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                secilenIlanImageView.setImageBitmap(bitmap);
                linear.setVisibility(View.VISIBLE);
                secilenIlanImageView.setVisibility(View.VISIBLE);
                resimSec.setVisibility(View.GONE);
                resimYukle.setVisibility(View.VISIBLE);
                linear.setVisibility(View.VISIBLE);
                secimiKaldir.setVisibility(View.VISIBLE);
                resimSil.setVisibility(View.GONE);
                Toast.makeText(getApplication(),"Fotoğraf seçildi.",Toast.LENGTH_LONG).show();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void tanimla() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        circleIndicator = (CircleIndicator) findViewById(R.id.sliderNokta);

        resimSil = (TextView) findViewById(R.id.resimSil);
        resimSec = (TextView) findViewById(R.id.resimSec);
        resimYukle = (TextView) findViewById(R.id.resimYukle);
        secilenIlanImageView = (ImageView) findViewById(R.id.secilenIlanImageView);
        linear=(LinearLayout) findViewById(R.id.linear);
    }
}