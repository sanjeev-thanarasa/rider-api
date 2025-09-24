package com.example.rideshare_api.passenger;
import jakarta.persistence.*;


@Entity
@Table(name = "passengers")
public class Passenger {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(nullable = false)
  private String name;


  @Column(nullable = false)
  private String phone;


  // getters & setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }
}