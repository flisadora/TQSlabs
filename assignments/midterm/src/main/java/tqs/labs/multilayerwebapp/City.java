package tqs.labs.multilayerwebapp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(min = 3, max = 20)
    private String name;
    private double lat;
    private double lng;

    private int air_quality_index;
    private String category;
    private double carbon;
    private double no;
    private double no2;
    private double o3;
    private double so2;
    private double pm25;
    private double pm10;
    private double nh3;

    private final Calendar timeOfRequest = Calendar.getInstance();

    public City() {
    }

    public City(String name, double lat, double lng, int air_quality_index,
                double carbon, double no, double no2, double o3, double so2,
                double pm25, double pm10, double nh3){

        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.air_quality_index = air_quality_index;
        this.carbon = carbon;
        this.no = no;
        this.no2 = no2;
        this.o3 = o3;
        this.so2 = so2;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.nh3 = nh3;
        setCategory(air_quality_index);

    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getLat() {
        return this.lat;
    }

    public double getLng() {
        return this.lng;
    }

    public int getAir_quality_index() { return this.air_quality_index; }

    public String getCategory() {
        return this.category;
    }

    public double getCarbon() { return carbon; }

    public double getNo() { return no; }

    public double getNo2() { return no2; }

    public double getO3() { return o3; }

    public double getSo2() { return so2; }

    public double getPm25() { return pm25; }

    public double getPm10() { return pm10; }

    public double getNh3() { return nh3; }

    public Calendar getTimeOfRequest(){
        return this.timeOfRequest;
    }

    public void setLat(double lat){
            this.lat = lat;
        }

    public void setLng(double lng) {
      this.lng = lng;
    }

    public void setAir_quality_index(int air_quality) {
        this.air_quality_index = air_quality;
    }

    public void setCategory(int air_quality_index) {
        switch(air_quality_index)
        {
            case 1:
                this.category = "Good";
                break;
            case 2:
                this.category = "Fair";
                break;
            case 3:
                this.category = "Moderate";
                break;
            case 4:
                this.category = "Poor";
                break;
            case 5:
                this.category = "Very Poor";
                break;
            default:
                this.category = "Undefined";
        }
    }

    public void setCarbon(double carbon) { this.carbon = carbon; }

    public void setNo(double no) { this.no = no; }

    public void setNo2(double no2) { this.no2 = no2; }

    public void setO3(double o3) { this.o3 = o3; }

    public void setSo2(double so2) { this.so2 = so2; }

    public void setPm25(double pm25) { this.pm25 = pm25; }

    public void setPm10(double pm10) { this.pm10 = pm10; }

    public void setNh3(double nh3) { this.nh3 = nh3; }
}
