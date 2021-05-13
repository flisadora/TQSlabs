package tqs.labs.multilayerwebapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AirPollutionController.class)
public class AirPollutionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirPollutionService service;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    public void whenGetCity_returnCity() throws Exception {
        City braga = new City("Braga", 48.12, -2.0, 2, 205.28,
                0, 1.65, 92.98, 0.65, 2.68, 4.36, 2.88);
        given(service.save(Mockito.any())).willReturn(braga);

        mvc.perform(get("/api/getCity?city_name=Braga").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(braga.getName())))
                .andExpect(jsonPath("$.lat", is(braga.getLat())))
                .andExpect(jsonPath("$.lng", is(braga.getLng())));
        verify(service, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(service);
    }

    @Test
    public void givenCities_whenGetCities_thenReturnJsonArray() throws Exception {
        City braga = new City("Braga",  48.12, -2.0, 2, 205.28,
                0, 1.65, 92.98, 0.65, 2.68, 4.36, 2.88);
        City lisboa = new City("Lisboa",  48.12, -2.0, 2, 205.28,
                0, 1.65, 92.98, 0.65, 2.68, 4.36, 2.88);
        City aveiro = new City("Aveiro",  48.12, -2.0, 2, 205.28,
                0, 1.65, 92.98, 0.65, 2.68, 4.36, 2.88);

        List<City> allCities = Arrays.asList(braga, lisboa, aveiro);

        given(service.getAllCitiesSave()).willReturn(allCities);

        mvc.perform(get("/api/getCities").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(braga.getName())))
                .andExpect(jsonPath("$[1].name", is(lisboa.getName())))
                .andExpect(jsonPath("$[2].name", is(aveiro.getName())));
        verify(service, VerificationModeFactory.times(1)).getAllCitiesSave();
        reset(service);
    }

    @Test
    public void whenGetStatistics_thenReturnJson() throws Exception {
        HashMap<String, Integer> statistics = new HashMap<String, Integer>(){{
            put("Number of requests:", 7);
            put("Number of hits:",2);
            put("Number of misses:", 5);
        }};

        given(service.getStatistic()).willReturn(statistics);
        mvc.perform(get("/api/statistics").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$['Number of requests:']", is(statistics.get("Number of requests:"))))
                .andExpect(jsonPath("$['Number of hits:']", is(statistics.get("Number of hits:"))))
                .andExpect(jsonPath("$['Number of misses:']", is(statistics.get("Number of misses:"))));
        verify(service, VerificationModeFactory.times(1)).getStatistic();
        reset(service);
    }
}