package com.example.rideshare_api.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

  private static final Logger log = LoggerFactory.getLogger(DriverController.class);

  private final DriverRepository repo;

  public DriverController(DriverRepository repo) {
    this.repo = repo;
  }

  // Create
  @PostMapping
  public Driver create(@RequestBody @jakarta.validation.Valid Driver body) {
    log.info("Creating driver name={} vehicle={}", body.getName(), body.getVehicleNumber());
    return repo.save(body);
  }

  // Get by ID
  @GetMapping("/{id}")
  public Driver get(@PathVariable Long id) {
    log.info("Getting driver id={}", id);
    return repo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  // Edit
  @PutMapping("/{id}")
  public Driver edit(@PathVariable Long id, @RequestBody @jakarta.validation.Valid Driver body) {
    Driver d = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    d.setName(body.getName());
    d.setAddress(body.getAddress());
    d.setVehicleNumber(body.getVehicleNumber());
    log.info("Editing driver id={} name={} vehicle={}", id, body.getName(), body.getVehicleNumber());
    return repo.save(d);
  }

  // List all
  @GetMapping
  public List<Driver> list() {
    log.info("Listing drivers");
    return repo.findAll();
  }

  // Count
  @GetMapping("/count")
  public Map<String, Long> count() {
    log.info("Counting drivers");
    return Map.of("count", repo.count());
  }

  // Delete
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    log.warn("Deleting driver id={}", id);
    repo.deleteById(id);
  }
}
