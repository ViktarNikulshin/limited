package com.beta.limited.model.routing;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class Root{
    public List<Vehicle> vehicles;
    public List<VehicleType> vehicle_types;
    @JsonProperty("services")
    public List<ServicePoints> servicePoints;
    public List<Shipment> shipments;
    public List<Objective> objectives;
    public Configuration configuration;
}
