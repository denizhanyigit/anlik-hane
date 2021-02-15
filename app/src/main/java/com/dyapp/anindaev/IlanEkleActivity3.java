package com.dyapp.anindaev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dyapp.anindaev.Models.IlanVerPojo;
import com.dyapp.anindaev.Models.IlanVermePojo;
import com.dyapp.anindaev.RestApi.ManagerAll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IlanEkleActivity3 extends AppCompatActivity {

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

    TextView btnIleri, btnGeri;
    EditText mahalle;
    RadioGroup site;
    SharedPreferences sharedPreferences;
    private RadioButton siteCevap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_ekle3);
        tanimla();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinnerIller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getSelectedItem().toString().equals(iller[0])) {
                    String[] adana = {"SEYHAN", "YÜREĞİR", "SARIÇAM", "ÇUKUROVA", "ALADAĞ(KARSANTI)", "CEYHAN", "FEKE", "İMAMOĞLU", "KARAİSALI", "KARATAŞ", "KOZAN", "POZANTI", "SAİMBEYLİ", "TUFANBEYLİ", "YUMURTALIK"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[1])) {
                    String[] adana = {"ADIYAMAN", "BESNİ", "ÇELİKHAN", "GERGER", "GÖLBAŞI", "KAHTA", "SAMSAT", "SİNCİK", "TUT"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[2])) {
                    String[] adana = {"AFYONKARAHİSAR", "BAŞMAKÇI", "BAYAT", "BOLVADİN", "ÇAY", "ÇOBANLAR", "DAZKIRI", "DİNAR", "EMİRDAĞ", "EVCİLER", "HOCALAR", "İHSANİYE", "İSCEHİSAR", "KIZILÖREN", "SANDIKLI", "SİNCANLI(SİNANPAŞA)", "SULTANDAĞI", "ŞUHUT"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[3])) {
                    String[] adana = {"AĞRI", "DİYADİN", "DOĞUBEYAZIT", "ELEŞKİRT", "HAMUR", "PATNOS", "TAŞLIÇAY", "TUTAK"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[4])) {
                    String[] adana = {"AKSARAY", "AĞAÇÖREN", "ESKİL", "GÜLAĞAÇ(AĞAÇLI)", "GÜZELYURT", "ORTAKÖY", "SARIYAHŞİ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[5])) {
                    String[] adana = {"AMASYA", "GÖYNÜCEK", "GÜMÜŞHACIKÖY", "HAMAMÖZÜ", "MERZİFON", "SULUOVA", "TAŞOVA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);

                }
                if (parent.getSelectedItem().toString().equals(iller[6])) {
                    String[] adana = {"ALTINDAĞ", "ÇANKAYA", "ETİMESGUT", "KEÇİÖREN", "MAMAK", "SİNCAN", "YENİMAHALLE", "GÖLBAŞI", "PURSAKLAR", "AKYURT", "AYAŞ", "BALA", "BEYPAZARI", "ÇAMLIDERE", "ÇUBUK", "ELMADAĞ", "EVREN", "GÜDÜL", "HAYMANA", "KALECİK", "KAZAN", "KIZILCAHAMAM", "NALLIHAN", "POLATLI", "ŞEREFLİKOÇHİSAR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);

                }
                if (parent.getSelectedItem().toString().equals(iller[7])) {
                    String[] adana = {"MURATPAŞA", "KEPEZ", "KONYAALTI", "AKSU", "DÖŞEMEALTI", "AKSEKİ", "ALANYA", "ELMALI", "FİNİKE", "GAZİPAŞA", "GÜNDOĞMUŞ", "İBRADI(AYDINKENT)", "KALE(DEMRE)", "KAŞ", "KEMER", "KORKUTELİ", "KUMLUCA", "MANAVGAT", "SERİK"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[8])) {
                    String[] adana = {"ARDAHAN", "ÇILDIR", "DAMAL", "GÖLE", "HANAK", "POSOF"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[9])) {
                    String[] adana = {"ARTVİN", "ARDANUÇ", "ARHAVİ", "BORÇKA", "HOPA", "MURGUL(GÖKTAŞ)", "ŞAVŞAT", "YUSUFELİ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[10])) {
                    String[] adana = {"AYDIN", "BOZDOĞAN", "BUHARKENT(ÇUBUKDAĞI)", "ÇİNE", "GERMENCİK", "İNCİRLİOVA", "KARACASU", "KARPUZLU", "KOÇARLI", "KÖŞK", "KUŞADASI", "KUYUCAK", "NAZİLLİ", "SÖKE", "SULTANHİSAR", "DİDİM(YENİHİSAR)", "YENİPAZAR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[11])) {
                    String[] adana = {"BALIKESİR", "AYVALIK", "BALYA", "BANDIRMA", "BİGADİÇ", "BURHANİYE", "DURSUNBEY", "EDREMİT", "ERDEK", "GÖMEÇ", "GÖNEN", "HAVRAN", "İVRİNDİ", "KEPSUT", "MANYAS", "MARMARA", "SAVAŞTEPE", "SINDIRGI", "SUSURLUK"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[12])) {
                    String[] adana = {"BARTIN", "AMASRA", "KURUCAŞİLE", "ULUS"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[13])) {
                    String[] adana = {"BATMAN", "BEŞİRİ", "GERCÜŞ", "HASANKEYF", "KOZLUK", "SASON"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[14])) {
                    String[] adana = {"BAYBURT", "AYDINTEPE", "DEMİRÖZÜ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[15])) {
                    String[] adana = {"BİLECİK", "BOZÜYÜK", "GÖLPAZARI", "İNHİSAR", "OSMANELİ", "PAZARYERİ", "SÖĞÜT", "YENİPAZAR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[16])) {
                    String[] adana = {"BİNGÖL", "ADAKLI", "GENÇ", "KARLIOVA", "KIĞI", "SOLHAN", "YAYLADERE", "YEDİSU"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[17])) {
                    String[] adana = {"BİTLİS", "ADİLCEVAZ", "AHLAT", "GÜROYMAK", "HİZAN", "MUTKİ", "TATVAN"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[18])) {
                    String[] adana = {"BOLU", "DÖRTDİVAN", "GEREDE", "GÖYNÜK", "KIBRISCIK", "MENGEN", "MUDURNU", "SEBEN", "YENİÇAĞA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[19])) {
                    String[] adana = {"BURDUR", "AĞLASUN", "ALTINYAYLA(DİRMİL)", "BUCAK", "ÇAVDIR", "ÇELTİKÇİ", "GÖLHİSAR", "KARAMANLI", "KEMER", "TEFENNİ", "YEŞİLOVA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[20])) {
                    String[] adana = {"NİLÜFER", "OSMANGAZİ", "YILDIRIM", "BÜYÜKORHAN", "GEMLİK", "GÜRSU", "HARMANCIK", "İNEGÖL", "İZNİK", "KARACABEY", "KELES", "KESTEL", "MUDANYA", "MUSTAFAKEMALPAŞA", "ORHANELİ", "ORHANGAZİ", "YENİŞEHİR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[21])) {
                    String[] adana = {"ÇANAKKALE", "AYVACIK", "BAYRAMİÇ", "BİGA", "BOZCAADA", "ÇAN", "ECEABAT", "EZİNE", "GELİBOLU", "GÖKÇEADA(İMROZ)", "LAPSEKİ", "YENİCE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[22])) {
                    String[] adana = {"ÇANKIRI", "ATKARACALAR", "BAYRAMÖREN", "ÇERKEŞ", "ELDİVAN", "ILGAZ", "KIZILIRMAK", "KORGUN", "KURŞUNLU", "ORTA", "ŞABANÖZÜ", "YAPRAKLI"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[23])) {
                    String[] adana = {"ÇORUM", "ALACA", "BAYAT", "BOĞAZKALE", "DODURGA", "İSKİLİP", "KARGI", "LAÇİN", "MECİTÖZÜ", "OĞUZLAR(KARAÖREN)", "ORTAKÖY", "OSMANCIK", "SUNGURLU", "UĞURLUDAĞ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[24])) {
                    String[] adana = {"DENİZLİ", "ACIPAYAM", "AKKÖY", "BABADAĞ", "BAKLAN", "BEKİLLİ", "BEYAĞAÇ", "BOZKURT", "BULDAN", "ÇAL", "ÇAMELİ", "ÇARDAK", "ÇİVRİL", "GÜNEY", "HONAZ", "KALE", "SARAYKÖY", "SERİNHİSAR", "TAVAS"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[25])) {
                    String[] adana = {"SUR", "BAĞLAR", "YENİŞEHİR", "KAYAPINAR", "BİSMİL", "ÇERMİK", "ÇINAR", "ÇÜNGÜŞ", "DİCLE", "EĞİL", "ERGANİ", "HANİ", "HAZRO", "KOCAKÖY", "KULP", "LİCE", "SİLVAN"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[26])) {
                    String[] adana = {"DÜZCE", "AKÇAKOCA", "CUMAYERİ", "ÇİLİMLİ", "GÖLYAKA", "GÜMÜŞOVA", "KAYNAŞLI", "YIĞILCA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[27])) {
                    String[] adana = {"EDİRNE", "ENEZ", "HAVSA", "İPSALA", "KEŞAN", "LALAPAŞA", "MERİÇ", "SÜLEOĞLU(SÜLOĞLU)", "UZUNKÖPRÜ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[28])) {
                    String[] adana = {"ELAZIĞ", "AĞIN", "ALACAKAYA", "ARICAK", "BASKİL", "KARAKOÇAN", "KEBAN", "KOVANCILAR", "MADEN", "PALU", "SİVRİCE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[29])) {
                    String[] adana = {"ERZİNCAN", "ÇAYIRLI", "İLİÇ(ILIÇ)", "KEMAH", "KEMALİYE", "OTLUKBELİ", "REFAHİYE", "TERCAN", "ÜZÜMLÜ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[30])) {
                    String[] adana = {"PALANDÖKEN", "YAKUTİYE", "AZİZİYE(ILICA)", "AŞKALE", "ÇAT", "HINIS", "HORASAN", "İSPİR", "KARAÇOBAN", "KARAYAZI", "KÖPRÜKÖY", "NARMAN", "OLTU", "OLUR", "PASİNLER", "PAZARYOLU", "ŞENKAYA", "TEKMAN", "TORTUM", "UZUNDERE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[31])) {
                    String[] adana = {"ODUNPAZARI", "TEPEBAŞI", "ALPU", "BEYLİKOVA", "ÇİFTELER", "GÜNYÜZÜ", "HAN", "İNÖNÜ", "MAHMUDİYE", "MİHALGAZİ", "MİHALIÇCIK", "SARICAKAYA", "SEYİTGAZİ", "SİVRİHİSAR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[32])) {
                    String[] adana = {"ŞAHİNBEY", "ŞEHİTKAMİL", "OĞUZELİ", "ARABAN", "İSLAHİYE", "KARKAMIŞ", "NİZİP", "NURDAĞI", "YAVUZELİ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[33])) {
                    String[] adana = {"GİRESUN", "ALUCRA", "BULANCAK", "ÇAMOLUK", "ÇANAKÇI", "DERELİ", "DOĞANKENT", "ESPİYE", "EYNESİL", "GÖRELE", "GÜCE", "KEŞAP", "PİRAZİZ", "ŞEBİNKARAHİSAR", "TİREBOLU", "YAĞLIDERE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[34])) {
                    String[] adana = {"GÜMÜŞHANE", "KELKİT", "KÖSE", "KÜRTÜN", "ŞİRAN", "TORUL"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[35])) {
                    String[] adana = {"HAKKARİ", "ÇUKURCA", "ŞEMDİNLİ", "YÜKSEKOVA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[36])) {
                    String[] adana = {"ANTAKYA", "ALTINÖZÜ", "BELEN", "DÖRTYOL", "ERZİN", "HASSA", "İSKENDERUN", "KIRIKHAN", "KUMLU", "REYHANLI", "SAMANDAĞ", "YAYLADAĞI"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[37])) {
                    String[] adana = {"IĞDIR", "ARALIK", "KARAKOYUNLU", "TUZLUCA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[38])) {
                    String[] adana = {"ISPARTA", "AKSU", "ATABEY", "EĞRİDİR(EĞİRDİR)", "GELENDOST", "GÖNEN", "KEÇİBORLU", "SENİRKENT", "SÜTÇÜLER", "ŞARKİKARAAĞAÇ", "ULUBORLU", "YALVAÇ", "YENİŞARBADEMLİ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[39])) {
                    String[] adana = {"BAKIRKÖY", "BAYRAMPAŞA", "BEŞİKTAŞ", "BEYOĞLU", "ARNAVUTKÖY", "EYÜP", "FATİH", "GAZİOSMANPAŞA", "KAĞITHANE", "KÜÇÜKÇEKMECE", "SARIYER", "ŞİŞLİ", "ZEYTİNBURNU", "AVCILAR", "GÜNGÖREN", "BAHÇELİEVLER", "BAĞCILAR", "ESENLER", "BAŞAKŞEHİR", "BEYLİKDÜZÜ", "ESENYURT", "SULTANGAZİ", "ADALAR", "BEYKOZ", "KADIKÖY", "KARTAL", "PENDİK", "ÜMRANİYE", "ÜSKÜDAR", "TUZLA", "MALTEPE", "ATAŞEHİR", "ÇEKMEKÖY", "SANCAKTEPE", "BÜYÜKÇEKMECE", "ÇATALCA", "SİLİVRİ", "ŞİLE", "SULTANBEYLİ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[40])) {
                    String[] adana = {"ALİAĞA", "BALÇOVA", "BAYINDIR", "BORNOVA", "BUCA", "ÇİĞLİ", "FOÇA", "GAZİEMİR", "GÜZELBAHÇE", "KARŞIYAKA", "KEMALPAŞA", "KONAK", "CUMAOVASI(MENDERES)", "MENEMEN", "NARLIDERE", "SEFERİHİSAR", "SELÇUK", "TORBALI", "URLA", "BAYRAKLI", "KARABAĞLAR", "BERGAMA", "BEYDAĞ", "ÇEŞME", "DİKİLİ", "KARABURUN", "KINIK", "KİRAZ", "ÖDEMİŞ", "TİRE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[41])) {
                    String[] adana = {"KAHRAMANMARAŞ", "AFŞİN", "ANDIRIN", "ÇAĞLAYANCERİT", "EKİNÖZÜ", "ELBİSTAN", "GÖKSUN", "NURHAK", "PAZARCIK", "TÜRKOĞLU"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[42])) {
                    String[] adana = {"KARABÜK", "EFLANİ", "ESKİPAZAR", "OVACIK", "SAFRANBOLU", "YENİCE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[43])) {
                    String[] adana = {"KARAMAN", "AYRANCI", "BAŞYAYLA", "ERMENEK", "KAZIMKARABEKİR", "SARIVELİLER"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[44])) {
                    String[] adana = {"KARS", "AKYAKA", "ARPAÇAY", "DİGOR", "KAĞIZMAN", "SARIKAMIŞ", "SELİM", "SUSUZ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[45])) {
                    String[] adana = {"KASTAMONU", "ABANA", "AĞLI", "ARAÇ", "AZDAVAY", "BOZKURT", "CİDE", "ÇATALZEYTİN", "DADAY", "DEVREKANİ", "DOĞANYURT", "HANÖNÜ(GÖKÇEAĞAÇ)", "İHSANGAZİ", "İNEBOLU", "KÜRE", "PINARBAŞI", "SEYDİLER", "ŞENPAZAR", "TAŞKÖPRÜ", "TOSYA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[46])) {
                    String[] adana = {"KOCASİNAN", "MELİKGAZİ", "TALAS", "AKKIŞLA", "BÜNYAN", "DEVELİ", "FELAHİYE", "HACILAR", "İNCESU", "ÖZVATAN(ÇUKUR)", "PINARBAŞI", "SARIOĞLAN", "SARIZ", "TOMARZA", "YAHYALI", "YEŞİLHİSAR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[47])) {
                    String[] adana = {"KIRIKKALE", "BAHŞİLİ", "BALIŞEYH", "ÇELEBİ", "DELİCE", "KARAKEÇİLİ", "KESKİN", "SULAKYURT", "YAHŞİHAN"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[48])) {
                    String[] adana = {"KIRKLARELİ", "BABAESKİ", "DEMİRKÖY", "KOFÇAZ", "LÜLEBURGAZ", "PEHLİVANKÖY", "PINARHİSAR", "VİZE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[49])) {
                    String[] adana = {"KIRŞEHİR", "AKÇAKENT", "AKPINAR", "BOZTEPE", "ÇİÇEKDAĞI", "KAMAN", "MUCUR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[50])) {
                    String[] adana = {"KİLİS", "ELBEYLİ", "MUSABEYLİ", "POLATELİ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[51])) {
                    String[] adana = {"İZMİT", "BAŞİSKELE", "ÇAYIROVA", "DARICA", "DİLOVASI", "KARTEPE", "GEBZE", "GÖLCÜK", "KANDIRA", "KARAMÜRSEL", "TÜTÜNÇİFTLİK", "DERİNCE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[52])) {
                    String[] adana = {"KARATAY", "MERAM", "SELÇUKLU", "AHIRLI", "AKÖREN", "AKŞEHİR", "ALTINEKİN", "BEYŞEHİR", "BOZKIR", "CİHANBEYLİ", "ÇELTİK", "ÇUMRA", "DERBENT", "DEREBUCAK", "DOĞANHİSAR", "EMİRGAZİ", "EREĞLİ", "GÜNEYSINIR", "HADİM", "HALKAPINAR", "HÜYÜK", "ILGIN", "KADINHANI", "KARAPINAR", "KULU", "SARAYÖNÜ", "SEYDİŞEHİR", "TAŞKENT", "TUZLUKÇU", "YALIHÜYÜK", "YUNAK"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[53])) {
                    String[] adana = {"KÜTAHYA", "ALTINTAŞ", "ASLANAPA", "ÇAVDARHİSAR", "DOMANİÇ", "DUMLUPINAR", "EMET", "GEDİZ", "HİSARCIK", "PAZARLAR", "SİMAV", "ŞAPHANE", "TAVŞANLI", "TUNÇBİLEK"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[54])) {
                    String[] adana = {"MALATYA", "AKÇADAĞ", "ARAPKİR", "ARGUVAN", "BATTALGAZİ", "DARENDE", "DOĞANŞEHİR", "DOĞANYOL", "HEKİMHAN", "KALE", "KULUNCAK", "PÖTÜRGE", "YAZIHAN", "YEŞİLYURT"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[55])) {
                    String[] adana = {"MANİSA", "AHMETLİ", "AKHİSAR", "ALAŞEHİR", "DEMİRCİ", "GÖLMARMARA", "GÖRDES", "KIRKAĞAÇ", "KÖPRÜBAŞI", "KULA", "SALİHLİ", "SARIGÖL", "SARUHANLI", "SELENDİ", "SOMA", "TURGUTLU"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[56])) {
                    String[] adana = {"MARDİN", "DARGEÇİT", "DERİK", "KIZILTEPE", "MAZIDAĞI", "MİDYAT(ESTEL)", "NUSAYBİN", "ÖMERLİ", "SAVUR", "YEŞİLLİ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[57])) {
                    String[] adana = {"AKDENİZ", "YENİŞEHİR", "TOROSLAR", "MEZİTLİ", "ANAMUR", "AYDINCIK", "BOZYAZI", "ÇAMLIYAYLA", "ERDEMLİ", "GÜLNAR(GÜLPINAR)", "MUT", "SİLİFKE", "TARSUS"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[58])) {
                    String[] adana = {"MUĞLA", "BODRUM", "DALAMAN", "DATÇA", "FETHİYE", "KAVAKLIDERE", "KÖYCEĞİZ", "MARMARİS", "MİLAS", "ORTACA", "ULA", "YATAĞAN"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[59])) {
                    String[] adana = {"MUŞ", "BULANIK", "HASKÖY", "KORKUT", "MALAZGİRT", "VARTO"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[60])) {
                    String[] adana = {"NEVŞEHİR", "ACIGÖL", "AVANOS", "DERİNKUYU", "GÜLŞEHİR", "HACIBEKTAŞ", "KOZAKLI", "ÜRGÜP"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[61])) {
                    String[] adana = {"NİĞDE", "ALTUNHİSAR", "BOR", "ÇAMARDI", "ÇİFTLİK(ÖZYURT)", "ULUKIŞLA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[62])) {
                    String[] adana = {"ORDU", "AKKUŞ", "AYBASTI", "ÇAMAŞ", "ÇATALPINAR", "ÇAYBAŞI", "FATSA", "GÖLKÖY", "GÜLYALI", "GÜRGENTEPE", "İKİZCE", "KARADÜZ(KABADÜZ)", "KABATAŞ", "KORGAN", "KUMRU", "MESUDİYE", "PERŞEMBE", "ULUBEY", "ÜNYE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[63])) {
                    String[] adana = {"OSMANİYE", "BAHÇE", "DÜZİÇİ", "HASANBEYLİ", "KADİRLİ", "SUMBAS", "TOPRAKKALE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[64])) {
                    String[] adana = {"RİZE", "ARDEŞEN", "ÇAMLIHEMŞİN", "ÇAYELİ", "DEREPAZARI", "FINDIKLI", "GÜNEYSU", "HEMŞİN", "İKİZDERE", "İYİDERE", "KALKANDERE", "PAZAR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[65])) {
                    String[] adana = {"ADAPAZARI", "HENDEK", "ARİFİYE", "ERENLER", "SERDİVAN", "AKYAZI", "FERİZLİ", "GEYVE", "KARAPÜRÇEK", "KARASU", "KAYNARCA", "KOCAALİ", "PAMUKOVA", "SAPANCA", "SÖĞÜTLÜ", "TARAKLI"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[66])) {
                    String[] adana = {"ATAKUM", "İLKADIM", "CANİK", "TEKKEKÖY", "ALAÇAM", "ASARCIK", "AYVACIK", "BAFRA", "ÇARŞAMBA", "HAVZA", "KAVAK", "LADİK", "19MAYIS(BALLICA)", "SALIPAZARI", "TERME", "VEZİRKÖPRÜ", "YAKAKENT"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[67])) {
                    String[] adana = {"SİİRT", "BAYKAN", "ERUH", "KURTALAN", "PERVARİ", "AYDINLAR", "ŞİRVAN"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[68])) {
                    String[] adana = {"SİNOP", "AYANCIK", "BOYABAT", "DİKMEN", "DURAĞAN", "ERFELEK", "GERZE", "SARAYDÜZÜ", "TÜRKELİ"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[69])) {
                    String[] adana = {"SİVAS", "AKINCILAR", "ALTINYAYLA", "DİVRİĞİ", "DOĞANŞAR", "GEMEREK", "GÖLOVA", "GÜRÜN", "HAFİK", "İMRANLI", "KANGAL", "KOYULHİSAR", "SUŞEHRİ", "ŞARKIŞLA", "ULAŞ", "YILDIZELİ", "ZARA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[70])) {
                    String[] adana = {"ŞANLIURFA", "AKÇAKALE", "BİRECİK", "BOZOVA", "CEYLANPINAR", "HALFETİ", "HARRAN", "HİLVAN", "SİVEREK", "SURUÇ", "VİRANŞEHİR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[71])) {
                    String[] adana = {"ŞIRNAK", "BEYTÜŞŞEBAP", "CİZRE", "GÜÇLÜKONAK", "İDİL", "SİLOPİ", "ULUDERE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[72])) {
                    String[] adana = {"TEKİRDAĞ", "ÇERKEZKÖY", "ÇORLU", "HAYRABOLU", "MALKARA", "MARMARAEREĞLİSİ", "MURATLI", "SARAY", "ŞARKÖY"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[73])) {
                    String[] adana = {"TOKAT", "ALMUS", "ARTOVA", "BAŞÇİFTLİK", "ERBAA", "NİKSAR", "PAZAR", "REŞADİYE", "SULUSARAY", "TURHAL", "YEŞİLYURT", "ZİLE"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[74])) {
                    String[] adana = {"TRABZON", "AKÇAABAT", "ARAKLI", "ARSİN", "BEŞİKDÜZÜ", "ÇARŞIBAŞI", "ÇAYKARA", "DERNEKPAZARI", "DÜZKÖY", "HAYRAT", "KÖPRÜBAŞI", "MAÇKA", "OF", "SÜRMENE", "ŞALPAZARI", "TONYA", "VAKFIKEBİR", "YOMRA"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[75])) {
                    String[] adana = {"TUNCELİ", "ÇEMİŞGEZEK", "HOZAT", "MAZGİRT", "NAZIMİYE", "OVACIK", "PERTEK", "PÜLÜMÜR"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[76])) {
                    String[] adana = {"UŞAK", "BANAZ", "EŞME", "KARAHALLI", "SİVASLI", "ULUBEY"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[77])) {
                    String[] adana = {"VAN", "BAHÇESARAY", "BAŞKALE", "ÇALDIRAN", "ÇATAK", "EDREMİT(GÜMÜŞDERE)", "ERCİŞ", "GEVAŞ", "GÜRPINAR", "MURADİYE", "ÖZALP", "SARAY"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[78])) {
                    String[] adana = {"YALOVA", "ALTINOVA", "ARMUTLU", "ÇİFTLİKKÖY", "ÇINARCIK", "TERMAL"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[79])) {
                    String[] adana = {"YOZGAT", "AKDAĞMADENİ", "AYDINCIK", "BOĞAZLIYAN", "ÇANDIR", "ÇAYIRALAN", "ÇEKEREK", "KADIŞEHRİ", "SARAYKENT", "SARIKAYA", "SORGUN", "ŞEFAATLİ", "YENİFAKILI", "YERKÖY"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }
                if (parent.getSelectedItem().toString().equals(iller[80])) {
                    String[] adana = {"ZONGULDAK", "ALAPLI", "ÇAYCUMA", "DEVREK", "KARADENİZEREĞLİ", "GÖKÇEBEY"};
                    dataAdapterForIlceSayisi = new ArrayAdapter<String>(IlanEkleActivity3.this, android.R.layout.simple_spinner_item, adana);
                    dataAdapterForIlceSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIlceler.setAdapter(dataAdapterForIlceSayisi);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IlanEkleActivity3.this, IlanEkleActivity2.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in_ters, R.anim.anim_out_ters);
            }
        });

        btnIleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mah = mahalle.getText().toString();
                int selectedId = site.getCheckedRadioButtonId();
                siteCevap = (RadioButton) findViewById(selectedId);
                if (!mah.equals("")) {
                    IlanVerPojo.setIl(spinnerIller.getSelectedItem().toString());
                    IlanVerPojo.setIlce(spinnerIlceler.getSelectedItem().toString());
                    IlanVerPojo.setMahalle(mahalle.getText().toString());
                    IlanVerPojo.setSiteIcindemi(siteCevap.getText().toString());

                    ilaniyayinla(IlanVerPojo.getKul_id(), IlanVerPojo.getKategori(), IlanVerPojo.getBaslik(), IlanVerPojo.getAciklama(),
                            IlanVerPojo.getFiyat(),IlanVerPojo.getNetMetre(), IlanVerPojo.getBrutMetre(), IlanVerPojo.getOdaSayisi(),
                            IlanVerPojo.getBulunduguKat(), IlanVerPojo.getKatSayisi(), IlanVerPojo.getIsitma(), IlanVerPojo.getBanyoSayisi(),
                            IlanVerPojo.getIl(), IlanVerPojo.getIlce(), IlanVerPojo.getMahalle(), IlanVerPojo.getSiteIcindemi());
                } else {
                    Toast.makeText(getApplicationContext(), "Lütfen mahalle giriniz.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void tanimla() {
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        IlanVerPojo.setKul_id(sharedPreferences.getString("uye_id", null));

        spinnerIller = (Spinner) findViewById(R.id.ilSpinner);
        spinnerIlceler = (Spinner) findViewById(R.id.ilceSpinner);

        dataAdapterForIlSayisi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, iller);
        dataAdapterForIlSayisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIller.setAdapter(dataAdapterForIlSayisi);

        btnIleri = (TextView) findViewById(R.id.btnIleri);
        btnGeri = (TextView) findViewById(R.id.btnGeri);

        mahalle = (EditText) findViewById(R.id.txtMahalle);
        site = (RadioGroup) findViewById(R.id.siteRadioGroup);

        /*spinnerIller.setSelection(IlanVerPojo.getIl());
        spinnerIlceler.setSelection(IlanVerPojo.getIlce());*/
        mahalle.setText(IlanVerPojo.getMahalle());
        // siteCevap.setText(IlanVerPojo.getSiteIcindemi());

    }

    public void ilaniyayinla(String kul_id, String kategori, String baslik, String aciklama, String fiyat, String netMetre,
                             String brutMetre, String odaSayisi, String bulunduguKat, String katSayisi, String isitma,
                             String banyoSayisi, String il, String ilce, String mahalle, String siteIcindemi) {
        final ProgressDialog progressDialog = new ProgressDialog(IlanEkleActivity3.this);
        progressDialog.setMessage("İlan yayınlanıyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<IlanVermePojo> x = ManagerAll.getInstance().ilanverme(kul_id, kategori, baslik, aciklama, fiyat, netMetre,
                brutMetre, odaSayisi, bulunduguKat, katSayisi, isitma,
                banyoSayisi, il, ilce, mahalle, siteIcindemi);
        x.enqueue(new Callback<IlanVermePojo>() {
            @Override
            public void onResponse(Call<IlanVermePojo> call, final Response<IlanVermePojo> response) {
                if (response.isSuccessful()) {
                    progressDialog.cancel();
                    Intent intent = new Intent(IlanEkleActivity3.this, IlanEkleActivity4.class);
                    intent.putExtra("ilan_id", response.body().getIlanid());
                    intent.putExtra("kul_id", response.body().getKulid());
                    startActivity(intent);
                    finish();
                  /*  MainFragment mainFragment = new MainFragment();
                    mainFragment.ilanBilgileriTemizle();*/

                } else {
                    progressDialog.cancel();
                    Dialog d = new Dialog(IlanEkleActivity3.this);
                    d.setContentView(R.layout.ilan_basarisiz);
                    d.setCancelable(false);
                    d.setTitle("Bilgi");
                    d.show();
                    Thread gecis;
                    gecis = new Thread() {
                        @Override
                        public void run() {
                            try {

                                synchronized (this) {
                                    wait(3500);
                                }
                            } catch (InterruptedException ex) {

                            } finally {
                                Intent intent = new Intent(IlanEkleActivity3.this, MainActivity.class);
                                startActivity(intent);

                            }

                        }
                    };
                    gecis.start();
                }
            }

            @Override
            public void onFailure(Call<IlanVermePojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}