package com.splitscale.mockie.energyconsumption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/energy/consumption")
public class EnergyConsumptionController {

  private final Map<String, EnergyConsumption> energyConsumptionMap = new HashMap<>();

  @PostMapping("/add")
  public ResponseEntity<EnergyConsumption> addEnergyConsumption(@RequestBody EnergyConsumption energyConsumption) {
    energyConsumptionMap.put(energyConsumption.getId(), energyConsumption);
    return new ResponseEntity<>(energyConsumption, HttpStatus.CREATED);
  }

  @PutMapping("/edit/{id}")
  public ResponseEntity<EnergyConsumption> editEnergyConsumption(
      @PathVariable("id") String id, @RequestBody EnergyConsumption energyConsumption) {
    EnergyConsumption oldEnergyConsumption = energyConsumptionMap.get(id);
    if (oldEnergyConsumption == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    oldEnergyConsumption.setEnergyConsumption(energyConsumption.getEnergyConsumption());
    oldEnergyConsumption.setDescription(energyConsumption.getDescription());
    oldEnergyConsumption.setImportance(energyConsumption.getImportance());
    energyConsumptionMap.put(id, oldEnergyConsumption);
    return new ResponseEntity<>(oldEnergyConsumption, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<HttpStatus> deleteEnergyConsumption(@PathVariable("id") String id) {
    EnergyConsumption energyConsumption = energyConsumptionMap.remove(id);
    if (energyConsumption == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/read/{id}")
  public ResponseEntity<EnergyConsumption> readEnergyConsumption(@PathVariable("id") String id) {
    EnergyConsumption energyConsumption = energyConsumptionMap.get(id);
    if (energyConsumption == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(energyConsumption, HttpStatus.OK);
  }

  @GetMapping("/filter/{importance}")
  public ResponseEntity<List<EnergyConsumption>> filterEnergyConsumptions(
      @PathVariable("importance") String importance) {
    List<EnergyConsumption> filteredEnergyConsumptions = energyConsumptionMap.values().stream()
        .filter(e -> e.getImportance().equals(importance)).collect(Collectors.toList());
    return new ResponseEntity<>(filteredEnergyConsumptions, HttpStatus.OK);
  }

  @GetMapping("/search/{query}")
  public ResponseEntity<List<EnergyConsumption>> searchEnergyConsumptions(@PathVariable("query") String query) {
    List<EnergyConsumption> matchingEnergyConsumptions = new ArrayList<>();
    for (EnergyConsumption energyConsumption : energyConsumptionMap.values()) {
      if (energyConsumption.getId().contains(query) || energyConsumption.getEnergyConsumption().contains(query)
          || energyConsumption.getDescription().contains(query)
          || energyConsumption.getImportance().contains(query)) {
        matchingEnergyConsumptions.add(energyConsumption);
      }
    }
    return new ResponseEntity<>(matchingEnergyConsumptions, HttpStatus.OK);
  }

}
