package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView city;
    TextView statusText;
    TextView wind;
    TextView humid;

    ImageView status;

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String key = "03675c235d5fa83f823f4f9a24953c1f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city = findViewById(R.id.city);
        statusText = findViewById(R.id.statusText);
        wind = findViewById(R.id.wind);
        humid = findViewById(R.id.humid);
        status = findViewById(R.id.status);

    }

    @SuppressLint("SetTextI18n")
    public void getWeatherInfo(View view) {
        String tempUrl = "";
        String name = city.getText().toString().trim();

        if (city.equals("")) {
            statusText.setText("City name cannot be emptied");
        } else {
            tempUrl = url + "?q=" + city + ",&appid=" + key;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, response -> {
            try {
                JSONObject jsonRespone = new JSONObject(response);

            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}