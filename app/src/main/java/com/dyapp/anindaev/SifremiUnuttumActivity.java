package com.dyapp.anindaev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dyapp.anindaev.Models.MailGonderPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SifremiUnuttumActivity extends AppCompatActivity {

    EditText etMail;
    Button btnGonder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_unuttum);
        tanimla();
        btnGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = etMail.getText().toString();
                if(!mail.equals("")) {
                    gonder(mail);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Lütfen bir e-mail giriniz."
                            ,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void gonder(String email)
    {
        final ProgressDialog progressDialog = new ProgressDialog(SifremiUnuttumActivity.this);
        progressDialog.setMessage("Mail gönderiliyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<MailGonderPojo> x = ManagerAll.getInstance().mailgonderme(email);
        x.enqueue(new Callback<MailGonderPojo>() {
            @Override
            public void onResponse(Call<MailGonderPojo> call, Response<MailGonderPojo> response) {
                if(response.body().getResult().equals("Sifre guncelleme basarili."))
                {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(),"Yeni şifreniz mail adresinize gönderilmiştir. Lütfen gereksiz e-posta(spam) " +
                                    "klasörünüzü de kontrol ediniz."
                    ,Toast.LENGTH_LONG).show();
                    etMail.setText("");
                    Intent intent = new Intent(SifremiUnuttumActivity.this, GirisActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(),"Sistemde girdiğiniz e-maile ait aktif bir kayıt bulunmamaktadır."
                            ,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<MailGonderPojo> call, Throwable t) {

            }
        });
    }
    public void tanimla()
    {
        etMail = (EditText) findViewById(R.id.email);
        btnGonder =(Button) findViewById(R.id.btnGonder);
    }
}