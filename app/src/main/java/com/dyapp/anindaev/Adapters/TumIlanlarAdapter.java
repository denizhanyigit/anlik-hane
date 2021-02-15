package com.dyapp.anindaev.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyapp.anindaev.Models.TumIlanlarPojo;
import com.dyapp.anindaev.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TumIlanlarAdapter extends BaseAdapter {

    List<TumIlanlarPojo> ilanlarPojoList;
    Context context;

    public TumIlanlarAdapter(List<TumIlanlarPojo> ilanlarPojoList, Context context) {
        this.ilanlarPojoList = ilanlarPojoList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ilanlarPojoList.size();
    }

    @Override
    public Object getItem(int position) {
        return ilanlarPojoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.tumilanlar_layout,parent,false);
        TextView baslik, il,ilce,fiyat;
        ImageView resim;

        baslik =(TextView) convertView.findViewById(R.id.ilanBaslik);
        il =(TextView)convertView.findViewById(R.id.ilanIl);
        ilce =(TextView) convertView.findViewById(R.id.ilanIlce);
        fiyat=(TextView)convertView.findViewById(R.id.ilanFiyat);
        resim=(ImageView)convertView.findViewById(R.id.ilanResim);

        baslik.setText(ilanlarPojoList.get(position).getBaslik());
        il.setText(ilanlarPojoList.get(position).getIl()+", ");
        ilce.setText(ilanlarPojoList.get(position).getIlce());
        fiyat.setText(ilanlarPojoList.get(position).getFiyat()+"₺");

        Picasso.with(context).load("https://anlikhane.denizhanyigit.com/"+ilanlarPojoList.get(position).getResim()).resize(200,150).
                into(resim);
        return convertView;
    }
}
