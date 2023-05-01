package com.splitscale.mockie.energyconsumption;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.splitscale.Loglemon;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/energy/consumption")
public class EnergyConsumptionController {

  @GetMapping
  public ResponseEntity<List<EnergyConsumption>> readEnergyConsumption() {
    Loglemon.sendLog("Inside readEnergyConsumption() method.");
    List<EnergyConsumption> energyConsumptions = EnergyConsumptionDatabase.getAllEnergyConsumptions();
    Loglemon.sendLog("Number of energy consumptions retrieved: " + energyConsumptions.size());
    return new ResponseEntity<List<EnergyConsumption>>(energyConsumptions, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<EnergyConsumption> addEnergyConsumption(@RequestBody Map<String, Object> energyConsumptionMap) {
    Loglemon.sendLog("Inside addEnergyConsumption() method.");
    String id = (String) energyConsumptionMap.get("id");
    String energyConsumption = (String) energyConsumptionMap.get("energyConsumption");
    String description = (String) energyConsumptionMap.get("description");
    String importance = (String) energyConsumptionMap.get("importance");

    EnergyConsumption newEnergyConsumption = new EnergyConsumption(id, energyConsumption, description, importance);
    EnergyConsumptionDatabase.addEnergyConsumption(newEnergyConsumption);

    Loglemon.sendLog("New energy consumption added: " + newEnergyConsumption.toString());
    return new ResponseEntity<>(newEnergyConsumption, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<EnergyConsumption> editEnergyConsumption(
      @PathVariable("id") String id, @RequestBody EnergyConsumption energyConsumption) {
    EnergyConsumption oldEnergyConsumption = EnergyConsumptionDatabase.getEnergyConsumption(id);
    if (oldEnergyConsumption == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    oldEnergyConsumption.setEnergyConsumption(energyConsumption.getEnergyConsumption());
    oldEnergyConsumption.setDescription(energyConsumption.getDescription());
    oldEnergyConsumption.setImportance(energyConsumption.getImportance());
    EnergyConsumptionDatabase.editEnergyConsumption(id, oldEnergyConsumption);
    return new ResponseEntity<>(oldEnergyConsumption, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteEnergyConsumption(@PathVariable("id") String id) {
    boolean isDeleted = EnergyConsumptionDatabase.deleteEnergyConsumption(id);
    if (!isDeleted) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/{id}")
  public ResponseEntity<EnergyConsumption> readEnergyConsumption(@PathVariable("id") String id) {
    EnergyConsumption energyConsumption = EnergyConsumptionDatabase.getEnergyConsumption(id);
    if (energyConsumption == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(energyConsumption, HttpStatus.OK);
  }

  @GetMapping("/filter/{importance}")
  public ResponseEntity<List<EnergyConsumption>> filterEnergyConsumptions(
      @PathVariable("importance") String importance) {
    List<EnergyConsumption> filteredEnergyConsumptions = EnergyConsumptionDatabase
        .filterEnergyConsumptionsByImportance(importance);
    return new ResponseEntity<>(filteredEnergyConsumptions, HttpStatus.OK);
  }

  @GetMapping("/search/{query}")
  public ResponseEntity<List<EnergyConsumption>> searchEnergyConsumptions(@PathVariable("query") String query) {
    List<EnergyConsumption> matchingEnergyConsumptions = EnergyConsumptionDatabase.searchEnergyConsumptions(query);
    return new ResponseEntity<>(matchingEnergyConsumptions, HttpStatus.OK);
  }
}
