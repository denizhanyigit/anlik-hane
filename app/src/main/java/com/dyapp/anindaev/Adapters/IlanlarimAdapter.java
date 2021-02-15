package com.dyapp.anindaev.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyapp.anindaev.Models.IlanlarimPojo;
import com.dyapp.anindaev.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IlanlarimAdapter extends BaseAdapter {

    List<IlanlarimPojo> list;
    Context context;
    String ilan_id,kul_id;

    public IlanlarimAdapter(List<IlanlarimPojo> list, Context context) {
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

        convertView = LayoutInflater.from(context).inflate(R.layout.ilanlarim_layout,parent,false);
        ImageView ilanResim;
        TextView ilanBaslik, ilanFiyat, ilanIl, ilanIlce,ilanTarih;

        ilanResim=(ImageView) convertView.findViewById(R.id.ilanResim);
        ilanBaslik=(TextView) convertView.findViewById(R.id.ilanBaslik);
        ilanFiyat=(TextView) convertView.findViewById(R.id.ilanFiyat);
        ilanIl=(TextView) convertView.findViewById(R.id.ilanIl);
        ilanIlce=(TextView) convertView.findViewById(R.id.ilanIlce);
        ilanTarih=(TextView)convertView.findViewById(R.id.ilanTarih);
        ilanBaslik.setText(list.get(position).getBaslik());
        ilanFiyat.setText(list.get(position).getFiyat()+"₺");
        ilanIl.setText(list.get(position).getIl()+", ");
        ilanIlce.setText(list.get(position).getIlce());
        ilanTarih.setText((CharSequence) list.get(position).getTarih());

        ilan_id=list.get(position).getIlanid();
        kul_id=list.get(position).getKulid();

        Picasso.with(context).load("https://anlikhane.denizhanyigit.com/"+list.get(position).getResim()).resize(200,150).into(ilanResim);



        return convertView;
    }
}
