package com.dyapp.anindaev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dyapp.anindaev.Models.SifreDegistirPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SifreDegistirActivity extends AppCompatActivity {

    EditText mevcutSifre, yeniSifre;
    TextView btnDegistir;
    SharedPreferences sharedPreferences;
    String kul_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifre_degistir);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tanimla();
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        kul_id = sharedPreferences.getString("uye_id", null);

        btnDegistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String parolam=mevcutSifre.getText().toString();
                String yeniSifrem=yeniSifre.getText().toString();
                if(!parolam.equals("") && !yeniSifrem.equals("")) {
                    degistir(kul_id, parolam, yeniSifrem);
                    mevcutSifre.setText("");;
                    yeniSifre.setText("");
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Tüm alanları doldurun.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void degistir(String kul_id, String parola, String yenisifre) {
        final ProgressDialog progressDialog = new ProgressDialog(SifreDegistirActivity.this);
        progressDialog.setMessage("Şifreniz değiştiriliyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<SifreDegistirPojo> x = ManagerAll.getInstance().sifreDegistirme(kul_id, parola, yenisifre);
        x.enqueue(new Callback<SifreDegistirPojo>() {
            @Override
            public void onResponse(Call<SifreDegistirPojo> call, Response<SifreDegistirPojo> response) {

                if (response.isSuccessful()) {
                    if (response.body().getSonuc().equals("Sifre degistirildi."))
                    {
                        progressDialog.cancel();
                        Toast.makeText(getApplicationContext(), "Şifreniz değiştirildi.", Toast.LENGTH_LONG).show();
                    } else if (response.body().getSonuc().equals("Sifreler degistirilemedi."))
                    {
                        progressDialog.cancel();
                        Toast.makeText(getApplicationContext(), "Mevcut şifrenizi yanlış girdiniz.", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(), "else", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SifreDegistirPojo> call, Throwable t) {

            }
        });
    }

    public void tanimla() {
        mevcutSifre = (EditText) findViewById(R.id.mevcutSifre);
        yeniSifre = (EditText) findViewById(R.id.yeniSifre);
        btnDegistir = (TextView) findViewById(R.id.btnDegistir);
    }
}