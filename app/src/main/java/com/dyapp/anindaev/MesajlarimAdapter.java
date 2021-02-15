package com.dyapp.anindaev;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dyapp.anindaev.Models.MesajModel;
import com.dyapp.anindaev.Models.ProfilimPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MesajlarimAdapter extends BaseAdapter {

    List<String> otherIDlist;
    String userID;
    Context context;

    public MesajlarimAdapter(List<String> otherIDlist, String userID, Context context) {
        this.otherIDlist = otherIDlist;
        this.userID = userID;
        this.context = context;

    }

    @Override
    public int getCount() {
        return otherIDlist.size();
    }

    @Override
    public Object getItem(int position) {
        return otherIDlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.mesajlarim_layout, parent, false);
        TextView kisiIsmi;
        kisiIsmi = (TextView) convertView.findViewById(R.id.isim);
        istekAt(otherIDlist.get(position), kisiIsmi);
        return convertView;
    }

    public void istekAt(String kul_id, final TextView textView) {
        Call<ProfilimPojo> x = ManagerAll.getInstance().profilGetir(kul_id);
        x.enqueue(new Callback<ProfilimPojo>() {
            @Override
            public void onResponse(Call<ProfilimPojo> call, Response<ProfilimPojo> response) {
                if (response.body().getResult().equals("Bilgiler getirildi.")) {
                    textView.setText(response.body().getKulad() + " " + response.body().getKulsoyad()+" ile sohbetiniz");
                } else {

                }
            }

            @Override
            public void onFailure(Call<ProfilimPojo> call, Throwable t) {

            }
        });
    }


}
