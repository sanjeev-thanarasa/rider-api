package com.example.rideshare_api.driver;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "drivers")
public class Driver {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "name is required")
  @Size(max = 100)
  @Column(nullable = false)
  private String name;

  @Size(max = 200)
  private String address;

  @Size(max = 20, message = "vehicleNumber max 20 chars")
  @Column(name = "vehicle_number")
  private String vehicleNumber;

  // getters & setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getVehicleNumber() {
    return vehicleNumber;
  }

  public void setVehicleNumber(String vehicleNumber) {
    this.vehicleNumber = vehicleNumber;
  }
}
