package com.splitscale.mockie.energyconsumption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class EnergyConsumptionDatabase {

  private static final Map<String, EnergyConsumption> ENERGY_CONSUMPTION_MAP = new HashMap<>();

  private EnergyConsumptionDatabase() {
    // Private constructor to prevent instantiation of this utility class
  }

  public static void addEnergyConsumption(EnergyConsumption energyConsumption) {
    ENERGY_CONSUMPTION_MAP.put(energyConsumption.getId(), energyConsumption);
  }

  public static void editEnergyConsumption(String id, EnergyConsumption energyConsumption) {
    EnergyConsumption oldEnergyConsumption = ENERGY_CONSUMPTION_MAP.get(id);
    if (oldEnergyConsumption != null) {
      oldEnergyConsumption.setEnergyConsumption(energyConsumption.getEnergyConsumption());
      oldEnergyConsumption.setDescription(energyConsumption.getDescription());
      oldEnergyConsumption.setImportance(energyConsumption.getImportance());
      ENERGY_CONSUMPTION_MAP.put(id, oldEnergyConsumption);
    }
  }

  public static boolean deleteEnergyConsumption(String id) {
    ENERGY_CONSUMPTION_MAP.remove(id);
    return true;
  }

  public static EnergyConsumption getEnergyConsumption(String id) {
    return ENERGY_CONSUMPTION_MAP.get(id);
  }

  public static Map<String, EnergyConsumption> getAllEnergyConsumptions() {
    return ENERGY_CONSUMPTION_MAP;
  }

  public static List<EnergyConsumption> filterEnergyConsumptionsByImportance(String importance) {
    return ENERGY_CONSUMPTION_MAP.values().stream()
        .filter(e -> e.getImportance().equals(importance))
        .collect(Collectors.toList());
  }

  public static List<EnergyConsumption> searchEnergyConsumptions(String query) {
    return ENERGY_CONSUMPTION_MAP.values().stream()
        .filter(e -> e.getId().contains(query) || e.getEnergyConsumption().contains(query)
            || e.getDescription().contains(query) || e.getImportance().contains(query))
        .collect(Collectors.toList());
  }

}
