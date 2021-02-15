package com.dyapp.anindaev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dyapp.anindaev.Adapters.IlanlarimAdapter;
import com.dyapp.anindaev.Models.IlanSilPojo;
import com.dyapp.anindaev.Models.IlanlarimPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IlanlarimActivity extends AppCompatActivity {

    ListView listView;
    IlanlarimAdapter ilanlarimAdapter;
    List<IlanlarimPojo> list;
    SharedPreferences sharedPreferences;
    String kul_id, ilan_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilanlarim);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.ilanlarimListView);
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        kul_id = sharedPreferences.getString("uye_id", null);
        ilanlarimiGoruntule();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ilan_id = list.get(position).getIlanid();
                ilanlarimSeceneklerDialog();


            }
        });

    }

    public void ilanlarimSeceneklerDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.ilanim_secenekler, null);
        TextView goruntule, guncelle,resimGuncelle, ilaniSil;
        ImageView iptal;
        goruntule = (TextView) view.findViewById(R.id.ilanGoruntule);
        guncelle = (TextView) view.findViewById(R.id.ilanGuncelle);
        resimGuncelle = (TextView) view.findViewById(R.id.ilanResimGuncelle);
        ilaniSil = (TextView) view.findViewById(R.id.ilanSil);
        iptal = (ImageView) view.findViewById(R.id.ilanIptal);
        AlertDialog.Builder alert = new AlertDialog.Builder(IlanlarimActivity.this);
        alert.setView(view);
        alert.setCancelable(false);
        final AlertDialog dialog = alert.create();
        dialog.show();
        dialog.getWindow().setLayout(650, 700);
        iptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent intent = new Intent(IlanlarimActivity.this, IlanlarimGuncelle.class);
                intent.putExtra("ilan_id", ilan_id);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        goruntule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent intent = new Intent(IlanlarimActivity.this, IlanDetayKisiTumIlanlariActivity.class);
                intent.putExtra("ilan_id", ilan_id);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        ilaniSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                ilanlarimAlertDialog();

            }
        });

        resimGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent intent = new Intent(IlanlarimActivity.this,IlanResimGuncelleActivity.class);
                intent.putExtra("ilan_id", ilan_id);
                intent.putExtra("kul_id", kul_id);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);

            }
        });

    }

    public void ilanlarimAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_layout, null);
        TextView sil, iptal;

        sil = (TextView) view.findViewById(R.id.sil);
        iptal = (TextView) view.findViewById(R.id.iptal);


        AlertDialog.Builder alert = new AlertDialog.Builder(IlanlarimActivity.this);
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
                ilanSil(ilan_id);
                Intent intent = new Intent(IlanlarimActivity.this, IlanlarimActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });


    }

    public void ilanSil(String ilanid) {
        final ProgressDialog progressDialog = new ProgressDialog(IlanlarimActivity.this);
        progressDialog.setMessage("İlanlarınız yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<IlanSilPojo> x = ManagerAll.getInstance().ilanSilme(ilanid);
        x.enqueue(new Callback<IlanSilPojo>() {
            @Override
            public void onResponse(Call<IlanSilPojo> call, Response<IlanSilPojo> response) {
                if (response.body().getResult().equals("Silindi.")) {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(), "Silindi.", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(), "Silinmedi.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<IlanSilPojo> call, Throwable t) {

            }
        });
    }

    public void ilanlarimiGoruntule() {
        final ProgressDialog progressDialog = new ProgressDialog(IlanlarimActivity.this);
        progressDialog.setMessage("İlanlarınız yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        list = new ArrayList<>();
        Call<List<IlanlarimPojo>> x = ManagerAll.getInstance().ilanGetirme(kul_id);
        x.enqueue(new Callback<List<IlanlarimPojo>>() {
            @Override
            public void onResponse(Call<List<IlanlarimPojo>> call, final Response<List<IlanlarimPojo>> response) {
                if (response.isSuccessful()) {
                    Thread gecis;
                    gecis = new Thread() {
                        @Override
                        public void run() {
                            try {
                                synchronized (this) {
                                    wait(1500);
                                }
                            } catch (InterruptedException ex) {

                            } finally {

                            }

                        }
                    };
                    gecis.start();
                    list = response.body();
                    if (response.body().get(0).isTf()) {
                        ilanlarimAdapter = new IlanlarimAdapter(list, getApplicationContext());
                        listView.setAdapter(ilanlarimAdapter);
                        progressDialog.cancel();
                    } else {
                        Toast.makeText(getApplicationContext(), "İlanınız bulunmamaktadır.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(IlanlarimActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.anim_in_ters, R.anim.anim_out_ters);
                    }


                } else {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(), "İlanlarınız yüklenemedi.", Toast.LENGTH_LONG).show();

                    {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<IlanlarimPojo>> call, Throwable t) {

            }
        });
    }

}