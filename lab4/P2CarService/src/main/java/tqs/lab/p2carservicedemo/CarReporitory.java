package tqs.lab.p2carservicedemo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CarReporitory extends JpaRepository<Car, Long> {

    public Car findCarByCarId(Long carId);
    public List<Car> findAll();
}
