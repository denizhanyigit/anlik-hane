package com.dyapp.anindaev.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyapp.anindaev.Models.FavoriIlanlarimPojo;
import com.dyapp.anindaev.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriIlanlarimAdapter extends BaseAdapter {

    List<FavoriIlanlarimPojo> list;
    Context context;

    public FavoriIlanlarimAdapter(List<FavoriIlanlarimPojo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.favori_ilan_layout,parent,false);
        TextView baslik,il,ilce,fiyat,tarih;
        ImageView imageView;
        baslik=(TextView) convertView.findViewById(R.id.ilanBaslik);
        il=(TextView) convertView.findViewById(R.id.ilanIl);
        ilce=(TextView) convertView.findViewById(R.id.ilanIlce);
        fiyat=(TextView) convertView.findViewById(R.id.ilanFiyat);
        tarih=(TextView) convertView.findViewById(R.id.ilanTarih);
        imageView=(ImageView)convertView.findViewById(R.id.ilanResim);

        baslik.setText(list.get(position).getBaslik());
        il.setText(list.get(position).getIl()+", ");
        ilce.setText(list.get(position).getIlce());
        fiyat.setText(list.get(position).getFiyat()+"₺");
        tarih.setText(list.get(position).getTarih());


        Picasso.with(context).load("https://anlikhane.denizhanyigit.com/"+list.get(position).getResim()).resize(200,150).into(imageView);

        return convertView;
    }
}
