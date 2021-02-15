package com.dyapp.anindaev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.dyapp.anindaev.Models.AddTokenPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager, manager;
    FragmentTransaction fragmentTransaction;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String kulAd, kulMail,kulSoyad;
    TextView ad, mail;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    String token, kul_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        kulMail = sharedPreferences.getString("uye_mail", null);
        kulAd = sharedPreferences.getString("uye_ad", null);
        kul_id = sharedPreferences.getString("uye_id", null);
        kulSoyad=sharedPreferences.getString("uye_soyad",null);


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        token = task.getResult().getToken();
                        Log.d("Token", token+" "+kul_id);
                        addToken(kul_id, token);
                    }
                });


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // menu yanındaki app-name color değiştirme
        //toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.purple200));

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //load default fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new MainFragment());
        fragmentTransaction.commit();

        // menu icon değiştirme
       /* getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.bars);*/

        // kullanıcı ad ve mail çekme
        View headerView = navigationView.getHeaderView(0);
        ad = (TextView) headerView.findViewById(R.id.txtAd);
        mail = (TextView) headerView.findViewById(R.id.txtMail);
        mail.setText(kulMail);
        ad.setText("Hoşgeldin, " + kulAd+" "+kulSoyad);

    }

    public void addToken(String kul_id, String token) {
        Call<AddTokenPojo> x = ManagerAll.getInstance().addTokenManager(kul_id, token);
        x.enqueue(new Callback<AddTokenPojo>() {
            @Override
            public void onResponse(Call<AddTokenPojo> call, Response<AddTokenPojo> response) {

            }

            @Override
            public void onFailure(Call<AddTokenPojo> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.ara);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("İlan başlığında ara");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, AramaDetayActivity.class);
                intent.putExtra("aramaText", query);
                startActivity(intent);
                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.anasayfa) {

            //  if (this == MainActivity.this) {
            //   drawerLayout.closeDrawer(GravityCompat.START);
            //  } else {
        //    gecisReklami();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            // overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            //  }

        }
        if (item.getItemId() == R.id.ilanekle) {

            Intent intent = new Intent(MainActivity.this, IlanEkleActivity1.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out);


        }
        if (item.getItemId() == R.id.ilanlarim) {

            Intent intent = new Intent(MainActivity.this, IlanlarimActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out);



 /*fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new MainFragment());
            fragmentTransaction.commit();*/
        }
        if (item.getItemId() == R.id.favoriIlanlarim) {
            Intent intent = new Intent(MainActivity.this, FavoriIlanlarimActivity.class);
            startActivity(intent);

           /* fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new DenemeFragment());
            fragmentTransaction.commit();*/
        }
        if (item.getItemId() == R.id.mesajlarim) {
            Intent intent = new Intent(MainActivity.this, MesajlarimActivity.class);
            startActivity(intent);

        }
        if (item.getItemId() == R.id.hesabim) {
            Intent intent = new Intent(MainActivity.this, ProfilimActivity.class);
            startActivity(intent);
            //finish();
        }
        if (item.getItemId() == R.id.sifredegistir) {
            Intent intent = new Intent(MainActivity.this, SifreDegistirActivity.class);
            startActivity(intent);
            //finish();
        }
        if (item.getItemId() == R.id.cikis) {
            editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(MainActivity.this, GirisActivity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }

    public void gecisReklami() {
        MobileAds.initialize(this, "ca-app-pub-6261653662849942~5050468043");

        mInterstitialAd = new InterstitialAd(this);

        mInterstitialAd.setAdUnitId("ca-app-pub-6261653662849942/9919651348");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                super.onAdLoaded();
            }

        });
    }


}