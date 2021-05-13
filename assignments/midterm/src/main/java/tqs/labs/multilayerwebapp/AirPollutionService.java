package tqs.labs.multilayerwebapp;

import java.util.HashMap;
import java.util.List;

public interface AirPollutionService {
    City getAirQuality(String name);
    City getCityById(Long id);
    boolean exists(String name);
    City save(City city);
    List<City> getAllCitiesSave();
    void countHits();
    void countMisses();
    HashMap<String, Integer> getStatistic();
    void deleteCity(City delCity);
}
