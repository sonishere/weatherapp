package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText city;
    TextView statusText;
    TextView wind;
    TextView humid;
    TextView temp;
    TextView main;
    TextView cityName;
    TextView pressure;
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
        temp = findViewById(R.id.temp);
        pressure = findViewById(R.id.pressure);
        cityName = findViewById(R.id.cityName);
        main = findViewById(R.id.main);

        getWeatherInfo();
    }

    @SuppressLint("SetTextI18n")
    public void getWeatherInfo() {
        city.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String tempUrl = "";
                    String name = city.getText().toString().trim();
                    System.out.println(tempUrl);
                    if (city.equals("")) {
                        statusText.setText("City name cannot be emptied");
                    } else {
                        tempUrl = url + "?q=" + name + "&appid=" + key;
                    }
                    setInfo(tempUrl);
                }
                return false;
            }
        });

    }

    public void setInfo(String tempUrl) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, response -> {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                statusText.setText(jsonObjectWeather.getString("description"));
                main.setText(jsonObjectWeather.getString("main"));

                temp.setText(String.format("%.1f", jsonObjectMain.getDouble("temp") - 273.15)+"°");
                pressure.setText(Integer.toString(jsonObjectMain.getInt("pressure"))+"hPA");
                humid.setText(Integer.toString(jsonObjectMain.getInt("humidity"))+"%");
                String img = "https://openweathermap.org/img/w/" + jsonObjectWeather.getString("icon") + ".png";
                Picasso.get().load(img).into(status);

                JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                wind.setText(jsonObjectWind.getString("speed")+"m/s");

                cityName.setText(jsonResponse.getString("name"));
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