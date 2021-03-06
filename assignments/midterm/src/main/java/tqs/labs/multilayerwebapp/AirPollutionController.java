package tqs.labs.multilayerwebapp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
// documentation apis:
// https://openweathermap.org/api/air-pollution

@Controller
@RequestMapping("/api")
public class AirPollutionController {

    private JSONParser parser = new JSONParser();
    private static final String API_KEY_A = "a0e504cc-aebd-4a88-9a09-e2cc66d1c104";
    private static
    final String HOST_A = "https://api.airvisual.com/v2/";
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private AirPollutionService airPollutionService;

    public JSONObject requestApi(String url) throws ParseException {
        String response = restTemplate.getForObject(url, String.class);
        return (JSONObject) parser.parse(response);
    }

    @GetMapping(value="/airpollution")
    public String start(Model model) throws ParseException{
        String url = HOST_A + "states?country=Portugal&key=" + API_KEY_A;
        JSONObject result = this.requestApi(url);
        JSONArray data = (JSONArray) result.get("data");

        List<String> states = new ArrayList<>();

        for(Object s: data){
            JSONObject state = (JSONObject) s;
            states.add((String) state.get("state"));
        }
        model.addAttribute("states",states);
        
        return "index";
    }

    @GetMapping(path="/getCity")
    @ResponseBody
    public ResponseEntity<City> getCity(@RequestParam String city_name) throws ParseException, InterruptedException {

        // Simulation time to live.
        // If city is in cache and their time of request when entry in cache as bigger than this time plus
        // one minute, need to be remove.
        Calendar target_date = Calendar.getInstance();
        if (this.airPollutionService.exists(city_name)){

            City target_city = this.airPollutionService.getAirPollution(city_name);
            Calendar requestDateTTL = target_city.getTimeOfRequest();
            requestDateTTL.add(Calendar.MINUTE, 1);

            if(requestDateTTL.compareTo(target_date) < 0) {
                this.airPollutionService.deleteCity(target_city);
            } else {
                this.airPollutionService.countHits();
                return new ResponseEntity<>(target_city, HttpStatus.OK);
            }
        }

        String url = HOST_A + "city?city=" + city_name + "&state=" + city_name +
                "&country=Portugal&key=" + API_KEY_A;
        JSONObject data;
        System.out.println("URL-------------> "+url);
        try {
            data = this.requestApi(url);
        }
        catch (Exception e){
            return new ResponseEntity<>(new City(), HttpStatus.NOT_FOUND);
        }

        data = (JSONObject) data.get("data");
        data = (JSONObject) data.get("location");
        JSONArray coords = (JSONArray) data.get("coordinates");
        double lat = (double) coords.get(1);
        double lon = (double) coords.get(0);

        //example url:
        //http://api.openweathermap.org/data/2.5/air_pollution?lat={lat}&lon={lon}&appid={API key}
        String API_KEY_B = "e7f3754334ee9670373a35ac58377e22";
        String HOST_B = "http://api.openweathermap.org/data/2.5/air_pollution?";
        url = HOST_B + "lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY_B;
        System.out.println(url);
        try{
            data = this.requestApi(url);
        }catch (Exception e){
            return new ResponseEntity<>(new City(), HttpStatus.NOT_FOUND);
        }

        JSONArray list = (JSONArray) data.get("list");
        data = (JSONObject) list.get(0);
        JSONObject components = (JSONObject) data.get("components");
        data = (JSONObject) data.get("main");


        City new_city = this.airPollutionService.save(new City(city_name, lat, lon,
                Integer.parseInt(data.get("aqi").toString()),
                (double) components.get("co"),
                components.get("no").toString().length() == 1 ? (int) Integer.parseInt(components.get("no").toString()) : (double) components.get("no"),
                components.get("no2").toString().length() == 1 ? (int) Integer.parseInt(components.get("no2").toString()) : (double) components.get("no2"),
                components.get("o3").toString().length() == 1 ? (int) Integer.parseInt(components.get("o3").toString()) : (double) components.get("o3"),
                components.get("so2").toString().length() == 1 ? (int) Integer.parseInt(components.get("so2").toString()) : (double) components.get("so2"),
                components.get("pm2_5").toString().length() == 1 ? (int) Integer.parseInt(components.get("pm2_5").toString()) : (double) components.get("pm2_5"),
                components.get("pm10").toString().length() == 1 ? (int) Integer.parseInt(components.get("pm10").toString()) : (double) components.get("pm10"),
                components.get("nh3").toString().length() == 1 ? (int) Integer.parseInt(components.get("nh3").toString()) : (double) components.get("nh3")
        ));

        this.airPollutionService.countMisses();

        return new ResponseEntity<>(new_city, HttpStatus.OK);
    }

    @PostMapping(path="/airpollution")
    public String airPollution(Model model, @RequestParam(name = "chosen_state") String chosen_state) throws ParseException, InterruptedException {
        ResponseEntity<City> response = this.getCity(chosen_state);
        HttpStatus statuscode = response.getStatusCode();

        if (statuscode == HttpStatus.NOT_FOUND)
            model.addAttribute("error", "Data unavailable!");

        City new_city = response.getBody();
        model.addAttribute("city", new_city);

        return "baqi";
    }

    @GetMapping(path="/getCities")
    @ResponseBody
    public ResponseEntity<List<City>> getAllCities(){
        return new ResponseEntity<>(this.airPollutionService.getAllCitiesSave(), HttpStatus.OK);
    }

    @GetMapping(path = "/statistics")
    @ResponseBody
    public Map<String, Integer> getStatistics(){
        return this.airPollutionService.getStatistic();
    }

}
