package com.dyapp.anindaev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dyapp.anindaev.Adapters.TumIlanlarAdapter;
import com.dyapp.anindaev.Models.TumIlanlarPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AramaDetayActivity extends AppCompatActivity {

    String text;
    List<TumIlanlarPojo> list;
    ListView listView;
    TumIlanlarAdapter tumIlanlarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arama_detay);

        tanimla();
        Bundle bundle = getIntent().getExtras();
        text = bundle.getString("aramaText");
        aramaSonuclari();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(AramaDetayActivity.this, IlanDetayActivity.class);
                intent.putExtra("ilan_id", list.get(position).getIlanid());
                intent.putExtra("aramaText", text);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });
    }

    public void aramaSonuclari() {
        final ProgressDialog progressDialog = new ProgressDialog(AramaDetayActivity.this);
        progressDialog.setMessage("Sonuçlar yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<List<TumIlanlarPojo>> x = ManagerAll.getInstance().ilanArama(text);
        x.enqueue(new Callback<List<TumIlanlarPojo>>() {
            @Override
            public void onResponse(Call<List<TumIlanlarPojo>> call, Response<List<TumIlanlarPojo>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    if (response.body().get(0).isTf()) {
                        tumIlanlarAdapter = new TumIlanlarAdapter(list, getApplicationContext());
                        listView.setAdapter(tumIlanlarAdapter);
                        progressDialog.cancel();
                        Toast.makeText(AramaDetayActivity.this, "Toplam " + response.body().get(0).getSayi() + " adet sonuç listelendi.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        progressDialog.cancel();
                        Toast.makeText(AramaDetayActivity.this, "Aradığınız kriterlere uygun sonuç bulunamadı. Anasayfaya yönlendiriliyorsunuz.",
                                Toast.LENGTH_LONG).show();
                        Thread gecis;
                        gecis = new Thread() {
                            @Override
                            public void run() {
                                try {

                                    synchronized (this) {
                                        wait(2500);
                                    }
                                } catch (InterruptedException ex) {

                                } finally {

                                    Intent intent = new Intent(AramaDetayActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                        };
                        gecis.start();


                    }
                }
            }

            @Override
            public void onFailure(Call<List<TumIlanlarPojo>> call, Throwable t) {

            }
        });


   /*FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, new DenemeFragment());
                fragmentTransaction.commit();*/
    }

    public void tanimla() {
        listView = (ListView) findViewById(R.id.ilanlarlistView);
    }
}