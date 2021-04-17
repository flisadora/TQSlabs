package tqs.lab.p2carservicedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.List;

@Service
@Transactional
public class CarManagerService {

    @Autowired
    private CarReporitory carReporitory;

    public Car save(Car employee) {
        return carReporitory.save(employee);
    }

    public List<Car> getAllCars(){ return carReporitory.findAll(); }

    public Car getCarDetails(Long carId){ return carReporitory.findCarByCarId(carId); }
}
