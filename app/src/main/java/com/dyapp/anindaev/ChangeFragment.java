package com.dyapp.anindaev;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class ChangeFragment {

    private Context context;

    public ChangeFragment(Context context) {
        this.context = context;
    }

    public void change(Fragment fragment) {

        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_layout, fragment, "fragment").
                setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
