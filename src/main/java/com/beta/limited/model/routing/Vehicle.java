package com.beta.limited.model.routing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;
@Data
public class Vehicle{
    public String vehicle_id;
    @JsonIgnore
    public String type_id;
    public Address start_address;
    public Address end_address;
    @JsonIgnore
    public int earliest_start;
    @JsonIgnore
    public int latest_end;
    @JsonIgnore
    public int max_jobs;
    @JsonIgnore
    public List<String> skills;
}
