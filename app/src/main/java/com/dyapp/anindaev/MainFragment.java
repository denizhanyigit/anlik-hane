package com.dyapp.anindaev;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dyapp.anindaev.Adapters.IlanlarimAdapter;
import com.dyapp.anindaev.Adapters.TumIlanlarAdapter;
import com.dyapp.anindaev.Models.IlanVerPojo;
import com.dyapp.anindaev.Models.TumIlanlarPojo;
import com.dyapp.anindaev.RestApi.ManagerAll;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    View view, footerView;
    List<TumIlanlarPojo> list;
    ListView listView, filtreleListView;
    TumIlanlarAdapter tumIlanlarAdapter;
    ImageView ilanSirala, ilanFiltrele, ilanFiltreleKaldir;
    TextView filtreText, pageCount;
    ImageView ileri, geri;
    private String[] iller = {"ADANA", "ADIYAMAN", "AFYON", "AĞRI", "AKSARAY", "AMASYA", "ANKARA", "ANTALYA", "ARDAHAN", "ARTVİN", "AYDIN", "BALIKESİR"
            , "BARTIN", "BATMAN", "BAYBURT", "BİLECİK", "BİNGÖL", "BİTLİS", "BOLU", "BURDUR", "BURSA", "ÇANAKKALE", "ÇANKIRI", "ÇORUM", "DENİZLİ", "DİYARBAKIR", "DÜZCE", "EDİRNE", "ELAZIĞ", "ERZİNCAN",
            "ERZURUM", "ESKİŞEHİR", "GAZİANTEP", "GİRESUN", "GÜMÜŞHANE", "HAKKARİ", "HATAY", "IĞDIR", "ISPARTA", "İSTANBUL", "İZMİR", "K.MARAŞ", "KARABÜK", "KARAMAN", "KARS", "KASTAMONU",
            "KAYSERİ", "KIRIKKALE", "KIRKLARELİ", "KIRŞEHİR", "KİLİS", "KOCAELİ", "KONYA", "KÜTAHYA", "MALATYA", "MANİSA", "MARDİN", "MERSİN", "MUĞLA", "MUŞ",
            "NEVŞEHİR", "NİĞDE", "ORDU", "OSMANİYE", "RİZE", "SAKARYA", "SAMSUN", "SİİRT", "SİNOP", "SİVAS", "ŞANLIURFA", "ŞIRNAK", "TEKİRDAĞ",
            "TOKAT", "TRABZON", "TUNCELİ", "UŞAK", "VAN", "YALOVA", "YOZGAT", "ZONGULDAK", ""};
    private Spinner spinnerIller;
    private ArrayAdapter<String> dataAdapterForIlSayisi;

    private ArrayAdapter<String> dataAdapterForIlceSayisi;
    private Spinner spinnerIlceler;

    private String[] kategoriler = {"", "Daire", "Residence", "Müstakil Ev", "Villa", "Çiftlik Evi", "Köşk & Konak", "Yalı", "Yalı Dairesi", "Yazlık"};
    private Spinner spinnerKategoriler;
    private ArrayAdapter<String> dataAdapterForKategoriler;

    ImageView close;
    EditText enazFiyat, encokFiyat;
    Button btnGoster, btnTemizle;
    Activity activity;
    LinearLayout filtrele, linearList;
    CheckBox checkBox;
    int sayfa = 1;
    int counter = 1;
    int sayfa_sayisi = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        footerView = inflater.inflate(R.layout.listview_footer, null);

       // gecisReklami();

      /*  AdView adView = new AdView(getContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-6261653662849942/6884340183");

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        checkBox = (CheckBox) view.findViewById(R.id.checkBox);

        listView = (ListView) view.findViewById(R.id.ilanlarlistView);
        filtreleListView = (ListView) view.findViewById(R.id.filtreleListview);
        listView.addFooterView(footerView);

        ilanSirala = (ImageView) view.findViewById(R.id.ilanSirala);
        ilanFiltrele = (ImageView) view.findViewById(R.id.ilanFiltrele);

        ilanlarimiGoruntule(sayfa);
        ilanBilgileriTemizle();

        pageCount = (TextView) footerView.findViewById(R.id.pageCount);
        ileri = (ImageView) footerView.findViewById(R.id.btnIleri);
        geri = (ImageView) footerView.findViewById(R.id.btnGeri);
        ileri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter < sayfa_sayisi) {
                    counter++;
                    pageCount.setText("Sayfa " + counter);
                    ilanlarimiGoruntule(counter);
                } else {

                }
            }
        });
        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (counter == 1) {

                } else {
                    counter--;
                    pageCount.setText("Sayfa " + counter);
                    ilanlarimiGoruntule(counter);
                }

            }
        });

        filtrele = (LinearLayout) view.findViewById(R.id.filtreleLayout);
        ilanFiltreleKaldir = (ImageView) view.findViewById(R.id.ilanFiltreKaldir);
        linearList = (LinearLayout) view.findViewById(R.id.linearList);
        filtreText = (TextView) view.findViewById(R.id.filtreText);
        btnGoster = (Button) view.findViewById(R.id.btnGoster);
        btnTemizle = (Button) view.findViewById(R.id.btnTemizle);
        close = (ImageView) view.findViewById(R.id.close);
        spinnerKategoriler = (Spinner) view.findViewById(R.id.spinnerKategori);
        spinnerIller = (Spinner) view.findViewById(R.id.spinnerIl);
        spinnerIlceler = (Spinner) view.findViewById(R.id.spinnerIlce);

        dataAdapterForIlSayisi = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, iller);
        dataAdapterForIlSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIller.setAdapter(dataAdapterForIlSayisi);
        spinnerIller.setSelection(81);

        String bosilce[] = {""};
        dataAdapterForIlceSayisi = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, bosilce);
        dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
        spinnerIlceler.setSelection(0);

        dataAdapterForKategoriler = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, kategoriler);
        dataAdapterForKategoriler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategoriler.setAdapter(dataAdapterForKategoriler);

        enazFiyat = (EditText) view.findViewById(R.id.enazFiyat);
        encokFiyat = (EditText) view.findViewById(R.id.encokFiyat);


        spinnerIller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getSelectedItem().toString().equals(iller[0])) {
                    String[] adana = {"SEYHAN", "YÜREĞİR", "SARIÇAM", "ÇUKUROVA", "ALADAĞ(KARSANTI)", "CEYHAN", "FEKE", "İMAMOĞLU", "KARAİSALI", "KARATAŞ", "KOZAN", "POZANTI", "SAİMBEYLİ", "TUFANBEYLİ", "YUMURTALIK"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[1])) {
                    String[] adana = {"ADIYAMAN", "BESNİ", "ÇELİKHAN", "GERGER", "GÖLBAŞI", "KAHTA", "SAMSAT", "SİNCİK", "TUT"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[2])) {
                    String[] adana = {"AFYONKARAHİSAR", "BAŞMAKÇI", "BAYAT", "BOLVADİN", "ÇAY", "ÇOBANLAR", "DAZKIRI", "DİNAR", "EMİRDAĞ", "EVCİLER", "HOCALAR", "İHSANİYE", "İSCEHİSAR", "KIZILÖREN", "SANDIKLI", "SİNCANLI(SİNANPAŞA)", "SULTANDAĞI", "ŞUHUT"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[3])) {
                    String[] adana = {"AĞRI", "DİYADİN", "DOĞUBEYAZIT", "ELEŞKİRT", "HAMUR", "PATNOS", "TAŞLIÇAY", "TUTAK"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[4])) {
                    String[] adana = {"AKSARAY", "AĞAÇÖREN", "ESKİL", "GÜLAĞAÇ(AĞAÇLI)", "GÜZELYURT", "ORTAKÖY", "SARIYAHŞİ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[5])) {
                    String[] adana = {"AMASYA", "GÖYNÜCEK", "GÜMÜŞHACIKÖY", "HAMAMÖZÜ", "MERZİFON", "SULUOVA", "TAŞOVA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);

                }
                if (parent.getSelectedItem().toString().equals(iller[6])) {
                    String[] adana = {"ALTINDAĞ", "ÇANKAYA", "ETİMESGUT", "KEÇİÖREN", "MAMAK", "SİNCAN", "YENİMAHALLE", "GÖLBAŞI", "PURSAKLAR", "AKYURT", "AYAŞ", "BALA", "BEYPAZARI", "ÇAMLIDERE", "ÇUBUK", "ELMADAĞ", "EVREN", "GÜDÜL", "HAYMANA", "KALECİK", "KAZAN", "KIZILCAHAMAM", "NALLIHAN", "POLATLI", "ŞEREFLİKOÇHİSAR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);

                }
                if (parent.getSelectedItem().toString().equals(iller[7])) {
                    String[] adana = {"MURATPAŞA", "KEPEZ", "KONYAALTI", "AKSU", "DÖŞEMEALTI", "AKSEKİ", "ALANYA", "ELMALI", "FİNİKE", "GAZİPAŞA", "GÜNDOĞMUŞ", "İBRADI(AYDINKENT)", "KALE(DEMRE)", "KAŞ", "KEMER", "KORKUTELİ", "KUMLUCA", "MANAVGAT", "SERİK"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[8])) {
                    String[] adana = {"ARDAHAN", "ÇILDIR", "DAMAL", "GÖLE", "HANAK", "POSOF"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[9])) {
                    String[] adana = {"ARTVİN", "ARDANUÇ", "ARHAVİ", "BORÇKA", "HOPA", "MURGUL(GÖKTAŞ)", "ŞAVŞAT", "YUSUFELİ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[10])) {
                    String[] adana = {"AYDIN", "BOZDOĞAN", "BUHARKENT(ÇUBUKDAĞI)", "ÇİNE", "GERMENCİK", "İNCİRLİOVA", "KARACASU", "KARPUZLU", "KOÇARLI", "KÖŞK", "KUŞADASI", "KUYUCAK", "NAZİLLİ", "SÖKE", "SULTANHİSAR", "DİDİM(YENİHİSAR)", "YENİPAZAR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[11])) {
                    String[] adana = {"BALIKESİR", "AYVALIK", "BALYA", "BANDIRMA", "BİGADİÇ", "BURHANİYE", "DURSUNBEY", "EDREMİT", "ERDEK", "GÖMEÇ", "GÖNEN", "HAVRAN", "İVRİNDİ", "KEPSUT", "MANYAS", "MARMARA", "SAVAŞTEPE", "SINDIRGI", "SUSURLUK"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[12])) {
                    String[] adana = {"BARTIN", "AMASRA", "KURUCAŞİLE", "ULUS"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[13])) {
                    String[] adana = {"BATMAN", "BEŞİRİ", "GERCÜŞ", "HASANKEYF", "KOZLUK", "SASON"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[14])) {
                    String[] adana = {"BAYBURT", "AYDINTEPE", "DEMİRÖZÜ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[15])) {
                    String[] adana = {"BİLECİK", "BOZÜYÜK", "GÖLPAZARI", "İNHİSAR", "OSMANELİ", "PAZARYERİ", "SÖĞÜT", "YENİPAZAR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[16])) {
                    String[] adana = {"BİNGÖL", "ADAKLI", "GENÇ", "KARLIOVA", "KIĞI", "SOLHAN", "YAYLADERE", "YEDİSU"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[17])) {
                    String[] adana = {"BİTLİS", "ADİLCEVAZ", "AHLAT", "GÜROYMAK", "HİZAN", "MUTKİ", "TATVAN"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[18])) {
                    String[] adana = {"BOLU", "DÖRTDİVAN", "GEREDE", "GÖYNÜK", "KIBRISCIK", "MENGEN", "MUDURNU", "SEBEN", "YENİÇAĞA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[19])) {
                    String[] adana = {"BURDUR", "AĞLASUN", "ALTINYAYLA(DİRMİL)", "BUCAK", "ÇAVDIR", "ÇELTİKÇİ", "GÖLHİSAR", "KARAMANLI", "KEMER", "TEFENNİ", "YEŞİLOVA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[20])) {
                    String[] adana = {"NİLÜFER", "OSMANGAZİ", "YILDIRIM", "BÜYÜKORHAN", "GEMLİK", "GÜRSU", "HARMANCIK", "İNEGÖL", "İZNİK", "KARACABEY", "KELES", "KESTEL", "MUDANYA", "MUSTAFAKEMALPAŞA", "ORHANELİ", "ORHANGAZİ", "YENİŞEHİR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[21])) {
                    String[] adana = {"ÇANAKKALE", "AYVACIK", "BAYRAMİÇ", "BİGA", "BOZCAADA", "ÇAN", "ECEABAT", "EZİNE", "GELİBOLU", "GÖKÇEADA(İMROZ)", "LAPSEKİ", "YENİCE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[22])) {
                    String[] adana = {"ÇANKIRI", "ATKARACALAR", "BAYRAMÖREN", "ÇERKEŞ", "ELDİVAN", "ILGAZ", "KIZILIRMAK", "KORGUN", "KURŞUNLU", "ORTA", "ŞABANÖZÜ", "YAPRAKLI"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[23])) {
                    String[] adana = {"ÇORUM", "ALACA", "BAYAT", "BOĞAZKALE", "DODURGA", "İSKİLİP", "KARGI", "LAÇİN", "MECİTÖZÜ", "OĞUZLAR(KARAÖREN)", "ORTAKÖY", "OSMANCIK", "SUNGURLU", "UĞURLUDAĞ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[24])) {
                    String[] adana = {"DENİZLİ", "ACIPAYAM", "AKKÖY", "BABADAĞ", "BAKLAN", "BEKİLLİ", "BEYAĞAÇ", "BOZKURT", "BULDAN", "ÇAL", "ÇAMELİ", "ÇARDAK", "ÇİVRİL", "GÜNEY", "HONAZ", "KALE", "SARAYKÖY", "SERİNHİSAR", "TAVAS"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[25])) {
                    String[] adana = {"SUR", "BAĞLAR", "YENİŞEHİR", "KAYAPINAR", "BİSMİL", "ÇERMİK", "ÇINAR", "ÇÜNGÜŞ", "DİCLE", "EĞİL", "ERGANİ", "HANİ", "HAZRO", "KOCAKÖY", "KULP", "LİCE", "SİLVAN"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[26])) {
                    String[] adana = {"DÜZCE", "AKÇAKOCA", "CUMAYERİ", "ÇİLİMLİ", "GÖLYAKA", "GÜMÜŞOVA", "KAYNAŞLI", "YIĞILCA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[27])) {
                    String[] adana = {"EDİRNE", "ENEZ", "HAVSA", "İPSALA", "KEŞAN", "LALAPAŞA", "MERİÇ", "SÜLEOĞLU(SÜLOĞLU)", "UZUNKÖPRÜ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[28])) {
                    String[] adana = {"ELAZIĞ", "AĞIN", "ALACAKAYA", "ARICAK", "BASKİL", "KARAKOÇAN", "KEBAN", "KOVANCILAR", "MADEN", "PALU", "SİVRİCE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[29])) {
                    String[] adana = {"ERZİNCAN", "ÇAYIRLI", "İLİÇ(ILIÇ)", "KEMAH", "KEMALİYE", "OTLUKBELİ", "REFAHİYE", "TERCAN", "ÜZÜMLÜ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[30])) {
                    String[] adana = {"PALANDÖKEN", "YAKUTİYE", "AZİZİYE(ILICA)", "AŞKALE", "ÇAT", "HINIS", "HORASAN", "İSPİR", "KARAÇOBAN", "KARAYAZI", "KÖPRÜKÖY", "NARMAN", "OLTU", "OLUR", "PASİNLER", "PAZARYOLU", "ŞENKAYA", "TEKMAN", "TORTUM", "UZUNDERE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[31])) {
                    String[] adana = {"ODUNPAZARI", "TEPEBAŞI", "ALPU", "BEYLİKOVA", "ÇİFTELER", "GÜNYÜZÜ", "HAN", "İNÖNÜ", "MAHMUDİYE", "MİHALGAZİ", "MİHALIÇCIK", "SARICAKAYA", "SEYİTGAZİ", "SİVRİHİSAR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[32])) {
                    String[] adana = {"ŞAHİNBEY", "ŞEHİTKAMİL", "OĞUZELİ", "ARABAN", "İSLAHİYE", "KARKAMIŞ", "NİZİP", "NURDAĞI", "YAVUZELİ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[33])) {
                    String[] adana = {"GİRESUN", "ALUCRA", "BULANCAK", "ÇAMOLUK", "ÇANAKÇI", "DERELİ", "DOĞANKENT", "ESPİYE", "EYNESİL", "GÖRELE", "GÜCE", "KEŞAP", "PİRAZİZ", "ŞEBİNKARAHİSAR", "TİREBOLU", "YAĞLIDERE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[34])) {
                    String[] adana = {"GÜMÜŞHANE", "KELKİT", "KÖSE", "KÜRTÜN", "ŞİRAN", "TORUL"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[35])) {
                    String[] adana = {"HAKKARİ", "ÇUKURCA", "ŞEMDİNLİ", "YÜKSEKOVA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[36])) {
                    String[] adana = {"ANTAKYA", "ALTINÖZÜ", "BELEN", "DÖRTYOL", "ERZİN", "HASSA", "İSKENDERUN", "KIRIKHAN", "KUMLU", "REYHANLI", "SAMANDAĞ", "YAYLADAĞI"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[37])) {
                    String[] adana = {"IĞDIR", "ARALIK", "KARAKOYUNLU", "TUZLUCA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[38])) {
                    String[] adana = {"ISPARTA", "AKSU", "ATABEY", "EĞRİDİR(EĞİRDİR)", "GELENDOST", "GÖNEN", "KEÇİBORLU", "SENİRKENT", "SÜTÇÜLER", "ŞARKİKARAAĞAÇ", "ULUBORLU", "YALVAÇ", "YENİŞARBADEMLİ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[39])) {
                    String[] adana = {"BAKIRKÖY", "BAYRAMPAŞA", "BEŞİKTAŞ", "BEYOĞLU", "ARNAVUTKÖY", "EYÜP", "FATİH", "GAZİOSMANPAŞA", "KAĞITHANE", "KÜÇÜKÇEKMECE", "SARIYER", "ŞİŞLİ", "ZEYTİNBURNU", "AVCILAR", "GÜNGÖREN", "BAHÇELİEVLER", "BAĞCILAR", "ESENLER", "BAŞAKŞEHİR", "BEYLİKDÜZÜ", "ESENYURT", "SULTANGAZİ", "ADALAR", "BEYKOZ", "KADIKÖY", "KARTAL", "PENDİK", "ÜMRANİYE", "ÜSKÜDAR", "TUZLA", "MALTEPE", "ATAŞEHİR", "ÇEKMEKÖY", "SANCAKTEPE", "BÜYÜKÇEKMECE", "ÇATALCA", "SİLİVRİ", "ŞİLE", "SULTANBEYLİ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[40])) {
                    String[] adana = {"ALİAĞA", "BALÇOVA", "BAYINDIR", "BORNOVA", "BUCA", "ÇİĞLİ", "FOÇA", "GAZİEMİR", "GÜZELBAHÇE", "KARŞIYAKA", "KEMALPAŞA", "KONAK", "CUMAOVASI(MENDERES)", "MENEMEN", "NARLIDERE", "SEFERİHİSAR", "SELÇUK", "TORBALI", "URLA", "BAYRAKLI", "KARABAĞLAR", "BERGAMA", "BEYDAĞ", "ÇEŞME", "DİKİLİ", "KARABURUN", "KINIK", "KİRAZ", "ÖDEMİŞ", "TİRE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[41])) {
                    String[] adana = {"KAHRAMANMARAŞ", "AFŞİN", "ANDIRIN", "ÇAĞLAYANCERİT", "EKİNÖZÜ", "ELBİSTAN", "GÖKSUN", "NURHAK", "PAZARCIK", "TÜRKOĞLU"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[42])) {
                    String[] adana = {"KARABÜK", "EFLANİ", "ESKİPAZAR", "OVACIK", "SAFRANBOLU", "YENİCE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[43])) {
                    String[] adana = {"KARAMAN", "AYRANCI", "BAŞYAYLA", "ERMENEK", "KAZIMKARABEKİR", "SARIVELİLER"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[44])) {
                    String[] adana = {"KARS", "AKYAKA", "ARPAÇAY", "DİGOR", "KAĞIZMAN", "SARIKAMIŞ", "SELİM", "SUSUZ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[45])) {
                    String[] adana = {"KASTAMONU", "ABANA", "AĞLI", "ARAÇ", "AZDAVAY", "BOZKURT", "CİDE", "ÇATALZEYTİN", "DADAY", "DEVREKANİ", "DOĞANYURT", "HANÖNÜ(GÖKÇEAĞAÇ)", "İHSANGAZİ", "İNEBOLU", "KÜRE", "PINARBAŞI", "SEYDİLER", "ŞENPAZAR", "TAŞKÖPRÜ", "TOSYA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[46])) {
                    String[] adana = {"KOCASİNAN", "MELİKGAZİ", "TALAS", "AKKIŞLA", "BÜNYAN", "DEVELİ", "FELAHİYE", "HACILAR", "İNCESU", "ÖZVATAN(ÇUKUR)", "PINARBAŞI", "SARIOĞLAN", "SARIZ", "TOMARZA", "YAHYALI", "YEŞİLHİSAR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[47])) {
                    String[] adana = {"KIRIKKALE", "BAHŞİLİ", "BALIŞEYH", "ÇELEBİ", "DELİCE", "KARAKEÇİLİ", "KESKİN", "SULAKYURT", "YAHŞİHAN"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[48])) {
                    String[] adana = {"KIRKLARELİ", "BABAESKİ", "DEMİRKÖY", "KOFÇAZ", "LÜLEBURGAZ", "PEHLİVANKÖY", "PINARHİSAR", "VİZE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[49])) {
                    String[] adana = {"KIRŞEHİR", "AKÇAKENT", "AKPINAR", "BOZTEPE", "ÇİÇEKDAĞI", "KAMAN", "MUCUR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[50])) {
                    String[] adana = {"KİLİS", "ELBEYLİ", "MUSABEYLİ", "POLATELİ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[51])) {
                    String[] adana = {"İZMİT", "BAŞİSKELE", "ÇAYIROVA", "DARICA", "DİLOVASI", "KARTEPE", "GEBZE", "GÖLCÜK", "KANDIRA", "KARAMÜRSEL", "TÜTÜNÇİFTLİK", "DERİNCE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[52])) {
                    String[] adana = {"KARATAY", "MERAM", "SELÇUKLU", "AHIRLI", "AKÖREN", "AKŞEHİR", "ALTINEKİN", "BEYŞEHİR", "BOZKIR", "CİHANBEYLİ", "ÇELTİK", "ÇUMRA", "DERBENT", "DEREBUCAK", "DOĞANHİSAR", "EMİRGAZİ", "EREĞLİ", "GÜNEYSINIR", "HADİM", "HALKAPINAR", "HÜYÜK", "ILGIN", "KADINHANI", "KARAPINAR", "KULU", "SARAYÖNÜ", "SEYDİŞEHİR", "TAŞKENT", "TUZLUKÇU", "YALIHÜYÜK", "YUNAK"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[53])) {
                    String[] adana = {"KÜTAHYA", "ALTINTAŞ", "ASLANAPA", "ÇAVDARHİSAR", "DOMANİÇ", "DUMLUPINAR", "EMET", "GEDİZ", "HİSARCIK", "PAZARLAR", "SİMAV", "ŞAPHANE", "TAVŞANLI", "TUNÇBİLEK"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[54])) {
                    String[] adana = {"MALATYA", "AKÇADAĞ", "ARAPKİR", "ARGUVAN", "BATTALGAZİ", "DARENDE", "DOĞANŞEHİR", "DOĞANYOL", "HEKİMHAN", "KALE", "KULUNCAK", "PÖTÜRGE", "YAZIHAN", "YEŞİLYURT"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[55])) {
                    String[] adana = {"MANİSA", "AHMETLİ", "AKHİSAR", "ALAŞEHİR", "DEMİRCİ", "GÖLMARMARA", "GÖRDES", "KIRKAĞAÇ", "KÖPRÜBAŞI", "KULA", "SALİHLİ", "SARIGÖL", "SARUHANLI", "SELENDİ", "SOMA", "TURGUTLU"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[56])) {
                    String[] adana = {"MARDİN", "DARGEÇİT", "DERİK", "KIZILTEPE", "MAZIDAĞI", "MİDYAT(ESTEL)", "NUSAYBİN", "ÖMERLİ", "SAVUR", "YEŞİLLİ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[57])) {
                    String[] adana = {"AKDENİZ", "YENİŞEHİR", "TOROSLAR", "MEZİTLİ", "ANAMUR", "AYDINCIK", "BOZYAZI", "ÇAMLIYAYLA", "ERDEMLİ", "GÜLNAR(GÜLPINAR)", "MUT", "SİLİFKE", "TARSUS"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[58])) {
                    String[] adana = {"MUĞLA", "BODRUM", "DALAMAN", "DATÇA", "FETHİYE", "KAVAKLIDERE", "KÖYCEĞİZ", "MARMARİS", "MİLAS", "ORTACA", "ULA", "YATAĞAN"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[59])) {
                    String[] adana = {"MUŞ", "BULANIK", "HASKÖY", "KORKUT", "MALAZGİRT", "VARTO"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[60])) {
                    String[] adana = {"NEVŞEHİR", "ACIGÖL", "AVANOS", "DERİNKUYU", "GÜLŞEHİR", "HACIBEKTAŞ", "KOZAKLI", "ÜRGÜP"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[61])) {
                    String[] adana = {"NİĞDE", "ALTUNHİSAR", "BOR", "ÇAMARDI", "ÇİFTLİK(ÖZYURT)", "ULUKIŞLA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[62])) {
                    String[] adana = {"ORDU", "AKKUŞ", "AYBASTI", "ÇAMAŞ", "ÇATALPINAR", "ÇAYBAŞI", "FATSA", "GÖLKÖY", "GÜLYALI", "GÜRGENTEPE", "İKİZCE", "KARADÜZ(KABADÜZ)", "KABATAŞ", "KORGAN", "KUMRU", "MESUDİYE", "PERŞEMBE", "ULUBEY", "ÜNYE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[63])) {
                    String[] adana = {"OSMANİYE", "BAHÇE", "DÜZİÇİ", "HASANBEYLİ", "KADİRLİ", "SUMBAS", "TOPRAKKALE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[64])) {
                    String[] adana = {"RİZE", "ARDEŞEN", "ÇAMLIHEMŞİN", "ÇAYELİ", "DEREPAZARI", "FINDIKLI", "GÜNEYSU", "HEMŞİN", "İKİZDERE", "İYİDERE", "KALKANDERE", "PAZAR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[65])) {
                    String[] adana = {"ADAPAZARI", "HENDEK", "ARİFİYE", "ERENLER", "SERDİVAN", "AKYAZI", "FERİZLİ", "GEYVE", "KARAPÜRÇEK", "KARASU", "KAYNARCA", "KOCAALİ", "PAMUKOVA", "SAPANCA", "SÖĞÜTLÜ", "TARAKLI"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[66])) {
                    String[] adana = {"ATAKUM", "İLKADIM", "CANİK", "TEKKEKÖY", "ALAÇAM", "ASARCIK", "AYVACIK", "BAFRA", "ÇARŞAMBA", "HAVZA", "KAVAK", "LADİK", "19MAYIS(BALLICA)", "SALIPAZARI", "TERME", "VEZİRKÖPRÜ", "YAKAKENT"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[67])) {
                    String[] adana = {"SİİRT", "BAYKAN", "ERUH", "KURTALAN", "PERVARİ", "AYDINLAR", "ŞİRVAN"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[68])) {
                    String[] adana = {"SİNOP", "AYANCIK", "BOYABAT", "DİKMEN", "DURAĞAN", "ERFELEK", "GERZE", "SARAYDÜZÜ", "TÜRKELİ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[69])) {
                    String[] adana = {"SİVAS", "AKINCILAR", "ALTINYAYLA", "DİVRİĞİ", "DOĞANŞAR", "GEMEREK", "GÖLOVA", "GÜRÜN", "HAFİK", "İMRANLI", "KANGAL", "KOYULHİSAR", "SUŞEHRİ", "ŞARKIŞLA", "ULAŞ", "YILDIZELİ", "ZARA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[70])) {
                    String[] adana = {"ŞANLIURFA", "AKÇAKALE", "BİRECİK", "BOZOVA", "CEYLANPINAR", "HALFETİ", "HARRAN", "HİLVAN", "SİVEREK", "SURUÇ", "VİRANŞEHİR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[71])) {
                    String[] adana = {"ŞIRNAK", "BEYTÜŞŞEBAP", "CİZRE", "GÜÇLÜKONAK", "İDİL", "SİLOPİ", "ULUDERE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[72])) {
                    String[] adana = {"TEKİRDAĞ", "ÇERKEZKÖY", "ÇORLU", "HAYRABOLU", "MALKARA", "MARMARAEREĞLİSİ", "MURATLI", "SARAY", "ŞARKÖY"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[73])) {
                    String[] adana = {"TOKAT", "ALMUS", "ARTOVA", "BAŞÇİFTLİK", "ERBAA", "NİKSAR", "PAZAR", "REŞADİYE", "SULUSARAY", "TURHAL", "YEŞİLYURT", "ZİLE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[74])) {
                    String[] adana = {"TRABZON", "AKÇAABAT", "ARAKLI", "ARSİN", "BEŞİKDÜZÜ", "ÇARŞIBAŞI", "ÇAYKARA", "DERNEKPAZARI", "DÜZKÖY", "HAYRAT", "KÖPRÜBAŞI", "MAÇKA", "OF", "SÜRMENE", "ŞALPAZARI", "TONYA", "VAKFIKEBİR", "YOMRA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[75])) {
                    String[] adana = {"TUNCELİ", "ÇEMİŞGEZEK", "HOZAT", "MAZGİRT", "NAZIMİYE", "OVACIK", "PERTEK", "PÜLÜMÜR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[76])) {
                    String[] adana = {"UŞAK", "BANAZ", "EŞME", "KARAHALLI", "SİVASLI", "ULUBEY"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[77])) {
                    String[] adana = {"VAN", "BAHÇESARAY", "BAŞKALE", "ÇALDIRAN", "ÇATAK", "EDREMİT(GÜMÜŞDERE)", "ERCİŞ", "GEVAŞ", "GÜRPINAR", "MURADİYE", "ÖZALP", "SARAY"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[78])) {
                    String[] adana = {"YALOVA", "ALTINOVA", "ARMUTLU", "ÇİFTLİKKÖY", "ÇINARCIK", "TERMAL"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[79])) {
                    String[] adana = {"YOZGAT", "AKDAĞMADENİ", "AYDINCIK", "BOĞAZLIYAN", "ÇANDIR", "ÇAYIRALAN", "ÇEKEREK", "KADIŞEHRİ", "SARAYKENT", "SARIKAYA", "SORGUN", "ŞEFAATLİ", "YENİFAKILI", "YERKÖY"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[80])) {
                    String[] adana = {"ZONGULDAK", "ALAPLI", "ÇAYCUMA", "DEVREK", "KARADENİZEREĞLİ", "GÖKÇEBEY"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtrele.setVisibility(View.GONE);
                linearList.setVisibility(View.VISIBLE);
                enazFiyat.setText("");
                encokFiyat.setText("");
                spinnerKategoriler.setSelection(0);
                spinnerIller.setSelection(81);
                checkBox.setChecked(false);
                String bosilce[] = {""};
                dataAdapterForIlceSayisi = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, bosilce);
                dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                spinnerIlceler.setSelection(0);
            }
        });


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String bosilce[] = {""};
                    spinnerIlceler.setEnabled(false);
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, bosilce);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                    spinnerIlceler.setSelection(0);
                } else {
                    spinnerIlceler.setEnabled(true);
                    String[] adana = {"SEYHAN", "YÜREĞİR", "SARIÇAM", "ÇUKUROVA", "ALADAĞ(KARSANTI)", "CEYHAN", "FEKE", "İMAMOĞLU", "KARAİSALI", "KARATAŞ", "KOZAN", "POZANTI", "SAİMBEYLİ", "TUFANBEYLİ", "YUMURTALIK"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                    spinnerIller.setSelection(0);

                }
            }
        });

        btnGoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kategori = spinnerKategoriler.getSelectedItem().toString();
                String il = spinnerIller.getSelectedItem().toString();
                String ilce = spinnerIlceler.getSelectedItem().toString();
                String fiyat1 = enazFiyat.getText().toString();
                String fiyat2 = encokFiyat.getText().toString();

                if (enazFiyat.getText().length() == 0) {
                    enazFiyat.setError("Boş geçemezsiniz");
                }
                if (encokFiyat.getText().length() == 0) {
                    encokFiyat.setError("Boş geçemezsiniz");
                } else {
                    filtrele.setVisibility(View.GONE);
                    filtrele(kategori, il, ilce, fiyat1, fiyat2);
                }
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), IlanDetayActivity.class);
                intent.putExtra("ilan_id", list.get(position).getIlanid());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        filtreleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), IlanDetayActivity.class);
                intent.putExtra("ilan_id", list.get(position).getIlanid());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        ilanSirala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siralaAlertDialog();

            }
        });

        ilanFiltrele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtrele.setVisibility(View.VISIBLE);
                linearList.setVisibility(View.GONE);
            }
        });

        ilanFiltreleKaldir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 1;
                listView.setVisibility(View.VISIBLE);
                filtreleListView.setVisibility(View.GONE);
                ilanlarimiGoruntuleArkaPlan(sayfa);
                ilanFiltreleKaldir.setVisibility(View.GONE);
                ilanFiltrele.setVisibility(View.VISIBLE);
                enazFiyat.setText("");
                encokFiyat.setText("");
                spinnerKategoriler.setSelection(0);
                spinnerIller.setSelection(81);
                checkBox.setChecked(false);
                String bosilce[] = {""};
                dataAdapterForIlceSayisi = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, bosilce);
                dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                spinnerIlceler.setSelection(0);
                filtreText.setText("Filtrele");
            }
        });
        btnTemizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enazFiyat.setText("");
                encokFiyat.setText("");
                spinnerKategoriler.setSelection(0);
                spinnerIller.setSelection(81);
                checkBox.setChecked(false);
                String bosilce[] = {""};
                dataAdapterForIlceSayisi = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, bosilce);
                dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                spinnerIlceler.setSelection(0);
            }
        });

        return view;
    }

    public void filtrele(String kategori, String il, String ilce, String fiyat1, String fiyat2) {
        filtreleListView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("İlanlar yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<List<TumIlanlarPojo>> x = ManagerAll.getInstance().ilanFiltreleme(kategori, il, ilce, fiyat1, fiyat2);
        x.enqueue(new Callback<List<TumIlanlarPojo>>() {
            @Override
            public void onResponse(Call<List<TumIlanlarPojo>> call, Response<List<TumIlanlarPojo>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    if (response.body().get(0).isTf()) {
                        tumIlanlarAdapter = new TumIlanlarAdapter(list, getContext());
                        filtreleListView.setAdapter(tumIlanlarAdapter);

                        progressDialog.cancel();
                        linearList.setVisibility(View.VISIBLE);
                        ilanFiltrele.setVisibility(View.GONE);
                        ilanFiltreleKaldir.setVisibility(View.VISIBLE);
                        filtreText.setText("Temizle");
                        Toast.makeText(getActivity(), "Toplam " + response.body().get(0).getSayi() + " adet sonuç listelendi.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        progressDialog.cancel();
                        filtrele.setVisibility(View.VISIBLE);
                        linearList.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Aradığınız kriterlere uygun sonuç bulunamadı.",
                                Toast.LENGTH_LONG).show();
                        ilanlarimiGoruntuleArkaPlan(sayfa);

                    }

                } else {
                    progressDialog.cancel();

                    Toast.makeText(getActivity(), "Sonuçlar listelenemedi.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<TumIlanlarPojo>> call, Throwable t) {

            }
        });
    }

    public void siralaAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.ilan_sirala_secenekler, null);

        TextView vazgec;
        vazgec = (TextView) view.findViewById(R.id.vazgec);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(false);
        final AlertDialog dialog = alert.create();
        dialog.show();
        dialog.getWindow().setLayout(1100, 1100);
        vazgec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        final RadioGroup sirala;
        sirala = (RadioGroup) view.findViewById(R.id.radioGroup);
        sirala.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = sirala.findViewById(checkedId);
                int index = sirala.indexOfChild(radioButton);
                switch (index) {
                    case 0:
                        dialog.cancel();
                        ilanlarimiGoruntule(sayfa);

                    case 1:
                        dialog.cancel();
                        EnYuksekFiyat();
                        break;
                    case 2:
                        dialog.cancel();
                        EnDusukFiyat();
                        break;
                    case 3:
                        dialog.cancel();
                        onceEnyeni();
                        break;
                    case 4:
                        dialog.cancel();
                        onceEneski();
                        break;
                }
            }
        });

    }

    public void EnYuksekFiyat() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("İlanlar yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<List<TumIlanlarPojo>> x = ManagerAll.getInstance().onceEnyuksekFiyatGetir();
        x.enqueue(new Callback<List<TumIlanlarPojo>>() {
            @Override
            public void onResponse(Call<List<TumIlanlarPojo>> call, Response<List<TumIlanlarPojo>> response) {
                if (response.isSuccessful()) {
                    if (response.body().get(0).isTf()) {
                        list = response.body();
                        tumIlanlarAdapter = new TumIlanlarAdapter(list, getContext());
                        listView.setAdapter(tumIlanlarAdapter);
                        progressDialog.cancel();
                    } else {
                        progressDialog.cancel();

                    }
                }
            }

            @Override
            public void onFailure(Call<List<TumIlanlarPojo>> call, Throwable t) {

            }
        });

    }

    public void EnDusukFiyat() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("İlanlar yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<List<TumIlanlarPojo>> x = ManagerAll.getInstance().onceEndusukFiyatGetir();
        x.enqueue(new Callback<List<TumIlanlarPojo>>() {
            @Override
            public void onResponse(Call<List<TumIlanlarPojo>> call, Response<List<TumIlanlarPojo>> response) {
                if (response.isSuccessful()) {
                    if (response.body().get(0).isTf()) {
                        list = response.body();
                        tumIlanlarAdapter = new TumIlanlarAdapter(list, getContext());
                        listView.setAdapter(tumIlanlarAdapter);
                        progressDialog.cancel();
                    } else {
                        progressDialog.cancel();

                    }
                }
            }

            @Override
            public void onFailure(Call<List<TumIlanlarPojo>> call, Throwable t) {

            }
        });

    }

    public void onceEnyeni() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("İlanlar yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<List<TumIlanlarPojo>> x = ManagerAll.getInstance().onceEnYeni();
        x.enqueue(new Callback<List<TumIlanlarPojo>>() {
            @Override
            public void onResponse(Call<List<TumIlanlarPojo>> call, Response<List<TumIlanlarPojo>> response) {
                if (response.isSuccessful()) {
                    if (response.body().get(0).isTf()) {
                        list = response.body();
                        tumIlanlarAdapter = new TumIlanlarAdapter(list, getContext());
                        listView.setAdapter(tumIlanlarAdapter);
                        progressDialog.cancel();
                    } else {
                        progressDialog.cancel();

                    }
                }
            }

            @Override
            public void onFailure(Call<List<TumIlanlarPojo>> call, Throwable t) {

            }
        });

    }

    public void onceEneski() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("İlanlar yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<List<TumIlanlarPojo>> x = ManagerAll.getInstance().onceEnEski();
        x.enqueue(new Callback<List<TumIlanlarPojo>>() {
            @Override
            public void onResponse(Call<List<TumIlanlarPojo>> call, Response<List<TumIlanlarPojo>> response) {
                if (response.isSuccessful()) {
                    if (response.body().get(0).isTf()) {
                        list = response.body();
                        tumIlanlarAdapter = new TumIlanlarAdapter(list, getContext());
                        listView.setAdapter(tumIlanlarAdapter);
                        progressDialog.cancel();
                    } else {
                        progressDialog.cancel();

                    }
                }
            }

            @Override
            public void onFailure(Call<List<TumIlanlarPojo>> call, Throwable t) {

            }
        });

    }


    public void ilanlarimiGoruntule(Integer sayfa) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("İlanlar yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<List<TumIlanlarPojo>> x = ManagerAll.getInstance().tumIlanlarGetir(sayfa);
        x.enqueue(new Callback<List<TumIlanlarPojo>>() {
            @Override
            public void onResponse(Call<List<TumIlanlarPojo>> call, Response<List<TumIlanlarPojo>> response) {
                if (response.isSuccessful()) {
                    if (response.body().get(0).isTf()) {
                        list = response.body();
                        sayfa_sayisi = response.body().get(0).getSayi();
                        tumIlanlarAdapter = new TumIlanlarAdapter(list, getContext());
                        listView.setAdapter(tumIlanlarAdapter);
                        progressDialog.cancel();
                    } else {
                        progressDialog.cancel();

                    }
                }
            }

            @Override
            public void onFailure(Call<List<TumIlanlarPojo>> call, Throwable t) {

            }
        });
    }

    public void ilanlarimiGoruntuleArkaPlan(Integer sayfa) {
        Call<List<TumIlanlarPojo>> x = ManagerAll.getInstance().tumIlanlarGetir(sayfa);
        x.enqueue(new Callback<List<TumIlanlarPojo>>() {
            @Override
            public void onResponse(Call<List<TumIlanlarPojo>> call, Response<List<TumIlanlarPojo>> response) {
                if (response.isSuccessful()) {
                    if (response.body().get(0).isTf()) {
                        list = response.body();
                        tumIlanlarAdapter = new TumIlanlarAdapter(list, getContext());
                        listView.setAdapter(tumIlanlarAdapter);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<TumIlanlarPojo>> call, Throwable t) {

            }
        });
    }

    public void gecisReklami() {
        MobileAds.initialize(getContext(), "ca-app-pub-6261653662849942~5050468043");

        mInterstitialAd = new InterstitialAd(getContext());

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

    public void ilanBilgileriTemizle() {

        IlanVerPojo.setKategori("");
        IlanVerPojo.setBaslik("");
        IlanVerPojo.setAciklama("");
        IlanVerPojo.setFiyat("");
        IlanVerPojo.setNetMetre("");
        IlanVerPojo.setBrutMetre("");
        IlanVerPojo.setOdaSayisi("");
        IlanVerPojo.setBulunduguKat("");
        IlanVerPojo.setKatSayisi("");
        IlanVerPojo.setIsitma("");
        IlanVerPojo.setBanyoSayisi("");
        IlanVerPojo.setIl("");
        IlanVerPojo.setMahalle("");
        IlanVerPojo.setSiteIcindemi("");
    }


}