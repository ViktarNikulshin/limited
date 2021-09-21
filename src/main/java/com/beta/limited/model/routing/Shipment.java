package com.beta.limited.model.routing;
import lombok.Data;

import java.util.List;
@Data
public class Shipment{
    public String id;
    public String name;
    public int priority;
    public Pickup pickup;
    public Delivery delivery;
    public List<Integer> size;
    public List<String> required_skills;
}
