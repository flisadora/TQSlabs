package tqs.labs.multilayerwebapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource( locations = "application-tests.properties")
@AutoConfigureTestDatabase
public class AirPollutionControllerIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AirPollutionServiceImpl airPollutionService;

    @AfterEach
    public void resetDb() {
        cityRepository.deleteAll();
        airPollutionService.setHits(0);
        airPollutionService.setMisses(0);
    }

    public void checkResponse(City city, String name_city) {
        assertThat(city.getName()).isEqualTo(name_city);
        assertThat(city.getAir_quality_index()).isNotNull();
        assertThat(city.getCategory()).isNotNull();
        assertThat(city.getCarbon()).isNotNull();
        assertThat(city.getO3()).isNotNull();
        assertThat(city.getSo2()).isNotNull();
        assertThat(city.getPm25()).isNotNull();
        assertThat(city.getPm10()).isNotNull();
        assertThat(city.getNh3()).isNotNull();
        assertThat(city.getNo()).isNotNull();
        assertThat(city.getNo2()).isNotNull();
    }

    @Test
    public void whenSearchingInvalidCity_thenReturnStatusCodeNot_Found() {
        // Misses and hits should be zero
        ResponseEntity<City> response = restTemplate.getForEntity("/api/getCity?city_name=Açores", City.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(cityRepository.findByName("Açores")).isNull();

        assertThat(airPollutionService.getMisses()).isEqualTo(0);
        assertThat(airPollutionService.getHits()).isEqualTo(0);
        assertThat(airPollutionService.getCountOfRequests()).isEqualTo(0);
    }

    @Test
    public void whenSearchingFirstTimeCity_thenReturnTheirAirQuality() {
        // Checking if the city requested is saved and
        // as is first time searched, misses should be one and hits equal to 0
        ResponseEntity<City>  response = restTemplate.getForEntity("/api/getCity?city_name=Braga", City.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(cityRepository.findByName("Braga")).isNotNull();

        checkResponse(Objects.requireNonNull(response.getBody()), "Braga");

        assertThat(airPollutionService.getMisses()).isEqualTo(1);
        assertThat(airPollutionService.getHits()).isEqualTo(0);
        assertThat(airPollutionService.getCountOfRequests()).isEqualTo(1);
    }

    @Test
    public void whenSearchingMoreThanOneTimeACity_thenReturnTheirAirQuality() {
        // If we searched a city before, now the city must be in cache
        // hits and misses should be one

        // create one miss
        ResponseEntity<City>  first_response = restTemplate.getForEntity("/api/getCity?city_name=Braga", City.class);

        // create one hit
        ResponseEntity<City>  second_response = restTemplate.getForEntity("/api/getCity?city_name=Braga", City.class);

        assertThat(airPollutionService.getHits()).isEqualTo(1);
        assertThat(airPollutionService.getMisses()).isEqualTo(1);
        assertThat(airPollutionService.getCountOfRequests()).isEqualTo(2);
    }

    @Test
    public void whenGetStatistics_thenReturnStatistics() {
        // 2 misses
        restTemplate.getForEntity("/api/getCity?city_name=Braga", City.class);
        restTemplate.getForEntity("/api/getCity?city_name=Aveiro", City.class);

        ResponseEntity<HashMap<String, Integer>> response = restTemplate
                .exchange("/api/statistics", HttpMethod.GET, null, new ParameterizedTypeReference<HashMap<String, Integer>>() {
                });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).get("Number of requests: ")).isEqualTo(2);
        assertThat(Objects.requireNonNull(response.getBody()).get("Number of hits: ")).isEqualTo(0);
        assertThat(Objects.requireNonNull(response.getBody()).get("Number of misses: ")).isEqualTo(2);
    }


    @Test
    public void whenSearchingCityAfterExpireInCache_thenReturnCityAsNewCacheEntry() throws InterruptedException {
        // Duration of 1 minute, because was declare time to live equals to one minute
        // create one miss
        ResponseEntity<City>  first_response = restTemplate.getForEntity("/api/getCity?city_name=Braga", City.class);

        TimeUnit.MINUTES.sleep(1);

        // create one hit
        ResponseEntity<City>  second_response = restTemplate.getForEntity("/api/getCity?city_name=Braga", City.class);

        assertThat(Objects.requireNonNull(first_response.getBody()).getTimeOfRequest())
                .isLessThan(Objects.requireNonNull(second_response.getBody()).getTimeOfRequest());
        assertThat(airPollutionService.getMisses()).isEqualTo(2);
        assertThat(airPollutionService.getHits()).isEqualTo(0);
        assertThat(airPollutionService.getCountOfRequests()).isEqualTo(2);
    }

    @Test
    public void whenGetCities_thenReturnAllCitiesInCache() {
        restTemplate.getForEntity("/api/getCity?city_name=Braga", City.class);
        restTemplate.getForEntity("/api/getCity?city_name=Aveiro", City.class);

        ResponseEntity<List<City>> response = restTemplate
                .exchange("/api/getCities", HttpMethod.GET, null, new ParameterizedTypeReference<List<City>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(City::getName).containsExactly("Braga", "Aveiro");
    }
}
