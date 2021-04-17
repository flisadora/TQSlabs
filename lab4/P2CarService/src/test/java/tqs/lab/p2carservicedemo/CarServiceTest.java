package tqs.lab.p2carservicedemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.assertj.core.api.Assertions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarReporitory carRepository;

    @InjectMocks
    private CarManagerService carService;

    @BeforeEach
    public void setUp() {
        Car delorean = new Car("DMC", "DeLorean");
        Car ecto = new Car("Cadillac", "Ectomobile");
        ecto.setCarId(1984L);
        Car spyder = new Car("Ferrari", "250 GT California Spyder");

        List<Car> allCars = Arrays.asList(delorean, ecto, spyder);
    }

    @Test
    public void getCarDetails_whenExists() {
        given( carRepository.findCarByCarId(1984L)).willReturn( new Car("Cadillac", "Ectomobile"));
        Car car = carService.getCarDetails( 1984L);
        Assertions.assertThat( car.getModel()).isEqualTo("Ectomobile");
    }

    @Test
    public void getCarDetails_whenDoesntExist_returnsException() {
        Long nonExisting = -1L;
        given( carRepository.findCarByCarId( nonExisting )).willThrow(RuntimeException.class);

        Assertions.assertThatThrownBy( () -> { carService.getCarDetails(nonExisting); }).isInstanceOf(RuntimeException.class);
    }
}
