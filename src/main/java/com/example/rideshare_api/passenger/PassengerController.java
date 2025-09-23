package com.example.rideshare_api.passenger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

  private final PassengerRepository repo;

  public PassengerController(PassengerRepository repo) {
    this.repo = repo;
  }

  // Create
  @PostMapping
  public Passenger create(@RequestBody Passenger body) {
    return repo.save(body);
  }

  // Get by ID
  @GetMapping("/{id}")
  public Passenger get(@PathVariable Long id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  // Edit
  @PutMapping("/{id}")
  public Passenger edit(@PathVariable Long id, @RequestBody Passenger body) {
    Passenger p = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    p.setName(body.getName());
    p.setPhone(body.getPhone());
    return repo.save(p);
  }

  // List all
  @GetMapping
  public List<Passenger> list() {
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
