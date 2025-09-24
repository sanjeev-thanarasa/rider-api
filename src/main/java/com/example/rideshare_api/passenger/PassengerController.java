package com.example.rideshare_api.passenger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.rideshare_api.driver.DriverController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

  private static final Logger log = LoggerFactory.getLogger(DriverController.class);

  private final PassengerRepository repo;

  public PassengerController(PassengerRepository repo) {
    this.repo = repo;
  }

  // Create
  @PostMapping
  public Passenger create(@RequestBody @jakarta.validation.Valid Passenger body) {
    log.info("Creating passenger name={} phone={}", body.getName(), body.getPhone());
    return repo.save(body);
  }

  // Get by ID
  @GetMapping("/{id}")
  public Passenger get(@PathVariable Long id) {
    log.info("Getting passenger id={}", id);
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  // Edit
  @PutMapping("/{id}")
  public Passenger edit(@PathVariable Long id, @RequestBody @jakarta.validation.Valid Passenger body) {
    Passenger p = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    p.setName(body.getName());
    p.setPhone(body.getPhone());
    log.info("Editing passenger id={} name={} phone={}", id, body.getName(), body.getPhone());
    return repo.save(p);
  }

  // List all
  @GetMapping
  public List<Passenger> list() {
    log.info("Listing passengers");
    return repo.findAll();
  }

  // Count
  @GetMapping("/count")
  public Map<String, Long> count() {
    log.info("Counting passengers");
    return Map.of("count", repo.count());
  }

  // Delete
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    log.warn("Deleting passenger id={}", id);
    repo.deleteById(id);
  }
}
