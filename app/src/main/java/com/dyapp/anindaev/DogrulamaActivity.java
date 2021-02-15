package com.dyapp.anindaev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dyapp.anindaev.Models.DogrulamaPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogrulamaActivity extends AppCompatActivity {

    EditText etEmail, etKod;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button btnDogrula,btnTekrarKod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogrulama);
        tanimla();
        mailAl();

        btnDogrula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String kod = etKod.getText().toString();
                dogrula(email, kod);
            }
        });



    }

    public void dogrula(String kadi, final String kod)
    {
        final ProgressDialog progressDialog = new ProgressDialog(DogrulamaActivity.this);
        progressDialog.setMessage("Kontrol ediliyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<DogrulamaPojo> x = ManagerAll.getInstance().dogrula(kadi, kod);
        x.enqueue(new Callback<DogrulamaPojo>() {
            @Override
            public void onResponse(Call<DogrulamaPojo> call, Response<DogrulamaPojo> response) {
                if(response.body().isTf())
                {
                    String uye_id = response.body().getId();
                    String uye_email = response.body().getEmail();
                    sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
                    editor = sharedPreferences.edit();
                    editor.putString("uye_id", uye_id);
                    editor.putString("uye_email",uye_email);
                    editor.commit();
                    progressDialog.cancel();
                    Intent intent = new Intent(DogrulamaActivity.this, GirisActivity.class);
                    Name.setMail(uye_email);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(),"Tebrikler. Hesabınız aktif hale getirildi. Buradan giriş" +
                            " yapabilirsiniz.",Toast.LENGTH_LONG).show();

                }
                else if(!response.body().isTf())
                {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(),"Doğrulama kodu yanlış.",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<DogrulamaPojo> call, Throwable t) {

            }
        });
    }

    public void tanimla() {
        etEmail = (EditText) findViewById(R.id.txtEmail);
        etKod = (EditText) findViewById(R.id.txtKod);
        btnDogrula =(Button) findViewById(R.id.btnDogrula);
        btnTekrarKod = (Button) findViewById(R.id.btnTekrarKod);


    }
    public void mailAl()
    {
        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");
        final Integer kod = bundle.getInt("kod");
        etEmail.setText(email);

        btnTekrarKod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Doğrulama Kodunuz : " + kod,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}