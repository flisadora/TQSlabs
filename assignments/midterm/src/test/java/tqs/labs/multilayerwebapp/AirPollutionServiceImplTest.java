package tqs.labs.multilayerwebapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AirPollutionServiceImplTest {

    @Mock( lenient = true)
    private CityRepository cityRepository;

    @InjectMocks
    private AirPollutionServiceImpl airPollutionService;

    @BeforeEach
    public void setUp() {
        City braga = new City("Braga",  48.12, -2.0, 2, 205.28,
                0, 1.65, 92.98, 0.65, 2.68, 4.36, 2.88);
        City lisboa = new City("Lisboa", 48.12, -2.0, 2, 205.28,
                0, 1.65, 92.98, 0.65, 2.68, 4.36, 2.88);
        City aveiro = new City("Aveiro", 48.12, -2.0, 2, 205.28,
                0, 1.65, 92.98, 0.65, 2.68, 4.36, 2.88);

        List<City> allCities = Arrays.asList(braga, lisboa,aveiro);

        airPollutionService.setMisses(3);

        Mockito.when(cityRepository.findByName(braga.getName())).thenReturn(braga);
        Mockito.when(cityRepository.findByName(lisboa.getName())).thenReturn(lisboa);
        Mockito.when(cityRepository.findByName(aveiro.getName())).thenReturn(aveiro);
        Mockito.when(cityRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(cityRepository.findById(braga.getId())).thenReturn(Optional.of(braga));
        Mockito.when(cityRepository.findAll()).thenReturn(allCities);
        Mockito.when(cityRepository.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
    public void whenValidName_thenCityShouldBeFound(){
        String name = "Braga";
        City found = airPollutionService.getAirPollution(name);

        assertThat(found.getName()).isEqualTo(name);
        verifyFindByNameIsCalledOnce("Braga");
    }

    @Test
    public void whenInvalidName_thenCityShouldNotBeFound(){
        City fromDb = airPollutionService.getAirPollution("wrong city");
        assertThat(fromDb).isNull();

        verifyFindByNameIsCalledOnce("wrong city");
    }

    @Test
    public void whenValidName_thenCityShouldExist(){
        boolean doesCityExist = airPollutionService.exists("Aveiro");
        assertThat(doesCityExist).isEqualTo(true);

        verifyFindByNameIsCalledOnce("Aveiro");
    }

    @Test
    public void whenNonExistingName_theCityShouldNotExist(){
        boolean doesCityExist = airPollutionService.exists("dont exist");
        assertThat(doesCityExist).isEqualTo(false);

        verifyFindByNameIsCalledOnce("dont exist");
    }

    @Test
    public void whenValidId_thenCityShouldBeFound() {
        City fromDb = airPollutionService.getCityById(cityRepository.findByName("Braga").getId());
        assertThat(fromDb.getName()).isEqualTo("Braga");

        verifyFindByIdIsCalledOnce();
    }

    @Test
    public void whenInValidId_thenCityShouldNotBeFound() {
        City fromDb = airPollutionService.getCityById(-99L);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
    public void given3City_whengetAll_thenReturn3Records() {
        City braga = new City("Braga",  48.12, -2.0, 2, 205.28,
                0, 1.65, 92.98, 0.65, 2.68, 4.36, 2.88);
        City lisboa = new City("Lisboa", 48.12, -2.0, 2, 205.28,
                0, 1.65, 92.98, 0.65, 2.68, 4.36, 2.88);
        City aveiro = new City("Aveiro", 48.12, -2.0, 2, 205.28,
                0, 1.65, 92.98, 0.65, 2.68, 4.36, 2.88);

        List<City> allCities = airPollutionService.getAllCitiesSave();
        verifyFindAllCitiesIsCalledOnce();
        assertThat(allCities).hasSize(3).extracting(City::getName).contains(braga.getName(), lisboa.getName(), aveiro.getName());
    }

    @Test
    public void whenGetStatistics_thenReturnHasMap() {
        HashMap<String, Integer> statistics =  airPollutionService.getStatistic();

        assertThat(statistics.get("Number of requests: ")).isEqualTo(3);
        assertThat(statistics.get("Number of hits: ")).isEqualTo(0);
        assertThat(statistics.get("Number of misses: ")).isEqualTo(3);
    }


    private void verifyFindByNameIsCalledOnce(String name_city) {
        Mockito.verify(cityRepository, VerificationModeFactory.times(1)).findByName(name_city);
        Mockito.reset(cityRepository);
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(cityRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
        Mockito.reset(cityRepository);
    }

    private void verifyFindAllCitiesIsCalledOnce() {
        Mockito.verify(cityRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(cityRepository);
    }
}