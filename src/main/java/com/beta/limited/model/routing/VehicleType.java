package com.beta.limited.model.routing;
import lombok.Data;

import java.util.List;
@Data
public class VehicleType{
    public String type_id;
    public List<Integer> capacity;
    public String profile;
}
