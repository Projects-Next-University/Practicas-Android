package com.victorflores.proyectosandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    private Button botonObtenerJSON;
    private Button botonValores;
    private TextView textoContenido;

    private static String URL = "https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonObtenerJSON = (Button)findViewById(R.id.btn_obtener_json);
        botonValores = (Button)findViewById(R.id.btn_valores);
        textoContenido = (TextView)findViewById(R.id.txt_contenido);

        botonObtenerJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textoContenido.setText(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(stringRequest);

            }
        });

        botonValores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            JSONArray results = response.getJSONArray("results");

                            for (int i = 0; i < results.length(); i++){

                                JSONObject geometry = results.getJSONObject(i).getJSONObject("geometry");
                                JSONObject location = geometry.getJSONObject("location");

                                String latitud = location.getString("lat");
                                String longitud = location.getString("lng");

                                Toast.makeText(MainActivity.this, "Latitud: "+latitud, Toast.LENGTH_SHORT).show();
                                Toast.makeText(MainActivity.this, "Longitud: "+longitud, Toast.LENGTH_SHORT).show();

                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(jsonObjectRequest);

            }
        });

    }
}
