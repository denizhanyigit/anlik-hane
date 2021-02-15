package com.dyapp.anindaev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dyapp.anindaev.Models.LoginPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GirisActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView tvKayitSayfasi, tvSifreUnuttum;
    EditText kad, parola;
    Button btnGiris;
    String hazirMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        tanimla();
        kad.setText(Name.getMail());

        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        if (sharedPreferences.getString("uye_id", null) != null &&
                sharedPreferences.getString("uye_mail", null) != null && sharedPreferences.getString("uye_ad", null) != null) {
            Intent intent = new Intent(GirisActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ad = kad.getText().toString();
                String sifre = parola.getText().toString();
                if (!ad.equals("") && !sifre.equals("")) {
                    if(sifre.trim().length()>5) {
                        login(ad, sifre);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Şifreniz en az 6 karakter olmalı", Toast.LENGTH_LONG).show();
                    }

                } else if (ad.equals("") || sifre.equals("")) {
                    Toast.makeText(getApplicationContext(), "Boş alanları doldurunuz.", Toast.LENGTH_LONG).show();
                }
            }
        });
        tvKayitSayfasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GirisActivity.this, KayitOlActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvSifreUnuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GirisActivity.this, SifremiUnuttumActivity.class);
                startActivity(intent);
            }
        });

    }

    public void login(String ad, String sifre) {
        final ProgressDialog progressDialog = new ProgressDialog(GirisActivity.this);
        progressDialog.setMessage("Giriş yapılıyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<LoginPojo> x = ManagerAll.getInstance().login(ad, sifre);
        x.enqueue(new Callback<LoginPojo>() {
            @Override
            public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {

                if (response.isSuccessful()) {
                    if (String.valueOf(response.body().getId()) != null && response.body().getMail() != null) {
                        String uyeId = String.valueOf(response.body().getId());
                        String mail = response.body().getMail();
                        String ad = response.body().getAd();
                        String soyad = response.body().getSoyad();
                        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
                        editor = sharedPreferences.edit();
                        editor.putString("uye_id", uyeId);
                        editor.putString("uye_mail", mail);
                        editor.putString("uye_ad", ad);
                        editor.putString("uye_soyad", soyad);
                        editor.commit();
                        Name.setMail("");
                        kad.setText("");
                        parola.setText("");
                        progressDialog.cancel();
                        Intent intent = new Intent(GirisActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        progressDialog.cancel();
                        toast();

                    }
                }

            }

            @Override
            public void onFailure(Call<LoginPojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void tanimla() {
        tvKayitSayfasi = (TextView) findViewById(R.id.kayitSayfasi);
        kad = (EditText) findViewById(R.id.email);
        parola = (EditText) findViewById(R.id.parola);
        btnGiris = (Button) findViewById(R.id.btnGiris);
        tvSifreUnuttum = (TextView) findViewById(R.id.txtSifreUnuttum);
    }

    public void toast() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.custom_toast_container));
        Toast toast = new Toast(GirisActivity.this);
        // toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }


}