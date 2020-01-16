package com.example.aplicacaodistribuidaauau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


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

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final ListView listView = findViewById(R.id.listview);
        final ArrayList<String> lista = new ArrayList();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista);

        // ...

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
               String nome = lista.get(position);
                String url = "https://dog.ceo/api/breed/"+nome+"/images/random";

                // final JSONArray jsonArray =  new JSONArray();

                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {


                                try {
                                    ImageView imageView = findViewById(R.id.imageViewPrincipal);
                                    String urlImagem = response.getString("message");
                                    Glide.with(MainActivity.this).load(urlImagem).into(imageView);

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

                Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),tela2.class);

                nome=lista.get(position);
                intent.putExtra("nome",nome);

                startActivity(intent);

            }
        });


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://dog.ceo/api/breeds/list";

        // final JSONArray jsonArray =  new JSONArray();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray aux = null;
                        try {
                            aux = response.getJSONArray("message");
                            for (int i = 0; i<aux.length();i++){
                                lista.add(String.valueOf(aux.get(i)));
                            }
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







}

