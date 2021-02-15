package com.dyapp.anindaev;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dyapp.anindaev.Models.ProfilimPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MesajlarimActivity extends AppCompatActivity {

    String userID;
    List<String> otherIDlist;
    SharedPreferences sharedPreferences;
    DatabaseReference reference;
    MesajlarimAdapter mesajlarimAdapter;
    ListView listView;
    String yeniId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesajlarim);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ProgressDialog progressDialog = new ProgressDialog(MesajlarimActivity.this);
        progressDialog.setMessage("Mesajlarınız listeleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Thread gecis;
        gecis = new Thread() {
            @Override
            public void run() {

                try {

                    synchronized (this) {
                        wait(2000);
                    }
                } catch (InterruptedException ex) {

                } finally {
                    progressDialog.cancel();

                }

            }
        };
        gecis.start();
        tanimla();
        listele();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                yeniId = otherIDlist.get(position);
                isimeIstekAt(yeniId);
            }
        });

    }

    public void isimeIstekAt(String no) {
        Call<ProfilimPojo> x = ManagerAll.getInstance().profilGetir(no);
        x.enqueue(new Callback<ProfilimPojo>() {
            @Override
            public void onResponse(Call<ProfilimPojo> call, Response<ProfilimPojo> response) {
                if (response.body().getResult().equals("Bilgiler getirildi.")) {
                    Name.setAd(response.body().getKulad() + " " + response.body().getKulsoyad());
                    Intent intent = new Intent(MesajlarimActivity.this, ChatActivity.class);
                    intent.putExtra("otherID", yeniId);
                    intent.putExtra("kisiAd", Name.getAd());
                    startActivity(intent);

                } else {

                }
            }

            @Override
            public void onFailure(Call<ProfilimPojo> call, Throwable t) {

            }
        });
    }

    public void tanimla() {
        otherIDlist = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        userID = sharedPreferences.getString("uye_id", null);
        mesajlarimAdapter = new MesajlarimAdapter(otherIDlist, userID, getApplicationContext());
        listView = (ListView) findViewById(R.id.mesajlarListView);
        listView.setAdapter(mesajlarimAdapter);
    }

    public void listele() {
        reference.child("Mesajlar").child(userID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                otherIDlist.add(dataSnapshot.getKey());
                mesajlarimAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
             /*   otherIDlist.add(dataSnapshot.getKey());
                mesajlarimAdapter.notifyDataSetChanged();*/
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                otherIDlist.add(dataSnapshot.getKey());
                mesajlarimAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}