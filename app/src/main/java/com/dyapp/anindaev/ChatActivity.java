package com.dyapp.anindaev;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dyapp.anindaev.Adapters.MesajAdapter;
import com.dyapp.anindaev.Models.BildirimGonderPojo;
import com.dyapp.anindaev.Models.BildirimMailPojo;
import com.dyapp.anindaev.Models.MesajModel;
import com.dyapp.anindaev.RestApi.ManagerAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    String userID, otherID, ilan_id, kisiAd;
    SharedPreferences sharedPreferences;
    EditText mesaj;
    ImageView mesajGonder;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<MesajModel> list;
    ListView listView;
    MesajAdapter mesajAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView kisiAdi = (TextView) toolbar.findViewById(R.id.kisiAd);
        tanimla();
        kisiAdi.setText(kisiAd + " ile sohbet");
        loadMessage();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.sohbetiTemizle:
                reference.child("Mesajlar").child(userID).child(otherID).removeValue();
                loadMessage();
                Toast.makeText(getApplicationContext(), "Sohbet silindi.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ChatActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                return true;
            case R.id.ilanGetir:
                Intent intent2 = new Intent(ChatActivity.this, KisiTumIlanlariActivity.class);
                intent2.putExtra("ilan_id", ilan_id);
                intent2.putExtra("other_id", otherID);
                intent2.putExtra("kisiAd", kisiAd);
                startActivity(intent2);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void tanimla() {

        list = new ArrayList<>();
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        userID = sharedPreferences.getString("uye_id", null);

        Bundle bundle = getIntent().getExtras();
        otherID = bundle.getString("otherID");
        kisiAd = bundle.getString("kisiAd");
        // ilan_id = bundle.getString("ilan_id");


        mesaj = (EditText) findViewById(R.id.mesajText);
        mesajGonder = (ImageView) findViewById(R.id.btnGonder);
        //kisiAdi = (TextView) findViewById(R.id.kisiAd);


        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();

        listView = (ListView) findViewById(R.id.listView);
        listView.setOverscrollFooter(new ColorDrawable(Color.TRANSPARENT));
        mesajAdapter = new MesajAdapter(getApplicationContext(), list, ChatActivity.this, userID);
        listView.setAdapter(mesajAdapter);

        mesajGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msj = mesaj.getText().toString();
                if (msj.equals("")) {
                    mesaj.setHint("Bir şeyler yazmalısınız");
                } else if (!msj.equals("")) {
                    mesajGonder(msj);
                    mesaj.setText("");
                    bildirimGonder(otherID, userID);

                }
            }
        });
    }

    public void mesajGonder(String text) {
        SimpleDateFormat bicim2 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date tarihSaat = new Date();

        final String key = reference.child("Mesajlar").child(userID).child(otherID).push().getKey();
        final Map messageMap = new HashMap();
        messageMap.put("text", text);
        messageMap.put("from", userID);
        messageMap.put("date", bicim2.format(tarihSaat));

        reference.child("Mesajlar").child(userID).child(otherID).child(key).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    reference.child("Mesajlar").child(otherID).child(userID).child(key).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
            }
        });
    }

    public void bildirimGonder(String kul_id, String gonderenId) {
        Call<BildirimGonderPojo> x = ManagerAll.getInstance().bildirimGonderManager(kul_id, gonderenId);
        x.enqueue(new Callback<BildirimGonderPojo>() {
            @Override
            public void onResponse(Call<BildirimGonderPojo> call, Response<BildirimGonderPojo> response) {

            }

            @Override
            public void onFailure(Call<BildirimGonderPojo> call, Throwable t) {

            }
        });
    }



    public void loadMessage() {
        reference.child("Mesajlar").child(userID).child(otherID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MesajModel m = dataSnapshot.getValue(MesajModel.class);
                list.add(m);
                mesajAdapter.notifyDataSetChanged();
                listView.smoothScrollToPosition(list.size() - 1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

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