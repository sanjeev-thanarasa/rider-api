package com.example.rideshare_api.driver;
import jakarta.persistence.*;


@Entity
@Table(name = "drivers")
public class Driver {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(nullable = false)
  private String name;


  private String address;


  @Column(name = "vehicle_number")
  private String vehicleNumber;


  // getters & setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getAddress() { return address; }
  public void setAddress(String address) { this.address = address; }
  public String getVehicleNumber() { return vehicleNumber; }
  public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
}
