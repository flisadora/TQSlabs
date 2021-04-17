package tqs.lab.p2carservicedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private CarReporitory carService;

    @PostMapping("/cars" )
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        HttpStatus status = HttpStatus.CREATED;
        Car saved = carService.save(car);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping(path="/cars")
    public List<Car> getAllCars() {
        return carService.findAll();
    }

    @GetMapping(path="/cars/id")
    public Car getCarById(Long carId){ return carService.findCarByCarId(carId); }

}
