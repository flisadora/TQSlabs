package tqs.labs.multilayerwebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AirPollutionServiceImpl implements AirPollutionService {

    @Autowired
    private CityRepository cityRepository;

    private int hits = 0;
    private int misses = 0;

    @Override
    public City getAirPollution(String name) {
        return this.cityRepository.findByName(name);
    }

    @Override
    public City getCityById(Long id) {
        return this.cityRepository.findById(id).orElse(null);
    }

    @Override
    public boolean exists(String name) {
        return this.cityRepository.findByName(name) != null;
    }

    @Override
    public City save(City city) {
        return this.cityRepository.save(city);
    }

    @Override
    public List<City> getAllCitiesSave() {
        return this.cityRepository.findAll();
    }

    @Override
    public void countHits() {
        this.hits ++;
    }

    @Override
    public void countMisses() {
        this.misses ++;
    }

    public void setHits(int number){
        this.hits = number;
    }

    public void setMisses(int number){
        this.misses = number;
    }

    public int getMisses(){
        return this.misses;
    }

    public int getHits(){
        return this.hits;
    }

    public int getCountOfRequests(){
        return this.misses + this.hits;
    }

    public void deleteCity(City delCity){
        this.cityRepository.delete(delCity);
    }

    @Override
    public HashMap<String, Integer> getStatistic() {
        HashMap<String, Integer> statistics = new HashMap<>();
        statistics.put("Number of requests: ", this.misses+this.hits);
        statistics.put("Number of hits: ", this.hits);
        statistics.put("Number of misses: ", this.misses);
        return statistics;
    }
}
