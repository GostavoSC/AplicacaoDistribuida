package com.example.aplicacaodistribuidaauau;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class tela2 extends AppCompatActivity {

    private String raca = "";
    private ArrayList<String> listaCachorros;
    private ArrayAdapter<String> arrayAdapter;
    private String urlRacas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela2);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        listaCachorros = new ArrayList();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaCachorros);

        Intent intent = getIntent();
        raca = intent.getStringExtra("raca");
        urlRacas = "https://dog.ceo/api/breed/" + raca + "/images/random";
        Toast.makeText(this, String.valueOf(raca), Toast.LENGTH_SHORT).show();
        setTitle(raca);



        final ListView listView;
        listView = findViewById(R.id.listview2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String nome2 = listaCachorros.get(position);
                final String nome3 = raca + " -> " + nome2;
                urlRacas = "https://dog.ceo/api/breed/" + raca + "/" + nome2 + "/images/random";
                setTitle(nome2);

                RequestQueue queue = Volley.newRequestQueue(tela2.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, urlRacas, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    ImageView imageView = findViewById(R.id.imageView);
                                    String urlImagem = response.getString("message");
                                    SharedPreferences preferences = getSharedPreferences("user_preferences",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("Url",urlImagem);
                                    editor.putString("raca", nome3);
                                    Log.e("urlimg", urlImagem);
                                    editor.apply();
                                    Glide.with(tela2.this).load(urlImagem).into(imageView);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                            }
                        });
                queue.add(jsonObjectRequest);
            }
        });


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://dog.ceo/api/breed/" + raca + "/list";

        urlFoto(urlRacas);

        // final JSONArray jsonArray =  new JSONArray();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray aux = null;
                        try {
                            aux = response.getJSONArray("message");
                            for (int i = 0; i < aux.length(); i++) {
                                listaCachorros.add(String.valueOf(aux.get(i)));
                            }
                            Log.e("raca", String.valueOf(raca));
                            listView.setAdapter(arrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void urlFoto(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ImageView imageView = findViewById(R.id.imageView);
                            String urlImagem = response.getString("message");

                            SharedPreferences preferences = getSharedPreferences("user_preferences",MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Url",urlImagem);
                            editor.putString("raca",raca);
                            editor.apply();

                            Glide.with(tela2.this).load(urlImagem).into(imageView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        queue.add(jsonObjectRequest);
    }
}

