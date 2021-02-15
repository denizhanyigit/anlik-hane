package com.dyapp.anindaev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.dyapp.anindaev.Models.ProfilGuncellePojo;
import com.dyapp.anindaev.Models.ProfilimPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilimActivity extends AppCompatActivity {

    EditText ad, soyad, email, tel;
    TextView btnGuncelle;
    SharedPreferences sharedPreferences;
    String kul_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilim);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tanimla();
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        kul_id = sharedPreferences.getString("uye_id", null);
        profilBilgiGetir();

      /*  FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        String token = task.getResult().getToken();
                        Log.d("Token", token);
                    }
                });*/

        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kulAd = ad.getText().toString();
                String kulSoyad = soyad.getText().toString();
                String getEmailId = email.getText().toString();
                String kulTel = tel.getText().toString();
                if (kulAd.equals("") || kulSoyad.equals("") || getEmailId.equals("")) {
                    Toast.makeText(getApplicationContext(), "Eksik bilgiler var.", Toast.LENGTH_LONG).show();
                } else if (!isEmailValid(getEmailId)) {
                    Toast.makeText(getApplicationContext(), "Geçerli bir e-mail giriniz.", Toast.LENGTH_LONG).show();
                } else if (!kulAd.equals("") && !kulSoyad.equals("") && isEmailValid(getEmailId) || !kulTel.equals("")) {
                    guncelle(kul_id, kulAd, kulSoyad, getEmailId, kulTel);
                    profilBilgiGetir();
                }
            }
        });
    }

    public void profilBilgiGetir() {
        final ProgressDialog progressDialog = new ProgressDialog(ProfilimActivity.this);
        progressDialog.setMessage("Bilgileriniz getiriliyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ProfilimPojo> x = ManagerAll.getInstance().profilGetir(kul_id);
        x.enqueue(new Callback<ProfilimPojo>() {
            @Override
            public void onResponse(Call<ProfilimPojo> call, Response<ProfilimPojo> response) {
                if (response.body().getResult().equals("Bilgiler getirildi.")) {
                    ad.setText(response.body().getKulad());
                    soyad.setText(response.body().getKulsoyad());
                    email.setText(response.body().getEmail());
                    tel.setText(response.body().getTelno());
                    progressDialog.cancel();
                } else {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(), "Bilgiler yüklenemedi.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProfilimPojo> call, Throwable t) {

            }
        });
    }

    public void guncelle(String kul_id, String ad, String soyad, String mail, String tel) {
        final ProgressDialog progressDialog = new ProgressDialog(ProfilimActivity.this);
        progressDialog.setMessage("Bilgileriniz güncelleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ProfilGuncellePojo> x = ManagerAll.getInstance().profilGuncelleme(kul_id, ad, soyad, mail, tel);
        x.enqueue(new Callback<ProfilGuncellePojo>() {
            @Override
            public void onResponse(Call<ProfilGuncellePojo> call, Response<ProfilGuncellePojo> response) {
                if (response.body().getSonuc().equals("Guncellendi")) {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(), "Bilgileriniz güncellendi.", Toast.LENGTH_LONG).show();

                } else {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(), "Bilgileriniz güncellenemedi.", Toast.LENGTH_LONG).show();
                    profilBilgiGetir();
                }
            }

            @Override
            public void onFailure(Call<ProfilGuncellePojo> call, Throwable t) {

            }
        });
    }

    public void tanimla() {
        ad = (EditText) findViewById(R.id.kulAd);
        soyad = (EditText) findViewById(R.id.kulSoyad);
        email = (EditText) findViewById(R.id.kulMail);
        tel = (EditText) findViewById(R.id.kulTel);
        btnGuncelle = (TextView) findViewById(R.id.btnGuncelle);
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}