package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
    private TextView weatherTextView;

    private static final String API_KEY = "c10d9a7ca0369f89897dd1265150a60a";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityEditText = findViewById(R.id.cityEditText);
        weatherTextView = findViewById(R.id.weatherTextView);
    }

    public void getWeather(View view) {
        String city = cityEditText.getText().toString().trim();
        if (!city.isEmpty()) {
            String url = BASE_URL + "?q=" + city +  "&units=metric" + "&appid=" + API_KEY;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, response -> {
                        try {
                            // Parse JSON response
                            double temperature = response.getJSONObject("main").getDouble("temp");
                            int humidity = response.getJSONObject("main").getInt("humidity");
                            String description = response.getJSONArray("weather")
                                    .getJSONObject(0).getString("description");

                            String weatherText =
                                    city + "\n"
                                            + temperature + "°C\n"
                                            + "Humidity: " + humidity + "%\n"
                                            + description;
                            weatherTextView.setText(weatherText);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    });

            // Add the request to the RequestQueue
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObjectRequest);
        }
    }
}