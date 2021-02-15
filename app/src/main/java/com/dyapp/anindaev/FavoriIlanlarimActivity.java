package com.dyapp.anindaev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dyapp.anindaev.Adapters.FavoriIlanlarimAdapter;
import com.dyapp.anindaev.Models.FavoriIlanlarimPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriIlanlarimActivity extends AppCompatActivity {


    ListView listView;
    List<FavoriIlanlarimPojo> list;
    FavoriIlanlarimAdapter favoriIlanlarimAdapter;
    SharedPreferences sharedPreferences;
    String kul_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favori_ilanlarim);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView =(ListView) findViewById(R.id.listView);
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        kul_id = sharedPreferences.getString("uye_id", null);

        ilanlarimiGoruntule();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FavoriIlanlarimActivity.this,IlanDetayActivity.class);
                intent.putExtra("ilan_id",list.get(position).getIlanid());
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });
    }
    public void ilanlarimiGoruntule() {
        final ProgressDialog progressDialog = new ProgressDialog(FavoriIlanlarimActivity.this);
        progressDialog.setMessage("İlanlarınız yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        list = new ArrayList<>();
        Call<List<FavoriIlanlarimPojo>> x = ManagerAll.getInstance().favoriIlanGetir(kul_id);
        x.enqueue(new Callback<List<FavoriIlanlarimPojo>>() {
            @Override
            public void onResponse(Call<List<FavoriIlanlarimPojo>> call, final Response<List<FavoriIlanlarimPojo>> response) {
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
                    if(response.body().get(0).isTf())
                    {
                        favoriIlanlarimAdapter = new FavoriIlanlarimAdapter(list,getApplicationContext());
                        listView.setAdapter(favoriIlanlarimAdapter);
                        progressDialog.cancel();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Favori ilanınız bulunmamaktadır.",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(FavoriIlanlarimActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.anim_in_ters, R.anim.anim_out_ters);
                    }



                } else {

                    Toast.makeText(getApplicationContext(), "İlanlar yüklenemedi.", Toast.LENGTH_LONG).show();

                    {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<FavoriIlanlarimPojo>> call, Throwable t) {

            }
        });
    }


}