package com.dyapp.anindaev.Adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dyapp.anindaev.Models.MesajModel;
import com.dyapp.anindaev.R;

import java.util.List;

public class MesajAdapter extends BaseAdapter {

    TextView textView,tarih;
    Context context;
    List<MesajModel> list;
    Activity activity;
    String userID;
    Boolean state;
    int view_send = 1;
    int view_received = 2;

    public MesajAdapter(Context context, List<MesajModel> list, Activity activity, String userID) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.userID = userID;
        state = false;
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
     View view;
     if(getItemViewType(position)==view_send) {
         view = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false);
         textView =(TextView) view.findViewById(R.id.userTextView);
         textView.setText(list.get(position).getText().toString());
         tarih=(TextView) view.findViewById(R.id.userDate);
         tarih.setText(list.get(position).getDate().toString());
         return view;
     }
     else
     {
         view = LayoutInflater.from(context).inflate(R.layout.other_layout, parent, false);
         textView =(TextView) view.findViewById(R.id.otherTextView);
         textView.setText(list.get(position).getText().toString());
         tarih=(TextView) view.findViewById(R.id.otherDate);
         tarih.setText(list.get(position).getDate().toString());
         return view;
     }
    }


    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getFrom().equals(userID)) {
            state=true;
            return view_send;
        } else {
            state=false;
            return view_received;
        }
    }
}
