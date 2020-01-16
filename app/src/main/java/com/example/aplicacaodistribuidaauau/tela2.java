package com.example.aplicacaodistribuidaauau;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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

    private String nome = "";

    private ListView listView;

    private ArrayList<String> lista;

    private ArrayAdapter<String> arrayAdapter;

    private ImageView imgCao;

    private String url2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela2);

        lista = new ArrayList();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista);


        Intent intent = getIntent();
        nome = intent.getStringExtra("nome");
        url2 = "https://dog.ceo/api/breed/" + nome + "/images/random";
        Toast.makeText(this, String.valueOf(nome), Toast.LENGTH_SHORT).show();
        setTitle(nome);
        listView = findViewById(R.id.listview2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nome2 = lista.get(position);
                url2 = "https://dog.ceo/api/breed/"+nome+"/"+nome2+"/images/random";
                setTitle(nome2);

                RequestQueue queue = Volley.newRequestQueue(tela2.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    ImageView imageView = findViewById(R.id.imageView);
                                    String urlImagem = response.getString("message");
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
        String url = "https://dog.ceo/api/breed/" + nome + "/list";

        urlFoto(url2);

        // final JSONArray jsonArray =  new JSONArray();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray aux = null;


                        try {

                            aux = response.getJSONArray("message");


                            for (int i = 0; i < aux.length(); i++) {
                                lista.add(String.valueOf(aux.get(i)));
                            }


                            Log.e("nome", String.valueOf(nome));

                            listView = findViewById(R.id.listview2);
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

