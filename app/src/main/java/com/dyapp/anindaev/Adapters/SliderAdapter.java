package com.dyapp.anindaev.Adapters;

import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.dyapp.anindaev.Models.SliderResimPojo;
import com.dyapp.anindaev.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    List<SliderResimPojo> list;
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(List<SliderResimPojo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(RelativeLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slider_layout,container,false);

        ImageView imageView = (ImageView) view.findViewById(R.id.sliderImageView);
        Picasso.with(context).load("https://anlikhane.denizhanyigit.com/"+list.get(position).getResim()).resize(1200,800).into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,Object object) {

        container.removeView((View)object);
       // super.destroyItem(container, position, object);
    }
}
