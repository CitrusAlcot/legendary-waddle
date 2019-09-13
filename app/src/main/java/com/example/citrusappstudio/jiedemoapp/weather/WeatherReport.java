package com.example.citrusappstudio.jiedemoapp.weather;

import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.ForecastIO;

/**
 * Represents a report that stores all weather related information
 * The report will contain the information in the context of one locale and timeframe.
 *
 * @author Jie He
 */
public class WeatherReport{
    private double temperature, precipitation, humidity;
    private String icon, summary, timezone, location;

    public WeatherReport(ForecastIO fio){
        timezone = fio.getTimezone();
        FIOCurrently currently = new FIOCurrently(fio);
        temperature = currently.get().temperature();
        precipitation = currently.get().precipProbability();
        humidity =  currently.get().humidity();
        icon = currently.get().icon();
        summary = currently.get().summary();
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getIcon() {
        return icon;
    }

    public String getSummary() {
        return summary;
    }

    public String gettimezone() {
        return timezone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
