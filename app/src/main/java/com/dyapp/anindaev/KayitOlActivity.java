package com.dyapp.anindaev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dyapp.anindaev.Models.RegisterPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KayitOlActivity extends AppCompatActivity {

    EditText etAd, etSoyad, etEmail, etParola;
    Button btnKayitOl;
    TextView tvGirisSayfasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        tanimla();
    }

    public void register(String ad, String soyad, final String email, String parola) {
        final ProgressDialog progressDialog = new ProgressDialog(KayitOlActivity.this);
        progressDialog.setMessage("Kayıt yapılıyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<RegisterPojo> request = ManagerAll.getInstance().register(ad, soyad, email, parola);
        request.enqueue(new Callback<RegisterPojo>() {
            @Override
            public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {
                if(response.body().getResult().equals("Boyle bir kayit var."))
                {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(), "Bu e-maile ait bir kayıt var", Toast.LENGTH_LONG).show();

                }
                else {
                    progressDialog.cancel();
                    kayitTemizle();
                    Integer kod = response.body().getDogrulamakodu();
                    Toast.makeText(getApplicationContext(), "Doğrulama Kodunuz : " + response.body().getDogrulamakodu(),
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(KayitOlActivity.this, DogrulamaActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("kod",kod);
                    startActivity(intent);
                    //finish();
                }
            }

            @Override
            public void onFailure(Call<RegisterPojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void tanimla() {
        etAd = (EditText) findViewById(R.id.txtAd);
        etSoyad = (EditText) findViewById(R.id.txtSoyad);
        etEmail = (EditText) findViewById(R.id.txtEmail);
        etParola = (EditText) findViewById(R.id.txtParola);
        btnKayitOl = (Button) findViewById(R.id.btnKayitOl);
        btnKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ad = etAd.getText().toString();
                String soyad = etSoyad.getText().toString();
               // String email = etEmail.getText().toString();
                String parola = etParola.getText().toString();


                String getEmailId = etEmail.getText().toString();
                if (!isEmailValid(getEmailId))
                {
                     Toast.makeText(getApplicationContext(),"Geçerli bir e-mail giriniz.",Toast.LENGTH_LONG).show();
                }
                else
                {
                if (!ad.equals("") && !soyad.equals("") && !getEmailId.equals("") && !parola.equals("")) {
                    if(parola.trim().length()>=6) {
                        register(ad, soyad, getEmailId, parola);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Şifre uzunluğu en az 6 karakter olmalı", Toast.LENGTH_LONG).show();
                    }
                } else if (ad.equals("") || soyad.equals("") || getEmailId.equals("") || parola.equals("")) {
                    Toast.makeText(getApplicationContext(), "Boş alanları doldurunuz", Toast.LENGTH_LONG).show();

                }
                }
            }
        });
        tvGirisSayfasi = (TextView) findViewById(R.id.girisSayfasi);
        tvGirisSayfasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KayitOlActivity.this, GirisActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void kayitTemizle() {
        etAd.setText("");
        etSoyad.setText("");
        etEmail.setText("");
        etParola.setText("");
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}