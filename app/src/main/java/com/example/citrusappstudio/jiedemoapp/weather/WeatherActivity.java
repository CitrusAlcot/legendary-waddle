package com.example.citrusappstudio.jiedemoapp.weather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.citrusappstudio.jiedemoapp.AppPreferences;
import com.example.citrusappstudio.jiedemoapp.MainActivity;
import com.example.citrusappstudio.jiedemoapp.R;
import com.example.citrusappstudio.jiedemoapp.SettingsActivity;

import java.net.URL;


/**
 * The weather activity of JieDemoApp.
 * This is the weather activity of JieDemoApp and showcases information about the weather
 * Uses AsyncTask for smooth app operation while app loads weather data online.
 *
 * @author Jie He
 */
public class WeatherActivity extends AppCompatActivity {

    private TextView mWeatherTextView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        setTitle("Weather");

        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadWeatherData();
    }

    /**
     * This method will get the user's preferred location for weather, and then tell some
     * background method to get the weather data in the background.
     */
    private void loadWeatherData() {
        showWeatherDataView();

        String location = AppPreferences.getWeatherLocation(this);
        new FetchWeatherTask().execute(location);
    }

    /**
     * shows the weather data and removes the error message.
     */
    private void showWeatherDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mWeatherTextView.setVisibility(View.VISIBLE);
    }

    /**
     * shows the error message and removes the weather data.
     */
    private void showErrorMessage() {
        mWeatherTextView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    /**
     * This class aims to find the weather information using the internet detailed in NetworkUtils.
     * @see NetworkUtils
     */
    public class FetchWeatherTask extends AsyncTask<String, Void, WeatherReport> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected WeatherReport doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String location = params[0];
            WeatherReport weather = NetworkUtils.createWeatherReport(location);
            return weather;
        }

        @Override
        protected void onPostExecute(WeatherReport weatherData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (weatherData != null) {
                showWeatherDataView();

                mWeatherTextView.append(("Location: " + weatherData.getLocation()) + "\n\n\n");
                mWeatherTextView.append(("Timezone: " + weatherData.gettimezone()) + "\n\n\n");
                mWeatherTextView.append(("Temperature: " + weatherData.getTemperature()) + "\n\n\n");
                mWeatherTextView.append(("Humidity: " + weatherData.getHumidity()) + "\n\n\n");
                mWeatherTextView.append(("Precipitation: " + weatherData.getPrecipitation()) + "\n\n\n");
                mWeatherTextView.append(("Icon: " + weatherData.getIcon()) + "\n\n\n");
                mWeatherTextView.append(("Summary: " + weatherData.getSummary()) + "\n\n\n");

            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.forecast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            mWeatherTextView.setText("");
            loadWeatherData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
