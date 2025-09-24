package com.example.rideshare_api.passenger;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "passengers")
public class Passenger {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "name is required")
  @Size(max = 100)
  @Column(nullable = false)
  private String name;

  @NotBlank(message = "phone is required")
  @Pattern(regexp = "^[+0-9\\- ]{7,20}$", message = "invalid phone")
  @Column(nullable = false)
  private String phone;

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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}