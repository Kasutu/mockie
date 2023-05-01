package com.splitscale.mockie.energyconsumption;

public class EnergyConsumption {
  private String id;
  private String energyConsumption;
  private String description;
  private String importance;

  public EnergyConsumption(String id, String energyConsumption, String description, String importance) {
    this.id = id;
    this.energyConsumption = energyConsumption;
    this.description = description;
    this.importance = importance;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEnergyConsumption() {
    return energyConsumption;
  }

  public void setEnergyConsumption(String energyConsumption) {
    this.energyConsumption = energyConsumption;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImportance() {
    return importance;
  }

  public void setImportance(String importance) {
    this.importance = importance;
  }
}
