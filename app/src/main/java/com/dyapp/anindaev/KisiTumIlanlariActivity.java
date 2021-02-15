package com.dyapp.anindaev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dyapp.anindaev.Adapters.FavoriIlanlarimAdapter;
import com.dyapp.anindaev.Adapters.IlanlarimAdapter;
import com.dyapp.anindaev.Models.FavoriIlanlarimPojo;
import com.dyapp.anindaev.Models.IlanlarimPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KisiTumIlanlariActivity extends AppCompatActivity {

    ListView listView;
    List<IlanlarimPojo> list;
    IlanlarimAdapter ilanlarimAdapter;
    String other_id,kisiAd;
    TextView baslik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisi_tum_ilanlari);

        tanimla();
        listeleIstek(other_id);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ilan_id = list.get(position).getIlanid();
                Intent intent = new Intent(KisiTumIlanlariActivity.this,IlanDetayKisiTumIlanlariActivity.class);
                intent.putExtra("ilan_id",ilan_id);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

    }

    public void listeleIstek(String other_id) {
        final ProgressDialog progressDialog = new ProgressDialog(KisiTumIlanlariActivity.this);
        progressDialog.setMessage("Kişinin ilanları yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<List<IlanlarimPojo>> x = ManagerAll.getInstance().ilanGetirme(other_id);
        x.enqueue(new Callback<List<IlanlarimPojo>>() {
            @Override
            public void onResponse(Call<List<IlanlarimPojo>> call, Response<List<IlanlarimPojo>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    if (response.body().get(0).isTf()) {
                        ilanlarimAdapter = new IlanlarimAdapter(list, getApplicationContext());
                        listView.setAdapter(ilanlarimAdapter);
                        baslik.setText(kisiAd+" kullanıcısının tüm ilanları");
                        progressDialog.cancel();
                    }
                } else {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(), "İlanlar yüklenemedi.", Toast.LENGTH_LONG).show();

                    {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<IlanlarimPojo>> call, Throwable t) {

            }
        });
    }

    public void tanimla() {
        Bundle bundle = getIntent().getExtras();
        other_id = bundle.getString("other_id");
        kisiAd=bundle.getString("kisiAd");
        list = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        baslik =(TextView) findViewById(R.id.baslik);
    }
}