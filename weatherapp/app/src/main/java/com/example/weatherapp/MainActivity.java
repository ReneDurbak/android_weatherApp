package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private EditText cityEditText;

    private TextView tempTextView;

    private TextView descriptionTextView;

    private TextView cityTextView;

    private TextView city2TextView;

    private ImageView descriptionIcon;

    private TextView humidityTextView;

    private TextView windSpeedTextView;

    private ImageView windIcon;

    private ImageView humidityIcon;

    private LinearLayout bottomLayout;

    private TextView windSpeedDesc;

    private TextView humidityDesc;


    private static final String API_KEY = "c10d9a7ca0369f89897dd1265150a60a";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityEditText = findViewById(R.id.cityEditText);
        tempTextView = findViewById(R.id.tempTextView);
        cityTextView= findViewById(R.id.cityTextView);
        city2TextView = findViewById(R.id.city2TextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        descriptionIcon = findViewById(R.id.descriptionIcon);
        humidityTextView = findViewById(R.id.tv_humidity);
        windSpeedTextView = findViewById(R.id.tv_windSpeed);
        windIcon = findViewById(R.id.windIcon);
        humidityIcon = findViewById(R.id.humidityIcon);
        bottomLayout = findViewById(R.id.bottomLayout);
        windSpeedDesc = findViewById(R.id.windSpeedDesc);
        humidityDesc = findViewById(R.id.humidityDesc);
    }

    public void getWeather(View view) {
        String city = cityEditText.getText().toString().trim();
        if (!city.isEmpty()) {
            String url = BASE_URL + "?q=" + city +  "&units=metric" + "&appid=" + API_KEY;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, response -> {
                        try {
                            int temperature = response.getJSONObject("main").getInt("temp");
                            int humidity = response.getJSONObject("main").getInt("humidity");
                            String description = response.getJSONArray("weather").getJSONObject(0).getString("description");
                            int descriptionCode = response.getJSONArray("weather").getJSONObject(0).getInt("id");
                            getWeatherIcon(descriptionCode);
                            int windSpeed = response.getJSONObject("wind").getInt("speed");


                            String temp = temperature + "°c";
                            String hum = humidity + "%";
                            String wind = windSpeed + " km/h";

                            cityTextView.setText(city);
                            city2TextView.setText(city);
                            tempTextView.setText(temp);
                            descriptionTextView.setText(description);
                            humidityTextView.setText(hum);
                            windSpeedTextView.setText(wind);
                            windIcon.setImageResource(R.drawable.wind);
                            humidityIcon.setImageResource(R.drawable.humidity);
                            bottomLayout.setBackgroundResource(R.drawable.info_background);
                            windSpeedDesc.setText("Wind speed");
                            humidityDesc.setText("Humidity");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                        Toast.makeText(MainActivity.this, "Error: Mesto nebolo nájdené", Toast.LENGTH_SHORT).show();
                    });

            // pridame request do RequestQueue
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObjectRequest);
        }else{
            Toast.makeText(MainActivity.this, "Error: Musíš zadať mesto!", Toast.LENGTH_SHORT).show();
        }
    }

    public void getWeatherIcon(int descriptionCode){
        if(descriptionCode >= 200 && descriptionCode <= 232){
            descriptionIcon.setImageResource(R.drawable.thunderstorm);
        }else if(descriptionCode >= 300 && descriptionCode <= 321){
            descriptionIcon.setImageResource(R.drawable.drizzle);
        }else if(descriptionCode >= 500 && descriptionCode <= 531){
            descriptionIcon.setImageResource(R.drawable.rain);
        }else if(descriptionCode >= 600 && descriptionCode <= 622){
            descriptionIcon.setImageResource(R.drawable.snow);
        }else if(descriptionCode >= 701 && descriptionCode <= 781){
            descriptionIcon.setImageResource(R.drawable.mist);
        }else if(descriptionCode == 800){
            descriptionIcon.setImageResource(R.drawable.sun);
        }else if(descriptionCode >= 801 && descriptionCode <= 804){
            descriptionIcon.setImageResource(R.drawable.cloudy);

        }
    }
}