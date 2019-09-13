package com.example.citrusappstudio.jiedemoapp.weather;

import android.net.Uri;
import android.util.Log;

import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.FIOHourly;
import com.github.dvdme.ForecastIOLib.ForecastIO;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * A network utility for the weather application which finds the forecast of a given location.
 * The location can be in text at which it will be geocoded to a accurate latitude and longitude.
 *
 * @see WeatherActivity
 * @author Jie He
 */
class NetworkUtils {
    private static final String FORECAST_BASE_URL = "https://api.darksky.net/forecast/686645ab8a8e428f668e40d097ef3eca;";
    private static final String FORECAST_API_KEY = "686645ab8a8e428f668e40d097ef3eca";

    private static final String GEOCODE_BASE_URL = "https://api.opencagedata.com/geocode/v1/xml?";
    private static final String GEOCODE_QUERY_PARAM = "q";
    private static final String GEOCODE_API_PARAM= "key";
    private static final String GEOCODE_API_KEY = "2f450fffa0f142329a95b12391366b79";

    private static final String TAG = "NetworkUtils";

    /**
     * Creates a weather report from a given latitude and longitude using
     * DarkSky API (maven:'com.github.dvdme:ForecastIOLib:1.6.0')
     * @param lat The latitude of the weather querying.
     * @param lng The longitude of the weather querying.
     * @return A weather report containing all the information about the weather.
     * @see WeatherReport
     */
    public static WeatherReport createWeatherReport(String lat, String lng) {
        ForecastIO fio = new ForecastIO(FORECAST_API_KEY); //instantiate the class with the API key.
        fio.setUnits(ForecastIO.UNITS_SI);             //sets the units as SI - optional
        fio.setExcludeURL("hourly,minutely");             //excluded the minutely and hourly reports from the reply
        fio.getForecast(lat, lng);

        WeatherReport r = new WeatherReport(fio);

        return r;
    }

    /**
     * Creates a weather report from a string location by geocoding.
     * @param location The location to check the weather on.
     * @return A weather report containing all the information about the weather.
     * @see WeatherReport
     * @see NetworkUtils#createWeatherReport(String, String)
     */
    public static WeatherReport createWeatherReport(String location){
        try {
            String[] latlong = findlatlong(location);
            if (latlong[0] == null | latlong[1] == null) throw new IOException();
            WeatherReport r = createWeatherReport(latlong[0], latlong[1]);
            r.setLocation(location);
            return r;
        } catch (IOException e) {
            Log.e(TAG, "Failed function latlong. IOException");
        } catch (JSONException e) {
            Log.e(TAG, "Failed function latlong. JSONException");
        }
        return null;
    }

    /**
     * Finds the latitude and longitude of a location string the user inputs.
     * Does this by geocoding the location using OpenCage geocode API
     * @param location The location to geocode
     * @return a string array containing the latitude and longitude of the location.
     * @throws IOException
     * @throws JSONException
     */
    private static String[] findlatlong(String location) throws IOException, JSONException {
        Uri builtUri = Uri.parse(GEOCODE_BASE_URL).buildUpon()
                .appendQueryParameter(GEOCODE_QUERY_PARAM, location)
                .appendQueryParameter(GEOCODE_API_PARAM, GEOCODE_API_KEY)
                .build();
        Log.v(TAG, builtUri.toString());

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //Attempts to open the url and finds the latitude and longitude in that XML string given by api.
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                String[] result = new String[2];

                String str = scanner.next();
                str = str.substring(str.indexOf("<northeast>"), str.indexOf("</northeast>"));
                result[0] = str.substring(str.indexOf("<lat>")+5, str.indexOf("</lat>"));
                result[1] = str.substring(str.indexOf("<lng>")+5, str.indexOf("</lng>"));

                Log.v(TAG, "lat: " + String.valueOf(result[0]) + ", long: " + String.valueOf(result[1]));
                return result;
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
