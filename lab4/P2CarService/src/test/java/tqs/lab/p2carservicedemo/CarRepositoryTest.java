package tqs.lab.p2carservicedemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarReporitory carReporitory;

    @Test
    public void getCarDetails() {
        Car ecto = new Car("Cadillac", "Ectomobile");
        ecto.setCarId(1984L);
        entityManager.persistAndFlush(ecto);

        Car car = carReporitory.findCarByCarId(1984L);
        assertThat(car.getModel()).isEqualTo(ecto.getModel());
    }

}
