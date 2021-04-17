package tqs.lab.p2carservicedemo;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name="car-service")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long carId;

    @NotNull
    private String maker;

    @NotNull
    private String model;

    public Car(){}

    public Car(String maker, String model){
        this.maker = maker;
        this.model = model;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", maker='" + maker + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
