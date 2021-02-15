package com.dyapp.anindaev;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dyapp.anindaev.Adapters.SliderAdapter;
import com.dyapp.anindaev.Models.IlanGuncellePojo;
import com.dyapp.anindaev.Models.IlanSilPojo;
import com.dyapp.anindaev.Models.IlanTumResimSilPojo;
import com.dyapp.anindaev.Models.IlanlarimGuncellemePojo;
import com.dyapp.anindaev.Models.ResimEklePojo;
import com.dyapp.anindaev.Models.SliderResimPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IlanlarimGuncelle extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView  guncelle;
    EditText ilanBaslik, ilanAciklama, ilanFiyat, ilanNet, ilanBrut;
    String ilan_id, kul_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilanlarim_guncelle1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tanimla();
        Bundle bundle = getIntent().getExtras();
        ilan_id = bundle.getString("ilan_id");
        BilgiGetir();
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        kul_id = sharedPreferences.getString("uye_id", null);


        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String baslik = ilanBaslik.getText().toString();
                String aciklama = ilanAciklama.getText().toString();
                String fiyat = ilanFiyat.getText().toString();
                String netMetre = ilanNet.getText().toString();
                String brutMetre = ilanBrut.getText().toString();
                Log.i("sonuc", ilan_id + " " + baslik + " " + aciklama + " " + fiyat + " " + netMetre + " " + brutMetre);

                if (baslik.equals("") || aciklama.equals("") || fiyat.equals("") || netMetre.equals("") || brutMetre.equals("")) {
                    Toast.makeText(getApplicationContext(), "Tüm alanları doldurunuz.", Toast.LENGTH_LONG).show();
                } else {
                    guncelleYap(ilan_id, baslik, aciklama, fiyat, netMetre, brutMetre);
                    BilgiGetir();
                }

            }
        });
    }

    public void BilgiGetir() {
        final ProgressDialog progressDialog = new ProgressDialog(IlanlarimGuncelle.this);
        progressDialog.setMessage("İlan bilgileriniz yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<IlanGuncellePojo> x = ManagerAll.getInstance().ilanBilgiGetirme(ilan_id);
        x.enqueue(new Callback<IlanGuncellePojo>() {
            @Override
            public void onResponse(Call<IlanGuncellePojo> call, Response<IlanGuncellePojo> response) {

                if (response.body().isTf()) {
                    ilanBaslik.setText(response.body().getBaslik());
                    ilanAciklama.setText(response.body().getAciklama());
                    ilanFiyat.setText(response.body().getFiyat());
                    ilanNet.setText(response.body().getNetMetre());
                    ilanBrut.setText(response.body().getBrutMetre());
                    progressDialog.cancel();
                } else {
                    Toast.makeText(getApplicationContext(), "İlan bilgileriniz yüklenemedi.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<IlanGuncellePojo> call, Throwable t) {

            }
        });
    }

    public void ilanlarimAlertDialog(Activity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_layout, null);
        TextView sil, iptal;

        sil = (TextView) view.findViewById(R.id.sil);
        iptal = (TextView) view.findViewById(R.id.iptal);


        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
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
                //  ilanSil(ilan_id);
                Intent intent = new Intent(IlanlarimGuncelle.this, IlanlarimActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void guncelleYap(String ilan_id, String baslik, String aciklama, String fiyat, String netMetre, String brutMetre) {
        final ProgressDialog progressDialog = new ProgressDialog(IlanlarimGuncelle.this);
        progressDialog.setMessage("İlanınız güncelleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<IlanlarimGuncellemePojo> x = ManagerAll.getInstance().ilanGuncelleme(ilan_id, baslik, aciklama, fiyat, netMetre, brutMetre);
        x.enqueue(new Callback<IlanlarimGuncellemePojo>() {
            @Override
            public void onResponse(Call<IlanlarimGuncellemePojo> call, Response<IlanlarimGuncellemePojo> response) {
                if (response.body().getSonuc().equals("Guncellendi")) {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(), "İlanınız güncellendi.", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(), "İlanınız güncellenemedi.", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<IlanlarimGuncellemePojo> call, Throwable t) {

            }
        });

    }


    public void tanimla() {
        ilanBaslik = (EditText) findViewById(R.id.txtIlanBasligi);
        ilanAciklama = (EditText) findViewById(R.id.txtAciklama);
        ilanFiyat = (EditText) findViewById(R.id.txtFiyat);
        ilanNet = (EditText) findViewById(R.id.txtNet);
        ilanBrut = (EditText) findViewById(R.id.txtBrut);


        guncelle = (TextView) findViewById(R.id.btnGuncelle);
    }
}