package com.soerdev.trackerroute;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.soerdev.trackerroute.adapter.AdapterAbsenList;
import com.soerdev.trackerroute.app.AppController;
import com.soerdev.trackerroute.model.ModelListAbsen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RekapAbsenActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    SharedPreferences sharedPreferences;


    List<ModelListAbsen> listAbsen = new ArrayList<ModelListAbsen>();
    AdapterAbsenList adapter;
    Context context;

    private ListView listView;

    private String varUsernameNow;
    private String caritanggal;

    private String TAG = RekapAbsenActivity.class.getSimpleName();

    private String TAG_USERNAME = "username";
    private String TAG_ID = "id";
    private String TAG_UNIQUE_CODE = "kodeUnik";
    private String TAG_LINK_IMAGE = "link_gambar";
    private String TAG_KOORDINAT_AWAL = "awal";
    private String TAG_KOORDINAT_AKHIR = "akhir";
    private String TAG_NAMA = "nama";
    private String TAG_ID_DEVICE = "id_device";
    private String TAG_DATE = "date";
    private String TAG_daTe = "daTe";

    private String URL_POST_USERNAME = "https://sembarangsims.000webhostapp.com/backSims/select_absensi.php";

    private String TAG_SUCCESS = "success";
    private String TAG_MESSAGE = "message";

    private String result = "";

    private int varUserUidNow;
    private String jsonResponse;

    SwipeRefreshLayout refreshLayout;

    TextView cobak;

    int success;

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap_absen);

        Toolbar toolbar = findViewById(R.id.lihatRekapAbsen);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        caritanggal = getIntent().getExtras().getString("cari");

        sharedPreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        varUserUidNow = (sharedPreferences.getInt(TAG_ID, 0));
        varUsernameNow = (sharedPreferences.getString(TAG_NAMA, ""));
        //cobak = findViewById(R.id.cobak);

        refreshLayout = findViewById(R.id.swipe_rl);

        listView = findViewById(R.id.listAbsenRV);
        adapter = new AdapterAbsenList(RekapAbsenActivity.this, listAbsen);
        listView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(this);

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                listAbsen.clear();
                adapter.notifyDataSetChanged();
                postUsername();
            }
        });
    }

    private void postUsername() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_POST_USERNAME, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            ModelListAbsen item = new ModelListAbsen();

                            item.setUsername(obj.getString(TAG_USERNAME));
                            item.setAwal(obj.getString(TAG_KOORDINAT_AWAL));
                            item.setAkhir(obj.getString(TAG_KOORDINAT_AKHIR));
                            item.setDate(obj.getString(TAG_DATE));

                            listAbsen.add(item);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RekapAbsenActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    adapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                } catch (JSONException f) {
                    f.printStackTrace();
                    Toast.makeText(RekapAbsenActivity.this, f.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());
                Toast.makeText(RekapAbsenActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String>getParams(){
                //Date currentDate = Calendar.getInstance().getTime();
                //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //String tanggal = simpleDateFormat.format(currentDate);

                Map<String, String> params = new HashMap<String, String>();

                params.put(TAG_daTe, caritanggal);

                Log.e(TAG, ""+params);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
        /*
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_POST_USERNAME, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Log.e(TAG, response.toString());
                Log.i("tagconvertstr", "["+response.toString()+"]");
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        ModelListAbsen item = new ModelListAbsen();

                        item.setUsername(obj.getString(TAG_USERNAME));
                        item.setAwal(obj.getString(TAG_KOORDINAT_AWAL));
                        item.setAkhir(obj.getString(TAG_KOORDINAT_AKHIR));
                        item.setDate(obj.getString(TAG_DATE));

                        listAbsen.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RekapAbsenActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(RekapAbsenActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String>getParams(){
                Date currentDate = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
                String tanggal = simpleDateFormat.format(currentDate);

                Map<String, String> params = new HashMap<String, String>();

                params.put(TAG_daTe, tanggal);

                Log.e(TAG, ""+params);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        /*
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_POST_USERNAME, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Response : " + response.toString());

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject object = dataArray.getJSONObject(i);

                        String username = object.getString(TAG_USERNAME);
                        String awal = object.getString(TAG_KOORDINAT_AWAL);
                        String akhir = object.getString(TAG_KOORDINAT_AKHIR);
                        String date = object.getString(TAG_DATE);

                        jsonResponse = "";
                        jsonResponse += "Username : " +username+"\n";
                        jsonResponse += "Awal : "+awal+"\n";
                        jsonResponse += "Akhir : "+akhir+"\n";
                        jsonResponse += "Tanggal : "+date+"\n";

                        cobak.setText(jsonResponse);

                        ModelListAbsen modelAbsen = new ModelListAbsen();
                        modelAbsen.setId(object.getString(TAG_ID));
                        modelAbsen.setUsername(object.getString(TAG_USERNAME));
                        modelAbsen.setKodeUnik(object.getString(TAG_UNIQUE_CODE));
                        modelAbsen.setLink_gambar(object.getString(TAG_LINK_IMAGE));
                        modelAbsen.setAwal(object.getString(TAG_KOORDINAT_AWAL));
                        modelAbsen.setAkhir(object.getString(TAG_KOORDINAT_AKHIR));
                        modelAbsen.setId_device(object.getString(TAG_ID_DEVICE));
                        modelAbsen.setDate(object.getString(TAG_DATE));

                        listAbsen.add(modelAbsen);

                        listAbsen.add(new ModelListAbsen(
                                object.getString(TAG_ID),
                                object.getString(TAG_USERNAME),
                                object.getString(TAG_UNIQUE_CODE),
                                object.getString(TAG_LINK_IMAGE),
                                object.getString(TAG_KOORDINAT_AWAL),
                                object.getString(TAG_KOORDINAT_AKHIR),
                                object.getString(TAG_ID_DEVICE),
                                object.getString(TAG_DATE)
                        ));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RekapAbsenActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest);
        */
    }

    @Override
    public void onRefresh() {
        listAbsen.clear();
        adapter.notifyDataSetChanged();
        postUsername();
    }

    /*
    public class AdapterListAbsen2 extends RecyclerView.Adapter<AdapterListAbsen2.ViewHolder>{

        private List<ModelListAbsen> modelListAbsens;
        private Context context;

        public AdapterListAbsen2(Context context, List<ModelListAbsen> modelListAbsenArrayList) {
            this.modelListAbsens = modelListAbsenArrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_absen, null);

            ViewHolder viewHolder = new ViewHolder(mView);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ModelListAbsen mLA = modelListAbsens.get(position);

            holder.username.setText(String.valueOf(mLA.getUsername()));
            holder.tanggalAbsen.setText(String.valueOf(mLA.getDate()));
            holder.kordinatAwal.setText(String.valueOf(mLA.getAwal()));
            holder.kordinatAkhir.setText(String.valueOf(mLA.getAkhir()));
        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView username, tanggalAbsen, kordinatAwal, kordinatAkhir;

            public ViewHolder(View itemView) {
                super(itemView);

                username = (TextView)itemView.findViewById(R.id.userName);
                tanggalAbsen = (TextView)itemView.findViewById(R.id.tanggalAbsen);
                kordinatAwal = (TextView)itemView.findViewById(R.id.awalKoordinat);
                kordinatAkhir = (TextView)itemView.findViewById(R.id.akhirKoordinat);
            }
        }
    }
    */
}

