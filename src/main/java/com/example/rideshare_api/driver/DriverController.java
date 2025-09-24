package com.example.rideshare_api.driver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

  private final DriverRepository repo;

  public DriverController(DriverRepository repo) {
    this.repo = repo;
  }

  // Create
  @PostMapping
  public Driver create(@RequestBody Driver body) {
    return repo.save(body);
  }

  // Get by ID
  @GetMapping("/{id}")
  public Driver get(@PathVariable Long id) {
    return repo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  // Edit
  @PutMapping("/{id}")
  public Driver edit(@PathVariable Long id, @RequestBody Driver body) {
    Driver d = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    d.setName(body.getName());
    d.setAddress(body.getAddress());
    d.setVehicleNumber(body.getVehicleNumber());
    return repo.save(d);
  }

  // List all
  @GetMapping
  public List<Driver> list() {
    return repo.findAll();
  }

  // Count
  @GetMapping("/count")
  public Map<String, Long> count() {
    return Map.of("count", repo.count());
  }

  // Delete
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    repo.deleteById(id);
  }
}
